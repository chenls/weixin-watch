/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerialContext;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Enumeration;

public class EnumerationSerializer
implements ObjectSerializer {
    public static EnumerationSerializer instance = new EnumerationSerializer();

    /*
     * Unable to fully structure code
     */
    @Override
    public void write(JSONSerializer var1_1, Object var2_2, Object var3_6, Type var4_7, int var5_8) throws IOException {
        var9_9 = var1_1.out;
        if (var2_2 == null) {
            var9_9.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        var8_10 = null;
        var7_11 = var8_10;
        if (var9_9.isEnabled(SerializerFeature.WriteClassName)) {
            var7_11 = var8_10;
            if (var4_7 instanceof ParameterizedType) {
                var7_11 = ((ParameterizedType)var4_7).getActualTypeArguments()[0];
            }
        }
        var8_10 = (Enumeration)var2_2;
        var4_7 = var1_1.context;
        var1_1.setContext((SerialContext)var4_7, var2_2, var3_6, 0);
        var9_9.append('[');
        var5_8 = 0;
        while (true) {
            block14: {
                if (!var8_10.hasMoreElements()) break;
                var2_2 = var8_10.nextElement();
                var6_12 = var5_8 + 1;
                if (var5_8 == 0) break block14;
                var9_9.append(',');
            }
            if (var2_2 == null) {
                var9_9.writeNull();
                var5_8 = var6_12;
            }
            var1_1.getObjectWriter(var2_2.getClass()).write(var1_1, var2_2, var6_12 - 1, (Type)var7_11, 0);
            var5_8 = var6_12;
            continue;
            break;
        }
        try {
            var9_9.append(']');
            var1_1.context = var4_7;
            return;
        }
        catch (Throwable var2_5) {
            ** continue;
        }
        catch (Throwable var2_3) lbl-1000:
        // 2 sources

        {
            while (true) {
                var1_1.context = var4_7;
                throw var2_4;
            }
        }
    }
}

