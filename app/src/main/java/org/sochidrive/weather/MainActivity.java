package org.sochidrive.weather;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import org.sochidrive.weather.fragment.HistoryCityFragment;
import org.sochidrive.weather.fragment.SettingsFragment;
import org.sochidrive.weather.fragment.HomeFragment;
import org.sochidrive.weather.fragment.ChangeCityFragment;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends BaseActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        getSavedData();
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setHomeFragment();
        setOnClickForSideMenuItems();

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
    }

    private void getSavedData() {
        SingletonSave.getInstance();
        if(SingletonSave.getWeatherData() == null) {
            SingletonSave.setMainActivity(this);
            SingletonSave.setCity(this.getCityPref());
            SingletonSave.setDegree(this.getDegreePref());
            SingletonSave.setIcon(this.getWeatherIconPref());
            SingletonSave.setCheckBoxWindSpeed(this.getWindSpeed());
            SingletonSave.setCheckBoxPressure(this.getPressure());
        }
    }

    public void setSavedData() {
        this.setCityPref(SingletonSave.getCity());
        this.setDegreePref(SingletonSave.getDegree());
        this.setWeatherIconPref(SingletonSave.getIcon());
        this.setDarkTheme(this.isDarkTheme());
        this.setWindSpeed(SingletonSave.getCheckBoxWindSpeed());
        this.setPressure(SingletonSave.getCheckBoxPressure());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        setSavedData();
    }

    @Override
    protected void onDestroy() {
        setSavedData();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
            navigationView.setCheckedItem(R.id.nav_home);
        }
        super.onBackPressed();
    }

    private void setOnClickForSideMenuItems() {
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home: {
                    setHomeFragment();
                    drawer.close();
                    break;
                }
                case R.id.nav_settings: {
                    setSettingsFragment();
                    drawer.close();
                    break;
                }
                case R.id.nav_change_city: {
                    setChangeCityFragment();
                    drawer.close();
                    break;
                }
                case R.id.nav_history_city: {
                    setHistoryCityFragment();
                    drawer.close();
                    break;
                }
            }
            return true;
        });
    }

    private void setHomeFragment() {
        HomeFragment fragment = new HomeFragment();
        setFragment(fragment);
    }

    private void setHistoryCityFragment() { setFragment(new HistoryCityFragment());}

    private void setSettingsFragment() {
        setFragment(new SettingsFragment());
    }

    private void setChangeCityFragment() {
        setFragment(new ChangeCityFragment());
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
