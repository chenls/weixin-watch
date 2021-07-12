/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.asm;

public class Type {
    public static final Type BOOLEAN_TYPE;
    public static final Type BYTE_TYPE;
    public static final Type CHAR_TYPE;
    public static final Type DOUBLE_TYPE;
    public static final Type FLOAT_TYPE;
    public static final Type INT_TYPE;
    public static final Type LONG_TYPE;
    public static final Type SHORT_TYPE;
    public static final Type VOID_TYPE;
    private final char[] buf;
    private final int len;
    private final int off;
    protected final int sort;

    static {
        VOID_TYPE = new Type(0, null, 0x56050000, 1);
        BOOLEAN_TYPE = new Type(1, null, 1509950721, 1);
        CHAR_TYPE = new Type(2, null, 1124075009, 1);
        BYTE_TYPE = new Type(3, null, 1107297537, 1);
        SHORT_TYPE = new Type(4, null, 1392510721, 1);
        INT_TYPE = new Type(5, null, 1224736769, 1);
        FLOAT_TYPE = new Type(6, null, 1174536705, 1);
        LONG_TYPE = new Type(7, null, 1241579778, 1);
        DOUBLE_TYPE = new Type(8, null, 1141048066, 1);
    }

    private Type(int n2, char[] cArray, int n3, int n4) {
        this.sort = n2;
        this.buf = cArray;
        this.off = n3;
        this.len = n4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int getArgumentsAndReturnSizes(String string2) {
        int n2 = 1;
        int n3 = 1;
        while (true) {
            int n4 = n3 + 1;
            if ((n3 = (int)string2.charAt(n3)) == 41) {
                n3 = string2.charAt(n4);
                if (n3 == 86) {
                    n3 = 0;
                    return n3 | n2 << 2;
                }
                if (n3 == 68 || n3 == 74) {
                    n3 = 2;
                    return n3 | n2 << 2;
                }
                n3 = 1;
                return n3 | n2 << 2;
            }
            if (n3 == 76) {
                while (true) {
                    n3 = n4 + 1;
                    if (string2.charAt(n4) == ';') break;
                    n4 = n3;
                }
                ++n2;
                continue;
            }
            if (n3 == 68 || n3 == 74) {
                n2 += 2;
                n3 = n4;
                continue;
            }
            ++n2;
            n3 = n4;
        }
    }

    public static Type getType(String string2) {
        return Type.getType(string2.toCharArray(), 0);
    }

    private static Type getType(char[] cArray, int n2) {
        int n3;
        switch (cArray[n2]) {
            default: {
                n3 = 1;
                while (cArray[n2 + n3] != ';') {
                    ++n3;
                }
                break;
            }
            case 'V': {
                return VOID_TYPE;
            }
            case 'Z': {
                return BOOLEAN_TYPE;
            }
            case 'C': {
                return CHAR_TYPE;
            }
            case 'B': {
                return BYTE_TYPE;
            }
            case 'S': {
                return SHORT_TYPE;
            }
            case 'I': {
                return INT_TYPE;
            }
            case 'F': {
                return FLOAT_TYPE;
            }
            case 'J': {
                return LONG_TYPE;
            }
            case 'D': {
                return DOUBLE_TYPE;
            }
            case '[': {
                int n4 = 1;
                while (cArray[n2 + n4] == '[') {
                    ++n4;
                }
                int n5 = n4;
                if (cArray[n2 + n4] == 'L') {
                    ++n4;
                    do {
                        n5 = ++n4;
                    } while (cArray[n2 + n4] != ';');
                }
                return new Type(9, cArray, n2, n5 + 1);
            }
        }
        return new Type(10, cArray, n2 + 1, n3 - 1);
    }

    String getDescriptor() {
        return new String(this.buf, this.off, this.len);
    }

    public String getInternalName() {
        return new String(this.buf, this.off, this.len);
    }
}

