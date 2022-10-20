package com.example.medireminder.forms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medireminder.R;

public class CreateReminderActivity extends AppCompatActivity {

    EditText remName, medName, date, time;
    Button uploadBtn, submitBtn;
    ImageView vewImage;

    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);

        // register all the EditText fields with their IDs.
        remName = findViewById(R.id.nameET);
        medName = findViewById(R.id.medicineET);
        date = findViewById(R.id.editTextDate);
        time = findViewById(R.id.editTextTime);

        // register all the Buttons with their IDs.
        uploadBtn = findViewById(R.id.uploadImgBtn);
        submitBtn = findViewById(R.id.submitBtn);

        // register ImageView with its ID.
        vewImage = findViewById(R.id.imageView);


        // handle the SUBMIT button
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // store the returned value of the dedicated function which checks
                // whether the entered data is valid or if any fields are left blank.
                isAllFieldsChecked = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {
                    Toast.makeText(getApplicationContext(),
                                    "Submitted",
                                    Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        //handle the Upload image button
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    // function which checks all the text fields
    // are filled or not by the user.
    // when user clicks on the PROCEED button
    // this function is triggered.
    private boolean CheckAllFields() {
        if (remName.length() == 0) {
            remName.setError("This field is required");
            return false;
        }

        if (medName.length() == 0) {
            medName.setError("This field is required");
            return false;
        }

        if (date.length() == 0) {
            date.setError("Date is required");
            return false;
        }

        if (time.length() == 0) {
            time.setError("Time is required");
            return false;
        }
        // after all validation return true.
        return true;
    }

}