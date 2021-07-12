/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 */
package com.umeng.analytics.game;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.Serializable;
import u.aly.am;
import u.aly.ap;

public class b {
    public String a;
    public String b;
    private Context c;
    private final String d;
    private final String e;
    private final String f;
    private final String g;
    private a h = null;

    public b(Context context) {
        this.d = "um_g_cache";
        this.e = "single_level";
        this.f = "stat_player_level";
        this.g = "stat_game_level";
        this.c = context;
    }

    public a a(String string2) {
        this.h = new a(string2);
        this.h.a();
        return this.h;
    }

    public void a() {
        if (this.h != null) {
            this.h.b();
            SharedPreferences.Editor editor = this.c.getSharedPreferences("um_g_cache", 0).edit();
            editor.putString("single_level", am.a(this.h));
            editor.putString("stat_player_level", this.b);
            editor.putString("stat_game_level", this.a);
            editor.commit();
        }
    }

    public a b(String object) {
        if (this.h != null) {
            this.h.d();
            if (this.h.a((String)object)) {
                object = this.h;
                this.h = null;
                return object;
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void b() {
        SharedPreferences sharedPreferences;
        block7: {
            block6: {
                block5: {
                    sharedPreferences = ap.a(this.c, "um_g_cache");
                    String string2 = sharedPreferences.getString("single_level", null);
                    if (string2 != null) {
                        this.h = (a)am.a(string2);
                        if (this.h != null) {
                            this.h.c();
                        }
                    }
                    if (this.b != null) break block5;
                    this.b = sharedPreferences.getString("stat_player_level", null);
                    if (this.b != null) break block5;
                    string2 = ap.a(this.c);
                    if (string2 == null) break block6;
                    this.b = string2.getString("userlevel", null);
                }
                if (this.a == null) break block7;
            }
            return;
        }
        this.a = sharedPreferences.getString("stat_game_level", null);
    }

    static class a
    implements Serializable {
        private static final long a = 20140327L;
        private String b;
        private long c;
        private long d;

        public a(String string2) {
            this.b = string2;
        }

        public void a() {
            this.d = System.currentTimeMillis();
        }

        public boolean a(String string2) {
            return this.b.equals(string2);
        }

        public void b() {
            this.c += System.currentTimeMillis() - this.d;
            this.d = 0L;
        }

        public void c() {
            this.a();
        }

        public void d() {
            this.b();
        }

        public long e() {
            return this.c;
        }

        public String f() {
            return this.b;
        }
    }
}

