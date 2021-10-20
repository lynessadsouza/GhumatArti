package com.example.ghumatarti.Activity;

import static com.example.ghumatarti.Activity.LoginActivity.loggedemail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ghumatarti.Adapter.FavouritesRecyclerAdapter;
import com.example.ghumatarti.ModelClass.FavouritesSongModel;
import com.example.ghumatarti.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Favourites extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    int favouriteCount = 0;
    public ArrayList<FavouritesSongModel> favouritesSongModelArrayList = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    private FavouritesRecyclerAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        recyclerView = (RecyclerView) findViewById(R.id.favouriteRecview);
        layoutManager = new LinearLayoutManager(this);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("Favourites");


        getFavourites();

    }

    //Starts here
    private void getFavourites() {
        Query query = databaseReference.orderByChild("loggedemail").equalTo(loggedemail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    Log.d("user", "" + snapshot);

                    for (DataSnapshot user : snapshot.getChildren()) {
                        // do something with the individual "issues"

                        Log.d("user", "" + user);


                        FavouritesSongModel favouritesSongModel = user.getValue(FavouritesSongModel.class);


                        if (favouritesSongModel.getLoggedemail().equals(loggedemail)) {

                            Log.d("Favourite Song", "" + favouritesSongModel.getSongName());

                            favouritesSongModelArrayList.add(favouritesSongModel);

                            favouriteCount++;
                            Log.d("Favourite Count", "" + favouriteCount);


                        } else {
                            Toast.makeText(getApplicationContext(), "No favourites added", Toast.LENGTH_LONG).show();
                        }

                    }

                    setOnClickListner(favouritesSongModelArrayList);
                    FavouritesRecyclerAdapter favouritesRecyclerAdapter = new FavouritesRecyclerAdapter(favouritesSongModelArrayList, listener);
                    recyclerView.setAdapter(favouritesRecyclerAdapter);

                    recyclerView.setAdapter(favouritesRecyclerAdapter);
                    recyclerView.setLayoutManager(layoutManager);

                } else {
                    Toast.makeText(getApplicationContext(), "No favourites added", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void setOnClickListner(ArrayList<FavouritesSongModel> displaySongs) {
        listener = new FavouritesRecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent i = new Intent(getApplicationContext(), PlaySong.class);


                i.putExtra("song_id", displaySongs.get(position).getSongId());
                i.putExtra("song_name", displaySongs.get(position).getSongName());
                i.putExtra("song_artist", displaySongs.get(position).getSongArtist());
                i.putExtra("song_duration", displaySongs.get(position).getSongDuration());
                i.putExtra("song_url", displaySongs.get(position).getSongUrl());
                i.putExtra("song_imageurl", displaySongs.get(position).getImageUrl());

                startActivity(i);
            }
        };
    }
    //Ends Here
}