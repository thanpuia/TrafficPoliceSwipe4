package com.example.lalthanpuia.trafficpoliceswipe4.police;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.lalthanpuia.trafficpoliceswipe4.MainActivity;
import com.example.lalthanpuia.trafficpoliceswipe4.R;
import com.example.lalthanpuia.trafficpoliceswipe4.adapters.SingleUserAdapter;
import com.example.lalthanpuia.trafficpoliceswipe4.entity.NotificationEntity;
import com.example.lalthanpuia.trafficpoliceswipe4.signing.GoogleSignIn;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class PoliceIncharge extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    SharedPreferences sharedPreferences;

    String shared_uid;
    public static List<String> post_assigned;
    ProgressDialog progressDialog;

    NotificationEntity notificationEntity;
    ArrayList<NotificationEntity> arrayLists;
    RecyclerView recyclerView;
    int i;

    static PoliceAdapter singleUserAdapterInPolice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_incharge);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        shared_uid = sharedPreferences.getString("uid","");

        notificationEntity = new NotificationEntity();
        arrayLists = new ArrayList<>();
        //postId = new PostIds();

        recyclerView = findViewById(R.id.recyclerViewPoliceView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("user_details/"+shared_uid+"/post_assigned/");

        showProgressDialog();

        try{//IF THE USER HAS POST ASSIGN OR NOT

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Map<String, String> td = (HashMap<String,String>) dataSnapshot.getValue();

                    try{

                        if(td.values()!=null) {
                            td.values();
                            Log.i("TAG",";"+td.values());
                            String s = String.valueOf(td.values());

                            //remove the bar barket
                            s = s.substring(1);//remove the first bracet
                            s = s.substring(0,s.length() - 1);

                            Log.i("TAG"," xx ");
                            //remove spaces
                            try{
                                s = s.replaceAll("\\s+","");
                                post_assigned = Arrays.asList(s.split(","));
                                Log.i("TAG","try");

                            }catch (Exception e){
                                post_assigned.add(s);
                                Log.i("TAG","String exceptiopn"); }

                            //Log.i("TAG",";;;"+postId.get(1));
                            downloadPolicePostHandle(post_assigned);

                        }
                    }catch (Exception e){dismissProgressDialog();}
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){}


    }

    private void downloadPolicePostHandle(final List<String> post_assigned) {

        Log.i("TAG/SingleUserFeed","single:"+post_assigned.size());
        final Gson gson = new Gson();

        for(i=0;i<post_assigned.size();i++){
            Log.i("TAG/SingleUserFeed","single:"+post_assigned.get(i));

            myRef = firebaseDatabase.getReference("notifications/"+post_assigned.get(i));
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try{

                        Log.i("TAG","asf:"+dataSnapshot);
                        notificationEntity = dataSnapshot.getValue(NotificationEntity.class);
                        Log.i("TAG","not:"+notificationEntity.getSortkey());
                        arrayLists.add(notificationEntity);

                        if(i==post_assigned.size()){
                            testFunction();
                        }
                        dismissProgressDialog();
                    }catch (Exception e){  Log.i("TAG","Exception trigger");
                    }

                    //notificationEntity
                    // notificationEntity = gson.fromJson((JsonElement) dataSnapshot.getValue(),NotificationEntity.class);

                    //  Log.i("TAG","single  :"+str);
                    //  notificationEntity= (NotificationEntity)dataSnapshot.getValue();


                    // Player p = g.fromJson(jsonString, Player.class)
                }

                @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
            });
        }


    }

    private void testFunction() {
        singleUserAdapterInPolice = new PoliceAdapter(arrayLists, "verifier");
        recyclerView.setAdapter(singleUserAdapterInPolice);
        dismissProgressDialog();
    }

    public static void changeStatus(String postKey){
        //since we cannot call a normal variable from a static method
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("notifications/"+postKey+ "/status" );

        myRef.setValue("resolved");

    }


    public void showProgressDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }

        progressDialog.setMessage("If you want something, ask!");
        progressDialog.show();

    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
