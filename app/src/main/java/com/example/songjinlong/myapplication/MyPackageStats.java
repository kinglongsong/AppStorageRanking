package com.example.songjinlong.myapplication;

import android.content.Context;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageStats;
import android.os.RemoteException;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

/**
 * Created by songjinlong on 2018/4/27.
 */
public class MyPackageStats extends IPackageStatsObserver.Stub {

    List<PackageStats> packageStatsesList;
    ListView listView;
    Context context;

    public MyPackageStats(List<PackageStats> packageStatsesList) {
        this.packageStatsesList = packageStatsesList;
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

            packageStatsesList.add(pStats);
            Log.d("Test", "Get app stat : " + pStats.packageName);

            if (listView != null) {
                Log.d("Test", "完成，共" + packageStatsesList.size() + "款");
                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(new DataAdapter(context, packageStatsesList));
                    }
                });
            }

        } else {
            Log.d("Test", "Fail to get app size.");
        }
    }

    public void prepareToShowList(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }
}
