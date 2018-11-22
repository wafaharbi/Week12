package com.example.wafa.studentapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Courses extends AppCompatActivity {


    private FirebaseAuth fAuth;
    private RecyclerView courseListt;
    private GridLayoutManager gridLayoutManager;

    private DatabaseReference reference ;

    private boolean isExist;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);


        courseListt = (RecyclerView) findViewById(R.id.course_list);


        gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        courseListt.setHasFixedSize(true);
        courseListt.setLayoutManager(gridLayoutManager);

        courseListt.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

        fAuth = FirebaseAuth.getInstance();





        if (fAuth.getCurrentUser() != null)
        {

            reference = FirebaseDatabase.getInstance().getReference().child("Courses").child(fAuth.getCurrentUser().getUid());
        }

        updateUI();

        loadData();

    }


    @Override
    public void onStart() {
        super.onStart();

    }
    private void loadData() {


        Query query = reference.orderByValue();
        FirebaseRecyclerAdapter<CourseModel, CourseViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CourseModel, CourseViewHolder>(

                CourseModel.class,
                R.layout.single_course_item,
                CourseViewHolder.class,
                query

        ) {
            @Override
            protected void populateViewHolder(final CourseViewHolder viewHolder, CourseModel model, int position) {

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                final String course_id = getRef(position).getKey();



                reference.child(course_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        if (dataSnapshot.hasChild("name") && dataSnapshot.hasChild("timestamp")) {


                            String title = dataSnapshot.child("name").getValue().toString();
                            String timestamp = dataSnapshot.child("timestamp").getValue().toString();



                            viewHolder.setCourseTitle(title);
                            //viewHolder.setNoteTime(timestamp);

                            GetTimeAgo getTimeAgo = new GetTimeAgo();

                            viewHolder.setCourseTime(getTimeAgo.getTimeAgo(Long.parseLong(timestamp), getApplicationContext()));

                            viewHolder.noteCard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    Intent intent = new Intent(Courses.this, NewCourse.class);
                                    intent.putExtra("course_id", course_id);
                                    startActivity(intent);
                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };
        courseListt.setAdapter(firebaseRecyclerAdapter);
    }


    private void updateUI(){

        if (fAuth.getCurrentUser() != null){
            Log.i("MainActivity", "fAuth != null");
        } else {
            Intent startIntent = new Intent(Courses.this, LoginStudent.class);
            startActivity(startIntent);
            finish();
            Log.i("MainActivity", "fAuth == null");
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.main_new_note_btn:
                Intent newIntent = new Intent(Courses.this, NewCourse.class);
                startActivity(newIntent);

                break;
        }

        return true;
    }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}

