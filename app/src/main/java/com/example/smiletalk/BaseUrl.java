package com.example.smiletalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

public class BaseUrl extends AppCompatActivity {
    public static Context context;
    public static String ip;
    public static void changeIp(String newIp) {
        StringBuilder stringBuilder = new StringBuilder(ip);
        stringBuilder.replace(0, ip.length(), newIp);
        ip = stringBuilder.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        changeIp("10.0.2.2");
        BaseUrl.changeIp("192.168.43.169");
    }
}