/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Clob;
import java.sql.SQLException;

public class ClobSeriliazer
implements ObjectSerializer {
    public static final ClobSeriliazer instance = new ClobSeriliazer();

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(JSONSerializer var1_1, Object var2_4, Object var3_5, Type var4_6, int var5_7) throws IOException {
        if (var2_4 != null) ** GOTO lbl5
        try {
            var1_1.writeNull();
            return;
lbl5:
            // 1 sources

            var2_4 = ((Clob)var2_4).getCharacterStream();
            var3_5 = new StringBuilder();
            try {
                var4_6 = new char[2048];
lbl9:
                // 2 sources

                while (true) {
                    var5_7 = var2_4.read((char[])var4_6, 0, ((Object)var4_6).length);
                    if (var5_7 < 0) {
                        var3_5 = var3_5.toString();
                    }
                    ** GOTO lbl-1000
                    break;
                }
            }
            catch (Exception var1_3) {
                throw new JSONException("read string from reader error", var1_3);
            }
            var2_4.close();
            var1_1.write((String)var3_5);
            return;
        }
        catch (SQLException var1_2) {
            throw new IOException("write clob error", var1_2);
        }
lbl-1000:
        // 1 sources

        {
            var3_5.append((char[])var4_6, 0, var5_7);
            ** continue;
        }
    }
}

