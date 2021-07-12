/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.deserializer.Jdk8DateCodec;
import com.alibaba.fastjson.parser.deserializer.OptionalCodec;
import com.alibaba.fastjson.serializer.ASMSerializerFactory;
import com.alibaba.fastjson.serializer.AppendableSerializer;
import com.alibaba.fastjson.serializer.ArraySerializer;
import com.alibaba.fastjson.serializer.AtomicCodec;
import com.alibaba.fastjson.serializer.AutowiredObjectSerializer;
import com.alibaba.fastjson.serializer.AwtCodec;
import com.alibaba.fastjson.serializer.BigDecimalCodec;
import com.alibaba.fastjson.serializer.BigIntegerCodec;
import com.alibaba.fastjson.serializer.BooleanCodec;
import com.alibaba.fastjson.serializer.CalendarCodec;
import com.alibaba.fastjson.serializer.CharacterCodec;
import com.alibaba.fastjson.serializer.ClobSeriliazer;
import com.alibaba.fastjson.serializer.CollectionCodec;
import com.alibaba.fastjson.serializer.DateCodec;
import com.alibaba.fastjson.serializer.DoubleSerializer;
import com.alibaba.fastjson.serializer.EnumSerializer;
import com.alibaba.fastjson.serializer.EnumerationSerializer;
import com.alibaba.fastjson.serializer.FloatCodec;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.JSONAwareSerializer;
import com.alibaba.fastjson.serializer.JSONSerializable;
import com.alibaba.fastjson.serializer.JSONSerializableSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ListSerializer;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.MapSerializer;
import com.alibaba.fastjson.serializer.MiscCodec;
import com.alibaba.fastjson.serializer.ObjectArrayCodec;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.PrimitiveArraySerializer;
import com.alibaba.fastjson.serializer.ReferenceCodec;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializeFilterable;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.util.ASMUtils;
import com.alibaba.fastjson.util.IdentityHashMap;
import com.alibaba.fastjson.util.ServiceLoader;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.File;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class SerializeConfig {
    private static boolean awtError;
    public static final SerializeConfig globalInstance;
    private static boolean jdk8Error;
    private static boolean oracleJdbcError;
    private boolean asm;
    private ASMSerializerFactory asmFactory;
    private final IdentityHashMap<Type, ObjectSerializer> serializers;
    private String typeKey;

    static {
        globalInstance = new SerializeConfig();
        awtError = false;
        jdk8Error = false;
        oracleJdbcError = false;
    }

    public SerializeConfig() {
        this(1024);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SerializeConfig(int n2) {
        boolean bl2 = !ASMUtils.IS_ANDROID;
        this.asm = bl2;
        this.typeKey = JSON.DEFAULT_TYPE_KEY;
        this.serializers = new IdentityHashMap(1024);
        try {
            if (this.asm) {
                this.asmFactory = new ASMSerializerFactory();
            }
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            this.asm = false;
        }
        catch (ExceptionInInitializerError exceptionInInitializerError) {
            this.asm = false;
        }
        this.put((Type)((Object)Boolean.class), BooleanCodec.instance);
        this.put((Type)((Object)Character.class), CharacterCodec.instance);
        this.put((Type)((Object)Byte.class), IntegerCodec.instance);
        this.put((Type)((Object)Short.class), IntegerCodec.instance);
        this.put((Type)((Object)Integer.class), IntegerCodec.instance);
        this.put((Type)((Object)Long.class), LongCodec.instance);
        this.put((Type)((Object)Float.class), FloatCodec.instance);
        this.put((Type)((Object)Double.class), DoubleSerializer.instance);
        this.put((Type)((Object)BigDecimal.class), BigDecimalCodec.instance);
        this.put((Type)((Object)BigInteger.class), BigIntegerCodec.instance);
        this.put((Type)((Object)String.class), StringCodec.instance);
        this.put((Type)((Object)byte[].class), PrimitiveArraySerializer.instance);
        this.put((Type)((Object)short[].class), PrimitiveArraySerializer.instance);
        this.put((Type)((Object)int[].class), PrimitiveArraySerializer.instance);
        this.put((Type)((Object)long[].class), PrimitiveArraySerializer.instance);
        this.put((Type)((Object)float[].class), PrimitiveArraySerializer.instance);
        this.put((Type)((Object)double[].class), PrimitiveArraySerializer.instance);
        this.put((Type)((Object)boolean[].class), PrimitiveArraySerializer.instance);
        this.put((Type)((Object)char[].class), PrimitiveArraySerializer.instance);
        this.put((Type)((Object)Object[].class), ObjectArrayCodec.instance);
        this.put((Type)((Object)Class.class), MiscCodec.instance);
        this.put((Type)((Object)SimpleDateFormat.class), MiscCodec.instance);
        this.put((Type)((Object)Currency.class), new MiscCodec());
        this.put((Type)((Object)TimeZone.class), MiscCodec.instance);
        this.put((Type)((Object)InetAddress.class), MiscCodec.instance);
        this.put((Type)((Object)Inet4Address.class), MiscCodec.instance);
        this.put((Type)((Object)Inet6Address.class), MiscCodec.instance);
        this.put((Type)((Object)InetSocketAddress.class), MiscCodec.instance);
        this.put((Type)((Object)File.class), MiscCodec.instance);
        this.put((Type)((Object)Appendable.class), AppendableSerializer.instance);
        this.put((Type)((Object)StringBuffer.class), AppendableSerializer.instance);
        this.put((Type)((Object)StringBuilder.class), AppendableSerializer.instance);
        this.put((Type)((Object)Charset.class), ToStringSerializer.instance);
        this.put((Type)((Object)Pattern.class), ToStringSerializer.instance);
        this.put((Type)((Object)Locale.class), ToStringSerializer.instance);
        this.put((Type)((Object)URI.class), ToStringSerializer.instance);
        this.put((Type)((Object)URL.class), ToStringSerializer.instance);
        this.put((Type)((Object)UUID.class), ToStringSerializer.instance);
        this.put((Type)((Object)AtomicBoolean.class), AtomicCodec.instance);
        this.put((Type)((Object)AtomicInteger.class), AtomicCodec.instance);
        this.put((Type)((Object)AtomicLong.class), AtomicCodec.instance);
        this.put((Type)((Object)AtomicReference.class), ReferenceCodec.instance);
        this.put((Type)((Object)AtomicIntegerArray.class), AtomicCodec.instance);
        this.put((Type)((Object)AtomicLongArray.class), AtomicCodec.instance);
        this.put((Type)((Object)WeakReference.class), ReferenceCodec.instance);
        this.put((Type)((Object)SoftReference.class), ReferenceCodec.instance);
    }

    public static SerializeConfig getGlobalInstance() {
        return globalInstance;
    }

    public void addFilter(Class<?> object, SerializeFilter serializeFilter) {
        if ((object = this.getObjectWriter((Class<?>)object)) instanceof SerializeFilterable) {
            ((SerializeFilterable)object).addFilter(serializeFilter);
        }
    }

    public final ObjectSerializer createASMSerializer(Class<?> clazz) throws Exception {
        return this.asmFactory.createJavaBeanSerializer(clazz, null);
    }

    /*
     * Handled impossible loop by adding 'first' condition
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ObjectSerializer createJavaBeanSerializer(Class<?> clazz) {
        void var6_3;
        if (!Modifier.isPublic(clazz.getModifiers())) {
            JavaBeanSerializer javaBeanSerializer = new JavaBeanSerializer(clazz);
            return var6_3;
        }
        boolean bl2 = this.asm;
        if (bl2 && this.asmFactory.classLoader.isExternalClass(clazz) || clazz == Serializable.class || clazz == Object.class) {
            bl2 = false;
        }
        JSONType jSONType = clazz.getAnnotation(JSONType.class);
        boolean bl3 = bl2;
        if (jSONType != null) {
            bl3 = bl2;
            if (!jSONType.asm()) {
                bl3 = false;
            }
        }
        bl2 = bl3;
        if (bl3) {
            bl2 = bl3;
            if (!ASMUtils.checkName(clazz.getName())) {
                bl2 = false;
            }
        }
        bl3 = bl2;
        boolean bl4 = true;
        while (true) {
            int n2;
            block15: {
                Object object;
                block14: {
                    int n3;
                    Field[] fieldArray;
                    block13: {
                        if (!bl4 || (bl4 = false)) break block13;
                        if (!bl2) break block14;
                        fieldArray = clazz.getDeclaredFields();
                        n3 = fieldArray.length;
                        n2 = 0;
                    }
                    bl3 = bl2;
                    if (n2 >= n3) break block14;
                    object = fieldArray[n2].getAnnotation(JSONField.class);
                    if (object == null || ASMUtils.checkName(object.name())) break block15;
                    bl3 = false;
                }
                if (!bl3) return new JavaBeanSerializer(clazz);
                object = this.createASMSerializer(clazz);
                ObjectSerializer objectSerializer = object;
                if (object != null) return var6_3;
                return new JavaBeanSerializer(clazz);
            }
            ++n2;
        }
        catch (Throwable throwable) {
            throw new JSONException("create asm serializer error, class " + clazz, throwable);
        }
        catch (ClassCastException classCastException) {
            return new JavaBeanSerializer(clazz);
        }
    }

    public final ObjectSerializer get(Type type) {
        return this.serializers.get(type);
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    public ObjectSerializer getObjectWriter(Class<?> var1_1) {
        block51: {
            block50: {
                block49: {
                    block48: {
                        block47: {
                            block46: {
                                block45: {
                                    block44: {
                                        block43: {
                                            block42: {
                                                block41: {
                                                    block40: {
                                                        block39: {
                                                            block38: {
                                                                block37: {
                                                                    block36: {
                                                                        block35: {
                                                                            block34: {
                                                                                var9_3 = this.serializers.get(var1_1);
                                                                                var8_5 /* !! */  = var9_3;
                                                                                if (var9_3 == null) {
                                                                                    try {
                                                                                        for (Object var9_3 : ServiceLoader.load(AutowiredObjectSerializer.class, Thread.currentThread().getContextClassLoader())) {
                                                                                            if (!(var9_3 instanceof AutowiredObjectSerializer)) continue;
                                                                                            var9_3 = (AutowiredObjectSerializer)var9_3;
                                                                                            var10_9 = var9_3.getAutowiredFor().iterator();
                                                                                            while (var10_9.hasNext()) {
                                                                                                this.put((Type)var10_9.next(), (ObjectSerializer)var9_3);
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    catch (ClassCastException var8_6) {
                                                                                        // empty catch block
                                                                                    }
                                                                                    var8_5 /* !! */  = this.serializers.get(var1_1);
                                                                                }
                                                                                var9_3 = var8_5 /* !! */ ;
                                                                                if (var8_5 /* !! */  == null) {
                                                                                    var10_9 = JSON.class.getClassLoader();
                                                                                    var9_3 = var8_5 /* !! */ ;
                                                                                    if (var10_9 != Thread.currentThread().getContextClassLoader()) {
                                                                                        try {
                                                                                            for (Object var9_3 : ServiceLoader.load(AutowiredObjectSerializer.class, (ClassLoader)var10_9)) {
                                                                                                if (!(var9_3 instanceof AutowiredObjectSerializer)) continue;
                                                                                                var9_3 = (AutowiredObjectSerializer)var9_3;
                                                                                                var10_9 = var9_3.getAutowiredFor().iterator();
                                                                                                while (var10_9.hasNext()) {
                                                                                                    this.put(var10_9.next(), (ObjectSerializer)var9_3);
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                        catch (ClassCastException var8_7) {
                                                                                            // empty catch block
                                                                                        }
                                                                                        var9_3 = this.serializers.get(var1_1);
                                                                                    }
                                                                                }
                                                                                var8_5 /* !! */  = var9_3;
                                                                                if (var9_3 != null) ** GOTO lbl44
                                                                                if (!Map.class.isAssignableFrom(var1_1)) break block34;
                                                                                this.put(var1_1, MapSerializer.instance);
lbl42:
                                                                                // 19 sources

                                                                                while (true) {
                                                                                    var8_5 /* !! */  = this.serializers.get(var1_1);
lbl44:
                                                                                    // 2 sources

                                                                                    return var8_5 /* !! */ ;
                                                                                }
                                                                            }
                                                                            if (!List.class.isAssignableFrom(var1_1)) break block35;
                                                                            this.put(var1_1, ListSerializer.instance);
                                                                            ** GOTO lbl42
                                                                        }
                                                                        if (!Collection.class.isAssignableFrom(var1_1)) break block36;
                                                                        this.put(var1_1, CollectionCodec.instance);
                                                                        ** GOTO lbl42
                                                                    }
                                                                    if (!Date.class.isAssignableFrom(var1_1)) break block37;
                                                                    this.put(var1_1, DateCodec.instance);
                                                                    ** GOTO lbl42
                                                                }
                                                                if (!JSONAware.class.isAssignableFrom(var1_1)) break block38;
                                                                this.put(var1_1, JSONAwareSerializer.instance);
                                                                ** GOTO lbl42
                                                            }
                                                            if (!JSONSerializable.class.isAssignableFrom(var1_1)) break block39;
                                                            this.put(var1_1, JSONSerializableSerializer.instance);
                                                            ** GOTO lbl42
                                                        }
                                                        if (!JSONStreamAware.class.isAssignableFrom(var1_1)) break block40;
                                                        this.put(var1_1, MiscCodec.instance);
                                                        ** GOTO lbl42
                                                    }
                                                    if (!var1_1.isEnum() && (var1_1.getSuperclass() == null || !var1_1.getSuperclass().isEnum())) break block41;
                                                    this.put(var1_1, EnumSerializer.instance);
                                                    ** GOTO lbl42
                                                }
                                                if (!var1_1.isArray()) break block42;
                                                var8_5 /* !! */  = var1_1.getComponentType();
                                                this.put(var1_1, new ArraySerializer((Class<?>)var8_5 /* !! */ , this.getObjectWriter((Class<?>)var8_5 /* !! */ )));
                                                ** GOTO lbl42
                                            }
                                            if (!Throwable.class.isAssignableFrom(var1_1)) break block43;
                                            this.put(var1_1, new JavaBeanSerializer(var1_1, null, TypeUtils.getSerializeFeatures(var1_1) | SerializerFeature.WriteClassName.mask));
                                            ** GOTO lbl42
                                        }
                                        if (!TimeZone.class.isAssignableFrom(var1_1)) break block44;
                                        this.put(var1_1, MiscCodec.instance);
                                        ** GOTO lbl42
                                    }
                                    if (!Appendable.class.isAssignableFrom(var1_1)) break block45;
                                    this.put(var1_1, AppendableSerializer.instance);
                                    ** GOTO lbl42
                                }
                                if (!Charset.class.isAssignableFrom(var1_1)) break block46;
                                this.put(var1_1, ToStringSerializer.instance);
                                ** GOTO lbl42
                            }
                            if (!Enumeration.class.isAssignableFrom(var1_1)) break block47;
                            this.put(var1_1, EnumerationSerializer.instance);
                            ** GOTO lbl42
                        }
                        if (!Calendar.class.isAssignableFrom(var1_1)) break block48;
                        this.put(var1_1, CalendarCodec.instance);
                        ** GOTO lbl42
                    }
                    if (!Clob.class.isAssignableFrom(var1_1)) break block49;
                    this.put(var1_1, ClobSeriliazer.instance);
                    ** GOTO lbl42
                }
                if (!TypeUtils.isPath(var1_1)) break block50;
                this.put(var1_1, ToStringSerializer.instance);
                ** GOTO lbl42
            }
            if (!Iterable.class.isAssignableFrom(var1_1) && !Iterator.class.isAssignableFrom(var1_1)) break block51;
            this.put(var1_1, MiscCodec.instance);
            ** GOTO lbl42
        }
        var8_5 /* !! */  = var1_1.getName();
        if (var8_5 /* !! */ .startsWith("java.awt.") && AwtCodec.support(var1_1)) {
            if (!SerializeConfig.awtError) {
                this.put(Class.forName("java.awt.Color"), AwtCodec.instance);
                this.put(Class.forName("java.awt.Font"), AwtCodec.instance);
                this.put(Class.forName("java.awt.Point"), AwtCodec.instance);
                this.put(Class.forName("java.awt.Rectangle"), AwtCodec.instance);
            }
lbl144:
            // 4 sources

            return AwtCodec.instance;
            catch (Throwable var1_2) {
                SerializeConfig.awtError = true;
                ** continue;
            }
        }
        if (!SerializeConfig.jdk8Error && (var8_5 /* !! */ .startsWith("java.time.") || var8_5 /* !! */ .startsWith("java.util.Optional"))) {
            try {
                this.put(Class.forName("java.time.LocalDateTime"), Jdk8DateCodec.instance);
                this.put(Class.forName("java.time.LocalDate"), Jdk8DateCodec.instance);
                this.put(Class.forName("java.time.LocalTime"), Jdk8DateCodec.instance);
                this.put(Class.forName("java.time.ZonedDateTime"), Jdk8DateCodec.instance);
                this.put(Class.forName("java.time.OffsetDateTime"), Jdk8DateCodec.instance);
                this.put(Class.forName("java.time.OffsetTime"), Jdk8DateCodec.instance);
                this.put(Class.forName("java.time.ZoneOffset"), Jdk8DateCodec.instance);
                this.put(Class.forName("java.time.ZoneRegion"), Jdk8DateCodec.instance);
                this.put(Class.forName("java.time.Period"), Jdk8DateCodec.instance);
                this.put(Class.forName("java.time.Duration"), Jdk8DateCodec.instance);
                this.put(Class.forName("java.time.Instant"), Jdk8DateCodec.instance);
                this.put(Class.forName("java.util.Optional"), OptionalCodec.instance);
                this.put(Class.forName("java.util.OptionalDouble"), OptionalCodec.instance);
                this.put(Class.forName("java.util.OptionalInt"), OptionalCodec.instance);
                this.put(Class.forName("java.util.OptionalLong"), OptionalCodec.instance);
                var9_3 = this.serializers.get(var1_1);
                if (var9_3 != null) {
                    return var9_3;
                }
            }
            catch (Throwable var9_4) {
                SerializeConfig.jdk8Error = true;
            }
        }
        if (!SerializeConfig.oracleJdbcError && var8_5 /* !! */ .startsWith("oracle.sql.")) {
            try {
                this.put(Class.forName("oracle.sql.DATE"), DateCodec.instance);
                this.put(Class.forName("oracle.sql.TIMESTAMP"), DateCodec.instance);
                var8_5 /* !! */  = this.serializers.get(var1_1);
                if (var8_5 /* !! */  != null) {
                    return var8_5 /* !! */ ;
                }
            }
            catch (Throwable var8_8) {
                SerializeConfig.oracleJdbcError = true;
            }
        }
        var5_10 = false;
        var6_11 = false;
        var8_5 /* !! */  = var1_1.getInterfaces();
        var7_12 = var8_5 /* !! */ .length;
        var4_13 = 0;
        while (true) {
            block54: {
                block53: {
                    block52: {
                        var2_14 = var5_10;
                        var3_15 = var6_11;
                        if (var4_13 >= var7_12) break block52;
                        var9_3 = var8_5 /* !! */ [var4_13].getName();
                        if (!var9_3.equals("net.sf.cglib.proxy.Factory") && !var9_3.equals("org.springframework.cglib.proxy.Factory")) break block53;
                        var2_14 = true;
                        var3_15 = var6_11;
                    }
lbl213:
                    // 2 sources

                    while (var2_14 || var3_15) {
                        var8_5 /* !! */  = this.getObjectWriter(var1_1.getSuperclass());
                        this.put(var1_1, (ObjectSerializer)var8_5 /* !! */ );
                        return var8_5 /* !! */ ;
                    }
                    break;
                }
                if (!var9_3.equals("javassist.util.proxy.ProxyObject") && !var9_3.equals("org.apache.ibatis.javassist.util.proxy.ProxyObject")) break block54;
                var3_15 = true;
                var2_14 = var5_10;
                ** GOTO lbl213
            }
            ++var4_13;
        }
        this.put(var1_1, this.createJavaBeanSerializer(var1_1));
        ** while (true)
    }

    public String getTypeKey() {
        return this.typeKey;
    }

    public boolean isAsmEnable() {
        return this.asm;
    }

    public boolean put(Type type, ObjectSerializer objectSerializer) {
        return this.serializers.put(type, objectSerializer);
    }

    public void setAsmEnable(boolean bl2) {
        if (ASMUtils.IS_ANDROID) {
            return;
        }
        this.asm = bl2;
    }

    public void setTypeKey(String string2) {
        this.typeKey = string2;
    }
}

