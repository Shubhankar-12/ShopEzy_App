package com.homofabers.shopezy.helpers;

import java.lang.reflect.Array;

import processing.core.PApplet;

public class GraphDailyTracking extends PApplet {

    private int graphicWidth = 0;
    private int graphicHeight = 0;
    private int mainPadding = 10;
    private Boolean startRender = false;
    public int[][] dataValues;
    private int max_data ;
    private int min_data ;
    private int x_sel = 80;
    private String[] months = {"Jan" ,"Feb", "Mar", "Apr" , "May" ,"Jun", "July"};
    private String[] days = { "Mon" , "Tues" , "Wed" , "Thur", "Fri", "Sat", "Sun"};
    int rect_size;
    Boolean darkMode;

    public GraphDailyTracking(int graphicWidth,int graphicHeight , int[][] dataValue , Boolean darkMode){

        this.graphicWidth = graphicWidth - mainPadding;
        this.graphicHeight = graphicHeight - mainPadding;
        this.dataValues = dataValue;
        this.darkMode = darkMode;
        max_data = getMaxValue(dataValue);
        min_data = getMinValue(dataValue);

    }

    public void settings(){
        size(graphicWidth, graphicHeight,P2D);
    }
    public void setup(){

        textSize(20);
    }
    public void draw(){

        if(darkMode){
            background(0);
            fill(0);
        }else{
            background(255);
            fill(255);
        }

        //strokeWeight(0);
        noStroke();

        int numberOfWeeks = 18;
        int x_rect_position = 80;
        int y_rect_position = 80;
        float total_rect_percent = 1.2f;
        rect_size = ((int) ((width - 80) / (numberOfWeeks * total_rect_percent)));

        for(int i = 0; i < numberOfWeeks; i++){

            for (int j = 0; j < 7 ; j++){
                fill(
                        colorForData(dataValues[i][j])[0],
                        colorForData(dataValues[i][j])[1],
                        colorForData(dataValues[i][j])[2]
                );
                rect(x_rect_position , y_rect_position , rect_size , rect_size , 5);

                y_rect_position += total_rect_percent * rect_size;
                if((i*7 +j ) % 30 == 0){
                    int index = (i * 7 + j) / 30;
                    fill(0);
                    text(months[index],x_rect_position,60);
                }
                if( i == 0 && (j % 2 == 0)){
                    text(days[j] , 15 , y_rect_position-5);
                }
            }
            x_rect_position += total_rect_percent * rect_size ;
            y_rect_position = 80;
        }
        if(darkMode){ stroke(255); }
        else{stroke(0);}
        noFill();
        rect(x_sel,80, rect_size, 8*rect_size);

        if(mouseX>80 && mouseX < width-50 && mouseY > 0 && mouseY < (80 + 8 * rect_size)) {
            x_sel = mouseX;
        }
        drawCorrespondingGraph(0);
    }
    int startX = 80;
    int startY;
    int x_line = 80;
    public void drawCorrespondingGraph(int i){
        startY = 80 + 18*rect_size;
        //rect(80, 80 + 8*rect_size,width-80,10 * rect_size);
        pushMatrix();
        scale(1,-1);
        translate(0,-startY);
        rect(0,0,20,20);
        fill(0,255,0);
//        rect(80 , 80+8*rect_size , rect_size , 6*rect_size);
//        rect(80+2*rect_size , 80+8*rect_size , rect_size , 6*rect_size);
        for(int j = 0 ; j < 7 ; j++){
            fill(
                    colorForData(dataValues[i][j])[0],
                    colorForData(dataValues[i][j])[1],
                    colorForData(dataValues[i][j])[2]
            );
            float mappedHeight = map(dataValues[i][j],min_data,max_data,10,8*rect_size);
            noStroke();

            fill(5,172,87);
            rect(startX+(j*3*rect_size) ,0,1.8f *rect_size,mappedHeight,0,0,20,20);

            pushMatrix();
            scale(1,-1);
            translate( 0,  20);
            text(days[j] , startX+(j*3*rect_size),0);
            popMatrix();
        }
        if(mouseX > 80 && mouseX < width - 50 && mouseY>(80 + 8 * rect_size) && mouseY < (80 + 16 * rect_size)){
            x_line = mouseX;
        }
        strokeWeight(1);
        stroke(0);
        line(x_line,0 , x_line, 8*rect_size);
        popMatrix();

    }

    public int[] colorForData(int data){

        int new_data = data - min_data;
        int range = max_data - min_data;
        float colorPercent = (new_data * 100 / range);

        if(colorPercent <= 5){
            return new int[]{205, 205, 205 };
        }else if(colorPercent <= 25){
            return new int[]{165, 180, 165};
        }else if(colorPercent <= 50){
            return new int[]{125, 180, 125};
        }else if(colorPercent <= 75){
            return new int[]{85, 180, 85};
        }else if(colorPercent <= 100){
            return new int[]{45, 180, 45};
        }else{
            return new int[]{5, 180, 5};
        }

    }

    public static int getMaxValue(int[][] numbers) {
        int maxValue = numbers[0][0];
        for (int j = 0; j < numbers.length; j++) {
            for (int i = 0; i < numbers[j].length; i++) {
                if (numbers[j][i] > maxValue) {
                    maxValue = numbers[j][i];
                }
            }
        }
        return maxValue;
    }

    public static int getMinValue(int[][] numbers) {
        int minValue = numbers[0][0];
        for (int j = 0; j < numbers.length; j++) {
            for (int i = 0; i < numbers[j].length; i++) {
                if (numbers[j][i] < minValue ) {
                    minValue = numbers[j][i];
                }
            }
        }
        return minValue ;
    }

}
