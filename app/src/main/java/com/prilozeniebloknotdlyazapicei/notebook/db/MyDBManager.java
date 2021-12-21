package com.prilozeniebloknotdlyazapicei.notebook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.prilozeniebloknotdlyazapicei.notebook.Adapter.ListItem;

import java.util.ArrayList;
import java.util.List;

public class MyDBManager {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public MyDBManager(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb(){
        database = dbHelper.getWritableDatabase() ;
    }

    public void insertToDb(String title, String discrip){
        ContentValues cv = new ContentValues();
        cv.put(MyConstans.TITLE, title);
        cv.put(MyConstans.DISCRIP, discrip);
        database.insert(MyConstans.TABLE_NAME, null, cv);
    }
    public void updateItem(String title, String discrip, int id){
        String selection = MyConstans._ID + "=" + id;
        ContentValues cv = new ContentValues();
        cv.put(MyConstans.TITLE, title);
        cv.put(MyConstans.DISCRIP, discrip);
        database.update(MyConstans.TABLE_NAME,cv,selection,null);
    }
    public void delete(int id){
        String selection = MyConstans._ID + "=" + id;
        database.delete(MyConstans.TABLE_NAME,selection,null);
    }

    public void getFromDb(String searchtext, OnDataReceived onDataReceived){
        List<ListItem> tempList = new ArrayList<>();
        String selection = MyConstans.TITLE + " like ?";
        Cursor cursor = database.query(MyConstans.TABLE_NAME,null,selection,new String[]{"%" + searchtext + "%"},null,null,null);
        while (cursor.moveToNext()){
            ListItem item = new ListItem();
            String title = cursor.getString(cursor.getColumnIndex(MyConstans.TITLE));
            String descripp2 = cursor.getString(cursor.getColumnIndex(MyConstans.DISCRIP));
            int _id = cursor.getInt(cursor.getColumnIndex(MyConstans._ID));
            item.setTitle(title);
            item.setDescrip(descripp2);
            item.setId(_id);
            tempList.add(item);
        }
        cursor.close();
        onDataReceived.onReceived(tempList);
    }

    public void closeDb(){
        dbHelper.close();
    }
}
