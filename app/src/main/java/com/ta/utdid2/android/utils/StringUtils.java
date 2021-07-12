/*
 * Decompiled with CFR 0.151.
 */
package com.ta.utdid2.android.utils;

public class StringUtils {
    public static String convertObjectToString(Object object) {
        if (object != null) {
            if (object instanceof String) {
                return ((String)object).toString();
            }
            if (object instanceof Integer) {
                return "" + (Integer)object;
            }
            if (object instanceof Long) {
                return "" + (Long)object;
            }
            if (object instanceof Double) {
                return "" + (Double)object;
            }
            if (object instanceof Float) {
                return "" + ((Float)object).floatValue();
            }
            if (object instanceof Short) {
                return "" + (Short)object;
            }
            if (object instanceof Byte) {
                return "" + (Byte)object;
            }
            if (object instanceof Boolean) {
                return ((Boolean)object).toString();
            }
            if (object instanceof Character) {
                return ((Character)object).toString();
            }
            return object.toString();
        }
        return "";
    }

    public static int hashCode(String object) {
        int n2 = 0;
        int n3 = 0;
        int n4 = n2;
        if (!false) {
            n4 = n2;
            if (((String)object).length() > 0) {
                object = ((String)object).toCharArray();
                n2 = 0;
                while (true) {
                    n4 = n3;
                    if (n2 >= ((Object)object).length) break;
                    n3 = n3 * 31 + object[n2];
                    ++n2;
                }
            }
        }
        return n4;
    }

    public static boolean isEmpty(String string2) {
        return string2 == null || string2.length() <= 0;
    }
}

