package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.calmable.adapter.ChillOutMusicAdapter;
import com.example.calmable.adapter.CreativityAdapter;
import com.example.calmable.model.FavModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreativityActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CreativityAdapter creativityAdapter;
    ArrayList<FavModel> listOfSongs;

    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creativity);

        recyclerView = findViewById(R.id.listOfSongRecycleView);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        initData();

    }

    private void initData() {

        listOfSongs = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Songs_Admin").child("Creativity");
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
                creativityAdapter = new CreativityAdapter(listOfSongs, getApplicationContext());
                recyclerView.setAdapter(creativityAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}