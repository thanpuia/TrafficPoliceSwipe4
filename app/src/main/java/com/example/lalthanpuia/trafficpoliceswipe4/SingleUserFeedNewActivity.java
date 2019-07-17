package com.example.lalthanpuia.trafficpoliceswipe4;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.lalthanpuia.trafficpoliceswipe4.adapters.SingleUserAdapter;
import com.example.lalthanpuia.trafficpoliceswipe4.entity.NotificationEntity;
import com.example.lalthanpuia.trafficpoliceswipe4.entity.PostIds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleUserFeedNewActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef, myRef2;
    List<String> postId;
    SharedPreferences sharedPreferences;

    String shared_uid;
    NotificationEntity notificationEntity;
    ArrayList<NotificationEntity> arrayLists;
    RecyclerView recyclerView;
    int i;

    SingleUserAdapter singleUserAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user_feed_new);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        shared_uid = sharedPreferences.getString("uid","");

        notificationEntity = new NotificationEntity();
        arrayLists = new ArrayList<>();
        //postId = new PostIds();

        recyclerView = findViewById(R.id.recyclerViewSingleUserFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user_details/"+shared_uid+"/post_id/");

       // postId = n();

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
                            postId = Arrays.asList(s.split(","));
                            Log.i("TAG","try");

                        }catch (Exception e){
                            postId.add(s);
                            Log.i("TAG","String exceptiopn"); }

                        /*EXAMPLE:
                        String text = "iMac";
                        String withoutFirstCharacter = text.substring(1); // index starts at zero
                        String withoutLastCharacter = text.substring(0, text.length() - 1);

                        st = st.replaceAll("\\s+","")*/

                        //Log.i("TAG",";;;"+postId.get(1));
                        downloadSingleUserPost(postId);

                    }
                }catch (Exception e){}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void downloadSingleUserPost(final List<String> postId) {


        Log.i("TAG/SingleUserFeed","single:"+postId.size());
        final Gson gson = new Gson();

        for(i=0;i<postId.size();i++){
            Log.i("TAG/SingleUserFeed","single:"+postId.get(i));

            myRef2 = database.getReference("notifications/"+postId.get(i));
            myRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try{

                        Log.i("TAG","asf:"+dataSnapshot);
                        notificationEntity = dataSnapshot.getValue(NotificationEntity.class);
                        Log.i("TAG","not:"+notificationEntity.getSortkey());

                        arrayLists.add(notificationEntity);

                        if(i==postId.size()){
                            testFunction();
                        }

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
        singleUserAdapter = new SingleUserAdapter(arrayLists);
        recyclerView.setAdapter(singleUserAdapter);
    }


    public void testClick(View view) {
        singleUserAdapter = new SingleUserAdapter(arrayLists);
        recyclerView.setAdapter(singleUserAdapter);

        Log.i("TAG",arrayLists.get(2).getDate());

    }
}
