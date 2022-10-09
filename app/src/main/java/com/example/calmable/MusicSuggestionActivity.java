package com.example.calmable;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.calmable.adapter.MusicSuggestionAdapter;
import com.example.calmable.model.MusicModel;
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
import java.util.Random;

public class MusicSuggestionActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MusicSuggestionAdapter musicSuggestionAdapter;
    ArrayList<MusicModel> listOfSongs;

    List<String> listOfKeys;

    boolean stopThread = false;

    FirebaseUser mUser;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_suggestion);


        recyclerView = findViewById(R.id.listOfSongRecycleView);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        initData();

        getDataId();

        calmingIndex();


    }

    private void initData() {

        listOfSongs = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Songs_Admin").child("Suggestion Music ");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postDataSnapshot : snapshot.getChildren()) {
                    MusicModel post = postDataSnapshot.getValue(MusicModel.class);
                    Log.d("Post", String.valueOf(post));
                    listOfSongs.add(post);
                }
                Log.d("List-->", String.valueOf(listOfSongs));


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child("sugMusic");

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot datas : dataSnapshot.getChildren()) {
                            String id = datas.child("id").getValue().toString();
                            String msgType = datas.child("msgType").getValue().toString();

                            Log.d("TAG", "onDataChange: " + id);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                musicSuggestionAdapter = new MusicSuggestionAdapter(listOfSongs, getApplicationContext());
                recyclerView.setAdapter(musicSuggestionAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //get songs id's
    private void getDataId() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Songs_Admin").child("Suggestion Music ");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<String> listOfID = new ArrayList<>();
                listOfKeys = new ArrayList<>();

                for (DataSnapshot postDataSnapshot : snapshot.getChildren()) {

                    String post = postDataSnapshot.child("id").getValue(String.class);
                    Log.d("id --> ", String.valueOf(post));
                    listOfID.add(String.valueOf(post));


                    key = postDataSnapshot.getKey();
                    //Log.d("TAG", "onCreate: key :" + key);
                    listOfKeys.add(key);
                    //Log.d("TAG", "keys -------aaa------: " + listOfKeys);

//                    stopThread = false;
//                    ExampleRunnable runnable = new ExampleRunnable();
//                    new Thread(runnable).start();

                }
                Log.d("list of songs id's --> ", String.valueOf(listOfID));
                Log.d("TAG", "keys: " + listOfKeys);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    public void calmingIndex() {

        final int min = 20;
        final int max = 100;
        final int random1 = new Random().nextInt((max - min) + 1) + min;
        final int random2 = new Random().nextInt((max - min) + 1) + min;

        String calmingIndex1 = String.valueOf(random1);
        String calmingIndex2 = String.valueOf(random2);

        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Object> hashMap2 = new HashMap<>();

        hashMap.put("sugIndex", calmingIndex1);
        hashMap2.put("sugIndex", calmingIndex2);


        FirebaseDatabase.getInstance().getReference()
                .child("Songs_Admin").child("Suggestion Music ").child("-MqSoabkE2hj0IGm5uJa").updateChildren(hashMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });


        FirebaseDatabase.getInstance().getReference()
                .child("Songs_Admin").child("Suggestion Music ").child("-MqTbvjR-EYgboaWTOFd").updateChildren(hashMap2)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    public void btnGoSugBreathing(View view) {
        startActivity(new Intent(getApplicationContext(), BreathPatterns.class));
    }

    class ExampleRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i >= 0 ; i++) {

                if (stopThread)
                    return;

                if (i == listOfKeys.size()) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            calmingIndex();
                        }
                    });


                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setOrder() {

        FirebaseDatabase.getInstance().getReference()
                .child("Songs_Admin").child("Suggestion Music ").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //list.clear(); clear the list before updating.
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String post = dataSnapshot.child("sugIndex").getValue(String.class);

                    Log.d(TAG, "onDataChange: " + post);


                }
               // Adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });
    }

    public void stopThread(View view) {
        stopThread = true;
    }
}