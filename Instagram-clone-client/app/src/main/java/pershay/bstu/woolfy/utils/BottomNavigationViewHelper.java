package pershay.bstu.woolfy.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import pershay.bstu.woolfy.addphoto.AddPhotoActivity;
import pershay.bstu.woolfy.main.MainActivity;
import pershay.bstu.woolfy.profile.ProfileActivity;
import pershay.bstu.woolfy.R;

public class BottomNavigationViewHelper {

    public static void setUpBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ic_house:
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        break;

                    case R.id.ic_add:
                        Intent intent3 = new Intent(context, AddPhotoActivity.class);
                        context.startActivity(intent3);
                        break;

                    case R.id.ic_user:
                        Intent intent5 = new Intent(context, ProfileActivity.class);
                        context.startActivity(intent5);
                        break;
                }

                return false;
            }
        });
    }
}
