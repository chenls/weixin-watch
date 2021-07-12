/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.lang.reflect.Type;

public class PrimitiveArraySerializer
implements ObjectSerializer {
    public static PrimitiveArraySerializer instance = new PrimitiveArraySerializer();

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public final void write(JSONSerializer object, Object object2, Object object3, Type type, int n2) throws IOException {
        object = ((JSONSerializer)object).out;
        if (object2 == null) {
            ((SerializeWriter)object).writeNull(SerializerFeature.WriteNullListAsEmpty);
            return;
        }
        if (object2 instanceof int[]) {
            object2 = (int[])object2;
            ((SerializeWriter)object).write(91);
            n2 = 0;
            while (true) {
                if (n2 >= ((Object)object2).length) {
                    ((SerializeWriter)object).write(93);
                    return;
                }
                if (n2 != 0) {
                    ((SerializeWriter)object).write(44);
                }
                ((SerializeWriter)object).writeInt((int)object2[n2]);
                ++n2;
            }
        }
        if (object2 instanceof short[]) {
            object2 = (short[])object2;
            ((SerializeWriter)object).write(91);
            n2 = 0;
            while (true) {
                if (n2 >= ((Object)object2).length) {
                    ((SerializeWriter)object).write(93);
                    return;
                }
                if (n2 != 0) {
                    ((SerializeWriter)object).write(44);
                }
                ((SerializeWriter)object).writeInt((int)object2[n2]);
                ++n2;
            }
        }
        if (object2 instanceof long[]) {
            object2 = (long[])object2;
            ((SerializeWriter)object).write(91);
            n2 = 0;
            while (true) {
                if (n2 >= ((Object)object2).length) {
                    ((SerializeWriter)object).write(93);
                    return;
                }
                if (n2 != 0) {
                    ((SerializeWriter)object).write(44);
                }
                ((SerializeWriter)object).writeLong((long)object2[n2]);
                ++n2;
            }
        }
        if (object2 instanceof boolean[]) {
            object2 = (boolean[])object2;
            ((SerializeWriter)object).write(91);
            n2 = 0;
            while (true) {
                if (n2 >= ((Object)object2).length) {
                    ((SerializeWriter)object).write(93);
                    return;
                }
                if (n2 != 0) {
                    ((SerializeWriter)object).write(44);
                }
                ((SerializeWriter)object).write((boolean)object2[n2]);
                ++n2;
            }
        }
        if (!(object2 instanceof float[])) {
            if (!(object2 instanceof double[])) {
                if (object2 instanceof byte[]) {
                    ((SerializeWriter)object).writeByteArray((byte[])object2);
                    return;
                }
                ((SerializeWriter)object).writeString(new String((char[])object2));
                return;
            }
        } else {
            object2 = (float[])object2;
            ((SerializeWriter)object).write(91);
            n2 = 0;
            while (true) {
                Object object4;
                if (n2 >= ((Object)object2).length) {
                    ((SerializeWriter)object).write(93);
                    return;
                }
                if (n2 != 0) {
                    ((SerializeWriter)object).write(44);
                }
                if (Float.isNaN((float)(object4 = object2[n2]))) {
                    ((SerializeWriter)object).writeNull();
                } else {
                    ((SerializeWriter)object).append(Float.toString((float)object4));
                }
                ++n2;
            }
        }
        object2 = (double[])object2;
        ((SerializeWriter)object).write(91);
        n2 = 0;
        while (true) {
            Object object5;
            if (n2 >= ((Object)object2).length) {
                ((SerializeWriter)object).write(93);
                return;
            }
            if (n2 != 0) {
                ((SerializeWriter)object).write(44);
            }
            if (Double.isNaN((double)(object5 = object2[n2]))) {
                ((SerializeWriter)object).writeNull();
            } else {
                ((SerializeWriter)object).append(Double.toString((double)object5));
            }
            ++n2;
        }
    }
}

