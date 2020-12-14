package com.example.http;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @Headers({"Content-Type: application/json"})
    @GET("comment/{id}")
    Call<List<Comment>> getCommentByPostId(@Path("id")int postId);

    //@GET("user/be2a17a4-f601-4297-a4f4-3f8de7140ea8")
    //Call<User> getJson();

    @GET("posts/{id}")
    Call<List<Post>> getPostByUserId(@Path("id")String userId);

    @GET("userdata/{id}")
    Call<UserData> getUserDataById(@Path("id")String userId);

    @GET("user/{id}")
    Call<User> getUserById(@Path("id")String userId);

    @POST("comment")
    Call<Comment> addComment(@Body Comment comment);

    @POST("posts")
    Call<Post> addPost(@Body Post post);


}
