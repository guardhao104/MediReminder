package com.example.medireminder.patient_info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.medireminder.R;
import com.example.medireminder.forms.CreateReminderActivity;
import com.example.medireminder.patient_info.patient_details.PatientDetailsActivity;
import com.example.medireminder.patient_list.Patient;
import com.example.medireminder.patient_list.PatientListActivity;
import com.example.medireminder.patient_list.PatientListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PatientInfoListActivity extends AppCompatActivity {
    private TextView patientName;
    private List<PatientInfo> patientsInfoList = new ArrayList<PatientInfo>();
    private ListView listView = null;
    private Button addReminderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info_list);

        Intent intent=getIntent();
        String name = intent.getStringExtra("patientName");

        patientName =(TextView)findViewById(R.id.patient_name);
        patientName.setText("Patient Name: " + name);

        String uid = intent.getStringExtra("patientUID");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(uid);

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

                            PatientInfo patientInfo = new PatientInfo(reminderName, reminderName, reminderMedName, reminderDate + " " + reminderTime);
                            patientsInfoList.add(patientInfo);

                            PatientInfoListAdapter adapter = new PatientInfoListAdapter(PatientInfoListActivity.this, R.layout.patient_info_item, patientsInfoList);
                            listView = (ListView) findViewById(R.id.list_view_items);
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

        // nav to CreateReminderActivity
        addReminderBtn = (Button) findViewById(R.id.addReminderButton);

        addReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = intent.getStringExtra("patientUID");
                Log.d("PatientInfoListActivity", "UID: " + uid);

                Intent intent = new Intent(PatientInfoListActivity.this, CreateReminderActivity.class);
                intent.putExtra("patientUID", uid);
                startActivity(intent);
            }
        });

    }
}