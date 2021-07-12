/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.umeng.analytics.g;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import u.aly.ap;
import u.aly.av;
import u.aly.bl;
import u.aly.d;
import u.aly.f;
import u.aly.h;
import u.aly.i;
import u.aly.l;
import u.aly.n;
import u.aly.o;
import u.aly.p;
import u.aly.q;

public class m {
    private static final int i = 48;
    private static final int j = 49;
    private static Context k;
    private h a = null;
    private o b = null;
    private p c = null;
    private boolean d = false;
    private boolean e = false;
    private long f = 0L;
    private final String g;
    private final String h;
    private List<String> l = new ArrayList<String>();
    private a m = null;
    private final Thread n = new Thread(new Runnable(){

        @Override
        public void run() {
            Looper.prepare();
            if (m.this.m == null) {
                u.aly.m.a(m.this, new a(m.this));
            }
            m.this.f();
        }
    });

    private m() {
        this.g = "main_fest_mode";
        this.h = "main_fest_timestamp";
        if (k != null) {
            if (this.a == null) {
                this.a = new h();
            }
            if (this.b == null) {
                this.b = o.a(k);
            }
            if (this.c == null) {
                this.c = new p();
            }
        }
        this.n.start();
    }

    static /* synthetic */ h a(m m2, h h2) {
        m2.a = h2;
        return h2;
    }

    static /* synthetic */ a a(m m2, a a2) {
        m2.m = a2;
        return a2;
    }

    public static final m a(Context context) {
        k = context;
        return u.aly.m$b.a;
    }

    static /* synthetic */ p a(m m2, p p2) {
        m2.c = p2;
        return p2;
    }

    private void a(l l2, List<String> list) {
        this.a.a(new f(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void a(Object object, boolean bl2) {
                if (object instanceof h) {
                    u.aly.m.a(m.this, (h)object);
                    return;
                } else {
                    if (!(object instanceof Boolean)) return;
                    m.this.l();
                    return;
                }
            }
        }, l2, list, this.l);
    }

    static /* synthetic */ boolean a(m m2, boolean bl2) {
        m2.d = bl2;
        return bl2;
    }

    private void f() {
        long l2 = System.currentTimeMillis();
        this.m.sendEmptyMessageDelayed(48, q.b(l2));
        this.m.sendEmptyMessageDelayed(49, q.c(l2));
    }

    private boolean g() {
        return this.l.size() < u.aly.n.a().d();
    }

    private void h() {
        SharedPreferences sharedPreferences = ap.a(k);
        if (!sharedPreferences.getBoolean("main_fest_mode", false)) {
            this.e = true;
            sharedPreferences = sharedPreferences.edit();
            sharedPreferences.putBoolean("main_fest_mode", true);
            sharedPreferences.putLong("main_fest_timestamp", System.currentTimeMillis());
            sharedPreferences.commit();
        }
    }

    private void i() {
        SharedPreferences.Editor editor = ap.a(k).edit();
        editor.putBoolean("main_fest_mode", false);
        editor.putLong("main_fest_timestamp", 0L);
        editor.commit();
        this.e = false;
    }

    private void j() {
        SharedPreferences sharedPreferences = ap.a(k);
        this.e = sharedPreferences.getBoolean("main_fest_mode", false);
        this.f = sharedPreferences.getLong("main_fest_timestamp", 0L);
    }

    private void k() {
        Iterator<Map.Entry<List<String>, i>> iterator = this.a.a().entrySet().iterator();
        while (iterator.hasNext()) {
            List<String> list = iterator.next().getKey();
            if (this.l.contains(list)) continue;
            this.l.add(u.aly.d.a(list));
        }
        if (this.l.size() > 0) {
            this.b.a(new f(), this.l);
        }
    }

    private void l() {
        this.c.a(new f(){

            @Override
            public void a(Object object, boolean bl2) {
                u.aly.m.a(m.this, (p)object);
            }
        }, "__ag_of");
    }

    private void m() {
        try {
            if (this.a.a().size() > 0) {
                this.b.c(new f(){

                    @Override
                    public void a(Object object, boolean bl2) {
                        if (object instanceof String) {
                            m.this.a.d();
                        }
                    }
                }, this.a.a());
            }
            if (this.c.a().size() > 0) {
                this.b.b(new f(){

                    @Override
                    public void a(Object object, boolean bl2) {
                        if (object instanceof String) {
                            m.this.c.b();
                        }
                    }
                }, this.c.a());
            }
            if (this.l.size() > 0) {
                this.b.a(new f(), this.l);
            }
            return;
        }
        catch (Throwable throwable) {
            bl.b("converyMemoryToDataTable happen error: " + throwable.toString());
            return;
        }
    }

    private void n() {
        try {
            if (this.a.a().size() > 0) {
                this.b.a(new f(){

                    @Override
                    public void a(Object object, boolean bl2) {
                    }
                }, this.a.a());
            }
            if (this.c.a().size() > 0) {
                this.b.b(new f(){

                    @Override
                    public void a(Object object, boolean bl2) {
                        if (object instanceof String) {
                            m.this.c.b();
                        }
                    }
                }, this.c.a());
            }
            if (this.l.size() > 0) {
                this.b.a(new f(), this.l);
            }
            return;
        }
        catch (Throwable throwable) {
            bl.b("convertMemoryToCacheTable happen error: " + throwable.toString());
            return;
        }
    }

    private void o() {
        List<String> list = this.b.b();
        if (list != null) {
            this.l = list;
        }
    }

    public void a(long l2, long l3, String string2) {
        this.b.a(new f(){

            @Override
            public void a(Object object, boolean bl2) {
                if (object.equals("success")) {
                    // empty if block
                }
            }
        }, string2, l2, l3);
    }

    public void a(av av2) {
        if (av2.b.h != null) {
            av2.b.h.a = this.b(new f());
            av2.b.h.b = this.c(new f());
        }
    }

    public void a(final f f2) {
        if (this.d) {
            return;
        }
        com.umeng.analytics.f.b(new g(){

            @Override
            public void a() {
                try {
                    m.this.b.a(new f(){

                        /*
                         * Enabled aggressive block sorting
                         */
                        @Override
                        public void a(Object object, boolean bl2) {
                            if (object instanceof Map) {
                                object = (Map)object;
                                m.this.a.a((Map<List<String>, i>)object);
                            } else if (object instanceof String || object instanceof Boolean) {
                                // empty if block
                            }
                            u.aly.m.a(m.this, true);
                        }
                    });
                    m.this.j();
                    m.this.o();
                    f2.a("success", false);
                    return;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    return;
                }
            }
        });
    }

    public void a(f object, Map<List<String>, l> object2) {
        object2 = (l)object2.values().toArray()[0];
        List<String> list = ((l)object2).a();
        if (this.l.size() > 0 && this.l.contains(u.aly.d.a(list))) {
            this.a.a(new f((f)object){
                final /* synthetic */ f a;
                {
                    this.a = f2;
                }

                @Override
                public void a(Object object, boolean bl2) {
                    if (object instanceof h) {
                        u.aly.m.a(m.this, (h)object);
                    }
                    this.a.a("success", false);
                }
            }, (l)object2);
            return;
        }
        if (this.e) {
            this.a((l)object2, list);
            return;
        }
        if (this.g()) {
            object = u.aly.d.a(list);
            if (!this.l.contains(object)) {
                this.l.add((String)object);
            }
            this.a.a(new f(){

                @Override
                public void a(Object object, boolean bl2) {
                    u.aly.m.a(m.this, (h)object);
                }
            }, list, (l)object2);
            return;
        }
        this.a((l)object2, list);
        this.h();
    }

    public boolean a() {
        return this.d;
    }

    public Map<String, List<av.e>> b(f object) {
        object = this.b.a();
        HashMap<String, List<av.e>> hashMap = new HashMap<String, List<av.e>>();
        if (object == null || object.size() <= 0) {
            return null;
        }
        for (String string2 : this.l) {
            if (!object.containsKey(string2)) continue;
            hashMap.put(string2, (List<av.e>)object.get(string2));
        }
        return hashMap;
    }

    public void b() {
        this.n();
    }

    public Map<String, List<av.f>> c(f f2) {
        if (this.c.a().size() > 0) {
            this.b.b(new f(){

                @Override
                public void a(Object object, boolean bl2) {
                    if (object instanceof String) {
                        m.this.c.b();
                    }
                }
            }, this.c.a());
        }
        return this.b.b(new f());
    }

    public void c() {
        this.n();
    }

    public void d() {
        this.n();
    }

    public void d(f f2) {
        boolean bl2 = false;
        if (this.e) {
            if (this.f == 0L) {
                this.j();
            }
            bl2 = q.a(System.currentTimeMillis(), this.f);
        }
        if (!bl2) {
            this.i();
            this.l.clear();
        }
        this.c.b();
        this.b.a(new f(){

            @Override
            public void a(Object object, boolean bl2) {
                if (object.equals("success")) {
                    m.this.k();
                }
            }
        }, bl2);
    }

    private static class a
    extends Handler {
        private final WeakReference<m> a;

        public a(m m2) {
            this.a = new WeakReference<m>(m2);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public void handleMessage(Message message) {
            if (this.a == null) return;
            switch (message.what) {
                default: {
                    return;
                }
                case 48: {
                    this.sendEmptyMessageDelayed(48, q.b(System.currentTimeMillis()));
                    u.aly.m.a(k).n();
                    return;
                }
                case 49: 
            }
            this.sendEmptyMessageDelayed(49, q.c(System.currentTimeMillis()));
            u.aly.m.a(k).m();
        }
    }

    private static class b {
        private static final m a = new m();

        private b() {
        }
    }
}

