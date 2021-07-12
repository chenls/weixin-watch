/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.net.wifi.WifiManager
 *  android.provider.Settings$Secure
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  org.json.JSONObject
 */
package com.umeng.analytics.social;

import android.content.ContentResolver;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.social.UMPlatformData;
import com.umeng.analytics.social.a;
import com.umeng.analytics.social.b;
import com.umeng.analytics.social.e;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public abstract class f {
    private static Map<String, String> a;

    private static String a(List<String> object) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(URLEncoder.encode(object.toString()).getBytes());
            object = byteArrayOutputStream.toString();
            return object;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static List<String> a(UMPlatformData ... object) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder stringBuilder3 = new StringBuilder();
        int n2 = ((UMPlatformData[])object).length;
        for (int i2 = 0; i2 < n2; ++i2) {
            UMPlatformData uMPlatformData = object[i2];
            stringBuilder.append(uMPlatformData.getMeida().toString());
            stringBuilder.append(',');
            stringBuilder2.append(uMPlatformData.getUsid());
            stringBuilder2.append(',');
            stringBuilder3.append(uMPlatformData.getWeiboId());
            stringBuilder3.append(',');
        }
        if (((UMPlatformData[])object).length > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder2.deleteCharAt(stringBuilder2.length() - 1);
            stringBuilder3.deleteCharAt(stringBuilder3.length() - 1);
        }
        object = new ArrayList();
        object.add("platform=" + stringBuilder.toString());
        object.add("usid=" + stringBuilder2.toString());
        if (stringBuilder3.length() > 0) {
            object.add("weiboid=" + stringBuilder3.toString());
        }
        return object;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Map<String, String> a(Context object) {
        void var1_5;
        HashMap<String, String> hashMap;
        block7: {
            block6: {
                hashMap = new HashMap<String, String>();
                TelephonyManager telephonyManager = (TelephonyManager)object.getSystemService("phone");
                if (telephonyManager == null) {
                    b.e("MobclickAgent", "No IMEI.");
                }
                try {
                    if (!f.a(object, "android.permission.READ_PHONE_STATE")) break block6;
                    String string2 = telephonyManager.getDeviceId();
                    break block7;
                }
                catch (Exception exception) {
                    b.e("MobclickAgent", "No IMEI.", exception);
                }
            }
            Object var1_8 = null;
        }
        String string3 = f.c(object);
        String string4 = Settings.Secure.getString((ContentResolver)object.getContentResolver(), (String)"android_id");
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            hashMap.put("mac", string3);
        }
        if (!TextUtils.isEmpty((CharSequence)var1_5)) {
            hashMap.put("imei", (String)var1_5);
        }
        if (!TextUtils.isEmpty((CharSequence)string4)) {
            hashMap.put("android_id", string4);
        }
        return hashMap;
    }

    private static boolean a(Context context, String string2) {
        return context.getPackageManager().checkPermission(string2, context.getPackageName()) == 0;
    }

    protected static String[] a(Context object, String object2, UMPlatformData ... object3) throws a {
        if (object3 == null || ((Object)object3).length == 0) {
            throw new a("platform data is null");
        }
        String string2 = AnalyticsConfig.getAppkey((Context)object);
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new a("can`t get appkey.");
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        string2 = "http://log.umsns.com/share/api/" + string2 + "/";
        if (a == null || a.isEmpty()) {
            a = f.b((Context)object);
        }
        if (a != null && !a.isEmpty()) {
            object = a.entrySet().iterator();
            while (object.hasNext()) {
                Map.Entry entry = (Map.Entry)object.next();
                arrayList.add((String)entry.getKey() + "=" + (String)entry.getValue());
            }
        }
        arrayList.add("date=" + String.valueOf(System.currentTimeMillis()));
        arrayList.add("channel=" + e.e);
        if (!TextUtils.isEmpty((CharSequence)object2)) {
            arrayList.add("topic=" + (String)object2);
        }
        arrayList.addAll(f.a((UMPlatformData[])object3));
        object3 = object = f.b((UMPlatformData[])object3);
        if (object == null) {
            object3 = "null";
        }
        object2 = string2 + "?" + f.a(arrayList);
        while (true) {
            object = object2;
            if (!((String)object2).contains("%2C+")) break;
            object2 = ((String)object2).replace("%2C+", "&");
        }
        while (true) {
            object2 = object;
            if (!((String)object).contains("%3D")) break;
            object = ((String)object).replace("%3D", "=");
        }
        while (true) {
            object = object2;
            if (!((String)object2).contains("%5B")) break;
            object2 = ((String)object2).replace("%5B", "");
        }
        while (((String)object).contains("%5D")) {
            object = ((String)object).replace("%5D", "");
        }
        b.c("MobclickAgent", "URL:" + (String)object);
        b.c("MobclickAgent", "BODY:" + (String)object3);
        return new String[]{object, object3};
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String b(UMPlatformData ... var0) {
        var4_2 = new JSONObject();
        var2_3 = var0.length;
        var1_4 = 0;
        while (true) {
            block4: {
                if (var1_4 >= var2_3) break block4;
                var5_6 = var0[var1_4];
                var3_5 /* !! */  = var5_6.getGender();
                var7_8 = var5_6.getName();
                if (var3_5 /* !! */  != null) ** GOTO lbl12
                try {
                    if (TextUtils.isEmpty((CharSequence)var7_8)) ** GOTO lbl28
lbl12:
                    // 2 sources

                    var6_7 = new JSONObject();
                    var3_5 /* !! */  = var3_5 /* !! */  == null ? "" : String.valueOf(var3_5 /* !! */ .value);
                    var6_7.put("gender", (Object)var3_5 /* !! */ );
                    var3_5 /* !! */  = var7_8 == null ? "" : String.valueOf(var7_8);
                    var6_7.put("name", (Object)var3_5 /* !! */ );
                    var4_2.put(var5_6.getMeida().toString(), (Object)var6_7);
                }
                catch (Exception var0_1) {
                    throw new a("build body exception", var0_1);
                }
            }
            if (var4_2.length() == 0) {
                return null;
            }
            return var4_2.toString();
lbl28:
            // 2 sources

            ++var1_4;
        }
    }

    private static Map<String, String> b(Context object) throws a {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        Map<String, String> map = f.a((Context)object);
        if (map != null && !map.isEmpty()) {
            object = new StringBuilder();
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry entry : map.entrySet()) {
                if (TextUtils.isEmpty((CharSequence)((CharSequence)entry.getValue()))) continue;
                stringBuilder.append((String)entry.getKey()).append(",");
                ((StringBuilder)object).append((String)entry.getValue()).append(",");
            }
            if (((StringBuilder)object).length() > 0) {
                ((StringBuilder)object).deleteCharAt(((StringBuilder)object).length() - 1);
                hashMap.put("deviceid", ((StringBuilder)object).toString());
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                hashMap.put("idtype", stringBuilder.toString());
            }
            return hashMap;
        }
        throw new a("can`t get device id.");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String c(Context context) {
        try {
            WifiManager wifiManager = (WifiManager)context.getSystemService("wifi");
            if (f.a(context, "android.permission.ACCESS_WIFI_STATE")) {
                return wifiManager.getConnectionInfo().getMacAddress();
            }
            b.e("MobclickAgent", "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
            return "";
        }
        catch (Exception exception) {
            b.e("MobclickAgent", "Could not get mac address." + exception.toString());
            return "";
        }
    }
}

