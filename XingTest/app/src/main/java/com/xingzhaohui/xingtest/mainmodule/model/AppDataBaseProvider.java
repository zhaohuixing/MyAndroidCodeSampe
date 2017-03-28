package com.xingzhaohui.xingtest.mainmodule.model;

/**
 * Created by zhaohuixing on 2017-03.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public abstract class AppDataBaseProvider extends SQLiteOpenHelper {
    public static final String APPDB_NAME = "xappdata";

    protected int DATABASE_VERSION = 1;
    protected String DATABASE_NAME;
    protected String TABLE_NAME;

    public AppDataBaseProvider(Context context, String dbName, String tableName, int version, CursorFactory factory) {
        super(context, dbName, factory, version);
        DATABASE_VERSION = version;
        DATABASE_NAME = dbName;
        TABLE_NAME = tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public static int CreateGenericIntegerKey () {
        //Don't worry about 18 Jan 2038 overflow of integer type value from Date
        //Don't need millisecond
        //So use integer
        //
        int key = (int) (new Date().getTime()/1000);
        return key;
    }

}
