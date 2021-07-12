/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.AfterFilter;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.BeforeFilter;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.LabelFilter;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerialContext;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilterable;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.util.FieldInfo;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

public class JSONSerializer
extends SerializeFilterable {
    private final SerializeConfig config;
    protected SerialContext context;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private String indent = "\t";
    private int indentCount = 0;
    protected Locale locale;
    public final SerializeWriter out;
    protected IdentityHashMap<Object, SerialContext> references = null;
    protected TimeZone timeZone = JSON.defaultTimeZone;

    public JSONSerializer() {
        this(new SerializeWriter(), SerializeConfig.getGlobalInstance());
    }

    public JSONSerializer(SerializeConfig serializeConfig) {
        this(new SerializeWriter(), serializeConfig);
    }

    public JSONSerializer(SerializeWriter serializeWriter) {
        this(serializeWriter, SerializeConfig.getGlobalInstance());
    }

    public JSONSerializer(SerializeWriter serializeWriter, SerializeConfig serializeConfig) {
        this.locale = JSON.defaultLocale;
        this.out = serializeWriter;
        this.config = serializeConfig;
    }

    public static void write(SerializeWriter serializeWriter, Object object) {
        new JSONSerializer(serializeWriter).write(object);
    }

    public static void write(Writer writer, Object object) {
        SerializeWriter serializeWriter = new SerializeWriter();
        try {
            new JSONSerializer(serializeWriter).write(object);
            serializeWriter.writeTo(writer);
            return;
        }
        catch (IOException iOException) {
            throw new JSONException(iOException.getMessage(), iOException);
        }
        finally {
            serializeWriter.close();
        }
    }

    public boolean apply(SerializeFilterable object, Object object2, String string2, Object object3) {
        if (this.propertyFilters != null) {
            Iterator iterator = this.propertyFilters.iterator();
            while (iterator.hasNext()) {
                if (((PropertyFilter)iterator.next()).apply(object2, string2, object3)) continue;
                return false;
            }
        }
        if (((SerializeFilterable)object).propertyFilters != null) {
            object = ((SerializeFilterable)object).propertyFilters.iterator();
            while (object.hasNext()) {
                if (((PropertyFilter)object.next()).apply(object2, string2, object3)) continue;
                return false;
            }
        }
        return true;
    }

    public boolean applyLabel(SerializeFilterable object, String string2) {
        if (this.labelFilters != null) {
            Iterator iterator = this.labelFilters.iterator();
            while (iterator.hasNext()) {
                if (((LabelFilter)iterator.next()).apply(string2)) continue;
                return false;
            }
        }
        if (((SerializeFilterable)object).labelFilters != null) {
            object = ((SerializeFilterable)object).labelFilters.iterator();
            while (object.hasNext()) {
                if (((LabelFilter)object.next()).apply(string2)) continue;
                return false;
            }
        }
        return true;
    }

    public boolean applyName(SerializeFilterable object, Object object2, String string2) {
        if (this.propertyPreFilters != null) {
            Iterator iterator = this.propertyPreFilters.iterator();
            while (iterator.hasNext()) {
                if (((PropertyPreFilter)iterator.next()).apply(this, object2, string2)) continue;
                return false;
            }
        }
        if (((SerializeFilterable)object).propertyPreFilters != null) {
            object = ((SerializeFilterable)object).propertyPreFilters.iterator();
            while (object.hasNext()) {
                if (((PropertyPreFilter)object.next()).apply(this, object2, string2)) continue;
                return false;
            }
        }
        return true;
    }

    public boolean checkValue(SerializeFilterable serializeFilterable) {
        return this.valueFilters != null && this.valueFilters.size() > 0 || this.contextValueFilters != null && this.contextValueFilters.size() > 0 || serializeFilterable.valueFilters != null && serializeFilterable.valueFilters.size() > 0 || serializeFilterable.contextValueFilters != null && serializeFilterable.contextValueFilters.size() > 0 || this.out.writeNonStringValueAsString;
    }

    public void close() {
        this.out.close();
    }

    public void config(SerializerFeature serializerFeature, boolean bl2) {
        this.out.config(serializerFeature, bl2);
    }

    public boolean containsReference(Object object) {
        return this.references != null && this.references.containsKey(object);
    }

    public void decrementIdent() {
        --this.indentCount;
    }

    public SerialContext getContext() {
        return this.context;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null && this.dateFormatPattern != null) {
            this.dateFormat = new SimpleDateFormat(this.dateFormatPattern, this.locale);
            this.dateFormat.setTimeZone(this.timeZone);
        }
        return this.dateFormat;
    }

    public String getDateFormatPattern() {
        if (this.dateFormat instanceof SimpleDateFormat) {
            return ((SimpleDateFormat)this.dateFormat).toPattern();
        }
        return this.dateFormatPattern;
    }

    public FieldInfo getFieldInfo() {
        return null;
    }

    public int getIndentCount() {
        return this.indentCount;
    }

    public SerializeConfig getMapping() {
        return this.config;
    }

    public ObjectSerializer getObjectWriter(Class<?> clazz) {
        return this.config.getObjectWriter(clazz);
    }

    public SerializeWriter getWriter() {
        return this.out;
    }

    public boolean hasNameFilters(SerializeFilterable serializeFilterable) {
        return this.nameFilters != null && this.nameFilters.size() > 0 || serializeFilterable.nameFilters != null && serializeFilterable.nameFilters.size() > 0;
    }

    public void incrementIndent() {
        ++this.indentCount;
    }

    public boolean isEnabled(SerializerFeature serializerFeature) {
        return this.out.isEnabled(serializerFeature);
    }

    public final boolean isWriteClassName(Type type, Object object) {
        return this.out.isEnabled(SerializerFeature.WriteClassName) && (type != null || !this.out.isEnabled(SerializerFeature.NotWriteRootClassName) || this.context.parent != null);
    }

    public void popContext() {
        if (this.context != null) {
            this.context = this.context.parent;
        }
    }

    public void println() {
        this.out.write(10);
        for (int i2 = 0; i2 < this.indentCount; ++i2) {
            this.out.write(this.indent);
        }
    }

    public String processKey(SerializeFilterable object, Object object2, String string2, Object object3) {
        String string3 = string2;
        if (this.nameFilters != null) {
            Iterator iterator = this.nameFilters.iterator();
            while (true) {
                string3 = string2;
                if (!iterator.hasNext()) break;
                string2 = ((NameFilter)iterator.next()).process(object2, string2, object3);
            }
        }
        string2 = string3;
        if (((SerializeFilterable)object).nameFilters != null) {
            object = ((SerializeFilterable)object).nameFilters.iterator();
            while (true) {
                string2 = string3;
                if (!object.hasNext()) break;
                string3 = ((NameFilter)object.next()).process(object2, string3, object3);
            }
        }
        return string2;
    }

    public Object processValue(SerializeFilterable object, BeanContext beanContext, Object object2, String string2, Object object3) {
        Object object4;
        block11: {
            block12: {
                object4 = object3;
                if (object3 == null) break block11;
                object4 = object3;
                if (!this.out.writeNonStringValueAsString) break block11;
                if (object3 instanceof Number) break block12;
                object4 = object3;
                if (!(object3 instanceof Boolean)) break block11;
            }
            object4 = object3.toString();
        }
        Iterator<ValueFilter> iterator = this.valueFilters;
        object3 = object4;
        if (iterator != null) {
            iterator = iterator.iterator();
            while (true) {
                object3 = object4;
                if (!iterator.hasNext()) break;
                object4 = iterator.next().process(object2, string2, object4);
            }
        }
        object4 = object3;
        if (((SerializeFilterable)object).valueFilters != null) {
            iterator = ((SerializeFilterable)object).valueFilters.iterator();
            while (true) {
                object4 = object3;
                if (!iterator.hasNext()) break;
                object3 = iterator.next().process(object2, string2, object3);
            }
        }
        object3 = object4;
        if (this.contextValueFilters != null) {
            iterator = this.contextValueFilters.iterator();
            while (true) {
                object3 = object4;
                if (!iterator.hasNext()) break;
                object4 = ((ContextValueFilter)((Object)iterator.next())).process(beanContext, object2, string2, object4);
            }
        }
        object4 = object3;
        if (((SerializeFilterable)object).contextValueFilters != null) {
            object = ((SerializeFilterable)object).contextValueFilters.iterator();
            while (true) {
                object4 = object3;
                if (!object.hasNext()) break;
                object3 = ((ContextValueFilter)object.next()).process(beanContext, object2, string2, object3);
            }
        }
        return object4;
    }

    public void setContext(SerialContext serialContext) {
        this.context = serialContext;
    }

    public void setContext(SerialContext serialContext, Object object, Object object2, int n2) {
        this.setContext(serialContext, object, object2, n2, 0);
    }

    public void setContext(SerialContext serialContext, Object object, Object object2, int n2, int n3) {
        if (this.out.disableCircularReferenceDetect) {
            return;
        }
        this.context = new SerialContext(serialContext, object, object2, n2, n3);
        if (this.references == null) {
            this.references = new IdentityHashMap();
        }
        this.references.put(object, this.context);
    }

    public void setContext(Object object, Object object2) {
        this.setContext(this.context, object, object2, 0);
    }

    public void setDateFormat(String string2) {
        this.dateFormatPattern = string2;
        if (this.dateFormat != null) {
            this.dateFormat = null;
        }
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        if (this.dateFormatPattern != null) {
            this.dateFormatPattern = null;
        }
    }

    public String toString() {
        return this.out.toString();
    }

    public final void write(Object object) {
        if (object == null) {
            this.out.writeNull();
            return;
        }
        ObjectSerializer objectSerializer = this.getObjectWriter(object.getClass());
        try {
            objectSerializer.write(this, object, null, null, 0);
            return;
        }
        catch (IOException iOException) {
            throw new JSONException(iOException.getMessage(), iOException);
        }
    }

    public final void write(String string2) {
        StringCodec.instance.write(this, string2);
    }

    public char writeAfter(SerializeFilterable object, Object object2, char c2) {
        char c3 = c2;
        if (this.afterFilters != null) {
            Iterator iterator = this.afterFilters.iterator();
            while (true) {
                c3 = c2;
                if (!iterator.hasNext()) break;
                c2 = ((AfterFilter)iterator.next()).writeAfter(this, object2, c2);
            }
        }
        c2 = c3;
        if (((SerializeFilterable)object).afterFilters != null) {
            object = ((SerializeFilterable)object).afterFilters.iterator();
            while (true) {
                c2 = c3;
                if (!object.hasNext()) break;
                c3 = ((AfterFilter)object.next()).writeAfter(this, object2, c3);
            }
        }
        return c2;
    }

    public char writeBefore(SerializeFilterable object, Object object2, char c2) {
        char c3 = c2;
        if (this.beforeFilters != null) {
            Iterator iterator = this.beforeFilters.iterator();
            while (true) {
                c3 = c2;
                if (!iterator.hasNext()) break;
                c2 = ((BeforeFilter)iterator.next()).writeBefore(this, object2, c2);
            }
        }
        c2 = c3;
        if (((SerializeFilterable)object).beforeFilters != null) {
            object = ((SerializeFilterable)object).beforeFilters.iterator();
            while (true) {
                c2 = c3;
                if (!object.hasNext()) break;
                c3 = ((BeforeFilter)object.next()).writeBefore(this, object2, c3);
            }
        }
        return c2;
    }

    public boolean writeDirect(JavaBeanSerializer javaBeanSerializer) {
        return this.out.writeDirect && this.writeDirect && javaBeanSerializer.writeDirect;
    }

    protected final void writeKeyValue(char c2, String string2, Object object) {
        if (c2 != '\u0000') {
            this.out.write(c2);
        }
        this.out.writeFieldName(string2);
        this.write(object);
    }

    public void writeNull() {
        this.out.writeNull();
    }

    public void writeReference(Object object) {
        SerialContext serialContext = this.context;
        if (object == serialContext.object) {
            this.out.write("{\"$ref\":\"@\"}");
            return;
        }
        SerialContext serialContext2 = serialContext.parent;
        if (serialContext2 != null && object == serialContext2.object) {
            this.out.write("{\"$ref\":\"..\"}");
            return;
        }
        while (true) {
            if (serialContext.parent == null) {
                if (object != serialContext.object) break;
                this.out.write("{\"$ref\":\"$\"}");
                return;
            }
            serialContext = serialContext.parent;
        }
        this.out.write("{\"$ref\":\"");
        this.out.write(this.references.get(object).toString());
        this.out.write("\"}");
    }

    public final void writeWithFieldName(Object object, Object object2) {
        this.writeWithFieldName(object, object2, null, 0);
    }

    /*
     * Unable to fully structure code
     */
    public final void writeWithFieldName(Object var1_1, Object var2_3, Type var3_4, int var4_5) {
        if (var1_1 != null) ** GOTO lbl5
        try {
            this.out.writeNull();
            return;
lbl5:
            // 1 sources

            this.getObjectWriter(var1_1.getClass()).write(this, var1_1, var2_3, var3_4, var4_5);
            return;
        }
        catch (IOException var1_2) {
            throw new JSONException(var1_2.getMessage(), var1_2);
        }
    }

    public final void writeWithFormat(Object object, String string2) {
        if (object instanceof Date) {
            DateFormat dateFormat;
            DateFormat dateFormat2 = dateFormat = this.getDateFormat();
            if (dateFormat == null) {
                dateFormat2 = new SimpleDateFormat(string2, this.locale);
                dateFormat2.setTimeZone(this.timeZone);
            }
            object = dateFormat2.format((Date)object);
            this.out.writeString((String)object);
            return;
        }
        this.write(object);
    }
}

