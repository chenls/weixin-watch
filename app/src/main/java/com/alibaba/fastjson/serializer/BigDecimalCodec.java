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
import java.math.BigDecimal;

public class BigDecimalCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static final BigDecimalCodec instance = new BigDecimalCodec();

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static <T> T deserialze(DefaultJSONParser object) {
        JSONLexer jSONLexer = ((DefaultJSONParser)object).lexer;
        if (jSONLexer.token() == 2) {
            object = jSONLexer.decimalValue();
            jSONLexer.nextToken(16);
            return (T)object;
        }
        if (jSONLexer.token() == 3) {
            object = jSONLexer.decimalValue();
            jSONLexer.nextToken(16);
            return (T)object;
        }
        if ((object = ((DefaultJSONParser)object).parse()) == null) {
            object = null;
            return (T)object;
        }
        object = TypeUtils.castToBigDecimal(object);
        return (T)object;
    }

    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object object) {
        return BigDecimalCodec.deserialze(defaultJSONParser);
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
            object2 = (BigDecimal)object2;
            ((SerializeWriter)object).write(((BigDecimal)object2).toString());
            if (!((SerializeWriter)object).isEnabled(SerializerFeature.WriteClassName) || type == BigDecimal.class || ((BigDecimal)object2).scale() != 0) return;
            ((SerializeWriter)object).write(46);
            return;
        }
    }
}

