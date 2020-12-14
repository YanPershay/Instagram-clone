package com.example.http;

import com.google.gson.annotations.SerializedName;

public class Comment {

    public Comment(String commentText, int postId, String userId) {
        CommentText = commentText;
        PostId = postId;
        UserId = userId;
    }

    @SerializedName("commentId")
    private int CommentId;

    @SerializedName("commentText")
    private String CommentText;

    @SerializedName("postId")
    private int PostId;

    @SerializedName("userId")
    private String UserId;

    public void setCommentId(int commentId) {
        CommentId = commentId;
    }

    public void setCommentText(String commentText) {
        CommentText = commentText;
    }

    public void setPostId(int postId) {
        PostId = postId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }


    public int getCommentId() {
        return CommentId;
    }

    public String getCommentText() {
        return CommentText;
    }

    public int getPostId() {
        return PostId;
    }

    public String getUserId() {
        return UserId;
    }
}
