package com.cookandroid.capstone4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StatsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        //변수 생성
        Spinner spiRoom = (Spinner)view.findViewById(R.id.spiRoomStats);
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
                    start_date_text.setText(Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(dayOfMonth));
                    start_cal.setVisibility(View.INVISIBLE);
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
                    end_date_text.setText(Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(dayOfMonth));
                    end_cal.setVisibility(View.INVISIBLE);
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

            }
        });

        return view;
    }
}