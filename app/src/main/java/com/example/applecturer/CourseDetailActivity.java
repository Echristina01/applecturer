package com.example.applecturer;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.applecturer.model.CourseModel;
import com.example.applecturer.model.LecturerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseDetailActivity extends AppCompatActivity {

    DatabaseReference dbCourse, dbLecturer;
    ArrayList<LecturerModel> lecturerList = new ArrayList<>();
    ArrayList<CourseModel> courseList = new ArrayList<>();
    CourseModel currentCourse;
    AppCompatActivity currentActivity;

    Spinner spinnerDay, spinnerStart, spinnerEnd, spinnerLecturer;
    EditText textName;
    String id;
    String name, lecturer_id;
    int start, end, day;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentActivity = this;

        setContentView(R.layout.course_detail);

        dbCourse = FirebaseDatabase.getInstance().getReference("courses");

        this.id = getIntent().getStringExtra("uid");
        System.out.println("EDITING 2 " + this.id);

        textName = findViewById(R.id.inputName);
        spinnerLecturer = findViewById(R.id.spinnerLecturer);
        spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(CourseDetailActivity.this,
                R.array.day, android.R.layout.simple_spinner_item);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapterDay);

        spinnerStart = (Spinner) findViewById(R.id.spinnerStart);
        ArrayAdapter<CharSequence> adapterStart = ArrayAdapter.createFromResource(CourseDetailActivity.this,
                R.array.time, android.R.layout.simple_spinner_item);
        adapterStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStart.setAdapter(adapterStart);

        spinnerEnd = (Spinner) findViewById(R.id.spinnerEnd);
        ArrayAdapter<CharSequence> adapterEnd = ArrayAdapter.createFromResource(CourseDetailActivity.this,
                R.array.time, android.R.layout.simple_spinner_item);
        adapterEnd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEnd.setAdapter(adapterEnd);

        dbCourse.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    CourseModel courseModel = childSnapshot.getValue(CourseModel.class);
                    courseList.add(courseModel);
                    if(courseModel.getId().equals(id)){
                        currentCourse = courseModel;
                        ((Toolbar) findViewById(R.id.toolbar)).setTitle("Edit " + currentCourse.getName());
                        textName.setText(currentCourse.getName());
                        spinnerDay.setSelection(currentCourse.getDay());
                        spinnerStart.setSelection(currentCourse.getStart());
                        spinnerEnd.setSelection(currentCourse.getEnd());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Delete " + id);
                dbCourse.child(id).removeValue();
                currentActivity.finish();
            }
        });

        findViewById(R.id.buttonEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbCourse.child(id).child(("name")).setValue(textName.getText().toString());
                dbCourse.child(id).child(("lecturer_id")).setValue(lecturerList.get((int)spinnerLecturer.getSelectedItemId()).getId());
                dbCourse.child(id).child(("day")).setValue(spinnerDay.getSelectedItemId());
                dbCourse.child(id).child(("start")).setValue(spinnerStart.getSelectedItemId());
                dbCourse.child(id).child(("end")).setValue(spinnerEnd.getSelectedItemId());
                currentActivity.finish();
            }
        });

        getLecturers();
    }


    private void getLecturers() {
        DatabaseReference lecturerDB = FirebaseDatabase.getInstance().getReference("lecturers");
        lecturerDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lecturerList.clear();
                int selectedLecturerIndex = 0;
                boolean selectedLecturerFound = false;
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    LecturerModel lecturerModel = childSnapshot.getValue(LecturerModel.class);
                    lecturerList.add(lecturerModel);
                    System.out.println("ADDING TO LECTURER LIST");
                    if (!selectedLecturerFound){
                        if (currentCourse.getLecturer_id().equals(lecturerModel.getId())) {
                            selectedLecturerFound = true;
                        } else {
                            selectedLecturerIndex++;
                        }
                    }
                }
                System.out.println("LECTURER LIST SIZE " + lecturerList.size());

                spinnerLecturer = (Spinner) findViewById(R.id.spinnerLecturer);

                ArrayAdapter<LecturerModel> adapterLecturer =
                        new ArrayAdapter<LecturerModel>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, lecturerList);

                adapterLecturer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerLecturer.setAdapter(adapterLecturer);

                spinnerLecturer.setSelection(selectedLecturerIndex);

//                System.out.println("LECTURER ID" + currentCourse.getLecturer_id());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
