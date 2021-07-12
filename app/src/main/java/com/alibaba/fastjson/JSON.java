/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONAware;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldTypeResolver;
import com.alibaba.fastjson.parser.deserializer.ParseProcess;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializeFilterable;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public abstract class JSON
implements JSONStreamAware,
JSONAware {
    public static int DEFAULT_GENERATE_FEATURE = 0;
    public static int DEFAULT_PARSER_FEATURE = 0;
    public static String DEFAULT_TYPE_KEY;
    public static String DEFFAULT_DATE_FORMAT;
    public static final String VERSION = "1.2.11";
    private static final ThreadLocal<byte[]> bytesLocal;
    private static final ThreadLocal<char[]> charsLocal;
    public static Locale defaultLocale;
    public static TimeZone defaultTimeZone;
    static final SerializeFilter[] emptyFilters;

    static {
        defaultTimeZone = TimeZone.getDefault();
        defaultLocale = Locale.getDefault();
        DEFAULT_TYPE_KEY = "@type";
        emptyFilters = new SerializeFilter[0];
        DEFAULT_PARSER_FEATURE = 0 | Feature.AutoCloseSource.getMask() | Feature.InternFieldNames.getMask() | Feature.UseBigDecimal.getMask() | Feature.AllowUnQuotedFieldNames.getMask() | Feature.AllowSingleQuotes.getMask() | Feature.AllowArbitraryCommas.getMask() | Feature.SortFeidFastMatch.getMask() | Feature.IgnoreNotMatch.getMask();
        DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        DEFAULT_GENERATE_FEATURE = 0 | SerializerFeature.QuoteFieldNames.getMask() | SerializerFeature.SkipTransientField.getMask() | SerializerFeature.WriteEnumUsingName.getMask() | SerializerFeature.SortField.getMask();
        bytesLocal = new ThreadLocal();
        charsLocal = new ThreadLocal();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static byte[] allocateBytes(int n2) {
        byte[] byArray;
        byte[] byArray2 = bytesLocal.get();
        if (byArray2 == null) {
            if (n2 > 65536) {
                return new byte[n2];
            }
            byArray = new byte[65536];
            bytesLocal.set(byArray);
            return byArray;
        } else {
            byArray = byArray2;
            if (byArray2.length >= n2) return byArray;
            return new byte[n2];
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static char[] allocateChars(int n2) {
        char[] cArray;
        char[] cArray2 = charsLocal.get();
        if (cArray2 == null) {
            if (n2 > 65536) {
                return new char[n2];
            }
            cArray = new char[65536];
            charsLocal.set(cArray);
            return cArray;
        } else {
            cArray = cArray2;
            if (cArray2.length >= n2) return cArray;
            return new char[n2];
        }
    }

    public static Object parse(String string2) {
        return JSON.parse(string2, DEFAULT_PARSER_FEATURE);
    }

    public static Object parse(String object, int n2) {
        if (object == null) {
            return null;
        }
        object = new DefaultJSONParser((String)object, ParserConfig.getGlobalInstance(), n2);
        Object object2 = ((DefaultJSONParser)object).parse();
        ((DefaultJSONParser)object).handleResovleTask(object2);
        ((DefaultJSONParser)object).close();
        return object2;
    }

    public static Object parse(String string2, Feature ... featureArray) {
        int n2 = DEFAULT_PARSER_FEATURE;
        int n3 = featureArray.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            n2 = Feature.config(n2, featureArray[i2], true);
        }
        return JSON.parse(string2, n2);
    }

    public static Object parse(byte[] object, int n2, int n3, CharsetDecoder object2, int n4) {
        ((CharsetDecoder)object2).reset();
        char[] cArray = JSON.allocateChars((int)((double)n3 * (double)((CharsetDecoder)object2).maxCharsPerByte()));
        object = ByteBuffer.wrap((byte[])object, n2, n3);
        CharBuffer charBuffer = CharBuffer.wrap(cArray);
        IOUtils.decode((CharsetDecoder)object2, (ByteBuffer)object, charBuffer);
        object = new DefaultJSONParser(cArray, charBuffer.position(), ParserConfig.getGlobalInstance(), n4);
        object2 = ((DefaultJSONParser)object).parse();
        ((DefaultJSONParser)object).handleResovleTask(object2);
        ((DefaultJSONParser)object).close();
        return object2;
    }

    public static Object parse(byte[] byArray, int n2, int n3, CharsetDecoder charsetDecoder, Feature ... featureArray) {
        if (byArray == null || byArray.length == 0) {
            return null;
        }
        int n4 = DEFAULT_PARSER_FEATURE;
        int n5 = featureArray.length;
        for (int i2 = 0; i2 < n5; ++i2) {
            n4 = Feature.config(n4, featureArray[i2], true);
        }
        return JSON.parse(byArray, n2, n3, charsetDecoder, n4);
    }

    public static Object parse(byte[] byArray, Feature ... featureArray) {
        char[] cArray = JSON.allocateChars(byArray.length);
        return JSON.parse(new String(cArray, 0, IOUtils.decodeUTF8(byArray, 0, byArray.length, cArray)), featureArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static JSONArray parseArray(String object) {
        if (object == null) {
            return null;
        }
        DefaultJSONParser defaultJSONParser = new DefaultJSONParser((String)object, ParserConfig.getGlobalInstance());
        object = defaultJSONParser.lexer;
        if (object.token() == 8) {
            object.nextToken();
            object = null;
        } else if (object.token() == 20) {
            object = null;
        } else {
            object = new JSONArray();
            defaultJSONParser.parseArray((Collection)object);
            defaultJSONParser.handleResovleTask(object);
        }
        defaultJSONParser.close();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static <T> List<T> parseArray(String object, Class<T> clazz) {
        if (object == null) {
            return null;
        }
        DefaultJSONParser defaultJSONParser = new DefaultJSONParser((String)object, ParserConfig.getGlobalInstance());
        object = defaultJSONParser.lexer;
        int n2 = object.token();
        if (n2 == 8) {
            object.nextToken();
            object = null;
        } else if (n2 == 20 && object.isBlankInput()) {
            object = null;
        } else {
            object = new ArrayList();
            defaultJSONParser.parseArray(clazz, (Collection)object);
            defaultJSONParser.handleResovleTask(object);
        }
        defaultJSONParser.close();
        return object;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static List<Object> parseArray(String object, Type[] typeArray) {
        void var0_3;
        void var1_5;
        if (object == null) {
            return null;
        }
        DefaultJSONParser defaultJSONParser = new DefaultJSONParser((String)object, ParserConfig.getGlobalInstance());
        Object[] objectArray = defaultJSONParser.parseArray((Type[])var1_5);
        if (objectArray == null) {
            Object var0_2 = null;
        } else {
            List<Object> list = Arrays.asList(objectArray);
        }
        defaultJSONParser.handleResovleTask(var0_3);
        defaultJSONParser.close();
        return var0_3;
    }

    public static JSONObject parseObject(String object) {
        if ((object = JSON.parse((String)object)) instanceof JSONObject) {
            return (JSONObject)object;
        }
        return (JSONObject)JSON.toJSON(object);
    }

    public static JSONObject parseObject(String string2, Feature ... featureArray) {
        return (JSONObject)JSON.parse(string2, featureArray);
    }

    public static <T> T parseObject(InputStream inputStream, Type type, Feature ... featureArray) throws IOException {
        return JSON.parseObject(inputStream, IOUtils.UTF8, type, featureArray);
    }

    public static <T> T parseObject(InputStream inputStream, Charset object, Type type, Feature ... featureArray) throws IOException {
        Charset charset = object;
        if (object == null) {
            charset = IOUtils.UTF8;
        }
        object = JSON.allocateBytes(65536);
        int n2 = 0;
        int n3;
        while ((n3 = inputStream.read((byte[])object, n2, ((Object)object).length - n2)) != -1) {
            n2 = n3 = n2 + n3;
            if (n3 != ((Object)object).length) continue;
            byte[] byArray = new byte[((Object)object).length * 3 / 2];
            System.arraycopy(object, 0, byArray, 0, ((Object)object).length);
            object = byArray;
            n2 = n3;
        }
        return JSON.parseObject((byte[])object, 0, n2, charset, type, featureArray);
    }

    public static <T> T parseObject(String string2, TypeReference<T> typeReference, Feature ... featureArray) {
        return JSON.parseObject(string2, typeReference.type, ParserConfig.global, DEFAULT_PARSER_FEATURE, featureArray);
    }

    public static <T> T parseObject(String string2, Class<T> clazz) {
        return JSON.parseObject(string2, clazz, new Feature[0]);
    }

    public static <T> T parseObject(String string2, Class<T> clazz, ParseProcess parseProcess, Feature ... featureArray) {
        return JSON.parseObject(string2, clazz, ParserConfig.global, parseProcess, DEFAULT_PARSER_FEATURE, featureArray);
    }

    public static <T> T parseObject(String string2, Class<T> clazz, Feature ... featureArray) {
        return JSON.parseObject(string2, clazz, ParserConfig.global, null, DEFAULT_PARSER_FEATURE, featureArray);
    }

    public static <T> T parseObject(String object, Type type, int n2, Feature ... featureArray) {
        if (object == null) {
            return null;
        }
        int n3 = featureArray.length;
        int n4 = 0;
        int n5 = n2;
        for (n2 = n4; n2 < n3; ++n2) {
            n5 = Feature.config(n5, featureArray[n2], true);
        }
        object = new DefaultJSONParser((String)object, ParserConfig.getGlobalInstance(), n5);
        type = ((DefaultJSONParser)object).parseObject(type);
        ((DefaultJSONParser)object).handleResovleTask(type);
        ((DefaultJSONParser)object).close();
        return (T)type;
    }

    public static <T> T parseObject(String string2, Type type, ParserConfig parserConfig, int n2, Feature ... featureArray) {
        return JSON.parseObject(string2, type, parserConfig, null, n2, featureArray);
    }

    public static <T> T parseObject(String object, Type type, ParserConfig parserConfig, ParseProcess parseProcess, int n2, Feature ... featureArray) {
        if (object == null) {
            return null;
        }
        int n3 = n2;
        if (featureArray != null) {
            int n4 = featureArray.length;
            int n5 = 0;
            while (true) {
                n3 = n2;
                if (n5 >= n4) break;
                n2 |= featureArray[n5].mask;
                ++n5;
            }
        }
        object = new DefaultJSONParser((String)object, parserConfig, n3);
        if (parseProcess != null) {
            if (parseProcess instanceof ExtraTypeProvider) {
                ((DefaultJSONParser)object).getExtraTypeProviders().add((ExtraTypeProvider)parseProcess);
            }
            if (parseProcess instanceof ExtraProcessor) {
                ((DefaultJSONParser)object).getExtraProcessors().add((ExtraProcessor)parseProcess);
            }
            if (parseProcess instanceof FieldTypeResolver) {
                ((DefaultJSONParser)object).setFieldTypeResolver((FieldTypeResolver)parseProcess);
            }
        }
        type = ((DefaultJSONParser)object).parseObject(type, null);
        ((DefaultJSONParser)object).handleResovleTask(type);
        ((DefaultJSONParser)object).close();
        return (T)type;
    }

    public static <T> T parseObject(String string2, Type type, ParserConfig parserConfig, Feature ... featureArray) {
        return JSON.parseObject(string2, type, parserConfig, null, DEFAULT_PARSER_FEATURE, featureArray);
    }

    public static <T> T parseObject(String string2, Type type, ParseProcess parseProcess, Feature ... featureArray) {
        return JSON.parseObject(string2, type, ParserConfig.global, parseProcess, DEFAULT_PARSER_FEATURE, featureArray);
    }

    public static <T> T parseObject(String string2, Type type, Feature ... featureArray) {
        return JSON.parseObject(string2, type, ParserConfig.global, DEFAULT_PARSER_FEATURE, featureArray);
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static <T> T parseObject(byte[] object, int n2, int n3, Charset object2, Type type, Feature ... featureArray) {
        void var5_8;
        void var4_7;
        void var0_2;
        void var2_5;
        void var1_4;
        char[] cArray;
        Charset charset = cArray;
        if (cArray == null) {
            charset = IOUtils.UTF8;
        }
        if (charset == IOUtils.UTF8) {
            cArray = JSON.allocateChars(((byte[])object).length);
            String string2 = new String(cArray, 0, IOUtils.decodeUTF8(object, (int)var1_4, (int)var2_5, cArray));
            return JSON.parseObject((String)var0_2, (Type)var4_7, (Feature[])var5_8);
        }
        String string3 = new String((byte[])object, (int)var1_4, (int)var2_5, charset);
        return JSON.parseObject((String)var0_2, (Type)var4_7, (Feature[])var5_8);
    }

    public static <T> T parseObject(byte[] object, int n2, int n3, CharsetDecoder charsetDecoder, Type type, Feature ... featureArray) {
        charsetDecoder.reset();
        char[] cArray = JSON.allocateChars((int)((double)n3 * (double)charsetDecoder.maxCharsPerByte()));
        object = ByteBuffer.wrap(object, n2, n3);
        CharBuffer charBuffer = CharBuffer.wrap(cArray);
        IOUtils.decode(charsetDecoder, (ByteBuffer)object, charBuffer);
        return JSON.parseObject(cArray, charBuffer.position(), type, featureArray);
    }

    public static <T> T parseObject(byte[] byArray, Type type, Feature ... featureArray) {
        return JSON.parseObject(byArray, 0, byArray.length, IOUtils.UTF8, type, featureArray);
    }

    public static <T> T parseObject(char[] object, int n2, Type type, Feature ... featureArray) {
        if (object == null || ((char[])object).length == 0) {
            return null;
        }
        int n3 = DEFAULT_PARSER_FEATURE;
        int n4 = featureArray.length;
        for (int i2 = 0; i2 < n4; ++i2) {
            n3 = Feature.config(n3, featureArray[i2], true);
        }
        object = new DefaultJSONParser((char[])object, n2, ParserConfig.getGlobalInstance(), n3);
        type = ((DefaultJSONParser)object).parseObject(type);
        ((DefaultJSONParser)object).handleResovleTask(type);
        ((DefaultJSONParser)object).close();
        return (T)type;
    }

    public static Object toJSON(Object object) {
        return JSON.toJSON(object, SerializeConfig.globalInstance);
    }

    public static Object toJSON(Object object, ParserConfig parserConfig) {
        return JSON.toJSON(object, SerializeConfig.globalInstance);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Object toJSON(Object iterator, SerializeConfig object) {
        void var4_4;
        if (iterator == null) {
            return var4_4;
        }
        Iterator<Map.Entry<String, Object>> iterator2 = iterator;
        if (iterator instanceof JSON) return var4_4;
        if (iterator instanceof Map) {
            object = (Map)((Object)iterator);
            iterator = new JSONObject(object.size());
            object = object.entrySet().iterator();
            while (true) {
                if (!object.hasNext()) {
                    return iterator;
                }
                Map.Entry entry = object.next();
                ((JSONObject)((Object)iterator)).put(TypeUtils.castToString(entry.getKey()), JSON.toJSON(entry.getValue()));
            }
        }
        if (iterator instanceof Collection) {
            object = (Collection)((Object)iterator);
            iterator = new JSONArray(object.size());
            object = object.iterator();
            while (true) {
                if (!object.hasNext()) {
                    return iterator;
                }
                ((JSONArray)((Object)iterator)).add(JSON.toJSON(object.next()));
            }
        }
        Class<?> clazz = iterator.getClass();
        if (clazz.isEnum()) {
            return ((Enum)((Object)iterator)).name();
        }
        if (clazz.isArray()) {
            int n2 = Array.getLength(iterator);
            object = new JSONArray(n2);
            int n3 = 0;
            while (true) {
                if (n3 >= n2) {
                    return object;
                }
                ((JSONArray)object).add(JSON.toJSON(Array.get(iterator, n3)));
                ++n3;
            }
        }
        Iterator<Map.Entry<String, Object>> iterator3 = iterator;
        if (ParserConfig.isPrimitive(clazz)) {
            return var4_4;
        }
        if (!((object = ((SerializeConfig)object).getObjectWriter(clazz)) instanceof JavaBeanSerializer)) {
            return null;
        }
        JavaBeanSerializer javaBeanSerializer = (JavaBeanSerializer)object;
        object = new JSONObject();
        try {
            for (Map.Entry<String, Object> entry : javaBeanSerializer.getFieldValuesMap(iterator).entrySet()) {
                ((JSONObject)object).put(entry.getKey(), JSON.toJSON(entry.getValue()));
            }
            return object;
        }
        catch (Exception exception) {
            throw new JSONException("toJSON error", exception);
        }
    }

    public static byte[] toJSONBytes(Object object, int n2, SerializerFeature ... serializerFeatureArray) {
        return JSON.toJSONBytes(object, SerializeConfig.globalInstance, n2, serializerFeatureArray);
    }

    public static byte[] toJSONBytes(Object object, SerializeConfig serializeConfig, int n2, SerializerFeature ... object2) {
        object2 = new SerializeWriter(null, n2, (SerializerFeature[])object2);
        try {
            new JSONSerializer((SerializeWriter)object2, serializeConfig).write(object);
            object = ((SerializeWriter)object2).toBytes(IOUtils.UTF8);
            return object;
        }
        finally {
            ((SerializeWriter)object2).close();
        }
    }

    public static byte[] toJSONBytes(Object object, SerializeConfig serializeConfig, SerializerFeature ... serializerFeatureArray) {
        return JSON.toJSONBytes(object, serializeConfig, DEFAULT_GENERATE_FEATURE, serializerFeatureArray);
    }

    public static byte[] toJSONBytes(Object object, SerializerFeature ... serializerFeatureArray) {
        return JSON.toJSONBytes(object, DEFAULT_GENERATE_FEATURE, serializerFeatureArray);
    }

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, emptyFilters, new SerializerFeature[0]);
    }

    public static String toJSONString(Object object, int n2, SerializerFeature ... object2) {
        object2 = new SerializeWriter((Writer)null, n2, (SerializerFeature[])object2);
        try {
            new JSONSerializer((SerializeWriter)object2).write(object);
            object = ((SerializeWriter)object2).toString();
            return object;
        }
        finally {
            ((SerializeWriter)object2).close();
        }
    }

    public static String toJSONString(Object object, SerializeConfig serializeConfig, SerializeFilter serializeFilter, SerializerFeature ... serializerFeatureArray) {
        int n2 = DEFAULT_GENERATE_FEATURE;
        return JSON.toJSONString(object, serializeConfig, new SerializeFilter[]{serializeFilter}, null, n2, serializerFeatureArray);
    }

    public static String toJSONString(Object object, SerializeConfig object2, SerializeFilter[] serializeFilterArray, String string2, int n2, SerializerFeature ... object3) {
        block9: {
            object3 = new SerializeWriter(null, n2, (SerializerFeature[])object3);
            object2 = new JSONSerializer((SerializeWriter)object3, (SerializeConfig)object2);
            if (string2 == null) break block9;
            if (string2.length() == 0) break block9;
            ((JSONSerializer)object2).setDateFormat(string2);
            ((JSONSerializer)object2).config(SerializerFeature.WriteDateUseDateFormat, true);
        }
        if (serializeFilterArray != null) {
            int n3 = serializeFilterArray.length;
            for (n2 = 0; n2 < n3; ++n2) {
                ((SerializeFilterable)object2).addFilter(serializeFilterArray[n2]);
                continue;
            }
        }
        try {
            ((JSONSerializer)object2).write(object);
            object = ((SerializeWriter)object3).toString();
            return object;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            ((SerializeWriter)object3).close();
        }
    }

    public static String toJSONString(Object object, SerializeConfig serializeConfig, SerializeFilter[] serializeFilterArray, SerializerFeature ... serializerFeatureArray) {
        return JSON.toJSONString(object, serializeConfig, serializeFilterArray, null, DEFAULT_GENERATE_FEATURE, serializerFeatureArray);
    }

    public static String toJSONString(Object object, SerializeConfig serializeConfig, SerializerFeature ... serializerFeatureArray) {
        return JSON.toJSONString(object, serializeConfig, (SerializeFilter)null, serializerFeatureArray);
    }

    public static String toJSONString(Object object, SerializeFilter serializeFilter, SerializerFeature ... serializerFeatureArray) {
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        int n2 = DEFAULT_GENERATE_FEATURE;
        return JSON.toJSONString(object, serializeConfig, new SerializeFilter[]{serializeFilter}, null, n2, serializerFeatureArray);
    }

    public static String toJSONString(Object object, boolean bl2) {
        if (!bl2) {
            return JSON.toJSONString(object);
        }
        return JSON.toJSONString(object, SerializerFeature.PrettyFormat);
    }

    public static String toJSONString(Object object, SerializeFilter[] serializeFilterArray, SerializerFeature ... serializerFeatureArray) {
        return JSON.toJSONString(object, SerializeConfig.globalInstance, serializeFilterArray, null, DEFAULT_GENERATE_FEATURE, serializerFeatureArray);
    }

    public static String toJSONString(Object object, SerializerFeature ... serializerFeatureArray) {
        return JSON.toJSONString(object, DEFAULT_GENERATE_FEATURE, serializerFeatureArray);
    }

    public static String toJSONStringWithDateFormat(Object object, String string2, SerializerFeature ... serializerFeatureArray) {
        return JSON.toJSONString(object, SerializeConfig.globalInstance, null, string2, DEFAULT_GENERATE_FEATURE, serializerFeatureArray);
    }

    public static String toJSONStringZ(Object object, SerializeConfig serializeConfig, SerializerFeature ... serializerFeatureArray) {
        return JSON.toJSONString(object, serializeConfig, emptyFilters, null, 0, serializerFeatureArray);
    }

    public static <T> T toJavaObject(JSON jSON, Class<T> clazz) {
        return TypeUtils.cast((Object)jSON, clazz, ParserConfig.getGlobalInstance());
    }

    public static final int writeJSONString(OutputStream outputStream, Object object, int n2, SerializerFeature ... serializerFeatureArray) throws IOException {
        return JSON.writeJSONString(outputStream, IOUtils.UTF8, object, SerializeConfig.globalInstance, null, null, n2, serializerFeatureArray);
    }

    public static final int writeJSONString(OutputStream outputStream, Object object, SerializerFeature ... serializerFeatureArray) throws IOException {
        return JSON.writeJSONString(outputStream, object, DEFAULT_GENERATE_FEATURE, serializerFeatureArray);
    }

    public static final int writeJSONString(OutputStream outputStream, Charset charset, Object object, SerializeConfig object2, SerializeFilter[] serializeFilterArray, String string2, int n2, SerializerFeature ... object3) throws IOException {
        block9: {
            object3 = new SerializeWriter(null, n2, (SerializerFeature[])object3);
            object2 = new JSONSerializer((SerializeWriter)object3, (SerializeConfig)object2);
            if (string2 == null) break block9;
            if (string2.length() == 0) break block9;
            ((JSONSerializer)object2).setDateFormat(string2);
            ((JSONSerializer)object2).config(SerializerFeature.WriteDateUseDateFormat, true);
        }
        if (serializeFilterArray != null) {
            int n3 = serializeFilterArray.length;
            for (n2 = 0; n2 < n3; ++n2) {
                ((SerializeFilterable)object2).addFilter(serializeFilterArray[n2]);
                continue;
            }
        }
        try {
            ((JSONSerializer)object2).write(object);
            n2 = ((SerializeWriter)object3).writeToEx(outputStream, charset);
            return n2;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            ((SerializeWriter)object3).close();
        }
    }

    public static final int writeJSONString(OutputStream outputStream, Charset charset, Object object, SerializerFeature ... serializerFeatureArray) throws IOException {
        return JSON.writeJSONString(outputStream, charset, object, SerializeConfig.globalInstance, null, null, DEFAULT_GENERATE_FEATURE, serializerFeatureArray);
    }

    public static void writeJSONString(Writer writer, Object object, int n2, SerializerFeature ... serializerFeatureArray) {
        writer = new SerializeWriter(writer, n2, serializerFeatureArray);
        try {
            new JSONSerializer((SerializeWriter)writer).write(object);
            return;
        }
        finally {
            ((SerializeWriter)writer).close();
        }
    }

    public static void writeJSONString(Writer writer, Object object, SerializerFeature ... serializerFeatureArray) {
        JSON.writeJSONString(writer, object, DEFAULT_GENERATE_FEATURE, serializerFeatureArray);
    }

    public static void writeJSONStringTo(Object object, Writer writer, SerializerFeature ... serializerFeatureArray) {
        JSON.writeJSONString(writer, object, serializerFeatureArray);
    }

    @Override
    public String toJSONString() {
        SerializeWriter serializeWriter = new SerializeWriter();
        try {
            new JSONSerializer(serializeWriter).write(this);
            String string2 = serializeWriter.toString();
            return string2;
        }
        finally {
            serializeWriter.close();
        }
    }

    public <T> T toJavaObject(Class<T> clazz) {
        return TypeUtils.cast((Object)this, clazz, ParserConfig.getGlobalInstance());
    }

    public String toString() {
        return this.toJSONString();
    }

    @Override
    public void writeJSONString(Appendable appendable) {
        SerializeWriter serializeWriter = new SerializeWriter();
        try {
            new JSONSerializer(serializeWriter).write(this);
            appendable.append(serializeWriter.toString());
            return;
        }
        catch (IOException iOException) {
            throw new JSONException(iOException.getMessage(), iOException);
        }
        finally {
            serializeWriter.close();
        }
    }
}

