/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

public final class zzmy {
    /*
     * Enabled aggressive block sorting
     */
    public static String zza(byte[] byArray, int n2, int n3, boolean bl2) {
        if (byArray == null || byArray.length == 0 || n2 < 0 || n3 <= 0 || n2 + n3 > byArray.length) {
            return null;
        }
        int n4 = 57;
        if (bl2) {
            n4 = 75;
        }
        StringBuilder stringBuilder = new StringBuilder(n4 * ((n3 + 16 - 1) / 16));
        int n5 = n3;
        int n6 = 0;
        int n7 = 0;
        while (n5 > 0) {
            if (n7 == 0) {
                if (n3 < 65536) {
                    stringBuilder.append(String.format("%04X:", n2));
                    n4 = n2;
                } else {
                    stringBuilder.append(String.format("%08X:", n2));
                    n4 = n2;
                }
            } else {
                n4 = n6;
                if (n7 == 8) {
                    stringBuilder.append(" -");
                    n4 = n6;
                }
            }
            stringBuilder.append(String.format(" %02X", byArray[n2] & 0xFF));
            n6 = n7 + 1;
            if (bl2 && (n6 == 16 || --n5 == 0)) {
                int n8 = 16 - n6;
                if (n8 > 0) {
                    for (n7 = 0; n7 < n8; ++n7) {
                        stringBuilder.append("   ");
                    }
                }
                if (n8 >= 8) {
                    stringBuilder.append("  ");
                }
                stringBuilder.append("  ");
                for (n7 = 0; n7 < n6; ++n7) {
                    char c2 = (char)byArray[n4 + n7];
                    if (c2 < ' ' || c2 > '~') {
                        c2 = '.';
                    }
                    stringBuilder.append(c2);
                }
            }
            if (n6 == 16 || n5 == 0) {
                stringBuilder.append('\n');
                n7 = 0;
            } else {
                n7 = n6;
            }
            ++n2;
            n6 = n4;
        }
        return stringBuilder.toString();
    }
}

