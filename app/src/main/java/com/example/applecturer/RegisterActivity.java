package com.example.applecturer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.applecturer.model.ModelStudent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private AppCompatActivity currentActivity;

    Dialog dialog;
    EditText inputEmail, inputName, inputPass, inputNim, inputAge, inputAddress;
    Button buttonRegister;
    String uid = "", email = "", pass = "", name = "", nim = "", age = "", gender = "male", address = "", action = "";
    ModelStudent student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = this;
        mAuth = FirebaseAuth.getInstance();
//        dialog = Glovar.loadingDialog(RegisterActivity.this);
        mDatabase = FirebaseDatabase.getInstance().getReference("student");
        setContentView(R.layout.register);

        inputEmail = findViewById(R.id.inputEmail);
        inputPass = findViewById(R.id.inputPassword);
        inputName = findViewById(R.id.inputName);
        inputNim = findViewById(R.id.inputNim);
        inputAge = findViewById(R.id.inputAge);
        inputAddress = findViewById(R.id.inputAddress);

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
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    private void register() {

        uid = "";
        email = inputEmail.getText().toString();
        pass = inputPass.getText().toString();
        name = inputName.getText().toString();
        nim = inputNim.getText().toString();
        age = inputAge.getText().toString();
        gender = "male";
        address = inputAddress.getText().toString();
        action = "";

//        dialog.show();

        System.out.println("Registering " + name);
        System.out.println("Email: " + email + ", Pass: " + pass);

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(RegisterActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Success");
//                            dialog.cancel();
                            uid = mAuth.getCurrentUser().getUid();
                            System.out.println(uid);
                            ModelStudent student = new ModelStudent(uid, email, pass, name, nim, gender, age, address);
                            mDatabase.child(uid).setValue(student).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "Student register successful", Toast.LENGTH_SHORT).show();
                                    currentActivity.finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "Student register failed - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            mAuth.signOut();
                        } else {
                            System.out.println("Not success");
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException malFormed) {
                                Toast.makeText(RegisterActivity.this, "Invalid email or password!", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthUserCollisionException existEmail) {
                                Toast.makeText(RegisterActivity.this, "Email already registered!", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(RegisterActivity.this, "Register failed!", Toast.LENGTH_SHORT).show();
                            }
//                            dialog.cancel();
                        }
                    }
                });
    }

    private void signIn(String email, String password) {

    }
}


