package com.example.medireminder.patient_info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.medireminder.R;
import com.example.medireminder.patient_list.Patient;

import java.util.List;

public class PatientInfoListAdapter extends ArrayAdapter {
    private final int resourceId;

    public PatientInfoListAdapter(@NonNull Context context, int resource, List<PatientInfo> object) {
        super(context, resource, object);
        this.resourceId = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PatientInfo patientInfo = (PatientInfo) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        ImageView medicinePhoto = (ImageView) view.findViewById(R.id.text_view_item_medicine_image);
        TextView medicineName = (TextView) view.findViewById(R.id.text_view_item_medicine_name);
        TextView reminderTime = (TextView) view.findViewById(R.id.text_view_item_medicine_time);

        medicineName.setText(patientInfo.getMedicineName());
        reminderTime.setText(patientInfo.getReminderTime());

        return view;
    }
}
