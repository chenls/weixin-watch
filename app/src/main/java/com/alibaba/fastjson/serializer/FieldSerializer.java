/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.FieldInfo;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class FieldSerializer
implements Comparable<FieldSerializer> {
    private final String double_quoted_fieldPrefix;
    protected int features;
    protected BeanContext fieldContext;
    public final FieldInfo fieldInfo;
    private String format;
    private RuntimeSerializerInfo runtimeInfo;
    private String single_quoted_fieldPrefix;
    private String un_quoted_fieldPrefix;
    protected boolean writeEnumUsingName;
    protected boolean writeEnumUsingToString;
    protected final boolean writeNull;

    /*
     * Enabled aggressive block sorting
     */
    public FieldSerializer(Class<?> object, FieldInfo serializerFeatureArray) {
        boolean bl2;
        block7: {
            int n2 = 0;
            this.writeEnumUsingToString = false;
            this.writeEnumUsingName = false;
            this.fieldInfo = serializerFeatureArray;
            this.fieldContext = new BeanContext((Class<?>)object, (FieldInfo)serializerFeatureArray);
            serializerFeatureArray.setAccessible();
            this.double_quoted_fieldPrefix = '\"' + serializerFeatureArray.name + "\":";
            bl2 = false;
            boolean bl3 = false;
            object = serializerFeatureArray.getAnnotation();
            if (object == null) break block7;
            serializerFeatureArray = object.serialzeFeatures();
            int n3 = serializerFeatureArray.length;
            int n4 = 0;
            while (true) {
                block9: {
                    block8: {
                        bl2 = bl3;
                        if (n4 >= n3) break block8;
                        if (serializerFeatureArray[n4] != SerializerFeature.WriteMapNullValue) break block9;
                        bl2 = true;
                    }
                    this.format = object.format();
                    if (this.format.trim().length() == 0) {
                        this.format = null;
                    }
                    serializerFeatureArray = object.serialzeFeatures();
                    n3 = serializerFeatureArray.length;
                    break;
                }
                ++n4;
            }
            for (n4 = n2; n4 < n3; ++n4) {
                SerializerFeature serializerFeature = serializerFeatureArray[n4];
                if (serializerFeature == SerializerFeature.WriteEnumUsingToString) {
                    this.writeEnumUsingToString = true;
                    continue;
                }
                if (serializerFeature != SerializerFeature.WriteEnumUsingName) continue;
                this.writeEnumUsingName = true;
            }
            this.features = SerializerFeature.of(object.serialzeFeatures());
        }
        this.writeNull = bl2;
    }

    @Override
    public int compareTo(FieldSerializer fieldSerializer) {
        return this.fieldInfo.compareTo(fieldSerializer.fieldInfo);
    }

    public Object getPropertyValue(Object object) throws InvocationTargetException, IllegalAccessException {
        return this.fieldInfo.get(object);
    }

    public void writePrefix(JSONSerializer object) throws IOException {
        object = ((JSONSerializer)object).out;
        if (((SerializeWriter)object).quoteFieldNames) {
            if (((SerializeWriter)object).useSingleQuotes) {
                if (this.single_quoted_fieldPrefix == null) {
                    this.single_quoted_fieldPrefix = '\'' + this.fieldInfo.name + "':";
                }
                ((SerializeWriter)object).write(this.single_quoted_fieldPrefix);
                return;
            }
            ((SerializeWriter)object).write(this.double_quoted_fieldPrefix);
            return;
        }
        if (this.un_quoted_fieldPrefix == null) {
            this.un_quoted_fieldPrefix = this.fieldInfo.name + ":";
        }
        ((SerializeWriter)object).write(this.un_quoted_fieldPrefix);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeValue(JSONSerializer jSONSerializer, Object object) throws Exception {
        Class<?> clazz;
        Class<?> clazz2;
        if (this.format != null) {
            jSONSerializer.writeWithFormat(object, this.format);
            return;
        }
        if (this.runtimeInfo == null) {
            clazz2 = object == null ? this.fieldInfo.fieldClass : object.getClass();
            this.runtimeInfo = new RuntimeSerializerInfo(jSONSerializer.getObjectWriter(clazz2), clazz2);
        }
        clazz2 = this.runtimeInfo;
        int n2 = this.fieldInfo.serialzeFeatures;
        if (object == null) {
            Class<?> clazz3 = ((RuntimeSerializerInfo)clazz2).runtimeFieldClass;
            object = jSONSerializer.out;
            if (Number.class.isAssignableFrom(clazz3)) {
                ((SerializeWriter)object).writeNull(this.features, SerializerFeature.WriteNullNumberAsZero.mask);
                return;
            }
            if (String.class == clazz3) {
                ((SerializeWriter)object).writeNull(this.features, SerializerFeature.WriteNullStringAsEmpty.mask);
                return;
            }
            if (Boolean.class == clazz3) {
                ((SerializeWriter)object).writeNull(this.features, SerializerFeature.WriteNullBooleanAsFalse.mask);
                return;
            }
            if (Collection.class.isAssignableFrom(clazz3)) {
                ((SerializeWriter)object).writeNull(this.features, SerializerFeature.WriteNullListAsEmpty.mask);
                return;
            }
            clazz2 = ((RuntimeSerializerInfo)clazz2).fieldSerializer;
            if (((SerializeWriter)object).isEnabled(SerializerFeature.WriteMapNullValue) && clazz2 instanceof JavaBeanSerializer) {
                ((SerializeWriter)object).writeNull();
                return;
            }
            clazz2.write(jSONSerializer, null, this.fieldInfo.name, this.fieldInfo.fieldType, n2);
            return;
        }
        if (this.fieldInfo.isEnum) {
            if (this.writeEnumUsingName) {
                jSONSerializer.out.writeString(((Enum)object).name());
                return;
            }
            if (this.writeEnumUsingToString) {
                jSONSerializer.out.writeString(((Enum)object).toString());
                return;
            }
        }
        if ((clazz = object.getClass()) == ((RuntimeSerializerInfo)clazz2).runtimeFieldClass) {
            ((RuntimeSerializerInfo)clazz2).fieldSerializer.write(jSONSerializer, object, this.fieldInfo.name, this.fieldInfo.fieldType, n2);
            return;
        }
        jSONSerializer.getObjectWriter(clazz).write(jSONSerializer, object, this.fieldInfo.name, this.fieldInfo.fieldType, n2);
    }

    static class RuntimeSerializerInfo {
        ObjectSerializer fieldSerializer;
        Class<?> runtimeFieldClass;

        public RuntimeSerializerInfo(ObjectSerializer objectSerializer, Class<?> clazz) {
            this.fieldSerializer = objectSerializer;
            this.runtimeFieldClass = clazz;
        }
    }
}

