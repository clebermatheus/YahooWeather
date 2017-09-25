package com.github.clebermatheus.yahooweather.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;
import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.location.LocationManager.NETWORK_PROVIDER;
import static android.net.Uri.encode;
import static com.android.volley.Request.Method.GET;
import static com.github.clebermatheus.yahooweather.utils.Util.MAX_REQUESTS;
import static com.github.clebermatheus.yahooweather.utils.Util.PREFERENCES;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Arquivo criado para testar o Splash screen
 *
 * Created by clebermatheus on 9/24/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class InitTest{
    private Context appContext = InstrumentationRegistry.getTargetContext();
    private static final String TAG = "SplashScreen";
    private RequestQueue requestQueue;
    @Rule
    public GrantPermissionRule permissions = GrantPermissionRule.grant(INTERNET, ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION);
    @Rule
    public ActivityTestRule<SplashScreenActivity> activity = new ActivityTestRule<>(SplashScreenActivity.class);

    @Test
    public void obtemLocalizacao() throws Exception {
        assertTrue("Sem acesso as permissões de GPS", ActivityCompat.checkSelfPermission(appContext, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(appContext, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED);
        LocationManager manager = (LocationManager) appContext.getSystemService(LOCATION_SERVICE);
        assertTrue("Tem conexão GPS", manager.isProviderEnabled(NETWORK_PROVIDER));
        if (ActivityCompat.checkSelfPermission(appContext, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(appContext, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity.getActivity(), new String[]{ACCESS_FINE_LOCATION,
                    ACCESS_COARSE_LOCATION}, 0);
            obtemLocalizacao();
            return;
        }
        manager.requestLocationUpdates(NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location loc) {
                assertNotNull("Localização não encontrada!", loc);
                recebeDadosDoYahooWeather(loc);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {Log.i(TAG, s);}

            @Override
            public void onProviderEnabled(String s) {Log.i(TAG, s);}

            @Override
            public void onProviderDisabled(String s) {Log.i(TAG, s);}
        }, Looper.getMainLooper());
    }

    private void recebeDadosDoYahooWeather(Location location){
        String query = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text = '("+
                String.valueOf(location.getLatitude()) + ", " + String.valueOf(location.getLongitude()) + ")') and u = 'c'";
        assertNotNull("Query YQL nula", query);
        assertNotNull("Query encoder YQL nula", encode(query));
        requestQueue = Volley.newRequestQueue(appContext);
        assertNotNull("RequestQueue", requestQueue);
        String url = "http://query.yahooapis.com/v1/public/yql?q=" + encode(query) + "&format=json";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(GET, url, null, response -> {
            assertNotNull("Result vazio", response);
            SharedPreferences sharedPreferences = appContext.getSharedPreferences(PREFERENCES,
                    MODE_PRIVATE);
            assertNotNull("SharedPreferences não encontrado", sharedPreferences);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            assertNotNull("Editor não encontrado", editor);
            editor.putString("resultJSON", response.toString()).apply();
            appContext.startActivity(new Intent(appContext, MainActivity.class));
            activity.finishActivity();
        }, Throwable::getStackTrace);
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(30000, MAX_REQUESTS, 1.0f));
        requestQueue.add(jsonRequest);
    }
}