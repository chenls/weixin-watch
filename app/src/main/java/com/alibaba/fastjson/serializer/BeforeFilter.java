/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeFilter;

public abstract class BeforeFilter
implements SerializeFilter {
    private static final Character COMMA;
    private static final ThreadLocal<Character> seperatorLocal;
    private static final ThreadLocal<JSONSerializer> serializerLocal;

    static {
        serializerLocal = new ThreadLocal();
        seperatorLocal = new ThreadLocal();
        COMMA = Character.valueOf(',');
    }

    final char writeBefore(JSONSerializer jSONSerializer, Object object, char c2) {
        serializerLocal.set(jSONSerializer);
        seperatorLocal.set(Character.valueOf(c2));
        this.writeBefore(object);
        serializerLocal.set(null);
        return seperatorLocal.get().charValue();
    }

    public abstract void writeBefore(Object var1);

    protected final void writeKeyValue(String string2, Object object) {
        JSONSerializer jSONSerializer = serializerLocal.get();
        char c2 = seperatorLocal.get().charValue();
        jSONSerializer.writeKeyValue(c2, string2, object);
        if (c2 != ',') {
            seperatorLocal.set(COMMA);
        }
    }
}

