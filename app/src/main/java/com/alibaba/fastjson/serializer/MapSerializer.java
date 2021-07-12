/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeFilterable;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapSerializer
extends SerializeFilterable
implements ObjectSerializer {
    public static MapSerializer instance = new MapSerializer();

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(JSONSerializer var1_1, Object var2_2, Object var3_4, Type var4_5, int var5_6) throws IOException {
        block26: {
            block25: {
                var12_7 = var1_1.out;
                if (var2_2 == null) {
                    var12_7.writeNull();
                    return;
                }
                var13_8 = (Map)var2_2;
                if (var1_1.containsReference(var2_2)) {
                    var1_1.writeReference(var2_2);
                    return;
                }
                var11_9 = var1_1.context;
                var1_1.setContext(var11_9, var2_2, var3_4, 0);
                try {
                    var12_7.write(123);
                    var1_1.incrementIndent();
                    var9_10 = null;
                    var8_11 = null;
                    var5_6 = var7_12 = 1;
                    if (var12_7.isEnabled(SerializerFeature.WriteClassName)) {
                        var3_4 = var13_8.getClass();
                        if (var3_4 != JSONObject.class && var3_4 != HashMap.class && var3_4 != LinkedHashMap.class) break block25;
                    }
                    ** GOTO lbl33
                }
                catch (Throwable var2_3) {
                    var1_1.context = var11_9;
                    throw var2_3;
                }
                {
                    if (!var13_8.containsKey(JSON.DEFAULT_TYPE_KEY)) break block25;
                    var6_13 = true;
lbl27:
                    // 2 sources

                    while (true) {
                        var5_6 = var7_12;
                        if (!var6_13) {
                            var12_7.writeFieldName(JSON.DEFAULT_TYPE_KEY);
                            var12_7.writeString(var2_2.getClass().getName());
                            var5_6 = 0;
                        }
lbl33:
                        // 12 sources

                        block6: for (Object var3_4 : var13_8.entrySet()) {
                            block27: {
                                var10_15 /* !! */  = var3_4.getValue();
                                var4_5 = var3_4.getKey();
                                var3_4 = var1_1.propertyPreFilters;
                                if (var3_4 == null || var3_4.size() <= 0) break block27;
                                if (var4_5 == null || var4_5 instanceof String) {
                                    if (!var1_1.applyName(this, var2_2, (String)var4_5)) continue;
                                }
                                ** GOTO lbl-1000
                            }
                            while (true) {
                                block28: {
                                    var3_4 = var1_1.propertyFilters;
                                    if (var3_4 == null || var3_4.size() <= 0) break block28;
                                    if (var4_5 == null || var4_5 instanceof String) {
                                        if (!var1_1.apply(this, var2_2, (String)var4_5, var10_15 /* !! */ )) continue block6;
                                    }
                                    ** GOTO lbl91
                                }
                                while (true) {
                                    block29: {
                                        var15_16 = var1_1.nameFilters;
                                        var3_4 = var4_5;
                                        if (var15_16 == null) break block29;
                                        var3_4 = var4_5;
                                        if (var15_16.size() <= 0) break block29;
                                        if (var4_5 != null && !(var4_5 instanceof String)) ** GOTO lbl93
                                        var3_4 = var1_1.processKey(this, var2_2, (String)var4_5, var10_15 /* !! */ );
                                    }
lbl58:
                                    // 3 sources

                                    while (true) {
                                        block31: {
                                            block30: {
                                                var4_5 = var1_1.valueFilters;
                                                var15_16 = var1_1.contextValueFilters;
                                                if (var4_5 != null && var4_5.size() > 0) break block30;
                                                var4_5 = var10_15 /* !! */ ;
                                                if (var15_16 == null) break block31;
                                                var4_5 = var10_15 /* !! */ ;
                                                if (var15_16.size() <= 0) break block31;
                                            }
                                            if (var3_4 != null && !(var3_4 instanceof String)) ** GOTO lbl99
                                            var4_5 = var1_1.processValue(this, null, var2_2, (String)var3_4, var10_15 /* !! */ );
                                        }
lbl70:
                                        // 3 sources

                                        while (var4_5 != null || var12_7.isEnabled(SerializerFeature.WriteMapNullValue)) {
                                            if (!(var3_4 instanceof String)) ** GOTO lbl105
                                            var10_15 /* !! */  = (String)var3_4;
                                            if (var5_6 == 0) {
                                                var12_7.write(44);
                                            }
                                            if (var12_7.isEnabled(SerializerFeature.PrettyFormat)) {
                                                var1_1.println();
                                            }
                                            var12_7.writeFieldName((String)var10_15 /* !! */ , true);
lbl78:
                                            // 2 sources

                                            while (true) {
                                                var5_6 = 0;
                                                if (var4_5 == null) {
                                                    var12_7.writeNull();
                                                    continue block6;
                                                }
                                                ** GOTO lbl113
                                                break;
                                            }
                                        }
                                        continue block6;
                                        break;
                                    }
                                    break;
                                }
                                break;
                            }
                        }
                        break block26;
                        break;
                    }
                }
            }
            var6_13 = false;
            ** while (true)
lbl-1000:
            // 1 sources

            {
                block33: {
                    block32: {
                        if (!var4_5.getClass().isPrimitive() && !(var4_5 instanceof Number) || var1_1.applyName(this, var2_2, JSON.toJSONString(var4_5))) ** continue;
                        ** GOTO lbl33
lbl91:
                        // 1 sources

                        if (!var4_5.getClass().isPrimitive() && !(var4_5 instanceof Number) || var1_1.apply(this, var2_2, JSON.toJSONString(var4_5), var10_15 /* !! */ )) ** continue;
                        ** GOTO lbl33
lbl93:
                        // 1 sources

                        if (var4_5.getClass().isPrimitive()) break block32;
                        var3_4 = var4_5;
                        if (!(var4_5 instanceof Number)) ** GOTO lbl58
                    }
                    var3_4 = var1_1.processKey(this, var2_2, JSON.toJSONString(var4_5), var10_15 /* !! */ );
                    ** continue;
lbl99:
                    // 1 sources

                    if (var3_4.getClass().isPrimitive()) break block33;
                    var4_5 = var10_15 /* !! */ ;
                    if (!(var3_4 instanceof Number)) ** GOTO lbl70
                }
                var4_5 = var1_1.processValue(this, null, var2_2, JSON.toJSONString(var3_4), var10_15 /* !! */ );
                ** GOTO lbl70
lbl105:
                // 1 sources

                if (var5_6 == 0) {
                    var12_7.write(44);
                }
                if (var12_7.isEnabled(SerializerFeature.BrowserCompatible) || var12_7.isEnabled(SerializerFeature.WriteNonStringKeyAsString) || var12_7.isEnabled(SerializerFeature.BrowserSecure)) {
                    var1_1.write(JSON.toJSONString(var3_4));
                } else {
                    var1_1.write(var3_4);
                }
                var12_7.write(58);
                ** continue;
lbl113:
                // 1 sources

                var10_15 /* !! */  = var4_5.getClass();
                if (var10_15 /* !! */  != var9_10) ** GOTO lbl117
            }
            {
                var8_11.write(var1_1, var4_5, var3_4, null, 0);
                ** GOTO lbl33
lbl117:
                // 1 sources

                var9_10 = var10_15 /* !! */ ;
                var8_11 = var1_1.getObjectWriter((Class<?>)var10_15 /* !! */ );
                var8_11.write(var1_1, var4_5, var3_4, null, 0);
                ** GOTO lbl33
            }
        }
        var1_1.context = var11_9;
        var1_1.decrementIdent();
        if (var12_7.isEnabled(SerializerFeature.PrettyFormat) && var13_8.size() > 0) {
            var1_1.println();
        }
        var12_7.write(125);
    }
}

