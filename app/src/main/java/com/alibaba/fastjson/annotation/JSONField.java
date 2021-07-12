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
@Target(value={ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface JSONField {
    public boolean deserialize() default true;

    public String format() default "";

    public String label() default "";

    public String name() default "";

    public int ordinal() default 0;

    public Feature[] parseFeatures() default {};

    public boolean serialize() default true;

    public SerializerFeature[] serialzeFeatures() default {};
}

