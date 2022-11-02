package com.cookandroid.capstone4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MoveFragment extends Fragment {
    int move_select;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move, container, false);

        //변수 생성
        RadioGroup move_group = view.findViewById(R.id.move_group);
        RadioButton move_auto = view.findViewById(R.id.move_auto);
        RadioButton move_time = view.findViewById(R.id.move_time);
        RadioButton move_random = view.findViewById(R.id.move_random);
        Spinner time_spi = view.findViewById(R.id.time_spi);
        Spinner room_spi = view.findViewById(R.id.room_spi);
        Button cancel_btn = view.findViewById(R.id.cancel_btn);
        Button ok_btn = view.findViewById(R.id.ok_btn);

        //이전 설정값 기록
        if(move_auto.isChecked())       { move_select = 1; }
        else if(move_time.isChecked())  { move_select = 2; }
        else if(move_random.isChecked()){ move_select = 3; }

        //스피너 안보이게
        time_spi.setVisibility(View.INVISIBLE);
        room_spi.setVisibility(View.INVISIBLE);

        //라디오버튼
        move_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.move_auto:    //자동
                        time_spi.setVisibility(View.INVISIBLE); //안보이게
                        room_spi.setVisibility(View.INVISIBLE); //안보이게
                        break;
                    case R.id.move_time:    //시간별
                        time_spi.setVisibility(View.VISIBLE);   //보이게
                        room_spi.setVisibility(View.INVISIBLE); //안보이게
                        break;
                    case R.id.move_random:  //임의 지정
                        time_spi.setVisibility(View.INVISIBLE); //안보이게
                        room_spi.setVisibility(View.VISIBLE);   //보이게
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
            }
        });

        //확인 버튼을 눌렀을 때
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(move_auto.isChecked()) {
                    move_select = 1;
                }
                else if(move_time.isChecked()) {
                    move_select = 2;
                }
                else if(move_random.isChecked()) {
                    move_select = 3;
                }
            }
        });

        return view;
    }
}