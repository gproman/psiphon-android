package com.psiphon3.psiphonlibrary;


import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        numApps = 4;
        apps = new StandUpAppInfo[numApps];
        apps[0] = new StandUpAppInfo(getString(R.string.briar_name), R.drawable.briar, false, getString(R.string.briar_blurb));
        apps[1] = new StandUpAppInfo(getString(R.string.serval_name), R.drawable.serval, false, getString(R.string.serval_blurb));
        apps[2] = new StandUpAppInfo(getString(R.string.psiphonmesh_name), R.drawable.psiphonmesh, false, getString(R.string.psiphonmesh_blurb));
        apps[3] = new StandUpAppInfo(getString(R.string.meshenger_name), R.drawable.meshenger, false, getString(R.string.meshenger_blurb));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.stand_up_main_activity);
        simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView
        // Create an object of StandUpCustomAdapter and set Adapter to GirdView
        StandUpCustomAdapter standUpCustomAdapter = new StandUpCustomAdapter(getApplicationContext(), apps);
        simpleGrid.setAdapter(standUpCustomAdapter);
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                Intent intent = new Intent(StandUpMainActivity.this, StandUpInstallActivity.class);
                intent.putExtra("image", apps[position].icon); // put image data in Intent
                intent.putExtra("name", apps[position].name);
                intent.putExtra("installed", apps[position].installed);
                intent.putExtra("blurb", apps[position].blurb);
                startActivity(intent); // start Intent
            }
        });
    }
}
