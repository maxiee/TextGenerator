package com.maxiee.textgenerator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.maxiee.textgenerator.database.DataTable;

public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static String DB_NAME = "data";
    private static int DB_VER = 1;

    private static DatabaseHelper instance;
    
    public DatabaseHelper(Context context) {
       super(context, DB_NAME, null, DB_VER);
    } 

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataTable.CREATE);
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

