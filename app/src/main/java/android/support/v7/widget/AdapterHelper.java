/*
 * Decompiled with CFR 0.151.
 */
package android.support.v7.widget;

import android.support.v4.util.Pools;
import android.support.v7.widget.OpReorderer;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AdapterHelper
implements OpReorderer.Callback {
    private static final boolean DEBUG = false;
    static final int POSITION_TYPE_INVISIBLE = 0;
    static final int POSITION_TYPE_NEW_OR_LAID_OUT = 1;
    private static final String TAG = "AHT";
    final Callback mCallback;
    final boolean mDisableRecycler;
    private int mExistingUpdateTypes = 0;
    Runnable mOnItemProcessedCallback;
    final OpReorderer mOpReorderer;
    final ArrayList<UpdateOp> mPendingUpdates;
    final ArrayList<UpdateOp> mPostponedList;
    private Pools.Pool<UpdateOp> mUpdateOpPool = new Pools.SimplePool<UpdateOp>(30);

    AdapterHelper(Callback callback) {
        this(callback, false);
    }

    AdapterHelper(Callback callback, boolean bl2) {
        this.mPendingUpdates = new ArrayList();
        this.mPostponedList = new ArrayList();
        this.mCallback = callback;
        this.mDisableRecycler = bl2;
        this.mOpReorderer = new OpReorderer(this);
    }

    private void applyAdd(UpdateOp updateOp) {
        this.postponeAndUpdateViewHolders(updateOp);
    }

    private void applyMove(UpdateOp updateOp) {
        this.postponeAndUpdateViewHolders(updateOp);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void applyRemove(UpdateOp updateOp) {
        int n2 = updateOp.positionStart;
        int n3 = 0;
        int n4 = updateOp.positionStart + updateOp.itemCount;
        int n5 = -1;
        for (int i2 = updateOp.positionStart; i2 < n4; ++i2) {
            int n6 = 0;
            int n7 = 0;
            if (this.mCallback.findViewHolder(i2) != null || this.canFindInPreLayout(i2)) {
                if (n5 == 0) {
                    this.dispatchAndUpdateViewHolders(this.obtainUpdateOp(2, n2, n3, null));
                    n7 = 1;
                }
                n5 = 1;
                n6 = n7;
                n7 = n5;
            } else {
                if (n5 == 1) {
                    this.postponeAndUpdateViewHolders(this.obtainUpdateOp(2, n2, n3, null));
                    n6 = 1;
                }
                n7 = 0;
            }
            if (n6 != 0) {
                i2 -= n3;
                n4 -= n3;
                n6 = 1;
            } else {
                n6 = n3 + 1;
            }
            n3 = n6;
            n5 = n7;
        }
        UpdateOp updateOp2 = updateOp;
        if (n3 != updateOp.itemCount) {
            this.recycleUpdateOp(updateOp);
            updateOp2 = this.obtainUpdateOp(2, n2, n3, null);
        }
        if (n5 == 0) {
            this.dispatchAndUpdateViewHolders(updateOp2);
            return;
        }
        this.postponeAndUpdateViewHolders(updateOp2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void applyUpdate(UpdateOp updateOp) {
        int n2 = updateOp.positionStart;
        int n3 = 0;
        int n4 = updateOp.positionStart;
        int n5 = updateOp.itemCount;
        int n6 = -1;
        for (int i2 = updateOp.positionStart; i2 < n4 + n5; ++i2) {
            int n7;
            int n8;
            if (this.mCallback.findViewHolder(i2) != null || this.canFindInPreLayout(i2)) {
                n8 = n3;
                int n9 = n2;
                if (n6 == 0) {
                    this.dispatchAndUpdateViewHolders(this.obtainUpdateOp(4, n2, n3, updateOp.payload));
                    n8 = 0;
                    n9 = i2;
                }
                n7 = 1;
                n2 = n9;
            } else {
                n8 = n3;
                n7 = n2;
                if (n6 == 1) {
                    this.postponeAndUpdateViewHolders(this.obtainUpdateOp(4, n2, n3, updateOp.payload));
                    n8 = 0;
                    n7 = i2;
                }
                n3 = 0;
                n2 = n7;
                n7 = n3;
            }
            n3 = n8 + 1;
            n6 = n7;
        }
        Object object = updateOp;
        if (n3 != updateOp.itemCount) {
            object = updateOp.payload;
            this.recycleUpdateOp(updateOp);
            object = this.obtainUpdateOp(4, n2, n3, object);
        }
        if (n6 == 0) {
            this.dispatchAndUpdateViewHolders((UpdateOp)object);
            return;
        }
        this.postponeAndUpdateViewHolders((UpdateOp)object);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean canFindInPreLayout(int n2) {
        int n3 = this.mPostponedList.size();
        int n4 = 0;
        while (true) {
            block7: {
                if (n4 >= n3) {
                    return false;
                }
                UpdateOp updateOp = this.mPostponedList.get(n4);
                if (updateOp.cmd == 8) {
                    if (this.findPositionOffset(updateOp.itemCount, n4 + 1) == n2) {
                        return true;
                    }
                } else if (updateOp.cmd == 1) {
                    int n5 = updateOp.positionStart;
                    int n6 = updateOp.itemCount;
                    for (int i2 = updateOp.positionStart; i2 < n5 + n6; ++i2) {
                        if (this.findPositionOffset(i2, n4 + 1) != n2) continue;
                    }
                }
                break block7;
                return true;
            }
            ++n4;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void dispatchAndUpdateViewHolders(UpdateOp updateOp) {
        Object object;
        int n2;
        if (updateOp.cmd == 1 || updateOp.cmd == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int n3 = this.updatePositionWithPostponed(updateOp.positionStart, updateOp.cmd);
        int n4 = 1;
        int n5 = updateOp.positionStart;
        switch (updateOp.cmd) {
            default: {
                throw new IllegalArgumentException("op should be remove or update." + updateOp);
            }
            case 4: {
                n2 = 1;
                break;
            }
            case 2: {
                n2 = 0;
                break;
            }
        }
        for (int i2 = 1; i2 < updateOp.itemCount; ++i2) {
            int n6;
            int n7 = this.updatePositionWithPostponed(updateOp.positionStart + n2 * i2, updateOp.cmd);
            int n8 = n6 = 0;
            switch (updateOp.cmd) {
                default: {
                    n8 = n6;
                    break;
                }
                case 4: {
                    if (n7 == n3 + 1) {
                        n8 = 1;
                        break;
                    }
                    n8 = 0;
                    break;
                }
                case 2: {
                    if (n7 == n3) {
                        n8 = 1;
                        break;
                    }
                    n8 = 0;
                }
                case 3: 
            }
            if (n8 != 0) {
                n8 = n4 + 1;
            } else {
                object = this.obtainUpdateOp(updateOp.cmd, n3, n4, updateOp.payload);
                this.dispatchFirstPassAndUpdateViewHolders((UpdateOp)object, n5);
                this.recycleUpdateOp((UpdateOp)object);
                n8 = n5;
                if (updateOp.cmd == 4) {
                    n8 = n5 + n4;
                }
                n3 = n7;
                n4 = 1;
                n5 = n8;
                n8 = n4;
            }
            n4 = n8;
        }
        object = updateOp.payload;
        this.recycleUpdateOp(updateOp);
        if (n4 > 0) {
            updateOp = this.obtainUpdateOp(updateOp.cmd, n3, n4, object);
            this.dispatchFirstPassAndUpdateViewHolders(updateOp, n5);
            this.recycleUpdateOp(updateOp);
        }
    }

    private void postponeAndUpdateViewHolders(UpdateOp updateOp) {
        this.mPostponedList.add(updateOp);
        switch (updateOp.cmd) {
            default: {
                throw new IllegalArgumentException("Unknown update op type for " + updateOp);
            }
            case 1: {
                this.mCallback.offsetPositionsForAdd(updateOp.positionStart, updateOp.itemCount);
                return;
            }
            case 8: {
                this.mCallback.offsetPositionsForMove(updateOp.positionStart, updateOp.itemCount);
                return;
            }
            case 2: {
                this.mCallback.offsetPositionsForRemovingLaidOutOrNewView(updateOp.positionStart, updateOp.itemCount);
                return;
            }
            case 4: 
        }
        this.mCallback.markViewHoldersUpdated(updateOp.positionStart, updateOp.itemCount, updateOp.payload);
    }

    /*
     * Enabled aggressive block sorting
     */
    private int updatePositionWithPostponed(int n2, int n3) {
        UpdateOp updateOp;
        int n4 = n2;
        for (int i2 = this.mPostponedList.size() - 1; i2 >= 0; --i2) {
            block26: {
                block29: {
                    block27: {
                        block28: {
                            int n5;
                            updateOp = this.mPostponedList.get(i2);
                            if (updateOp.cmd != 8) break block27;
                            if (updateOp.positionStart < updateOp.itemCount) {
                                n5 = updateOp.positionStart;
                                n2 = updateOp.itemCount;
                            } else {
                                n5 = updateOp.itemCount;
                                n2 = updateOp.positionStart;
                            }
                            if (n4 < n5 || n4 > n2) break block28;
                            if (n5 == updateOp.positionStart) {
                                if (n3 == 1) {
                                    ++updateOp.itemCount;
                                } else if (n3 == 2) {
                                    --updateOp.itemCount;
                                }
                                n2 = n4 + 1;
                                break block26;
                            } else {
                                if (n3 == 1) {
                                    ++updateOp.positionStart;
                                } else if (n3 == 2) {
                                    --updateOp.positionStart;
                                }
                                n2 = n4 - 1;
                            }
                            break block26;
                        }
                        n2 = n4;
                        if (n4 >= updateOp.positionStart) break block26;
                        if (n3 == 1) {
                            ++updateOp.positionStart;
                            ++updateOp.itemCount;
                            n2 = n4;
                            break block26;
                        } else {
                            n2 = n4;
                            if (n3 == 2) {
                                --updateOp.positionStart;
                                --updateOp.itemCount;
                                n2 = n4;
                            }
                        }
                        break block26;
                    }
                    if (updateOp.positionStart > n4) break block29;
                    if (updateOp.cmd == 1) {
                        n2 = n4 - updateOp.itemCount;
                        break block26;
                    } else {
                        n2 = n4;
                        if (updateOp.cmd == 2) {
                            n2 = n4 + updateOp.itemCount;
                        }
                    }
                    break block26;
                }
                if (n3 == 1) {
                    ++updateOp.positionStart;
                    n2 = n4;
                } else {
                    n2 = n4;
                    if (n3 == 2) {
                        --updateOp.positionStart;
                        n2 = n4;
                    }
                }
            }
            n4 = n2;
        }
        n2 = this.mPostponedList.size() - 1;
        while (n2 >= 0) {
            updateOp = this.mPostponedList.get(n2);
            if (updateOp.cmd == 8) {
                if (updateOp.itemCount == updateOp.positionStart || updateOp.itemCount < 0) {
                    this.mPostponedList.remove(n2);
                    this.recycleUpdateOp(updateOp);
                }
            } else if (updateOp.itemCount <= 0) {
                this.mPostponedList.remove(n2);
                this.recycleUpdateOp(updateOp);
            }
            --n2;
        }
        return n4;
    }

    AdapterHelper addUpdateOp(UpdateOp ... updateOpArray) {
        Collections.addAll(this.mPendingUpdates, updateOpArray);
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int applyPendingUpdatesToPosition(int n2) {
        int n3 = this.mPendingUpdates.size();
        int n4 = 0;
        int n5 = n2;
        while (true) {
            n2 = n5;
            if (n4 >= n3) return n2;
            UpdateOp updateOp = this.mPendingUpdates.get(n4);
            switch (updateOp.cmd) {
                default: {
                    n2 = n5;
                    break;
                }
                case 1: {
                    n2 = n5;
                    if (updateOp.positionStart > n5) break;
                    n2 = n5 + updateOp.itemCount;
                    break;
                }
                case 2: {
                    n2 = n5;
                    if (updateOp.positionStart > n5) break;
                    if (updateOp.positionStart + updateOp.itemCount > n5) {
                        return -1;
                    }
                    n2 = n5 - updateOp.itemCount;
                    break;
                }
                case 8: {
                    if (updateOp.positionStart == n5) {
                        n2 = updateOp.itemCount;
                        break;
                    }
                    int n6 = n5;
                    if (updateOp.positionStart < n5) {
                        n6 = n5 - 1;
                    }
                    n2 = n6;
                    if (updateOp.itemCount > n6) break;
                    n2 = n6 + 1;
                }
            }
            ++n4;
            n5 = n2;
        }
    }

    void consumePostponedUpdates() {
        int n2 = this.mPostponedList.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            this.mCallback.onDispatchSecondPass(this.mPostponedList.get(i2));
        }
        this.recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    void consumeUpdatesInOnePass() {
        this.consumePostponedUpdates();
        int n2 = this.mPendingUpdates.size();
        int n3 = 0;
        while (true) {
            if (n3 >= n2) {
                this.recycleUpdateOpsAndClearList(this.mPendingUpdates);
                this.mExistingUpdateTypes = 0;
                return;
            }
            UpdateOp updateOp = this.mPendingUpdates.get(n3);
            switch (updateOp.cmd) {
                case 1: {
                    this.mCallback.onDispatchSecondPass(updateOp);
                    this.mCallback.offsetPositionsForAdd(updateOp.positionStart, updateOp.itemCount);
                    break;
                }
                case 2: {
                    this.mCallback.onDispatchSecondPass(updateOp);
                    this.mCallback.offsetPositionsForRemovingInvisible(updateOp.positionStart, updateOp.itemCount);
                    break;
                }
                case 4: {
                    this.mCallback.onDispatchSecondPass(updateOp);
                    this.mCallback.markViewHoldersUpdated(updateOp.positionStart, updateOp.itemCount, updateOp.payload);
                    break;
                }
                case 8: {
                    this.mCallback.onDispatchSecondPass(updateOp);
                    this.mCallback.offsetPositionsForMove(updateOp.positionStart, updateOp.itemCount);
                    break;
                }
            }
            if (this.mOnItemProcessedCallback != null) {
                this.mOnItemProcessedCallback.run();
            }
            ++n3;
        }
    }

    void dispatchFirstPassAndUpdateViewHolders(UpdateOp updateOp, int n2) {
        this.mCallback.onDispatchFirstPass(updateOp);
        switch (updateOp.cmd) {
            default: {
                throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
            }
            case 2: {
                this.mCallback.offsetPositionsForRemovingInvisible(n2, updateOp.itemCount);
                return;
            }
            case 4: 
        }
        this.mCallback.markViewHoldersUpdated(n2, updateOp.itemCount, updateOp.payload);
    }

    int findPositionOffset(int n2) {
        return this.findPositionOffset(n2, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    int findPositionOffset(int n2, int n3) {
        int n4 = this.mPostponedList.size();
        int n5 = n3;
        n3 = n2;
        while (true) {
            block11: {
                UpdateOp updateOp;
                block10: {
                    n2 = n3;
                    if (n5 >= n4) return n2;
                    updateOp = this.mPostponedList.get(n5);
                    if (updateOp.cmd != 8) break block10;
                    if (updateOp.positionStart == n3) {
                        n2 = updateOp.itemCount;
                        break block11;
                    } else {
                        int n6 = n3;
                        if (updateOp.positionStart < n3) {
                            n6 = n3 - 1;
                        }
                        n2 = n6;
                        if (updateOp.itemCount <= n6) {
                            n2 = n6 + 1;
                        }
                    }
                    break block11;
                }
                n2 = n3;
                if (updateOp.positionStart <= n3) {
                    if (updateOp.cmd == 2) {
                        if (n3 < updateOp.positionStart + updateOp.itemCount) {
                            return -1;
                        }
                        n2 = n3 - updateOp.itemCount;
                    } else {
                        n2 = n3;
                        if (updateOp.cmd == 1) {
                            n2 = n3 + updateOp.itemCount;
                        }
                    }
                }
            }
            ++n5;
            n3 = n2;
        }
    }

    boolean hasAnyUpdateTypes(int n2) {
        return (this.mExistingUpdateTypes & n2) != 0;
    }

    boolean hasPendingUpdates() {
        return this.mPendingUpdates.size() > 0;
    }

    boolean hasUpdates() {
        return !this.mPostponedList.isEmpty() && !this.mPendingUpdates.isEmpty();
    }

    @Override
    public UpdateOp obtainUpdateOp(int n2, int n3, int n4, Object object) {
        UpdateOp updateOp = this.mUpdateOpPool.acquire();
        if (updateOp == null) {
            return new UpdateOp(n2, n3, n4, object);
        }
        updateOp.cmd = n2;
        updateOp.positionStart = n3;
        updateOp.itemCount = n4;
        updateOp.payload = object;
        return updateOp;
    }

    boolean onItemRangeChanged(int n2, int n3, Object object) {
        this.mPendingUpdates.add(this.obtainUpdateOp(4, n2, n3, object));
        this.mExistingUpdateTypes |= 4;
        return this.mPendingUpdates.size() == 1;
    }

    boolean onItemRangeInserted(int n2, int n3) {
        this.mPendingUpdates.add(this.obtainUpdateOp(1, n2, n3, null));
        this.mExistingUpdateTypes |= 1;
        return this.mPendingUpdates.size() == 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    boolean onItemRangeMoved(int n2, int n3, int n4) {
        boolean bl2 = true;
        if (n2 == n3) {
            return false;
        }
        if (n4 != 1) {
            throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
        }
        this.mPendingUpdates.add(this.obtainUpdateOp(8, n2, n3, null));
        this.mExistingUpdateTypes |= 8;
        if (this.mPendingUpdates.size() != 1) return false;
        return bl2;
    }

    boolean onItemRangeRemoved(int n2, int n3) {
        this.mPendingUpdates.add(this.obtainUpdateOp(2, n2, n3, null));
        this.mExistingUpdateTypes |= 2;
        return this.mPendingUpdates.size() == 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    void preProcess() {
        this.mOpReorderer.reorderOps(this.mPendingUpdates);
        int n2 = this.mPendingUpdates.size();
        int n3 = 0;
        while (true) {
            if (n3 >= n2) {
                this.mPendingUpdates.clear();
                return;
            }
            UpdateOp updateOp = this.mPendingUpdates.get(n3);
            switch (updateOp.cmd) {
                case 1: {
                    this.applyAdd(updateOp);
                    break;
                }
                case 2: {
                    this.applyRemove(updateOp);
                    break;
                }
                case 4: {
                    this.applyUpdate(updateOp);
                    break;
                }
                case 8: {
                    this.applyMove(updateOp);
                    break;
                }
            }
            if (this.mOnItemProcessedCallback != null) {
                this.mOnItemProcessedCallback.run();
            }
            ++n3;
        }
    }

    @Override
    public void recycleUpdateOp(UpdateOp updateOp) {
        if (!this.mDisableRecycler) {
            updateOp.payload = null;
            this.mUpdateOpPool.release(updateOp);
        }
    }

    void recycleUpdateOpsAndClearList(List<UpdateOp> list) {
        int n2 = list.size();
        for (int i2 = 0; i2 < n2; ++i2) {
            this.recycleUpdateOp(list.get(i2));
        }
        list.clear();
    }

    void reset() {
        this.recycleUpdateOpsAndClearList(this.mPendingUpdates);
        this.recycleUpdateOpsAndClearList(this.mPostponedList);
        this.mExistingUpdateTypes = 0;
    }

    static interface Callback {
        public RecyclerView.ViewHolder findViewHolder(int var1);

        public void markViewHoldersUpdated(int var1, int var2, Object var3);

        public void offsetPositionsForAdd(int var1, int var2);

        public void offsetPositionsForMove(int var1, int var2);

        public void offsetPositionsForRemovingInvisible(int var1, int var2);

        public void offsetPositionsForRemovingLaidOutOrNewView(int var1, int var2);

        public void onDispatchFirstPass(UpdateOp var1);

        public void onDispatchSecondPass(UpdateOp var1);
    }

    static class UpdateOp {
        static final int ADD = 1;
        static final int MOVE = 8;
        static final int POOL_SIZE = 30;
        static final int REMOVE = 2;
        static final int UPDATE = 4;
        int cmd;
        int itemCount;
        Object payload;
        int positionStart;

        UpdateOp(int n2, int n3, int n4, Object object) {
            this.cmd = n2;
            this.positionStart = n3;
            this.itemCount = n4;
            this.payload = object;
        }

        String cmdToString() {
            switch (this.cmd) {
                default: {
                    return "??";
                }
                case 1: {
                    return "add";
                }
                case 2: {
                    return "rm";
                }
                case 4: {
                    return "up";
                }
                case 8: 
            }
            return "mv";
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || this.getClass() != object.getClass()) {
                return false;
            }
            object = (UpdateOp)object;
            if (this.cmd != ((UpdateOp)object).cmd) {
                return false;
            }
            if (this.cmd == 8 && Math.abs(this.itemCount - this.positionStart) == 1 && this.itemCount == ((UpdateOp)object).positionStart && this.positionStart == ((UpdateOp)object).itemCount) return true;
            if (this.itemCount != ((UpdateOp)object).itemCount) {
                return false;
            }
            if (this.positionStart != ((UpdateOp)object).positionStart) {
                return false;
            }
            if (this.payload != null) {
                if (this.payload.equals(((UpdateOp)object).payload)) return true;
                return false;
            }
            if (((UpdateOp)object).payload != null) return false;
            return true;
        }

        public int hashCode() {
            return (this.cmd * 31 + this.positionStart) * 31 + this.itemCount;
        }

        public String toString() {
            return Integer.toHexString(System.identityHashCode(this)) + "[" + this.cmdToString() + ",s:" + this.positionStart + "c:" + this.itemCount + ",p:" + this.payload + "]";
        }
    }
}

