package com.example.songjinlong.myapplication;

import android.content.ContentValues;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageStats;
import android.database.sqlite.SQLiteDatabase;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by songjinlong on 2018/4/27.
 */
public class MyPackageStats extends IPackageStatsObserver.Stub {

    SQLiteDatabase db;

    public MyPackageStats(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
        if (succeeded) {

            ContentValues cv = new ContentValues();
            cv.put(DatabaseManager.INDEX_PKG_NAME, pStats.packageName);
            cv.put(DatabaseManager.INDEX_APK_SIZE, pStats.codeSize);
            cv.put(DatabaseManager.INDEX_DATA_SIZE, pStats.dataSize);
            cv.put(DatabaseManager.INDEX_CACHE_SIZE, pStats.cacheSize);
            db.insert(DatabaseManager.TABLE_APP_SIZE_DETAIL, null, cv);

        } else {
            Log.d("Test", "Fail to get app size.");
        }
    }
}
