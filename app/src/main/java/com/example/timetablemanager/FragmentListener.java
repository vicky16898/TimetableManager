package com.example.timetablemanager;

public interface FragmentListener {
    void populateList(String subjectName, String time, String day);
    String getFragmentName();
}
