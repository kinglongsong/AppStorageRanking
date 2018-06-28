package com.example.songjinlong.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

public class DataAdapter extends BaseAdapter {

    List<MyAppInfo> myAppInfoList;
    Context context;

    public DataAdapter(Context context, List<MyAppInfo> myAppInfoList) {
        this.context = context;
        this.myAppInfoList = myAppInfoList;
    }


    @Override
    public int getCount() {
        return myAppInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return myAppInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyAppInfo myAppInfo = myAppInfoList.get(position);
        PackageManager packageManager = (PackageManager) context.getPackageManager();
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(2);

        if (convertView == null) {
            //生成item的view
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            View itemView = layoutInflater.inflate(R.layout.listview_item, null);
            ViewHolder holder = new ViewHolder();
            holder.iconView = (ImageView) itemView.findViewById(R.id.appIcon);
            holder.appName = (TextView) itemView.findViewById(R.id.appName);
            holder.codeSize = (TextView) itemView.findViewById(R.id.codeSize);
            holder.totalSize = (TextView) itemView.findViewById(R.id.totalSize);
            holder.expansion = (TextView) itemView.findViewById(R.id.expansion);

            try {
                Drawable iconDrawable = packageManager.getApplicationIcon(myAppInfo.pkgName);
                holder.iconView.setImageDrawable(iconDrawable);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            try {
                holder.appName.setText(packageManager.getApplicationLabel(packageManager.getApplicationInfo(myAppInfo.pkgName, 0)));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            holder.codeSize.setText(" " + Formatter.formatFileSize(context, myAppInfo.codeSize));
            long totalSize = myAppInfo.codeSize + myAppInfo.dataSize;
            float expansion = ((float) (totalSize - myAppInfo.codeSize)) / myAppInfo.codeSize;
            holder.totalSize.setText(" " + Formatter.formatFileSize(context, totalSize));
            String expansionStr = numberFormat.format(expansion);
            holder.expansion.setText(" 膨胀" + expansionStr);
            itemView.setTag(holder);
            return itemView;
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        try {
            Drawable iconDrawable = packageManager.getApplicationIcon(myAppInfo.pkgName);
            holder.iconView.setImageDrawable(iconDrawable);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            holder.appName.setText(packageManager.getApplicationLabel(packageManager.getApplicationInfo(myAppInfo.pkgName, 0)));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        holder.codeSize.setText(" " + Formatter.formatFileSize(context, myAppInfo.codeSize));
        long totalSize = myAppInfo.codeSize + myAppInfo.dataSize;
        float expansion = ((float) (totalSize - myAppInfo.codeSize)) / myAppInfo.codeSize;
        holder.totalSize.setText(" " + Formatter.formatFileSize(context, totalSize));
        String expansionStr = numberFormat.format(expansion);
        holder.expansion.setText(" 膨胀" + expansionStr);

        return convertView;
    }

    class ViewHolder {
        ImageView iconView;
        TextView appName;
        TextView codeSize;
        TextView totalSize;
        TextView expansion;
    }
}
