package com.homofabers.shopezy.helpers;

import static com.homofabers.shopezy.helpers.GraphDailyTracking.getMaxValue;
import static com.homofabers.shopezy.helpers.GraphDailyTracking.getMinValue;

import processing.core.PApplet;

public class PeriodicGraph extends PApplet {

    private int graphicWidth;
    private int graphicHeight;
    public int[][] dataSet;
    private int maxData ;
    private int minData ;
    private int padding = 10;
    Boolean darkMode = false;

    public PeriodicGraph(int graphicWidth, int graphicHeight , int[][] dataSet, Boolean darkMode){
        this.graphicWidth = graphicWidth - padding;
        this.graphicHeight = graphicHeight - padding;
        this.dataSet = dataSet;
        this.darkMode = darkMode;

        maxData = getMaxValue(dataSet);
        minData = getMinValue(dataSet);
    }

    public void settings(){
        size(graphicWidth, graphicHeight,P2D);
    }
    public void setup() {
        background(255);
    }
    public void draw(){

        float x_last = padding;
        float y_data1last = padding;
        float y_data2last = padding;
        float y_data3last = padding;
        if(darkMode){ background(0);}
        else{ background(255); }

        //rect(0,0,width,height);
        scale(1,-1);
        translate(0,-height);
        pushMatrix();
        translate(10 , 0);
        int noOfDays = dataSet[0].length;
        for(int j = 0; j < noOfDays ; j++){

            float x_value = map(j , 0 , noOfDays-1 , padding, width-padding);
            float y_data1 = map(dataSet[0][j],minData , maxData , padding , height-padding);
            float y_data2 = map(dataSet[1][j],minData , maxData , padding , height-padding);
            float y_data3 = map(dataSet[2][j],minData , maxData , padding , height-padding);

            point(x_value ,y_data1);
            text(j,x_value ,y_data1);
            point(x_value ,y_data2);
            text(j,x_value ,y_data2);
            point(x_value ,y_data3);
            text(j,x_value ,y_data3);

            stroke(255,0,0);
            line(x_value ,y_data1 , x_last , y_data1last );
            stroke(0,255,0);
            line(x_value ,y_data2 , x_last , y_data2last );
            stroke(0,0,255);
            line(x_value ,y_data3 , x_last , y_data3last );

            x_last = x_value;
            y_data1last = y_data1;
            y_data2last = y_data2;
            y_data3last = y_data3;

        }
        popMatrix();

        if(darkMode){ stroke(230);}
        else{ stroke(30); }

        line(padding , padding , width-padding , padding);
        line(padding , padding , padding , height - padding);

    }

    }
