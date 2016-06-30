package com.example.rathimunukur.todoapp;

import java.io.Serializable;

/**
 * Created by rathimunukur on 6/29/16.
 */
public class TodoItem implements Serializable{
    public String text;
    public int id;

    public TodoItem()
    {

    }
    public TodoItem(String text, int id)
    {
        this.text = text;
        this.id = id;
    }

}
