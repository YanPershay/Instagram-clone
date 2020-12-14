package pershay.bstu.woolfy.addphoto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import pershay.bstu.woolfy.main.MainActivity;
import pershay.bstu.woolfy.R;
import pershay.bstu.woolfy.retrofit.APIClient;
import pershay.bstu.woolfy.retrofit.JsonPlaceHolderApi;
import pershay.bstu.woolfy.models.Post;
import pershay.bstu.woolfy.utils.SqliteHelper;
import pershay.bstu.woolfy.utils.UniversalImageLoader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NextActivity extends AppCompatActivity {

    private String mAppend = "file:/";
    private String imgUrl;
    private Bitmap bitmap;
    ImageView image, shareClose;
    TextView nextScreen;
    EditText description;
    private SqliteHelper helper;
    String userId;
    ProgressBar mProgressBar;

    private static final int NOTIFY_ID = 101;

    private static String CHANNEL_ID = "AddPhotoChannel";

    @Override
    protected void onPause() {
        super.onPause();
        this.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        image = findViewById(R.id.imageShare);
        description = findViewById(R.id.description);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);

        if(helper == null)
            helper = new SqliteHelper(getApplicationContext());

        getUserId();

        shareClose = findViewById(R.id.ivBackArrow);
        shareClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            setImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        nextScreen = findViewById(R.id.tvShare);
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhoto();
            }
        });

    }

    public void getUserId(){
        Cursor res = helper.getData();
        while(res.moveToNext()){
            userId = res.getString(0);
        }
    }

    private void addPhoto(){
        mProgressBar.setVisibility(View.VISIBLE);
        String image = ImageToString();

        Post post = new Post(userId, description.getText().toString(), 1, image);

        JsonPlaceHolderApi jsonPlaceHolderApi = APIClient.getClient().create(JsonPlaceHolderApi.class);

        Call<Post> call = jsonPlaceHolderApi.uploadImage(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                notification();
                mProgressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(NextActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(NextActivity.this);
                dlgAlert.setMessage("Error: " + t.getMessage());
                dlgAlert.setTitle("Fail");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

                mProgressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(NextActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void notification(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(NextActivity.this, "notify_001");

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.setBigContentTitle("Success");

        mBuilder.setSmallIcon(R.drawable.inst_logo);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.inst_logo));
        mBuilder.setContentText("You upload photo successfully!");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        NotificationManager mNotificationManager =
                (NotificationManager) NextActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }

    private void setImage() throws IOException {
        Intent intent = getIntent();

        if(intent.hasExtra(getString(R.string.selected_image))){
            imgUrl = intent.getStringExtra(getString(R.string.selected_image));
            UniversalImageLoader.setImage(imgUrl, image, null, mAppend);
            Uri uri = Uri.fromFile(new File(imgUrl));
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        }
        else if(intent.hasExtra(getString(R.string.selected_bitmap))){
            bitmap = intent.getParcelableExtra("selected_bitmap");
            image.setImageBitmap(bitmap);
        }
    }

    private String ImageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imagebyte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imagebyte,Base64.DEFAULT);
    }
}
