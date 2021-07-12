/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatSerializer
implements ObjectSerializer {
    private final String pattern;

    public SimpleDateFormatSerializer(String string2) {
        this.pattern = string2;
    }

    @Override
    public void write(JSONSerializer jSONSerializer, Object object, Object object2, Type type, int n2) throws IOException {
        if (object == null) {
            jSONSerializer.out.writeNull();
            return;
        }
        object = (Date)object;
        object2 = new SimpleDateFormat(this.pattern, jSONSerializer.locale);
        ((DateFormat)object2).setTimeZone(jSONSerializer.timeZone);
        jSONSerializer.write(((DateFormat)object2).format((Date)object));
    }
}

