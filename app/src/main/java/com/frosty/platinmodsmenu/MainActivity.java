package com.frosty.platinmodsmenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static android.os.Environment.getExternalStorageDirectory;

public class MainActivity extends Activity {

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Start();


    }
    public void Start() {
        System.loadLibrary("Frosty");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
          startService(new Intent(MainActivity.this, MenuService.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            if (resultCode == RESULT_OK) {
                startService(new Intent(MainActivity.this, MenuService.class));
            } else {
                Toast.makeText(this, "Draw over other app permission not available. Closing the application", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
