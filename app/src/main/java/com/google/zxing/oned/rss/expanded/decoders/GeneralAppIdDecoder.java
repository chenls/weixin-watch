/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.BlockParsedResult;
import com.google.zxing.oned.rss.expanded.decoders.CurrentParsingState;
import com.google.zxing.oned.rss.expanded.decoders.DecodedChar;
import com.google.zxing.oned.rss.expanded.decoders.DecodedInformation;
import com.google.zxing.oned.rss.expanded.decoders.DecodedNumeric;
import com.google.zxing.oned.rss.expanded.decoders.FieldParser;

final class GeneralAppIdDecoder {
    private final StringBuilder buffer;
    private final CurrentParsingState current = new CurrentParsingState();
    private final BitArray information;

    GeneralAppIdDecoder(BitArray bitArray) {
        this.buffer = new StringBuilder();
        this.information = bitArray;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private DecodedChar decodeAlphanumeric(int n2) {
        char c2;
        int n3 = this.extractNumericValueFromBitArray(n2, 5);
        if (n3 == 15) {
            return new DecodedChar(n2 + 5, '$');
        }
        if (n3 >= 5 && n3 < 15) {
            return new DecodedChar(n2 + 5, (char)(n3 + 48 - 5));
        }
        n3 = this.extractNumericValueFromBitArray(n2, 6);
        if (n3 >= 32 && n3 < 58) {
            return new DecodedChar(n2 + 6, (char)(n3 + 33));
        }
        switch (n3) {
            default: {
                throw new IllegalStateException("Decoding invalid alphanumeric value: " + n3);
            }
            case 58: {
                c2 = '*';
                return new DecodedChar(n2 + 6, c2);
            }
            case 59: {
                c2 = ',';
                return new DecodedChar(n2 + 6, c2);
            }
            case 60: {
                c2 = '-';
                return new DecodedChar(n2 + 6, c2);
            }
            case 61: {
                c2 = '.';
                return new DecodedChar(n2 + 6, c2);
            }
            case 62: 
        }
        c2 = '/';
        return new DecodedChar(n2 + 6, c2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private DecodedChar decodeIsoIec646(int n2) throws FormatException {
        char c2;
        int n3 = this.extractNumericValueFromBitArray(n2, 5);
        if (n3 == 15) {
            return new DecodedChar(n2 + 5, '$');
        }
        if (n3 >= 5 && n3 < 15) {
            return new DecodedChar(n2 + 5, (char)(n3 + 48 - 5));
        }
        n3 = this.extractNumericValueFromBitArray(n2, 7);
        if (n3 >= 64 && n3 < 90) {
            return new DecodedChar(n2 + 7, (char)(n3 + 1));
        }
        if (n3 >= 90 && n3 < 116) {
            return new DecodedChar(n2 + 7, (char)(n3 + 7));
        }
        switch (this.extractNumericValueFromBitArray(n2, 8)) {
            default: {
                throw FormatException.getFormatInstance();
            }
            case 232: {
                c2 = '!';
                return new DecodedChar(n2 + 8, c2);
            }
            case 233: {
                c2 = '\"';
                return new DecodedChar(n2 + 8, c2);
            }
            case 234: {
                c2 = '%';
                return new DecodedChar(n2 + 8, c2);
            }
            case 235: {
                c2 = '&';
                return new DecodedChar(n2 + 8, c2);
            }
            case 236: {
                c2 = '\'';
                return new DecodedChar(n2 + 8, c2);
            }
            case 237: {
                c2 = '(';
                return new DecodedChar(n2 + 8, c2);
            }
            case 238: {
                c2 = ')';
                return new DecodedChar(n2 + 8, c2);
            }
            case 239: {
                c2 = '*';
                return new DecodedChar(n2 + 8, c2);
            }
            case 240: {
                c2 = '+';
                return new DecodedChar(n2 + 8, c2);
            }
            case 241: {
                c2 = ',';
                return new DecodedChar(n2 + 8, c2);
            }
            case 242: {
                c2 = '-';
                return new DecodedChar(n2 + 8, c2);
            }
            case 243: {
                c2 = '.';
                return new DecodedChar(n2 + 8, c2);
            }
            case 244: {
                c2 = '/';
                return new DecodedChar(n2 + 8, c2);
            }
            case 245: {
                c2 = ':';
                return new DecodedChar(n2 + 8, c2);
            }
            case 246: {
                c2 = ';';
                return new DecodedChar(n2 + 8, c2);
            }
            case 247: {
                c2 = '<';
                return new DecodedChar(n2 + 8, c2);
            }
            case 248: {
                c2 = '=';
                return new DecodedChar(n2 + 8, c2);
            }
            case 249: {
                c2 = '>';
                return new DecodedChar(n2 + 8, c2);
            }
            case 250: {
                c2 = '?';
                return new DecodedChar(n2 + 8, c2);
            }
            case 251: {
                c2 = '_';
                return new DecodedChar(n2 + 8, c2);
            }
            case 252: 
        }
        c2 = ' ';
        return new DecodedChar(n2 + 8, c2);
    }

    private DecodedNumeric decodeNumeric(int n2) throws FormatException {
        if (n2 + 7 > this.information.getSize()) {
            if ((n2 = this.extractNumericValueFromBitArray(n2, 4)) == 0) {
                return new DecodedNumeric(this.information.getSize(), 10, 10);
            }
            return new DecodedNumeric(this.information.getSize(), n2 - 1, 10);
        }
        int n3 = this.extractNumericValueFromBitArray(n2, 7);
        return new DecodedNumeric(n2 + 7, (n3 - 8) / 11, (n3 - 8) % 11);
    }

    static int extractNumericValueFromBitArray(BitArray bitArray, int n2, int n3) {
        int n4 = 0;
        for (int i2 = 0; i2 < n3; ++i2) {
            int n5 = n4;
            if (bitArray.get(n2 + i2)) {
                n5 = n4 | 1 << n3 - i2 - 1;
            }
            n4 = n5;
        }
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isAlphaOr646ToNumericLatch(int n2) {
        if (n2 + 3 <= this.information.getSize()) {
            int n3 = n2;
            while (true) {
                if (n3 >= n2 + 3) {
                    return true;
                }
                if (this.information.get(n3)) break;
                ++n3;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isAlphaTo646ToAlphaLatch(int n2) {
        if (n2 + 1 > this.information.getSize()) return false;
        for (int i2 = 0; i2 < 5 && i2 + n2 < this.information.getSize(); ++i2) {
            if (i2 == 2) {
                if (!this.information.get(n2 + 2)) return false;
                continue;
            }
            if (!this.information.get(n2 + i2)) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isNumericToAlphaNumericLatch(int n2) {
        if (n2 + 1 > this.information.getSize()) return false;
        for (int i2 = 0; i2 < 4 && i2 + n2 < this.information.getSize(); ++i2) {
            if (this.information.get(n2 + i2)) return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isStillAlpha(int n2) {
        boolean bl2 = true;
        if (n2 + 5 > this.information.getSize()) {
            return false;
        }
        int n3 = this.extractNumericValueFromBitArray(n2, 5);
        if (n3 >= 5 && n3 < 16) {
            return true;
        }
        if (n2 + 6 > this.information.getSize()) return false;
        if ((n2 = this.extractNumericValueFromBitArray(n2, 6)) < 16) return false;
        if (n2 >= 63) return false;
        return bl2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isStillIsoIec646(int n2) {
        boolean bl2 = true;
        if (n2 + 5 > this.information.getSize()) {
            return false;
        }
        int n3 = this.extractNumericValueFromBitArray(n2, 5);
        if (n3 >= 5 && n3 < 16) {
            return true;
        }
        if (n2 + 7 > this.information.getSize()) return false;
        n3 = this.extractNumericValueFromBitArray(n2, 7);
        if (n3 >= 64 && n3 < 116) {
            return true;
        }
        if (n2 + 8 > this.information.getSize()) return false;
        if ((n2 = this.extractNumericValueFromBitArray(n2, 8)) < 232) return false;
        if (n2 >= 253) return false;
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isStillNumeric(int n2) {
        if (n2 + 7 > this.information.getSize()) {
            if (n2 + 4 <= this.information.getSize()) return true;
            return false;
        }
        int n3 = n2;
        while (n3 < n2 + 3) {
            if (this.information.get(n3)) {
                return true;
            }
            ++n3;
        }
        return this.information.get(n2 + 3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private BlockParsedResult parseAlphaBlock() {
        while (this.isStillAlpha(this.current.getPosition())) {
            DecodedChar decodedChar = this.decodeAlphanumeric(this.current.getPosition());
            this.current.setPosition(decodedChar.getNewPosition());
            if (decodedChar.isFNC1()) {
                return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedChar.getValue());
        }
        if (this.isAlphaOr646ToNumericLatch(this.current.getPosition())) {
            this.current.incrementPosition(3);
            this.current.setNumeric();
            return new BlockParsedResult(false);
        }
        if (!this.isAlphaTo646ToAlphaLatch(this.current.getPosition())) return new BlockParsedResult(false);
        if (this.current.getPosition() + 5 < this.information.getSize()) {
            this.current.incrementPosition(5);
        } else {
            this.current.setPosition(this.information.getSize());
        }
        this.current.setIsoIec646();
        return new BlockParsedResult(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    private DecodedInformation parseBlocks() throws FormatException {
        BlockParsedResult blockParsedResult;
        boolean bl2;
        do {
            int n2 = this.current.getPosition();
            if (this.current.isAlpha()) {
                blockParsedResult = this.parseAlphaBlock();
                bl2 = blockParsedResult.isFinished();
            } else if (this.current.isIsoIec646()) {
                blockParsedResult = this.parseIsoIec646Block();
                bl2 = blockParsedResult.isFinished();
            } else {
                blockParsedResult = this.parseNumericBlock();
                bl2 = blockParsedResult.isFinished();
            }
            n2 = n2 != this.current.getPosition() ? 1 : 0;
            if (n2 != 0 || bl2) continue;
            return blockParsedResult.getDecodedInformation();
        } while (!bl2);
        return blockParsedResult.getDecodedInformation();
    }

    /*
     * Enabled aggressive block sorting
     */
    private BlockParsedResult parseIsoIec646Block() throws FormatException {
        while (this.isStillIsoIec646(this.current.getPosition())) {
            DecodedChar decodedChar = this.decodeIsoIec646(this.current.getPosition());
            this.current.setPosition(decodedChar.getNewPosition());
            if (decodedChar.isFNC1()) {
                return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedChar.getValue());
        }
        if (this.isAlphaOr646ToNumericLatch(this.current.getPosition())) {
            this.current.incrementPosition(3);
            this.current.setNumeric();
            return new BlockParsedResult(false);
        }
        if (!this.isAlphaTo646ToAlphaLatch(this.current.getPosition())) return new BlockParsedResult(false);
        if (this.current.getPosition() + 5 < this.information.getSize()) {
            this.current.incrementPosition(5);
        } else {
            this.current.setPosition(this.information.getSize());
        }
        this.current.setAlpha();
        return new BlockParsedResult(false);
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private BlockParsedResult parseNumericBlock() throws FormatException {
        while (this.isStillNumeric(this.current.getPosition())) {
            DecodedNumeric decodedNumeric = this.decodeNumeric(this.current.getPosition());
            this.current.setPosition(decodedNumeric.getNewPosition());
            if (decodedNumeric.isFirstDigitFNC1()) {
                void var1_3;
                if (decodedNumeric.isSecondDigitFNC1()) {
                    DecodedInformation decodedInformation = new DecodedInformation(this.current.getPosition(), this.buffer.toString());
                    return new BlockParsedResult((DecodedInformation)var1_3, true);
                }
                DecodedInformation decodedInformation = new DecodedInformation(this.current.getPosition(), this.buffer.toString(), decodedNumeric.getSecondDigit());
                return new BlockParsedResult((DecodedInformation)var1_3, true);
            }
            this.buffer.append(decodedNumeric.getFirstDigit());
            if (decodedNumeric.isSecondDigitFNC1()) {
                return new BlockParsedResult(new DecodedInformation(this.current.getPosition(), this.buffer.toString()), true);
            }
            this.buffer.append(decodedNumeric.getSecondDigit());
        }
        if (!this.isNumericToAlphaNumericLatch(this.current.getPosition())) return new BlockParsedResult(false);
        this.current.setAlpha();
        this.current.incrementPosition(4);
        return new BlockParsedResult(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    String decodeAllCodes(StringBuilder stringBuilder, int n2) throws NotFoundException, FormatException {
        String string2 = null;
        while (true) {
            DecodedInformation decodedInformation;
            if ((string2 = FieldParser.parseFieldsInGeneralPurpose((decodedInformation = this.decodeGeneralPurposeField(n2, string2)).getNewString())) != null) {
                stringBuilder.append(string2);
            }
            string2 = decodedInformation.isRemaining() ? String.valueOf(decodedInformation.getRemainingValue()) : null;
            if (n2 == decodedInformation.getNewPosition()) {
                return stringBuilder.toString();
            }
            n2 = decodedInformation.getNewPosition();
        }
    }

    DecodedInformation decodeGeneralPurposeField(int n2, String object) throws FormatException {
        this.buffer.setLength(0);
        if (object != null) {
            this.buffer.append((String)object);
        }
        this.current.setPosition(n2);
        object = this.parseBlocks();
        if (object != null && ((DecodedInformation)object).isRemaining()) {
            return new DecodedInformation(this.current.getPosition(), this.buffer.toString(), ((DecodedInformation)object).getRemainingValue());
        }
        return new DecodedInformation(this.current.getPosition(), this.buffer.toString());
    }

    int extractNumericValueFromBitArray(int n2, int n3) {
        return GeneralAppIdDecoder.extractNumericValueFromBitArray(this.information, n2, n3);
    }
}

