/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class DefaultFieldDeserializer
extends FieldDeserializer {
    private ObjectDeserializer fieldValueDeserilizer;

    public DefaultFieldDeserializer(ParserConfig parserConfig, Class<?> clazz, FieldInfo fieldInfo) {
        super(clazz, fieldInfo);
    }

    @Override
    public int getFastMatchToken() {
        if (this.fieldValueDeserilizer != null) {
            return this.fieldValueDeserilizer.getFastMatchToken();
        }
        return 2;
    }

    public ObjectDeserializer getFieldValueDeserilizer(ParserConfig parserConfig) {
        if (this.fieldValueDeserilizer == null) {
            this.fieldValueDeserilizer = parserConfig.getDeserializer(this.fieldInfo.fieldClass, this.fieldInfo.fieldType);
        }
        return this.fieldValueDeserilizer;
    }

    @Override
    public void parseField(DefaultJSONParser defaultJSONParser, Object object, Type type, Map<String, Object> map) {
        if (this.fieldValueDeserilizer == null) {
            this.fieldValueDeserilizer = defaultJSONParser.getConfig().getDeserializer(this.fieldInfo);
        }
        if (type instanceof ParameterizedType) {
            defaultJSONParser.getContext().type = type;
        }
        type = this.fieldValueDeserilizer.deserialze(defaultJSONParser, this.fieldInfo.fieldType, this.fieldInfo.name);
        if (defaultJSONParser.getResolveStatus() == 1) {
            object = defaultJSONParser.getLastResolveTask();
            ((DefaultJSONParser.ResolveTask)object).fieldDeserializer = this;
            ((DefaultJSONParser.ResolveTask)object).ownerContext = defaultJSONParser.getContext();
            defaultJSONParser.setResolveStatus(0);
            return;
        }
        if (object == null) {
            map.put(this.fieldInfo.name, type);
            return;
        }
        this.setValue(object, type);
    }
}

