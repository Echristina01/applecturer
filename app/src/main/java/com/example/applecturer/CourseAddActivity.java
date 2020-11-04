package com.example.applecturer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applecturer.model.CourseModel;
import com.example.applecturer.model.LecturerModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseAddActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private AppCompatActivity currentActivity;

    Dialog dialog;
    EditText inputName, inputExpertise;
    RadioGroup rgGender;
    Button buttonRegister;
    Spinner spinnerDay, spinnerStart, spinnerEnd, spinnerLecturer;
    String id;
    String name, lecturer_id;
    int start, end, day;
    CourseModel courseModel;
    ArrayList<LecturerModel> lecturerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = this;
        mDatabase = FirebaseDatabase.getInstance().getReference("courses");
        this.setContentView(R.layout.course_add);

        inputName = findViewById(R.id.inputName);
        inputExpertise = findViewById(R.id.inputExpertise);
        rgGender = findViewById(R.id.rgGender);

        buttonRegister = findViewById(R.id.buttonSubmit);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(CourseAddActivity.this,
                R.array.day, android.R.layout.simple_spinner_item);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapterDay);

        spinnerStart = (Spinner) findViewById(R.id.spinnerStart);
        ArrayAdapter<CharSequence> adapterStart = ArrayAdapter.createFromResource(CourseAddActivity.this,
                R.array.time, android.R.layout.simple_spinner_item);
        adapterStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStart.setAdapter(adapterStart);

        spinnerEnd = (Spinner) findViewById(R.id.spinnerEnd);
        ArrayAdapter<CharSequence> adapterEnd = ArrayAdapter.createFromResource(CourseAddActivity.this,
                R.array.time, android.R.layout.simple_spinner_item);
        adapterEnd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEnd.setAdapter(adapterEnd);


        getLecturers();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void register() {

        id = mDatabase.child("courses").push().getKey();;
        lecturer_id = lecturerList.get(spinnerLecturer.getSelectedItemPosition()).getId();
        name = inputName.getText().toString();
        start = spinnerStart.getSelectedItemPosition();
        end = spinnerEnd.getSelectedItemPosition();
        day = spinnerDay.getSelectedItemPosition();

        System.out.println("Adding course " + name);

        courseModel = new CourseModel(id, name, lecturer_id, start, end, day);

        mDatabase.child(String.valueOf(id)).setValue(courseModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CourseAddActivity.this, "Added a new course", Toast.LENGTH_SHORT).show();
                currentActivity.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CourseAddActivity.this, "Adding new course failed - " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void signIn(String email, String password) {

    }

    private void getLecturers() {
        DatabaseReference lecturerDB = FirebaseDatabase.getInstance().getReference("lecturers");
        lecturerDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lecturerList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    LecturerModel lecturerModel = childSnapshot.getValue(LecturerModel.class);
                    lecturerList.add(lecturerModel);
                    System.out.println("ADDING TO LECTURER LIST");
                }
                System.out.println("LECTURER LIST SIZE " + lecturerList.size());

                spinnerLecturer = (Spinner) findViewById(R.id.spinnerLecturer);

                ArrayAdapter<LecturerModel> adapterLecturer =
                        new ArrayAdapter<LecturerModel>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, lecturerList);

                adapterLecturer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerLecturer.setAdapter(adapterLecturer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}


