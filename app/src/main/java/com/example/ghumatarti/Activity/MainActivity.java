package com.example.ghumatarti.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.ModelClass.DisplaySongs;
import com.example.ghumatarti.Adapter.RecyclerAdapter;
import com.example.ghumatarti.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MaterialButton upload;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public ArrayList<DisplaySongs> displaySongsArrayList = new ArrayList<>();

     BottomNavigationView bottomNavigationView;

    //For Onclick of song
    private RecyclerAdapter.RecyclerViewClickListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        upload = (MaterialButton) findViewById(R.id.upload);
        recyclerView = (RecyclerView) findViewById(R.id.recview);

        layoutManager = new LinearLayoutManager(this);


    /*    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getApplicationContext(), displaySongsArrayList);
        recyclerView.setAdapter(recyclerAdapter);
*/
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.page_1) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                if (itemId == R.id.page_2) {
                    Intent intent = new Intent(getApplicationContext(), SearchSong.class);
                    startActivity(intent);
                }

                if (itemId == R.id.page_3) {
                    Intent intent = new Intent(getApplicationContext(), Favourites.class);
                    startActivity(intent);
                }

                if (itemId == R.id.page_4) {
                    Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                    startActivity(intent);
                }
                return false;
            }
        });




        final DatabaseReference nm = FirebaseDatabase.getInstance().getReference("Songs");
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter( displaySongsArrayList,listener);
                    recyclerView.setAdapter(recyclerAdapter);

                    recyclerView.setAdapter(recyclerAdapter);
                    recyclerView.setLayoutManager(layoutManager);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Upload starts here
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, UploadSongs.class);
                startActivity(i);
            }
        });
        //Upload ends here

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