/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.text.TextUtils
 *  android.util.Base64
 */
package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.e;
import java.util.ArrayList;
import java.util.List;
import u.aly.ai;
import u.aly.ap;
import u.aly.ar;
import u.aly.as;
import u.aly.av;
import u.aly.aw;
import u.aly.bc;
import u.aly.bd;
import u.aly.bj;
import u.aly.bk;
import u.aly.bl;
import u.aly.by;
import u.aly.m;
import u.aly.v;
import u.aly.x;

public class ak {
    private List<ai> a = new ArrayList<ai>();
    private Context b = null;

    public ak(Context context) {
        this.b = context;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void c(av av2) {
        // MONITORENTER : this
        SharedPreferences sharedPreferences = this.a.iterator();
        while (sharedPreferences.hasNext()) {
            sharedPreferences.next().a(av2);
        }
        sharedPreferences = ap.a(this.b);
        if (sharedPreferences == null) {
            // MONITOREXIT : this
            return;
        }
        if (!TextUtils.isEmpty((CharSequence)(sharedPreferences = sharedPreferences.getString("userlevel", "")))) {
            av2.b.j = sharedPreferences;
        }
        this.a.clear();
        // MONITOREXIT : this
        if (av.c != 0L) {
            av2.b.d.a = av.c;
        }
        m.a(this.b).a(av2);
        sharedPreferences = e.a(this.b);
        if (sharedPreferences != null && !TextUtils.isEmpty((CharSequence)sharedPreferences[0]) && !TextUtils.isEmpty((CharSequence)sharedPreferences[1])) {
            av2.b.g.a = sharedPreferences[0];
            av2.b.g.b = sharedPreferences[1];
        }
        aw.a(this.b).a(av2);
    }

    public Context a() {
        return this.b;
    }

    public void a(ai ai2) {
        synchronized (this) {
            this.a.add(ai2);
            return;
        }
    }

    public void a(av av2) {
        if (ar.g(this.b) == null) {
            return;
        }
        this.b(av2);
        this.c(av2);
    }

    protected boolean a(int n2) {
        return true;
    }

    public int b() {
        synchronized (this) {
            int n2;
            block3: {
                int n3 = this.a.size();
                long l2 = av.c;
                n2 = n3;
                if (l2 == 0L) break block3;
                n2 = n3 + 1;
            }
            return n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void b(av av2) {
        String string2;
        String[] stringArray;
        av2.a.a = AnalyticsConfig.getAppkey(this.b);
        av2.a.b = AnalyticsConfig.getChannel(this.b);
        av2.a.c = bk.a(AnalyticsConfig.getSecretKey(this.b));
        av2.a.m = AnalyticsConfig.getVerticalType(this.b);
        av2.a.l = AnalyticsConfig.getSDKVersion(this.b);
        av2.a.e = bj.z(this.b);
        int n2 = Integer.parseInt(bj.c(this.b));
        String string3 = bj.d(this.b);
        SharedPreferences sharedPreferences = ap.a(this.b);
        if (sharedPreferences == null) {
            av2.a.h = n2;
            av2.a.d = string3;
        } else {
            av2.a.h = sharedPreferences.getInt("versioncode", 0);
            av2.a.d = sharedPreferences.getString("versionname", "");
        }
        av2.a.f = bj.A(this.b);
        av2.a.g = bj.D(this.b);
        if (AnalyticsConfig.mWrapperType != null && AnalyticsConfig.mWrapperVersion != null) {
            av2.a.i = AnalyticsConfig.mWrapperType;
            av2.a.j = AnalyticsConfig.mWrapperVersion;
        }
        av2.a.t = bj.f(this.b);
        av2.a.n = bj.g(this.b);
        av2.a.s = bj.u(this.b);
        av2.a.B = bj.E(this.b);
        av2.a.C = bj.F(this.b);
        int[] nArray = bj.w(this.b);
        if (nArray != null) {
            av2.a.r = nArray[1] + "*" + nArray[0];
        }
        if (AnalyticsConfig.GPU_RENDERER == null || AnalyticsConfig.GPU_VENDER != null) {
            // empty if block
        }
        av2.a.H = "Wi-Fi".equals((stringArray = bj.m(this.b))[0]) ? "wifi" : ("2G/3G".equals(stringArray[0]) ? "2G/3G" : "unknow");
        if (!"".equals(stringArray[1])) {
            av2.a.I = stringArray[1];
        }
        if (!TextUtils.isEmpty((CharSequence)(string2 = bj.h(this.b)))) {
            av2.a.J = string2;
        }
        av2.a.G = bj.k(this.b);
        String[] stringArray2 = bj.r(this.b);
        av2.a.F = stringArray2[0];
        av2.a.E = stringArray2[1];
        av2.a.D = bj.p(this.b);
        as.a(this.b, av2);
        try {
            bc bc2 = v.a(this.b).b();
            if (bc2 == null) {
                return;
            }
            byte[] byArray = new by().a(bc2);
            av2.a.O = Base64.encodeToString((byte[])byArray, (int)0);
        }
        catch (Exception exception) {}
        try {
            bd bd2 = x.a(this.b).a();
            if (bd2 == null) {
                bl.e("trans the imprint is null");
                return;
            }
            byte[] byArray = new by().a(bd2);
            av2.a.N = Base64.encodeToString((byte[])byArray, (int)0);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }
}

