package com.example.abaehre.sqlitetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abaehre on 7/17/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context c;

    public DBHelper(Context context) {
        super(context, "test.db", null, 1);
        c = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS testTable(id integer primary key, name " +
                "text," +
                "password text);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS testTable");
        onCreate(db);
    }


    public void add(String id, String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues temp = new ContentValues();
        temp.put("id", id);
        temp.put("name", name);
        temp.put("password", password);
        try {
            db.insert("testTable", null, temp);
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
        db.close();
    }

    public String deleteFirst() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query("testTable", null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                db.beginTransaction();
                try {
                    String rowId = cursor.getString(cursor.getColumnIndex("id"));
                    db.delete("testTable", "id=" + rowId, null);
                    db.setTransactionSuccessful();
                }
                finally{
                    db.endTransaction();
                }
                cursor.close();
                db.close();
                return "";
            } else {
                cursor.close();
                db.close();
                return "Nothing to delete";
            }
        }
        cursor.close();
        db.close();
        return "";
    }

    public int getNumRows(){
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("testTable", null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while(!cursor.isAfterLast()){
                    count++;
                    cursor.moveToNext();
                }
            }
        }
        cursor.close();
        db.close();
        return count;
    }

    public String update() {
        SQLiteDatabase db = this.getReadableDatabase();
        String text = "";
        Cursor cursor = db.query("testTable", null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                while(!cursor.isAfterLast()){
                    text+=cursor.getString(cursor.getColumnIndex("id")) + " ";
                    cursor.moveToNext();
                }
            }
        }
        cursor.close();
        db.close();
        return text;
    }

}
