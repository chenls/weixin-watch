/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded;

import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.ExpandedPair;
import java.util.List;

final class BitArrayBuilder {
    private BitArrayBuilder() {
    }

    static BitArray buildBitArray(List<ExpandedPair> list) {
        int n2;
        int n3 = n2 = list.size() * 2 - 1;
        if (list.get(list.size() - 1).getRightChar() == null) {
            n3 = n2 - 1;
        }
        BitArray bitArray = new BitArray(n3 * 12);
        n3 = 0;
        int n4 = list.get(0).getRightChar().getValue();
        for (n2 = 11; n2 >= 0; --n2) {
            if ((1 << n2 & n4) != 0) {
                bitArray.set(n3);
            }
            ++n3;
        }
        for (n4 = 1; n4 < list.size(); ++n4) {
            ExpandedPair expandedPair = list.get(n4);
            int n5 = expandedPair.getLeftChar().getValue();
            for (n2 = 11; n2 >= 0; --n2) {
                if ((1 << n2 & n5) != 0) {
                    bitArray.set(n3);
                }
                ++n3;
            }
            n2 = n3;
            if (expandedPair.getRightChar() != null) {
                int n6 = expandedPair.getRightChar().getValue();
                n5 = 11;
                while (true) {
                    n2 = ++n3;
                    if (n5 < 0) break;
                    if ((1 << n5 & n6) != 0) {
                        bitArray.set(n3);
                    }
                    --n5;
                }
            }
            n3 = n2;
        }
        return bitArray;
    }
}

