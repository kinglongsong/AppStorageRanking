package com.example.songjinlong.myapplication;

public class MyAppInfo {

    String pkgName;
    String appName;
    long codeSize;
    long dataSize;
    long cacheSize;
    long totalSize;
    float expansivity;

    public boolean toDatabase() {
        //write data of this appinfo to database
        return true;
    }
}
