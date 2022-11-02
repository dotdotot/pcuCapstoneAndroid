package com.cookandroid.capstone4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //변수 생성
        TextView home_loc = (TextView)view.findViewById(R.id.home_loc);
        TextView home_dust = (TextView)view.findViewById(R.id.home_dust);
        TextView home_temp = (TextView)view.findViewById(R.id.home_temp);
        TextView home_humi = (TextView)view.findViewById(R.id.home_humi);

        return view;
    }
}