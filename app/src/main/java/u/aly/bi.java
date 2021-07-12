/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import u.aly.bk;

public class bi {
    public static int a;

    public static String a(byte[] byArray, String string2) throws UnsupportedEncodingException, DataFormatException {
        if ((byArray = bi.b(byArray)) != null) {
            return new String(byArray, string2);
        }
        return null;
    }

    public static byte[] a(String string2, String string3) throws IOException {
        if (bk.d(string2)) {
            return null;
        }
        return bi.a(string2.getBytes(string3));
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static byte[] a(byte[] var0) throws IOException {
        block9: {
            if (var0 == null || var0.length <= 0) {
                return null;
            }
            var3_4 = new Deflater();
            var3_4.setInput(var0);
            var3_4.finish();
            var0 = new byte[8192];
            bi.a = 0;
            var2_5 = new ByteArrayOutputStream();
            try {
                while (!var3_4.finished()) {
                    var1_6 = var3_4.deflate(var0);
                    bi.a += var1_6;
                    var2_5.write(var0, 0, var1_6);
                }
                ** GOTO lbl-1000
            }
            catch (Throwable var0_1) lbl-1000:
            // 2 sources

            {
                while (true) {
                    if (var2_5 != null) {
                        var2_5.close();
                    }
                    throw var0_2;
                }
            }
            catch (Throwable var0_3) {
                var2_5 = null;
                ** continue;
            }
lbl-1000:
            // 1 sources

            {
                var3_4.end();
                if (var2_5 == null) break block9;
            }
            var2_5.close();
        }
        return var2_5.toByteArray();
    }

    public static byte[] b(byte[] object) throws UnsupportedEncodingException, DataFormatException {
        int n2 = 0;
        if (object == null || ((byte[])object).length == 0) {
            return null;
        }
        Inflater inflater = new Inflater();
        inflater.setInput((byte[])object, 0, ((byte[])object).length);
        object = new ByteArrayOutputStream();
        byte[] byArray = new byte[1024];
        while (!inflater.needsInput()) {
            int n3 = inflater.inflate(byArray);
            ((ByteArrayOutputStream)object).write(byArray, n2, n3);
            n2 += n3;
        }
        inflater.end();
        return ((ByteArrayOutputStream)object).toByteArray();
    }
}

