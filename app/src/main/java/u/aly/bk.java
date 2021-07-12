/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 */
package u.aly;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import u.aly.bl;

public class bk {
    public static final String a = System.getProperty("line.separator");
    private static final String b = "helper";

    public static String a() {
        return bk.a(new Date());
    }

    public static String a(Context object, long l2) {
        if (l2 < 1000L) {
            return (int)l2 + "B";
        }
        if (l2 < 1000000L) {
            return Math.round((double)l2 / 1000.0) + "K";
        }
        if (l2 < 1000000000L) {
            object = new DecimalFormat("#0.0");
            return ((NumberFormat)object).format((double)l2 / 1000000.0) + "M";
        }
        object = new DecimalFormat("#0.00");
        return ((NumberFormat)object).format((double)l2 / 1.0E9) + "G";
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String a(File object) {
        MessageDigest messageDigest;
        byte[] byArray = new byte[1024];
        try {
            int n2;
            if (!((File)object).isFile()) {
                return "";
            }
            messageDigest = MessageDigest.getInstance("MD5");
            object = new FileInputStream((File)object);
            while ((n2 = ((FileInputStream)object).read(byArray, 0, 1024)) != -1) {
                messageDigest.update(byArray, 0, n2);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        {
            ((FileInputStream)object).close();
        }
        return String.format("%1$032x", new BigInteger(1, messageDigest.digest()));
    }

    public static String a(InputStream closeable) throws IOException {
        int n2;
        closeable = new InputStreamReader((InputStream)closeable);
        char[] cArray = new char[1024];
        StringWriter stringWriter = new StringWriter();
        while (-1 != (n2 = ((Reader)closeable).read(cArray))) {
            stringWriter.write(cArray, 0, n2);
        }
        return stringWriter.toString();
    }

    public static String a(String string2) {
        Object object;
        Object object2;
        if (string2 == null) {
            return null;
        }
        try {
            object2 = string2.getBytes();
            object = MessageDigest.getInstance("MD5");
            ((MessageDigest)object).reset();
            ((MessageDigest)object).update((byte[])object2);
            object2 = ((MessageDigest)object).digest();
            object = new StringBuffer();
            for (int i2 = 0; i2 < ((byte[])object2).length; ++i2) {
                ((StringBuffer)object).append(String.format("%02X", object2[i2]));
            }
        }
        catch (Exception exception) {
            return string2.replaceAll("[^[a-z][A-Z][0-9][.][_]]", "");
        }
        object2 = ((StringBuffer)object).toString();
        return object2;
    }

    public static String a(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date);
    }

    public static void a(Context context, String string2) {
        context.startActivity(context.getPackageManager().getLaunchIntentForPackage(string2));
    }

    public static void a(File file, String string2) throws IOException {
        bk.a(file, string2.getBytes());
    }

    public static void a(File object, byte[] byArray) throws IOException {
        object = new FileOutputStream((File)object);
        try {
            ((FileOutputStream)object).write(byArray);
            ((OutputStream)object).flush();
            return;
        }
        finally {
            bk.a((OutputStream)object);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void a(OutputStream outputStream) {
        if (outputStream == null) return;
        try {
            outputStream.close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static String b(String object) {
        Object object2 = MessageDigest.getInstance("MD5");
        ((MessageDigest)object2).update(((String)object).getBytes());
        object = ((MessageDigest)object2).digest();
        object2 = new StringBuffer();
        int n2 = 0;
        while (true) {
            if (n2 >= ((Object)object).length) break;
            ((StringBuffer)object2).append(Integer.toHexString(object[n2] & 0xFF));
            ++n2;
            continue;
            break;
        }
        try {
            object = ((StringBuffer)object2).toString();
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            bl.c(b, "getMD5 error", noSuchAlgorithmException);
            return "";
        }
    }

    public static boolean b(Context context, String string2) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)string2)));
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static byte[] b(InputStream inputStream) throws IOException {
        int n2;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byArray = new byte[1024];
        while (-1 != (n2 = inputStream.read(byArray))) {
            byteArrayOutputStream.write(byArray, 0, n2);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String c(String object) {
        long l2;
        block4: {
            try {
                l2 = Long.valueOf((String)object);
                if (l2 >= 1024L) break block4;
                return (int)l2 + "B";
            }
            catch (NumberFormatException numberFormatException) {
                return object;
            }
        }
        if (l2 < 0x100000L) {
            object = new DecimalFormat("#0.00");
            return ((NumberFormat)object).format((double)l2 / 1024.0) + "K";
        }
        if (l2 < 0x40000000L) {
            object = new DecimalFormat("#0.00");
            return ((NumberFormat)object).format((double)l2 / 1048576.0) + "M";
        }
        object = new DecimalFormat("#0.00");
        return ((NumberFormat)object).format((double)l2 / 1.073741824E9) + "G";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void c(InputStream inputStream) {
        if (inputStream == null) return;
        try {
            inputStream.close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static boolean d(String string2) {
        return string2 == null || string2.length() == 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean e(String string2) {
        return !bk.d(string2) && ((string2 = string2.trim().toLowerCase(Locale.US)).startsWith("http://") || string2.startsWith("https://"));
    }
}

