/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.SQLException
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteOpenHelper
 */
package u.aly;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import u.aly.bl;
import u.aly.d;
import u.aly.e;

class c
extends SQLiteOpenHelper {
    private static Context b;
    private String a;

    private c(Context context, String string2, SQLiteDatabase.CursorFactory cursorFactory, int n2) {
        String string3;
        block3: {
            block2: {
                if (string2 == null) break block2;
                string3 = string2;
                if (!string2.equals("")) break block3;
            }
            string3 = "cc.db";
        }
        super(context, string3, cursorFactory, n2);
        this.b();
    }

    private c(Context context, String string2, String string3, SQLiteDatabase.CursorFactory cursorFactory, int n2) {
        this((Context)new e(context, string2), string3, cursorFactory, n2);
    }

    static /* synthetic */ Context a() {
        return b;
    }

    public static c a(Context object) {
        synchronized (c.class) {
            b = object;
            object = u.aly.c$a.a;
            return object;
        }
    }

    private boolean a(SQLiteDatabase sQLiteDatabase) {
        try {
            this.a = "create table if not exists limitedck(Id INTEGER primary key autoincrement, ck TEXT unique)";
            sQLiteDatabase.execSQL(this.a);
            return true;
        }
        catch (SQLException sQLException) {
            bl.e("create reference table error!");
            return false;
        }
    }

    private void b() {
        try {
            SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
            if (!this.a("aggregated", sQLiteDatabase) || !this.a("aggregated_cache", sQLiteDatabase)) {
                this.c(sQLiteDatabase);
            }
            if (!this.a("system", sQLiteDatabase)) {
                this.b(sQLiteDatabase);
            }
            if (!this.a("limitedck", sQLiteDatabase)) {
                this.a(sQLiteDatabase);
            }
            return;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        catch (Exception exception) {
            return;
        }
    }

    private boolean b(SQLiteDatabase sQLiteDatabase) {
        try {
            this.a = "create table if not exists system(Id INTEGER primary key autoincrement, key TEXT, timeStamp INTEGER, count INTEGER)";
            sQLiteDatabase.execSQL(this.a);
            return true;
        }
        catch (SQLException sQLException) {
            bl.e("create system table error!");
            return false;
        }
    }

    private boolean c(SQLiteDatabase sQLiteDatabase) {
        try {
            this.a = "create table if not exists aggregated_cache(Id INTEGER primary key autoincrement, key TEXT, totalTimestamp TEXT, value INTEGER, count INTEGER, label TEXT, timeWindowNum TEXT)";
            sQLiteDatabase.execSQL(this.a);
            this.a = "create table if not exists aggregated(Id INTEGER primary key autoincrement, key TEXT, totalTimestamp TEXT, value INTEGER, count INTEGER, label TEXT, timeWindowNum TEXT)";
            sQLiteDatabase.execSQL(this.a);
            return true;
        }
        catch (SQLException sQLException) {
            bl.e("create aggregated table error!");
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean a(String string2, SQLiteDatabase sQLiteDatabase) {
        boolean bl2;
        String string3 = null;
        String string4 = null;
        boolean bl3 = false;
        boolean bl4 = false;
        if (string2 == null) return bl4;
        try {
            string2 = sQLiteDatabase.rawQuery("select count(*) as c from sqlite_master where type ='table' and name ='" + string2.trim() + "' ", null);
            bl2 = bl3;
            string4 = string2;
            string3 = string2;
            if (string2.moveToNext()) {
                string4 = string2;
                string3 = string2;
                int n2 = string2.getInt(0);
                bl2 = bl3;
                if (n2 > 0) {
                    bl2 = true;
                }
            }
            bl4 = bl2;
            if (string2 == null) return bl4;
        }
        catch (Exception exception) {
            if (string4 == null) return bl4;
            string4.close();
            return false;
        }
        catch (Throwable throwable) {
            if (string3 == null) throw throwable;
            string3.close();
            throw throwable;
        }
        string2.close();
        return bl2;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.beginTransaction();
            this.c(sQLiteDatabase);
            this.b(sQLiteDatabase);
            this.a(sQLiteDatabase);
            sQLiteDatabase.setTransactionSuccessful();
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n2, int n3) {
    }

    private static class a {
        private static final c a = new c(c.a(), d.a(c.a()), "cc.db", null, 1);

        private a() {
        }
    }
}

