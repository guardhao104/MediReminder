package com.example.medireminder.patient.forms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.medireminder.R;
import com.example.medireminder.patient_info.PatientInfo;

import java.util.List;

public class ReminderAdapter extends ArrayAdapter {
    private final int resourceId;

    public ReminderAdapter(@NonNull Context context, int resource, List<Reminder> object) {
        super(context, resource, object);
        this.resourceId = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Reminder reminder = (Reminder) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

//        ImageView medicinePhoto = (ImageView) view.findViewById(R.id.medicineImage);
        TextView reminderName = (TextView) view.findViewById(R.id.reminderName);
        TextView medicineName = (TextView) view.findViewById(R.id.medicineName);
        TextView reminderTime = (TextView) view.findViewById(R.id.medicineTime);

        reminderName.setText(reminder.getReminderName());
        medicineName.setText(reminder.getMedicineName());
        reminderTime.setText(reminder.getReminderTime());

        return view;
    }
}
