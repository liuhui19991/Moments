package com.liuhui.moments.ui.model;

/**
 * 回复评论的内容和用户信息
 * Created by liuhui on 2020/10/6.
 */

public class SenderModel {
    private String content;
    private UserInfoModel userInfoModelnfo;
    private String avatar;
    private String nick;
    private String username;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserInfoModel getUserInfoModelnfo() {
        return userInfoModelnfo;
    }

    public void setUserInfoModelnfo(UserInfoModel userInfoModelnfo) {
        this.userInfoModelnfo = userInfoModelnfo;
    }
}
