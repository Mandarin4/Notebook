package com.moinoviibloknote.notebook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.moinoviibloknote.notebook.Adapter.Listitem;

import java.util.ArrayList;
import java.util.List;

public class MyDBManager {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public MyDBManager(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public void openDb(){
        db = dbHelper.getWritableDatabase() ;
    }

    public void insertToDb(String title, String disc){
        ContentValues cv = new ContentValues();
        cv.put(MyConstans.TITLE, title);
        cv.put(MyConstans.DISC, disc);
        db.insert(MyConstans.TABLE_NAME, null, cv);
    }
    public void updateItem(String title, String disc, int id){
        String selection = MyConstans._ID + "=" + id;
        ContentValues cv = new ContentValues();
        cv.put(MyConstans.TITLE, title);
        cv.put(MyConstans.DISC, disc);
        db.update(MyConstans.TABLE_NAME,cv,selection,null);
    }
    public void delete(int id){
        String selection = MyConstans._ID + "=" + id;
        db.delete(MyConstans.TABLE_NAME,selection,null);
    }

    public void getFromDb(String searchtext, OnDataReceived onDataReceived){
        List<Listitem> tempList = new ArrayList<>();
        String selection = MyConstans.TITLE + " like ?";
        Cursor cursor = db.query(MyConstans.TABLE_NAME,null,selection,new String[]{"%" + searchtext + "%"},null,null,null);
        while (cursor.moveToNext()){
            Listitem item = new Listitem();
            String title = cursor.getString(cursor.getColumnIndex(MyConstans.TITLE));
            String desc = cursor.getString(cursor.getColumnIndex(MyConstans.DISC));
            int _id = cursor.getInt(cursor.getColumnIndex(MyConstans._ID));
            item.setTitle(title);
            item.setDesc(desc);
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
