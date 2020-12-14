package com.example.http;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Post {
    public Post(String userId, String caption, int likesCount, String image) {
        UserId = userId;
        Caption = caption;
        LikesCount = likesCount;
        Image = image;
    }

    @SerializedName("postId")
    public int PostId;

    @SerializedName("userId")
    public String UserId;

    @SerializedName("caption")
    public String Caption;

    @SerializedName("likesCount")
    public int LikesCount;

    @SerializedName("image")
    public String Image;

    @SerializedName("dateCreated")
    public Date DateCreated;

    @SerializedName("commentId")
    public int CommentId;

    @SerializedName("user")
    public User user;

    public User getUser() {
        return user;
    }

    public void setPostId(int postId) {
        PostId = postId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public void setLikesCount(int likesCount) {
        LikesCount = likesCount;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setDateCreated(Date dateCreated) {
        DateCreated = dateCreated;
    }

    public void setCommentId(int commentId) {
        CommentId = commentId;
    }

    public int getPostId() {
        return PostId;
    }

    public String getUserId() {
        return UserId;
    }

    public String getCaption() {
        return Caption;
    }

    public int getLikesCount() {
        return LikesCount;
    }

    public String getImage() {
        return Image;
    }

    public Date getDateCreated() {
        return DateCreated;
    }

    public int getCommentId() {
        return CommentId;
    }
}
