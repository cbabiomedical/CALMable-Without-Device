package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Relax extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax);

        NavigationBar();
    }


    private void NavigationBar() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.relax);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        return true;
                    case R.id.relax:
                        return true;
                    case R.id.journal:
                        startActivity(new Intent(getApplicationContext(), Journal.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        return true;
                    case R.id.challenge:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), ProfileMain.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        return true;
                }

                return false;
            }
        });

    }

    public void BtnGoDeepRelaxMusic(View view) {
        startActivity(new Intent(getApplicationContext(), DeepRelaxMusicActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void BtnGoNatureSounds(View view) {
        startActivity(new Intent(getApplicationContext(), NatureSoundsActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void BtnGoMeditateMusic(View view) {
        startActivity(new Intent(getApplicationContext(), MeditateMusicActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void BtnGoPainReliefMusic(View view) {
        startActivity(new Intent(getApplicationContext(), PainReliefMusicActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void BtnGoChillOutMusic(View view) {
        startActivity(new Intent(getApplicationContext(), ChillOutMusicActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void BtnGoCalmingVideos(View view) {
        startActivity(new Intent(getApplicationContext(), VideoPlayerActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onBackPressed() {
        finish();
        Intent intent = new Intent(Relax.this, Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}