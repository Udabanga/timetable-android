package com.example.timetableio.model;

import java.util.List;

public class Module {
    private long id;
    private String moduleName;
    private List<Schedule> schedules;


    public Module() {
    }

    public Module(String moduleName) {
        this.moduleName = moduleName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", moduleName='" + moduleName + '\'' +
                '}';
    }
}
