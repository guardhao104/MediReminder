package com.example.medireminder.patient_list;

public class Patient {
    private String name;
    private String age;
    private String email;

    public Patient(String name, String age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() { return name; }
    public String getAge() { return age; }
    public String getEmail() { return email; }
}
