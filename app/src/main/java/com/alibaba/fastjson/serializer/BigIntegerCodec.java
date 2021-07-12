/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

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
import java.math.BigInteger;

public class BigIntegerCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static final BigIntegerCodec instance = new BigIntegerCodec();

    public static <T> T deserialze(DefaultJSONParser object) {
        JSONLexer jSONLexer = ((DefaultJSONParser)object).lexer;
        if (jSONLexer.token() == 2) {
            object = jSONLexer.numberString();
            jSONLexer.nextToken(16);
            return (T)new BigInteger((String)object);
        }
        if ((object = ((DefaultJSONParser)object).parse()) == null) {
            return null;
        }
        return (T)TypeUtils.castToBigInteger(object);
    }

    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object object) {
        return BigIntegerCodec.deserialze(defaultJSONParser);
    }

    @Override
    public int getFastMatchToken() {
        return 2;
    }

    @Override
    public void write(JSONSerializer object, Object object2, Object object3, Type type, int n2) throws IOException {
        object = ((JSONSerializer)object).out;
        if (object2 == null) {
            ((SerializeWriter)object).writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        }
        ((SerializeWriter)object).write(((BigInteger)object2).toString());
    }
}

