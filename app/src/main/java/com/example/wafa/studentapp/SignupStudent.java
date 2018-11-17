package com.example.wafa.studentapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.auth.internal.IdTokenListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.internal.InternalTokenResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignupStudent extends AppCompatActivity implements View.OnClickListener{

     EditText studentName , studentEmail , studentPassword , studentPhone , studentUsername;
     Button signup;
     FirebaseAuth auth;
     FirebaseDatabase firebaseDatabase;
     FirebaseUser firebaseUser;
     DatabaseReference ref;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_student);

        studentName = (EditText) findViewById( R.id.editName);
        studentEmail= (EditText) findViewById(R.id.editElogin);
        studentPassword = (EditText) findViewById(R.id.editPlogin);
        studentPhone = (EditText) findViewById(R.id.editPhone);
        studentUsername= (EditText) findViewById(R.id.editUsername);
        signup = (Button) findViewById(R.id.textViewSignup);

        firebaseUser =FirebaseAuth.getInstance().getCurrentUser();

        signup.setOnClickListener(this);
        auth= FirebaseAuth.getInstance();

     }
    public void Account(View v){
        Intent i = new Intent(this, LoginStudent.class);
        startActivity(i);
    }


    @Override
    protected void onStart() {

        super.onStart();
        if(auth.getCurrentUser() != null){

        }
    }


    public void register(){

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Processing your request, please wait...");

        progressDialog.show();

        final String name =  studentName.getText().toString().trim();
        final String username =  studentUsername.getText().toString().trim();
        final String email = studentEmail.getText().toString().trim();
        final String password = studentPassword.getText().toString().trim();
        final String phone = studentPhone.getText().toString().trim();
        final String image = "default";
        final  String thumb_image= "default";


        if(name.isEmpty()){
            studentName.setError(" invalid name");
            studentName.requestFocus();
            return;
        }

        if(username.isEmpty()){
            studentUsername.setError(" invalid username");
            studentUsername.requestFocus();
            return;

        }
        if(email.isEmpty()){
            studentEmail.setError(" error email");
            studentEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            studentEmail.setError("  invalid email");
            studentEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            studentPassword.setError("blank");
            studentPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            studentPassword.setError(" password at least 6 charecter");
            studentPassword.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            studentPhone.setError(" empty");
            studentPhone.requestFocus();
            return;
        }
        if(phone.length() != 10){

            studentPhone.setError(" atleast 10 number");
            studentPhone.requestFocus();
            return;

        }


        ///////



        final User student= new User(name ,email,password,phone,username , image, thumb_image );

        firebaseDatabase = FirebaseDatabase.getInstance();

        ref= firebaseDatabase.getReference().child("Student");

///        ref.child(user.getUsername()).setValue(user);



        Query usernameQuery  = FirebaseDatabase.getInstance().getReference().child("Student").orderByChild("username").equalTo(username);


        usernameQuery.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getChildrenCount()>0){


                    Toast.makeText(getApplicationContext(), "correct username " , Toast.LENGTH_SHORT).show();

                }
                else
                    {

                        Toast.makeText(getApplicationContext(), "choose different username " , Toast.LENGTH_SHORT).show();


                    }


                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            final  String id= firebaseUser.getUid();

                            User student= new User(name ,email,password,phone,username ,image , thumb_image , id) ;

                            // ref= firebaseDatabase.getReference().child("Student");

                            HashMap<String, String> userMap = new HashMap<>();

                            userMap.put("name", name);
                            userMap.put("username" , username);
                            userMap.put("email" , email);
                            userMap.put("password" , password);
                            userMap.put("phone" , phone);
                            userMap.put("id" , id);
                            userMap.put("image", "default");
                            userMap.put("thumb_image", "default");

                            FirebaseDatabase.getInstance().getReference("Student").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {


                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), " Student signup successflly", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        finish();
                                        Intent i = new Intent(getApplicationContext(), StudentHome.class);
                                        startActivity(i);

                                    }

                                    else {
                                        progressDialog.dismiss();
                                        if( task.getException() instanceof FirebaseAuthUserCollisionException)
                                        {
                                            Toast.makeText(getApplicationContext()," You are already signin " , Toast.LENGTH_SHORT).show();

                                        }
                                        else {
                                            progressDialog.dismiss();

                                            Toast.makeText(getApplicationContext(), " Please try again", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                }
                            });
                        }
                        else{
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), "an error accoured , Please try again", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



     }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewSignup:
                register();

                break;


        }

    }


}
