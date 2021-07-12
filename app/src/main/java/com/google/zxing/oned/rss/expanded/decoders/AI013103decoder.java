/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AI013x0xDecoder;

final class AI013103decoder
extends AI013x0xDecoder {
    AI013103decoder(BitArray bitArray) {
        super(bitArray);
    }

    @Override
    protected void addWeightCode(StringBuilder stringBuilder, int n2) {
        stringBuilder.append("(3103)");
    }

    @Override
    protected int checkWeight(int n2) {
        return n2;
    }
}

