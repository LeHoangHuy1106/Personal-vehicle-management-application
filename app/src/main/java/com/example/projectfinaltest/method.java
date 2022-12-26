package com.example.projectfinaltest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.text.Editable;

import androidx.core.app.ActivityCompat;

public class method {
    public static  String nameGmail(String gmail)
    {
        String gmail2;
        int i = gmail.indexOf("@");
        gmail2= gmail.substring(0,i);
        return gmail2;
    }

}
