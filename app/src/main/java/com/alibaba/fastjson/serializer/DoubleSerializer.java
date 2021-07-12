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
import java.text.DecimalFormat;

public class DoubleSerializer
implements ObjectSerializer {
    public static final DoubleSerializer instance = new DoubleSerializer();
    private DecimalFormat decimalFormat = null;

    public DoubleSerializer() {
    }

    public DoubleSerializer(String string2) {
        this(new DecimalFormat(string2));
    }

    public DoubleSerializer(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    @Override
    public void write(JSONSerializer object, Object object2, Object object3, Type type, int n2) throws IOException {
        object = ((JSONSerializer)object).out;
        if (object2 == null) {
            ((SerializeWriter)object).writeNull(SerializerFeature.WriteNullNumberAsZero);
            return;
        }
        double d2 = (Double)object2;
        if (Double.isNaN(d2) || Double.isInfinite(d2)) {
            ((SerializeWriter)object).writeNull();
            return;
        }
        if (this.decimalFormat == null) {
            ((SerializeWriter)object).writeDouble(d2, true);
            return;
        }
        ((SerializeWriter)object).write(this.decimalFormat.format(d2));
    }
}

