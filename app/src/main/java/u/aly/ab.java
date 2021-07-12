/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Environment
 */
package u.aly;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import u.aly.bj;
import u.aly.bk;
import u.aly.r;

public class ab
extends r {
    private static final String a = "utdid";
    private static final String b = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final Pattern c = Pattern.compile("UTDID\">([^<]+)");
    private Context d;

    public ab(Context context) {
        super(a);
        this.d = context;
    }

    /*
     * Enabled aggressive block sorting
     */
    private String b(String object) {
        if (object == null || !((Matcher)(object = c.matcher((CharSequence)object))).find()) {
            return null;
        }
        return ((Matcher)object).group(1);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String g() {
        Object object = this.h();
        if (object == null || !((File)object).exists()) {
            return null;
        }
        object = new FileInputStream((File)object);
        {
            catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
            try {
                String string2 = this.b(bk.a((InputStream)object));
                return string2;
            }
            finally {
                bk.c((InputStream)object);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private File h() {
        if (!bj.a(this.d, b)) {
            return null;
        }
        if (!Environment.getExternalStorageState().equals("mounted")) return null;
        File file = Environment.getExternalStorageDirectory();
        try {
            return new File(file.getCanonicalPath(), ".UTSystemConfig/Global/Alvin2.xml");
        }
        catch (Exception exception) {
            return null;
        }
    }

    @Override
    public String f() {
        try {
            String string2 = (String)Class.forName("com.ut.device.UTDevice").getMethod("getUtdid", Context.class).invoke(null, this.d);
            return string2;
        }
        catch (Exception exception) {
            return this.g();
        }
    }
}

