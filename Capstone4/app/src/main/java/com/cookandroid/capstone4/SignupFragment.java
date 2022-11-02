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

import org.json.JSONArray;


public class SignupFragment extends Fragment {
    MainActivity activity;
    int signup_pw_check_flag;
    int signup_flag;
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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        //변수 생성
        TextView signup_back_btn = view.findViewById(R.id.signup_back_btn);
        EditText[] signup_info = new EditText[7];
        signup_info[0] = view.findViewById(R.id.signup_id);             signup_info[1] = view.findViewById(R.id.signup_pw);
        signup_info[2] = view.findViewById(R.id.signup_pw_check_edit);  signup_info[3] = view.findViewById(R.id.signup_name);
        signup_info[4] = view.findViewById(R.id.signup_nickname);       signup_info[5] = view.findViewById(R.id.signup_mail);
        signup_info[6] = view.findViewById(R.id.signup_phone);
        TextView[] signup_text = new TextView[7];
        signup_text[0] = view.findViewById(R.id.signup_id_text);        signup_text[1] = view.findViewById(R.id.signup_pw_text);
        signup_text[2] = view.findViewById(R.id.signup_pw_check_text);  signup_text[3] = view.findViewById(R.id.signup_name_text);
        signup_text[4] = view.findViewById(R.id.signup_nickname_text);  signup_text[5] = view.findViewById(R.id.signup_mail_text);
        signup_text[6] = view.findViewById(R.id.signup_phone_text);
        int[] signup_info_num = new int[7];
        for(int i = 0; i < 7; i++)
            signup_info_num[i] = 0;
        TextView signup_pw_check_notice = view.findViewById(R.id.signup_pw_check_notice);
        Button signup_pw_check_btn = view.findViewById(R.id.signup_pw_check_btn);
        Button signup_btn = view.findViewById(R.id.signup_btn);
        signup_pw_check_flag = 0;   //체크 버튼 눌렀는지
        signup_flag = 0;            //빈 칸의 갯수

        //뒤로가기 버튼 눌렀을 때
        signup_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onFragmentChange(0);      //로그인 화면으로 이동
                for(int i = 0; i < 7; i++) {            //입력한 정보들 전부 초기화
                    signup_info[i].setText("");         //입력칸 비우기
                    signup_pw_check_notice.setText(""); //공지칸 비우기
                }
            }
        });

        //비밀번호 확인 버튼 눌렀을 때
        signup_pw_check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //비밀번호 입력칸과 확인 칸의 입력값이 같을 때
                if(signup_info[1].getText().toString().equals(signup_info[2].getText().toString())) {
                    signup_pw_check_flag = 1;
                    signup_pw_check_notice.setText("비밀번호가 같습니다.");
                    signup_pw_check_notice.setTextColor(getResources().getColor(R.color.blue));
                }
                else {
                    signup_pw_check_flag = 0;
                    signup_pw_check_notice.setText("비밀번호가 다릅니다.");
                    signup_pw_check_notice.setTextColor(getResources().getColor(R.color.red));
                }
            }
        });

        //회원가입 완료 버튼 눌렀을 때
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //빈 칸 표시 초기화
                signup_flag = 0;
                //빈 칸 갯수 검사
                for(int i = 0; i < 7; i++) {
                    if (signup_info[i].getText().toString().equals("")) {   //빈 칸일 때
                        signup_flag++;                                      //빈 칸 수 증가
                        signup_info_num[i] = 0;                             //비어있다.
                    }
                    else
                        signup_info_num[i] = 1;                             //채워졌다.
                }
                //비밀번호 확인 버튼을 안눌렀다면
                if(signup_pw_check_flag == 0)
                    Toast.makeText(getActivity().getApplicationContext(), "비밀번호 확인을 하십시오.", Toast.LENGTH_SHORT).show();
                //빈 칸이 없을 경우 (회원가입 성공)
                if(signup_flag == 0 && signup_pw_check_flag == 1) {
                    activity.onFragmentChange(0); //로그인 화면으로 이동
                }
                //빈 칸이 있을 경우 (회원가입 실패)
                else {                                                         //그냥 빈 칸이 있을 경우
                    for (int i = 0; i < 7; i++) {
                        if (signup_info_num[i] == 0)
                            signup_text[i].setTextColor(getResources().getColor(R.color.red));
                        else
                            signup_text[i].setTextColor(getResources().getColor(R.color.black));
                    }
                }
            }
        });


        return view;
    }
}