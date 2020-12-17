package pershay.bstu.woolfy.main;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

import pershay.bstu.woolfy.R;
import pershay.bstu.woolfy.ViewCommentsFragment;
import pershay.bstu.woolfy.addphoto.GalleryFragment;
import pershay.bstu.woolfy.login.LoginActivity;
import pershay.bstu.woolfy.models.Post;
import pershay.bstu.woolfy.utils.BottomNavHelper;
import pershay.bstu.woolfy.utils.SqliteHelper;
import pershay.bstu.woolfy.utils.UniversalImageLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = MainActivity.this;
        BottomNavHelper.ACTIVE_NUM = 0;

        if(!isUserLoggedin()){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavHelper.setUpBottomNavigationView(context, bottomNavigationViewEx);

        if (!ImageLoader.getInstance().isInited()) {
            initImageLoader();
        }
    }

    private Context mContext = MainActivity.this;
    SqliteHelper helper;

    private boolean isUserLoggedin(){
        if(helper == null)
            helper = new SqliteHelper(getApplicationContext());

        Cursor res = helper.getData();
        if(res.getCount() == 0){
            return false;
        }
        return true;
    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

}
