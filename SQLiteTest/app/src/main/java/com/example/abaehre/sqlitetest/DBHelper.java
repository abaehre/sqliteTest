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
        db.delete("testTable", null, null);
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
        ContentValues temp = new ContentValues();
        temp.put("id", id);
        temp.put("name", name);
        temp.put("password", password);
        db.insert("testTable", null, temp);
        System.out.println("INSERT: " + id);
    }

    public String deleteFirst() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query("testTable", null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                System.out.println("Delete first");
                String rowId = cursor.getString(cursor.getColumnIndex("id"));
                db.delete("testTable", "id=?", new String[]{rowId});
                System.out.println("DELETE: " + rowId);
                cursor.close();
                return "";
            } else {
                System.out.println("Nothing to delete");
                cursor.close();
                return "Nothing to delete";
            }
        }
        cursor.close();
        return "";
    }

    public String update() {
        SQLiteDatabase db = this.getReadableDatabase();
        String text = "";
        Cursor cursor = db.query("testTable", null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String rowId = cursor.getString(cursor.getColumnIndex("id"));
                    text += rowId + " ";
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        return text;
    }

}
