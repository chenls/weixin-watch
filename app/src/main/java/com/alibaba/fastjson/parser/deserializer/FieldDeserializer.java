/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class FieldDeserializer {
    protected final Class<?> clazz;
    public final FieldInfo fieldInfo;

    public FieldDeserializer(Class<?> clazz, FieldInfo fieldInfo) {
        this.clazz = clazz;
        this.fieldInfo = fieldInfo;
    }

    public int getFastMatchToken() {
        return 0;
    }

    public abstract void parseField(DefaultJSONParser var1, Object var2, Type var3, Map<String, Object> var4);

    public void setValue(Object object, int n2) {
        this.setValue(object, (Object)n2);
    }

    public void setValue(Object object, long l2) {
        this.setValue(object, (Object)l2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setValue(Object object, Object object2) {
        AccessibleObject accessibleObject;
        block27: {
            block28: {
                block29: {
                    if (object2 == null && this.fieldInfo.fieldClass.isPrimitive()) return;
                    try {
                        accessibleObject = this.fieldInfo.method;
                        if (accessibleObject == null) break block27;
                        if (!this.fieldInfo.getOnly) break block28;
                        if (this.fieldInfo.fieldClass == AtomicInteger.class) {
                            if ((object = (AtomicInteger)((Method)accessibleObject).invoke(object, new Object[0])) == null) return;
                            ((AtomicInteger)object).set(((AtomicInteger)object2).get());
                            return;
                        }
                        if (this.fieldInfo.fieldClass != AtomicLong.class) break block29;
                        if ((object = (AtomicLong)((Method)accessibleObject).invoke(object, new Object[0])) == null) return;
                        ((AtomicLong)object).set(((AtomicLong)object2).get());
                        return;
                    }
                    catch (Exception exception) {
                        throw new JSONException("set property error, " + this.fieldInfo.name, exception);
                    }
                }
                if (this.fieldInfo.fieldClass == AtomicBoolean.class) {
                    if ((object = (AtomicBoolean)((Method)accessibleObject).invoke(object, new Object[0])) == null) return;
                    ((AtomicBoolean)object).set(((AtomicBoolean)object2).get());
                    return;
                }
                if (Map.class.isAssignableFrom(((Method)accessibleObject).getReturnType())) {
                    if ((object = (Map)((Method)accessibleObject).invoke(object, new Object[0])) == null) return;
                    object.putAll((Map)object2);
                    return;
                } else {
                    if ((object = (Collection)((Method)accessibleObject).invoke(object, new Object[0])) == null) return;
                    object.addAll((Collection)object2);
                    return;
                }
            }
            ((Method)accessibleObject).invoke(object, object2);
            return;
        }
        accessibleObject = this.fieldInfo.field;
        if (this.fieldInfo.getOnly) {
            if (this.fieldInfo.fieldClass == AtomicInteger.class) {
                if ((object = (AtomicInteger)((Field)accessibleObject).get(object)) == null) return;
                ((AtomicInteger)object).set(((AtomicInteger)object2).get());
                return;
            }
            if (this.fieldInfo.fieldClass == AtomicLong.class) {
                if ((object = (AtomicLong)((Field)accessibleObject).get(object)) == null) return;
                ((AtomicLong)object).set(((AtomicLong)object2).get());
                return;
            }
            if (this.fieldInfo.fieldClass == AtomicBoolean.class) {
                if ((object = (AtomicBoolean)((Field)accessibleObject).get(object)) == null) return;
                ((AtomicBoolean)object).set(((AtomicBoolean)object2).get());
                return;
            }
            if (Map.class.isAssignableFrom(this.fieldInfo.fieldClass)) {
                if ((object = (Map)((Field)accessibleObject).get(object)) == null) return;
                object.putAll((Map)object2);
                return;
            } else {
                if ((object = (Collection)((Field)accessibleObject).get(object)) == null) return;
                object.addAll((Collection)object2);
                return;
            }
        }
        if (accessibleObject == null) {
            return;
        }
        ((Field)accessibleObject).set(object, object2);
    }

    public void setValue(Object object, String string2) {
        this.setValue(object, (Object)string2);
    }

    public void setValue(Object object, boolean bl2) {
        this.setValue(object, (Object)bl2);
    }
}

