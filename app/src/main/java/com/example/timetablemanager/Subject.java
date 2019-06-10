package com.example.timetablemanager;

public class Subject {
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

    public String getTime() {
        return time;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }
}
