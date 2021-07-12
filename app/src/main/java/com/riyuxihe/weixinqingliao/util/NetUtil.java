/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo$DetailedState
 *  android.util.Pair
 */
package com.riyuxihe.weixinqingliao.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Pair;
import com.riyuxihe.weixinqingliao.util.StreamUtil;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

public class NetUtil {
    public static Pair<Boolean, Boolean> checkNet(@NonNull Context context) {
        boolean bl2 = false;
        if ((context = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo()) != null) {
            boolean bl3 = context.isConnected();
            if (context.getDetailedState() == NetworkInfo.DetailedState.BLOCKED) {
                bl2 = true;
            }
            return Pair.create((Object)bl3, (Object)bl2);
        }
        return Pair.create((Object)false, (Object)false);
    }

    public static HttpsURLConnection getHttpsConnection(String object, int n2) throws IOException {
        object = (HttpsURLConnection)new URL((String)object).openConnection();
        ((URLConnection)object).setRequestProperty("Referer", "https://wx.qq.com/");
        ((URLConnection)object).setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/51.0.2704.79 Chrome/51.0.2704.79 Safari/537.36");
        if (n2 > 0) {
            ((URLConnection)object).setConnectTimeout(n2);
        }
        return object;
    }

    public static String getHttpsResponse(String string2, int n2) throws IOException {
        return NetUtil.getHttpsResponse(string2, n2, null);
    }

    public static String getHttpsResponse(String object, int n2, String string2) throws IOException {
        object = NetUtil.getHttpsConnection((String)object, n2);
        if (string2 != null) {
            ((URLConnection)object).setRequestProperty("Cookie", string2);
        }
        ((URLConnection)object).connect();
        string2 = StreamUtil.readFromStream(((URLConnection)object).getInputStream(), "utf-8");
        ((HttpURLConnection)object).disconnect();
        return string2;
    }
}

