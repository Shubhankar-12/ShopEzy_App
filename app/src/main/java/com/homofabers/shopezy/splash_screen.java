package com.homofabers.shopezy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.homofabers.shopezy.model.UserSetting;

public class splash_screen extends AppCompatActivity {

    UserSetting userSetting;
    String selectedTheme ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // create a setting object
        userSetting = new UserSetting();
        // load the setting from shared preference
        userSetting.getStoredSetting(this);

        // get theme selected from user setting
        if(userSetting.getTheme_selected().equals("Dark") && AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else if(userSetting.getTheme_selected().equals("Light") && AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        startSplashAnimation(userSetting.getUser_registered());

    }

    private void startSplashAnimation(Boolean userState){

        // run the code after an interval of 2 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(userState){
                    intent = new Intent(splash_screen.this , Dashboard.class);
                }else{
                    intent = new Intent(splash_screen.this , language_selection.class);
                }

                startActivity(intent);
                finish();

            }
        } , 2000);

    }

}