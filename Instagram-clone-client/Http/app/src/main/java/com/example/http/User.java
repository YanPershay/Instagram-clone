package com.example.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("userId")
    public String UserId;

    @SerializedName("username")
    public String Username;

    @SerializedName("email")
    public String Email;

    @SerializedName("password")
    public String Password;

    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;

    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserId() {
        return UserId;
    }

    public String getUsername() {
        return Username;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }
}
