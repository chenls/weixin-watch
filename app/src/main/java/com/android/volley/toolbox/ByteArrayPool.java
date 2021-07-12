/*
 * Decompiled with CFR 0.151.
 */
package com.android.volley.toolbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ByteArrayPool {
    protected static final Comparator<byte[]> BUF_COMPARATOR = new Comparator<byte[]>(){

        @Override
        public int compare(byte[] byArray, byte[] byArray2) {
            return byArray.length - byArray2.length;
        }
    };
    private List<byte[]> mBuffersByLastUse = new LinkedList<byte[]>();
    private List<byte[]> mBuffersBySize = new ArrayList<byte[]>(64);
    private int mCurrentSize = 0;
    private final int mSizeLimit;

    public ByteArrayPool(int n2) {
        this.mSizeLimit = n2;
    }

    private void trim() {
        synchronized (this) {
            while (this.mCurrentSize > this.mSizeLimit) {
                byte[] byArray = this.mBuffersByLastUse.remove(0);
                this.mBuffersBySize.remove(byArray);
                this.mCurrentSize -= byArray.length;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] getBuf(int n2) {
        synchronized (this) {
            byte[] byArray;
            int n3;
            block4: {
                n3 = 0;
                while (n3 < this.mBuffersBySize.size()) {
                    byArray = this.mBuffersBySize.get(n3);
                    if (byArray.length >= n2) {
                        this.mCurrentSize -= byArray.length;
                        break block4;
                    }
                    ++n3;
                }
                return new byte[n2];
            }
            this.mBuffersBySize.remove(n3);
            this.mBuffersByLastUse.remove(byArray);
            return byArray;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void returnBuf(byte[] byArray) {
        synchronized (this) {
            int n2;
            block9: {
                int n3;
                block8: {
                    if (byArray != null) {
                        n2 = byArray.length;
                        n3 = this.mSizeLimit;
                        if (n2 <= n3) break block8;
                    }
                    return;
                }
                this.mBuffersByLastUse.add(byArray);
                n2 = n3 = Collections.binarySearch(this.mBuffersBySize, byArray, BUF_COMPARATOR);
                if (n3 >= 0) break block9;
                n2 = -n3 - 1;
            }
            this.mBuffersBySize.add(n2, byArray);
            this.mCurrentSize += byArray.length;
            this.trim();
            return;
        }
    }
}

