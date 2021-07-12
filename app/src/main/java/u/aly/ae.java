/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package u.aly;

import android.content.Context;
import com.umeng.analytics.f;
import com.umeng.analytics.g;
import u.aly.ad;
import u.aly.ah;
import u.aly.ai;

public final class ae
implements ah {
    private static ae c;
    private ah a;
    private Context b;

    private ae(Context context) {
        this.b = context.getApplicationContext();
        this.a = new ad(this.b);
    }

    public static ae a(Context object) {
        synchronized (ae.class) {
            if (c == null && object != null) {
                c = new ae((Context)object);
            }
            object = c;
            return object;
        }
    }

    @Override
    public void a() {
        f.b(new g(){

            @Override
            public void a() {
                ae.this.a.a();
            }
        });
    }

    public void a(ah ah2) {
        this.a = ah2;
    }

    @Override
    public void a(final ai ai2) {
        f.b(new g(){

            @Override
            public void a() {
                ae.this.a.a(ai2);
            }
        });
    }

    @Override
    public void b() {
        f.b(new g(){

            @Override
            public void a() {
                ae.this.a.b();
            }
        });
    }

    @Override
    public void b(ai ai2) {
        this.a.b(ai2);
    }

    @Override
    public void c() {
        f.c(new g(){

            @Override
            public void a() {
                ae.this.a.c();
            }
        });
    }
}

