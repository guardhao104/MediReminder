package com.example.medireminder;

public class User {
    public String fullName, age, email;
    public boolean isPatient;

    public User(){

    }

    public User(String fullName, String age, String email, boolean isPatient){
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.isPatient = isPatient;
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
}
