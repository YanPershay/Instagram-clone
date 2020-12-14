package pershay.bstu.woolfy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import okhttp3.ResponseBody;
import pershay.bstu.woolfy.models.Heart;
import pershay.bstu.woolfy.models.Post;
import pershay.bstu.woolfy.models.UserData;
import pershay.bstu.woolfy.profile.EditProfileActivity;
import pershay.bstu.woolfy.profile.ProfileActivity;
import pershay.bstu.woolfy.retrofit.APIClient;
import pershay.bstu.woolfy.retrofit.JsonPlaceHolderApi;
import pershay.bstu.woolfy.utils.SquareImageview;
import pershay.bstu.woolfy.utils.UniversalImageLoader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPostFragment extends Fragment {
    private static final String TAG = "ViewPostFragment";

    public interface OnCommentThreadSelectedListener{
        void onCommentThreadSelectedListener(Post post);
    }

    OnCommentThreadSelectedListener mOnCommentThreadSelectedListener;

    private Post mPost;
    private int mActivityNumber = 0;
    private SquareImageview mPostImage;
    private ImageView mBackArrow, mEllipses, mHeartRed, mHeartWhite, mProfileImage, mComment, mDelete;
    private TextView mCaption, tvUsername, tvUsernameCaption, tvCaption;
    private GestureDetector mGestureDetector;
    private Heart mHeart;
    Bitmap bitmap;

    public ViewPostFragment(){
        super();
        setArguments(new Bundle());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_post, container, false);
        mPostImage = view.findViewById(R.id.post_image);
        mBackArrow = view.findViewById(R.id.imageBackArrow);
        mEllipses = view.findViewById(R.id.ivEllipses);
        mHeartRed = view.findViewById(R.id.image_heart_red);
        mHeartWhite = view.findViewById(R.id.image_heart);
        mProfileImage = view.findViewById(R.id.profile_photo);
        mComment = view.findViewById(R.id.speech_bubble);
        mCaption = view.findViewById(R.id.tvCaption);
        mDelete = view.findViewById(R.id.ivDelete);
        mGestureDetector = new GestureDetector(getActivity(), new GestureListener());

        tvUsername = view.findViewById(R.id.tvUsername);
        tvCaption = view.findViewById(R.id.tvCaption);
        tvUsernameCaption = view.findViewById(R.id.tvUsernameCaption);

        mHeartRed.setVisibility(View.GONE);
        mHeartWhite.setVisibility(View.VISIBLE);
        mHeart = new Heart(mHeartWhite, mHeartRed);

        mPost = getPostFromBundle();

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getContext());
                dlgAlert.setMessage("Are you sure you want to delete this post?");
                dlgAlert.setTitle("Delete");
                dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletePost();
                    }
                });
                dlgAlert.setCancelable(true);
                dlgAlert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                dlgAlert.create().show();

            }
        });

        tvUsernameCaption.setText(getPostFromBundle().getUser().getUsername());
        tvCaption.setText(" " + mPost.getCaption());

        try{
            bitmap = StringToImage(mPost.getImage());
            mPostImage.setImageBitmap(bitmap);
            mProfileImage.setImageBitmap(bitmap);
            mActivityNumber = getActivityNumFromBundle();
        }catch (NullPointerException e){
            Log.e(TAG, "onCreateView: NullPointerException: photo was null from bundle" + e.getMessage());
        }

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCommentThreadSelectedListener.onCommentThreadSelectedListener(mPost);
            }
        });

        tvUsername.setText(getPostFromBundle().getUser().getUsername());

        testToggle();

        return view;
    }

    private void deletePost(){
        JsonPlaceHolderApi jsonPlaceHolderApi = APIClient.getClient().create(JsonPlaceHolderApi.class);

        Call<ResponseBody> call = jsonPlaceHolderApi.deletePost(mPost.PostId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getContext(), "server  response : "+response.code(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getContext());
                dlgAlert.setMessage("Error: " + t.getMessage());
                dlgAlert.setTitle("Fail");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
        });
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
                mOnCommentThreadSelectedListener = (OnCommentThreadSelectedListener) getActivity();
        }catch(ClassCastException ex){
            Log.e(TAG,  "onAttach: CLass cast exxception: " + ex.getMessage());
        }
    }

    private void testToggle(){
        mHeartRed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: readHeart detected");
                return mGestureDetector.onTouchEvent(event);
            }
        });

        mHeartWhite.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: whiteHeart detected");
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    private Post getPostFromBundle(){
        Bundle bundle = this.getArguments();
        if(bundle != null){
            return bundle.getParcelable(getString(R.string.photo));
        }else{
            return null;
        }
    }

    private UserData getUsernameFromBundle(){
        Bundle bundle = this.getArguments();
        if(bundle != null){
            return bundle.getParcelable(getString(R.string.userData));
        }else{
            return null;
        }
    }

    private int getActivityNumFromBundle(){
        Bundle bundle = this.getArguments();
        if(bundle != null){
            return bundle.getInt(getString(R.string.activity_number));
        }else{
            return 0;
        }
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            mHeart.toggleLike();
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(TAG, "onTouch: dbltap detected");

            return true;
        }
    }
}
