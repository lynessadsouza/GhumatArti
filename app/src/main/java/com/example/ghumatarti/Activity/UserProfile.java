package com.example.ghumatarti.Activity;

import static com.example.ghumatarti.Activity.LoginActivity.loggedemail;
import static com.example.ghumatarti.Activity.LoginActivity.loggedname;
import static com.example.ghumatarti.Activity.LoginActivity.loggedphone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ghumatarti.ModelClass.FavouritesSongModel;
import com.example.ghumatarti.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    TextView email, name, phone;

    TextView favouriteCountt;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    int favouriteCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        email = (TextView) findViewById(R.id.profile_email);
        name = (TextView) findViewById(R.id.profile_name);
        phone = (TextView) findViewById(R.id.profile_phone);

        favouriteCountt = (TextView) findViewById(R.id.favouriteCount);

        email.setText(loggedemail);
        name.setText(loggedname);
        phone.setText(loggedphone);

       // Log.d("Email", "email" + email);
      String  e = loggedemail.replace(".", ",");


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("Favourites").child(e);


        getFavourites();

        favouriteCountt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserProfile.this, Favourites.class);
                startActivity(i);
            }
        });


    }

    private void getFavourites() {


        Query query = databaseReference.orderByChild("loggedemail").equalTo(loggedemail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    Log.d("user", "" + snapshot);

                    for (DataSnapshot user : snapshot.getChildren()) {
                        // do something with the individual "issues"


                        FavouritesSongModel favouritesSongModel = user.getValue(FavouritesSongModel.class);


                        if (favouritesSongModel.getLoggedemail().equals(loggedemail)) {

                            Log.d("Favourite Song", "" + favouritesSongModel.getSongName());


                            favouriteCount++;
                            Log.d("Favourite Count", "" + favouriteCount);
                            favouriteCountt.setText("" + favouriteCount);

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