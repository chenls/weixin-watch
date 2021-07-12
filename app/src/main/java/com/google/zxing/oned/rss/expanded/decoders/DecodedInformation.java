/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.oned.rss.expanded.decoders.DecodedObject;

final class DecodedInformation
extends DecodedObject {
    private final String newString;
    private final boolean remaining;
    private final int remainingValue;

    DecodedInformation(int n2, String string2) {
        super(n2);
        this.newString = string2;
        this.remaining = false;
        this.remainingValue = 0;
    }

    DecodedInformation(int n2, String string2, int n3) {
        super(n2);
        this.remaining = true;
        this.remainingValue = n3;
        this.newString = string2;
    }

    String getNewString() {
        return this.newString;
    }

    int getRemainingValue() {
        return this.remainingValue;
    }

    boolean isRemaining() {
        return this.remaining;
    }
}

