package pershay.bstu.woolfy.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import pershay.bstu.woolfy.R;
import pershay.bstu.woolfy.UserProfileActivity;
import pershay.bstu.woolfy.UserProfileFragment;
import pershay.bstu.woolfy.models.Heart;
import pershay.bstu.woolfy.models.Post;
import pershay.bstu.woolfy.models.User;
import pershay.bstu.woolfy.retrofit.APIClient;
import pershay.bstu.woolfy.retrofit.JsonPlaceHolderApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainfeedListAdapter extends ArrayAdapter<Post> {

    private LayoutInflater mInflater;
    private int mLayoutResource;
    private Context mContext;
    private Bitmap bitmap;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private ArrayList<String> photos;

    public MainfeedListAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResource = resource;
        this.mContext = context;
    }

    static class ViewHolder{
        CircleImageView mprofileImage;
        TextView username, timeDelta, caption, captionUsername, tvDate;
        SquareImageview image;
        ImageView heartRed, heartWhite;
        StringBuilder users;
        Heart heart;
        GestureDetector detector;
        Post photo;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();
            jsonPlaceHolderApi = APIClient.getClient().create(JsonPlaceHolderApi.class);
            holder.username = convertView.findViewById(R.id.tvUsername);
            holder.image = convertView.findViewById(R.id.post_image);
            holder.heartRed = convertView.findViewById(R.id.image_heart_red);
            holder.heartWhite = convertView.findViewById(R.id.image_heart);
            holder.caption = convertView.findViewById(R.id.tvCaption);
            holder.timeDelta = convertView.findViewById(R.id.image_time_posted);
            holder.mprofileImage = convertView.findViewById(R.id.profile_photo);
            holder.captionUsername = convertView.findViewById(R.id.tvUsernameCaption);
            holder.tvDate = convertView.findViewById(R.id.tvDate);

            photos = new ArrayList<>();
            holder.photo = getItem(position);

            holder.users = new StringBuilder();

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Date date = new Date();
        long diffInMillies = Math.abs(date.getTime() - getItem(position).getDateCreated().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        holder.photo = getItem(position);
        holder.detector = new GestureDetector(mContext, new GestureListener(holder));
        holder.username.setText(getItem(position).getUser().getUsername());
        holder.caption.setText(getItem(position).getCaption());
        holder.captionUsername.setText(getItem(position).getUser().getUsername());
        String postedDate;
        if(diff == 0){
            postedDate = "Posted today";
        } else{
            postedDate = "Posted " + diff + " days ago";
        }
        holder.tvDate.setText(postedDate);
        bitmap = StringToImage(getItem(position).getImage());
        holder.image.setImageBitmap(bitmap);

        Call<List<Post>> call = jsonPlaceHolderApi.downloadImage(getItem(position).getUser().getUserId());
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                for (Post photo: response.body()) {
                    photos.add(photo.getImage());
                }
                holder.mprofileImage.setImageBitmap(StringToImage(photos.get(photos.size() - 1)));
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(mContext, "error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.mprofileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserProfileActivity.class);
                intent.putExtra("userId", getItem(position).getUser().getUserId());
                intent.putExtra("userName", getItem(position).getUser().getUsername());
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    private Bitmap StringToImage(String image){
        try{
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);

            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener{
        ViewHolder holder;

        public GestureListener(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            holder.heart.toggleLike();
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }
    }

}
