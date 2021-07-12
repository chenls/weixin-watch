/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson;

import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class TypeReference<T> {
    protected final Type type;

    protected TypeReference() {
        this.type = ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected TypeReference(Type ... typeArray) {
        Type[] typeArray2 = (Type[])((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Type type = typeArray2.getRawType();
        typeArray2 = typeArray2.getActualTypeArguments();
        int n2 = 0;
        int n3 = 0;
        while (true) {
            int n4;
            block4: {
                block3: {
                    if (n3 >= typeArray2.length) break block3;
                    n4 = n2;
                    if (!(typeArray2[n3] instanceof TypeVariable)) break block4;
                    n4 = n2 + 1;
                    typeArray2[n3] = typeArray[n2];
                    if (n4 < typeArray.length) break block4;
                }
                this.type = new ParameterizedTypeImpl(typeArray2, this.getClass(), type);
                return;
            }
            ++n3;
            n2 = n4;
        }
    }

    public Type getType() {
        return this.type;
    }
}

