/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.database.DatabaseErrorHandler
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 */
package u.aly;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;

public class e
extends ContextWrapper {
    private String a;

    public e(Context context, String string2) {
        super(context);
        this.a = string2;
    }

    public File getDatabasePath(String object) {
        object = new File(this.a + (String)object);
        if (!((File)object).getParentFile().exists() && !((File)object).getParentFile().isDirectory()) {
            ((File)object).getParentFile().mkdirs();
        }
        return object;
    }

    public SQLiteDatabase openOrCreateDatabase(String string2, int n2, SQLiteDatabase.CursorFactory cursorFactory) {
        return SQLiteDatabase.openOrCreateDatabase((String)this.getDatabasePath(string2).getAbsolutePath(), (SQLiteDatabase.CursorFactory)cursorFactory);
    }

    public SQLiteDatabase openOrCreateDatabase(String string2, int n2, SQLiteDatabase.CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) {
        return SQLiteDatabase.openOrCreateDatabase((String)this.getDatabasePath(string2).getAbsolutePath(), (SQLiteDatabase.CursorFactory)cursorFactory, (DatabaseErrorHandler)databaseErrorHandler);
    }
}

