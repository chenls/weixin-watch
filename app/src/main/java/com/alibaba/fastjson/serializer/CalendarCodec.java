/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.DateCodec;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

public class CalendarCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static final CalendarCodec instance = new CalendarCodec();

    @Override
    public <T> T deserialze(DefaultJSONParser object, Type object2, Object object3) {
        if ((object2 = DateCodec.instance.deserialze((DefaultJSONParser)object, (Type)object2, object3)) instanceof Calendar) {
            return (T)object2;
        }
        if ((object2 = (Date)object2) == null) {
            return null;
        }
        object = ((DefaultJSONParser)object).lexer;
        object = Calendar.getInstance(object.getTimeZone(), object.getLocale());
        ((Calendar)object).setTime((Date)object2);
        return (T)object;
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
        object3 = ((JSONSerializer)object).out;
        if (object2 == null) {
            ((SerializeWriter)object3).writeNull();
            return;
        }
        object2 = (Calendar)object2;
        if (!((SerializeWriter)object3).isEnabled(SerializerFeature.UseISO8601DateFormat)) {
            ((JSONSerializer)object).write(((Calendar)object2).getTime());
            return;
        }
        char c2 = ((SerializeWriter)object3).isEnabled(SerializerFeature.UseSingleQuotes) ? (char)'\'' : '\"';
        ((SerializeWriter)object3).append(c2);
        n2 = ((Calendar)object2).get(1);
        int n3 = ((Calendar)object2).get(2) + 1;
        int n4 = ((Calendar)object2).get(5);
        int n5 = ((Calendar)object2).get(11);
        int n6 = ((Calendar)object2).get(12);
        int n7 = ((Calendar)object2).get(13);
        int n8 = ((Calendar)object2).get(14);
        if (n8 != 0) {
            object = "0000-00-00T00:00:00.000".toCharArray();
            IOUtils.getChars(n8, 23, (char[])object);
            IOUtils.getChars(n7, 19, (char[])object);
            IOUtils.getChars(n6, 16, (char[])object);
            IOUtils.getChars(n5, 13, (char[])object);
            IOUtils.getChars(n4, 10, (char[])object);
            IOUtils.getChars(n3, 7, (char[])object);
            IOUtils.getChars(n2, 4, (char[])object);
        } else if (n7 == 0 && n6 == 0 && n5 == 0) {
            object = "0000-00-00".toCharArray();
            IOUtils.getChars(n4, 10, (char[])object);
            IOUtils.getChars(n3, 7, (char[])object);
            IOUtils.getChars(n2, 4, (char[])object);
        } else {
            object = "0000-00-00T00:00:00".toCharArray();
            IOUtils.getChars(n7, 19, (char[])object);
            IOUtils.getChars(n6, 16, (char[])object);
            IOUtils.getChars(n5, 13, (char[])object);
            IOUtils.getChars(n4, 10, (char[])object);
            IOUtils.getChars(n3, 7, (char[])object);
            IOUtils.getChars(n2, 4, (char[])object);
        }
        ((Writer)object3).write((char[])object);
        n2 = ((Calendar)object2).getTimeZone().getRawOffset() / 3600000;
        if (n2 == 0) {
            ((SerializeWriter)object3).append("Z");
        } else if (n2 > 0) {
            ((SerializeWriter)object3).append("+").append(String.format("%02d", n2)).append(":00");
        } else {
            ((SerializeWriter)object3).append("-").append(String.format("%02d", -n2)).append(":00");
        }
        ((SerializeWriter)object3).append(c2);
    }
}

