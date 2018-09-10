package com.example.lalthanpuia.trafficpoliceswipe4;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemOneFragment extends Fragment {


    private ArrayList<String> dateList,messageList,adminList,dateListTemp,messageListTemp,adminListTemp;
    FirebaseDatabase database;
    DatabaseReference myReef;
   // SwipeRefreshLayout mSwipeRefreshLayout;
    SwipyRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager layoutManager;
    public DataSnapshot specialDataSnapshot;

    private Query postQuery;
    private String newestPostId;
    private String oldesPostId;
    private int startAt = 0;

    private boolean temp1;
    private boolean temp2;
    private boolean gate;
    private ScrollListView scrollListView;

    private int counter = 0;

    private CustomAdapter customAdapter = new CustomAdapter();


    public static ItemOneFragment newInstance() {
        ItemOneFragment fragment = new ItemOneFragment();
        return fragment;
    }
    public ItemOneFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_item_one, container, false);
        temp1=temp2=true;


        layoutManager = new LinearLayoutManager(getContext());
        scrollListView = new ScrollListView(getContext());

        ListView listView= view.findViewById(R.id.listView);
        mSwipeRefreshLayout = view.findViewById(R.id.pullToRefresh);

        dateList= new ArrayList<String>();
        messageList= new ArrayList<String>();
        adminList = new ArrayList<String>();
        dateListTemp= new ArrayList<String>();
        messageListTemp= new ArrayList<String>();
        adminListTemp = new ArrayList<String>();
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        //final CustomAdapter adapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),""+i,Toast.LENGTH_SHORT).show();
            }
        });
        database = FirebaseDatabase.getInstance();
        myReef = database.getReference("notifications");



        //A RESHERH LEH LIST CU A DIK A MHS, BLANK DATA A LA
        /*myReef.limitToFirst(5).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    oldestPostId = child.getKey();

                    String admin = (String)dataSnapshot.child("admin").getValue();
                    String date = (String) dataSnapshot.child("date").getValue();
                    String message = (String) dataSnapshot.child("message").getValue();

                    adminList.add(0,admin);
                    dateList.add(0,date);
                    messageList.add(0,message);

                    customAdapter.notifyDataSetChanged();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        myReef.orderByKey().limitToLast(5).addChildEventListener(new ChildEventListener() {
            //.startAt(oldesPostId)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String admin = (String)dataSnapshot.child("admin").getValue();
                String date = (String) dataSnapshot.child("date").getValue();
                String message = (String) dataSnapshot.child("message").getValue();

                adminList.add(0,admin);
                dateList.add(0,date);
                messageList.add(0,message);

                customAdapter.notifyDataSetChanged();

                if(temp1){
                    newestPostId = dataSnapshot.getKey();
                    temp1 = false;
                }
                oldesPostId = dataSnapshot.getKey();
                specialDataSnapshot =dataSnapshot;
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String admin = (String)dataSnapshot.child("admin").getValue();
                String date = (String) dataSnapshot.child("date").getValue();
                String message = (String) dataSnapshot.child("message").getValue();

                adminList.add(0,admin);
                dateList.add(0,date);
                messageList.add(0,message);

                customAdapter.notifyDataSetChanged();

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


       myReef.limitToLast(10).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

//                String admin = (String)dataSnapshot.child("admin").getValue();
//                String date = (String) dataSnapshot.child("date").getValue();
//                String message = (String) dataSnapshot.child("message").getValue();
//
//                adminList.add(0,admin);
//                dateList.add(0,date);
//                messageList.add(0,message);
//
//                customAdapter.notifyDataSetChanged();
//                oldesPostId = dataSnapshot.getKey();
//


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String admin = (String)dataSnapshot.child("admin").getValue();
                String date = (String) dataSnapshot.child("date").getValue();
                String message = (String) dataSnapshot.child("message").getValue();

                adminList.add(0,admin);
                dateList.add(0,date);
                messageList.add(0,message);

                customAdapter.notifyDataSetChanged();

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

//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                doSomething();
//                mSwipeRefreshLayout.setRefreshing(false);
//
//            }
//        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                temp1=temp2=true;

                if(direction == SwipyRefreshLayoutDirection.TOP){
                    doSomething();
                    mSwipeRefreshLayout.setRefreshing(false);
                }else{
                    bottomrefresh();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                }
        });
/*
        //A NODE ZONG A LA TAWP
        myReef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String admin = (String)dataSnapshot.child("admin").getValue();
                String date = (String) dataSnapshot.child("date").getValue();
                String message = (String) dataSnapshot.child("message").getValue();

                adminList.add(0,admin);
                dateList.add(0,date);
                messageList.add(0,message);

                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String admin = (String)dataSnapshot.child("admin").getValue();
                String date = (String) dataSnapshot.child("date").getValue();
                String message = (String) dataSnapshot.child("message").getValue();

                adminList.add(0,admin);
                dateList.add(0,date);
                messageList.add(0,message);

                customAdapter.notifyDataSetChanged();

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
        });*/

        return view;
    }
    private void updateUI(FirebaseUser user) {

    }

    class CustomAdapter extends android.widget.BaseAdapter {
        @Override
        public int getCount() {
            // return 0;
            return dateList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView= getLayoutInflater().inflate(R.layout.list_details,null);

            TextView textView_admin= convertView.findViewById(R.id.textview_admin);
            TextView textView_date= convertView.findViewById(R.id.textview_date);
            TextView textView_message= convertView.findViewById(R.id.textview_message);

            textView_admin.setText(adminList.get(position));
            textView_date.setText(dateList.get(position));
            textView_message.setText(messageList.get(position));
            return convertView;
        }
    }


    private void doSomething() {

       /* myReef.orderByKey().startAt(oldestPostId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren()) {

                    String admin = (String)dataSnapshot.child("admin").getValue();
                    String date = (String) dataSnapshot.child("date").getValue();
                    String message = (String) dataSnapshot.child("message").getValue();

                    adminList.add(0,admin);
                    dateList.add(0,date);
                    messageList.add(0,message);

                    customAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        adminList.clear();
        dateList.clear();
        messageList.clear();


        myReef.orderByKey().limitToLast(5).addChildEventListener(new ChildEventListener() {
//.startAt(oldesPostId)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String admin = (String)dataSnapshot.child("admin").getValue();
                String date = (String) dataSnapshot.child("date").getValue();
                String message = (String) dataSnapshot.child("message").getValue();

                adminList.add(0,admin);
                dateList.add(0,date);
                messageList.add(0,message);

                customAdapter.notifyDataSetChanged();

                if(temp1){
                    newestPostId = dataSnapshot.getKey();
                    temp1 = false;
                }
                oldesPostId = dataSnapshot.getKey();
                specialDataSnapshot =dataSnapshot;
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String admin = (String)dataSnapshot.child("admin").getValue();
                String date = (String) dataSnapshot.child("date").getValue();
                String message = (String) dataSnapshot.child("message").getValue();

                adminList.add(0,admin);
                dateList.add(0,date);
                messageList.add(0,message);

                customAdapter.notifyDataSetChanged();

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

    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//
//        outState.putStringArrayList("admin",adminList);
//        outState.putStringArrayList("date",dateList);
//        outState.putStringArrayList("message",messageList);
//        super.onSaveInstanceState(outState);
//    }

    public void bottomrefresh(){
        Toast.makeText(getContext(),"bott",Toast.LENGTH_SHORT).show();
        gate=true;

        myReef.endAt(newestPostId).orderByKey().addChildEventListener(new ChildEventListener() {
            //.startAt(oldesPostId)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String adminTemp = (String)dataSnapshot.child("admin").getValue();
                String dateTemp = (String) dataSnapshot.child("date").getValue();
                String messageTemp = (String) dataSnapshot.child("message").getValue();

                adminListTemp.add(0,adminTemp);
                dateListTemp.add(0,dateTemp);
                messageListTemp.add(0,messageTemp);
                /*
                 LISTTEMP A KA DAH CHAN CU, DATABASE A TANG A A LAK KHAN A TOP A TANG A LA THIN A,
                 A BOTTOM A TANG A DISPLAY KA DUH BAWK SIA. CCUVANG CUAN ARRYLIST AH A HARN IN KA HREM LEH A.
                 CUTANG TANG CUAN A LET LING ZAWNGIN KA REM A . LISTVIEW AH KA DAH CUAK
                */
                counter++;
                if(counter==4){

                    for(int i=0;i<counter;i++){
                        if(i!=0){
                            String admin = adminListTemp.get(i);
                            String date = dateListTemp.get(i);
                            String message = messageListTemp.get(i);
                            adminList.add(admin);
                            dateList.add(date);
                            messageList.add(message);
                            customAdapter.notifyDataSetChanged();
                        }
                    }
                    counter=0;
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String admin = (String)dataSnapshot.child("admin").getValue();
                String date = (String) dataSnapshot.child("date").getValue();
                String message = (String) dataSnapshot.child("message").getValue();

                adminList.add(0,admin);
                dateList.add(0,date);
                messageList.add(0,message);

                customAdapter.notifyDataSetChanged();

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
    }
}