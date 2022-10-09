package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.calmable.adapter.DeepRelaxMusicAdapter;
import com.example.calmable.adapter.HealingMusicAdapter;
import com.example.calmable.model.MusicModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HealingMusicActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HealingMusicAdapter healingMusicAdapter;
    ArrayList<MusicModel> listOfSongs;

    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healing_music);

        recyclerView = findViewById(R.id.listOfSongRecycleView);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        initData();

        //getDataId();
    }

    private void initData() {

        listOfSongs = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Songs_Admin").child("Healing Music");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postDataSnapshot : snapshot.getChildren()) {
                    MusicModel post = postDataSnapshot.getValue(MusicModel.class);
                    Log.d("Post ", String.valueOf(post));
                    listOfSongs.add(post);
                }
                Log.d("List-->", String.valueOf(listOfSongs));

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                healingMusicAdapter = new HealingMusicAdapter(listOfSongs, getApplicationContext());
                recyclerView.setAdapter(healingMusicAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


//    //get songs id's
//    private void getDataId() {
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Songs_Admin").child("Healing Music");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                List<String> listOfID = new ArrayList<>();
//
//                for (DataSnapshot postDataSnapshot : snapshot.getChildren()) {
//
//                    String post = postDataSnapshot.child("id").getValue(String.class);
//                    Log.d("id --> ", String.valueOf(post));
//                    listOfID.add(String.valueOf(post));
//                }
//                Log.d("list of songs id's -->", String.valueOf(listOfID));
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
//    }
}