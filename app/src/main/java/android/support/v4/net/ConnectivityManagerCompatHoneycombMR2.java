/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.ConnectivityManager
 */
package android.support.v4.net;

import android.net.ConnectivityManager;

class ConnectivityManagerCompatHoneycombMR2 {
    ConnectivityManagerCompatHoneycombMR2() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isActiveNetworkMetered(ConnectivityManager connectivityManager) {
        if ((connectivityManager = connectivityManager.getActiveNetworkInfo()) == null) {
            return true;
        }
        switch (connectivityManager.getType()) {
            case 0: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: {
                return true;
            }
            default: {
                return true;
            }
            case 1: 
            case 7: 
            case 9: 
        }
        return false;
    }
}

