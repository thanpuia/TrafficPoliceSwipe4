package com.example.lalthanpuia.trafficpoliceswipe4;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.lalthanpuia.trafficpoliceswipe4.signing.GoogleSignIn;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

//import com.google.android.gms.location.LocationManager;


public class MainActivity extends AppCompatActivity {

    public static final String FIREBASE_USERNAME = "thanpuia46@gmail.com";
    public static final String FIREBASE_PASSWORD = "Lorenzo@99";
    boolean Status = true;
    boolean unique;
    FirebaseDatabase database,database_policeIncharge;
    DatabaseReference myRef_police;
    public static ArrayList<String> policeName;
    Intent intent;

    BottomNavigationView bottomNavigationView;
    BottomNavigationViewHelper bottomNavigationViewHelper;
    public static LocationListener locationListener;

    private static final int CAMERA_REQUEST = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting toolbar as the ActionBar with setSupportActionBar() call
        boolean citizen = true;

        policeName = new ArrayList<>();
        policeName.add("");



      /*  bottomNavigationView = findViewById(R.id.navigation);
        if(citizen){
            bottomNavigationView.inflateMenu(R.menu.bottom_navigation_items_for_citizen);
        }
        else{
            bottomNavigationView.inflateMenu(R.menu.bottom_navigation_items);

        }

       // bottomNavigationView.getMenu().add(R.menu.bottom_navigation_items_for_citizen);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        ItemOneFragment itemOneFragment = null;
                        ItemTwoFragment itemTwoFragment = null;
                        ItemThreeFragment itemThreeFragment = null;
                        ItemFourFragment itemFourFragment = null;
                        ItemFiveFragment itemFiveFragment = null;
                        SingleUserFeed singleUserFeed = null;

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        switch (item.getItemId()) {

                            case R.id.action_item1:
                                itemOneFragment = ItemOneFragment.newInstance();
                                transaction.replace(R.id.frame_layout, itemOneFragment);
                                break;

                            case R.id.action_item2:
                                itemTwoFragment = ItemTwoFragment.newInstance();
                                transaction.replace(R.id.frame_layout, itemTwoFragment);
                                break;

                            case R.id.action_item3:
                              *//*  itemThreeFragment = ItemThreeFragment.newInstance();
                                transaction.replace(R.id.frame_layout,itemThreeFragment);*//*
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, CAMERA_REQUEST);
                                break;

                            case R.id.action_item4:
                                itemTwoFragment = ItemTwoFragment.newInstance();
                                // itemFourFragment = ItemFourFragment.newInstance();
                                // transaction.replace(R.id.frame_layout,itemFourFragment);
                                singleUserFeed = new SingleUserFeed();
                                transaction.replace(R.id.frame_layout, singleUserFeed);
                                break;
                        }

                        transaction.commit();

                        return true;

                    }
                });*/
        //GETTING THE POLICE NAMES FOR THE HINT
        database_policeIncharge = FirebaseDatabase.getInstance();

        myRef_police = database_policeIncharge.getReference("police_details");

        //getReference hi police_details hi dah ve ve angai.
        myRef_police.orderByChild("police_details").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //  Log.v("tag",""+dataSnapshot);
                String officerTemp = String.valueOf(dataSnapshot.child("name").getValue());

                Log.v("tag","name: "+officerTemp);

                //  policeName[]
                policeName.add(officerTemp);

            }

            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }@Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }@Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }@Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

  /*      //MANUALLY DISPLAY THE FIRST FRAGMENT ONLY OE TIME
        FragmentTransaction tempTtransaction = getSupportFragmentManager().beginTransaction();
        tempTtransaction.replace(R.id.frame_layout, ItemOneFragment.newInstance());
        tempTtransaction.commit();*/

        intent = new Intent(this, FragmentHolderActivity.class);
    }
/*
    public void logoutClick(View view) {

    }*/

    public void oneclick(View view) {
        intent.putExtra("click","1");
        startActivity(intent);
    }
    public void twoclick(View view) {
        intent.putExtra("click","2");
        startActivity(intent);
    }
    public void threeclick(View view) {
        intent.putExtra("click","3");
        startActivity(intent);
    }
    public void fourclick(View view) {
        intent.putExtra("click","4");
        startActivity(intent);
    }
    public void fiveclick(View view) {
        intent.putExtra("click","5");
        startActivity(intent);
    }
    public void sixclick(View view) {
        try {

            GoogleSignIn.mAuth.signOut();
            Intent intent = new Intent(this, GoogleSignIn.class);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            Toasty.error(this,"Somethings wrong");

            e.printStackTrace();
        }
    } public void sevenclick(View view) {
        intent.putExtra("click","7");
        startActivity(intent);
    }

    public void eightclick(View view) {
        intent.putExtra("click","8");
        startActivity(intent);
    }
}
//    ListView listView;
//    ArrayList<String> arrayList,dateList,messageList;
//   // ArrayAdapter<String> adapter;
//    FirebaseDatabase database;



//        listView= findViewById(R.id.listView);
//
//        arrayList = new ArrayList<String>();
//        dateList= new ArrayList<String>();
//        messageList= new ArrayList<String>();
//final CustomAdapter customAdapter = new CustomAdapter();
//       //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
//        //final CustomAdapter adapter = new CustomAdapter();
//        listView.setAdapter(customAdapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),""+i,Toast.LENGTH_SHORT).show();
//            }
//        });
//        database = FirebaseDatabase.getInstance();
//        //DatabaseReference myReef = database.getReference("notindex");
//        DatabaseReference myReef = database.getReference("notifications");
//
//        myReef.addChildEventListener(new ChildEventListener() {
//
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                String date = (String) dataSnapshot.child("date").getValue();
//                String message = (String) dataSnapshot.child("message").getValue();
//
//               // arrayList.add(0,date+message);
//                dateList.add(0,date);
//                messageList.add(0,message);
//                customAdapter.notifyDataSetChanged();
//
//
//                //  DataSnapshot ds_date =  dataSnapshot.child("date");
//              //  String data_date = (String) ds_date.getValue();
//
//              //  DataSnapshot ds_message =  dataSnapshot.child("date");
//              //  String data_message = (String) ds_message.getValue();
//
//               // Toast.makeText(getApplicationContext(),""+data_message,Toast.LENGTH_SHORT).show();
//
//               // Toast.makeText(getApplicationContext(),""+data_date,Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//          //      Log.i("TAGGG",s);
//                //String date  = dataSnapshot.getRef().toString();
//              //  String change = dataSnapshot.getValue(String.class);
//               // for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
//                    String date = (String) dataSnapshot.child("date").getValue();
//                    dateList.add(0,date);
//                    String message = (String) dataSnapshot.child("message").getValue();
//                    messageList.add(0,message);
//                    customAdapter.notifyDataSetChanged();
//                //    tv1.setText(date);
//                   // tv2.setText(message);
//                //}
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
   // }

//    private void updateUI(FirebaseUser user) {
//
//    }
//
//    public void ButtonClick(View view) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference();
//
//        myRef.push().setValue("33");
//    }
//    class CustomAdapter extends android.widget.BaseAdapter {
//        @Override
//        public int getCount() {
//            // return 0;
//            return dateList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            convertView= getLayoutInflater().inflate(R.layout.list_details,null);
//
//            android.widget.TextView textView1= (android.widget.TextView)convertView.findViewById(R.id.textview1_listview);
//            android.widget.TextView textView2= (android.widget.TextView)convertView.findViewById(R.id.textview2_listview);
//
//            textView1.setText(dateList.get(position));
//            textView2.setText(messageList.get(position));
//            return convertView;
//        }
//    }
//}
