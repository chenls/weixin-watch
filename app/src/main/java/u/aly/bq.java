/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public final class bq {
    private static final Comparator a = new a();

    private bq() {
    }

    public static int a(byte by2, byte by3) {
        if (by2 < by3) {
            return -1;
        }
        if (by3 < by2) {
            return 1;
        }
        return 0;
    }

    public static int a(double d2, double d3) {
        if (d2 < d3) {
            return -1;
        }
        if (d3 < d2) {
            return 1;
        }
        return 0;
    }

    public static int a(int n2, int n3) {
        if (n2 < n3) {
            return -1;
        }
        if (n3 < n2) {
            return 1;
        }
        return 0;
    }

    public static int a(long l2, long l3) {
        if (l2 < l3) {
            return -1;
        }
        if (l3 < l2) {
            return 1;
        }
        return 0;
    }

    public static int a(Comparable comparable, Comparable comparable2) {
        return comparable.compareTo(comparable2);
    }

    public static int a(Object object, Object object2) {
        if (object instanceof Comparable) {
            return bq.a((Comparable)object, (Comparable)object2);
        }
        if (object instanceof List) {
            return bq.a((List)object, (List)object2);
        }
        if (object instanceof Set) {
            return bq.a((Set)object, (Set)object2);
        }
        if (object instanceof Map) {
            return bq.a((Map)object, (Map)object2);
        }
        if (object instanceof byte[]) {
            return bq.a((byte[])object, (byte[])object2);
        }
        throw new IllegalArgumentException("Cannot compare objects of type " + object.getClass());
    }

    public static int a(String string2, String string3) {
        return string2.compareTo(string3);
    }

    public static int a(ByteBuffer byteBuffer, byte[] byArray, int n2) {
        int n3 = byteBuffer.remaining();
        System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byArray, n2, n3);
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int a(List list, List list2) {
        int n2 = 0;
        int n3 = bq.a(list.size(), list2.size());
        if (n3 == 0) {
            int n4 = 0;
            while (true) {
                n3 = n2;
                if (n4 >= list.size()) break;
                n3 = a.compare(list.get(n4), list2.get(n4));
                if (n3 != 0) {
                    return n3;
                }
                ++n4;
            }
        }
        return n3;
    }

    public static int a(Map object, Map object2) {
        int n2 = bq.a(object.size(), object2.size());
        if (n2 != 0) {
            return n2;
        }
        Object object3 = new TreeMap(a);
        object3.putAll(object);
        object = object3.entrySet().iterator();
        object3 = new TreeMap(a);
        object3.putAll(object2);
        object2 = object3.entrySet().iterator();
        while (object.hasNext() && object2.hasNext()) {
            object3 = (Map.Entry)object.next();
            Map.Entry entry = (Map.Entry)object2.next();
            n2 = a.compare(object3.getKey(), entry.getKey());
            if (n2 != 0) {
                return n2;
            }
            n2 = a.compare(object3.getValue(), entry.getValue());
            if (n2 == 0) continue;
            return n2;
        }
        return 0;
    }

    public static int a(Set object, Set object2) {
        int n2 = bq.a(object.size(), object2.size());
        if (n2 != 0) {
            return n2;
        }
        TreeSet treeSet = new TreeSet(a);
        treeSet.addAll(object);
        object = new TreeSet(a);
        object.addAll(object2);
        object2 = treeSet.iterator();
        object = object.iterator();
        while (object2.hasNext() && object.hasNext()) {
            n2 = a.compare(object2.next(), object.next());
            if (n2 == 0) continue;
            return n2;
        }
        return 0;
    }

    public static int a(short s2, short s3) {
        if (s2 < s3) {
            return -1;
        }
        if (s3 < s2) {
            return 1;
        }
        return 0;
    }

    public static int a(boolean bl2, boolean bl3) {
        return Boolean.valueOf(bl2).compareTo(bl3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int a(byte[] byArray, byte[] byArray2) {
        int n2 = 0;
        int n3 = bq.a(byArray.length, byArray2.length);
        if (n3 == 0) {
            int n4 = 0;
            while (true) {
                n3 = n2;
                if (n4 >= byArray.length) break;
                n3 = bq.a(byArray[n4], byArray2[n4]);
                if (n3 != 0) {
                    return n3;
                }
                ++n4;
            }
        }
        return n3;
    }

    public static String a(byte by2) {
        return Integer.toHexString((by2 | 0x100) & 0x1FF).toUpperCase().substring(1);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void a(ByteBuffer byteBuffer, StringBuilder stringBuilder) {
        byte[] byArray = byteBuffer.array();
        int n2 = byteBuffer.arrayOffset();
        int n3 = n2 + byteBuffer.position();
        int n4 = byteBuffer.limit() + n2;
        n2 = n4 - n3 > 128 ? n3 + 128 : n4;
        for (int i2 = n3; i2 < n2; ++i2) {
            if (i2 > n3) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(bq.a(byArray[i2]));
        }
        if (n4 != n2) {
            stringBuilder.append("...");
        }
    }

    public static byte[] a(ByteBuffer byteBuffer) {
        if (bq.b(byteBuffer)) {
            return byteBuffer.array();
        }
        byte[] byArray = new byte[byteBuffer.remaining()];
        bq.a(byteBuffer, byArray, 0);
        return byArray;
    }

    public static byte[] a(byte[] byArray) {
        if (byArray == null) {
            return null;
        }
        byte[] byArray2 = new byte[byArray.length];
        System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
        return byArray2;
    }

    public static boolean b(ByteBuffer byteBuffer) {
        return byteBuffer.hasArray() && byteBuffer.position() == 0 && byteBuffer.arrayOffset() == 0 && byteBuffer.remaining() == byteBuffer.capacity();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ByteBuffer c(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return null;
        }
        ByteBuffer byteBuffer2 = byteBuffer;
        if (bq.b(byteBuffer)) return byteBuffer2;
        return ByteBuffer.wrap(bq.a(byteBuffer));
    }

    public static ByteBuffer d(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return null;
        }
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(new byte[byteBuffer.remaining()]);
        if (byteBuffer.hasArray()) {
            System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), byteBuffer2.array(), 0, byteBuffer.remaining());
            return byteBuffer2;
        }
        byteBuffer.slice().get(byteBuffer2.array());
        return byteBuffer2;
    }

    private static class a
    implements Comparator {
        private a() {
        }

        public int compare(Object object, Object object2) {
            if (object == null && object2 == null) {
                return 0;
            }
            if (object == null) {
                return -1;
            }
            if (object2 == null) {
                return 1;
            }
            if (object instanceof List) {
                return bq.a((List)object, (List)object2);
            }
            if (object instanceof Set) {
                return bq.a((Set)object, (Set)object2);
            }
            if (object instanceof Map) {
                return bq.a((Map)object, (Map)object2);
            }
            if (object instanceof byte[]) {
                return bq.a((byte[])object, (byte[])object2);
            }
            return bq.a((Comparable)object, (Comparable)object2);
        }
    }
}

