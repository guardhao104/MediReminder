package com.example.medireminder.patient_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.medireminder.R;

import java.util.List;

public class PatientListAdapter extends ArrayAdapter {
    private final int resourceId;

    public PatientListAdapter(@NonNull Context context, int resource, List<Patient> object) {
        super(context, resource, object);
        this.resourceId = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Patient patient = (Patient) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        TextView patientName = (TextView) view.findViewById(R.id.text_view_item_name);
        TextView patientAge = (TextView) view.findViewById(R.id.text_view_item_age);
        TextView patientEmail = (TextView) view.findViewById(R.id.text_view_item_email);

        patientName.setText(patient.getName());
        patientAge.setText("(" + patient.getAge() + ")");
        // patientEmail.setText(patient.getEmail());

        return view;
    }
}
