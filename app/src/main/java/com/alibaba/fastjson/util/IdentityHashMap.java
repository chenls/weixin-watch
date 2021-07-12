/*
 * Decompiled with CFR 0.151.
 */
package com.alibaba.fastjson.util;

public class IdentityHashMap<K, V> {
    private final Entry<K, V>[] buckets;
    private final int indexMask;

    public IdentityHashMap() {
        this(1024);
    }

    public IdentityHashMap(int n2) {
        this.indexMask = n2 - 1;
        this.buckets = new Entry[n2];
    }

    public final V get(K k2) {
        int n2 = System.identityHashCode(k2);
        int n3 = this.indexMask;
        Entry<K, V> entry = this.buckets[n2 & n3];
        while (entry != null) {
            if (k2 == entry.key) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    public boolean put(K object, V v2) {
        int n2 = System.identityHashCode(object);
        int n3 = n2 & this.indexMask;
        Entry<K, V> entry = this.buckets[n3];
        while (entry != null) {
            if (object == entry.key) {
                entry.value = v2;
                return true;
            }
            entry = entry.next;
        }
        object = new Entry<K, V>(object, v2, n2, this.buckets[n3]);
        this.buckets[n3] = object;
        return false;
    }

    protected static final class Entry<K, V> {
        public final int hashCode;
        public final K key;
        public final Entry<K, V> next;
        public V value;

        public Entry(K k2, V v2, int n2, Entry<K, V> entry) {
            this.key = k2;
            this.value = v2;
            this.next = entry;
            this.hashCode = n2;
        }
    }
}

