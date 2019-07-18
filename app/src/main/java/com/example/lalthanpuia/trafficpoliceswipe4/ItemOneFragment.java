package com.example.lalthanpuia.trafficpoliceswipe4;

/*
*
*
*
*
*
* //TODO: THIS IS THE ADMIN VIEW
*
*
*
*
*
* */





import android.Manifest;
import android.Manifest.permission;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lalthanpuia.trafficpoliceswipe4.entity.NotificationEntity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.srx.widget.PullToLoadView;


import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */

public class ItemOneFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;
    public static LocationManager locationManager;

    List<String> postId;
    SharedPreferences sharedPreferences;

    String shared_uid;
    NotificationEntity notificationEntity;
    ArrayList<NotificationEntity> arrayLists;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    int i;
    Toolbar toolbar;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public static ItemOneFragment newInstance() {
        ItemOneFragment fragment = new ItemOneFragment();
        return fragment;
    }

    public ItemOneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_item_one, container, false);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        PullToLoadView pullToLoadView = view.findViewById(R.id.pullToLoadView);

        toolbar = (Toolbar) container.findViewById(R.id.tool_bar_singleuserfeed);
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setTitleTextColor(Color.rgb(205, 163, 128));
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("List of Report");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        shared_uid = sharedPreferences.getString("uid","");

        notificationEntity = new NotificationEntity();
        arrayLists = new ArrayList<>();
        //postId = new PostIds();

        recyclerView = view.findViewById(R.id.recyclerViewSingleUserFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user_details/"+shared_uid+"/post_id/");

        //  new Paginator(getContext(),pullToLoadView).initializePaginator();




        checkLocationPermission();
        return view;
    }


    // PERMISSION FOR LOCATION STARTS HEREE
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getContext())
                        .setTitle("Permission")
                        .setMessage("permit em?")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{permission.ACCESS_FINE_LOCATION},
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
                    if (ContextCompat.checkSelfPermission(getContext(), permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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

    //PERMISSION FOR STORAGE STARTS HERE


    //PERMISSION FOR STORAGE ENDS HERE
}