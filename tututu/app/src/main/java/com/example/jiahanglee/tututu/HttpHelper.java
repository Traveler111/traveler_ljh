package com.example.jiahanglee.tututu;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by jiahang Lee on 2016/12/14.
 */

public class HttpHelper {
    public class HTTPHelper {

        public HTTPHelper() {
        }

        public void get(String url,ResponseHandlerInterface callback){
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url,callback);
        }

    }
}
