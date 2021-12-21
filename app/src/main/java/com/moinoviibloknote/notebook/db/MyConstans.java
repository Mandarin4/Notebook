package com.moinoviibloknote.notebook.db;

public class MyConstans {
    public static final String EDIT_STATE = "my_state";
    public static final String LIST_ITEM_INTENT = "my_table";
    public static final String TABLE_NAME = "my_table";
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String DISC = "disc";
    public static final String DB_NAME = "my_db.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + TITLE +
            " TEXT," + DISC + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
