package com.example.http;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Retrofit retrofit;
    ArrayList<Comment> comments;
    User user;
    UserData userdata;
    Button btnToPostComment, btnToPostPost;

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tvResult);
        btnToPostComment = findViewById(R.id.btnToPostComment);
        btnToPostPost = findViewById(R.id.btnToPostPost);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://0ebfc59b.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getCommentById();
        //getUser();
        //getUserData();
        getPostById();

        btnToPostPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
                startActivity(intent);
            }
        });

    }

    public void getPostById(){
        Call<List<Post>> call = jsonPlaceHolderApi.getPostByUserId("be2a17a4-f601-4297-a4f4-3f8de7140ea8");

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code:" + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts) {
                    String content = "";
                    content += "PostId " + post.getPostId() + "\n";
                    content += "UserId " + post.getUserId() + "\n";
                    content += "Caption " + post.getCaption() + "\n";
                    content += "DateCreated " + post.getDateCreated() + "\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    public void getCommentById(){
        Call<List<Comment>> call = jsonPlaceHolderApi.getCommentByPostId(11);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code:" + response.code());
                    return;
                }

                List<Comment> comments = response.body();

                for (Comment comment : comments) {
                    String content = "";
                    content += "CommentId " + comment.getCommentId() + "\n";
                    content += "CommentText " + comment.getCommentText() + "\n";
                    content += "PostId " + comment.getPostId() + "\n";
                    content += "UserId " + comment.getUserId() + "\n";

                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    public void getUser(){

        Call<User> call = jsonPlaceHolderApi.getUserById("be2a17a4-f601-4297-a4f4-3f8de7140ea8");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code:" + response.code());
                    return;
                }
                    textView.setText(response.body().getEmail());
                    comments = new ArrayList<>(response.body().getComments());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }

    public void getUserData(){
        Call<UserData> call = jsonPlaceHolderApi.getUserDataById("be2a17a4-f601-4297-a4f4-3f8de7140ea8");
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code:" + response.code());
                    return;
                }

                //textView.setText(response.body().getDescription());
                textView.setText(response.body().getFirstname());
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}

