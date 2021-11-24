package com.example.ghumatarti.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ghumatarti.ModelClass.FavouritesSongModel;
import com.example.ghumatarti.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FavouritesRecyclerAdapter extends RecyclerView.Adapter<FavouritesRecyclerAdapter.RecyclerViewHolder> {
    Context context;
    ArrayList<FavouritesSongModel> favouritesSongModelArrayList;

    private RecyclerViewClickListener listener;

    public FavouritesRecyclerAdapter(ArrayList<FavouritesSongModel> displaySongs, RecyclerViewClickListener listener) {
        this.favouritesSongModelArrayList = displaySongs;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view1 = inflater.inflate(R.layout.song_item, parent, false);
        return new RecyclerViewHolder(view1);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {


        holder.songName.setText(favouritesSongModelArrayList.get(position).getSongName());
        Log.d("songName", "" + favouritesSongModelArrayList.get(position).getSongName());

        holder.artistName.setText(favouritesSongModelArrayList.get(position).getSongArtist());
        holder.songDuration.setText(favouritesSongModelArrayList.get(position).getSongDuration());


        //    holder.songDuration.setText(String.valueOf(songsArrayList.get(position).getSongDuration()));

        Picasso.get().load(favouritesSongModelArrayList.get(position).getImageUrl()).into(holder.songThumbnail);

        //     String audiolink=songsArrayList.get(position).getSongUrl();
        Log.d("heyyy", "Song Link");


    }

    @Override
    public int getItemCount() {
        return favouritesSongModelArrayList.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);

    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView songThumbnail;

        TextView songDuration, artistName, songName;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.songName);
            artistName = itemView.findViewById(R.id.artistName);
            songDuration = itemView.findViewById(R.id.songDuration);
            songThumbnail = itemView.findViewById(R.id.songThumbnail);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            Log.d("You clicked", "" + getAdapterPosition());
            listener.onClick(view, getAdapterPosition());
        }
    }
}