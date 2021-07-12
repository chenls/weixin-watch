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
import java.util.concurrent.atomic.AtomicBoolean;

public class BooleanCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static final BooleanCodec instance = new BooleanCodec();

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public <T> T deserialze(DefaultJSONParser object, Type type, Object object2) {
        object2 = ((DefaultJSONParser)object).lexer;
        if (object2.token() == 6) {
            object2.nextToken(16);
            object = Boolean.TRUE;
        } else if (object2.token() == 7) {
            object2.nextToken(16);
            object = Boolean.FALSE;
        } else if (object2.token() == 2) {
            int n2 = object2.intValue();
            object2.nextToken(16);
            object = n2 == 1 ? Boolean.TRUE : Boolean.FALSE;
        } else {
            if ((object = ((DefaultJSONParser)object).parse()) == null) {
                return null;
            }
            object = TypeUtils.castToBoolean(object);
        }
        object2 = object;
        if (type == AtomicBoolean.class) {
            object2 = new AtomicBoolean((Boolean)object);
        }
        return (T)object2;
    }

    @Override
    public int getFastMatchToken() {
        return 6;
    }

    @Override
    public void write(JSONSerializer object, Object object2, Object object3, Type type, int n2) throws IOException {
        object = ((JSONSerializer)object).out;
        if ((object2 = (Boolean)object2) == null) {
            ((SerializeWriter)object).writeNull(SerializerFeature.WriteNullBooleanAsFalse);
            return;
        }
        if (((Boolean)object2).booleanValue()) {
            ((SerializeWriter)object).write("true");
            return;
        }
        ((SerializeWriter)object).write("false");
    }
}

