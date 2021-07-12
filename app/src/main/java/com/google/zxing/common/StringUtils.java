/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common;

import com.google.zxing.DecodeHintType;
import java.nio.charset.Charset;
import java.util.Map;

public final class StringUtils {
    private static final boolean ASSUME_SHIFT_JIS;
    private static final String EUC_JP = "EUC_JP";
    public static final String GB2312 = "GB2312";
    private static final String ISO88591 = "ISO8859_1";
    private static final String PLATFORM_DEFAULT_ENCODING;
    public static final String SHIFT_JIS = "SJIS";
    private static final String UTF8 = "UTF8";

    /*
     * Enabled aggressive block sorting
     */
    static {
        PLATFORM_DEFAULT_ENCODING = Charset.defaultCharset().name();
        boolean bl2 = SHIFT_JIS.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING) || EUC_JP.equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING);
        ASSUME_SHIFT_JIS = bl2;
    }

    private StringUtils() {
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static String guessEncoding(byte[] object, Map<DecodeHintType, ?> object2) {
        int n2;
        int n3;
        String string2;
        if (string2 != null && (string2 = (String)string2.get((Object)DecodeHintType.CHARACTER_SET)) != null) {
            return string2;
        }
        int n4 = ((byte[])object).length;
        boolean bl2 = true;
        int n5 = 1;
        int n6 = 1;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        int n14 = 0;
        int n15 = 0;
        int n16 = 0;
        int n17 = 0;
        boolean bl3 = ((byte[])object).length > 3 && object[0] == -17 && object[1] == -69 && object[2] == -65;
        for (int i2 = 0; i2 < n4 && (bl2 || n5 != 0 || n6 != 0); ++i2) {
            boolean bl4;
            int n18;
            int n19;
            int n20;
            int n21;
            block47: {
                block49: {
                    block48: {
                        n21 = object[i2] & 0xFF;
                        n3 = n6;
                        n20 = n8;
                        n19 = n9;
                        n18 = n10;
                        n2 = n7;
                        if (n6 != 0) {
                            if (n7 > 0) {
                                if ((n21 & 0x80) == 0) {
                                    n3 = 0;
                                    n2 = n7;
                                    n18 = n10;
                                    n19 = n9;
                                    n20 = n8;
                                } else {
                                    n2 = n7 - 1;
                                    n3 = n6;
                                    n20 = n8;
                                    n19 = n9;
                                    n18 = n10;
                                }
                            } else {
                                n3 = n6;
                                n20 = n8;
                                n19 = n9;
                                n18 = n10;
                                n2 = n7;
                                if ((n21 & 0x80) != 0) {
                                    if ((n21 & 0x40) == 0) {
                                        n3 = 0;
                                        n20 = n8;
                                        n19 = n9;
                                        n18 = n10;
                                        n2 = n7;
                                    } else {
                                        n2 = n7 + 1;
                                        if ((n21 & 0x20) == 0) {
                                            n20 = n8 + 1;
                                            n3 = n6;
                                            n19 = n9;
                                            n18 = n10;
                                        } else {
                                            ++n2;
                                            if ((n21 & 0x10) == 0) {
                                                n19 = n9 + 1;
                                                n3 = n6;
                                                n20 = n8;
                                                n18 = n10;
                                            } else {
                                                ++n2;
                                                if ((n21 & 8) == 0) {
                                                    n18 = n10 + 1;
                                                    n3 = n6;
                                                    n20 = n8;
                                                    n19 = n9;
                                                } else {
                                                    n3 = 0;
                                                    n20 = n8;
                                                    n19 = n9;
                                                    n18 = n10;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        bl4 = bl2;
                        n7 = n17;
                        if (!bl2) break block47;
                        if (n21 <= 127 || n21 >= 160) break block48;
                        bl4 = false;
                        n7 = n17;
                        break block47;
                    }
                    bl4 = bl2;
                    n7 = n17;
                    if (n21 <= 159) break block47;
                    if (n21 < 192 || n21 == 215) break block49;
                    bl4 = bl2;
                    n7 = n17;
                    if (n21 != 247) break block47;
                }
                n7 = n17 + 1;
                bl4 = bl2;
            }
            n6 = n5;
            n8 = n11;
            n10 = n14;
            n9 = n13;
            int n22 = n12;
            int n23 = n16;
            int n24 = n15;
            if (n5 != 0) {
                if (n11 > 0) {
                    if (n21 < 64 || n21 == 127 || n21 > 252) {
                        n6 = 0;
                        n24 = n15;
                        n23 = n16;
                        n22 = n12;
                        n9 = n13;
                        n10 = n14;
                        n8 = n11;
                    } else {
                        n8 = n11 - 1;
                        n6 = n5;
                        n10 = n14;
                        n9 = n13;
                        n22 = n12;
                        n23 = n16;
                        n24 = n15;
                    }
                } else if (n21 == 128 || n21 == 160 || n21 > 239) {
                    n6 = 0;
                    n8 = n11;
                    n10 = n14;
                    n9 = n13;
                    n22 = n12;
                    n23 = n16;
                    n24 = n15;
                } else if (n21 > 160 && n21 < 224) {
                    n14 = n12 + 1;
                    n17 = 0;
                    n12 = n13 + 1;
                    n6 = n5;
                    n8 = n11;
                    n10 = n17;
                    n9 = n12;
                    n22 = n14;
                    n23 = n16;
                    n24 = n15;
                    if (n12 > n15) {
                        n24 = n12;
                        n6 = n5;
                        n8 = n11;
                        n10 = n17;
                        n9 = n12;
                        n22 = n14;
                        n23 = n16;
                    }
                } else if (n21 > 127) {
                    n13 = n11 + 1;
                    n17 = 0;
                    n11 = n14 + 1;
                    n6 = n5;
                    n8 = n13;
                    n10 = n11;
                    n9 = n17;
                    n22 = n12;
                    n23 = n16;
                    n24 = n15;
                    if (n11 > n16) {
                        n23 = n11;
                        n6 = n5;
                        n8 = n13;
                        n10 = n11;
                        n9 = n17;
                        n22 = n12;
                        n24 = n15;
                    }
                } else {
                    n9 = 0;
                    n10 = 0;
                    n6 = n5;
                    n8 = n11;
                    n22 = n12;
                    n23 = n16;
                    n24 = n15;
                }
            }
            bl2 = bl4;
            n5 = n6;
            n6 = n3;
            n17 = n7;
            n11 = n8;
            n14 = n10;
            n13 = n9;
            n12 = n22;
            n16 = n23;
            n15 = n24;
            n8 = n20;
            n9 = n19;
            n10 = n18;
            n7 = n2;
        }
        n2 = n6;
        if (n6 != 0) {
            n2 = n6;
            if (n7 > 0) {
                n2 = 0;
            }
        }
        n3 = n5;
        if (n5 != 0) {
            n3 = n5;
            if (n11 > 0) {
                n3 = 0;
            }
        }
        if (n2 != 0 && (bl3 || n8 + n9 + n10 > 0)) {
            return UTF8;
        }
        if (n3 != 0 && (ASSUME_SHIFT_JIS || n15 >= 3 || n16 >= 3)) {
            return SHIFT_JIS;
        }
        if (bl2 && n3 != 0) {
            void var0_2;
            if ((n15 != 2 || n12 != 2) && n17 * 10 < n4) {
                return var0_2;
            }
            String string3 = SHIFT_JIS;
            return var0_2;
        }
        if (bl2) {
            return ISO88591;
        }
        if (n3 != 0) {
            return SHIFT_JIS;
        }
        if (n2 != 0) {
            return UTF8;
        }
        return PLATFORM_DEFAULT_ENCODING;
    }
}

