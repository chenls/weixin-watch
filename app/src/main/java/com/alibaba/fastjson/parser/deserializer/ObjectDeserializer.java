/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;

public interface ObjectDeserializer {
    public <T> T deserialze(DefaultJSONParser var1, Type var2, Object var3);

    public int getFastMatchToken();
}

