package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MostStressedPeople extends AppCompatActivity {

    Button save,finish;
    ArrayList<String> addArray = new ArrayList<String>();
    EditText text;
    ListView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_stressed_people);

        text = (EditText) findViewById(R.id.textInput);
        show = (ListView) findViewById(R.id.listView);
        save = (Button) findViewById(R.id.btnSave);
        finish = (Button) findViewById(R.id.btnContinue);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.calmable", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("addArray", null);

        if (set == null) {

        } else {
            addArray = new ArrayList(set);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MostStressedPeople.this, android.R.layout.simple_list_item_1,addArray);
            show.setAdapter(adapter);
        }

        //save the entered list
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getInput = text.getText().toString();

                if(addArray.contains(getInput)){
                    Toast.makeText(getBaseContext(), "Name already added", Toast.LENGTH_SHORT).show();
                }
                else if (getInput == null || getInput.trim().equals("")){
                    Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    addArray.add(getInput);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MostStressedPeople.this, android.R.layout.simple_list_item_1,addArray);
                    show.setAdapter(adapter);
                    ((EditText)findViewById(R.id.textInput)).setText("");
                }
            }
        });


        // save list to shared preferences
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.calmable", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Set<String> set = new HashSet<String>();
                set.addAll(addArray);
                editor.putStringSet("addArray", set);
                editor.apply();
                Log.d("addarray-------",""+set);

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        // long press delete names
        show.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int itemDelete = i;

                new AlertDialog.Builder(MostStressedPeople.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Good to hear!")
                        .setMessage("Do you want to delete this person?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MostStressedPeople.this, android.R.layout.simple_list_item_1,addArray);
                                addArray.remove(itemDelete);
                                adapter.notifyDataSetChanged();

                                Toast.makeText(getApplicationContext(), "Successfully Deleted!", Toast.LENGTH_SHORT).show();

                                // permanent save names
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.calmable", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet(addArray);
                                sharedPreferences.edit().putStringSet("addArray", set).apply();
                                show.setAdapter(adapter);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;

            }

        });
    }
}