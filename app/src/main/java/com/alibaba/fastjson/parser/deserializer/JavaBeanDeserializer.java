/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class JavaBeanDeserializer
implements ObjectDeserializer {
    public final JavaBeanInfo beanInfo;
    protected final Class<?> clazz;
    private final FieldDeserializer[] fieldDeserializers;
    protected final FieldDeserializer[] sortedFieldDeserializers;

    public JavaBeanDeserializer(ParserConfig parserConfig, Class<?> clazz) {
        this(parserConfig, clazz, clazz);
    }

    /*
     * WARNING - void declaration
     */
    public JavaBeanDeserializer(ParserConfig object, Class<?> object22, Type type) {
        void var3_6;
        this.clazz = object22;
        this.beanInfo = JavaBeanInfo.build(object22, (Type)var3_6);
        this.sortedFieldDeserializers = new FieldDeserializer[this.beanInfo.sortedFields.length];
        for (FieldInfo fieldInfo : this.beanInfo.sortedFields) {
            FieldDeserializer fieldDeserializer;
            this.sortedFieldDeserializers[i2] = fieldDeserializer = ((ParserConfig)object).createFieldDeserializer((ParserConfig)object, this.beanInfo, fieldInfo);
        }
        this.fieldDeserializers = new FieldDeserializer[this.beanInfo.fields.length];
        int n2 = this.beanInfo.fields.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            this.fieldDeserializers[i2] = object = this.getFieldDeserializer(this.beanInfo.fields[i2].name);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected static void parseArray(Collection collection, ObjectDeserializer objectDeserializer, DefaultJSONParser defaultJSONParser, Type type, Object object) {
        int n2;
        object = (JSONLexerBase)defaultJSONParser.lexer;
        int n3 = n2 = ((JSONLexerBase)object).token();
        if (n2 == 8) {
            ((JSONLexerBase)object).nextToken(16);
            n3 = ((JSONLexerBase)object).token();
        }
        if (n3 != 14) {
            defaultJSONParser.throwException(n3);
        }
        if (((JSONLexerBase)object).getCurrent() == '[') {
            ((JSONLexerBase)object).next();
            ((JSONLexerBase)object).setToken(14);
        } else {
            ((JSONLexerBase)object).nextToken(14);
        }
        n3 = 0;
        while (true) {
            collection.add(objectDeserializer.deserialze(defaultJSONParser, type, n3));
            ++n3;
            if (((JSONLexerBase)object).token() != 16) break;
            if (((JSONLexerBase)object).getCurrent() == '[') {
                ((JSONLexerBase)object).next();
                ((JSONLexerBase)object).setToken(14);
                continue;
            }
            ((JSONLexerBase)object).nextToken(14);
        }
        n3 = ((JSONLexerBase)object).token();
        if (n3 != 15) {
            defaultJSONParser.throwException(n3);
        }
        if (((JSONLexerBase)object).getCurrent() == ',') {
            ((JSONLexerBase)object).next();
            ((JSONLexerBase)object).setToken(16);
            return;
        }
        ((JSONLexerBase)object).nextToken(16);
    }

    protected void check(JSONLexer jSONLexer, int n2) {
        if (jSONLexer.token() != n2) {
            throw new JSONException("syntax error");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object createInstance(DefaultJSONParser fieldInfoArray, Type object) {
        block8: {
            if (object instanceof Class && this.clazz.isInterface()) {
                fieldInfoArray = (Class)object;
                object = Thread.currentThread().getContextClassLoader();
                JSONObject jSONObject = new JSONObject();
                return Proxy.newProxyInstance((ClassLoader)object, new Class[]{fieldInfoArray}, jSONObject);
            }
            if (this.beanInfo.defaultConstructor == null) {
                return null;
            }
            try {
                object = this.beanInfo.defaultConstructor;
                object = this.beanInfo.defaultConstructorParameterSize == 0 ? ((Constructor)object).newInstance(new Object[0]) : ((Constructor)object).newInstance(fieldInfoArray.getContext().object);
                if (fieldInfoArray == null || !((DefaultJSONParser)fieldInfoArray).lexer.isEnabled(Feature.InitStringFieldAsEmpty)) break block8;
                fieldInfoArray = this.beanInfo.fields;
            }
            catch (Exception exception) {
                throw new JSONException("create instance error, class " + this.clazz.getName(), exception);
            }
            int n2 = ((Object)fieldInfoArray).length;
            for (int i2 = 0; i2 < n2; ++i2) {
                Object fieldInfo = fieldInfoArray[i2];
                if (((FieldInfo)fieldInfo).fieldClass != String.class) continue;
                try {
                    ((FieldInfo)fieldInfo).set(object, "");
                }
                catch (Exception exception) {
                    throw new JSONException("create instance error, class " + this.clazz.getName(), exception);
                }
            }
        }
        return object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object createInstance(Map<String, Object> object, ParserConfig objectArray) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        Object object2 = null;
        if (this.beanInfo.creatorConstructor == null && this.beanInfo.buildMethod == null) {
            object2 = this.createInstance(null, this.clazz);
            Iterator iterator = object.entrySet().iterator();
            while (true) {
                object = object2;
                if (!iterator.hasNext()) return object;
                object = iterator.next();
                Object object3 = (String)object.getKey();
                object = object.getValue();
                if ((object3 = this.getFieldDeserializer((String)object3)) == null) continue;
                Method method = object3.fieldInfo.method;
                if (method != null) {
                    method.invoke(object2, TypeUtils.cast(object, method.getGenericParameterTypes()[0], (ParserConfig)objectArray));
                    continue;
                }
                object3.fieldInfo.field.set(object2, TypeUtils.cast(object, object3.fieldInfo.fieldType, (ParserConfig)objectArray));
            }
        }
        FieldInfo[] fieldInfoArray = this.beanInfo.fields;
        int n2 = fieldInfoArray.length;
        objectArray = new Object[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            objectArray[i2] = object.get(fieldInfoArray[i2].name);
        }
        if (this.beanInfo.creatorConstructor != null) {
            try {
                return this.beanInfo.creatorConstructor.newInstance(objectArray);
            }
            catch (Exception exception) {
                throw new JSONException("create instance error, " + this.beanInfo.creatorConstructor.toGenericString(), exception);
            }
        }
        object = object2;
        if (this.beanInfo.factoryMethod == null) return object;
        try {
            return this.beanInfo.factoryMethod.invoke(null, objectArray);
        }
        catch (Exception exception) {
            throw new JSONException("create factory method error, " + this.beanInfo.factoryMethod.toString(), exception);
        }
    }

    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object object) {
        return this.deserialze(defaultJSONParser, type, object, null);
    }

    /*
     * Exception decompiling
     */
    protected <T> T deserialze(DefaultJSONParser var1_1, Type var2_2, Object var3_9, Object var4_10) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 12[TRYBLOCK] [12 : 463->471)] java.lang.Throwable
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2289)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:414)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:73)
         *     at org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     */
    public <T> T deserialzeArrayMapping(DefaultJSONParser defaultJSONParser, Type object, Object object2, Object object3) {
        object2 = defaultJSONParser.lexer;
        if (object2.token() != 14) {
            throw new JSONException("error");
        }
        object3 = this.createInstance(defaultJSONParser, (Type)object);
        int n2 = 0;
        int n3 = this.sortedFieldDeserializers.length;
        while (true) {
            int n4;
            if (n2 >= n3) {
                object2.nextToken(16);
                return (T)object3;
            }
            char c2 = n2 == n3 - 1 ? (char)']' : ',';
            FieldDeserializer fieldDeserializer = this.sortedFieldDeserializers[n2];
            object = fieldDeserializer.fieldInfo.fieldClass;
            if (object == Integer.TYPE) {
                fieldDeserializer.setValue(object3, object2.scanInt(c2));
            } else if (object == String.class) {
                fieldDeserializer.setValue(object3, object2.scanString(c2));
            } else if (object == Long.TYPE) {
                fieldDeserializer.setValue(object3, object2.scanLong(c2));
            } else if (((Class)object).isEnum()) {
                n4 = object2.getCurrent();
                if (n4 == 34 || n4 == 110) {
                    object = object2.scanEnum((Class<?>)object, defaultJSONParser.getSymbolTable(), c2);
                } else if (n4 >= 48 && n4 <= 57) {
                    n4 = object2.scanInt(c2);
                    object = ((EnumDeserializer)((DefaultFieldDeserializer)fieldDeserializer).getFieldValueDeserilizer(defaultJSONParser.getConfig())).valueOf(n4);
                } else {
                    object = this.scanEnum((JSONLexer)object2, c2);
                }
                fieldDeserializer.setValue(object3, object);
            } else if (object == Boolean.TYPE) {
                fieldDeserializer.setValue(object3, object2.scanBoolean(c2));
            } else if (object == Float.TYPE) {
                fieldDeserializer.setValue(object3, Float.valueOf(object2.scanFloat(c2)));
            } else if (object == Double.TYPE) {
                fieldDeserializer.setValue(object3, object2.scanDouble(c2));
            } else if (object == Date.class && object2.getCurrent() == '1') {
                fieldDeserializer.setValue(object3, new Date(object2.scanLong(c2)));
            } else {
                object2.nextToken(14);
                fieldDeserializer.setValue(object3, defaultJSONParser.parseObject(fieldDeserializer.fieldInfo.fieldType));
                n4 = c2 == ']' ? 15 : 16;
                this.check((JSONLexer)object2, n4);
            }
            ++n2;
        }
    }

    @Override
    public int getFastMatchToken() {
        return 12;
    }

    /*
     * Enabled aggressive block sorting
     */
    public FieldDeserializer getFieldDeserializer(String string2) {
        if (string2 != null) {
            int n2 = 0;
            int n3 = this.sortedFieldDeserializers.length - 1;
            while (n2 <= n3) {
                int n4 = n2 + n3 >>> 1;
                int n5 = this.sortedFieldDeserializers[n4].fieldInfo.name.compareTo(string2);
                if (n5 < 0) {
                    n2 = n4 + 1;
                    continue;
                }
                if (n5 <= 0) {
                    return this.sortedFieldDeserializers[n4];
                }
                n3 = n4 - 1;
            }
        }
        return null;
    }

    public Type getFieldType(int n2) {
        return this.sortedFieldDeserializers[n2].fieldInfo.fieldType;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected JavaBeanDeserializer getSeeAlso(ParserConfig parserConfig, JavaBeanInfo object, String string2) {
        if (((JavaBeanInfo)object).jsonType == null) {
            return null;
        }
        Class<?>[] classArray = ((JavaBeanInfo)object).jsonType.seeAlso();
        int n2 = classArray.length;
        int n3 = 0;
        while (n3 < n2) {
            object = parserConfig.getDeserializer(classArray[n3]);
            if (object instanceof JavaBeanDeserializer) {
                object = (JavaBeanDeserializer)object;
                JavaBeanInfo javaBeanInfo = ((JavaBeanDeserializer)object).beanInfo;
                if (javaBeanInfo.typeName.equals(string2)) return object;
                object = this.getSeeAlso(parserConfig, javaBeanInfo, string2);
                if (object != null) {
                    return object;
                }
            }
            ++n3;
        }
        return null;
    }

    public boolean parseField(DefaultJSONParser defaultJSONParser, String string2, Object object, Type type, Map<String, Object> map) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        FieldDeserializer fieldDeserializer = this.smartMatch(string2);
        if (fieldDeserializer == null) {
            if (!jSONLexer.isEnabled(Feature.IgnoreNotMatch)) {
                throw new JSONException("setter not found, class " + this.clazz.getName() + ", property " + string2);
            }
            defaultJSONParser.parseExtra(object, string2);
            return false;
        }
        jSONLexer.nextTokenWithColon(fieldDeserializer.getFastMatchToken());
        fieldDeserializer.parseField(defaultJSONParser, object, type, map);
        return true;
    }

    protected Object parseRest(DefaultJSONParser defaultJSONParser, Type type, Object object, Object object2) {
        return this.deserialze(defaultJSONParser, type, object, object2);
    }

    protected Enum<?> scanEnum(JSONLexer jSONLexer, char c2) {
        throw new JSONException("illegal enum. " + jSONLexer.info());
    }

    /*
     * Enabled aggressive block sorting
     */
    public FieldDeserializer smartMatch(String object) {
        int n2;
        int n3;
        FieldDeserializer[] fieldDeserializerArray;
        Object object2;
        int n4 = 0;
        if (object == null) {
            return null;
        }
        Object object3 = object2 = this.getFieldDeserializer((String)object);
        if (object2 == null) {
            boolean bl2 = ((String)object).startsWith("is");
            fieldDeserializerArray = this.sortedFieldDeserializers;
            n3 = fieldDeserializerArray.length;
            n2 = 0;
            while (true) {
                object3 = object2;
                if (n2 >= n3) break;
                object3 = fieldDeserializerArray[n2];
                Object object4 = ((FieldDeserializer)object3).fieldInfo;
                Class<?> clazz = ((FieldInfo)object4).fieldClass;
                object4 = ((FieldInfo)object4).name;
                if (((String)object4).equalsIgnoreCase((String)object) || bl2 && (clazz == Boolean.TYPE || clazz == Boolean.class) && ((String)object4).equalsIgnoreCase(((String)object).substring(2))) {
                    break;
                }
                ++n2;
            }
        }
        object2 = object3;
        if (object3 != null) return object2;
        object2 = object3;
        if (((String)object).indexOf(95) == -1) return object2;
        object3 = ((String)object).replaceAll("_", "");
        object2 = object = this.getFieldDeserializer((String)object3);
        if (object != null) return object2;
        fieldDeserializerArray = this.sortedFieldDeserializers;
        n3 = fieldDeserializerArray.length;
        n2 = n4;
        while (true) {
            object2 = object;
            if (n2 >= n3) return object2;
            object2 = fieldDeserializerArray[n2];
            if (object2.fieldInfo.name.equalsIgnoreCase((String)object3)) {
                return object2;
            }
            ++n2;
        }
    }
}

