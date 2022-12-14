package com.example.capstone5;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    LoginFragment loginFragment;
    SignupFragment signupFragment;
    HomeFragment homeFragment;
    MoveFragment moveFragment;
    StatsFragment statsFragment;
    SettingFragment settingFragment;
    NavigationBarView navigationBarView;
    String login_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("홈");

        //변수 생성
        loginFragment = new LoginFragment();
        signupFragment = new SignupFragment();
        homeFragment = new HomeFragment();
        moveFragment = new MoveFragment();
        statsFragment = new StatsFragment();
        settingFragment = new SettingFragment();
        navigationBarView = findViewById(R.id.bottom_navigationview);

        //초기화면 설정
        navigationBarView.setVisibility(View.INVISIBLE); //네비게이션 바 안보이게
        getSupportFragmentManager().beginTransaction().replace(R.id.containers, loginFragment).commit(); //로그인화면으로

        //네비게이션바 설정
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        setTitle("홈");
                        homeFragment.login_ID = login_id;
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
                        return true;
                    case R.id.move:
                        setTitle("이동");
                        moveFragment.login_ID = login_id;
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, moveFragment).commit();
                        return true;
                    case R.id.stats:
                        setTitle("통계");
                        statsFragment.login_ID = login_id;
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, statsFragment).commit();
                        return true;
                    case R.id.setting:
                        setTitle("설정");
                        settingFragment.login_ID = login_id;
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, settingFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    //화면 전환 (로그인/회원가입/홈)
    public void onFragmentChange(int index, String id) {
        if(index == 0) {         //로그인 화면
            getSupportFragmentManager().beginTransaction().replace(R.id.containers, loginFragment).commit();
            navigationBarView.setVisibility(View.INVISIBLE); //네비게이션 바 안보이게
            setTitle("로그인");
        }
        else if(index == 1) {    //회원가입 화면
            getSupportFragmentManager().beginTransaction().replace(R.id.containers, signupFragment).commit();
            setTitle("회원 가입");
        }
        else if(index == 2) {   //홈 화면
            getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
            navigationBarView.setVisibility(View.VISIBLE);   //네비게이션 바 보이게
            homeFragment.login_ID = id;
            setTitle("홈");
        }
    }

    //로그인 한 아이디 저장
    public void setId(String id) { login_id = id; }
}