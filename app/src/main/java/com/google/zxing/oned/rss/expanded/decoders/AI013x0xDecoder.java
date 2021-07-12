/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AI01weightDecoder;

abstract class AI013x0xDecoder
extends AI01weightDecoder {
    private static final int HEADER_SIZE = 5;
    private static final int WEIGHT_SIZE = 15;

    AI013x0xDecoder(BitArray bitArray) {
        super(bitArray);
    }

    @Override
    public String parseInformation() throws NotFoundException {
        if (this.getInformation().getSize() != 60) {
            throw NotFoundException.getNotFoundInstance();
        }
        StringBuilder stringBuilder = new StringBuilder();
        this.encodeCompressedGtin(stringBuilder, 5);
        this.encodeCompressedWeight(stringBuilder, 45, 15);
        return stringBuilder.toString();
    }
}

