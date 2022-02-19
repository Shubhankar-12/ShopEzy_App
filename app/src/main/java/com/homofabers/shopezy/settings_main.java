package com.homofabers.shopezy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class settings_main extends AppCompatActivity {

    ImageView back_btn;
    ConstraintLayout account_setting , invoice_setting , analytics_setting , stocks_setting ,
    personalization_setting , integration_setting , notification_setting , about_us_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_main);

        initializeVars();
    }

    private void initializeVars() {

        back_btn = findViewById(R.id.back_button);
        account_setting = findViewById(R.id.account_setting);
        invoice_setting = findViewById(R.id.invoice_setting);
        analytics_setting = findViewById(R.id.analytics_setting);
        stocks_setting = findViewById(R.id.stocks_setting);
        personalization_setting = findViewById(R.id.personalization_setting);
        integration_setting = findViewById(R.id.integration_setting);
        notification_setting = findViewById(R.id.notification_setting);
        about_us_setting = findViewById(R.id.about_us_setting);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        account_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings_main.this , settings_account.class);
                startActivity(intent);
            }
        });

        invoice_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings_main.this , FeatureMissing.class);
                startActivity(intent);
            }
        });

        analytics_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings_main.this , FeatureMissing.class);
                startActivity(intent);
            }
        });

        stocks_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings_main.this , FeatureMissing.class);
                startActivity(intent);
            }
        });

        personalization_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings_main.this , language_selection.class);
                startActivity(intent);
            }
        });

        integration_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings_main.this , FeatureMissing.class);
                startActivity(intent);
            }
        });

        notification_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings_main.this , FeatureMissing.class);
                startActivity(intent);
            }
        });

        about_us_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settings_main.this , privacy_policy.class);
                startActivity(intent);
            }
        });

    }
}