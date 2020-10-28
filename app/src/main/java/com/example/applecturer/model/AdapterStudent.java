package com.example.applecturer.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applecturer.R;
import com.example.applecturer.StudentDetailActivity;

import java.util.ArrayList;

public class AdapterStudent extends RecyclerView.Adapter<AdapterStudent.StudentViewHolder> {
    private ArrayList<ModelStudent> studentList;
    private AppCompatActivity parentActivity;

    //    Inner Class
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView textName, textAge, textAddress;
        public String uid;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textAddress = itemView.findViewById(R.id.textAddress);
            textAge = itemView.findViewById(R.id.textAge);
        }
    }

    public AdapterStudent(ArrayList<ModelStudent> studentList, AppCompatActivity parentActivity) {
        this.studentList = studentList;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.liststudent_cardview, parent, false);
        final StudentViewHolder studentViewHolder = new StudentViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parentActivity, StudentDetailActivity.class);
                intent.putExtra("uid", studentViewHolder.uid);
                parentActivity.startActivity(intent);
            }
        });

        return studentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        ModelStudent currentStudent = this.studentList.get(position);
        holder.textName.setText(currentStudent.getName());
        holder.textAddress.setText(currentStudent.getAddress());
        holder.textAge.setText(currentStudent.getAge() + " years old");
        holder.uid = currentStudent.getUid();
    }

    @Override
    public int getItemCount() {
        return this.studentList.size();
    }

}
