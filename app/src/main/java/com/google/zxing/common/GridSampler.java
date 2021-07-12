/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DefaultGridSampler;
import com.google.zxing.common.PerspectiveTransform;

public abstract class GridSampler {
    private static GridSampler gridSampler = new DefaultGridSampler();

    /*
     * Enabled aggressive block sorting
     */
    protected static void checkAndNudgePoints(BitMatrix bitMatrix, float[] fArray) throws NotFoundException {
        int n2;
        int n3;
        int n4;
        int n5 = bitMatrix.getWidth();
        int n6 = bitMatrix.getHeight();
        boolean bl2 = true;
        for (n4 = 0; n4 < fArray.length && bl2; n4 += 2) {
            n3 = (int)fArray[n4];
            n2 = (int)fArray[n4 + 1];
            if (n3 < -1 || n3 > n5 || n2 < -1 || n2 > n6) {
                throw NotFoundException.getNotFoundInstance();
            }
            bl2 = false;
            if (n3 == -1) {
                fArray[n4] = 0.0f;
                bl2 = true;
            } else if (n3 == n5) {
                fArray[n4] = n5 - 1;
                bl2 = true;
            }
            if (n2 == -1) {
                fArray[n4 + 1] = 0.0f;
                bl2 = true;
                continue;
            }
            if (n2 != n6) continue;
            fArray[n4 + 1] = n6 - 1;
            bl2 = true;
        }
        bl2 = true;
        for (n4 = fArray.length - 2; n4 >= 0 && bl2; n4 -= 2) {
            n3 = (int)fArray[n4];
            n2 = (int)fArray[n4 + 1];
            if (n3 < -1 || n3 > n5 || n2 < -1 || n2 > n6) {
                throw NotFoundException.getNotFoundInstance();
            }
            bl2 = false;
            if (n3 == -1) {
                fArray[n4] = 0.0f;
                bl2 = true;
            } else if (n3 == n5) {
                fArray[n4] = n5 - 1;
                bl2 = true;
            }
            if (n2 == -1) {
                fArray[n4 + 1] = 0.0f;
                bl2 = true;
                continue;
            }
            if (n2 != n6) continue;
            fArray[n4 + 1] = n6 - 1;
            bl2 = true;
        }
    }

    public static GridSampler getInstance() {
        return gridSampler;
    }

    public static void setGridSampler(GridSampler gridSampler) {
        GridSampler.gridSampler = gridSampler;
    }

    public abstract BitMatrix sampleGrid(BitMatrix var1, int var2, int var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, float var14, float var15, float var16, float var17, float var18, float var19) throws NotFoundException;

    public abstract BitMatrix sampleGrid(BitMatrix var1, int var2, int var3, PerspectiveTransform var4) throws NotFoundException;
}

