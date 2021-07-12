/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.reflect.Type;

public class JSONAwareSerializer
implements ObjectSerializer {
    public static JSONAwareSerializer instance = new JSONAwareSerializer();

    @Override
    public void write(JSONSerializer jSONSerializer, Object object, Object object2, Type type, int n2) throws IOException {
        jSONSerializer.out.write(((JSONAware)object).toJSONString());
    }
}

