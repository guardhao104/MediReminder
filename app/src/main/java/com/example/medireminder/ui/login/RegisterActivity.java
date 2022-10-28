package com.example.medireminder.ui.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medireminder.LandingPage;
import com.example.medireminder.R;
import com.example.medireminder.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView banner, registerUser;
    private EditText editTextFullName, editTextAge, editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextAge = (EditText) findViewById(R.id.age);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;

        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();

        if(fullName.isEmpty()){
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
        }

        if(!Pattern.matches("^[A-Z][a-zA-Z .]*[ ][A-Z][a-zA-Z .]*$", fullName)){
            editTextFullName.setError("Full name format is wrong!");
            editTextFullName.requestFocus();
        }

        if(age.isEmpty()){
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide a valid email!");
            editTextEmail.requestFocus();
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
        }

        if(password.length() < 6){
            editTextPassword.setError("Min password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }



// Add a new document with a generated ID

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullName, age, email, true);

                            // Create a new user with a first and last name
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                String userId;
                                                Toast.makeText(RegisterActivity.this, "User successfully registered", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.VISIBLE);
                                                userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                System.out.println(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                addToDatabase(userId, user);
                                                startActivity(new Intent(RegisterActivity.this, LandingPage.class));
                                            }else{
                                                Toast.makeText(RegisterActivity.this, "There has been an error", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);

                                            }
                                        }
                                    });

                        }else{
                            Toast.makeText(RegisterActivity.this, "There has been an error before db creation", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void addToDatabase(String uid, User user){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("FullName", user.fullName);
        userInfo.put("Age", user.age);
        userInfo.put("email", user.email);
        db.collection("users")
                .document(uid)
                .set(userInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisterActivity.this,"Register sucess",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}