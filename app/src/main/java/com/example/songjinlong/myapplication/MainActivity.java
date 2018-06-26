package com.example.songjinlong.myapplication;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
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
    List<PackageStats> packageStatsesList;
    DatabaseManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
        lv = (ListView) findViewById(R.id.listView);

        // dbManager = new DatabaseManager(this, "storage_size_detail", null, 1);

        //generate data
        PackageManager pm = getPackageManager();
        packageStatsesList = new ArrayList<>();
        try {
            Method mt = pm.getClass().getDeclaredMethod("getPackageSizeInfoAsUser", String.class, int.class, IPackageStatsObserver.class);
            List<PackageInfo> pkginfos = pm.getInstalledPackages(0);
            for (int i = 0; i < pkginfos.size(); i++) {
                if ((pkginfos.get(i).applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
                    continue;
                }
                if (i == (pkginfos.size() - 1)) {
                    MyPackageStats myPackageStats = new MyPackageStats(packageStatsesList);
                    myPackageStats.prepareToShowList(this, lv);
                    mt.invoke(pm, pkginfos.get(i).packageName, Process.myUid() / 100000, myPackageStats);
                    return;
                }
                mt.invoke(pm, pkginfos.get(i).packageName, Process.myUid() / 100000, new MyPackageStats(this.packageStatsesList));
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
