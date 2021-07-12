/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.util;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class ASMUtils {
    public static final boolean IS_ANDROID;
    public static final String JAVA_VM_NAME;

    static {
        JAVA_VM_NAME = System.getProperty("java.vm.name");
        IS_ANDROID = ASMUtils.isAndroid(JAVA_VM_NAME);
    }

    public static boolean checkName(String string2) {
        boolean bl2 = true;
        int n2 = 0;
        while (true) {
            block4: {
                boolean bl3;
                block3: {
                    bl3 = bl2;
                    if (n2 >= string2.length()) break block3;
                    char c2 = string2.charAt(n2);
                    if (c2 >= '\u0001' && c2 <= '\u007f') break block4;
                    bl3 = false;
                }
                return bl3;
            }
            ++n2;
        }
    }

    public static String desc(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return ASMUtils.getPrimitiveLetter(clazz);
        }
        if (clazz.isArray()) {
            return "[" + ASMUtils.desc(clazz.getComponentType());
        }
        return "L" + ASMUtils.type(clazz) + ";";
    }

    public static String desc(Method method) {
        Class<?>[] classArray = method.getParameterTypes();
        StringBuilder stringBuilder = new StringBuilder(classArray.length + 1 << 4);
        stringBuilder.append('(');
        for (int i2 = 0; i2 < classArray.length; ++i2) {
            stringBuilder.append(ASMUtils.desc(classArray[i2]));
        }
        stringBuilder.append(')');
        stringBuilder.append(ASMUtils.desc(method.getReturnType()));
        return stringBuilder.toString();
    }

    public static Type getMethodType(Class<?> type, String string2) {
        try {
            type = type.getMethod(string2, new Class[0]).getGenericReturnType();
            return type;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static String getPrimitiveLetter(Class<?> clazz) {
        if (Integer.TYPE == clazz) {
            return "I";
        }
        if (Void.TYPE == clazz) {
            return "V";
        }
        if (Boolean.TYPE == clazz) {
            return "Z";
        }
        if (Character.TYPE == clazz) {
            return "C";
        }
        if (Byte.TYPE == clazz) {
            return "B";
        }
        if (Short.TYPE == clazz) {
            return "S";
        }
        if (Float.TYPE == clazz) {
            return "F";
        }
        if (Long.TYPE == clazz) {
            return "J";
        }
        if (Double.TYPE == clazz) {
            return "D";
        }
        throw new IllegalStateException("Type: " + clazz.getCanonicalName() + " is not a primitive type");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean isAndroid(String string2) {
        return string2 != null && ((string2 = string2.toLowerCase()).contains("dalvik") || string2.contains("lemur"));
    }

    public static String type(Class<?> clazz) {
        if (clazz.isArray()) {
            return "[" + ASMUtils.desc(clazz.getComponentType());
        }
        if (!clazz.isPrimitive()) {
            return clazz.getName().replace('.', '/');
        }
        return ASMUtils.getPrimitiveLetter(clazz);
    }
}

