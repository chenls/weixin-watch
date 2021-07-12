/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.decoder.BarcodeMetadata;
import com.google.zxing.pdf417.decoder.BoundingBox;
import com.google.zxing.pdf417.decoder.Codeword;
import com.google.zxing.pdf417.decoder.DetectionResultColumn;
import com.google.zxing.pdf417.decoder.DetectionResultRowIndicatorColumn;
import java.util.Formatter;

final class DetectionResult {
    private static final int ADJUST_ROW_NUMBER_SKIP = 2;
    private final int barcodeColumnCount;
    private final BarcodeMetadata barcodeMetadata;
    private BoundingBox boundingBox;
    private final DetectionResultColumn[] detectionResultColumns;

    DetectionResult(BarcodeMetadata barcodeMetadata, BoundingBox boundingBox) {
        this.barcodeMetadata = barcodeMetadata;
        this.barcodeColumnCount = barcodeMetadata.getColumnCount();
        this.boundingBox = boundingBox;
        this.detectionResultColumns = new DetectionResultColumn[this.barcodeColumnCount + 2];
    }

    private void adjustIndicatorColumnRowNumbers(DetectionResultColumn detectionResultColumn) {
        if (detectionResultColumn != null) {
            ((DetectionResultRowIndicatorColumn)detectionResultColumn).adjustCompleteIndicatorColumnRowNumbers(this.barcodeMetadata);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean adjustRowNumber(Codeword codeword, Codeword codeword2) {
        if (codeword2 == null || !codeword2.hasValidRowNumber() || codeword2.getBucket() != codeword.getBucket()) {
            return false;
        }
        codeword.setRowNumber(codeword2.getRowNumber());
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int adjustRowNumberIfValid(int n2, int n3, Codeword codeword) {
        if (codeword == null) {
            return n3;
        }
        int n4 = n3;
        if (codeword.hasValidRowNumber()) return n4;
        if (!codeword.isValidRowNumber(n2)) return n3 + 1;
        codeword.setRowNumber(n2);
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int adjustRowNumbers() {
        int n2 = this.adjustRowNumbersByRow();
        if (n2 == 0) {
            return 0;
        }
        int n3 = 1;
        while (true) {
            int n4 = n2;
            if (n3 >= this.barcodeColumnCount + 1) return n4;
            Codeword[] codewordArray = this.detectionResultColumns[n3].getCodewords();
            for (n4 = 0; n4 < codewordArray.length; ++n4) {
                if (codewordArray[n4] == null || codewordArray[n4].hasValidRowNumber()) continue;
                this.adjustRowNumbers(n3, n4, codewordArray);
            }
            ++n3;
        }
    }

    private void adjustRowNumbers(int n2, int n3, Codeword[] codewordArray) {
        Codeword[] codewordArray2;
        int n4 = 0;
        Codeword codeword = codewordArray[n3];
        Codeword[] codewordArray3 = codewordArray2 = this.detectionResultColumns[n2 - 1].getCodewords();
        if (this.detectionResultColumns[n2 + 1] != null) {
            codewordArray3 = this.detectionResultColumns[n2 + 1].getCodewords();
        }
        Codeword[] codewordArray4 = new Codeword[14];
        codewordArray4[2] = codewordArray2[n3];
        codewordArray4[3] = codewordArray3[n3];
        if (n3 > 0) {
            codewordArray4[0] = codewordArray[n3 - 1];
            codewordArray4[4] = codewordArray2[n3 - 1];
            codewordArray4[5] = codewordArray3[n3 - 1];
        }
        if (n3 > 1) {
            codewordArray4[8] = codewordArray[n3 - 2];
            codewordArray4[10] = codewordArray2[n3 - 2];
            codewordArray4[11] = codewordArray3[n3 - 2];
        }
        if (n3 < codewordArray.length - 1) {
            codewordArray4[1] = codewordArray[n3 + 1];
            codewordArray4[6] = codewordArray2[n3 + 1];
            codewordArray4[7] = codewordArray3[n3 + 1];
        }
        if (n3 < codewordArray.length - 2) {
            codewordArray4[9] = codewordArray[n3 + 2];
            codewordArray4[12] = codewordArray2[n3 + 2];
            codewordArray4[13] = codewordArray3[n3 + 2];
        }
        n3 = codewordArray4.length;
        n2 = n4;
        while (n2 < n3 && !DetectionResult.adjustRowNumber(codeword, codewordArray4[n2])) {
            ++n2;
        }
        return;
    }

    private int adjustRowNumbersByRow() {
        this.adjustRowNumbersFromBothRI();
        int n2 = this.adjustRowNumbersFromLRI();
        return this.adjustRowNumbersFromRRI() + n2;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void adjustRowNumbersFromBothRI() {
        if (this.detectionResultColumns[0] == null) return;
        if (this.detectionResultColumns[this.barcodeColumnCount + 1] == null) {
            return;
        }
        Codeword[] codewordArray = this.detectionResultColumns[0].getCodewords();
        Codeword[] codewordArray2 = this.detectionResultColumns[this.barcodeColumnCount + 1].getCodewords();
        int n2 = 0;
        while (n2 < codewordArray.length) {
            if (codewordArray[n2] != null && codewordArray2[n2] != null && codewordArray[n2].getRowNumber() == codewordArray2[n2].getRowNumber()) {
                for (int i2 = 1; i2 <= this.barcodeColumnCount; ++i2) {
                    Codeword codeword = this.detectionResultColumns[i2].getCodewords()[n2];
                    if (codeword == null) continue;
                    codeword.setRowNumber(codewordArray[n2].getRowNumber());
                    if (codeword.hasValidRowNumber()) continue;
                    this.detectionResultColumns[i2].getCodewords()[n2] = null;
                }
            }
            ++n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int adjustRowNumbersFromLRI() {
        if (this.detectionResultColumns[0] == null) {
            return 0;
        }
        int n2 = 0;
        Codeword[] codewordArray = this.detectionResultColumns[0].getCodewords();
        int n3 = 0;
        while (true) {
            int n4;
            int n5 = n2;
            if (n3 >= codewordArray.length) return n5;
            if (codewordArray[n3] == null) {
                n4 = n2;
            } else {
                int n6 = codewordArray[n3].getRowNumber();
                int n7 = 0;
                n5 = 1;
                while (true) {
                    n4 = n2;
                    if (n5 >= this.barcodeColumnCount + 1) break;
                    n4 = n2;
                    if (n7 >= 2) break;
                    Codeword codeword = this.detectionResultColumns[n5].getCodewords()[n3];
                    n4 = n7;
                    int n8 = n2;
                    if (codeword != null) {
                        n4 = n7 = DetectionResult.adjustRowNumberIfValid(n6, n7, codeword);
                        n8 = n2;
                        if (!codeword.hasValidRowNumber()) {
                            n8 = n2 + 1;
                            n4 = n7;
                        }
                    }
                    ++n5;
                    n7 = n4;
                    n2 = n8;
                }
            }
            ++n3;
            n2 = n4;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int adjustRowNumbersFromRRI() {
        if (this.detectionResultColumns[this.barcodeColumnCount + 1] == null) {
            return 0;
        }
        int n2 = 0;
        Codeword[] codewordArray = this.detectionResultColumns[this.barcodeColumnCount + 1].getCodewords();
        int n3 = 0;
        while (true) {
            int n4;
            int n5 = n2;
            if (n3 >= codewordArray.length) return n5;
            if (codewordArray[n3] == null) {
                n4 = n2;
            } else {
                int n6 = codewordArray[n3].getRowNumber();
                int n7 = 0;
                n5 = this.barcodeColumnCount + 1;
                while (true) {
                    n4 = n2;
                    if (n5 <= 0) break;
                    n4 = n2;
                    if (n7 >= 2) break;
                    Codeword codeword = this.detectionResultColumns[n5].getCodewords()[n3];
                    n4 = n7;
                    int n8 = n2;
                    if (codeword != null) {
                        n4 = n7 = DetectionResult.adjustRowNumberIfValid(n6, n7, codeword);
                        n8 = n2;
                        if (!codeword.hasValidRowNumber()) {
                            n8 = n2 + 1;
                            n4 = n7;
                        }
                    }
                    --n5;
                    n7 = n4;
                    n2 = n8;
                }
            }
            ++n3;
            n2 = n4;
        }
    }

    int getBarcodeColumnCount() {
        return this.barcodeColumnCount;
    }

    int getBarcodeECLevel() {
        return this.barcodeMetadata.getErrorCorrectionLevel();
    }

    int getBarcodeRowCount() {
        return this.barcodeMetadata.getRowCount();
    }

    BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    DetectionResultColumn getDetectionResultColumn(int n2) {
        return this.detectionResultColumns[n2];
    }

    DetectionResultColumn[] getDetectionResultColumns() {
        int n2;
        int n3;
        this.adjustIndicatorColumnRowNumbers(this.detectionResultColumns[0]);
        this.adjustIndicatorColumnRowNumbers(this.detectionResultColumns[this.barcodeColumnCount + 1]);
        int n4 = 928;
        do {
            n2 = n4;
            n3 = this.adjustRowNumbers();
            if (n3 <= 0) break;
            n4 = n3;
        } while (n3 < n2);
        return this.detectionResultColumns;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    void setDetectionResultColumn(int n2, DetectionResultColumn detectionResultColumn) {
        this.detectionResultColumns[n2] = detectionResultColumn;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        Object object;
        Object object2 = object = this.detectionResultColumns[0];
        if (object == null) {
            object2 = this.detectionResultColumns[this.barcodeColumnCount + 1];
        }
        object = new Formatter();
        int n2 = 0;
        while (true) {
            if (n2 >= ((DetectionResultColumn)object2).getCodewords().length) {
                object2 = ((Formatter)object).toString();
                ((Formatter)object).close();
                return object2;
            }
            ((Formatter)object).format("CW %3d:", n2);
            for (int i2 = 0; i2 < this.barcodeColumnCount + 2; ++i2) {
                if (this.detectionResultColumns[i2] == null) {
                    ((Formatter)object).format("    |   ", new Object[0]);
                    continue;
                }
                Codeword codeword = this.detectionResultColumns[i2].getCodewords()[n2];
                if (codeword == null) {
                    ((Formatter)object).format("    |   ", new Object[0]);
                    continue;
                }
                ((Formatter)object).format(" %3d|%3d", codeword.getRowNumber(), codeword.getValue());
            }
            ((Formatter)object).format("%n", new Object[0]);
            ++n2;
        }
    }
}

