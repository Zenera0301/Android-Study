package com.jsc4.aboutactivity.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserData {

    @SerializedName("title")
    private String mTitle;

    @SerializedName("content")
    private String mContent;

    @SerializedName("user")
    private User mUser;

    @SerializedName("images")
    private List<String> mImages;

    // get & set methods
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public List<String> getImages() {
        return mImages;
    }

    public void setImages(List<String> images) {
        mImages = images;
    }


    // 用户子类
    public class User{
        @SerializedName("id")
        private long mId;

        @SerializedName("name")
        private String mName;

        @SerializedName("avatar")
        private String mAvatar;
    }


}
