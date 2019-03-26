package com.example.lalthanpuia.trafficpoliceswipe4.signing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.lalthanpuia.trafficpoliceswipe4.R;

import es.dmoral.toasty.Toasty;

public class AfterSignIn extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sign_in);

    }

    public void signOutClick(View view) {
        try {

            GoogleSignIn.mAuth.signOut();
            Intent intent = new Intent(this, GoogleSignIn.class);
            startActivity(intent);

        } catch (Exception e) {
            Toasty.error(this,"Somethings wrong");

            e.printStackTrace();
        }
    }
}
