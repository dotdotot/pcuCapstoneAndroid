package com.example.capstone5;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import java.util.ArrayList;
import java.util.HashMap;

public class StatsFragment extends Fragment {
    String login_ID;
    String url_stat; String url_plus; String url_get;
    String old_room_arr[];
    String old_room_arr_kr[];
    String old_room_select;
    JSONArray jsonArray_room;
    JSONObject jsonObject_room_big;
    JSONObject jsonObject_room_small[];
    JSONArray jsonArray_stats;
    JSONObject jsonObject_stats_big;
    JSONObject jsonObject_stats_small[];
    ArrayAdapter<String> old_room_adapter;
    ArrayList<String> old_room_list;
    int temp[]; int humi[]; int dust[];
    double avg_temp = 0, avg_humi = 0, avg_dust = 0;
    int length;
    String start_date; String end_date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        NukeSSLCerts.nuke();

        //변수 생성
        Spinner stats_room = (Spinner)view.findViewById(R.id.stats_room);
        TextView start_date_text = (TextView)view.findViewById(R.id.start_date_text);
        ImageView start_date_btn = (ImageView)view.findViewById(R.id.start_date_btn);
        TextView end_date_text = (TextView)view.findViewById(R.id.end_date_text);
        ImageView end_date_btn = (ImageView)view.findViewById(R.id.end_date_btn);
        CalendarView start_cal = (CalendarView)view.findViewById(R.id.start_cal);
        CalendarView end_cal = (CalendarView)view.findViewById(R.id.end_cal);
        Button cancel_btn = (Button)view.findViewById(R.id.cancel_btn);
        Button ok_btn = (Button)view.findViewById(R.id.ok_btn);
        TextView stats_temp = (TextView)view.findViewById(R.id.stats_temp);
        TextView stats_humi = (TextView)view.findViewById(R.id.stats_humi);
        TextView stats_dust = (TextView)view.findViewById(R.id.stats_dust);
        url_stat = "https://203.250.133.171:8000/androidMethod/stat/" + login_ID;
        url_get = "https://203.250.133.171:8000/androidMethod/getRoom/" + login_ID;

        //초기 캘린더 안보이게
        start_cal.setVisibility(View.INVISIBLE);
        end_cal.setVisibility(View.INVISIBLE);

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
                            jsonObject_room_big = new JSONObject(response);
                            jsonArray_room = jsonObject_room_big.getJSONArray("result");
                            length = jsonArray_room.length();
                            jsonObject_room_small = new JSONObject[length];
                            old_room_arr = new String[length];
                            old_room_arr_kr = new String[length];
                            old_room_list = new ArrayList<String>();
                            for(int i = 0; i<length; i++) {
                                jsonObject_room_small[i] = jsonArray_room.getJSONObject(i);
                                old_room_arr[i] = jsonObject_room_small[i].getString("room_name");
                                old_room_arr_kr[i] = new String(old_room_arr[i].getBytes("8859_1"),"utf-8");
                                old_room_list.add(old_room_arr_kr[i]);
                            }
                            old_room_adapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, old_room_list);
                            stats_room.setAdapter(old_room_adapter);
                            stats_room.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        Log.d("", "\nstats failed");
                    }
                });
        request.setShouldCache(false);
        queue.add(request);

        //start_date_btn와 end_date_btn 눌렀을 때
        start_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_cal.setVisibility(View.VISIBLE);
            }
        });
        end_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                end_cal.setVisibility(View.VISIBLE);
            }
        });

        //시작 날짜와 종료 날짜
        final int[] start_year = new int[1]; final int[] start_month = new int[1]; final int[] start_day = new int[1];
        final int[] end_year = new int[1];   final int[] end_month = new int[1];   final int[] end_day = new int[1];
        start_year[0] = 0;  start_month[0] = 0; start_day[0] = 0;
        end_year[0] = 2100; end_month[0] = 12;  end_day[0] = 31;

        //start_cal과 end_cal에서 날짜를 선택했을 때
        start_cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                month++;
                start_year[0] = year;  start_month[0] = month;    start_day[0] = dayOfMonth;
                if(start_year[0]<=end_year[0] && start_month[0]<=end_month[0] && start_day[0]<=end_day[0]) {
                    start_date_text.setText(Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(dayOfMonth));
                    start_cal.setVisibility(View.INVISIBLE);
                    if(dayOfMonth<10)
                        start_date = Integer.toString(year) + "-" + Integer.toString(month) + "-0" + Integer.toString(dayOfMonth);
                    else
                        start_date = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(dayOfMonth);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "날짜 범위가 옳지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        end_cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                month++;
                end_year[0] = year;    end_month[0] =month;    end_day[0] = dayOfMonth;
                if(start_year[0]<=end_year[0] && start_month[0]<=end_month[0] && start_day[0]<=end_day[0]) {
                    end_date_text.setText(Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(dayOfMonth));
                    end_cal.setVisibility(View.INVISIBLE);
                    if(dayOfMonth<10)
                        end_date = Integer.toString(year) + "-" + Integer.toString(month) + "-0" + Integer.toString(dayOfMonth)+"";
                    else
                        end_date = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(dayOfMonth)+"";
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "날짜 범위가 옳지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //초기화 버튼 눌렀을 때
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                start_year[0] = 0;  start_month[0] = 0; start_day[0] = 0;
                end_year[0] = 2100; end_month[0] = 12;  end_day[0] = 31;
                start_date_text.setText(""); end_date_text.setText("");
                stats_temp.setText(""); stats_humi.setText(""); stats_dust.setText("");
            }
        });

        //조회 버튼 눌렀을 때
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //평균값 초기화
                avg_temp = 0;   avg_humi = 0;   avg_dust = 0;

                //실제 서버 연결
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("login_id", "test1");
                params.put("room", stats_room.getSelectedItem().toString());
                params.put("start_date", start_date+" 00:00:00");
                params.put("end_date", end_date+" 00:00:00");

                //전송 url 및 data 파싱
                String dataParse = "";
                String getUrl = "";
                dataParse = String.valueOf(params.toString());
                dataParse = dataParse.replaceAll("[{]","");
                dataParse = dataParse.replaceAll("[}]","");
                dataParse = dataParse.replaceAll("[,]","&");
                url_plus = "/" + old_room_select +"/" + start_date + "00:00:00" + "/" + end_date + "00:00:00";
                getUrl = url_stat + url_plus + "?" + dataParse;

                //response 객체 생성
                StringRequest request = new StringRequest(Request.Method.GET, getUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("", "\nstats success");
                                Log.d("","\n"+"["+"응답 전체 - "+String.valueOf(response.toString())+"]");
                                try {
                                    //값 받는 코드
                                    jsonObject_stats_big = new JSONObject(response);                //받은 값(전체)를 jsonObject_big에 저장
                                    jsonArray_stats = jsonObject_stats_big.getJSONArray("result");  //jsonObject_big에 result array값 저장
                                    length = jsonArray_stats.length();                              //jsonArray 길이 저장
                                    jsonObject_stats_small = new JSONObject[length];                //jsonObject_small 크기 초기화
                                    temp = new int[length];
                                    humi = new int[length];
                                    dust = new int[length];
                                    //개별 값 저장
                                    for(int i = 0; i<length; i++) {
                                        jsonObject_stats_small[i] = jsonArray_stats.getJSONObject(i);
                                        temp[i] = jsonObject_stats_small[i].getInt("temp");
                                        humi[i] = jsonObject_stats_small[i].getInt("humitiy");
                                        dust[i] = jsonObject_stats_small[i].getInt("finedust");
                                    }
                                    //평균 값 구하기
                                    for(int i = 0; i<length; i++) {
                                        avg_temp += temp[i];
                                        avg_humi += humi[i];
                                        avg_dust += dust[i];
                                    }
                                    avg_temp /= length;
                                    avg_humi /= length;
                                    avg_dust /= length;

                                    stats_temp.setText(String.valueOf(avg_temp));
                                    stats_humi.setText(String.valueOf(avg_humi));
                                    stats_dust.setText(String.valueOf(avg_dust));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                request.setShouldCache(false);
                queue.add(request);
            }
        });

        return view;
    }
}