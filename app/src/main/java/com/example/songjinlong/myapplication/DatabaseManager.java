package com.example.songjinlong.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by songjinlong on 2018/5/13.
 */
public class DatabaseManager extends SQLiteOpenHelper {

    public static final String TABLE_APP_SIZE_DETAIL = "app_size_detail";
    public static final String INDEX_PKG_NAME = "pkgName";
    public static final String INDEX_APK_SIZE = "apkSize";
    public static final String INDEX_DATA_SIZE = "dataSize";
    public static final String INDEX_CACHE_SIZE = "cacheSize";

    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE app_size_detail (_id INTEGER PRIMARY KEY AUTOINCREMENT, pkgName TEXT, apkSize REAL,dataSize REAL,cacheSize REAl);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static boolean isDataOutOfTime() {
        return true;
    }
}
