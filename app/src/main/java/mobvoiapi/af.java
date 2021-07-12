/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Process
 *  android.text.TextUtils
 *  android.util.Log
 */
package mobvoiapi;

import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class af {
    private Writer a;
    private String b;
    private SimpleDateFormat c;
    private SimpleDateFormat d;
    private String e;
    private String f;
    private String g;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(String string2) {
        synchronized (this) {
            block8: {
                boolean bl2;
                if (this.a != null || (bl2 = this.a())) {
                    try {
                        if (!this.b().equals(this.b)) {
                            this.a.flush();
                            this.a.close();
                            this.a = null;
                            if (!this.a()) break block8;
                        }
                        this.a.write(string2);
                        this.a.flush();
                    }
                    catch (IOException iOException) {
                        iOException.printStackTrace();
                    }
                }
            }
            return;
        }
    }

    private boolean a() {
        if (this.e == null) {
            return false;
        }
        File file = new File(this.e);
        if (!file.exists() && !file.mkdirs()) {
            Log.w((String)"FileLogger", (String)("Cannot create dir: " + this.e));
            return false;
        }
        this.b = this.b();
        try {
            this.a = new FileWriter(new File(this.e, this.b(this.b)), true);
            return true;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
        }
    }

    private String b() {
        return this.c.format(new Date());
    }

    private String b(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.f).append("_log_").append(string2);
        if (!TextUtils.isEmpty((CharSequence)this.g)) {
            stringBuilder.append("_").append(this.g);
        }
        stringBuilder.append(".txt");
        return stringBuilder.toString();
    }

    public void a(String string2, String string3, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.d.format(new Date()));
        stringBuilder.append(" ");
        stringBuilder.append(string2);
        stringBuilder.append("\t");
        stringBuilder.append(Process.myPid()).append(" ").append(Process.myTid()).append(" ");
        if (!TextUtils.isEmpty((CharSequence)string3)) {
            stringBuilder.append(string3);
        }
        if (throwable != null) {
            stringBuilder.append("\n\t");
            stringBuilder.append(Log.getStackTraceString((Throwable)throwable));
        }
        stringBuilder.append("\n");
        this.a(stringBuilder.toString());
    }
}

