/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

public class JavaObjectDeserializer
implements ObjectDeserializer {
    public static final JavaObjectDeserializer instance = new JavaObjectDeserializer();

    @Override
    public <T> T deserialze(DefaultJSONParser objectArray, Type object, Object arrayList) {
        if (object instanceof GenericArrayType) {
            arrayList = ((GenericArrayType)object).getGenericComponentType();
            object = arrayList;
            if (arrayList instanceof TypeVariable) {
                object = ((TypeVariable)((Object)arrayList)).getBounds()[0];
            }
            arrayList = new ArrayList();
            objectArray.parseArray((Type)object, arrayList);
            if (object instanceof Class) {
                objectArray = (Object[])Array.newInstance((Class)object, arrayList.size());
                arrayList.toArray(objectArray);
                return (T)objectArray;
            }
            return (T)arrayList.toArray();
        }
        return (T)objectArray.parse(arrayList);
    }

    @Override
    public int getFastMatchToken() {
        return 12;
    }
}

