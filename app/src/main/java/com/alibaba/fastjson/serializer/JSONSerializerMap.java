/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;

@Deprecated
public class JSONSerializerMap
extends SerializeConfig {
    public final boolean put(Class<?> clazz, ObjectSerializer objectSerializer) {
        return super.put(clazz, objectSerializer);
    }
}

