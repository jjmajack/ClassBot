package com.peuyanaga.classbot.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jackson on 11/11/2017.
 */

public class User implements Parcelable {

    int userId;
    String username;
    String password;
    String firstname;
    String lastname;
    String gender;
    String dob;
    String role;
    School school;
    Grade grade;

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int userId, String username, String password, String fisrtname, String lastname, String gender, String dob) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstname = fisrtname;
        this.lastname = lastname;
        this.gender = gender;
        this.dob = dob;
    }

    public User(Parcel in){
        this.userId = in.readInt();
        this.username = in.readString();
        this.password = in.readString();
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.gender = in.readString();
        this.dob = in.readString();
    }
    public int getUserId() { return userId; }
    public  void setUserId(int userId){ this.userId = userId; }
    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public School getSchool() {return school;}

    public void setSchool(School school) {this.school = school;}

    public Grade getGrade() {return grade;}

    public void setGrade(Grade grade) {this.grade = grade;}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString(){
        return getLastname() + " " + getFirstname();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(userId);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(firstname);
        parcel.writeString(lastname);
        parcel.writeString(gender);
        parcel.writeString(dob);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            // TODO Auto-generated method stub
            return new User[size];
        }

    };
}
