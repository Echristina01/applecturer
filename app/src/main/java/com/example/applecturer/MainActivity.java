package com.example.applecturer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null){
            System.out.println("NOT LOGGED IN");
        } else {
            System.out.println("LOGGED IN AS " + mAuth.getCurrentUser().getEmail());
            startActivity(new Intent(MainActivity.this, StudentDashboardActivity.class));
        }

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StudentRegisterActivity.class));
            }
        });
        findViewById(R.id.buttonStudentList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StudentListActivity.class));
            }
        });
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StudentLoginActivity.class));
            }
        });
        findViewById(R.id.buttonAddLecturer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LecturerAddActivity.class));
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null){
            System.out.println("NOT LOGGED IN");
        } else {
            System.out.println("LOGGED IN AS " + mAuth.getCurrentUser().getEmail());
            startActivity(new Intent(MainActivity.this, StudentDashboardActivity.class));
        }
    }

}
