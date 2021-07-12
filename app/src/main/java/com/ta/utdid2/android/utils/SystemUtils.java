/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build$VERSION
 *  android.os.Environment
 *  android.util.Log
 */
package com.ta.utdid2.android.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SystemUtils {
    public static String getAppLabel(Context object) {
        block3: {
            PackageManager packageManager = object.getPackageManager();
            object = object.getPackageName();
            if (packageManager == null || object == null) break block3;
            try {
                object = ((Object)packageManager.getApplicationLabel(packageManager.getPackageInfo((String)object, (int)1).applicationInfo)).toString();
                return object;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                nameNotFoundException.printStackTrace();
            }
        }
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getCpuInfo() {
        block10: {
            var2 = null;
            var1_3 = null;
            var0_7 = null;
            var4_8 = null;
            var3_9 = null;
            var5_10 = null;
            var6_11 = new FileReader("/proc/cpuinfo");
            var0_7 = var5_10;
            if (var6_11 == null) break block10;
            {
                catch (FileNotFoundException var1_5) {
                    ** GOTO lbl43
                }
            }
            var5_10 = new BufferedReader(var6_11, 1024);
            var0_7 = var4_8;
            var1_3 = var3_9;
            var0_7 = var2 = var5_10.readLine();
            var1_3 = var2;
            var5_10.close();
            var0_7 = var2;
            var1_3 = var2;
            var6_11.close();
            var0_7 = var2;
            break block10;
            catch (IOException var1_4) {
                block11: {
                    var0_7 = var2;
                    var2 = var1_4;
                    break block11;
                    catch (FileNotFoundException var1_6) {
                        ** GOTO lbl43
                    }
                    catch (IOException var2_2) {
                        var0_7 = var1_3;
                    }
                }
                var1_3 = var0_7;
                try {
                    Log.e((String)"Could not read from file /proc/cpuinfo", (String)var2.toString());
                }
                catch (FileNotFoundException var2_1) {
                    var0_7 = var1_3;
                    var1_3 = var2_1;
lbl43:
                    // 3 sources

                    Log.e((String)"BaseParameter-Could not open file /proc/cpuinfo", (String)var1_3.toString());
                }
            }
        }
        if (var0_7 != null) {
            return var0_7.substring(var0_7.indexOf(58) + 1).trim();
        }
        return "";
    }

    public static File getRootFolder(String object) {
        File file = Environment.getExternalStorageDirectory();
        if (file != null) {
            object = new File(String.format("%s%s%s", file.getAbsolutePath(), File.separator, object));
            if (object != null && !((File)object).exists()) {
                ((File)object).mkdirs();
            }
            return object;
        }
        return null;
    }

    public static int getSystemVersion() {
        try {
            int n2 = Build.VERSION.class.getField("SDK_INT").getInt(null);
            return n2;
        }
        catch (Exception exception) {
            try {
                int n3 = Integer.parseInt((String)Build.VERSION.class.getField("SDK").get(null));
                return n3;
            }
            catch (Exception exception2) {
                exception2.printStackTrace();
                return 2;
            }
        }
    }
}

