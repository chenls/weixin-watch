/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  javax.microedition.khronos.opengles.GL10
 */
package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.a;
import com.umeng.analytics.b;
import com.umeng.analytics.c;
import com.umeng.analytics.f;
import com.umeng.analytics.g;
import com.umeng.analytics.social.e;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.microedition.khronos.opengles.GL10;
import u.aly.ae;
import u.aly.af;
import u.aly.ag;
import u.aly.ai;
import u.aly.an;
import u.aly.ap;
import u.aly.ar;
import u.aly.at;
import u.aly.av;
import u.aly.bj;
import u.aly.bl;
import u.aly.m;

public class d
implements an {
    private Context a = null;
    private c b;
    private af c = new af();
    private at d = new at();
    private ar e = new ar();
    private ag f;
    private ae g;
    private m h = null;
    private boolean i = false;
    private boolean j = false;

    d() {
        this.c.a(this);
    }

    static /* synthetic */ boolean a(d d2, boolean bl2) {
        d2.j = bl2;
        return bl2;
    }

    private void e(Context context) {
        if (!this.i) {
            this.a = context.getApplicationContext();
            this.f = new ag(this.a);
            this.g = ae.a(this.a);
            this.i = true;
            if (this.h == null) {
                this.h = m.a(this.a);
            }
            if (!this.j) {
                com.umeng.analytics.f.b(new g(){

                    @Override
                    public void a() {
                        d.this.h.a(new u.aly.f(){

                            @Override
                            public void a(Object object, boolean bl2) {
                                com.umeng.analytics.d.a(d.this, true);
                            }
                        });
                    }
                });
            }
        }
    }

    private void f(Context context) {
        this.e.c(context);
        if (this.b != null) {
            this.b.a();
        }
    }

    private void g(Context context) {
        this.e.d(context);
        this.d.a(context);
        if (this.b != null) {
            this.b.b();
        }
        this.g.b();
    }

    public ar a() {
        return this.e;
    }

    void a(double d2, double d3) {
        if (AnalyticsConfig.a == null) {
            AnalyticsConfig.a = new double[2];
        }
        AnalyticsConfig.a[0] = d2;
        AnalyticsConfig.a[1] = d3;
    }

    void a(long l2) {
        AnalyticsConfig.sLatentWindow = (int)l2 * 1000;
    }

    void a(final Context context) {
        if (context == null) {
            bl.e("unexpected null context in onResume");
            return;
        }
        if (AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            this.d.a(context.getClass().getName());
        }
        try {
            if (!this.i) {
                this.e(context);
            }
            com.umeng.analytics.f.a(new g(){

                @Override
                public void a() {
                    d.this.f(context.getApplicationContext());
                }
            });
            return;
        }
        catch (Exception exception) {
            bl.e("Exception occurred in Mobclick.onResume(). ", exception);
            return;
        }
    }

    public void a(Context context, int n2) {
        AnalyticsConfig.a(context, n2);
    }

    void a(Context context, MobclickAgent.EScenarioType eScenarioType) {
        if (context != null) {
            this.a = context.getApplicationContext();
        }
        if (eScenarioType != null) {
            this.a(context, eScenarioType.toValue());
        }
    }

    void a(Context object, String string2) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return;
        }
        if (object == null) {
            bl.e("unexpected null context in reportError");
            return;
        }
        try {
            if (!this.i) {
                this.e((Context)object);
            }
            object = new av.i();
            object.a = System.currentTimeMillis();
            object.b = 2L;
            object.c = string2;
            this.g.a((ai)object);
            return;
        }
        catch (Exception exception) {
            bl.e(exception);
            return;
        }
    }

    public void a(Context context, String string2, String string3, long l2, int n2) {
        try {
            if (!this.i) {
                this.e(context);
            }
            this.f.a(string2, string3, l2, n2);
            return;
        }
        catch (Exception exception) {
            bl.e(exception);
            return;
        }
    }

    public void a(Context context, String string2, HashMap<String, Object> hashMap) {
        try {
            if (!this.i) {
                this.e(context);
            }
            this.f.a(string2, hashMap);
            return;
        }
        catch (Exception exception) {
            bl.e(exception);
            return;
        }
    }

    void a(Context context, String string2, Map<String, Object> map, long l2) {
        try {
            if (!this.i) {
                this.e(context);
            }
            this.f.a(string2, map, l2);
            return;
        }
        catch (Exception exception) {
            bl.e(exception);
            return;
        }
    }

    void a(Context context, Throwable throwable) {
        if (context == null || throwable == null) {
            return;
        }
        try {
            this.a(context, com.umeng.analytics.b.a(throwable));
            return;
        }
        catch (Exception exception) {
            bl.e(exception);
            return;
        }
    }

    public void a(Context context, List<String> list, int n2, String string2) {
        try {
            if (!this.i) {
                this.e(context);
            }
            this.f.a(list, n2, string2);
            return;
        }
        catch (Exception exception) {
            bl.e(exception);
            return;
        }
    }

    void a(MobclickAgent.UMAnalyticsConfig uMAnalyticsConfig) {
        if (uMAnalyticsConfig.mContext != null) {
            this.a = uMAnalyticsConfig.mContext.getApplicationContext();
        }
        if (!TextUtils.isEmpty((CharSequence)uMAnalyticsConfig.mAppkey)) {
            AnalyticsConfig.a(uMAnalyticsConfig.mContext, uMAnalyticsConfig.mAppkey);
            if (!TextUtils.isEmpty((CharSequence)uMAnalyticsConfig.mChannelId)) {
                AnalyticsConfig.a(uMAnalyticsConfig.mChannelId);
            }
            AnalyticsConfig.CATCH_EXCEPTION = uMAnalyticsConfig.mIsCrashEnable;
            this.a(this.a, uMAnalyticsConfig.mType);
            return;
        }
        bl.e("the appkey is null!");
    }

    public void a(c c2) {
        this.b = c2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    void a(String string2) {
        if (AnalyticsConfig.ACTIVITY_DURATION_OPEN) return;
        try {
            this.d.a(string2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public void a(String string2, String string3) {
        AnalyticsConfig.mWrapperType = string2;
        AnalyticsConfig.mWrapperVersion = string3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void a(Throwable throwable) {
        try {
            this.d.a();
            if (this.a != null) {
                if (throwable != null && this.g != null) {
                    av.i i2 = new av.i();
                    i2.a = System.currentTimeMillis();
                    i2.b = 1L;
                    i2.c = com.umeng.analytics.b.a(throwable);
                    this.g.a(i2);
                }
                this.h.c();
                this.g(this.a);
                ap.a(this.a).edit().commit();
            }
            com.umeng.analytics.f.a();
            return;
        }
        catch (Exception exception) {
            bl.e("Exception in onAppCrash", exception);
            return;
        }
    }

    void a(GL10 stringArray) {
        if ((stringArray = bj.a((GL10)stringArray)).length == 2) {
            AnalyticsConfig.GPU_VENDER = stringArray[0];
            AnalyticsConfig.GPU_RENDERER = stringArray[1];
        }
    }

    void a(boolean bl2) {
        AnalyticsConfig.CATCH_EXCEPTION = bl2;
    }

    void b() {
        try {
            com.umeng.analytics.f.a(new g(){

                @Override
                public void a() {
                    String[] stringArray = com.umeng.analytics.e.a(d.this.a);
                    if (stringArray != null && !TextUtils.isEmpty((CharSequence)stringArray[0]) && !TextUtils.isEmpty((CharSequence)stringArray[1])) {
                        boolean bl2 = d.this.a().e(d.this.a);
                        ae.a(d.this.a).c();
                        if (bl2) {
                            d.this.a().f(d.this.a);
                        }
                        com.umeng.analytics.e.b(d.this.a);
                    }
                }
            });
            return;
        }
        catch (Exception exception) {
            bl.e(" Excepthon  in  onProfileSignOff", exception);
            return;
        }
    }

    void b(long l2) {
        AnalyticsConfig.kContinueSessionMillis = l2;
    }

    void b(final Context context) {
        if (context == null) {
            bl.e("unexpected null context in onPause");
            return;
        }
        if (AnalyticsConfig.ACTIVITY_DURATION_OPEN) {
            this.d.b(context.getClass().getName());
        }
        try {
            if (!this.i) {
                this.e(context);
            }
            com.umeng.analytics.f.a(new g(){

                @Override
                public void a() {
                    d.this.g(context.getApplicationContext());
                    d.this.h.d();
                }
            });
            return;
        }
        catch (Exception exception) {
            bl.e("Exception occurred in Mobclick.onRause(). ", exception);
            return;
        }
    }

    void b(Context context, String string2) {
        if (context != null) {
            this.a = context.getApplicationContext();
        }
        AnalyticsConfig.b(context, string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    void b(String string2) {
        if (AnalyticsConfig.ACTIVITY_DURATION_OPEN) return;
        try {
            this.d.b(string2);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    void b(final String string2, final String string3) {
        try {
            com.umeng.analytics.f.a(new g(){

                @Override
                public void a() {
                    String[] stringArray = com.umeng.analytics.e.a(d.this.a);
                    if (stringArray == null || !string2.equals(stringArray[0]) || !string3.equals(stringArray[1])) {
                        boolean bl2 = d.this.a().e(d.this.a);
                        ae.a(d.this.a).c();
                        if (bl2) {
                            d.this.a().f(d.this.a);
                        }
                        com.umeng.analytics.e.a(d.this.a, string2, string3);
                    }
                }
            });
            return;
        }
        catch (Exception exception) {
            bl.e(" Excepthon  in  onProfileSignIn", exception);
            return;
        }
    }

    void b(boolean bl2) {
        AnalyticsConfig.ENABLE_MEMORY_BUFFER = bl2;
    }

    void c(Context context) {
        try {
            if (!this.i) {
                this.e(context);
            }
            this.g.a();
            return;
        }
        catch (Exception exception) {
            bl.e(exception);
            return;
        }
    }

    void c(boolean bl2) {
        AnalyticsConfig.ACTIVITY_DURATION_OPEN = bl2;
    }

    void d(Context context) {
        try {
            this.d.a();
            this.g(context);
            ap.a(context).edit().commit();
            this.h.b();
            com.umeng.analytics.f.a();
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    void d(boolean bl2) {
        com.umeng.analytics.a.e = bl2;
    }

    void e(boolean bl2) {
        bl.a = bl2;
        com.umeng.analytics.social.e.v = bl2;
    }

    void f(boolean bl2) {
        AnalyticsConfig.a(bl2);
    }
}

