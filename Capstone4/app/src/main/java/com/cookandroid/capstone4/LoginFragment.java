package com.cookandroid.capstone4;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //변수 생성
        EditText login_id = view.findViewById(R.id.login_id);
        EditText login_pw = view.findViewById(R.id.login_pw);
        Button login_btn = view.findViewById(R.id.login_btn);
        TextView login_signup_btn = view.findViewById(R.id.login_signup_btn);

        //로그인 버튼을 눌렀을 때
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onFragmentChange(2);              //홈 화면으로 이동
                login_id.setText("");   login_pw.setText("");   //입력칸 비우기
            }
        });

        //회원가입 버튼 눌렀을 때
        login_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onFragmentChange(1);              //홈 화면으로 이동
                login_id.setText("");   login_pw.setText("");   //입력칸 비우기
            }
        });

        return view;
    }
}