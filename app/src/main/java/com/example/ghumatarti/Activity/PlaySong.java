package com.example.ghumatarti.Activity;

import static com.example.ghumatarti.Activity.LoginActivity.loggedemail;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.ghumatarti.ModelClass.FavouritesSongModel;
import com.example.ghumatarti.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class PlaySong extends AppCompatActivity {

    public int playflag = 0, pausedLength;
    public String length;
    ImageView imageurl, favourite;
    //, songId;
    TextView s_name, s_duration, s_artist;
    public String id;
    Uri songurl;
    SeekBar seekBar;
    double current_pos, total_duration;
    MaterialButton play, pause;
    MediaPlayer mediaPlayer;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference favReference;


    //public ArrayList<DisplaySongs> displaySongsArrayList = new ArrayList<>();

    public String email = loggedemail;
    public String emailId;

    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);


        imageurl = (ImageView) findViewById(R.id.songThumbnail);
        s_name = (TextView) findViewById(R.id.s_name);
        s_duration = (TextView) findViewById(R.id.s_duration);
        s_artist = (TextView) findViewById(R.id.s_artist);
        favourite = (ImageView) findViewById(R.id.favourite);
        play = (MaterialButton) findViewById(R.id.play);
        pause = (MaterialButton) findViewById(R.id.pause);
        seekBar = (SeekBar) findViewById(R.id.seekbar);


        // if (mediaPlayer.isPlaying()==true)
     /*       if( mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }*/
        mediaPlayer = new MediaPlayer();


        Log.d("Email", "email" + email);
        emailId = email.replace(".", ",");


        Log.d("emailId", "" + emailId);
        String information = "Not set";


        Bundle extras = getIntent().getExtras();

/*
DisplaySongs displaySongs=(DisplaySongs)getIntent().getSerializableExtra("song");
//to get values
displaySongs.getSongId();
*/


        //   getFavourites();


        if (extras != null) {
            id = extras.getString("song_id");

            Log.d("Song id is  ", ""+id);

            songurl = Uri.parse(extras.getString("song_url"));
            s_name.setText(extras.getString("song_name"));
            s_duration.setText(extras.getString("song_duration"));
            s_artist.setText(extras.getString("song_artist"));

            Picasso.get().load(extras.getString("song_imageurl"))
                    .into(imageurl);
        } else {
            Log.d("name is ", "" + information);
        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setAudioProgress();


                current_pos = seekBar.getProgress();
                mediaPlayer.seekTo((int) current_pos);
            }

            private void setAudioProgress() {

                //get the audio duration
                current_pos = mediaPlayer.getCurrentPosition();
                total_duration = mediaPlayer.getDuration();

                //display the audio duration
                //.setText(timerConversion((long) total_duration));
                //    current.setText(timerConversion((long) current_pos));
                seekBar.setMax((int) total_duration);
                final Handler handler = new Handler();

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            current_pos = mediaPlayer.getCurrentPosition();
                            //    current.setText(timerConversion((long) current_pos));
                            seekBar.setProgress((int) current_pos);
                            handler.postDelayed(this, 1000);
                        } catch (IllegalStateException ed) {
                            ed.printStackTrace();
                        }
                    }
                };
                handler.postDelayed(runnable, 1000);


            }

            public String timerConversion(long value) {
                String audioTime;
                int dur = (int) value;
                int hrs = (dur / 3600000);
                int mns = (dur / 60000) % 60000;
                int scs = dur % 60000 / 1000;

                if (hrs > 0) {
                    audioTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
                } else {
                    audioTime = String.format("%02d:%02d", mns, scs);
                }
                return audioTime;
            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //fav func ends here

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Favourites");
                Query query = reference
                        .child(emailId)
                        .orderByChild("songId")
                        .equalTo(id);
                query.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount()>0) {
                            //username found

                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren())
                            {

                                String clubkey = childSnapshot.getKey();
                                childSnapshot.getRef().removeValue();
                                favourite.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favourite_filled));

                                Log.d("cannot add","cnt add"+clubkey);
                            }


                            Log.d("cannot add","cnt add");
                            Toast.makeText(getApplicationContext(),"Favourite already added",Toast.LENGTH_LONG).show();

                        }else{
                            // username not found
                            String songId = extras.getString("song_id");
                            String songName = extras.getString("song_name");
                            String songUrl = extras.getString("song_url");
                            String imageUrl = extras.getString("song_imageurl");
                            String songArtist = extras.getString("song_artist");
                            String songDuration = extras.getString("song_duration");


                            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                            DatabaseReference reference = rootNode.getReference("Favourites");
                            String id = reference.push().getKey();
                            String id1 = loggedemail.replace(".", ",");


                            FavouritesSongModel addFavourite = new FavouritesSongModel(loggedemail, songId, songName, songUrl, imageUrl, songArtist, songDuration);
                            reference.child(id1).child(id).setValue(addFavourite);

                            favourite.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favourite_filled));
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });










             /*   String songId = extras.getString("song_id");
                String songName = extras.getString("song_name");
                String songUrl = extras.getString("song_url");
                String imageUrl = extras.getString("song_imageurl");
                String songArtist = extras.getString("song_artist");
                String songDuration = extras.getString("song_duration");


                FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                DatabaseReference reference = rootNode.getReference("Favourites");
                String id = reference.push().getKey();
                String id1 = loggedemail.replace(".", ",");


                FavouritesSongModel addFavourite = new FavouritesSongModel(loggedemail, songId, songName, songUrl, imageUrl, songArtist, songDuration);
                reference.child(id1).child(id).setValue(addFavourite);

                favourite.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favourite_filled));*/
            }
        });
        //Favourite ended here


//play started
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                play.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);

                Uri myUri = songurl; // initialize Uri here
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                if (playflag == 0) {
                    Log.d("playflag in play first", "" + playflag);

                    try {
                        // below line is use to set our
                        // url to our media player.
                        mediaPlayer.setDataSource(getApplicationContext(), myUri);

                        int length = mediaPlayer.getCurrentPosition();
                        Log.d("legth initially", "" + length);

                        // below line is use to prepare
                        // and start our media player.
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                        // below line is use to display a toast message.
                        Toast.makeText(getApplicationContext(), "Audio started playing..", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        // this line of code is use to handle error while playing our audio file.
                        Toast.makeText(getApplicationContext(), "Error found is " + e, Toast.LENGTH_SHORT).show();
                    }
                } else {

                    play.setVisibility(View.GONE);
                    pause.setVisibility(View.VISIBLE);


                    Log.d("playflag in play else ", "" + playflag);
                    Log.d("pausedLength in first ", "" + pausedLength);
                    try {
                        mediaPlayer.setDataSource(getApplicationContext(), myUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mediaPlayer.seekTo(pausedLength);
                    mediaPlayer.start();
                }


            }
        });
//play ended

//Pause button
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // checking the media player
                // if the audio is playing or not.
                if (mediaPlayer.isPlaying()) {

                    pause.setVisibility(View.GONE);
                    play.setVisibility(View.VISIBLE);
                    // pausing the media player if
                    // media player is playing we are
                    // calling below line to stop our media player.
                    playflag = playflag + 1;


                    Log.d("playflag in pause ", "" + playflag);


                    pausedLength = mediaPlayer.getCurrentPosition();
                    Log.d("pausedLength ", "" + pausedLength);

                    mediaPlayer.pause();

                    // below line is to display a message when media player is paused.
                    Toast.makeText(getApplicationContext(), "Audio has been paused", Toast.LENGTH_SHORT).show();
                } else {
                    // this method is called when media player is not playing.
                    Toast.makeText(getApplicationContext(), "Audio has not played", Toast.LENGTH_SHORT).show();
                }
            }


        });
//pause ended

    }

    private void getFavourites() {

        Log.d("HEY INTO GET FAV", "HEYYY" );

        Query query = favReference.orderByChild("loggedemail");//.equalTo(loggedemail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("user", "" + snapshot);
                    for (DataSnapshot user : snapshot.getChildren()) {
                        Log.d("Favoutrite songs ", "" + user);
                        FavouritesSongModel favouritesSongModel = user.getValue(FavouritesSongModel.class);
                        if (favouritesSongModel.getLoggedemail().equals(loggedemail)) {
                            Log.d("HEY SONG NAME " , "" + favouritesSongModel.getSongName());
                            Log.d("HEY SONG ID " , "" + favouritesSongModel.getSongId());
                            favourite.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favourite_filled));
                        } else {
                            Toast.makeText(getApplicationContext(), "No favourites added", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No favourites added", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}