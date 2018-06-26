package com.example.songjinlong.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DataAdapter extends BaseAdapter {

    List<PackageStats> packageStatsList;
    Context context;

    public DataAdapter(Context context, List<PackageStats> packageStatsList) {
        this.context = context;
        this.packageStatsList = packageStatsList;
    }


    @Override
    public int getCount() {
        return packageStatsList.size();
    }

    @Override
    public Object getItem(int position) {
        return packageStatsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PackageStats packageStats = packageStatsList.get(position);
        PackageManager packageManager = (PackageManager) context.getPackageManager();

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
                Drawable iconDrawable = packageManager.getApplicationIcon(packageStats.packageName);
                holder.iconView.setImageDrawable(iconDrawable);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            try {
                holder.appName.setText(packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageStats.packageName, 0)));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            holder.codeSize.setText(packageStats.codeSize + "");
            long totalSize = packageStats.codeSize + packageStats.dataSize;
            int expansion = (int) (totalSize / packageStats.codeSize);
            holder.totalSize.setText(totalSize + "");
            holder.expansion.setText(expansion);
            itemView.setTag(holder);
            return itemView;
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        try {
            Drawable iconDrawable = packageManager.getApplicationIcon(packageStats.packageName);
            holder.iconView.setImageDrawable(iconDrawable);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        try {
            holder.appName.setText(packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageStats.packageName, 0)));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        holder.codeSize.setText(packageStats.codeSize + "");
        long totalSize = packageStats.codeSize + packageStats.dataSize;
        int expansion = (int) (totalSize / packageStats.codeSize);
        holder.totalSize.setText(totalSize + "");
        holder.expansion.setText(expansion);

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
