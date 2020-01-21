package com.psiphon3.psiphonlibrary;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
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
import android.widget.Button;
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


    @SuppressLint("WrongViewCast")
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

        Button installButton = (Button) findViewById(R.id.installButton);

        PackageManager packageManager = getApplicationContext().getPackageManager();
        if (StandUpMainActivity.isPackageInstalled(intent.getStringExtra("package"), packageManager)) {
            installButton.setText(R.string.open);
        } else {
            installButton.setText(R.string.install);
        }
    }

    private String getApkFilename(String appName) {
        String output = "";
        switch (appName) {
            case "Briar":
                output = "briar.apk";
                break;
            case "Serval":
                output = "servalproject.apk";
                break;
            case "Psiphon Mesh":
                output = "psiphon_mesh.apk";
                break;
            case "Meshenger":
                output = "meshenger.apk";
                break;
        }
        return output;
    }

    private void openMeshApp(String packageName) {
        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
        startActivity(LaunchIntent);
    }

    public void onStandUpInstall(View v) {
        Intent intent = getIntent();
        PackageManager packageManager = getApplicationContext().getPackageManager();

        if (StandUpMainActivity.isPackageInstalled(intent.getStringExtra("package"), packageManager)) {
            openMeshApp(intent.getStringExtra("package"));
            finish();
            startActivity(getIntent());
            return;
        }
        String appName = intent.getStringExtra("name");
        if (appName.equals("Psiphon Mesh")) {
            return;
        }
        String apkName = getApkFilename(appName);
        File apkFile = new File(getFilesDir(), "/" + apkName);

        try {
            InputStream apkStream = getAssets().open(apkName);
            FileOutputStream apkOutputStream = new FileOutputStream(apkFile);
            byte[] buffer = new byte[8 * 1024];
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
        } else {
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            install.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
            startActivity(install);
            finish();
            startActivity(getIntent());
        }


    }
}
