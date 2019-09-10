package com.vktrhrsny.clndrx.vp.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "memories")
public class MemoryEntity {

    @PrimaryKey (autoGenerate = true)
    public int memoryID;

    @ColumnInfo (name = "memo_entry")
    private String entry;

    @ColumnInfo (name = "memo_date")
    private String date;

    @ColumnInfo (name = "emot_icon")
    private int emotIcon = 0;

    public void setEntry(String entry){ this.entry=entry;}

    public String getEntry(){
        return entry;
    }

    public int getMemoryID(){ return memoryID;}

    public void setEmotIcon(int emotIcon){this.emotIcon=emotIcon;}

    public int getEmotIcon(){return emotIcon;}

    public void setDate(String date){ this.date=date;}

    public String getDate(){
        return date;
    }


}
