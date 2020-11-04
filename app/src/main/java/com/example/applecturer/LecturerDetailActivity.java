package com.example.applecturer;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.applecturer.model.LecturerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LecturerDetailActivity extends AppCompatActivity {

    DatabaseReference dbLecturer;
    ArrayList<LecturerModel> lecturerList = new ArrayList<>();
    LecturerModel currentLecturer;
    String id;
    EditText name, expertise;
    RadioGroup rgGender;
    AppCompatActivity currentActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentActivity = this;

        setContentView(R.layout.lecturer_detail);

        dbLecturer = FirebaseDatabase.getInstance().getReference("lecturers");

        this.id = getIntent().getStringExtra("uid");

        name = findViewById(R.id.inputName);
        expertise = findViewById(R.id.inputExpertise);
        rgGender = findViewById(R.id.rgGender);

        dbLecturer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lecturerList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    LecturerModel lecturerModel = childSnapshot.getValue(LecturerModel.class);
                    lecturerList.add(lecturerModel);
                    if(lecturerModel.getId().equals(id)){
                        currentLecturer = lecturerModel;
                        ((Toolbar) findViewById(R.id.toolbar)).setTitle("Edit " + currentLecturer.getName());
                        name.setText(currentLecturer.getName());
                        expertise.setText(currentLecturer.getExpertise());

                        System.out.println("Checking gender");
                        if (currentLecturer.getGender().equals("Female")){
                            rgGender.check(R.id.radioButtonFemale);
                        }else{
                            rgGender.check(R.id.radioButtonMale);
                        }
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
                System.out.println("Delete " + id);
                dbLecturer.child(id).removeValue();
                currentActivity.finish();
            }
        });

        findViewById(R.id.buttonEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbLecturer.child(id).child(("name")).setValue(name.getText().toString());
                dbLecturer.child(id).child(("gender")).setValue(((RadioButton) findViewById(rgGender.getCheckedRadioButtonId())).getText());
                dbLecturer.child(id).child(("expertise")).setValue(expertise.getText().toString());
                currentActivity.finish();
            }
        });
    }
}
