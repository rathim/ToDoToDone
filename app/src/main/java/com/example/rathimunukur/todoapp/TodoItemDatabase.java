package com.example.rathimunukur.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rathimunukur on 6/29/16.
 */
public class TodoItemDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todoDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_TODO = "items";

    // To Do items Table Columns
    private static final String KEY_TODO_ID = "id";
    private static final String KEY_TODO_text = "text";

    private static TodoItemDatabase sInstance;

    public static synchronized TodoItemDatabase getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new TodoItemDatabase(context.getApplicationContext());
        }
        return sInstance;
    }

    private TodoItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // These is where we need to write create table statements.
    // This is called when database is created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO +
                "(" +
                KEY_TODO_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_TODO_text + " TEXT" +
                ")";

        db.execSQL(CREATE_TODO_TABLE);
    }

    // This method is called when database is upgraded like
    // modifying the table structure,
    // adding constraints to database, etc
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
            onCreate(db);
        }
    }

    // Insert a post into the database
    public void addItem(TodoItem todoItem) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //values.put(KEY_TODO_ID, todoItem.id);
            values.put(KEY_TODO_text, todoItem.text);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_TODO, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            //Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public void updateItem(TodoItem todoItem) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            //values.put(KEY_TODO_ID, todoItem.id);
            values.put(KEY_TODO_text, todoItem.text);

            db.update(TABLE_TODO, values, KEY_TODO_ID + " = ?",
                    new String[]{String.valueOf(todoItem.id)});

            db.setTransactionSuccessful();
        }
        catch(Exception e) {
        } finally {
            db.endTransaction();
        }


    }
    public void deleteItem(int id) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            db.delete(TABLE_TODO, KEY_TODO_ID + " = ?",
                    new String[]{String.valueOf(id)});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            //Log.d(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }

    }

    public TodoItem getItem(int id) {
        TodoItem item = new TodoItem();

        String ITEMS_SELECT_QUERY =
                String.format("SELECT id _id,* FROM %s",TABLE_TODO);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ITEMS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    if(cursor.getInt(cursor.getColumnIndex(KEY_TODO_ID))== id)
                    {
                        item.id = id;
                        item.text = cursor.getString(cursor.getColumnIndex(KEY_TODO_text));
                        break;
                    }
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            //Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return item;
    }

    public Cursor getFirstPost(){
        // Get access to the underlying writeable database
        SQLiteDatabase db = getWritableDatabase();
        // Query for items from the database and get a cursor back
        String ITEMS_SELECT_QUERY =
                String.format("SELECT id _id,* FROM %s",TABLE_TODO);
        Cursor todoCursor = db.rawQuery(ITEMS_SELECT_QUERY, null);
        return todoCursor;
    }
}
