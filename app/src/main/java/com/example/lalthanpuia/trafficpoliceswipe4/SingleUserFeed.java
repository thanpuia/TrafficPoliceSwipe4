package com.example.lalthanpuia.trafficpoliceswipe4;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.OnDisconnect;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;


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
        adapter=new MyAdapter(c,new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
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

   /* private void getUserPostIds() {
        myRef = database.getReference("user_details/" + shared_userUniqueKey + "/post_id");

        processDone =false;

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if(dataSnapshot.exists()){
                    String tempUniqueKey = (String) dataSnapshot.getValue();
                    userPostUniqueKeyList.add(tempUniqueKey);
                    Log.d("TAG", "getUserPostId: " + dataSnapshot);

                    Toasty.normal(getContext(), "" + userPostUniqueKeyList, Toasty.LENGTH_SHORT).show();
                }else{

                    processDone = true;
                    getUserPosts();
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

    }*/

    private void getUserPosts() {

        for (int x = 0; x < ItemTwoFragment.userPostUniqueIdLists.size(); x++) {
            myRef = database.getReference("notifications/"+ItemTwoFragment.userPostUniqueIdLists.get(x));
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
                    adapter.add(adminTemp, dateTemp, messageTemp, downloadURL, latitude, longitude, uniqueKey, userUniqueKey);
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}
