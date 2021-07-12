/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.ChecksumException;
import com.google.zxing.pdf417.decoder.ec.ModulusGF;
import com.google.zxing.pdf417.decoder.ec.ModulusPoly;

public final class ErrorCorrection {
    private final ModulusGF field = ModulusGF.PDF417_GF;

    private int[] findErrorLocations(ModulusPoly modulusPoly) throws ChecksumException {
        int n2 = modulusPoly.getDegree();
        int[] nArray = new int[n2];
        int n3 = 0;
        for (int i2 = 1; i2 < this.field.getSize() && n3 < n2; ++i2) {
            int n4 = n3;
            if (modulusPoly.evaluateAt(i2) == 0) {
                nArray[n3] = this.field.inverse(i2);
                n4 = n3 + 1;
            }
            n3 = n4;
        }
        if (n3 != n2) {
            throw ChecksumException.getChecksumInstance();
        }
        return nArray;
    }

    private int[] findErrorMagnitudes(ModulusPoly modulusPoly, ModulusPoly modulusPoly2, int[] nArray) {
        int n2;
        int n3 = modulusPoly2.getDegree();
        int[] nArray2 = new int[n3];
        for (n2 = 1; n2 <= n3; ++n2) {
            nArray2[n3 - n2] = this.field.multiply(n2, modulusPoly2.getCoefficient(n2));
        }
        modulusPoly2 = new ModulusPoly(this.field, nArray2);
        n3 = nArray.length;
        nArray2 = new int[n3];
        for (n2 = 0; n2 < n3; ++n2) {
            int n4 = this.field.inverse(nArray[n2]);
            int n5 = this.field.subtract(0, modulusPoly.evaluateAt(n4));
            n4 = this.field.inverse(modulusPoly2.evaluateAt(n4));
            nArray2[n2] = this.field.multiply(n5, n4);
        }
        return nArray2;
    }

    private ModulusPoly[] runEuclideanAlgorithm(ModulusPoly modulusPoly, ModulusPoly modulusPoly2, int n2) throws ChecksumException {
        ModulusPoly modulusPoly3 = modulusPoly;
        ModulusPoly modulusPoly4 = modulusPoly2;
        if (modulusPoly.getDegree() < modulusPoly2.getDegree()) {
            modulusPoly4 = modulusPoly;
            modulusPoly3 = modulusPoly2;
        }
        modulusPoly2 = modulusPoly3;
        ModulusPoly modulusPoly5 = modulusPoly4;
        modulusPoly3 = this.field.getZero();
        modulusPoly = this.field.getOne();
        modulusPoly4 = modulusPoly2;
        modulusPoly2 = modulusPoly5;
        while (true) {
            ModulusPoly modulusPoly6 = modulusPoly3;
            modulusPoly5 = modulusPoly4;
            if (modulusPoly2.getDegree() < n2 / 2) break;
            modulusPoly4 = modulusPoly2;
            modulusPoly3 = modulusPoly;
            if (modulusPoly4.isZero()) {
                throw ChecksumException.getChecksumInstance();
            }
            modulusPoly2 = modulusPoly5;
            modulusPoly = this.field.getZero();
            int n3 = modulusPoly4.getCoefficient(modulusPoly4.getDegree());
            n3 = this.field.inverse(n3);
            while (modulusPoly2.getDegree() >= modulusPoly4.getDegree() && !modulusPoly2.isZero()) {
                int n4 = modulusPoly2.getDegree() - modulusPoly4.getDegree();
                int n5 = this.field.multiply(modulusPoly2.getCoefficient(modulusPoly2.getDegree()), n3);
                modulusPoly = modulusPoly.add(this.field.buildMonomial(n4, n5));
                modulusPoly2 = modulusPoly2.subtract(modulusPoly4.multiplyByMonomial(n4, n5));
            }
            modulusPoly = modulusPoly.multiply(modulusPoly3).subtract(modulusPoly6).negative();
        }
        n2 = modulusPoly.getCoefficient(0);
        if (n2 == 0) {
            throw ChecksumException.getChecksumInstance();
        }
        n2 = this.field.inverse(n2);
        return new ModulusPoly[]{modulusPoly.multiply(n2), modulusPoly2.multiply(n2)};
    }

    public int decode(int[] nArray, int n2, int[] object) throws ChecksumException {
        Object object2;
        int n3;
        Object object3 = new ModulusPoly(this.field, nArray);
        Object object4 = new int[n2];
        int n4 = 0;
        for (n3 = n2; n3 > 0; --n3) {
            object4[n2 - n3] = object2 = object3.evaluateAt(this.field.exp(n3));
            if (object2 == 0) continue;
            n4 = 1;
        }
        if (n4 == 0) {
            return 0;
        }
        object3 = this.field.getOne();
        if (object != null) {
            n4 = ((Object)object).length;
            for (n3 = 0; n3 < n4; ++n3) {
                object2 = object[n3];
                object2 = this.field.exp(nArray.length - 1 - object2);
                object3 = object3.multiply(new ModulusPoly(this.field, new int[]{this.field.subtract(0, (int)object2), 1}));
            }
        }
        ModulusPoly modulusPoly = new ModulusPoly(this.field, (int[])object4);
        object3 = this.runEuclideanAlgorithm(this.field.buildMonomial(n2, 1), modulusPoly, n2);
        ModulusPoly modulusPoly2 = object3[0];
        object4 = object3[1];
        object3 = this.findErrorLocations(modulusPoly2);
        int[] nArray2 = this.findErrorMagnitudes((ModulusPoly)object4, modulusPoly2, (int[])object3);
        for (n2 = 0; n2 < ((ModulusPoly[])object3).length; ++n2) {
            n3 = nArray.length - 1 - this.field.log((int)object3[n2]);
            if (n3 < 0) {
                throw ChecksumException.getChecksumInstance();
            }
            nArray[n3] = this.field.subtract(nArray[n3], nArray2[n2]);
        }
        return ((ModulusPoly[])object3).length;
    }
}

