package com.example.lalthanpuia.trafficpoliceswipe4.signing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.lalthanpuia.trafficpoliceswipe4.MainActivity;
import com.example.lalthanpuia.trafficpoliceswipe4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;

public class GoogleSignIn extends AppCompatActivity {

    public static FirebaseAuth mAuth;
    EditText userEmail,userPassword;

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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }



    public void SignInButtonClick(View view) {
        String mEmail = userEmail.getText().toString();
        String mPassword = userPassword.getText().toString();
        if(mEmail.equals("") || mPassword.equals("")){
            Toasty.error(this,"Fill Up",Toasty.LENGTH_SHORT).show();
        }else {

            Log.d("TAG",userEmail+" "+ userPassword);

            mAuth.signInWithEmailAndPassword(mEmail, mPassword)
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
                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }




    }

    private void updateUI(FirebaseUser currentUser) {

        if(currentUser!=null){
            Toasty.success(this,""+currentUser.getEmail()+currentUser.getPhoneNumber(),Toasty.LENGTH_SHORT).show();
            //Intent intent = new Intent(this,AfterSignIn.class);
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        }


    }
}
