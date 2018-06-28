package com.example.songjinlong.myapplication;

import android.content.Context;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageStats;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by songjinlong on 2018/4/27.
 */
public class MyPackageStats extends IPackageStatsObserver.Stub {

    MyAppInfo myAppInfo;
    Context context;
    boolean isLastApp;
    Handler handler;

    public MyPackageStats(MyAppInfo myAppInfo, boolean isLastApp) {
        this.myAppInfo = myAppInfo;
        this.isLastApp = isLastApp;
    }

    @Override
    public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
        if (succeeded) {

//            ContentValues cv = new ContentValues();
//            cv.put(DatabaseManager.INDEX_PKG_NAME, pStats.packageName);
//            cv.put(DatabaseManager.INDEX_APK_SIZE, pStats.codeSize);
//            cv.put(DatabaseManager.INDEX_DATA_SIZE, pStats.dataSize);
//            cv.put(DatabaseManager.INDEX_CACHE_SIZE, pStats.cacheSize);
//            db.insert(DatabaseManager.TABLE_APP_SIZE_DETAIL, null, cv);

            Log.d("Test", "Get app stat : " + pStats.packageName);

            myAppInfo.appName = "";
            myAppInfo.pkgName = pStats.packageName;
            myAppInfo.codeSize = pStats.codeSize;
            myAppInfo.dataSize = pStats.dataSize;
            myAppInfo.cacheSize = pStats.cacheSize;

            if (isLastApp) {
                Log.d("Test", "扫描完成");
                //通知主线程可以刷新界面了
                Message message = Message.obtain(handler);
                message.what = 0;
                handler.sendMessage(message);
            }

        } else {
            Log.d("Test", "Fail to get app size.");
        }
    }

    public void prepareToShowList(Handler handler) {
        this.handler = handler;
    }
}
