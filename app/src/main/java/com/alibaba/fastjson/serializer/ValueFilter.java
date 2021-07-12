/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.SerializeFilter;

public interface ValueFilter
extends SerializeFilter {
    public Object process(Object var1, String var2, Object var3);
}

