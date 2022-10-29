package com.example.medireminder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {
    public String fullName, age, email;
    public boolean isPatient;
    public List<Map<String, Object>> reminder;

    public User(){

    }

    public User(String fullName, String age, String email, boolean isPatient, List<Map<String, Object>> reminder){
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.isPatient = isPatient;
        this.reminder = reminder;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsPatient() { return isPatient; }

    public List<Map<String, Object>> getReminder() { return reminder; }
}
