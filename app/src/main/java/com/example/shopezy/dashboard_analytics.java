package com.homofabers.shopezy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.homofabers.shopezy.helpers.GraphDailyTracking;
import com.homofabers.shopezy.helpers.PeriodicGraph;

import processing.android.PFragment;

public class dashboard_analytics extends Fragment {

    FragmentContainerView sell_tracking_graph , periodic_sell_graph;
    LottieAnimationView lottieAnimationView;
    ScrollView graphContainer;
    TextView emptyText;

    public dashboard_analytics() {
    }

    public static dashboard_analytics newInstance() {
        dashboard_analytics fragment = new dashboard_analytics();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard_analytics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeSketch();
        initializeSketch2();
        initializeData();
    }

    private void initializeData() {

        lottieAnimationView = getActivity().findViewById(R.id.notEnoughData1);
        emptyText = getActivity().findViewById(R.id.notdatatxt);
        graphContainer = getActivity().findViewById(R.id.graphContainer);
        lottieAnimationView.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                graphContainer.setVisibility(View.GONE);
                emptyText.setVisibility(View.VISIBLE);
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
            }
        },600);
    }

    private void initializeSketch2() {
        periodic_sell_graph = getActivity().findViewById(R.id.periodGraph);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Boolean darkMode = false;
                if(AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES){
                    darkMode = false;
                }else{ darkMode = true; }

                int[][] dataSet = new int[][]{
                        {10, 60, 30, 40, 55, 60, 70}, {20, 30, 65, 50, 43, 70, 20}, {25, 40, 50, 60, 75, 80, 10}
                };

                PeriodicGraph periodicGraph = new PeriodicGraph(periodic_sell_graph.getMeasuredWidth() ,
                        periodic_sell_graph.getMeasuredHeight() , dataSet , darkMode);
                PFragment fragment = new PFragment(periodicGraph);
                fragment.setView(periodic_sell_graph, getActivity());
            }
        },100);

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

        sell_tracking_graph = getActivity().findViewById(R.id.sell_tracking_graph);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Boolean darkMode = false;
                if(AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES){
                    darkMode = false;
                }else{ darkMode = true; }

                GraphDailyTracking graphDailyTracking = new GraphDailyTracking(
                        sell_tracking_graph.getMeasuredWidth(), sell_tracking_graph.getMeasuredHeight(), dataSet , darkMode);
                PFragment fragment = new PFragment(graphDailyTracking);
                fragment.setView(sell_tracking_graph, getActivity());
            }
        },50);








    }

}