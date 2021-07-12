/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerialContext;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.lang.reflect.Type;

public class ArraySerializer
implements ObjectSerializer {
    private final ObjectSerializer compObjectSerializer;
    private final Class<?> componentType;

    public ArraySerializer(Class<?> clazz, ObjectSerializer objectSerializer) {
        this.componentType = clazz;
        this.compObjectSerializer = objectSerializer;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void write(JSONSerializer var1_1, Object var2_2, Object var3_4, Type var4_5, int var5_6) throws IOException {
        block8: {
            var7_7 = var1_1.out;
            if (var2_2 == null) {
                var7_7.writeNull(SerializerFeature.WriteNullListAsEmpty);
                return;
            }
            var8_8 = (Object[])var2_2;
            var6_9 = var8_8.length;
            var4_5 = var1_1.context;
            var1_1.setContext((SerialContext)var4_5, var2_2, var3_4, 0);
            try {
                var7_7.append('[');
                var5_6 = 0;
lbl13:
                // 2 sources

                while (true) {
                    if (var5_6 < var6_9) {
                        if (var5_6 != 0) {
                            var7_7.append(',');
                        }
                    }
                    ** GOTO lbl33
                    break;
                }
            }
            catch (Throwable var2_3) {
                var1_1.context = var4_5;
                throw var2_3;
            }
            var2_2 = var8_8[var5_6];
            if (var2_2 != null) ** GOTO lbl28
            {
                var7_7.append("null");
                break block8;
lbl28:
                // 1 sources

                if (var2_2.getClass() != this.componentType) ** GOTO lbl-1000
                this.compObjectSerializer.write(var1_1, var2_2, var5_6, null, 0);
                break block8;
            }
lbl-1000:
            // 1 sources

            {
                var1_1.getObjectWriter(var2_2.getClass()).write(var1_1, var2_2, var5_6, null, 0);
                break block8;
lbl33:
                // 1 sources

                var7_7.append(']');
                var1_1.context = var4_5;
                return;
            }
        }
        ++var5_6;
        ** while (true)
    }
}

