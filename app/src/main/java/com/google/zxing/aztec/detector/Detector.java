/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.aztec.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

public final class Detector {
    private static final int[] EXPECTED_CORNER_BITS = new int[]{3808, 476, 2107, 1799};
    private boolean compact;
    private final BitMatrix image;
    private int nbCenterLayers;
    private int nbDataBlocks;
    private int nbLayers;
    private int shift;

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    private static float distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.distance(resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    private static float distance(Point point, Point point2) {
        return MathUtils.distance(point.getX(), point.getY(), point2.getX(), point2.getY());
    }

    private static ResultPoint[] expandSquare(ResultPoint[] resultPointArray, float f2, float f3) {
        f2 = f3 / (2.0f * f2);
        f3 = resultPointArray[0].getX() - resultPointArray[2].getX();
        float f4 = resultPointArray[0].getY() - resultPointArray[2].getY();
        float f5 = (resultPointArray[0].getX() + resultPointArray[2].getX()) / 2.0f;
        float f6 = (resultPointArray[0].getY() + resultPointArray[2].getY()) / 2.0f;
        ResultPoint resultPoint = new ResultPoint(f2 * f3 + f5, f2 * f4 + f6);
        ResultPoint resultPoint2 = new ResultPoint(f5 - f2 * f3, f6 - f2 * f4);
        f3 = resultPointArray[1].getX() - resultPointArray[3].getX();
        f4 = resultPointArray[1].getY() - resultPointArray[3].getY();
        f5 = (resultPointArray[1].getX() + resultPointArray[3].getX()) / 2.0f;
        f6 = (resultPointArray[1].getY() + resultPointArray[3].getY()) / 2.0f;
        return new ResultPoint[]{resultPoint, new ResultPoint(f2 * f3 + f5, f2 * f4 + f6), resultPoint2, new ResultPoint(f5 - f2 * f3, f6 - f2 * f4)};
    }

    /*
     * Enabled aggressive block sorting
     */
    private void extractParameters(ResultPoint[] resultPointArray) throws NotFoundException {
        if (!(this.isValid(resultPointArray[0]) && this.isValid(resultPointArray[1]) && this.isValid(resultPointArray[2]) && this.isValid(resultPointArray[3]))) {
            throw NotFoundException.getNotFoundInstance();
        }
        int n2 = this.nbCenterLayers * 2;
        int[] nArray = new int[]{this.sampleLine(resultPointArray[0], resultPointArray[1], n2), this.sampleLine(resultPointArray[1], resultPointArray[2], n2), this.sampleLine(resultPointArray[2], resultPointArray[3], n2), this.sampleLine(resultPointArray[3], resultPointArray[0], n2)};
        this.shift = Detector.getRotation(nArray, n2);
        long l2 = 0L;
        for (n2 = 0; n2 < 4; ++n2) {
            int n3 = nArray[(this.shift + n2) % 4];
            l2 = this.compact ? (l2 << 7) + (long)(n3 >> 1 & 0x7F) : (l2 << 10) + (long)((n3 >> 2 & 0x3E0) + (n3 >> 1 & 0x1F));
        }
        n2 = Detector.getCorrectedParameterData(l2, this.compact);
        if (this.compact) {
            this.nbLayers = (n2 >> 6) + 1;
            this.nbDataBlocks = (n2 & 0x3F) + 1;
            return;
        }
        this.nbLayers = (n2 >> 11) + 1;
        this.nbDataBlocks = (n2 & 0x7FF) + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private ResultPoint[] getBullsEyeCorners(Point object) throws NotFoundException {
        float f2;
        Object object2 = object;
        Object object3 = object;
        Object object4 = object;
        boolean bl2 = true;
        this.nbCenterLayers = 1;
        while (true) {
            Point point;
            Point point2;
            Point point3;
            Point point4;
            block4: {
                block3: {
                    if (this.nbCenterLayers >= 9) break block3;
                    point4 = this.getFirstDifferent((Point)object2, bl2, 1, -1);
                    point3 = this.getFirstDifferent((Point)object3, bl2, 1, 1);
                    point2 = this.getFirstDifferent((Point)object4, bl2, -1, 1);
                    point = this.getFirstDifferent((Point)object, bl2, -1, -1);
                    if (this.nbCenterLayers <= 2 || !((double)(f2 = Detector.distance(point, point4) * (float)this.nbCenterLayers / (Detector.distance((Point)object, (Point)object2) * (float)(this.nbCenterLayers + 2))) < 0.75) && !((double)f2 > 1.25) && this.isWhiteOrBlackRectangle(point4, point3, point2, point)) break block4;
                }
                if (this.nbCenterLayers == 5 || this.nbCenterLayers == 7) break;
                throw NotFoundException.getNotFoundInstance();
            }
            object2 = point4;
            object3 = point3;
            object4 = point2;
            object = point;
            bl2 = !bl2;
            ++this.nbCenterLayers;
        }
        bl2 = this.nbCenterLayers == 5;
        this.compact = bl2;
        object2 = new ResultPoint((float)((Point)object2).getX() + 0.5f, (float)((Point)object2).getY() - 0.5f);
        object3 = new ResultPoint((float)((Point)object3).getX() + 0.5f, (float)((Point)object3).getY() + 0.5f);
        object4 = new ResultPoint((float)((Point)object4).getX() - 0.5f, (float)((Point)object4).getY() + 0.5f);
        object = new ResultPoint((float)((Point)object).getX() - 0.5f, (float)((Point)object).getY() - 0.5f);
        f2 = this.nbCenterLayers * 2 - 3;
        float f3 = this.nbCenterLayers * 2;
        return Detector.expandSquare(new ResultPoint[]{object2, object3, object4, object}, f2, f3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int getColor(Point point, Point point2) {
        float f2 = Detector.distance(point, point2);
        float f3 = (float)(point2.getX() - point.getX()) / f2;
        float f4 = (float)(point2.getY() - point.getY()) / f2;
        int n2 = 0;
        float f5 = point.getX();
        float f6 = point.getY();
        boolean bl2 = this.image.get(point.getX(), point.getY());
        int n3 = 0;
        while ((float)n3 < f2) {
            int n4 = n2;
            if (this.image.get(MathUtils.round(f5 += f3), MathUtils.round(f6 += f4)) != bl2) {
                n4 = n2 + 1;
            }
            ++n3;
            n2 = n4;
        }
        f6 = (float)n2 / f2;
        if (f6 > 0.1f && f6 < 0.9f) {
            return 0;
        }
        boolean bl3 = f6 <= 0.1f;
        if (bl3 == bl2) {
            return 1;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static int getCorrectedParameterData(long l2, boolean bl2) throws NotFoundException {
        int n2;
        int n3;
        int n4;
        if (bl2) {
            n4 = 7;
            n3 = 2;
        } else {
            n4 = 10;
            n3 = 4;
        }
        int[] nArray = new int[n4];
        for (n2 = n4 - 1; n2 >= 0; l2 >>= 4, --n2) {
            nArray[n2] = (int)l2 & 0xF;
        }
        try {
            new ReedSolomonDecoder(GenericGF.AZTEC_PARAM).decode(nArray, n4 - n3);
            n2 = 0;
            n4 = 0;
        }
        catch (ReedSolomonException reedSolomonException) {
            throw NotFoundException.getNotFoundInstance();
        }
        while (n4 < n3) {
            n2 = (n2 << 4) + nArray[n4];
            ++n4;
        }
        return n2;
    }

    private int getDimension() {
        if (this.compact) {
            return this.nbLayers * 4 + 11;
        }
        if (this.nbLayers <= 4) {
            return this.nbLayers * 4 + 15;
        }
        return this.nbLayers * 4 + ((this.nbLayers - 4) / 8 + 1) * 2 + 15;
    }

    private Point getFirstDifferent(Point point, boolean bl2, int n2, int n3) {
        int n4 = point.getX() + n2;
        int n5 = point.getY() + n3;
        while (this.isValid(n4, n5) && this.image.get(n4, n5) == bl2) {
            n4 += n2;
            n5 += n3;
        }
        int n6 = n4 - n2;
        n4 = n5 - n3;
        n5 = n6;
        while (this.isValid(n5, n4) && this.image.get(n5, n4) == bl2) {
            n5 += n2;
        }
        n5 -= n2;
        n2 = n4;
        while (this.isValid(n5, n2) && this.image.get(n5, n2) == bl2) {
            n2 += n3;
        }
        return new Point(n5, n2 - n3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Point getMatrixCenter() {
        Object object;
        Object object2;
        Object object3;
        int n2;
        int n3;
        Object object4;
        block4: {
            try {
                object4 = new WhiteRectangleDetector(this.image).detect();
            }
            catch (NotFoundException notFoundException) {
                n3 = this.image.getWidth() / 2;
                n2 = this.image.getHeight() / 2;
                object3 = this.getFirstDifferent(new Point(n3 + 7, n2 - 7), false, 1, -1).toResultPoint();
                object2 = this.getFirstDifferent(new Point(n3 + 7, n2 + 7), false, 1, 1).toResultPoint();
                object = this.getFirstDifferent(new Point(n3 - 7, n2 + 7), false, -1, 1).toResultPoint();
                object4 = this.getFirstDifferent(new Point(n3 - 7, n2 - 7), false, -1, -1).toResultPoint();
                break block4;
            }
            object3 = object4[0];
            object2 = object4[1];
            object = object4[2];
            object4 = object4[3];
        }
        n3 = MathUtils.round((((ResultPoint)object3).getX() + ((ResultPoint)object4).getX() + ((ResultPoint)object2).getX() + ((ResultPoint)object).getX()) / 4.0f);
        n2 = MathUtils.round((((ResultPoint)object3).getY() + ((ResultPoint)object4).getY() + ((ResultPoint)object2).getY() + ((ResultPoint)object).getY()) / 4.0f);
        try {
            object4 = new WhiteRectangleDetector(this.image, 15, n3, n2).detect();
        }
        catch (NotFoundException notFoundException) {
            object3 = this.getFirstDifferent(new Point(n3 + 7, n2 - 7), false, 1, -1).toResultPoint();
            object2 = this.getFirstDifferent(new Point(n3 + 7, n2 + 7), false, 1, 1).toResultPoint();
            object = this.getFirstDifferent(new Point(n3 - 7, n2 + 7), false, -1, 1).toResultPoint();
            object4 = this.getFirstDifferent(new Point(n3 - 7, n2 - 7), false, -1, -1).toResultPoint();
            return new Point(MathUtils.round((((ResultPoint)object3).getX() + ((ResultPoint)object4).getX() + ((ResultPoint)object2).getX() + ((ResultPoint)object).getX()) / 4.0f), MathUtils.round((((ResultPoint)object3).getY() + ((ResultPoint)object4).getY() + ((ResultPoint)object2).getY() + ((ResultPoint)object).getY()) / 4.0f));
        }
        object3 = object4[0];
        object2 = object4[1];
        object = object4[2];
        object4 = object4[3];
        return new Point(MathUtils.round((((ResultPoint)object3).getX() + ((ResultPoint)object4).getX() + ((ResultPoint)object2).getX() + ((ResultPoint)object).getX()) / 4.0f), MathUtils.round((((ResultPoint)object3).getY() + ((ResultPoint)object4).getY() + ((ResultPoint)object2).getY() + ((ResultPoint)object).getY()) / 4.0f));
    }

    private ResultPoint[] getMatrixCornerPoints(ResultPoint[] resultPointArray) {
        return Detector.expandSquare(resultPointArray, this.nbCenterLayers * 2, this.getDimension());
    }

    private static int getRotation(int[] nArray, int n2) throws NotFoundException {
        int n3 = 0;
        for (int n4 : nArray) {
            n3 = (n3 << 3) + ((n4 >> n2 - 2 << 1) + (n4 & 1));
        }
        for (n2 = 0; n2 < 4; ++n2) {
            if (Integer.bitCount(EXPECTED_CORNER_BITS[n2] ^ ((n3 & 1) << 11) + (n3 >> 1)) > 2) continue;
            return n2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private boolean isValid(int n2, int n3) {
        return n2 >= 0 && n2 < this.image.getWidth() && n3 > 0 && n3 < this.image.getHeight();
    }

    private boolean isValid(ResultPoint resultPoint) {
        return this.isValid(MathUtils.round(resultPoint.getX()), MathUtils.round(resultPoint.getY()));
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isWhiteOrBlackRectangle(Point point, Point point2, Point point3, Point point4) {
        point = new Point(point.getX() - 3, point.getY() + 3);
        point2 = new Point(point2.getX() - 3, point2.getY() - 3);
        point3 = new Point(point3.getX() + 3, point3.getY() - 3);
        int n2 = this.getColor(point4 = new Point(point4.getX() + 3, point4.getY() + 3), point);
        return n2 != 0 && this.getColor(point, point2) == n2 && this.getColor(point2, point3) == n2 && this.getColor(point3, point4) == n2;
    }

    private BitMatrix sampleGrid(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) throws NotFoundException {
        GridSampler gridSampler = GridSampler.getInstance();
        int n2 = this.getDimension();
        float f2 = (float)n2 / 2.0f - (float)this.nbCenterLayers;
        float f3 = (float)n2 / 2.0f + (float)this.nbCenterLayers;
        return gridSampler.sampleGrid(bitMatrix, n2, n2, f2, f2, f3, f2, f3, f3, f2, f3, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint4.getX(), resultPoint4.getY());
    }

    private int sampleLine(ResultPoint resultPoint, ResultPoint resultPoint2, int n2) {
        int n3 = 0;
        float f2 = Detector.distance(resultPoint, resultPoint2);
        float f3 = f2 / (float)n2;
        float f4 = resultPoint.getX();
        float f5 = resultPoint.getY();
        float f6 = (resultPoint2.getX() - resultPoint.getX()) * f3 / f2;
        f2 = (resultPoint2.getY() - resultPoint.getY()) * f3 / f2;
        for (int i2 = 0; i2 < n2; ++i2) {
            int n4 = n3;
            if (this.image.get(MathUtils.round((float)i2 * f6 + f4), MathUtils.round((float)i2 * f2 + f5))) {
                n4 = n3 | 1 << n2 - i2 - 1;
            }
            n3 = n4;
        }
        return n3;
    }

    public AztecDetectorResult detect() throws NotFoundException {
        return this.detect(false);
    }

    public AztecDetectorResult detect(boolean bl2) throws NotFoundException {
        ResultPoint[] resultPointArray = this.getBullsEyeCorners(this.getMatrixCenter());
        if (bl2) {
            ResultPoint resultPoint = resultPointArray[0];
            resultPointArray[0] = resultPointArray[2];
            resultPointArray[2] = resultPoint;
        }
        this.extractParameters(resultPointArray);
        return new AztecDetectorResult(this.sampleGrid(this.image, resultPointArray[this.shift % 4], resultPointArray[(this.shift + 1) % 4], resultPointArray[(this.shift + 2) % 4], resultPointArray[(this.shift + 3) % 4]), this.getMatrixCornerPoints(resultPointArray), this.compact, this.nbDataBlocks, this.nbLayers);
    }

    static final class Point {
        private final int x;
        private final int y;

        Point(int n2, int n3) {
            this.x = n2;
            this.y = n3;
        }

        int getX() {
            return this.x;
        }

        int getY() {
            return this.y;
        }

        ResultPoint toResultPoint() {
            return new ResultPoint(this.getX(), this.getY());
        }

        public String toString() {
            return "<" + this.x + ' ' + this.y + '>';
        }
    }
}

