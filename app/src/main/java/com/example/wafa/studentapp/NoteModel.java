package com.example.wafa.studentapp;

public class NoteModel {



    public String noteTitle;
    public String noteTime;

    public String name;

    public NoteModel() {

    }
    //constructor


    public NoteModel(String noteTitle, String noteTime , String name) {
        this.noteTitle = noteTitle;
        this.noteTime = noteTime;
        this.name = name;
    }

    public String getNoteTitle()
    {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteTime() {
        return noteTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNoteTime(String noteTime) { this.noteTime = noteTime;


    }
}


