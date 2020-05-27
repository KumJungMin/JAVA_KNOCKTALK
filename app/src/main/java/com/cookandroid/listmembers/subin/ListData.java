package com.cookandroid.listmembers.subin;

import androidx.appcompat.app.AppCompatActivity;

public class ListData extends AppCompatActivity {
    private int year,month,date,start,end;
    private String keyword, place, memo;
    private String color;
    private boolean door;

    public ListData(int year,int month, int date, int start, int end, String keyword,String place, String memo, String color){
        this.year=year;
        this.month=month;
        this.date=date;
        this.start=start;
        this.end=end;
        this.keyword=keyword;
        this.place=place;
        this.memo=memo;
        this.color=color;
        this.door=false ;
    }



    public void setYear(int year) { this.year = year; }
    public void setMonth(int month) { this.month = month; }
    public void setDate(int date) { this.date=date; }
    public void setStart(int start){ this.start=start; }
    public void setEnd(int end){ this.end=end; }
    public void setKeyword(String keyword){ this.keyword=keyword;}
    public void setPlace(String place){ this.place=place;}
    public void setMemo(String memo) {this.memo=memo;}
    public void setColor(String color){this.color=color;}
    public void setDoor(boolean door){this.door=door;}

    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDate() { return date; }
    public int getStart() { return start; }
    public int getEnd(){ return end; }
    public String getKeyword() { return keyword;}
    public String getPlace() {return  place;}
    public String getMemo(){return memo;}
    public String getColor(){return color;}
    public boolean getDoor(){return door;}


}
