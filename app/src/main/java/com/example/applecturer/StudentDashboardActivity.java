package com.example.applecturer;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class StudentDashboardActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    StudentDashboardActivity currentActivity = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.student_dashboard);

        mAuth = FirebaseAuth.getInstance();

        String email = mAuth.getCurrentUser().getEmail();
        String uuid = mAuth.getCurrentUser().getUid();

        ((TextView)findViewById(R.id.textUser)).setText("Welcome, " + email + "!");

        findViewById(R.id.buttonLogout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAuth.signOut();
                        currentActivity.finish();
                    }
                }
        );
    }
}
