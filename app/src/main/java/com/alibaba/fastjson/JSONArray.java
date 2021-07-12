/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public class JSONArray
extends JSON
implements List<Object>,
Cloneable,
RandomAccess,
Serializable {
    protected transient Type componentType;
    private final List<Object> list;
    protected transient Object relatedArray;

    public JSONArray() {
        this.list = new ArrayList<Object>();
    }

    public JSONArray(int n2) {
        this.list = new ArrayList<Object>(n2);
    }

    public JSONArray(List<Object> list) {
        this.list = list;
    }

    @Override
    public void add(int n2, Object object) {
        this.list.add(n2, object);
    }

    @Override
    public boolean add(Object object) {
        return this.list.add(object);
    }

    @Override
    public boolean addAll(int n2, Collection<? extends Object> collection) {
        return this.list.addAll(n2, collection);
    }

    @Override
    public boolean addAll(Collection<? extends Object> collection) {
        return this.list.addAll(collection);
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    public Object clone() {
        return new JSONArray(new ArrayList<Object>(this.list));
    }

    @Override
    public boolean contains(Object object) {
        return this.list.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.list.containsAll(collection);
    }

    @Override
    public boolean equals(Object object) {
        return this.list.equals(object);
    }

    public JSONArray fluentAdd(int n2, Object object) {
        this.list.add(n2, object);
        return this;
    }

    public JSONArray fluentAdd(Object object) {
        this.list.add(object);
        return this;
    }

    public JSONArray fluentAddAll(int n2, Collection<? extends Object> collection) {
        this.list.addAll(n2, collection);
        return this;
    }

    public JSONArray fluentAddAll(Collection<? extends Object> collection) {
        this.list.addAll(collection);
        return this;
    }

    public JSONArray fluentClear() {
        this.list.clear();
        return this;
    }

    public JSONArray fluentRemove(int n2) {
        this.list.remove(n2);
        return this;
    }

    public JSONArray fluentRemove(Object object) {
        this.list.remove(object);
        return this;
    }

    public JSONArray fluentRemoveAll(Collection<?> collection) {
        this.list.removeAll(collection);
        return this;
    }

    public JSONArray fluentRetainAll(Collection<?> collection) {
        this.list.retainAll(collection);
        return this;
    }

    public JSONArray fluentSet(int n2, Object object) {
        this.set(n2, object);
        return this;
    }

    @Override
    public Object get(int n2) {
        return this.list.get(n2);
    }

    public BigDecimal getBigDecimal(int n2) {
        return TypeUtils.castToBigDecimal(this.get(n2));
    }

    public BigInteger getBigInteger(int n2) {
        return TypeUtils.castToBigInteger(this.get(n2));
    }

    public Boolean getBoolean(int n2) {
        Object object = this.get(n2);
        if (object == null) {
            return null;
        }
        return TypeUtils.castToBoolean(object);
    }

    public boolean getBooleanValue(int n2) {
        Object object = this.get(n2);
        if (object == null) {
            return false;
        }
        return TypeUtils.castToBoolean(object);
    }

    public Byte getByte(int n2) {
        return TypeUtils.castToByte(this.get(n2));
    }

    public byte getByteValue(int n2) {
        Object object = this.get(n2);
        if (object == null) {
            return 0;
        }
        return TypeUtils.castToByte(object);
    }

    public Type getComponentType() {
        return this.componentType;
    }

    public java.util.Date getDate(int n2) {
        return TypeUtils.castToDate(this.get(n2));
    }

    public Double getDouble(int n2) {
        return TypeUtils.castToDouble(this.get(n2));
    }

    public double getDoubleValue(int n2) {
        Object object = this.get(n2);
        if (object == null) {
            return 0.0;
        }
        return TypeUtils.castToDouble(object);
    }

    public Float getFloat(int n2) {
        return TypeUtils.castToFloat(this.get(n2));
    }

    public float getFloatValue(int n2) {
        Object object = this.get(n2);
        if (object == null) {
            return 0.0f;
        }
        return TypeUtils.castToFloat(object).floatValue();
    }

    public int getIntValue(int n2) {
        Object object = this.get(n2);
        if (object == null) {
            return 0;
        }
        return TypeUtils.castToInt(object);
    }

    public Integer getInteger(int n2) {
        return TypeUtils.castToInt(this.get(n2));
    }

    public JSONArray getJSONArray(int n2) {
        Object object = this.list.get(n2);
        if (object instanceof JSONArray) {
            return (JSONArray)object;
        }
        return (JSONArray)JSONArray.toJSON(object);
    }

    public JSONObject getJSONObject(int n2) {
        Object object = this.list.get(n2);
        if (object instanceof JSONObject) {
            return (JSONObject)object;
        }
        return (JSONObject)JSONArray.toJSON(object);
    }

    public Long getLong(int n2) {
        return TypeUtils.castToLong(this.get(n2));
    }

    public long getLongValue(int n2) {
        Object object = this.get(n2);
        if (object == null) {
            return 0L;
        }
        return TypeUtils.castToLong(object);
    }

    public <T> T getObject(int n2, Class<T> clazz) {
        return TypeUtils.castToJavaBean(this.list.get(n2), clazz);
    }

    public Object getRelatedArray() {
        return this.relatedArray;
    }

    public Short getShort(int n2) {
        return TypeUtils.castToShort(this.get(n2));
    }

    public short getShortValue(int n2) {
        Object object = this.get(n2);
        if (object == null) {
            return 0;
        }
        return TypeUtils.castToShort(object);
    }

    public Date getSqlDate(int n2) {
        return TypeUtils.castToSqlDate(this.get(n2));
    }

    public String getString(int n2) {
        return TypeUtils.castToString(this.get(n2));
    }

    public Timestamp getTimestamp(int n2) {
        return TypeUtils.castToTimestamp(this.get(n2));
    }

    @Override
    public int hashCode() {
        return this.list.hashCode();
    }

    @Override
    public int indexOf(Object object) {
        return this.list.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public Iterator<Object> iterator() {
        return this.list.iterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return this.list.lastIndexOf(object);
    }

    @Override
    public ListIterator<Object> listIterator() {
        return this.list.listIterator();
    }

    @Override
    public ListIterator<Object> listIterator(int n2) {
        return this.list.listIterator(n2);
    }

    @Override
    public Object remove(int n2) {
        return this.list.remove(n2);
    }

    @Override
    public boolean remove(Object object) {
        return this.list.remove(object);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return this.list.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return this.list.retainAll(collection);
    }

    @Override
    public Object set(int n2, Object object) {
        if (n2 == -1) {
            this.list.add(object);
            return null;
        }
        if (this.list.size() <= n2) {
            for (int i2 = this.list.size(); i2 < n2; ++i2) {
                this.list.add(null);
            }
            this.list.add(object);
            return null;
        }
        return this.list.set(n2, object);
    }

    public void setComponentType(Type type) {
        this.componentType = type;
    }

    public void setRelatedArray(Object object) {
        this.relatedArray = object;
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public List<Object> subList(int n2, int n3) {
        return this.list.subList(n2, n3);
    }

    @Override
    public Object[] toArray() {
        return this.list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] TArray) {
        return this.list.toArray(TArray);
    }
}

