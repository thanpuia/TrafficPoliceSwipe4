package com.example.lalthanpuia.trafficpoliceswipe4;

import android.content.Intent;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    public static final String FIREBASE_USERNAME = "thanpuia46@gmail.com";
    public static final String FIREBASE_PASSWORD = "Lorenzo@99";
    boolean Status = true;
    boolean unique ;

    BottomNavigationView bottomNavigationView;
    BottomNavigationViewHelper bottomNavigationViewHelper;

    private static final int CAMERA_REQUEST = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            ItemOneFragment itemOneFragment = null;
            ItemTwoFragment itemTwoFragment = null;
            ItemThreeFragment itemThreeFragment = null;
            ItemFourFragment itemFourFragment = null;

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()){
                case R.id.action_item1:
                    itemOneFragment = ItemOneFragment.newInstance();
                    transaction.replace(R.id.frame_layout,itemOneFragment);
                    break;
                case R.id.action_item2:
                    itemTwoFragment = ItemTwoFragment.newInstance();
                    transaction.replace(R.id.frame_layout,itemTwoFragment);
                    break;
                case R.id.action_item3:
                  /*  itemThreeFragment = ItemThreeFragment.newInstance();
                    transaction.replace(R.id.frame_layout,itemThreeFragment);*/
                  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  startActivityForResult(intent, CAMERA_REQUEST);

                    break;
                case R.id.action_item4:
                    itemFourFragment = ItemFourFragment.newInstance();
                    transaction.replace(R.id.frame_layout,itemFourFragment);
                    break;

            }

            transaction.commit();

            return true;

        }
    });



    //MANUALLY DISPLAY THE FIRST FRAGMENT ONLY OE TIME
    FragmentTransaction tempTtransaction = getSupportFragmentManager().beginTransaction();
        tempTtransaction.replace(R.id.frame_layout,ItemOneFragment.newInstance());
        tempTtransaction.commit();
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
    }

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
}
