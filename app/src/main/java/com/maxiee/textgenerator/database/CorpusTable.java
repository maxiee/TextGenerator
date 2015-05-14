package com.maxiee.textgenerator.database;

public class CorpusTable {

    public static final String NAME = "corpus";
    public static final String ID = "id";
    public static final String CONTENT = "content";
    public static final String CREATE = "create table " + NAME
        + "(" + ID + " integer primary key autoincrement,"
        + CONTENT + " text" + ");";
}
