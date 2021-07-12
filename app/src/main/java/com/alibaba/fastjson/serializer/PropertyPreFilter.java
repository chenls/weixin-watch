/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeFilter;

public interface PropertyPreFilter
extends SerializeFilter {
    public boolean apply(JSONSerializer var1, Object var2, String var3);
}

