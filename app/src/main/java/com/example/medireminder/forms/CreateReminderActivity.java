package com.example.medireminder.forms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medireminder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

public class CreateReminderActivity extends AppCompatActivity {

    EditText remName, medName, dateEdit, time;
    Button uploadBtn, submitBtn;
    ImageView vewImage;

    CalendarView expandableCalendarView;
    TimePicker expandableTimePicker;

    public static final int IMAGE_CODE = 200;

    // one boolean variable to check whether all the text fields
    // are filled by the user, properly or not.
    boolean isAllFieldsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);

        Intent intent=getIntent();
        String uid = intent.getStringExtra("patientUID");
        Log.d("CreateReminderActivity", "UID: " + uid);

        // register all the EditText fields with their IDs.
        remName = findViewById(R.id.nameET);
        medName = findViewById(R.id.medicineET);
        dateEdit = findViewById(R.id.editTextDate);
        time = findViewById(R.id.editTextTime);

        // register all the Buttons with their IDs.
        uploadBtn = findViewById(R.id.uploadImgBtn);
        submitBtn = findViewById(R.id.submitBtn);

        // register ImageView with its ID.
        vewImage = findViewById(R.id.imageView);
        expandableTimePicker = findViewById(R.id.expandable_time_picker);
        expandableTimePicker.setVisibility(View.GONE);
        expandableTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                time.setText(String.valueOf(hour) + ":" + String.valueOf(minute));
                expandableTimePicker.setVisibility(View.GONE);
            }
        });

        expandableCalendarView = findViewById(R.id.expandable_calendar_view);
        expandableCalendarView.setVisibility(View.GONE);
        expandableCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date) {
                dateEdit.setText(String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(date));
                expandableCalendarView.setVisibility(View.GONE);
            }
        });

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
                    String reminderName = remName.getText().toString();
                    String medicineName = medName.getText().toString();
                    String dateName = dateEdit.getText().toString();
                    String timeName = time.getText().toString();

                    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    userID = uid;
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    WriteBatch batch = db.batch();
                    //DocumentReference patient = db.collection("users").document(whateverpatientuseridis);

                    DocumentReference pharmacist = db.collection("users").document(userID).collection("Reminders").document();

                    Map<String, Object> reminderInfo = new HashMap<>();
                    reminderInfo.put("ReminderName", reminderName);
                    reminderInfo.put("MedicineName", medicineName);
                    reminderInfo.put("Date", dateName);
                    reminderInfo.put("Time", timeName);

                    batch.set(pharmacist, reminderInfo);
                    //batch.set(patient, reminderInfo);
                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(),
                                            "Submitted",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }
        });

        //handle the Upload image button
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableTimePicker.setVisibility(expandableTimePicker.isShown() ? View.GONE : View.VISIBLE);
            }
        });

        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableCalendarView.setVisibility(expandableCalendarView.isShown() ? View.GONE : View.VISIBLE);
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

        if (dateEdit.length() == 0) {
            dateEdit.setError("Date is required");
            return false;
        }

        if (time.length() == 0) {
            time.setError("Time is required");
            return false;
        }
        // after all validation return true.
        return true;
    }

    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), IMAGE_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == IMAGE_CODE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    vewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }
}