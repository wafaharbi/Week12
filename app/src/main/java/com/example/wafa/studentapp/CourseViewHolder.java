package com.example.wafa.studentapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CourseViewHolder extends RecyclerView.ViewHolder{


    View mView;
    TextView textTitle, textTime;
    CardView noteCard;


    public CourseViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        textTitle = mView.findViewById(R.id.course_title);
        textTime = mView.findViewById(R.id.course_time);
        noteCard = mView.findViewById(R.id.course_card);
    }

    public void setCourseTitle(String title) {

        textTitle.setText(title);
    }

    public void setCourseTime(String time) {

        textTime.setText(time);
    }


}
