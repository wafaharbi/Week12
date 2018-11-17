package com.example.wafa.studentapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginTeacher extends AppCompatActivity {

    TextView forget, signup;
    EditText TeacherEmail, TeacherPassword;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase database;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_teacher);



        TeacherEmail = (EditText) findViewById(R.id.editElogin);
        TeacherPassword = (EditText) findViewById(R.id.editPlogin);
        forget = (TextView) findViewById(R.id.textviewForget);
        signup = (TextView) findViewById(R.id.textViewSignup);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Teachers");


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignupTeacher.class);
                startActivity(i);
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ForgetPassword.class);
                startActivity(i);
            }
        });
    }
    public void userloginStudent(View v) {

        final String email = TeacherEmail.getText().toString().trim();
        final String password = TeacherPassword.getText().toString().trim();



        if (email.isEmpty()) {
            TeacherEmail.setError(" error email");
            TeacherEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            TeacherEmail.setError("  invalid email");
            TeacherEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            TeacherPassword.setError("blank");
            TeacherPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            TeacherPassword.setError(" password at least 6 charecter");
            TeacherPassword.requestFocus();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in, please wait...");
        progressDialog.show();


        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override

            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), " user login ", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i = new Intent(getApplicationContext(), TeacherHome.class);
                    startActivity(i);

                }
                else {
                    Toast.makeText(getApplicationContext(), "you should  Signup first ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }




}

