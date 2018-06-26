package com.example.songjinlong.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by songjinlong on 2018/4/27.
 */

class ViewHolder {
    ImageView iconView;
    TextView appName;
    TextView codeSize;
    TextView totalSize;
    TextView expansion;
}

public class DataAdaptor extends ArrayAdapter {
    public DataAdaptor(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        return super.getView(position, convertView, parent);
    }
}
