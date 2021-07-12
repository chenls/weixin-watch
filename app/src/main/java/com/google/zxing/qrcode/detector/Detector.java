/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.detector.AlignmentPattern;
import com.google.zxing.qrcode.detector.AlignmentPatternFinder;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.util.Map;

public class Detector {
    private final BitMatrix image;
    private ResultPointCallback resultPointCallback;

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    private float calculateModuleSizeOneWay(ResultPoint resultPoint, ResultPoint resultPoint2) {
        float f2 = this.sizeOfBlackWhiteBlackRunBothWays((int)resultPoint.getX(), (int)resultPoint.getY(), (int)resultPoint2.getX(), (int)resultPoint2.getY());
        float f3 = this.sizeOfBlackWhiteBlackRunBothWays((int)resultPoint2.getX(), (int)resultPoint2.getY(), (int)resultPoint.getX(), (int)resultPoint.getY());
        if (Float.isNaN(f2)) {
            return f3 / 7.0f;
        }
        if (Float.isNaN(f3)) {
            return f2 / 7.0f;
        }
        return (f2 + f3) / 14.0f;
    }

    private static int computeDimension(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, float f2) throws NotFoundException {
        int n2 = (MathUtils.round(ResultPoint.distance(resultPoint, resultPoint2) / f2) + MathUtils.round(ResultPoint.distance(resultPoint, resultPoint3) / f2)) / 2 + 7;
        switch (n2 & 3) {
            default: {
                return n2;
            }
            case 0: {
                return n2 + 1;
            }
            case 2: {
                return n2 - 1;
            }
            case 3: 
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static PerspectiveTransform createTransform(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int n2) {
        float f2;
        float f3;
        float f4;
        float f5;
        float f6 = (float)n2 - 3.5f;
        if (resultPoint4 != null) {
            f5 = resultPoint4.getX();
            f4 = resultPoint4.getY();
            f2 = f3 = f6 - 3.0f;
            return PerspectiveTransform.quadrilateralToQuadrilateral(3.5f, 3.5f, f6, 3.5f, f3, f2, 3.5f, f6, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), f5, f4, resultPoint3.getX(), resultPoint3.getY());
        }
        f5 = resultPoint2.getX() - resultPoint.getX() + resultPoint3.getX();
        f4 = resultPoint2.getY() - resultPoint.getY() + resultPoint3.getY();
        f3 = f6;
        f2 = f6;
        return PerspectiveTransform.quadrilateralToQuadrilateral(3.5f, 3.5f, f6, 3.5f, f3, f2, 3.5f, f6, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), f5, f4, resultPoint3.getX(), resultPoint3.getY());
    }

    private static BitMatrix sampleGrid(BitMatrix bitMatrix, PerspectiveTransform perspectiveTransform, int n2) throws NotFoundException {
        return GridSampler.getInstance().sampleGrid(bitMatrix, n2, n2, perspectiveTransform);
    }

    /*
     * Enabled aggressive block sorting
     */
    private float sizeOfBlackWhiteBlackRun(int n2, int n3, int n4, int n5) {
        boolean bl2 = Math.abs(n5 - n3) > Math.abs(n4 - n2);
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        int n9 = n5;
        if (bl2) {
            n9 = n4;
            n8 = n5;
            n7 = n2;
            n6 = n3;
        }
        int n10 = Math.abs(n8 - n6);
        int n11 = Math.abs(n9 - n7);
        int n12 = -n10 / 2;
        int n13 = n6 < n8 ? 1 : -1;
        int n14 = n7 < n9 ? 1 : -1;
        n4 = 0;
        n2 = n6;
        n3 = n7;
        while (true) {
            int n15;
            block8: {
                block9: {
                    block7: {
                        n5 = n4;
                        if (n2 == n8 + n13) break block7;
                        n15 = bl2 ? n3 : n2;
                        int n16 = bl2 ? n2 : n3;
                        boolean bl3 = n4 == 1;
                        n5 = n4;
                        if (bl3 == this.image.get(n15, n16)) {
                            if (n4 == 2) {
                                return MathUtils.distance(n2, n3, n6, n7);
                            }
                            n5 = n4 + 1;
                        }
                        n4 = n12 += n11;
                        n15 = n3;
                        if (n12 <= 0) break block8;
                        if (n3 != n9) break block9;
                    }
                    if (n5 != 2) break;
                    return MathUtils.distance(n8 + n13, n9, n6, n7);
                }
                n15 = n3 + n14;
                n4 = n12 - n10;
            }
            n2 += n13;
            n12 = n4;
            n4 = n5;
            n3 = n15;
        }
        return Float.NaN;
    }

    /*
     * Enabled aggressive block sorting
     */
    private float sizeOfBlackWhiteBlackRunBothWays(int n2, int n3, int n4, int n5) {
        float f2 = this.sizeOfBlackWhiteBlackRun(n2, n3, n4, n5);
        float f3 = 1.0f;
        int n6 = n2 - (n4 - n2);
        if (n6 < 0) {
            f3 = (float)n2 / (float)(n2 - n6);
            n4 = 0;
        } else {
            n4 = n6;
            if (n6 >= this.image.getWidth()) {
                f3 = (float)(this.image.getWidth() - 1 - n2) / (float)(n6 - n2);
                n4 = this.image.getWidth() - 1;
            }
        }
        n6 = (int)((float)n3 - (float)(n5 - n3) * f3);
        f3 = 1.0f;
        if (n6 < 0) {
            f3 = (float)n3 / (float)(n3 - n6);
            n5 = 0;
            return f2 + this.sizeOfBlackWhiteBlackRun(n2, n3, (int)((float)n2 + (float)(n4 - n2) * f3), n5) - 1.0f;
        }
        n5 = n6;
        if (n6 < this.image.getHeight()) return f2 + this.sizeOfBlackWhiteBlackRun(n2, n3, (int)((float)n2 + (float)(n4 - n2) * f3), n5) - 1.0f;
        f3 = (float)(this.image.getHeight() - 1 - n3) / (float)(n6 - n3);
        n5 = this.image.getHeight() - 1;
        return f2 + this.sizeOfBlackWhiteBlackRun(n2, n3, (int)((float)n2 + (float)(n4 - n2) * f3), n5) - 1.0f;
    }

    protected final float calculateModuleSize(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3) {
        return (this.calculateModuleSizeOneWay(resultPoint, resultPoint2) + this.calculateModuleSizeOneWay(resultPoint, resultPoint3)) / 2.0f;
    }

    public DetectorResult detect() throws NotFoundException, FormatException {
        return this.detect(null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final DetectorResult detect(Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        ResultPointCallback resultPointCallback = map == null ? null : (ResultPointCallback)map.get((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK);
        this.resultPointCallback = resultPointCallback;
        return this.processFinderPatternInfo(new FinderPatternFinder(this.image, this.resultPointCallback).find(map));
    }

    protected final AlignmentPattern findAlignmentInRegion(float f2, int n2, int n3, float f3) throws NotFoundException {
        int n4 = (int)(f3 * f2);
        int n5 = Math.max(0, n2 - n4);
        n2 = Math.min(this.image.getWidth() - 1, n2 + n4);
        if ((float)(n2 - n5) < f2 * 3.0f) {
            throw NotFoundException.getNotFoundInstance();
        }
        int n6 = Math.max(0, n3 - n4);
        n3 = Math.min(this.image.getHeight() - 1, n3 + n4);
        if ((float)(n3 - n6) < f2 * 3.0f) {
            throw NotFoundException.getNotFoundInstance();
        }
        return new AlignmentPatternFinder(this.image, n5, n6, n2 - n5, n3 - n6, f2, this.resultPointCallback).find();
    }

    protected final BitMatrix getImage() {
        return this.image;
    }

    protected final ResultPointCallback getResultPointCallback() {
        return this.resultPointCallback;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final DetectorResult processFinderPatternInfo(FinderPatternInfo object) throws NotFoundException, FormatException {
        void var1_8;
        void var1_6;
        ResultPoint[] resultPointArray;
        FinderPattern finderPattern;
        FinderPattern finderPattern2;
        FinderPattern finderPattern3 = ((FinderPatternInfo)object).getTopLeft();
        float f2 = this.calculateModuleSize(finderPattern3, finderPattern2 = ((FinderPatternInfo)object).getTopRight(), finderPattern = ((FinderPatternInfo)object).getBottomLeft());
        if (f2 < 1.0f) {
            throw NotFoundException.getNotFoundInstance();
        }
        int n2 = Detector.computeDimension(finderPattern3, finderPattern2, finderPattern, f2);
        Object object2 = Version.getProvisionalVersionForDimension(n2);
        int n3 = ((Version)object2).getDimensionForVersion();
        ResultPoint[] resultPointArray2 = resultPointArray = null;
        if (((Version)object2).getAlignmentPatternCenters().length > 0) {
            float f3 = finderPattern2.getX();
            float f4 = finderPattern3.getX();
            float f5 = finderPattern.getX();
            float f6 = finderPattern2.getY();
            float f7 = finderPattern3.getY();
            float f8 = finderPattern.getY();
            float f9 = 1.0f - 3.0f / (float)(n3 - 7);
            int n4 = (int)(finderPattern3.getX() + (f3 - f4 + f5 - finderPattern3.getX()) * f9);
            int n5 = (int)(finderPattern3.getY() + (f6 - f7 + f8 - finderPattern3.getY()) * f9);
            n3 = 4;
            while (true) {
                ResultPoint[] resultPointArray3 = resultPointArray;
                if (n3 > 16) break;
                f3 = n3;
                try {
                    AlignmentPattern alignmentPattern = this.findAlignmentInRegion(f2, n4, n5, f3);
                }
                catch (NotFoundException notFoundException) {
                    n3 <<= 1;
                    continue;
                }
                break;
            }
        }
        resultPointArray = Detector.createTransform(finderPattern3, finderPattern2, finderPattern, (ResultPoint)var1_6, n2);
        object2 = Detector.sampleGrid(this.image, (PerspectiveTransform)resultPointArray, n2);
        if (var1_6 == null) {
            ResultPoint[] resultPointArray4 = new ResultPoint[]{finderPattern, finderPattern3, finderPattern2};
            return new DetectorResult((BitMatrix)object2, (ResultPoint[])var1_8);
        }
        resultPointArray = new ResultPoint[]{finderPattern, finderPattern3, finderPattern2, var1_6};
        ResultPoint[] resultPointArray5 = resultPointArray;
        return new DetectorResult((BitMatrix)object2, (ResultPoint[])var1_8);
    }
}

