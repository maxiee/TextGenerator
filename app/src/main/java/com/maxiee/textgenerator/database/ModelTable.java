package com.maxiee.textgenerator.database;

/**
 * Created by maxiee on 15-5-14.
 */
public class ModelTable {

    public static final String NAME = "model";
    public static final String ID = "id";
    public static final String CONTENT = "content";
    public static final String CREATE = "create table " + NAME
            + "(" + ID + " integer primary key autoincrement,"
            + CONTENT + " text" + ");";
}
