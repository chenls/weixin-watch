/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

public class SerialContext {
    public final int features;
    public final Object fieldName;
    public final Object object;
    public final SerialContext parent;

    public SerialContext(SerialContext serialContext, Object object, Object object2, int n2, int n3) {
        this.parent = serialContext;
        this.object = object;
        this.fieldName = object2;
        this.features = n2;
    }

    public String toString() {
        if (this.parent == null) {
            return "$";
        }
        if (this.fieldName instanceof Integer) {
            return this.parent.toString() + "[" + this.fieldName + "]";
        }
        return this.parent.toString() + "." + this.fieldName;
    }
}

