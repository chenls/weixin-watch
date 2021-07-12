/*
 * Decompiled with CFR 0.151.
 */
package android.support.v4.util;

class ContainerHelpers {
    static final int[] EMPTY_INTS = new int[0];
    static final long[] EMPTY_LONGS = new long[0];
    static final Object[] EMPTY_OBJECTS = new Object[0];

    ContainerHelpers() {
    }

    static int binarySearch(int[] nArray, int n2, int n3) {
        int n4;
        block3: {
            n4 = 0;
            int n5 = n2 - 1;
            n2 = n4;
            n4 = n5;
            while (n2 <= n4) {
                n5 = n2 + n4 >>> 1;
                int n6 = nArray[n5];
                if (n6 < n3) {
                    n2 = n5 + 1;
                    continue;
                }
                n4 = n5;
                if (n6 > n3) {
                    n4 = n5 - 1;
                    continue;
                }
                break block3;
            }
            n4 = ~n2;
        }
        return n4;
    }

    static int binarySearch(long[] lArray, int n2, long l2) {
        int n3;
        block3: {
            n3 = 0;
            int n4 = n2 - 1;
            n2 = n3;
            n3 = n4;
            while (n2 <= n3) {
                n4 = n2 + n3 >>> 1;
                long l3 = lArray[n4];
                if (l3 < l2) {
                    n2 = n4 + 1;
                    continue;
                }
                n3 = n4;
                if (l3 > l2) {
                    n3 = n4 - 1;
                    continue;
                }
                break block3;
            }
            n3 = ~n2;
        }
        return n3;
    }

    public static boolean equal(Object object, Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }

    public static int idealByteArraySize(int n2) {
        int n3 = 4;
        while (true) {
            block4: {
                int n4;
                block3: {
                    n4 = n2;
                    if (n3 >= 32) break block3;
                    if (n2 > (1 << n3) - 12) break block4;
                    n4 = (1 << n3) - 12;
                }
                return n4;
            }
            ++n3;
        }
    }

    public static int idealIntArraySize(int n2) {
        return ContainerHelpers.idealByteArraySize(n2 * 4) / 4;
    }

    public static int idealLongArraySize(int n2) {
        return ContainerHelpers.idealByteArraySize(n2 * 8) / 8;
    }
}

