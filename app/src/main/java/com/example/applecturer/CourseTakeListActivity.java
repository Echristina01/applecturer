package com.example.applecturer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applecturer.adapter.CourseTakeAdapter;
import com.example.applecturer.model.CourseModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseTakeListActivity extends AppCompatActivity {
    public static ArrayList<CourseModel> courseList;

    private RecyclerView rv;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvLayout;
    private DatabaseReference dbCourse;
    private CourseTakeListActivity parentclass = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_recycler);
        renderView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        renderView();
    }

    public void renderView(){

        dbCourse = FirebaseDatabase.getInstance().getReference("courses");

        courseList = new ArrayList<CourseModel>();

        dbCourse.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    CourseModel courseModel = childSnapshot.getValue(CourseModel.class);
                    courseList.add(courseModel);
                    System.out.println("ADDING TO COURSE LIST");
                }
                System.out.println("COURSE LIST SIZE " + courseList.size());

                rv = (RecyclerView) findViewById(R.id.recyclerView);
                rv.setHasFixedSize(true);
                rvLayout = new LinearLayoutManager(parentclass);
                rvAdapter = new CourseTakeAdapter(courseList, parentclass);

                rv.setLayoutManager(rvLayout);
                rv.setAdapter(rvAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
