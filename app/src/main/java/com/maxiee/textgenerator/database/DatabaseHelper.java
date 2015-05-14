package com.maxiee.textgenerator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static String DB_NAME = "data";
    private static int DB_VER = 1;

    private static DatabaseHelper instance;
    
    public DatabaseHelper(Context context) {
       super(context, DB_NAME, null, DB_VER);
    } 

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CorpusTable.CREATE);
        db.execSQL(ModelTable.CREATE);
        ContentValues values = new ContentValues();
        values.put(ModelTable.CONTENT, "");
        db.insert(ModelTable.NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int from, int to) {

    }

    public static synchronized DatabaseHelper instance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }

        return instance;
    }
}

