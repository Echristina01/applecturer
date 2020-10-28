package com.example.applecturer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applecturer.adapter.AdapterStudent;
import com.example.applecturer.model.StudentModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {
    public static ArrayList<StudentModel> studentList;

    private RecyclerView rv;
    private RecyclerView.Adapter rvAdapter;
    private RecyclerView.LayoutManager rvLayout;
    private DatabaseReference dbstudent;
    private StudentListActivity parentclass = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list_recycler);
        renderView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        renderView();
    }

    public void renderView(){

        dbstudent = FirebaseDatabase.getInstance().getReference("student");

        studentList = new ArrayList<StudentModel>();

        dbstudent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    StudentModel studentModel = childSnapshot.getValue(StudentModel.class);
                    studentList.add(studentModel);
                    System.out.println("ADDING TO STUDENT LIST");
                }
                System.out.println("STUDENT LIST SIZE " + studentList.size());

                rv = (RecyclerView) findViewById(R.id.recyclerView);
                rv.setHasFixedSize(true);
                rvLayout = new LinearLayoutManager(parentclass);
                rvAdapter = new AdapterStudent(studentList, parentclass);

                rv.setLayoutManager(rvLayout);
                rv.setAdapter(rvAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
