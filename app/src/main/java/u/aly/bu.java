/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.lang.reflect.InvocationTargetException;
import u.aly.bt;

public class bu {
    public static bt a(Class<? extends bt> object, int n2) {
        try {
            object = (bt)((Class)object).getMethod("findByValue", Integer.TYPE).invoke(null, n2);
            return object;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
        catch (IllegalAccessException illegalAccessException) {
            return null;
        }
        catch (InvocationTargetException invocationTargetException) {
            return null;
        }
    }
}

