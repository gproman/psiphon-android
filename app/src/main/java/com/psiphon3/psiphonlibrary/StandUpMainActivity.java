package com.psiphon3.psiphonlibrary;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.psiphon3.R;

public class StandUpMainActivity extends AppCompatActivity {

    GridView simpleGrid;
    int numApps;
    StandUpAppInfo[] apps;
    PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stand_up_main_activity); // BELONGS IN ON_CREATE()
    }

    @Override
    protected void onResume() {
        numApps = 4;
        apps = new StandUpAppInfo[numApps];
        packageManager = getPackageManager();
        apps[0] = new StandUpAppInfo(
                getString(R.string.briar_name),
                R.drawable.briar,
                isPackageInstalled(getString(R.string.briar_package), packageManager),
                getString(R.string.briar_blurb),
                getString(R.string.briar_package));
        apps[1] = new StandUpAppInfo(getString(
                R.string.serval_name),
                R.drawable.serval,
                isPackageInstalled(getString(R.string.serval_package), packageManager),
                getString(R.string.serval_blurb),
                getString(R.string.serval_package));
        apps[2] = new StandUpAppInfo(
                getString(R.string.psiphonmesh_name),
                R.drawable.psiphonmesh,
                false,
                getString(R.string.psiphonmesh_blurb),
                "");
        apps[3] = new StandUpAppInfo(
                getString(R.string.meshenger_name),
                R.drawable.meshenger,
                isPackageInstalled(getString(R.string.meshenger_package), packageManager),
                getString(R.string.meshenger_blurb),
                getString(R.string.meshenger_package));

        super.onResume();
        simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
        StandUpCustomAdapter standUpCustomAdapter = new StandUpCustomAdapter(getApplicationContext(), apps);
        simpleGrid.setAdapter(standUpCustomAdapter);
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StandUpMainActivity.this, StandUpInstallActivity.class);
                intent.putExtra("image", apps[position].icon); // put image data in Intent
                intent.putExtra("name", apps[position].name);
                intent.putExtra("installed", apps[position].installed);
                intent.putExtra("blurb", apps[position].blurb);
                intent.putExtra("package", apps[position].packageName);
                startActivity(intent); // start Intent
            }
        });
    }

    public static boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}


