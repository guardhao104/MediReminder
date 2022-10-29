package com.example.medireminder.patient_list;

public class Patient {
    private String name;
    private String age;
    private String email;
    private String uid;

    public Patient(String name, String age, String email, String uid) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.uid = uid;
    }

    public String getName() { return name; }
    public String getAge() { return age; }
    public String getEmail() { return email; }
    public String getUID() { return uid; }
}
