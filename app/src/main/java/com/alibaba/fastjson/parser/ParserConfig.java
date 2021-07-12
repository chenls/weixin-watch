/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.SymbolTable;
import com.alibaba.fastjson.parser.deserializer.ASMDeserializerFactory;
import com.alibaba.fastjson.parser.deserializer.ArrayListTypeFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.AutowiredObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.EnumDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.Jdk8DateCodec;
import com.alibaba.fastjson.parser.deserializer.MapDeserializer;
import com.alibaba.fastjson.parser.deserializer.NumberDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.parser.deserializer.OptionalCodec;
import com.alibaba.fastjson.parser.deserializer.SqlDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.StackTraceElementDeserializer;
import com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer;
import com.alibaba.fastjson.parser.deserializer.TimeDeserializer;
import com.alibaba.fastjson.serializer.AtomicCodec;
import com.alibaba.fastjson.serializer.AwtCodec;
import com.alibaba.fastjson.serializer.BigDecimalCodec;
import com.alibaba.fastjson.serializer.BigIntegerCodec;
import com.alibaba.fastjson.serializer.BooleanCodec;
import com.alibaba.fastjson.serializer.CalendarCodec;
import com.alibaba.fastjson.serializer.CharArrayCodec;
import com.alibaba.fastjson.serializer.CharacterCodec;
import com.alibaba.fastjson.serializer.CollectionCodec;
import com.alibaba.fastjson.serializer.DateCodec;
import com.alibaba.fastjson.serializer.FloatCodec;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.MiscCodec;
import com.alibaba.fastjson.serializer.ObjectArrayCodec;
import com.alibaba.fastjson.serializer.ReferenceCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.ASMClassLoader;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.alibaba.fastjson.util.JavaBeanInfo;
import com.alibaba.fastjson.util.ServiceLoader;
import java.io.Closeable;
import java.io.File;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.AccessControlException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class ParserConfig {
    public static final String DENY_PROPERTY = "fastjson.parser.deny";
    private static boolean awtError;
    public static ParserConfig global;
    private static boolean jdk8Error;
    private boolean asmEnable;
    protected ASMDeserializerFactory asmFactory;
    protected ClassLoader defaultClassLoader;
    private String[] denyList;
    private final IdentityHashMap<Type, ObjectDeserializer> derializers;
    public final SymbolTable symbolTable;

    static {
        global = new ParserConfig();
        awtError = false;
        jdk8Error = false;
    }

    public ParserConfig() {
        this(null, null);
    }

    public ParserConfig(ASMDeserializerFactory aSMDeserializerFactory) {
        this(aSMDeserializerFactory, null);
    }

    /*
     * Unable to fully structure code
     */
    private ParserConfig(ASMDeserializerFactory var1_1, ClassLoader var2_2) {
        block12: {
            super();
            this.derializers = new IdentityHashMap<K, V>();
            if (!ASMUtils.IS_ANDROID) {
                var3_6 = true;
lbl5:
                // 2 sources

                while (true) {
                    this.asmEnable = var3_6;
                    this.symbolTable = new SymbolTable(4096);
                    this.denyList = new String[]{"java.lang.Thread"};
                    var4_7 = var1_1;
                    if (var1_1 == null) {
                        var4_7 = var1_1;
                        if (!ASMUtils.IS_ANDROID) {
                            if (var2_2 != null) break block12;
                            var4_7 = new ASMDeserializerFactory(new ASMClassLoader());
                        }
                    }
lbl16:
                    // 9 sources

                    while (true) {
                        this.asmFactory = var4_7;
                        if (var4_7 == null) {
                            this.asmEnable = false;
                        }
                        this.derializers.put((Type)SimpleDateFormat.class, MiscCodec.instance);
                        this.derializers.put((Type)Timestamp.class, SqlDateDeserializer.instance_timestamp);
                        this.derializers.put((Type)java.sql.Date.class, SqlDateDeserializer.instance);
                        this.derializers.put((Type)Time.class, TimeDeserializer.instance);
                        this.derializers.put((Type)Date.class, DateCodec.instance);
                        this.derializers.put((Type)Calendar.class, CalendarCodec.instance);
                        this.derializers.put((Type)JSONObject.class, MapDeserializer.instance);
                        this.derializers.put((Type)JSONArray.class, CollectionCodec.instance);
                        this.derializers.put((Type)Map.class, MapDeserializer.instance);
                        this.derializers.put((Type)HashMap.class, MapDeserializer.instance);
                        this.derializers.put((Type)LinkedHashMap.class, MapDeserializer.instance);
                        this.derializers.put((Type)TreeMap.class, MapDeserializer.instance);
                        this.derializers.put((Type)ConcurrentMap.class, MapDeserializer.instance);
                        this.derializers.put((Type)ConcurrentHashMap.class, MapDeserializer.instance);
                        this.derializers.put((Type)Collection.class, CollectionCodec.instance);
                        this.derializers.put((Type)List.class, CollectionCodec.instance);
                        this.derializers.put((Type)ArrayList.class, CollectionCodec.instance);
                        this.derializers.put((Type)Object.class, JavaObjectDeserializer.instance);
                        this.derializers.put((Type)String.class, StringCodec.instance);
                        this.derializers.put((Type)StringBuffer.class, StringCodec.instance);
                        this.derializers.put((Type)StringBuilder.class, StringCodec.instance);
                        this.derializers.put(Character.TYPE, CharacterCodec.instance);
                        this.derializers.put((Type)Character.class, CharacterCodec.instance);
                        this.derializers.put(Byte.TYPE, NumberDeserializer.instance);
                        this.derializers.put((Type)Byte.class, NumberDeserializer.instance);
                        this.derializers.put(Short.TYPE, NumberDeserializer.instance);
                        this.derializers.put((Type)Short.class, NumberDeserializer.instance);
                        this.derializers.put(Integer.TYPE, IntegerCodec.instance);
                        this.derializers.put((Type)Integer.class, IntegerCodec.instance);
                        this.derializers.put(Long.TYPE, LongCodec.instance);
                        this.derializers.put((Type)Long.class, LongCodec.instance);
                        this.derializers.put((Type)BigInteger.class, BigIntegerCodec.instance);
                        this.derializers.put((Type)BigDecimal.class, BigDecimalCodec.instance);
                        this.derializers.put(Float.TYPE, FloatCodec.instance);
                        this.derializers.put((Type)Float.class, FloatCodec.instance);
                        this.derializers.put(Double.TYPE, NumberDeserializer.instance);
                        this.derializers.put((Type)Double.class, NumberDeserializer.instance);
                        this.derializers.put(Boolean.TYPE, BooleanCodec.instance);
                        this.derializers.put((Type)Boolean.class, BooleanCodec.instance);
                        this.derializers.put((Type)Class.class, MiscCodec.instance);
                        this.derializers.put((Type)char[].class, new CharArrayCodec());
                        this.derializers.put((Type)AtomicBoolean.class, BooleanCodec.instance);
                        this.derializers.put((Type)AtomicInteger.class, IntegerCodec.instance);
                        this.derializers.put((Type)AtomicLong.class, LongCodec.instance);
                        this.derializers.put((Type)AtomicReference.class, ReferenceCodec.instance);
                        this.derializers.put((Type)WeakReference.class, ReferenceCodec.instance);
                        this.derializers.put((Type)SoftReference.class, ReferenceCodec.instance);
                        this.derializers.put((Type)UUID.class, MiscCodec.instance);
                        this.derializers.put((Type)TimeZone.class, MiscCodec.instance);
                        this.derializers.put((Type)Locale.class, MiscCodec.instance);
                        this.derializers.put((Type)Currency.class, MiscCodec.instance);
                        this.derializers.put((Type)InetAddress.class, MiscCodec.instance);
                        this.derializers.put((Type)Inet4Address.class, MiscCodec.instance);
                        this.derializers.put((Type)Inet6Address.class, MiscCodec.instance);
                        this.derializers.put((Type)InetSocketAddress.class, MiscCodec.instance);
                        this.derializers.put((Type)File.class, MiscCodec.instance);
                        this.derializers.put((Type)URI.class, MiscCodec.instance);
                        this.derializers.put((Type)URL.class, MiscCodec.instance);
                        this.derializers.put((Type)Pattern.class, MiscCodec.instance);
                        this.derializers.put((Type)Charset.class, MiscCodec.instance);
                        this.derializers.put((Type)JSONPath.class, MiscCodec.instance);
                        this.derializers.put((Type)Number.class, NumberDeserializer.instance);
                        this.derializers.put((Type)AtomicIntegerArray.class, AtomicCodec.instance);
                        this.derializers.put((Type)AtomicLongArray.class, AtomicCodec.instance);
                        this.derializers.put((Type)StackTraceElement.class, StackTraceElementDeserializer.instance);
                        this.derializers.put((Type)Serializable.class, JavaObjectDeserializer.instance);
                        this.derializers.put((Type)Cloneable.class, JavaObjectDeserializer.instance);
                        this.derializers.put((Type)Comparable.class, JavaObjectDeserializer.instance);
                        this.derializers.put((Type)Closeable.class, JavaObjectDeserializer.instance);
                        this.addDeny("java.lang.Thread");
                        this.configFromPropety(System.getProperties());
                        return;
                    }
                    break;
                }
            }
            var3_6 = false;
            ** while (true)
        }
        try {
            var4_7 = new ASMDeserializerFactory(var2_2);
            ** GOTO lbl16
        }
        catch (NoClassDefFoundError var2_3) {
            var4_7 = var1_1;
            ** GOTO lbl16
        }
        catch (AccessControlException var2_4) {
            var4_7 = var1_1;
            ** GOTO lbl16
        }
        catch (ExceptionInInitializerError var2_5) {
            var4_7 = var1_1;
            ** continue;
        }
    }

    public ParserConfig(ClassLoader classLoader) {
        this(null, classLoader);
    }

    public static Field getField(Class<?> clazz, String string2) {
        Field field;
        Field field2 = field = ParserConfig.getField0(clazz, string2);
        if (field == null) {
            field2 = ParserConfig.getField0(clazz, "_" + string2);
        }
        field = field2;
        if (field2 == null) {
            field = ParserConfig.getField0(clazz, "m_" + string2);
        }
        return field;
    }

    private static Field getField0(Class<?> clazz, String string2) {
        for (Field field : clazz.getDeclaredFields()) {
            if (!string2.equals(field.getName())) continue;
            return field;
        }
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            return ParserConfig.getField(clazz.getSuperclass(), string2);
        }
        return null;
    }

    public static ParserConfig getGlobalInstance() {
        return global;
    }

    public static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || clazz == Boolean.class || clazz == Character.class || clazz == Byte.class || clazz == Short.class || clazz == Integer.class || clazz == Long.class || clazz == Float.class || clazz == Double.class || clazz == BigInteger.class || clazz == BigDecimal.class || clazz == String.class || clazz == Date.class || clazz == java.sql.Date.class || clazz == Time.class || clazz == Timestamp.class || clazz.isEnum();
    }

    public void addDeny(String string2) {
        if (string2 == null || string2.length() == 0) {
            return;
        }
        String[] stringArray = new String[this.denyList.length + 1];
        System.arraycopy(this.denyList, 0, stringArray, 0, this.denyList.length);
        stringArray[stringArray.length - 1] = string2;
        this.denyList = stringArray;
    }

    public void configFromPropety(Properties stringArray) {
        if ((stringArray = stringArray.getProperty(DENY_PROPERTY)) != null && stringArray.length() > 0) {
            stringArray = stringArray.split(",");
            for (int i2 = 0; i2 < stringArray.length; ++i2) {
                this.addDeny(stringArray[i2]);
            }
        }
    }

    public FieldDeserializer createFieldDeserializer(ParserConfig parserConfig, JavaBeanInfo object, FieldInfo fieldInfo) {
        object = ((JavaBeanInfo)object).clazz;
        Class<?> clazz = fieldInfo.fieldClass;
        if (clazz == List.class || clazz == ArrayList.class) {
            return new ArrayListTypeFieldDeserializer(parserConfig, (Class<?>)object, fieldInfo);
        }
        return new DefaultFieldDeserializer(parserConfig, (Class<?>)object, fieldInfo);
    }

    /*
     * Handled impossible loop by adding 'first' condition
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ObjectDeserializer createJavaBeanDeserializer(Class<?> clazz, Type type) {
        GenericDeclaration genericDeclaration;
        boolean bl2;
        FieldInfo[] fieldInfoArray;
        boolean bl3;
        block30: {
            boolean bl4;
            bl3 = bl4 = this.asmEnable;
            if (bl4) {
                fieldInfoArray = clazz.getAnnotation(JSONType.class);
                bl2 = bl4;
                if (fieldInfoArray != null) {
                    bl2 = bl4;
                    if (!fieldInfoArray.asm()) {
                        bl2 = false;
                    }
                }
                bl3 = bl2;
                if (bl2) {
                    genericDeclaration = JavaBeanInfo.getBuilderClass((JSONType)fieldInfoArray);
                    fieldInfoArray = genericDeclaration;
                    if (genericDeclaration == null) {
                        fieldInfoArray = clazz;
                    }
                    do {
                        if (!Modifier.isPublic(fieldInfoArray.getModifiers())) {
                            bl3 = false;
                            break block30;
                        }
                        genericDeclaration = fieldInfoArray.getSuperclass();
                        bl3 = bl2;
                        if (genericDeclaration == Object.class) break block30;
                        fieldInfoArray = genericDeclaration;
                    } while (genericDeclaration != null);
                    bl3 = bl2;
                }
            }
        }
        bl2 = bl3;
        if (clazz.getTypeParameters().length != 0) {
            bl2 = false;
        }
        bl3 = bl2;
        if (bl2) {
            bl3 = bl2;
            if (this.asmFactory != null) {
                bl3 = bl2;
                if (this.asmFactory.classLoader.isExternalClass(clazz)) {
                    bl3 = false;
                }
            }
        }
        bl2 = bl3;
        if (bl3) {
            bl2 = ASMUtils.checkName(clazz.getName());
        }
        bl3 = bl2;
        boolean bl5 = true;
        while (true) {
            int n2;
            block38: {
                block32: {
                    block37: {
                        Object object;
                        block36: {
                            block35: {
                                block34: {
                                    block33: {
                                        int n3;
                                        block31: {
                                            if (!bl5 || (bl5 = false)) break block31;
                                            if (!bl2) break block32;
                                            if (clazz.isInterface()) {
                                                bl2 = false;
                                            }
                                            fieldInfoArray = JavaBeanInfo.build(clazz, type);
                                            bl3 = bl2;
                                            if (bl2) {
                                                bl3 = bl2;
                                                if (fieldInfoArray.fields.length > 200) {
                                                    bl3 = false;
                                                }
                                            }
                                            genericDeclaration = fieldInfoArray.defaultConstructor;
                                            bl2 = bl3;
                                            if (bl3) {
                                                bl2 = bl3;
                                                if (genericDeclaration == null) {
                                                    bl2 = bl3;
                                                    if (!clazz.isInterface()) {
                                                        bl2 = false;
                                                    }
                                                }
                                            }
                                            fieldInfoArray = fieldInfoArray.fields;
                                            n3 = fieldInfoArray.length;
                                            n2 = 0;
                                        }
                                        bl3 = bl2;
                                        if (n2 >= n3) break block32;
                                        object = fieldInfoArray[n2];
                                        if (!((FieldInfo)object).getOnly) break block33;
                                        bl3 = false;
                                        break block32;
                                    }
                                    genericDeclaration = ((FieldInfo)object).fieldClass;
                                    if (Modifier.isPublic(((Class)genericDeclaration).getModifiers())) break block34;
                                    bl3 = false;
                                    break block32;
                                }
                                if (!((Class)genericDeclaration).isMemberClass() || Modifier.isStatic(((Class)genericDeclaration).getModifiers())) break block35;
                                bl3 = false;
                                break block32;
                            }
                            if (((FieldInfo)object).getMember() == null || ASMUtils.checkName(((FieldInfo)object).getMember().getName())) break block36;
                            bl3 = false;
                            break block32;
                        }
                        if ((object = ((FieldInfo)object).getAnnotation()) == null || ASMUtils.checkName(object.name())) break block37;
                        bl3 = false;
                        break block32;
                    }
                    if (!((Class)genericDeclaration).isEnum() || this.getDeserializer((Type)((Object)genericDeclaration)) instanceof EnumDeserializer) break block38;
                    bl3 = false;
                }
                bl2 = bl3;
                if (bl3) {
                    bl2 = bl3;
                    if (clazz.isMemberClass()) {
                        bl2 = bl3;
                        if (!Modifier.isStatic(clazz.getModifiers())) {
                            bl2 = false;
                        }
                    }
                }
                if (bl2) break;
                return new JavaBeanDeserializer(this, clazz, type);
            }
            ++n2;
        }
        try {
            return this.asmFactory.createJavaBeanDeserializer(this, clazz, type);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return new JavaBeanDeserializer(this, clazz, type);
        }
        catch (JSONException jSONException) {
            return new JavaBeanDeserializer(this, clazz, type);
        }
        catch (Exception exception) {
            throw new JSONException("create asm deserializer error, " + clazz.getName(), exception);
        }
    }

    public ClassLoader getDefaultClassLoader() {
        return this.defaultClassLoader;
    }

    public IdentityHashMap<Type, ObjectDeserializer> getDerializers() {
        return this.derializers;
    }

    public ObjectDeserializer getDeserializer(FieldInfo fieldInfo) {
        return this.getDeserializer(fieldInfo.fieldClass, fieldInfo.fieldType);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ObjectDeserializer getDeserializer(Class<?> object, Type object2) {
        void var6_9;
        Object object3 = this.derializers.get((Type)object2);
        if (object3 != null) {
            return object3;
        }
        Object object4 = object2;
        if (object2 == null) {
            object4 = object;
        }
        if ((object2 = this.derializers.get((Type)object4)) != null) {
            return object2;
        }
        object3 = ((Class)object).getAnnotation(JSONType.class);
        if (object3 != null && (object3 = object3.mappingTo()) != Void.class) {
            return this.getDeserializer((Class<?>)object3, (Type)object3);
        }
        if (object4 instanceof WildcardType || object4 instanceof TypeVariable || object4 instanceof ParameterizedType) {
            object2 = this.derializers.get((Type)object);
        }
        if (object2 != null) {
            return object2;
        }
        String string2 = ((Class)object).getName();
        for (int i2 = 0; i2 < this.denyList.length; ++i2) {
            object3 = this.denyList[i2];
            String string3 = var6_9.replace('$', '.');
            if (!string3.startsWith((String)object3)) continue;
            throw new JSONException("parser deny : " + string3);
        }
        object3 = object2;
        if (var6_9.startsWith("java.awt.")) {
            object3 = object2;
            if (AwtCodec.support(object)) {
                object3 = object2;
                if (!awtError) {
                    try {
                        this.derializers.put(Class.forName("java.awt.Point"), AwtCodec.instance);
                        this.derializers.put(Class.forName("java.awt.Font"), AwtCodec.instance);
                        this.derializers.put(Class.forName("java.awt.Rectangle"), AwtCodec.instance);
                        this.derializers.put(Class.forName("java.awt.Color"), AwtCodec.instance);
                    }
                    catch (Throwable throwable) {
                        awtError = true;
                    }
                    object3 = AwtCodec.instance;
                }
            }
        }
        object2 = object3;
        if (!jdk8Error) {
            try {
                if (var6_9.startsWith("java.time.")) {
                    this.derializers.put(Class.forName("java.time.LocalDateTime"), Jdk8DateCodec.instance);
                    this.derializers.put(Class.forName("java.time.LocalDate"), Jdk8DateCodec.instance);
                    this.derializers.put(Class.forName("java.time.LocalTime"), Jdk8DateCodec.instance);
                    this.derializers.put(Class.forName("java.time.ZonedDateTime"), Jdk8DateCodec.instance);
                    this.derializers.put(Class.forName("java.time.OffsetDateTime"), Jdk8DateCodec.instance);
                    this.derializers.put(Class.forName("java.time.OffsetTime"), Jdk8DateCodec.instance);
                    this.derializers.put(Class.forName("java.time.ZoneOffset"), Jdk8DateCodec.instance);
                    this.derializers.put(Class.forName("java.time.ZoneRegion"), Jdk8DateCodec.instance);
                    this.derializers.put(Class.forName("java.time.ZoneId"), Jdk8DateCodec.instance);
                    this.derializers.put(Class.forName("java.time.Period"), Jdk8DateCodec.instance);
                    this.derializers.put(Class.forName("java.time.Duration"), Jdk8DateCodec.instance);
                    this.derializers.put(Class.forName("java.time.Instant"), Jdk8DateCodec.instance);
                    object2 = this.derializers.get((Type)object);
                } else {
                    object2 = object3;
                    if (var6_9.startsWith("java.util.Optional")) {
                        this.derializers.put(Class.forName("java.util.Optional"), OptionalCodec.instance);
                        this.derializers.put(Class.forName("java.util.OptionalDouble"), OptionalCodec.instance);
                        this.derializers.put(Class.forName("java.util.OptionalInt"), OptionalCodec.instance);
                        this.derializers.put(Class.forName("java.util.OptionalLong"), OptionalCodec.instance);
                        object2 = this.derializers.get((Type)object);
                    }
                }
            }
            catch (Throwable throwable) {
                jdk8Error = true;
                object2 = object3;
            }
        }
        if (var6_9.equals("java.nio.file.Path")) {
            this.derializers.put((Type)object, MiscCodec.instance);
        }
        object3 = Thread.currentThread().getContextClassLoader();
        try {
            for (AutowiredObjectDeserializer autowiredObjectDeserializer : ServiceLoader.load(AutowiredObjectDeserializer.class, (ClassLoader)object3)) {
                for (Type type : autowiredObjectDeserializer.getAutowiredFor()) {
                    this.derializers.put(type, autowiredObjectDeserializer);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        object3 = object2;
        if (object2 == null) {
            object3 = this.derializers.get((Type)object4);
        }
        if (object3 != null) {
            return object3;
        }
        object = ((Class)object).isEnum() ? new EnumDeserializer((Class<?>)object) : (((Class)object).isArray() ? ObjectArrayCodec.instance : (object == Set.class || object == HashSet.class || object == Collection.class || object == List.class || object == ArrayList.class ? CollectionCodec.instance : (Collection.class.isAssignableFrom((Class<?>)object) ? CollectionCodec.instance : (Map.class.isAssignableFrom((Class<?>)object) ? MapDeserializer.instance : (Throwable.class.isAssignableFrom((Class<?>)object) ? new ThrowableDeserializer(this, (Class<?>)object) : this.createJavaBeanDeserializer((Class<?>)object, (Type)object4))))));
        this.putDeserializer((Type)object4, (ObjectDeserializer)object);
        return object;
    }

    public ObjectDeserializer getDeserializer(Type type) {
        Object object = this.derializers.get(type);
        if (object != null) {
            return object;
        }
        if (type instanceof Class) {
            return this.getDeserializer((Class)type, type);
        }
        if (type instanceof ParameterizedType) {
            object = ((ParameterizedType)type).getRawType();
            if (object instanceof Class) {
                return this.getDeserializer((Class)object, type);
            }
            return this.getDeserializer((Type)object);
        }
        return JavaObjectDeserializer.instance;
    }

    public boolean isAsmEnable() {
        return this.asmEnable;
    }

    public void putDeserializer(Type type, ObjectDeserializer objectDeserializer) {
        this.derializers.put(type, objectDeserializer);
    }

    public void setAsmEnable(boolean bl2) {
        this.asmEnable = bl2;
    }

    public void setDefaultClassLoader(ClassLoader classLoader) {
        this.defaultClassLoader = classLoader;
    }
}

