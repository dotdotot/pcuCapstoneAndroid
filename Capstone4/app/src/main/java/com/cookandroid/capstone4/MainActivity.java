package com.cookandroid.capstone4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    LoginFragment loginFragment;
    SignupFragment signupFragment;
    HomeFragment homeFragment;
    MoveFragment moveFragment;
    StatsFragment statsFragment;
    SettingFragment settingFragment;
    NavigationBarView navigationBarView;

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
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
                        return true;
                    case R.id.move:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, moveFragment).commit();
                        setTitle("이동");
                        return true;
                    case R.id.stats:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, statsFragment).commit();
                        setTitle("통계");
                        return true;
                    case R.id.setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, settingFragment).commit();
                        setTitle("설정");
                        return true;
                }
                return false;
            }
        });
    }

    public void onFragmentChange(int index) {
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
            setTitle("홈");
        }
    }

}