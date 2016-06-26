package com.example.rathimunukur.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> todoItems;
    private ArrayAdapter<String> aToDoAdapter;
    private ListView lvItems;
    private EditText etEditText;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateArrayItems();

        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);

        etEditText = (EditText) findViewById(R.id.etEditText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //delete the item
                todoItems.remove(i);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();

                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //edit the item
                Intent toEdit = new Intent(MainActivity.this, EditItemActivity.class);
                Toast.makeText(MainActivity.this, todoItems.get(i),Toast.LENGTH_SHORT).show();
                toEdit.putExtra("text",todoItems.get(i));
                toEdit.putExtra("position",i);
                startActivityForResult(toEdit, REQUEST_CODE);
            }
        });
    }

    public void populateArrayItems()
    {
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract new item and position from result extras
            String newItem = data.getExtras().getString("text");
            int pos = data.getExtras().getInt("pos", 0);
            // Replace item on list
            if( newItem.length() != 0) {
                todoItems.set(pos, newItem);
            }
            else{
                todoItems.remove(pos);
            }
            //updating the file
            aToDoAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void readItems()
    {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(file)) ;
        } catch(IOException e){
            todoItems = new ArrayList<String>();

        }
    }

    private void writeItems()
    {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(file, todoItems);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void onAddItem(View view) {
        String text = etEditText.getText().toString();
        if(text.length()!=0) {
            //add the item
            aToDoAdapter.add(text);
            etEditText.setText("");
            writeItems();
        }
        else {
            //prompt user to enter text first
            Toast.makeText(this, "Please enter text first", Toast.LENGTH_SHORT).show();
        }

    }

}
