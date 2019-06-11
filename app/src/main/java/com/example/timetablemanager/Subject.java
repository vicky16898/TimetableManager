package com.example.timetablemanager;

import java.io.Serializable;

public class Subject implements Serializable {
    private long element_id;
    private String subjectName;
    private String time;
    private String dayOfWeek;

    public Subject(long element_id, String subjectName, String time, String dayOfWeek) {
        this.element_id = element_id;
        this.subjectName = subjectName;
        this.time = time;
        this.dayOfWeek = dayOfWeek;
    }

    public long getElement_id() {
        return element_id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

}
