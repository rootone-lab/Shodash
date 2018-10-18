package com.rootonelabs.vicky.shodash;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {


    private static final String TAG = "Home";
    
    private ListView DetailListView;
    private Button logout;


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ChildEventListener childEventListener;

    private ListAdapter listAdapter;
    private ProgressBar progressBar;

    private List<listDetails> speakerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("sessions");

        DetailListView = (ListView)findViewById(R.id.detailListView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        progressBar.setVisibility(ProgressBar.INVISIBLE);

        //Initializeing List And Its adapter

        speakerList = new ArrayList<>();
        listAdapter = new ListAdapter(this, R.layout.item_detail, speakerList);
        DetailListView.setAdapter(listAdapter);


        logout = (Button)findViewById(R.id.logout);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Toast.makeText(Home.this, "Signed in: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Home.this, "Signed Out Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(Home.this);
                startActivity(new Intent(Home.this, LoginActivity.class));
            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            listDetails details = ds.getValue(listDetails.class);
            listAdapter.add(details);
           /* Log.i("Speaker Name", details.getSpeakerName());
            Log.i("Topic", details.getTopic());
            Log.i("Date", details.getDate());
            Log.i("Day", String.valueOf(details.getDay()));
*/
        }
    }


    @Override
    protected void onStart() {

        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {


        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }

    }
}
