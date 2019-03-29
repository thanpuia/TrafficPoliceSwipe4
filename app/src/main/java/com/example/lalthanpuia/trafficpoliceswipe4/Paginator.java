package com.example.lalthanpuia.trafficpoliceswipe4;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import java.util.ArrayList;
import java.util.Arrays;

public class Paginator {

    Context c;
    private PullToLoadView pullToLoadView;
    RecyclerView rv;
    private MyAdapter adapter;
    private boolean isLoading = false;
    private boolean hasLoadedAll = false;
    private int nextPage;
    int NUM_LOAD_INDEX = 10;

    FirebaseDatabase database;
    DatabaseReference myRef;
    DataSnapshot specialDataSnapshot;

    Long startAt;
    String lastKey;

    int counter = 0;
    boolean firstPageLoaded = false;
    String from;


    public Paginator(final Context c, PullToLoadView pullToLoadView) {
        this.c = c;
        this.pullToLoadView = pullToLoadView;

        //RECYCLERVIEW
        RecyclerView rv=pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL,false));

//        admin_arrayList = new ArrayList<>();
//        date_arrayList = new ArrayList<>();
//        message_arrayList = new ArrayList<>();
        //adminTemp = new String();

       // adapter=new MyAdapter(c,new ArrayList<String>());


        adapter=new MyAdapter(c,new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>(),new String(),new ArrayList<String>());
        rv.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("notifications");

        rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c,"click"+view.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        initializePaginator();
    }

    /*
    PAGE DATA
     */

    public void initializePaginator()
    {
        Log.i("LOADMORE/3","3");
        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.setPullCallback(new PullCallback() {

            //LOAD MORE DATA
            @Override
            public void onLoadMore() {
                Log.i("LOADMORE/2","2");
                loadData(2);
            }

            //REFRESH AND TAKE US TO FIRST PAGE
            @Override
            public void onRefresh() {
                Log.i("LOADMORE/1","1");
                adapter.clear();
                firstPageLoaded = false;
                adapter.notifyDataSetChanged();
                hasLoadedAll=false;
                loadData(1);
               // loadNewData();
            }

            //IS LOADING
            @Override
            public boolean isLoading() {
                return isLoading;
            }

            //CURRENT PAGE LOADED
            @Override
            public boolean hasLoadedAllItems() {
                return hasLoadedAll;
            }
        });

        pullToLoadView.initLoad();
    }

    //LOAD NEW DATA ON REFRESH !

    /*
     LOAD MORE DATA
     SIMULATE USING HANDLERS
     */

    public void loadData(final int page)
    {
        //adapter.clear();
        Log.i("LOADMORE/PAGE",""+page);
        if(firstPageLoaded && page==1){
            return;
        }else
            firstPageLoaded = true;

        isLoading = true;

        Query ref1;
        if(page == 1) {
            Log.i("LOADMORE/INITIAL","load");
            ref1 = myRef.orderByChild("sortkey").limitToFirst(NUM_LOAD_INDEX);
        } else {
            Log.i("LOADMORE/NEW", "START " + startAt);
            ref1 = myRef.orderByChild("sortkey").startAt(startAt).limitToFirst(NUM_LOAD_INDEX);
        }

        ref1.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().equals(lastKey)){
                    return;
                }

                String adminTemp    = (String) dataSnapshot.child("admin")  .getValue();
                String dateTemp     = (String) dataSnapshot.child("date")   .getValue();
                String messageTemp  = (String) dataSnapshot.child("message").getValue();

                String downloadURL = (String)  dataSnapshot.child("downloadURL").getValue();

                String latitude = (String)     dataSnapshot.child("latitude").getValue();
                String longitude = (String)    dataSnapshot.child("longitude").getValue();

                String userUniqueKey = (String)dataSnapshot.child("user_id").getValue();
                String police_incharge = (String) dataSnapshot.child("police_incharge").getValue();


                lastKey = dataSnapshot.getKey();

                String uniqueKey = lastKey;
               // String postuniquekey= lastKey;

                //messageTemp = messageTemp+" \n"+latitude+" "+longitude;
////                admin_arrayList.add(adminTemp);
////                date_arrayList.add(dateTemp);
////                message_arrayList.add(messageTemp);
//
                from = "paginator";
                adapter.add(adminTemp, dateTemp, messageTemp, downloadURL, latitude, longitude, uniqueKey, userUniqueKey, from,police_incharge);
                adapter.notifyDataSetChanged();
//                counter++;
                startAt = (Long) dataSnapshot.child("sortkey").getValue();



            }
            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
//
//                //ADD CURRENT PAGE'S DATA
//                for (int i=0;i<counter;i++)
//                {
//                    String adminTEMP = admin_arrayList.get(i);
//                    String dateTEMP = date_arrayList.get(i);
//                    String messageTEMP = message_arrayList.get(i);
//
//                    adapter.add(adminTEMP, dateTEMP, messageTEMP);
//                }

                pullToLoadView.setComplete();
                isLoading=false;
                nextPage=page+1;
            }
        },1500);
    }
  /*  public void loadNewData(){

        isLoading = true;
        Log.i("LOADMORE/NEW", "START " + startAt);

        myRef.orderByChild("sortkey").startAt(startAt).limitToFirst(NUM_LOAD_INDEX).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String adminTemp    = (String) dataSnapshot.child("admin")  .getValue();
                String dateTemp     = (String) dataSnapshot.child("date")   .getValue();
                String messageTemp  = (String) dataSnapshot.child("message").getValue();

                String downloadURL = (String) dataSnapshot.child("downloadURL").getValue();

                String latitude = (String) dataSnapshot.child("latitude").getValue();
                String longitude = (String)dataSnapshot.child("longitude").getValue();
               // messageTemp = messageTemp+"\n "+latitude+" "+longitude;

                adapter.add(adminTemp, dateTemp, messageTemp, downloadURL, latitude, longitude);
                adapter.notifyDataSetChanged();

//                admin_arrayList.add(adminTemp);
//                date_arrayList.add(dateTemp);
//                message_arrayList.add(messageTemp);
//               adapter.add(admin_arrayList,date_arrayList,message_arrayList);

//                counter++;
     //           startAt = dataSnapshot.getKey();
                startAt = (Long) dataSnapshot.child("sortkey").getValue();

            }
            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
//                adapter.clear();
//                adapter.notifyDataSetChanged();
//
//                //ADD CURRENT PAGE'S DATA
//                for (int i=0;i<counter;i++)
//                {
//                    String adminTEMP = admin_arrayList.get(i);
//                    String dateTEMP = date_arrayList.get(i);
//                    String messageTEMP = message_arrayList.get(i);
//
//                    adapter.add(adminTEMP, dateTEMP, messageTEMP);
//                }

                pullToLoadView.setComplete();
                isLoading=false;
               // nextPage=page+1;
            }
        },1200);
    }*/
}
