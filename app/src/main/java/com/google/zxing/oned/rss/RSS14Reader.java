/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.AbstractRSSReader;
import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;
import com.google.zxing.oned.rss.Pair;
import com.google.zxing.oned.rss.RSSUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class RSS14Reader
extends AbstractRSSReader {
    private static final int[][] FINDER_PATTERNS;
    private static final int[] INSIDE_GSUM;
    private static final int[] INSIDE_ODD_TOTAL_SUBSET;
    private static final int[] INSIDE_ODD_WIDEST;
    private static final int[] OUTSIDE_EVEN_TOTAL_SUBSET;
    private static final int[] OUTSIDE_GSUM;
    private static final int[] OUTSIDE_ODD_WIDEST;
    private final List<Pair> possibleLeftPairs = new ArrayList<Pair>();
    private final List<Pair> possibleRightPairs = new ArrayList<Pair>();

    static {
        OUTSIDE_EVEN_TOTAL_SUBSET = new int[]{1, 10, 34, 70, 126};
        INSIDE_ODD_TOTAL_SUBSET = new int[]{4, 20, 48, 81};
        OUTSIDE_GSUM = new int[]{0, 161, 961, 2015, 2715};
        INSIDE_GSUM = new int[]{0, 336, 1036, 1516};
        OUTSIDE_ODD_WIDEST = new int[]{8, 6, 4, 3, 1};
        INSIDE_ODD_WIDEST = new int[]{2, 4, 6, 8};
        int[] nArray = new int[]{3, 5, 5, 1};
        int[] nArray2 = new int[]{3, 3, 7, 1};
        int[] nArray3 = new int[]{3, 1, 9, 1};
        int[] nArray4 = new int[]{2, 7, 4, 1};
        int[] nArray5 = new int[]{2, 5, 6, 1};
        int[] nArray6 = new int[]{1, 3, 9, 1};
        FINDER_PATTERNS = new int[][]{{3, 8, 2, 1}, nArray, nArray2, nArray3, nArray4, nArray5, {2, 3, 8, 1}, {1, 5, 7, 1}, nArray6};
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void addOrTally(Collection<Pair> collection, Pair pair) {
        boolean bl2;
        block2: {
            Pair pair2;
            if (pair == null) {
                return;
            }
            boolean bl3 = false;
            Iterator<Pair> iterator = collection.iterator();
            do {
                bl2 = bl3;
                if (!iterator.hasNext()) break block2;
            } while ((pair2 = iterator.next()).getValue() != pair.getValue());
            pair2.incrementCount();
            return;
        }
        if (bl2) return;
        collection.add(pair);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void adjustOddEvenCounts(boolean bl2, int n2) throws NotFoundException {
        boolean bl3;
        int n3;
        boolean bl4;
        boolean bl5;
        boolean bl6;
        int n4;
        int n5;
        int n6;
        block39: {
            int n7;
            int n8;
            boolean bl7;
            block38: {
                int n9;
                n6 = RSS14Reader.count(this.getOddCounts());
                n5 = RSS14Reader.count(this.getEvenCounts());
                n4 = n6 + n5 - n2;
                n2 = bl2 ? 1 : 0;
                bl6 = (n6 & 1) == n2;
                bl5 = (n5 & 1) == 1;
                bl4 = false;
                bl7 = false;
                n3 = 0;
                n2 = 0;
                bl3 = false;
                n8 = 0;
                if (!bl2) break block38;
                if (n6 > 12) {
                    n9 = 1;
                } else {
                    n9 = n2;
                    if (n6 < 4) {
                        bl7 = true;
                        n9 = n2;
                    }
                }
                if (n5 > 12) {
                    n2 = 1;
                    bl4 = bl7;
                    n3 = n9;
                    break block39;
                } else {
                    n2 = n8;
                    n3 = n9;
                    bl4 = bl7;
                    if (n5 < 4) {
                        bl3 = true;
                        n2 = n8;
                        n3 = n9;
                        bl4 = bl7;
                    }
                }
                break block39;
            }
            if (n6 > 11) {
                n7 = 1;
                bl7 = bl4;
            } else {
                n7 = n3;
                bl7 = bl4;
                if (n6 < 5) {
                    bl7 = true;
                    n7 = n3;
                }
            }
            if (n5 > 10) {
                n2 = 1;
                n3 = n7;
                bl4 = bl7;
            } else {
                n2 = n8;
                n3 = n7;
                bl4 = bl7;
                if (n5 < 4) {
                    bl3 = true;
                    n2 = n8;
                    n3 = n7;
                    bl4 = bl7;
                }
            }
        }
        if (n4 == 1) {
            if (bl6) {
                if (bl5) {
                    throw NotFoundException.getNotFoundInstance();
                }
                n3 = 1;
            } else {
                if (!bl5) {
                    throw NotFoundException.getNotFoundInstance();
                }
                n2 = 1;
            }
        } else if (n4 == -1) {
            if (bl6) {
                if (bl5) {
                    throw NotFoundException.getNotFoundInstance();
                }
                bl4 = true;
            } else {
                if (!bl5) {
                    throw NotFoundException.getNotFoundInstance();
                }
                bl3 = true;
            }
        } else {
            if (n4 != 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (bl6) {
                if (!bl5) {
                    throw NotFoundException.getNotFoundInstance();
                }
                if (n6 < n5) {
                    bl4 = true;
                    n2 = 1;
                } else {
                    n3 = 1;
                    bl3 = true;
                }
            } else if (bl5) {
                throw NotFoundException.getNotFoundInstance();
            }
        }
        if (bl4) {
            if (n3 != 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            RSS14Reader.increment(this.getOddCounts(), this.getOddRoundingErrors());
        }
        if (n3 != 0) {
            RSS14Reader.decrement(this.getOddCounts(), this.getOddRoundingErrors());
        }
        if (bl3) {
            if (n2 != 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            RSS14Reader.increment(this.getEvenCounts(), this.getOddRoundingErrors());
        }
        if (n2 != 0) {
            RSS14Reader.decrement(this.getEvenCounts(), this.getEvenRoundingErrors());
        }
    }

    private static boolean checkChecksum(Pair pair, Pair pair2) {
        int n2;
        int n3 = pair.getChecksumPortion();
        int n4 = pair2.getChecksumPortion();
        int n5 = n2 = pair.getFinderPattern().getValue() * 9 + pair2.getFinderPattern().getValue();
        if (n2 > 72) {
            n5 = n2 - 1;
        }
        n2 = n5;
        if (n5 > 8) {
            n2 = n5 - 1;
        }
        return (n3 + n4 * 16) % 79 == n2;
    }

    private static Result constructResult(Pair object, Pair object2) {
        int n2;
        Object object3 = String.valueOf(4537077L * (long)((DataCharacter)object).getValue() + (long)((DataCharacter)object2).getValue());
        Object object4 = new StringBuilder(14);
        for (n2 = 13 - object3.length(); n2 > 0; --n2) {
            ((StringBuilder)object4).append('0');
        }
        ((StringBuilder)object4).append((String)object3);
        int n3 = 0;
        for (n2 = 0; n2 < 13; ++n2) {
            int n4;
            int n5 = n4 = ((StringBuilder)object4).charAt(n2) - 48;
            if ((n2 & 1) == 0) {
                n5 = n4 * 3;
            }
            n3 += n5;
        }
        n2 = n3 = 10 - n3 % 10;
        if (n3 == 10) {
            n2 = 0;
        }
        ((StringBuilder)object4).append(n2);
        Object object5 = ((Pair)object).getFinderPattern().getResultPoints();
        object3 = ((Pair)object2).getFinderPattern().getResultPoints();
        object = String.valueOf(((StringBuilder)object4).toString());
        object2 = object5[0];
        object4 = object5[1];
        object5 = object3[0];
        object3 = object3[1];
        BarcodeFormat barcodeFormat = BarcodeFormat.RSS_14;
        return new Result((String)object, null, new ResultPoint[]{object2, object4, object5, object3}, barcodeFormat);
    }

    /*
     * Enabled aggressive block sorting
     */
    private DataCharacter decodeDataCharacter(BitArray object, FinderPattern object2, boolean bl2) throws NotFoundException {
        int n2;
        int n3;
        int n4;
        int n5;
        int[] nArray = this.getDataCharacterCounters();
        nArray[0] = 0;
        nArray[1] = 0;
        nArray[2] = 0;
        nArray[3] = 0;
        nArray[4] = 0;
        nArray[5] = 0;
        nArray[6] = 0;
        nArray[7] = 0;
        if (bl2) {
            RSS14Reader.recordPatternInReverse((BitArray)object, ((FinderPattern)object2).getStartEnd()[0], nArray);
        } else {
            RSS14Reader.recordPattern((BitArray)object, ((FinderPattern)object2).getStartEnd()[1] + 1, nArray);
            n5 = 0;
            for (n4 = nArray.length - 1; n5 < n4; ++n5, --n4) {
                n3 = nArray[n5];
                nArray[n5] = nArray[n4];
                nArray[n4] = n3;
            }
        }
        n5 = bl2 ? 16 : 15;
        float f2 = (float)RSS14Reader.count(nArray) / (float)n5;
        object = this.getOddCounts();
        object2 = this.getEvenCounts();
        float[] fArray = this.getOddRoundingErrors();
        float[] fArray2 = this.getEvenRoundingErrors();
        for (n3 = 0; n3 < nArray.length; ++n3) {
            float f3 = (float)nArray[n3] / f2;
            n2 = (int)(0.5f + f3);
            if (n2 < 1) {
                n4 = 1;
            } else {
                n4 = n2;
                if (n2 > 8) {
                    n4 = 8;
                }
            }
            n2 = n3 / 2;
            if ((n3 & 1) == 0) {
                object[n2] = n4;
                fArray[n2] = f3 - (float)n4;
                continue;
            }
            object2[n2] = n4;
            fArray2[n2] = f3 - (float)n4;
        }
        this.adjustOddEvenCounts(bl2, n5);
        n4 = 0;
        n5 = 0;
        for (n3 = ((Object)object).length - 1; n3 >= 0; n4 += object[n3], --n3) {
            n5 = n5 * 9 + object[n3];
        }
        int n6 = 0;
        n3 = 0;
        for (n2 = ((Object)object2).length - 1; n2 >= 0; n3 += object2[n2], --n2) {
            n6 = n6 * 9 + object2[n2];
        }
        n5 += n6 * 3;
        if (bl2) {
            if ((n4 & 1) == 0 && n4 <= 12 && n4 >= 4) {
                n4 = (12 - n4) / 2;
                n2 = OUTSIDE_ODD_WIDEST[n4];
                n3 = RSSUtils.getRSSvalue((int[])object, n2, false);
                n2 = RSSUtils.getRSSvalue((int[])object2, 9 - n2, true);
                return new DataCharacter(n3 * OUTSIDE_EVEN_TOTAL_SUBSET[n4] + n2 + OUTSIDE_GSUM[n4], n5);
            }
            throw NotFoundException.getNotFoundInstance();
        }
        if ((n3 & 1) == 0 && n3 <= 10 && n3 >= 4) {
            n4 = (10 - n3) / 2;
            n3 = INSIDE_ODD_WIDEST[n4];
            n2 = RSSUtils.getRSSvalue((int[])object, n3, true);
            return new DataCharacter(RSSUtils.getRSSvalue((int[])object2, 9 - n3, false) * INSIDE_ODD_TOTAL_SUBSET[n4] + n2 + INSIDE_GSUM[n4], n5);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Pair decodePair(BitArray object, boolean bl2, int n2, Map<DecodeHintType, ?> object2) {
        try {
            int[] nArray = this.findFinderPattern((BitArray)object, 0, bl2);
            FinderPattern finderPattern = this.parseFoundFinderPattern((BitArray)object, n2, bl2, nArray);
            object2 = object2 == null ? null : (ResultPointCallback)object2.get((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK);
            if (object2 != null) {
                float f2;
                float f3 = f2 = (float)(nArray[0] + nArray[1]) / 2.0f;
                if (bl2) {
                    f3 = (float)(((BitArray)object).getSize() - 1) - f2;
                }
                object2.foundPossibleResultPoint(new ResultPoint(f3, n2));
            }
            object2 = this.decodeDataCharacter((BitArray)object, finderPattern, true);
            object = this.decodeDataCharacter((BitArray)object, finderPattern, false);
            return new Pair(((DataCharacter)object2).getValue() * 1597 + ((DataCharacter)object).getValue(), ((DataCharacter)object2).getChecksumPortion() + ((DataCharacter)object).getChecksumPortion() * 4, finderPattern);
        }
        catch (NotFoundException notFoundException) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private int[] findFinderPattern(BitArray bitArray, int n2, boolean bl2) throws NotFoundException {
        int[] nArray = this.getDecodeFinderCounters();
        nArray[0] = 0;
        nArray[1] = 0;
        nArray[2] = 0;
        nArray[3] = 0;
        int n3 = bitArray.getSize();
        boolean bl3 = false;
        while (n2 < n3 && bl2 != (bl3 = !bitArray.get(n2))) {
            ++n2;
        }
        int n4 = 0;
        int n5 = n2;
        int n6 = n2;
        n2 = n5;
        n5 = n4;
        while (true) {
            if (n6 >= n3) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (bitArray.get(n6) ^ bl3) {
                nArray[n5] = nArray[n5] + 1;
                bl2 = bl3;
            } else {
                if (n5 == 3) {
                    if (RSS14Reader.isFinderPattern(nArray)) {
                        return new int[]{n2, n6};
                    }
                    n2 += nArray[0] + nArray[1];
                    nArray[0] = nArray[2];
                    nArray[1] = nArray[3];
                    nArray[2] = 0;
                    nArray[3] = 0;
                    --n5;
                } else {
                    ++n5;
                }
                nArray[n5] = 1;
                bl2 = !bl3;
            }
            ++n6;
            bl3 = bl2;
        }
    }

    private FinderPattern parseFoundFinderPattern(BitArray bitArray, int n2, boolean bl2, int[] nArray) throws NotFoundException {
        int n3;
        boolean bl3 = bitArray.get(nArray[0]);
        for (n3 = nArray[0] - 1; n3 >= 0 && bitArray.get(n3) ^ bl3; --n3) {
        }
        int n4 = n3 + 1;
        n3 = nArray[0];
        int[] nArray2 = this.getDecodeFinderCounters();
        System.arraycopy(nArray2, 0, nArray2, 1, nArray2.length - 1);
        nArray2[0] = n3 - n4;
        int n5 = RSS14Reader.parseFinderValue(nArray2, FINDER_PATTERNS);
        int n6 = n4;
        int n7 = nArray[1];
        int n8 = n6;
        n3 = n7;
        if (bl2) {
            n8 = bitArray.getSize() - 1 - n6;
            n3 = bitArray.getSize() - 1 - n7;
        }
        return new FinderPattern(n5, new int[]{n4, nArray[1]}, n8, n3, n2);
    }

    @Override
    public Result decodeRow(int n2, BitArray object, Map<DecodeHintType, ?> object2) throws NotFoundException {
        Pair pair = this.decodePair((BitArray)object, false, n2, (Map<DecodeHintType, ?>)object2);
        RSS14Reader.addOrTally(this.possibleLeftPairs, pair);
        ((BitArray)object).reverse();
        object2 = this.decodePair((BitArray)object, true, n2, (Map<DecodeHintType, ?>)object2);
        RSS14Reader.addOrTally(this.possibleRightPairs, (Pair)object2);
        ((BitArray)object).reverse();
        int n3 = this.possibleLeftPairs.size();
        for (n2 = 0; n2 < n3; ++n2) {
            object = this.possibleLeftPairs.get(n2);
            if (((Pair)object).getCount() <= 1) continue;
            int n4 = this.possibleRightPairs.size();
            for (int i2 = 0; i2 < n4; ++i2) {
                object2 = this.possibleRightPairs.get(i2);
                if (((Pair)object2).getCount() <= 1 || !RSS14Reader.checkChecksum((Pair)object, (Pair)object2)) continue;
                return RSS14Reader.constructResult((Pair)object, (Pair)object2);
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public void reset() {
        this.possibleLeftPairs.clear();
        this.possibleRightPairs.clear();
    }
}

