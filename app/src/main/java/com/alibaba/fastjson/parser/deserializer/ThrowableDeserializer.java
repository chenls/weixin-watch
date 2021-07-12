/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;

public class ThrowableDeserializer
extends JavaBeanDeserializer {
    public ThrowableDeserializer(ParserConfig parserConfig, Class<?> clazz) {
        super(parserConfig, clazz, clazz);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private Throwable createException(String string2, Throwable throwable, Class<?> genericDeclaration) throws Exception {
        void var7_7;
        Object var7_6 = null;
        Constructor constructor = null;
        Constructor constructor2 = null;
        for (Constructor<?> constructor3 : ((Class)genericDeclaration).getConstructors()) {
            void var11_22;
            void var9_14;
            Constructor<?> constructor4;
            Class<?>[] classArray = constructor3.getParameterTypes();
            if (classArray.length == 0) {
                Constructor constructor5 = constructor;
                constructor4 = constructor3;
                Constructor constructor6 = constructor2;
            } else if (classArray.length == 1 && classArray[0] == String.class) {
                Constructor constructor7 = constructor2;
                constructor4 = var7_7;
                Constructor<?> constructor8 = constructor3;
            } else {
                Constructor constructor9 = constructor2;
                constructor4 = var7_7;
                Constructor constructor10 = constructor;
                if (classArray.length == 2) {
                    Constructor constructor11 = constructor2;
                    constructor4 = var7_7;
                    Constructor constructor12 = constructor;
                    if (classArray[0] == String.class) {
                        Constructor constructor13 = constructor2;
                        constructor4 = var7_7;
                        Constructor constructor14 = constructor;
                        if (classArray[1] == Throwable.class) {
                            Constructor<?> constructor15 = constructor3;
                            constructor4 = var7_7;
                            Constructor constructor16 = constructor;
                        }
                    }
                }
            }
            constructor2 = var9_14;
            Constructor<?> constructor17 = constructor4;
            constructor = var11_22;
        }
        if (constructor2 != null) {
            return (Throwable)constructor2.newInstance(string2, throwable);
        }
        if (constructor != null) {
            return (Throwable)constructor.newInstance(string2);
        }
        if (var7_7 != null) {
            return (Throwable)var7_7.newInstance(new Object[0]);
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public <T> T deserialze(DefaultJSONParser var1_1, Type var2_3, Object var3_4) {
        block18: {
            block24: {
                block23: {
                    block21: {
                        block22: {
                            block19: {
                                block20: {
                                    block16: {
                                        block17: {
                                            var10_5 = var1_1.lexer;
                                            if (var10_5.token() == 8) {
                                                var10_5.nextToken();
                                                var2_3 = null;
                                                return (T)var2_3;
                                            }
                                            if (var1_1.getResolveStatus() == 2) {
                                                var1_1.setResolveStatus(0);
lbl9:
                                                // 2 sources

                                                while (true) {
                                                    var6_6 = null;
                                                    var4_7 = null;
                                                    var3_4 = var4_7;
                                                    if (var2_3 != null) {
                                                        var3_4 = var4_7;
                                                        if (var2_3 instanceof Class) {
                                                            var2_3 = (Class)var2_3;
                                                            var3_4 = var4_7;
                                                            if (Throwable.class.isAssignableFrom((Class<?>)var2_3)) {
                                                                var3_4 = var2_3;
                                                            }
                                                        }
                                                    }
                                                    var5_8 = null;
                                                    var4_7 = null;
                                                    var11_9 = new HashMap<String, Object>();
                                                    var2_3 = var6_6;
lbl24:
                                                    // 3 sources

                                                    while (true) {
                                                        var6_6 = var10_5.scanSymbol(var1_1.getSymbolTable());
                                                        if (var6_6 != null) break block16;
                                                        if (var10_5.token() != 13) break block17;
                                                        var10_5.nextToken(16);
lbl29:
                                                        // 2 sources

                                                        while (var3_4 == null) {
                                                            var1_1 = new Exception(var5_8, (Throwable)var2_3);
lbl31:
                                                            // 3 sources

                                                            while (true) {
                                                                var2_3 = var1_1;
                                                                if (var4_7 == null) ** continue;
                                                                var1_1.setStackTrace(var4_7);
                                                                return (T)var1_1;
                                                            }
                                                        }
                                                        break block18;
                                                        break;
                                                    }
                                                    break;
                                                }
                                            }
                                            ** while (var10_5.token() == 12)
lbl38:
                                            // 1 sources

                                            throw new JSONException("syntax error");
                                        }
                                        if (var10_5.token() == 16 && var10_5.isEnabled(Feature.AllowArbitraryCommas)) ** GOTO lbl24
                                    }
                                    var10_5.nextTokenWithColon(4);
                                    if (!JSON.DEFAULT_TYPE_KEY.equals(var6_6)) break block19;
                                    if (var10_5.token() != 4) break block20;
                                    var8_11 = TypeUtils.loadClass(var10_5.stringVal(), var1_1.getConfig().getDefaultClassLoader());
                                    var10_5.nextToken(16);
                                    var7_10 = var4_7;
                                    var6_6 = var5_8;
                                    var9_12 = var2_3;
lbl50:
                                    // 5 sources

                                    while (true) {
                                        var2_3 = var9_12;
                                        var3_4 = var8_11;
                                        var5_8 = var6_6;
                                        var4_7 = var7_10;
                                        if (var10_5.token() == 13) ** break;
                                        ** continue;
                                        var10_5.nextToken(16);
                                        var2_3 = var9_12;
                                        var3_4 = var8_11;
                                        var5_8 = var6_6;
                                        var4_7 = var7_10;
                                        ** GOTO lbl29
                                        break;
                                    }
                                }
                                throw new JSONException("syntax error");
                            }
                            if (!"message".equals(var6_6)) break block21;
                            if (var10_5.token() != 8) break block22;
                            var6_6 = null;
lbl69:
                            // 2 sources

                            while (true) {
                                var10_5.nextToken();
                                var9_12 = var2_3;
                                var8_11 = var3_4;
                                var7_10 = var4_7;
                                ** GOTO lbl50
                                break;
                            }
                        }
                        if (var10_5.token() == 4) {
                            var6_6 = var10_5.stringVal();
                            ** continue;
                        }
                        throw new JSONException("syntax error");
                    }
                    if (!"cause".equals(var6_6)) break block23;
                    var9_12 = (Throwable)this.deserialze((DefaultJSONParser)var1_1, null, "cause");
                    var8_11 = var3_4;
                    var6_6 = var5_8;
                    var7_10 = var4_7;
                    ** GOTO lbl50
                }
                if (!"stackTrace".equals(var6_6)) break block24;
                var7_10 = var1_1.parseObject(StackTraceElement[].class);
                var9_12 = var2_3;
                var8_11 = var3_4;
                var6_6 = var5_8;
                ** GOTO lbl50
            }
            var11_9.put(var6_6, var1_1.parse());
            var9_12 = var2_3;
            var8_11 = var3_4;
            var6_6 = var5_8;
            var7_10 = var4_7;
            ** while (true)
        }
        try {
            var1_1 = var3_4 = this.createException(var5_8, (Throwable)var2_3, (Class<?>)var3_4);
            if (var3_4 != null) ** GOTO lbl31
        }
        catch (Exception var1_2) {
            throw new JSONException("create instance error", var1_2);
        }
        var1_1 = new Exception(var5_8, (Throwable)var2_3);
        ** continue;
    }

    @Override
    public int getFastMatchToken() {
        return 12;
    }
}

