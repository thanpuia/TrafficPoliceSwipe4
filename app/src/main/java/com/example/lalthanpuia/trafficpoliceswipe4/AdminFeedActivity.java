package com.example.lalthanpuia.trafficpoliceswipe4;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.lalthanpuia.trafficpoliceswipe4.adapters.MyAdapter;
import com.example.lalthanpuia.trafficpoliceswipe4.entity.NotificationEntity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class AdminFeedActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    public static LocationManager locationManager;

    List<String> postId;
    SharedPreferences sharedPreferences;

    String shared_uid;
    NotificationEntity notificationEntity;
    ArrayList<NotificationEntity> notificationEntities;
    RecyclerView recyclerView;
    MyAdapter myAdapter;

    //PullToRefreshView pullToRefreshView;

    private final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feed);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        shared_uid = sharedPreferences.getString("uid","");


        notificationEntity = new NotificationEntity();
        notificationEntities = new ArrayList<>();
        //postId = new PostIds();

        recyclerView = findViewById(R.id.recyclerViewAdminView);
        //pullToRefreshView = findViewById(R.id.pullToRefreshAdminView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("notifications/");

        // TODO: DOWNLOAD ALL THE NOTIFICATION ONE BY ONE
        myRef.orderByChild("sortkey").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("TAG/admin view","snap:"+dataSnapshot);
                notificationEntity = dataSnapshot.getValue(NotificationEntity.class);

                notificationEntities.add(notificationEntity);
                String name = (String) dataSnapshot.child("sender_name").getValue();
                Log.i("TAG/adminfeed","sender_name:"+name);
                //myAdapter = new MyAdapter(notificationEntity);
                //recyclerView.setAdapter(myAdapter);
                //notifyAll();
            }
            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        //TODO: THIS IS CALLED AFTER ALL THE DATA IS FINISHED FROM THE CHILD EVENT LISTENER!
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myAdapter = new MyAdapter(notificationEntities);
                recyclerView.setAdapter(myAdapter);
                Log.i("TAG/admin feed","finsiher!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    // PERMISSION FOR LOCATION STARTS HEREE
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Permission")
                        .setMessage("permit em?")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(AdminFeedActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        //Request location updates:
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, (LocationListener) this);
                        //locationManager.requestLocationUpdates(this.LOCATION_SERVICE, 400, 1, (LocationListener) MainActivity.this);
                    }
                    // locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
    //PERMISSION FOR LOCATION ENDS HERE

}
