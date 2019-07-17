package com.example.lalthanpuia.trafficpoliceswipe4.signing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lalthanpuia.trafficpoliceswipe4.MainActivity;
import com.example.lalthanpuia.trafficpoliceswipe4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class GoogleSignUp extends AppCompatActivity {
    public FirebaseAuth mAuth;
    EditText citizenName, citizenEmail;
    Button signUpButton;
    UserDetails userDetails;
    String tempUserName ;
    TextView phoneNumberTV;

    String tempUserEmail ;
   // String tempUserPassword ;
    //String tempUserConfirmPassword ;
    String phoneNumber, uid;
    ProgressBar progressBar;

    DatabaseReference database;
    private ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_up);

        database = FirebaseDatabase.getInstance().getReference("user_details");

        citizenName = findViewById(R.id.citizenName);
        citizenEmail = findViewById(R.id.citizenEmail);
        phoneNumberTV = findViewById(R.id.phoneNumberTV);
     //   citizenPassword = findViewById(R.id.citizenPassword);
       // citizenConfirmPassword = findViewById(R.id.citizenConfirmPassword);


        signUpButton =findViewById(R.id.signUpButton);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();


       // String username = sharedPreferences.getString("username","");
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        uid = intent.getStringExtra("uid");

        phoneNumberTV.setText(phoneNumber);
        userDetails = new UserDetails();
        mAuth = FirebaseAuth.getInstance();

        //PREPOPULATE IF USER ALREADY EXIST
        showProgressDialog();


        prepopulateIfUserExist(uid);



    }

    @Override
    protected void onStart() {
        super.onStart();



        // Check if user is signed in (non-null) and update UI accordingly.
     //   FirebaseUser currentUser = mAuth.getCurrentUser();

      //  updateUI(currentUser);
    }


    public void GoogleSignUpClick(View view) {
        showProgressDialog();

        tempUserName = citizenName.getText().toString();
        tempUserEmail = citizenEmail.getText().toString();
     //   tempUserPassword = citizenPassword.getText().toString();
     //   tempUserConfirmPassword = citizenConfirmPassword.getText().toString();



        // 1. IF PASSWORD MATCH
        if(tempUserName.isEmpty() || tempUserEmail.isEmpty() ){
            Toasty.error(getApplicationContext(), "Fill everything", Toast.LENGTH_SHORT, true).show();
            dismissProgressDialog();
        } else {

                //GOTO STEP:2
                updateUI(uid);
                dismissProgressDialog();

                /*mAuth.createUserWithEmailAndPassword(tempUserEmail, tempUserPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    signUpButton.setEnabled(false);
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    dismissProgressDialog();
                                    updateUI(null);
                                }

                            }
                        });*/

            }


    }

    public void prepopulateIfUserExist(final String mUID) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("user_details/"+mUID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.i("TAG/SIGN up","datasnap:"+dataSnapshot);

                Log.i("TAG/SIGN up","valyue:"+dataSnapshot.getValue());
                String value = String.valueOf(dataSnapshot.getValue());
                Log.i("TAG/SIGN up","valyue in strg:"+value);

                if(value.matches("null")){

                    Log.i("TAG/SIGN up","IFF");

                    //NEW USER
                  dismissProgressDialog();
                }else {
                    Log.i("TAG/SIGN up","ESLSS");

                    //TODO : RECURRING USER
                    //DataSnapshot messageSnapshot = (DataSnapshot) dataSnapshot.getChildren();
                    //String name = (String) dataSnapshot.child(mUID).getValue();
                    //String message = (String) messageSnapshot.child("message").getValue();
                    Log.i("TAG", "datasnapshot+ " +dataSnapshot);


                    //  Log.i("TAG", "name+" +name );


                    // if(name == null){
                    Log.i("TAG", "if+");
                    //   }else {
                    UserDetails myUserDetails = dataSnapshot.getValue(UserDetails.class);
                    Log.i("TAG", "datasnap:"+myUserDetails.getName());

                    citizenName.setText(myUserDetails.getName());
                    //citizenPassword.setText(myUserDetails.getPassword());
                    //citizenConfirmPassword.setText(myUserDetails.getPassword());
                    citizenEmail.setText(myUserDetails.getEmail());
                    phoneNumberTV.setText(myUserDetails.getPhone());

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                    //intent.putExtra("role","citizen");
                    editor.putString("role",myUserDetails.getRole());
                    editor.putString("fullName" , myUserDetails.getName());
                    //Log.i("TAG","Name:"+tempUserName);

                    editor.putString("email" , myUserDetails.getEmail());
                    editor.putString("phoneNumber" , myUserDetails.getPhone());
                    //   Log.i("TAG","Phone:"+phoneNumber);

                    editor.commit();

                    startActivity(intent);
                    finish();
                }

                }
           // }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dismissProgressDialog();

    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(GoogleSignUp.this);
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
    private void  updateUI(String  uid) {

        if(uid!=null){
            //String newKey = database.push().getKey();

            String Uid = uid;
            editor.putString("userUniqueKey" ,Uid );
            editor.commit();

            Log.i("tag",""+tempUserName);

            userDetails = new UserDetails();
            userDetails.setName(tempUserName);
            userDetails.setEmail(tempUserEmail);
            //userDetails.setPassword(tempUserPassword);
            userDetails.setPhone(phoneNumber);
            userDetails.setRole("citizen");

            database.child(Uid).setValue(userDetails).addOnCompleteListener (new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    signUpButton.setEnabled(true);
                    citizenName .setText("");
                    citizenEmail .setText("");
                    dismissProgressDialog();
                    Toasty.success(getApplicationContext(),"Registration Successful",Toasty.LENGTH_SHORT).show();
                }
            });

            Intent intent = new Intent(this, MainActivity.class);

            //intent.putExtra("role","citizen");
            editor.putString("role", "citizen");
            editor.putString("fullName" , tempUserName);
            Log.i("TAG","Name:"+tempUserName);

            editor.putString("email" , tempUserEmail);
            editor.putString("phoneNumber" , phoneNumber);
            Log.i("TAG","Phone:"+phoneNumber);

            editor.commit();

            startActivity(intent);
            finish();
        }
    }
}
