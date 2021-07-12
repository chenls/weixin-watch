/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common;

import java.util.Arrays;

public final class BitArray
implements Cloneable {
    private int[] bits;
    private int size;

    public BitArray() {
        this.size = 0;
        this.bits = new int[1];
    }

    public BitArray(int n2) {
        this.size = n2;
        this.bits = BitArray.makeArray(n2);
    }

    BitArray(int[] nArray, int n2) {
        this.bits = nArray;
        this.size = n2;
    }

    private void ensureCapacity(int n2) {
        if (n2 > this.bits.length * 32) {
            int[] nArray = BitArray.makeArray(n2);
            System.arraycopy(this.bits, 0, nArray, 0, this.bits.length);
            this.bits = nArray;
        }
    }

    private static int[] makeArray(int n2) {
        return new int[(n2 + 31) / 32];
    }

    public void appendBit(boolean bl2) {
        this.ensureCapacity(this.size + 1);
        if (bl2) {
            int[] nArray = this.bits;
            int n2 = this.size / 32;
            nArray[n2] = nArray[n2] | 1 << (this.size & 0x1F);
        }
        ++this.size;
    }

    public void appendBitArray(BitArray bitArray) {
        int n2 = bitArray.size;
        this.ensureCapacity(this.size + n2);
        for (int i2 = 0; i2 < n2; ++i2) {
            this.appendBit(bitArray.get(i2));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void appendBits(int n2, int n3) {
        if (n3 < 0 || n3 > 32) {
            throw new IllegalArgumentException("Num bits must be between 0 and 32");
        }
        this.ensureCapacity(this.size + n3);
        while (n3 > 0) {
            boolean bl2 = (n2 >> n3 - 1 & 1) == 1;
            this.appendBit(bl2);
            --n3;
        }
        return;
    }

    public void clear() {
        int n2 = this.bits.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            this.bits[i2] = 0;
        }
    }

    public BitArray clone() {
        return new BitArray((int[])this.bits.clone(), this.size);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof BitArray)) break block2;
                object = (BitArray)object;
                if (this.size == ((BitArray)object).size && Arrays.equals(this.bits, ((BitArray)object).bits)) break block3;
            }
            return false;
        }
        return true;
    }

    public void flip(int n2) {
        int[] nArray = this.bits;
        int n3 = n2 / 32;
        nArray[n3] = nArray[n3] ^ 1 << (n2 & 0x1F);
    }

    public boolean get(int n2) {
        return (this.bits[n2 / 32] & 1 << (n2 & 0x1F)) != 0;
    }

    public int[] getBitArray() {
        return this.bits;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getNextSet(int n2) {
        if (n2 >= this.size) {
            return this.size;
        }
        int n3 = n2 / 32;
        int n4 = this.bits[n3] & ~((1 << (n2 & 0x1F)) - 1);
        n2 = n3;
        while (n4 == 0) {
            if (++n2 == this.bits.length) {
                return this.size;
            }
            n4 = this.bits[n2];
        }
        n2 = n4 = n2 * 32 + Integer.numberOfTrailingZeros(n4);
        if (n4 <= this.size) return n2;
        return this.size;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getNextUnset(int n2) {
        if (n2 >= this.size) {
            return this.size;
        }
        int n3 = n2 / 32;
        int n4 = ~this.bits[n3] & ~((1 << (n2 & 0x1F)) - 1);
        n2 = n3;
        while (n4 == 0) {
            if (++n2 == this.bits.length) {
                return this.size;
            }
            n4 = ~this.bits[n2];
        }
        n2 = n4 = n2 * 32 + Integer.numberOfTrailingZeros(n4);
        if (n4 <= this.size) return n2;
        return this.size;
    }

    public int getSize() {
        return this.size;
    }

    public int getSizeInBytes() {
        return (this.size + 7) / 8;
    }

    public int hashCode() {
        return this.size * 31 + Arrays.hashCode(this.bits);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isRange(int n2, int n3, boolean bl2) {
        if (n3 < n2) {
            throw new IllegalArgumentException();
        }
        if (n3 != n2) {
            int n4 = n3 - 1;
            int n5 = n2 / 32;
            int n6 = n4 / 32;
            for (int i2 = n5; i2 <= n6; ++i2) {
                int n7;
                n3 = i2 > n5 ? 0 : n2 & 0x1F;
                int n8 = i2 < n6 ? 31 : n4 & 0x1F;
                if (n3 == 0 && n8 == 31) {
                    n3 = -1;
                } else {
                    n7 = 0;
                    int n9 = n3;
                    while (true) {
                        n3 = n7;
                        if (n9 > n8) break;
                        n7 |= 1 << n9;
                        ++n9;
                    }
                }
                n8 = this.bits[i2];
                n7 = bl2 ? n3 : 0;
                if ((n8 & n3) == n7) continue;
                return false;
            }
        }
        return true;
    }

    public void reverse() {
        int n2;
        int[] nArray = new int[this.bits.length];
        int n3 = (this.size - 1) / 32;
        int n4 = n3 + 1;
        for (n2 = 0; n2 < n4; ++n2) {
            long l2 = this.bits[n2];
            l2 = l2 >> 1 & 0x55555555L | (0x55555555L & l2) << 1;
            l2 = l2 >> 2 & 0x33333333L | (0x33333333L & l2) << 2;
            l2 = l2 >> 4 & 0xF0F0F0FL | (0xF0F0F0FL & l2) << 4;
            l2 = l2 >> 8 & 0xFF00FFL | (0xFF00FFL & l2) << 8;
            nArray[n3 - n2] = (int)(l2 >> 16 & 0xFFFFL | (0xFFFFL & l2) << 16);
        }
        if (this.size != n4 * 32) {
            int n5 = n4 * 32 - this.size;
            n2 = 1;
            for (n3 = 0; n3 < 31 - n5; ++n3) {
                n2 = n2 << 1 | 1;
            }
            int n6 = nArray[0] >> n5 & n2;
            for (n3 = 1; n3 < n4; ++n3) {
                int n7 = nArray[n3];
                nArray[n3 - 1] = n6 | n7 << 32 - n5;
                n6 = n7 >> n5 & n2;
            }
            nArray[n4 - 1] = n6;
        }
        this.bits = nArray;
    }

    public void set(int n2) {
        int[] nArray = this.bits;
        int n3 = n2 / 32;
        nArray[n3] = nArray[n3] | 1 << (n2 & 0x1F);
    }

    public void setBulk(int n2, int n3) {
        this.bits[n2 / 32] = n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setRange(int n2, int n3) {
        if (n3 < n2) {
            throw new IllegalArgumentException();
        }
        if (n3 != n2) {
            int n4 = n3 - 1;
            int n5 = n2 / 32;
            int n6 = n4 / 32;
            for (int i2 = n5; i2 <= n6; ++i2) {
                int n7;
                n3 = i2 > n5 ? 0 : n2 & 0x1F;
                int n8 = i2 < n6 ? 31 : n4 & 0x1F;
                if (n3 == 0 && n8 == 31) {
                    n7 = -1;
                } else {
                    n7 = 0;
                    int n9 = n3;
                    n3 = n7;
                    while (true) {
                        n7 = n3;
                        if (n9 > n8) break;
                        n3 |= 1 << n9;
                        ++n9;
                    }
                }
                int[] nArray = this.bits;
                nArray[i2] = nArray[i2] | n7;
            }
        }
    }

    public void toBytes(int n2, byte[] byArray, int n3, int n4) {
        int n5 = 0;
        int n6 = n2;
        for (n2 = n5; n2 < n4; ++n2) {
            int n7 = 0;
            for (n5 = 0; n5 < 8; ++n5) {
                int n8 = n7;
                if (this.get(n6)) {
                    n8 = n7 | 1 << 7 - n5;
                }
                ++n6;
                n7 = n8;
            }
            byArray[n3 + n2] = (byte)n7;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.size);
        int n2 = 0;
        while (n2 < this.size) {
            if ((n2 & 7) == 0) {
                stringBuilder.append(' ');
            }
            char c2 = this.get(n2) ? (char)'X' : '.';
            stringBuilder.append(c2);
            ++n2;
        }
        return stringBuilder.toString();
    }

    public void xor(BitArray bitArray) {
        if (this.bits.length != bitArray.bits.length) {
            throw new IllegalArgumentException("Sizes don't match");
        }
        for (int i2 = 0; i2 < this.bits.length; ++i2) {
            int[] nArray = this.bits;
            nArray[i2] = nArray[i2] ^ bitArray.bits[i2];
        }
    }
}

