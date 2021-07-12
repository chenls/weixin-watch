/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

public class JSONLibDataFormatSerializer
implements ObjectSerializer {
    @Override
    public void write(JSONSerializer jSONSerializer, Object object, Object object2, Type type, int n2) throws IOException {
        if (object == null) {
            jSONSerializer.out.writeNull();
            return;
        }
        object = (Date)object;
        object2 = new JSONObject();
        ((JSONObject)object2).put("date", (Object)((Date)object).getDate());
        ((JSONObject)object2).put("day", (Object)((Date)object).getDay());
        ((JSONObject)object2).put("hours", (Object)((Date)object).getHours());
        ((JSONObject)object2).put("minutes", (Object)((Date)object).getMinutes());
        ((JSONObject)object2).put("month", (Object)((Date)object).getMonth());
        ((JSONObject)object2).put("seconds", (Object)((Date)object).getSeconds());
        ((JSONObject)object2).put("time", (Object)((Date)object).getTime());
        ((JSONObject)object2).put("timezoneOffset", (Object)((Date)object).getTimezoneOffset());
        ((JSONObject)object2).put("year", (Object)((Date)object).getYear());
        jSONSerializer.write(object2);
    }
}

