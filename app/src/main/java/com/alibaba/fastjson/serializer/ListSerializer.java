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
import java.util.List;

public final class ListSerializer
implements ObjectSerializer {
    public static final ListSerializer instance = new ListSerializer();

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void write(JSONSerializer var1_1, Object var2_2, Object var3_6, Type var4_7, int var5_8) throws IOException {
        block28: {
            var7_9 = var1_1.out.isEnabled(SerializerFeature.WriteClassName);
            var12_10 = var1_1.out;
            var11_11 = null;
            var10_12 = var11_11;
            if (var7_9) {
                var10_12 = var11_11;
                if (var4_7 instanceof ParameterizedType) {
                    var10_12 = ((ParameterizedType)var4_7).getActualTypeArguments()[0];
                }
            }
            if (var2_2 == null) {
                var12_10.writeNull(SerializerFeature.WriteNullListAsEmpty);
                return;
            }
            var14_13 = (List)var2_2;
            if (var14_13.size() == 0) {
                var12_10.append("[]");
                return;
            }
            var13_14 = var1_1.context;
            var1_1.setContext(var13_14, var2_2, var3_6, 0);
            try {
                if (!var12_10.isEnabled(SerializerFeature.PrettyFormat)) {
                    var12_10.append('[');
                    var5_8 = 0;
                    var6_17 = var14_13.size();
                    var4_7 = null;
                    break block28;
                }
                var12_10.append('[');
                var1_1.incrementIndent();
                var5_8 = 0;
                var14_13 = var14_13.iterator();
                while (true) lbl-1000:
                // 2 sources

                {
                    if (!var14_13.hasNext()) break block29;
                    var15_15 = var14_13.next();
                    if (var5_8 != 0) {
                        var12_10.append(',');
                    }
                    var1_1.println();
                    if (var15_15 == null) break block30;
                    if (var1_1.containsReference(var15_15)) {
                        var1_1.writeReference(var15_15);
                        break block31;
                    }
                    var11_11 = var4_7 = var1_1.getObjectWriter(var15_15.getClass());
                    break;
                }
            }
            catch (Throwable var2_3) lbl-1000:
            // 2 sources

            {
                while (true) {
                    var1_1.context = var13_14;
                    throw var2_4;
                }
            }
            {
                block31: {
                    block29: {
                        block30: {
                            var1_1.context = new SerialContext(var13_14, var2_2, var3_6, 0, 0);
                            var11_11 = var4_7;
                            var4_7.write(var1_1, var15_15, var5_8, var10_12, 0);
                            break block31;
                        }
                        var1_1.out.writeNull();
                    }
                    var1_1.decrementIdent();
                    var1_1.println();
                    var12_10.append(']');
                    var1_1.context = var13_14;
                    return;
                }
                ++var5_8;
                ** while (true)
            }
        }
        while (true) lbl-1000:
        // 2 sources

        {
            if (var5_8 >= var6_17) break block34;
            var11_11 = var4_7;
            var15_16 = var14_13.get(var5_8);
            if (var5_8 != 0) {
                var11_11 = var4_7;
                var12_10.append(',');
            }
            if (var15_16 == null) {
                var11_11 = var4_7;
                var12_10.append("null");
                break block32;
            }
            var11_11 = var4_7;
            var11_11 = var15_16.getClass();
            if (var11_11 != Integer.class) break block33;
            var11_11 = var4_7;
            break;
        }
        catch (Throwable var2_5) {
            ** continue;
        }
        {
            block32: {
                block34: {
                    block33: {
                        var12_10.writeInt((Integer)var15_16);
                        break block32;
                    }
                    if (var11_11 != Long.class) ** GOTO lbl106
                    var11_11 = var4_7;
                    var8_18 = (Long)var15_16;
                    if (var7_9) {
                        var11_11 = var4_7;
                        var12_10.writeLong(var8_18);
                        var11_11 = var4_7;
                        var12_10.write(76);
                        break block32;
                    } else {
                        var11_11 = var4_7;
                        var12_10.writeLong(var8_18);
                    }
                    break block32;
lbl106:
                    // 1 sources

                    var11_11 = var4_7;
                    if (!var12_10.disableCircularReferenceDetect) {
                        var11_11 = var4_7;
                        var1_1.context = new SerialContext(var13_14, var2_2, var3_6, 0, 0);
                    }
                    var11_11 = var4_7;
                    if (var1_1.containsReference(var15_16)) {
                        var11_11 = var4_7;
                        var1_1.writeReference(var15_16);
                        break block32;
                    }
                    var11_11 = var4_7;
                    var4_7 = var1_1.getObjectWriter(var15_16.getClass());
                    {
                        var4_7.write(var1_1, var15_16, var5_8, var10_12, 0);
                        break block32;
                    }
                }
                var11_11 = var4_7;
                var12_10.append(']');
                var1_1.context = var13_14;
                return;
            }
            ++var5_8;
            ** while (true)
        }
    }
}

