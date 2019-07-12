package com.example.lalthanpuia.trafficpoliceswipe4;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.lalthanpuia.trafficpoliceswipe4.entity.PostIds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingleUserFeedNewActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<PostIds> postId;
    SharedPreferences sharedPreferences;

    String shared_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user_feed_new);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        shared_uid = sharedPreferences.getString("uid","");

        //postId = new PostIds();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user_details/"+shared_uid+"/post_id/");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snap : dataSnapshot.getChildren()){

                    postId = (ArrayList<PostIds>) snap.getValue();

                }
               // postId = dataSnapshot.getValue(PostIds.class);
               // Log.i("TAG","single User feed:"+postId.getPost_id().get(0));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
