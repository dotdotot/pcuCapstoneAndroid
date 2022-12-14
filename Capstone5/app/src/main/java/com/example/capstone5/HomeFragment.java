package com.example.capstone5;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class HomeFragment extends Fragment {
    String login_ID;
    String url;
    JSONObject jsonObject;
    double temp; int humi; int dust;
    String led_color;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        NukeSSLCerts.nuke();

        //변수 생성
        ImageView LED_red = (ImageView)view.findViewById(R.id.LED_red);
        ImageView LED_green = (ImageView)view.findViewById(R.id.LED_green);
        ImageView LED_yellow = (ImageView)view.findViewById(R.id.LED_yellow);
        TextView home_loc = (TextView)view.findViewById(R.id.home_loc);
        TextView home_dust = (TextView)view.findViewById(R.id.home_dust);
        TextView home_temp = (TextView)view.findViewById(R.id.home_temp);
        TextView home_humi = (TextView)view.findViewById(R.id.home_humi);
        url = "https://203.250.133.171:8000/androidMethod/home/" + login_ID;

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
        getUrl = url + "?" + dataParse;
        getUrl = getUrl.replaceAll(" ","");

        //response 객체 생성
        StringRequest request = new StringRequest(Request.Method.GET, getUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("", "\nhome success");
                        try {
                            jsonObject = new JSONObject(response);
                            Log.d("","\n"+"["+"응답 전체 - "+String.valueOf(response.toString())+"]");
                            temp = jsonObject.getDouble("temp");
                            humi = jsonObject.getInt("humidity");
                            dust = jsonObject.getInt("finedust");
                            led_color = jsonObject.getString("ledcolor");
                            home_temp.setText(String.valueOf(temp));
                            home_humi.setText(String.valueOf(humi));
                            home_dust.setText(String.valueOf(dust));
                            home_loc.setText("거실");
                            //led 컬러별 설정
                            //빨강일 때
                            if(led_color.equals("red")) {
                                LED_red.setVisibility(View.VISIBLE);
                                LED_green.setVisibility(View.INVISIBLE);
                                LED_yellow.setVisibility(View.INVISIBLE);
                            }
                            //초록일 때
                            else if(led_color.equals("green")){
                                LED_red.setVisibility(View.INVISIBLE);
                                LED_green.setVisibility(View.VISIBLE);
                                LED_yellow.setVisibility(View.INVISIBLE);
                            }
                            //노랑일 때
                            else if(led_color.equals("yellow")) {
                                LED_red.setVisibility(View.INVISIBLE);
                                LED_green.setVisibility(View.INVISIBLE);
                                LED_yellow.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("", "\nhome failed");
                    }
                });
        request.setShouldCache(false);
        queue.add(request);

        return view;
    }
}