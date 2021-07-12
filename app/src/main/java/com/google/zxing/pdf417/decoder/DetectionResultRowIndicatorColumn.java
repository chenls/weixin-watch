/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.ResultPoint;
import com.google.zxing.pdf417.decoder.BarcodeMetadata;
import com.google.zxing.pdf417.decoder.BarcodeValue;
import com.google.zxing.pdf417.decoder.BoundingBox;
import com.google.zxing.pdf417.decoder.Codeword;
import com.google.zxing.pdf417.decoder.DetectionResultColumn;

final class DetectionResultRowIndicatorColumn
extends DetectionResultColumn {
    private final boolean isLeft;

    DetectionResultRowIndicatorColumn(BoundingBox boundingBox, boolean bl2) {
        super(boundingBox);
        this.isLeft = bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void removeIncorrectCodewords(Codeword[] codewordArray, BarcodeMetadata barcodeMetadata) {
        int n2 = 0;
        while (n2 < codewordArray.length) {
            Codeword codeword = codewordArray[n2];
            if (codewordArray[n2] != null) {
                int n3 = codeword.getValue() % 30;
                int n4 = codeword.getRowNumber();
                if (n4 > barcodeMetadata.getRowCount()) {
                    codewordArray[n2] = null;
                } else {
                    int n5 = n4;
                    if (!this.isLeft) {
                        n5 = n4 + 2;
                    }
                    switch (n5 % 3) {
                        default: {
                            break;
                        }
                        case 0: {
                            if (n3 * 3 + 1 == barcodeMetadata.getRowCountUpperPart()) break;
                            codewordArray[n2] = null;
                            break;
                        }
                        case 1: {
                            if (n3 / 3 == barcodeMetadata.getErrorCorrectionLevel() && n3 % 3 == barcodeMetadata.getRowCountLowerPart()) break;
                            codewordArray[n2] = null;
                            break;
                        }
                        case 2: {
                            if (n3 + 1 == barcodeMetadata.getColumnCount()) break;
                            codewordArray[n2] = null;
                        }
                    }
                }
            }
            ++n2;
        }
        return;
    }

    /*
     * Unable to fully structure code
     */
    int adjustCompleteIndicatorColumnRowNumbers(BarcodeMetadata var1_1) {
        block12: {
            block20: {
                block14: {
                    block13: {
                        block19: {
                            block18: {
                                block17: {
                                    block11: {
                                        block16: {
                                            block15: {
                                                var13_2 = this.getCodewords();
                                                this.setRowNumbers();
                                                this.removeIncorrectCodewords(var13_2, var1_1);
                                                var12_3 = this.getBoundingBox();
                                                if (!this.isLeft) break block15;
                                                var11_4 = var12_3.getTopLeft();
lbl7:
                                                // 2 sources

                                                while (this.isLeft) {
                                                    var12_3 = var12_3.getBottomLeft();
lbl9:
                                                    // 2 sources

                                                    while (true) {
                                                        var10_6 = this.imageRowToCodewordIndex((int)var12_3.getY());
                                                        var2_7 = (float)(var10_6 - var5_5) / (float)var1_1.getRowCount();
                                                        var4_8 = -1;
                                                        var6_9 = 1;
                                                        var7_10 = 0;
                                                        block2: for (var5_5 = this.imageRowToCodewordIndex((int)var11_4.getY()); var5_5 < var10_6; ++var5_5) {
                                                            if (var13_2[var5_5] == null) {
                                                                var3_11 = var7_10;
lbl18:
                                                                // 6 sources

                                                                while (true) {
                                                                    var7_10 = var3_11;
                                                                    continue block2;
                                                                    break;
                                                                }
                                                            }
                                                            break block11;
                                                        }
                                                        break block12;
                                                        break;
                                                    }
                                                }
                                                break block16;
                                            }
                                            var11_4 = var12_3.getTopRight();
                                            ** GOTO lbl7
                                        }
                                        var12_3 = var12_3.getBottomRight();
                                        ** while (true)
                                    }
                                    var11_4 = var13_2[var5_5];
                                    var8_12 = var11_4.getRowNumber() - var4_8;
                                    if (var8_12 != 0) break block17;
                                    var3_11 = var7_10 + 1;
                                    ** GOTO lbl18
                                }
                                if (var8_12 != 1) break block18;
                                var6_9 = Math.max(var6_9, var7_10);
                                var3_11 = 1;
                                var4_8 = var11_4.getRowNumber();
                                ** GOTO lbl18
                            }
                            if (var8_12 >= 0 && var11_4.getRowNumber() < var1_1.getRowCount() && var8_12 <= var5_5) break block19;
                            var13_2[var5_5] = null;
                            var3_11 = var7_10;
                            ** GOTO lbl18
                        }
                        if (var6_9 > 2) {
                            var8_12 = (var6_9 - 2) * var8_12;
                        }
                        if (var8_12 >= var5_5) {
                            var3_11 = 1;
lbl52:
                            // 2 sources

                            while (true) {
                                block5: for (var9_13 = 1; var9_13 <= var8_12 && var3_11 == 0; ++var9_13) {
                                    if (var13_2[var5_5 - var9_13] != null) {
                                        var3_11 = 1;
lbl56:
                                        // 2 sources

                                        continue block5;
                                    }
                                    break block13;
                                }
                                break block14;
                                break;
                            }
                        }
                        var3_11 = 0;
                        ** while (true)
                    }
                    var3_11 = 0;
                    ** while (true)
                }
                if (var3_11 == 0) break block20;
                var13_2[var5_5] = null;
                var3_11 = var7_10;
                ** GOTO lbl18
            }
            var4_8 = var11_4.getRowNumber();
            var3_11 = 1;
            ** while (true)
        }
        return (int)((double)var2_7 + 0.5);
    }

    /*
     * Enabled aggressive block sorting
     */
    int adjustIncompleteIndicatorColumnRowNumbers(BarcodeMetadata barcodeMetadata) {
        Object object = this.getBoundingBox();
        Object object2 = this.isLeft ? ((BoundingBox)object).getTopLeft() : ((BoundingBox)object).getTopRight();
        object = this.isLeft ? ((BoundingBox)object).getBottomLeft() : ((BoundingBox)object).getBottomRight();
        int n2 = this.imageRowToCodewordIndex((int)object2.getY());
        int n3 = this.imageRowToCodewordIndex((int)((ResultPoint)object).getY());
        float f2 = (float)(n3 - n2) / (float)barcodeMetadata.getRowCount();
        object2 = this.getCodewords();
        int n4 = -1;
        int n5 = 1;
        int n6 = 0;
        while (n2 < n3) {
            if (object2[n2] != null) {
                object = object2[n2];
                ((Codeword)object).setRowNumberAsRowIndicatorColumn();
                int n7 = ((Codeword)object).getRowNumber() - n4;
                if (n7 == 0) {
                    ++n6;
                } else if (n7 == 1) {
                    n5 = Math.max(n5, n6);
                    n6 = 1;
                    n4 = ((Codeword)object).getRowNumber();
                } else if (((Codeword)object).getRowNumber() >= barcodeMetadata.getRowCount()) {
                    object2[n2] = null;
                } else {
                    n4 = ((Codeword)object).getRowNumber();
                    n6 = 1;
                }
            }
            ++n2;
        }
        return (int)((double)f2 + 0.5);
    }

    /*
     * Enabled aggressive block sorting
     */
    BarcodeMetadata getBarcodeMetadata() {
        Codeword[] codewordArray = this.getCodewords();
        Object object = new BarcodeValue();
        BarcodeValue barcodeValue = new BarcodeValue();
        BarcodeValue barcodeValue2 = new BarcodeValue();
        BarcodeValue barcodeValue3 = new BarcodeValue();
        int n2 = codewordArray.length;
        block5: for (int i2 = 0; i2 < n2; ++i2) {
            int n3;
            Codeword codeword = codewordArray[i2];
            if (codeword == null) continue;
            codeword.setRowNumberAsRowIndicatorColumn();
            int n4 = codeword.getValue() % 30;
            int n5 = n3 = codeword.getRowNumber();
            if (!this.isLeft) {
                n5 = n3 + 2;
            }
            switch (n5 % 3) {
                default: {
                    continue block5;
                }
                case 0: {
                    barcodeValue.setValue(n4 * 3 + 1);
                    continue block5;
                }
                case 1: {
                    barcodeValue3.setValue(n4 / 3);
                    barcodeValue2.setValue(n4 % 3);
                    continue block5;
                }
                case 2: {
                    ((BarcodeValue)object).setValue(n4 + 1);
                }
            }
        }
        if (((BarcodeValue)object).getValue().length != 0 && barcodeValue.getValue().length != 0 && barcodeValue2.getValue().length != 0 && barcodeValue3.getValue().length != 0 && ((BarcodeValue)object).getValue()[0] >= 1 && barcodeValue.getValue()[0] + barcodeValue2.getValue()[0] >= 3 && barcodeValue.getValue()[0] + barcodeValue2.getValue()[0] <= 90) {
            object = new BarcodeMetadata(((BarcodeValue)object).getValue()[0], barcodeValue.getValue()[0], barcodeValue2.getValue()[0], barcodeValue3.getValue()[0]);
            this.removeIncorrectCodewords(codewordArray, (BarcodeMetadata)object);
            return object;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    int[] getRowHeights() throws FormatException {
        Object object = this.getBarcodeMetadata();
        if (object == null) {
            return null;
        }
        this.adjustIncompleteIndicatorColumnRowNumbers((BarcodeMetadata)object);
        int[] nArray = new int[((BarcodeMetadata)object).getRowCount()];
        Codeword[] codewordArray = this.getCodewords();
        int n2 = codewordArray.length;
        int n3 = 0;
        while (true) {
            object = nArray;
            if (n3 >= n2) return object;
            object = codewordArray[n3];
            if (object != null) {
                int n4 = ((Codeword)object).getRowNumber();
                if (n4 >= nArray.length) {
                    throw FormatException.getFormatInstance();
                }
                nArray[n4] = nArray[n4] + 1;
            }
            ++n3;
        }
    }

    boolean isLeft() {
        return this.isLeft;
    }

    void setRowNumbers() {
        for (Codeword codeword : this.getCodewords()) {
            if (codeword == null) continue;
            codeword.setRowNumberAsRowIndicatorColumn();
        }
    }

    @Override
    public String toString() {
        return "IsLeft: " + this.isLeft + '\n' + super.toString();
    }
}

