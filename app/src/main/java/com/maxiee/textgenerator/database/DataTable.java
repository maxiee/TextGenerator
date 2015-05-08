package com.maxiee.textgenerator.database;

public class DataTable {

    public static final String NAME = "data";
    public static final String ID = "id";
    public static final String TEXT = "text";
    public static final String CREATE = "create table " + NAME
        + "(" + ID + " integer primary key autoincrement,"
        + TEXT + " text" + ");";
}
