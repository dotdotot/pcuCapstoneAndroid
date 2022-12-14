package com.example.capstone5;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class SettingFragment extends Fragment {
    MainActivity activity;
    int length;
    String login_ID;
    String url_get;
    String url_put;
    String old_room_arr[];
    String old_room_arr_kr[];
    String old_room_select;
    JSONArray jsonArray;
    JSONObject jsonObject_big;
    JSONObject jsonObject_small[];
    ArrayAdapter<String>  old_room_adapter;
    ArrayList<String> old_room_list;


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
        Button set_rename = view.findViewById(R.id.set_rename);
        Button set_logout = view.findViewById(R.id.set_logout);
        Button rename_cancel = view.findViewById(R.id.rename_cancel);
        Button rename_ok = view.findViewById(R.id.rename_ok);
        LinearLayout rename_layout = view.findViewById(R.id.rename_layout);
        Spinner room_before = view.findViewById(R.id.room_before);
        EditText room_after = view.findViewById(R.id.room_after);
        url_get = "https://203.250.133.171:8000/androidMethod/getRoom/" + login_ID;
        url_put = "https://203.250.133.171:8000/androidMethod/update_roomName/" + login_ID;

        //안보이게 설정
        rename_layout.setVisibility(View.INVISIBLE);

        //방이름 바꾸기 버튼을 눌렀을 때
        set_rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rename_layout.setVisibility(View.VISIBLE);
                //실제 서버 연결
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                HashMap<String, String> params = new HashMap<String, String>();
                //전송 url 및 data 파싱
                String dataParse = "";
                String getUrl = "";
                dataParse = String.valueOf(params.toString());
                dataParse = dataParse.replaceAll("[{]","");
                dataParse = dataParse.replaceAll("[}]","");
                dataParse = dataParse.replaceAll("[,]","&");
                getUrl = url_get + "?" + dataParse;
                getUrl = getUrl.replaceAll(" ","");
                //response 객체 생성
                StringRequest request = new StringRequest(Request.Method.GET, getUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.d("", "\nsetting success");
                                    Log.d("","\n"+"["+"응답 전체 - "+String.valueOf(response.toString())+"]");
                                    jsonObject_big = new JSONObject(response);
                                    jsonArray = jsonObject_big.getJSONArray("result");
                                    length = jsonArray.length();
                                    jsonObject_small = new JSONObject[length];
                                    old_room_arr = new String[length];
                                    old_room_arr_kr = new String[length];
                                    old_room_list = new ArrayList<String>();
                                    for(int i = 0; i<length; i++) {
                                        jsonObject_small[i] = jsonArray.getJSONObject(i);
                                        old_room_arr[i] = jsonObject_small[i].getString("room_name");
                                        old_room_arr_kr[i] = new String(old_room_arr[i].getBytes("8859_1"),"utf-8");
                                        old_room_list.add(old_room_arr_kr[i]);
                                    }
                                    old_room_adapter = new ArrayAdapter<>(activity.getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, old_room_list);
                                    room_before.setAdapter(old_room_adapter);
                                    room_before.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            old_room_select = old_room_list.get(i);
                                        }
                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {}
                                    });
                                } catch (JSONException | UnsupportedEncodingException e) { e.printStackTrace(); }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("", "\nsetting failed");
                            }
                        });
                request.setShouldCache(false);
                queue.add(request);
        }});

        //방 이름 바꾸기 취소 눌렀을 때
        rename_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                room_after.setText("");
                rename_layout.setVisibility(View.INVISIBLE);
            }
        });

        //방 이름 바꾸기 확인 눌렀을 때
        rename_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rename_layout.setVisibility(View.INVISIBLE);
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
                getUrl = url_put + "/" + old_room_select + "/" + room_after.getText() + "?" + dataParse;
                room_after.setText("");

                //response 객체 생성
                StringRequest request = new StringRequest(Request.Method.PUT, getUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("", "\nsetting success");
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

        //로그아웃 버튼을 눌렀을 때
        set_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { activity.onFragmentChange(0, ""); }
        });

        return view;
    }
}