package com.example.rathimunukur.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
/**
 * Created by rathimunukur on 6/30/16.
 */
public class TodoCursorAdapter extends CursorAdapter{
    public TodoCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }


    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvText = (TextView) view.findViewById(R.id.tvText);
        TextView tvListDate = (TextView) view.findViewById(R.id.tvListDate);
        TextView tvListPriority = (TextView) view.findViewById(R.id.tvListPriority);

        // Extract properties from cursor
        String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
        String priority = cursor.getString(cursor.getColumnIndexOrThrow("priority"));
        // Populate fields with extracted properties
        tvText.setText(text);
        tvListDate.setText(date);
        switch (priority)
        {
            case "High":
                tvListPriority.setTextColor(Color.rgb(0xFF, 0, 0));
                break;
            case "Medium":
                tvListPriority.setTextColor(Color.rgb(0xFF, 0xA5, 0));
                break;
            case "Low":
            default:
                tvListPriority.setTextColor(Color.rgb(0xA4, 0xC6, 0x39));
                break;
        }
        tvListPriority.setText(priority);

    }
}
