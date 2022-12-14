package com.example.capstone5;

import static java.sql.DriverManager.println;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {
    MainActivity activity;
    String url;
    JSONObject jsonObject;
    String success;
    String TRUE = new String("TRUE");
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
        NukeSSLCerts.nuke();

        //변수 생성
        EditText login_id_edt = view.findViewById(R.id.login_id);
        EditText login_pw_edt = view.findViewById(R.id.login_pw);
        TextView login_notice = view.findViewById(R.id.login_notice);
        Button login_btn = view.findViewById(R.id.login_btn);
        TextView login_signup_btn = view.findViewById(R.id.login_signup_btn);
        url = "https://203.250.133.171:8000/login/";

        //로그인 버튼을 눌렀을 때
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //서버 통신
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

                HashMap<String, String> params = new HashMap<String, String>();

                //전송 url 및 data 파싱
                String dataParse = "";
                String getUrl = "";
                dataParse = String.valueOf(params.toString());
                dataParse = dataParse.replaceAll("[{]","");
                dataParse = dataParse.replaceAll("[}]","");
                dataParse = dataParse.replaceAll("[,]","&");
                getUrl = url + login_id_edt.getText().toString() + "/" + login_pw_edt.getText().toString() + "?" + dataParse;

                //response 객체 생성
                StringRequest request = new StringRequest(Request.Method.GET, getUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("", "\nlogin success");
                                try {
                                    jsonObject = new JSONObject(response);
                                    success = jsonObject.getString("result");
                                    if(success.equals(TRUE)) {
                                        activity.onFragmentChange(2, login_id_edt.getText().toString()); //홈 화면으로 이동
                                        activity.setId(login_id_edt.getText().toString());
                                        //모든 칸 비우기
                                        login_notice.setText("");
                                        login_id_edt.setText("");
                                        login_pw_edt.setText("");
                                    }
                                    else {
                                        login_notice.setText("아이디 혹은 비밀번호를 잘못 입력했습니다.");
                                        login_notice.setTextColor(getResources().getColor(R.color.red));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) { }
                        });
                request.setShouldCache(false);
                queue.add(request);


            }
        });

        //회원가입 버튼 눌렀을 때
        login_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onFragmentChange(1, "");                     //홈 화면으로 이동
                login_id_edt.setText("");   login_pw_edt.setText("");   //입력칸 비우기
            }
        });

        return view;
    }
}