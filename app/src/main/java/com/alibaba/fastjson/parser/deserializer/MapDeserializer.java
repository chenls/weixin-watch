/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MapDeserializer
implements ObjectDeserializer {
    public static MapDeserializer instance = new MapDeserializer();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Object parseMap(DefaultJSONParser defaultJSONParser, Map<Object, Object> object, Type object2, Type type, Object object3) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() != 12 && jSONLexer.token() != 16) {
            throw new JSONException("syntax error, expect {, actual " + jSONLexer.tokenName());
        }
        ObjectDeserializer objectDeserializer = defaultJSONParser.getConfig().getDeserializer((Type)object2);
        ObjectDeserializer objectDeserializer2 = defaultJSONParser.getConfig().getDeserializer(type);
        jSONLexer.nextToken(objectDeserializer.getFastMatchToken());
        object3 = defaultJSONParser.getContext();
        try {
            while (true) {
                if (jSONLexer.token() == 13) {
                    jSONLexer.nextToken(16);
                    return object;
                }
                if (jSONLexer.token() == 4 && jSONLexer.isRef() && !jSONLexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                    object = null;
                    jSONLexer.nextTokenWithColon(4);
                    if (jSONLexer.token() != 4) throw new JSONException("illegal ref, " + JSONToken.name(jSONLexer.token()));
                    object2 = jSONLexer.stringVal();
                    if ("..".equals(object2)) {
                        object = object3.parent.object;
                    } else if ("$".equals(object2)) {
                        object = object3;
                        while (((ParseContext)object).parent != null) {
                            object = ((ParseContext)object).parent;
                        }
                        object = ((ParseContext)object).object;
                    } else {
                        defaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask((ParseContext)object3, (String)object2));
                        defaultJSONParser.setResolveStatus(1);
                    }
                    jSONLexer.nextToken(13);
                    if (jSONLexer.token() != 13) {
                        throw new JSONException("illegal ref");
                    }
                    jSONLexer.nextToken(16);
                    return object;
                }
                if (object.size() == 0 && jSONLexer.token() == 4 && JSON.DEFAULT_TYPE_KEY.equals(jSONLexer.stringVal()) && !jSONLexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                    jSONLexer.nextTokenWithColon(4);
                    jSONLexer.nextToken(16);
                    if (jSONLexer.token() == 13) {
                        jSONLexer.nextToken();
                        return object;
                    }
                    jSONLexer.nextToken(objectDeserializer.getFastMatchToken());
                }
                Object t2 = objectDeserializer.deserialze(defaultJSONParser, (Type)object2, null);
                if (jSONLexer.token() != 17) {
                    throw new JSONException("syntax error, expect :, actual " + jSONLexer.token());
                }
                jSONLexer.nextToken(objectDeserializer2.getFastMatchToken());
                Object t3 = objectDeserializer2.deserialze(defaultJSONParser, type, t2);
                defaultJSONParser.checkMapResolve((Map)object, t2);
                object.put(t2, t3);
                if (jSONLexer.token() != 16) continue;
                jSONLexer.nextToken(objectDeserializer.getFastMatchToken());
            }
        }
        finally {
            defaultJSONParser.setContext((ParseContext)object3);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Map parseMap(DefaultJSONParser var0, Map<String, Object> var1_1, Type var2_3, Object var3_4) {
        var11_5 = var0.lexer;
        if (var11_5.token() != 12) {
            throw new JSONException("syntax error, expect {, actual " + var11_5.token());
        }
        var10_6 = var0.getContext();
        var6_7 = 0;
        while (true) {
            block24: {
                block25: {
                    block22: {
                        block23: {
                            block27: {
                                block21: {
                                    try {
                                        block26: {
                                            var11_5.skipWhitespace();
                                            var7_10 = var5_9 = var11_5.getCurrent();
                                            if (var11_5.isEnabled(Feature.AllowArbitraryCommas)) {
                                                while (true) {
                                                    var7_10 = var5_9;
                                                    if (var5_9 != 44) break;
                                                    var11_5.next();
                                                    var11_5.skipWhitespace();
                                                    var5_9 = var11_5.getCurrent();
                                                }
                                            }
                                            if (var7_10 != 34) break block26;
                                            var8_11 = var11_5.scanSymbol(var0.getSymbolTable(), '\"');
                                            var11_5.skipWhitespace();
                                            if (var11_5.getCurrent() != ':') {
                                                throw new JSONException("expect ':' at " + var11_5.pos());
                                            }
                                            ** GOTO lbl53
                                        }
                                        if (var7_10 != 125) break block21;
                                        var11_5.next();
                                        var11_5.resetStringPosition();
                                        var11_5.nextToken(16);
                                        var0.setContext(var10_6);
                                        return var1_1;
                                    }
                                    catch (Throwable var1_2) {
                                        var0.setContext(var10_6);
                                        throw var1_2;
                                    }
                                }
                                if (var7_10 != 39) ** GOTO lbl46
                                if (!var11_5.isEnabled(Feature.AllowSingleQuotes)) {
                                    throw new JSONException("syntax error");
                                }
                                var8_11 = var11_5.scanSymbol(var0.getSymbolTable(), '\'');
                                var11_5.skipWhitespace();
                                if (var11_5.getCurrent() != ':') {
                                    throw new JSONException("expect ':' at " + var11_5.pos());
                                }
                                break block27;
lbl46:
                                // 1 sources

                                if (!var11_5.isEnabled(Feature.AllowUnQuotedFieldNames)) {
                                    throw new JSONException("syntax error");
                                }
                                var8_11 = var11_5.scanSymbolUnQuoted(var0.getSymbolTable());
                                var11_5.skipWhitespace();
                                var4_8 = var11_5.getCurrent();
                                if (var4_8 != ':') {
                                    throw new JSONException("expect ':' at " + var11_5.pos() + ", actual " + var4_8);
                                }
                            }
                            var11_5.next();
                            var11_5.skipWhitespace();
                            var11_5.getCurrent();
                            var11_5.resetStringPosition();
                            if (var8_11 != JSON.DEFAULT_TYPE_KEY || var11_5.isEnabled(Feature.DisableSpecialKeyDetect)) break block22;
                            var8_11 = TypeUtils.loadClass(var11_5.scanSymbol(var0.getSymbolTable(), '\"'), var0.getConfig().getDefaultClassLoader());
                            if (!Map.class.isAssignableFrom(var8_11)) break block23;
                            var11_5.nextToken(16);
                            if (var11_5.token() == 13) {
                                var11_5.nextToken(16);
                                var0.setContext(var10_6);
                                return var1_1;
                            }
                            break block24;
                        }
                        var1_1 = var0.getConfig().getDeserializer(var8_11);
                        var11_5.nextToken(16);
                        var0.setResolveStatus(2);
                        if (var10_6 != null && !(var3_4 instanceof Integer)) {
                            var0.popContext();
                        }
                        var1_1 = (Map)var1_1.deserialze(var0, var8_11, var3_4);
                        var0.setContext(var10_6);
                        return var1_1;
                    }
                    var11_5.nextToken();
                    if (var6_7 != 0) {
                        var0.setContext(var10_6);
                    }
                    if (var11_5.token() != 8) break block25;
                    var9_12 = null;
                    var11_5.nextToken();
                }
                var9_12 = (V)var0.parseObject(var2_3, (Object)var8_11);
                var1_1.put(var8_11, var9_12);
                var0.checkMapResolve((Map)var1_1, var8_11);
                var0.setContext(var10_6, var9_12, var8_11);
                var0.setContext(var10_6);
                var5_9 = var11_5.token();
                if (var5_9 == 20 || var5_9 == 15) {
                    var0.setContext(var10_6);
                    return var1_1;
                }
                if (var5_9 == 13) {
                    var11_5.nextToken();
                    var0.setContext(var10_6);
                    return var1_1;
                }
            }
            ++var6_7;
        }
    }

    protected Map<Object, Object> createMap(Type type) {
        if (type == Properties.class) {
            return new Properties();
        }
        if (type == Hashtable.class) {
            return new Hashtable<Object, Object>();
        }
        if (type == IdentityHashMap.class) {
            return new IdentityHashMap<Object, Object>();
        }
        if (type == SortedMap.class || type == TreeMap.class) {
            return new TreeMap<Object, Object>();
        }
        if (type == ConcurrentMap.class || type == ConcurrentHashMap.class) {
            return new ConcurrentHashMap<Object, Object>();
        }
        if (type == Map.class || type == HashMap.class) {
            return new HashMap<Object, Object>();
        }
        if (type == LinkedHashMap.class) {
            return new LinkedHashMap<Object, Object>();
        }
        if (type instanceof ParameterizedType) {
            return this.createMap(((ParameterizedType)type).getRawType());
        }
        Object object = (Class)type;
        if (((Class)object).isInterface()) {
            throw new JSONException("unsupport type " + type);
        }
        try {
            object = (Map)((Class)object).newInstance();
            return object;
        }
        catch (Exception exception) {
            throw new JSONException("unsupport type " + type, exception);
        }
    }

    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type object, Object object2) {
        if (object == JSONObject.class && defaultJSONParser.getFieldTypeResolver() == null) {
            return (T)defaultJSONParser.parseObject();
        }
        Object object3 = defaultJSONParser.lexer;
        if (object3.token() == 8) {
            object3.nextToken(16);
            return null;
        }
        Map<Object, Object> map = this.createMap((Type)object);
        object3 = defaultJSONParser.getContext();
        try {
            defaultJSONParser.setContext((ParseContext)object3, map, object2);
            object = this.deserialze(defaultJSONParser, (Type)object, object2, map);
            return (T)object;
        }
        finally {
            defaultJSONParser.setContext((ParseContext)object3);
        }
    }

    protected Object deserialze(DefaultJSONParser defaultJSONParser, Type type, Object object, Map map) {
        if (type instanceof ParameterizedType) {
            Type type2 = (ParameterizedType)type;
            type = type2.getActualTypeArguments()[0];
            type2 = type2.getActualTypeArguments()[1];
            if (String.class == type) {
                return MapDeserializer.parseMap(defaultJSONParser, map, type2, object);
            }
            return MapDeserializer.parseMap(defaultJSONParser, map, type, type2, object);
        }
        return defaultJSONParser.parseObject(map, object);
    }

    @Override
    public int getFastMatchToken() {
        return 12;
    }
}

