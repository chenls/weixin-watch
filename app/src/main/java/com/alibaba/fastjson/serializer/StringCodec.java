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
import java.io.IOException;
import java.lang.reflect.Type;

public class StringCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static StringCodec instance = new StringCodec();

    public static <T> T deserialze(DefaultJSONParser object) {
        JSONLexer jSONLexer = ((DefaultJSONParser)object).getLexer();
        if (jSONLexer.token() == 4) {
            object = jSONLexer.stringVal();
            jSONLexer.nextToken(16);
            return (T)object;
        }
        if (jSONLexer.token() == 2) {
            object = jSONLexer.numberString();
            jSONLexer.nextToken(16);
            return (T)object;
        }
        if ((object = ((DefaultJSONParser)object).parse()) == null) {
            return null;
        }
        return (T)object.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public <T> T deserialze(DefaultJSONParser object, Type object2, Object object3) {
        object3 = null;
        if (object2 == StringBuffer.class) {
            object2 = ((DefaultJSONParser)object).lexer;
            if (object2.token() == 4) {
                object = object2.stringVal();
                object2.nextToken(16);
                object = new StringBuffer((String)object);
                return (T)object;
            } else {
                object2 = ((DefaultJSONParser)object).parse();
                object = object3;
                if (object2 == null) return (T)object;
                return (T)new StringBuffer(object2.toString());
            }
        }
        if (object2 != StringBuilder.class) {
            return StringCodec.deserialze((DefaultJSONParser)object);
        }
        object2 = ((DefaultJSONParser)object).lexer;
        if (object2.token() == 4) {
            object = object2.stringVal();
            object2.nextToken(16);
            return (T)new StringBuilder((String)object);
        }
        object2 = ((DefaultJSONParser)object).parse();
        object = object3;
        if (object2 == null) return (T)object;
        return (T)new StringBuilder(object2.toString());
    }

    @Override
    public int getFastMatchToken() {
        return 4;
    }

    @Override
    public void write(JSONSerializer jSONSerializer, Object object, Object object2, Type type, int n2) throws IOException {
        this.write(jSONSerializer, (String)object);
    }

    public void write(JSONSerializer object, String string2) {
        object = ((JSONSerializer)object).out;
        if (string2 == null) {
            ((SerializeWriter)object).writeNull(SerializerFeature.WriteNullStringAsEmpty);
            return;
        }
        ((SerializeWriter)object).writeString(string2);
    }
}

