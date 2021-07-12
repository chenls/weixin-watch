/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.ConnectivityManager
 */
package mobvoiapi;

import android.net.ConnectivityManager;

public class bq {
    public static boolean a(ConnectivityManager connectivityManager) {
        return (connectivityManager = connectivityManager.getActiveNetworkInfo()) != null && connectivityManager.isConnected();
    }
}

