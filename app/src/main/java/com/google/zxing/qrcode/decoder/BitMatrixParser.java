/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.DataMask;
import com.google.zxing.qrcode.decoder.FormatInformation;
import com.google.zxing.qrcode.decoder.Version;

final class BitMatrixParser {
    private final BitMatrix bitMatrix;
    private boolean mirror;
    private FormatInformation parsedFormatInfo;
    private Version parsedVersion;

    BitMatrixParser(BitMatrix bitMatrix) throws FormatException {
        int n2 = bitMatrix.getHeight();
        if (n2 < 21 || (n2 & 3) != 1) {
            throw FormatException.getFormatInstance();
        }
        this.bitMatrix = bitMatrix;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int copyBit(int n2, int n3, int n4) {
        boolean bl2 = this.mirror ? this.bitMatrix.get(n3, n2) : this.bitMatrix.get(n2, n3);
        if (bl2) {
            return n4 << 1 | 1;
        }
        return n4 << 1;
    }

    void mirror() {
        for (int i2 = 0; i2 < this.bitMatrix.getWidth(); ++i2) {
            for (int i3 = i2 + 1; i3 < this.bitMatrix.getHeight(); ++i3) {
                if (this.bitMatrix.get(i2, i3) == this.bitMatrix.get(i3, i2)) continue;
                this.bitMatrix.flip(i3, i2);
                this.bitMatrix.flip(i2, i3);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    byte[] readCodewords() throws FormatException {
        Object object = this.readFormatInformation();
        Version version = this.readVersion();
        object = DataMask.forReference(((FormatInformation)object).getDataMask());
        int n2 = this.bitMatrix.getHeight();
        ((DataMask)object).unmaskBitMatrix(this.bitMatrix, n2);
        object = version.buildFunctionPattern();
        boolean bl2 = true;
        byte[] byArray = new byte[version.getTotalCodewords()];
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = n2 - 1;
        block0: while (true) {
            int n7;
            if (n6 > 0) {
                n7 = n6;
                if (n6 == 6) {
                    n7 = n6 - 1;
                }
            } else {
                if (n3 != version.getTotalCodewords()) {
                    throw FormatException.getFormatInstance();
                }
                return byArray;
            }
            n6 = 0;
            while (true) {
                int n8;
                if (n6 < n2) {
                    n8 = bl2 ? n2 - 1 - n6 : n6;
                } else {
                    bl2 ^= true;
                    n6 = n7 - 2;
                    continue block0;
                }
                for (int i2 = 0; i2 < 2; ++i2) {
                    int n9 = n5++;
                    int n10 = n4;
                    if (!((BitMatrix)object).get(n7 - i2, n8)) {
                        n4 = n10 = n4 << 1;
                        if (this.bitMatrix.get(n7 - i2, n8)) {
                            n4 = n10 | 1;
                        }
                        n9 = n5;
                        n10 = n4;
                        if (n5 == 8) {
                            n5 = n3 + 1;
                            byArray[n3] = (byte)n4;
                            n10 = 0;
                            n4 = 0;
                            n3 = n5;
                            n5 = n10;
                            continue;
                        }
                    }
                    n5 = n9;
                    n4 = n10;
                }
                ++n6;
            }
            break;
        }
    }

    FormatInformation readFormatInformation() throws FormatException {
        int n2;
        int n3;
        if (this.parsedFormatInfo != null) {
            return this.parsedFormatInfo;
        }
        int n4 = 0;
        for (n3 = 0; n3 < 6; ++n3) {
            n4 = this.copyBit(n3, 8, n4);
        }
        n4 = this.copyBit(8, 7, this.copyBit(8, 8, this.copyBit(7, 8, n4)));
        for (n3 = 5; n3 >= 0; --n3) {
            n4 = this.copyBit(8, n3, n4);
        }
        int n5 = this.bitMatrix.getHeight();
        n3 = 0;
        for (n2 = n5 - 1; n2 >= n5 - 7; --n2) {
            n3 = this.copyBit(8, n2, n3);
        }
        for (n2 = n5 - 8; n2 < n5; ++n2) {
            n3 = this.copyBit(n2, 8, n3);
        }
        this.parsedFormatInfo = FormatInformation.decodeFormatInformation(n4, n3);
        if (this.parsedFormatInfo != null) {
            return this.parsedFormatInfo;
        }
        throw FormatException.getFormatInstance();
    }

    Version readVersion() throws FormatException {
        int n2;
        if (this.parsedVersion != null) {
            return this.parsedVersion;
        }
        int n3 = this.bitMatrix.getHeight();
        int n4 = (n3 - 17) / 4;
        if (n4 <= 6) {
            return Version.getVersionForNumber(n4);
        }
        int n5 = 0;
        int n6 = n3 - 11;
        for (n4 = 5; n4 >= 0; --n4) {
            for (n2 = n3 - 9; n2 >= n6; --n2) {
                n5 = this.copyBit(n2, n4, n5);
            }
        }
        Version version = Version.decodeVersionInformation(n5);
        if (version != null && version.getDimensionForVersion() == n3) {
            this.parsedVersion = version;
            return version;
        }
        n5 = 0;
        for (n4 = 5; n4 >= 0; --n4) {
            for (n2 = n3 - 9; n2 >= n6; --n2) {
                n5 = this.copyBit(n4, n2, n5);
            }
        }
        version = Version.decodeVersionInformation(n5);
        if (version != null && version.getDimensionForVersion() == n3) {
            this.parsedVersion = version;
            return version;
        }
        throw FormatException.getFormatInstance();
    }

    void remask() {
        if (this.parsedFormatInfo == null) {
            return;
        }
        DataMask dataMask = DataMask.forReference(this.parsedFormatInfo.getDataMask());
        int n2 = this.bitMatrix.getHeight();
        dataMask.unmaskBitMatrix(this.bitMatrix, n2);
    }

    void setMirror(boolean bl2) {
        this.parsedVersion = null;
        this.parsedFormatInfo = null;
        this.mirror = bl2;
    }
}

