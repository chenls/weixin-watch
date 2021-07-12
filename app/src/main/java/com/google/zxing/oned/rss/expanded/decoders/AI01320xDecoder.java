/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AI013x0xDecoder;

final class AI01320xDecoder
extends AI013x0xDecoder {
    AI01320xDecoder(BitArray bitArray) {
        super(bitArray);
    }

    @Override
    protected void addWeightCode(StringBuilder stringBuilder, int n2) {
        if (n2 < 10000) {
            stringBuilder.append("(3202)");
            return;
        }
        stringBuilder.append("(3203)");
    }

    @Override
    protected int checkWeight(int n2) {
        if (n2 < 10000) {
            return n2;
        }
        return n2 - 10000;
    }
}

