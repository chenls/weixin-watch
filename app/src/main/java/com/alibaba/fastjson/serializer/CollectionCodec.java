/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerialContext;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

public class CollectionCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static final CollectionCodec instance = new CollectionCodec();

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type object, Object object2) {
        if (defaultJSONParser.lexer.token() == 8) {
            defaultJSONParser.lexer.nextToken(16);
            return null;
        }
        if (object == JSONArray.class) {
            object = new JSONArray();
            defaultJSONParser.parseArray((Collection)object);
            return (T)object;
        }
        Collection collection = TypeUtils.createCollection((Type)object);
        Type type = null;
        if (object instanceof ParameterizedType) {
            object = ((ParameterizedType)object).getActualTypeArguments()[0];
        } else {
            Type type2 = type;
            if (object instanceof Class) {
                object = (Class)object;
                type2 = type;
                if (!((Class)object).getName().startsWith("java.")) {
                    object = ((Class)object).getGenericSuperclass();
                    type2 = type;
                    if (object instanceof ParameterizedType) {
                        type2 = ((ParameterizedType)object).getActualTypeArguments()[0];
                    }
                }
            }
            object = type2;
            if (type2 == null) {
                object = Object.class;
            }
        }
        defaultJSONParser.parseArray((Type)object, collection, object2);
        return (T)collection;
    }

    @Override
    public int getFastMatchToken() {
        return 14;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void write(JSONSerializer var1_1, Object var2_2, Object var3_6, Type var4_7, int var5_8) throws IOException {
        block22: {
            block18: {
                block25: {
                    block21: {
                        block20: {
                            block24: {
                                block23: {
                                    var9_9 = var1_1.out;
                                    if (var2_2 == null) {
                                        var9_9.writeNull(SerializerFeature.WriteNullListAsEmpty);
                                        return;
                                    }
                                    var7_11 = var8_10 = null;
                                    if (var9_9.isEnabled(SerializerFeature.WriteClassName)) {
                                        var7_11 = var8_10;
                                        if (var4_7 instanceof ParameterizedType) {
                                            var7_11 = ((ParameterizedType)var4_7).getActualTypeArguments()[0];
                                        }
                                    }
                                    var8_10 = (Collection)var2_2;
                                    var4_7 = var1_1.context;
                                    var1_1.setContext((SerialContext)var4_7, var2_2, var3_6, 0);
                                    if (!var9_9.isEnabled(SerializerFeature.WriteClassName)) break block23;
                                    if (HashSet.class != var8_10.getClass()) break block24;
                                    var9_9.append("Set");
                                }
lbl18:
                                // 3 sources

                                while (true) {
                                    var9_9.append('[');
                                    var2_2 = var8_10.iterator();
                                    var5_8 = 0;
lbl24:
                                    // 5 sources

                                    while (true) {
                                        block19: {
                                            if (!var2_2.hasNext()) break block18;
                                            var3_6 = var2_2.next();
                                            var6_12 = var5_8 + 1;
                                            if (var5_8 == 0) break block19;
                                            var9_9.append(',');
                                        }
                                        if (var3_6 != null) break block20;
                                        var9_9.writeNull();
                                        var5_8 = var6_12;
                                        break;
                                    }
                                    break;
                                }
                            }
                            if (TreeSet.class != var8_10.getClass()) ** GOTO lbl18
                            var9_9.append("TreeSet");
                            ** while (true)
                        }
                        try {
                            var8_10 = var3_6.getClass();
                            if (var8_10 != Integer.class) break block21;
                        }
                        catch (Throwable var2_3) lbl-1000:
                        // 2 sources

                        {
                            while (true) {
                                var1_1.context = var4_7;
                                throw var2_4;
                            }
                        }
                        var9_9.writeInt((Integer)var3_6);
                        var5_8 = var6_12;
                        ** GOTO lbl24
                    }
                    if (var8_10 != Long.class) break block25;
                    var9_9.writeLong((Long)var3_6);
                    if (!var9_9.isEnabled(SerializerFeature.WriteClassName)) break block22;
                    var9_9.write(76);
                    var5_8 = var6_12;
                    ** GOTO lbl24
                }
                var1_1.getObjectWriter((Class<?>)var8_10).write(var1_1, var3_6, var6_12 - 1, (Type)var7_11, 0);
                var5_8 = var6_12;
                ** GOTO lbl24
            }
            try {
                var9_9.append(']');
                var1_1.context = var4_7;
                return;
            }
            catch (Throwable var2_5) {
                ** continue;
            }
        }
        var5_8 = var6_12;
        ** while (true)
    }
}

