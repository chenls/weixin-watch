/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.decoder;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

final class BoundingBox {
    private ResultPoint bottomLeft;
    private ResultPoint bottomRight;
    private BitMatrix image;
    private int maxX;
    private int maxY;
    private int minX;
    private int minY;
    private ResultPoint topLeft;
    private ResultPoint topRight;

    BoundingBox(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) throws NotFoundException {
        if (resultPoint == null && resultPoint3 == null || resultPoint2 == null && resultPoint4 == null || resultPoint != null && resultPoint2 == null || resultPoint3 != null && resultPoint4 == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        this.init(bitMatrix, resultPoint, resultPoint2, resultPoint3, resultPoint4);
    }

    BoundingBox(BoundingBox boundingBox) {
        this.init(boundingBox.image, boundingBox.topLeft, boundingBox.bottomLeft, boundingBox.topRight, boundingBox.bottomRight);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calculateMinMaxValues() {
        if (this.topLeft == null) {
            this.topLeft = new ResultPoint(0.0f, this.topRight.getY());
            this.bottomLeft = new ResultPoint(0.0f, this.bottomRight.getY());
        } else if (this.topRight == null) {
            this.topRight = new ResultPoint(this.image.getWidth() - 1, this.topLeft.getY());
            this.bottomRight = new ResultPoint(this.image.getWidth() - 1, this.bottomLeft.getY());
        }
        this.minX = (int)Math.min(this.topLeft.getX(), this.bottomLeft.getX());
        this.maxX = (int)Math.max(this.topRight.getX(), this.bottomRight.getX());
        this.minY = (int)Math.min(this.topLeft.getY(), this.topRight.getY());
        this.maxY = (int)Math.max(this.bottomLeft.getY(), this.bottomRight.getY());
    }

    private void init(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) {
        this.image = bitMatrix;
        this.topLeft = resultPoint;
        this.bottomLeft = resultPoint2;
        this.topRight = resultPoint3;
        this.bottomRight = resultPoint4;
        this.calculateMinMaxValues();
    }

    static BoundingBox merge(BoundingBox boundingBox, BoundingBox boundingBox2) throws NotFoundException {
        if (boundingBox == null) {
            return boundingBox2;
        }
        if (boundingBox2 == null) {
            return boundingBox;
        }
        return new BoundingBox(boundingBox.image, boundingBox.topLeft, boundingBox.bottomLeft, boundingBox2.topRight, boundingBox2.bottomRight);
    }

    /*
     * Enabled aggressive block sorting
     */
    BoundingBox addMissingRows(int n2, int n3, boolean bl2) throws NotFoundException {
        ResultPoint resultPoint = this.topLeft;
        ResultPoint resultPoint2 = this.bottomLeft;
        ResultPoint resultPoint3 = this.topRight;
        ResultPoint resultPoint4 = this.bottomRight;
        ResultPoint resultPoint5 = resultPoint;
        ResultPoint resultPoint6 = resultPoint3;
        if (n2 > 0) {
            int n4;
            resultPoint5 = bl2 ? this.topLeft : this.topRight;
            n2 = n4 = (int)resultPoint5.getY() - n2;
            if (n4 < 0) {
                n2 = 0;
            }
            resultPoint5 = new ResultPoint(resultPoint5.getX(), n2);
            if (bl2) {
                resultPoint6 = resultPoint3;
            } else {
                resultPoint6 = resultPoint5;
                resultPoint5 = resultPoint;
            }
        }
        resultPoint = resultPoint2;
        resultPoint3 = resultPoint4;
        if (n3 > 0) {
            resultPoint = bl2 ? this.bottomLeft : this.bottomRight;
            n2 = n3 = (int)resultPoint.getY() + n3;
            if (n3 >= this.image.getHeight()) {
                n2 = this.image.getHeight() - 1;
            }
            resultPoint = new ResultPoint(resultPoint.getX(), n2);
            if (bl2) {
                resultPoint3 = resultPoint4;
            } else {
                resultPoint3 = resultPoint;
                resultPoint = resultPoint2;
            }
        }
        this.calculateMinMaxValues();
        return new BoundingBox(this.image, resultPoint5, resultPoint, resultPoint6, resultPoint3);
    }

    ResultPoint getBottomLeft() {
        return this.bottomLeft;
    }

    ResultPoint getBottomRight() {
        return this.bottomRight;
    }

    int getMaxX() {
        return this.maxX;
    }

    int getMaxY() {
        return this.maxY;
    }

    int getMinX() {
        return this.minX;
    }

    int getMinY() {
        return this.minY;
    }

    ResultPoint getTopLeft() {
        return this.topLeft;
    }

    ResultPoint getTopRight() {
        return this.topRight;
    }
}

