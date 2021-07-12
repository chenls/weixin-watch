/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.dynamic;

import com.google.android.gms.dynamic.zzd;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

public final class zze<T>
extends zzd.zza {
    private final T mWrappedObject;

    private zze(T t2) {
        this.mWrappedObject = t2;
    }

    public static <T> zzd zzC(T t2) {
        return new zze<T>(t2);
    }

    public static <T> T zzp(zzd object) {
        if (object instanceof zze) {
            return ((zze)object).mWrappedObject;
        }
        Object object2 = (object = object.asBinder()).getClass().getDeclaredFields();
        if (((Field[])object2).length == 1) {
            if (!((AccessibleObject)(object2 = object2[0])).isAccessible()) {
                ((AccessibleObject)object2).setAccessible(true);
                try {
                    object = ((Field)object2).get(object);
                }
                catch (NullPointerException nullPointerException) {
                    throw new IllegalArgumentException("Binder object is null.", nullPointerException);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw new IllegalArgumentException("remoteBinder is the wrong class.", illegalArgumentException);
                }
                catch (IllegalAccessException illegalAccessException) {
                    throw new IllegalArgumentException("Could not access the field in remoteBinder.", illegalAccessException);
                }
                return (T)object;
            }
            throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly one declared *private* field for the wrapped object. Preferably, this is an instance of the ObjectWrapper<T> class.");
        }
        throw new IllegalArgumentException("The concrete class implementing IObjectWrapper must have exactly *one* declared private field for the wrapped object.  Preferably, this is an instance of the ObjectWrapper<T> class.");
    }
}

