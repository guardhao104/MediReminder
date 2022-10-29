package com.example.medireminder.patient.forms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.medireminder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewReminderActivity extends AppCompatActivity {
    private TextView patientName;
    private List<Reminder> ReminderList = new ArrayList<Reminder>();
    private ListView listView = null;
    private Button addReminderBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminder);

        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userUid);

        // get data from database and create list to show user's reminder
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        List<Map<String, Object>> reminderList = new ArrayList<Map<String, Object>>();
                        reminderList = (List<Map<String, Object>>) document.getData().get("Reminder");

                        for(int i = 0; i < reminderList.size(); i++) {
                            Map<String, Object> reminder = reminderList.get(i);
                            String reminderName = (String) reminder.get("ReminderName");
                            String reminderMedName = (String) reminder.get("MedicineName");
                            String reminderTime = (String) reminder.get("Time");
                            String reminderDate = (String) reminder.get("Date");

                            Reminder rem = new Reminder(reminderName, reminderName, reminderMedName, reminderDate + " " + reminderTime);
                            ReminderList.add(rem);

                            ReminderAdapter adapter = new ReminderAdapter(ViewReminderActivity.this, R.layout.reminder_item, ReminderList);
                            listView = (ListView) findViewById(R.id.reminderListView);
                            listView.setAdapter(adapter);
                        }
                    } else {
                        Log.d("PatientInfoListActivity", "No such document");
                    }
                } else {
                    Log.d("PatientInfoListActivity", "get failed with ", task.getException());
                }
            }
        });
    }
}