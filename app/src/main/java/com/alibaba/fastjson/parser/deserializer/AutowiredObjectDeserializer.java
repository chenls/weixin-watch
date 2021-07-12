/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;
import java.util.Set;

public interface AutowiredObjectDeserializer
extends ObjectDeserializer {
    public Set<Type> getAutowiredFor();
}

