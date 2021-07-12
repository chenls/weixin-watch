/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;

public class CharacterCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static final CharacterCodec instance = new CharacterCodec();

    @Override
    public <T> T deserialze(DefaultJSONParser object, Type type, Object object2) {
        if ((object = ((DefaultJSONParser)object).parse()) == null) {
            return null;
        }
        return (T)TypeUtils.castToChar(object);
    }

    @Override
    public int getFastMatchToken() {
        return 4;
    }

    @Override
    public void write(JSONSerializer object, Object object2, Object object3, Type type, int n2) throws IOException {
        object = ((JSONSerializer)object).out;
        if ((object2 = (Character)object2) == null) {
            ((SerializeWriter)object).writeString("");
            return;
        }
        if (((Character)object2).charValue() == '\u0000') {
            ((SerializeWriter)object).writeString("\u0000");
            return;
        }
        ((SerializeWriter)object).writeString(((Character)object2).toString());
    }
}

