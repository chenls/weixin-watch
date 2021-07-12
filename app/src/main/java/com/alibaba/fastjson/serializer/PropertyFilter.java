/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.SerializeFilter;

public interface PropertyFilter
extends SerializeFilter {
    public boolean apply(Object var1, String var2, Object var3);
}

