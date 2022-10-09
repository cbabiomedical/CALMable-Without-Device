package com.example.calmable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashSet;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class Journal extends AppCompatActivity {
//
//
//    static ArrayList<String> listOfNotes = new ArrayList<>();
//    static ArrayAdapter listViewNoteAdapter;
//
//    ListView listViewJournal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        NavigationBar();

        Button addNoteBtn = findViewById(R.id.addnewnotebtn);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Journal.this,AddNoteActivity.class));
            }
        });

        Realm.init(getApplicationContext());
        //Realm realm = Realm.getDefaultInstance();
        Realm realm = Realm.getInstance(RealmUtility.getDefaultConfig());

        RealmResults<Note> notesList = realm.where(Note.class).sort("createdTime", Sort.DESCENDING).findAll();


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(getApplicationContext(),notesList);
        recyclerView.setAdapter(myAdapter);

        notesList.addChangeListener(new RealmChangeListener<RealmResults<Note>>() {
            @Override
            public void onChange(RealmResults<Note> notes) {
                myAdapter.notifyDataSetChanged();
            }
        });


    }


//        Toast.makeText(getApplicationContext(), "Write your day here!", Toast.LENGTH_SHORT).show();
//
//        listViewJournal = (ListView) findViewById(R.id.listViewJournal);
//
//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.calmable", Context.MODE_PRIVATE);
//        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("lostOfNotes", null);
//
//        if (set == null) {
//            //listOfNotes.add("Write your day here!");
//            //Toast.makeText(Journal.this, "Write your day here!", Toast.LENGTH_SHORT).show();
//        } else {
//            listOfNotes = new ArrayList(set);
//        }
//
//
//        listViewNoteAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 , listOfNotes);
//        listViewJournal.setAdapter(listViewNoteAdapter);
//
//
//        // click list view
//        listViewJournal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
//                intent.putExtra("noteId", i);
//
//                Log.i("TAG", "btn create success");
//
//                startActivity(intent);
//            }
//        });
//
//
//        // long press delete notes
//        listViewJournal.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                final int itemDelete = i;
//
//                new AlertDialog.Builder(Journal.this)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Are you sure?")
//                        .setMessage("Do you want to delete this note?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                listOfNotes.remove(itemDelete);
//                                listViewNoteAdapter.notifyDataSetChanged();
//
//                                Toast.makeText(getApplicationContext(), "Delete Successfully!", Toast.LENGTH_SHORT).show();
//
//                                // permanent save notes
//                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.calmable", Context.MODE_PRIVATE);
//                                HashSet<String> set = new HashSet(Journal.listOfNotes);
//                                sharedPreferences.edit().putStringSet("lostOfNotes", set).apply();
//                            }
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
//
//                return true;
//
//            }
//
//        });
//
//
//
//
//
//
//
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.add_note_menu, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    // handle the menu item clicks
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//
//        if (item.getItemId() == R.id.add_note) {
//            Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
//
//            Toast.makeText(getApplicationContext(), "Add New Note", Toast.LENGTH_SHORT).show();
//
//            startActivity(intent);
//
//            return true;
//        }
//        return false;
//    }
//
//
//
//
//
//
    private void NavigationBar() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.journal);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        return true;
                    case R.id.relax:
                        startActivity(new Intent(getApplicationContext(), Relax.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        return true;
                    case R.id.journal:
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
//
                return false;
           }
        });

    }

    public void onBackPressed() {
        finish();
        Intent intent = new Intent(getApplicationContext(), Home.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}