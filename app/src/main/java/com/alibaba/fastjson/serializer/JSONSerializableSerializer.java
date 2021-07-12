/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializable;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

public class JSONSerializableSerializer
implements ObjectSerializer {
    public static JSONSerializableSerializer instance = new JSONSerializableSerializer();

    @Override
    public void write(JSONSerializer jSONSerializer, Object object, Object object2, Type type, int n2) throws IOException {
        ((JSONSerializable)object).write(jSONSerializer, object2, type, 0);
    }
}

