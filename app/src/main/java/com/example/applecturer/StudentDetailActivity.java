package com.example.applecturer;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.applecturer.model.ModelStudent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentDetailActivity extends AppCompatActivity {

    DatabaseReference dbStudent;
    ArrayList<ModelStudent> studentList = new ArrayList<>();
    ModelStudent currentStudent;
    String uid;
    EditText name, password, email, nim, address, age;
    AppCompatActivity currentActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentActivity = this;

        setContentView(R.layout.student_detail);

        dbStudent = FirebaseDatabase.getInstance().getReference("student");

        this.uid = getIntent().getStringExtra("uid");

        name = findViewById(R.id.inputName);
        nim = findViewById(R.id.inputNim);
        address = findViewById(R.id.inputAddress);
        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        age = findViewById(R.id.inputAge);

        dbStudent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    ModelStudent modelStudent = childSnapshot.getValue(ModelStudent.class);
                    studentList.add(modelStudent);
                    if(modelStudent.getUid().equals(uid)){
                        currentStudent = modelStudent;
                        ((Toolbar) findViewById(R.id.toolbar)).setTitle("Edit " + currentStudent.getName());
                        name.setText(currentStudent.getName());
                        nim.setText(currentStudent.getNim());
                        address.setText(currentStudent.getAddress());
                        email.setText(currentStudent.getEmail());
                        password.setText(currentStudent.getPassword());
                        age.setText(currentStudent.getAge());
                        break;
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
                System.out.println("Delete " + uid);
                dbStudent.child(uid).removeValue();
                currentActivity.finish();
            }
        });

        findViewById(R.id.buttonEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbStudent.child(uid).child(("age")).setValue(age.getText().toString());
                dbStudent.child(uid).child(("name")).setValue(name.getText().toString());
                dbStudent.child(uid).child(("password")).setValue(password.getText().toString());
                dbStudent.child(uid).child(("nim")).setValue(nim.getText().toString());
                dbStudent.child(uid).child(("email")).setValue(email.getText().toString());
                dbStudent.child(uid).child(("address")).setValue(address.getText().toString());
                currentActivity.finish();
            }
        });
    }
}
