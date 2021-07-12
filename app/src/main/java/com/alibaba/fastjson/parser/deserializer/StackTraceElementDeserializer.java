/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;

public class StackTraceElementDeserializer
implements ObjectDeserializer {
    public static final StackTraceElementDeserializer instance = new StackTraceElementDeserializer();

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type object, Object object2) {
        int n2;
        String string2;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken();
            return null;
        }
        if (jSONLexer.token() != 12 && jSONLexer.token() != 16) {
            throw new JSONException("syntax error: " + JSONToken.name(jSONLexer.token()));
        }
        Object object3 = null;
        String string3 = null;
        Object object4 = null;
        int n3 = 0;
        while (true) {
            block21: {
                block26: {
                    block25: {
                        block24: {
                            block23: {
                                block22: {
                                    if ((object = jSONLexer.scanSymbol(defaultJSONParser.getSymbolTable())) == null) {
                                        if (jSONLexer.token() == 13) {
                                            jSONLexer.nextToken(16);
                                            string2 = string3;
                                            n2 = n3;
                                            object2 = object4;
                                            object = object3;
                                            return (T)new StackTraceElement((String)object, string2, (String)object2, n2);
                                        }
                                        if (jSONLexer.token() == 16 && jSONLexer.isEnabled(Feature.AllowArbitraryCommas)) continue;
                                    }
                                    jSONLexer.nextTokenWithColon(4);
                                    if (!"className".equals(object)) break block22;
                                    if (jSONLexer.token() == 8) {
                                        object = null;
                                        string2 = string3;
                                        n2 = n3;
                                        object2 = object4;
                                        break block21;
                                    } else {
                                        if (jSONLexer.token() != 4) throw new JSONException("syntax error");
                                        object = jSONLexer.stringVal();
                                        object2 = object4;
                                        n2 = n3;
                                        string2 = string3;
                                    }
                                    break block21;
                                }
                                if (!"methodName".equals(object)) break block23;
                                if (jSONLexer.token() == 8) {
                                    string2 = null;
                                    object = object3;
                                    object2 = object4;
                                    n2 = n3;
                                    break block21;
                                } else {
                                    if (jSONLexer.token() != 4) throw new JSONException("syntax error");
                                    string2 = jSONLexer.stringVal();
                                    object = object3;
                                    object2 = object4;
                                    n2 = n3;
                                }
                                break block21;
                            }
                            if (!"fileName".equals(object)) break block24;
                            if (jSONLexer.token() == 8) {
                                object2 = null;
                                object = object3;
                                n2 = n3;
                                string2 = string3;
                                break block21;
                            } else {
                                if (jSONLexer.token() != 4) throw new JSONException("syntax error");
                                object2 = jSONLexer.stringVal();
                                object = object3;
                                n2 = n3;
                                string2 = string3;
                            }
                            break block21;
                        }
                        if (!"lineNumber".equals(object)) break block25;
                        if (jSONLexer.token() == 8) {
                            n2 = 0;
                            object = object3;
                            object2 = object4;
                            string2 = string3;
                            break block21;
                        } else {
                            if (jSONLexer.token() != 2) throw new JSONException("syntax error");
                            n2 = jSONLexer.intValue();
                            object = object3;
                            object2 = object4;
                            string2 = string3;
                        }
                        break block21;
                    }
                    if (!"nativeMethod".equals(object)) break block26;
                    if (jSONLexer.token() == 8) {
                        jSONLexer.nextToken(16);
                        object = object3;
                        object2 = object4;
                        n2 = n3;
                        string2 = string3;
                        break block21;
                    } else if (jSONLexer.token() == 6) {
                        jSONLexer.nextToken(16);
                        object = object3;
                        object2 = object4;
                        n2 = n3;
                        string2 = string3;
                        break block21;
                    } else {
                        if (jSONLexer.token() != 7) throw new JSONException("syntax error");
                        jSONLexer.nextToken(16);
                        object = object3;
                        object2 = object4;
                        n2 = n3;
                        string2 = string3;
                    }
                    break block21;
                }
                if (object != JSON.DEFAULT_TYPE_KEY) throw new JSONException("syntax error : " + (String)object);
                if (jSONLexer.token() == 4) {
                    String string4 = jSONLexer.stringVal();
                    object = object3;
                    object2 = object4;
                    n2 = n3;
                    string2 = string3;
                    if (!string4.equals("java.lang.StackTraceElement")) {
                        throw new JSONException("syntax error : " + string4);
                    }
                } else {
                    object = object3;
                    object2 = object4;
                    n2 = n3;
                    string2 = string3;
                    if (jSONLexer.token() != 8) {
                        throw new JSONException("syntax error");
                    }
                }
            }
            object3 = object;
            object4 = object2;
            n3 = n2;
            string3 = string2;
            if (jSONLexer.token() == 13) break;
        }
        jSONLexer.nextToken(16);
        return (T)new StackTraceElement((String)object, string2, (String)object2, n2);
    }

    @Override
    public int getFastMatchToken() {
        return 12;
    }
}

