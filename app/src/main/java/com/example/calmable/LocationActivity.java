package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;
import com.example.calmable.PopUpOne;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Set;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Button currentLocation = (Button) findViewById(R.id.button1);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.calmable", 0);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("addArray", "");
        Type type = new TypeToken<Set<LatLng>>() {}.getType();
        Set<LatLng> arrayList = gson.fromJson(json, type);

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(arrayList == null){
                    Toast.makeText(LocationActivity.this, "Stressed Locations Not found", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent in = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(in);
                }
            }
        });

    }

//        public void btnCurrentLocation (View view){
//            startActivity(new Intent(this, MapsActivity.class));
//        }
//
//        public void btnRetrieveLocation (View view){
//            //startActivity(new Intent(getApplicationContext(), RetrieveMapsActivity.class));
//        }
}
