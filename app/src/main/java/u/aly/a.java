/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.database.Cursor
 *  android.database.SQLException
 *  android.database.sqlite.SQLiteDatabase
 */
package u.aly;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import u.aly.av;
import u.aly.bl;
import u.aly.d;
import u.aly.f;
import u.aly.i;
import u.aly.k;
import u.aly.n;

public class a {
    private static ContentValues a(i i2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("key", i2.a());
        contentValues.put("label", i2.c());
        contentValues.put("count", Long.valueOf(i2.g()));
        contentValues.put("value", Long.valueOf(i2.f()));
        contentValues.put("totalTimestamp", Long.valueOf(i2.e()));
        contentValues.put("timeWindowNum", i2.h());
        return contentValues;
    }

    /*
     * Unable to fully structure code
     */
    public static String a(SQLiteDatabase var0) {
        block17: {
            block15: {
                block16: {
                    var0.beginTransaction();
                    if (a.c(var0, "aggregated_cache") > 0) break block15;
                    if (!false) break block16;
                    throw new NullPointerException();
                }
                var0.endTransaction();
                return String.valueOf(0);
            }
            var2_5 = var1_1 = var0.rawQuery("select * from aggregated_cache", null);
            if (!var1_1.moveToLast()) break block17;
            var2_5 = var1_1;
            try {
                var3_6 = var1_1.getString(var1_1.getColumnIndex("timeWindowNum"));
            }
            catch (SQLException var4_9) {
                var3_6 = null;
                ** GOTO lbl29
            }
lbl17:
            // 2 sources

            while (true) {
                block18: {
                    var2_5 = var1_1;
                    var0.setTransactionSuccessful();
                    if (var1_1 == null) break block18;
                    var1_1.close();
                }
                var0.endTransaction();
                return var3_6;
            }
            catch (SQLException var4_7) {
                var1_1 = null;
                var3_6 = null;
lbl29:
                // 3 sources

                while (true) {
                    block19: {
                        var2_5 = var1_1;
                        bl.e("queryLastTimeWindowNumFromCache error " + var4_8.toString());
                        if (var1_1 == null) break block19;
                        var1_1.close();
                    }
                    var0.endTransaction();
                    return var3_6;
                }
            }
            catch (Throwable var1_2) {
                var2_5 = null;
lbl40:
                // 2 sources

                while (true) {
                    if (var2_5 != null) {
                        var2_5.close();
                    }
                    var0.endTransaction();
                    throw var1_3;
                }
            }
            {
                catch (Throwable var1_4) {
                    ** continue;
                }
            }
            catch (SQLException var4_10) {
                ** continue;
            }
        }
        var3_6 = null;
        ** while (true)
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Map<String, List<av.f>> a(f var0, SQLiteDatabase var1_4) {
        block16: {
            block17: {
                try {
                    var4_5 = new HashMap<String, List<av.f>>();
                    if (a.c(var1_4, "system") <= 0) break block16;
                    var1_4 = var1_4.rawQuery("select * from system", null);
                }
                catch (Throwable var0_3) {
                    var2_6 = null;
                    ** continue;
                }
                catch (SQLException var3_10) {
                    var1_4 = null;
                    ** continue;
                }
                block8: while (true) {
                    block19: {
                        var2_6 = var1_4;
                        try {
                            block18: {
                                try {
                                    if (!var1_4.moveToNext()) break block17;
                                    var2_6 = var1_4;
                                    var5_11 = var1_4.getString(var1_4.getColumnIndex("key"));
                                    var2_6 = var1_4;
                                    if (!var4_5.containsKey(var5_11)) break block18;
                                    var2_6 = var1_4;
                                    var3_7 = (ArrayList<av.f>)var4_5.get(var5_11);
                                    var2_6 = var1_4;
                                    var4_5.remove(var5_11);
                                    break block19;
                                }
                                catch (SQLException var3_8) lbl-1000:
                                // 2 sources

                                {
                                    while (true) {
                                        var2_6 = var1_4;
                                        var0.a("faild", false);
                                        var2_6 = var1_4;
                                        bl.e("readAllSystemDataForUpload error " + var3_9.toString());
                                        if (var1_4 != null) {
                                            var1_4.close();
                                        }
                                        return null;
                                    }
                                }
                            }
                            var2_6 = var1_4;
                            break;
                        }
                        catch (Throwable var0_1) lbl-1000:
                        // 2 sources

                        {
                            while (true) {
                                if (var2_6 != null) {
                                    var2_6.close();
                                }
                                throw var0_2;
                            }
                        }
                    }
lbl38:
                    // 2 sources

                    while (true) {
                        var2_6 = var1_4;
                        var6_12 = new av.f();
                        var2_6 = var1_4;
                        var6_12.b = var1_4.getLong(var1_4.getColumnIndex("timeStamp"));
                        var2_6 = var1_4;
                        var6_12.a = (int)var1_4.getLong(var1_4.getColumnIndex("count"));
                        var2_6 = var1_4;
                        var3_7.add(var6_12);
                        var2_6 = var1_4;
                        var4_5.put(var5_11, var3_7);
                        continue block8;
                        break;
                    }
                    break;
                }
                var3_7 = new ArrayList<av.f>();
                ** while (true)
            }
lbl55:
            // 2 sources

            while (true) {
                if (var1_4 != null) {
                    var1_4.close();
                }
                return var4_5;
            }
        }
        var1_4 = null;
        ** while (true)
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a(SQLiteDatabase sQLiteDatabase, String string2, long l2, long l3) {
        try {
            int n2 = a.c(sQLiteDatabase, "system");
            int n3 = n.a().c();
            if (n2 < n3) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("key", string2);
                contentValues.put("timeStamp", Long.valueOf(l3));
                contentValues.put("count", Long.valueOf(l2));
                sQLiteDatabase.insert("system", null, contentValues);
                return;
            }
            if (n2 == n3) {
                string2 = new ContentValues();
                string2.put("key", "__meta_ve_of");
                string2.put("timeStamp", Long.valueOf(System.currentTimeMillis()));
                string2.put("count", Integer.valueOf(1));
                sQLiteDatabase.insert("system", null, (ContentValues)string2);
                return;
            }
            a.d(sQLiteDatabase, "__meta_ve_of");
            return;
        }
        catch (SQLException sQLException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a(SQLiteDatabase sQLiteDatabase, Map<String, k> cursor, f f2) {
        long l2;
        long l3;
        k k2;
        Cursor cursor2;
        int n2 = 0;
        Cursor cursor3 = null;
        Cursor cursor4 = cursor2 = null;
        Cursor cursor5 = cursor3;
        try {
            block16: {
                k2 = (k)cursor.get("__ag_of");
                if (k2 == null) return;
                cursor4 = cursor2;
                cursor5 = cursor3;
                try {
                    cursor4 = cursor = sQLiteDatabase.rawQuery("select * from " + "system where key=\"__ag_of\"", null);
                    cursor5 = cursor;
                    cursor.moveToFirst();
                    l3 = 0L;
                    while (true) {
                        cursor4 = cursor;
                        cursor5 = cursor;
                        if (cursor.isAfterLast()) break;
                        cursor4 = cursor;
                        cursor5 = cursor;
                        if (cursor.getCount() > 0) {
                            cursor4 = cursor;
                            cursor5 = cursor;
                            n2 = cursor.getInt(cursor.getColumnIndex("count"));
                            cursor4 = cursor;
                            cursor5 = cursor;
                            l3 = cursor.getLong(cursor.getColumnIndex("timeStamp"));
                            cursor4 = cursor;
                            cursor5 = cursor;
                            sQLiteDatabase.execSQL("delete from " + "system where key=\"__ag_of\"");
                        }
                        cursor4 = cursor;
                        cursor5 = cursor;
                        cursor.moveToNext();
                    }
                    cursor4 = cursor;
                    cursor5 = cursor;
                }
                catch (SQLException sQLException) {
                    cursor5 = cursor4;
                    bl.e("save to system table error " + sQLException.toString());
                    if (cursor4 == null) return;
                    cursor4.close();
                    return;
                }
                cursor2 = new ContentValues();
                cursor4 = cursor;
                cursor5 = cursor;
                cursor2.put("key", k2.c());
                if (n2 != 0) break block16;
                cursor4 = cursor;
                cursor5 = cursor;
                l2 = k2.e();
            }
            l2 = n2;
            cursor4 = cursor;
            cursor5 = cursor;
            long l4 = k2.e();
            l2 += l4;
            cursor4 = cursor;
            cursor5 = cursor;
        }
        catch (Throwable throwable) {
            if (cursor5 == null) throw throwable;
            cursor5.close();
            throw throwable;
        }
        cursor2.put("count", Long.valueOf(l2));
        l2 = l3;
        if (l3 == 0L) {
            cursor4 = cursor;
            cursor5 = cursor;
            l2 = k2.d();
        }
        cursor4 = cursor;
        cursor5 = cursor;
        cursor2.put("timeStamp", Long.valueOf(l2));
        cursor4 = cursor;
        cursor5 = cursor;
        sQLiteDatabase.insert("system", null, (ContentValues)cursor2);
        cursor4 = cursor;
        cursor5 = cursor;
        f2.a("success", false);
        if (cursor == null) {
            return;
        }
        cursor.close();
        return;
    }

    public static void a(SQLiteDatabase sQLiteDatabase, boolean bl2, f f2) {
        a.b(sQLiteDatabase, "system");
        a.b(sQLiteDatabase, "aggregated");
        if (!bl2) {
            a.b(sQLiteDatabase, "limitedck");
            f2.a("success", false);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a(f f2, SQLiteDatabase sQLiteDatabase, List<String> object) {
        try {
            try {
                sQLiteDatabase.beginTransaction();
                if (a.c(sQLiteDatabase, "limitedck") > 0) {
                    a.b(sQLiteDatabase, "limitedck");
                }
                object = object.iterator();
                while (object.hasNext()) {
                    String string2 = (String)object.next();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("ck", string2);
                    sQLiteDatabase.insert("limitedck", null, contentValues);
                }
            }
            catch (SQLException sQLException) {
                bl.e("insertToLimitCKTable error " + sQLException.toString());
                return;
            }
            sQLiteDatabase.setTransactionSuccessful();
            f2.a("success", false);
            return;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public static boolean a(SQLiteDatabase sQLiteDatabase, String string2) {
        try {
            sQLiteDatabase.execSQL("drop table if exists " + string2);
            return true;
        }
        catch (SQLException sQLException) {
            bl.e("delete table faild!");
            sQLException.printStackTrace();
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean a(SQLiteDatabase sQLiteDatabase, Collection<i> object) {
        try {
            try {
                sQLiteDatabase.beginTransaction();
                if (a.c(sQLiteDatabase, "aggregated_cache") > 0) {
                    a.b(sQLiteDatabase, "aggregated_cache");
                }
                object = object.iterator();
                while (object.hasNext()) {
                    sQLiteDatabase.insert("aggregated_cache", null, a.a((i)object.next()));
                }
            }
            catch (SQLException sQLException) {
                bl.e("insert to Aggregated cache table faild!");
                return false;
            }
            sQLiteDatabase.setTransactionSuccessful();
            return true;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    public static boolean a(SQLiteDatabase sQLiteDatabase, f f2) {
        try {
            sQLiteDatabase.beginTransaction();
            if (a.c(sQLiteDatabase, "aggregated_cache") <= 0) {
                f2.a("faild", false);
                return false;
            }
            sQLiteDatabase.execSQL("insert into aggregated(key, count, value, totalTimestamp, timeWindowNum, label) select key, count, value, totalTimestamp, timeWindowNum, label from aggregated_cache");
            sQLiteDatabase.setTransactionSuccessful();
            a.b(sQLiteDatabase, "aggregated_cache");
            f2.a("success", false);
            return true;
        }
        catch (SQLException sQLException) {
            f2.a(false, false);
            bl.e("cacheToAggregatedTable happen " + sQLException.toString());
            return false;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean a(f f2, SQLiteDatabase sQLiteDatabase, Collection<i> object) {
        try {
            try {
                sQLiteDatabase.beginTransaction();
                object = object.iterator();
                while (object.hasNext()) {
                    sQLiteDatabase.insert("aggregated", null, a.a((i)object.next()));
                }
            }
            catch (SQLException sQLException) {
                bl.e("insert to Aggregated cache table faild!");
                return false;
            }
            sQLiteDatabase.setTransactionSuccessful();
            a.b(sQLiteDatabase, "aggregated_cache");
            f2.a("success", false);
            return true;
        }
        finally {
            sQLiteDatabase.endTransaction();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Map<String, List<av.e>> b(SQLiteDatabase var0) {
        if (a.c(var0 /* !! */ , "aggregated") <= 0) ** GOTO lbl44
        var0 /* !! */  = var0 /* !! */ .rawQuery("select * from aggregated", null);
        try {
            var3_2 = new HashMap<String, List<av.e>>();
            while (var0 /* !! */ .moveToNext()) {
                var2_6 = var0 /* !! */ .getString(var0 /* !! */ .getColumnIndex("key"));
                if (var3_2.containsKey(var2_6)) {
                    var1_3 = (List)var3_2.get(var2_6);
                    var3_2.remove(var2_6);
                } else {
                    var1_3 = new ArrayList<E>();
                }
                var4_9 = new av.e();
                var4_9.e = d.a(var0 /* !! */ .getString(var0 /* !! */ .getColumnIndex("label")));
                var4_9.a = var0 /* !! */ .getLong(var0 /* !! */ .getColumnIndex("value"));
                var4_9.b = var0 /* !! */ .getLong(var0 /* !! */ .getColumnIndex("totalTimestamp"));
                var4_9.c = Integer.parseInt(var0 /* !! */ .getString(var0 /* !! */ .getColumnIndex("timeWindowNum")));
                var4_9.d = (int)var0 /* !! */ .getLong(var0 /* !! */ .getColumnIndex("count"));
                var1_3.add(var4_9);
                var3_2.put(var2_6, (List<av.e>)var1_3);
            }
            ** GOTO lbl32
        }
        catch (SQLException var1_4) {}
        ** GOTO lbl-1000
        catch (Throwable var2_7) {
            block14: {
                var1_3 = var0 /* !! */ ;
                var0 /* !! */  = var2_7;
                break block14;
lbl32:
                // 1 sources

                if (var0 /* !! */  != null) {
                    var0 /* !! */ .close();
                }
                return var3_2;
                catch (Throwable var0_1) {
                    var1_3 = null;
                    break block14;
                }
                catch (SQLException var1_5) {
                    var0 /* !! */  = null;
                }
lbl-1000:
                // 2 sources

                {
                    block15: {
                        try {
                            bl.e("readAllAggregatedDataForUpload error " + var1_3.toString());
                            if (var0 /* !! */  == null) break block15;
                        }
                        catch (Throwable var2_8) {
                            var1_3 = var0 /* !! */ ;
                            var0 /* !! */  = var2_8;
                        }
                        var0 /* !! */ .close();
                    }
                    return null;
                }
            }
            if (var1_3 != null) {
                var1_3.close();
            }
            throw var0 /* !! */ ;
        }
    }

    public static boolean b(SQLiteDatabase sQLiteDatabase, String string2) {
        try {
            if (a.c(sQLiteDatabase, string2) >= 0) {
                sQLiteDatabase.execSQL("delete from " + string2);
            }
            return true;
        }
        catch (SQLException sQLException) {
            bl.e("cleanTableData faild!" + sQLException.toString());
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean b(SQLiteDatabase sQLiteDatabase, f f2) {
        SQLiteDatabase sQLiteDatabase2 = null;
        Object object = null;
        SQLiteDatabase sQLiteDatabase3 = object;
        SQLiteDatabase sQLiteDatabase4 = sQLiteDatabase2;
        try {
            try {
                HashMap<List<String>, Object> hashMap = new HashMap<List<String>, Object>();
                sQLiteDatabase3 = object;
                sQLiteDatabase4 = sQLiteDatabase2;
                sQLiteDatabase = sQLiteDatabase.rawQuery("select * from aggregated_cache", null);
                while (true) {
                    sQLiteDatabase3 = sQLiteDatabase;
                    sQLiteDatabase4 = sQLiteDatabase;
                    if (!sQLiteDatabase.moveToNext()) break;
                    sQLiteDatabase3 = sQLiteDatabase;
                    sQLiteDatabase4 = sQLiteDatabase;
                    object = new i();
                    sQLiteDatabase3 = sQLiteDatabase;
                    sQLiteDatabase4 = sQLiteDatabase;
                    ((i)object).a(d.a(sQLiteDatabase.getString(sQLiteDatabase.getColumnIndex("key"))));
                    sQLiteDatabase3 = sQLiteDatabase;
                    sQLiteDatabase4 = sQLiteDatabase;
                    ((i)object).b(d.a(sQLiteDatabase.getString(sQLiteDatabase.getColumnIndex("label"))));
                    sQLiteDatabase3 = sQLiteDatabase;
                    sQLiteDatabase4 = sQLiteDatabase;
                    ((i)object).c(sQLiteDatabase.getInt(sQLiteDatabase.getColumnIndex("count")));
                    sQLiteDatabase3 = sQLiteDatabase;
                    sQLiteDatabase4 = sQLiteDatabase;
                    ((i)object).b(sQLiteDatabase.getInt(sQLiteDatabase.getColumnIndex("value")));
                    sQLiteDatabase3 = sQLiteDatabase;
                    sQLiteDatabase4 = sQLiteDatabase;
                    ((i)object).b(sQLiteDatabase.getString(sQLiteDatabase.getColumnIndex("timeWindowNum")));
                    sQLiteDatabase3 = sQLiteDatabase;
                    sQLiteDatabase4 = sQLiteDatabase;
                    ((i)object).a(Long.parseLong(sQLiteDatabase.getString(sQLiteDatabase.getColumnIndex("totalTimestamp"))));
                    sQLiteDatabase3 = sQLiteDatabase;
                    sQLiteDatabase4 = sQLiteDatabase;
                    hashMap.put(d.a(sQLiteDatabase.getString(sQLiteDatabase.getColumnIndex("key"))), object);
                }
                sQLiteDatabase3 = sQLiteDatabase;
                sQLiteDatabase4 = sQLiteDatabase;
                if (hashMap.size() > 0) {
                    sQLiteDatabase3 = sQLiteDatabase;
                    sQLiteDatabase4 = sQLiteDatabase;
                    f2.a(hashMap, false);
                } else {
                    sQLiteDatabase3 = sQLiteDatabase;
                    sQLiteDatabase4 = sQLiteDatabase;
                    f2.a("faild", false);
                }
            }
            catch (SQLException sQLException) {
                sQLiteDatabase4 = sQLiteDatabase3;
                f2.a(false, false);
                sQLiteDatabase4 = sQLiteDatabase3;
                bl.e("cacheToMemory happen " + sQLException.toString());
                if (sQLiteDatabase3 == null) return false;
                sQLiteDatabase3.close();
                return false;
            }
            if (sQLiteDatabase == null) return false;
        }
        catch (Throwable throwable) {
            if (sQLiteDatabase4 == null) throw throwable;
            sQLiteDatabase4.close();
            throw throwable;
        }
        sQLiteDatabase.close();
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int c(SQLiteDatabase sQLiteDatabase, String string2) {
        int n2;
        SQLiteDatabase sQLiteDatabase2 = null;
        SQLiteDatabase sQLiteDatabase3 = null;
        int n3 = 0;
        sQLiteDatabase3 = sQLiteDatabase = sQLiteDatabase.rawQuery("select * from " + string2, null);
        sQLiteDatabase2 = sQLiteDatabase;
        try {
            n3 = n2 = sQLiteDatabase.getCount();
            if (sQLiteDatabase == null) return n3;
        }
        catch (Exception exception) {
            sQLiteDatabase2 = sQLiteDatabase3;
            try {
                bl.e("count error " + exception.toString());
                if (sQLiteDatabase3 == null) return n3;
            }
            catch (Throwable throwable) {
                if (sQLiteDatabase2 == null) throw throwable;
                sQLiteDatabase2.close();
                throw throwable;
            }
            sQLiteDatabase3.close();
            return 0;
        }
        sQLiteDatabase.close();
        return n2;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public static List<String> c(SQLiteDatabase var0) {
        block15: {
            if (a.c(var0 /* !! */ , "limitedck") <= 0) break block15;
            var1_1 = var0 /* !! */ .rawQuery("select * from limitedck", null);
            var0 /* !! */  = var1_1;
            try {
                var2_5 = new ArrayList<String>();
            }
            catch (SQLException var2_6) lbl-1000:
            // 2 sources

            {
                while (true) {
                    block17: {
                        var0 /* !! */  = var1_1;
                        bl.e("loadLimitCKFromDB error " + var2_7.toString());
                        if (var1_1 == null) break block17;
                        var1_1.close();
                    }
lbl24:
                    // 2 sources

                    while (true) {
                        var0 /* !! */  = null;
lbl26:
                        // 2 sources

                        return var0 /* !! */ ;
                    }
                    break;
                }
            }
            while (true) {
                var0 /* !! */  = var1_1;
                if (!var1_1.moveToNext()) break;
                var0 /* !! */  = var1_1;
                var2_5.add(var1_1.getString(var1_1.getColumnIndex("ck")));
                continue;
                break;
            }
            var0 /* !! */  = var2_5;
            ** while (var1_1 == null)
lbl31:
            // 1 sources

            var1_1.close();
            return var2_5;
        }
        ** while (!false)
lbl35:
        // 1 sources

        throw new NullPointerException();
        catch (Throwable var1_2) {
            var0 /* !! */  = null;
lbl38:
            // 2 sources

            while (true) {
                if (var0 /* !! */  != null) {
                    var0 /* !! */ .close();
                }
                throw var1_3;
            }
        }
        {
            catch (Throwable var1_4) {
                ** continue;
            }
        }
        catch (SQLException var2_8) {
            var1_1 = null;
            ** continue;
        }
    }

    private static void d(SQLiteDatabase sQLiteDatabase, String string2) {
        try {
            sQLiteDatabase.beginTransaction();
            sQLiteDatabase.execSQL("update system set count=count+1 where key like '" + string2 + "'");
            sQLiteDatabase.setTransactionSuccessful();
            return;
        }
        catch (SQLException sQLException) {
            return;
        }
        finally {
            if (sQLiteDatabase != null) {
                sQLiteDatabase.endTransaction();
            }
        }
    }
}

