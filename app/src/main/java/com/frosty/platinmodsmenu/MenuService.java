package com.frosty.platinmodsmenu;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MenuService extends Service {
    private static boolean VIP;

    private static int[] backgroundColor;

    private static int[] borderColor;

    private static boolean menuRestarted;

    private static boolean resetAvailable;

    private static int[] seekbarValue;

    private static int[] spinnerValue;

    private static int[] textColor;

    private static int[] toggleOffColor;

    private static int[] toggleOnColor;

    private static boolean[] toggleValue;

    int[] aboutMarginsL = new int[] { 0, 187, 62, 125, 187, 0, 0, 0, 287, 125 };

    int[] aboutMarginsT = new int[] { 5, 0, 5, 0, 25, 25, 5, 175, 25, 0 };

    int[] aboutParamsX = new int[] { 125, 2, 2, 2, 102, 127, 2, 289, 2, 62 };

    int[] aboutParamsY = new int[] { 2, 25, 20, 25, 2, 2, 170, 2, 150, 2 };

    private RelativeLayout.LayoutParams[] borderParams;

    private RelativeLayout[] borders;

    private ImageView gPmtIcon = null;

    private ScrollView gScrollView = null;

    private String gameName;

    private String gameVersion;

    private int height;

    private RelativeLayout iconHolder;

    WindowManager.LayoutParams iconOverlayParams;

    private int[] m_gConfig = new int[99];

    private RelativeLayout menuHolder;

    WindowManager.LayoutParams menuOverlayParams;

    private int menuSize;

    private TextView[] menuText;

    private RelativeLayout.LayoutParams[] menuTextParams;

    private String modCredits;

    private LinearLayout modHolder;

    int[] modMarginsL = new int[] { 0, 0, 62, 62, 287, 0, 62, 125, 125, 187 };

    int[] modMarginsT = new int[] { 0, 0, 0, 25, 25, 175, 5, 5, 5, 5 };

    int[] modParamsX = new int[] { 62, 2, 2, 226, 2, 289, 63, 2, 62, 2 };

    int[] modParamsY = new int[] { 2, 175, 25, 2, 150, 2, 2, 20, 2, 20 };

    private String modResource;

    private String modYT;

    private RelativeLayout modsView;

    int[] optionMarginsL = new int[] { 0, 62, 62, 125, 125, 0, 0, 0, 287, 125 };

    int[] optionMarginsT = new int[] { 5, 0, 0, 0, 25, 25, 5, 175, 25, 5 };

    int[] optionParamsX = new int[] { 62, 63, 2, 2, 165, 64, 2, 289, 2, 62 };

    int[] optionParamsY = new int[] { 2, 2, 25, 25, 2, 2, 170, 2, 150, 2 };

    private int overlay;

    private int scrollPosY;

    private int seekbarAdded;

    private int seekbarAmount;

    private int spinnerAdded;

    private int spinnerAmount;

    int[] textMarginsL = new int[] { 0, 62, 125 };

    int[] textMarginsL2 = new int[] { 2, 64, 127 };

    int[] textMarginsL3 = new int[] { 0, 62, 125 };

    int[] textMarginsT = new int[] { 0, 5, 5 };

    int[] textMarginsT2 = new int[] { 7, 2, 7 };

    int[] textMarginsT3 = new int[] { 5, 5, 0 };

    int[] textParamsX = new int[] { 62, 63, 62 };

    int[] textParamsX2 = new int[] { 60, 61, 60 };

    int[] textParamsX3 = new int[] { 62, 63, 62 };

    int[] textParamsY = new int[] { 25, 20, 20 };

    int[] textParamsY2 = new int[] { 18, 23, 18 };

    int[] textParamsY3 = new int[] { 20, 20, 25 };

    private int textSize;

    private int toggleAdded;

    private int toggleAmount;

    private int width;

    private WindowManager windowManager;

    public native void changeToggle(int i);

    private native String[] getListFT();

    private void AddAboutInfo() {
        AddAboutText("------------------------------------");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Serial: ");
        stringBuilder.append(Settings.Secure.getString(getContentResolver(), "android_id"));
        AddAboutText(stringBuilder.toString());
        AddAboutText("------------------------------------");
        AddAboutText("Created by Frosty Hacker");
        AddHyperlink("Youtube Channel", "https://www.youtube.com/c/FROSTYHACKER666");
        AddAboutText("------------------------------------");
    }

    private void AddAboutText(String paramString) {
        TextView textView = new TextView((Context)this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        textView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        layoutParams.setMargins(this.width / 100, 0, 0, 0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        stringBuilder.append(paramString);
        textView.setText(stringBuilder.toString());
        textView.setTextSize(this.textSize);
        int[] arrayOfInt = textColor;
        textView.setTextColor(Color.argb(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]));
        this.modHolder.addView((View)textView);
    }

    private void AddButton(String paramString, View.OnClickListener paramOnClickListener) {
        Button button = new Button((Context)this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(paramString);
        button.setText(stringBuilder.toString());
        button.setTextSize(this.textSize);
        button.setOnClickListener(paramOnClickListener);
        int[] arrayOfInt = textColor;
        button.setTextColor(Color.argb(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]));
        button.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -2));
        button.setScaleX(0.75F);
        button.setScaleY(0.75F);
        this.modHolder.addView((View)button);
    }

    private void AddHyperlink(String paramString1, final String url) {
        TextView textView = new TextView((Context)this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        textView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        layoutParams.setMargins(this.width / 100, 0, 0, 0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        stringBuilder.append(paramString1);
        textView.setText(stringBuilder.toString());
        textView.setTextSize(this.textSize);
        textView.setTextColor(Color.parseColor("#0000EE"));
        this.modHolder.addView((View)textView);
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                MenuService.this.iconHolder.setVisibility(View.VISIBLE);
                MenuService.this.menuHolder.setVisibility(View.GONE);
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse(url));
                intent = Intent.createChooser(intent, "Open With");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MenuService.this.startActivity(intent);
            }
        });
    }

    private void AddModifications() {
        UpdateConfig();
        //AddAboutText("[-] Visual Hack");
        final String[] listFT = getListFT();
        for (int i2 = 0; i2 < listFT.length; i2++) {
            final int l2 = i2;
            AddToggle(listFT[i2], new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton param1CompoundButton, boolean isChecked) {
                    if (isChecked) {
                        changeToggle(l2);
                    }
                    else
                    {
                        changeToggle(l2);
                    }
                }
            });
        }


    }

    private void AddMultipliers(String paramString1, String paramString2, String paramString3, View.OnClickListener paramOnClickListener1, View.OnClickListener paramOnClickListener2) {
        LinearLayout linearLayout = new LinearLayout((Context)this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-2, -2));
        this.modHolder.addView((View)linearLayout);
        TextView textView = new TextView((Context)this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        stringBuilder.append(paramString1);
        textView.setText(stringBuilder.toString());
        int[] arrayOfInt1 = textColor;
        textView.setTextColor(Color.argb(arrayOfInt1[0], arrayOfInt1[1], arrayOfInt1[2], arrayOfInt1[3]));
        textView.setTextSize(this.textSize);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(-2, -2);
        textView.setLayoutParams((ViewGroup.LayoutParams)layoutParams1);
        layoutParams1.setMargins(this.width / 100, 0, 0, 0);
        linearLayout.addView((View)textView);
        Button button = new Button((Context)this);
        button.setText(paramString2);
        button.setTextSize(this.textSize);
        int[] arrayOfInt2 = textColor;
        button.setTextColor(Color.argb(arrayOfInt2[0], arrayOfInt2[1], arrayOfInt2[2], arrayOfInt2[3]));
        arrayOfInt2 = toggleOnColor;
        button.setBackgroundColor(Color.argb(arrayOfInt2[0], arrayOfInt2[1], arrayOfInt2[2], arrayOfInt2[3]));
        button.setOnClickListener(paramOnClickListener1);
        button.setScaleX(0.75F);
        button.setScaleY(0.75F);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.setMargins(this.width / 125, 0, 0, 0);
        button.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
        linearLayout.addView((View)button);
        button = new Button((Context)this);
        button.setText(paramString3);
        button.setTextSize(this.textSize);
        int[] arrayOfInt3 = textColor;
        button.setTextColor(Color.argb(arrayOfInt3[0], arrayOfInt3[1], arrayOfInt3[2], arrayOfInt3[3]));
        arrayOfInt3 = toggleOffColor;
        button.setBackgroundColor(Color.argb(arrayOfInt3[0], arrayOfInt3[1], arrayOfInt3[2], arrayOfInt3[3]));
        button.setOnClickListener(paramOnClickListener2);
        button.setScaleX(0.75F);
        button.setScaleY(0.75F);
        button.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
        linearLayout.addView((View)button);
    }

    private void AddOptionText(String paramString) {
        TextView textView = new TextView((Context)this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        textView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        layoutParams.setMargins(this.width / 100, 0, 0, 0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        stringBuilder.append(paramString);
        textView.setText(stringBuilder.toString());
        textView.setTextSize(this.textSize);
        int[] arrayOfInt = textColor;
        textView.setTextColor(Color.argb(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]));
        this.modHolder.addView((View)textView);
    }

    private void AddOptions() {
        AddOptionText("Change Border Color");
        AddSliderOptions("Alpha:", borderColor[0], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeBorderColor(i, 0, 0, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Alpha Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Red:", borderColor[1], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeBorderColor(0, i, 0, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Red Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Green:", borderColor[2], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeBorderColor(0, 0, i, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Green Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Blue:", borderColor[3], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeBorderColor(0, 0, 0, i);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Blue Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddOptionText("");
        AddOptionText("Change Background Color");
        AddSliderOptions("Alpha:", backgroundColor[0], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeBackgroundColor(i, 0, 0, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Alpha Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Red:", backgroundColor[1], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeBackgroundColor(0, i, 0, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Red Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Green:", backgroundColor[2], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeBackgroundColor(0, 0, i, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Green Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Blue:", backgroundColor[3], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeBackgroundColor(0, 0, 0, i);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Blue Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddOptionText("");
        AddOptionText("Change Toggle On Color");
        AddSliderOptions("Alpha:", toggleOnColor[0], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeToggleOnColor(i, 0, 0, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Alpha Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Red:", toggleOnColor[1], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeToggleOnColor(0, i, 0, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Red Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Green:", toggleOnColor[2], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeToggleOnColor(0, 0, i, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Green Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Blue:", toggleOnColor[3], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeToggleOnColor(0, 0, 0, i);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Blue Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddOptionText("");
        AddOptionText("Change Toggle Off Color");
        AddSliderOptions("Alpha:", toggleOffColor[0], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeToggleOffColor(i, 0, 0, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Alpha Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Red:", toggleOffColor[1], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeToggleOffColor(0, i, 0, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Red Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Green:", toggleOffColor[2], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeToggleOffColor(0, 0, i, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Green Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Blue:", toggleOffColor[3], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeToggleOffColor(0, 0, 0, i);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Blue Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddOptionText("");
        AddOptionText("Change Text Color");
        AddSliderOptions("Alpha:", textColor[0], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeToggleOffColor(i, 0, 0, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Alpha Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Red:", textColor[1], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeTextColor(0, i, 0, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Red Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Green:", textColor[2], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeTextColor(0, 0, i, 0);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Green Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddSliderOptions("Blue:", textColor[3], 255, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeTextColor(0, 0, 0, i);
                MenuService menuService = MenuService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Blue Set To: ");
                stringBuilder.append(String.valueOf(i));
                Toast.makeText((Context)menuService, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        AddOptionText("");
        AddOptionText("Change Text Size");
        AddSliderOptions("Menu:", this.menuSize, 8, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeMenuSize(i);
            }
        });
        AddSliderOptions("Text:", this.textSize, 30, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar param1SeekBar, int param1Int, boolean param1Boolean) {}

            public void onStartTrackingTouch(SeekBar param1SeekBar) {}

            public void onStopTrackingTouch(SeekBar param1SeekBar) {
                int i = param1SeekBar.getProgress();
                MenuService.this.ChangeTextSize(i);
            }
        });
        final ToggleButton button = new ToggleButton((Context)this);
        button.setText("Save Config");
        button.setTextSize(this.textSize);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                button.setText("Save Config");
                MenuService.this.WriteConfig();
            }
        });
        button.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -2));
        button.setScaleX(0.75F);
        button.setScaleY(0.75F);
        this.modHolder.addView(button);
    }

    private void AddSeekBar(String paramString, int paramInt1, int paramInt2, SeekBar.OnSeekBarChangeListener paramOnSeekBarChangeListener) {
        this.seekbarAdded++;
        TextView textView = new TextView((Context)this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        stringBuilder.append(paramString);
        textView.setText(stringBuilder.toString());
        int[] arrayOfInt = textColor;
        textView.setTextColor(Color.argb(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]));
        textView.setTextSize(this.textSize);
        textView.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-2, -2));
        this.modHolder.addView((View)textView);
        SeekBar seekBar = new SeekBar((Context)this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            seekBar.setMin(paramInt1);
        }
        seekBar.setMax(paramInt2);
        seekBar.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -2));
        seekBar.setOnSeekBarChangeListener(paramOnSeekBarChangeListener);
        this.modHolder.addView((View)seekBar);
        if (menuRestarted)
            seekBar.setProgress(seekbarValue[this.seekbarAdded]);
    }

    private void AddSliderOptions(String paramString, int paramInt1, int paramInt2, SeekBar.OnSeekBarChangeListener paramOnSeekBarChangeListener) {
        TextView textView = new TextView((Context)this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        stringBuilder.append(paramString);
        textView.setText(stringBuilder.toString());
        int[] arrayOfInt = textColor;
        textView.setTextColor(Color.argb(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]));
        textView.setTextSize(this.textSize);
        textView.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-2, -2));
        this.modHolder.addView((View)textView);
        SeekBar seekBar = new SeekBar((Context)this);
        seekBar.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        seekBar.setMax(paramInt2);
        seekBar.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -2));
        seekBar.setOnSeekBarChangeListener(paramOnSeekBarChangeListener);
        this.modHolder.addView((View)seekBar);
        seekBar.setProgress(paramInt1);
    }

    private void AddSpinner(String paramString, String[] paramArrayOfString, AdapterView.OnItemSelectedListener paramOnItemSelectedListener) {
        this.spinnerAdded++;
        TextView textView = new TextView((Context)this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        stringBuilder.append(paramString);
        textView.setText(stringBuilder.toString());
        textView.setTextSize(this.textSize);
        int[] arrayOfInt = textColor;
        textView.setTextColor(Color.argb(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]));
        textView.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -2));
        Spinner spinner = new Spinner((Context)this);
        spinner.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -2));
        //ArrayAdapter arrayAdapter = new ArrayAdapter((Context)this, 17367048, (Object[])paramArrayOfString);
        // arrayAdapter.setDropDownViewResource(17367048);
        // spinner.setAdapter((SpinnerAdapter)arrayAdapter);
        spinner.setOnItemSelectedListener(paramOnItemSelectedListener);
        spinner.getSelectedItemPosition();
        this.modHolder.addView((View)textView);
        this.modHolder.addView((View)spinner);
        if (menuRestarted)
            spinner.setSelection(spinnerValue[this.spinnerAdded]);
    }

    private void AddText(String paramString1, String paramString2) {
        TextView textView = new TextView((Context)this);
        textView.setText(paramString1);
        textView.setTextColor(Color.parseColor(paramString2));
        textView.setTextSize(this.textSize);
        textView.setPadding(15, 0, 0, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.setMargins(this.width / 100, 0, 0, 0);
        textView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.modHolder.addView((View)textView);
    }

    private void AddToggle(String paramString, CompoundButton.OnCheckedChangeListener paramOnCheckedChangeListener) {
        this.toggleAdded++;
        final Switch switch_ = new Switch((Context)this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        stringBuilder.append(paramString);
        switch_.setText(stringBuilder.toString());
        switch_.setTextSize(this.textSize);
        int[] arrayOfInt = toggleOffColor;
        switch_.setTextColor(Color.argb(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]));
        switch_.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                if (switch_.isChecked()) {
                    switch_.setTextColor(Color.argb(MenuService.toggleOnColor[0], MenuService.toggleOnColor[1], MenuService.toggleOnColor[2], MenuService.toggleOnColor[3]));
                    return;
                }
                switch_.setTextColor(Color.argb(MenuService.toggleOffColor[0], MenuService.toggleOffColor[1], MenuService.toggleOffColor[2], MenuService.toggleOffColor[3]));
            }
        });
        switch_.setOnCheckedChangeListener(paramOnCheckedChangeListener);
        switch_.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -2));
        this.modHolder.addView((View)switch_);
        if (menuRestarted && toggleValue[this.toggleAdded]) {
            switch_.setChecked(true);
            arrayOfInt = toggleOnColor;
            switch_.setTextColor(Color.argb(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]));
        }
    }

    private void ChangeBackgroundColor(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (paramInt1 != 0)
            backgroundColor[0] = paramInt1;
        if (paramInt2 != 0)
            backgroundColor[1] = paramInt2;
        if (paramInt3 != 0)
            backgroundColor[2] = paramInt3;
        if (paramInt4 != 0)
            backgroundColor[3] = paramInt4;
        RecreateMenu();
    }

    private void ChangeBorderColor(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (paramInt1 != 0)
            borderColor[0] = paramInt1;
        if (paramInt2 != 0)
            borderColor[1] = paramInt2;
        if (paramInt3 != 0)
            borderColor[2] = paramInt3;
        if (paramInt4 != 0)
            borderColor[3] = paramInt4;
        for (paramInt1 = 0; paramInt1 < 10; paramInt1++) {
            RelativeLayout relativeLayout = this.borders[paramInt1];
            int[] arrayOfInt = borderColor;
            relativeLayout.setBackgroundColor(Color.argb(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]));
        }
        RecreateMenu();
    }

    private void ChangeMenuSize(int paramInt) {
        if (paramInt < 1) {
            this.menuSize = 1;
        } else {
            this.menuSize = paramInt;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Menu Size: ");
        stringBuilder.append(String.valueOf(this.menuSize));
        Toast.makeText((Context)this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        RecreateMenu();
    }

    private void ChangeTextColor(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (paramInt1 != 0)
            textColor[0] = paramInt1;
        if (paramInt2 != 0)
            textColor[1] = paramInt2;
        if (paramInt3 != 0)
            textColor[2] = paramInt3;
        if (paramInt4 != 0)
            textColor[3] = paramInt4;
        RecreateMenu();
    }

    private void ChangeTextSize(int paramInt) {
        if (paramInt < 11) {
            this.textSize = paramInt + 10;
        } else {
            this.textSize = paramInt;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Text Size: ");
        stringBuilder.append(String.valueOf(this.textSize));
        Toast.makeText((Context)this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        RecreateMenu();
    }

    private void ChangeToggleOffColor(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (paramInt1 != 0)
            toggleOffColor[0] = paramInt1;
        if (paramInt2 != 0)
            toggleOffColor[1] = paramInt2;
        if (paramInt3 != 0)
            toggleOffColor[2] = paramInt3;
        if (paramInt4 != 0)
            toggleOffColor[3] = paramInt4;
        RecreateMenu();
    }

    private void ChangeToggleOnColor(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        if (paramInt1 != 0)
            toggleOnColor[0] = paramInt1;
        if (paramInt2 != 0)
            toggleOnColor[1] = paramInt2;
        if (paramInt3 != 0)
            toggleOnColor[2] = paramInt3;
        if (paramInt4 != 0)
            toggleOnColor[3] = paramInt4;
        RecreateMenu();
    }

    private void CreateModsTab(int i) {
        this.borders = new RelativeLayout[12];
        this.menuText = new TextView[3];
        RelativeLayout relativeLayout = new RelativeLayout((Context)this);
        this.borderParams = new RelativeLayout.LayoutParams[12];
        this.menuTextParams = new RelativeLayout.LayoutParams[3];
        relativeLayout.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-2, -2));
        this.modsView.addView((View)relativeLayout);
        TextView textView = new TextView((Context)this);
        textView.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-2, -2));
        textView.setText((CharSequence)"");
        textView.setTextSize((float)this.textSize);
        this.modHolder.addView((View)textView);
        TextView textView2 = new TextView((Context)this);
        int menuSize = this.menuSize;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(menuSize * 30, menuSize * 15);
        int menuSize2 = this.menuSize;
        layoutParams.setMargins(menuSize2 * 259, menuSize2 * 10, 0, 0);
        textView2.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        int[] backgroundColor = this.backgroundColor;
        textView2.setBackgroundColor(Color.argb(backgroundColor[0], backgroundColor[1], backgroundColor[2], backgroundColor[3]));
        int[] textColor = this.textColor;
        textView2.setTextColor(Color.argb(textColor[0], textColor[1], textColor[2], textColor[3]));
        textView2.setText((CharSequence)"X");
        textView2.setGravity(17);
        textView2.setTextSize((float)this.textSize);
        relativeLayout.addView((View)textView2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconHolder.setVisibility(View.VISIBLE);
                menuHolder.setVisibility(View.GONE);
            }
        });
        TextView textView3 = new TextView((Context)this);
        int menuSize3 = this.menuSize;
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(menuSize3 * 30, menuSize3 * 15);
        int menuSize4 = this.menuSize;
        layoutParams2.setMargins(menuSize4 * 259, menuSize4 * 175, 0, 0);
        textView3.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
        int[] backgroundColor2 = this.backgroundColor;
        textView3.setBackgroundColor(Color.argb(backgroundColor2[0], backgroundColor2[1], backgroundColor2[2], backgroundColor2[3]));
        int[] textColor2 = this.textColor;
        textView3.setTextColor(Color.argb(textColor2[0], textColor2[1], textColor2[2], textColor2[3]));
        textView3.setText((CharSequence)"+");
        textView3.setGravity(17);
        textView3.setTextSize((float)this.textSize);
        relativeLayout.addView((View)textView3);
        for (int j = 0; j < 12; ++j) {
            this.borders[j] = new RelativeLayout((Context)this);
        }
        if (i == 0) {
            for (int k = 0; k < 3; ++k) {
                this.menuText[k] = new TextView((Context)this);
                RelativeLayout.LayoutParams[] menuTextParams = this.menuTextParams;
                int n = this.textParamsX[k];
                int menuSize5 = this.menuSize;
                menuTextParams[k] = new RelativeLayout.LayoutParams(n * menuSize5, this.textParamsY[k] * menuSize5);
                RelativeLayout.LayoutParams relativeLayout$LayoutParams = this.menuTextParams[k];
                int n2 = this.textMarginsL[k];
                int menuSize6 = this.menuSize;
                relativeLayout$LayoutParams.setMargins(n2 * menuSize6, this.textMarginsT[k] * menuSize6, 0, 0);
                this.menuText[k].setLayoutParams((ViewGroup.LayoutParams)this.menuTextParams[k]);
                this.modsView.addView((View)this.menuText[k]);
                this.menuText[k].setTextSize((float)this.textSize);
                this.menuText[k].setGravity(17);
                TextView textView4 = this.menuText[k];
                int[] textColor3 = this.textColor;
                textView4.setTextColor(Color.argb(textColor3[0], textColor3[1], textColor3[2], textColor3[3]));
            }
            for (int l = 0; l < 10; ++l) {
                RelativeLayout.LayoutParams[] borderParams = this.borderParams;
                int n3 = this.modParamsX[l];
                int menuSize7 = this.menuSize;
                borderParams[l] = new RelativeLayout.LayoutParams(n3 * menuSize7, this.modParamsY[l] * menuSize7);
                RelativeLayout.LayoutParams relativeLayout$LayoutParams2 = this.borderParams[l];
                int n4 = this.modMarginsL[l];
                int menuSize8 = this.menuSize;
                relativeLayout$LayoutParams2.setMargins(n4 * menuSize8, this.modMarginsT[l] * menuSize8, 0, 0);
                this.borders[l].setLayoutParams((ViewGroup.LayoutParams)this.borderParams[l]);
                this.modsView.addView((View)this.borders[l]);
                RelativeLayout relativeLayout2 = this.borders[l];
                int[] borderColor = this.borderColor;
                relativeLayout2.setBackgroundColor(Color.argb(borderColor[0], borderColor[1], borderColor[2], borderColor[3]));
            }
            this.AddModifications();
            this.menuRestarted = true;
            TextView textView5 = this.menuText[0];
            int[] backgroundColor3 = this.backgroundColor;
            textView5.setBackgroundColor(Color.argb(backgroundColor3[0], backgroundColor3[1], backgroundColor3[2], backgroundColor3[3]));
            TextView textView6 = this.menuText[1];
            int[] backgroundColor4 = this.backgroundColor;
            textView6.setBackgroundColor(Color.argb(backgroundColor4[0] - 32, backgroundColor4[1], backgroundColor4[2], backgroundColor4[3]));
            TextView textView7 = this.menuText[2];
            int[] backgroundColor5 = this.backgroundColor;
            textView7.setBackgroundColor(Color.argb(backgroundColor5[0] - 32, backgroundColor5[1], backgroundColor5[2], backgroundColor5[3]));
        }
        if (i == 1) {
            for (int n5 = 0; n5 < 3; ++n5) {
                this.menuText[n5] = new TextView((Context)this);
                RelativeLayout.LayoutParams[] menuTextParams2 = this.menuTextParams;
                int n6 = this.textParamsX2[n5];
                int menuSize9 = this.menuSize;
                menuTextParams2[n5] = new RelativeLayout.LayoutParams(n6 * menuSize9, this.textParamsY2[n5] * menuSize9);
                RelativeLayout.LayoutParams relativeLayout$LayoutParams3 = this.menuTextParams[n5];
                int n7 = this.textMarginsL2[n5];
                int menuSize10 = this.menuSize;
                relativeLayout$LayoutParams3.setMargins(n7 * menuSize10, this.textMarginsT2[n5] * menuSize10, 0, 0);
                this.menuText[n5].setLayoutParams((ViewGroup.LayoutParams)this.menuTextParams[n5]);
                this.modsView.addView((View)this.menuText[n5]);
                this.menuText[n5].setTextSize((float)this.textSize);
                this.menuText[n5].setGravity(17);
                TextView textView8 = this.menuText[n5];
                int[] textColor4 = this.textColor;
                textView8.setTextColor(Color.argb(textColor4[0], textColor4[1], textColor4[2], textColor4[3]));


            }
            for (int n8 = 0; n8 < 10; ++n8) {
                RelativeLayout.LayoutParams[] borderParams2 = this.borderParams;
                int n9 = this.optionParamsX[n8];
                int menuSize11 = this.menuSize;
                borderParams2[n8] = new RelativeLayout.LayoutParams(n9 * menuSize11, this.optionParamsY[n8] * menuSize11);
                RelativeLayout.LayoutParams relativeLayout$LayoutParams4 = this.borderParams[n8];
                int n10 = this.optionMarginsL[n8];
                int menuSize12 = this.menuSize;
                relativeLayout$LayoutParams4.setMargins(n10 * menuSize12, this.optionMarginsT[n8] * menuSize12, 0, 0);
                this.borders[n8].setLayoutParams((ViewGroup.LayoutParams)this.borderParams[n8]);
                relativeLayout.addView((View)this.borders[n8]);
                RelativeLayout relativeLayout3 = this.borders[n8];
                int[] borderColor2 = this.borderColor;
                relativeLayout3.setBackgroundColor(Color.argb(borderColor2[0], borderColor2[1], borderColor2[2], borderColor2[3]));
            }
            AddOptions();
            this.menuRestarted = true;
            RelativeLayout relativeLayout4 = new RelativeLayout((Context)this);
            int menuSize13 = this.menuSize;
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(menuSize13 * 2, menuSize13 * 20);
            int menuSize14 = this.menuSize;
            layoutParams3.setMargins(menuSize14 * 187, menuSize14 * 5, 0, 0);
            relativeLayout4.setLayoutParams((ViewGroup.LayoutParams)layoutParams3);
            int[] borderColor3 = this.borderColor;
            relativeLayout4.setBackgroundColor(Color.argb(borderColor3[0], borderColor3[1], borderColor3[2], borderColor3[3]));
            relativeLayout.addView((View)relativeLayout4);
            TextView textView9 = this.menuText[0];
            int[] backgroundColor6 = this.backgroundColor;
            textView9.setBackgroundColor(Color.argb(backgroundColor6[0] - 32, backgroundColor6[1], backgroundColor6[2], backgroundColor6[3]));
            TextView textView10 = this.menuText[1];
            int[] backgroundColor7 = this.backgroundColor;
            textView10.setBackgroundColor(Color.argb(backgroundColor7[0], backgroundColor7[1], backgroundColor7[2], backgroundColor7[3]));
            TextView textView11 = this.menuText[2];
            int[] backgroundColor8 = this.backgroundColor;
            textView11.setBackgroundColor(Color.argb(backgroundColor8[0] - 32, backgroundColor8[1], backgroundColor8[2], backgroundColor8[3]));
        }
        if (i == 2) {
            RelativeLayout.LayoutParams[] menuTextParams3;
            int n11;
            int menuSize15;
            RelativeLayout.LayoutParams relativeLayout$LayoutParams5;
            int n12;
            int menuSize16;
            TextView textView12;
            int[] textColor5;
            for (i = 0; i < 3; ++i) {
                this.menuText[i] = new TextView((Context)this);
                menuTextParams3 = this.menuTextParams;
                n11 = this.textParamsX3[i];
                menuSize15 = this.menuSize;
                menuTextParams3[i] = new RelativeLayout.LayoutParams(n11 * menuSize15, this.textParamsY3[i] * menuSize15);
                relativeLayout$LayoutParams5 = this.menuTextParams[i];
                n12 = this.textMarginsL3[i];
                menuSize16 = this.menuSize;
                relativeLayout$LayoutParams5.setMargins(n12 * menuSize16, this.textMarginsT3[i] * menuSize16, 0, 0);
                this.menuText[i].setLayoutParams((ViewGroup.LayoutParams)this.menuTextParams[i]);
                this.modsView.addView((View)this.menuText[i]);
                this.menuText[i].setTextSize((float)this.textSize);
                this.menuText[i].setGravity(17);
                textView12 = this.menuText[i];
                textColor5 = this.textColor;
                textView12.setTextColor(Color.argb(textColor5[0], textColor5[1], textColor5[2], textColor5[3]));
                final int finalI2 = i;

            }
            RelativeLayout.LayoutParams[] borderParams3;
            int n13;
            int menuSize17;
            RelativeLayout.LayoutParams relativeLayout$LayoutParams6;
            int n14;
            int menuSize18;
            RelativeLayout relativeLayout5;
            int[] borderColor4;
            for (i = 0; i < 10; ++i) {
                borderParams3 = this.borderParams;
                n13 = this.aboutParamsX[i];
                menuSize17 = this.menuSize;
                borderParams3[i] = new RelativeLayout.LayoutParams(n13 * menuSize17, this.aboutParamsY[i] * menuSize17);
                relativeLayout$LayoutParams6 = this.borderParams[i];
                n14 = this.aboutMarginsL[i];
                menuSize18 = this.menuSize;
                relativeLayout$LayoutParams6.setMargins(n14 * menuSize18, this.aboutMarginsT[i] * menuSize18, 0, 0);
                this.borders[i].setLayoutParams((ViewGroup.LayoutParams)this.borderParams[i]);
                this.modsView.addView((View)this.borders[i]);
                relativeLayout5 = this.borders[i];
                borderColor4 = this.borderColor;
                relativeLayout5.setBackgroundColor(Color.argb(borderColor4[0], borderColor4[1], borderColor4[2], borderColor4[3]));
            }
            AddAboutInfo();
            this.menuRestarted = true;
            TextView textView13 = this.menuText[0];
            int[] backgroundColor9 = this.backgroundColor;
            textView13.setBackgroundColor(Color.argb(backgroundColor9[0] - 32, backgroundColor9[1], backgroundColor9[2], backgroundColor9[3]));
            TextView textView14 = this.menuText[1];
            int[] backgroundColor10 = this.backgroundColor;
            textView14.setBackgroundColor(Color.argb(backgroundColor10[0] - 32, backgroundColor10[1], backgroundColor10[2], backgroundColor10[3]));
            TextView textView15 = this.menuText[2];
            int[] backgroundColor11 = this.backgroundColor;
            textView15.setBackgroundColor(Color.argb(backgroundColor11[0], backgroundColor11[1], backgroundColor11[2], backgroundColor11[3]));
        }
        this.menuText[0].setText((CharSequence)"Mods");
        this.menuText[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                modsView.removeAllViews();
                SetupNewMenu(0);
            }
        });
        this.menuText[1].setText((CharSequence)"Options");
        this.menuText[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modsView.removeAllViews();
                SetupNewMenu(1);

            }
        });
        this.menuText[2].setText((CharSequence)"About");
        this.menuText[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modsView.removeAllViews();
                SetupNewMenu(2);

            }
        });
    }

    private void CustomizeMenu() {
        this.gameName = "Test Menu";
        this.gameVersion = "Version 1";
        this.modCredits = "Created by Aimar";
        VIP = true;
        this.modResource = "";
        this.modYT = "";
        this.toggleAmount = 25;
        this.seekbarAmount = 25;
        this.spinnerAmount = 25;
    }

    private boolean IsAppInBackground(Context paramContext) {
        boolean bool2 = true;
        boolean bool1 = true;
        ActivityManager activityManager = (ActivityManager)paramContext.getSystemService(ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > 20) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                bool2 = bool1;
                if (runningAppProcessInfo.importance == 100) {
                    String[] arrayOfString = runningAppProcessInfo.pkgList;
                    int j = arrayOfString.length;
                    for (int i = 0; i < j; i++) {
                        if (arrayOfString[i].equals(paramContext.getPackageName()))
                            bool1 = false;
                    }
                    bool2 = bool1;
                }
                bool1 = bool2;
            }
            return bool1;
        }
        bool1 = bool2;
        if (((ActivityManager.RunningTaskInfo)activityManager.getRunningTasks(1).get(0)).topActivity.getPackageName().equals(paramContext.getPackageName()))
            bool1 = false;
        return bool1;
    }

    private void LoadConfig() throws Exception {
        String[] arrayOfString = new String[2];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
        stringBuilder.append("/config/fsf.cfg");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(stringBuilder.toString()));
        while (true) {
            String str = bufferedReader.readLine();
            if (str != null) {
                if (str.contains("Border Alpha:")) {
                    String[] arrayOfString1 = str.split(": ");
                    borderColor[0] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Border Red:")) {
                    String[] arrayOfString1 = str.split(": ");
                    borderColor[1] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Border Green:")) {
                    String[] arrayOfString1 = str.split(": ");
                    borderColor[2] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Border Blue:")) {
                    String[] arrayOfString1 = str.split(": ");
                    borderColor[3] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Background Alpha:")) {
                    String[] arrayOfString1 = str.split(": ");
                    backgroundColor[0] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Background Red:")) {
                    String[] arrayOfString1 = str.split(": ");
                    backgroundColor[1] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Background Green:")) {
                    String[] arrayOfString1 = str.split(": ");
                    backgroundColor[2] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Background Blue:")) {
                    String[] arrayOfString1 = str.split(": ");
                    backgroundColor[3] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Toggle On Alpha:")) {
                    String[] arrayOfString1 = str.split(": ");
                    toggleOnColor[0] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Toggle On Red:")) {
                    String[] arrayOfString1 = str.split(": ");
                    toggleOnColor[1] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Toggle On Green:")) {
                    String[] arrayOfString1 = str.split(": ");
                    toggleOnColor[2] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Toggle On Blue:")) {
                    String[] arrayOfString1 = str.split(": ");
                    toggleOnColor[3] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Toggle Off Alpha:")) {
                    String[] arrayOfString1 = str.split(": ");
                    toggleOffColor[0] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Toggle Off Red:")) {
                    String[] arrayOfString1 = str.split(": ");
                    toggleOffColor[1] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Toggle Off Green:")) {
                    String[] arrayOfString1 = str.split(": ");
                    toggleOffColor[2] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Toggle Off Blue:")) {
                    String[] arrayOfString1 = str.split(": ");
                    toggleOffColor[3] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Text Alpha:")) {
                    String[] arrayOfString1 = str.split(": ");
                    textColor[0] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Text Red:")) {
                    String[] arrayOfString1 = str.split(": ");
                    textColor[1] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Text Green:")) {
                    String[] arrayOfString1 = str.split(": ");
                    textColor[2] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Text Blue:")) {
                    String[] arrayOfString1 = str.split(": ");
                    textColor[3] = Integer.valueOf(arrayOfString1[1]).intValue();
                }
                if (str.contains("Sizement Menu:"))
                    this.menuSize = Integer.valueOf(str.split(": ")[1]).intValue();
                if (str.contains("Sizement Text:"))
                    this.textSize = Integer.valueOf(str.split(": ")[1]).intValue();
                continue;
            }
            bufferedReader.close();
            return;
        }
    }

    private void MenuSystem() {
        this.windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            this.overlay = 2038;
        } else {
            this.overlay = 2002;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        this.height = displayMetrics.widthPixels;
        this.width = displayMetrics.heightPixels;
        getResources().getDisplayMetrics();
        final WindowManager.LayoutParams iconOverlayParam = new WindowManager.LayoutParams(-2, -2, this.overlay, 8, -3);
        int i = this.menuSize;
        final WindowManager.LayoutParams menuOverlayParam = new WindowManager.LayoutParams(i * 289, i * 190, this.overlay, 8, -3);
        iconOverlayParam.gravity = 51;
        menuOverlayParam.gravity = 17;
        iconOverlayParam.x = 0;
        iconOverlayParam.y = 0;
        menuOverlayParam.x = 0;
        menuOverlayParam.y = 0;
        this.iconOverlayParams = iconOverlayParam;
        this.menuOverlayParams = menuOverlayParam;
        final RelativeLayout icon = new RelativeLayout((Context)this);
        RelativeLayout relativeLayout2 = new RelativeLayout((Context)this);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(this.width, -2);
        icon.setLayoutParams((ViewGroup.LayoutParams)layoutParams3);
        relativeLayout2.setLayoutParams((ViewGroup.LayoutParams)layoutParams4);
        this.windowManager.addView((View)icon, iconOverlayParam);
        this.windowManager.addView((View)relativeLayout2, (ViewGroup.LayoutParams)menuOverlayParam);
        this.iconHolder = icon;
        this.menuHolder = relativeLayout2;
        this.iconHolder.setVisibility(View.VISIBLE);
        this.menuHolder.setVisibility(View.GONE);
        SetupNewMenu(2);
        //iconOverlayParams,widthPixels,windowManager,heightPixels,icon
        this.iconHolder.setOnTouchListener(new View.OnTouchListener() {
            final View collapsedView = iconHolder;
            //The root element of the expanded view layout
            final View expandedView = menuHolder;
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //remember the initial position.
                        initialX = iconOverlayParams.x;
                        initialY = iconOverlayParams.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);

                        //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (Xdiff < 10 && Ydiff < 10) {

                                //When user clicks on the image view of the collapsed layout,
                                //visibility of the collapsed layout will be changed to "View.GONE"
                                //and expanded view will become visible.
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);


                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        iconOverlayParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                        iconOverlayParams.y = initialY + (int) (event.getRawY() - initialTouchY);

                        //Update the layout with new X & Y coordinate
                        windowManager.updateViewLayout(icon, iconOverlayParams);
                        return true;
                }
                return false;
            }
        });
        this.menuHolder.setOnTouchListener(new View.OnTouchListener() {
            private float initialTouchX;

            private float initialTouchY;

            private int initialX;

            private int initialY;

            public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
                int i = param1MotionEvent.getAction();
                if (i != 0) {
                    if (i != 1) {
                        if (i != 2)
                            return false;
                        menuOverlayParam.x = this.initialX + (int)(param1MotionEvent.getRawX() - this.initialTouchX);
                        menuOverlayParam.y = this.initialY + (int)(param1MotionEvent.getRawY() - this.initialTouchY);
                        MenuService.this.windowManager.updateViewLayout((View)MenuService.this.menuHolder, (ViewGroup.LayoutParams)menuOverlayParam);
                        return true;
                    }
                    i = (int)(param1MotionEvent.getRawX() - this.initialTouchX);
                    i = (int)(param1MotionEvent.getRawY() - this.initialTouchY);
                    return true;
                }
                this.initialX = menuOverlayParam.x;
                this.initialY = menuOverlayParam.y;
                this.initialTouchX = param1MotionEvent.getRawX();
                this.initialTouchY = param1MotionEvent.getRawY();
                return true;
            }
        });
        PerformLoop();
    }
    private void PerformLoop() {
        if (IsAppInBackground((Context)this)) {
            this.iconHolder.setVisibility(View.GONE);
            this.menuHolder.setVisibility(View.GONE);
            resetAvailable = true;
        }
        if(!IsAppInBackground((Context)this) &&resetAvailable) {
            this.iconHolder.setVisibility(View.VISIBLE);
            resetAvailable = false;
        }
        Runnable runnable = new Runnable() {
            public void run() {
                MenuService.this.PerformLoop2();
            }
        };
        (new Handler()).postDelayed(runnable, 100L);
    }

    private void PerformLoop2() {
        PerformLoop();
    }
    private void RecreateMenu() {
        WindowManager.LayoutParams layoutParams = this.menuOverlayParams;
        if (layoutParams != null) {
            int j = this.menuSize;
            layoutParams.width = j * 289;
            layoutParams.height = j * 190;
            this.windowManager.updateViewLayout((View)this.menuHolder, (ViewGroup.LayoutParams)layoutParams);
        }
        final int i = this.gScrollView.getScrollY();
        menuRestarted = true;
        this.modsView.removeAllViews();
        SetupNewMenu(1);
        this.gScrollView.post(new Runnable() {
            public void run() {
                MenuService.this.gScrollView.scrollTo(0, i);
            }
        });
    }

    private void SetupNewMenu(int paramInt) {
        this.toggleAdded = -1;
        this.spinnerAdded = -1;
        this.seekbarAdded = -1;
        RelativeLayout relativeLayout2 = new RelativeLayout((Context)this);
        RelativeLayout relativeLayout3 = new RelativeLayout((Context)this);
        RelativeLayout relativeLayout4 = new RelativeLayout((Context)this);
        RelativeLayout relativeLayout5 = new RelativeLayout((Context)this);
        ImageView imageView = new ImageView((Context)this);
        ScrollView scrollView = new ScrollView((Context)this);
        RelativeLayout relativeLayout1 = new RelativeLayout((Context)this);
        LinearLayout linearLayout = new LinearLayout((Context)this);
        this.gScrollView = scrollView;
        this.modHolder = linearLayout;
        this.modsView = relativeLayout3;
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(this.menuSize * 289, -2);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
        RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(-2, -2);
        int i = this.width;
        int j = this.height;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams((i + j) / 16, (i + j) / 16);
        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(-1, this.menuSize * 150);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(-1, -2);
        LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(-1, -2);
        relativeLayout2.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
        relativeLayout3.setLayoutParams((ViewGroup.LayoutParams)layoutParams3);
        relativeLayout4.setLayoutParams((ViewGroup.LayoutParams)layoutParams4);
        relativeLayout5.setLayoutParams((ViewGroup.LayoutParams)layoutParams5);
        imageView.setLayoutParams(layoutParams);
        relativeLayout1.setLayoutParams((ViewGroup.LayoutParams)layoutParams1);
        scrollView.setLayoutParams((ViewGroup.LayoutParams)layoutParams6);
        this.modHolder.setLayoutParams((ViewGroup.LayoutParams)layoutParams7);
        this.modHolder.setOrientation(LinearLayout.VERTICAL);
        this.iconHolder.addView((View)relativeLayout2);
        relativeLayout2.addView((View)imageView);
        this.menuHolder.addView((View)relativeLayout3);
        layoutParams3.setMargins(0, 0, 0, 0);
        relativeLayout3.addView((View)relativeLayout1);
        relativeLayout1.addView((View)scrollView);
        scrollView.addView((View)linearLayout);
        final byte[] decode = Base64.decode("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", 0);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(decode, 0, decode.length));
        this.gPmtIcon = imageView;
        layoutParams1.setMargins(0, this.menuSize * 25, 0, 0);
        int[] arrayOfInt = backgroundColor;
        relativeLayout1.setBackgroundColor(Color.argb(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]));
        CreateModsTab(paramInt);
    }

    private void UpdateConfig() {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0;; i++) {
                if (i < this.m_gConfig.length) {
                    stringBuilder.append(this.m_gConfig[i]);
                    if (i < this.m_gConfig.length - 1)
                        stringBuilder.append(",");
                } else {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(getFilesDir(), "Config.ini")));
                    bufferedWriter.write(stringBuilder.toString());
                    bufferedWriter.close();
                    return;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private void WriteConfig() {


        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Environment.getExternalStorageDirectory().getPath());
            stringBuilder.append("/config/");
            File file = new File(stringBuilder.toString());
            file.mkdir();
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append(Environment.getExternalStorageDirectory().getPath());
            stringBuilder1.append("/config/fsf.cfg");
            File file1 = new File(stringBuilder1.toString());
            file1.createNewFile();
            FileWriter fileWriter = new FileWriter(file1);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Mod Menu Configuration\n\n~ Border Color ~\nBorder Alpha: ");
            stringBuilder2.append(MenuService.borderColor[0]);
            stringBuilder2.append("\nBorder Red: ");
            stringBuilder2.append(MenuService.borderColor[1]);
            stringBuilder2.append("\nBorder Green: ");
            stringBuilder2.append(MenuService.borderColor[2]);
            stringBuilder2.append("\nBorder Blue: ");
            stringBuilder2.append(MenuService.borderColor[3]);
            stringBuilder2.append("\n\n~ Background Color ~\nBackground Alpha: ");
            stringBuilder2.append(MenuService.backgroundColor[0]);
            stringBuilder2.append("\nBackground Red: ");
            stringBuilder2.append(MenuService.backgroundColor[1]);
            stringBuilder2.append("\nBackground Green: ");
            stringBuilder2.append(MenuService.backgroundColor[2]);
            stringBuilder2.append("\nBackground Blue: ");
            stringBuilder2.append(MenuService.backgroundColor[3]);
            stringBuilder2.append("\n\n~ Toggle On Color ~\nToggle On Alpha: ");
            stringBuilder2.append(MenuService.toggleOnColor[0]);
            stringBuilder2.append("\nToggle On Red: ");
            stringBuilder2.append(MenuService.toggleOnColor[1]);
            stringBuilder2.append("\nToggle On Green: ");
            stringBuilder2.append(MenuService.toggleOnColor[2]);
            stringBuilder2.append("\nToggle On Blue: ");
            stringBuilder2.append(MenuService.toggleOnColor[3]);
            stringBuilder2.append("\n\n~ Toggle Off Color ~\nToggle Off Alpha: ");
            stringBuilder2.append(MenuService.toggleOffColor[0]);
            stringBuilder2.append("\nToggle Off Red: ");
            stringBuilder2.append(MenuService.toggleOffColor[1]);
            stringBuilder2.append("\nToggle Off Green: ");
            stringBuilder2.append(MenuService.toggleOffColor[2]);
            stringBuilder2.append("\nToggle Off Blue: ");
            stringBuilder2.append(MenuService.toggleOffColor[3]);
            stringBuilder2.append("\n\n~ Text Color ~\nText Alpha: ");
            stringBuilder2.append(MenuService.textColor[0]);
            stringBuilder2.append("\nText Red: ");
            stringBuilder2.append(MenuService.textColor[1]);
            stringBuilder2.append("\nText Green: ");
            stringBuilder2.append(MenuService.textColor[2]);
            stringBuilder2.append("\nText Blue: ");
            stringBuilder2.append(MenuService.textColor[3]);
            stringBuilder2.append("\n\n~ Sizement ~\nSizement Menu: ");
            stringBuilder2.append(MenuService.this.menuSize);
            stringBuilder2.append("\nSizement Text: ");
            stringBuilder2.append(MenuService.this.textSize);
            fileWriter.write(stringBuilder2.toString());
            fileWriter.close();

        }  catch (IOException ex) {

        }


    }

    public IBinder onBind(Intent paramIntent) {
        return null;
    }

    public void onCreate() {

        UpdateConfig();
        borderColor = new int[4];
        backgroundColor = new int[4];
        toggleOnColor = new int[4];
        toggleOffColor = new int[4];
        textColor = new int[4];
        resetAvailable = false;
        CustomizeMenu();
        toggleValue = new boolean[this.toggleAmount];
        seekbarValue = new int[this.seekbarAmount];
        spinnerValue = new int[this.spinnerAmount];
        try {
            LoadConfig();
        } catch (Exception e) {
            int[] arrayOfInt = borderColor;
            arrayOfInt[0] = 255;
            arrayOfInt[1] = 33;
            arrayOfInt[2] = 33;
            arrayOfInt[3] = 33;
            arrayOfInt = backgroundColor;
            arrayOfInt[0] = 255;
            arrayOfInt[1] = 255;
            arrayOfInt[2] = 255;
            arrayOfInt[3] = 255;
            arrayOfInt = toggleOnColor;
            arrayOfInt[0] = 255;
            arrayOfInt[1] = 0;
            arrayOfInt[2] = 0;
            arrayOfInt[3] = 0;
            arrayOfInt = toggleOffColor;
            arrayOfInt[0] = 255;
            arrayOfInt[1] = 0;
            arrayOfInt[2] = 0;
            arrayOfInt[3] = 0;
            arrayOfInt = textColor;
            arrayOfInt[0] = 255;
            arrayOfInt[1] = 0;
            arrayOfInt[2] = 0;
            arrayOfInt[3] = 0;
            this.menuSize = 3;
            this.textSize = 14;
        }

        MenuSystem();
    }

    public void onDestroy() {
        super.onDestroy();
        RelativeLayout relativeLayout = this.iconHolder;
        if (relativeLayout != null)
            this.windowManager.removeView((View)relativeLayout);
    }


    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
        return START_NOT_STICKY;
    }

    public void onTaskRemoved(Intent paramIntent) {
        stopSelf();
        try {
            Thread.sleep(100L);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        super.onTaskRemoved(paramIntent);
    }
}
