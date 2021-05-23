package com.example.timetableio.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Schedule {
    private long id;
    private Date date;
    private Date startTime;
    private Date endTime;
    private Classroom classroom;
    private Module module;
    private User user;
    private Set<Batch> batches = new HashSet<>();

    public Schedule() {
    }

    public Schedule(Date date, Date startTime, Date endTime, Classroom classroom, Module module, User user, Set<Batch> batches) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroom = classroom;
        this.module = module;
        this.user = user;
        this.batches = batches;
    }

    public Schedule(long id, Date date, Date startTime, Date endTime, Classroom classroom, Module module, User user, Set<Batch> batches) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroom = classroom;
        this.module = module;
        this.user = user;
        this.batches = batches;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Batch> getBatches() {
        return batches;
    }

    public void setBatches(Set<Batch> batches) {
        this.batches = batches;
    }
}
