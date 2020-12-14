package pershay.bstu.woolfy.profile;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import pershay.bstu.woolfy.R;
import pershay.bstu.woolfy.login.LoginActivity;
import pershay.bstu.woolfy.retrofit.APIClient;
import pershay.bstu.woolfy.retrofit.JsonPlaceHolderApi;
import pershay.bstu.woolfy.models.Post;
import pershay.bstu.woolfy.models.User;
import pershay.bstu.woolfy.models.UserData;
import pershay.bstu.woolfy.utils.BottomNavHelper;
import pershay.bstu.woolfy.utils.ProfileGridImageAdapter;
import pershay.bstu.woolfy.utils.SqliteHelper;
import pershay.bstu.woolfy.utils.UniversalImageLoader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    public interface OnGridImageSelectedListener{
        void onGridImageSelected(Post post, UserData userData, int activityNumber);
    }

    private OnGridImageSelectedListener mOnGridImageSelectedListener;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private static final int ACTIVITY_NUM = 4;
    private Context mContext;
    private Toolbar toolbar;
    private ImageView profileMenu;
    private TextView tvUserName, tvDisplayName, tvDescription, tvAge, tvCity, tvFolowers, tvFollowing, tvPosts, tvEditProfile, tvLogout;

    private static String TAG = "ProfileActivity";
    private ImageView profilePhoto;
    private ProgressBar mProgressBar;
    private GridView gridView;

    String userId, username, firstName, lastName;

    private UserData userData;

    private SqliteHelper helper;

    private static final int NUM_GRID_COLUMNS = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = getActivity();

        if(helper == null)
           helper = new SqliteHelper(getContext());

        toolbar = view.findViewById(R.id.profileToolBar);
        gridView = view.findViewById(R.id.gridView);
        mProgressBar = view.findViewById(R.id.profileProgresBar);
        profilePhoto = view.findViewById(R.id.profileImage);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvAge = view.findViewById(R.id.tvAge);
        tvCity = view.findViewById(R.id.tvCity);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvDisplayName = view.findViewById(R.id.tvDisplayName);
        tvFollowing = view.findViewById(R.id.tvFollowing);
        tvFolowers = view.findViewById(R.id.tvFollowers);
        tvPosts = view.findViewById(R.id.tvPosts);
        tvEditProfile = view.findViewById(R.id.tvEditProfile);
        tvLogout = view.findViewById(R.id.tvLogout);

        jsonPlaceHolderApi = APIClient.getClient().create(JsonPlaceHolderApi.class);

        BottomNavHelper.ACTIVE_NUM = 2;
        BottomNavigationViewEx bottomNavigationViewEx = view.findViewById(R.id.bottomNavViewBar);
        BottomNavHelper.setUpBottomNavigationView(mContext, bottomNavigationViewEx);

        getUserId();

        tvUserName.setText(username);

        setUpToolbar();
        downloadImage();
        getUserData();

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.deleteData();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        tvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                intent.putExtra(getString(R.string.firstname), firstName);
                intent.putExtra(getString(R.string.lastname), lastName);
                intent.putExtra(getString(R.string.description), tvDescription.getText());
                intent.putExtra(getString(R.string.city), tvCity.getText());
                intent.putExtra(getString(R.string.age), tvAge.getText().subSequence(0, 2));
                startActivity(intent);
            }
        });

        return view;
    }

    public void getUserId(){
        Cursor res = helper.getData();
        while(res.moveToNext()){
            userId = res.getString(0);
            username = res.getString(1);
        }
    }

    private void downloadImage(){
        Call<List<Post>> call = jsonPlaceHolderApi.downloadImage(userId);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                try{
                    setupGridView(response.body());
                    tvPosts.setText(String.valueOf(response.body().size()));
                }
                catch(Exception ex){
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getContext());
                    dlgAlert.setMessage("You haven't any photos! Add something.");
                    dlgAlert.setTitle("Hi!");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                    tvPosts.setText("0");
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
                Toast.makeText(mContext, "Response: "+response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(mContext, "error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

   private void getUserData(){
       Call<UserData> call = jsonPlaceHolderApi.getUserDataById(userId);
       call.enqueue(new Callback<UserData>() {
           @Override
           public void onResponse(Call<UserData> call, Response<UserData> response) {
               if (!response.isSuccessful()) {
                   Toast.makeText(mContext, "Code: "+response.code(), Toast.LENGTH_SHORT).show();
                   return;
               }
               userData = response.body();

               tvAge.setText(userData.getAge() + " y.o.");
               tvCity.setText(userData.getCity());
               tvDescription.setText(userData.getDescription());
               tvDisplayName.setText(userData.getFirstname() + " " + userData.getLastname());
               firstName = userData.getFirstname();
               lastName = userData.getLastname();
               tvFollowing.setText(String.valueOf(userData.getFollows()));
               tvFolowers.setText(String.valueOf(userData.getFollowers()));
           }

           @Override
           public void onFailure(Call<UserData> call, Throwable t) {
               Toast.makeText(mContext, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
   }

    private void setUpToolbar(){
        ((ProfileActivity)getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onAttach(Context context) {
        try{
            mOnGridImageSelectedListener = (OnGridImageSelectedListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
        super.onAttach(context);
    }

    private void setupGridView(final List<Post> posts){

        final ArrayList<String> photos = new ArrayList<>();

        for (Post url : posts) {
            photos.add(url.getImage());
        }

        profilePhoto.setImageBitmap(StringToImage(photos.get(photos.size() - 1)));
        mProgressBar.setVisibility(View.INVISIBLE);

        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);
        
        ProfileGridImageAdapter adapter = new ProfileGridImageAdapter(getActivity(),R.layout.layout_grid_imageview,
                "", photos);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mOnGridImageSelectedListener.onGridImageSelected(posts.get(position), userData, ACTIVITY_NUM);
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

}
