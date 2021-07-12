/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.oned.rss.expanded.decoders.DecodedObject;

final class DecodedChar
extends DecodedObject {
    static final char FNC1 = '$';
    private final char value;

    DecodedChar(int n2, char c2) {
        super(n2);
        this.value = c2;
    }

    char getValue() {
        return this.value;
    }

    boolean isFNC1() {
        return this.value == '$';
    }
}

