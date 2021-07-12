/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss;

import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;

final class Pair
extends DataCharacter {
    private int count;
    private final FinderPattern finderPattern;

    Pair(int n2, int n3, FinderPattern finderPattern) {
        super(n2, n3);
        this.finderPattern = finderPattern;
    }

    int getCount() {
        return this.count;
    }

    FinderPattern getFinderPattern() {
        return this.finderPattern;
    }

    void incrementCount() {
        ++this.count;
    }
}

