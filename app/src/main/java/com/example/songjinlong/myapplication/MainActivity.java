package com.example.songjinlong.myapplication;

import android.app.Activity;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.widget.ListView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


    ListView lv;
    ArrayList<PackageStats> packageStatses;
    DatabaseManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DatabaseManager(this, "storage_size_detail", null, 1);

        PackageManager pm = getPackageManager();

        lv = (ListView) findViewById(R.id.listView);

        packageStatses = new ArrayList();

        SQLiteDatabase db = dbManager.getReadableDatabase();

        try {

            Method mt = pm.getClass().getDeclaredMethod("getPackageSizeInfoAsUser", String.class, int.class, IPackageStatsObserver.class);

            List<PackageInfo> pkginfos = pm.getInstalledPackages(0);

            for (int i = 0; i < pkginfos.size(); i++) {
                mt.invoke(pm, pkginfos.get(i).packageName, Process.myUid() / 100000, new MyPackageStats(dbManager.getWritableDatabase()));
            }


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.d("Test", e.toString());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Log.d("Test", e.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.d("Test", e.toString());
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
