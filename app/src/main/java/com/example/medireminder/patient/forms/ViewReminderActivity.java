package com.example.medireminder.patient.forms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medireminder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ViewReminderActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView patientName;
    private List<Reminder> ReminderList = new ArrayList<Reminder>();
    private ListView listView = null;
    private Button addReminderBtn;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private int notificationId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminder);

        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userUid);

        Button button = (Button) findViewById(R.id.cReminder);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                int rh = 0;
                int rm = 0;
                String rmessage = "";
                String reminderName = "";
                for(int i = 0; i < ReminderList.size(); i++)
                {
                    String str = ReminderList.get(i).getReminderName();
                    String[] strarray = str.split("[ ]");

                    String[] time = strarray[1].split(":");

                    String hour = time[0];
                    String min = time[1];

                    rh = Integer.parseInt(hour);
                    rm = Integer.parseInt(min);
                    rmessage = ReminderList.get(i).getReminderTime();
                    reminderName = ReminderList.get(i).getMedicineName();
//                    Log.d("ViewReminderActivity", hour + " : " + min);
//                    Log.d("ViewReminderActivity", ReminderList.get(i).getReminderTime()); // mname
//                    Log.d("ViewReminderActivity", ReminderList.get(i).getReminderName()); // time
//                    Log.d("ViewReminderActivity", ReminderList.get(i).getMedicineName()); // rname

//                    Intent intent = new Intent(ViewReminderActivity.this, AlarmReceive.class);
//                    intent.putExtra("notificationId", notificationId);
//                    intent.putExtra("message", ReminderList.get(i).getReminderTime());
//                    intent.putExtra("reminderName", ReminderList.get(i).getMedicineName());
//
//                    PendingIntent alarmIntent = PendingIntent.getBroadcast(
//                            ViewReminderActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
//                    );

//                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//
//                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                        OffsetTime offset = OffsetTime.now();
//
//                        int h = offset.getHour();
//                        int m = offset.getMinute();
//                        int s = offset.getSecond();
//
//                        int second = (int) s + 5;
//
//
//                        h =Integer.parseInt(hour);
//                        m =Integer.parseInt(min);
//
//                        Log.d("ViewReminderActivity", h + " : " + m);
//
//                        Calendar startTime = Calendar.getInstance();
//                        startTime.set(Calendar.HOUR_OF_DAY, h);
//                        startTime.set(Calendar.MINUTE, m);
//                        startTime.set(Calendar.SECOND, 0);
//                        long alarmStartTime = startTime.getTimeInMillis();
//
//                        Log.d("ViewReminderActivity", "Time: " + h + " : " + m + " : " + second);
//
//                        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
//                    }
                }

                Intent intent = new Intent(ViewReminderActivity.this, AlarmReceive.class);
                intent.putExtra("notificationId", notificationId);
                intent.putExtra("message", rmessage);
                intent.putExtra("reminderName", reminderName);

                PendingIntent alarmIntent = PendingIntent.getBroadcast(
                        ViewReminderActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
                );

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    OffsetTime offset = OffsetTime.now();

                    int h = offset.getHour();
                    int m = offset.getMinute();
                    int s = offset.getSecond();

                    int second = (int) s + 5;


                    h =rh;
                    m =rm;

                    Log.d("ViewReminderActivity", h + " : " + m);

                    Calendar startTime = Calendar.getInstance();
                    startTime.set(Calendar.HOUR_OF_DAY, h);
                    startTime.set(Calendar.MINUTE, m);
                    startTime.set(Calendar.SECOND, 0);
                    long alarmStartTime = startTime.getTimeInMillis();

                    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
                }
            }
        });

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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ViewReminderActivity.this, AlarmReceive.class);
        intent.putExtra("notificationId", notificationId);
        intent.putExtra("message", "Hello World");


        PendingIntent alarmIntent = PendingIntent.getBroadcast(
                ViewReminderActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            OffsetTime offset = OffsetTime.now();

            int h = offset.getHour();
            int m = offset.getMinute();
            int s = offset.getSecond();

            int second = (int) s + 5;


            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, h);
            startTime.set(Calendar.MINUTE, m);
            startTime.set(Calendar.SECOND, second);
            long alarmStartTime = startTime.getTimeInMillis();

            Log.d("ViewReminderActivity", "Time: " + h + " : " + m + " : " + second);

            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
        }

        Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
    }
}