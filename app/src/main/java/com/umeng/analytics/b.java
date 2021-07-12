/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.umeng.analytics;

import android.content.Context;
import com.umeng.analytics.a;
import com.umeng.analytics.h;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import u.aly.bl;
import u.aly.m;

public class b {
    private static final byte[] a = new byte[]{10, 1, 11, 5, 4, 15, 7, 9, 23, 3, 1, 6, 8, 12, 13, 91};

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int a(int n2, String string2) {
        int n3 = 0;
        Random random = new Random();
        if ((double)random.nextFloat() < 0.001) {
            if (string2 == null) {
                bl.c("--->", "null signature..");
            }
            try {
                n2 = Integer.parseInt(string2.substring(9, 11), 16);
                return (n2 | 0x80) * 1000;
            }
            catch (Exception exception) {
                n2 = n3;
                return (n2 | 0x80) * 1000;
            }
        }
        n2 = n3 = new Random().nextInt(n2);
        if (n3 > 255000) return n2;
        n2 = n3;
        if (n3 < 128000) return n2;
        return 127000;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String a(Throwable object) {
        Object object2 = null;
        if (object == null) {
            return null;
        }
        Object object3 = object2;
        try {
            StringWriter stringWriter = new StringWriter();
            object3 = object2;
            PrintWriter printWriter = new PrintWriter(stringWriter);
            object3 = object2;
            ((Throwable)object).printStackTrace(printWriter);
            object3 = object2;
            object = ((Throwable)object).getCause();
            while (true) {
                if (object == null) {
                    object3 = object2;
                    object3 = object = ((Object)stringWriter).toString();
                    printWriter.close();
                    object3 = object;
                    ((Writer)stringWriter).close();
                    return object;
                }
                object3 = object2;
                ((Throwable)object).printStackTrace(printWriter);
                object3 = object2;
                object = ((Throwable)object).getCause();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return object3;
        }
    }

    public static String a(byte[] byArray) {
        if (byArray == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < byArray.length; ++i2) {
            stringBuffer.append(String.format("%02X", byArray[i2]));
        }
        return stringBuffer.toString().toLowerCase(Locale.US);
    }

    public static boolean a(Context context, byte[] byArray) {
        long l2 = byArray.length;
        if (l2 > com.umeng.analytics.a.v) {
            h.a(context).h();
            m.a(context).a(l2, System.currentTimeMillis(), "__data_size_of");
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static byte[] a(String string2) {
        int n2;
        byte[] byArray = null;
        if (string2 != null && (n2 = string2.length()) % 2 == 0) {
            byte[] byArray2 = new byte[n2 / 2];
            int n3 = 0;
            while (true) {
                byArray = byArray2;
                if (n3 >= n2) break;
                byArray2[n3 / 2] = (byte)Integer.valueOf(string2.substring(n3, n3 + 2), 16).intValue();
                n3 += 2;
            }
        }
        return byArray;
    }

    public static byte[] a(byte[] byArray, byte[] byArray2) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(1, (Key)new SecretKeySpec(byArray2, "AES"), new IvParameterSpec(a));
        return cipher.doFinal(byArray);
    }

    public static String b(String string2) {
        return "http://" + string2 + ".umeng.com/app_logs";
    }

    public static byte[] b(byte[] byArray) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(byArray);
            byArray = messageDigest.digest();
            return byArray;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static byte[] b(byte[] byArray, byte[] byArray2) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        cipher.init(2, (Key)new SecretKeySpec(byArray2, "AES"), new IvParameterSpec(a));
        return cipher.doFinal(byArray);
    }
}

