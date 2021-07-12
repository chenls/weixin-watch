/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.net.http.AndroidHttpClient
 *  android.os.Build$VERSION
 *  org.apache.http.client.HttpClient
 */
package com.android.volley.toolbox;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import java.io.File;
import org.apache.http.client.HttpClient;

public class Volley {
    private static final String DEFAULT_CACHE_DIR = "volley";

    public static RequestQueue newRequestQueue(Context context) {
        return Volley.newRequestQueue(context, null);
    }

    public static RequestQueue newRequestQueue(Context context, int n2) {
        return Volley.newRequestQueue(context, null, n2);
    }

    public static RequestQueue newRequestQueue(Context context, HttpStack httpStack) {
        return Volley.newRequestQueue(context, httpStack, -1);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static RequestQueue newRequestQueue(Context object, HttpStack httpStack, int n2) {
        File file = new File(object.getCacheDir(), DEFAULT_CACHE_DIR);
        String string2 = "volley/0";
        try {
            String string3 = object.getPackageName();
            object = object.getPackageManager().getPackageInfo(string3, 0);
            object = string3 + "/" + object.versionCode;
            string2 = object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {}
        object = httpStack;
        if (httpStack == null) {
            object = Build.VERSION.SDK_INT >= 9 ? new HurlStack() : new HttpClientStack((HttpClient)AndroidHttpClient.newInstance((String)string2));
        }
        object = new BasicNetwork((HttpStack)object);
        object = n2 <= -1 ? new RequestQueue(new DiskBasedCache(file), (Network)object) : new RequestQueue(new DiskBasedCache(file, n2), (Network)object);
        object.start();
        return object;
    }
}

