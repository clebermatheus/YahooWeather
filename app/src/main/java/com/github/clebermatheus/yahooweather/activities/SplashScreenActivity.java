package com.github.clebermatheus.yahooweather.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.clebermatheus.yahooweather.R;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static android.location.LocationManager.PASSIVE_PROVIDER;
import static android.net.Uri.encode;
import static com.android.volley.Request.Method.GET;
import static com.github.clebermatheus.yahooweather.utils.Util.MAX_REQUESTS;
import static com.github.clebermatheus.yahooweather.utils.Util.PREFERENCES;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreen";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Log.i(TAG, "SplashScreen");
        getLocation();
    }

    private void requestQueueYahooWeather(String query) {
        if(requestQueue == null) requestQueue = Volley.newRequestQueue(this);
        String url = "http://query.yahooapis.com/v1/public/yql?q=" + encode(query) + "&format=json";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(GET, url, null, response -> {
            SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("resultJSON", response.toString()).apply();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, Throwable::getStackTrace);
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(30000, MAX_REQUESTS, 1.0f));
        requestQueue.add(jsonRequest);
    }

    public void getLocation() {
        LocationManager manager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 0);
                getLocation();
                return;
            }
        }
        /*manager.requestLocationUpdates(NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Latitude", String.valueOf(location.getLatitude()));
                Log.d("Longitude", String.valueOf(location.getLongitude()));
                requestQueueYahooWeather("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text = '("+
                        String.valueOf(location.getLatitude()) + ", " + String.valueOf(location.getLongitude()) + ")') and u = 'c'");
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {Log.i(TAG, s);}

            @Override
            public void onProviderEnabled(String s) {Log.i(TAG, s);}

            @Override
            public void onProviderDisabled(String s) {Log.i(TAG, s);}
        });
        */
        Location location = manager.getLastKnownLocation(PASSIVE_PROVIDER);
        Log.d("Latitude", String.valueOf(location.getLatitude()));
        Log.d("Longitude", String.valueOf(location.getLongitude()));
        requestQueueYahooWeather("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text = '("+
                String.valueOf(location.getLatitude()) + ", " + String.valueOf(location.getLongitude()) + ")') and u = 'c'");
    }
}
