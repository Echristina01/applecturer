package com.example.applecturer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseModel implements Parcelable {
    private String id, name, lecturer_id;
    private int day, start, end;

    public CourseModel() {
    }

    protected CourseModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        lecturer_id = in.readString();
        start = in.readInt();
        end = in.readInt();
        day = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(lecturer_id);
        dest.writeInt(start);
        dest.writeInt(end);
        dest.writeInt(day);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CourseModel> CREATOR = new Creator<CourseModel>() {
        @Override
        public CourseModel createFromParcel(Parcel in) {
            return new CourseModel(in);
        }

        @Override
        public CourseModel[] newArray(int size) {
            return new CourseModel[size];
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

    public String getLecturer_id() {
        return lecturer_id;
    }

    public void setLecturer_id(String lecturer_id) {
        this.lecturer_id = lecturer_id;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public CourseModel(String id, String name, String lecturer_id, int start, int end, int day) {
        this.id = id;
        this.name = name;
        this.lecturer_id = lecturer_id;
        this.start = start;
        this.end = end;
        this.day = day;
    }

    @Override
    public String toString() {
        return "CourseModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lecturer_id='" + lecturer_id + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", day=" + day +
                '}';
    }
}
