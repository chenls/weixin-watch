/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.NameFilter;

public class PascalNameFilter
implements NameFilter {
    @Override
    public String process(Object object, String string2, Object object2) {
        if (string2 == null || string2.length() == 0) {
            return string2;
        }
        object = string2.toCharArray();
        object[0] = Character.toUpperCase((char)object[0]);
        return new String((char[])object);
    }
}

