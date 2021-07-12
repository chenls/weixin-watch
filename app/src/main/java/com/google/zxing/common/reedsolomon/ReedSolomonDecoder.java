/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common.reedsolomon;

import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.GenericGFPoly;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

public final class ReedSolomonDecoder {
    private final GenericGF field;

    public ReedSolomonDecoder(GenericGF genericGF) {
        this.field = genericGF;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int[] findErrorLocations(GenericGFPoly object) throws ReedSolomonException {
        int n2 = ((GenericGFPoly)object).getDegree();
        if (n2 == 1) {
            int[] nArray = new int[]{((GenericGFPoly)object).getCoefficient(1)};
            return nArray;
        }
        int[] nArray = new int[n2];
        int n3 = 0;
        for (int i2 = 1; i2 < this.field.getSize() && n3 < n2; ++i2) {
            int n4 = n3;
            if (((GenericGFPoly)object).evaluateAt(i2) == 0) {
                nArray[n3] = this.field.inverse(i2);
                n4 = n3 + 1;
            }
            n3 = n4;
        }
        object = nArray;
        if (n3 == n2) return object;
        throw new ReedSolomonException("Error locator degree does not match number of roots");
    }

    /*
     * Enabled aggressive block sorting
     */
    private int[] findErrorMagnitudes(GenericGFPoly genericGFPoly, int[] nArray) {
        int n2 = nArray.length;
        int[] nArray2 = new int[n2];
        int n3 = 0;
        while (n3 < n2) {
            int n4 = this.field.inverse(nArray[n3]);
            int n5 = 1;
            for (int i2 = 0; i2 < n2; ++i2) {
                int n6 = n5;
                if (n3 != i2) {
                    n6 = this.field.multiply(nArray[i2], n4);
                    n6 = (n6 & 1) == 0 ? (n6 |= 1) : (n6 &= 0xFFFFFFFE);
                    n6 = this.field.multiply(n5, n6);
                }
                n5 = n6;
            }
            nArray2[n3] = this.field.multiply(genericGFPoly.evaluateAt(n4), this.field.inverse(n5));
            if (this.field.getGeneratorBase() != 0) {
                nArray2[n3] = this.field.multiply(nArray2[n3], n4);
            }
            ++n3;
        }
        return nArray2;
    }

    private GenericGFPoly[] runEuclideanAlgorithm(GenericGFPoly genericGFPoly, GenericGFPoly genericGFPoly2, int n2) throws ReedSolomonException {
        GenericGFPoly genericGFPoly3;
        block5: {
            GenericGFPoly genericGFPoly4;
            GenericGFPoly genericGFPoly5 = genericGFPoly;
            genericGFPoly3 = genericGFPoly2;
            if (genericGFPoly.getDegree() < genericGFPoly2.getDegree()) {
                genericGFPoly3 = genericGFPoly;
                genericGFPoly5 = genericGFPoly2;
            }
            GenericGFPoly genericGFPoly6 = this.field.getZero();
            genericGFPoly2 = this.field.getOne();
            do {
                GenericGFPoly genericGFPoly7 = genericGFPoly6;
                if (genericGFPoly3.getDegree() < n2 / 2) break block5;
                genericGFPoly4 = genericGFPoly3;
                genericGFPoly6 = genericGFPoly2;
                if (genericGFPoly4.isZero()) {
                    throw new ReedSolomonException("r_{i-1} was zero");
                }
                genericGFPoly = genericGFPoly5;
                genericGFPoly2 = this.field.getZero();
                int n3 = genericGFPoly4.getCoefficient(genericGFPoly4.getDegree());
                n3 = this.field.inverse(n3);
                while (genericGFPoly.getDegree() >= genericGFPoly4.getDegree() && !genericGFPoly.isZero()) {
                    int n4 = genericGFPoly.getDegree() - genericGFPoly4.getDegree();
                    int n5 = this.field.multiply(genericGFPoly.getCoefficient(genericGFPoly.getDegree()), n3);
                    genericGFPoly2 = genericGFPoly2.addOrSubtract(this.field.buildMonomial(n4, n5));
                    genericGFPoly = genericGFPoly.addOrSubtract(genericGFPoly4.multiplyByMonomial(n4, n5));
                }
                genericGFPoly2 = genericGFPoly2.multiply(genericGFPoly6).addOrSubtract(genericGFPoly7);
                genericGFPoly3 = genericGFPoly;
                genericGFPoly5 = genericGFPoly4;
            } while (genericGFPoly.getDegree() < genericGFPoly4.getDegree());
            throw new IllegalStateException("Division algorithm failed to reduce polynomial?");
        }
        n2 = genericGFPoly2.getCoefficient(0);
        if (n2 == 0) {
            throw new ReedSolomonException("sigmaTilde(0) was zero");
        }
        n2 = this.field.inverse(n2);
        return new GenericGFPoly[]{genericGFPoly2.multiply(n2), genericGFPoly3.multiply(n2)};
    }

    /*
     * Enabled aggressive block sorting
     */
    public void decode(int[] nArray, int n2) throws ReedSolomonException {
        int n3;
        GenericGFPoly genericGFPoly = new GenericGFPoly(this.field, nArray);
        int[] nArray2 = new int[n2];
        boolean bl2 = true;
        for (n3 = 0; n3 < n2; ++n3) {
            int n4;
            nArray2[nArray2.length - 1 - n3] = n4 = genericGFPoly.evaluateAt(this.field.exp(this.field.getGeneratorBase() + n3));
            if (n4 == 0) continue;
            bl2 = false;
        }
        if (!bl2) {
            GenericGFPoly genericGFPoly2 = new GenericGFPoly(this.field, nArray2);
            GenericGFPoly[] genericGFPolyArray = this.runEuclideanAlgorithm(this.field.buildMonomial(n2, 1), genericGFPoly2, n2);
            GenericGFPoly genericGFPoly3 = genericGFPolyArray[0];
            GenericGFPoly genericGFPoly4 = genericGFPolyArray[1];
            int[] nArray3 = this.findErrorLocations(genericGFPoly3);
            int[] nArray4 = this.findErrorMagnitudes(genericGFPoly4, nArray3);
            for (n2 = 0; n2 < nArray3.length; ++n2) {
                n3 = nArray.length - 1 - this.field.log(nArray3[n2]);
                if (n3 < 0) {
                    throw new ReedSolomonException("Bad error location");
                }
                nArray[n3] = GenericGF.addOrSubtract(nArray[n3], nArray4[n2]);
            }
        }
    }
}

