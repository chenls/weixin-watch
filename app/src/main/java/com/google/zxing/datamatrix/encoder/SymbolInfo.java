/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import com.google.zxing.datamatrix.encoder.DataMatrixSymbolInfo144;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;

public class SymbolInfo {
    static final SymbolInfo[] PROD_SYMBOLS = new SymbolInfo[]{new SymbolInfo(false, 3, 5, 8, 8, 1), new SymbolInfo(false, 5, 7, 10, 10, 1), new SymbolInfo(true, 5, 7, 16, 6, 1), new SymbolInfo(false, 8, 10, 12, 12, 1), new SymbolInfo(true, 10, 11, 14, 6, 2), new SymbolInfo(false, 12, 12, 14, 14, 1), new SymbolInfo(true, 16, 14, 24, 10, 1), new SymbolInfo(false, 18, 14, 16, 16, 1), new SymbolInfo(false, 22, 18, 18, 18, 1), new SymbolInfo(true, 22, 18, 16, 10, 2), new SymbolInfo(false, 30, 20, 20, 20, 1), new SymbolInfo(true, 32, 24, 16, 14, 2), new SymbolInfo(false, 36, 24, 22, 22, 1), new SymbolInfo(false, 44, 28, 24, 24, 1), new SymbolInfo(true, 49, 28, 22, 14, 2), new SymbolInfo(false, 62, 36, 14, 14, 4), new SymbolInfo(false, 86, 42, 16, 16, 4), new SymbolInfo(false, 114, 48, 18, 18, 4), new SymbolInfo(false, 144, 56, 20, 20, 4), new SymbolInfo(false, 174, 68, 22, 22, 4), new SymbolInfo(false, 204, 84, 24, 24, 4, 102, 42), new SymbolInfo(false, 280, 112, 14, 14, 16, 140, 56), new SymbolInfo(false, 368, 144, 16, 16, 16, 92, 36), new SymbolInfo(false, 456, 192, 18, 18, 16, 114, 48), new SymbolInfo(false, 576, 224, 20, 20, 16, 144, 56), new SymbolInfo(false, 696, 272, 22, 22, 16, 174, 68), new SymbolInfo(false, 816, 336, 24, 24, 16, 136, 56), new SymbolInfo(false, 1050, 408, 18, 18, 36, 175, 68), new SymbolInfo(false, 1304, 496, 20, 20, 36, 163, 62), new DataMatrixSymbolInfo144()};
    private static SymbolInfo[] symbols = PROD_SYMBOLS;
    private final int dataCapacity;
    private final int dataRegions;
    private final int errorCodewords;
    public final int matrixHeight;
    public final int matrixWidth;
    private final boolean rectangular;
    private final int rsBlockData;
    private final int rsBlockError;

    public SymbolInfo(boolean bl2, int n2, int n3, int n4, int n5, int n6) {
        this(bl2, n2, n3, n4, n5, n6, n2, n3);
    }

    SymbolInfo(boolean bl2, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this.rectangular = bl2;
        this.dataCapacity = n2;
        this.errorCodewords = n3;
        this.matrixWidth = n4;
        this.matrixHeight = n5;
        this.dataRegions = n6;
        this.rsBlockData = n7;
        this.rsBlockError = n8;
    }

    public static SymbolInfo lookup(int n2) {
        return SymbolInfo.lookup(n2, SymbolShapeHint.FORCE_NONE, true);
    }

    public static SymbolInfo lookup(int n2, SymbolShapeHint symbolShapeHint) {
        return SymbolInfo.lookup(n2, symbolShapeHint, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static SymbolInfo lookup(int n2, SymbolShapeHint symbolShapeHint, Dimension dimension, Dimension dimension2, boolean bl2) {
        for (SymbolInfo symbolInfo : symbols) {
            if (symbolShapeHint == SymbolShapeHint.FORCE_SQUARE && symbolInfo.rectangular || symbolShapeHint == SymbolShapeHint.FORCE_RECTANGLE && !symbolInfo.rectangular || dimension != null && (symbolInfo.getSymbolWidth() < dimension.getWidth() || symbolInfo.getSymbolHeight() < dimension.getHeight()) || dimension2 != null && (symbolInfo.getSymbolWidth() > dimension2.getWidth() || symbolInfo.getSymbolHeight() > dimension2.getHeight()) || n2 > symbolInfo.dataCapacity) continue;
            return symbolInfo;
        }
        if (bl2) {
            throw new IllegalArgumentException("Can't find a symbol arrangement that matches the message. Data codewords: " + n2);
        }
        return null;
    }

    private static SymbolInfo lookup(int n2, SymbolShapeHint symbolShapeHint, boolean bl2) {
        return SymbolInfo.lookup(n2, symbolShapeHint, null, null, bl2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static SymbolInfo lookup(int n2, boolean bl2, boolean bl3) {
        SymbolShapeHint symbolShapeHint;
        if (bl2) {
            symbolShapeHint = SymbolShapeHint.FORCE_NONE;
            return SymbolInfo.lookup(n2, symbolShapeHint, bl3);
        }
        symbolShapeHint = SymbolShapeHint.FORCE_SQUARE;
        return SymbolInfo.lookup(n2, symbolShapeHint, bl3);
    }

    public static void overrideSymbolSet(SymbolInfo[] symbolInfoArray) {
        symbols = symbolInfoArray;
    }

    public int getCodewordCount() {
        return this.dataCapacity + this.errorCodewords;
    }

    public final int getDataCapacity() {
        return this.dataCapacity;
    }

    public int getDataLengthForInterleavedBlock(int n2) {
        return this.rsBlockData;
    }

    public final int getErrorCodewords() {
        return this.errorCodewords;
    }

    public final int getErrorLengthForInterleavedBlock(int n2) {
        return this.rsBlockError;
    }

    final int getHorizontalDataRegions() {
        int n2 = 2;
        switch (this.dataRegions) {
            default: {
                throw new IllegalStateException("Cannot handle this number of data regions");
            }
            case 1: {
                n2 = 1;
            }
            case 2: 
            case 4: {
                return n2;
            }
            case 16: {
                return 4;
            }
            case 36: 
        }
        return 6;
    }

    public int getInterleavedBlockCount() {
        return this.dataCapacity / this.rsBlockData;
    }

    public final int getSymbolDataHeight() {
        return this.getVerticalDataRegions() * this.matrixHeight;
    }

    public final int getSymbolDataWidth() {
        return this.getHorizontalDataRegions() * this.matrixWidth;
    }

    public final int getSymbolHeight() {
        return this.getSymbolDataHeight() + this.getVerticalDataRegions() * 2;
    }

    public final int getSymbolWidth() {
        return this.getSymbolDataWidth() + this.getHorizontalDataRegions() * 2;
    }

    final int getVerticalDataRegions() {
        int n2 = 1;
        switch (this.dataRegions) {
            default: {
                throw new IllegalStateException("Cannot handle this number of data regions");
            }
            case 4: {
                n2 = 2;
            }
            case 1: 
            case 2: {
                return n2;
            }
            case 16: {
                return 4;
            }
            case 36: 
        }
        return 6;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = this.rectangular ? "Rectangular Symbol:" : "Square Symbol:";
        stringBuilder.append(string2);
        stringBuilder.append(" data region ").append(this.matrixWidth).append('x').append(this.matrixHeight);
        stringBuilder.append(", symbol size ").append(this.getSymbolWidth()).append('x').append(this.getSymbolHeight());
        stringBuilder.append(", symbol data size ").append(this.getSymbolDataWidth()).append('x').append(this.getSymbolDataHeight());
        stringBuilder.append(", codewords ").append(this.dataCapacity).append('+').append(this.errorCodewords);
        return stringBuilder.toString();
    }
}

