package com.example.medireminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.medireminder.ui.login.RegisterActivity;
import com.example.medireminder.ui.login.RegisterPhaActivity;

public class RegisterTypePage extends AppCompatActivity {


    public Button patientBtn;
    public Button pharmaBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_type_page);

        patientBtn = findViewById(R.id.patientBtn);
        patientBtn .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(RegisterTypePage.this, RegisterActivity.class));
            }
        });



        pharmaBtn = findViewById(R.id.phamaBtn);
        patientBtn .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(RegisterTypePage.this, RegisterPhaActivity.class));
            }
        });




    }


}