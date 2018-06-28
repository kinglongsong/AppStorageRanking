package com.example.songjinlong.myapplication;

import android.app.Activity;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.ListView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


    ListView lv;
    List<MyAppInfo> myAppInfoList;
    DatabaseManager dbManager;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    Log.d("Test", "刷新主界面");
                    lv.setAdapter(new DataAdapter(MainActivity.this, myAppInfoList));
                }
                default: {

                }

            }

            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
        lv = (ListView) findViewById(R.id.listView);

        // dbManager = new DatabaseManager(this, "storage_size_detail", null, 1);

        //generate data
        PackageManager pm = getPackageManager();
        myAppInfoList = new ArrayList<>();

        /*
        数据没有过期，则直接从数据库加载列表
        数据已经过期，重新扫描并计算应用列表信息
        */
        if (!DatabaseManager.isDataOutOfTime()) {
            //load data freom database

        } else {
            //rescan and caculate myappinfo
            /*
            扫描应用的大小（代码、数据&缓存）需要适配

            PackageStats（因为是隐藏API，通过反射的方式用在API level 26以前，也就是Android 8.0之前）
            Android8.0及以后，使用StorageStatsManager
            官方说明：
            This class was deprecated in API level 26.
            this class is an orphan that could never be obtained from a valid public API. If you need package storage statistics use the new StorageStatsManager APIs.

             */

            if (Build.VERSION.SDK_INT < 26) {
                //Android8.0以下，采用反射的方式，但是不同的ROM，方法不一样
                String method1 = "getPackageSizeInfo";
                String method2 = "getPackageSizeInfoAsUser";
                //暂时使用第二个（测试机为坚果Pro，Android7.1.1，方法为第二个
                try {
                    Method mt = pm.getClass().getDeclaredMethod(method1, String.class, int.class, IPackageStatsObserver.class);
                    List<PackageInfo> pkginfos = pm.getInstalledPackages(0);
                    for (int i = 0; i < 5; i++) {
//                        if ((pkginfos.get(i).applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
//                            //不统计系统应用的数据，跳过本次循环
//                            continue;
//                        }
                        if (i == (5 - 1)) {
                            MyAppInfo myAppInfo = new MyAppInfo();
                            myAppInfoList.add(myAppInfo);
                            MyPackageStats myPackageStats = new MyPackageStats(myAppInfo, true);
                            myPackageStats.prepareToShowList(handler);
                            mt.invoke(pm, pkginfos.get(i).packageName, Process.myUid() / 100000, myPackageStats);
                            return;
                        }
                        MyAppInfo myAppInfo = new MyAppInfo();
                        myAppInfoList.add(myAppInfo);
                        mt.invoke(pm, pkginfos.get(i).packageName, Process.myUid() / 100000, new MyPackageStats(myAppInfo, false));
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

            } else {


            }
        }





    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
