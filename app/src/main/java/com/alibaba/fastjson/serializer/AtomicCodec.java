/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public class AtomicCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static final AtomicCodec instance = new AtomicCodec();

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public <T> T deserialze(DefaultJSONParser object, Type object2, Object object3) {
        if (((DefaultJSONParser)object).lexer.token() == 8) {
            ((DefaultJSONParser)object).lexer.nextToken(16);
            object = null;
            return (T)object;
        } else {
            object3 = new JSONArray();
            ((DefaultJSONParser)object).parseArray((Collection)object3);
            if (object2 == AtomicIntegerArray.class) {
                object2 = new AtomicIntegerArray(((JSONArray)object3).size());
                int n2 = 0;
                while (true) {
                    object = object2;
                    if (n2 >= ((JSONArray)object3).size()) return (T)object;
                    ((AtomicIntegerArray)object2).set(n2, ((JSONArray)object3).getInteger(n2));
                    ++n2;
                }
            } else {
                object2 = new AtomicLongArray(((JSONArray)object3).size());
                int n3 = 0;
                while (true) {
                    object = object2;
                    if (n3 >= ((JSONArray)object3).size()) return (T)object;
                    ((AtomicLongArray)object2).set(n3, ((JSONArray)object3).getLong(n3));
                    ++n3;
                }
            }
        }
    }

    @Override
    public int getFastMatchToken() {
        return 14;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(JSONSerializer object, Object object2, Object object3, Type type, int n2) throws IOException {
        object3 = ((JSONSerializer)object).out;
        if (object2 instanceof AtomicInteger) {
            ((SerializeWriter)object3).writeInt(((AtomicInteger)object2).get());
            return;
        }
        if (object2 instanceof AtomicLong) {
            ((SerializeWriter)object3).writeLong(((AtomicLong)object2).get());
            return;
        }
        if (object2 instanceof AtomicBoolean) {
            object = ((AtomicBoolean)object2).get() ? "true" : "false";
            ((SerializeWriter)object3).append((CharSequence)object);
            return;
        }
        if (object2 == null) {
            ((SerializeWriter)object3).writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        if (object2 instanceof AtomicIntegerArray) {
            object = (AtomicIntegerArray)object2;
            int n3 = ((AtomicIntegerArray)object).length();
            ((SerializeWriter)object3).write(91);
            n2 = 0;
            while (true) {
                if (n2 >= n3) {
                    ((SerializeWriter)object3).write(93);
                    return;
                }
                int n4 = ((AtomicIntegerArray)object).get(n2);
                if (n2 != 0) {
                    ((SerializeWriter)object3).write(44);
                }
                ((SerializeWriter)object3).writeInt(n4);
                ++n2;
            }
        }
        object = (AtomicLongArray)object2;
        int n5 = ((AtomicLongArray)object).length();
        ((SerializeWriter)object3).write(91);
        n2 = 0;
        while (true) {
            if (n2 >= n5) {
                ((SerializeWriter)object3).write(93);
                return;
            }
            long l2 = ((AtomicLongArray)object).get(n2);
            if (n2 != 0) {
                ((SerializeWriter)object3).write(44);
            }
            ((SerializeWriter)object3).writeLong(l2);
            ++n2;
        }
    }
}

