/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.qrcode.encoder;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.encoder.BlockPair;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.MaskUtil;
import com.google.zxing.qrcode.encoder.MatrixUtil;
import com.google.zxing.qrcode.encoder.QRCode;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

public final class Encoder {
    private static final int[] ALPHANUMERIC_TABLE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1};
    static final String DEFAULT_BYTE_MODE_ENCODING = "ISO-8859-1";

    private Encoder() {
    }

    static void append8BitBytes(String object, BitArray bitArray, String string2) throws WriterException {
        try {
            object = ((String)object).getBytes(string2);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new WriterException(unsupportedEncodingException);
        }
        int n2 = ((Object)object).length;
        for (int i2 = 0; i2 < n2; ++i2) {
            bitArray.appendBits((int)object[i2], 8);
        }
    }

    static void appendAlphanumericBytes(CharSequence charSequence, BitArray bitArray) throws WriterException {
        int n2 = charSequence.length();
        int n3 = 0;
        while (n3 < n2) {
            int n4 = Encoder.getAlphanumericCode(charSequence.charAt(n3));
            if (n4 == -1) {
                throw new WriterException();
            }
            if (n3 + 1 < n2) {
                int n5 = Encoder.getAlphanumericCode(charSequence.charAt(n3 + 1));
                if (n5 == -1) {
                    throw new WriterException();
                }
                bitArray.appendBits(n4 * 45 + n5, 11);
                n3 += 2;
                continue;
            }
            bitArray.appendBits(n4, 6);
            ++n3;
        }
    }

    static void appendBytes(String string2, Mode mode, BitArray bitArray, String string3) throws WriterException {
        switch (1.$SwitchMap$com$google$zxing$qrcode$decoder$Mode[mode.ordinal()]) {
            default: {
                throw new WriterException("Invalid mode: " + (Object)((Object)mode));
            }
            case 1: {
                Encoder.appendNumericBytes(string2, bitArray);
                return;
            }
            case 2: {
                Encoder.appendAlphanumericBytes(string2, bitArray);
                return;
            }
            case 3: {
                Encoder.append8BitBytes(string2, bitArray, string3);
                return;
            }
            case 4: 
        }
        Encoder.appendKanjiBytes(string2, bitArray);
    }

    private static void appendECI(CharacterSetECI characterSetECI, BitArray bitArray) {
        bitArray.appendBits(Mode.ECI.getBits(), 4);
        bitArray.appendBits(characterSetECI.getValue(), 8);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void appendKanjiBytes(String object, BitArray bitArray) throws WriterException {
        try {
            object = ((String)object).getBytes("Shift_JIS");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new WriterException(unsupportedEncodingException);
        }
        int n2 = ((Object)object).length;
        int n3 = 0;
        while (n3 < n2) {
            int n4;
            int n5 = (object[n3] & 0xFF) << 8 | object[n3 + 1] & 0xFF;
            int n6 = -1;
            if (n5 >= 33088 && n5 <= 40956) {
                n4 = n5 - 33088;
            } else {
                n4 = n6;
                if (n5 >= 57408) {
                    n4 = n6;
                    if (n5 <= 60351) {
                        n4 = n5 - 49472;
                    }
                }
            }
            if (n4 == -1) {
                throw new WriterException("Invalid byte sequence");
            }
            bitArray.appendBits((n4 >> 8) * 192 + (n4 & 0xFF), 13);
            n3 += 2;
        }
        return;
    }

    static void appendLengthInfo(int n2, Version version, Mode mode, BitArray bitArray) throws WriterException {
        int n3 = mode.getCharacterCountBits(version);
        if (n2 >= 1 << n3) {
            throw new WriterException(n2 + " is bigger than " + ((1 << n3) - 1));
        }
        bitArray.appendBits(n2, n3);
    }

    static void appendModeInfo(Mode mode, BitArray bitArray) {
        bitArray.appendBits(mode.getBits(), 4);
    }

    static void appendNumericBytes(CharSequence charSequence, BitArray bitArray) {
        int n2 = charSequence.length();
        int n3 = 0;
        while (n3 < n2) {
            int n4 = charSequence.charAt(n3) - 48;
            if (n3 + 2 < n2) {
                bitArray.appendBits(n4 * 100 + (charSequence.charAt(n3 + 1) - 48) * 10 + (charSequence.charAt(n3 + 2) - 48), 10);
                n3 += 3;
                continue;
            }
            if (n3 + 1 < n2) {
                bitArray.appendBits(n4 * 10 + (charSequence.charAt(n3 + 1) - 48), 7);
                n3 += 2;
                continue;
            }
            bitArray.appendBits(n4, 4);
            ++n3;
        }
    }

    private static int calculateMaskPenalty(ByteMatrix byteMatrix) {
        return MaskUtil.applyMaskPenaltyRule1(byteMatrix) + MaskUtil.applyMaskPenaltyRule2(byteMatrix) + MaskUtil.applyMaskPenaltyRule3(byteMatrix) + MaskUtil.applyMaskPenaltyRule4(byteMatrix);
    }

    private static int chooseMaskPattern(BitArray bitArray, ErrorCorrectionLevel errorCorrectionLevel, Version version, ByteMatrix byteMatrix) throws WriterException {
        int n2 = Integer.MAX_VALUE;
        int n3 = -1;
        for (int i2 = 0; i2 < 8; ++i2) {
            MatrixUtil.buildMatrix(bitArray, errorCorrectionLevel, version, i2, byteMatrix);
            int n4 = Encoder.calculateMaskPenalty(byteMatrix);
            int n5 = n2;
            if (n4 < n2) {
                n5 = n4;
                n3 = i2;
            }
            n2 = n5;
        }
        return n3;
    }

    public static Mode chooseMode(String string2) {
        return Encoder.chooseMode(string2, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Mode chooseMode(String string2, String string3) {
        if ("Shift_JIS".equals(string3)) {
            if (Encoder.isOnlyDoubleByteKanji(string2)) {
                return Mode.KANJI;
            }
            return Mode.BYTE;
        }
        boolean bl2 = false;
        boolean bl3 = false;
        for (int i2 = 0; i2 < string2.length(); ++i2) {
            char c2 = string2.charAt(i2);
            if (c2 >= '0' && c2 <= '9') {
                bl2 = true;
                continue;
            }
            if (Encoder.getAlphanumericCode(c2) == -1) {
                return Mode.BYTE;
            }
            bl3 = true;
        }
        if (bl3) {
            return Mode.ALPHANUMERIC;
        }
        if (bl2) {
            return Mode.NUMERIC;
        }
        return Mode.BYTE;
    }

    private static Version chooseVersion(int n2, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        for (int i2 = 1; i2 <= 40; ++i2) {
            Version version = Version.getVersionForNumber(i2);
            if (version.getTotalCodewords() - version.getECBlocksForLevel(errorCorrectionLevel).getTotalECCodewords() < (n2 + 7) / 8) continue;
            return version;
        }
        throw new WriterException("Data too big");
    }

    public static QRCode encode(String string2, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        return Encoder.encode(string2, errorCorrectionLevel, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static QRCode encode(String object, ErrorCorrectionLevel errorCorrectionLevel, Map<EncodeHintType, ?> object2) throws WriterException {
        Object object3;
        object2 = object2 == null ? null : (String)object2.get((Object)EncodeHintType.CHARACTER_SET);
        Object object4 = object2;
        if (object2 == null) {
            object4 = DEFAULT_BYTE_MODE_ENCODING;
        }
        object2 = Encoder.chooseMode((String)object, (String)object4);
        Object object5 = new BitArray();
        if (object2 == Mode.BYTE && !DEFAULT_BYTE_MODE_ENCODING.equals(object4) && (object3 = CharacterSetECI.getCharacterSetECIByName((String)object4)) != null) {
            Encoder.appendECI((CharacterSetECI)((Object)object3), (BitArray)object5);
        }
        Encoder.appendModeInfo((Mode)((Object)object2), (BitArray)object5);
        object3 = new BitArray();
        Encoder.appendBytes((String)object, (Mode)((Object)object2), (BitArray)object3, (String)object4);
        object4 = Encoder.chooseVersion(((BitArray)object5).getSize() + ((Mode)((Object)object2)).getCharacterCountBits(Version.getVersionForNumber(1)) + ((BitArray)object3).getSize(), errorCorrectionLevel);
        object4 = Encoder.chooseVersion(((BitArray)object5).getSize() + ((Mode)((Object)object2)).getCharacterCountBits((Version)object4) + ((BitArray)object3).getSize(), errorCorrectionLevel);
        BitArray bitArray = new BitArray();
        bitArray.appendBitArray((BitArray)object5);
        int n2 = object2 == Mode.BYTE ? ((BitArray)object3).getSizeInBytes() : ((String)object).length();
        Encoder.appendLengthInfo(n2, (Version)object4, (Mode)((Object)object2), bitArray);
        bitArray.appendBitArray((BitArray)object3);
        object = ((Version)object4).getECBlocksForLevel(errorCorrectionLevel);
        n2 = ((Version)object4).getTotalCodewords() - ((Version.ECBlocks)object).getTotalECCodewords();
        Encoder.terminateBits(n2, bitArray);
        object = Encoder.interleaveWithECBytes(bitArray, ((Version)object4).getTotalCodewords(), n2, ((Version.ECBlocks)object).getNumBlocks());
        object5 = new QRCode();
        ((QRCode)object5).setECLevel(errorCorrectionLevel);
        ((QRCode)object5).setMode((Mode)((Object)object2));
        ((QRCode)object5).setVersion((Version)object4);
        n2 = ((Version)object4).getDimensionForVersion();
        object2 = new ByteMatrix(n2, n2);
        n2 = Encoder.chooseMaskPattern((BitArray)object, errorCorrectionLevel, (Version)object4, (ByteMatrix)object2);
        ((QRCode)object5).setMaskPattern(n2);
        MatrixUtil.buildMatrix((BitArray)object, errorCorrectionLevel, (Version)object4, n2, (ByteMatrix)object2);
        ((QRCode)object5).setMatrix((ByteMatrix)object2);
        return object5;
    }

    static byte[] generateECBytes(byte[] byArray, int n2) {
        int n3;
        int n4 = byArray.length;
        int[] nArray = new int[n4 + n2];
        for (n3 = 0; n3 < n4; ++n3) {
            nArray[n3] = byArray[n3] & 0xFF;
        }
        new ReedSolomonEncoder(GenericGF.QR_CODE_FIELD_256).encode(nArray, n2);
        byArray = new byte[n2];
        for (n3 = 0; n3 < n2; ++n3) {
            byArray[n3] = (byte)nArray[n4 + n3];
        }
        return byArray;
    }

    static int getAlphanumericCode(int n2) {
        if (n2 < ALPHANUMERIC_TABLE.length) {
            return ALPHANUMERIC_TABLE[n2];
        }
        return -1;
    }

    static void getNumDataBytesAndNumECBytesForBlockID(int n2, int n3, int n4, int n5, int[] nArray, int[] nArray2) throws WriterException {
        int n6;
        if (n5 >= n4) {
            throw new WriterException("Block ID too large");
        }
        int n7 = n2 % n4;
        int n8 = n4 - n7;
        int n9 = n2 / n4;
        int n10 = n9 - (n3 /= n4);
        if (n10 != (n9 = n9 + 1 - (n6 = n3 + 1))) {
            throw new WriterException("EC bytes mismatch");
        }
        if (n4 != n8 + n7) {
            throw new WriterException("RS blocks mismatch");
        }
        if (n2 != (n3 + n10) * n8 + (n6 + n9) * n7) {
            throw new WriterException("Total bytes mismatch");
        }
        if (n5 < n8) {
            nArray[0] = n3;
            nArray2[0] = n10;
            return;
        }
        nArray[0] = n6;
        nArray2[0] = n9;
    }

    static BitArray interleaveWithECBytes(BitArray bitArray, int n2, int n3, int n4) throws WriterException {
        byte[] byArray;
        Object object;
        if (bitArray.getSizeInBytes() != n3) {
            throw new WriterException("Number of bits and data bytes does not match");
        }
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        ArrayList<BlockPair> arrayList = new ArrayList<BlockPair>(n4);
        for (int i2 = 0; i2 < n4; ++i2) {
            object = new int[1];
            Object[] objectArray = new int[1];
            Encoder.getNumDataBytesAndNumECBytesForBlockID(n2, n3, n4, i2, (int[])object, objectArray);
            int n8 = object[0];
            byArray = new byte[n8];
            bitArray.toBytes(n5 * 8, byArray, 0, n8);
            objectArray = Encoder.generateECBytes(byArray, objectArray[0]);
            arrayList.add(new BlockPair(byArray, (byte[])objectArray));
            n6 = Math.max(n6, n8);
            n7 = Math.max(n7, objectArray.length);
            n5 += object[0];
        }
        if (n3 != n5) {
            throw new WriterException("Data bytes does not match offset");
        }
        bitArray = new BitArray();
        for (n3 = 0; n3 < n6; ++n3) {
            object = arrayList.iterator();
            while (object.hasNext()) {
                byArray = ((BlockPair)object.next()).getDataBytes();
                if (n3 >= byArray.length) continue;
                bitArray.appendBits(byArray[n3], 8);
            }
        }
        for (n3 = 0; n3 < n7; ++n3) {
            object = arrayList.iterator();
            while (object.hasNext()) {
                byArray = ((BlockPair)object.next()).getErrorCorrectionBytes();
                if (n3 >= byArray.length) continue;
                bitArray.appendBits(byArray[n3], 8);
            }
        }
        if (n2 != bitArray.getSizeInBytes()) {
            throw new WriterException("Interleaving error: " + n2 + " and " + bitArray.getSizeInBytes() + " differ.");
        }
        return bitArray;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean isOnlyDoubleByteKanji(String object) {
        try {
            object = ((String)object).getBytes("Shift_JIS");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return false;
        }
        int n2 = ((Object)object).length;
        if (n2 % 2 != 0) {
            return false;
        }
        int n3 = 0;
        while (n3 < n2) {
            int n4 = object[n3] & 0xFF;
            if (!(n4 >= 129 && n4 <= 159 || n4 >= 224 && n4 <= 235)) {
                return false;
            }
            n3 += 2;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    static void terminateBits(int n2, BitArray bitArray) throws WriterException {
        int n3;
        int n4 = n2 * 8;
        if (bitArray.getSize() > n4) {
            throw new WriterException("data bits cannot fit in the QR Code" + bitArray.getSize() + " > " + n4);
        }
        for (n3 = 0; n3 < 4 && bitArray.getSize() < n4; ++n3) {
            bitArray.appendBit(false);
        }
        n3 = bitArray.getSize() & 7;
        if (n3 > 0) {
            while (n3 < 8) {
                bitArray.appendBit(false);
                ++n3;
            }
        }
        int n5 = bitArray.getSizeInBytes();
        for (n3 = 0; n3 < n2 - n5; ++n3) {
            int n6 = (n3 & 1) == 0 ? 236 : 17;
            bitArray.appendBits(n6, 8);
        }
        if (bitArray.getSize() != n4) {
            throw new WriterException("Bits size does not equal capacity");
        }
    }
}

