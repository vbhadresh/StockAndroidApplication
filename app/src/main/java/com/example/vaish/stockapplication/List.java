package com.example.vaish.stockapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by vaish on 4/29/2018.
 */

public class List extends SQLiteOpenHelper {

    private static final String TAG = "List";
    private static final int DATABASE_VERSION = 1;
    private static final String DB_Name = "MyDB";
    private static final String Table = "MyTable";
    private static final String SYMBOL = "Symbol";
    private static final String Name = "Name";
    private static final String EXT ="extra";
    private static final String EXT_1 ="extra1";
    private static final String Create = "CREATE TABLE " + Table + " (" +
            SYMBOL + " TEXT not null unique," +
            Name + " TEXT not null )";
    private static List instance;
    private SQLiteDatabase sqLiteDatabase;
    private static final String DATABASE_ALTER_FOR_VERSION2 = "ALTER TABLE "
            + Table + " ADD COLUMN " + EXT + " int not null default 0;";

    private static final String DATABASE_ALTER_FOR_VERSION3 = "ALTER TABLE "
            + Table + " ADD COLUMN " + EXT_1 + " int not null default 0;";

    public static List getInstance(Context c) {
        Log.d(TAG, "getInstance: Came to get instance");
        if (instance == null)
            instance = new List(c);
        return instance;
    }

    private List(Context c) {
        super(c, DB_Name, null, DATABASE_VERSION);
        sqLiteDatabase = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        if (newV == 2) {
            db.execSQL(DATABASE_ALTER_FOR_VERSION2);
        }
        if (newV == 3) {
            db.execSQL(DATABASE_ALTER_FOR_VERSION3);
        }
    }

    public void setupDb() {
        Log.d(TAG, "setupDb: came to setup");
        sqLiteDatabase = getWritableDatabase();
    }

    public void addentry(Stock stock) {
        ContentValues v = new ContentValues();
        v.put(SYMBOL, stock.getStock_Sys());
        v.put(Name, stock.getCompanys_Name());
        sqLiteDatabase.insert(Table, null, v);

    }

    public void Deleteentery(String s) {
        sqLiteDatabase.delete(Table, SYMBOL +"= ?", new String[]{s});
    }

    public ArrayList<String[]> Load() {
        ArrayList<String[]> ss = new ArrayList<>();
        Cursor c = sqLiteDatabase.query(Table, new String[]{SYMBOL, Name}, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();

            for (int i = 0; i < c.getCount(); i++) {
                String s1 = c.getString(0);
                String s2 = c.getString(1);
                ss.add(new String[]{s1,s2});
                c.moveToNext();
            }
            c.close();
        }
        return ss;
    }
    public void shutdown(){
        sqLiteDatabase.close();
    }
}
