/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class q {
    public static final int a = 1;
    private static final int b = 1000;
    private static final int c = 1001;
    private static final int d = 1002;

    /*
     * Enabled aggressive block sorting
     */
    private static long a(long l2, int n2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l2);
        int n3 = calendar.get(11);
        int n4 = calendar.get(12) / 6;
        int n5 = calendar.get(13);
        int n6 = 0;
        if (n2 == 1002) {
            n6 = 360 - (calendar.get(12) % 6 * 60 + n5);
            return n6 * 1000;
        }
        if (n2 != 1001) return n6 * 1000;
        n6 = n2 = 60 - n5 % 60;
        if ((n4 + 1 + n3 * 10) % 6 != 0) return n6 * 1000;
        n6 = n2 + 60;
        return n6 * 1000;
    }

    public static String a(long l2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l2);
        int n2 = calendar.get(11);
        int n3 = calendar.get(12) / 6;
        l2 = q.d(l2);
        return String.valueOf((long)(n3 + 1 + n2 * 10 - 1) + l2 * 240L);
    }

    public static boolean a(long l2, long l3) {
        return q.e(l2) == q.e(l3);
    }

    public static long b(long l2) {
        return q.a(l2, 1001);
    }

    public static long c(long l2) {
        return q.a(l2, 1002);
    }

    private static long d(long l2) {
        long l3 = 0L;
        try {
            long l4 = new SimpleDateFormat("yyyy").parse("1970").getTime();
            long l5 = (l2 - l4) / 86400000L;
            if ((l2 - l4) % 86400000L > 0L) {
                l3 = 1L;
            }
            return l3 + l5;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return 0L;
        }
    }

    private static int e(long l2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l2);
        return calendar.get(5);
    }
}

