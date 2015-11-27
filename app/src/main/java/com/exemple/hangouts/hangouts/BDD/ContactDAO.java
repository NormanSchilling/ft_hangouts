package com.exemple.hangouts.hangouts.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by nono on 27/11/15.
 */
public class ContactDAO extends DAOBase
{
        public static final String TABLE_NAME = "contact";
        public static final String KEY = "id";
        public static final String NAME = "name";
        public static final String NUMERO = "numero";

        public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + NUMERO + " TEXT);";

        public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public ContactDAO(Context pContext) {
            super(pContext);
        }

       public void add(Contact c)
        {
            ContentValues value = new ContentValues();
            value.put(ContactDAO.NAME, c.getName());
            value.put(ContactDAO.NUMERO, c.getNumero());
            mDb.insert(ContactDAO.TABLE_NAME, null, value);
        }

        public void delete(long id)
        {
            mDb.delete(TABLE_NAME, KEY + " = ?", new String[]{String.valueOf(id)});
        }

        public void update(Contact c)
        {
            ContentValues value = new ContentValues();
            value.put(ContactDAO.NAME, c.getName());
            value.put(ContactDAO.NUMERO, c.getNumero());
            mDb.update(TABLE_NAME, value, KEY  + " = ?", new String[] {String.valueOf(c.getId())});
        }

        public void select(long id)
        {
            Cursor c = mDb.rawQuery("select " + NAME + ", " + NUMERO + " from " + TABLE_NAME + " where id = ?", new String[]{String.valueOf(id)});
        }

        public void selectAll()
        {
            Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, new String[]{});
            if (c == null)
                return ;
            while (c.moveToNext()) {
                String[] items = c.getColumnNames();
                Log.d("NAME CONTACT",items.getClass().getName());
            }
            c.close();

        }
}
