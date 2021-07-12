/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;

public class GlobalHistogramBinarizer
extends Binarizer {
    private static final byte[] EMPTY = new byte[0];
    private static final int LUMINANCE_BITS = 5;
    private static final int LUMINANCE_BUCKETS = 32;
    private static final int LUMINANCE_SHIFT = 3;
    private final int[] buckets;
    private byte[] luminances = EMPTY;

    public GlobalHistogramBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
        this.buckets = new int[32];
    }

    private static int estimateBlackPoint(int[] nArray) throws NotFoundException {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6 = nArray.length;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        for (n5 = 0; n5 < n6; ++n5) {
            n4 = n9;
            if (nArray[n5] > n9) {
                n8 = n5;
                n4 = nArray[n5];
            }
            n3 = n7;
            if (nArray[n5] > n7) {
                n3 = nArray[n5];
            }
            n9 = n4;
            n7 = n3;
        }
        n5 = 0;
        n9 = 0;
        for (n4 = 0; n4 < n6; ++n4) {
            n3 = n4 - n8;
            n2 = nArray[n4] * n3 * n3;
            n3 = n9;
            if (n2 > n9) {
                n5 = n4;
                n3 = n2;
            }
            n9 = n3;
        }
        n9 = n8;
        n4 = n5;
        if (n8 > n5) {
            n4 = n8;
            n9 = n5;
        }
        if (n4 - n9 <= n6 / 16) {
            throw NotFoundException.getNotFoundInstance();
        }
        n2 = n4 - 1;
        n5 = -1;
        for (n8 = n4 - 1; n8 > n9; --n8) {
            n3 = n8 - n9;
            n6 = n3 * n3 * (n4 - n8) * (n7 - nArray[n8]);
            n3 = n5;
            if (n6 > n5) {
                n2 = n8;
                n3 = n6;
            }
            n5 = n3;
        }
        return n2 << 3;
    }

    private void initArrays(int n2) {
        if (this.luminances.length < n2) {
            this.luminances = new byte[n2];
        }
        for (n2 = 0; n2 < 32; ++n2) {
            this.buckets[n2] = 0;
        }
    }

    @Override
    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new GlobalHistogramBinarizer(luminanceSource);
    }

    @Override
    public BitMatrix getBlackMatrix() throws NotFoundException {
        int n2;
        int n3;
        int n4;
        Object object = this.getLuminanceSource();
        int n5 = ((LuminanceSource)object).getWidth();
        int n6 = ((LuminanceSource)object).getHeight();
        BitMatrix bitMatrix = new BitMatrix(n5, n6);
        this.initArrays(n5);
        int[] nArray = this.buckets;
        for (n4 = 1; n4 < 5; ++n4) {
            byte[] byArray = ((LuminanceSource)object).getRow(n6 * n4 / 5, this.luminances);
            n3 = n5 * 4 / 5;
            for (n2 = n5 / 5; n2 < n3; ++n2) {
                int n7 = (byArray[n2] & 0xFF) >> 3;
                nArray[n7] = nArray[n7] + 1;
            }
        }
        n3 = GlobalHistogramBinarizer.estimateBlackPoint(nArray);
        object = ((LuminanceSource)object).getMatrix();
        for (n4 = 0; n4 < n6; ++n4) {
            for (n2 = 0; n2 < n5; ++n2) {
                if ((object[n4 * n5 + n2] & 0xFF) >= n3) continue;
                bitMatrix.set(n2, n4);
            }
        }
        return bitMatrix;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public BitArray getBlackRow(int n2, BitArray bitArray) throws NotFoundException {
        int n3;
        Object object = this.getLuminanceSource();
        int n4 = ((LuminanceSource)object).getWidth();
        if (bitArray == null || bitArray.getSize() < n4) {
            bitArray = new BitArray(n4);
        } else {
            bitArray.clear();
        }
        this.initArrays(n4);
        object = ((LuminanceSource)object).getRow(n2, this.luminances);
        int[] nArray = this.buckets;
        for (n2 = 0; n2 < n4; ++n2) {
            n3 = (object[n2] & 0xFF) >> 3;
            nArray[n3] = nArray[n3] + 1;
        }
        int n5 = GlobalHistogramBinarizer.estimateBlackPoint(nArray);
        int n6 = object[0] & 0xFF;
        n2 = object[1] & 0xFF;
        n3 = 1;
        while (n3 < n4 - 1) {
            int n7 = object[n3 + 1] & 0xFF;
            if ((n2 * 4 - n6 - n7) / 2 < n5) {
                bitArray.set(n3);
            }
            n6 = n2;
            n2 = n7;
            ++n3;
        }
        return bitArray;
    }
}

