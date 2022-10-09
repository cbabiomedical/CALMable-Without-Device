package com.example.calmable.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calmable.db.FavDB;
import com.example.calmable.db.FavDeepRelaxDB;
import com.example.calmable.model.FavModel;
import com.example.calmable.model.MusicModel;
import com.example.calmable.MusicPlayer;
import com.example.calmable.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChillOutMusicAdapter extends RecyclerView.Adapter<ChillOutMusicAdapter.ViewHolder> {

    private ArrayList<FavModel> listOfSongs;
    private Context context;
    private FavDB favDB;
    public static ViewHolder viewHolder;

    public ChillOutMusicAdapter(ArrayList<FavModel> listOfSongs, Context context) {
        this.listOfSongs = listOfSongs;
        this.context = context;
    }

    @NonNull
    @Override
    public ChillOutMusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        favDB = new FavDB(context);
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_name, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ChillOutMusicAdapter.ViewHolder holder, int position) {

        final FavModel coffeeItem = listOfSongs.get(position);

        readCursorDataDP(coffeeItem, holder);

        Picasso.get().load(listOfSongs.get(position).getUrl()).into(holder.imageView);

        holder.songTitle.setText(listOfSongs.get(position).getSongName());
        holder.songTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOfSongs.get(position);

                Intent intent = new Intent(context, MusicPlayer.class);

                String songName = listOfSongs.get(position).getSongName();
                String url = listOfSongs.get(position).getUrl();
                intent.putExtra("songName", songName);
                intent.putExtra("url", url);

                Log.d("TAG", "song name : " + songName);
                Log.d("TAG", "url : " + url);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listOfSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView songTitle;
        RecyclerView recyclerView;
        ImageView imageView;
        AppCompatButton favBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.songTitle);
            recyclerView = itemView.findViewById(R.id.listOfSongRecycleView);
            imageView = itemView.findViewById(R.id.favImageView);
            favBtn = itemView.findViewById(R.id.favHeartIcon);


            //add to fav btn
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    FavModel favModel = listOfSongs.get(position);
                    if (favModel.getIsFav().equals("0")) {
                        favModel.setIsFav("1");
                        favDB.insertIntoTheDatabase(favModel.getSongName(), favModel.getImageView(),
                                favModel.getId(), favModel.getIsFav() ,favModel.getUrl());
                        favBtn.setBackgroundResource(R.drawable.ic_fill_fav_icon);
                        Toast.makeText(context, "Added To Favourite!", Toast.LENGTH_SHORT).show();
                    } else {
                        favModel.setIsFav("0");
                        favDB.remove_fav_music(favModel.getId());
                        favBtn.setBackgroundResource(R.drawable.ic_favorite);
                        Toast.makeText(context, "Removed From Favourite!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorDataDP(FavModel coffeeItem, ViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(coffeeItem.getId());
        SQLiteDatabase db = favDB.getReadableDatabase();

        try {
            while (cursor.moveToNext()) {
                String item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                coffeeItem.setIsFav(item_fav_status);

                //check fav status
                if (item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_fill_fav_icon);
                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

    }

}
