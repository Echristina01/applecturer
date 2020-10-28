package com.example.applecturer.delete;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applecturer.MainActivity;
import com.example.applecturer.R;

public class AddLecturerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.del_addlecturer);

//        bar = findViewById(R.id.tb_lecturer);
//        setSupportActionBar(bar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RadioGroup rg_gender = findViewById(R.id.rgGender);
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
//                String gender;
                Boolean isFemale;
                if (radioButton.getText().toString().equalsIgnoreCase("Male")) {
//                    gender = "m";
                    isFemale = false;
                } else {
//                    gender = "f";
                    isFemale = true;
                }
                System.out.println(isFemale);
            }
        });
    }

    public void onBackPressed() {
        Intent intent;
        intent = new Intent(AddLecturerActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(AddLecturerActivity.this);
        startActivity(intent, options.toBundle());
        finish();
    }
}
