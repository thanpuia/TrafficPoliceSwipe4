package com.example.lalthanpuia.trafficpoliceswipe4.signing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.lalthanpuia.trafficpoliceswipe4.MainActivity;
import com.example.lalthanpuia.trafficpoliceswipe4.R;
import com.example.lalthanpuia.trafficpoliceswipe4.police.PoliceIncharge;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class GoogleSignIn extends AppCompatActivity {

    public static FirebaseAuth mAuth;
    EditText userEmail,userPassword;
    public static FirebaseUser currentUser;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static ArrayList<String> roleAssign;
    ProgressDialog progressDialog;
    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);

        mAuth = FirebaseAuth.getInstance();
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);

    }

    public void signUpClick(View view) {

        Intent registrationIntent = new Intent(this, FireBasePhoneAuth.class);
        startActivity(registrationIntent);

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void SignInButtonClick(View view) {

        showProgressDialog();
        String mEmail = userEmail.getText().toString();
        String mPassword = userPassword.getText().toString();
        if(mEmail.equals("") || mPassword.equals("")){
            dismissProgressDialog();
            Toasty.error(this,"Fill Up",Toasty.LENGTH_SHORT).show();

        }else {

            Log.d("TAG",userEmail+" "+ userPassword);

      /*      mAuth.signInWithEmailAndPassword(mEmail, mPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                Toasty.error(getApplicationContext(),"Email not registered",Toasty.LENGTH_SHORT).show();
                                dismissProgressDialog();
                                updateUI(null);
                            }
                        }
                    });*/
        }
    }

    private void updateUI(final FirebaseUser currentUser) {
       //final long role= 0;
        if(currentUser!=null){

            //FIRST CHECK THE SHARED PREFERENCESS
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            editor = sharedPreferences.edit();

            //PUT THE FIREBASE USER DATA IN SHAREDPREFERENCE
            editor.putString("Uid",currentUser.getUid());

            editor.commit();

            String role = sharedPreferences.getString("role","");

            if(role.equals("")){
                //role base view configurere
                final FirebaseDatabase databaseReference;
                DatabaseReference firebaseDatabase;
                databaseReference = FirebaseDatabase.getInstance();
                firebaseDatabase=databaseReference.getReference("user_details");

                //changing the view according to the role
                firebaseDatabase.addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String email = (String) dataSnapshot.child("email").getValue();

                        if (email.equals(currentUser.getEmail())){
                            String role = (String) dataSnapshot.child("role").getValue();

                            String userKey = dataSnapshot.getKey();
                            editor.putString("role", role);
                            editor.commit();

                            Log.v("TAG","tole "+role);
                            goToMainMenuWithTheRole(role,email);
                            return;
                        }
                    }
                    @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }@Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }@Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }@Override public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
            }
            else{
                goToMainMenuWithTheRole(role,"");
            }
        }

    }
    public void goToMainMenuWithTheRole (String role, String email){
//        Toasty.success(this,""+currentUser.getEmail()+currentUser.getPhoneNumber(),Toasty.LENGTH_SHORT).show();
        //Intent intent = new Intent(this,AfterSignIn.class);

        //IF THE ROLE IS NOT POLICE OR NOT CHECK . IF POLICE GO TO THE POLICE ACTIVITY ELSE GO TO THE MAIN MENU
        if(role.equals("police")){

            //Download all the assign
            downloadTheAssignReports(email);
            dismissProgressDialog();

            Intent policeIntent = new Intent(this, PoliceIncharge.class);
            startActivity(policeIntent);
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            dismissProgressDialog();

            intent.putExtra("role",role);
            startActivity(intent);
        }

    }

    public void downloadTheAssignReports(final String mEmail){

        final FirebaseDatabase databaseReference;
        DatabaseReference firebaseDatabase;
        databaseReference = FirebaseDatabase.getInstance();
        firebaseDatabase=databaseReference.getReference("police_details");

        //changing the view according to the role
        firebaseDatabase.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String email = (String) dataSnapshot.child("email").getValue();

                if (email.equals(mEmail)){

                    roleAssign = (ArrayList<String>) dataSnapshot.child("postHandle").getValue();
                    // String role = (String) dataSnapshot.child("role").getValue();

                    //String userKey = dataSnapshot.getKey();
                    //sharedPreferences.edit().putString("role", role).apply();

                    //Log.v("TAG","tole "+role);
                   // goToMainMenuWithTheRole(role);
                    return;
                }
            }
            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }@Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }@Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }@Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }

    //TODO: Download the userdata
    public UserDetails userDetailsDownload(String Uid) {

        userDetails = new UserDetails();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user_details/"+Uid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userDetails = dataSnapshot.getValue(UserDetails.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return  userDetails;
    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(GoogleSignIn.this);
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
