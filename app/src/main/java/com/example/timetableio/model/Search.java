package com.example.timetableio.model;

import org.json.JSONObject;

import java.util.Date;

public class Search extends JSONObject {
    Batch batch;
    User user;
    Classroom classroom;

    Date startDate;
    Date endDate;

    public Search(Batch batch, User user, Classroom classroom, Date startDate, Date endDate) {
        this.batch = batch;
        this.user = user;
        this.classroom = classroom;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Search(Batch batch, User user, Date startDate, Date endDate) {
        this.batch = batch;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Search(User user, Date startDate, Date endDate) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Search(Classroom classroom, Date startDate, Date endDate) {
        this.classroom = classroom;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Search() {

    }

    public Search(Batch selectedBatch, Date startDate, Date endDate) {
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
