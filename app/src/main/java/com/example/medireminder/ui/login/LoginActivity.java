package com.example.medireminder.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medireminder.LandingPage;
import com.example.medireminder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private Button signInBtn;
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private  EditText editTextPassword;
    private String strEmail;
    private  String strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // login
        mAuth = FirebaseAuth.getInstance();
        signInBtn = (Button) findViewById(R.id.signIn);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        // register
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    private void signIn(String email, String password) {
        Log.d("LoginActivity", "email: " + email);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LoginActivity", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, LandingPage.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LoginActivity", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.register:
                System.out.println("TEST");
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

//    public void toLandingPage(View view){
//
//    }

}
