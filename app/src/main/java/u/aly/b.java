/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteOpenHelper
 */
package u.aly;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.concurrent.atomic.AtomicInteger;
import u.aly.c;

public class b {
    private static b c;
    private static SQLiteOpenHelper d;
    private AtomicInteger a = new AtomicInteger();
    private AtomicInteger b = new AtomicInteger();
    private SQLiteDatabase e;

    public static b a(Context object) {
        synchronized (b.class) {
            if (c == null) {
                u.aly.b.b(object);
            }
            object = c;
            return object;
        }
    }

    private static void b(Context context) {
        synchronized (b.class) {
            if (c == null) {
                c = new b();
                d = u.aly.c.a(context);
            }
            return;
        }
    }

    public SQLiteDatabase a() {
        synchronized (this) {
            if (this.a.incrementAndGet() == 1) {
                this.e = d.getReadableDatabase();
            }
            SQLiteDatabase sQLiteDatabase = this.e;
            return sQLiteDatabase;
        }
    }

    public SQLiteDatabase b() {
        synchronized (this) {
            if (this.a.incrementAndGet() == 1) {
                this.e = d.getWritableDatabase();
            }
            SQLiteDatabase sQLiteDatabase = this.e;
            return sQLiteDatabase;
        }
    }

    public void c() {
        synchronized (this) {
            if (this.a.decrementAndGet() == 0) {
                this.e.close();
            }
            if (this.b.decrementAndGet() == 0) {
                this.e.close();
            }
            return;
        }
    }
}

