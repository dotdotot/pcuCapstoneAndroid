package com.example.capstone5;

import static java.sql.DriverManager.println;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SignupFragment extends Fragment {
    MainActivity activity;
    int signup_pw_check_flag;
    int signup_flag;
    String url;
    String post_url;
    String[] signup_info_str = new String[6];
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

    //실제 서버 통신
    public void VolleyServer(String[] signup_info_str, String url) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JSONObject InfoJSON = new JSONObject();
        try {
            InfoJSON.put("nickname", signup_info_str[3]);
            InfoJSON.put("login_id", signup_info_str[0]);
            InfoJSON.put("login_pw", signup_info_str[1]);
            InfoJSON.put("name", signup_info_str[2]);
            InfoJSON.put("email", signup_info_str[4]);
            InfoJSON.put("phone", signup_info_str[5]);
        } catch (JSONException e) { e.printStackTrace(); }
        final String requestBody = String.valueOf(InfoJSON.toString());

        post_url = url + signup_info_str[0] + "/" + signup_info_str[1] + "/" + signup_info_str[3] + "/" + signup_info_str[2] + "/" + signup_info_str[4] + "/" + signup_info_str[5];
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, post_url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        println("응답 => " + response);
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러 => " + error.getMessage());
                    }}
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
            public byte[] getBody() {
                try {
                    if(requestBody!=null && requestBody.length()>0 && !requestBody.equals("")){
                        return requestBody.getBytes("utf-8"); }
                    else { return null; }
                } catch (UnsupportedEncodingException e) { return null; }
            }
        };
        request.setShouldCache(false);
        queue.add(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        NukeSSLCerts.nuke();

        //변수 생성
        TextView signup_back_btn = view.findViewById(R.id.signup_back_btn);
        EditText[] signup_info_edt = new EditText[7];
        signup_info_edt[0] = view.findViewById(R.id.signup_id);             signup_info_edt[1] = view.findViewById(R.id.signup_pw);
        signup_info_edt[2] = view.findViewById(R.id.signup_pw_check_edit);  signup_info_edt[3] = view.findViewById(R.id.signup_name);
        signup_info_edt[4] = view.findViewById(R.id.signup_nickname);       signup_info_edt[5] = view.findViewById(R.id.signup_mail);
        signup_info_edt[6] = view.findViewById(R.id.signup_phone);
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
        signup_pw_check_flag = 0;   //비밀번호 확인 버튼 눌렀는지
        signup_flag = 0;            //빈 칸의 갯수
        url = "https://203.250.133.171:8000/register/";

        //뒤로가기 버튼 눌렀을 때
        signup_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onFragmentChange(0, "");//로그인 화면으로 이동
                for(int i = 0; i < 7; i++) {            //입력한 정보들 전부 초기화
                    signup_info_edt[i].setText("");     //입력칸 비우기
                    signup_pw_check_notice.setText(""); //공지칸 비우기
                }
            }
        });

        //비밀번호 확인 버튼 눌렀을 때
        signup_pw_check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //비밀번호 입력칸과 확인 칸의 입력값이 같을 때
                if(signup_info_edt[1].getText().toString().equals(signup_info_edt[2].getText().toString())) {
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
                    if (signup_info_edt[i].getText().toString().equals("")) {   //빈 칸일 때
                        signup_flag++;                                          //빈 칸 수 증가
                        signup_info_num[i] = 0;                                 //비어있다.
                    }
                    else
                        signup_info_num[i] = 1;                                 //채워졌다.
                }
                //비밀번호 확인 버튼을 안눌렀다면
                if(signup_pw_check_flag == 0)
                    Toast.makeText(getActivity().getApplicationContext(), "비밀번호를 확인 하십시오.", Toast.LENGTH_SHORT).show();

                //빈 칸이 없을 경우 (회원가입 성공)
                if(signup_flag == 0 && signup_pw_check_flag == 1) {
                    signup_info_str[0] = signup_info_edt[0].getText().toString();
                    signup_info_str[1] = signup_info_edt[1].getText().toString();
                    signup_info_str[2] = signup_info_edt[3].getText().toString();
                    signup_info_str[3] = signup_info_edt[4].getText().toString();
                    signup_info_str[4] = signup_info_edt[5].getText().toString();
                    signup_info_str[5] = signup_info_edt[6].getText().toString();
                    activity.onFragmentChange(0, "");//로그인 화면으로 이동
                    VolleyServer(signup_info_str, url);
                    for(int i = 0; i < 7; i++) {            //입력한 정보들 전부 초기화
                        signup_info_edt[i].setText("");     //입력칸 비우기
                        signup_pw_check_notice.setText(""); //공지칸 비우기
                    }
                }

                //빈 칸이 있을 경우 (회원가입 실패)
                else {
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