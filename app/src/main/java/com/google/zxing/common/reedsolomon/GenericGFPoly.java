/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common.reedsolomon;

import com.google.zxing.common.reedsolomon.GenericGF;

final class GenericGFPoly {
    private final int[] coefficients;
    private final GenericGF field;

    GenericGFPoly(GenericGF genericGF, int[] nArray) {
        if (nArray.length == 0) {
            throw new IllegalArgumentException();
        }
        this.field = genericGF;
        int n2 = nArray.length;
        if (n2 > 1 && nArray[0] == 0) {
            int n3;
            for (n3 = 1; n3 < n2 && nArray[n3] == 0; ++n3) {
            }
            if (n3 == n2) {
                this.coefficients = new int[]{0};
                return;
            }
            this.coefficients = new int[n2 - n3];
            System.arraycopy(nArray, n3, this.coefficients, 0, this.coefficients.length);
            return;
        }
        this.coefficients = nArray;
    }

    GenericGFPoly addOrSubtract(GenericGFPoly object) {
        int[] nArray;
        if (!this.field.equals(((GenericGFPoly)object).field)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (this.isZero()) {
            return object;
        }
        if (((GenericGFPoly)object).isZero()) {
            return this;
        }
        int[] nArray2 = this.coefficients;
        int[] nArray3 = nArray = ((GenericGFPoly)object).coefficients;
        object = nArray2;
        if (nArray2.length > nArray.length) {
            object = nArray;
            nArray3 = nArray2;
        }
        nArray2 = new int[nArray3.length];
        int n2 = nArray3.length - ((Object)object).length;
        System.arraycopy(nArray3, 0, nArray2, 0, n2);
        for (int i2 = n2; i2 < nArray3.length; ++i2) {
            nArray2[i2] = GenericGF.addOrSubtract((int)object[i2 - n2], nArray3[i2]);
        }
        return new GenericGFPoly(this.field, nArray2);
    }

    GenericGFPoly[] divide(GenericGFPoly genericGFPoly) {
        if (!this.field.equals(genericGFPoly.field)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (genericGFPoly.isZero()) {
            throw new IllegalArgumentException("Divide by 0");
        }
        GenericGFPoly genericGFPoly2 = this.field.getZero();
        GenericGFPoly genericGFPoly3 = this;
        int n2 = genericGFPoly.getCoefficient(genericGFPoly.getDegree());
        n2 = this.field.inverse(n2);
        while (genericGFPoly3.getDegree() >= genericGFPoly.getDegree() && !genericGFPoly3.isZero()) {
            int n3 = genericGFPoly3.getDegree() - genericGFPoly.getDegree();
            int n4 = this.field.multiply(genericGFPoly3.getCoefficient(genericGFPoly3.getDegree()), n2);
            GenericGFPoly genericGFPoly4 = genericGFPoly.multiplyByMonomial(n3, n4);
            genericGFPoly2 = genericGFPoly2.addOrSubtract(this.field.buildMonomial(n3, n4));
            genericGFPoly3 = genericGFPoly3.addOrSubtract(genericGFPoly4);
        }
        return new GenericGFPoly[]{genericGFPoly2, genericGFPoly3};
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    int evaluateAt(int n2) {
        int n3;
        int n4 = 0;
        if (n2 == 0) {
            return this.getCoefficient(0);
        }
        int n5 = this.coefficients.length;
        if (n2 == 1) {
            n2 = 0;
            int[] nArray = this.coefficients;
            int n6 = nArray.length;
            while (true) {
                n3 = n2;
                if (n4 >= n6) return n3;
                n2 = GenericGF.addOrSubtract(n2, nArray[n4]);
                ++n4;
            }
        }
        n4 = this.coefficients[0];
        int n7 = 1;
        while (true) {
            n3 = n4;
            if (n7 >= n5) return n3;
            n4 = GenericGF.addOrSubtract(this.field.multiply(n2, n4), this.coefficients[n7]);
            ++n7;
        }
    }

    int getCoefficient(int n2) {
        return this.coefficients[this.coefficients.length - 1 - n2];
    }

    int[] getCoefficients() {
        return this.coefficients;
    }

    int getDegree() {
        return this.coefficients.length - 1;
    }

    boolean isZero() {
        boolean bl2 = false;
        if (this.coefficients[0] == 0) {
            bl2 = true;
        }
        return bl2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    GenericGFPoly multiply(int n2) {
        if (n2 == 0) {
            return this.field.getZero();
        }
        Object object = this;
        if (n2 == 1) return object;
        int n3 = this.coefficients.length;
        object = new int[n3];
        int n4 = 0;
        while (n4 < n3) {
            object[n4] = this.field.multiply(this.coefficients[n4], n2);
            ++n4;
        }
        return new GenericGFPoly(this.field, (int[])object);
    }

    GenericGFPoly multiply(GenericGFPoly object) {
        if (!this.field.equals(((GenericGFPoly)object).field)) {
            throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
        }
        if (this.isZero() || ((GenericGFPoly)object).isZero()) {
            return this.field.getZero();
        }
        int[] nArray = this.coefficients;
        int n2 = nArray.length;
        object = ((GenericGFPoly)object).coefficients;
        int n3 = ((Object)object).length;
        int[] nArray2 = new int[n2 + n3 - 1];
        for (int i2 = 0; i2 < n2; ++i2) {
            int n4 = nArray[i2];
            for (int i3 = 0; i3 < n3; ++i3) {
                nArray2[i2 + i3] = GenericGF.addOrSubtract(nArray2[i2 + i3], this.field.multiply(n4, (int)object[i3]));
            }
        }
        return new GenericGFPoly(this.field, nArray2);
    }

    GenericGFPoly multiplyByMonomial(int n2, int n3) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        if (n3 == 0) {
            return this.field.getZero();
        }
        int n4 = this.coefficients.length;
        int[] nArray = new int[n4 + n2];
        for (n2 = 0; n2 < n4; ++n2) {
            nArray[n2] = this.field.multiply(this.coefficients[n2], n3);
        }
        return new GenericGFPoly(this.field, nArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.getDegree() * 8);
        int n2 = this.getDegree();
        while (n2 >= 0) {
            int n3 = this.getCoefficient(n2);
            if (n3 != 0) {
                int n4;
                if (n3 < 0) {
                    stringBuilder.append(" - ");
                    n4 = -n3;
                } else {
                    n4 = n3;
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(" + ");
                        n4 = n3;
                    }
                }
                if (n2 == 0 || n4 != 1) {
                    if ((n4 = this.field.log(n4)) == 0) {
                        stringBuilder.append('1');
                    } else if (n4 == 1) {
                        stringBuilder.append('a');
                    } else {
                        stringBuilder.append("a^");
                        stringBuilder.append(n4);
                    }
                }
                if (n2 != 0) {
                    if (n2 == 1) {
                        stringBuilder.append('x');
                    } else {
                        stringBuilder.append("x^");
                        stringBuilder.append(n2);
                    }
                }
            }
            --n2;
        }
        return stringBuilder.toString();
    }
}

