/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 */
package u.aly;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.a;
import com.umeng.analytics.b;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import u.aly.aj;
import u.aly.aw;
import u.aly.bj;
import u.aly.bk;
import u.aly.bl;
import u.aly.x;

public class al {
    private String a;
    private String b = "10.0.0.172";
    private int c = 80;
    private Context d;
    private aj e;

    public al(Context context) {
        this.d = context;
        this.a = this.a(context);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String a(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Android");
        stringBuffer.append("/");
        stringBuffer.append("6.0.1");
        stringBuffer.append(" ");
        try {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append(bj.B(context));
            stringBuffer2.append("/");
            stringBuffer2.append(bj.d(context));
            stringBuffer2.append(" ");
            stringBuffer2.append(Build.MODEL);
            stringBuffer2.append("/");
            stringBuffer2.append(Build.VERSION.RELEASE);
            stringBuffer2.append(" ");
            stringBuffer2.append(bk.a(AnalyticsConfig.getAppkey(context)));
            stringBuffer.append(URLEncoder.encode(stringBuffer2.toString(), "UTF-8"));
            return stringBuffer.toString();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return stringBuffer.toString();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void a() {
        bl.b("constructURLS");
        String string2 = x.a(this.d).b().b("");
        String string3 = x.a(this.d).b().a("");
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            com.umeng.analytics.a.f = com.umeng.analytics.b.b(string2);
        }
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            com.umeng.analytics.a.g = com.umeng.analytics.b.b(string3);
        }
        com.umeng.analytics.a.i = new String[]{com.umeng.analytics.a.f, com.umeng.analytics.a.g};
        if (bj.q(this.d)) {
            com.umeng.analytics.a.i = new String[]{com.umeng.analytics.a.g, com.umeng.analytics.a.f};
        } else {
            int n2 = aw.a(this.d).b();
            if (n2 != -1) {
                if (n2 == 0) {
                    com.umeng.analytics.a.i = new String[]{com.umeng.analytics.a.f, com.umeng.analytics.a.g};
                } else if (n2 == 1) {
                    com.umeng.analytics.a.i = new String[]{com.umeng.analytics.a.g, com.umeng.analytics.a.f};
                }
            }
        }
        bl.b("constructURLS list size:" + com.umeng.analytics.a.i.length);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private byte[] a(byte[] var1_1, String var2_7) {
        if (this.e != null) {
            this.e.a();
        }
        if (this.b()) {
            var5_10 = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.b, this.c));
            var5_10 = (HttpURLConnection)new URL((String)var2_8).openConnection((Proxy)var5_10);
        } else {
            var5_10 = (HttpURLConnection)new URL((String)var2_8).openConnection();
        }
        var6_11 = var5_10;
        var5_10.setRequestProperty("X-Umeng-UTC", String.valueOf(System.currentTimeMillis()));
        var6_11 = var5_10;
        var5_10.setRequestProperty("X-Umeng-Sdk", this.a);
        var6_11 = var5_10;
        var5_10.setRequestProperty("Msg-Type", "envelope/json");
        var6_11 = var5_10;
        var5_10.setRequestProperty("Content-Type", "envelope/json");
        var6_11 = var5_10;
        var5_10.setConnectTimeout(10000);
        var6_11 = var5_10;
        var5_10.setReadTimeout(30000);
        var6_11 = var5_10;
        var5_10.setRequestMethod("POST");
        var6_11 = var5_10;
        var5_10.setDoOutput(true);
        var6_11 = var5_10;
        var5_10.setDoInput(true);
        var6_11 = var5_10;
        var5_10.setUseCaches(false);
        var6_11 = var5_10;
        if (Integer.parseInt(Build.VERSION.SDK) < 8) {
            var6_11 = var5_10;
            System.setProperty("http.keepAlive", "false");
        }
        var6_11 = var5_10;
        var7_12 = var5_10.getOutputStream();
        var6_11 = var5_10;
        var7_12.write((byte[])var1_1);
        var6_11 = var5_10;
        var7_12.flush();
        var6_11 = var5_10;
        var7_12.close();
        var6_11 = var5_10;
        if (this.e != null) {
            var6_11 = var5_10;
            this.e.b();
        }
        var6_11 = var5_10;
        var4_13 = var5_10.getResponseCode();
        var6_11 = var5_10;
        var1_1 = var5_10.getHeaderField("Content-Type");
        var6_11 = var5_10;
        if (TextUtils.isEmpty((CharSequence)var1_1)) ** GOTO lbl-1000
        var6_11 = var5_10;
        if (var1_1.equalsIgnoreCase("application/thrift")) {
            var3_14 = true;
        } else lbl-1000:
        // 2 sources

        {
            var3_14 = false;
        }
        if (var4_13 != 200 || !var3_14) ** GOTO lbl81
        var6_11 = var5_10;
        bl.c("Send message to " + (String)var2_8);
        var6_11 = var5_10;
        var1_1 = var5_10.getInputStream();
        var2_8 = bk.b((InputStream)var1_1);
        var6_11 = var5_10;
        bk.c((InputStream)var1_1);
        if (var5_10 != null) {
            var5_10.disconnect();
        }
        return var2_8;
        catch (Throwable var2_9) {
            var6_11 = var5_10;
            try {
                bk.c((InputStream)var1_1);
                var6_11 = var5_10;
                throw var2_9;
            }
            catch (Exception var1_2) {}
            ** GOTO lbl-1000
            {
                catch (Throwable var1_6) {}
            }
lbl81:
            // 1 sources

            if (var5_10 != null) {
                var5_10.disconnect();
            }
            return null;
            catch (Throwable var1_4) {
                var6_11 = null;
                if (var6_11 != null) {
                    var6_11.disconnect();
                }
                throw var1_5;
            }
            catch (Exception var1_7) {
                var5_10 = null;
            }
lbl-1000:
            // 2 sources

            {
                var6_11 = var5_10;
                bl.e("IOException,Failed to send message.", (Throwable)var1_3);
                if (var5_10 != null) {
                    var5_10.disconnect();
                }
                return null;
            }
        }
    }

    private boolean b() {
        block8: {
            Object object;
            block7: {
                if (this.d.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", this.d.getPackageName()) != 0) {
                    return false;
                }
                object = (ConnectivityManager)this.d.getSystemService("connectivity");
                if (bj.a(this.d, "android.permission.ACCESS_NETWORK_STATE")) break block7;
                return false;
            }
            object = object.getActiveNetworkInfo();
            if (object == null) break block8;
            if (object.getType() == 1 || (object = object.getExtraInfo()) == null) break block8;
            try {
                boolean bl2;
                if (((String)object).equals("cmwap") || ((String)object).equals("3gwap") || (bl2 = ((String)object).equals("uniwap"))) {
                    return true;
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return false;
    }

    public void a(aj aj2) {
        this.e = aj2;
    }

    public byte[] a(byte[] byArray) {
        byte[] byArray2 = null;
        int n2 = 0;
        while (true) {
            block7: {
                byte[] byArray3;
                block6: {
                    byArray3 = byArray2;
                    if (n2 >= com.umeng.analytics.a.i.length) break block6;
                    byArray2 = this.a(byArray, com.umeng.analytics.a.i[n2]);
                    if (byArray2 == null) break block7;
                    byArray3 = byArray2;
                    if (this.e != null) {
                        this.e.c();
                        byArray3 = byArray2;
                    }
                }
                return byArray3;
            }
            if (this.e != null) {
                this.e.d();
            }
            ++n2;
        }
    }
}

