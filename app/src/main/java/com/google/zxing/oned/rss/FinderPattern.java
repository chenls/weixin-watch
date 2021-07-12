/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss;

import com.google.zxing.ResultPoint;

public final class FinderPattern {
    private final ResultPoint[] resultPoints;
    private final int[] startEnd;
    private final int value;

    public FinderPattern(int n2, int[] nArray, int n3, int n4, int n5) {
        this.value = n2;
        this.startEnd = nArray;
        this.resultPoints = new ResultPoint[]{new ResultPoint(n3, n5), new ResultPoint(n4, n5)};
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof FinderPattern)) break block2;
                object = (FinderPattern)object;
                if (this.value == ((FinderPattern)object).value) break block3;
            }
            return false;
        }
        return true;
    }

    public ResultPoint[] getResultPoints() {
        return this.resultPoints;
    }

    public int[] getStartEnd() {
        return this.startEnd;
    }

    public int getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value;
    }
}

