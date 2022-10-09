package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.calmable.adapter.DeepRelaxMusicAdapter;
import com.example.calmable.adapter.SleepStoryAudioAdapter;
import com.example.calmable.model.FavModel;
import com.example.calmable.model.MusicModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SleepStoryAudioActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SleepStoryAudioAdapter sleepStoryAudioAdapter;
    ArrayList<FavModel> listOfSongs;

    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_story_audio);

        recyclerView = findViewById(R.id.listOfSongRecycleView);

        mUser = FirebaseAuth.getInstance().getCurrentUser();


        initData();
    }

    private void initData() {

        listOfSongs = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Songs_Admin").child("Sleep Stories");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postDataSnapshot : snapshot.getChildren()) {
                    FavModel post = postDataSnapshot.getValue(FavModel.class);
                    Log.d("Post", String.valueOf(post));
                    listOfSongs.add(post);
                }
                Log.d("List-->", String.valueOf(listOfSongs));

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                sleepStoryAudioAdapter = new SleepStoryAudioAdapter(listOfSongs, getApplicationContext());
                recyclerView.setAdapter(sleepStoryAudioAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void btnGoStoryRead (View view){
        startActivity(new Intent(getApplicationContext(), SleepStoryReadActivity.class));
    }
}