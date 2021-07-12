/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class ResolveFieldDeserializer
extends FieldDeserializer {
    private final Collection collection;
    private final int index;
    private final Object key;
    private final List list;
    private final Map map;
    private final DefaultJSONParser parser;

    public ResolveFieldDeserializer(DefaultJSONParser defaultJSONParser, List list, int n2) {
        super(null, null);
        this.parser = defaultJSONParser;
        this.index = n2;
        this.list = list;
        this.key = null;
        this.map = null;
        this.collection = null;
    }

    public ResolveFieldDeserializer(Collection collection) {
        super(null, null);
        this.parser = null;
        this.index = -1;
        this.list = null;
        this.key = null;
        this.map = null;
        this.collection = collection;
    }

    public ResolveFieldDeserializer(Map map, Object object) {
        super(null, null);
        this.parser = null;
        this.index = -1;
        this.list = null;
        this.key = object;
        this.map = map;
        this.collection = null;
    }

    @Override
    public void parseField(DefaultJSONParser defaultJSONParser, Object object, Type type, Map<String, Object> map) {
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setValue(Object object, Object object2) {
        Object object3;
        if (this.map != null) {
            this.map.put(this.key, object2);
            return;
        } else {
            if (this.collection != null) {
                this.collection.add(object2);
                return;
            }
            this.list.set(this.index, object2);
            if (!(this.list instanceof JSONArray) || (object3 = ((JSONArray)(object = (JSONArray)this.list)).getRelatedArray()) == null || Array.getLength(object3) <= this.index) return;
            object = ((JSONArray)object).getComponentType() != null ? TypeUtils.cast(object2, ((JSONArray)object).getComponentType(), this.parser.getConfig()) : object2;
        }
        Array.set(object3, this.index, object);
    }
}

