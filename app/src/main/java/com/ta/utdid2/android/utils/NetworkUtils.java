/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.net.NetworkInfo$State
 *  android.net.wifi.WifiManager
 */
package com.ta.utdid2.android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import com.ta.utdid2.android.utils.StringUtils;

public class NetworkUtils {
    public static final String DEFAULT_WIFI_ADDRESS = "00-00-00-00-00-00";
    public static final String WIFI = "Wi-Fi";

    private static String _convertIntToIp(int n2) {
        return (n2 & 0xFF) + "." + (n2 >> 8 & 0xFF) + "." + (n2 >> 16 & 0xFF) + "." + (n2 >> 24 & 0xFF);
    }

    public static String[] getNetworkState(Context context) {
        String[] stringArray;
        block11: {
            block10: {
                block9: {
                    block8: {
                        stringArray = new String[]{"Unknown", "Unknown"};
                        try {
                            if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) == 0) break block8;
                            stringArray[0] = "Unknown";
                            return stringArray;
                        }
                        catch (Exception exception) {
                            return stringArray;
                        }
                    }
                    context = (ConnectivityManager)context.getSystemService("connectivity");
                    if (context != null) break block9;
                    stringArray[0] = "Unknown";
                    return stringArray;
                }
                NetworkInfo networkInfo = context.getNetworkInfo(1);
                if (networkInfo == null) break block10;
                if (networkInfo.getState() != NetworkInfo.State.CONNECTED) break block10;
                stringArray[0] = WIFI;
                return stringArray;
            }
            context = context.getNetworkInfo(0);
            if (context == null) break block11;
            if (context.getState() != NetworkInfo.State.CONNECTED) break block11;
            stringArray[0] = "2G/3G";
            stringArray[1] = context.getSubtypeName();
        }
        return stringArray;
    }

    public static String getWifiAddress(Context object) {
        if (object != null) {
            if ((object = ((WifiManager)object.getSystemService("wifi")).getConnectionInfo()) != null) {
                String string2 = object.getMacAddress();
                object = string2;
                if (StringUtils.isEmpty(string2)) {
                    object = DEFAULT_WIFI_ADDRESS;
                }
                return object;
            }
            return DEFAULT_WIFI_ADDRESS;
        }
        return DEFAULT_WIFI_ADDRESS;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String getWifiIpAddress(Context context) {
        String string3;
        String string2 = string3 = null;
        if (context == null) return string2;
        try {
            context = ((WifiManager)context.getSystemService("wifi")).getConnectionInfo();
            string2 = string3;
            if (context == null) return string2;
        }
        catch (Exception exception) {
            return null;
        }
        return NetworkUtils._convertIntToIp(context.getIpAddress());
    }

    public static boolean isConnectInternet(Context context) {
        block4: {
            if ((context = (ConnectivityManager)context.getSystemService("connectivity")) != null) {
                context = context.getActiveNetworkInfo();
                if (context == null) break block4;
                try {
                    boolean bl2 = context.isAvailable();
                    return bl2;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isWifi(Context context) {
        boolean bl3;
        boolean bl2 = bl3 = false;
        if (context == null) return bl2;
        try {
            boolean bl4 = NetworkUtils.getNetworkState(context)[0].equals(WIFI);
            bl2 = bl3;
            if (!bl4) return bl2;
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }
}

