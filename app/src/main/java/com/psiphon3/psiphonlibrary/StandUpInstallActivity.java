package com.psiphon3.psiphonlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.psiphon3.R;

public class StandUpInstallActivity extends AppCompatActivity {

    ImageView appIcon;
    TextView appName;
    TextView appBlurb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stand_up_install_activity);
        Intent intent = getIntent(); // get Intent which we set from Previous Activity

        appIcon = (ImageView) findViewById(R.id.appIcon); // init a ImageView
        appIcon.setImageResource(intent.getIntExtra("image", 0)); // get image from Intent and set it in ImageView

        appName = (TextView) findViewById(R.id.name);
        appName.setText(intent.getStringExtra("name"));

        appBlurb = (TextView) findViewById(R.id.blurb);
        appBlurb.setText(intent.getStringExtra("blurb"));
    }
}
