/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

public class OptionalCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static OptionalCodec instance = new OptionalCodec();

    @Override
    public <T> T deserialze(DefaultJSONParser object, Type type, Object object2) {
        if (type == OptionalInt.class) {
            if ((object = TypeUtils.castToInt(((DefaultJSONParser)object).parseObject(Integer.class))) == null) {
                return (T)OptionalInt.empty();
            }
            return (T)OptionalInt.of((Integer)object);
        }
        if (type == OptionalLong.class) {
            if ((object = TypeUtils.castToLong(((DefaultJSONParser)object).parseObject(Long.class))) == null) {
                return (T)OptionalLong.empty();
            }
            return (T)OptionalLong.of((Long)object);
        }
        if (type == OptionalDouble.class) {
            if ((object = TypeUtils.castToDouble(((DefaultJSONParser)object).parseObject(Double.class))) == null) {
                return (T)OptionalDouble.empty();
            }
            return (T)OptionalDouble.of((Double)object);
        }
        if ((object = ((DefaultJSONParser)object).parseObject(TypeUtils.unwrapOptional(type))) == null) {
            return (T)Optional.empty();
        }
        return (T)Optional.of(object);
    }

    @Override
    public int getFastMatchToken() {
        return 12;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(JSONSerializer jSONSerializer, Object object, Object object2, Type type, int n2) throws IOException {
        if (object == null) {
            jSONSerializer.writeNull();
            return;
        }
        if (object instanceof Optional) {
            object = ((Optional)(object = (Optional)object)).isPresent() ? ((Optional)object).get() : null;
            jSONSerializer.write(object);
            return;
        }
        if (object instanceof OptionalDouble) {
            if (((OptionalDouble)(object = (OptionalDouble)object)).isPresent()) {
                jSONSerializer.write(((OptionalDouble)object).getAsDouble());
                return;
            }
            jSONSerializer.writeNull();
            return;
        }
        if (object instanceof OptionalInt) {
            if (((OptionalInt)(object = (OptionalInt)object)).isPresent()) {
                n2 = ((OptionalInt)object).getAsInt();
                jSONSerializer.out.writeInt(n2);
                return;
            }
            jSONSerializer.writeNull();
            return;
        }
        if (!(object instanceof OptionalLong)) {
            throw new JSONException("not support optional : " + object.getClass());
        }
        if (((OptionalLong)(object = (OptionalLong)object)).isPresent()) {
            long l2 = ((OptionalLong)object).getAsLong();
            jSONSerializer.out.writeLong(l2);
            return;
        }
        jSONSerializer.writeNull();
    }
}

