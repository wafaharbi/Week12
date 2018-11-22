package com.example.wafa.studentapp;

public class CourseModel {

    public  String name , coursename , quizmark, midmark, markfinal , mark;

    public CourseModel(String name, String coursename, String quizmark, String midmark, String markfinal ,  String mark) {
        this.name = name;
        this.coursename = coursename;
        this.quizmark = quizmark;
        this.midmark = midmark;
        this.markfinal = markfinal;
        this.mark = mark;
    }

    public CourseModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getQuizmark() {
        return quizmark;
    }

    public void setQuizmark(String quizmark) {
        this.quizmark = quizmark;
    }

    public String getMidmark() {
        return midmark;
    }

    public void setMidmark(String midmark) {
        this.midmark = midmark;
    }

    public String getMarkfinal() {
        return markfinal;
    }

    public void setMarkfinal(String markfinal) {
        this.markfinal = markfinal;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
