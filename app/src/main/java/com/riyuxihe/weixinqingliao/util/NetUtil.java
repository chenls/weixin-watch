package com.riyuxihe.weixinqingliao.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;
import android.util.Pair;

import com.riyuxihe.weixinqingliao.model.Token;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetUtil {
    public static Pair<Boolean, Boolean> checkNet(@NonNull Context context) {
        boolean isBlocked = false;
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return Pair.create(false, false);
        }
        boolean isConnected = activeNetworkInfo.isConnected();
        if (activeNetworkInfo.getDetailedState() == NetworkInfo.DetailedState.BLOCKED) {
            isBlocked = true;
        }
        return Pair.create(Boolean.valueOf(isConnected), Boolean.valueOf(isBlocked));
    }

    public static String getHttpsResponse(String url, int timeout) throws IOException {
        return getHttpsResponse(url, timeout, null);
    }

    public static String getHttpsResponse(String url, int timeout, String cookie) throws IOException {
        HttpsURLConnection conn = getHttpsConnection(url, timeout);
        if (cookie != null) {
            conn.setRequestProperty(Token.COOKIE, cookie);
        }
        conn.connect();
        String text = StreamUtil.readFromStream(conn.getInputStream(), "utf-8");
        conn.disconnect();
        return text;
    }

    public static HttpsURLConnection getHttpsConnection(String url, int timeout) throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
        conn.setRequestProperty("Referer", "https://wx2.qq.com/");
        conn.setRequestProperty("User-Agent", Constants.USER_AGENT);
        if (timeout > 0) {
            conn.setConnectTimeout(timeout);
        }
        return conn;
    }
}
