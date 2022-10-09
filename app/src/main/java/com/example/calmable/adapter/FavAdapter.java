package com.example.calmable.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calmable.MusicPlayer;
import com.example.calmable.R;
import com.example.calmable.db.FavDB;
import com.example.calmable.model.FavModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private Context context;
    private List<FavModel> listOfSongs;
    private FavDB favDB;

    public FavAdapter(Context context, List<FavModel> listOfSongs) {
        this.context = context;
        this.listOfSongs = listOfSongs;
    }

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_name, parent, false);
        favDB = new FavDB(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.favTextView.setText(listOfSongs.get(position).getId());
        Picasso.get().load(listOfSongs.get(position).getUrl()).into(holder.favImageView);


        holder.favTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOfSongs.get(position);

                Intent intent = new Intent(context, MusicPlayer.class);

                String songName = listOfSongs.get(position).getId();
                String url = listOfSongs.get(position).getUrl();
                String image = listOfSongs.get(position).getImageView();
                intent.putExtra("songName", songName);
                intent.putExtra("url", url);
                intent.putExtra("image", image);

                Log.d("TAG", "song name : " + songName);
                Log.d("TAG", "url : " + url);

                Log.d(" adapter List-->", String.valueOf(listOfSongs));

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

        TextView favTextView;
        Button favBtn;
        ImageView favImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            favTextView = itemView.findViewById(R.id.songTitle);
            favBtn = itemView.findViewById(R.id.favHeartIcon);
            favImageView = itemView.findViewById(R.id.favImageView);

            favBtn.setBackgroundResource(R.drawable.ic_fill_fav_icon);

            //remove from fav after click
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final FavModel favItem = listOfSongs.get(position);
                    Log.d("TAG", "remove method id -->: " + favItem.getSongName());
                    favDB.remove_fav_music(favItem.getSongName());
                    removeItem(position);
                    Toast.makeText(context, "Removed From Favourite", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void removeItem(int position) {
        listOfSongs.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listOfSongs.size());
    }
}