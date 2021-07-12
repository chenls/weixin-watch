/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Jdk8DateCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static final Jdk8DateCodec instance = new Jdk8DateCodec();

    @Override
    public <T> T deserialze(DefaultJSONParser object, Type type, Object object2) {
        object = ((DefaultJSONParser)object).lexer;
        if (object.token() == 4) {
            object2 = object.stringVal();
            object.nextToken();
            if (type == LocalDateTime.class) {
                return (T)LocalDateTime.parse((CharSequence)object2);
            }
            if (type == LocalDate.class) {
                return (T)LocalDate.parse((CharSequence)object2);
            }
            if (type == LocalTime.class) {
                return (T)LocalTime.parse((CharSequence)object2);
            }
            if (type == ZonedDateTime.class) {
                return (T)ZonedDateTime.parse((CharSequence)object2);
            }
            if (type == OffsetDateTime.class) {
                return (T)OffsetDateTime.parse((CharSequence)object2);
            }
            if (type == OffsetTime.class) {
                return (T)OffsetTime.parse((CharSequence)object2);
            }
            if (type == ZoneId.class) {
                return (T)ZoneId.of((String)object2);
            }
            if (type == Period.class) {
                return (T)Period.parse((CharSequence)object2);
            }
            if (type == Duration.class) {
                return (T)Duration.parse((CharSequence)object2);
            }
            if (type == Instant.class) {
                return (T)Instant.parse((CharSequence)object2);
            }
        } else {
            throw new UnsupportedOperationException();
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return 4;
    }

    @Override
    public void write(JSONSerializer object, Object object2, Object object3, Type type, int n2) throws IOException {
        object = ((JSONSerializer)object).out;
        if (object2 == null) {
            ((SerializeWriter)object).writeNull();
            return;
        }
        ((SerializeWriter)object).writeString(object2.toString());
    }
}

