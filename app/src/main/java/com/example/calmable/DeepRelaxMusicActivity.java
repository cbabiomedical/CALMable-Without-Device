package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.calmable.adapter.DeepRelaxMusicAdapter;
import com.example.calmable.model.FavModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeepRelaxMusicActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DeepRelaxMusicAdapter deepRelaxMusicAdapter;
    ArrayList<FavModel> listOfSongs;

    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_relax_music);

        recyclerView = findViewById(R.id.listOfSongRecycleView);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        listOfSongs = new ArrayList<>();

//        listOfSongs.add(new FavModel("0", "Test - Ours Saxmental-version", R.drawable.item_bg1,
//                "https://firebasestorage.googleapis.com/v0/b/calmableproject.appspot.com/o/Songs%2Fmusic-is-ours-saxmental-version.mp3?alt=media&token=379b9b63-d4a5-4968-bf3b-1a2aafa88b22", "0"));
//
//        listOfSongs.add(new FavModel("1", "Test - Lilac Days", R.drawable.item_bg2,
//                "https://firebasestorage.googleapis.com/v0/b/calmableproject.appspot.com/o/Songs%2Flilac-days.mp3?alt=media&token=5e3076ec-628e-4fcd-8591-2fc833c65c26", "0"));
//
        HashMap<String, Object> songs = new HashMap<>();
        songs.put("songList0", listOfSongs);
        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("testMusic");
        //reference.setValue(songs);


        //Log.d("List -> ", String.valueOf(listOfSongs));

//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        deepRelaxMusicAdapter = new DeepRelaxMusicAdapter(listOfSongs, getApplicationContext());
//        recyclerView.setAdapter(deepRelaxMusicAdapter);

        initData();

        getDataId();
    }

    private void initData() {

        listOfSongs = new ArrayList<>();
        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Music").child("songList");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Songs_Admin").child("Deep Relax");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postDataSnapshot : snapshot.getChildren()) {
                    FavModel post = postDataSnapshot.getValue(FavModel.class);
                    Log.d("Post", String.valueOf(post));
                    listOfSongs.add(post);
                }
                Log.d("List -->", String.valueOf(listOfSongs));

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                deepRelaxMusicAdapter = new DeepRelaxMusicAdapter(listOfSongs, getApplicationContext());
                recyclerView.setAdapter(deepRelaxMusicAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //get songs id's
    private void getDataId() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Songs_Admin").child("Deep Relax");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<String> listOfID = new ArrayList<>();

                for (DataSnapshot postDataSnapshot : snapshot.getChildren()) {

                    String post = postDataSnapshot.child("id").getValue(String.class);
                    Log.d("Post --> ", String.valueOf(post));
                    listOfID.add(String.valueOf(post));
                }
                Log.d("list of songs id's -->", String.valueOf(listOfID));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


    public void sugSongs() {
        List<Object> reference = new ArrayList<>();
        HashMap<String, Object> sugSongsMap = new HashMap<>();

        sugSongsMap.put("sugSongs", reference);
        FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).updateChildren(sugSongsMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                        //Intent intent=new Intent(UserPreferences.this,ProfileActivity.class);
                        //startActivity(intent);
                    }
                });

        Log.d("User", mUser.getUid());
        Toast.makeText(getApplicationContext(), "Successful !", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getApplicationContext(), EnterPhoneActivity.class);
//        startActivity(intent);
    }

}