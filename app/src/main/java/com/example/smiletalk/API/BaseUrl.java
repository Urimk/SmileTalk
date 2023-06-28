package com.example.smiletalk.API;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

public class BaseUrl extends AppCompatActivity {
    public static Context context;
    public static String ip = "";
    private static final String start = "http://";
    private static final String end = ":5000/api/";
    public static void changeIp(String newIp) {
        newIp = start + newIp + end;
        StringBuilder stringBuilder = new StringBuilder(ip);
        stringBuilder.replace(0, ip.length(),newIp);
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