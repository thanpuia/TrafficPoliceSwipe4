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
import android.widget.Toast;

import com.example.lalthanpuia.trafficpoliceswipe4.MainActivity;
import com.example.lalthanpuia.trafficpoliceswipe4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class GoogleSignUp extends AppCompatActivity {
    public FirebaseAuth mAuth;
    EditText citizenName, citizenPassword, citizenEmail, citizenConfirmPassword;
    Button signUpButton;
    UserDetails userDetails;
    String tempUserName ;

    String tempUserEmail ;
    String tempUserPassword ;
    String tempUserConfirmPassword ;
    String phoneNumber;
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
        citizenPassword = findViewById(R.id.citizenPassword);
        citizenConfirmPassword = findViewById(R.id.citizenConfirmPassword);

        signUpButton =findViewById(R.id.signUpButton);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();


       // String username = sharedPreferences.getString("username","");

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");

        userDetails = new UserDetails();
        mAuth = FirebaseAuth.getInstance();

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
        tempUserPassword = citizenPassword.getText().toString();
        tempUserConfirmPassword = citizenConfirmPassword.getText().toString();


        editor.putString("fullName" , tempUserName);
        editor.putString("email" , tempUserEmail);
        editor.putString("phoneNumber" , phoneNumber);
        editor.commit();

        // 1. IF PASSWORD MATCH
        if(tempUserName.isEmpty() || tempUserEmail.isEmpty() || tempUserPassword.isEmpty() || tempUserConfirmPassword.isEmpty() ){
            Toasty.error(getApplicationContext(), "Fill everything", Toast.LENGTH_SHORT, true).show();
            dismissProgressDialog();
        } else {
            if (tempUserPassword.equals(tempUserConfirmPassword)){
                //GOTO STEP:2
                mAuth.createUserWithEmailAndPassword(tempUserEmail, tempUserPassword)
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
                        });

            }else{
                //error pasword mismatch
                dismissProgressDialog();
                Toasty.error(getApplicationContext(), "Password Mismatch", Toast.LENGTH_LONG, true).show();
            }
        }

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
    private void updateUI(FirebaseUser currentUser) {

        if(currentUser!=null){
            String newKey = database.push().getKey();

            editor.putString("userUniqueKey" ,newKey );
            editor.commit();

            Log.i("tag",""+citizenName.getText());

            userDetails = new UserDetails();
            userDetails.setName(tempUserName);

            userDetails.setEmail(tempUserEmail);
            userDetails.setPassword(tempUserPassword);
            userDetails.setPhone(phoneNumber);


            database.child(newKey).setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    signUpButton.setEnabled(true);
                    citizenName .setText("");
                    citizenEmail .setText("");
                    citizenPassword .setText("");
                    citizenConfirmPassword .setText("");
                    dismissProgressDialog();
                    Toasty.success(getApplicationContext(),"Registration Successful",Toasty.LENGTH_SHORT).show();
                }
            });

            Intent intent = new Intent(this, GoogleSignIn.class);
            startActivity(intent);
        }
    }
}
