/*
 * Decompiled with CFR 0.151.
 */
package android.support.v4.util;

public final class CircularArray<E> {
    private int mCapacityBitmask;
    private E[] mElements;
    private int mHead;
    private int mTail;

    public CircularArray() {
        this(8);
    }

    public CircularArray(int n2) {
        if (n2 < 1) {
            throw new IllegalArgumentException("capacity must be >= 1");
        }
        if (n2 > 0x40000000) {
            throw new IllegalArgumentException("capacity must be <= 2^30");
        }
        if (Integer.bitCount(n2) != 1) {
            n2 = Integer.highestOneBit(n2 - 1) << 1;
        }
        this.mCapacityBitmask = n2 - 1;
        this.mElements = new Object[n2];
    }

    private void doubleCapacity() {
        int n2 = this.mElements.length;
        int n3 = n2 - this.mHead;
        int n4 = n2 << 1;
        if (n4 < 0) {
            throw new RuntimeException("Max array capacity exceeded");
        }
        Object[] objectArray = new Object[n4];
        System.arraycopy(this.mElements, this.mHead, objectArray, 0, n3);
        System.arraycopy(this.mElements, 0, objectArray, n3, this.mHead);
        this.mElements = objectArray;
        this.mHead = 0;
        this.mTail = n2;
        this.mCapacityBitmask = n4 - 1;
    }

    public void addFirst(E e2) {
        this.mHead = this.mHead - 1 & this.mCapacityBitmask;
        this.mElements[this.mHead] = e2;
        if (this.mHead == this.mTail) {
            this.doubleCapacity();
        }
    }

    public void addLast(E e2) {
        this.mElements[this.mTail] = e2;
        this.mTail = this.mTail + 1 & this.mCapacityBitmask;
        if (this.mTail == this.mHead) {
            this.doubleCapacity();
        }
    }

    public void clear() {
        this.removeFromStart(this.size());
    }

    public E get(int n2) {
        if (n2 < 0 || n2 >= this.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.mElements[this.mHead + n2 & this.mCapacityBitmask];
    }

    public E getFirst() {
        if (this.mHead == this.mTail) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.mElements[this.mHead];
    }

    public E getLast() {
        if (this.mHead == this.mTail) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.mElements[this.mTail - 1 & this.mCapacityBitmask];
    }

    public boolean isEmpty() {
        return this.mHead == this.mTail;
    }

    public E popFirst() {
        if (this.mHead == this.mTail) {
            throw new ArrayIndexOutOfBoundsException();
        }
        E e2 = this.mElements[this.mHead];
        this.mElements[this.mHead] = null;
        this.mHead = this.mHead + 1 & this.mCapacityBitmask;
        return e2;
    }

    public E popLast() {
        if (this.mHead == this.mTail) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int n2 = this.mTail - 1 & this.mCapacityBitmask;
        E e2 = this.mElements[n2];
        this.mElements[n2] = null;
        this.mTail = n2;
        return e2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void removeFromEnd(int n2) {
        int n3;
        block9: {
            block8: {
                if (n2 <= 0) break block8;
                if (n2 > this.size()) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                n3 = 0;
                if (n2 < this.mTail) {
                    n3 = this.mTail - n2;
                }
                for (int i2 = n3; i2 < this.mTail; ++i2) {
                    this.mElements[i2] = null;
                }
                n3 = this.mTail - n3;
                this.mTail -= n3;
                if ((n2 -= n3) > 0) break block9;
            }
            return;
        }
        this.mTail = this.mElements.length;
        n2 = n3 = this.mTail - n2;
        while (true) {
            if (n2 >= this.mTail) {
                this.mTail = n3;
                return;
            }
            this.mElements[n2] = null;
            ++n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void removeFromStart(int n2) {
        int n3;
        block9: {
            block8: {
                int n4;
                if (n2 <= 0) break block8;
                if (n2 > this.size()) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                n3 = n4 = this.mElements.length;
                if (n2 < n4 - this.mHead) {
                    n3 = this.mHead + n2;
                }
                for (n4 = this.mHead; n4 < n3; ++n4) {
                    this.mElements[n4] = null;
                }
                n4 = n3 - this.mHead;
                n3 = n2 - n4;
                this.mHead = this.mHead + n4 & this.mCapacityBitmask;
                if (n3 > 0) break block9;
            }
            return;
        }
        n2 = 0;
        while (true) {
            if (n2 >= n3) {
                this.mHead = n3;
                return;
            }
            this.mElements[n2] = null;
            ++n2;
        }
    }

    public int size() {
        return this.mTail - this.mHead & this.mCapacityBitmask;
    }
}

