/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;

public class NumberDeserializer
implements ObjectDeserializer {
    public static final NumberDeserializer instance = new NumberDeserializer();

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public <T> T deserialze(DefaultJSONParser object, Type type, Object object2) {
        JSONLexer jSONLexer = ((DefaultJSONParser)object).lexer;
        if (jSONLexer.token() == 2) {
            if (type == Double.TYPE || type == Double.class) {
                object = jSONLexer.numberString();
                jSONLexer.nextToken(16);
                object = Double.parseDouble((String)object);
                return (T)object;
            } else {
                long l2 = jSONLexer.longValue();
                jSONLexer.nextToken(16);
                if (type == Short.TYPE || type == Short.class) {
                    return (T)Short.valueOf((short)l2);
                }
                if (type == Byte.TYPE || type == Byte.class) {
                    return (T)Byte.valueOf((byte)l2);
                }
                if (l2 < Integer.MIN_VALUE || l2 > Integer.MAX_VALUE) return (T)Long.valueOf(l2);
                return (T)Integer.valueOf((int)l2);
            }
        }
        if (jSONLexer.token() == 3) {
            if (type == Double.TYPE || type == Double.class) {
                object = jSONLexer.numberString();
                jSONLexer.nextToken(16);
                return (T)Double.valueOf(Double.parseDouble((String)object));
            }
            object2 = jSONLexer.decimalValue();
            jSONLexer.nextToken(16);
            if (type == Short.TYPE || type == Short.class) {
                return (T)Short.valueOf(((Number)object2).shortValue());
            }
            if (type == Byte.TYPE) return (T)Byte.valueOf(((Number)object2).byteValue());
            object = object2;
            if (type != Byte.class) return (T)object;
            return (T)Byte.valueOf(((Number)object2).byteValue());
        }
        if ((object = ((DefaultJSONParser)object).parse()) == null) {
            return null;
        }
        if (type == Double.TYPE || type == Double.class) {
            return (T)TypeUtils.castToDouble(object);
        }
        if (type == Short.TYPE || type == Short.class) {
            return (T)TypeUtils.castToShort(object);
        }
        if (type == Byte.TYPE || type == Byte.class) return (T)TypeUtils.castToByte(object);
        return (T)TypeUtils.castToBigDecimal(object);
    }

    @Override
    public int getFastMatchToken() {
        return 2;
    }
}

