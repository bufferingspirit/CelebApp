package com.example.admin.celebapp;

/**
 * Created by Admin on 8/10/2017.
 */

public class celebEntry {
    String firstName;
    String lastName;
    String height;
    String age;
    String description;


    byte[] picture;

    public celebEntry(String firstName, String lastName, String height, String age, String description, byte[] picture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
        this.age = age;
        this.description = description;
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
