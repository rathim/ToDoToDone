package com.example.rathimunukur.todoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private EditText etEditField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etEditField = (EditText) findViewById(R.id.etEditField);
        TodoItem item = (TodoItem) getIntent().getSerializableExtra("item");

        etEditField.setText(item.text);
    }

    public void saveItem(View view) {
        String newText = etEditField.getText().toString();
        TodoItem item = (TodoItem) getIntent().getSerializableExtra("item");
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
}
