package com.example.capstone5;

import static java.sql.DriverManager.println;

import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MoveFragment extends Fragment {
    String login_ID;
    int move_select;
    int move_time_select_int;    int move_room_select_int;
    String move_time_select_str; String move_room_select_str;
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move, container, false);
        NukeSSLCerts.nuke();

        //변수 생성
        RadioGroup move_group = view.findViewById(R.id.move_group);
        RadioButton move_auto = view.findViewById(R.id.move_auto);
        RadioButton move_time = view.findViewById(R.id.move_time);
        RadioButton move_random = view.findViewById(R.id.move_random);
        TextView move_time_text = view.findViewById(R.id.time_text);
        Spinner move_time_spi = view.findViewById(R.id.time_spi);
        Spinner move_room_spi = view.findViewById(R.id.room_spi);
        Button cancel_btn = view.findViewById(R.id.cancel_btn);
        Button ok_btn = view.findViewById(R.id.ok_btn);
        move_time_select_int = 0;
        move_room_select_int = 0;
        url = "https://203.250.133.171:8000/androidMethod/move/" + login_ID;

        //이전 설정값 기록
        if(move_auto.isChecked())       { move_select = 1; }
        else if(move_time.isChecked())  { move_select = 2; }
        else if(move_random.isChecked()){ move_select = 3; }

        //스피너 안보이게
        move_time_spi.setVisibility(View.INVISIBLE);
        move_time_text.setVisibility(View.INVISIBLE);
        move_room_spi.setVisibility(View.INVISIBLE);

        //라디오버튼
        move_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.move_auto:    //자동
                        move_time_spi.setVisibility(View.INVISIBLE); //안보이게
                        move_time_text.setVisibility(View.INVISIBLE);//안보이게
                        move_room_spi.setVisibility(View.INVISIBLE); //안보이게
                        break;
                    case R.id.move_time:    //시간별
                        move_time_spi.setVisibility(View.VISIBLE);   //보이게
                        move_time_text.setVisibility(View.VISIBLE);  //보이게
                        move_room_spi.setVisibility(View.INVISIBLE); //안보이게
                        break;
                    case R.id.move_random:  //임의 지정
                        move_time_spi.setVisibility(View.INVISIBLE); //안보이게
                        move_time_text.setVisibility(View.INVISIBLE);//안보이게
                        move_room_spi.setVisibility(View.VISIBLE);   //보이게
                        break;
                }
            }
        });

        //취소 버튼을 눌렀을 때
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(move_select == 1) {
                    move_auto.setChecked(true);
                }
                else if(move_select == 2) {
                    move_time.setChecked(true);
                }
                else if(move_select == 3) {
                    move_random.setChecked(true);
                }
                move_time_spi.setSelection(move_time_select_int);
                move_room_spi.setSelection(move_room_select_int);
            }
        });

        //확인 버튼을 눌렀을 때
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //선택 별 저장값
                if(move_auto.isChecked()) {
                    move_select = 1;
                    move_time_select_int = 0;
                    move_room_select_int = 0;
                    move_time_select_str = "null";
                    move_room_select_str = "null";
                }
                else if(move_time.isChecked()) {
                    move_select = 2;
                    move_time_spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            move_time_select_int = i; }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                    move_time_select_str = move_time_spi.getSelectedItem().toString();
                    move_room_select_int = 0;
                    move_room_select_str = "null";
                }
                else if(move_random.isChecked()) {
                    move_select = 3;
                    move_time_select_int = 0;
                    move_room_spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            move_room_select_int = i; }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) { }
                    });
                    move_room_select_str = move_room_spi.getSelectedItem().toString();
                    move_time_select_str = "null";
                }

                //실제 서버 통신
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                JSONObject InfoJSON = new JSONObject();
                try {
                    InfoJSON.put("move_select", move_select);
                    InfoJSON.put("time", move_time_select_str);
                    InfoJSON.put("room", move_room_select_str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String requestBody = String.valueOf(InfoJSON.toString());

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                println("응답 => " + response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                println("에러 => " + error.getMessage());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        return params;
                    }

                    public byte[] getBody() {
                        try {
                            if (requestBody != null && requestBody.length() > 0 && !requestBody.equals("")) {
                                return requestBody.getBytes("utf-8");
                            } else {
                                return null;
                            }
                        } catch (UnsupportedEncodingException e) {
                            return null;
                        }
                    }
                };
                request.setShouldCache(false);
                queue.add(request);


            }
        });

        return view;
    }
}