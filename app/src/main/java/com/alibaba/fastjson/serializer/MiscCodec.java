/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.JSONStreamAware;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

public class MiscCodec
implements ObjectSerializer,
ObjectDeserializer {
    public static final MiscCodec instance = new MiscCodec();
    private static Method method_paths_get;
    private static boolean method_paths_get_error;

    static {
        method_paths_get_error = false;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public <T> T deserialze(DefaultJSONParser object, Type object2, Object object3) {
        Object object4 = ((DefaultJSONParser)object).lexer;
        if (object2 == InetSocketAddress.class) {
            if (object4.token() == 8) {
                object4.nextToken();
                return null;
            }
        } else {
            if (((DefaultJSONParser)object).resolveStatus == 2) {
                ((DefaultJSONParser)object).resolveStatus = 0;
                ((DefaultJSONParser)object).accept(16);
                if (object4.token() != 4) {
                    throw new JSONException("syntax error");
                }
                if (!"val".equals(object4.stringVal())) {
                    throw new JSONException("syntax error");
                }
                object4.nextToken();
                ((DefaultJSONParser)object).accept(17);
                object3 = ((DefaultJSONParser)object).parse();
                ((DefaultJSONParser)object).accept(13);
            } else {
                object3 = ((DefaultJSONParser)object).parse();
            }
            if (object3 == null) {
                object3 = null;
            } else {
                if (!(object3 instanceof String)) {
                    throw new JSONException("expect string");
                }
                object3 = (String)object3;
            }
            if (object3 == null || ((String)object3).length() == 0) {
                return null;
            }
            if (object2 == UUID.class) {
                return (T)UUID.fromString((String)object3);
            }
            if (object2 == URI.class) {
                return (T)URI.create((String)object3);
            }
            if (object2 == URL.class) {
                try {
                    object = new URL((String)object3);
                }
                catch (MalformedURLException malformedURLException) {
                    throw new JSONException("create url error", malformedURLException);
                }
                return (T)object;
            }
            if (object2 == Pattern.class) {
                return (T)Pattern.compile((String)object3);
            }
            if (object2 == Locale.class) {
                object = ((String)object3).split("_");
                if (((Object)object).length == 1) {
                    return (T)new Locale((String)object[0]);
                }
                if (((Object)object).length == 2) {
                    return (T)new Locale((String)object[0], (String)object[1]);
                }
                return (T)new Locale((String)object[0], (String)object[1], (String)object[2]);
            }
            if (object2 == SimpleDateFormat.class) {
                object = new SimpleDateFormat((String)object3, object4.getLocale());
                ((DateFormat)object).setTimeZone(object4.getTimeZone());
                return (T)object;
            }
            if (object2 == InetAddress.class || object2 == Inet4Address.class || object2 == Inet6Address.class) {
                try {
                    object = InetAddress.getByName((String)object3);
                }
                catch (UnknownHostException unknownHostException) {
                    throw new JSONException("deserialize inet adress error", unknownHostException);
                }
                return (T)object;
            }
            if (object2 == File.class) {
                return (T)new File((String)object3);
            }
            if (object2 == TimeZone.class) {
                return (T)TimeZone.getTimeZone((String)object3);
            }
            object4 = object2;
            if (object2 instanceof ParameterizedType) {
                object4 = ((ParameterizedType)object2).getRawType();
            }
            if (object4 == Class.class) {
                return (T)TypeUtils.loadClass((String)object3, ((DefaultJSONParser)object).getConfig().getDefaultClassLoader());
            }
            if (object4 == Charset.class) {
                return (T)Charset.forName((String)object3);
            }
            if (object4 == Currency.class) {
                return (T)Currency.getInstance((String)object3);
            }
            if (object4 == JSONPath.class) {
                return (T)new JSONPath((String)object3);
            }
            object = object4.getTypeName();
            if (((String)object).equals("java.nio.file.Path")) {
                try {
                    if (method_paths_get == null && !method_paths_get_error) {
                        method_paths_get = TypeUtils.loadClass("java.nio.file.Paths").getMethod("get", String.class, String[].class);
                    }
                    if (method_paths_get != null) {
                        return (T)method_paths_get.invoke(null, object3, new String[0]);
                    }
                    throw new JSONException("Path deserialize erorr");
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    method_paths_get_error = true;
                }
            }
            throw new JSONException("MiscCodec not support " + (String)object);
            catch (IllegalAccessException illegalAccessException) {
                throw new JSONException("Path deserialize erorr", illegalAccessException);
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new JSONException("Path deserialize erorr", invocationTargetException);
            }
        }
        ((DefaultJSONParser)object).accept(12);
        object2 = null;
        int n2 = 0;
        while (true) {
            object3 = object4.stringVal();
            object4.nextToken(17);
            if (((String)object3).equals("address")) {
                ((DefaultJSONParser)object).accept(17);
                object2 = ((DefaultJSONParser)object).parseObject(InetAddress.class);
            } else if (((String)object3).equals("port")) {
                ((DefaultJSONParser)object).accept(17);
                if (object4.token() != 2) {
                    throw new JSONException("port is not int");
                }
                n2 = object4.intValue();
                object4.nextToken();
            } else {
                ((DefaultJSONParser)object).accept(17);
                ((DefaultJSONParser)object).parse();
            }
            if (object4.token() != 16) {
                ((DefaultJSONParser)object).accept(13);
                return (T)new InetSocketAddress((InetAddress)object2, n2);
            }
            object4.nextToken();
        }
    }

    @Override
    public int getFastMatchToken() {
        return 4;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(JSONSerializer object, Object object2, Object clazz, Type type, int n2) throws IOException {
        SerializeWriter serializeWriter;
        block16: {
            block11: {
                block15: {
                    block14: {
                        block13: {
                            block12: {
                                block10: {
                                    serializeWriter = ((JSONSerializer)object).out;
                                    if (object2 == null) {
                                        serializeWriter.writeNull();
                                        return;
                                    }
                                    clazz = object2.getClass();
                                    if (clazz != SimpleDateFormat.class) break block10;
                                    clazz = ((SimpleDateFormat)object2).toPattern();
                                    if (serializeWriter.isEnabled(SerializerFeature.WriteClassName) && object2.getClass() != type) {
                                        serializeWriter.write(123);
                                        serializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
                                        ((JSONSerializer)object).write(object2.getClass().getName());
                                        serializeWriter.writeFieldValue(',', "val", (String)((Object)clazz));
                                        serializeWriter.write(125);
                                        return;
                                    }
                                    object = clazz;
                                    break block11;
                                }
                                if (clazz != Class.class) break block12;
                                object = ((Class)object2).getName();
                                break block11;
                            }
                            if (clazz == InetSocketAddress.class) {
                                object2 = (InetSocketAddress)object2;
                                clazz = ((InetSocketAddress)object2).getAddress();
                                serializeWriter.write(123);
                                if (clazz != null) {
                                    serializeWriter.writeFieldName("address");
                                    ((JSONSerializer)object).write(clazz);
                                    serializeWriter.write(44);
                                }
                                serializeWriter.writeFieldName("port");
                                serializeWriter.writeInt(((InetSocketAddress)object2).getPort());
                                serializeWriter.write(125);
                                return;
                            }
                            if (!(object2 instanceof File)) break block13;
                            object = ((File)object2).getPath();
                            break block11;
                        }
                        if (!(object2 instanceof InetAddress)) break block14;
                        object = ((InetAddress)object2).getHostAddress();
                        break block11;
                    }
                    if (!(object2 instanceof TimeZone)) break block15;
                    object = ((TimeZone)object2).getID();
                    break block11;
                }
                if (!(object2 instanceof Currency)) break block16;
                object = ((Currency)object2).getCurrencyCode();
            }
            serializeWriter.writeString((String)object);
            return;
        }
        if (object2 instanceof JSONStreamAware) {
            ((JSONStreamAware)object2).writeJSONString(serializeWriter);
            return;
        }
        if (object2 instanceof Iterator) {
            this.writeIterator((JSONSerializer)object, serializeWriter, (Iterator)object2);
            return;
        }
        if (object2 instanceof Iterable) {
            this.writeIterator((JSONSerializer)object, serializeWriter, ((Iterable)object2).iterator());
            return;
        }
        throw new JSONException("not support class : " + clazz);
    }

    protected void writeIterator(JSONSerializer jSONSerializer, SerializeWriter serializeWriter, Iterator<?> iterator) {
        int n2 = 0;
        serializeWriter.write(91);
        while (iterator.hasNext()) {
            if (n2 != 0) {
                serializeWriter.write(44);
            }
            jSONSerializer.write(iterator.next());
            ++n2;
        }
        serializeWriter.write(93);
    }
}

