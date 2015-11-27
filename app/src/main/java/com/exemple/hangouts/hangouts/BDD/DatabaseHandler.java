package com.exemple.hangouts.hangouts.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by nono on 27/11/15.
 */

public class DatabaseHandler extends SQLiteOpenHelper
{

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DEBUG", "onCreate");
        db.execSQL(ContactDAO.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ContactDAO.TABLE_DROP);
        onCreate(db);
    }
}