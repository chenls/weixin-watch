/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.parser;

import java.lang.reflect.Type;

public class ParseContext {
    public final Object fieldName;
    public Object object;
    public final ParseContext parent;
    public Type type;

    public ParseContext(ParseContext parseContext, Object object, Object object2) {
        this.parent = parseContext;
        this.object = object;
        this.fieldName = object2;
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

