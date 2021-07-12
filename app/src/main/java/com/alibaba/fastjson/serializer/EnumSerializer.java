/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

public class EnumSerializer
implements ObjectSerializer {
    public static final EnumSerializer instance = new EnumSerializer();

    @Override
    public void write(JSONSerializer jSONSerializer, Object object, Object object2, Type type, int n2) throws IOException {
        jSONSerializer.out.writeEnum((Enum)object);
    }
}

