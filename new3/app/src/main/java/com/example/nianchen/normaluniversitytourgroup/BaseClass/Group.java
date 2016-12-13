package com.example.nianchen.normaluniversitytourgroup.BaseClass;

/**
 * Created by zhangzhixin on 2016/12/11.
 */

public class Group {
    private String groupname;
    private String desc;
    private int img;

    public Group(String groupname, String desc, int img) {
        this.groupname = groupname;
        this.desc = desc;
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }


    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }


}
