/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package ticwear.design.utils.blur;

import android.graphics.Bitmap;
import java.lang.reflect.Array;

public class FastBlur {
    /*
     * Enabled aggressive block sorting
     */
    public static Bitmap doBlur(Bitmap bitmap, int n2, boolean bl2) {
        int n3;
        int n4;
        int[] nArray;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        int n12;
        int n13;
        if (!bl2) {
            bitmap = bitmap.copy(bitmap.getConfig(), true);
        }
        if (n2 < 1) {
            return null;
        }
        int n14 = bitmap.getWidth();
        int n15 = bitmap.getHeight();
        int[] nArray2 = new int[n14 * n15];
        bitmap.getPixels(nArray2, 0, n14, 0, 0, n14, n15);
        int n16 = n14 - 1;
        int n17 = n15 - 1;
        int n18 = n14 * n15;
        int n19 = n2 + n2 + 1;
        int[] nArray3 = new int[n18];
        int[] nArray4 = new int[n18];
        int[] nArray5 = new int[n18];
        int[] nArray6 = new int[Math.max(n14, n15)];
        n18 = n19 + 1 >> 1;
        int n20 = n18 * n18;
        int[] nArray7 = new int[n20 * 256];
        for (n18 = 0; n18 < n20 * 256; ++n18) {
            nArray7[n18] = n18 / n20;
        }
        int n21 = 0;
        int n22 = 0;
        int[][] nArray8 = (int[][])Array.newInstance(Integer.TYPE, n19, 3);
        int n23 = n2 + 1;
        int n24 = 0;
        while (true) {
            if (n24 >= n15) break;
            n13 = 0;
            n12 = 0;
            n11 = 0;
            n20 = 0;
            n10 = 0;
            n9 = 0;
            n18 = 0;
            n8 = 0;
            n7 = 0;
            for (n6 = -n2; n6 <= n2; ++n6) {
                n5 = nArray2[Math.min(n16, Math.max(n6, 0)) + n21];
                nArray = nArray8[n6 + n2];
                nArray[0] = (0xFF0000 & n5) >> 16;
                nArray[1] = (0xFF00 & n5) >> 8;
                nArray[2] = n5 & 0xFF;
                n5 = n23 - Math.abs(n6);
                n11 += nArray[0] * n5;
                n12 += nArray[1] * n5;
                n13 += nArray[2] * n5;
                if (n6 > 0) {
                    n7 += nArray[0];
                    n8 += nArray[1];
                    n18 += nArray[2];
                    continue;
                }
                n9 += nArray[0];
                n10 += nArray[1];
                n20 += nArray[2];
            }
            n5 = n2;
            for (n6 = 0; n6 < n14; n7 -= nArray[0], n8 -= nArray[1], n18 -= nArray[2], ++n21, ++n6) {
                nArray3[n21] = nArray7[n11];
                nArray4[n21] = nArray7[n12];
                nArray5[n21] = nArray7[n13];
                nArray = nArray8[(n5 - n2 + n19) % n19];
                int n25 = nArray[0];
                n4 = nArray[1];
                n3 = nArray[2];
                if (n24 == 0) {
                    nArray6[n6] = Math.min(n6 + n2 + 1, n16);
                }
                int n26 = nArray2[nArray6[n6] + n22];
                nArray[0] = (0xFF0000 & n26) >> 16;
                nArray[1] = (0xFF00 & n26) >> 8;
                nArray[2] = n26 & 0xFF;
                n11 = n11 - n9 + (n7 += nArray[0]);
                n12 = n12 - n10 + (n8 += nArray[1]);
                n13 = n13 - n20 + (n18 += nArray[2]);
                n5 = (n5 + 1) % n19;
                nArray = nArray8[n5 % n19];
                n9 = n9 - n25 + nArray[0];
                n10 = n10 - n4 + nArray[1];
                n20 = n20 - n3 + nArray[2];
            }
            n22 += n14;
            ++n24;
        }
        n18 = 0;
        while (true) {
            if (n18 >= n14) {
                bitmap.setPixels(nArray2, 0, n14, 0, 0, n14, n15);
                return bitmap;
            }
            n9 = 0;
            n11 = 0;
            n22 = 0;
            n8 = 0;
            n13 = 0;
            n12 = 0;
            n20 = 0;
            n7 = 0;
            n10 = 0;
            n24 = -n2 * n14;
            for (n21 = -n2; n21 <= n2; ++n21) {
                n6 = Math.max(0, n24) + n18;
                nArray = nArray8[n21 + n2];
                nArray[0] = nArray3[n6];
                nArray[1] = nArray4[n6];
                nArray[2] = nArray5[n6];
                n5 = n23 - Math.abs(n21);
                n22 += nArray3[n6] * n5;
                n11 += nArray4[n6] * n5;
                n9 += nArray5[n6] * n5;
                if (n21 > 0) {
                    n10 += nArray[0];
                    n7 += nArray[1];
                    n20 += nArray[2];
                } else {
                    n12 += nArray[0];
                    n13 += nArray[1];
                    n8 += nArray[2];
                }
                n6 = n24;
                if (n21 < n17) {
                    n6 = n24 + n14;
                }
                n24 = n6;
            }
            n21 = n18;
            n6 = n2;
            for (n24 = 0; n24 < n15; n10 -= nArray[0], n7 -= nArray[1], n20 -= nArray[2], n21 += n14, ++n24) {
                nArray2[n21] = 0xFF000000 & nArray2[n21] | nArray7[n22] << 16 | nArray7[n11] << 8 | nArray7[n9];
                nArray = nArray8[(n6 - n2 + n19) % n19];
                n3 = nArray[0];
                n16 = nArray[1];
                n5 = nArray[2];
                if (n18 == 0) {
                    nArray6[n24] = Math.min(n24 + n23, n17) * n14;
                }
                n4 = n18 + nArray6[n24];
                nArray[0] = nArray3[n4];
                nArray[1] = nArray4[n4];
                nArray[2] = nArray5[n4];
                n22 = n22 - n12 + (n10 += nArray[0]);
                n11 = n11 - n13 + (n7 += nArray[1]);
                n9 = n9 - n8 + (n20 += nArray[2]);
                n6 = (n6 + 1) % n19;
                nArray = nArray8[n6];
                n12 = n12 - n3 + nArray[0];
                n13 = n13 - n16 + nArray[1];
                n8 = n8 - n5 + nArray[2];
            }
            ++n18;
        }
    }
}

