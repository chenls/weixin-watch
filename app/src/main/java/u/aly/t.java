/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  org.json.JSONObject
 */
package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.analytics.b;
import java.io.File;
import org.json.JSONObject;
import u.aly.ap;
import u.aly.bg;
import u.aly.bi;
import u.aly.bj;
import u.aly.bk;
import u.aly.by;
import u.aly.x;

public class t {
    private final byte[] a = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
    private final int b;
    private final int c;
    private String d = "1.0";
    private String e = null;
    private byte[] f = null;
    private byte[] g = null;
    private byte[] h = null;
    private int i = 0;
    private int j = 0;
    private int k = 0;
    private byte[] l = null;
    private byte[] m = null;
    private boolean n = false;

    private t(byte[] byArray, String string2, byte[] byArray2) throws Exception {
        this.b = 1;
        this.c = 0;
        if (byArray == null || byArray.length == 0) {
            throw new Exception("entity is null or empty");
        }
        this.e = string2;
        this.k = byArray.length;
        this.l = bi.a(byArray);
        this.j = (int)(System.currentTimeMillis() / 1000L);
        this.m = byArray2;
    }

    public static String a(Context context) {
        if ((context = ap.a(context)) == null) {
            return null;
        }
        return context.getString("signature", null);
    }

    public static t a(Context context, String object, byte[] byArray) {
        try {
            String string2 = bj.u(context);
            String string3 = bj.f(context);
            SharedPreferences sharedPreferences = ap.a(context);
            String string4 = sharedPreferences.getString("signature", null);
            int n2 = sharedPreferences.getInt("serial", 1);
            object = new t(byArray, (String)object, (string3 + string2).getBytes());
            ((t)object).a(string4);
            ((t)object).a(n2);
            ((t)object).b();
            sharedPreferences.edit().putInt("serial", n2 + 1).putString("signature", ((t)object).a()).commit();
            ((t)object).b(context);
            return object;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private byte[] a(byte[] byArray, int n2) {
        int n3;
        int n4 = 0;
        byte[] byArray2 = com.umeng.analytics.b.b(this.m);
        byte[] byArray3 = com.umeng.analytics.b.b(this.l);
        int n5 = byArray2.length;
        byte[] byArray4 = new byte[n5 * 2];
        for (n3 = 0; n3 < n5; ++n3) {
            byArray4[n3 * 2] = byArray3[n3];
            byArray4[n3 * 2 + 1] = byArray2[n3];
        }
        for (n3 = 0; n3 < 2; ++n3) {
            byArray4[n3] = byArray[n3];
            byArray4[byArray4.length - n3 - 1] = byArray[byArray.length - n3 - 1];
        }
        byte by2 = (byte)(n2 & 0xFF);
        byte by3 = (byte)(n2 >> 8 & 0xFF);
        byte by4 = (byte)(n2 >> 16 & 0xFF);
        byte by5 = (byte)(n2 >>> 24);
        for (n2 = n4; n2 < byArray4.length; ++n2) {
            byArray4[n2] = (byte)(byArray4[n2] ^ (new byte[]{by2, by3, by4, by5})[n2 % 4]);
        }
        return byArray4;
    }

    public static t b(Context context, String object, byte[] byArray) {
        try {
            String string2 = bj.u(context);
            String string3 = bj.f(context);
            SharedPreferences sharedPreferences = ap.a(context);
            String string4 = sharedPreferences.getString("signature", null);
            int n2 = sharedPreferences.getInt("serial", 1);
            object = new t(byArray, (String)object, (string3 + string2).getBytes());
            ((t)object).a(true);
            ((t)object).a(string4);
            ((t)object).a(n2);
            ((t)object).b();
            sharedPreferences.edit().putInt("serial", n2 + 1).putString("signature", ((t)object).a()).commit();
            ((t)object).b(context);
            return object;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private byte[] d() {
        return this.a(this.a, (int)(System.currentTimeMillis() / 1000L));
    }

    private byte[] e() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(com.umeng.analytics.b.a(this.f));
        stringBuilder.append(this.i);
        stringBuilder.append(this.j);
        stringBuilder.append(this.k);
        stringBuilder.append(com.umeng.analytics.b.a(this.g));
        return com.umeng.analytics.b.b(stringBuilder.toString().getBytes());
    }

    public String a() {
        return com.umeng.analytics.b.a(this.f);
    }

    public void a(int n2) {
        this.i = n2;
    }

    public void a(String string2) {
        this.f = com.umeng.analytics.b.a(string2);
    }

    public void a(boolean bl2) {
        this.n = bl2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b() {
        if (this.f == null) {
            this.f = this.d();
        }
        if (this.n) {
            byte[] byArray = new byte[16];
            try {
                System.arraycopy(this.f, 1, byArray, 0, 16);
                this.l = com.umeng.analytics.b.a(this.l, byArray);
            }
            catch (Exception exception) {}
        }
        this.g = this.a(this.f, this.j);
        this.h = this.e();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void b(Context object) {
        String string2 = this.e;
        String string3 = x.a((Context)object).b().e(null);
        String string4 = com.umeng.analytics.b.a(this.f);
        byte[] byArray = new byte[16];
        System.arraycopy(this.f, 2, byArray, 0, 16);
        String string5 = com.umeng.analytics.b.a(com.umeng.analytics.b.b(byArray));
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appkey", (Object)string2);
            if (string3 != null) {
                jSONObject.put("umid", (Object)string3);
            }
            jSONObject.put("signature", (Object)string4);
            jSONObject.put("checksum", (Object)string5);
            string2 = jSONObject.toString();
            object = new File(object.getFilesDir(), ".umeng");
            if (!((File)object).exists()) {
                ((File)object).mkdir();
            }
            bk.a(new File((File)object, "exchangeIdentity.json"), string2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] c() {
        bg bg2 = new bg();
        bg2.a(this.d);
        bg2.b(this.e);
        bg2.c(com.umeng.analytics.b.a(this.f));
        bg2.a(this.i);
        bg2.c(this.j);
        bg2.d(this.k);
        bg2.a(this.l);
        int n2 = this.n ? 1 : 0;
        bg2.e(n2);
        bg2.d(com.umeng.analytics.b.a(this.g));
        bg2.e(com.umeng.analytics.b.a(this.h));
        try {
            return (bg)new by().a(bg2);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        int n2 = 1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("version : %s\n", this.d));
        stringBuilder.append(String.format("address : %s\n", this.e));
        stringBuilder.append(String.format("signature : %s\n", com.umeng.analytics.b.a(this.f)));
        stringBuilder.append(String.format("serial : %s\n", this.i));
        stringBuilder.append(String.format("timestamp : %d\n", this.j));
        stringBuilder.append(String.format("length : %d\n", this.k));
        stringBuilder.append(String.format("guid : %s\n", com.umeng.analytics.b.a(this.g)));
        stringBuilder.append(String.format("checksum : %s ", com.umeng.analytics.b.a(this.h)));
        if (!this.n) {
            n2 = 0;
        }
        stringBuilder.append(String.format("codex : %d", n2));
        return stringBuilder.toString();
    }
}

