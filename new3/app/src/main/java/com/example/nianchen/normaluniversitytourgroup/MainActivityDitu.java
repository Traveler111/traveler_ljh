package com.example.nianchen.normaluniversitytourgroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbyResult;
import com.baidu.mapapi.radar.RadarNearbySearchOption;
import com.baidu.mapapi.radar.RadarSearchError;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.radar.RadarSearchManager;
import com.baidu.mapapi.radar.RadarUploadInfo;
import com.baidu.mapapi.radar.RadarUploadInfoCallback;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivityDitu extends AppCompatActivity implements  BaiduMap.OnMapClickListener,OnGetRoutePlanResultListener {
    // 百度地图视图
    TextureMapView mMapView = null;
    // 百度地图控制器
    private BaiduMap mBaiduMap = null;

    /* 定位的客户端 */
    private LocationClient mLocationClient = null;
    /* 周边雷达管理器 */
    private RadarSearchManager mRadarSearchManager = null;
    /* 定位的监听器 */
    public MyLocationListener mMyLocationListener = null;
    /* 周边雷达的监听器 */
    MyRadarSearchListener mRadarSerchListener = null;
    /* UserID */
    private String mUserID = "钢铁侠";

    // 百度地图 UI 控制器
    private UiSettings mUiSettings = null;
    /* 当前定位的模式 */
    private MyLocationConfiguration.LocationMode mCurrentMode
            = MyLocationConfiguration.LocationMode.NORMAL;
    /* 是否是第一次定位 */
    private volatile boolean isFristLocation = true;
    /* 最新一次的经纬度 */
    private double mCurrentLantitude;
    private double mCurrentLongitude;
    /* 地图定位的模式 */
    private String[] mStyles
            = new String[]{"地图模式【正常】 ",
            "地图模式【跟随】 ",
            "地图模式【罗盘】 "};
    /* 当前地图定位模式的 Index */
    private int mCurrentStyle = 0;
    // 浏览路线节点相关


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
// 注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main2);
// 获取地图控件引用
        mMapView = (TextureMapView) findViewById(R.id.bmapView);
        initBaiduMap();
        initMyLocation ();
        initRadarSearch ();
    }






    private void initRadarSearch() {
        // 获取周边雷达实例
        mRadarSearchManager = RadarSearchManager.getInstance();
// 周边雷达设置监听， RadarSearchListener 接口实现
        mRadarSerchListener = new MyRadarSearchListener();
        mRadarSearchManager.addNearbyInfoListener(mRadarSerchListener);
// 周边雷达设置用户身份标识， id 为 null 是设备标识，必须设置
        mRadarSearchManager.setUserID(mUserID);
    }

    /**
     * 初始化定位相关代码
     */
    private void initMyLocation() {
        // 定位 SDK 初始化
        mLocationClient = new
                LocationClient(getApplicationContext());
// 设置定位的相关配置
        LocationClientOption option = new
                LocationClientOption();
        option.setOpenGps(true); // 打开 gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000); // 自动定位间隔
        option.setIsNeedAddress(true);// 是否需要地址
        option.setIsNeedLocationPoiList(true);
// 定位模式
        option.setLocationMode(LocationClientOption.LocationMode.
                Hight_Accuracy);
// 根据配置信息对定位客户端进行设置
        mLocationClient.setLocOption(option);
// 注册定位监听
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);

        // 构建 Marker 图标
        int n;
        if(mUserID.equals("钢铁侠"))
            n = R.drawable.location;
        else
            n = R.drawable.sishen;
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(n);
        // 设置百度地图定位图层显示模式
        //////////////////////////////////////
        MyLocationConfiguration config1 = new
                MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL,
                true,
                bitmap);
        mBaiduMap.setMyLocationConfigeration(config1);
// 设置定位图标
//        BitmapDescriptor mCurrentMarker =
//                BitmapDescriptorFactory
//                        .fromResource(R.drawable.location);
//        MyLocationConfiguration config = new
//                MyLocationConfiguration(
//                mCurrentMode, true, mCurrentMarker);
//        mBaiduMap.setMyLocationConfigeration(config);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_map_myLoc: // 标注覆盖物
                center2myLoc();
                break;
            case R.id.id_menu_map_style: // 地图模式
                mCurrentStyle = (++mCurrentStyle) %
                        mStyles.length;
                item.setTitle(mStyles[mCurrentStyle]);
// 设置自定义图标
                switch (mCurrentStyle)
                {
                    case 0:
                        mCurrentMode =
                                MyLocationConfiguration.LocationMode.NORMAL;
                        break;
                    case 1:
                        mCurrentMode =
                                MyLocationConfiguration.LocationMode.FOLLOWING;
                        break;
                    case 2:
                        mCurrentMode =
                                MyLocationConfiguration.LocationMode.COMPASS;
                        break;
                }
                BitmapDescriptor mCurrentMarker =
                BitmapDescriptorFactory
                        .fromResource(R.drawable.location);
                MyLocationConfiguration config = new
                        MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker);
                mBaiduMap.setMyLocationConfigeration(config);
                break;
            case R.id.id_menu_map_upload: // 上传位置
                mRadarSearchManager.startUploadAuto(mRadarSerchListener,
                        5000);
                break;
            case R.id.id_menu_map_destory: // 销毁位置
// 停止上传位置信息
                mRadarSearchManager.stopUploadAuto();
// 清除用户信息
                mRadarSearchManager.clearUserInfo();
                break;
            case R.id.id_menu_map_search: // 查询周边
// 构造请求参数，其中 centerPt 是自己的位置坐标
                LatLng ll = new LatLng(mCurrentLantitude,
                        mCurrentLongitude);
                RadarNearbySearchOption option
                        = new RadarNearbySearchOption()
                        .centerPt(ll) // 搜索中心点
                        .pageNum(0) // 分页编号
                        .pageCapacity(50) // 每页容量
                        .radius(2000); // 检索半径
// 发起查询请求
                mRadarSearchManager.nearbyInfoRequest(option);
                break;
        }
        return super.onOptionsItemSelected(item);
    }











    /**
     * 添加标注覆盖物
     */
    private void addMarkerOverlay() {
        // 定义 Maker 坐标点
        LatLng point = new LatLng(39.913285, 116.403923);
// 构建 Marker 图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.marker);
// 构建 MarkerOption，用于在地图上添加 Marker
        OverlayOptions option = new MarkerOptions()
                .position(point) // 设置 marker 的位置
                .draggable(true) // 设置是否允许拖拽
                .title("国旗") // 设置 marker 的 title
                .icon(bitmap); // 必须设置 marker 图标
//在地图上添加 Marker，并显示
        Marker marker = (Marker)
                mBaiduMap.addOverlay(option);
    }


    /* 初始化 Baidu 地图相关设置 */
    private void initBaiduMap() {
// 获取地图视图
        mMapView = (TextureMapView) findViewById(R.id.bmapView);
// 获取地图控制器
        mBaiduMap = mMapView.getMap();
// 设置比例尺为 500M
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
// 获取地图 UI 控制器
        mUiSettings = mBaiduMap.getUiSettings();
// 隐藏指南针
        mUiSettings.setCompassEnabled(false);
        setMarkerListener();
    }

    private void setMarkerListener() {
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
            // 点击处理
                Toast.makeText(MainActivityDitu.this,
                        marker.getTitle(),
                        Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    /**
     * 设置周边雷达监听
     */
    public class MyRadarSearchListener implements RadarSearchListener,RadarUploadInfoCallback {

        @Override
        public void onGetNearbyInfoList(RadarNearbyResult radarNearbyResult, RadarSearchError radarSearchError) {
            if (radarSearchError ==
                    RadarSearchError.RADAR_NO_ERROR) {
                Log.i("RadarUpload", "查询周边成功");
// 清理覆盖物
                mBaiduMap.clear();
                for (int i=0; i<
                        radarNearbyResult.infoList.size(); i++) {
                    Log.i("RadarUpload", "NO." + i + " : " +
                            radarNearbyResult.infoList.get(i).userID + "\n" +
                            radarNearbyResult.infoList.get(i).comments + "\n" +
                            radarNearbyResult.infoList.get(i).distance + "\n" +
                            radarNearbyResult.infoList.get(i).pt +
                            "\n" +
                            radarNearbyResult.infoList.get(i).timeStamp);
                    addMarker(radarNearbyResult.infoList.get(i).userID,
                            radarNearbyResult.infoList.get(i).pt);
                }
            } else {
                Log.i("RadarUpload", "查询周边错误： " +
                        radarSearchError.toString());
            }
        }

        @Override
        public void onGetUploadState(RadarSearchError radarSearchError) {
            SimpleDateFormat simpleDateFormat = new
                    SimpleDateFormat("hh:mm:ss :");
            Date curDate = new Date(System.currentTimeMillis());
            String strTime = simpleDateFormat.format(curDate);
            if (radarSearchError ==
                    RadarSearchError.RADAR_NO_ERROR) {
//上传成功
                Log.i("RadarUpload", strTime + "上传成功");
            } else {
//上传失败
                Log.i("RadarUpload", strTime + "上传错误： " +
                        radarSearchError.toString());
            }
        }

        @Override
        public void onGetClearInfoState(RadarSearchError radarSearchError) {
            if (radarSearchError ==
                    RadarSearchError.RADAR_NO_ERROR) {
//清除成功
                Log.i("RadarUpload", "清除位置成功");
            } else {
//清除失败
                Log.i("RadarUpload", "清除位置失败");
            }

        }

        @Override
        public RadarUploadInfo onUploadInfoCallback() {
            // 将要上传的 Info
            RadarUploadInfo info = new RadarUploadInfo();
// Info 的备注信息
            SimpleDateFormat simpleDateFormat = new
                    SimpleDateFormat("hhmmss");
            Date curDate = new Date(System.currentTimeMillis());
            String str = simpleDateFormat.format(curDate);
            info.comments = str;
// Info 的点信息
            LatLng pt = new LatLng(mCurrentLantitude,
                    mCurrentLongitude);
            info.pt = pt;
// 返回要上传的信息，即上传信息
            return info;
        }
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
// mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
// 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
// 此处设置开发者获取到的方向信息，顺时针 0-360
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
// 设置 BaiduMap 的定位数据
            mBaiduMap.setMyLocationData(locData);
// 记录位置信息
            mCurrentLantitude = location.getLatitude();
            mCurrentLongitude = location.getLongitude();
// 第一次定位时，将地图位置移动到当前位置
            if (isFristLocation) {
                isFristLocation = false;
                center2myLoc();
            }
// Log 记录位置信息
            StringBuffer sb = new StringBuffer(256);
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\naddress : ");
            sb.append(location.getAddrStr());
            for(int i=0; i<location.getPoiList().size(); i++) {
                Poi p = location.getPoiList().get(i);
                sb.append("\nPoi NO.");
                sb.append(i);
                sb.append(" : ");
                sb.append(p.getId());
                sb.append("-");
                sb.append(p.getName());
                sb.append("-");
                sb.append(p.getRank());
            }
            Log.i("BaiduLocationInfo", sb.toString());
        }
    }
    private void addMarker(String userID, LatLng pt){
        int n;
        if(userID.equals("钢铁侠"))
            n = R.drawable.gangtiexia;
        else if(userID.equals("蝙蝠侠"))
            n = R.drawable.bianfuxia;
        else if(userID.equals("闪电侠"))
            n = R.drawable.shandianxia;
        else
            n = R.drawable.sishen;
// 构建 Marker 图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(n);
// 构建 MarkerOption，用于在地图上添加 Marker
        OverlayOptions option = new MarkerOptions()
                .position(pt)// 设置 marker 的位置
                .icon(bitmap); // 必须设置 marker 图标
//在地图上添加 Marker，并显示
        Marker marker = (Marker)mBaiduMap.addOverlay(option);
    }

    public void center2myLoc(){
        LatLng ll = new LatLng(mCurrentLantitude,
                mCurrentLongitude);
// 设置当前定位位置为 BaiduMap 的中心点，并移动到定位位置
        MapStatusUpdate u =
                MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }

    @Override
    protected void onStart() {
        // 开启图层定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
        {
            mLocationClient.start();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        // 关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //移除监听
        mRadarSearchManager.removeNearbyInfoListener(mRadarSerchListener);
//释放资源
        mRadarSearchManager.destroy();
        mRadarSearchManager = null;
        super.onDestroy();
// 在 activity 执行 onDestroy 时执行 mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
