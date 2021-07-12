/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.aztec.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import java.util.Arrays;

public final class Decoder {
    private static final String[] DIGIT_TABLE;
    private static final String[] LOWER_TABLE;
    private static final String[] MIXED_TABLE;
    private static final String[] PUNCT_TABLE;
    private static final String[] UPPER_TABLE;
    private AztecDetectorResult ddata;

    static {
        UPPER_TABLE = new String[]{"CTRL_PS", " ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "CTRL_LL", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
        LOWER_TABLE = new String[]{"CTRL_PS", " ", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "CTRL_US", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
        MIXED_TABLE = new String[]{"CTRL_PS", " ", "\u0001", "\u0002", "\u0003", "\u0004", "\u0005", "\u0006", "\u0007", "\b", "\t", "\n", "\u000b", "\f", "\r", "\u001b", "\u001c", "\u001d", "\u001e", "\u001f", "@", "\\", "^", "_", "`", "|", "~", "\u007f", "CTRL_LL", "CTRL_UL", "CTRL_PL", "CTRL_BS"};
        PUNCT_TABLE = new String[]{"", "\r", "\r\n", ". ", ", ", ": ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">", "?", "[", "]", "{", "}", "CTRL_UL"};
        DIGIT_TABLE = new String[]{"CTRL_PS", " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ",", ".", "CTRL_UL", "CTRL_US"};
    }

    /*
     * Unable to fully structure code
     */
    private boolean[] correctBits(boolean[] var1_1) throws FormatException {
        block22: {
            block24: {
                block23: {
                    block17: {
                        block19: {
                            block21: {
                                block20: {
                                    block18: {
                                        if (this.ddata.getNbLayers() > 2) break block18;
                                        var2_3 = 6;
                                        var10_4 = GenericGF.AZTEC_DATA_6;
lbl4:
                                        // 4 sources

                                        while ((var5_6 = var1_1.length / var2_3) < (var6_5 = this.ddata.getNbDatablocks())) {
                                            throw FormatException.getFormatInstance();
                                        }
                                        break block19;
                                    }
                                    if (this.ddata.getNbLayers() > 8) break block20;
                                    var2_3 = 8;
                                    var10_4 = GenericGF.AZTEC_DATA_8;
                                    ** GOTO lbl4
                                }
                                if (this.ddata.getNbLayers() > 22) break block21;
                                var2_3 = 10;
                                var10_4 = GenericGF.AZTEC_DATA_10;
                                ** GOTO lbl4
                            }
                            var2_3 = 12;
                            var10_4 = GenericGF.AZTEC_DATA_12;
                            ** GOTO lbl4
                        }
                        var3_7 = var1_1.length % var2_3;
                        var11_8 = new int[var5_6];
                        var4_9 = 0;
                        while (var4_9 < var5_6) {
                            var11_8[var4_9] = Decoder.readCode(var1_1, var3_7, var2_3);
                            ++var4_9;
                            var3_7 += var2_3;
                        }
                        try {
                            new ReedSolomonDecoder(var10_4).decode(var11_8, var5_6 - var6_5);
                            var7_10 = (1 << var2_3) - 1;
                            var4_9 = 0;
                            var3_7 = 0;
                        }
                        catch (ReedSolomonException var1_2) {
                            throw FormatException.getFormatInstance(var1_2);
                        }
lbl35:
                        // 2 sources

                        while (var3_7 < var6_5) {
                            var8_11 = var11_8[var3_7];
                            if (var8_11 == 0 || var8_11 == var7_10) {
                                throw FormatException.getFormatInstance();
                            }
                            break block17;
                        }
                        break block22;
                    }
                    if (var8_11 == 1) break block23;
                    var5_6 = var4_9;
                    if (var8_11 != var7_10 - 1) break block24;
                }
                var5_6 = var4_9 + 1;
            }
            ++var3_7;
            var4_9 = var5_6;
            ** GOTO lbl35
        }
        var1_1 = new boolean[var6_5 * var2_3 - var4_9];
        var3_7 = 0;
        block5: for (var4_9 = 0; var4_9 < var6_5; ++var4_9) {
            var8_11 = var11_8[var4_9];
            if (var8_11 == 1 || var8_11 == var7_10 - 1) {
                if (var8_11 > 1) {
                    var9_12 = true;
lbl61:
                    // 2 sources

                    while (true) {
                        Arrays.fill(var1_1, var3_7, var3_7 + var2_3 - 1, var9_12);
                        var3_7 += var2_3 - 1;
                        continue block5;
                        break;
                    }
                }
                var9_12 = false;
                ** continue;
            }
            var5_6 = var2_3 - 1;
            block8: while (true) {
                if (var5_6 < 0) ** continue;
                if ((1 << var5_6 & var8_11) == 0) break;
                var9_12 = true;
lbl73:
                // 2 sources

                while (true) {
                    var1_1[var3_7] = var9_12;
                    --var5_6;
                    ++var3_7;
                    continue block8;
                    break;
                }
                break;
            }
            var9_12 = false;
            ** continue;
        }
        return var1_1;
    }

    private static String getCharacter(Table table, int n2) {
        switch (1.$SwitchMap$com$google$zxing$aztec$decoder$Decoder$Table[table.ordinal()]) {
            default: {
                throw new IllegalStateException("Bad table");
            }
            case 1: {
                return UPPER_TABLE[n2];
            }
            case 2: {
                return LOWER_TABLE[n2];
            }
            case 3: {
                return MIXED_TABLE[n2];
            }
            case 4: {
                return PUNCT_TABLE[n2];
            }
            case 5: 
        }
        return DIGIT_TABLE[n2];
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String getEncodedData(boolean[] blArray) {
        int n2 = blArray.length;
        Table table = Table.UPPER;
        Table table2 = Table.UPPER;
        StringBuilder stringBuilder = new StringBuilder(20);
        int n3 = 0;
        block0: while (n3 < n2) {
            int n4;
            int n5;
            int n6;
            if (table2 == Table.BINARY) {
                if (n2 - n3 < 5) break;
                n6 = Decoder.readCode(blArray, n3, 5);
                n3 = n5 = n3 + 5;
                n4 = n6;
                if (n6 == 0) {
                    if (n2 - n5 < 11) break;
                    n4 = Decoder.readCode(blArray, n5, 11) + 31;
                    n3 = n5 + 11;
                }
            } else {
                n6 = table2 == Table.DIGIT ? 4 : 5;
                if (n2 - n3 < n6) break;
                n4 = Decoder.readCode(blArray, n3, n6);
                n6 = n3 + n6;
                String string2 = Decoder.getCharacter(table2, n4);
                if (string2.startsWith("CTRL_")) {
                    Table table3 = Decoder.getTable(string2.charAt(5));
                    n3 = n6;
                    table2 = table3;
                    if (string2.charAt(6) != 'L') continue;
                    table = table3;
                    n3 = n6;
                    table2 = table3;
                    continue;
                }
                stringBuilder.append(string2);
                table2 = table;
                n3 = n6;
                continue;
            }
            n5 = 0;
            while (true) {
                block11: {
                    block10: {
                        n6 = n3;
                        if (n5 >= n4) break block10;
                        if (n2 - n3 >= 8) break block11;
                        n6 = n2;
                    }
                    table2 = table;
                    n3 = n6;
                    continue block0;
                }
                stringBuilder.append((char)Decoder.readCode(blArray, n3, 8));
                n3 += 8;
                ++n5;
            }
        }
        return stringBuilder.toString();
    }

    private static Table getTable(char c2) {
        switch (c2) {
            default: {
                return Table.UPPER;
            }
            case 'L': {
                return Table.LOWER;
            }
            case 'P': {
                return Table.PUNCT;
            }
            case 'M': {
                return Table.MIXED;
            }
            case 'D': {
                return Table.DIGIT;
            }
            case 'B': 
        }
        return Table.BINARY;
    }

    public static String highLevelDecode(boolean[] blArray) {
        return Decoder.getEncodedData(blArray);
    }

    private static int readCode(boolean[] blArray, int n2, int n3) {
        int n4 = 0;
        for (int i2 = n2; i2 < n2 + n3; ++i2) {
            int n5;
            n4 = n5 = n4 << 1;
            if (!blArray[i2]) continue;
            n4 = n5 | 1;
        }
        return n4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int totalBitsInLayer(int n2, boolean bl2) {
        int n3;
        if (bl2) {
            n3 = 88;
            return (n3 + n2 * 16) * n2;
        }
        n3 = 112;
        return (n3 + n2 * 16) * n2;
    }

    public DecoderResult decode(AztecDetectorResult aztecDetectorResult) throws FormatException {
        this.ddata = aztecDetectorResult;
        return new DecoderResult(null, Decoder.getEncodedData(this.correctBits(this.extractBits(aztecDetectorResult.getBits()))), null, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean[] extractBits(BitMatrix bitMatrix) {
        int n2;
        int n3;
        int n4;
        int n5;
        boolean bl2 = this.ddata.isCompact();
        int n6 = this.ddata.getNbLayers();
        int n7 = bl2 ? n6 * 4 + 11 : n6 * 4 + 14;
        int[] nArray = new int[n7];
        boolean[] blArray = new boolean[Decoder.totalBitsInLayer(n6, bl2)];
        if (bl2) {
            for (n5 = 0; n5 < nArray.length; ++n5) {
                nArray[n5] = n5;
            }
        } else {
            n5 = (n7 / 2 - 1) / 15;
            n4 = n7 / 2;
            n3 = (n7 + 1 + n5 * 2) / 2;
            for (n5 = 0; n5 < n4; ++n5) {
                n2 = n5 + n5 / 15;
                nArray[n4 - n5 - 1] = n3 - n2 - 1;
                nArray[n4 + n5] = n3 + n2 + 1;
            }
        }
        n5 = 0;
        n4 = 0;
        while (n5 < n6) {
            n3 = bl2 ? (n6 - n5) * 4 + 9 : (n6 - n5) * 4 + 12;
            int n8 = n5 * 2;
            int n9 = n7 - 1 - n8;
            for (n2 = 0; n2 < n3; ++n2) {
                int n10 = n2 * 2;
                for (int i2 = 0; i2 < 2; ++i2) {
                    blArray[n4 + n10 + i2] = bitMatrix.get(nArray[n8 + i2], nArray[n8 + n2]);
                    blArray[n3 * 2 + n4 + n10 + i2] = bitMatrix.get(nArray[n8 + n2], nArray[n9 - i2]);
                    blArray[n3 * 4 + n4 + n10 + i2] = bitMatrix.get(nArray[n9 - i2], nArray[n9 - n2]);
                    blArray[n3 * 6 + n4 + n10 + i2] = bitMatrix.get(nArray[n9 - n2], nArray[n8 + i2]);
                }
            }
            n4 += n3 * 8;
            ++n5;
        }
        return blArray;
    }

    private static enum Table {
        UPPER,
        LOWER,
        MIXED,
        DIGIT,
        PUNCT,
        BINARY;

    }
}

