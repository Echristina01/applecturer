package com.example.applecturer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LecturerModel implements Parcelable {
    private String id, name, expertise, gender;

    public LecturerModel() {
    }

    public LecturerModel(String id, String name, String expertise, String gender) {
        this.id = id;
        this.name = name;
        this.expertise = expertise;
        this.gender = gender;
    }

    protected LecturerModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        expertise = in.readString();
        gender = in.readString();
    }

    public static final Creator<LecturerModel> CREATOR = new Creator<LecturerModel>() {
        @Override
        public LecturerModel createFromParcel(Parcel in) {
            return new LecturerModel(in);
        }

        @Override
        public LecturerModel[] newArray(int size) {
            return new LecturerModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(expertise);
    }

    @Override
    public String toString() {
        return "ModelLecturer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", expertise='" + expertise + '\'' +
                '}';
    }
}
