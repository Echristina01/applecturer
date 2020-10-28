package com.example.applecturer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentModel implements Parcelable {
    private String uid, email, password, name, nim, gender, age, address;

    public StudentModel(){}

    public StudentModel(String name, String email, String age){
        this.email = email;
        this.name = name;
        this.age = age;
    }

    public StudentModel(String uid, String email, String password, String name, String nim, String gender, String age, String address) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nim = nim;
        this.gender = gender;
        this.age = age;
        this.address = address;
    }

    protected StudentModel(Parcel in) {
        uid = in.readString();
        email = in.readString();
        password = in.readString();
        name = in.readString();
        nim = in.readString();
        gender = in.readString();
        age = in.readString();
        address = in.readString();
    }

    public static final Creator<StudentModel> CREATOR = new Creator<StudentModel>() {
        @Override
        public StudentModel createFromParcel(Parcel in) {
            return new StudentModel(in);
        }

        @Override
        public StudentModel[] newArray(int size) {
            return new StudentModel[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(nim);
        dest.writeString(gender);
        dest.writeString(age);
        dest.writeString(address);
    }

    @Override
    public String toString() {
        return "ModelStudent{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", nim='" + nim + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
