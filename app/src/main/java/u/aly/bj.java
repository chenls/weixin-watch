/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Configuration
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo$State
 *  android.net.wifi.WifiManager
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Environment
 *  android.provider.Settings$Secure
 *  android.provider.Settings$System
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  android.util.DisplayMetrics
 *  android.view.WindowManager
 *  javax.microedition.khronos.opengles.GL10
 */
package u.aly;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.a;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import javax.microedition.khronos.opengles.GL10;
import u.aly.bk;
import u.aly.bl;
import u.aly.x;

public class bj {
    protected static final String a = bj.class.getName();
    public static final String b = "";
    public static final String c = "2G/3G";
    public static final String d = "Wi-Fi";
    public static final int e = 8;
    private static final String f = "ro.miui.ui.version.name";

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String A(Context object) {
        Object object2 = object.getPackageManager();
        try {
            object = object2.getPackageInfo(bj.z((Context)object), 64);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            nameNotFoundException.printStackTrace();
            object = null;
        }
        object2 = new ByteArrayInputStream(((PackageInfo)object).signatures[0].toByteArray());
        try {
            object = CertificateFactory.getInstance("X509");
        }
        catch (CertificateException certificateException) {
            certificateException.printStackTrace();
            object = null;
        }
        try {
            object = (X509Certificate)((CertificateFactory)object).generateCertificate((InputStream)object2);
        }
        catch (CertificateException certificateException) {
            certificateException.printStackTrace();
            object = null;
        }
        try {
            return bj.a(MessageDigest.getInstance("MD5").digest(((Certificate)object).getEncoded()));
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
            return null;
        }
        catch (CertificateEncodingException certificateEncodingException) {
            certificateEncodingException.printStackTrace();
            return null;
        }
    }

    public static String B(Context context) {
        return context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
    }

    public static boolean C(Context context) {
        boolean bl2 = false;
        try {
            int n2 = context.getApplicationInfo().flags;
            if ((n2 & 2) != 0) {
                bl2 = true;
            }
            return bl2;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static String D(Context object) {
        try {
            object = object.getPackageManager().getPackageInfo((String)object.getPackageName(), (int)0).applicationInfo.loadLabel(object.getPackageManager()).toString();
            return object;
        }
        catch (Exception exception) {
            bl.a(a, exception);
            return null;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String E(Context object) {
        Properties properties;
        block3: {
            properties = bj.e();
            object = properties.getProperty(f);
            if (!TextUtils.isEmpty((CharSequence)object)) return "MIUI";
            if (!bj.f()) break block3;
            return "Flyme";
        }
        try {
            if (TextUtils.isEmpty((CharSequence)bj.a(properties))) return object;
            return "YunOS";
        }
        catch (Exception exception) {
            object = null;
            exception.printStackTrace();
        }
        return object;
    }

    public static String F(Context object) {
        String string2;
        Properties properties;
        block8: {
            block7: {
                properties = bj.e();
                try {
                    string2 = properties.getProperty(f);
                    object = string2;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    return null;
                }
                if (!TextUtils.isEmpty((CharSequence)string2)) break block7;
                boolean bl2 = bj.f();
                if (!bl2) break block8;
                try {
                    object = bj.b(properties);
                }
                catch (Exception exception) {
                    return string2;
                }
            }
            return object;
        }
        try {
            object = bj.a(properties);
            return object;
        }
        catch (Exception exception) {
            return string2;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Locale G(Context object) {
        void var1_9;
        void var0_3;
        Object var1_6 = null;
        try {
            Configuration configuration = new Configuration();
            configuration.setToDefaults();
            Settings.System.getConfiguration((ContentResolver)object.getContentResolver(), (Configuration)configuration);
            Object var0_1 = var1_6;
            if (configuration != null) {
                Locale locale = configuration.locale;
            }
        }
        catch (Exception exception) {
            bl.c(a, "fail to read user config locale");
            Object var0_5 = var1_6;
        }
        void var1_7 = var0_3;
        if (var0_3 == null) {
            Locale locale = Locale.getDefault();
        }
        return var1_9;
    }

    private static String H(Context context) {
        try {
            WifiManager wifiManager = (WifiManager)context.getSystemService("wifi");
            if (bj.a(context, "android.permission.ACCESS_WIFI_STATE")) {
                return wifiManager.getConnectionInfo().getMacAddress();
            }
            bl.e(a, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
            return b;
        }
        catch (Exception exception) {
            bl.e(a, "Could not get mac address." + exception.toString());
            return b;
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private static String I(Context var0) {
        block16: {
            block18: {
                block17: {
                    var2_1 = "";
                    var3_2 = (TelephonyManager)var0 /* !! */ .getSystemService("phone");
                    if (Build.VERSION.SDK_INT < 23) break block17;
                    if (var3_2 != null) {
                        if (!bj.a(var0 /* !! */ , "android.permission.READ_PHONE_STATE")) break block16;
                        var2_1 = var3_2.getDeviceId();
                        bl.a(bj.a, new Object[]{"getDeviceId, IMEI: " + var2_1});
lbl10:
                        // 3 sources

                        while (true) {
                            var1_3 = var2_1;
                            if (TextUtils.isEmpty((CharSequence)var2_1)) {
                                var2_1 = bj.d();
                                bl.a(bj.a, new Object[]{"getDeviceId, mc: " + var2_1});
                                var1_3 = var2_1;
                                if (TextUtils.isEmpty((CharSequence)var2_1)) {
                                    var0 /* !! */  = Settings.Secure.getString((ContentResolver)var0 /* !! */ .getContentResolver(), (String)"android_id");
                                    bl.a(bj.a, new Object[]{"getDeviceId, android_id: " + (String)var0 /* !! */ });
                                    var1_3 = var0 /* !! */ ;
                                    if (TextUtils.isEmpty((CharSequence)var0 /* !! */ )) {
                                        var1_3 = var0 /* !! */ ;
                                        if (Build.VERSION.SDK_INT >= 9) {
                                            var1_3 = Build.SERIAL;
                                        }
                                        bl.a(bj.a, new Object[]{"getDeviceId, serial no: " + var1_3});
                                    }
                                }
                            }
                            return var1_3;
                        }
                        catch (Exception var1_4) {
                            var2_1 = "";
lbl29:
                            // 2 sources

                            while (true) {
                                bl.d(bj.a, "No IMEI.", (Throwable)var1_3);
                                break;
                            }
                        }
                    }
                    break block16;
                }
                var1_3 = var2_1;
                if (var3_2 != null) {
                    var1_3 = var2_1;
                    if (!bj.a(var0 /* !! */ , "android.permission.READ_PHONE_STATE")) ** GOTO lbl40
                    var1_3 = var3_2.getDeviceId();
                }
lbl40:
                // 4 sources

                while (TextUtils.isEmpty((CharSequence)var1_3)) {
                    bl.e(bj.a, new Object[]{"No IMEI."});
                    var1_3 = var2_1 = bj.u(var0 /* !! */ );
                    if (!TextUtils.isEmpty((CharSequence)var2_1)) ** continue;
                    bl.e(bj.a, new Object[]{"Failed to take mac as IMEI. Try to use Secure.ANDROID_ID instead."});
                    var0 /* !! */  = Settings.Secure.getString((ContentResolver)var0 /* !! */ .getContentResolver(), (String)"android_id");
                    bl.a(bj.a, new Object[]{"getDeviceId: Secure.ANDROID_ID: " + (String)var0 /* !! */ });
                    return var0 /* !! */ ;
                }
                break block18;
                catch (Exception var1_5) {
                    bl.d(bj.a, "No IMEI.", var1_5);
                    var1_3 = var2_1;
                }
                ** GOTO lbl40
                catch (Exception var1_6) {
                    ** continue;
                }
            }
            return var1_3;
        }
        var2_1 = "";
        ** while (true)
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private static String J(Context var0) {
        block13: {
            block14: {
                block15: {
                    block16: {
                        var3_2 = (TelephonyManager)var0 /* !! */ .getSystemService("phone");
                        if (Build.VERSION.SDK_INT < 23) break block16;
                        var2_3 = Settings.Secure.getString((ContentResolver)var0 /* !! */ .getContentResolver(), (String)"android_id");
                        bl.a(bj.a, new Object[]{"getDeviceId, android_id: " + var2_3});
                        var1_5 = var2_3;
                        if (!TextUtils.isEmpty((CharSequence)var2_3)) ** GOTO lbl23
                        var2_3 = bj.d();
                        bl.a(bj.a, new Object[]{"getDeviceId, mc: " + var2_3});
                        var1_5 = var2_3;
                        if (!TextUtils.isEmpty((CharSequence)var2_3)) ** GOTO lbl23
                        if (var3_2 == null) break block13;
                        if (!bj.a(var0 /* !! */ , "android.permission.READ_PHONE_STATE")) break block13;
                        var0 /* !! */  = var3_2.getDeviceId();
                        bl.a(bj.a, new Object[]{"getDeviceId, IMEI: " + (String)var0 /* !! */ });
lbl17:
                        // 3 sources

                        while (true) {
                            if (!TextUtils.isEmpty((CharSequence)var0 /* !! */ )) break block14;
                            if (Build.VERSION.SDK_INT >= 9) {
                                var0 /* !! */  = Build.SERIAL;
                            }
                            bl.a(bj.a, new Object[]{"getDeviceId, serial no: " + (String)var0 /* !! */ });
                            var1_5 = var0 /* !! */ ;
lbl23:
                            // 6 sources

                            return var1_5;
                        }
                        catch (Exception var1_6) {
                            var0 /* !! */  = var2_3;
lbl27:
                            // 2 sources

                            while (true) {
                                bl.d(bj.a, "No IMEI.", (Throwable)var1_5);
                                break;
                            }
                        }
                    }
                    var2_4 = Settings.Secure.getString((ContentResolver)var0 /* !! */ .getContentResolver(), (String)"android_id");
                    bl.a(bj.a, new Object[]{"getDeviceId: Secure.ANDROID_ID: " + var2_4});
                    var1_5 = var2_4;
                    if (!TextUtils.isEmpty((CharSequence)var2_4)) ** GOTO lbl23
                    bl.e(bj.a, new Object[]{"No IMEI."});
                    var1_5 = var2_4 = bj.u(var0 /* !! */ );
                    if (!TextUtils.isEmpty((CharSequence)var2_4)) ** GOTO lbl23
                    var1_5 = var2_4;
                    if (var3_2 != null) ** break;
                    ** while (true)
                    try {
                        if (!bj.a(var0 /* !! */ , "android.permission.READ_PHONE_STATE")) break block15;
                        var0 /* !! */  = var3_2.getDeviceId();
                    }
                    catch (Exception var0_1) {
                        bl.d(bj.a, "No IMEI.", var0_1);
                        return var2_4;
                    }
lbl45:
                    // 2 sources

                    return var0 /* !! */ ;
                    catch (Exception var1_7) {
                        ** continue;
                    }
                }
                var0 /* !! */  = var2_4;
                ** while (true)
            }
            return var0 /* !! */ ;
        }
        var0 /* !! */  = var2_3;
        ** while (true)
    }

    private static int a(Object object, String object2) {
        try {
            object2 = DisplayMetrics.class.getDeclaredField((String)object2);
            ((AccessibleObject)object2).setAccessible(true);
            int n2 = ((Field)object2).getInt(object);
            return n2;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return -1;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int a(Date date, Date date2) {
        Date date3;
        if (date.after(date2)) {
            date3 = date2;
            date2 = date;
        } else {
            date3 = date;
        }
        long l2 = date3.getTime();
        return (int)((date2.getTime() - l2) / 1000L);
    }

    /*
     * Exception decompiling
     */
    public static String a() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[CATCHBLOCK]], but top level block is 10[UNCONDITIONALDOLOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:845)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:73)
         *     at org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static String a(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(date);
    }

    private static String a(Properties object) {
        if (!TextUtils.isEmpty((CharSequence)(object = ((Properties)object).getProperty("ro.yunos.version")))) {
            return object;
        }
        return null;
    }

    private static String a(byte[] byArray) {
        StringBuilder stringBuilder = new StringBuilder(byArray.length * 2);
        for (int i2 = 0; i2 < byArray.length; ++i2) {
            String string2 = Integer.toHexString(byArray[i2]);
            int n2 = string2.length();
            String string3 = string2;
            if (n2 == 1) {
                string3 = "0" + string2;
            }
            string2 = string3;
            if (n2 > 2) {
                string2 = string3.substring(n2 - 2, n2);
            }
            stringBuilder.append(string2.toUpperCase());
            if (i2 >= byArray.length - 1) continue;
            stringBuilder.append(':');
        }
        return stringBuilder.toString();
    }

    public static Date a(String object) {
        try {
            object = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse((String)object);
            return object;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static boolean a(Context context) {
        return context.getResources().getConfiguration().locale.toString().equals(Locale.CHINA.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean a(Context context, String string2) {
        boolean bl2 = false;
        if (Build.VERSION.SDK_INT < 23) {
            if (context.getPackageManager().checkPermission(string2, context.getPackageName()) != 0) return bl2;
            return true;
        }
        try {
            int n2 = (Integer)Class.forName("android.content.Context").getMethod("checkSelfPermission", String.class).invoke(context, string2);
            if (n2 != 0) return false;
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static boolean a(String string2, Context context) {
        context = context.getPackageManager();
        try {
            context.getPackageInfo(string2, 1);
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    public static String[] a(GL10 object) {
        String string2;
        try {
            string2 = object.glGetString(7936);
            object = object.glGetString(7937);
        }
        catch (Exception exception) {
            bl.e(a, "Could not read gpu infor:", exception);
            return new String[0];
        }
        return new String[]{string2, object};
    }

    /*
     * Unable to fully structure code
     */
    private static String b(String var0) throws FileNotFoundException {
        block29: {
            var4_6 = null;
            var5_7 = new FileReader(var0);
            var1_8 = var4_6;
            if (var5_7 == null) ** GOTO lbl20
            var2_12 = new BufferedReader(var5_7, 1024);
            var1_8 = var2_12;
            var0 = var3_14 = var2_12.readLine();
            if (var5_7 == null) break block29;
            try {
                var5_7.close();
            }
            catch (IOException var1_9) {
                var1_9.printStackTrace();
                ** continue;
            }
        }
lbl14:
        // 2 sources

        while (true) {
            var1_8 = var0;
            if (var2_12 != null) {
                var2_12.close();
                var1_8 = var0;
            }
lbl20:
            // 5 sources

            return var1_8;
        }
        catch (IOException var1_10) {
            var1_10.printStackTrace();
            return var0;
        }
        catch (IOException var3_15) {
            var2_12 = null;
lbl30:
            // 2 sources

            while (true) {
                block30: {
                    var1_8 = var2_12;
                    bl.e(bj.a, "Could not read from file " + var0, (Throwable)var3_16);
                    if (var5_7 == null) break block30;
                    try {
                        var5_7.close();
                    }
                    catch (IOException var0_2) {
                        var0_2.printStackTrace();
                        ** continue;
                    }
                }
lbl41:
                // 2 sources

                while (true) {
                    var1_8 = var4_6;
                    if (var2_12 == null) ** continue;
                    try {
                        var2_12.close();
                        return null;
                    }
                    catch (IOException var0_1) {
                        var0_1.printStackTrace();
                        return null;
                    }
                    break;
                }
                break;
            }
        }
        catch (Throwable var0_3) {
            var1_8 = null;
lbl52:
            // 2 sources

            while (true) {
                if (var5_7 != null) {
                    var5_7.close();
                }
lbl56:
                // 4 sources

                while (true) {
                    if (var1_8 != null) {
                        var1_8.close();
                    }
lbl60:
                    // 4 sources

                    throw var0_4;
                }
                catch (IOException var2_13) {
                    var2_13.printStackTrace();
                    ** continue;
                }
                catch (IOException var1_11) {
                    var1_11.printStackTrace();
                    ** continue;
                }
                break;
            }
        }
        {
            catch (Throwable var0_5) {
                ** continue;
            }
        }
        catch (IOException var3_17) {
            ** continue;
        }
    }

    private static String b(Properties object) {
        try {
            object = ((Properties)object).getProperty("ro.build.display.id").toLowerCase(Locale.getDefault());
            if (((String)object).contains("flyme os")) {
                object = ((String)object).split(" ")[2];
                return object;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    public static boolean b() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean b(Context context) {
        return context.getResources().getConfiguration().orientation == 1;
    }

    public static String c() {
        Date date = new Date();
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date);
    }

    public static String c(Context context) {
        int n2;
        try {
            n2 = context.getPackageManager().getPackageInfo((String)context.getPackageName(), (int)0).versionCode;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return b;
        }
        return String.valueOf(n2);
    }

    private static String d() {
        if (com.umeng.analytics.a.e) {
            String[] stringArray = new String[]{"/sys/class/net/wlan0/address", "/sys/class/net/eth0/address", "/sys/devices/virtual/net/wlan0/address"};
            for (int i2 = 0; i2 < stringArray.length; ++i2) {
                try {
                    String string2 = bj.b(stringArray[i2]);
                    if (string2 == null) continue;
                    return string2;
                }
                catch (Exception exception) {
                    bl.e(a, "open file  Failed", exception);
                }
            }
        }
        return null;
    }

    public static String d(Context object) {
        try {
            object = object.getPackageManager().getPackageInfo((String)object.getPackageName(), (int)0).versionName;
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return b;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String e(Context object) {
        void var0_4;
        void var0_2;
        PackageManager packageManager = object.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(object.getPackageName(), 0);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Object var0_6 = null;
        }
        if (var0_2 != null) {
            CharSequence charSequence = packageManager.getApplicationLabel((ApplicationInfo)var0_2);
            return (String)var0_4;
        }
        return (String)var0_4;
    }

    /*
     * Unable to fully structure code
     */
    private static Properties e() {
        block18: {
            var3 = new Properties();
            var0_1 = null;
            var0_1 = var1_4 = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
            var3.load((InputStream)var1_4);
            if (var1_4 == null) break block18;
            try {
                var1_4.close();
            }
            catch (IOException var0_2) {
                var0_2.printStackTrace();
                return var3;
            }
        }
        return var3;
        catch (IOException var2_6) {
            var1_4 = null;
lbl18:
            // 2 sources

            while (true) {
                var0_1 = var1_4;
                var2_7.printStackTrace();
                if (var1_4 == null) ** continue;
                try {
                    var1_4.close();
                    return var3;
                }
                catch (IOException var0_3) {
                    var0_3.printStackTrace();
                    return var3;
                }
                break;
            }
        }
        catch (Throwable var2_8) {
            var1_4 = var0_1;
            var0_1 = var2_8;
lbl32:
            // 2 sources

            while (true) {
                if (var1_4 != null) {
                    var1_4.close();
                }
lbl36:
                // 4 sources

                throw var0_1;
                catch (IOException var1_5) {
                    var1_5.printStackTrace();
                    ** continue;
                }
                break;
            }
        }
        {
            catch (Throwable var2_9) {
                var1_4 = var0_1;
                var0_1 = var2_9;
                ** continue;
            }
        }
        catch (IOException var2_10) {
            ** continue;
        }
    }

    public static String f(Context context) {
        if (MobclickAgent.EScenarioType.E_UM_ANALYTICS_OEM.toValue() == AnalyticsConfig.getVerticalType(context) || MobclickAgent.EScenarioType.E_UM_GAME_OEM.toValue() == AnalyticsConfig.getVerticalType(context)) {
            return bj.J(context);
        }
        return bj.I(context);
    }

    private static boolean f() {
        try {
            Build.class.getMethod("hasSmartBar", new Class[0]);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public static String g(Context context) {
        return bk.b(bj.f(context));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static String h(Context object) {
        void var0_3;
        int n2;
        int n3;
        block5: {
            block4: {
                if (bj.i(object) == null) break block4;
                n3 = object.getResources().getConfiguration().mcc;
                n2 = object.getResources().getConfiguration().mnc;
                if (n3 != 0) break block5;
            }
            return null;
        }
        String string2 = String.valueOf(n2);
        if (n2 < 10) {
            String string3 = String.format("%02d", n2);
        }
        return new StringBuffer().append(String.valueOf(n3)).append((String)var0_3).toString();
    }

    public static String i(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        if (bj.a(context, "android.permission.READ_PHONE_STATE")) {
            return telephonyManager.getSubscriberId();
        }
        return null;
    }

    public static String j(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        if (bj.a(context, "android.permission.READ_PHONE_STATE")) {
            return telephonyManager.getNetworkOperator();
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String k(Context object) {
        TelephonyManager telephonyManager;
        try {
            telephonyManager = (TelephonyManager)object.getSystemService("phone");
            if (!bj.a(object, "android.permission.READ_PHONE_STATE")) {
                return b;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return b;
        }
        if (telephonyManager == null) return b;
        return telephonyManager.getNetworkOperatorName();
    }

    public static String l(Context object) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager)object.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            int n2 = displayMetrics.widthPixels;
            int n3 = displayMetrics.heightPixels;
            object = String.valueOf(n3) + "*" + String.valueOf(n2);
            return object;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return b;
        }
    }

    public static String[] m(Context context) {
        String[] stringArray;
        block9: {
            block8: {
                block7: {
                    block6: {
                        stringArray = new String[]{b, b};
                        if (bj.a(context, "android.permission.ACCESS_NETWORK_STATE")) break block6;
                        stringArray[0] = b;
                        return stringArray;
                    }
                    context = (ConnectivityManager)context.getSystemService("connectivity");
                    if (context != null) break block7;
                    stringArray[0] = b;
                    return stringArray;
                }
                if (context.getNetworkInfo(1).getState() != NetworkInfo.State.CONNECTED) break block8;
                stringArray[0] = d;
                return stringArray;
            }
            context = context.getNetworkInfo(0);
            if (context.getState() != NetworkInfo.State.CONNECTED) break block9;
            stringArray[0] = c;
            try {
                stringArray[1] = context.getSubtypeName();
                return stringArray;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return stringArray;
    }

    public static boolean n(Context context) {
        return d.equals(bj.m(context)[0]);
    }

    public static boolean o(Context context) {
        block3: {
            try {
                context = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (context == null) break block3;
            }
            catch (Exception exception) {
                return true;
            }
            boolean bl2 = context.isConnectedOrConnecting();
            return bl2;
        }
        return false;
    }

    public static int p(Context object) {
        block3: {
            object = Calendar.getInstance(bj.G((Context)object));
            if (object == null) break block3;
            try {
                int n2 = ((Calendar)object).getTimeZone().getRawOffset() / 3600000;
                return n2;
            }
            catch (Exception exception) {
                bl.c(a, "error in getTimeZone", exception);
            }
        }
        return 8;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean q(Context object) {
        String string2 = x.a((Context)object).b().c(b);
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            if (string2.equals("cn")) return true;
            return false;
        }
        if (bj.i((Context)object) == null) {
            if (!TextUtils.isEmpty((CharSequence)(object = bj.r((Context)object)[0])) && ((String)object).equalsIgnoreCase("cn")) return true;
            return false;
        }
        int n2 = object.getResources().getConfiguration().mcc;
        if (n2 == 460 || n2 == 461) {
            return true;
        }
        if (n2 == 0 && !TextUtils.isEmpty((CharSequence)(object = bj.r((Context)object)[0])) && ((String)object).equalsIgnoreCase("cn")) return true;
        return false;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String[] r(Context object) {
        String[] stringArray = new String[2];
        try {
            object = bj.G((Context)object);
            if (object != null) {
                stringArray[0] = ((Locale)object).getCountry();
                stringArray[1] = ((Locale)object).getLanguage();
            }
            if (TextUtils.isEmpty((CharSequence)stringArray[0])) {
                stringArray[0] = "Unknown";
            }
        }
        catch (Exception exception) {
            bl.e(a, "error in getLocaleInfo", exception);
            return stringArray;
        }
        {
            if (TextUtils.isEmpty((CharSequence)stringArray[1])) {
                stringArray[1] = "Unknown";
            }
            return stringArray;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String s(Context object) {
        try {
            object = object.getPackageManager().getApplicationInfo(object.getPackageName(), 128);
            if (object == null) return null;
            object = ((ApplicationInfo)object).metaData.getString("UMENG_APPKEY");
            if (object != null) {
                return ((String)object).trim();
            }
            bl.c(a, "getAppkey failed. the applicationinfo is null!");
            return null;
        }
        catch (Exception exception) {
            bl.e(a, "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.", exception);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String t(Context object) {
        try {
            object = object.getPackageManager().getApplicationInfo(object.getPackageName(), 128);
            if (object == null) return null;
            if (((ApplicationInfo)object).metaData == null) return null;
            object = ((ApplicationInfo)object).metaData.getString("UMENG_TOKEN");
            if (object != null) {
                return ((String)object).trim();
            }
            bl.c(a, "getToken failed.");
            return null;
        }
        catch (Exception exception) {
            bl.e(a, "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.", exception);
            return null;
        }
    }

    public static String u(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            String string2;
            String string3 = string2 = bj.d();
            if (string2 == null) {
                string3 = bj.H(context);
            }
            return string3;
        }
        return bj.H(context);
    }

    public static String v(Context object) {
        if ((object = (Object)bj.w(object)) != null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append((int)object[0]);
            stringBuffer.append("*");
            stringBuffer.append((int)object[1]);
            return stringBuffer.toString();
        }
        return "Unknown";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int[] w(Context object) {
        int n2;
        int n3;
        int n4;
        DisplayMetrics displayMetrics;
        block6: {
            block5: {
                try {
                    displayMetrics = new DisplayMetrics();
                    ((WindowManager)object.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
                    if ((object.getApplicationInfo().flags & 0x2000) != 0) break block5;
                    n4 = bj.a(displayMetrics, "noncompatWidthPixels");
                    n3 = bj.a(displayMetrics, "noncompatHeightPixels");
                    break block6;
                }
                catch (Exception exception) {
                    bl.e(a, "read resolution fail", exception);
                    return null;
                }
            }
            n3 = -1;
            n4 = -1;
        }
        if (n4 == -1 || n3 == -1) {
            n3 = displayMetrics.widthPixels;
            n4 = displayMetrics.heightPixels;
            n2 = n3;
            n3 = n4;
        } else {
            n2 = n4;
        }
        object = new int[2];
        if (n2 > n3) {
            object[0] = (Context)n3;
            object[1] = (Context)n2;
            return object;
        }
        object[0] = (Context)n2;
        object[1] = (Context)n3;
        return object;
    }

    public static String x(Context object) {
        try {
            object = ((TelephonyManager)object.getSystemService("phone")).getNetworkOperatorName();
            return object;
        }
        catch (Exception exception) {
            bl.c(a, "read carrier fail", exception);
            return "Unknown";
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String y(Context object) {
        block6: {
            block7: {
                try {
                    object = object.getPackageManager().getApplicationInfo(object.getPackageName(), 128);
                    if (object == null) break block6;
                }
                catch (Exception exception) {
                    bl.a(a, "Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.");
                    exception.printStackTrace();
                    return "Unknown";
                }
                if (((ApplicationInfo)object).metaData == null || (object = ((ApplicationInfo)object).metaData.get("UMENG_CHANNEL")) == null) break block6;
                object = object.toString();
                if (object == null) break block7;
                return object;
            }
            bl.a(a, "Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.");
        }
        return "Unknown";
    }

    public static String z(Context context) {
        return context.getPackageName();
    }
}

