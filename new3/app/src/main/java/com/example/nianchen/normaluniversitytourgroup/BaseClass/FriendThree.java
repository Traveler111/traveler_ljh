package com.example.nianchen.normaluniversitytourgroup.BaseClass;

/**
 * Created by nianchen on 2016/11/24.
 */
public class FriendThree {
    private int image;
    private String name;
    private String personality;
    private int picture;
    private String mess;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public FriendThree(int image, String name, String personality, int picture, String mess) {
        this.image = image;
        this.name = name;
        this.personality = personality;
        this.picture = picture;
        this.mess = mess;
    }
}
