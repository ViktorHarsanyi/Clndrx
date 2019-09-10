package com.vktrhrsny.clndrx.vp.pojo;

import java.util.Date;

public class DayItem {


    private int dayDate,memoId;
    private Date date;
    private String message;
    private int emotIcon;


    public DayItem(int dayDate,Date date){
        message="";
        this.dayDate=dayDate;
        this.date=date;
    }

    public DayItem(int memoId,String message,int emotIcon,Date date){
        this.message=message;
        this.memoId=memoId;
        this.emotIcon=emotIcon;
        this.date=date;
    }

    public Date getDate(){return date;}

    public int getDayDate(){
        return dayDate;
    }

    public int getMemoId(){ return memoId; }

    public String getMessage(){
        return message;
    }

    public void setMessage(String m){
        message=m;
    }

    public void setEmotIcon(int emotIcon) {this.emotIcon=emotIcon;}

    public int getEmotIcon() {return emotIcon;}
}
