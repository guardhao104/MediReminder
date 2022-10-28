package com.example.medireminder.patient_info;

public class PatientInfo {
    private String medicinePhoto;
    private String medicineName;
    private String reminderTime;

    public PatientInfo(String medicinePhoto, String medicineName, String reminderTime) {
        this.medicinePhoto = medicinePhoto;
        this.medicineName = medicineName;
        this.reminderTime = reminderTime;
    }

    public String getMedicinePhoto() { return medicinePhoto; };
    public String getMedicineName() { return medicineName; };
    public String getReminderTime() { return reminderTime; };
}
