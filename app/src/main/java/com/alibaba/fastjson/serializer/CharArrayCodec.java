/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;

public class CharArrayCodec
implements ObjectDeserializer {
    public static <T> T deserialze(DefaultJSONParser object) {
        JSONLexer jSONLexer = ((DefaultJSONParser)object).lexer;
        if (jSONLexer.token() == 4) {
            object = jSONLexer.stringVal();
            jSONLexer.nextToken(16);
            return (T)((String)object).toCharArray();
        }
        if (jSONLexer.token() == 2) {
            object = jSONLexer.integerValue();
            jSONLexer.nextToken(16);
            return (T)object.toString().toCharArray();
        }
        if ((object = ((DefaultJSONParser)object).parse()) == null) {
            return null;
        }
        return (T)JSON.toJSONString(object).toCharArray();
    }

    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object object) {
        return CharArrayCodec.deserialze(defaultJSONParser);
    }

    @Override
    public int getFastMatchToken() {
        return 4;
    }
}

