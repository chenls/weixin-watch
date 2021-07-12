/*
 * Decompiled with CFR 0.151.
 */
package hugo.weaving;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface DebugLog {
}

