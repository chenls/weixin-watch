/*
 * Decompiled with CFR 0.151.
 */
package android.support.v4.util;

import java.io.PrintWriter;

public final class TimeUtils {
    public static final int HUNDRED_DAY_FIELD_LEN = 19;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    private static char[] sFormatStr;
    private static final Object sFormatSync;

    static {
        sFormatSync = new Object();
        sFormatStr = new char[24];
    }

    private TimeUtils() {
    }

    private static int accumField(int n2, int n3, boolean bl2, int n4) {
        if (n2 > 99 || bl2 && n4 >= 3) {
            return n3 + 3;
        }
        if (n2 > 9 || bl2 && n4 >= 2) {
            return n3 + 2;
        }
        if (bl2 || n2 > 0) {
            return n3 + 1;
        }
        return 0;
    }

    public static void formatDuration(long l2, long l3, PrintWriter printWriter) {
        if (l2 == 0L) {
            printWriter.print("--");
            return;
        }
        TimeUtils.formatDuration(l2 - l3, printWriter, 0);
    }

    public static void formatDuration(long l2, PrintWriter printWriter) {
        TimeUtils.formatDuration(l2, printWriter, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void formatDuration(long l2, PrintWriter printWriter, int n2) {
        Object object = sFormatSync;
        synchronized (object) {
            n2 = TimeUtils.formatDurationLocked(l2, n2);
            printWriter.print(new String(sFormatStr, 0, n2));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void formatDuration(long l2, StringBuilder stringBuilder) {
        Object object = sFormatSync;
        synchronized (object) {
            int n2 = TimeUtils.formatDurationLocked(l2, 0);
            stringBuilder.append(sFormatStr, 0, n2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static int formatDurationLocked(long l2, int n2) {
        boolean bl2;
        int n3;
        if (sFormatStr.length < n2) {
            sFormatStr = new char[n2];
        }
        char[] cArray = sFormatStr;
        if (l2 == 0L) {
            while (true) {
                if (n2 - 1 >= 0) {
                    cArray[0] = 48;
                    return 1;
                }
                cArray[0] = 32;
            }
        }
        if (l2 > 0L) {
            n3 = 43;
        } else {
            n3 = 45;
            l2 = -l2;
        }
        int n4 = (int)(l2 % 1000L);
        int n5 = (int)Math.floor(l2 / 1000L);
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = n5;
        if (n5 > 86400) {
            n6 = n5 / 86400;
            n9 = n5 - 86400 * n6;
        }
        n5 = n9;
        if (n9 > 3600) {
            n7 = n9 / 3600;
            n5 = n9 - n7 * 3600;
        }
        int n10 = n5;
        if (n5 > 60) {
            n8 = n5 / 60;
            n10 = n5 - n8 * 60;
        }
        int n11 = 0;
        int n12 = 0;
        if (n2 != 0) {
            n9 = TimeUtils.accumField(n6, 1, false, 0);
            bl2 = n9 > 0;
            bl2 = (n9 += TimeUtils.accumField(n7, 1, bl2, 2)) > 0;
            bl2 = (n9 += TimeUtils.accumField(n8, 1, bl2, 2)) > 0;
            n5 = n9 + TimeUtils.accumField(n10, 1, bl2, 2);
            n9 = n5 > 0 ? 3 : 0;
            n5 += TimeUtils.accumField(n4, 2, true, n9) + 1;
            n9 = n12;
            while (true) {
                n11 = n9++;
                if (n5 >= n2) break;
                cArray[n9] = 32;
                ++n5;
            }
        }
        cArray[n11] = n3;
        n5 = n11 + 1;
        n2 = n2 != 0 ? 1 : 0;
        bl2 = (n6 = TimeUtils.printField(cArray, n6, 'd', n5, false, 0)) != n5;
        n9 = n2 != 0 ? 2 : 0;
        bl2 = (n6 = TimeUtils.printField(cArray, n7, 'h', n6, bl2, n9)) != n5;
        n9 = n2 != 0 ? 2 : 0;
        bl2 = (n6 = TimeUtils.printField(cArray, n8, 'm', n6, bl2, n9)) != n5;
        n9 = n2 != 0 ? 2 : 0;
        n9 = TimeUtils.printField(cArray, n10, 's', n6, bl2, n9);
        n2 = n2 != 0 && n9 != n5 ? 3 : 0;
        n2 = TimeUtils.printField(cArray, n4, 'm', n9, true, n2);
        cArray[n2] = 115;
        return n2 + 1;
    }

    private static int printField(char[] cArray, int n2, char c2, int n3, boolean bl2, int n4) {
        int n5;
        block5: {
            block9: {
                int n6;
                block8: {
                    block7: {
                        block6: {
                            block4: {
                                if (bl2) break block4;
                                n5 = n3;
                                if (n2 <= 0) break block5;
                            }
                            if (bl2 && n4 >= 3) break block6;
                            n5 = n2;
                            n6 = n3;
                            if (n2 <= 99) break block7;
                        }
                        n5 = n2 / 100;
                        cArray[n3] = (char)(n5 + 48);
                        n6 = n3 + 1;
                        n5 = n2 - n5 * 100;
                    }
                    if (bl2 && n4 >= 2 || n5 > 9) break block8;
                    n4 = n5;
                    n2 = n6;
                    if (n3 == n6) break block9;
                }
                n3 = n5 / 10;
                cArray[n6] = (char)(n3 + 48);
                n2 = n6 + 1;
                n4 = n5 - n3 * 10;
            }
            cArray[n2] = (char)(n4 + 48);
            cArray[++n2] = c2;
            n5 = n2 + 1;
        }
        return n5;
    }
}

