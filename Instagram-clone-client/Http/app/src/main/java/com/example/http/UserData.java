package com.example.http;

import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("userId")
    public String UserId;

    @SerializedName("firstname")
    public String Firstname;

    @SerializedName("lastname")
    public String Lastname;

    @SerializedName("age")
    public int Age;

    @SerializedName("description")
    public String Description;

    @SerializedName("city")
    public String City;

    @SerializedName("followers")
    public int Followers;

    public void setUser(User user) {
        this.user = user;
    }

    @SerializedName("user")
    public User user;

    public User getUser() {
        return user;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public void setAge(int age) {
        Age = age;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setFollowers(int followers) {
        Followers = followers;
    }

    public void setFollows(int follows) {
        Follows = follows;
    }

    public void setIdData(int idData) {
        IdData = idData;
    }

    public String getFirstname() {
        return Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public int getAge() {
        return Age;
    }

    public String getDescription() {
        return Description;
    }

    public String getCity() {
        return City;
    }

    public int getFollowers() {
        return Followers;
    }

    public int getFollows() {
        return Follows;
    }

    public int getIdData() {
        return IdData;
    }

    @SerializedName("follows")
    public int Follows;

    @SerializedName("idData")
    public int IdData;

}
