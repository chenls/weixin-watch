/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.lang.reflect.Type;

public class AppendableSerializer
implements ObjectSerializer {
    public static final AppendableSerializer instance = new AppendableSerializer();

    @Override
    public void write(JSONSerializer jSONSerializer, Object object, Object object2, Type type, int n2) throws IOException {
        if (object == null) {
            jSONSerializer.out.writeNull(SerializerFeature.WriteNullStringAsEmpty);
            return;
        }
        jSONSerializer.write(object.toString());
    }
}

