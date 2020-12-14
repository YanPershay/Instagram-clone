package com.example.http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPostActivity extends AppCompatActivity {

    EditText etCaption, etLikes, etImage, etUserId;
    Button btnPost;
    TextView tvResult;

    JsonPlaceHolderApi jsonPlaceHolderApi;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        etCaption = findViewById(R.id.etCaption);
        etLikes = findViewById(R.id.etLikes);
        etImage = findViewById(R.id.etImage);
        etUserId = findViewById(R.id.etUserId);
        tvResult = findViewById(R.id.tvResult);
        btnPost = findViewById(R.id.btnPost);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://fff37e4e.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost();
            }
        });
    }

    private void addPost(){
        int likes = Integer.parseInt(etLikes.getText().toString());
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Post post = new Post(etUserId.getText().toString(), etCaption.getText().toString(), likes, etImage.getText().toString());
        Call<Post> call = jsonPlaceHolderApi.addPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    tvResult.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "UserId " + postResponse.getUserId() + "\n";
                content += "Caption " + postResponse.getCaption() + "\n";
                content += "LikesCount " + postResponse.getLikesCount() + "\n";
                content += "Date " + postResponse.getDateCreated() + "\n";
                content += "Image " + postResponse.getImage() + "\n";
                tvResult.append(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                tvResult.setText(t.getMessage());
            }
        });
    }
}
