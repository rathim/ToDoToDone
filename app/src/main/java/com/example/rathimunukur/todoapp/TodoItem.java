package com.example.rathimunukur.todoapp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rathimunukur on 6/29/16.
 */
public class TodoItem implements Serializable{

    public String text;
    public int id;
    public Date duedate;
    public String priority;


    public TodoItem()
    {
        priority = "Low";
        duedate = new Date();
    }
    public TodoItem(String text, int id)
    {
        this.text = text;
        this.id = id;
        priority = "Low";
        duedate = new Date();
    }

    public String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = sdf.format(date.getTime());
        return strDate;
    }
}
