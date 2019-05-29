package com.example.timetablemanager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyFragment extends Fragment implements FragmentListener {
    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_PARAM = "param";
    public String fragmentName;


    @BindView(R.id.list)
    ListView list;


    public MyFragment() {
    }

    public static MyFragment newInstance(String param) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        ButterKnife.bind(this, view);
        fragmentName = getArguments().getString(ARG_PARAM);
        return view;
    }


    @Override
    public void action() {
        Log.e("FRAGMENT_NAME", fragmentName);
    }
}
