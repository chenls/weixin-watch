/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.decoder;

final class BarcodeMetadata {
    private final int columnCount;
    private final int errorCorrectionLevel;
    private final int rowCount;
    private final int rowCountLowerPart;
    private final int rowCountUpperPart;

    BarcodeMetadata(int n2, int n3, int n4, int n5) {
        this.columnCount = n2;
        this.errorCorrectionLevel = n5;
        this.rowCountUpperPart = n3;
        this.rowCountLowerPart = n4;
        this.rowCount = n3 + n4;
    }

    int getColumnCount() {
        return this.columnCount;
    }

    int getErrorCorrectionLevel() {
        return this.errorCorrectionLevel;
    }

    int getRowCount() {
        return this.rowCount;
    }

    int getRowCountLowerPart() {
        return this.rowCountLowerPart;
    }

    int getRowCountUpperPart() {
        return this.rowCountUpperPart;
    }
}

