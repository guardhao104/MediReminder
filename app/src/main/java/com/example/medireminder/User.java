package com.example.medireminder;

public class User {
    public String fullName, age, email;
    public int type;

    public User(){

    }

    public User(String fullName, String age, String email, int type){
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.type = type;
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

    public int getType() {return type;}
}
