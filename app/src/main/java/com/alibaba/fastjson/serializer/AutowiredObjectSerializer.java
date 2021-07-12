/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.lang.reflect.Type;
import java.util.Set;

public interface AutowiredObjectSerializer
extends ObjectSerializer {
    public Set<Type> getAutowiredFor();
}

