package com.example.medireminder.patient_info;

public class PatientInfo {
    private String medicinePhoto;
    private String reminderName;
    private String medicineName;
    private String reminderTime;

    public PatientInfo(String medicinePhoto, String medicineName, String reminderTime, String reminderName) {
        this.medicinePhoto = medicinePhoto;
        this.medicineName = medicineName;
        this.reminderTime = reminderTime;
        this.reminderName = reminderName;
    }

    public String getMedicinePhoto() { return medicinePhoto; };
    public String getMedicineName() { return medicineName; };
    public String getReminderTime() { return reminderTime; };
    public String getReminderName() { return reminderName; };
}
