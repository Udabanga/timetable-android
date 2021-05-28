package com.example.timetableio.model;

public class Batch {
    private long id;
    private String faculty;
    private int year;
    private String semester;
    private String batchCode;


    public Batch() {

    }

    public Batch(String faculty, int year, String semester, String batchCode) {
        this.faculty = faculty;
        this.year = year;
        this.semester = semester;
        this.batchCode = batchCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    @Override
    public String toString() {
        return batchCode;

    }
}
