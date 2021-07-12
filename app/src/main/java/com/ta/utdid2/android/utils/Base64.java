/*
 * Decompiled with CFR 0.151.
 */
package com.ta.utdid2.android.utils;

import java.io.UnsupportedEncodingException;

public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final int CRLF = 4;
    public static final int DEFAULT = 0;
    public static final int NO_CLOSE = 16;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int URL_SAFE = 8;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl2 = !Base64.class.desiredAssertionStatus();
        $assertionsDisabled = bl2;
    }

    private Base64() {
    }

    public static byte[] decode(String string2, int n2) {
        return Base64.decode(string2.getBytes(), n2);
    }

    public static byte[] decode(byte[] byArray, int n2) {
        return Base64.decode(byArray, 0, byArray.length, n2);
    }

    public static byte[] decode(byte[] byArray, int n2, int n3, int n4) {
        Decoder decoder = new Decoder(n4, new byte[n3 * 3 / 4]);
        if (!decoder.process(byArray, n2, n3, true)) {
            throw new IllegalArgumentException("bad base-64");
        }
        if (decoder.op == decoder.output.length) {
            return decoder.output;
        }
        byArray = new byte[decoder.op];
        System.arraycopy(decoder.output, 0, byArray, 0, decoder.op);
        return byArray;
    }

    public static byte[] encode(byte[] byArray, int n2) {
        return Base64.encode(byArray, 0, byArray.length, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static byte[] encode(byte[] byArray, int n2, int n3, int n4) {
        Encoder encoder = new Encoder(n4, null);
        int n5 = n3 / 3 * 4;
        if (encoder.do_padding) {
            n4 = n5;
            if (n3 % 3 > 0) {
                n4 = n5 + 4;
            }
        } else {
            n4 = n5;
            switch (n3 % 3) {
                case 0: {
                    break;
                }
                default: {
                    n4 = n5;
                    break;
                }
                case 1: {
                    n4 = n5 + 2;
                    break;
                }
                case 2: {
                    n4 = n5 + 3;
                }
            }
        }
        n5 = n4;
        if (encoder.do_newline) {
            n5 = n4;
            if (n3 > 0) {
                int n6 = (n3 - 1) / 57;
                n5 = encoder.do_cr ? 2 : 1;
                n5 = n4 + n5 * (n6 + 1);
            }
        }
        encoder.output = new byte[n5];
        encoder.process(byArray, n2, n3, true);
        if (!$assertionsDisabled && encoder.op != n5) {
            throw new AssertionError();
        }
        return encoder.output;
    }

    public static String encodeToString(byte[] object, int n2) {
        try {
            object = new String(Base64.encode(object, n2), "US-ASCII");
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError((Object)unsupportedEncodingException);
        }
    }

    public static String encodeToString(byte[] object, int n2, int n3, int n4) {
        try {
            object = new String(Base64.encode(object, n2, n3, n4), "US-ASCII");
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new AssertionError((Object)unsupportedEncodingException);
        }
    }

    static abstract class Coder {
        public int op;
        public byte[] output;

        Coder() {
        }

        public abstract int maxOutputSize(int var1);

        public abstract boolean process(byte[] var1, int var2, int var3, boolean var4);
    }

    static class Decoder
    extends Coder {
        private static final int[] DECODE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] DECODE_WEBSAFE = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int EQUALS = -2;
        private static final int SKIP = -1;
        private final int[] alphabet;
        private int state;
        private int value;

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public Decoder(int n2, byte[] objectArray) {
            void var2_4;
            this.output = objectArray;
            if ((n2 & 8) == 0) {
                int[] nArray = DECODE;
            } else {
                int[] nArray = DECODE_WEBSAFE;
            }
            this.alphabet = var2_4;
            this.state = 0;
            this.value = 0;
        }

        @Override
        public int maxOutputSize(int n2) {
            return n2 * 3 / 4 + 10;
        }

        /*
         * Unable to fully structure code
         */
        @Override
        public boolean process(byte[] var1_1, int var2_2, int var3_3, boolean var4_4) {
            if (this.state == 6) {
                return false;
            }
            var6_5 = var2_2;
            var11_6 = var3_3 + var2_2;
            var9_7 = this.state;
            var3_3 = this.value;
            var5_8 = 0;
            var12_9 = this.output;
            var13_10 = this.alphabet;
            block15: while (var6_5 < var11_6) {
                block24: {
                    var7_11 = var5_8;
                    var10_13 = var6_5;
                    var8_12 = var3_3;
                    if (var9_7 == 0) {
                        var2_2 = var3_3;
                        while (var6_5 + 4 <= var11_6) {
                            var2_2 = var3_3 = var13_10[var1_1[var6_5] & 255] << 18 | var13_10[var1_1[var6_5 + 1] & 255] << 12 | var13_10[var1_1[var6_5 + 2] & 255] << 6 | var13_10[var1_1[var6_5 + 3] & 255];
                            if (var3_3 < 0) break;
                            var12_9[var5_8 + 2] = (byte)var3_3;
                            var12_9[var5_8 + 1] = (byte)(var3_3 >> 8);
                            var12_9[var5_8] = (byte)(var3_3 >> 16);
                            var5_8 += 3;
                            var6_5 += 4;
                            var2_2 = var3_3;
                        }
                        var7_11 = var5_8;
                        var10_13 = var6_5;
                        var8_12 = var2_2;
                        if (var6_5 >= var11_6) {
                            var3_3 = var2_2;
                            var2_2 = var5_8;
lbl32:
                            // 2 sources

                            while (true) {
                                if (!var4_4) {
                                    this.state = var9_7;
                                    this.value = var3_3;
                                    this.op = var2_2;
                                    return true;
                                }
                                break block24;
                                break;
                            }
                        }
                    }
                    var6_5 = var13_10[var1_1[var10_13] & 255];
                    switch (var9_7) {
                        default: {
                            var3_3 = var8_12;
                            var2_2 = var9_7;
                            var5_8 = var7_11;
lbl45:
                            // 14 sources

                            while (true) {
                                var6_5 = var10_13 + 1;
                                var9_7 = var2_2;
                                continue block15;
                                break;
                            }
                        }
                        case 0: {
                            if (var6_5 < 0) ** GOTO lbl55
                            var3_3 = var6_5;
                            var2_2 = var9_7 + 1;
                            var5_8 = var7_11;
                            ** GOTO lbl45
lbl55:
                            // 1 sources

                            var5_8 = var7_11;
                            var2_2 = var9_7;
                            var3_3 = var8_12;
                            if (var6_5 == -1) ** GOTO lbl45
                            this.state = 6;
                            return false;
                        }
                        case 1: {
                            if (var6_5 < 0) ** GOTO lbl67
                            var3_3 = var8_12 << 6 | var6_5;
                            var2_2 = var9_7 + 1;
                            var5_8 = var7_11;
                            ** GOTO lbl45
lbl67:
                            // 1 sources

                            var5_8 = var7_11;
                            var2_2 = var9_7;
                            var3_3 = var8_12;
                            if (var6_5 == -1) ** GOTO lbl45
                            this.state = 6;
                            return false;
                        }
                        case 2: {
                            if (var6_5 < 0) ** GOTO lbl79
                            var3_3 = var8_12 << 6 | var6_5;
                            var2_2 = var9_7 + 1;
                            var5_8 = var7_11;
                            ** GOTO lbl45
lbl79:
                            // 1 sources

                            if (var6_5 != -2) ** GOTO lbl85
                            var12_9[var7_11] = (byte)(var8_12 >> 4);
                            var2_2 = 4;
                            var5_8 = var7_11 + 1;
                            var3_3 = var8_12;
                            ** GOTO lbl45
lbl85:
                            // 1 sources

                            var5_8 = var7_11;
                            var2_2 = var9_7;
                            var3_3 = var8_12;
                            if (var6_5 == -1) ** GOTO lbl45
                            this.state = 6;
                            return false;
                        }
                        case 3: {
                            if (var6_5 < 0) ** GOTO lbl100
                            var3_3 = var8_12 << 6 | var6_5;
                            var12_9[var7_11 + 2] = (byte)var3_3;
                            var12_9[var7_11 + 1] = (byte)(var3_3 >> 8);
                            var12_9[var7_11] = (byte)(var3_3 >> 16);
                            var5_8 = var7_11 + 3;
                            var2_2 = 0;
                            ** GOTO lbl45
lbl100:
                            // 1 sources

                            if (var6_5 != -2) ** GOTO lbl107
                            var12_9[var7_11 + 1] = (byte)(var8_12 >> 2);
                            var12_9[var7_11] = (byte)(var8_12 >> 10);
                            var5_8 = var7_11 + 2;
                            var2_2 = 5;
                            var3_3 = var8_12;
                            ** GOTO lbl45
lbl107:
                            // 1 sources

                            var5_8 = var7_11;
                            var2_2 = var9_7;
                            var3_3 = var8_12;
                            if (var6_5 == -1) ** GOTO lbl45
                            this.state = 6;
                            return false;
                        }
                        case 4: {
                            if (var6_5 != -2) ** GOTO lbl119
                            var2_2 = var9_7 + 1;
                            var5_8 = var7_11;
                            var3_3 = var8_12;
                            ** GOTO lbl45
lbl119:
                            // 1 sources

                            var5_8 = var7_11;
                            var2_2 = var9_7;
                            var3_3 = var8_12;
                            if (var6_5 == -1) ** GOTO lbl45
                            this.state = 6;
                            return false;
                        }
                        case 5: 
                    }
                    var5_8 = var7_11;
                    var2_2 = var9_7;
                    var3_3 = var8_12;
                    if (var6_5 != -1) ** break;
                    ** continue;
                    this.state = 6;
                    return false;
                }
                switch (var9_7) lbl-1000:
                // 4 sources

                {
                    default: {
                        this.state = var9_7;
                        this.op = var2_2;
                        return true;
                    }
                    case 0: {
                        ** GOTO lbl-1000
                    }
                    case 1: {
                        this.state = 6;
                        return false;
                    }
                    case 2: {
                        var5_8 = var2_2 + 1;
                        var12_9[var2_2] = (byte)(var3_3 >> 4);
                        var2_2 = var5_8;
                        ** GOTO lbl-1000
                    }
                    case 3: {
                        var5_8 = var2_2 + 1;
                        var12_9[var2_2] = (byte)(var3_3 >> 10);
                        var12_9[var5_8] = (byte)(var3_3 >> 2);
                        var2_2 = var5_8 + 1;
                        ** GOTO lbl-1000
                    }
                    case 4: 
                }
                this.state = 6;
                return false;
            }
            var2_2 = var5_8;
            ** while (true)
        }
    }

    static class Encoder
    extends Coder {
        static final /* synthetic */ boolean $assertionsDisabled;
        private static final byte[] ENCODE;
        private static final byte[] ENCODE_WEBSAFE;
        public static final int LINE_GROUPS = 19;
        private final byte[] alphabet;
        private int count;
        public final boolean do_cr;
        public final boolean do_newline;
        public final boolean do_padding;
        private final byte[] tail;
        int tailLen;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl2 = !Base64.class.desiredAssertionStatus();
            $assertionsDisabled = bl2;
            ENCODE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
            ENCODE_WEBSAFE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        }

        /*
         * Enabled aggressive block sorting
         */
        public Encoder(int n2, byte[] byArray) {
            boolean bl2 = true;
            this.output = byArray;
            boolean bl3 = (n2 & 1) == 0;
            this.do_padding = bl3;
            bl3 = (n2 & 2) == 0;
            this.do_newline = bl3;
            bl3 = (n2 & 4) != 0 ? bl2 : false;
            this.do_cr = bl3;
            byArray = (n2 & 8) == 0 ? ENCODE : ENCODE_WEBSAFE;
            this.alphabet = byArray;
            this.tail = new byte[2];
            this.tailLen = 0;
            n2 = this.do_newline ? 19 : -1;
            this.count = n2;
        }

        @Override
        public int maxOutputSize(int n2) {
            return n2 * 8 / 5 + 10;
        }

        /*
         * Handled duff style switch with additional control
         * Handled impossible loop by duplicating code
         * Enabled aggressive block sorting
         */
        @Override
        public boolean process(byte[] byArray, int n2, int n3, boolean bl2) {
            int n4;
            byte[] byArray2 = this.alphabet;
            byte[] byArray3 = this.output;
            int n5 = 0;
            int n6 = this.count;
            int n7 = n2;
            int n8 = n3 + n2;
            n3 = -1;
            n2 = n7;
            int n9 = n3;
            int n10 = Integer.MIN_VALUE;
            block5: do {
                switch (n10 == Integer.MIN_VALUE ? this.tailLen : n10) {
                    default: {
                        n9 = n3;
                        n2 = n7;
                        break;
                    }
                    case 1: {
                        n2 = n7;
                        n9 = n3;
                        n10 = 0;
                        if (n7 + 2 > n8) continue block5;
                        n3 = this.tail[0];
                        n9 = n7 + 1;
                        n7 = byArray[n7];
                        n2 = n9 + 1;
                        n9 = (n3 & 0xFF) << 16 | (n7 & 0xFF) << 8 | byArray[n9] & 0xFF;
                        this.tailLen = 0;
                        break;
                    }
                    case 2: {
                        n2 = n7;
                        n9 = n3;
                        if (n7 + 1 <= n8) {
                            n9 = (this.tail[0] & 0xFF) << 16 | (this.tail[1] & 0xFF) << 8 | byArray[n7] & 0xFF;
                            this.tailLen = 0;
                            n2 = n7 + 1;
                        }
                    }
                    case 0: 
                }
                break;
            } while (true);
            n3 = n6;
            n7 = n5;
            n5 = n2;
            if (n9 == -1) {
                n9 = n5;
                n2 = n7;
                n5 = n3;
                n3 = n9;
            } else {
                n7 = 0 + 1;
                byArray3[0] = byArray2[n9 >> 18 & 0x3F];
                n3 = n7 + 1;
                byArray3[n7] = byArray2[n9 >> 12 & 0x3F];
                n7 = n3 + 1;
                byArray3[n3] = byArray2[n9 >> 6 & 0x3F];
                n4 = n7 + 1;
                byArray3[n7] = byArray2[n9 & 0x3F];
                n3 = n9 = n6 - 1;
                n7 = n4;
                n5 = n2;
                if (n9 != 0) {
                    n9 = n5;
                    n2 = n7;
                    n5 = n3;
                    n3 = n9;
                } else {
                    n3 = n4;
                    if (this.do_cr) {
                        byArray3[n4] = 13;
                        n3 = n4 + 1;
                    }
                    n7 = n3 + 1;
                    byArray3[n3] = 10;
                    n5 = 19;
                    n3 = n2;
                    n2 = n7;
                }
            }
            while (true) {
                block38: {
                    block44: {
                        block39: {
                            block41: {
                                block43: {
                                    block42: {
                                        block40: {
                                            block37: {
                                                if (n3 + 3 > n8) break block37;
                                                n7 = (byArray[n3] & 0xFF) << 16 | (byArray[n3 + 1] & 0xFF) << 8 | byArray[n3 + 2] & 0xFF;
                                                byArray3[n2] = byArray2[n7 >> 18 & 0x3F];
                                                byArray3[n2 + 1] = byArray2[n7 >> 12 & 0x3F];
                                                byArray3[n2 + 2] = byArray2[n7 >> 6 & 0x3F];
                                                byArray3[n2 + 3] = byArray2[n7 & 0x3F];
                                                n9 = n3 + 3;
                                                n3 = n4 = n5 - 1;
                                                n7 = n2 += 4;
                                                n5 = n9;
                                                if (n4 == 0) {
                                                    n3 = n2;
                                                    if (this.do_cr) {
                                                        byArray3[n2] = 13;
                                                        n3 = n2 + 1;
                                                    }
                                                    n2 = n3 + 1;
                                                    byArray3[n3] = 10;
                                                    n5 = 19;
                                                    n3 = n9;
                                                    continue;
                                                }
                                                break block38;
                                            }
                                            if (!bl2) break block39;
                                            if (n3 - this.tailLen != n8 - 1) break block40;
                                            n7 = 0;
                                            if (this.tailLen > 0) {
                                                n9 = this.tail[0];
                                                n7 = 0 + 1;
                                            } else {
                                                n4 = n3 + 1;
                                                n9 = byArray[n3];
                                                n3 = n4;
                                            }
                                            n9 = (n9 & 0xFF) << 4;
                                            this.tailLen -= n7;
                                            n7 = n2 + 1;
                                            byArray3[n2] = byArray2[n9 >> 6 & 0x3F];
                                            n2 = n7 + 1;
                                            byArray3[n7] = byArray2[n9 & 0x3F];
                                            n9 = n2;
                                            if (this.do_padding) {
                                                n7 = n2 + 1;
                                                byArray3[n2] = 61;
                                                n9 = n7 + 1;
                                                byArray3[n7] = 61;
                                            }
                                            n2 = n9;
                                            n7 = n3;
                                            if (!this.do_newline) break block41;
                                            n2 = n9;
                                            if (this.do_cr) {
                                                byArray3[n9] = 13;
                                                n2 = n9 + 1;
                                            }
                                            n7 = n2 + 1;
                                            byArray3[n2] = 10;
                                            n2 = n7;
                                            break block42;
                                        }
                                        if (n3 - this.tailLen != n8 - 2) break block43;
                                        n7 = 0;
                                        if (this.tailLen > 1) {
                                            n9 = this.tail[0];
                                            n7 = 0 + 1;
                                        } else {
                                            n4 = n3 + 1;
                                            n9 = byArray[n3];
                                            n3 = n4;
                                        }
                                        if (this.tailLen > 0) {
                                            n4 = this.tail[n7];
                                            ++n7;
                                        } else {
                                            n4 = byArray[n3];
                                            ++n3;
                                        }
                                        n9 = (n9 & 0xFF) << 10 | (n4 & 0xFF) << 2;
                                        this.tailLen -= n7;
                                        n7 = n2 + 1;
                                        byArray3[n2] = byArray2[n9 >> 12 & 0x3F];
                                        n4 = n7 + 1;
                                        byArray3[n7] = byArray2[n9 >> 6 & 0x3F];
                                        n2 = n4 + 1;
                                        byArray3[n4] = byArray2[n9 & 0x3F];
                                        n9 = n2;
                                        if (this.do_padding) {
                                            byArray3[n2] = 61;
                                            n9 = n2 + 1;
                                        }
                                        n2 = n9;
                                        n7 = n3;
                                        if (!this.do_newline) break block41;
                                        n2 = n9;
                                        if (this.do_cr) {
                                            byArray3[n9] = 13;
                                            n2 = n9 + 1;
                                        }
                                        n7 = n2 + 1;
                                        byArray3[n2] = 10;
                                        n2 = n7;
                                    }
                                    n7 = n3;
                                    break block41;
                                }
                                n7 = n2;
                                if (this.do_newline) {
                                    n7 = n2;
                                    if (n2 > 0) {
                                        n7 = n2;
                                        if (n5 != 19) {
                                            if (this.do_cr) {
                                                n7 = n2 + 1;
                                                byArray3[n2] = 13;
                                                n2 = n7;
                                            }
                                            n7 = n2 + 1;
                                            byArray3[n2] = 10;
                                        }
                                    }
                                }
                                n2 = n7;
                                n7 = n3;
                            }
                            if (!$assertionsDisabled && this.tailLen != 0) {
                                throw new AssertionError();
                            }
                            n3 = n2;
                            if (!$assertionsDisabled) {
                                n3 = n2;
                                if (n7 != n8) {
                                    throw new AssertionError();
                                }
                            }
                            break block44;
                        }
                        if (n3 == n8 - 1) {
                            byArray2 = this.tail;
                            n7 = this.tailLen;
                            this.tailLen = n7 + 1;
                            byArray2[n7] = byArray[n3];
                            n3 = n2;
                        } else {
                            if (n3 == n8 - 2) {
                                byArray2 = this.tail;
                                n7 = this.tailLen;
                                this.tailLen = n7 + 1;
                                byArray2[n7] = byArray[n3];
                                byArray2 = this.tail;
                                n7 = this.tailLen;
                                this.tailLen = n7 + 1;
                                byArray2[n7] = byArray[n3 + 1];
                            }
                            n3 = n2;
                        }
                    }
                    this.op = n3;
                    this.count = n5;
                    return true;
                }
                n9 = n5;
                n2 = n7;
                n5 = n3;
                n3 = n9;
            }
        }
    }
}

