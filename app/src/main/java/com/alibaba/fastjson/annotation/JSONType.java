/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.annotation;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface JSONType {
    public boolean alphabetic() default true;

    public boolean asm() default true;

    public Class<?> builder() default Void.class;

    public String[] ignores() default {};

    public String[] includes() default {};

    public Class<?> mappingTo() default Void.class;

    public String[] orders() default {};

    public Feature[] parseFeatures() default {};

    public Class<?>[] seeAlso() default {};

    public SerializerFeature[] serialzeFeatures() default {};

    public String typeName() default "";
}

