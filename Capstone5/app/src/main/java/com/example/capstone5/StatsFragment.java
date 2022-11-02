package com.example.capstone5;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashMap;

public class StatsFragment extends Fragment {
    String url; String url_plus;
    JSONArray jsonArray;
    JSONObject jsonObject;
    int temp; int humi; int dust;
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
        url = "https://203.250.133.171:8000/stat/";

        //초기 캘린더 안보이게
        start_cal.setVisibility(View.INVISIBLE);
        end_cal.setVisibility(View.INVISIBLE);

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
                url_plus = "test1" + "/" + stats_room.getSelectedItem().toString() +"/" + start_date + "00:00:00" + "/" + end_date + "00:00:00";
                getUrl = url + url_plus + "?" + dataParse;

                //response 객체 생성
                StringRequest request = new StringRequest(Request.Method.GET, getUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("", "\nstats success");
                                try {
                                    jsonArray = new JSONArray(response);
                                    Log.d("", "length=" + jsonArray.length());
                                    jsonObject = jsonArray.getJSONObject(0);
                                    temp = jsonObject.getInt("temp");
                                    stats_temp.setText(String.valueOf(temp));
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