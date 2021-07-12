/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.decoder.Version;

final class BitMatrixParser {
    private final BitMatrix mappingBitMatrix;
    private final BitMatrix readMappingMatrix;
    private final Version version;

    BitMatrixParser(BitMatrix bitMatrix) throws FormatException {
        int n2 = bitMatrix.getHeight();
        if (n2 < 8 || n2 > 144 || (n2 & 1) != 0) {
            throw FormatException.getFormatInstance();
        }
        this.version = BitMatrixParser.readVersion(bitMatrix);
        this.mappingBitMatrix = this.extractDataRegion(bitMatrix);
        this.readMappingMatrix = new BitMatrix(this.mappingBitMatrix.getWidth(), this.mappingBitMatrix.getHeight());
    }

    private static Version readVersion(BitMatrix bitMatrix) throws FormatException {
        return Version.getVersionForDimensions(bitMatrix.getHeight(), bitMatrix.getWidth());
    }

    BitMatrix extractDataRegion(BitMatrix bitMatrix) {
        int n2 = this.version.getSymbolSizeRows();
        int n3 = this.version.getSymbolSizeColumns();
        if (bitMatrix.getHeight() != n2) {
            throw new IllegalArgumentException("Dimension of bitMarix must match the version size");
        }
        int n4 = this.version.getDataRegionSizeRows();
        int n5 = this.version.getDataRegionSizeColumns();
        int n6 = n2 / n4;
        int n7 = n3 / n5;
        BitMatrix bitMatrix2 = new BitMatrix(n7 * n5, n6 * n4);
        for (n3 = 0; n3 < n6; ++n3) {
            for (n2 = 0; n2 < n7; ++n2) {
                for (int i2 = 0; i2 < n4; ++i2) {
                    for (int i3 = 0; i3 < n5; ++i3) {
                        if (!bitMatrix.get((n5 + 2) * n2 + 1 + i3, (n4 + 2) * n3 + 1 + i2)) continue;
                        bitMatrix2.set(n2 * n5 + i3, n3 * n4 + i2);
                    }
                }
            }
        }
        return bitMatrix2;
    }

    Version getVersion() {
        return this.version;
    }

    /*
     * Exception decompiling
     */
    byte[] readCodewords() throws FormatException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[DOLOOP]], but top level block is 3[UNCONDITIONALDOLOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:845)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1042)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:929)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:73)
         *     at org.benf.cfr.reader.Main.main(Main.java:49)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    int readCorner1(int n2, int n3) {
        int n4;
        int n5 = 0;
        if (this.readModule(n2 - 1, 0, n2, n3)) {
            n5 = 0 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(n2 - 1, 1, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(n2 - 1, 2, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(0, n3 - 2, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(0, n3 - 1, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(1, n3 - 1, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(2, n3 - 1, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(3, n3 - 1, n2, n3)) {
            n5 = n4 | 1;
        }
        return n5;
    }

    int readCorner2(int n2, int n3) {
        int n4;
        int n5 = 0;
        if (this.readModule(n2 - 3, 0, n2, n3)) {
            n5 = 0 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(n2 - 2, 0, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(n2 - 1, 0, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(0, n3 - 4, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(0, n3 - 3, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(0, n3 - 2, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(0, n3 - 1, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(1, n3 - 1, n2, n3)) {
            n5 = n4 | 1;
        }
        return n5;
    }

    int readCorner3(int n2, int n3) {
        int n4;
        int n5 = 0;
        if (this.readModule(n2 - 1, 0, n2, n3)) {
            n5 = 0 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(n2 - 1, n3 - 1, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(0, n3 - 3, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(0, n3 - 2, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(0, n3 - 1, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(1, n3 - 3, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(1, n3 - 2, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(1, n3 - 1, n2, n3)) {
            n5 = n4 | 1;
        }
        return n5;
    }

    int readCorner4(int n2, int n3) {
        int n4;
        int n5 = 0;
        if (this.readModule(n2 - 3, 0, n2, n3)) {
            n5 = 0 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(n2 - 2, 0, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(n2 - 1, 0, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(0, n3 - 2, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(0, n3 - 1, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(1, n3 - 1, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(2, n3 - 1, n2, n3)) {
            n5 = n4 | 1;
        }
        n5 = n4 = n5 << 1;
        if (this.readModule(3, n3 - 1, n2, n3)) {
            n5 = n4 | 1;
        }
        return n5;
    }

    boolean readModule(int n2, int n3, int n4, int n5) {
        int n6 = n2;
        int n7 = n3;
        if (n2 < 0) {
            n6 = n2 + n4;
            n7 = n3 + (4 - (n4 + 4 & 7));
        }
        n3 = n6;
        n2 = n7;
        if (n7 < 0) {
            n2 = n7 + n5;
            n3 = n6 + (4 - (n5 + 4 & 7));
        }
        this.readMappingMatrix.set(n2, n3);
        return this.mappingBitMatrix.get(n2, n3);
    }

    int readUtah(int n2, int n3, int n4, int n5) {
        int n6;
        int n7 = 0;
        if (this.readModule(n2 - 2, n3 - 2, n4, n5)) {
            n7 = 0 | 1;
        }
        n7 = n6 = n7 << 1;
        if (this.readModule(n2 - 2, n3 - 1, n4, n5)) {
            n7 = n6 | 1;
        }
        n7 = n6 = n7 << 1;
        if (this.readModule(n2 - 1, n3 - 2, n4, n5)) {
            n7 = n6 | 1;
        }
        n7 = n6 = n7 << 1;
        if (this.readModule(n2 - 1, n3 - 1, n4, n5)) {
            n7 = n6 | 1;
        }
        n7 = n6 = n7 << 1;
        if (this.readModule(n2 - 1, n3, n4, n5)) {
            n7 = n6 | 1;
        }
        n7 = n6 = n7 << 1;
        if (this.readModule(n2, n3 - 2, n4, n5)) {
            n7 = n6 | 1;
        }
        n7 = n6 = n7 << 1;
        if (this.readModule(n2, n3 - 1, n4, n5)) {
            n7 = n6 | 1;
        }
        n7 = n6 = n7 << 1;
        if (this.readModule(n2, n3, n4, n5)) {
            n7 = n6 | 1;
        }
        return n7;
    }
}

