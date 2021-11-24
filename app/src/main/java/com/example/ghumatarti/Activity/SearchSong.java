package com.example.ghumatarti.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ghumatarti.Adapter.RecyclerAdapter;
import com.example.ghumatarti.ModelClass.DisplaySongs;
import com.example.ghumatarti.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchSong extends AppCompatActivity {

    public ArrayList<DisplaySongs> displaySongsArrayList = new ArrayList<>();
    TextInputEditText search_et;
    RecyclerView rec_search;
    //  RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference databaseReference;
    private RecyclerAdapter.RecyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_song);

        search_et = (TextInputEditText) findViewById(R.id.search_et);
        rec_search = (RecyclerView) findViewById(R.id.rec_search);
        //  search_tv=(MaterialTextView) findViewById(R.id.search_tv);


        layoutManager = new LinearLayoutManager(this);
        ViewSongs();

        search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                ArrayList<DisplaySongs> filteredList = new ArrayList<>();
                filter(editable.toString(), filteredList);
            }
        });


    }

    private void filter(String toString, ArrayList<DisplaySongs> filteredList) {
        Log.d("Text is ", "" + toString);

//        filteredList.clear();
        for (DisplaySongs item : displaySongsArrayList) {
            if (item.getSongName().toLowerCase().contains(toString.toLowerCase())) {
                filteredList.add(item);
            }
        }
        setOnClickListner(filteredList);

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(filteredList, listener);

        rec_search.setAdapter(recyclerAdapter);

        rec_search.setLayoutManager(new LinearLayoutManager(SearchSong.this));


    }

    private void ViewSongs() {


        databaseReference = FirebaseDatabase.getInstance().getReference("Songs");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("data snapshot", "" + dataSnapshot);

                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        DisplaySongs displaySongs = npsnapshot.getValue(DisplaySongs.class);
                        Log.d("npsnapshot", "" + npsnapshot);
                        displaySongsArrayList.add(displaySongs);
                        Log.d("heyy", "ADDED");
                    }
                    setOnClickListner(displaySongsArrayList);
                    //  RecyclerAdapter recyclerAdapter1 = new RecyclerAdapter(displaySongsArrayList, listener);
                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(displaySongsArrayList, listener);
                    rec_search.setAdapter(recyclerAdapter);

                    rec_search.setAdapter(recyclerAdapter);
                    rec_search.setLayoutManager(layoutManager);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void setOnClickListner(ArrayList<DisplaySongs> displaySongs) {
        listener = new RecyclerAdapter.RecyclerViewClickListener() {
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

}