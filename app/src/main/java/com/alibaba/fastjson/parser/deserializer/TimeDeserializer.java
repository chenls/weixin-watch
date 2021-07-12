/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;
import java.sql.Time;

public class TimeDeserializer
implements ObjectDeserializer {
    public static final TimeDeserializer instance = new TimeDeserializer();

    /*
     * Unable to fully structure code
     */
    @Override
    public <T> T deserialze(DefaultJSONParser var1_1, Type var2_2, Object var3_3) {
        block14: {
            var2_2 = var1_1.lexer;
            if (var2_2.token() == 16) {
                var2_2.nextToken(4);
                if (var2_2.token() != 4) {
                    throw new JSONException("syntax error");
                }
                var2_2.nextTokenWithColon(2);
                if (var2_2.token() != 2) {
                    throw new JSONException("syntax error");
                }
                var7_4 = var2_2.longValue();
                var2_2.nextToken(13);
                if (var2_2.token() != 13) {
                    throw new JSONException("syntax error");
                }
                var2_2.nextToken(16);
                var1_1 = new Time(var7_4);
lbl15:
                // 2 sources

                return (T)var1_1;
            }
            var2_2 = var1_1.parse();
            if (var2_2 == null) {
                return null;
            }
            var1_1 = var2_2;
            ** while (var2_2 instanceof Time)
lbl22:
            // 1 sources

            if (var2_2 instanceof Number) {
                return (T)new Time(((Number)var2_2).longValue());
            }
            if (!(var2_2 instanceof String)) break block14;
            var1_1 = (String)var2_2;
            if (var1_1.length() == 0) {
                return null;
            }
            var2_2 = new JSONScanner((String)var1_1);
            if (var2_2.scanISO8601DateIfMatch()) {
                var7_5 = var2_2.getCalendar().getTimeInMillis();
lbl31:
                // 2 sources

                while (true) {
                    var2_2.close();
                    return (T)new Time(var7_5);
                }
            }
            var6_6 = '\u0001';
            var4_7 = 0;
            while (true) {
                block16: {
                    block15: {
                        var5_8 = var6_6;
                        if (var4_7 >= var1_1.length()) break block15;
                        var5_8 = var1_1.charAt(var4_7);
                        if (var5_8 >= '0' && var5_8 <= '9') break block16;
                        var5_8 = '\u0000';
                    }
                    if (var5_8 != '\u0000') break;
                    var2_2.close();
                    return (T)Time.valueOf((String)var1_1);
                }
                ++var4_7;
            }
            var7_5 = Long.parseLong((String)var1_1);
            ** while (true)
        }
        throw new JSONException("parse error");
    }

    @Override
    public int getFastMatchToken() {
        return 2;
    }
}

