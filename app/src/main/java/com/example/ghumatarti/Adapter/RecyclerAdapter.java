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

import com.example.ModelClass.DisplaySongs;
import com.example.ghumatarti.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    Context context;
    ArrayList<DisplaySongs> songsArrayList;

    private RecyclerViewClickListener listener;

    public RecyclerAdapter(ArrayList<DisplaySongs> displaySongs, RecyclerViewClickListener listener) {
        this.songsArrayList = displaySongs;
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


        holder.songName.setText(songsArrayList.get(position).getSongName());
        Log.d("songName",""+songsArrayList.get(position).getSongName());

        holder.artistName.setText(songsArrayList.get(position).getSongArtist());
        holder.songDuration.setText(songsArrayList.get(position).getSongDuration());


    //    holder.songDuration.setText(String.valueOf(songsArrayList.get(position).getSongDuration()));

        Picasso.get().load(songsArrayList.get(position).getImageUrl()).into(holder.songThumbnail);

   //     String audiolink=songsArrayList.get(position).getSongUrl();
        Log.d("heyyy","Song Link");



    }
    public interface RecyclerViewClickListener {
        void onClick(View v, int position);

    }

    @Override
    public int getItemCount() {
        return songsArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView songThumbnail;

        TextView songDuration,artistName,songName;


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