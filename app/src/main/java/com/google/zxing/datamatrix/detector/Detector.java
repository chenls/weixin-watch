/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class Detector {
    private final BitMatrix image;
    private final WhiteRectangleDetector rectangleDetector;

    public Detector(BitMatrix bitMatrix) throws NotFoundException {
        this.image = bitMatrix;
        this.rectangleDetector = new WhiteRectangleDetector(bitMatrix);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private ResultPoint correctTopRight(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int n2) {
        float f2 = (float)Detector.distance(resultPoint, resultPoint2) / (float)n2;
        int n3 = Detector.distance(resultPoint3, resultPoint4);
        float f3 = (resultPoint4.getX() - resultPoint3.getX()) / (float)n3;
        float f4 = (resultPoint4.getY() - resultPoint3.getY()) / (float)n3;
        ResultPoint resultPoint5 = new ResultPoint(resultPoint4.getX() + f2 * f3, resultPoint4.getY() + f2 * f4);
        f2 = (float)Detector.distance(resultPoint, resultPoint3) / (float)n2;
        n2 = Detector.distance(resultPoint2, resultPoint4);
        f3 = (resultPoint4.getX() - resultPoint2.getX()) / (float)n2;
        f4 = (resultPoint4.getY() - resultPoint2.getY()) / (float)n2;
        resultPoint4 = new ResultPoint(resultPoint4.getX() + f2 * f3, resultPoint4.getY() + f2 * f4);
        if (!this.isValid(resultPoint5)) {
            if (!this.isValid(resultPoint4)) return null;
            return resultPoint4;
        }
        resultPoint = resultPoint5;
        if (!this.isValid(resultPoint4)) return resultPoint;
        resultPoint = resultPoint5;
        if (Math.abs(this.transitionsBetween(resultPoint3, resultPoint5).getTransitions() - this.transitionsBetween(resultPoint2, resultPoint5).getTransitions()) <= Math.abs(this.transitionsBetween(resultPoint3, resultPoint4).getTransitions() - this.transitionsBetween(resultPoint2, resultPoint4).getTransitions())) return resultPoint;
        return resultPoint4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private ResultPoint correctTopRightRectangular(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int n2, int n3) {
        float f2 = (float)Detector.distance(resultPoint, resultPoint2) / (float)n2;
        int n4 = Detector.distance(resultPoint3, resultPoint4);
        float f3 = (resultPoint4.getX() - resultPoint3.getX()) / (float)n4;
        float f4 = (resultPoint4.getY() - resultPoint3.getY()) / (float)n4;
        ResultPoint resultPoint5 = new ResultPoint(resultPoint4.getX() + f2 * f3, resultPoint4.getY() + f2 * f4);
        f2 = (float)Detector.distance(resultPoint, resultPoint3) / (float)n3;
        n4 = Detector.distance(resultPoint2, resultPoint4);
        f3 = (resultPoint4.getX() - resultPoint2.getX()) / (float)n4;
        f4 = (resultPoint4.getY() - resultPoint2.getY()) / (float)n4;
        resultPoint = new ResultPoint(resultPoint4.getX() + f2 * f3, resultPoint4.getY() + f2 * f4);
        if (!this.isValid(resultPoint5)) {
            if (this.isValid(resultPoint)) return resultPoint;
            return null;
        }
        if (!this.isValid(resultPoint)) {
            return resultPoint5;
        }
        if (Math.abs(n2 - this.transitionsBetween(resultPoint3, resultPoint5).getTransitions()) + Math.abs(n3 - this.transitionsBetween(resultPoint2, resultPoint5).getTransitions()) <= Math.abs(n2 - this.transitionsBetween(resultPoint3, resultPoint).getTransitions()) + Math.abs(n3 - this.transitionsBetween(resultPoint2, resultPoint).getTransitions())) return resultPoint5;
        return resultPoint;
    }

    private static int distance(ResultPoint resultPoint, ResultPoint resultPoint2) {
        return MathUtils.round(ResultPoint.distance(resultPoint, resultPoint2));
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void increment(Map<ResultPoint, Integer> map, ResultPoint resultPoint) {
        Integer n2 = map.get(resultPoint);
        int n3 = n2 == null ? 1 : n2 + 1;
        map.put(resultPoint, n3);
    }

    private boolean isValid(ResultPoint resultPoint) {
        return resultPoint.getX() >= 0.0f && resultPoint.getX() < (float)this.image.getWidth() && resultPoint.getY() > 0.0f && resultPoint.getY() < (float)this.image.getHeight();
    }

    private static BitMatrix sampleGrid(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int n2, int n3) throws NotFoundException {
        return GridSampler.getInstance().sampleGrid(bitMatrix, n2, n3, 0.5f, 0.5f, (float)n2 - 0.5f, 0.5f, (float)n2 - 0.5f, (float)n3 - 0.5f, 0.5f, (float)n3 - 0.5f, resultPoint.getX(), resultPoint.getY(), resultPoint4.getX(), resultPoint4.getY(), resultPoint3.getX(), resultPoint3.getY(), resultPoint2.getX(), resultPoint2.getY());
    }

    /*
     * Enabled aggressive block sorting
     */
    private ResultPointsAndTransitions transitionsBetween(ResultPoint resultPoint, ResultPoint resultPoint2) {
        int n2 = (int)resultPoint.getX();
        int n3 = (int)resultPoint.getY();
        int n4 = (int)resultPoint2.getX();
        int n5 = (int)resultPoint2.getY();
        boolean bl2 = Math.abs(n5 - n3) > Math.abs(n4 - n2);
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        int n9 = n5;
        if (bl2) {
            n6 = n3;
            n7 = n2;
            n9 = n4;
            n8 = n5;
        }
        int n10 = Math.abs(n8 - n6);
        int n11 = Math.abs(n9 - n7);
        int n12 = -n10 / 2;
        n2 = n7 < n9 ? 1 : -1;
        n3 = n6 < n8 ? 1 : -1;
        int n13 = 0;
        BitMatrix bitMatrix = this.image;
        n4 = bl2 ? n7 : n6;
        n5 = bl2 ? n6 : n7;
        boolean bl3 = bitMatrix.get(n4, n5);
        n4 = n6;
        n6 = n7;
        n7 = n4;
        n4 = n13;
        while (true) {
            boolean bl4;
            block7: {
                block8: {
                    block6: {
                        n5 = n4;
                        if (n7 == n8) break block6;
                        bitMatrix = this.image;
                        n5 = bl2 ? n6 : n7;
                        n13 = bl2 ? n7 : n6;
                        boolean bl5 = bitMatrix.get(n5, n13);
                        bl4 = bl3;
                        n5 = n4;
                        if (bl5 != bl3) {
                            n5 = n4 + 1;
                            bl4 = bl5;
                        }
                        n4 = n12 += n11;
                        n13 = n6;
                        if (n12 <= 0) break block7;
                        if (n6 != n9) break block8;
                    }
                    return new ResultPointsAndTransitions(resultPoint, resultPoint2, n5);
                }
                n13 = n6 + n2;
                n4 = n12 - n10;
            }
            n7 += n3;
            n12 = n4;
            bl3 = bl4;
            n4 = n5;
            n6 = n13;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public DetectorResult detect() throws NotFoundException {
        Object object = this.rectangleDetector.detect();
        ResultPoint resultPoint = object[0];
        ResultPoint resultPoint2 = object[1];
        ResultPoint resultPoint3 = object[2];
        ResultPoint resultPoint4 = object[3];
        Object object2 = new ArrayList<ResultPointsAndTransitions>(4);
        object2.add(this.transitionsBetween(resultPoint, resultPoint2));
        object2.add(this.transitionsBetween(resultPoint, resultPoint3));
        object2.add(this.transitionsBetween(resultPoint2, resultPoint4));
        object2.add(this.transitionsBetween(resultPoint3, resultPoint4));
        Collections.sort(object2, new ResultPointsAndTransitionsComparator());
        object = (ResultPointsAndTransitions)object2.get(0);
        object2 = (ResultPointsAndTransitions)object2.get(1);
        HashMap<ResultPoint, Integer> hashMap = new HashMap<ResultPoint, Integer>();
        Detector.increment(hashMap, ((ResultPointsAndTransitions)object).getFrom());
        Detector.increment(hashMap, ((ResultPointsAndTransitions)object).getTo());
        Detector.increment(hashMap, ((ResultPointsAndTransitions)object2).getFrom());
        Detector.increment(hashMap, ((ResultPointsAndTransitions)object2).getTo());
        object2 = null;
        Object object3 = null;
        Object object4 = null;
        for (Map.Entry entry : hashMap.entrySet()) {
            object = (ResultPoint)entry.getKey();
            if ((Integer)entry.getValue() == 2) {
                object3 = object;
                continue;
            }
            if (object2 == null) {
                object2 = object;
                continue;
            }
            object4 = object;
        }
        if (object2 == null || object3 == null || object4 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        object = new ResultPoint[]{object2, object3, object4};
        ResultPoint.orderBestPatterns((ResultPoint[])object);
        object3 = object[0];
        Object object5 = object[1];
        Object object6 = object[2];
        object = !hashMap.containsKey(resultPoint) ? resultPoint : (!hashMap.containsKey(resultPoint2) ? resultPoint2 : (!hashMap.containsKey(resultPoint3) ? resultPoint3 : resultPoint4));
        int n2 = this.transitionsBetween((ResultPoint)object6, (ResultPoint)object).getTransitions();
        int n3 = this.transitionsBetween((ResultPoint)object3, (ResultPoint)object).getTransitions();
        int n4 = n2;
        if ((n2 & 1) == 1) {
            n4 = n2 + 1;
        }
        n2 = n4 + 2;
        n4 = n3;
        if ((n3 & 1) == 1) {
            n4 = n3 + 1;
        }
        if (n2 * 4 >= (n4 += 2) * 7 || n4 * 4 >= n2 * 7) {
            object2 = object4 = this.correctTopRightRectangular((ResultPoint)object5, (ResultPoint)object3, (ResultPoint)object6, (ResultPoint)object, n2, n4);
            if (object4 == null) {
                object2 = object;
            }
            n3 = this.transitionsBetween((ResultPoint)object6, (ResultPoint)object2).getTransitions();
            n2 = this.transitionsBetween((ResultPoint)object3, (ResultPoint)object2).getTransitions();
            n4 = n3;
            if ((n3 & 1) == 1) {
                n4 = n3 + 1;
            }
            n3 = n2;
            if ((n2 & 1) == 1) {
                n3 = n2 + 1;
            }
            object = Detector.sampleGrid(this.image, (ResultPoint)object6, (ResultPoint)object5, (ResultPoint)object3, (ResultPoint)object2, n4, n3);
            return new DetectorResult((BitMatrix)object, new ResultPoint[]{object6, object5, object3, object2});
        } else {
            object2 = object4 = this.correctTopRight((ResultPoint)object5, (ResultPoint)object3, (ResultPoint)object6, (ResultPoint)object, Math.min(n4, n2));
            if (object4 == null) {
                object2 = object;
            }
            n4 = n3 = Math.max(this.transitionsBetween((ResultPoint)object6, (ResultPoint)object2).getTransitions(), this.transitionsBetween((ResultPoint)object3, (ResultPoint)object2).getTransitions()) + 1;
            if ((n3 & 1) == 1) {
                n4 = n3 + 1;
            }
            object = Detector.sampleGrid(this.image, (ResultPoint)object6, (ResultPoint)object5, (ResultPoint)object3, (ResultPoint)object2, n4, n4);
        }
        return new DetectorResult((BitMatrix)object, new ResultPoint[]{object6, object5, object3, object2});
    }

    private static final class ResultPointsAndTransitions {
        private final ResultPoint from;
        private final ResultPoint to;
        private final int transitions;

        private ResultPointsAndTransitions(ResultPoint resultPoint, ResultPoint resultPoint2, int n2) {
            this.from = resultPoint;
            this.to = resultPoint2;
            this.transitions = n2;
        }

        ResultPoint getFrom() {
            return this.from;
        }

        ResultPoint getTo() {
            return this.to;
        }

        public int getTransitions() {
            return this.transitions;
        }

        public String toString() {
            return this.from + "/" + this.to + '/' + this.transitions;
        }
    }

    private static final class ResultPointsAndTransitionsComparator
    implements Comparator<ResultPointsAndTransitions>,
    Serializable {
        private ResultPointsAndTransitionsComparator() {
        }

        @Override
        public int compare(ResultPointsAndTransitions resultPointsAndTransitions, ResultPointsAndTransitions resultPointsAndTransitions2) {
            return resultPointsAndTransitions.getTransitions() - resultPointsAndTransitions2.getTransitions();
        }
    }
}

