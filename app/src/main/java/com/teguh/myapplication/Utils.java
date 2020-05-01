package com.teguh.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.WIFI_SERVICE;

public class Utils {

    public static final String TAG_FRAGMENT_HOME ="CaesarChipherFragment";
    public static final String TAG_FRAGMENT_VIGENERE ="VigenereCipherFragment";
    public static final String TAG_FRAGMENT_HILL ="HillChiperFragment";

}
