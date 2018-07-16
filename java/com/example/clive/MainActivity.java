package com.example.clive;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;

import static android.graphics.Color.BLACK;

public class MainActivity extends AppCompatActivity {

    public static int ACCESS_CODE = 1;
    public static TextView temperature;
    public static TextView geography;
    private static TextView time;
    public static ImageView icon;
    public static boolean night;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);

        temperature = findViewById(R.id.temperature);
        geography = findViewById(R.id.geography);
        icon = findViewById(R.id.icon);
        time = findViewById(R.id.time);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/lato.ttf");
        temperature.setTypeface(typeface);

        if(!isNight()){
            temperature.setTextColor(BLACK);
            geography.setTextColor(BLACK);
            time.setTextColor(BLACK);
            icon.setImageResource(R.drawable.fewclouds);
        }else {
           icon.setImageResource(R.drawable.moon);
        }

       android.text.format.Time mtime = new android.text.format.Time();
       mtime.setToNow();
       time.setText(mtime.format("%k:%M"));

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                startProcess(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},ACCESS_CODE);
        } else {
                try{
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    startProcess(location);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }catch (Exception e){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACCESS_CODE) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 100, locationListener);
            }
        }
    }
    private boolean isNight(){
        int state = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (state==Configuration.UI_MODE_NIGHT_YES){
            night = true;
            return true;
        }else {
            night = false;
            return false;
        }
    }
    private void startProcess(Location location){
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        LocationData locationData =
                new LocationData(decimalFormat.format(latitude),decimalFormat.format(longitude));
        new LocationProcess(MainActivity.this).execute(locationData);
    }
}

/*TODO: Following are the fixes required:
* Time doesn't update dynamically --- make it live
* Hardcoded clear sky image --- save it the first time
* Add more images
* Seek based background change
* Handle permission denies
* Initiate the use of NETWORK_PROVIDER if GPS_PROVIDER is unavailable
*/