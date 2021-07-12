/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexerBase;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.IOUtils;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.AccessControlException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TypeUtils {
    public static boolean compatibleWithJavaBean = false;
    private static ConcurrentMap<String, Class<?>> mappings;
    private static Class<?> optionalClass;
    private static boolean optionalClassInited;
    private static Method oracleDateMethod;
    private static boolean oracleDateMethodInited;
    private static Method oracleTimestampMethod;
    private static boolean oracleTimestampMethodInited;
    private static Class<?> pathClass;
    private static boolean pathClass_error;
    private static boolean setAccessibleEnable;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        setAccessibleEnable = true;
        oracleTimestampMethodInited = false;
        oracleDateMethodInited = false;
        optionalClassInited = false;
        try {
            String string2 = System.getProperty("fastjson.compatibleWithJavaBean");
            if ("true".equals(string2)) {
                compatibleWithJavaBean = true;
            } else if ("false".equals(string2)) {
                compatibleWithJavaBean = false;
            }
        }
        catch (Throwable throwable) {}
        mappings = new ConcurrentHashMap();
        TypeUtils.addBaseClassMappings();
        pathClass_error = false;
    }

    private static void addBaseClassMappings() {
        mappings.put("byte", Byte.TYPE);
        mappings.put("short", Short.TYPE);
        mappings.put("int", Integer.TYPE);
        mappings.put("long", Long.TYPE);
        mappings.put("float", Float.TYPE);
        mappings.put("double", Double.TYPE);
        mappings.put("boolean", Boolean.TYPE);
        mappings.put("char", Character.TYPE);
        mappings.put("[byte", byte[].class);
        mappings.put("[short", short[].class);
        mappings.put("[int", int[].class);
        mappings.put("[long", long[].class);
        mappings.put("[float", float[].class);
        mappings.put("[double", double[].class);
        mappings.put("[boolean", boolean[].class);
        mappings.put("[char", char[].class);
        mappings.put(HashMap.class.getName(), HashMap.class);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T> T cast(Object object, Class<T> clazz, ParserConfig object2) {
        Iterator iterator;
        if (object == null) {
            iterator = null;
            return (T)iterator;
        }
        if (clazz == null) {
            throw new IllegalArgumentException("clazz is null");
        }
        iterator = object;
        if (clazz == object.getClass()) return (T)iterator;
        if (object instanceof Map) {
            iterator = object;
            if (clazz == Map.class) return (T)iterator;
            Map map = (Map)object;
            if (clazz != Object.class) return TypeUtils.castToJavaBean((Map)object, clazz, (ParserConfig)object2);
            iterator = object;
            if (!map.containsKey(JSON.DEFAULT_TYPE_KEY)) return (T)iterator;
            return TypeUtils.castToJavaBean((Map)object, clazz, (ParserConfig)object2);
        }
        if (clazz.isArray()) {
            if (object instanceof Collection) {
                iterator = (Collection)object;
                int n2 = 0;
                object = Array.newInstance(clazz.getComponentType(), iterator.size());
                iterator = iterator.iterator();
                while (true) {
                    if (!iterator.hasNext()) {
                        return (T)object;
                    }
                    Array.set(object, n2, TypeUtils.cast(iterator.next(), clazz.getComponentType(), (ParserConfig)object2));
                    ++n2;
                }
            }
            if (clazz == byte[].class) {
                return (T)TypeUtils.castToBytes(object);
            }
        }
        iterator = object;
        if (clazz.isAssignableFrom(object.getClass())) return (T)iterator;
        if (clazz == Boolean.TYPE || clazz == Boolean.class) {
            return (T)TypeUtils.castToBoolean(object);
        }
        if (clazz == Byte.TYPE || clazz == Byte.class) {
            return (T)TypeUtils.castToByte(object);
        }
        if (clazz == Short.TYPE || clazz == Short.class) {
            return (T)TypeUtils.castToShort(object);
        }
        if (clazz == Integer.TYPE || clazz == Integer.class) {
            return (T)TypeUtils.castToInt(object);
        }
        if (clazz == Long.TYPE || clazz == Long.class) {
            return (T)TypeUtils.castToLong(object);
        }
        if (clazz == Float.TYPE || clazz == Float.class) {
            return (T)TypeUtils.castToFloat(object);
        }
        if (clazz == Double.TYPE || clazz == Double.class) {
            return (T)TypeUtils.castToDouble(object);
        }
        if (clazz == String.class) {
            return (T)TypeUtils.castToString(object);
        }
        if (clazz == BigDecimal.class) {
            return (T)TypeUtils.castToBigDecimal(object);
        }
        if (clazz == BigInteger.class) {
            return (T)TypeUtils.castToBigInteger(object);
        }
        if (clazz == java.util.Date.class) {
            return (T)TypeUtils.castToDate(object);
        }
        if (clazz == Date.class) {
            return (T)TypeUtils.castToSqlDate(object);
        }
        if (clazz == Timestamp.class) {
            return (T)TypeUtils.castToTimestamp(object);
        }
        if (clazz.isEnum()) {
            return TypeUtils.castToEnum(object, clazz, (ParserConfig)object2);
        }
        if (Calendar.class.isAssignableFrom(clazz)) {
            object2 = TypeUtils.castToDate(object);
            if (clazz == Calendar.class) {
                object = Calendar.getInstance(JSON.defaultTimeZone, JSON.defaultLocale);
            } else {
                object = (Calendar)clazz.newInstance();
            }
            ((Calendar)object).setTime((java.util.Date)object2);
            return (T)object;
            catch (Exception exception) {
                throw new JSONException("can not cast to : " + clazz.getName(), exception);
            }
        }
        if (!(object instanceof String)) throw new JSONException("can not cast to : " + clazz.getName());
        if (((String)(object = (String)object)).length() == 0 || "null".equals(object) || "NULL".equals(object)) {
            return null;
        }
        if (clazz != Currency.class) throw new JSONException("can not cast to : " + clazz.getName());
        return (T)Currency.getInstance((String)object);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static <T> T cast(Object object, ParameterizedType object2, ParserConfig parserConfig) {
        Type type;
        Type type2 = object2.getRawType();
        if (type2 == Set.class || type2 == HashSet.class || type2 == TreeSet.class || type2 == List.class || type2 == ArrayList.class) {
            type = object2.getActualTypeArguments()[0];
            if (object instanceof Iterable) {
                object2 = type2 == Set.class || type2 == HashSet.class ? new HashSet() : (type2 == TreeSet.class ? new TreeSet() : new ArrayList());
                Iterator iterator = ((Iterable)object).iterator();
                while (true) {
                    object = object2;
                    if (!iterator.hasNext()) return (T)object;
                    object2.add(TypeUtils.cast(iterator.next(), type, parserConfig));
                }
            }
        }
        if (type2 == Map.class || type2 == HashMap.class) {
            type = object2.getActualTypeArguments()[0];
            Type type3 = object2.getActualTypeArguments()[1];
            if (object instanceof Map) {
                object2 = new HashMap();
                for (Map.Entry entry : ((Map)object).entrySet()) {
                    object2.put(TypeUtils.cast(entry.getKey(), type, parserConfig), TypeUtils.cast(entry.getValue(), type3, parserConfig));
                }
                object = object2;
                return (T)object;
            }
        }
        if (object instanceof String && ((String)object).length() == 0) {
            return null;
        }
        if (object2.getActualTypeArguments().length != 1 || !(object2.getActualTypeArguments()[0] instanceof WildcardType)) throw new JSONException("can not cast to : " + object2);
        return TypeUtils.cast(object, type2, parserConfig);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static <T> T cast(Object object, Type type, ParserConfig object2) {
        if (object == null) {
            object = null;
            return (T)object;
        } else {
            if (type instanceof Class) {
                return TypeUtils.cast(object, (Class)type, (ParserConfig)object2);
            }
            if (type instanceof ParameterizedType) {
                return TypeUtils.cast(object, (ParameterizedType)type, (ParserConfig)object2);
            }
            if (object instanceof String && (((String)(object2 = (String)object)).length() == 0 || "null".equals(object2) || "NULL".equals(object2))) {
                return null;
            }
            if (type instanceof TypeVariable) return (T)object;
            throw new JSONException("can not cast to : " + type);
        }
    }

    public static BigDecimal castToBigDecimal(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof BigDecimal) {
            return (BigDecimal)object;
        }
        if (object instanceof BigInteger) {
            return new BigDecimal((BigInteger)object);
        }
        if (((String)(object = object.toString())).length() == 0) {
            return null;
        }
        return new BigDecimal((String)object);
    }

    public static BigInteger castToBigInteger(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof BigInteger) {
            return (BigInteger)object;
        }
        if (object instanceof Float || object instanceof Double) {
            return BigInteger.valueOf(((Number)object).longValue());
        }
        if (((String)(object = object.toString())).length() == 0 || "null".equals(object) || "NULL".equals(object)) {
            return null;
        }
        return new BigInteger((String)object);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Boolean castToBoolean(Object object) {
        boolean bl2 = true;
        if (object == null) {
            return null;
        }
        if (object instanceof Boolean) {
            return (Boolean)object;
        }
        if (object instanceof Number) {
            if (((Number)object).intValue() == 1) {
                return bl2;
            }
            bl2 = false;
            return bl2;
        }
        if (!(object instanceof String)) throw new JSONException("can not cast to boolean, value : " + object);
        String string2 = (String)object;
        if (string2.length() == 0 || "null".equals(string2) || "NULL".equals(string2)) {
            return null;
        }
        if ("true".equalsIgnoreCase(string2) || "1".equals(string2)) {
            return Boolean.TRUE;
        }
        if (!"false".equalsIgnoreCase(string2) && !"0".equals(string2)) throw new JSONException("can not cast to boolean, value : " + object);
        return Boolean.FALSE;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Byte castToByte(Object object) {
        block6: {
            block5: {
                if (object == null) break block5;
                if (object instanceof Number) {
                    return ((Number)object).byteValue();
                }
                if (!(object instanceof String)) {
                    throw new JSONException("can not cast to byte, value : " + object);
                }
                if (((String)(object = (String)object)).length() != 0 && !"null".equals(object) && !"NULL".equals(object)) break block6;
            }
            return null;
        }
        return Byte.parseByte((String)object);
    }

    public static byte[] castToBytes(Object object) {
        if (object instanceof byte[]) {
            return (byte[])object;
        }
        if (object instanceof String) {
            return IOUtils.decodeBase64((String)object);
        }
        throw new JSONException("can not cast to int, value : " + object);
    }

    public static Character castToChar(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Character) {
            return (Character)object;
        }
        if (object instanceof String) {
            String string2 = (String)object;
            if (string2.length() == 0) {
                return null;
            }
            if (string2.length() != 1) {
                throw new JSONException("can not cast to char, value : " + object);
            }
            return Character.valueOf(string2.charAt(0));
        }
        throw new JSONException("can not cast to char, value : " + object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static java.util.Date castToDate(Object object) {
        block31: {
            Class<?> clazz;
            if (object == null) {
                return null;
            }
            if (object instanceof java.util.Date) {
                return (java.util.Date)object;
            }
            if (object instanceof Calendar) {
                return ((Calendar)object).getTime();
            }
            long l2 = -1L;
            if (object instanceof Number) {
                return new java.util.Date(((Number)object).longValue());
            }
            if (object instanceof String) {
                block29: {
                    String string2 = (String)object;
                    clazz = new JSONScanner(string2);
                    try {
                        if (((JSONScanner)((Object)clazz)).scanISO8601DateIfMatch(false)) {
                            object = ((JSONLexerBase)((Object)clazz)).getCalendar().getTime();
                            return object;
                        }
                        clazz = string2;
                        if (string2.startsWith("/Date(")) {
                            clazz = string2;
                            if (string2.endsWith(")/")) {
                                clazz = string2.substring(6, string2.length() - 2);
                            }
                        }
                        if (((String)((Object)clazz)).indexOf(45) != -1) {
                            object = ((String)((Object)clazz)).length() == JSON.DEFFAULT_DATE_FORMAT.length() ? JSON.DEFFAULT_DATE_FORMAT : (((String)((Object)clazz)).length() == 10 ? "yyyy-MM-dd" : (((String)((Object)clazz)).length() == "yyyy-MM-dd HH:mm:ss".length() ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd HH:mm:ss.SSS"));
                        }
                        break block29;
                    }
                    finally {
                        ((JSONLexerBase)((Object)clazz)).close();
                    }
                    object = new SimpleDateFormat((String)object, JSON.defaultLocale);
                    ((DateFormat)object).setTimeZone(JSON.defaultTimeZone);
                    try {
                        return ((DateFormat)object).parse((String)((Object)clazz));
                    }
                    catch (ParseException parseException) {
                        throw new JSONException("can not cast to Date, value : " + (String)((Object)clazz));
                    }
                }
                if (((String)((Object)clazz)).length() == 0) return null;
                l2 = Long.parseLong((String)((Object)clazz));
            }
            if (l2 >= 0L) return new java.util.Date(l2);
            clazz = object.getClass();
            if ("oracle.sql.TIMESTAMP".equals(clazz.getName())) {
                block30: {
                    if (oracleTimestampMethod == null && !oracleTimestampMethodInited) {
                        try {
                            oracleTimestampMethod = clazz.getMethod("toJdbc", new Class[0]);
                            break block30;
                        }
                        catch (NoSuchMethodException noSuchMethodException) {}
                        break block30;
                        finally {
                            oracleTimestampMethodInited = true;
                        }
                    }
                }
                try {
                    object = oracleTimestampMethod.invoke(object, new Object[0]);
                }
                catch (Exception exception) {
                    throw new JSONException("can not cast oracle.sql.TIMESTAMP to Date", exception);
                }
                return (java.util.Date)object;
            }
            if (!"oracle.sql.DATE".equals(clazz.getName())) throw new JSONException("can not cast to Date, value : " + object);
            if (oracleDateMethod == null && !oracleDateMethodInited) {
                try {
                    oracleDateMethod = clazz.getMethod("toJdbc", new Class[0]);
                    break block31;
                }
                catch (NoSuchMethodException noSuchMethodException) {}
                break block31;
                finally {
                    oracleDateMethodInited = true;
                }
            }
        }
        try {
            object = oracleDateMethod.invoke(object, new Object[0]);
        }
        catch (Exception exception) {
            throw new JSONException("can not cast oracle.sql.DATE to Date", exception);
        }
        return (java.util.Date)object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Double castToDouble(Object object) {
        String string2;
        block7: {
            block6: {
                if (object == null) break block6;
                if (object instanceof Number) {
                    return ((Number)object).doubleValue();
                }
                if (!(object instanceof String)) {
                    throw new JSONException("can not cast to double, value : " + object);
                }
                string2 = object.toString();
                if (string2.length() != 0 && !"null".equals(string2) && !"NULL".equals(string2)) break block7;
            }
            return null;
        }
        object = string2;
        if (string2.indexOf(44) != 0) {
            object = string2.replaceAll(",", "");
        }
        return Double.parseDouble((String)object);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T> T castToEnum(Object object, Class<T> clazz, ParserConfig parserConfig) {
        void var1_5;
        block4: {
            T[] TArray;
            int n2;
            try {
                if (object instanceof String) {
                    String string2 = (String)object;
                    if (string2.length() == 0) {
                        return null;
                    }
                    return Enum.valueOf(var1_5, string2);
                }
                if (!(object instanceof Number) || (n2 = ((Number)object).intValue()) >= (TArray = var1_5.getEnumConstants()).length) break block4;
            }
            catch (Exception exception) {
                throw new JSONException("can not cast to : " + var1_5.getName(), exception);
            }
            Object t2 = TArray[n2];
            return t2;
        }
        throw new JSONException("can not cast to : " + var1_5.getName());
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Float castToFloat(Object object) {
        String string2;
        block7: {
            block6: {
                if (object == null) break block6;
                if (object instanceof Number) {
                    return Float.valueOf(((Number)object).floatValue());
                }
                if (!(object instanceof String)) {
                    throw new JSONException("can not cast to float, value : " + object);
                }
                string2 = object.toString();
                if (string2.length() != 0 && !"null".equals(string2) && !"NULL".equals(string2)) break block7;
            }
            return null;
        }
        object = string2;
        if (string2.indexOf(44) != 0) {
            object = string2.replaceAll(",", "");
        }
        return Float.valueOf(Float.parseFloat((String)object));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Integer castToInt(Object object) {
        int n2;
        if (object == null) {
            return null;
        }
        if (object instanceof Integer) {
            return (Integer)object;
        }
        if (object instanceof Number) {
            return ((Number)object).intValue();
        }
        if (object instanceof String) {
            String string2 = (String)object;
            if (string2.length() == 0 || "null".equals(string2) || "NULL".equals(string2)) {
                return null;
            }
            object = string2;
            if (string2.indexOf(44) == 0) return Integer.parseInt((String)object);
            object = string2.replaceAll(",", "");
            return Integer.parseInt((String)object);
        }
        if (!(object instanceof Boolean)) throw new JSONException("can not cast to int, value : " + object);
        if (((Boolean)object).booleanValue()) {
            n2 = 1;
            return n2;
        }
        n2 = 0;
        return n2;
    }

    public static <T> T castToJavaBean(Object object, Class<T> clazz) {
        return TypeUtils.cast(object, clazz, ParserConfig.getGlobalInstance());
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T> T castToJavaBean(Map<String, Object> var0, Class<T> var1_2, ParserConfig var2_3) {
        if (var1_2 != StackTraceElement.class) ** GOTO lbl12
        try {
            var1_2 = (String)var0.get("className");
            var2_3 = (String)var0.get("methodName");
            var4_4 = (String)var0.get("fileName");
            var0 = (Number)var0.get("lineNumber");
            if (var0 == null) {
                var3_6 = 0;
                return (T)new StackTraceElement((String)var1_2, (String)var2_3, var4_4, var3_6);
            }
            var3_6 = var0.intValue();
            return (T)new StackTraceElement((String)var1_2, (String)var2_3, var4_4, var3_6);
lbl12:
            // 1 sources

            var4_5 = var0.get(JSON.DEFAULT_TYPE_KEY);
            if (var4_5 instanceof String) {
                var5_7 = TypeUtils.loadClass((String)(var4_5 = (String)var4_5));
                if (var5_7 == null) {
                    throw new ClassNotFoundException((String)var4_5 + " not found");
                }
            }
            ** GOTO lbl23
        }
        catch (Exception var0_1) {
            throw new JSONException(var0_1.getMessage(), var0_1);
        }
        {
            if (!var5_7.equals(var1_2)) {
                return TypeUtils.castToJavaBean((Map<String, Object>)var0, var5_7, (ParserConfig)var2_3);
            }
lbl23:
            // 3 sources

            if (var1_2.isInterface()) {
                var0 = var0 instanceof JSONObject != false ? (JSONObject)var0 : new JSONObject((Map<String, Object>)var0);
                return (T)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{var1_2}, (InvocationHandler)var0);
            }
            var4_5 = var2_3;
            if (var2_3 == null) {
                var4_5 = ParserConfig.getGlobalInstance();
            }
            var2_3 = null;
            var5_7 = var4_5.getDeserializer((Type)var1_2);
            var1_2 = var2_3;
            if (var5_7 instanceof JavaBeanDeserializer) {
                var1_2 = (JavaBeanDeserializer)var5_7;
            }
            if (var1_2 == null) {
                throw new JSONException("can not get javaBeanDeserializer");
            }
            var0 = var1_2.createInstance((Map<String, Object>)var0, (ParserConfig)var4_5);
        }
        return (T)var0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Long castToLong(Object object) {
        if (object == null) return null;
        if (object instanceof Number) {
            return ((Number)object).longValue();
        }
        if (!(object instanceof String)) throw new JSONException("can not cast to long, value : " + object);
        String string2 = (String)object;
        if (string2.length() == 0 || "null".equals(string2) || "NULL".equals(string2)) {
            return null;
        }
        Object object2 = string2;
        if (string2.indexOf(44) != 0) {
            object2 = string2.replaceAll(",", "");
        }
        try {
            long l2 = Long.parseLong((String)object2);
            return l2;
        }
        catch (NumberFormatException numberFormatException) {
            JSONScanner jSONScanner = new JSONScanner((String)object2);
            object2 = null;
            if (jSONScanner.scanISO8601DateIfMatch(false)) {
                object2 = jSONScanner.getCalendar();
            }
            jSONScanner.close();
            if (object2 == null) throw new JSONException("can not cast to long, value : " + object);
            return ((Calendar)object2).getTimeInMillis();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Short castToShort(Object object) {
        block6: {
            block5: {
                if (object == null) break block5;
                if (object instanceof Number) {
                    return ((Number)object).shortValue();
                }
                if (!(object instanceof String)) {
                    throw new JSONException("can not cast to short, value : " + object);
                }
                if (((String)(object = (String)object)).length() != 0 && !"null".equals(object) && !"NULL".equals(object)) break block6;
            }
            return null;
        }
        return Short.parseShort((String)object);
    }

    public static Date castToSqlDate(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Date) {
            return (Date)object;
        }
        if (object instanceof java.util.Date) {
            return new Date(((java.util.Date)object).getTime());
        }
        if (object instanceof Calendar) {
            return new Date(((Calendar)object).getTimeInMillis());
        }
        long l2 = 0L;
        if (object instanceof Number) {
            l2 = ((Number)object).longValue();
        }
        if (object instanceof String) {
            String string2 = (String)object;
            if (string2.length() == 0 || "null".equals(string2) || "NULL".equals(string2)) {
                return null;
            }
            l2 = Long.parseLong(string2);
        }
        if (l2 <= 0L) {
            throw new JSONException("can not cast to Date, value : " + object);
        }
        return new Date(l2);
    }

    public static String castToString(Object object) {
        if (object == null) {
            return null;
        }
        return object.toString();
    }

    public static Timestamp castToTimestamp(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Calendar) {
            return new Timestamp(((Calendar)object).getTimeInMillis());
        }
        if (object instanceof Timestamp) {
            return (Timestamp)object;
        }
        if (object instanceof java.util.Date) {
            return new Timestamp(((java.util.Date)object).getTime());
        }
        long l2 = 0L;
        if (object instanceof Number) {
            l2 = ((Number)object).longValue();
        }
        if (object instanceof String) {
            String string2 = (String)object;
            if (string2.length() == 0 || "null".equals(string2) || "NULL".equals(string2)) {
                return null;
            }
            l2 = Long.parseLong(string2);
        }
        if (l2 <= 0L) {
            throw new JSONException("can not cast to Date, value : " + object);
        }
        return new Timestamp(l2);
    }

    public static void clearClassMapping() {
        mappings.clear();
        TypeUtils.addBaseClassMappings();
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    public static List<FieldInfo> computeGetters(Class<?> var0, JSONType var1_1, Map<String, String> var2_2, boolean var3_3) {
        block23: {
            block26: {
                block27: {
                    block28: {
                        block39: {
                            var17_4 = new LinkedHashMap<Object, FieldInfo>();
                            block0: for (Method var19_19 : var0.getMethods()) {
                                block37: {
                                    block24: {
                                        block25: {
                                            block36: {
                                                block35: {
                                                    block31: {
                                                        block32: {
                                                            block29: {
                                                                block30: {
                                                                    var16_18 = var19_19.getName();
                                                                    var5_9 = 0;
                                                                    var6_10 = 0;
                                                                    var1_1 = null;
                                                                    if (Modifier.isStatic(var19_19.getModifiers())) lbl-1000:
                                                                    // 16 sources

                                                                    {
                                                                        continue block0;
                                                                    }
                                                                    if (var19_19.getReturnType().equals(Void.TYPE) || var19_19.getParameterTypes().length != 0 || var19_19.getReturnType() == ClassLoader.class || var19_19.getName().equals("getMetaClass") && var19_19.getReturnType().getName().equals("groovy.lang.MetaClass")) ** GOTO lbl-1000
                                                                    var14_16 = var11_13 = var19_19.getAnnotation(JSONField.class);
                                                                    if (var11_13 == null) {
                                                                        var14_16 = TypeUtils.getSupperMethodAnnotation(var0, var19_19);
                                                                    }
                                                                    var11_13 = var1_1;
                                                                    if (var14_16 == null) break block29;
                                                                    if (!var14_16.serialize()) ** GOTO lbl-1000
                                                                    var7_11 = var14_16.ordinal();
                                                                    var8_12 = SerializerFeature.of(var14_16.serialzeFeatures());
                                                                    if (var14_16.name().length() == 0) break block30;
                                                                    var1_1 = var11_13 = var14_16.name();
                                                                    if (var2_2 == null || (var1_1 = (String)var2_2.get(var11_13)) != null) {
                                                                        var17_4.put(var1_1, new FieldInfo((String)var1_1, var19_19, null, (Class<?>)var0, null, var7_11, var8_12, (JSONField)var14_16, null, null));
                                                                    }
                                                                    ** GOTO lbl-1000
                                                                }
                                                                var5_9 = var7_11;
                                                                var6_10 = var8_12;
                                                                var11_13 = var1_1;
                                                                if (var14_16.label().length() != 0) {
                                                                    var11_13 = var14_16.label();
                                                                    var6_10 = var8_12;
                                                                    var5_9 = var7_11;
                                                                }
                                                            }
                                                            var8_12 = var5_9;
                                                            var7_11 = var6_10;
                                                            var12_14 = var11_13;
                                                            if (!var16_18.startsWith("get")) ** GOTO lbl79
                                                            if (var16_18.length() < 4 || var16_18.equals("getClass")) ** GOTO lbl-1000
                                                            var4_8 = var16_18.charAt(3);
                                                            if (!Character.isUpperCase(var4_8) && var4_8 <= '\u0200') break block31;
                                                            if (!TypeUtils.compatibleWithJavaBean) break block32;
                                                            var1_1 = TypeUtils.decapitalize(var16_18.substring(3));
lbl43:
                                                            // 5 sources

                                                            while (true) {
                                                                block33: {
                                                                    if (TypeUtils.isJSONTypeIgnore(var0, (String)var1_1)) ** GOTO lbl-1000
                                                                    var20_20 = ParserConfig.getField(var0, (String)var1_1);
                                                                    var12_14 = null;
                                                                    var13_15 = var1_1;
                                                                    var8_12 = var5_9;
                                                                    var7_11 = var6_10;
                                                                    if (var20_20 == null) break block23;
                                                                    var15_17 = var20_20.getAnnotation(JSONField.class);
                                                                    var13_15 = var1_1;
                                                                    var8_12 = var5_9;
                                                                    var7_11 = var6_10;
                                                                    var12_14 = var15_17;
                                                                    if (var15_17 == null) break block23;
                                                                    if (!var15_17.serialize()) ** GOTO lbl-1000
                                                                    var5_9 = var15_17.ordinal();
                                                                    var6_10 = SerializerFeature.of(var15_17.serialzeFeatures());
                                                                    if (var15_17.name().length() == 0) break block33;
                                                                    var1_1 = var12_14 = var15_17.name();
                                                                    if (var2_2 != null && (var1_1 = (String)var2_2.get(var12_14)) == null) ** GOTO lbl-1000
                                                                }
                                                                var13_15 = var1_1;
                                                                var8_12 = var5_9;
                                                                var7_11 = var6_10;
                                                                var12_14 = var15_17;
                                                                if (var15_17.label().length() == 0) break block23;
                                                                var11_13 = var15_17.label();
                                                                var12_14 = var15_17;
                                                                var7_11 = var6_10;
                                                                var8_12 = var5_9;
lbl73:
                                                                // 2 sources

                                                                while (true) {
                                                                    var13_15 = var1_1;
                                                                    if (var2_2 != null && (var13_15 = (String)var2_2.get(var1_1)) == null) ** GOTO lbl-1000
                                                                    var17_4.put(var13_15, new FieldInfo((String)var13_15, var19_19, var20_20, (Class<?>)var0, null, var8_12, var7_11, (JSONField)var14_16, (JSONField)var12_14, (String)var11_13));
                                                                    var12_14 = var11_13;
lbl79:
                                                                    // 2 sources

                                                                    if (!var16_18.startsWith("is") || var16_18.length() < 3) ** GOTO lbl-1000
                                                                    var4_8 = var16_18.charAt(2);
                                                                    if (!Character.isUpperCase(var4_8)) break block24;
                                                                    if (!TypeUtils.compatibleWithJavaBean) break block25;
                                                                    var1_1 = TypeUtils.decapitalize(var16_18.substring(2));
lbl84:
                                                                    // 4 sources

                                                                    while (true) {
                                                                        block34: {
                                                                            var15_17 = var11_13 = ParserConfig.getField(var0, (String)var1_1);
                                                                            if (var11_13 == null) {
                                                                                var15_17 = ParserConfig.getField(var0, (String)var16_18);
                                                                            }
                                                                            var11_13 = null;
                                                                            var13_15 = var1_1;
                                                                            var6_10 = var8_12;
                                                                            var5_9 = var7_11;
                                                                            if (var15_17 == null) break block26;
                                                                            var16_18 = var15_17.getAnnotation(JSONField.class);
                                                                            var13_15 = var1_1;
                                                                            var6_10 = var8_12;
                                                                            var5_9 = var7_11;
                                                                            var11_13 = var16_18;
                                                                            if (var16_18 == null) break block26;
                                                                            if (!var16_18.serialize()) ** GOTO lbl-1000
                                                                            var7_11 = var16_18.ordinal();
                                                                            var8_12 = SerializerFeature.of(var16_18.serialzeFeatures());
                                                                            if (var16_18.name().length() == 0) break block34;
                                                                            var1_1 = var11_13 = var16_18.name();
                                                                            if (var2_2 != null && (var1_1 = (String)var2_2.get(var11_13)) == null) ** GOTO lbl-1000
                                                                        }
                                                                        var13_15 = var1_1;
                                                                        var6_10 = var7_11;
                                                                        var5_9 = var8_12;
                                                                        var11_13 = var16_18;
                                                                        if (var16_18.label().length() == 0) break block26;
                                                                        var12_14 = var16_18.label();
                                                                        var11_13 = var16_18;
                                                                        var5_9 = var8_12;
                                                                        var6_10 = var7_11;
lbl115:
                                                                        // 2 sources

                                                                        while (true) {
                                                                            var13_15 = var1_1;
                                                                            if (var2_2 != null && (var13_15 = (String)var2_2.get(var1_1)) == null) ** GOTO lbl-1000
                                                                            var17_4.put(var13_15, new FieldInfo((String)var13_15, var19_19, (Field)var15_17, (Class<?>)var0, null, var6_10, var5_9, (JSONField)var14_16, (JSONField)var11_13, (String)var12_14));
                                                                            ** GOTO lbl-1000
                                                                            break;
                                                                        }
                                                                        break;
                                                                    }
                                                                    break;
                                                                }
                                                                break;
                                                            }
                                                        }
                                                        var1_1 = Character.toLowerCase(var16_18.charAt(3)) + var16_18.substring(4);
                                                        ** GOTO lbl43
                                                    }
                                                    if (var4_8 != '_') break block35;
                                                    var1_1 = var16_18.substring(4);
                                                    ** GOTO lbl43
                                                }
                                                if (var4_8 != 'f') break block36;
                                                var1_1 = var16_18.substring(3);
                                                ** GOTO lbl43
                                            }
                                            if (var16_18.length() < 5 || !Character.isUpperCase(var16_18.charAt(4))) ** GOTO lbl-1000
                                            var1_1 = TypeUtils.decapitalize(var16_18.substring(3));
                                            ** continue;
                                        }
                                        var1_1 = Character.toLowerCase(var16_18.charAt(2)) + var16_18.substring(3);
                                        ** GOTO lbl84
                                    }
                                    if (var4_8 != '_') break block37;
                                    var1_1 = var16_18.substring(3);
                                    ** GOTO lbl84
                                }
                                if (var4_8 == 'f') ** break;
                                ** continue;
                                var1_1 = var16_18.substring(2);
                                ** continue;
                            }
                            block6: for (Object var15_17 : var0.getFields()) {
                                block38: {
                                    if (Modifier.isStatic(var15_17.getModifiers())) lbl-1000:
                                    // 4 sources

                                    {
                                        continue block6;
                                    }
                                    var16_18 = var15_17.getAnnotation(JSONField.class);
                                    var6_10 = 0;
                                    var7_11 = 0;
                                    var11_13 = var15_17.getName();
                                    var13_15 = null;
                                    var1_1 = var11_13;
                                    var12_14 = var13_15;
                                    if (var16_18 == null) break block38;
                                    if (!var16_18.serialize()) ** GOTO lbl-1000
                                    var8_12 = var16_18.ordinal();
                                    var9_7 = SerializerFeature.of(var16_18.serialzeFeatures());
                                    if (var16_18.name().length() != 0) {
                                        var11_13 = var16_18.name();
                                    }
                                    var1_1 = var11_13;
                                    var6_10 = var8_12;
                                    var7_11 = var9_7;
                                    var12_14 = var13_15;
                                    if (var16_18.label().length() != 0) {
                                        var12_14 = var16_18.label();
                                        var7_11 = var9_7;
                                        var6_10 = var8_12;
                                        var1_1 = var11_13;
                                    }
                                }
                                var11_13 = var1_1;
                                if (var2_2 != null && (var11_13 = (String)var2_2.get(var1_1)) == null || var17_4.containsKey(var11_13)) ** GOTO lbl-1000
                                var17_4.put(var11_13, new FieldInfo((String)var11_13, null, (Field)var15_17, (Class<?>)var0, null, var6_10, var7_11, null, (JSONField)var16_18, (String)var12_14));
                                ** continue;
                            }
                            var2_2 = new ArrayList<E>();
                            var5_9 = 0;
                            var1_1 = null;
                            var11_13 = var0.getAnnotation(JSONType.class);
                            var0 = var1_1;
                            if (var11_13 == null) ** GOTO lbl198
                            var1_1 = var11_13.orders();
                            if (var1_1 == null || ((String[])var1_1).length != var17_4.size()) break block39;
                            var7_11 = 1;
                            var8_12 = ((Object)var1_1).length;
                            var6_10 = 0;
                            while (true) {
                                block41: {
                                    block40: {
                                        var5_9 = var7_11;
                                        var0 = var1_1;
                                        if (var6_10 >= var8_12) break block40;
                                        if (var17_4.containsKey(var1_1[var6_10])) break block41;
                                        var5_9 = 0;
                                        var0 = var1_1;
                                    }
lbl199:
                                    // 2 sources

                                    while (var5_9 != 0) {
                                        var6_10 = ((Object)var0).length;
                                        for (var5_9 = 0; var5_9 < var6_10; ++var5_9) {
                                            var2_2.add((FieldInfo)var17_4.get(var0[var5_9]));
                                        }
                                        break block27;
                                    }
                                    break block28;
                                }
                                ++var6_10;
                            }
                        }
                        var5_9 = 0;
                        var0 = var1_1;
                        ** GOTO lbl199
                    }
                    var0 = var17_4.values().iterator();
                    while (var0.hasNext()) {
                        var2_2.add((FieldInfo)var0.next());
                    }
                    if (var3_3) {
                        Collections.sort(var2_2);
                    }
                }
                return var2_2;
            }
            var1_1 = var13_15;
            ** while (true)
        }
        var1_1 = var13_15;
        ** while (true)
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Collection createCollection(Type object) {
        Class<AbstractCollection> clazz = TypeUtils.getRawClass((Type)object);
        if (clazz == AbstractCollection.class) return new ArrayList();
        if (clazz == Collection.class) {
            return new ArrayList();
        }
        if (clazz.isAssignableFrom(HashSet.class)) {
            return new HashSet();
        }
        if (clazz.isAssignableFrom(LinkedHashSet.class)) {
            return new LinkedHashSet();
        }
        if (clazz.isAssignableFrom(TreeSet.class)) {
            return new TreeSet();
        }
        if (clazz.isAssignableFrom(ArrayList.class)) {
            return new ArrayList();
        }
        if (clazz.isAssignableFrom(EnumSet.class)) {
            if (object instanceof ParameterizedType) {
                object = ((ParameterizedType)object).getActualTypeArguments()[0];
                return EnumSet.noneOf((Class)object);
            }
            object = Object.class;
            return EnumSet.noneOf((Class)object);
        }
        try {
            return (Collection)clazz.newInstance();
        }
        catch (Exception exception) {
            throw new JSONException("create instane error, class " + clazz.getName());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String decapitalize(String object) {
        if (object == null || ((String)object).length() == 0 || ((String)object).length() > 1 && Character.isUpperCase(((String)object).charAt(1)) && Character.isUpperCase(((String)object).charAt(0))) {
            return object;
        }
        object = ((String)object).toCharArray();
        object[0] = Character.toLowerCase((char)object[0]);
        return new String((char[])object);
    }

    public static Class<?> getClass(Type type) {
        if (type.getClass() == Class.class) {
            return (Class)type;
        }
        if (type instanceof ParameterizedType) {
            return TypeUtils.getClass(((ParameterizedType)type).getRawType());
        }
        if (type instanceof TypeVariable) {
            return (Class)((TypeVariable)type).getBounds()[0];
        }
        return Object.class;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Class<?> getCollectionItemClass(Type object) {
        if (!(object instanceof ParameterizedType)) return Object.class;
        if (!((object = ((ParameterizedType)object).getActualTypeArguments()[0]) instanceof Class)) throw new JSONException("can not create ASMParser");
        Class clazz = (Class)object;
        object = clazz;
        if (Modifier.isPublic(clazz.getModifiers())) return object;
        throw new JSONException("can not create ASMParser");
    }

    public static Field getField(Class<?> clazz, String string2, Field[] fieldArray) {
        for (Field field : fieldArray) {
            if (!string2.equals(field.getName())) continue;
            return field;
        }
        if ((clazz = clazz.getSuperclass()) != null && clazz != Object.class) {
            return TypeUtils.getField(clazz, string2, clazz.getDeclaredFields());
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Type getGenericParamType(Type type) {
        if (type instanceof ParameterizedType || !(type instanceof Class)) {
            return type;
        }
        return TypeUtils.getGenericParamType(((Class)type).getGenericSuperclass());
    }

    public static int getParserFeatures(Class<?> object) {
        if ((object = ((Class)object).getAnnotation(JSONType.class)) == null) {
            return 0;
        }
        return Feature.of(object.parseFeatures());
    }

    public static Class<?> getRawClass(Type type) {
        if (type instanceof Class) {
            return (Class)type;
        }
        if (type instanceof ParameterizedType) {
            return TypeUtils.getRawClass(((ParameterizedType)type).getRawType());
        }
        throw new JSONException("TODO");
    }

    public static int getSerializeFeatures(Class<?> object) {
        if ((object = ((Class)object).getAnnotation(JSONType.class)) == null) {
            return 0;
        }
        return SerializerFeature.of(object.serialzeFeatures());
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    public static JSONField getSupperMethodAnnotation(Class<?> var0, Method var1_1) {
        block8: {
            if ((var0 = var0.getInterfaces()).length <= 0) break block8;
            var9_2 = var1_1.getParameterTypes();
            var7_3 = var0.length;
            for (var2_4 = 0; var2_4 < var7_3; ++var2_4) {
                block1: for (Object var11_11 : var0[var2_4].getMethods()) {
                    var12_12 = var11_11.getParameterTypes();
                    if (var12_12.length != var9_2.length) lbl-1000:
                    // 3 sources

                    {
                        continue block1;
                    }
                    if (!var11_11.getName().equals(var1_1.getName())) ** GOTO lbl-1000
                    var6_8 = true;
                    var4_6 = 0;
                    while (true) {
                        block10: {
                            block9: {
                                var5_7 = var6_8;
                                if (var4_6 >= var9_2.length) break block9;
                                if (var12_12[var4_6].equals(var9_2[var4_6])) break block10;
                                var5_7 = false;
                            }
                            if (var5_7 && (var11_11 = var11_11.getAnnotation(JSONField.class)) != null) ** break;
                            ** continue;
                            return var11_11;
                        }
                        ++var4_6;
                    }
                }
            }
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isGenericParamType(Type type) {
        boolean bl2 = false;
        if (type instanceof ParameterizedType) {
            return true;
        }
        boolean bl3 = bl2;
        if (!(type instanceof Class)) return bl3;
        type = ((Class)type).getGenericSuperclass();
        bl3 = bl2;
        if (type == Object.class) return bl3;
        return TypeUtils.isGenericParamType(type);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean isJSONTypeIgnore(Class<?> clazz, String string2) {
        String[] stringArray = clazz.getAnnotation(JSONType.class);
        if (stringArray != null) {
            String[] stringArray2 = stringArray.includes();
            if (stringArray2.length > 0) {
                int n2 = 0;
                while (true) {
                    if (n2 >= stringArray2.length) {
                        return true;
                    }
                    if (string2.equals(stringArray2[n2])) return false;
                    ++n2;
                }
            }
            stringArray = stringArray.ignores();
            for (int i2 = 0; i2 < stringArray.length; ++i2) {
                if (!string2.equals(stringArray[i2])) continue;
                return true;
            }
        }
        if (clazz.getSuperclass() != Object.class && clazz.getSuperclass() != null && TypeUtils.isJSONTypeIgnore(clazz.getSuperclass(), string2)) return true;
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean isPath(Class<?> clazz) {
        if (pathClass == null && !pathClass_error) {
            try {
                pathClass = Class.forName("java.nio.file.Path");
            }
            catch (Throwable throwable) {
                pathClass_error = true;
            }
        }
        if (pathClass != null) {
            return pathClass.isAssignableFrom(clazz);
        }
        return false;
    }

    public static Class<?> loadClass(String string2) {
        return TypeUtils.loadClass(string2, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Class<?> loadClass(String string2, ClassLoader clazz) {
        Class<?> clazz2;
        block14: {
            if (string2 == null) return null;
            if (string2.length() == 0) {
                return null;
            }
            Object object = (Class<?>)mappings.get(string2);
            clazz2 = object;
            if (object != null) return clazz2;
            if (string2.charAt(0) == '[') {
                return Array.newInstance(TypeUtils.loadClass(string2.substring(1), clazz), 0).getClass();
            }
            if (string2.startsWith("L") && string2.endsWith(";")) {
                return TypeUtils.loadClass(string2.substring(1, string2.length() - 1), clazz);
            }
            clazz2 = object;
            if (clazz != null) {
                clazz2 = object;
                clazz2 = clazz = ((ClassLoader)((Object)clazz)).loadClass(string2);
                try {
                    mappings.put(string2, clazz);
                    return clazz;
                }
                catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            clazz = clazz2;
            object = Thread.currentThread().getContextClassLoader();
            clazz = clazz2;
            if (object == null) break block14;
            clazz = clazz2;
            clazz = clazz2 = ((ClassLoader)object).loadClass(string2);
            try {
                mappings.put(string2, clazz2);
                return clazz2;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        try {
            clazz = clazz2 = Class.forName(string2);
        }
        catch (Throwable throwable) {
            return clazz;
        }
        mappings.put(string2, clazz2);
        return clazz2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void setAccessible(AccessibleObject accessibleObject) {
        if (!setAccessibleEnable || accessibleObject.isAccessible()) {
            return;
        }
        try {
            accessibleObject.setAccessible(true);
            return;
        }
        catch (AccessControlException accessControlException) {
            setAccessibleEnable = false;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Type unwrapOptional(Type type) {
        block5: {
            if (!optionalClassInited) {
                try {
                    optionalClass = Class.forName("java.util.Optional");
                    break block5;
                }
                catch (Exception exception) {}
                break block5;
                finally {
                    optionalClassInited = true;
                }
            }
        }
        Type type2 = type;
        if (!(type instanceof ParameterizedType)) return type2;
        ParameterizedType parameterizedType = (ParameterizedType)type;
        type2 = type;
        if (parameterizedType.getRawType() != optionalClass) return type2;
        return parameterizedType.getActualTypeArguments()[0];
    }
}

