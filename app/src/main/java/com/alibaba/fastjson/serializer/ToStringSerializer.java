/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class ToStringSerializer
implements ObjectSerializer {
    public static final ToStringSerializer instance = new ToStringSerializer();

    @Override
    public void write(JSONSerializer object, Object object2, Object object3, Type type, int n2) throws IOException {
        object = ((JSONSerializer)object).out;
        if (object2 == null) {
            ((SerializeWriter)object).writeNull();
            return;
        }
        ((SerializeWriter)object).writeString(object2.toString());
    }
}

