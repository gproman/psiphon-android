package com.psiphon3.psiphonlibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psiphon3.R;

public class StandUpCustomAdapter extends BaseAdapter {
    Context context;
    StandUpAppInfo[] apps;
    LayoutInflater inflter;

    public StandUpCustomAdapter(Context applicationContext, StandUpAppInfo[] apps) {
        this.context = applicationContext;
        this.apps = apps;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return apps.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.stand_up_gridview_activity, null); // inflate the layout

        ImageView icon = (ImageView) view.findViewById(R.id.icon); // get the reference of ImageView
        icon.setImageResource(apps[i].icon); // set logo images

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(apps[i].name);

        TextView installedFlag = (TextView) view.findViewById(R.id.installed_flag);
        if (apps[i].installed) {
            installedFlag.setText(context.getString(R.string.installed_flag));
        } else {
            installedFlag.setText(context.getString(R.string.ready_to_install_flag));
        }
        return view;
    }
}
