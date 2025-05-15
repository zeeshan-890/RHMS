package com.remote_vitals.frontend.models;

public class Doctor {
    private final String firstName;
    private final String lastName;
    private final String gender;
    private final String phoneNumber;
    private final String email;
    private final String qualification;

    public Doctor(String firstName, String lastName, String gender, String phoneNumber, String email, String qualification) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.qualification = qualification;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getQualification() {
        return qualification;
    }
} 