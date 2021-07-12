/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;

final class AnyAIDecoder
extends AbstractExpandedDecoder {
    private static final int HEADER_SIZE = 5;

    AnyAIDecoder(BitArray bitArray) {
        super(bitArray);
    }

    @Override
    public String parseInformation() throws NotFoundException, FormatException {
        StringBuilder stringBuilder = new StringBuilder();
        return this.getGeneralDecoder().decodeAllCodes(stringBuilder, 5);
    }
}

