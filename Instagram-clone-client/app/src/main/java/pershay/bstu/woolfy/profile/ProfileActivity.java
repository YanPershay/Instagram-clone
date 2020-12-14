package pershay.bstu.woolfy.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import pershay.bstu.woolfy.ViewCommentsFragment;
import pershay.bstu.woolfy.ViewPostFragment;
import pershay.bstu.woolfy.R;
import pershay.bstu.woolfy.models.Post;
import pershay.bstu.woolfy.models.UserData;
import pershay.bstu.woolfy.utils.BottomNavHelper;

public class ProfileActivity extends AppCompatActivity implements ProfileFragment.OnGridImageSelectedListener, ViewPostFragment.OnCommentThreadSelectedListener {

    @Override
    protected void onPause() {
        super.onPause();
        this.finishAffinity();
    }

    @Override
    public void onGridImageSelected(Post post, UserData userData, int activityNumber) {
        ViewPostFragment fragment = new ViewPostFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.photo), post);
        args.putParcelable(getString(R.string.userData), userData);
        args.putInt(getString(R.string.activity_number), activityNumber);

        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.view_post_fragment));
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavHelper.ACTIVE_NUM = 4;
        init();
    }

    private void init(){
        ProfileFragment fragment = new ProfileFragment();
        FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.profile_fragment));
        transaction.commit();
    }

    @Override
    public void onCommentThreadSelectedListener(Post post) {
        ViewCommentsFragment fragment = new ViewCommentsFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.photo), post);
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.view_comments_fragment));
        transaction.commit();
    }

}
