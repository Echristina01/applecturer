package com.example.applecturer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applecturer.model.LecturerModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LecturerAddActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private AppCompatActivity currentActivity;

    Dialog dialog;
    EditText inputName, inputExpertise;
    RadioGroup rgGender;
    Button buttonRegister;
    String id;
    String name = "", expertise = "", gender = "";
    LecturerModel currentLecturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = this;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("lecturers");
        this.setContentView(R.layout.lecturer_add);

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
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void register() {
//        System.out.println(mDatabase.child("lastid").toString());

//        id = Integer.parseInt(mDatabase.child("lastid").toString()) + 1;
        id = mDatabase.child("lecturer").push().getKey();;

        name = inputName.getText().toString();
        expertise = inputExpertise.getText().toString();

        RadioButton radioButton = (RadioButton) findViewById(rgGender.getCheckedRadioButtonId());
        gender = (String) radioButton.getText();

        System.out.println("Adding lecturer " + name);

        currentLecturer = new LecturerModel(id, name, expertise, gender);
//        mDatabase.push().setValueAsync(new Post("alanisawesome", "The Turing Machine"));

        mDatabase.child(String.valueOf(id)).setValue(currentLecturer).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(LecturerAddActivity.this, "Added a new lecturer", Toast.LENGTH_SHORT).show();
                currentActivity.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LecturerAddActivity.this, "Adding new lecturer failed - " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void signIn(String email, String password) {

    }
}


