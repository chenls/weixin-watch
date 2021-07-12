/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.oned.rss.expanded.decoders.DecodedObject;

final class DecodedNumeric
extends DecodedObject {
    static final int FNC1 = 10;
    private final int firstDigit;
    private final int secondDigit;

    DecodedNumeric(int n2, int n3, int n4) throws FormatException {
        super(n2);
        if (n3 < 0 || n3 > 10 || n4 < 0 || n4 > 10) {
            throw FormatException.getFormatInstance();
        }
        this.firstDigit = n3;
        this.secondDigit = n4;
    }

    int getFirstDigit() {
        return this.firstDigit;
    }

    int getSecondDigit() {
        return this.secondDigit;
    }

    int getValue() {
        return this.firstDigit * 10 + this.secondDigit;
    }

    boolean isAnyFNC1() {
        return this.firstDigit == 10 || this.secondDigit == 10;
    }

    boolean isFirstDigitFNC1() {
        return this.firstDigit == 10;
    }

    boolean isSecondDigitFNC1() {
        return this.secondDigit == 10;
    }
}

