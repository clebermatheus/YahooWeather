package com.github.clebermatheus.yahooweather.activities

import android.Manifest.permission.*
import android.content.Context.LOCATION_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.location.LocationManager
import android.location.LocationManager.NETWORK_PROVIDER
import android.location.LocationManager.PASSIVE_PROVIDER
import android.net.Uri.encode
import android.os.Build
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import android.support.v4.app.ActivityCompat
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request.Method.GET
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.clebermatheus.yahooweather.utils.Util
import org.json.JSONObject
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Arquivo criado para testar o Splash screen
 *
 * Created by clebermatheus on 9/24/17.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class InitTest {
    private val appContext = InstrumentationRegistry.getTargetContext()
    private var requestQueue: RequestQueue? = null
    @Rule
    var permissions = GrantPermissionRule.grant(INTERNET, ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION)
    @Rule
    var activity = ActivityTestRule(SplashScreenActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun obtemLocalizacao() {
        assertTrue("Sem acesso as permissões de GPS", ActivityCompat.checkSelfPermission(appContext, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(appContext, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED)
        val manager = appContext.getSystemService(LOCATION_SERVICE) as LocationManager
        assertTrue("Tem conexão GPS", manager.isProviderEnabled(NETWORK_PROVIDER))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(appContext, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(appContext, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity.activity, arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION), 0)
                obtemLocalizacao()
                return
            }
        }
        /*
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
        */
        recebeDadosDoYahooWeather(manager.getLastKnownLocation(PASSIVE_PROVIDER))
    }

    private fun recebeDadosDoYahooWeather(location: Location) {
        val query = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text = '(" +
                location.latitude.toString() + ", " + location.longitude.toString() + ")') and u = 'c'"
        assertNotNull("Query YQL nula", query)
        assertNotNull("Query encoder YQL nula", encode(query))
        requestQueue = Volley.newRequestQueue(appContext)
        assertNotNull("RequestQueue", requestQueue)
        val url = "http://query.yahooapis.com/v1/public/yql?q=" + encode(query) + "&format=json"

        val jsonRequest = JsonObjectRequest(GET, url, null, Response.Listener<JSONObject> {
            response ->
            assertNotNull("Result vazio", response)
            val sharedPreferences = appContext.getSharedPreferences(Util.PREFERENCES,
                    MODE_PRIVATE)
            assertNotNull("SharedPreferences não encontrado", sharedPreferences)
            val editor = sharedPreferences.edit()
            assertNotNull("Editor não encontrado", editor)
            editor.putString("resultJSON", response.toString()).apply()
            appContext.startActivity(Intent(appContext, MainActivity::class.java))
            activity.finishActivity()
        }, Response.ErrorListener({ it.stackTrace }))
        jsonRequest.retryPolicy = DefaultRetryPolicy(30000, Util.MAX_REQUESTS, 1.0f)
        requestQueue!!.add<JSONObject>(jsonRequest)
    }

    companion object {
        private val TAG = "SplashScreen"
    }
}