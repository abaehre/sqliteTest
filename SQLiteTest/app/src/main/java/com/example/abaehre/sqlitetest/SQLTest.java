package com.example.abaehre.sqlitetest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

/**
 * Created by abaehre on 7/20/15.
 */
public class SQLTest extends AndroidTestCase{

    private DBHelper db;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = new DBHelper(context);

    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }


    public void testAdd(){
        db.add("8083", "Andrew", "password");
        SQLiteDatabase temp = db.getReadableDatabase();
        Cursor cursor = temp.query("testTable", null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String rowId = cursor.getString(cursor.getColumnIndex("id"));
                assertEquals(rowId, "8083");
            }
        }
    }

    public void testUpdate(){
        String text = "";
        db.add("0","Andrew","password");
        db.add("1","Aditya","pAssword");
        SQLiteDatabase temp = db.getReadableDatabase();
        Cursor cursor = temp.query("testTable", null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String rowId = cursor.getString(cursor.getColumnIndex("id"));
                    text += rowId + " ";
                } while (cursor.moveToNext());
                assertEquals(text,"0 1 ");
            }
        }
    }

    public void testDeleteFirst(){
        String rowId;
        db.add("70","Andrew", "Password");
        db.add("100", "Aditya", "pAssword");
        db.add("200", "Elliot", "paSsword");
        assertEquals(db.update(), "70 100 200 ");
        SQLiteDatabase temp = db.getReadableDatabase();
        Cursor cursor = temp.query("testTable", null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                rowId = cursor.getString(cursor.getColumnIndex("id"));
                assertEquals(rowId, "70");
            }
        }
        assertEquals(db.getNumRows(), 3);
        String blah = db.deleteFirst();
        assertEquals(blah, "");
        assertEquals(db.getNumRows(), 2);
        assertEquals(db.update(), "100 200 ");
        cursor = temp.query("testTable", null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            assertEquals(db.getNumRows(), 2);
            rowId = cursor.getString(cursor.getColumnIndex("id"));
            assertEquals(rowId, "100");
        }
        cursor.moveToNext();
        rowId = cursor.getString(cursor.getColumnIndex("id"));
        assertEquals(rowId, "200");
    }

}
