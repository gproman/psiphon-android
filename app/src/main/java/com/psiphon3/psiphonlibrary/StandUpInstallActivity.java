package com.psiphon3.psiphonlibrary;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.psiphon3.R;

import org.zirco.utils.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    public void onStandUpInstall(View v) {
        Intent intent = getIntent(); // get Intent which we set from Previous Activity

        Toast.makeText(this, getFilesDir().toString(), Toast.LENGTH_LONG).show();
        final Uri uri = Uri.parse(getFilesDir().toString() + "/briar.apk");
//        Toast.makeText(this, intent.getStringExtra("name") + " installing...", Toast.LENGTH_LONG).show();
//

        String filename;
        switch (intent.getStringExtra("name")) {
            case "Briar":
                filename = "/briar.apk";
                break;
            case "FireChat":
                filename = "/firechat.apk";
                break;
            case "Psiphon Mesh":
                filename = "/psiphonmesh.apk";
                break;
        }

        File apkFile = new File(getFilesDir(), filename);
        try {
            InputStream apkStream = getAssets().open("briar.apk");
            FileOutputStream apkOutputStream = new FileOutputStream(apkFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = apkStream.read(buffer)) != -1) {
                apkOutputStream.write(buffer, 0, bytesRead);
            }
            apkStream.close();
            apkOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getApplicationContext();
            Uri apkUri = FileProvider.getUriForFile(getApplicationContext(), "com.psiphon3.UpgradeFileProvider", apkFile);
            Intent openFileIntent = new Intent(Intent.ACTION_VIEW);
            openFileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            openFileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            openFileIntent.setData(apkUri);
            startActivity(openFileIntent);
        }
//        try {
//            File apkFile;
//            try (OutputStream output = new FileOutputStream(apkFile)) {
//                byte[] buffer = new byte[4 * 1024];
//                int read
//
//
//            }
//        }
//        File apkFile;
//
//        InputStream apkStream = null;
//        try {
//            apkStream = getAssets().open("briar.apk");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        FileUtils.copyInputStreamToFile(apkStream, apkFile);
//
//        Intent intent2 = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent2);
    }
}
