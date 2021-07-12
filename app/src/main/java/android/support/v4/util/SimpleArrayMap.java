/*
 * Decompiled with CFR 0.151.
 */
package android.support.v4.util;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.ContainerHelpers;
import java.util.Map;

public class SimpleArrayMap<K, V> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArrayMap";
    static Object[] mBaseCache;
    static int mBaseCacheSize;
    static Object[] mTwiceBaseCache;
    static int mTwiceBaseCacheSize;
    Object[] mArray;
    int[] mHashes;
    int mSize;

    public SimpleArrayMap() {
        this.mHashes = ContainerHelpers.EMPTY_INTS;
        this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        this.mSize = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public SimpleArrayMap(int n2) {
        if (n2 == 0) {
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            this.allocArrays(n2);
        }
        this.mSize = 0;
    }

    public SimpleArrayMap(SimpleArrayMap simpleArrayMap) {
        this();
        if (simpleArrayMap != null) {
            this.putAll(simpleArrayMap);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void allocArrays(int n2) {
        if (n2 == 8) {
            synchronized (ArrayMap.class) {
                if (mTwiceBaseCache != null) {
                    Object[] objectArray = mTwiceBaseCache;
                    this.mArray = objectArray;
                    mTwiceBaseCache = (Object[])objectArray[0];
                    this.mHashes = (int[])objectArray[1];
                    objectArray[1] = null;
                    objectArray[0] = null;
                    --mTwiceBaseCacheSize;
                    return;
                }
            }
        } else if (n2 == 4) {
            synchronized (ArrayMap.class) {
                if (mBaseCache != null) {
                    Object[] objectArray = mBaseCache;
                    this.mArray = objectArray;
                    mBaseCache = (Object[])objectArray[0];
                    this.mHashes = (int[])objectArray[1];
                    objectArray[1] = null;
                    objectArray[0] = null;
                    --mBaseCacheSize;
                    return;
                }
            }
        }
        this.mHashes = new int[n2];
        this.mArray = new Object[n2 << 1];
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static void freeArrays(int[] nArray, Object[] objectArray, int n2) {
        block11: {
            block12: {
                block9: {
                    block10: {
                        block8: {
                            if (nArray.length != 8) break block8;
                            // MONITORENTER : android.support.v4.util.ArrayMap.class
                            if (mTwiceBaseCacheSize >= 10) break block9;
                            objectArray[0] = mTwiceBaseCache;
                            objectArray[1] = nArray;
                            break block10;
                        }
                        if (nArray.length != 4) return;
                        // MONITORENTER : android.support.v4.util.ArrayMap.class
                        if (mBaseCacheSize >= 10) break block11;
                        objectArray[0] = mBaseCache;
                        objectArray[1] = nArray;
                        break block12;
                    }
                    for (n2 = (n2 << 1) - 1; n2 >= 2; --n2) {
                        objectArray[n2] = null;
                    }
                    mTwiceBaseCache = objectArray;
                    ++mTwiceBaseCacheSize;
                }
                // MONITOREXIT : android.support.v4.util.ArrayMap.class
                return;
            }
            for (n2 = (n2 << 1) - 1; n2 >= 2; --n2) {
                objectArray[n2] = null;
            }
            mBaseCache = objectArray;
            ++mBaseCacheSize;
        }
        // MONITOREXIT : android.support.v4.util.ArrayMap.class
    }

    public void clear() {
        if (this.mSize != 0) {
            SimpleArrayMap.freeArrays(this.mHashes, this.mArray, this.mSize);
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
        }
    }

    public boolean containsKey(Object object) {
        return this.indexOfKey(object) >= 0;
    }

    public boolean containsValue(Object object) {
        return this.indexOfValue(object) >= 0;
    }

    public void ensureCapacity(int n2) {
        if (this.mHashes.length < n2) {
            int[] nArray = this.mHashes;
            Object[] objectArray = this.mArray;
            this.allocArrays(n2);
            if (this.mSize > 0) {
                System.arraycopy(nArray, 0, this.mHashes, 0, this.mSize);
                System.arraycopy(objectArray, 0, this.mArray, 0, this.mSize << 1);
            }
            SimpleArrayMap.freeArrays(nArray, objectArray, this.mSize);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SimpleArrayMap)) {
            if (!(object instanceof Map)) return false;
            object = (Map)object;
            if (this.size() != object.size()) {
                return false;
            }
        } else {
            object = (SimpleArrayMap)object;
            if (this.size() != ((SimpleArrayMap)object).size()) {
                return false;
            }
            int n2 = 0;
            try {
                while (n2 < this.mSize) {
                    K k2 = this.keyAt(n2);
                    V v2 = this.valueAt(n2);
                    V v3 = ((SimpleArrayMap)object).get(k2);
                    if (v2 == null) {
                        if (v3 != null) return false;
                        if (!((SimpleArrayMap)object).containsKey(k2)) {
                            return false;
                        }
                    } else {
                        boolean bl2 = v2.equals(v3);
                        if (!bl2) {
                            return false;
                        }
                    }
                    ++n2;
                }
                return true;
            }
            catch (NullPointerException nullPointerException) {
                return false;
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }
        int n3 = 0;
        try {
            while (n3 < this.mSize) {
                K k3 = this.keyAt(n3);
                V v4 = this.valueAt(n3);
                Object v5 = object.get(k3);
                if (v4 == null) {
                    if (v5 != null) return false;
                    if (!object.containsKey(k3)) {
                        return false;
                    }
                } else {
                    boolean bl3 = v4.equals(v5);
                    if (!bl3) {
                        return false;
                    }
                }
                ++n3;
            }
            return true;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public V get(Object object) {
        int n2 = this.indexOfKey(object);
        if (n2 >= 0) {
            return (V)this.mArray[(n2 << 1) + 1];
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int[] nArray = this.mHashes;
        Object[] objectArray = this.mArray;
        int n2 = 0;
        int n3 = 0;
        int n4 = 1;
        int n5 = this.mSize;
        while (n3 < n5) {
            Object object = objectArray[n4];
            int n6 = nArray[n3];
            int n7 = object == null ? 0 : object.hashCode();
            n2 += n7 ^ n6;
            ++n3;
            n4 += 2;
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    int indexOf(Object object, int n2) {
        int n3;
        int n4 = this.mSize;
        if (n4 == 0) {
            return -1;
        }
        int n5 = n3 = ContainerHelpers.binarySearch(this.mHashes, n4, n2);
        if (n3 < 0) return n5;
        n5 = n3;
        if (object.equals(this.mArray[n3 << 1])) return n5;
        for (n5 = n3 + 1; n5 < n4 && this.mHashes[n5] == n2; ++n5) {
            if (!object.equals(this.mArray[n5 << 1])) continue;
            return n5;
        }
        --n3;
        while (n3 >= 0) {
            if (this.mHashes[n3] != n2) return ~n5;
            if (object.equals(this.mArray[n3 << 1])) {
                return n3;
            }
            --n3;
        }
        return ~n5;
    }

    public int indexOfKey(Object object) {
        if (object == null) {
            return this.indexOfNull();
        }
        return this.indexOf(object, object.hashCode());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    int indexOfNull() {
        int n2;
        int n3 = this.mSize;
        if (n3 == 0) {
            return -1;
        }
        int n4 = n2 = ContainerHelpers.binarySearch(this.mHashes, n3, 0);
        if (n2 < 0) return n4;
        n4 = n2;
        if (this.mArray[n2 << 1] == null) return n4;
        for (n4 = n2 + 1; n4 < n3 && this.mHashes[n4] == 0; ++n4) {
            if (this.mArray[n4 << 1] != null) continue;
            return n4;
        }
        --n2;
        while (n2 >= 0) {
            if (this.mHashes[n2] != 0) return ~n4;
            if (this.mArray[n2 << 1] == null) {
                return n2;
            }
            --n2;
        }
        return ~n4;
    }

    int indexOfValue(Object object) {
        int n2 = this.mSize * 2;
        Object[] objectArray = this.mArray;
        if (object == null) {
            for (int i2 = 1; i2 < n2; i2 += 2) {
                if (objectArray[i2] != null) continue;
                return i2 >> 1;
            }
        } else {
            for (int i3 = 1; i3 < n2; i3 += 2) {
                if (!object.equals(objectArray[i3])) continue;
                return i3 >> 1;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.mSize <= 0;
    }

    public K keyAt(int n2) {
        return (K)this.mArray[n2 << 1];
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public V put(K object, V v2) {
        void var2_3;
        int n2;
        int n3;
        int n4 = 8;
        if (object == null) {
            n3 = 0;
            n2 = this.indexOfNull();
        } else {
            n3 = object.hashCode();
            n2 = this.indexOf(object, n3);
        }
        if (n2 >= 0) {
            n2 = (n2 << 1) + 1;
            Object object2 = this.mArray[n2];
            this.mArray[n2] = var2_3;
            return (V)object2;
        }
        int n5 = ~n2;
        if (this.mSize >= this.mHashes.length) {
            if (this.mSize >= 8) {
                n2 = this.mSize + (this.mSize >> 1);
            } else {
                n2 = n4;
                if (this.mSize < 4) {
                    n2 = 4;
                }
            }
            int[] nArray = this.mHashes;
            Object[] objectArray = this.mArray;
            this.allocArrays(n2);
            if (this.mHashes.length > 0) {
                System.arraycopy(nArray, 0, this.mHashes, 0, nArray.length);
                System.arraycopy(objectArray, 0, this.mArray, 0, objectArray.length);
            }
            SimpleArrayMap.freeArrays(nArray, objectArray, this.mSize);
        }
        if (n5 < this.mSize) {
            System.arraycopy(this.mHashes, n5, this.mHashes, n5 + 1, this.mSize - n5);
            System.arraycopy(this.mArray, n5 << 1, this.mArray, n5 + 1 << 1, this.mSize - n5 << 1);
        }
        this.mHashes[n5] = n3;
        this.mArray[n5 << 1] = object;
        this.mArray[(n5 << 1) + 1] = var2_3;
        ++this.mSize;
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void putAll(SimpleArrayMap<? extends K, ? extends V> simpleArrayMap) {
        int n2 = simpleArrayMap.mSize;
        this.ensureCapacity(this.mSize + n2);
        if (this.mSize == 0) {
            if (n2 <= 0) return;
            System.arraycopy(simpleArrayMap.mHashes, 0, this.mHashes, 0, n2);
            System.arraycopy(simpleArrayMap.mArray, 0, this.mArray, 0, n2 << 1);
            this.mSize = n2;
            return;
        } else {
            for (int i2 = 0; i2 < n2; ++i2) {
                this.put(simpleArrayMap.keyAt(i2), simpleArrayMap.valueAt(i2));
            }
        }
    }

    public V remove(Object object) {
        int n2 = this.indexOfKey(object);
        if (n2 >= 0) {
            return this.removeAt(n2);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public V removeAt(int n2) {
        int n3 = 8;
        Object object = this.mArray[(n2 << 1) + 1];
        if (this.mSize <= 1) {
            SimpleArrayMap.freeArrays(this.mHashes, this.mArray, this.mSize);
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
            return (V)object;
        }
        if (this.mHashes.length > 8 && this.mSize < this.mHashes.length / 3) {
            if (this.mSize > 8) {
                n3 = this.mSize + (this.mSize >> 1);
            }
            int[] nArray = this.mHashes;
            Object[] objectArray = this.mArray;
            this.allocArrays(n3);
            --this.mSize;
            if (n2 > 0) {
                System.arraycopy(nArray, 0, this.mHashes, 0, n2);
                System.arraycopy(objectArray, 0, this.mArray, 0, n2 << 1);
            }
            if (n2 >= this.mSize) return (V)object;
            System.arraycopy(nArray, n2 + 1, this.mHashes, n2, this.mSize - n2);
            System.arraycopy(objectArray, n2 + 1 << 1, this.mArray, n2 << 1, this.mSize - n2 << 1);
            return (V)object;
        }
        --this.mSize;
        if (n2 < this.mSize) {
            System.arraycopy(this.mHashes, n2 + 1, this.mHashes, n2, this.mSize - n2);
            System.arraycopy(this.mArray, n2 + 1 << 1, this.mArray, n2 << 1, this.mSize - n2 << 1);
        }
        this.mArray[this.mSize << 1] = null;
        this.mArray[(this.mSize << 1) + 1] = null;
        return (V)object;
    }

    public V setValueAt(int n2, V v2) {
        n2 = (n2 << 1) + 1;
        Object object = this.mArray[n2];
        this.mArray[n2] = v2;
        return (V)object;
    }

    public int size() {
        return this.mSize;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        if (this.isEmpty()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.mSize * 28);
        stringBuilder.append('{');
        int n2 = 0;
        while (true) {
            K k2;
            if (n2 >= this.mSize) {
                stringBuilder.append('}');
                return stringBuilder.toString();
            }
            if (n2 > 0) {
                stringBuilder.append(", ");
            }
            if ((k2 = this.keyAt(n2)) != this) {
                stringBuilder.append(k2);
            } else {
                stringBuilder.append("(this Map)");
            }
            stringBuilder.append('=');
            V v2 = this.valueAt(n2);
            if (v2 != this) {
                stringBuilder.append(v2);
            } else {
                stringBuilder.append("(this Map)");
            }
            ++n2;
        }
    }

    public V valueAt(int n2) {
        return (V)this.mArray[(n2 << 1) + 1];
    }
}

