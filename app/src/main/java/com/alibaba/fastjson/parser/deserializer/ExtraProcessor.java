/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.deserializer.ParseProcess;

public interface ExtraProcessor
extends ParseProcess {
    public void processExtra(Object var1, String var2, Object var3);
}

