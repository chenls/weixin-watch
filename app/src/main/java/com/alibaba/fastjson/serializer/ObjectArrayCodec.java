/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;

public class ObjectArrayCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static final ObjectArrayCodec instance = new ObjectArrayCodec();

    /*
     * Enabled aggressive block sorting
     */
    private <T> T toObjectArray(DefaultJSONParser defaultJSONParser, Class<?> clazz, JSONArray jSONArray) {
        if (jSONArray == null) {
            return null;
        }
        int n2 = jSONArray.size();
        Object object = Array.newInstance(clazz, n2);
        int n3 = 0;
        while (true) {
            Object object2;
            if (n3 >= n2) {
                jSONArray.setRelatedArray(object);
                jSONArray.setComponentType(clazz);
                return (T)object;
            }
            Object object3 = jSONArray.get(n3);
            if (object3 == jSONArray) {
                Array.set(object, n3, object);
            } else if (clazz.isArray()) {
                object2 = clazz.isInstance(object3) ? object3 : this.toObjectArray(defaultJSONParser, clazz, (JSONArray)object3);
                Array.set(object, n3, object2);
            } else {
                Object object4 = null;
                object2 = object4;
                if (object3 instanceof JSONArray) {
                    boolean bl2 = false;
                    JSONArray jSONArray2 = (JSONArray)object3;
                    int n4 = jSONArray2.size();
                    for (int i2 = 0; i2 < n4; ++i2) {
                        if (jSONArray2.get(i2) != jSONArray) continue;
                        jSONArray2.set(n3, object);
                        bl2 = true;
                    }
                    object2 = object4;
                    if (bl2) {
                        object2 = jSONArray2.toArray();
                    }
                }
                object4 = object2;
                if (object2 == null) {
                    object4 = TypeUtils.cast(object3, clazz, defaultJSONParser.getConfig());
                }
                Array.set(object, n3, object4);
            }
            ++n3;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public <T> T deserialze(DefaultJSONParser object, Type type, Object object2) {
        Object object3;
        block9: {
            block10: {
                object3 = ((DefaultJSONParser)object).lexer;
                if (object3.token() == 8) {
                    object3.nextToken(16);
                    return null;
                }
                if (object3.token() == 4) {
                    object = object3.bytesValue();
                    object3.nextToken(16);
                    return (T)object;
                }
                if (!(type instanceof GenericArrayType)) break block10;
                if ((type = ((GenericArrayType)type).getGenericComponentType()) instanceof TypeVariable) {
                    TypeVariable typeVariable = (TypeVariable)type;
                    type = object.getContext().type;
                    if (type instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType)type;
                        TypeVariable<Class<T>>[] typeVariableArray = parameterizedType.getRawType();
                        object3 = null;
                        type = null;
                        if (typeVariableArray instanceof Class) {
                            typeVariableArray = ((Class)typeVariableArray).getTypeParameters();
                            int n2 = 0;
                            while (true) {
                                object3 = type;
                                if (n2 >= typeVariableArray.length) break;
                                if (typeVariableArray[n2].getName().equals(typeVariable.getName())) {
                                    type = parameterizedType.getActualTypeArguments()[n2];
                                }
                                ++n2;
                            }
                        }
                        type = object3 instanceof Class ? (Class)object3 : Object.class;
                        break block9;
                    } else {
                        type = TypeUtils.getClass(typeVariable.getBounds()[0]);
                    }
                    break block9;
                } else {
                    type = TypeUtils.getClass(type);
                }
                break block9;
            }
            type = ((Class)type).getComponentType();
        }
        object3 = new JSONArray();
        ((DefaultJSONParser)object).parseArray(type, (Collection)object3, object2);
        return this.toObjectArray((DefaultJSONParser)object, (Class<?>)type, (JSONArray)object3);
    }

    @Override
    public int getFastMatchToken() {
        return 14;
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
        var9_7 = var1_1.out;
        var10_8 = (Object[])var2_2;
        if (var2_2 == null) {
            var9_7.writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        var6_9 = var10_8.length;
        var7_10 = var6_9 - 1;
        if (var7_10 == -1) {
            var9_7.append("[]");
            return;
        }
        var8_11 = var1_1.context;
        var1_1.setContext(var8_11, var2_2, var3_4, 0);
        var2_2 = null;
        var3_4 = null;
        try {
            var9_7.append('[');
            if (var9_7.isEnabled(SerializerFeature.PrettyFormat)) {
                var1_1.incrementIndent();
                var1_1.println();
                var5_6 = 0;
                while (true) {
                    if (var5_6 >= var6_9) {
                        var1_1.decrementIdent();
                        var1_1.println();
                        var9_7.write(93);
                        var1_1.context = var8_11;
                        return;
                    }
                    if (var5_6 != 0) {
                        var9_7.write(44);
                        var1_1.println();
                    }
                    var1_1.write(var10_8[var5_6]);
                    ++var5_6;
                }
            }
            var5_6 = 0;
        }
        catch (Throwable var2_3) {
            var1_1.context = var8_11;
            throw var2_3;
        }
        while (true) {
            block18: {
                block19: {
                    if (var5_6 >= var7_10) break block19;
                    var11_12 = var10_8[var5_6];
                    if (var11_12 != null) ** GOTO lbl48
                    {
                        var9_7.append("null,");
                        break block18;
lbl48:
                        // 1 sources

                        if (!var1_1.containsReference(var11_12)) ** GOTO lbl-1000
                        var1_1.writeReference(var11_12);
lbl50:
                        // 3 sources

                        while (true) {
                            var9_7.append(',');
                            break block18;
                            break;
                        }
                    }
lbl-1000:
                    // 1 sources

                    {
                        var4_5 = var11_12.getClass();
                        if (var4_5 != var2_2) ** GOTO lbl58
                    }
                    {
                        var3_4.write(var1_1, var11_12, null, null, 0);
                        ** GOTO lbl50
lbl58:
                        // 1 sources

                        var2_2 = var4_5;
                        var3_4 = var1_1.getObjectWriter(var4_5);
                        var3_4.write(var1_1, var11_12, null, null, 0);
                        ** continue;
                    }
                }
                var2_2 = var10_8[var7_10];
                if (var2_2 != null) ** GOTO lbl-1000
                {
                    var9_7.append("null]");
                }
lbl67:
                // 2 sources

                while (true) {
                    var1_1.context = var8_11;
                    return;
                }
lbl-1000:
                // 1 sources

                {
                    if (var1_1.containsReference(var2_2)) {
                        var1_1.writeReference(var2_2);
                    } else {
                        var1_1.writeWithFieldName(var2_2, var7_10);
                    }
                    var9_7.append(']');
                    ** continue;
                }
            }
            ++var5_6;
        }
    }
}

