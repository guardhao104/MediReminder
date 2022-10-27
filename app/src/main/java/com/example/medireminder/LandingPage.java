package com.example.medireminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medireminder.ui.login.LoginActivity;
import com.example.medireminder.ui.login.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.List;
import java.util.Objects;

public class LandingPage extends AppCompatActivity {
    private TextView userName;
    private TextView useremail;
    private Button logOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //here toolbar is your id in xml
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Landing Page");

        userName = findViewById(R.id.userName);
        useremail = findViewById(R.id.userEmail);
        logOutBtn = findViewById(R.id.logOutButton);
        //useremail.setText(user[2]);
        //getSupportActionBar().setTitle(user.getUid());
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(LandingPage.this, LoginActivity.class));
            }
        });


        DocumentReference docRef = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());

// Source can be CACHE, SERVER, or DEFAULT.
        Source source = Source.CACHE;

// Get the document, forcing the SDK to use the offline cache
        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot document = task.getResult();
                    //List<User> types = document.toObjects(User.class);
                    Toast.makeText(LandingPage.this, "Hello!", Toast.LENGTH_LONG).show();
                    //User user = document.toObject(User.class);
                    //userName.setText(user.getFullName());

                    System.out.println(Objects.requireNonNull(document.getData()).get("FullName"));
                    System.out.println(user);
                    userName.setText(Objects.requireNonNull(document.getData()).get("FullName").toString());
                    useremail.setText(Objects.requireNonNull(document.getData()).get("email").toString());
                    //System.out.println(document.getData().get('FullName'));
//                    Log.d(TAG, "Cached document data: " + document.getData());
                } else {
                    Toast.makeText(LandingPage.this, "Retrieve user error", Toast.LENGTH_LONG).show();
//                    Log.d(TAG, "Cached get failed: ", task.getException());
                }
            }
        });

    }
}