/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.telephony.TelephonyManager
 */
package com.ta.utdid2.android.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.ta.utdid2.android.utils.Base64;
import com.ta.utdid2.android.utils.IntUtils;
import com.ta.utdid2.android.utils.StringUtils;
import java.util.Random;

public class PhoneInfoUtils {
    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getImei(Context object) {
        void var0_5;
        String string2;
        String string3 = string2 = null;
        if (object != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager)object.getSystemService("phone");
                string3 = string2;
                if (telephonyManager != null) {
                    string3 = telephonyManager.getDeviceId();
                }
            }
            catch (Exception exception) {
                string3 = string2;
            }
        }
        String string4 = string3;
        if (StringUtils.isEmpty(string3)) {
            String string5 = PhoneInfoUtils.getUniqueID();
        }
        return var0_5;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getImsi(Context object) {
        void var0_5;
        String string2;
        String string3 = string2 = null;
        if (object != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager)object.getSystemService("phone");
                string3 = string2;
                if (telephonyManager != null) {
                    string3 = telephonyManager.getSubscriberId();
                }
            }
            catch (Exception exception) {
                string3 = string2;
            }
        }
        String string4 = string3;
        if (StringUtils.isEmpty(string3)) {
            String string5 = PhoneInfoUtils.getUniqueID();
        }
        return var0_5;
    }

    public static final String getUniqueID() {
        int n2 = (int)(System.currentTimeMillis() / 1000L);
        int n3 = (int)System.nanoTime();
        int n4 = new Random().nextInt();
        int n5 = new Random().nextInt();
        byte[] byArray = IntUtils.getBytes(n2);
        byte[] byArray2 = IntUtils.getBytes(n3);
        byte[] byArray3 = IntUtils.getBytes(n4);
        byte[] byArray4 = IntUtils.getBytes(n5);
        byte[] byArray5 = new byte[16];
        System.arraycopy(byArray, 0, byArray5, 0, 4);
        System.arraycopy(byArray2, 0, byArray5, 4, 4);
        System.arraycopy(byArray3, 0, byArray5, 8, 4);
        System.arraycopy(byArray4, 0, byArray5, 12, 4);
        return Base64.encodeToString(byArray5, 2);
    }
}

