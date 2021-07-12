/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

public class ReferenceCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static final ReferenceCodec instance = new ReferenceCodec();

    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object object) {
        type = (ParameterizedType)type;
        defaultJSONParser = defaultJSONParser.parseObject(type.getActualTypeArguments()[0]);
        if ((type = type.getRawType()) == AtomicReference.class) {
            return (T)new AtomicReference<DefaultJSONParser>(defaultJSONParser);
        }
        if (type == WeakReference.class) {
            return (T)new WeakReference<DefaultJSONParser>(defaultJSONParser);
        }
        if (type == SoftReference.class) {
            return (T)new SoftReference<DefaultJSONParser>(defaultJSONParser);
        }
        throw new UnsupportedOperationException(type.toString());
    }

    @Override
    public int getFastMatchToken() {
        return 12;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(JSONSerializer jSONSerializer, Object object, Object object2, Type type, int n2) throws IOException {
        object = object instanceof AtomicReference ? ((AtomicReference)object).get() : ((Reference)object).get();
        jSONSerializer.write(object);
    }
}

