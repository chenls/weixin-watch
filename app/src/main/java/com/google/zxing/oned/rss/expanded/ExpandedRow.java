/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded;

import com.google.zxing.oned.rss.expanded.ExpandedPair;
import java.util.ArrayList;
import java.util.List;

final class ExpandedRow {
    private final List<ExpandedPair> pairs;
    private final int rowNumber;
    private final boolean wasReversed;

    ExpandedRow(List<ExpandedPair> list, int n2, boolean bl2) {
        this.pairs = new ArrayList<ExpandedPair>(list);
        this.rowNumber = n2;
        this.wasReversed = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        return object instanceof ExpandedRow && this.pairs.equals(((ExpandedRow)(object = (ExpandedRow)object)).getPairs()) && this.wasReversed == ((ExpandedRow)object).wasReversed;
    }

    List<ExpandedPair> getPairs() {
        return this.pairs;
    }

    int getRowNumber() {
        return this.rowNumber;
    }

    public int hashCode() {
        return this.pairs.hashCode() ^ Boolean.valueOf(this.wasReversed).hashCode();
    }

    boolean isEquivalent(List<ExpandedPair> list) {
        return this.pairs.equals(list);
    }

    boolean isReversed() {
        return this.wasReversed;
    }

    public String toString() {
        return "{ " + this.pairs + " }";
    }
}

