/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  android.os.Build$VERSION
 */
package android.support.v4.net;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.net.ConnectivityManagerCompatHoneycombMR2;
import android.support.v4.net.ConnectivityManagerCompatJellyBean;

public final class ConnectivityManagerCompat {
    private static final ConnectivityManagerCompatImpl IMPL = Build.VERSION.SDK_INT >= 16 ? new JellyBeanConnectivityManagerCompatImpl() : (Build.VERSION.SDK_INT >= 13 ? new HoneycombMR2ConnectivityManagerCompatImpl() : new BaseConnectivityManagerCompatImpl());

    private ConnectivityManagerCompat() {
    }

    public static NetworkInfo getNetworkInfoFromBroadcast(ConnectivityManager connectivityManager, Intent intent) {
        if ((intent = (NetworkInfo)intent.getParcelableExtra("networkInfo")) != null) {
            return connectivityManager.getNetworkInfo(intent.getType());
        }
        return null;
    }

    public static boolean isActiveNetworkMetered(ConnectivityManager connectivityManager) {
        return IMPL.isActiveNetworkMetered(connectivityManager);
    }

    static class BaseConnectivityManagerCompatImpl
    implements ConnectivityManagerCompatImpl {
        BaseConnectivityManagerCompatImpl() {
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean isActiveNetworkMetered(ConnectivityManager connectivityManager) {
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
            }
            return false;
        }
    }

    static interface ConnectivityManagerCompatImpl {
        public boolean isActiveNetworkMetered(ConnectivityManager var1);
    }

    static class HoneycombMR2ConnectivityManagerCompatImpl
    implements ConnectivityManagerCompatImpl {
        HoneycombMR2ConnectivityManagerCompatImpl() {
        }

        @Override
        public boolean isActiveNetworkMetered(ConnectivityManager connectivityManager) {
            return ConnectivityManagerCompatHoneycombMR2.isActiveNetworkMetered(connectivityManager);
        }
    }

    static class JellyBeanConnectivityManagerCompatImpl
    implements ConnectivityManagerCompatImpl {
        JellyBeanConnectivityManagerCompatImpl() {
        }

        @Override
        public boolean isActiveNetworkMetered(ConnectivityManager connectivityManager) {
            return ConnectivityManagerCompatJellyBean.isActiveNetworkMetered(connectivityManager);
        }
    }
}

