/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.OneDReader;
import java.util.Map;

public final class Code128Reader
extends OneDReader {
    private static final int CODE_CODE_A = 101;
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_A = 101;
    private static final int CODE_FNC_4_B = 100;
    static final int[][] CODE_PATTERNS;
    private static final int CODE_SHIFT = 98;
    private static final int CODE_START_A = 103;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final float MAX_AVG_VARIANCE = 0.25f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.7f;

    static {
        int[] nArray = new int[]{2, 1, 2, 2, 2, 2};
        int[] nArray2 = new int[]{1, 2, 1, 3, 2, 2};
        int[] nArray3 = new int[]{1, 2, 2, 2, 1, 3};
        int[] nArray4 = new int[]{1, 2, 2, 3, 1, 2};
        int[] nArray5 = new int[]{1, 3, 2, 2, 1, 2};
        int[] nArray6 = new int[]{2, 2, 1, 3, 1, 2};
        int[] nArray7 = new int[]{1, 1, 2, 2, 3, 2};
        int[] nArray8 = new int[]{1, 2, 2, 1, 3, 2};
        int[] nArray9 = new int[]{1, 2, 2, 2, 3, 1};
        int[] nArray10 = new int[]{1, 1, 3, 2, 2, 2};
        int[] nArray11 = new int[]{1, 2, 3, 1, 2, 2};
        int[] nArray12 = new int[]{1, 2, 3, 2, 2, 1};
        int[] nArray13 = new int[]{2, 2, 3, 2, 1, 1};
        int[] nArray14 = new int[]{2, 1, 3, 2, 1, 2};
        int[] nArray15 = new int[]{2, 2, 3, 1, 1, 2};
        int[] nArray16 = new int[]{3, 2, 1, 1, 2, 2};
        int[] nArray17 = new int[]{3, 2, 1, 2, 2, 1};
        int[] nArray18 = new int[]{3, 1, 2, 2, 1, 2};
        int[] nArray19 = new int[]{3, 2, 2, 1, 1, 2};
        int[] nArray20 = new int[]{3, 2, 2, 2, 1, 1};
        int[] nArray21 = new int[]{2, 3, 2, 1, 2, 1};
        int[] nArray22 = new int[]{1, 3, 1, 1, 2, 3};
        int[] nArray23 = new int[]{1, 3, 1, 3, 2, 1};
        int[] nArray24 = new int[]{1, 1, 2, 3, 1, 3};
        int[] nArray25 = new int[]{1, 3, 2, 3, 1, 1};
        int[] nArray26 = new int[]{2, 1, 1, 3, 1, 3};
        int[] nArray27 = new int[]{2, 3, 1, 1, 1, 3};
        int[] nArray28 = new int[]{2, 3, 1, 3, 1, 1};
        int[] nArray29 = new int[]{1, 1, 2, 1, 3, 3};
        int[] nArray30 = new int[]{1, 1, 2, 3, 3, 1};
        int[] nArray31 = new int[]{1, 3, 2, 1, 3, 1};
        int[] nArray32 = new int[]{1, 1, 3, 3, 2, 1};
        int[] nArray33 = new int[]{3, 1, 3, 1, 2, 1};
        int[] nArray34 = new int[]{2, 1, 1, 3, 3, 1};
        int[] nArray35 = new int[]{2, 1, 3, 3, 1, 1};
        int[] nArray36 = new int[]{2, 1, 3, 1, 3, 1};
        int[] nArray37 = new int[]{3, 1, 1, 1, 2, 3};
        int[] nArray38 = new int[]{3, 1, 1, 3, 2, 1};
        int[] nArray39 = new int[]{3, 3, 1, 1, 2, 1};
        int[] nArray40 = new int[]{3, 1, 2, 1, 1, 3};
        int[] nArray41 = new int[]{3, 1, 2, 3, 1, 1};
        int[] nArray42 = new int[]{2, 2, 1, 4, 1, 1};
        int[] nArray43 = new int[]{4, 3, 1, 1, 1, 1};
        int[] nArray44 = new int[]{1, 1, 1, 4, 2, 2};
        int[] nArray45 = new int[]{1, 2, 1, 1, 2, 4};
        int[] nArray46 = new int[]{1, 1, 2, 4, 1, 2};
        int[] nArray47 = new int[]{1, 2, 2, 1, 1, 4};
        int[] nArray48 = new int[]{1, 2, 2, 4, 1, 1};
        int[] nArray49 = new int[]{1, 4, 2, 1, 1, 2};
        int[] nArray50 = new int[]{1, 4, 2, 2, 1, 1};
        int[] nArray51 = new int[]{2, 2, 1, 1, 1, 4};
        int[] nArray52 = new int[]{1, 3, 4, 1, 1, 1};
        int[] nArray53 = new int[]{1, 1, 1, 2, 4, 2};
        int[] nArray54 = new int[]{1, 2, 1, 2, 4, 1};
        int[] nArray55 = new int[]{4, 2, 1, 2, 1, 1};
        int[] nArray56 = new int[]{2, 1, 4, 1, 2, 1};
        int[] nArray57 = new int[]{1, 1, 1, 1, 4, 3};
        int[] nArray58 = new int[]{1, 1, 1, 3, 4, 1};
        int[] nArray59 = new int[]{1, 3, 1, 1, 4, 1};
        int[] nArray60 = new int[]{1, 1, 4, 1, 1, 3};
        int[] nArray61 = new int[]{1, 1, 4, 3, 1, 1};
        int[] nArray62 = new int[]{4, 1, 1, 1, 1, 3};
        int[] nArray63 = new int[]{3, 1, 1, 1, 4, 1};
        int[] nArray64 = new int[]{2, 1, 1, 4, 1, 2};
        int[] nArray65 = new int[]{2, 1, 1, 2, 3, 2};
        int[] nArray66 = new int[]{2, 3, 3, 1, 1, 1, 2};
        CODE_PATTERNS = new int[][]{nArray, {2, 2, 2, 1, 2, 2}, {2, 2, 2, 2, 2, 1}, {1, 2, 1, 2, 2, 3}, nArray2, {1, 3, 1, 2, 2, 2}, nArray3, nArray4, nArray5, {2, 2, 1, 2, 1, 3}, nArray6, {2, 3, 1, 2, 1, 2}, nArray7, nArray8, nArray9, nArray10, nArray11, nArray12, nArray13, {2, 2, 1, 1, 3, 2}, {2, 2, 1, 2, 3, 1}, nArray14, nArray15, {3, 1, 2, 1, 3, 1}, {3, 1, 1, 2, 2, 2}, nArray16, nArray17, nArray18, nArray19, nArray20, {2, 1, 2, 1, 2, 3}, {2, 1, 2, 3, 2, 1}, nArray21, {1, 1, 1, 3, 2, 3}, nArray22, nArray23, nArray24, {1, 3, 2, 1, 1, 3}, nArray25, nArray26, nArray27, nArray28, nArray29, nArray30, nArray31, {1, 1, 3, 1, 2, 3}, nArray32, {1, 3, 3, 1, 2, 1}, nArray33, nArray34, {2, 3, 1, 1, 3, 1}, {2, 1, 3, 1, 1, 3}, nArray35, nArray36, nArray37, nArray38, nArray39, nArray40, nArray41, {3, 3, 2, 1, 1, 1}, {3, 1, 4, 1, 1, 1}, nArray42, nArray43, {1, 1, 1, 2, 2, 4}, nArray44, nArray45, {1, 2, 1, 4, 2, 1}, {1, 4, 1, 1, 2, 2}, {1, 4, 1, 2, 2, 1}, {1, 1, 2, 2, 1, 4}, nArray46, nArray47, nArray48, nArray49, nArray50, {2, 4, 1, 2, 1, 1}, nArray51, {4, 1, 3, 1, 1, 1}, {2, 4, 1, 1, 1, 2}, nArray52, nArray53, {1, 2, 1, 1, 4, 2}, nArray54, {1, 1, 4, 2, 1, 2}, {1, 2, 4, 1, 1, 2}, {1, 2, 4, 2, 1, 1}, {4, 1, 1, 2, 1, 2}, {4, 2, 1, 1, 1, 2}, nArray55, {2, 1, 2, 1, 4, 1}, nArray56, {4, 1, 2, 1, 2, 1}, nArray57, nArray58, nArray59, nArray60, nArray61, nArray62, {4, 1, 1, 3, 1, 1}, {1, 1, 3, 1, 4, 1}, {1, 1, 4, 1, 3, 1}, nArray63, {4, 1, 1, 1, 3, 1}, nArray64, {2, 1, 1, 2, 1, 4}, nArray65, nArray66};
    }

    private static int decodeCode(BitArray bitArray, int[] nArray, int n2) throws NotFoundException {
        Code128Reader.recordPattern(bitArray, n2, nArray);
        float f2 = 0.25f;
        int n3 = -1;
        for (n2 = 0; n2 < CODE_PATTERNS.length; ++n2) {
            float f3 = Code128Reader.patternMatchVariance(nArray, CODE_PATTERNS[n2], 0.7f);
            float f4 = f2;
            if (f3 < f2) {
                f4 = f3;
                n3 = n2;
            }
            f2 = f4;
        }
        if (n3 >= 0) {
            return n3;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int[] findStartPattern(BitArray bitArray) throws NotFoundException {
        int n2 = bitArray.getSize();
        int n3 = bitArray.getNextSet(0);
        int n4 = 0;
        int[] nArray = new int[6];
        int n5 = n3;
        boolean bl2 = false;
        int n6 = nArray.length;
        while (true) {
            int n7;
            if (n3 >= n2) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (bitArray.get(n3) ^ bl2) {
                nArray[n4] = nArray[n4] + 1;
                n7 = n4;
            } else {
                if (n4 == n6 - 1) {
                    float f2 = 0.25f;
                    int n8 = -1;
                    for (n7 = 103; n7 <= 105; ++n7) {
                        float f3 = Code128Reader.patternMatchVariance(nArray, CODE_PATTERNS[n7], 0.7f);
                        float f4 = f2;
                        if (f3 < f2) {
                            f4 = f3;
                            n8 = n7;
                        }
                        f2 = f4;
                    }
                    if (n8 >= 0 && bitArray.isRange(Math.max(0, n5 - (n3 - n5) / 2), n5, false)) {
                        return new int[]{n5, n3, n8};
                    }
                    n5 += nArray[0] + nArray[1];
                    System.arraycopy(nArray, 2, nArray, 0, n6 - 2);
                    nArray[n6 - 2] = 0;
                    nArray[n6 - 1] = 0;
                    n7 = n4 - 1;
                } else {
                    n7 = n4 + 1;
                }
                nArray[n7] = 1;
                bl2 = !bl2;
            }
            ++n3;
            n4 = n7;
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public Result decodeRow(int var1_1, BitArray var2_2, Map<DecodeHintType, ?> var3_3) throws NotFoundException, FormatException, ChecksumException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[CASE]], but top level block is 8[SWITCH]
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
}

