package com.tillster.betterweatherv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.tabs.TabLayout;
import com.tillster.betterweatherv2.Adapters.ViewPagerAdapter;
import com.tillster.betterweatherv2.Common.Common;
import com.tillster.betterweatherv2.Permissions.BetterPermissions;

public class MainActivity extends AppCompatActivity {

    final String TAG = "Location";
    final static int PERM_REQEUSTCODE = 0;
    TabLayout tabLayout;
    Toolbar toolBar;
    ViewPager viewPager;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.better_tabs);
        toolBar = findViewById(R.id.better_toolbar);
        setSupportActionBar(toolBar);
        viewPager = findViewById(R.id.better_pager);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        //Setting up Permissions _________________________________
        String[] permsToReqeust = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};

        if (!BetterPermissions.hasPermissions(this, permsToReqeust)) ;
        {
            ActivityCompat.requestPermissions(this, permsToReqeust, PERM_REQEUSTCODE);
        }

        buildLocationRequest();
        buildLocationCallBack();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback,
                Looper.myLooper());


    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

       current_weather.getInstance();
       adapter.addFragment(current_weather.getInstance(),"Today");
       adapter.addFragment(five_day_forecast.getInstance(),"Daily Weather");
       adapter.addFragment(city_weather.getInstance(),"City Weather");
       viewPager.setAdapter(adapter);
    }

    private void buildLocationRequest()
    {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10.0f);
    }

    private void buildLocationCallBack()
    {
        locationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Common.currentLocation = locationResult.getLastLocation();

                Log.i(TAG, "our location is: " + Common.currentLocation);

                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);
            }
        };
    };




}
