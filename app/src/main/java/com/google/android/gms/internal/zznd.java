/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

public class zznd {
    /*
     * Enabled aggressive block sorting
     */
    public static int zza(byte[] byArray, int n2, int n3, int n4) {
        block6: {
            int n5 = n2 + (n3 & 0xFFFFFFFC);
            int n6 = n4;
            n4 = n2;
            n2 = n6;
            while (n4 < n5) {
                n6 = (byArray[n4] & 0xFF | (byArray[n4 + 1] & 0xFF) << 8 | (byArray[n4 + 2] & 0xFF) << 16 | byArray[n4 + 3] << 24) * -862048943;
                n2 = (n6 >>> 17 | n6 << 15) * 461845907 ^ n2;
                n2 = -430675100 + (n2 >>> 19 | n2 << 13) * 5;
                n4 += 4;
            }
            n6 = 0;
            n4 = 0;
            switch (n3 & 3) {
                default: {
                    break block6;
                }
                case 3: {
                    n4 = (byArray[n5 + 2] & 0xFF) << 16;
                }
                case 2: {
                    n6 = n4 | (byArray[n5 + 1] & 0xFF) << 8;
                    break;
                }
                case 1: 
            }
            n4 = (n6 | byArray[n5] & 0xFF) * -862048943;
            n2 = (n4 >>> 17 | n4 << 15) * 461845907 ^ n2;
        }
        n2 ^= n3;
        n2 = (n2 ^ n2 >>> 16) * -2048144789;
        n2 = (n2 ^ n2 >>> 13) * -1028477387;
        return n2 ^ n2 >>> 16;
    }
}

