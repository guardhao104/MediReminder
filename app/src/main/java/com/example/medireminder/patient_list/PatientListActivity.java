package com.example.medireminder.patient_list;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.medireminder.R;
import com.example.medireminder.patient_info.PatientInfoListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientListActivity extends AppCompatActivity {
    private List<Patient> patientsList = new ArrayList<Patient>();
    private ListView listView = null;
    private SearchView searchView;
    private String searchStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // get data from database and insert into listview
        db.collection("users")
                .whereEqualTo("IsPatient", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("PatientListActivity", document.getId() + " => " + document.getData());
                                String fullname = document.getData().get("FullName").toString();
                                String age = document.getData().get("Age").toString();
                                String email = document.getData().get("email").toString();
                                String uid = document.getId();


                                Patient patient = new Patient(fullname, age, email, uid);
                                patientsList.add(patient);

                                PatientListAdapter adapter = new PatientListAdapter(PatientListActivity.this, R.layout.patient_item, patientsList);
                                listView = (ListView) findViewById(R.id.list_view_items);
                                listView.setAdapter(adapter);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Patient patient = patientsList.get(i);

                                        Intent intent = new Intent(PatientListActivity.this, PatientInfoListActivity.class);
                                        intent.putExtra("patientName", patient.getName());
                                        intent.putExtra("patientAge", patient.getAge());
                                        intent.putExtra("patientEmail", patient.getEmail());
                                        intent.putExtra("patientUID", patient.getUID());

                                        Log.d("PatientListActivity", "Test: " + patient.getUID());

                                        startActivity(intent);
                                    }
                                });
//                                Log.d("PatientListActivity", "Test: " + fullname + " : " + age + " : " + email);
                            }
                        } else {
                            Log.w("PatientListActivity", "Error getting documents.", task.getException());
                        }
                    }
                });


        // search user name then claen listview and insert search result into the listview
        searchView = (SearchView) findViewById(R.id.list_view_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {

                db.collection("users")
                        .whereEqualTo("IsPatient", true)
                        .whereGreaterThanOrEqualTo("FullName", s)
                        .whereLessThanOrEqualTo("FullName",s+ '\uf8ff')
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    List<Patient> searchResultList = new ArrayList<Patient>();

                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        String fullname = document.getData().get("FullName").toString();
                                        String age = document.getData().get("Age").toString();
                                        String email = document.getData().get("email").toString();

                                        Patient patient = new Patient(fullname, age, email, document.getId());
                                        searchResultList.add(patient);

                                        PatientListAdapter adapter = new PatientListAdapter(PatientListActivity.this, R.layout.patient_item, searchResultList);
                                        listView = (ListView) findViewById(R.id.list_view_items);
                                        listView.setAdapter(null);
                                        listView.setAdapter(adapter);

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                Patient patient = patientsList.get(i);

                                                Intent intent = new Intent(PatientListActivity.this, PatientInfoListActivity.class);
                                                intent.putExtra("patientName", patient.getName());
                                                intent.putExtra("patientAge", patient.getAge());
                                                intent.putExtra("patientEmail", patient.getEmail());
                                                intent.putExtra("patientUID", patient.getUID());

                                                startActivity(intent);
                                            }
                                        });

//                                        Log.d("PatientListActivity", document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.w("PatientListActivity", "Error getting documents.", task.getException());
                                }
                            }
                        });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Patient> redisplayltList = new ArrayList<Patient>();

                if(s.length() == 0)
                {
                    db.collection("users")
                            .whereEqualTo("IsPatient", true)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {

                                            String fullname = document.getData().get("FullName").toString();
                                            String age = document.getData().get("Age").toString();
                                            String email = document.getData().get("email").toString();

                                            Patient patient = new Patient(fullname, age, email, document.getId());
                                            redisplayltList.add(patient);

                                            PatientListAdapter adapter = new PatientListAdapter(PatientListActivity.this, R.layout.patient_item, redisplayltList);
                                            listView = (ListView) findViewById(R.id.list_view_items);
                                            listView.setAdapter(adapter);

                                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                    Patient patient = redisplayltList.get(i);

                                                    Intent intent = new Intent(PatientListActivity.this, PatientInfoListActivity.class);
                                                    intent.putExtra("patientName", patient.getName());
                                                    intent.putExtra("patientAge", patient.getAge());
                                                    intent.putExtra("patientEmail", patient.getEmail());
                                                    intent.putExtra("patientUID", patient.getUID());

                                                    startActivity(intent);
                                                }
                                            });

                                            Log.d("PatientListActivity", "Test: " + fullname + " : " + age + " : " + email);
                                        }
                                    } else {
                                        Log.w("PatientListActivity", "Error getting documents.", task.getException());
                                    }
                                }
                            });
                }

                return false;
            }
        });
    }
}