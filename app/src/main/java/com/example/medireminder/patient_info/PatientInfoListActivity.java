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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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

//        DocumentReference pharmacist = db.collection("users").document(userID).collection("Reminders").document();

//        db.collection("users")



        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Patient> searchResultList = new ArrayList<Patient>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("PatientInfoListActivity", document.getId() + " => " + document.getData());
                                Log.d("PatientInfoListActivity", "thishishsihs");
                            }
                        } else {
                            Log.w("PatientListActivity", "Error getting documents.", task.getException());
                        }
                    }
                });


        initpatientInfoList();

        PatientInfoListAdapter adapter = new PatientInfoListAdapter(PatientInfoListActivity.this, R.layout.patient_info_item, patientsInfoList);
        listView = (ListView) findViewById(R.id.list_view_items);
        listView.setAdapter(adapter);

        // press listview item to open a new activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PatientInfo patient = patientsInfoList.get(i);

                Intent intent = new Intent(PatientInfoListActivity.this, PatientDetailsActivity.class);

                startActivity(intent);
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

    private void initpatientInfoList() {
        PatientInfo patientInfo = new PatientInfo("a", "aa", "2000,10,31");
        patientsInfoList.add(patientInfo);
        patientsInfoList.add(patientInfo);
        patientsInfoList.add(patientInfo);
        patientsInfoList.add(patientInfo);
        patientsInfoList.add(patientInfo);
        patientsInfoList.add(patientInfo);
        patientsInfoList.add(patientInfo);
        patientsInfoList.add(patientInfo);
        patientsInfoList.add(patientInfo);
        patientsInfoList.add(patientInfo);
    }
}