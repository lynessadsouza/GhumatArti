package com.example.ghumatarti.Adapter;


import static com.example.ghumatarti.Activity.LoginActivity.loggedemail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ghumatarti.ModelClass.DisplaySongs;
import com.example.ghumatarti.ModelClass.FavouritesSongModel;
import com.example.ghumatarti.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    Context context;
    ArrayList<DisplaySongs> songsArrayList;
    private RecyclerViewClickListener listener;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String emailid = loggedemail.replace(".", ",");




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

        //Add to playlist-------
     //   databaseReference = firebaseDatabase.getInstance().getReference("Playlist").child(emailid);
/*
        String songId = extras.getString("song_id");
        String songName = extras.getString("song_name");
        String songUrl = extras.getString("song_url");
        String imageUrl = extras.getString("song_imageurl");
        String songArtist = extras.getString("song_artist");
        String songDuration = extras.getString("song_duration");*/




      //  favourite.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favourite_filled));



//----------------------
        holder.songName.setText(songsArrayList.get(position).getSongName());
        Log.d("songName", "" + songsArrayList.get(position).getSongName());

        holder.artistName.setText(songsArrayList.get(position).getSongArtist());
        holder.songDuration.setText(songsArrayList.get(position).getSongDuration());


        Picasso.get().load(songsArrayList.get(position).getImageUrl()).into(holder.songThumbnail);


        Log.d("heyyy", "Song Link");


        //Add to playlist
        holder.addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                DatabaseReference reference = rootNode.getReference("Playlist");
                String id = reference.push().getKey();
                String id1 = loggedemail.replace(".", ",");


                FavouritesSongModel addFavourite = new FavouritesSongModel(loggedemail,
                        songsArrayList.get(position).getSongId(),
                        songsArrayList.get(position).getSongName(),
                        songsArrayList.get(position).getSongUrl(),
                        songsArrayList.get(position).getImageUrl(),
                        songsArrayList.get(position).getSongArtist(),
                        songsArrayList.get(position).getSongDuration());

                reference.child(id1).child(id).setValue(addFavourite);

            }
        });
        //ends here


    }

    @Override
    public int getItemCount() {
        return songsArrayList.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);

    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView songThumbnail, addPlaylist;

        TextView songDuration, artistName, songName;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.songName);
            artistName = itemView.findViewById(R.id.artistName);
            songDuration = itemView.findViewById(R.id.songDuration);
            songThumbnail = itemView.findViewById(R.id.songThumbnail);
            addPlaylist = itemView.findViewById(R.id.addPlaylist);





            itemView.setOnClickListener(this);











            //---------




            //----







        }

        @Override
        public void onClick(View view) {
            Log.d("You clicked", "" + getAdapterPosition());
            listener.onClick(view, getAdapterPosition());
        }
    }
}