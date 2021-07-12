/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.decoder.BoundingBox;
import com.google.zxing.pdf417.decoder.Codeword;
import java.util.Formatter;

class DetectionResultColumn {
    private static final int MAX_NEARBY_DISTANCE = 5;
    private final BoundingBox boundingBox;
    private final Codeword[] codewords;

    DetectionResultColumn(BoundingBox boundingBox) {
        this.boundingBox = new BoundingBox(boundingBox);
        this.codewords = new Codeword[boundingBox.getMaxY() - boundingBox.getMinY() + 1];
    }

    final BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    final Codeword getCodeword(int n2) {
        return this.codewords[this.imageRowToCodewordIndex(n2)];
    }

    final Codeword getCodewordNearby(int n2) {
        Codeword codeword = this.getCodeword(n2);
        if (codeword != null) {
            return codeword;
        }
        for (int i2 = 1; i2 < 5; ++i2) {
            int n3 = this.imageRowToCodewordIndex(n2) - i2;
            if (n3 >= 0 && (codeword = this.codewords[n3]) != null) {
                return codeword;
            }
            n3 = this.imageRowToCodewordIndex(n2) + i2;
            if (n3 >= this.codewords.length || (codeword = this.codewords[n3]) == null) continue;
            return codeword;
        }
        return null;
    }

    final Codeword[] getCodewords() {
        return this.codewords;
    }

    final int imageRowToCodewordIndex(int n2) {
        return n2 - this.boundingBox.getMinY();
    }

    final void setCodeword(int n2, Codeword codeword) {
        this.codewords[this.imageRowToCodewordIndex((int)n2)] = codeword;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        Formatter formatter = new Formatter();
        Codeword[] codewordArray = this.codewords;
        int n2 = codewordArray.length;
        int n3 = 0;
        int n4 = 0;
        while (true) {
            int n5;
            if (n3 >= n2) {
                String string2 = formatter.toString();
                formatter.close();
                return string2;
            }
            Codeword codeword = codewordArray[n3];
            if (codeword == null) {
                n5 = n4 + 1;
                formatter.format("%3d:    |   %n", n4);
                n4 = n5;
            } else {
                n5 = n4 + 1;
                formatter.format("%3d: %3d|%3d%n", n4, codeword.getRowNumber(), codeword.getValue());
                n4 = n5;
            }
            ++n3;
        }
    }
}

