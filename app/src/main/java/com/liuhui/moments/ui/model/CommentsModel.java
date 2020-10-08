package com.liuhui.moments.ui.model;

/**
 * 评论
 * Created by liuhui on 2020/10/6.
 */

public class CommentsModel extends DataModel{
    private String content;
    private UserInfoModel sender;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserInfoModel getSender() {
        return sender;
    }

    public void setSender(UserInfoModel sender) {
        this.sender = sender;
    }
}
