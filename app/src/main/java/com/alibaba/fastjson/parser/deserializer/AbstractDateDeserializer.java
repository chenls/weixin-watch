/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;

public abstract class AbstractDateDeserializer
implements ObjectDeserializer {
    protected abstract <T> T cast(DefaultJSONParser var1, Type var2, Object var3, Object var4);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type object, Object object2) {
        Object object3;
        Object object4;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 2) {
            object4 = jSONLexer.longValue();
            jSONLexer.nextToken(16);
            object3 = object;
            return this.cast(defaultJSONParser, (Type)object3, object2, object4);
        }
        if (jSONLexer.token() == 4) {
            String string2;
            String string3 = string2 = jSONLexer.stringVal();
            jSONLexer.nextToken(16);
            object4 = string3;
            object3 = object;
            if (!jSONLexer.isEnabled(Feature.AllowISO8601DateFormat)) return this.cast(defaultJSONParser, (Type)object3, object2, object4);
            object3 = new JSONScanner(string2);
            object4 = string3;
            if (((JSONScanner)object3).scanISO8601DateIfMatch()) {
                object4 = ((JSONLexerBase)object3).getCalendar().getTime();
            }
            ((JSONLexerBase)object3).close();
            object3 = object;
            return this.cast(defaultJSONParser, (Type)object3, object2, object4);
        }
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            object4 = null;
            object3 = object;
            return this.cast(defaultJSONParser, (Type)object3, object2, object4);
        }
        if (jSONLexer.token() == 12) {
            jSONLexer.nextToken();
            if (jSONLexer.token() != 4) throw new JSONException("syntax error");
            object4 = jSONLexer.stringVal();
            Object object5 = object;
            if (JSON.DEFAULT_TYPE_KEY.equals(object4)) {
                jSONLexer.nextToken();
                defaultJSONParser.accept(17);
                object4 = TypeUtils.loadClass(jSONLexer.stringVal(), defaultJSONParser.getConfig().getDefaultClassLoader());
                if (object4 != null) {
                    object = object4;
                }
                defaultJSONParser.accept(4);
                defaultJSONParser.accept(16);
                object5 = object;
            }
            jSONLexer.nextTokenWithColon(2);
            if (jSONLexer.token() != 2) throw new JSONException("syntax error : " + jSONLexer.tokenName());
            long l2 = jSONLexer.longValue();
            jSONLexer.nextToken();
            object4 = l2;
            defaultJSONParser.accept(13);
            object3 = object5;
            return this.cast(defaultJSONParser, (Type)object3, object2, object4);
        }
        if (defaultJSONParser.getResolveStatus() == 2) {
            defaultJSONParser.setResolveStatus(0);
            defaultJSONParser.accept(16);
            if (jSONLexer.token() != 4) throw new JSONException("syntax error");
            if (!"val".equals(jSONLexer.stringVal())) {
                throw new JSONException("syntax error");
            }
            jSONLexer.nextToken();
            defaultJSONParser.accept(17);
            object4 = defaultJSONParser.parse();
            defaultJSONParser.accept(13);
            object3 = object;
            return this.cast(defaultJSONParser, (Type)object3, object2, object4);
        }
        object4 = defaultJSONParser.parse();
        object3 = object;
        return this.cast(defaultJSONParser, (Type)object3, object2, object4);
    }
}

