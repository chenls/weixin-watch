/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class JSONObject
extends JSON
implements Map<String, Object>,
Cloneable,
Serializable,
InvocationHandler {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private final Map<String, Object> map;

    public JSONObject() {
        this(16, false);
    }

    public JSONObject(int n2) {
        this(n2, false);
    }

    public JSONObject(int n2, boolean bl2) {
        if (bl2) {
            this.map = new LinkedHashMap<String, Object>(n2);
            return;
        }
        this.map = new HashMap<String, Object>(n2);
    }

    public JSONObject(Map<String, Object> map) {
        this.map = map;
    }

    public JSONObject(boolean bl2) {
        this(16, bl2);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object clone() {
        void var1_2;
        if (this.map instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>(this.map);
            return new JSONObject((Map<String, Object>)var1_2);
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>(this.map);
        return new JSONObject((Map<String, Object>)var1_2);
    }

    @Override
    public boolean containsKey(Object object) {
        return this.map.containsKey(object);
    }

    @Override
    public boolean containsValue(Object object) {
        return this.map.containsValue(object);
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return this.map.entrySet();
    }

    @Override
    public boolean equals(Object object) {
        return this.map.equals(object);
    }

    public JSONObject fluentClear() {
        this.map.clear();
        return this;
    }

    public JSONObject fluentPut(String string2, Object object) {
        this.map.put(string2, object);
        return this;
    }

    public JSONObject fluentPutAll(Map<? extends String, ? extends Object> map) {
        this.map.putAll(map);
        return this;
    }

    public JSONObject fluentRemove(Object object) {
        this.map.remove(object);
        return this;
    }

    @Override
    public Object get(Object object) {
        return this.map.get(object);
    }

    public BigDecimal getBigDecimal(String string2) {
        return TypeUtils.castToBigDecimal(this.get(string2));
    }

    public BigInteger getBigInteger(String string2) {
        return TypeUtils.castToBigInteger(this.get(string2));
    }

    public Boolean getBoolean(String object) {
        if ((object = this.get(object)) == null) {
            return null;
        }
        return TypeUtils.castToBoolean(object);
    }

    public boolean getBooleanValue(String object) {
        if ((object = this.get(object)) == null) {
            return false;
        }
        return TypeUtils.castToBoolean(object);
    }

    public Byte getByte(String string2) {
        return TypeUtils.castToByte(this.get(string2));
    }

    public byte getByteValue(String object) {
        if ((object = this.get(object)) == null) {
            return 0;
        }
        return TypeUtils.castToByte(object);
    }

    public byte[] getBytes(String object) {
        if ((object = this.get(object)) == null) {
            return null;
        }
        return TypeUtils.castToBytes(object);
    }

    public java.util.Date getDate(String string2) {
        return TypeUtils.castToDate(this.get(string2));
    }

    public Double getDouble(String string2) {
        return TypeUtils.castToDouble(this.get(string2));
    }

    public double getDoubleValue(String object) {
        if ((object = this.get(object)) == null) {
            return 0.0;
        }
        return TypeUtils.castToDouble(object);
    }

    public Float getFloat(String string2) {
        return TypeUtils.castToFloat(this.get(string2));
    }

    public float getFloatValue(String object) {
        if ((object = this.get(object)) == null) {
            return 0.0f;
        }
        return TypeUtils.castToFloat(object).floatValue();
    }

    public int getIntValue(String object) {
        if ((object = this.get(object)) == null) {
            return 0;
        }
        return TypeUtils.castToInt(object);
    }

    public Integer getInteger(String string2) {
        return TypeUtils.castToInt(this.get(string2));
    }

    public JSONArray getJSONArray(String object) {
        if ((object = this.map.get(object)) instanceof JSONArray) {
            return (JSONArray)object;
        }
        return (JSONArray)JSONObject.toJSON(object);
    }

    public JSONObject getJSONObject(String object) {
        if ((object = this.map.get(object)) instanceof JSONObject) {
            return (JSONObject)object;
        }
        return (JSONObject)JSONObject.toJSON(object);
    }

    public Long getLong(String string2) {
        return TypeUtils.castToLong(this.get(string2));
    }

    public long getLongValue(String object) {
        if ((object = this.get(object)) == null) {
            return 0L;
        }
        return TypeUtils.castToLong(object);
    }

    public <T> T getObject(String string2, Class<T> clazz) {
        return TypeUtils.castToJavaBean(this.map.get(string2), clazz);
    }

    public Short getShort(String string2) {
        return TypeUtils.castToShort(this.get(string2));
    }

    public short getShortValue(String object) {
        if ((object = this.get(object)) == null) {
            return 0;
        }
        return TypeUtils.castToShort(object);
    }

    public Date getSqlDate(String string2) {
        return TypeUtils.castToSqlDate(this.get(string2));
    }

    public String getString(String object) {
        if ((object = this.get(object)) == null) {
            return null;
        }
        return object.toString();
    }

    public Timestamp getTimestamp(String string2) {
        return TypeUtils.castToTimestamp(this.get(string2));
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object invoke(Object object, Method method, Object[] object2) throws Throwable {
        void var3_7;
        block20: {
            block19: {
                object = method.getParameterTypes();
                if (((Object)object).length == 1) {
                    void var4_12;
                    if (method.getName().equals("equals")) {
                        return this.equals(object2[0]);
                    }
                    if (method.getReturnType() != Void.TYPE) {
                        throw new JSONException("illegal setter");
                    }
                    Object var4_9 = null;
                    JSONField jSONField = method.getAnnotation(JSONField.class);
                    object = var4_9;
                    if (jSONField != null) {
                        object = var4_9;
                        if (jSONField.name().length() != 0) {
                            object = jSONField.name();
                        }
                    }
                    Object object3 = object;
                    if (object == null) {
                        object = method.getName();
                        if (!((String)object).startsWith("set")) {
                            throw new JSONException("illegal setter");
                        }
                        if (((String)(object = ((String)object).substring(3))).length() == 0) {
                            throw new JSONException("illegal setter");
                        }
                        String string2 = Character.toLowerCase(((String)object).charAt(0)) + ((String)object).substring(1);
                    }
                    this.map.put((String)var4_12, object2[0]);
                    return null;
                }
                if (((Object)object).length != 0) throw new UnsupportedOperationException(method.toGenericString());
                if (method.getReturnType() == Void.TYPE) {
                    throw new JSONException("illegal getter");
                }
                Object var3_4 = null;
                JSONField jSONField = method.getAnnotation(JSONField.class);
                object = var3_4;
                if (jSONField != null) {
                    object = var3_4;
                    if (jSONField.name().length() != 0) {
                        object = jSONField.name();
                    }
                }
                Object object4 = object;
                if (object != null) break block19;
                object = method.getName();
                if (!((String)object).startsWith("get")) break block20;
                if (((String)(object = ((String)object).substring(3))).length() == 0) {
                    throw new JSONException("illegal getter");
                }
                String string3 = Character.toLowerCase(((String)object).charAt(0)) + ((String)object).substring(1);
            }
            return TypeUtils.cast(this.map.get(var3_7), method.getGenericReturnType(), ParserConfig.getGlobalInstance());
        }
        if (((String)object).startsWith("is")) {
            if (((String)(object = ((String)object).substring(2))).length() == 0) {
                throw new JSONException("illegal getter");
            }
            String string4 = Character.toLowerCase(((String)object).charAt(0)) + ((String)object).substring(1);
            return TypeUtils.cast(this.map.get(var3_7), method.getGenericReturnType(), ParserConfig.getGlobalInstance());
        }
        if (((String)object).startsWith("hashCode")) {
            return this.hashCode();
        }
        if (!((String)object).startsWith("toString")) throw new JSONException("illegal getter");
        return this.toString();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return this.map.keySet();
    }

    @Override
    public Object put(String string2, Object object) {
        return this.map.put(string2, object);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> map) {
        this.map.putAll(map);
    }

    @Override
    public Object remove(Object object) {
        return this.map.remove(object);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public Collection<Object> values() {
        return this.map.values();
    }
}

