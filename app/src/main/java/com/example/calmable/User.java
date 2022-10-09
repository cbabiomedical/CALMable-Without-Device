package com.example.calmable;

public class User {

    // Creating private variables to store user data
    // private variables for encapsulation
    public String fullName,age, email , gender,phoneNumber, profile;
    public String preference;
    public String journalNote;
    private long coins = 0;

    public int sugIndex;

    public User() {
    }

    public User(String fullName, String age, String email,String gender,String phoneNumber) {
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public User(String fullName, String age, String email, String gender, String phoneNumber, int sugIndex) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.sugIndex = sugIndex;
    }

    public User(String preference) {
        this.preference = preference;
    }


    public User(String preference, String journalNote) {
        this.journalNote = journalNote;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJournalNote() {
        return journalNote;
    }

    public void setJournalNote(String journalNote) {
        this.journalNote = journalNote;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getSugIndex() {
        return sugIndex;
    }

    public void setSugIndex(int sugIndex) {
        this.sugIndex = sugIndex;
    }
}