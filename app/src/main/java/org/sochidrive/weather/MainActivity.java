package org.sochidrive.weather;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import org.sochidrive.weather.fragment.DeveloperFragment;
import org.sochidrive.weather.fragment.FeedbackFragment;
import org.sochidrive.weather.fragment.SettingsFragment;
import org.sochidrive.weather.fragment.HomeFragment;
import org.sochidrive.weather.fragment.ChangeCityFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends BaseActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setHomeFragment();
        setOnClickForSideMenuItems();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
            navigationView.setCheckedItem(R.id.nav_home);
        }
        super.onBackPressed();
    }

    private void setOnClickForSideMenuItems() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home: {
                        setHomeFragment();
                        drawer.close();
                        break;
                    }
                    case R.id.nav_gallery: {
                        setSettingsFragment();
                        drawer.close();
                        break;
                    }
                    case R.id.nav_slideshow: {
                        setSlideshowFragment();
                        drawer.close();
                        break;
                    }
                    case R.id.nav_feedback: {
                        setFeedbackFragment();
                        drawer.close();
                        break;
                    }
                    case R.id.nav_developer: {
                        setDeveloperFragment();
                        drawer.close();
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void setHomeFragment() {
        HomeFragment fragment = new HomeFragment();
        setFragment(fragment);
    }

    private void setSettingsFragment() {
        setFragment(new SettingsFragment());
    }

    private void setFeedbackFragment() {
        setFragment(new FeedbackFragment());
    }

    private void setDeveloperFragment() {
        setFragment(new DeveloperFragment());
    }

    private void setSlideshowFragment() {
        setFragment(new ChangeCityFragment());
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
