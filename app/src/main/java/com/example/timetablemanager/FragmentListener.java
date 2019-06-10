package com.example.timetablemanager;

public interface FragmentListener {
    void populateList(long dbId, String subjectName, String time, String day);
    void updateList(int i);
    String getFragmentName();
}
