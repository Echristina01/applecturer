package com.example.applecturer.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applecturer.CourseDetailActivity;
import com.example.applecturer.R;
import com.example.applecturer.model.CourseModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private ArrayList<CourseModel> courseList;
    private AppCompatActivity parentActivity;

    //    Inner Class
    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView textName, textLecturer, textDay, textTime;
        public String uid;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textLecturer = itemView.findViewById(R.id.textLecturer);
            textDay = itemView.findViewById(R.id.textDay);
            textTime = itemView.findViewById(R.id.textTime);
        }
    }

    public CourseAdapter(ArrayList<CourseModel> courseList, AppCompatActivity parentActivity) {
        this.courseList = courseList;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_card, parent, false);
        final CourseViewHolder courseViewHolder = new CourseViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parentActivity, CourseDetailActivity.class);
                intent.putExtra("uid", courseViewHolder.uid);
                System.out.println("EDITING " + courseViewHolder.uid);
                parentActivity.startActivity(intent);
            }
        });

        return courseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseViewHolder holder, int position) {
        final CourseModel currentCourse = this.courseList.get(position);
        holder.textName.setText(currentCourse.getName());
        holder.textDay.setText(parentActivity.getResources().getStringArray(R.array.day)[currentCourse.getDay()]);
        holder.textTime.setText(parentActivity.getResources().getStringArray(R.array.time)[currentCourse.getStart()] + " - " + parentActivity.getResources().getStringArray(R.array.time)[currentCourse.getEnd()]);
        holder.uid = currentCourse.getId();

        DatabaseReference dblecturer;
        dblecturer = FirebaseDatabase.getInstance().getReference("lecturers").child(currentCourse.getLecturer_id()).child("name");
        dblecturer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.textLecturer.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.courseList.size();
    }

}
