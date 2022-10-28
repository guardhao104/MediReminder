package com.example.medireminder.patient_info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.medireminder.R;
import com.example.medireminder.patient_info.patient_details.PatientDetailsActivity;
import com.example.medireminder.patient_list.Patient;
import com.example.medireminder.patient_list.PatientListActivity;
import com.example.medireminder.patient_list.PatientListAdapter;

import java.util.ArrayList;
import java.util.List;

public class PatientInfoListActivity extends AppCompatActivity {
    private TextView patientName;
    private List<PatientInfo> patientsInfoList = new ArrayList<PatientInfo>();
    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info_list);

        Intent intent=getIntent();
        String name = intent.getStringExtra("patientName");

        patientName =(TextView)findViewById(R.id.patient_name);
        patientName.setText("Patient Name: " + name);

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