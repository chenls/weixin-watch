/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.util.FieldInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public final class BeanContext {
    private final Class<?> beanClass;
    private final FieldInfo fieldInfo;

    BeanContext(Class<?> clazz, FieldInfo fieldInfo) {
        this.beanClass = clazz;
        this.fieldInfo = fieldInfo;
    }

    public <T extends Annotation> T getAnnation(Class<T> clazz) {
        return this.fieldInfo.getAnnation(clazz);
    }

    public Class<?> getBeanClass() {
        return this.beanClass;
    }

    public int getFeatures() {
        return this.fieldInfo.serialzeFeatures;
    }

    public Field getField() {
        return this.fieldInfo.field;
    }

    public Class<?> getFieldClass() {
        return this.fieldInfo.fieldClass;
    }

    public Type getFieldType() {
        return this.fieldInfo.fieldType;
    }

    public String getLabel() {
        return this.fieldInfo.label;
    }

    public Method getMethod() {
        return this.fieldInfo.method;
    }

    public String getName() {
        return this.fieldInfo.name;
    }
}

