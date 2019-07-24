package com.example.lalthanpuia.trafficpoliceswipe4;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationListener;

import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.lalthanpuia.trafficpoliceswipe4.signing.FireBasePhoneAuth;
import com.example.lalthanpuia.trafficpoliceswipe4.signing.GoogleSignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static android.support.constraint.Constraints.TAG;

//import com.google.android.gms.location.LocationManager;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public static LocationManager locationManager;
    FirebaseStorage storage;
    StorageReference storageReference;
    public static final String FIREBASE_USERNAME = "thanpuia46@gmail.com";
    public static final String FIREBASE_PASSWORD = "Lorenzo@99";
    boolean Status = true;
    boolean unique;
    FirebaseDatabase database,database_policeIncharge;
    DatabaseReference myRef_police;
    public static ArrayList<String> policeName, policeNameKey;
    Intent intent;
    Button adminfeedButton, reportButton, adminGlobalSenderButton, globalNotifyButton;
  //  BottomNavigationViewHelper bottomNavigationViewHelper;
    public static LocationListener locationListener;

    private static final int CAMERA_REQUEST = 123;
    public static ArrayList<String> userPostUniqueIdLists;
    String shared_userUniqueKey;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    FrameLayout singleUserFeedFrame, globalNotifyFrame;
    FirebaseAuth firebaseAuth;
    TextView username, userphone;


    private final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
///
        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitleTextColor(Color.rgb(205,163,128));
        setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Traffic Apps");
///

        username = findViewById(R.id.userNameMainMenu);
        userphone = findViewById(R.id.userphoneMainMenu);

        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();

        //GETTING THE SHARED PREFERENCES:
      //  shared_userUniqueKey = sharedPreferences.getString("userUniqueKey","");

        getUserPostIds();
//        checkLocationPermission();
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        boolean citizen = true;
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        policeName = new ArrayList<>();
        policeNameKey = new ArrayList<>();

        policeNameKey.add("");
        policeName.add("");

        adminfeedButton = findViewById(R.id.adminFeedButton);
       // reportButton = findViewById(R.id.reportButton);
        singleUserFeedFrame = findViewById(R.id.singleUserFeed);
        adminGlobalSenderButton = findViewById(R.id.adminGlobalSenderButton);
        globalNotifyFrame = findViewById(R.id.globalNotificationFrame);

        //FIRST CHECK THE SHARED PREFERENCE
       // sharedPreferences = this.getSharedPreferences("com.example.lalthanpuia.trafficpoliceswipe4.signing", Context.MODE_PRIVATE);

        Intent intentfromSigin = getIntent();

        //Not from intent, but from sharedPreference
        //String role = (String) intentfromSigin.getStringExtra("role");

        String role = sharedPreferences.getString("role","");
        Log.v("TAG","ROle from MainActivigy"+ role);

        //HIDE THE BUTTON IF THE USER IS ADMIN
        if(role.equals("admin")){
           // singleUserFeedFrame.setVisibility(View.GONE);
        }
        else if(role.equals("citizen")){
            adminfeedButton.setVisibility(View.GONE);
            adminGlobalSenderButton.setVisibility(View.GONE);
        }

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
                });
        */


        //GETTING THE POLICE NAMES FOR THE HINT
        database_policeIncharge = FirebaseDatabase.getInstance();

        myRef_police = database_policeIncharge.getReference("police_details");

        //getReference hi police_details hi dah ve ve angai.
        myRef_police.orderByChild("police_details").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //  Log.v("tag",""+dataSnapshot);
                String officerTemp = String.valueOf(dataSnapshot.child("name").getValue());
                String key = (String) dataSnapshot.getKey();
                Log.v("tag","name: "+ officerTemp);

                //  policeName[]
                policeName.add(officerTemp);
                policeNameKey.add(key);

            }

            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }@Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }@Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }@Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

  /*      //MANUALLY DISPLAY THE FIRST FRAGMENT ONLY OE TIME
        FragmentTransaction tempTtransaction = getSupportFragmentManager().beginTransaction();
        tempTtransaction.replace(R.id.frame_layout, ItemOneFragment.newInstance());
        tempTtransaction.commit();*/

      //  intent = new Intent(this, FragmentHolderActivity.class);

        username.setText(sharedPreferences.getString("fullName",""));
        userphone.setText(sharedPreferences.getString("phoneNumber",""));
    }
/*
    public void logoutClick(View view) {

    }*/

    public void oneclick(View view) {

        //TODO: ADMIN VIEW

        Intent intent = new Intent(this,AdminFeedActivity.class);
        startActivity(intent);

       /* intent.putExtra("click","1");
        startActivity(intent);*/
    }
    public void twoclick(View view) {

        Intent intent = new Intent(this, ReportSubmitActivity.class);

        //intent.putExtra("click","2");
        startActivity(intent);
    }
    public void threeclick(View view) {
        intent.putExtra("click","3");
        startActivity(intent);
    }
    public void fourclick(View view) {


        //intent.putExtra("click","4");
        startActivity(new Intent(this,SingleUserFeedNewActivity.class));
    }
    public void fiveclick(View view) {
        intent.putExtra("click","5");
        startActivity(intent);
    }
    public void sixclick(View view) {
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

    } public void sevenclick(View view) {
        intent.putExtra("click","7");
        startActivity(intent);
    }

    public void eightclick(View view) {
        intent.putExtra("click","8");
        startActivity(intent);
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
                                ActivityCompat.requestPermissions((MainActivity)getApplicationContext(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions((Activity) this,
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
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, (LocationListener) getApplicationContext());
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

    //GETTING ALL THE ID THAT THE USER HAVE POSTED . SO THAT IT CAN BE DL IN THE SingleUserFeed
    private void getUserPostIds() {
       /* FirebaseDatabase database1;
        database1 = FirebaseDatabase.getInstance();

        userPostUniqueIdLists = new ArrayList<>();
        DatabaseReference myRef;
        myRef = database1.getReference("user_details/" + shared_userUniqueKey + "/post_id");


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String tempUniqueKey = (String) dataSnapshot.getValue();
                userPostUniqueIdLists.add(tempUniqueKey);
                Log.d("TAG", "getUserPostId: " + dataSnapshot);

                // Toasty.normal(getContext(), "" + userPostUniqueIdLists, Toasty.LENGTH_SHORT).show();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }

        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void logoutClick(MenuItem item) {

        //ERASE ALL THE USER DATA IN THE SHARED PREFERENCES
        editor.putString("uid","");
        editor.putString("role","");
        editor.putString("fullName","");
        editor.putString("email","");
        editor.putString("phoneNumber","");

        editor.commit();
        //FireBasePhoneAuth.signOut();
        firebaseAuth.signOut();
        Toasty.success(this,"Log out!",Toasty.LENGTH_SHORT).show();
        startActivity(new Intent(this, FireBasePhoneAuth.class));

    }
}

//    ListView listView;
//    ArrayList<String> arrayList,dateList,messageList;
//    // ArrayAdapter<String> adapter;
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
