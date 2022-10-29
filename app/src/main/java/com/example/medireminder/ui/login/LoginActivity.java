package com.example.medireminder.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import com.example.medireminder.LandingPage;
import com.example.medireminder.R;
import com.example.medireminder.RegisterTypePage;

/**import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;*/

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register;
    private TextView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);
        btn = (TextView) findViewById(R.id.landingButton);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, LandingPage.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.register:
                System.out.println("TEST");
                startActivity(new Intent(LoginActivity.this, RegisterTypePage.class));
                break;
        }
    }

//    public void toLandingPage(View view){
//
//    }

}
