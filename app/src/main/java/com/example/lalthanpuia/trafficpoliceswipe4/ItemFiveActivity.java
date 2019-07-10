package com.example.lalthanpuia.trafficpoliceswipe4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lalthanpuia.trafficpoliceswipe4.MapActivity.MapsActivity;
import com.example.lalthanpuia.trafficpoliceswipe4.signing.UserDetails;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ItemFiveActivity extends AppCompatActivity {

    TextView admintv,timetv,messagetv,selectedPoliceNameTv;
    ImageView ivMap,ivImage;
    Button pendingButton, resolvedButton;
    String uniqueKey;
    FirebaseDatabase database,database1,databasePoliceAssign;
    DatabaseReference myRef_police, myRef_user,myRef_PoliceAssign;
    ArrayList<String> officerNames;
    String[] policeList;
    Spinner policeSpinner;

    UserDetails userDetails1;

    String message;
    String downloadurl;
    String latitude;
    String longitude;
    String userUniqueKey;
    String postUniqueKey;
    String selectedPoliceName;
    String police_incharge;

    ArrayList<String> policeNameKey;
    String policeNameKeyStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_five);

        userDetails1 = new UserDetails();
        officerNames = new ArrayList<>();
        policeList = new String[]{};
        Intent intent = getIntent();
        message = intent.getStringExtra("message");
        downloadurl = intent.getStringExtra("downloadUrl");
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");
        userUniqueKey = intent.getStringExtra("userUniqueKey");
        uniqueKey = intent.getStringExtra("uniqueKey");

        Log.v("tag",""+userUniqueKey);

       messagetv = findViewById(R.id.messageTV);
       ivImage = findViewById(R.id.imageView);
       ivMap = findViewById(R.id.mapIV);

       pendingButton = findViewById(R.id.pending);
       resolvedButton = findViewById(R.id.resolved);
       policeSpinner = findViewById(R.id.spinner);
       selectedPoliceNameTv = findViewById(R.id.selectedPoliceName);

       messagetv.setText(message);

       policeNameKey = MainActivity.policeNameKey;
       policeAssignChecking();

       //PLACE FOR EMBEDDING THE MAP
        String maplocation = "http://maps.google.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom=16&size=200x100&scale=2&markers=size:tiny%7Ccolor:red%7Clabel:S%7C"+ latitude + "," + longitude +"&key=AIzaSyARsaFMey84nKb-_wlxkkXhRseNak1fSRY";
        Glide.with(ivMap.getContext())
                .load(maplocation)
                .into(ivMap);

       //IF THERE IS A PICTURE EMBEDDED, SET THE PICTURE
       if(downloadurl != "0"){

           ivImage.setMaxWidth(250);
           ivImage.setMaxHeight(250);
           FirebaseStorage storage = FirebaseStorage.getInstance();
           StorageReference storageRef = storage.getReferenceFromUrl("gs://traffficpoliceswipe4.appspot.com").child(downloadurl);

           final long ONE_MEGABYTE = 1024 * 1024;

           //download file as a byte array
           storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
               @Override
               public void onSuccess(byte[] bytes) {
                   Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                   ivImage.setImageBitmap(bitmap);
                   //showToast("Download successful!");
               }
           });
       }

        //GETTING THE POLICE NAMES FOR THE HINT
        database = FirebaseDatabase.getInstance();

        myRef_police = database.getReference("police_details");
        //getReference hi police_details hi dah ve ve angai.
       myRef_police.orderByChild("police_details").addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              //  Log.v("tag",""+dataSnapshot);
               String officerTemp = String.valueOf(dataSnapshot.child("name").getValue());
               Log.v("tag","name: "+officerTemp);

             //  policeName[]
               officerNames.add(officerTemp);

           }

           @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }@Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }@Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }@Override public void onCancelled(@NonNull DatabaseError databaseError) { }
       });

      //  String[] countries = (String[]) getResources().getStringArray(officer);
       // String[] ttt= new String[]{"what","the ","fudge"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,MainActivity.policeName );//officerNames

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        policeSpinner.setAdapter(adapter);

        //GETTING THE POLICE NAME
       policeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPoliceName = MainActivity.policeName.get(position);
                policeNameKeyStr = policeNameKey.get(position);

                Toast.makeText(getApplicationContext(), ""+selectedPoliceName, Toast.LENGTH_SHORT).show();

                Log.v("TAG","police name:"+ selectedPoliceName);
                Log.v("TAG","position: "+position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //GETTING THE USER DETAILS:
        //GET THE USER ID FROM THE PAGINATOR AND SERCH THE USER ID IN THE USER_DETAILS
        database1 = FirebaseDatabase.getInstance();
        myRef_user = database.getReference("user_details/");
        myRef_user.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                userDetails1 = dataSnapshot.getValue(UserDetails.class);
               // Log.v("tag","User Name: "+ dataSnapshot.child("name").getValue());
                Log.v("tag","UserDetails Name "+ userDetails1.getName());
            }

            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }@Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }@Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }@Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }

    //SEND THIS POST IN THE POLICE USER_DETAILS
        private void updatePoliceUser(String mSelectedPoliceName, String mPoliceNameKeyStr, String mPostUniqueKey) {
        DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("police_details/"+ mPoliceNameKeyStr +"/postHandle");
      //  DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference("police_details/"+ mPoliceNameKeyStr +"/postHandle");

        String newKey = mFirebaseDatabase.push().getKey();

        mFirebaseDatabase.child(newKey).setValue(mPostUniqueKey);
    }

/*    //SEND THIS POST IN THE Normal USER_DETAILS
    private void updatePoliceUser(String mSelectedPoliceName, String mPoliceNameKeyStr, String mPostUniqueKey) {
    DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("user_details/"+ mPoliceNameKeyStr +"/postHandle");
    //  DatabaseReference mDatabaseReference = mFirebaseDatabase.getReference("police_details/"+ mPoliceNameKeyStr +"/postHandle");

    String newKey = mFirebaseDatabase.push().getKey();

    mFirebaseDatabase.child(newKey).setValue(mPostUniqueKey);
}*/

    private void policeAssignChecking() {
      //  databasePoliceAssign = FirebaseDatabase.getInstance();

        databasePoliceAssign = FirebaseDatabase.getInstance();
        myRef_PoliceAssign = databasePoliceAssign.getReference("notifications/"+uniqueKey);

        Query policeInchargeCheck;
        policeInchargeCheck = myRef_PoliceAssign.orderByChild(uniqueKey);
        //myRef_PoliceAssign = databasePoliceAssign.getReference("notifications");

        Log.v("TAG","unique kEu"+uniqueKey);

        policeInchargeCheck.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                police_incharge = (String) dataSnapshot.child("police_incharge").getValue();
               // Toasty.success(getApplicationContext(),police_incharge,Toasty.LENGTH_SHORT).show();

                String policeAssign = "Police Assign: "+police_incharge;
                selectedPoliceNameTv.setText(policeAssign);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void ActionUnderTaking(View view) {
        resolvedButton.setEnabled(true);
        DatabaseReference mRef;

        FirebaseDatabase firebaseDatabase;
        mRef = FirebaseDatabase.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();

        //SEND THIS POST IN THE POLICE USER_DETAILS
        if(selectedPoliceName.equals("")){
            Log.v("TAG","Select the police officer");
        }else{
            Log.v("TAG","police assigned successfully");
            mRef.child("notifications/" + uniqueKey ).child("police_incharge").setValue(selectedPoliceName);
            updatePoliceUser(selectedPoliceName,policeNameKeyStr, uniqueKey);


        }

    }

    public void Resolved(View view) {
        Log.i("unique",""+uniqueKey);
    }

    public void mapClick(View view) {
       // Toasty.success(view.getContext(),"I said hell yea, it is right",Toasty.LENGTH_SHORT).show();

        Intent intentToMap = new Intent(this, MapsActivity.class);
        intentToMap.putExtra("latitude", latitude);
        intentToMap.putExtra("longitude", longitude);
        startActivity(intentToMap);

    }
}
