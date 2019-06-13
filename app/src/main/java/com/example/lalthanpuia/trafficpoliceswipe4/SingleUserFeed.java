package com.example.lalthanpuia.trafficpoliceswipe4;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingleUserFeed extends Fragment {

    Context c;
    private PullToLoadView pullToLoadView;
    RecyclerView rv;
    private MyAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int nextPage;
    int NUM_LOAD_INDEX = 10;
    int x;

    FirebaseDatabase database;
    DatabaseReference myRef,myRef_userpostid;
    DataSnapshot specialDataSnapshot;

    Long startAt;
    String lastKey;

    int counter = 0;
    boolean firstPageLoaded = false;
    SharedPreferences sharedPreferences;
    String shared_userUniqueKey;
    ArrayList<String> userPostUniqueKeyList;
    Boolean userPostUniqueKeyListDownloadFinished = false;
    boolean processDone;

    public SingleUserFeed() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_user_feed, container, false);

        rv = view.findViewById(R.id.recyclerView);
        database = FirebaseDatabase.getInstance();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        shared_userUniqueKey = sharedPreferences.getString("userUniqueKey","");
        userPostUniqueKeyList = new ArrayList<>();

        //RECYCLERVIEW
        rv.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL,false));

        this.c=getContext();
        adapter=new MyAdapter(c,new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(), new String(), new ArrayList<String>());
        rv.setAdapter(adapter);


        rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(c,"click"+view.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        //GET THE DATA NOW
            getUserPosts();

        return view;
    }

    private void getUserPosts() {

        for ( x = 0; x < MainActivity.userPostUniqueIdLists.size(); x++ ) {
            myRef = database.getReference("notifications/"+MainActivity.userPostUniqueIdLists.get(x));
            Query ref1;
            ref1 = myRef.orderByChild("sortkey");

            ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Log.d("TAG", "getUserPost: " + dataSnapshot);
                   // Toasty.normal(getContext(),""+dataSnapshot,Toasty.LENGTH_SHORT).show();

                    lastKey = dataSnapshot.getKey();

                    String uniqueKey = lastKey;
                    String adminTemp    = (String) dataSnapshot.child("admin")  .getValue();
                    String dateTemp     = (String) dataSnapshot.child("date")   .getValue();
                    String messageTemp  = (String) dataSnapshot.child("message").getValue();
                    String downloadURL = (String)  dataSnapshot.child("downloadURL").getValue();
                    String latitude = (String)     dataSnapshot.child("latitude").getValue();
                    String longitude = (String)    dataSnapshot.child("longitude").getValue();
                    String userUniqueKey = String.valueOf(dataSnapshot.child("user_id").getValue());
                    String policeIncharge = (String) dataSnapshot.child("police_incharge").getValue();

                    String from = "singleUserFeed";
                    adapter.add(adminTemp, dateTemp, messageTemp, downloadURL, latitude, longitude, uniqueKey, userUniqueKey, from, policeIncharge);
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
