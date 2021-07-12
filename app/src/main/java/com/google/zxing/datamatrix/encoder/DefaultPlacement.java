/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.encoder;

import java.util.Arrays;

public class DefaultPlacement {
    private final byte[] bits;
    private final CharSequence codewords;
    private final int numcols;
    private final int numrows;

    public DefaultPlacement(CharSequence charSequence, int n2, int n3) {
        this.codewords = charSequence;
        this.numcols = n2;
        this.numrows = n3;
        this.bits = new byte[n2 * n3];
        Arrays.fill(this.bits, (byte)-1);
    }

    private void corner1(int n2) {
        this.module(this.numrows - 1, 0, n2, 1);
        this.module(this.numrows - 1, 1, n2, 2);
        this.module(this.numrows - 1, 2, n2, 3);
        this.module(0, this.numcols - 2, n2, 4);
        this.module(0, this.numcols - 1, n2, 5);
        this.module(1, this.numcols - 1, n2, 6);
        this.module(2, this.numcols - 1, n2, 7);
        this.module(3, this.numcols - 1, n2, 8);
    }

    private void corner2(int n2) {
        this.module(this.numrows - 3, 0, n2, 1);
        this.module(this.numrows - 2, 0, n2, 2);
        this.module(this.numrows - 1, 0, n2, 3);
        this.module(0, this.numcols - 4, n2, 4);
        this.module(0, this.numcols - 3, n2, 5);
        this.module(0, this.numcols - 2, n2, 6);
        this.module(0, this.numcols - 1, n2, 7);
        this.module(1, this.numcols - 1, n2, 8);
    }

    private void corner3(int n2) {
        this.module(this.numrows - 3, 0, n2, 1);
        this.module(this.numrows - 2, 0, n2, 2);
        this.module(this.numrows - 1, 0, n2, 3);
        this.module(0, this.numcols - 2, n2, 4);
        this.module(0, this.numcols - 1, n2, 5);
        this.module(1, this.numcols - 1, n2, 6);
        this.module(2, this.numcols - 1, n2, 7);
        this.module(3, this.numcols - 1, n2, 8);
    }

    private void corner4(int n2) {
        this.module(this.numrows - 1, 0, n2, 1);
        this.module(this.numrows - 1, this.numcols - 1, n2, 2);
        this.module(0, this.numcols - 3, n2, 3);
        this.module(0, this.numcols - 2, n2, 4);
        this.module(0, this.numcols - 1, n2, 5);
        this.module(1, this.numcols - 3, n2, 6);
        this.module(1, this.numcols - 2, n2, 7);
        this.module(1, this.numcols - 1, n2, 8);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void module(int n2, int n3, int n4, int n5) {
        boolean bl2 = true;
        int n6 = n2;
        int n7 = n3;
        if (n2 < 0) {
            n6 = n2 + this.numrows;
            n7 = n3 + (4 - (this.numrows + 4) % 8);
        }
        n3 = n6;
        n2 = n7;
        if (n7 < 0) {
            n2 = n7 + this.numcols;
            n3 = n6 + (4 - (this.numcols + 4) % 8);
        }
        if ((this.codewords.charAt(n4) & 1 << 8 - n5) == 0) {
            bl2 = false;
        }
        this.setBit(n2, n3, bl2);
    }

    private void utah(int n2, int n3, int n4) {
        this.module(n2 - 2, n3 - 2, n4, 1);
        this.module(n2 - 2, n3 - 1, n4, 2);
        this.module(n2 - 1, n3 - 2, n4, 3);
        this.module(n2 - 1, n3 - 1, n4, 4);
        this.module(n2 - 1, n3, n4, 5);
        this.module(n2, n3 - 2, n4, 6);
        this.module(n2, n3 - 1, n4, 7);
        this.module(n2, n3, n4, 8);
    }

    public final boolean getBit(int n2, int n3) {
        return this.bits[this.numcols * n3 + n2] == 1;
    }

    final byte[] getBits() {
        return this.bits;
    }

    final int getNumcols() {
        return this.numcols;
    }

    final int getNumrows() {
        return this.numrows;
    }

    final boolean hasBit(int n2, int n3) {
        return this.bits[this.numcols * n3 + n2] >= 0;
    }

    public final void place() {
        int n2 = 0;
        int n3 = 4;
        int n4 = 0;
        while (true) {
            int n5 = n2;
            if (n3 == this.numrows) {
                n5 = n2;
                if (n4 == 0) {
                    this.corner1(n2);
                    n5 = n2 + 1;
                }
            }
            int n6 = n5;
            if (n3 == this.numrows - 2) {
                n6 = n5;
                if (n4 == 0) {
                    n6 = n5;
                    if (this.numcols % 4 != 0) {
                        this.corner2(n5);
                        n6 = n5 + 1;
                    }
                }
            }
            n2 = n6;
            if (n3 == this.numrows - 2) {
                n2 = n6;
                if (n4 == 0) {
                    n2 = n6;
                    if (this.numcols % 8 == 4) {
                        this.corner3(n6);
                        n2 = n6 + 1;
                    }
                }
            }
            int n7 = n4;
            n5 = n2;
            n6 = n3;
            if (n3 == this.numrows + 4) {
                n7 = n4;
                n5 = n2;
                n6 = n3;
                if (n4 == 2) {
                    n7 = n4;
                    n5 = n2;
                    n6 = n3;
                    if (this.numcols % 8 == 0) {
                        this.corner4(n2);
                        n5 = n2 + 1;
                        n6 = n3;
                        n7 = n4;
                    }
                }
            }
            do {
                n3 = n5;
                if (n6 < this.numrows) {
                    n3 = n5;
                    if (n7 >= 0) {
                        n3 = n5;
                        if (!this.hasBit(n7, n6)) {
                            this.utah(n6, n7, n5);
                            n3 = n5 + 1;
                        }
                    }
                }
                n2 = n6 - 2;
                n4 = n7 + 2;
                if (n2 < 0) break;
                n7 = n4;
                n5 = n3;
                n6 = n2;
            } while (n4 < this.numcols);
            n6 = n2 + 1;
            n2 = n4 + 3;
            n5 = n3;
            n4 = n6;
            n3 = n2;
            do {
                if (n4 < 0 || n3 >= this.numcols || this.hasBit(n3, n4)) continue;
                n2 = n5 + 1;
                this.utah(n4, n3, n5);
                n5 = n2;
            } while ((n4 += 2) < this.numrows && (n3 -= 2) >= 0);
            n6 = n4 + 3;
            n4 = n7 = n3 + 1;
            n2 = n5;
            n3 = n6;
            if (n6 < this.numrows) continue;
            n4 = n7;
            n2 = n5;
            n3 = n6;
            if (n7 >= this.numcols) break;
        }
        if (!this.hasBit(this.numcols - 1, this.numrows - 1)) {
            this.setBit(this.numcols - 1, this.numrows - 1, true);
            this.setBit(this.numcols - 2, this.numrows - 2, true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    final void setBit(int n2, int n3, boolean bl2) {
        byte[] byArray = this.bits;
        int n4 = this.numcols;
        byte by2 = bl2 ? (byte)1 : 0;
        byArray[n4 * n3 + n2] = by2;
    }
}

