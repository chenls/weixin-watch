/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.qrcode.detector.FinderPattern;

public final class FinderPatternInfo {
    private final FinderPattern bottomLeft;
    private final FinderPattern topLeft;
    private final FinderPattern topRight;

    public FinderPatternInfo(FinderPattern[] finderPatternArray) {
        this.bottomLeft = finderPatternArray[0];
        this.topLeft = finderPatternArray[1];
        this.topRight = finderPatternArray[2];
    }

    public FinderPattern getBottomLeft() {
        return this.bottomLeft;
    }

    public FinderPattern getTopLeft() {
        return this.topLeft;
    }

    public FinderPattern getTopRight() {
        return this.topRight;
    }
}

