package com.github.clebermatheus.yahooweather.activities

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationManager.NETWORK_PROVIDER
import android.net.Uri.encode
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request.Method.GET
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.clebermatheus.yahooweather.R
import com.github.clebermatheus.yahooweather.utils.Util.MAX_REQUESTS
import com.github.clebermatheus.yahooweather.utils.Util.PREFERENCES
import org.json.JSONObject

class SplashScreenActivity : AppCompatActivity() {
    private var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Log.i(TAG, "SplashScreen")
        getLocation()
    }

    private fun requestQueueYahooWeather(query: String) {
        Log.d(TAG, query)
        if (requestQueue == null) requestQueue = Volley.newRequestQueue(this)
        val url = "http://query.yahooapis.com/v1/public/yql?q=${encode(query)}&format=json"

        val jsonRequest = JsonObjectRequest(GET, url, null, Response.Listener<JSONObject> { response ->
            val sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("resultJSON", response.toString()).apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, Response.ErrorListener{ it.stackTrace })
        jsonRequest.retryPolicy = DefaultRetryPolicy(30000, MAX_REQUESTS, 1.0f)
        requestQueue!!.add<JSONObject>(jsonRequest)
    }

    private fun getLocation() {
        val manager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), 0)
                getLocation()
                return
            }
        }
        manager.requestLocationUpdates(NETWORK_PROVIDER, 0, 0f, object : LocationListener {
            override fun onLocationChanged(location: Location) {
                Log.d("Latitude", location.latitude.toString())
                Log.d("Longitude", location.longitude.toString())
                requestQueueYahooWeather("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text = '(${location.latitude}, ${location.longitude})') and u = 'c'")
            }

            override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {
                Log.i(TAG, s)
            }

            override fun onProviderEnabled(s: String) {
                Log.i(TAG, s)
            }

            override fun onProviderDisabled(s: String) {
                Log.i(TAG, s)
            }
        })
        /*Location location = manager.getLastKnownLocation(PASSIVE_PROVIDER);
        Log.d("Latitude", String.valueOf(location.getLatitude()));
        Log.d("Longitude", String.valueOf(location.getLongitude()));
        requestQueueYahooWeather("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text = '("+
                String.valueOf(location.getLatitude()) + ", " + String.valueOf(location.getLongitude()) + ")') and u = 'c'");*/
    }

    companion object {
        private val TAG = "SplashScreen"
    }
}
