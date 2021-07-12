/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common;

import com.google.zxing.common.BitArray;
import java.util.Arrays;

public final class BitMatrix
implements Cloneable {
    private final int[] bits;
    private final int height;
    private final int rowSize;
    private final int width;

    public BitMatrix(int n2) {
        this(n2, n2);
    }

    public BitMatrix(int n2, int n3) {
        if (n2 < 1 || n3 < 1) {
            throw new IllegalArgumentException("Both dimensions must be greater than 0");
        }
        this.width = n2;
        this.height = n3;
        this.rowSize = (n2 + 31) / 32;
        this.bits = new int[this.rowSize * n3];
    }

    private BitMatrix(int n2, int n3, int n4, int[] nArray) {
        this.width = n2;
        this.height = n3;
        this.rowSize = n4;
        this.bits = nArray;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static BitMatrix parse(String object, String string2, String string3) {
        int n2;
        if (object == null) {
            throw new IllegalArgumentException();
        }
        boolean[] blArray = new boolean[((String)object).length()];
        int n3 = 0;
        int n4 = 0;
        int n5 = -1;
        int n6 = 0;
        int n7 = 0;
        while (n7 < ((String)object).length()) {
            if (((String)object).charAt(n7) == '\n' || ((String)object).charAt(n7) == '\r') {
                int n8 = n6;
                n2 = n5;
                int n9 = n4;
                if (n3 > n4) {
                    if (n5 == -1) {
                        n2 = n3 - n4;
                    } else {
                        n2 = n5;
                        if (n3 - n4 != n5) {
                            throw new IllegalArgumentException("row lengths do not match");
                        }
                    }
                    n9 = n3;
                    n8 = n6 + 1;
                }
                ++n7;
                n6 = n8;
                n5 = n2;
                n4 = n9;
                continue;
            }
            if (((String)object).substring(n7, string2.length() + n7).equals(string2)) {
                n7 += string2.length();
                blArray[n3] = true;
                ++n3;
                continue;
            }
            if (!((String)object).substring(n7, string3.length() + n7).equals(string3)) {
                throw new IllegalArgumentException("illegal character encountered: " + ((String)object).substring(n7));
            }
            n7 += string3.length();
            blArray[n3] = false;
            ++n3;
        }
        n2 = n6;
        n7 = n5;
        if (n3 > n4) {
            if (n5 == -1) {
                n7 = n3 - n4;
            } else {
                n7 = n5;
                if (n3 - n4 != n5) {
                    throw new IllegalArgumentException("row lengths do not match");
                }
            }
            n2 = n6 + 1;
        }
        object = new BitMatrix(n7, n2);
        n5 = 0;
        while (n5 < n3) {
            if (blArray[n5]) {
                ((BitMatrix)object).set(n5 % n7, n5 / n7);
            }
            ++n5;
        }
        return object;
    }

    public void clear() {
        int n2 = this.bits.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            this.bits[i2] = 0;
        }
    }

    public BitMatrix clone() {
        return new BitMatrix(this.width, this.height, this.rowSize, (int[])this.bits.clone());
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof BitMatrix)) break block2;
                object = (BitMatrix)object;
                if (this.width == ((BitMatrix)object).width && this.height == ((BitMatrix)object).height && this.rowSize == ((BitMatrix)object).rowSize && Arrays.equals(this.bits, ((BitMatrix)object).bits)) break block3;
            }
            return false;
        }
        return true;
    }

    public void flip(int n2, int n3) {
        n3 = this.rowSize * n3 + n2 / 32;
        int[] nArray = this.bits;
        nArray[n3] = nArray[n3] ^ 1 << (n2 & 0x1F);
    }

    public boolean get(int n2, int n3) {
        int n4 = this.rowSize;
        int n5 = n2 / 32;
        return (this.bits[n4 * n3 + n5] >>> (n2 & 0x1F) & 1) != 0;
    }

    public int[] getBottomRightOnBit() {
        int n2;
        for (n2 = this.bits.length - 1; n2 >= 0 && this.bits[n2] == 0; --n2) {
        }
        if (n2 < 0) {
            return null;
        }
        int n3 = n2 / this.rowSize;
        int n4 = this.rowSize;
        int n5 = this.bits[n2];
        int n6 = 31;
        while (n5 >>> n6 == 0) {
            --n6;
        }
        return new int[]{n2 % n4 * 32 + n6, n3};
    }

    public int[] getEnclosingRectangle() {
        int n2;
        int n3;
        int n4 = this.width;
        int n5 = this.height;
        int n6 = -1;
        int n7 = -1;
        for (n3 = 0; n3 < this.height; ++n3) {
            for (int i2 = 0; i2 < this.rowSize; ++i2) {
                int n8 = this.bits[this.rowSize * n3 + i2];
                int n9 = n7;
                int n10 = n4;
                int n11 = n6;
                int n12 = n5;
                if (n8 != 0) {
                    n2 = n5;
                    if (n3 < n5) {
                        n2 = n3;
                    }
                    n5 = n7;
                    if (n3 > n7) {
                        n5 = n3;
                    }
                    n7 = n4;
                    if (i2 * 32 < n4) {
                        n12 = 0;
                        while (n8 << 31 - n12 == 0) {
                            ++n12;
                        }
                        n7 = n4;
                        if (i2 * 32 + n12 < n4) {
                            n7 = i2 * 32 + n12;
                        }
                    }
                    n9 = n5;
                    n10 = n7;
                    n11 = n6;
                    n12 = n2;
                    if (i2 * 32 + 31 > n6) {
                        n4 = 31;
                        while (n8 >>> n4 == 0) {
                            --n4;
                        }
                        n9 = n5;
                        n10 = n7;
                        n11 = n6;
                        n12 = n2;
                        if (i2 * 32 + n4 > n6) {
                            n11 = i2 * 32 + n4;
                            n12 = n2;
                            n10 = n7;
                            n9 = n5;
                        }
                    }
                }
                n7 = n9;
                n4 = n10;
                n6 = n11;
                n5 = n12;
            }
        }
        n3 = n6 - n4;
        n2 = n7 - n5;
        if (n3 < 0 || n2 < 0) {
            return null;
        }
        return new int[]{n4, n5, n3, n2};
    }

    public int getHeight() {
        return this.height;
    }

    /*
     * Enabled aggressive block sorting
     */
    public BitArray getRow(int n2, BitArray bitArray) {
        if (bitArray == null || bitArray.getSize() < this.width) {
            bitArray = new BitArray(this.width);
        } else {
            bitArray.clear();
        }
        int n3 = this.rowSize;
        int n4 = 0;
        while (n4 < this.rowSize) {
            bitArray.setBulk(n4 * 32, this.bits[n2 * n3 + n4]);
            ++n4;
        }
        return bitArray;
    }

    public int getRowSize() {
        return this.rowSize;
    }

    public int[] getTopLeftOnBit() {
        int n2;
        for (n2 = 0; n2 < this.bits.length && this.bits[n2] == 0; ++n2) {
        }
        if (n2 == this.bits.length) {
            return null;
        }
        int n3 = n2 / this.rowSize;
        int n4 = this.rowSize;
        int n5 = this.bits[n2];
        int n6 = 0;
        while (n5 << 31 - n6 == 0) {
            ++n6;
        }
        return new int[]{n2 % n4 * 32 + n6, n3};
    }

    public int getWidth() {
        return this.width;
    }

    public int hashCode() {
        return (((this.width * 31 + this.width) * 31 + this.height) * 31 + this.rowSize) * 31 + Arrays.hashCode(this.bits);
    }

    public void rotate180() {
        int n2 = this.getWidth();
        int n3 = this.getHeight();
        BitArray bitArray = new BitArray(n2);
        BitArray bitArray2 = new BitArray(n2);
        for (n2 = 0; n2 < (n3 + 1) / 2; ++n2) {
            bitArray = this.getRow(n2, bitArray);
            bitArray2 = this.getRow(n3 - 1 - n2, bitArray2);
            bitArray.reverse();
            bitArray2.reverse();
            this.setRow(n2, bitArray2);
            this.setRow(n3 - 1 - n2, bitArray);
        }
    }

    public void set(int n2, int n3) {
        n3 = this.rowSize * n3 + n2 / 32;
        int[] nArray = this.bits;
        nArray[n3] = nArray[n3] | 1 << (n2 & 0x1F);
    }

    public void setRegion(int n2, int n3, int n4, int n5) {
        if (n3 < 0 || n2 < 0) {
            throw new IllegalArgumentException("Left and top must be nonnegative");
        }
        if (n5 < 1 || n4 < 1) {
            throw new IllegalArgumentException("Height and width must be at least 1");
        }
        int n6 = n2 + n4;
        if ((n5 = n3 + n5) > this.height || n6 > this.width) {
            throw new IllegalArgumentException("The region must fit inside the matrix");
        }
        while (n3 < n5) {
            int n7 = this.rowSize;
            for (n4 = n2; n4 < n6; ++n4) {
                int[] nArray = this.bits;
                int n8 = n4 / 32 + n3 * n7;
                nArray[n8] = nArray[n8] | 1 << (n4 & 0x1F);
            }
            ++n3;
        }
    }

    public void setRow(int n2, BitArray bitArray) {
        System.arraycopy(bitArray.getBitArray(), 0, this.bits, this.rowSize * n2, this.rowSize);
    }

    public String toString() {
        return this.toString("X ", "  ");
    }

    public String toString(String string2, String string3) {
        return this.toString(string2, string3, System.lineSeparator());
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString(String string2, String string3, String string4) {
        StringBuilder stringBuilder = new StringBuilder(this.height * (this.width + 1));
        int n2 = 0;
        while (n2 < this.height) {
            for (int i2 = 0; i2 < this.width; ++i2) {
                String string5 = this.get(i2, n2) ? string2 : string3;
                stringBuilder.append(string5);
            }
            stringBuilder.append(string4);
            ++n2;
        }
        return stringBuilder.toString();
    }

    public void unset(int n2, int n3) {
        n3 = this.rowSize * n3 + n2 / 32;
        int[] nArray = this.bits;
        nArray[n3] = nArray[n3] & ~(1 << (n2 & 0x1F));
    }

    public void xor(BitMatrix bitMatrix) {
        if (this.width != bitMatrix.getWidth() || this.height != bitMatrix.getHeight() || this.rowSize != bitMatrix.getRowSize()) {
            throw new IllegalArgumentException("input matrix dimensions do not match");
        }
        BitArray bitArray = new BitArray(this.width / 32 + 1);
        for (int i2 = 0; i2 < this.height; ++i2) {
            int n2 = this.rowSize;
            int[] nArray = bitMatrix.getRow(i2, bitArray).getBitArray();
            for (int i3 = 0; i3 < this.rowSize; ++i3) {
                int[] nArray2 = this.bits;
                int n3 = i2 * n2 + i3;
                nArray2[n3] = nArray2[n3] ^ nArray[i3];
            }
        }
    }
}

