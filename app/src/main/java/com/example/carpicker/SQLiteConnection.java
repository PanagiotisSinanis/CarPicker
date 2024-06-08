package com.example.carpicker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

public class SQLiteConnection {
    private SelectionLoggerDbHelper dbHelper;
    private SQLiteDatabase db;

    SQLiteConnection(Context context){
        dbHelper = new SelectionLoggerDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(String brand, String model, String timestamp){

        ContentValues values = new ContentValues();
        values.put(SelectionLoggerContract.LoggerEntry.COLUMN_NAME_BRAND, brand);
        values.put(SelectionLoggerContract.LoggerEntry.COLUMN_NAME_MODEL, model);
        values.put(SelectionLoggerContract.LoggerEntry.COLUMN_NAME_TIMESTAMP, new Date(System.currentTimeMillis()).toString());

        long newRowId = db.insert(SelectionLoggerContract.LoggerEntry.TABLE_NAME, null, values);
        return newRowId;

    }

    public void close(){
        db.close();
    }
}

