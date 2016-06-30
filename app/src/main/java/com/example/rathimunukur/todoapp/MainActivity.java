package com.example.rathimunukur.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private TodoCursorAdapter todoCursorAdapter;
    private ListView lvItems;
    private EditText etEditText;
    private final int EDIT_REQUEST_CODE = 20;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    TodoItemDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db  = TodoItemDatabase.getInstance(this);
        Cursor todoCursor = db.getFirstPost();
        todoCursorAdapter = new TodoCursorAdapter(this, todoCursor, 0);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(todoCursorAdapter);

        etEditText = (EditText) findViewById(R.id.etEditText);


        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                //delete the item
                db.deleteItem((int)l);
                refresh();

                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                //edit the item
                Intent toEdit = new Intent(MainActivity.this, EditItemActivity.class);

                TodoItem itemToEdit = db.getItem((int)id);
                toEdit.putExtra("item", itemToEdit);

                startActivityForResult(toEdit, EDIT_REQUEST_CODE);

            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //reload the screen with the new contents
        refresh();
    }

    public void onAddItem(View view) {
        String text = etEditText.getText().toString();
        if (text.length() != 0) {
            //add the item
            TodoItem newItem = new TodoItem();
            newItem.text = text;
            db.addItem(newItem);

            refresh();

            etEditText.setText("");

        } else {
            //prompt user to enter text first
            Toast.makeText(this, "Please enter text first", Toast.LENGTH_SHORT).show();
        }

    }

    public void refresh()
    {
        //reloading screen with new contents
        Cursor cursor = db.getFirstPost();
        todoCursorAdapter.changeCursor(cursor);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.rathimunukur.todoapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.rathimunukur.todoapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

}
