package com.example.nianchen.normaluniversitytourgroup.BaseClass;

/**
 * Created by nianchen on 2016/11/24.
 */
public class FriendOne {
    private int img;
    private String name;
    private String misoshu;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMisoshu() {
        return misoshu;
    }

    public void setMisoshu(String misoshu) {
        this.misoshu = misoshu;
    }

    public FriendOne(int img, String name, String misoshu) {

        this.img = img;
        this.name = name;
        this.misoshu = misoshu;
    }
}
