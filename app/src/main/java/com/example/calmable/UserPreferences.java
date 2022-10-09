package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

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

public class UserPreferences extends AppCompatActivity {

    private Button done;
    CheckBox c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14;
    FirebaseDatabase database;
    //DatabaseReference reference;

    Member member;
    int i = 0;

    FirebaseUser mUser;
    FirebaseAuth mAuth;

    List<Object> reference = new ArrayList<>();
    HashMap<String, Object> preference = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);


        //reference=database.getInstance().getReference().child("Users");

        member = new Member();
        done = findViewById(R.id.done);
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        c4 = findViewById(R.id.c4);
        c5 = findViewById(R.id.c5);
        c6 = findViewById(R.id.c6);
        c7 = findViewById(R.id.c7);
        c8 = findViewById(R.id.c8);
        c9 = findViewById(R.id.c9);
        c10 = findViewById(R.id.c10);
        c11 = findViewById(R.id.c11);
        c12 = findViewById(R.id.c12);
        c13 = findViewById(R.id.c13);
        c14 = findViewById(R.id.c14);


        String d1 = "Travel Music";
        String d2 = "Inspiration";
        String d3 = "Relax Music";
        String d4 = "Nature Sounds";
        String d5 = "Meditate";
        String d6 = "Country Music";
        String d7 = "Sleep Stories";
        String d8 = "Chill out music";
        String d9 = "Classical Music";
        String d10 = "Videos";
        String d11 = "Games";
        String d12 = "Breathing Exercise";
        String d13 = "Success Stories";
        String d14 = "Yoga";


        mUser = FirebaseAuth.getInstance().getCurrentUser();

        /*reference.addValueEventListener(new ValueEventListener(){


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    i=(int)snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


        done.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        ////////////////

                                        ///////////////
                                        if (c1.isChecked()) {
                                            reference.add(d1);
                                        } else {
                                            //
                                        }
                                        if (c2.isChecked()) {
                                            reference.add(d2);
                                        } else {
                                            //
                                        }
                                        if (c3.isChecked()) {
                                            reference.add(d3);
                                        } else {
                                            //
                                        }
                                        if (c4.isChecked()) {
                                            reference.add(d4);
                                        } else {
                                            //
                                        }
                                        if (c5.isChecked()) {
                                            reference.add(d5);
                                        } else {
                                            //
                                        }
                                        if (c6.isChecked()) {
                                            reference.add(d6);
                                        } else {
                                            //
                                        }
                                        if (c7.isChecked()) {
                                            reference.add(d7);
                                        } else {
                                            //
                                        }
                                        if (c8.isChecked()) {
                                            reference.add(d8);
                                        } else {
                                            //
                                        }
                                        if (c9.isChecked()) {
                                            reference.add(d9);
                                        } else {
                                            //
                                        }
                                        if (c10.isChecked()) {
                                            reference.add(d10);
                                        } else {
                                            //
                                        }
                                        if (c11.isChecked()) {
                                            reference.add(d11);
                                        } else {
                                            //
                                        }
                                        if (c12.isChecked()) {
                                            reference.add(d12);
                                        } else {
                                            //
                                        }
                                        if (c13.isChecked()) {
                                            reference.add(d13);
                                        } else {
                                            //
                                        }
                                        if (c14.isChecked()) {
                                            reference.add(d14);
                                        } else {
                                            //
                                        }
                                        preference.put("preferences", reference);
                                        FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).updateChildren(preference)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Toast.makeText(UserPreferences.this, "Successful", Toast.LENGTH_SHORT).show();
                                                        //Intent intent=new Intent(UserPreferences.this,ProfileActivity.class);
                                                        //startActivity(intent);
                                                    }
                                                });
                                        Log.d("User", mUser.getUid());
                                        Toast.makeText(UserPreferences.this, "Successful !", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(UserPreferences.this, EnterPhoneActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    }
                                }

        );
    }
}