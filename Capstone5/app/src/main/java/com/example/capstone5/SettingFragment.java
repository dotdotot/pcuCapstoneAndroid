package com.example.capstone5;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {
    MainActivity activity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity)getActivity();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        //변수 생성
        Button set_logout = view.findViewById(R.id.set_logout);

        //로그아웃 버튼을 눌렀을 때
        set_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { activity.onFragmentChange(0); }
        });

        return view;
    }
}