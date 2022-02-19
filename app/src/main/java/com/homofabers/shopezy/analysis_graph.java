package com.homofabers.shopezy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.os.Handler;

import com.homofabers.shopezy.helpers.GraphDailyTracking;

import processing.android.PFragment;

public class analysis_graph extends AppCompatActivity {

    FragmentContainerView sell_tracking_graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_graph);

        initializeSketch();
    }

       private void initializeSketch() {
        int[][] dataSet = new int[][]{
                {10, 20, 30, 40, 50, 60, 70}, {20, 30, 40, 50, 60, 70, 20}, {30, 40, 50, 60, 70, 80, 90},
                {10, 10, 30, 40, 10, 10, 20}, {20, 30, 40, 50, 60, 70, 80}, {30, 10, 50, 60, 10, 80, 20},
                {10, 30, 30, 40, 50, 60, 70}, {20, 10, 10, 50, 60, 70, 80}, {30, 10, 50, 10, 70, 80, 90},
                {10, 20, 30, 40, 50, 60, 70}, {10, 30, 40, 50, 30, 10, 20}, {30, 40, 50, 60, 70, 10, 90},
                {10, 20, 10, 40, 10, 60, 70}, {20, 30, 40, 10, 60, 70, 80}, {30, 40, 50, 30, 70, 80, 90},
                {10, 20, 30, 40, 10, 60, 20}, {20, 30, 40, 50, 60, 70, 20}, {30, 10, 50, 60, 10, 30, 20}
        };

        sell_tracking_graph = findViewById(R.id.sell_tracking_graph);

           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   GraphDailyTracking graphDailyTracking = new GraphDailyTracking(
                           sell_tracking_graph.getMeasuredWidth(), sell_tracking_graph.getMeasuredHeight(), dataSet, false);
                   PFragment fragment = new PFragment(graphDailyTracking);
                   fragment.setView(sell_tracking_graph,analysis_graph.this);
               }
           },100);








    }

}