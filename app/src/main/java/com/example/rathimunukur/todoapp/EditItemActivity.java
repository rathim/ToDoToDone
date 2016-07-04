package com.example.rathimunukur.todoapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class EditItemActivity extends AppCompatActivity {

    private EditText etEditField;
    private TextView tvDate;
    private TextView tvPriority;
    private TodoItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etEditField = (EditText) findViewById(R.id.etEditField);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvPriority = (TextView) findViewById(R.id.tvPriority);

        item = (TodoItem) getIntent().getSerializableExtra("item");

        etEditField.setText(item.text);
        if (item.duedate != null)
        {
            tvDate.setText(item.dateToString(item.duedate));
        }
        else {
            Date d = new Date();
            item.duedate = d;
            tvDate.setText(item.dateToString(d));
        }

        tvPriority.setText(item.priority);
    }

    public void saveItem(View view) {
        String newText = etEditField.getText().toString();
        item.text = newText;
        TodoItemDatabase db = TodoItemDatabase.getInstance(this);

        if( newText.length() != 0) {
            //Replace item and save
            db.updateItem(item);
        }
        else {
            //Text deleted. So delete item
            db.deleteItem(item.id);
        }
        this.finish();
    }

    public void setDate(int year, int month, int date) {
        TodoItemDatabase db = TodoItemDatabase.getInstance(this);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        Date d = calendar.getTime();
        item.duedate = d;
        tvDate.setText(item.dateToString(d));
        db.updateItem(item);

    }

    public void onDateClick(View view) {
        DatePickerFragment timeFrag = DatePickerFragment.newInstance("Set date");
        FragmentManager fm = getSupportFragmentManager();
        timeFrag.show(fm,"datePicker");
    }


    public void onPriorityClick(View view) {
        EditPriorityDialogFragment priorityDialogFragment = EditPriorityDialogFragment.newInstance("Choose priority", item.priority);
        FragmentManager fm = getSupportFragmentManager();
        priorityDialogFragment.show(fm,"fragment_priority");
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String priority = new String();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.btnHigh:
                if (checked)
                    priority ="High";
                    break;
            case R.id.btnMedium:
                if (checked)
                    priority = "Medium";
                    break;
            case R.id.btnLow:
                if (checked)
                    priority = "Low";
                break;
        }
        tvPriority.setText(priority);

        TodoItemDatabase db = TodoItemDatabase.getInstance(this);
        item.priority = priority;
        db.updateItem(item);

    }

    public void onCancelClick(View view) {
        this.finish();
    }
}
