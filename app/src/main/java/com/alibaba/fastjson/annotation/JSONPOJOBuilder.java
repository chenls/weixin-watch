/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface JSONPOJOBuilder {
    public String buildMethod() default "build";

    public String withPrefix() default "with";
}

