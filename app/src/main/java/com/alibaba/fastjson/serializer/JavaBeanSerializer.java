/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.FieldSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerialContext;
import com.alibaba.fastjson.serializer.SerializeFilterable;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JavaBeanSerializer
extends SerializeFilterable
implements ObjectSerializer {
    protected final Class<?> beanType;
    protected int features = 0;
    protected final FieldSerializer[] getters;
    protected final JSONType jsonType;
    protected final FieldSerializer[] sortedGetters;
    protected String typeName;

    public JavaBeanSerializer(Class<?> clazz) {
        this(clazz, (Map<String, String>)null);
    }

    public JavaBeanSerializer(Class<?> clazz, Map<String, String> map) {
        this(clazz, map, TypeUtils.getSerializeFeatures(clazz));
    }

    public JavaBeanSerializer(Class<?> objectArray, Map<String, String> object, int n2) {
        this.features = n2;
        this.beanType = objectArray;
        this.jsonType = objectArray.getAnnotation(JSONType.class);
        if (this.jsonType != null) {
            SerializerFeature.of(this.jsonType.serialzeFeatures());
        }
        Object object2 = new ArrayList();
        String[] stringArray = TypeUtils.computeGetters(objectArray, this.jsonType, object, false).iterator();
        while (stringArray.hasNext()) {
            object2.add(new FieldSerializer((Class<?>)objectArray, stringArray.next()));
        }
        this.getters = object2.toArray(new FieldSerializer[object2.size()]);
        object2 = null;
        if (this.jsonType != null) {
            stringArray = this.jsonType.orders();
            String string2 = this.jsonType.typeName();
            object2 = stringArray;
            if (string2.length() != 0) {
                this.typeName = string2;
                object2 = stringArray;
            }
        }
        if (object2 != null && ((ArrayList<E>)object2).length != 0) {
            object2 = TypeUtils.computeGetters(objectArray, this.jsonType, object, true);
            object = new ArrayList();
            object2 = object2.iterator();
            while (object2.hasNext()) {
                object.add(new FieldSerializer((Class<?>)objectArray, (FieldInfo)object2.next()));
            }
            this.sortedGetters = object.toArray(new FieldSerializer[object.size()]);
            return;
        }
        objectArray = new FieldSerializer[this.getters.length];
        System.arraycopy(this.getters, 0, objectArray, 0, this.getters.length);
        Arrays.sort(objectArray);
        if (Arrays.equals(objectArray, this.getters)) {
            this.sortedGetters = this.getters;
            return;
        }
        this.sortedGetters = objectArray;
    }

    public JavaBeanSerializer(Class<?> clazz, String ... stringArray) {
        this(clazz, JavaBeanSerializer.createAliasMap(stringArray));
    }

    static Map<String, String> createAliasMap(String ... stringArray) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (String string2 : stringArray) {
            hashMap.put(string2, string2);
        }
        return hashMap;
    }

    protected BeanContext getBeanContext(int n2) {
        return this.sortedGetters[n2].fieldContext;
    }

    /*
     * Enabled aggressive block sorting
     */
    public FieldSerializer getFieldSerializer(String string2) {
        if (string2 != null) {
            int n2 = 0;
            int n3 = this.sortedGetters.length - 1;
            while (n2 <= n3) {
                int n4 = n2 + n3 >>> 1;
                int n5 = this.sortedGetters[n4].fieldInfo.name.compareTo(string2);
                if (n5 < 0) {
                    n2 = n4 + 1;
                    continue;
                }
                if (n5 <= 0) {
                    return this.sortedGetters[n4];
                }
                n3 = n4 - 1;
            }
        }
        return null;
    }

    protected Type getFieldType(int n2) {
        return this.sortedGetters[n2].fieldInfo.fieldType;
    }

    public List<Object> getFieldValues(Object object) throws Exception {
        ArrayList<Object> arrayList = new ArrayList<Object>(this.sortedGetters.length);
        FieldSerializer[] fieldSerializerArray = this.sortedGetters;
        int n2 = fieldSerializerArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            arrayList.add(fieldSerializerArray[i2].getPropertyValue(object));
        }
        return arrayList;
    }

    public Map<String, Object> getFieldValuesMap(Object object) throws Exception {
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>(this.sortedGetters.length);
        for (FieldSerializer fieldSerializer : this.sortedGetters) {
            linkedHashMap.put(fieldSerializer.fieldInfo.name, fieldSerializer.getPropertyValue(object));
        }
        return linkedHashMap;
    }

    public int getSize(Object object) throws Exception {
        int n2 = 0;
        FieldSerializer[] fieldSerializerArray = this.sortedGetters;
        int n3 = fieldSerializerArray.length;
        for (int i2 = 0; i2 < n3; ++i2) {
            int n4 = n2;
            if (fieldSerializerArray[i2].getPropertyValue(object) != null) {
                n4 = n2 + 1;
            }
            n2 = n4;
        }
        return n2;
    }

    public boolean isWriteAsArray(JSONSerializer jSONSerializer) {
        return (this.features & SerializerFeature.BeanToArray.mask) != 0 || jSONSerializer.out.beanToArray;
    }

    /*
     * Exception decompiling
     */
    @Override
    public void write(JSONSerializer var1_1, Object var2_2, Object var3_4, Type var4_5, int var5_7) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block [egrp 30[TRYBLOCK] [75, 76 : 998->1013)] java.lang.Exception
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.insertExceptionBlocks(Op02WithProcessedDataAndRefs.java:2289)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:414)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:73)
         *     at org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void writeAsArrayNonContext(JSONSerializer jSONSerializer, Object object, Object object2, Type type, int n2) throws IOException {
        this.write(jSONSerializer, object, object2, type, n2);
    }

    public void writeDirectNonContext(JSONSerializer jSONSerializer, Object object, Object object2, Type type, int n2) throws IOException {
        this.write(jSONSerializer, object, object2, type, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean writeReference(JSONSerializer jSONSerializer, Object object, int n2) {
        SerialContext serialContext = jSONSerializer.context;
        int n3 = SerializerFeature.DisableCircularReferenceDetect.mask;
        if (serialContext == null || (serialContext.features & n3) != 0 || (n2 & n3) != 0 || jSONSerializer.references == null || !jSONSerializer.references.containsKey(object)) {
            return false;
        }
        jSONSerializer.writeReference(object);
        return true;
    }
}

