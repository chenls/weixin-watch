/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

public class bm {
    public static final byte a(byte by2, int n2, boolean bl2) {
        return (byte)bm.a((int)by2, n2, bl2);
    }

    public static final int a(int n2, int n3, boolean bl2) {
        if (bl2) {
            return 1 << n3 | n2;
        }
        return bm.b(n2, n3);
    }

    public static final int a(byte[] byArray) {
        return bm.a(byArray, 0);
    }

    public static final int a(byte[] byArray, int n2) {
        return (byArray[n2] & 0xFF) << 24 | (byArray[n2 + 1] & 0xFF) << 16 | (byArray[n2 + 2] & 0xFF) << 8 | byArray[n2 + 3] & 0xFF;
    }

    public static final long a(long l2, int n2, boolean bl2) {
        if (bl2) {
            return 1L << n2 | l2;
        }
        return bm.b(l2, n2);
    }

    public static final short a(short s2, int n2, boolean bl2) {
        return (short)bm.a((int)s2, n2, bl2);
    }

    public static final void a(int n2, byte[] byArray) {
        bm.a(n2, byArray, 0);
    }

    public static final void a(int n2, byte[] byArray, int n3) {
        byArray[n3] = (byte)(n2 >> 24 & 0xFF);
        byArray[n3 + 1] = (byte)(n2 >> 16 & 0xFF);
        byArray[n3 + 2] = (byte)(n2 >> 8 & 0xFF);
        byArray[n3 + 3] = (byte)(n2 & 0xFF);
    }

    public static final boolean a(byte by2, int n2) {
        return bm.a((int)by2, n2);
    }

    public static final boolean a(int n2, int n3) {
        return (1 << n3 & n2) != 0;
    }

    public static final boolean a(long l2, int n2) {
        return (1L << n2 & l2) != 0L;
    }

    public static final boolean a(short s2, int n2) {
        return bm.a((int)s2, n2);
    }

    public static final byte b(byte by2, int n2) {
        return (byte)bm.b((int)by2, n2);
    }

    public static final int b(int n2, int n3) {
        return ~(1 << n3) & n2;
    }

    public static final long b(long l2, int n2) {
        return (1L << n2 ^ 0xFFFFFFFFFFFFFFFFL) & l2;
    }

    public static final short b(short s2, int n2) {
        return (short)bm.b((int)s2, n2);
    }
}

