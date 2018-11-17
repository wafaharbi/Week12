package com.example.wafa.studentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class EventDescription extends AppCompatActivity {

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);


        final TextView titleView =findViewById(R.id.title);
        final TextView descView = findViewById(R.id.desc);
        final ImageView imageView = findViewById(R.id.image);

        final String user_id=   getIntent().getStringExtra("user_id");
        final String title=   getIntent().getStringExtra("title");
        final String desc=   getIntent().getStringExtra("desc");
        final String image=   getIntent().getStringExtra("image");




        descView.setText(desc);
        titleView.setText(title);
        Picasso.with(EventDescription.this).load(image).placeholder(R.drawable.default_img).into(imageView);

        reference = FirebaseDatabase.getInstance().getReference().child("event").child("user_id");
    }
}
