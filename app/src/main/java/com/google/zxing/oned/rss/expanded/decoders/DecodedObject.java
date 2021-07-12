/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded.decoders;

abstract class DecodedObject {
    private final int newPosition;

    DecodedObject(int n2) {
        this.newPosition = n2;
    }

    final int getNewPosition() {
        return this.newPosition;
    }
}

