/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.decoder;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.StringUtils;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

final class DecodedBitStreamParser {
    private static final char[] ALPHANUMERIC_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '$', '%', '*', '+', '-', '.', '/', ':'};
    private static final int GB2312_SUBSET = 1;

    private DecodedBitStreamParser() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static DecoderResult decode(byte[] byArray, Version arrayList, ErrorCorrectionLevel object, Map<DecodeHintType, ?> object2) throws FormatException {
        int n2;
        int n3;
        Mode mode;
        Mode mode2;
        BitSource bitSource = new BitSource(byArray);
        StringBuilder stringBuilder = new StringBuilder(50);
        ArrayList<byte[]> arrayList2 = new ArrayList<byte[]>(1);
        int n4 = -1;
        int n5 = -1;
        CharacterSetECI characterSetECI = null;
        boolean bl2 = false;
        do {
            boolean bl3;
            CharacterSetECI characterSetECI2;
            block13: {
                block14: {
                    block18: {
                        block17: {
                            block16: {
                                block15: {
                                    try {
                                        mode2 = bitSource.available() < 4 ? Mode.TERMINATOR : Mode.forBits(bitSource.readBits(4));
                                        characterSetECI2 = characterSetECI;
                                        n3 = n4;
                                        n2 = n5;
                                        bl3 = bl2;
                                        if (mode2 == Mode.TERMINATOR) break block13;
                                        if (mode2 == Mode.FNC1_FIRST_POSITION || mode2 == Mode.FNC1_SECOND_POSITION) break block14;
                                        if (mode2 != Mode.STRUCTURED_APPEND) break block15;
                                        if (bitSource.available() < 16) {
                                            throw FormatException.getFormatInstance();
                                        }
                                        n3 = bitSource.readBits(8);
                                        n2 = bitSource.readBits(8);
                                        characterSetECI2 = characterSetECI;
                                        bl3 = bl2;
                                        break block13;
                                    }
                                    catch (IllegalArgumentException illegalArgumentException) {
                                        throw FormatException.getFormatInstance();
                                    }
                                }
                                if (mode2 != Mode.ECI) break block16;
                                characterSetECI2 = characterSetECI = CharacterSetECI.getCharacterSetECIByValue(DecodedBitStreamParser.parseECIValue(bitSource));
                                n3 = n4;
                                n2 = n5;
                                bl3 = bl2;
                                if (characterSetECI == null) {
                                    throw FormatException.getFormatInstance();
                                }
                                break block13;
                            }
                            if (mode2 != Mode.HANZI) break block17;
                            int n6 = bitSource.readBits(4);
                            int n7 = bitSource.readBits(mode2.getCharacterCountBits((Version)((Object)arrayList)));
                            characterSetECI2 = characterSetECI;
                            n3 = n4;
                            n2 = n5;
                            bl3 = bl2;
                            if (n6 == 1) {
                                DecodedBitStreamParser.decodeHanziSegment(bitSource, stringBuilder, n7);
                                characterSetECI2 = characterSetECI;
                                n3 = n4;
                                n2 = n5;
                                bl3 = bl2;
                            }
                            break block13;
                        }
                        n3 = bitSource.readBits(mode2.getCharacterCountBits((Version)((Object)arrayList)));
                        if (mode2 != Mode.NUMERIC) break block18;
                        DecodedBitStreamParser.decodeNumericSegment(bitSource, stringBuilder, n3);
                        characterSetECI2 = characterSetECI;
                        n3 = n4;
                        n2 = n5;
                        bl3 = bl2;
                        break block13;
                    }
                    if (mode2 == Mode.ALPHANUMERIC) {
                        DecodedBitStreamParser.decodeAlphanumericSegment(bitSource, stringBuilder, n3, bl2);
                        characterSetECI2 = characterSetECI;
                        n3 = n4;
                        n2 = n5;
                        bl3 = bl2;
                        break block13;
                    } else if (mode2 == Mode.BYTE) {
                        DecodedBitStreamParser.decodeByteSegment(bitSource, stringBuilder, n3, characterSetECI, arrayList2, object2);
                        characterSetECI2 = characterSetECI;
                        n3 = n4;
                        n2 = n5;
                        bl3 = bl2;
                        break block13;
                    } else {
                        if (mode2 != Mode.KANJI) throw FormatException.getFormatInstance();
                        DecodedBitStreamParser.decodeKanjiSegment(bitSource, stringBuilder, n3);
                        characterSetECI2 = characterSetECI;
                        n3 = n4;
                        n2 = n5;
                        bl3 = bl2;
                    }
                    break block13;
                }
                bl3 = true;
                characterSetECI2 = characterSetECI;
                n3 = n4;
                n2 = n5;
            }
            mode = Mode.TERMINATOR;
            characterSetECI = characterSetECI2;
            n4 = n3;
            n5 = n2;
            bl2 = bl3;
        } while (mode2 != mode);
        object2 = stringBuilder.toString();
        arrayList = arrayList2.isEmpty() ? null : arrayList2;
        if (object == null) {
            object = null;
            return new DecoderResult(byArray, (String)object2, arrayList, (String)object, n3, n2);
        }
        object = ((Enum)object).toString();
        return new DecoderResult(byArray, (String)object2, arrayList, (String)object, n3, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void decodeAlphanumericSegment(BitSource bitSource, StringBuilder stringBuilder, int n2, boolean bl2) throws FormatException {
        int n3 = stringBuilder.length();
        while (n2 > 1) {
            if (bitSource.available() < 11) {
                throw FormatException.getFormatInstance();
            }
            int n4 = bitSource.readBits(11);
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n4 / 45));
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n4 % 45));
            n2 -= 2;
        }
        if (n2 == 1) {
            if (bitSource.available() < 6) {
                throw FormatException.getFormatInstance();
            }
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(bitSource.readBits(6)));
        }
        if (bl2) {
            for (n2 = n3; n2 < stringBuilder.length(); ++n2) {
                if (stringBuilder.charAt(n2) != '%') continue;
                if (n2 < stringBuilder.length() - 1 && stringBuilder.charAt(n2 + 1) == '%') {
                    stringBuilder.deleteCharAt(n2 + 1);
                    continue;
                }
                stringBuilder.setCharAt(n2, '\u001d');
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void decodeByteSegment(BitSource object, StringBuilder stringBuilder, int n2, CharacterSetECI characterSetECI, Collection<byte[]> collection, Map<DecodeHintType, ?> map) throws FormatException {
        if (n2 * 8 > ((BitSource)object).available()) {
            throw FormatException.getFormatInstance();
        }
        byte[] byArray = new byte[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            byArray[i2] = (byte)((BitSource)object).readBits(8);
        }
        object = characterSetECI == null ? StringUtils.guessEncoding(byArray, map) : characterSetECI.name();
        try {
            stringBuilder.append(new String(byArray, (String)object));
            collection.add(byArray);
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw FormatException.getFormatInstance();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void decodeHanziSegment(BitSource bitSource, StringBuilder stringBuilder, int n2) throws FormatException {
        if (n2 * 13 > bitSource.available()) {
            throw FormatException.getFormatInstance();
        }
        byte[] byArray = new byte[n2 * 2];
        int n3 = 0;
        while (n2 > 0) {
            int n4 = bitSource.readBits(13);
            n4 = (n4 = n4 / 96 << 8 | n4 % 96) < 959 ? (n4 += 41377) : (n4 += 42657);
            byArray[n3] = (byte)(n4 >> 8 & 0xFF);
            byArray[n3 + 1] = (byte)(n4 & 0xFF);
            n3 += 2;
            --n2;
        }
        try {
            stringBuilder.append(new String(byArray, "GB2312"));
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw FormatException.getFormatInstance();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void decodeKanjiSegment(BitSource bitSource, StringBuilder stringBuilder, int n2) throws FormatException {
        if (n2 * 13 > bitSource.available()) {
            throw FormatException.getFormatInstance();
        }
        byte[] byArray = new byte[n2 * 2];
        int n3 = 0;
        while (n2 > 0) {
            int n4 = bitSource.readBits(13);
            n4 = (n4 = n4 / 192 << 8 | n4 % 192) < 7936 ? (n4 += 33088) : (n4 += 49472);
            byArray[n3] = (byte)(n4 >> 8);
            byArray[n3 + 1] = (byte)n4;
            n3 += 2;
            --n2;
        }
        try {
            stringBuilder.append(new String(byArray, "SJIS"));
            return;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw FormatException.getFormatInstance();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void decodeNumericSegment(BitSource bitSource, StringBuilder stringBuilder, int n2) throws FormatException {
        while (n2 >= 3) {
            if (bitSource.available() < 10) {
                throw FormatException.getFormatInstance();
            }
            int n3 = bitSource.readBits(10);
            if (n3 >= 1000) {
                throw FormatException.getFormatInstance();
            }
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n3 / 100));
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n3 / 10 % 10));
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n3 % 10));
            n2 -= 3;
        }
        if (n2 == 2) {
            if (bitSource.available() < 7) {
                throw FormatException.getFormatInstance();
            }
            n2 = bitSource.readBits(7);
            if (n2 >= 100) {
                throw FormatException.getFormatInstance();
            }
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n2 / 10));
            stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n2 % 10));
            return;
        }
        if (n2 != 1) return;
        if (bitSource.available() < 4) {
            throw FormatException.getFormatInstance();
        }
        n2 = bitSource.readBits(4);
        if (n2 >= 10) {
            throw FormatException.getFormatInstance();
        }
        stringBuilder.append(DecodedBitStreamParser.toAlphaNumericChar(n2));
    }

    private static int parseECIValue(BitSource bitSource) throws FormatException {
        int n2 = bitSource.readBits(8);
        if ((n2 & 0x80) == 0) {
            return n2 & 0x7F;
        }
        if ((n2 & 0xC0) == 128) {
            return (n2 & 0x3F) << 8 | bitSource.readBits(8);
        }
        if ((n2 & 0xE0) == 192) {
            return (n2 & 0x1F) << 16 | bitSource.readBits(16);
        }
        throw FormatException.getFormatInstance();
    }

    private static char toAlphaNumericChar(int n2) throws FormatException {
        if (n2 >= ALPHANUMERIC_CHARS.length) {
            throw FormatException.getFormatInstance();
        }
        return ALPHANUMERIC_CHARS[n2];
    }
}

