package com.example.finalproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNavigationView;
    public static SearchData searchData;
    public static GPS gps;

    //Location Service permissions
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        searchData = new SearchData();
        gps = new GPS(MainActivity.this);
        //Checks for permission
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        selectedFragment = new SearchFragment();
                        break;
                    case R.id.action_results:
                        selectedFragment = new ResultsFragment();
                        break;
                    case R.id.action_map:
                        selectedFragment = new MapDisplayFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return false;
            }
        });
    }

    public static void onSearchClick(String term, String location) {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String URL = "https://api.yelp.com/v3/businesses/search?location=" + location + "&radius=16094&term=" + term;
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .method("GET", null)
                    .addHeader("Authorization", "Bearer IEp52v9JbOBF7tAiZI2d-gC1lIEMh1rWU14zmJ9Odtm9d57edOe6OB05Ij2ooHZE1dQGQ1r6_5cFsSiuif98vBLXxmvUNbnzH5PawcXvazp3ol4NLh0J4mqPKmrfX3Yx")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                JSONObject json = new JSONObject(response.body().string());
                searchData.setBusinessArray(json.getJSONArray("businesses"));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void onLocClick(String term) {
        if(gps.canGetLocation()){
            double lat = gps.getLatitude();
            double lon = gps.getLongitude();
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String URL = "https://api.yelp.com/v3/businesses/search?latitude=" + lat + "&longitude=" + lon + "&radius=16094&term=" + term;
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url(URL)
                        .method("GET", null)
                        .addHeader("Authorization", "Bearer IEp52v9JbOBF7tAiZI2d-gC1lIEMh1rWU14zmJ9Odtm9d57edOe6OB05Ij2ooHZE1dQGQ1r6_5cFsSiuif98vBLXxmvUNbnzH5PawcXvazp3ol4NLh0J4mqPKmrfX3Yx")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONObject json = new JSONObject(response.body().string());
                    searchData.setBusinessArray(json.getJSONArray("businesses"));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public static SearchData getSearchData(){
        return searchData;
    }
}