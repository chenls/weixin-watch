/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.asm;

import com.alibaba.fastjson.asm.ByteVector;
import com.alibaba.fastjson.asm.MethodWriter;

public class Label {
    int inputStackTop;
    Label next;
    int outputStackMax;
    int position;
    private int referenceCount;
    private int[] srcAndRefPositions;
    int status;
    Label successor;

    private void addReference(int n2, int n3) {
        int[] nArray;
        if (this.srcAndRefPositions == null) {
            this.srcAndRefPositions = new int[6];
        }
        if (this.referenceCount >= this.srcAndRefPositions.length) {
            nArray = new int[this.srcAndRefPositions.length + 6];
            System.arraycopy(this.srcAndRefPositions, 0, nArray, 0, this.srcAndRefPositions.length);
            this.srcAndRefPositions = nArray;
        }
        nArray = this.srcAndRefPositions;
        int n4 = this.referenceCount;
        this.referenceCount = n4 + 1;
        nArray[n4] = n2;
        nArray = this.srcAndRefPositions;
        n2 = this.referenceCount;
        this.referenceCount = n2 + 1;
        nArray[n2] = n3;
    }

    void put(MethodWriter methodWriter, ByteVector byteVector, int n2) {
        if ((this.status & 2) == 0) {
            this.addReference(n2, byteVector.length);
            byteVector.putShort(-1);
            return;
        }
        byteVector.putShort(this.position - n2);
    }

    void resolve(MethodWriter object, int n2, byte[] byArray) {
        this.status |= 2;
        this.position = n2;
        int n3 = 0;
        while (n3 < this.referenceCount) {
            object = this.srcAndRefPositions;
            Object object2 = n3 + 1;
            Object object3 = object[n3];
            object = this.srcAndRefPositions;
            n3 = object2 + 1;
            object2 = object[object2];
            object3 = n2 - object3;
            byArray[object2] = (byte)(object3 >>> 8);
            byArray[object2 + 1] = (byte)object3;
        }
    }
}

