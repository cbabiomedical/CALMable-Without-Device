package com.example.calmable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.calmable.adapter.FavAdapter;
import com.example.calmable.db.FavDB;
import com.example.calmable.model.FavModel;

import java.util.ArrayList;

public class MyFavouritesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<FavModel> listOfSongs;
    private FavDB favDB;
    FavAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourites);

        recyclerView = findViewById(R.id.gridView);
        favDB=new FavDB(getApplicationContext());

        Log.d("TAG", "LoadData: " + listOfSongs);

        LoadData();
    }


    private void LoadData() {

        SQLiteDatabase db = favDB.getReadableDatabase();
        Cursor cursor = favDB.select_all_favorite_list();

        //check empty list
        if (cursor.getCount() == 0){
            Toast.makeText(MyFavouritesActivity.this,"NO FAVOURITE ADDED!!",Toast.LENGTH_LONG).show();
            return;
        }

        listOfSongs = new ArrayList<>();
        if (listOfSongs != null) {
            listOfSongs.clear();
        }


        try {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_TITLE));
                String id = cursor.getString(cursor.getColumnIndex(FavDB.KEY_ID));
                String url = cursor.getString(cursor.getColumnIndex(FavDB.ITEM_URL));
                String image = String.valueOf(cursor.getString(cursor.getColumnIndex(FavDB.ITEM_IMAGE)));
                FavModel favItem = new FavModel(title, id, url, image);
                listOfSongs.add(favItem);
                Log.d("Fav List =", String.valueOf(listOfSongs));
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavAdapter(this,listOfSongs);
        recyclerView.setAdapter(adapter);

        Log.d("TAG", "LoadData: " + listOfSongs);

    }

}