package com.example.applecturer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applecturer.R;
import com.example.applecturer.model.CourseModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseTakeAdapter extends RecyclerView.Adapter<CourseTakeAdapter.CourseTakeViewHolder> {
    private ArrayList<CourseModel> courseList;
    private AppCompatActivity parentActivity;
    ArrayList<String> coursesTaken = new ArrayList<>();

    //    Inner Class
    public static class CourseTakeViewHolder extends RecyclerView.ViewHolder {
        public TextView textName, textLecturer, textDay, textTime;
        public String uid;

        public CourseTakeViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textLecturer = itemView.findViewById(R.id.textLecturer);
            textDay = itemView.findViewById(R.id.textDay);
            textTime = itemView.findViewById(R.id.textTime);
        }
    }

    public CourseTakeAdapter(ArrayList<CourseModel> courseList, AppCompatActivity parentActivity) {
        this.courseList = courseList;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public CourseTakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list_card, parent, false);
        final CourseTakeViewHolder courseTakeViewHolder = new CourseTakeViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Taking class");
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                //setup db
                final DatabaseReference dbStudent = FirebaseDatabase.getInstance().getReference("student").child(uid).child("coursestaken");

                //reading db
                dbStudent.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        coursesTaken = (ArrayList<String>) snapshot.getValue();

                        // writing to db\
                        if(coursesTaken != null) {
                            if (!coursesTaken.contains(courseTakeViewHolder.uid)) {
                                coursesTaken.add(courseTakeViewHolder.uid);
                            }
                        } else {
                            coursesTaken = new ArrayList<>();
                            coursesTaken.add(courseTakeViewHolder.uid);
                        }
                        // TOast course successfully added
                        dbStudent.setValue(coursesTaken);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });

        return courseTakeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseTakeViewHolder holder, int position) {
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
