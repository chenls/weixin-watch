/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AI01weightDecoder;

final class AI013x0x1xDecoder
extends AI01weightDecoder {
    private static final int DATE_SIZE = 16;
    private static final int HEADER_SIZE = 8;
    private static final int WEIGHT_SIZE = 20;
    private final String dateCode;
    private final String firstAIdigits;

    AI013x0x1xDecoder(BitArray bitArray, String string2, String string3) {
        super(bitArray);
        this.dateCode = string3;
        this.firstAIdigits = string2;
    }

    private void encodeCompressedDate(StringBuilder stringBuilder, int n2) {
        int n3 = this.getGeneralDecoder().extractNumericValueFromBitArray(n2, 16);
        if (n3 == 38400) {
            return;
        }
        stringBuilder.append('(');
        stringBuilder.append(this.dateCode);
        stringBuilder.append(')');
        n2 = n3 % 32;
        int n4 = n3 / 32;
        n3 = n4 % 12 + 1;
        if ((n4 /= 12) / 10 == 0) {
            stringBuilder.append('0');
        }
        stringBuilder.append(n4);
        if (n3 / 10 == 0) {
            stringBuilder.append('0');
        }
        stringBuilder.append(n3);
        if (n2 / 10 == 0) {
            stringBuilder.append('0');
        }
        stringBuilder.append(n2);
    }

    @Override
    protected void addWeightCode(StringBuilder stringBuilder, int n2) {
        stringBuilder.append('(');
        stringBuilder.append(this.firstAIdigits);
        stringBuilder.append(n2 /= 100000);
        stringBuilder.append(')');
    }

    @Override
    protected int checkWeight(int n2) {
        return n2 % 100000;
    }

    @Override
    public String parseInformation() throws NotFoundException {
        if (this.getInformation().getSize() != 84) {
            throw NotFoundException.getNotFoundInstance();
        }
        StringBuilder stringBuilder = new StringBuilder();
        this.encodeCompressedGtin(stringBuilder, 8);
        this.encodeCompressedWeight(stringBuilder, 48, 20);
        this.encodeCompressedDate(stringBuilder, 68);
        return stringBuilder.toString();
    }
}

