package com.example.choisung.emotiondiary;

/**
 * Created by ChoISung on 2017-05-27.
 */

public class DiaryText {

    private int code;
    private String time;
    private String month;
    private String dayOfWeek;
    private String title;
    private  String contents;
    private String date;

    public int getCode(){
        return code;
    }

    public void setCode(int aCode){
        this.code = aCode;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String aTitle){
        this.title = aTitle;
    }

    public String getContents(){
        return contents;
    }

    public void setContents(String aContents){
        this.contents = aContents;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String aDate){
        this.date = aDate;
    }


    public String getTime(){
        return time;
    }

    public void setTime(String aTime){
        this.time = aTime;
    }

    public String getMonth(){
        return month;
    }

    public void setMonth(String aMonth){
        this.month = aMonth;
    }

    public String getDayOfWeek(){
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek){
        this.dayOfWeek = dayOfWeek;
    }




}
