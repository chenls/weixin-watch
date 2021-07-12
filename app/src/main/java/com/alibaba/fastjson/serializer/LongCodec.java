/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicLong;

public class LongCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static LongCodec instance = new LongCodec();

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public <T> T deserialze(DefaultJSONParser object, Type type, Object object2) {
        object2 = ((DefaultJSONParser)object).lexer;
        if (object2.token() == 2) {
            long l2 = object2.longValue();
            object2.nextToken(16);
            object = l2;
        } else {
            if ((object = ((DefaultJSONParser)object).parse()) == null) {
                return null;
            }
            object = TypeUtils.castToLong(object);
        }
        object2 = object;
        if (type == AtomicLong.class) {
            object2 = new AtomicLong((Long)object);
        }
        return (T)object2;
    }

    @Override
    public int getFastMatchToken() {
        return 2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(JSONSerializer object, Object object2, Object object3, Type type, int n2) throws IOException {
        object = ((JSONSerializer)object).out;
        if (object2 == null) {
            ((SerializeWriter)object).writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        } else {
            long l2 = (Long)object2;
            ((SerializeWriter)object).writeLong(l2);
            if (!((SerializeWriter)object).isEnabled(SerializerFeature.WriteClassName) || l2 > Integer.MAX_VALUE || l2 < Integer.MIN_VALUE || type == Long.class) return;
            ((SerializeWriter)object).write(76);
            return;
        }
    }
}

