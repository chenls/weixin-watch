/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzsu;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class zzsv {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void zza(String string2, Object object, StringBuffer stringBuffer, StringBuffer stringBuffer2) throws IllegalAccessException, InvocationTargetException {
        GenericDeclaration genericDeclaration;
        String string3;
        int n2;
        int n3;
        int n4;
        AccessibleObject[] accessibleObjectArray;
        Class<?> clazz;
        int n5;
        if (object == null) {
            return;
        }
        if (object instanceof zzsu) {
            n5 = stringBuffer.length();
            if (string2 != null) {
                stringBuffer2.append(stringBuffer).append(zzsv.zzgP(string2)).append(" <\n");
                stringBuffer.append("  ");
            }
            clazz = object.getClass();
            accessibleObjectArray = clazz.getFields();
            n4 = accessibleObjectArray.length;
        } else {
            string2 = zzsv.zzgP(string2);
            stringBuffer2.append(stringBuffer).append(string2).append(": ");
            if (object instanceof String) {
                string2 = zzsv.zzbZ((String)object);
                stringBuffer2.append("\"").append(string2).append("\"");
            } else if (object instanceof byte[]) {
                zzsv.zza((byte[])object, stringBuffer2);
            } else {
                stringBuffer2.append(object);
            }
            stringBuffer2.append("\n");
            return;
        }
        for (n3 = 0; n3 < n4; ++n3) {
            Field field = accessibleObjectArray[n3];
            n2 = field.getModifiers();
            string3 = field.getName();
            if ("cachedSize".equals(string3) || (n2 & 1) != 1 || (n2 & 8) == 8 || string3.startsWith("_") || string3.endsWith("_")) continue;
            genericDeclaration = field.getType();
            Object object2 = field.get(object);
            if (((Class)genericDeclaration).isArray()) {
                if (((Class)genericDeclaration).getComponentType() == Byte.TYPE) {
                    zzsv.zza(string3, object2, stringBuffer, stringBuffer2);
                    continue;
                }
                n2 = object2 == null ? 0 : Array.getLength(object2);
                for (int i2 = 0; i2 < n2; ++i2) {
                    zzsv.zza(string3, Array.get(object2, i2), stringBuffer, stringBuffer2);
                }
                continue;
            }
            zzsv.zza(string3, object2, stringBuffer, stringBuffer2);
        }
        accessibleObjectArray = clazz.getMethods();
        n2 = accessibleObjectArray.length;
        n3 = 0;
        while (true) {
            block19: {
                if (n3 >= n2) {
                    if (string2 == null) return;
                    stringBuffer.setLength(n5);
                    stringBuffer2.append(stringBuffer).append(">\n");
                    return;
                }
                string3 = ((Method)accessibleObjectArray[n3]).getName();
                if (string3.startsWith("set")) {
                    string3 = string3.substring(3);
                    try {
                        genericDeclaration = clazz.getMethod("has" + string3, new Class[0]);
                        if (!((Boolean)((Method)genericDeclaration).invoke(object, new Object[0])).booleanValue()) break block19;
                    }
                    catch (NoSuchMethodException noSuchMethodException) {}
                    try {
                        genericDeclaration = clazz.getMethod("get" + string3, new Class[0]);
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        break block19;
                    }
                    zzsv.zza(string3, ((Method)genericDeclaration).invoke(object, new Object[0]), stringBuffer, stringBuffer2);
                }
            }
            ++n3;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void zza(byte[] byArray, StringBuffer stringBuffer) {
        if (byArray == null) {
            stringBuffer.append("\"\"");
            return;
        }
        stringBuffer.append('\"');
        int n2 = 0;
        while (true) {
            if (n2 >= byArray.length) {
                stringBuffer.append('\"');
                return;
            }
            int n3 = byArray[n2] & 0xFF;
            if (n3 == 92 || n3 == 34) {
                stringBuffer.append('\\').append((char)n3);
            } else if (n3 >= 32 && n3 < 127) {
                stringBuffer.append((char)n3);
            } else {
                stringBuffer.append(String.format("\\%03o", n3));
            }
            ++n2;
        }
    }

    private static String zzbZ(String string2) {
        String string3 = string2;
        if (!string2.startsWith("http")) {
            string3 = string2;
            if (string2.length() > 200) {
                string3 = string2.substring(0, 200) + "[...]";
            }
        }
        return zzsv.zzcU(string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String zzcU(String string2) {
        int n2 = string2.length();
        StringBuilder stringBuilder = new StringBuilder(n2);
        int n3 = 0;
        while (n3 < n2) {
            char c2 = string2.charAt(n3);
            if (c2 >= ' ' && c2 <= '~' && c2 != '\"' && c2 != '\'') {
                stringBuilder.append(c2);
            } else {
                stringBuilder.append(String.format("\\u%04x", c2));
            }
            ++n3;
        }
        return stringBuilder.toString();
    }

    public static <T extends zzsu> String zzf(T t2) {
        if (t2 == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            zzsv.zza(null, t2, new StringBuffer(), stringBuffer);
            return stringBuffer.toString();
        }
        catch (IllegalAccessException illegalAccessException) {
            return "Error printing proto: " + illegalAccessException.getMessage();
        }
        catch (InvocationTargetException invocationTargetException) {
            return "Error printing proto: " + invocationTargetException.getMessage();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String zzgP(String string2) {
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = 0;
        while (n2 < string2.length()) {
            char c2 = string2.charAt(n2);
            if (n2 == 0) {
                stringBuffer.append(Character.toLowerCase(c2));
            } else if (Character.isUpperCase(c2)) {
                stringBuffer.append('_').append(Character.toLowerCase(c2));
            } else {
                stringBuffer.append(c2);
            }
            ++n2;
        }
        return stringBuffer.toString();
    }
}

