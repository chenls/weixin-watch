/*
 * Decompiled with CFR 0.151.
 */
package android.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
public @interface IntRange {
    public long from() default -9223372036854775808L;

    public long to() default 0x7FFFFFFFFFFFFFFFL;
}

