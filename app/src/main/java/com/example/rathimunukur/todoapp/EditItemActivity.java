package com.example.rathimunukur.todoapp;

import android.content.Intent;
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
        String textToEdit = getIntent().getStringExtra("text");
        etEditField.setText(textToEdit);
    }

    public void saveItem(View view) {
        //replace to do item and save
        String newText = etEditField.getText().toString();
        Intent retData = new Intent();
        retData.putExtra("text",newText);
        retData.putExtra("pos",(int) getIntent().getIntExtra("position",0));
        setResult(RESULT_OK, retData);

        this.finish();
    }
}
