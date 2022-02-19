package com.homofabers.shopezy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void open_registration(View view) {
        Intent intent = new Intent(MainActivity.this , RegistrationScreen.class);
        startActivity(intent);
    }

    public void openAzure(View view) {
        Intent intent = new Intent(MainActivity.this , azure_demo.class);
        startActivity(intent);
    }

    public void openBarcode(View view) {
        Intent intent = new Intent(MainActivity.this , barcodeScannerAc.class);
        startActivity(intent);
    }

    public void openGraphs(View view) {
        Intent intent = new Intent(MainActivity.this , analysis_graph.class);
        startActivity(intent);
    }

    public void openSplash(View view) {
        Intent intent = new Intent(MainActivity.this , splash_screen.class);
        startActivity(intent);
    }

    public void openDashboard(View view) {
        Intent intent = new Intent(this , Dashboard.class);
        startActivity(intent);
    }
}