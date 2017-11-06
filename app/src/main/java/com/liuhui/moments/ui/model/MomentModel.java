package com.liuhui.moments.ui.model;


import java.util.List;

/**
 * 朋友圈所有数据
 * Created by liuhui on 2017/11/4.
 */

public class MomentModel extends DataModel {

    private String content;
    private SenderModel sender;
    private String error;
    private List<ImagesModel> images;
    private List<CommentsModel> comments;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ImagesModel> getImages() {
        return images;
    }

    public void setImages(List<ImagesModel> images) {
        this.images = images;
    }

    public List<CommentsModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentsModel> comments) {
        this.comments = comments;
    }

    public SenderModel getSender() {
        return sender;
    }

    public void setSender(SenderModel sender) {
        this.sender = sender;
    }

}
