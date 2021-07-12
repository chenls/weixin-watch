/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;

public class EnumDeserializer
implements ObjectDeserializer {
    private final Class<?> enumClass;
    protected final Enum[] values;

    public EnumDeserializer(Class<?> clazz) {
        this.enumClass = clazz;
        this.values = (Enum[])clazz.getEnumConstants();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <T> T deserialze(DefaultJSONParser object, Type object2, Object object3) {
        block9: {
            object2 = ((DefaultJSONParser)object).lexer;
            int n2 = object2.token();
            if (n2 == 2) {
                n2 = object2.intValue();
                object2.nextToken(16);
                if (n2 >= 0 && n2 <= this.values.length) return (T)this.values[n2];
                throw new JSONException("parse enum " + this.enumClass.getName() + " error, value : " + n2);
            }
            if (n2 == 4) {
                object = object2.stringVal();
                object2.nextToken(16);
                if (((String)object).length() == 0) return null;
                return (T)Enum.valueOf(this.enumClass, (String)object);
            }
            if (n2 != 8) break block9;
            object2.nextToken(16);
            return null;
        }
        try {
            object = ((DefaultJSONParser)object).parse();
            throw new JSONException("parse enum " + this.enumClass.getName() + " error, value : " + object);
        }
        catch (JSONException jSONException) {
            throw jSONException;
        }
        catch (Exception exception) {
            throw new JSONException(exception.getMessage(), exception);
        }
    }

    @Override
    public int getFastMatchToken() {
        return 2;
    }

    public Enum<?> valueOf(int n2) {
        return this.values[n2];
    }
}

