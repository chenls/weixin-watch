/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

public class SqlDateDeserializer
extends AbstractDateDeserializer
implements ObjectDeserializer {
    public static final SqlDateDeserializer instance = new SqlDateDeserializer();
    public static final SqlDateDeserializer instance_timestamp = new SqlDateDeserializer(true);
    private boolean timestamp = false;

    public SqlDateDeserializer() {
    }

    public SqlDateDeserializer(boolean bl2) {
        this.timestamp = true;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    protected <T> T cast(DefaultJSONParser var1_1, Type var2_4, Object var3_5, Object var4_6) {
        var7_7 = null;
        if (this.timestamp) {
            var2_4 = this.castTimestamp((DefaultJSONParser)var1_1, (Type)var2_4, var3_5, var4_6);
lbl4:
            // 3 sources

            return (T)var2_4;
        }
        var2_4 = var7_7;
        if (var4_6 == null) ** GOTO lbl4
        if (var4_6 instanceof Date) {
            var1_1 = new java.sql.Date(((Date)var4_6).getTime());
lbl10:
            // 2 sources

            return (T)var1_1;
        }
        if (var4_6 instanceof Number) {
            var1_1 = new java.sql.Date(((Number)var4_6).longValue());
            ** continue;
        }
        if (var4_6 instanceof String) {
            var3_5 = (String)var4_6;
            var2_4 = var7_7;
            if (var3_5.length() != 0) ** break;
            ** continue;
            var2_4 = new JSONScanner((String)var3_5);
            try {
                if (var2_4.scanISO8601DateIfMatch()) {
                    var5_8 = var2_4.getCalendar().getTimeInMillis();
lbl24:
                    // 2 sources

                    return (T)new java.sql.Date(var5_8);
                }
                var1_1 = var1_1.getDateFormat();
                try {
                    var1_1 = new java.sql.Date(var1_1.parse((String)var3_5).getTime());
                }
                catch (ParseException var1_2) {
                    var5_8 = Long.parseLong((String)var3_5);
                    ** continue;
                }
                return (T)var1_1;
            }
            finally {
                var2_4.close();
            }
        }
        throw new JSONException("parse error : " + var4_6);
    }

    /*
     * Unable to fully structure code
     */
    protected <T> T castTimestamp(DefaultJSONParser var1_1, Type var2_4, Object var3_5, Object var4_6) {
        if (var4_6 == null) {
            return null;
        }
        if (var4_6 instanceof Date) {
            return (T)new Timestamp(((Date)var4_6).getTime());
        }
        if (var4_6 instanceof Number) {
            return (T)new Timestamp(((Number)var4_6).longValue());
        }
        if (var4_6 instanceof String) {
            if ((var3_5 = (String)var4_6).length() == 0) ** continue;
            var2_4 = new JSONScanner((String)var3_5);
            try {
                if (var2_4.scanISO8601DateIfMatch()) {
                    var5_7 = var2_4.getCalendar().getTimeInMillis();
lbl14:
                    // 2 sources

                    return (T)new Timestamp(var5_7);
                }
                var1_1 = var1_1.getDateFormat();
                try {
                    var1_1 = new Timestamp(var1_1.parse((String)var3_5).getTime());
                }
                catch (ParseException var1_2) {
                    var5_7 = Long.parseLong((String)var3_5);
                    ** continue;
                }
                return (T)var1_1;
            }
            finally {
                var2_4.close();
            }
        }
        throw new JSONException("parse error");
    }

    @Override
    public int getFastMatchToken() {
        return 2;
    }
}

