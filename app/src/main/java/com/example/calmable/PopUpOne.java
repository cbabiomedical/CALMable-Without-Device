package com.example.calmable;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;

import com.example.calmable.adapter.SuggestionSimpleCursorAdapter;
import com.example.calmable.db.SuggestionDatabase;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.widget.SearchView;

public class PopUpOne extends AppCompatDialogFragment implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {

    private PopUpOneListener listener;
    private EditText editPerson;
    private EditText editPlace,editReason;
    public static String latLong;
    public static double latitude,longitude;
    FirebaseUser mUser;
    File fileName1;
    //AutoCompleteTextView autoCompleteTextView;
    FusedLocationProviderClient fusedLocationProviderClient;

    private SuggestionDatabase database;
    private SearchView searchView;

    ArrayAdapter<String> adapter;
    static ArrayList<String> personList = new ArrayList<>();
    public static ArrayList<LatLng> addArray = new ArrayList<LatLng>();


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_popupone,null);

        database = new SuggestionDatabase(getActivity());
        searchView = view.findViewById(R.id.ac_text_view);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);

        builder.setView(view)
                .setTitle("")
                .setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editPerson.setText(searchView.getQuery());
                        String person = editPerson.getText().toString();
                        String place = editPlace.getText().toString();
                        String reason = editReason.getText().toString();
                        listener.applyText(person,place,reason);
                        startActivity(new Intent(getActivity(), MusicSuggestionActivity.class));
                        Log.d("TAG person-----", person);
                        Log.d("TAG location-----", place);
                        Log.d("TAG reason-----", reason);

                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.calmable", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(addArray);
                        editor.putString("addArray", json);
                        editor.commit();
                    }
                });

        editPerson = view.findViewById(R.id.edit_person);
        editPlace  = view.findViewById(R.id.edit_place);
        editReason = view.findViewById(R.id.edit_reason);
        //autoCompleteTextView = view.findViewById(R.id.ac_text_view);

        //initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity().getApplicationContext());

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission granted
            getLocation();
        } else {
            //when permission denied
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.example.calmable", Context.MODE_PRIVATE);
//        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("addArray", null);
//
//        if (set == null) {
//
//        } else {
//            personList = new ArrayList(set);
//        }


        //initialize adapter
        //adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,personList);

        //Get suggestion after the number of word types
        // autoCompleteTextView.setThreshold(1);

        //Set adapter
        //autoCompleteTextView.setAdapter(adapter);

//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                //set selected text on text view
//                editPerson.setText(adapter.getItem(i));
//                //personList.add(editPerson);
//                Log.d("---------", String.valueOf(editPerson));
//            }
//        });

        return builder.create();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location!=null){
                    try {
                        //initialize geocoder
                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                        //initialize address list
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        //set address
                        editPlace.setText(addresses.get(0).getAddressLine(0));

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        LatLng place = new LatLng(latitude,longitude);
                        Log.d("Sydney-----------", String.valueOf(place));

                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.calmable", 0);
                        Gson gson = new Gson();
                        String json = sharedPreferences.getString("addArray", "");
                        Type type = new TypeToken<Set<LatLng>>() {}.getType();
                        Set<LatLng> arrayList = gson.fromJson(json, type);

                        if (arrayList == null) {
                            saveLocation();
                        } else {
                            addArray = new ArrayList(arrayList);
                        }
                        addArray.add(place);

                        //Uploading stressed location data to firebase realtime database
                        mUser = FirebaseAuth.getInstance().getCurrentUser();
                        mUser.getUid();
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("Stressed locations");
                        myRef.setValue(addArray);


                        latLong = Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude());
                        // latLong = new LatLng(location.getLatitude(), location.getLongitude());
                        Log.d("Loca LatLong-----------", latLong);

                        //Writing stressed location data to text file
                        try {
                            fileName1 = new File(getActivity().getCacheDir() + "/stressedLocation.txt");
                            fileName1.createNewFile();
                            if (!fileName1.exists()) {
                                fileName1.mkdirs();
                            }
                            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName1, true));

                            writer.write(latLong);
                            writer.newLine();
                            writer.flush();

                            Log.d("TAG", "----------stressed Location File");

                            writer.close();

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }


                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void saveLocation(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.calmable", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(addArray);
        editor.putString("addArray", json);
        editor.commit();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (PopUpOneListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"Must implement");
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        long result = database.insertSuggestion(query);
        //editPerson.setText(searchView.getQuery());
        return result != -1;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        Cursor cursor = database.getSuggestions(newText);
        if(cursor.getCount() != 0)
        {
            String[] columns = new String[] {SuggestionDatabase.FIELD_SUGGESTION };
            int[] columnTextId = new int[] { android.R.id.text1};

            SuggestionSimpleCursorAdapter simple = new SuggestionSimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, cursor,
                    columns , columnTextId
                    , 0);

            searchView.setSuggestionsAdapter(simple);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean onSuggestionSelect(int i) {

        return false;
    }

    @Override
    public boolean onSuggestionClick(int position) {

        SQLiteCursor cursor = (SQLiteCursor) searchView.getSuggestionsAdapter().getItem(position);
        int indexColumnSuggestion = cursor.getColumnIndex( SuggestionDatabase.FIELD_SUGGESTION);

        searchView.setQuery(cursor.getString(indexColumnSuggestion), false);
//        editPerson.setText(searchView.getQuery());
        return true;
    }

    public interface PopUpOneListener{
        void applyText(String person,String place,String reason);
    }
}