package pershay.bstu.woolfy.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavHelper {

    public static int ACTIVE_NUM;

    public static void setUpBottomNavigationView(Context context, BottomNavigationViewEx bottomNavigationViewEx){
        BottomNavigationViewHelper.setUpBottomNavigationView(bottomNavigationViewEx);

        BottomNavigationViewHelper.enableNavigation(context, bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVE_NUM);
        menuItem.setChecked(true);
    }
}
