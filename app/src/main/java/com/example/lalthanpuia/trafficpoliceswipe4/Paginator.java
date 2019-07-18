package com.example.lalthanpuia.trafficpoliceswipe4;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.lalthanpuia.trafficpoliceswipe4.entity.NotificationEntity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;
import java.util.List;

public class Paginator {

    FirebaseDatabase database;
    DatabaseReference myRef, myRef2;
    List<String> postId;
    SharedPreferences sharedPreferences;

    String shared_uid;
    NotificationEntity notificationEntity;
    ArrayList<NotificationEntity> arrayLists;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    int i;
    Toolbar toolbar;

    public Paginator(Context context, PullToLoadView pullToLoadView) {

       /*

        toolbar = (Toolbar)(R.id.tool_bar_singleuserfeed);
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        toolbar.setTitleTextColor(Color.rgb(205, 163, 128));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("List of Report");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        shared_uid = sharedPreferences.getString("uid","");

        notificationEntity = new NotificationEntity();
        arrayLists = new ArrayList<>();
        //postId = new PostIds();

        recyclerView = findViewById(R.id.recyclerViewSingleUserFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user_details/"+shared_uid+"/post_id/");

        */


    }




    public void initializePaginator() {


    }
}
