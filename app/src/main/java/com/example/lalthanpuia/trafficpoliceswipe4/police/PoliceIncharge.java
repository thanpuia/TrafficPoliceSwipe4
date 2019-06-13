package com.example.lalthanpuia.trafficpoliceswipe4.police;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lalthanpuia.trafficpoliceswipe4.MainActivity;
import com.example.lalthanpuia.trafficpoliceswipe4.R;
import com.example.lalthanpuia.trafficpoliceswipe4.signing.GoogleSignIn;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class PoliceIncharge extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String message;
    String date;
    String senderName;
    double lat,lng;
    RecyclerView recyclerView;
    PoliceEntity policeEntity;
    SharedPreferences sharedPreferences;
    ArrayList<String> assignedRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_incharge);
        sharedPreferences = this.getSharedPreferences("com.example.lalthanpuia.trafficpoliceswipe4.signing", Context.MODE_PRIVATE);

        //getting the role from the GoogleSignIn
        assignedRole = GoogleSignIn.roleAssign;

        policeEntity = new PoliceEntity();
        recyclerView = findViewById(R.id.recyclerViewPoliceView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        final PoliceAdapter policeAdapter = new PoliceAdapter(new ArrayList<PoliceEntity>());

        recyclerView.setAdapter(policeAdapter);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("notifications");

        databaseReference.orderByChild("sortkey").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                policeEntity = dataSnapshot.getValue(PoliceEntity.class);
                policeAdapter.add(policeEntity);
                policeAdapter.notifyDataSetChanged();

            }

            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }@Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }@Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }@Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void logoutClick(View view) {
        try {

            GoogleSignIn.mAuth.signOut();
            sharedPreferences.edit().putString("role", "").apply();
            Intent intent = new Intent(this, GoogleSignIn.class);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            Toasty.error(this,"Somethings wrong");

            e.printStackTrace();
        }
    }
}
