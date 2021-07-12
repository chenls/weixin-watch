/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class IntegerCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static IntegerCodec instance = new IntegerCodec();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <T> T deserialze(DefaultJSONParser object, Type type, Object object2) {
        block7: {
            JSONLexer jSONLexer;
            block6: {
                int n2;
                jSONLexer = ((DefaultJSONParser)object).lexer;
                if (jSONLexer.token() == 8) {
                    jSONLexer.nextToken(16);
                    object2 = null;
                    return (T)object2;
                }
                if (jSONLexer.token() != 2) break block6;
                try {
                    n2 = jSONLexer.intValue();
                }
                catch (NumberFormatException numberFormatException) {
                    throw new JSONException("int value overflow, field : " + object2, numberFormatException);
                }
                jSONLexer.nextToken(16);
                object = n2;
                break block7;
            }
            if (jSONLexer.token() == 3) {
                object = jSONLexer.decimalValue();
                jSONLexer.nextToken(16);
                object = ((BigDecimal)object).intValue();
            } else {
                object = TypeUtils.castToInt(((DefaultJSONParser)object).parse());
            }
        }
        object2 = object;
        if (type != AtomicInteger.class) return (T)object2;
        return (T)new AtomicInteger((Integer)object);
    }

    @Override
    public int getFastMatchToken() {
        return 2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(JSONSerializer object, Object clazz, Object object2, Type type, int n2) throws IOException {
        object = ((JSONSerializer)object).out;
        object2 = (Number)((Object)clazz);
        if (object2 == null) {
            ((SerializeWriter)object).writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        } else {
            if (clazz instanceof Long) {
                ((SerializeWriter)object).writeLong(((Number)object2).longValue());
            } else {
                ((SerializeWriter)object).writeInt(((Number)object2).intValue());
            }
            if (!((SerializeWriter)object).isEnabled(SerializerFeature.WriteClassName)) return;
            clazz = object2.getClass();
            if (clazz == Byte.class) {
                ((SerializeWriter)object).write(66);
                return;
            }
            if (clazz != Short.class) return;
            ((SerializeWriter)object).write(83);
            return;
        }
    }
}

