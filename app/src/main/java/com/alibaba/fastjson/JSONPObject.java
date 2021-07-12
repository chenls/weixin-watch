/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializable;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONPObject
implements JSONSerializable {
    private String function;
    private final List<Object> parameters = new ArrayList<Object>();

    public JSONPObject() {
    }

    public JSONPObject(String string2) {
        this.function = string2;
    }

    public void addParameter(Object object) {
        this.parameters.add(object);
    }

    public String getFunction() {
        return this.function;
    }

    public List<Object> getParameters() {
        return this.parameters;
    }

    public void setFunction(String string2) {
        this.function = string2;
    }

    public String toJSONString() {
        return this.toString();
    }

    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public void write(JSONSerializer jSONSerializer, Object object, Type type, int n2) throws IOException {
        object = jSONSerializer.out;
        ((SerializeWriter)object).write(this.function);
        ((SerializeWriter)object).write(40);
        for (n2 = 0; n2 < this.parameters.size(); ++n2) {
            if (n2 != 0) {
                ((SerializeWriter)object).write(44);
            }
            jSONSerializer.write(this.parameters.get(n2));
        }
        ((SerializeWriter)object).write(41);
    }
}

