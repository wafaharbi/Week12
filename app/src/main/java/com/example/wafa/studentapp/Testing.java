package com.example.wafa.studentapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Testing extends AppCompatActivity {

    DatabaseReference parentRef, studentRef;

    FirebaseUser fuser;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);


        listView = (ListView) findViewById(R.id.listInfoStudent);


        fuser = FirebaseAuth.getInstance().getCurrentUser();


        parentRef = FirebaseDatabase.getInstance().getReference().child("Parents").child(fuser.getUid());

        studentRef = FirebaseDatabase.getInstance().getReference().child("Student").getParent();


        Query p = parentRef.child("username").startAt("34").getRef();


        p.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                studentRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for (DataSnapshot ds : dataSnapshot.getChildren()) {



                            showData(dataSnapshot);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void showData(DataSnapshot dataSnapshot) {

        final User info = new User();


        for (DataSnapshot ds : dataSnapshot.getChildren()) {


            final String name = info.setName(dataSnapshot.child("name").getValue().toString());
            // final String username = info.setUsername(dataSnapshot.child("username").getValue().toString());
            final String email = info.setEmail(dataSnapshot.child("email").getValue().toString());
            //  final String password = info.setPassword(dataSnapshot.child("password").getValue().toString());
            //  final String phone = info.setPhone(dataSnapshot.child("phone").getValue().toString());
            final String image = dataSnapshot.child("image").getValue().toString();

            // Picasso.with(Testing.this).load(image).placeholder(R.drawable.default_img).into(mDisplayImage);

            ArrayList<String> array = new ArrayList<>();
            array.add(name);
            // array.add(username);
            array.add(email);
            //  array.add(password);
            // array.add(phone);

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);

            listView.setAdapter(arrayAdapter);

        }
    }
}
