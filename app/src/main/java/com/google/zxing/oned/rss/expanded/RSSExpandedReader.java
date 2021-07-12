/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.RSSUtils;
import com.google.zxing.oned.rss.expanded.BitArrayBuilder;
import com.google.zxing.oned.rss.expanded.ExpandedPair;
import com.google.zxing.oned.rss.expanded.ExpandedRow;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class RSSExpandedReader
extends AbstractRSSReader {
    private static final int[] EVEN_TOTAL_SUBSET;
    private static final int[][] FINDER_PATTERNS;
    private static final int[][] FINDER_PATTERN_SEQUENCES;
    private static final int FINDER_PAT_A = 0;
    private static final int FINDER_PAT_B = 1;
    private static final int FINDER_PAT_C = 2;
    private static final int FINDER_PAT_D = 3;
    private static final int FINDER_PAT_E = 4;
    private static final int FINDER_PAT_F = 5;
    private static final int[] GSUM;
    private static final int MAX_PAIRS = 11;
    private static final int[] SYMBOL_WIDEST;
    private static final int[][] WEIGHTS;
    private final List<ExpandedPair> pairs = new ArrayList<ExpandedPair>(11);
    private final List<ExpandedRow> rows = new ArrayList<ExpandedRow>();
    private final int[] startEnd = new int[2];
    private boolean startFromEven = false;

    static {
        SYMBOL_WIDEST = new int[]{7, 5, 4, 3, 1};
        EVEN_TOTAL_SUBSET = new int[]{4, 20, 52, 104, 204};
        GSUM = new int[]{0, 348, 1388, 2948, 3988};
        int[] nArray = new int[]{3, 2, 8, 1};
        FINDER_PATTERNS = new int[][]{{1, 8, 4, 1}, {3, 6, 4, 1}, {3, 4, 6, 1}, nArray, {2, 6, 5, 1}, {2, 2, 9, 1}};
        nArray = new int[]{1, 3, 9, 27, 81, 32, 96, 77};
        int[] nArray2 = new int[]{20, 60, 180, 118, 143, 7, 21, 63};
        int[] nArray3 = new int[]{189, 145, 13, 39, 117, 140, 209, 205};
        int[] nArray4 = new int[]{193, 157, 49, 147, 19, 57, 171, 91};
        int[] nArray5 = new int[]{62, 186, 136, 197, 169, 85, 44, 132};
        int[] nArray6 = new int[]{185, 133, 188, 142, 4, 12, 36, 108};
        int[] nArray7 = new int[]{113, 128, 173, 97, 80, 29, 87, 50};
        int[] nArray8 = new int[]{150, 28, 84, 41, 123, 158, 52, 156};
        int[] nArray9 = new int[]{46, 138, 203, 187, 139, 206, 196, 166};
        int[] nArray10 = new int[]{76, 17, 51, 153, 37, 111, 122, 155};
        int[] nArray11 = new int[]{16, 48, 144, 10, 30, 90, 59, 177};
        int[] nArray12 = new int[]{70, 210, 208, 202, 184, 130, 179, 115};
        int[] nArray13 = new int[]{134, 191, 151, 31, 93, 68, 204, 190};
        int[] nArray14 = new int[]{120, 149, 25, 75, 14, 42, 126, 167};
        int[] nArray15 = new int[]{79, 26, 78, 23, 69, 207, 199, 175};
        int[] nArray16 = new int[]{103, 98, 83, 38, 114, 131, 182, 124};
        int[] nArray17 = new int[]{161, 61, 183, 127, 170, 88, 53, 159};
        int[] nArray18 = new int[]{45, 135, 194, 160, 58, 174, 100, 89};
        WEIGHTS = new int[][]{nArray, nArray2, nArray3, nArray4, nArray5, nArray6, nArray7, nArray8, nArray9, nArray10, {43, 129, 176, 106, 107, 110, 119, 146}, nArray11, {109, 116, 137, 200, 178, 112, 125, 164}, nArray12, nArray13, {148, 22, 66, 198, 172, 94, 71, 2}, {6, 18, 54, 162, 64, 192, 154, 40}, nArray14, nArray15, nArray16, nArray17, {55, 165, 73, 8, 24, 72, 5, 15}, nArray18};
        nArray = new int[]{0, 0};
        nArray2 = new int[]{0, 1, 1};
        nArray3 = new int[]{0, 2, 1, 3};
        nArray4 = new int[]{0, 4, 1, 3, 2};
        nArray5 = new int[]{0, 4, 1, 3, 4, 5, 5};
        FINDER_PATTERN_SEQUENCES = new int[][]{nArray, nArray2, nArray3, nArray4, {0, 4, 1, 3, 3, 5}, nArray5, {0, 0, 1, 1, 2, 2, 3, 3}, {0, 0, 1, 1, 2, 2, 3, 4, 4}, {0, 0, 1, 1, 2, 2, 3, 4, 5, 5}, {0, 0, 1, 1, 2, 3, 3, 4, 4, 5, 5}};
    }

    /*
     * Enabled aggressive block sorting
     */
    private void adjustOddEvenCounts(int n2) throws NotFoundException {
        boolean bl2;
        boolean bl3 = false;
        int n3 = RSSExpandedReader.count(this.getOddCounts());
        int n4 = RSSExpandedReader.count(this.getEvenCounts());
        int n5 = n3 + n4 - n2;
        boolean bl4 = (n3 & 1) == 1;
        if ((n4 & 1) == 0) {
            bl3 = true;
        }
        n2 = 0;
        boolean bl5 = false;
        if (n3 > 13) {
            bl2 = true;
        } else {
            bl2 = bl5;
            if (n3 < 4) {
                n2 = 1;
                bl2 = bl5;
            }
        }
        boolean bl6 = false;
        boolean bl7 = false;
        if (n4 > 13) {
            bl5 = true;
        } else {
            bl5 = bl7;
            if (n4 < 4) {
                bl6 = true;
                bl5 = bl7;
            }
        }
        if (n5 == 1) {
            if (bl4) {
                if (bl3) {
                    throw NotFoundException.getNotFoundInstance();
                }
                bl2 = true;
            } else {
                if (!bl3) {
                    throw NotFoundException.getNotFoundInstance();
                }
                bl5 = true;
            }
        } else if (n5 == -1) {
            if (bl4) {
                if (bl3) {
                    throw NotFoundException.getNotFoundInstance();
                }
                n2 = 1;
            } else {
                if (!bl3) {
                    throw NotFoundException.getNotFoundInstance();
                }
                bl6 = true;
            }
        } else {
            if (n5 != 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (bl4) {
                if (!bl3) {
                    throw NotFoundException.getNotFoundInstance();
                }
                if (n3 < n4) {
                    n2 = 1;
                    bl5 = true;
                } else {
                    bl2 = true;
                    bl6 = true;
                }
            } else if (bl3) {
                throw NotFoundException.getNotFoundInstance();
            }
        }
        if (n2 != 0) {
            if (bl2) {
                throw NotFoundException.getNotFoundInstance();
            }
            RSSExpandedReader.increment(this.getOddCounts(), this.getOddRoundingErrors());
        }
        if (bl2) {
            RSSExpandedReader.decrement(this.getOddCounts(), this.getOddRoundingErrors());
        }
        if (bl6) {
            if (bl5) {
                throw NotFoundException.getNotFoundInstance();
            }
            RSSExpandedReader.increment(this.getEvenCounts(), this.getOddRoundingErrors());
        }
        if (bl5) {
            RSSExpandedReader.decrement(this.getEvenCounts(), this.getEvenRoundingErrors());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean checkChecksum() {
        block4: {
            block3: {
                Object object = this.pairs.get(0);
                DataCharacter dataCharacter = ((ExpandedPair)object).getLeftChar();
                if ((object = ((ExpandedPair)object).getRightChar()) == null) break block3;
                int n2 = ((DataCharacter)object).getChecksumPortion();
                int n3 = 2;
                for (int i2 = 1; i2 < this.pairs.size(); ++i2) {
                    object = this.pairs.get(i2);
                    int n4 = n2 + ((ExpandedPair)object).getLeftChar().getChecksumPortion();
                    int n5 = n3 + 1;
                    object = ((ExpandedPair)object).getRightChar();
                    n2 = n4;
                    n3 = n5;
                    if (object == null) continue;
                    n2 = n4 + ((DataCharacter)object).getChecksumPortion();
                    n3 = n5 + 1;
                }
                if ((n3 - 4) * 211 + n2 % 211 == dataCharacter.getValue()) break block4;
            }
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<ExpandedPair> checkRows(List<ExpandedRow> list, int n2) throws NotFoundException {
        while (true) {
            if (n2 >= this.rows.size()) {
                throw NotFoundException.getNotFoundInstance();
            }
            ExpandedRow expandedRow = this.rows.get(n2);
            this.pairs.clear();
            int n3 = list.size();
            for (int i2 = 0; i2 < n3; ++i2) {
                this.pairs.addAll(list.get(i2).getPairs());
            }
            this.pairs.addAll(expandedRow.getPairs());
            if (RSSExpandedReader.isValidSequence(this.pairs)) {
                if (this.checkChecksum()) {
                    return this.pairs;
                }
                ArrayList<ExpandedRow> arrayList = new ArrayList<ExpandedRow>();
                arrayList.addAll(list);
                arrayList.add(expandedRow);
                try {
                    return this.checkRows(arrayList, n2 + 1);
                }
                catch (NotFoundException notFoundException) {}
            }
            ++n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<ExpandedPair> checkRows(boolean bl2) {
        List<ExpandedPair> list;
        if (this.rows.size() > 25) {
            this.rows.clear();
            return null;
        }
        this.pairs.clear();
        if (bl2) {
            Collections.reverse(this.rows);
        }
        List<ExpandedPair> list2 = null;
        try {
            list2 = list = this.checkRows(new ArrayList<ExpandedRow>(), 0);
        }
        catch (NotFoundException notFoundException) {}
        list = list2;
        if (!bl2) {
            return list;
        }
        Collections.reverse(this.rows);
        return list2;
    }

    static Result constructResult(List<ExpandedPair> object) throws NotFoundException, FormatException {
        String string2 = AbstractExpandedDecoder.createDecoder(BitArrayBuilder.buildBitArray(object)).parseInformation();
        Object object2 = object.get(0).getFinderPattern().getResultPoints();
        Object object3 = object.get(object.size() - 1).getFinderPattern().getResultPoints();
        object = object2[0];
        object2 = object2[1];
        ResultPoint resultPoint = object3[0];
        object3 = object3[1];
        BarcodeFormat barcodeFormat = BarcodeFormat.RSS_EXPANDED;
        return new Result(string2, null, new ResultPoint[]{object, object2, resultPoint, object3}, barcodeFormat);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void findNextPair(BitArray bitArray, List<ExpandedPair> list, int n2) throws NotFoundException {
        int[] nArray = this.getDecodeFinderCounters();
        nArray[0] = 0;
        nArray[1] = 0;
        nArray[2] = 0;
        nArray[3] = 0;
        int n3 = bitArray.getSize();
        if (n2 < 0) {
            n2 = list.isEmpty() ? 0 : list.get(list.size() - 1).getFinderPattern().getStartEnd()[1];
        }
        int n4 = list.size() % 2 != 0 ? 1 : 0;
        int n5 = n4;
        if (this.startFromEven) {
            n5 = n4 == 0 ? 1 : 0;
        }
        n4 = 0;
        while (n2 < n3 && (n4 = !bitArray.get(n2) ? 1 : 0) != 0) {
            ++n2;
        }
        int n6 = 0;
        int n7 = n2;
        int n8 = n2;
        n2 = n7;
        n7 = n4;
        n4 = n6;
        while (true) {
            if (n8 >= n3) {
                throw NotFoundException.getNotFoundInstance();
            }
            if ((bitArray.get(n8) ^ n7) != 0) {
                nArray[n4] = nArray[n4] + 1;
            } else {
                if (n4 == 3) {
                    if (n5 != 0) {
                        RSSExpandedReader.reverseCounters(nArray);
                    }
                    if (RSSExpandedReader.isFinderPattern(nArray)) {
                        this.startEnd[0] = n2;
                        this.startEnd[1] = n8;
                        return;
                    }
                    if (n5 != 0) {
                        RSSExpandedReader.reverseCounters(nArray);
                    }
                    n2 += nArray[0] + nArray[1];
                    nArray[0] = nArray[2];
                    nArray[1] = nArray[3];
                    nArray[2] = 0;
                    nArray[3] = 0;
                    --n4;
                } else {
                    ++n4;
                }
                nArray[n4] = 1;
                n7 = n7 == 0 ? 1 : 0;
            }
            ++n8;
        }
    }

    private static int getNextSecondBar(BitArray bitArray, int n2) {
        if (bitArray.get(n2)) {
            return bitArray.getNextSet(bitArray.getNextUnset(n2));
        }
        return bitArray.getNextUnset(bitArray.getNextSet(n2));
    }

    private static boolean isNotA1left(FinderPattern finderPattern, boolean bl2, boolean bl3) {
        return finderPattern.getValue() != 0 || !bl2 || !bl3;
    }

    private static boolean isPartialRow(Iterable<ExpandedPair> iterable, Iterable<ExpandedRow> object) {
        object = object.iterator();
        while (object.hasNext()) {
            boolean bl2;
            block3: {
                ExpandedRow expandedRow = (ExpandedRow)object.next();
                boolean bl3 = true;
                Iterator<ExpandedPair> iterator = iterable.iterator();
                block1: do {
                    bl2 = bl3;
                    if (!iterator.hasNext()) break block3;
                    ExpandedPair expandedPair = iterator.next();
                    boolean bl4 = false;
                    Iterator<ExpandedPair> iterator2 = expandedRow.getPairs().iterator();
                    do {
                        bl2 = bl4;
                        if (!iterator2.hasNext()) continue block1;
                    } while (!expandedPair.equals(iterator2.next()));
                    bl2 = true;
                } while (bl2);
                bl2 = false;
            }
            if (!bl2) continue;
            return true;
        }
        return false;
    }

    /*
     * Unable to fully structure code
     */
    private static boolean isValidSequence(List<ExpandedPair> var0) {
        block0: for (int[] var7_7 : RSSExpandedReader.FINDER_PATTERN_SEQUENCES) {
            if (var0.size() > var7_7.length) {
                continue block0;
            }
            var4_6 = true;
            var2_4 = 0;
            while (true) {
                block8: {
                    block7: {
                        var3_5 = var4_6;
                        if (var2_4 >= var0.size()) break block7;
                        if (var0.get(var2_4).getFinderPattern().getValue() == var7_7[var2_4]) break block8;
                        var3_5 = false;
                    }
                    if (!var3_5) ** continue;
                    return true;
                }
                ++var2_4;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private FinderPattern parseFoundFinderPattern(BitArray object, int n2, boolean bl2) {
        int n3;
        int n4;
        int n5;
        if (bl2) {
            for (n5 = this.startEnd[0] - 1; n5 >= 0 && !((BitArray)object).get(n5); --n5) {
            }
            n4 = this.startEnd[0] - ++n5;
            n3 = this.startEnd[1];
        } else {
            n5 = this.startEnd[0];
            n3 = ((BitArray)object).getNextUnset(this.startEnd[1] + 1);
            n4 = n3 - this.startEnd[1];
        }
        object = this.getDecodeFinderCounters();
        System.arraycopy(object, 0, object, 1, ((Object)object).length - 1);
        object[0] = n4;
        try {
            n4 = RSSExpandedReader.parseFinderValue((int[])object, FINDER_PATTERNS);
        }
        catch (NotFoundException notFoundException) {
            return null;
        }
        return new FinderPattern(n4, new int[]{n5, n3}, n5, n3, n2);
    }

    private static void removePartialRows(List<ExpandedPair> list, List<ExpandedRow> object) {
        object = object.iterator();
        while (object.hasNext()) {
            boolean bl2;
            block3: {
                Object object2 = (ExpandedRow)object.next();
                if (((ExpandedRow)object2).getPairs().size() == list.size()) continue;
                boolean bl3 = true;
                object2 = ((ExpandedRow)object2).getPairs().iterator();
                block1: do {
                    bl2 = bl3;
                    if (!object2.hasNext()) break block3;
                    ExpandedPair expandedPair = (ExpandedPair)object2.next();
                    boolean bl4 = false;
                    Iterator<ExpandedPair> iterator = list.iterator();
                    do {
                        bl2 = bl4;
                        if (!iterator.hasNext()) continue block1;
                    } while (!expandedPair.equals(iterator.next()));
                    bl2 = true;
                } while (bl2);
                bl2 = false;
            }
            if (!bl2) continue;
            object.remove();
        }
    }

    private static void reverseCounters(int[] nArray) {
        int n2 = nArray.length;
        for (int i2 = 0; i2 < n2 / 2; ++i2) {
            int n3 = nArray[i2];
            nArray[i2] = nArray[n2 - i2 - 1];
            nArray[n2 - i2 - 1] = n3;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void storeRow(int n2, boolean bl2) {
        int n3 = 0;
        boolean bl3 = false;
        boolean bl4 = false;
        while (true) {
            ExpandedRow expandedRow;
            block6: {
                boolean bl5;
                block5: {
                    bl5 = bl4;
                    if (n3 >= this.rows.size()) break block5;
                    expandedRow = this.rows.get(n3);
                    if (expandedRow.getRowNumber() <= n2) break block6;
                    bl5 = expandedRow.isEquivalent(this.pairs);
                }
                if (!bl5 && !bl3) break;
                return;
            }
            bl3 = expandedRow.isEquivalent(this.pairs);
            ++n3;
        }
        if (RSSExpandedReader.isPartialRow(this.pairs, this.rows)) {
            return;
        }
        this.rows.add(n3, new ExpandedRow(this.pairs, n2, bl2));
        RSSExpandedReader.removePartialRows(this.pairs, this.rows);
    }

    /*
     * Enabled aggressive block sorting
     */
    DataCharacter decodeDataCharacter(BitArray object, FinderPattern finderPattern, boolean bl2, boolean bl3) throws NotFoundException {
        int n2;
        int n3;
        float f2;
        float f3;
        int n4;
        int n5;
        int n6;
        int[] nArray = this.getDataCharacterCounters();
        nArray[0] = 0;
        nArray[1] = 0;
        nArray[2] = 0;
        nArray[3] = 0;
        nArray[4] = 0;
        nArray[5] = 0;
        nArray[6] = 0;
        nArray[7] = 0;
        if (bl3) {
            RSSExpandedReader.recordPatternInReverse((BitArray)object, finderPattern.getStartEnd()[0], nArray);
        } else {
            RSSExpandedReader.recordPattern((BitArray)object, finderPattern.getStartEnd()[1], nArray);
            n6 = 0;
            for (n5 = nArray.length - 1; n6 < n5; ++n6, --n5) {
                n4 = nArray[n6];
                nArray[n6] = nArray[n5];
                nArray[n5] = n4;
            }
        }
        if (Math.abs((f3 = (float)RSSExpandedReader.count(nArray) / (float)17) - (f2 = (float)(finderPattern.getStartEnd()[1] - finderPattern.getStartEnd()[0]) / 15.0f)) / f2 > 0.3f) {
            throw NotFoundException.getNotFoundInstance();
        }
        object = this.getOddCounts();
        int[] nArray2 = this.getEvenCounts();
        float[] fArray = this.getOddRoundingErrors();
        float[] fArray2 = this.getEvenRoundingErrors();
        for (n6 = 0; n6 < nArray.length; ++n6) {
            f2 = 1.0f * (float)nArray[n6] / f3;
            n4 = (int)(0.5f + f2);
            if (n4 < 1) {
                if (f2 < 0.3f) {
                    throw NotFoundException.getNotFoundInstance();
                }
                n5 = 1;
            } else {
                n5 = n4;
                if (n4 > 8) {
                    if (f2 > 8.7f) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    n5 = 8;
                }
            }
            n4 = n6 / 2;
            if ((n6 & 1) == 0) {
                object[n4] = n5;
                fArray[n4] = f2 - (float)n5;
                continue;
            }
            nArray2[n4] = n5;
            fArray2[n4] = f2 - (float)n5;
        }
        this.adjustOddEvenCounts(17);
        n4 = finderPattern.getValue();
        n5 = bl2 ? 0 : 2;
        n6 = bl3 ? 0 : 1;
        int n7 = n6 + (n4 * 4 + n5) - 1;
        n6 = 0;
        n5 = 0;
        for (n4 = ((Object)object).length - 1; n4 >= 0; n6 += object[n4], --n4) {
            n3 = n5;
            if (RSSExpandedReader.isNotA1left(finderPattern, bl2, bl3)) {
                n3 = WEIGHTS[n7][n4 * 2];
                n3 = n5 + object[n4] * n3;
            }
            n5 = n3;
        }
        n4 = 0;
        for (n3 = nArray2.length - 1; n3 >= 0; --n3) {
            n2 = n4;
            if (RSSExpandedReader.isNotA1left(finderPattern, bl2, bl3)) {
                n2 = WEIGHTS[n7][n3 * 2 + 1];
                n2 = n4 + nArray2[n3] * n2;
            }
            n4 = n2;
        }
        if ((n6 & 1) == 0 && n6 <= 13 && n6 >= 4) {
            n6 = (13 - n6) / 2;
            n2 = SYMBOL_WIDEST[n6];
            n3 = RSSUtils.getRSSvalue((int[])object, n2, true);
            n2 = RSSUtils.getRSSvalue(nArray2, 9 - n2, false);
            return new DataCharacter(n3 * EVEN_TOTAL_SUBSET[n6] + n2 + GSUM[n6], n5 + n4);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public Result decodeRow(int n2, BitArray bitArray, Map<DecodeHintType, ?> object) throws NotFoundException, FormatException {
        this.pairs.clear();
        this.startFromEven = false;
        try {
            object = RSSExpandedReader.constructResult(this.decodeRow2pairs(n2, bitArray));
            return object;
        }
        catch (NotFoundException notFoundException) {
            this.pairs.clear();
            this.startFromEven = true;
            return RSSExpandedReader.constructResult(this.decodeRow2pairs(n2, bitArray));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    List<ExpandedPair> decodeRow2pairs(int n2, BitArray bitArray) throws NotFoundException {
        try {
            while (true) {
                ExpandedPair expandedPair = this.retrieveNextPair(bitArray, this.pairs, n2);
                this.pairs.add(expandedPair);
            }
        }
        catch (NotFoundException notFoundException) {
            if (this.pairs.isEmpty()) {
                throw notFoundException;
            }
            if (this.checkChecksum()) {
                return this.pairs;
            }
            boolean bl2 = !this.rows.isEmpty();
            this.storeRow(n2, false);
            if (!bl2) throw NotFoundException.getNotFoundInstance();
            List<ExpandedPair> list = this.checkRows(false);
            List<ExpandedPair> list2 = list;
            if (list != null) return list2;
            list = this.checkRows(true);
            list2 = list;
            if (list != null) return list2;
            throw NotFoundException.getNotFoundInstance();
        }
    }

    List<ExpandedRow> getRows() {
        return this.rows;
    }

    @Override
    public void reset() {
        this.pairs.clear();
        this.rows.clear();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    ExpandedPair retrieveNextPair(BitArray object, List<ExpandedPair> list, int n2) throws NotFoundException {
        FinderPattern finderPattern;
        boolean bl2;
        boolean bl3 = list.size() % 2 == 0;
        boolean bl4 = bl3;
        if (this.startFromEven) {
            bl4 = !bl3;
        }
        boolean bl5 = true;
        int n3 = -1;
        do {
            this.findNextPair((BitArray)object, list, n3);
            finderPattern = this.parseFoundFinderPattern((BitArray)object, n2, bl4);
            if (finderPattern == null) {
                n3 = RSSExpandedReader.getNextSecondBar((BitArray)object, this.startEnd[0]);
                bl2 = bl5;
            } else {
                bl2 = false;
            }
            bl5 = bl2;
        } while (bl2);
        DataCharacter dataCharacter = this.decodeDataCharacter((BitArray)object, finderPattern, bl4, true);
        if (!list.isEmpty() && list.get(list.size() - 1).mustBeLast()) {
            throw NotFoundException.getNotFoundInstance();
        }
        try {
            object = this.decodeDataCharacter((BitArray)object, finderPattern, bl4, false);
            return new ExpandedPair(dataCharacter, (DataCharacter)object, finderPattern, true);
        }
        catch (NotFoundException notFoundException) {
            object = null;
            return new ExpandedPair(dataCharacter, (DataCharacter)object, finderPattern, true);
        }
    }
}

