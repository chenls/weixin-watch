/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.text.TextUtils
 */
package com.umeng.analytics.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.d;
import com.umeng.analytics.f;
import com.umeng.analytics.g;
import com.umeng.analytics.game.a;
import com.umeng.analytics.game.b;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import u.aly.ap;
import u.aly.bl;

class c
implements com.umeng.analytics.c {
    private d a = MobclickAgent.getAgent();
    private b b = null;
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private final int g;
    private final String h;
    private final String i;
    private final String j;
    private final String k;
    private final String l;
    private final String m;
    private final String n;
    private final String o;
    private final String p;
    private final String q;
    private final String r;
    private final String s;
    private final String t;
    private final String u;
    private final String v;
    private final String w;
    private final String x;
    private final String y;
    private Context z;

    public c() {
        this.c = 100;
        this.d = 1;
        this.e = 0;
        this.f = -1;
        this.g = 1;
        this.h = "level";
        this.i = "pay";
        this.j = "buy";
        this.k = "use";
        this.l = "bonus";
        this.m = "item";
        this.n = "cash";
        this.o = "coin";
        this.p = "source";
        this.q = "amount";
        this.r = "user_level";
        this.s = "bonus_source";
        this.t = "level";
        this.u = "status";
        this.v = "duration";
        this.w = "curtype";
        this.x = "orderid";
        this.y = "UMGameAgent.init(Context) should be called before any game api";
        com.umeng.analytics.game.a.a = true;
    }

    private void a(final String string2, final int n2) {
        if (this.z == null) {
            bl.e("UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        com.umeng.analytics.f.a(new g(){

            @Override
            public void a() {
                Serializable serializable = c.this.b.b(string2);
                if (serializable != null) {
                    long l2 = ((b.a)serializable).e();
                    if (l2 <= 0L) {
                        bl.b("level duration is 0");
                        return;
                    }
                    serializable = new HashMap();
                    ((HashMap)serializable).put("level", string2);
                    ((HashMap)serializable).put("status", n2);
                    ((HashMap)serializable).put("duration", l2);
                    if (((c)c.this).b.b != null) {
                        ((HashMap)serializable).put("user_level", ((c)c.this).b.b);
                    }
                    c.this.a.a(c.this.z, "level", (HashMap<String, Object>)serializable);
                    return;
                }
                bl.d(String.format("finishLevel(or failLevel) called before startLevel", new Object[0]));
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a() {
        bl.b("App resume from background");
        if (this.z == null) {
            bl.e("UMGameAgent.init(Context) should be called before any game api");
            return;
        } else {
            if (!com.umeng.analytics.game.a.a) return;
            this.b.b();
            return;
        }
    }

    void a(double d2, double d3, int n2) {
        if (this.z == null) {
            bl.e("UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("cash", (long)(d2 * 100.0));
        hashMap.put("coin", (long)(d3 * 100.0));
        hashMap.put("source", n2);
        if (this.b.b != null) {
            hashMap.put("user_level", this.b.b);
        }
        if (this.b.a != null) {
            hashMap.put("level", this.b.a);
        }
        this.a.a(this.z, "pay", hashMap);
    }

    void a(double d2, int n2) {
        if (this.z == null) {
            bl.e("UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("coin", (long)(100.0 * d2));
        hashMap.put("bonus_source", n2);
        if (this.b.b != null) {
            hashMap.put("user_level", this.b.b);
        }
        if (this.b.a != null) {
            hashMap.put("level", this.b.a);
        }
        this.a.a(this.z, "bonus", hashMap);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void a(double d2, String string2, double d3, int n2, String string3) {
        if (this.z == null) {
            bl.e("UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        if (!(d2 >= 0.0) || !(d3 >= 0.0)) return;
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        if (!TextUtils.isEmpty((CharSequence)string2) && string2.length() > 0 && string2.length() <= 3) {
            hashMap.put("curtype", string2);
        }
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            try {
                int n3 = string3.getBytes("UTF-8").length;
                if (n3 > 0 && n3 <= 1024) {
                    hashMap.put("orderid", string3);
                }
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                unsupportedEncodingException.printStackTrace();
            }
        }
        hashMap.put("cash", (long)(d2 * 100.0));
        hashMap.put("coin", (long)(d3 * 100.0));
        hashMap.put("source", n2);
        if (this.b.b != null) {
            hashMap.put("user_level", this.b.b);
        }
        if (this.b.a != null) {
            hashMap.put("level", this.b.a);
        }
        this.a.a(this.z, "pay", hashMap);
    }

    void a(double d2, String string2, int n2, double d3, int n3) {
        this.a(d2, d3 * (double)n2, n3);
        this.a(string2, n2, d3);
    }

    void a(Context context) {
        if (context == null) {
            bl.e("Context is null, can't init GameAgent");
            return;
        }
        this.z = context.getApplicationContext();
        this.a.a(this);
        this.b = new b(this.z);
        this.a.a(context, 1);
    }

    void a(String string2) {
        this.b.b = string2;
        SharedPreferences sharedPreferences = ap.a(this.z);
        if (sharedPreferences == null) {
            return;
        }
        sharedPreferences = sharedPreferences.edit();
        sharedPreferences.putString("userlevel", string2);
        sharedPreferences.commit();
    }

    void a(String string2, int n2, double d2) {
        if (this.z == null) {
            bl.e("UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("item", string2);
        hashMap.put("amount", n2);
        hashMap.put("coin", (long)((double)n2 * d2 * 100.0));
        if (this.b.b != null) {
            hashMap.put("user_level", this.b.b);
        }
        if (this.b.a != null) {
            hashMap.put("level", this.b.a);
        }
        this.a.a(this.z, "buy", hashMap);
    }

    void a(String string2, int n2, double d2, int n3) {
        this.a((double)n2 * d2, n3);
        this.a(string2, n2, d2);
    }

    void a(boolean bl2) {
        bl.b(String.format("Trace sleep time : %b", bl2));
        com.umeng.analytics.game.a.a = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void b() {
        if (this.z == null) {
            bl.e("UMGameAgent.init(Context) should be called before any game api");
            return;
        } else {
            if (!com.umeng.analytics.game.a.a) return;
            this.b.a();
            return;
        }
    }

    void b(final String string2) {
        if (this.z == null) {
            bl.e("UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        this.b.a = string2;
        com.umeng.analytics.f.a(new g(){

            @Override
            public void a() {
                c.this.b.a(string2);
                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                hashMap.put("level", string2);
                hashMap.put("status", 0);
                if (((c)c.this).b.b != null) {
                    hashMap.put("user_level", ((c)c.this).b.b);
                }
                c.this.a.a(c.this.z, "level", hashMap);
            }
        });
    }

    void b(String string2, int n2, double d2) {
        if (this.z == null) {
            bl.e("UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("item", string2);
        hashMap.put("amount", n2);
        hashMap.put("coin", (long)((double)n2 * d2 * 100.0));
        if (this.b.b != null) {
            hashMap.put("user_level", this.b.b);
        }
        if (this.b.a != null) {
            hashMap.put("level", this.b.a);
        }
        this.a.a(this.z, "use", hashMap);
    }

    void c(String string2) {
        if (this.z == null) {
            bl.e("UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        this.a(string2, 1);
    }

    void d(String string2) {
        if (this.z == null) {
            bl.e("UMGameAgent.init(Context) should be called before any game api");
            return;
        }
        this.a(string2, -1);
    }
}

