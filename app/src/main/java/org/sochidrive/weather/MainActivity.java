package org.sochidrive.weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.sochidrive.weather.fragment.HistoryCityFragment;
import org.sochidrive.weather.fragment.SettingsFragment;
import org.sochidrive.weather.fragment.HomeFragment;
import org.sochidrive.weather.fragment.ChangeCityFragment;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends BaseActivity {

    private static final int PERMISSION_REQUEST_CODE = 10;
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
        requestPemissions();


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
                case R.id.nav_current_city: {
                    setCurrentCity();
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

    private void setCurrentCity() {
        EventBus.getBus().post(new ChangeCityEvent(SingletonSave.getGeoCity()));
        navigationView.setCheckedItem(R.id.nav_home);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void requestPemissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocation();
        } else {
            requestLocationPermissions();
        }
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        String provider = locationManager.getBestProvider(criteria, true);

        final Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        if (loc != null) {
            setGeoCity(loc.getLatitude(),loc.getLongitude());
        }

        if (provider != null) {

            locationManager.requestLocationUpdates(provider, 10000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(@NotNull Location location) {
                    double lat = Objects.requireNonNull(location).getLatitude();
                    double lng = Objects.requireNonNull(location).getLongitude();

                    setGeoCity(lat,lng);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }
                @Override
                public void onProviderEnabled(@NotNull String provider) {
                }

                @Override
                public void onProviderDisabled(@NotNull String provider) {
                }
            });
        }
    }

    private  void setGeoCity(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getApplication(), Locale.getDefault());
        Log.d("GEOPOS",lat+" "+lng);
        try {
            List<Address> address = geocoder.getFromLocation(lat, lng, 1);
            if (address.size() > 0) {
                String city = address.get(0).getLocality();
                SingletonSave.setGeoCity(city);
                Log.d("GEOPOS",city);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {   // Запрошенный нами
            if (grantResults.length == 2 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                requestLocation();
            }
        }
    }

}
