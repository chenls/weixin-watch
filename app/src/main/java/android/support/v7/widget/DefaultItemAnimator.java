/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.widget;

import android.support.annotation.NonNull;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class DefaultItemAnimator
extends SimpleItemAnimator {
    private static final boolean DEBUG = false;
    private ArrayList<RecyclerView.ViewHolder> mAddAnimations;
    private ArrayList<ArrayList<RecyclerView.ViewHolder>> mAdditionsList;
    private ArrayList<RecyclerView.ViewHolder> mChangeAnimations;
    private ArrayList<ArrayList<ChangeInfo>> mChangesList;
    private ArrayList<RecyclerView.ViewHolder> mMoveAnimations;
    private ArrayList<ArrayList<MoveInfo>> mMovesList;
    private ArrayList<RecyclerView.ViewHolder> mPendingAdditions;
    private ArrayList<ChangeInfo> mPendingChanges;
    private ArrayList<MoveInfo> mPendingMoves;
    private ArrayList<RecyclerView.ViewHolder> mPendingRemovals = new ArrayList();
    private ArrayList<RecyclerView.ViewHolder> mRemoveAnimations;

    public DefaultItemAnimator() {
        this.mPendingAdditions = new ArrayList();
        this.mPendingMoves = new ArrayList();
        this.mPendingChanges = new ArrayList();
        this.mAdditionsList = new ArrayList();
        this.mMovesList = new ArrayList();
        this.mChangesList = new ArrayList();
        this.mAddAnimations = new ArrayList();
        this.mMoveAnimations = new ArrayList();
        this.mRemoveAnimations = new ArrayList();
        this.mChangeAnimations = new ArrayList();
    }

    private void animateAddImpl(final RecyclerView.ViewHolder viewHolder) {
        final ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate(viewHolder.itemView);
        this.mAddAnimations.add(viewHolder);
        viewPropertyAnimatorCompat.alpha(1.0f).setDuration(this.getAddDuration()).setListener(new VpaListenerAdapter(){

            @Override
            public void onAnimationCancel(View view) {
                ViewCompat.setAlpha(view, 1.0f);
            }

            @Override
            public void onAnimationEnd(View view) {
                viewPropertyAnimatorCompat.setListener(null);
                DefaultItemAnimator.this.dispatchAddFinished(viewHolder);
                DefaultItemAnimator.this.mAddAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            @Override
            public void onAnimationStart(View view) {
                DefaultItemAnimator.this.dispatchAddStarting(viewHolder);
            }
        }).start();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void animateChangeImpl(final ChangeInfo changeInfo) {
        Object object = changeInfo.oldHolder;
        object = object == null ? null : ((RecyclerView.ViewHolder)object).itemView;
        RecyclerView.ViewHolder viewHolder = changeInfo.newHolder;
        viewHolder = viewHolder != null ? viewHolder.itemView : null;
        if (object != null) {
            object = ViewCompat.animate((View)object).setDuration(this.getChangeDuration());
            this.mChangeAnimations.add(changeInfo.oldHolder);
            ((ViewPropertyAnimatorCompat)object).translationX(changeInfo.toX - changeInfo.fromX);
            ((ViewPropertyAnimatorCompat)object).translationY(changeInfo.toY - changeInfo.fromY);
            ((ViewPropertyAnimatorCompat)object).alpha(0.0f).setListener(new VpaListenerAdapter((ViewPropertyAnimatorCompat)object){
                final /* synthetic */ ViewPropertyAnimatorCompat val$oldViewAnim;
                {
                    this.val$oldViewAnim = viewPropertyAnimatorCompat;
                }

                @Override
                public void onAnimationEnd(View view) {
                    this.val$oldViewAnim.setListener(null);
                    ViewCompat.setAlpha(view, 1.0f);
                    ViewCompat.setTranslationX(view, 0.0f);
                    ViewCompat.setTranslationY(view, 0.0f);
                    DefaultItemAnimator.this.dispatchChangeFinished(changeInfo.oldHolder, true);
                    DefaultItemAnimator.this.mChangeAnimations.remove(changeInfo.oldHolder);
                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                }

                @Override
                public void onAnimationStart(View view) {
                    DefaultItemAnimator.this.dispatchChangeStarting(changeInfo.oldHolder, true);
                }
            }).start();
        }
        if (viewHolder != null) {
            object = ViewCompat.animate((View)viewHolder);
            this.mChangeAnimations.add(changeInfo.newHolder);
            ((ViewPropertyAnimatorCompat)object).translationX(0.0f).translationY(0.0f).setDuration(this.getChangeDuration()).alpha(1.0f).setListener(new VpaListenerAdapter((ViewPropertyAnimatorCompat)object, (View)viewHolder){
                final /* synthetic */ View val$newView;
                final /* synthetic */ ViewPropertyAnimatorCompat val$newViewAnimation;
                {
                    this.val$newViewAnimation = viewPropertyAnimatorCompat;
                    this.val$newView = view;
                }

                @Override
                public void onAnimationEnd(View view) {
                    this.val$newViewAnimation.setListener(null);
                    ViewCompat.setAlpha(this.val$newView, 1.0f);
                    ViewCompat.setTranslationX(this.val$newView, 0.0f);
                    ViewCompat.setTranslationY(this.val$newView, 0.0f);
                    DefaultItemAnimator.this.dispatchChangeFinished(changeInfo.newHolder, false);
                    DefaultItemAnimator.this.mChangeAnimations.remove(changeInfo.newHolder);
                    DefaultItemAnimator.this.dispatchFinishedWhenDone();
                }

                @Override
                public void onAnimationStart(View view) {
                    DefaultItemAnimator.this.dispatchChangeStarting(changeInfo.newHolder, false);
                }
            }).start();
        }
    }

    private void animateMoveImpl(final RecyclerView.ViewHolder viewHolder, final int n2, final int n3, int n4, int n5) {
        Object object = viewHolder.itemView;
        n2 = n4 - n2;
        n3 = n5 - n3;
        if (n2 != 0) {
            ViewCompat.animate((View)object).translationX(0.0f);
        }
        if (n3 != 0) {
            ViewCompat.animate((View)object).translationY(0.0f);
        }
        object = ViewCompat.animate((View)object);
        this.mMoveAnimations.add(viewHolder);
        ((ViewPropertyAnimatorCompat)object).setDuration(this.getMoveDuration()).setListener(new VpaListenerAdapter((ViewPropertyAnimatorCompat)object){
            final /* synthetic */ ViewPropertyAnimatorCompat val$animation;
            {
                this.val$animation = viewPropertyAnimatorCompat;
            }

            @Override
            public void onAnimationCancel(View view) {
                if (n2 != 0) {
                    ViewCompat.setTranslationX(view, 0.0f);
                }
                if (n3 != 0) {
                    ViewCompat.setTranslationY(view, 0.0f);
                }
            }

            @Override
            public void onAnimationEnd(View view) {
                this.val$animation.setListener(null);
                DefaultItemAnimator.this.dispatchMoveFinished(viewHolder);
                DefaultItemAnimator.this.mMoveAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            @Override
            public void onAnimationStart(View view) {
                DefaultItemAnimator.this.dispatchMoveStarting(viewHolder);
            }
        }).start();
    }

    private void animateRemoveImpl(final RecyclerView.ViewHolder viewHolder) {
        final ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate(viewHolder.itemView);
        this.mRemoveAnimations.add(viewHolder);
        viewPropertyAnimatorCompat.setDuration(this.getRemoveDuration()).alpha(0.0f).setListener(new VpaListenerAdapter(){

            @Override
            public void onAnimationEnd(View view) {
                viewPropertyAnimatorCompat.setListener(null);
                ViewCompat.setAlpha(view, 1.0f);
                DefaultItemAnimator.this.dispatchRemoveFinished(viewHolder);
                DefaultItemAnimator.this.mRemoveAnimations.remove(viewHolder);
                DefaultItemAnimator.this.dispatchFinishedWhenDone();
            }

            @Override
            public void onAnimationStart(View view) {
                DefaultItemAnimator.this.dispatchRemoveStarting(viewHolder);
            }
        }).start();
    }

    private void dispatchFinishedWhenDone() {
        if (!this.isRunning()) {
            this.dispatchAnimationsFinished();
        }
    }

    private void endChangeAnimation(List<ChangeInfo> list, RecyclerView.ViewHolder viewHolder) {
        for (int i2 = list.size() - 1; i2 >= 0; --i2) {
            ChangeInfo changeInfo = list.get(i2);
            if (!this.endChangeAnimationIfNecessary(changeInfo, viewHolder) || changeInfo.oldHolder != null || changeInfo.newHolder != null) continue;
            list.remove(changeInfo);
        }
    }

    private void endChangeAnimationIfNecessary(ChangeInfo changeInfo) {
        if (changeInfo.oldHolder != null) {
            this.endChangeAnimationIfNecessary(changeInfo, changeInfo.oldHolder);
        }
        if (changeInfo.newHolder != null) {
            this.endChangeAnimationIfNecessary(changeInfo, changeInfo.newHolder);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean endChangeAnimationIfNecessary(ChangeInfo changeInfo, RecyclerView.ViewHolder viewHolder) {
        boolean bl2 = false;
        if (changeInfo.newHolder == viewHolder) {
            changeInfo.newHolder = null;
        } else {
            if (changeInfo.oldHolder != viewHolder) {
                return false;
            }
            changeInfo.oldHolder = null;
            bl2 = true;
        }
        ViewCompat.setAlpha(viewHolder.itemView, 1.0f);
        ViewCompat.setTranslationX(viewHolder.itemView, 0.0f);
        ViewCompat.setTranslationY(viewHolder.itemView, 0.0f);
        this.dispatchChangeFinished(viewHolder, bl2);
        return true;
    }

    private void resetAnimation(RecyclerView.ViewHolder viewHolder) {
        AnimatorCompatHelper.clearInterpolator(viewHolder.itemView);
        this.endAnimation(viewHolder);
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder viewHolder) {
        this.resetAnimation(viewHolder);
        ViewCompat.setAlpha(viewHolder.itemView, 0.0f);
        this.mPendingAdditions.add(viewHolder);
        return true;
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int n2, int n3, int n4, int n5) {
        if (viewHolder == viewHolder2) {
            return this.animateMove(viewHolder, n2, n3, n4, n5);
        }
        float f2 = ViewCompat.getTranslationX(viewHolder.itemView);
        float f3 = ViewCompat.getTranslationY(viewHolder.itemView);
        float f4 = ViewCompat.getAlpha(viewHolder.itemView);
        this.resetAnimation(viewHolder);
        int n6 = (int)((float)(n4 - n2) - f2);
        int n7 = (int)((float)(n5 - n3) - f3);
        ViewCompat.setTranslationX(viewHolder.itemView, f2);
        ViewCompat.setTranslationY(viewHolder.itemView, f3);
        ViewCompat.setAlpha(viewHolder.itemView, f4);
        if (viewHolder2 != null) {
            this.resetAnimation(viewHolder2);
            ViewCompat.setTranslationX(viewHolder2.itemView, -n6);
            ViewCompat.setTranslationY(viewHolder2.itemView, -n7);
            ViewCompat.setAlpha(viewHolder2.itemView, 0.0f);
        }
        this.mPendingChanges.add(new ChangeInfo(viewHolder, viewHolder2, n2, n3, n4, n5));
        return true;
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder viewHolder, int n2, int n3, int n4, int n5) {
        View view = viewHolder.itemView;
        n2 = (int)((float)n2 + ViewCompat.getTranslationX(viewHolder.itemView));
        n3 = (int)((float)n3 + ViewCompat.getTranslationY(viewHolder.itemView));
        this.resetAnimation(viewHolder);
        int n6 = n4 - n2;
        int n7 = n5 - n3;
        if (n6 == 0 && n7 == 0) {
            this.dispatchMoveFinished(viewHolder);
            return false;
        }
        if (n6 != 0) {
            ViewCompat.setTranslationX(view, -n6);
        }
        if (n7 != 0) {
            ViewCompat.setTranslationY(view, -n7);
        }
        this.mPendingMoves.add(new MoveInfo(viewHolder, n2, n3, n4, n5));
        return true;
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder viewHolder) {
        this.resetAnimation(viewHolder);
        this.mPendingRemovals.add(viewHolder);
        return true;
    }

    @Override
    public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull List<Object> list) {
        return !list.isEmpty() || super.canReuseUpdatedViewHolder(viewHolder, list);
    }

    void cancelAll(List<RecyclerView.ViewHolder> list) {
        for (int i2 = list.size() - 1; i2 >= 0; --i2) {
            ViewCompat.animate(list.get((int)i2).itemView).cancel();
        }
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder viewHolder) {
        ArrayList<Object> arrayList;
        int n2;
        View view = viewHolder.itemView;
        ViewCompat.animate(view).cancel();
        for (n2 = this.mPendingMoves.size() - 1; n2 >= 0; --n2) {
            if (this.mPendingMoves.get((int)n2).holder != viewHolder) continue;
            ViewCompat.setTranslationY(view, 0.0f);
            ViewCompat.setTranslationX(view, 0.0f);
            this.dispatchMoveFinished(viewHolder);
            this.mPendingMoves.remove(n2);
        }
        this.endChangeAnimation(this.mPendingChanges, viewHolder);
        if (this.mPendingRemovals.remove(viewHolder)) {
            ViewCompat.setAlpha(view, 1.0f);
            this.dispatchRemoveFinished(viewHolder);
        }
        if (this.mPendingAdditions.remove(viewHolder)) {
            ViewCompat.setAlpha(view, 1.0f);
            this.dispatchAddFinished(viewHolder);
        }
        for (n2 = this.mChangesList.size() - 1; n2 >= 0; --n2) {
            arrayList = this.mChangesList.get(n2);
            this.endChangeAnimation(arrayList, viewHolder);
            if (!arrayList.isEmpty()) continue;
            this.mChangesList.remove(n2);
        }
        block2: for (n2 = this.mMovesList.size() - 1; n2 >= 0; --n2) {
            arrayList = this.mMovesList.get(n2);
            int n3 = arrayList.size() - 1;
            while (true) {
                if (n3 < 0) continue block2;
                if (((MoveInfo)arrayList.get((int)n3)).holder == viewHolder) {
                    ViewCompat.setTranslationY(view, 0.0f);
                    ViewCompat.setTranslationX(view, 0.0f);
                    this.dispatchMoveFinished(viewHolder);
                    arrayList.remove(n3);
                    if (!arrayList.isEmpty()) continue block2;
                    this.mMovesList.remove(n2);
                    continue block2;
                }
                --n3;
            }
        }
        for (n2 = this.mAdditionsList.size() - 1; n2 >= 0; --n2) {
            arrayList = this.mAdditionsList.get(n2);
            if (!arrayList.remove(viewHolder)) continue;
            ViewCompat.setAlpha(view, 1.0f);
            this.dispatchAddFinished(viewHolder);
            if (!arrayList.isEmpty()) continue;
            this.mAdditionsList.remove(n2);
        }
        if (this.mRemoveAnimations.remove(viewHolder)) {
            // empty if block
        }
        if (this.mAddAnimations.remove(viewHolder)) {
            // empty if block
        }
        if (this.mChangeAnimations.remove(viewHolder)) {
            // empty if block
        }
        if (this.mMoveAnimations.remove(viewHolder)) {
            // empty if block
        }
        this.dispatchFinishedWhenDone();
    }

    @Override
    public void endAnimations() {
        int n2;
        Object object;
        ArrayList<Object> arrayList;
        int n3;
        for (n3 = this.mPendingMoves.size() - 1; n3 >= 0; --n3) {
            arrayList = this.mPendingMoves.get(n3);
            object = arrayList.holder.itemView;
            ViewCompat.setTranslationY(object, 0.0f);
            ViewCompat.setTranslationX(object, 0.0f);
            this.dispatchMoveFinished(((MoveInfo)arrayList).holder);
            this.mPendingMoves.remove(n3);
        }
        for (n3 = this.mPendingRemovals.size() - 1; n3 >= 0; --n3) {
            this.dispatchRemoveFinished(this.mPendingRemovals.get(n3));
            this.mPendingRemovals.remove(n3);
        }
        for (n3 = this.mPendingAdditions.size() - 1; n3 >= 0; --n3) {
            arrayList = this.mPendingAdditions.get(n3);
            ViewCompat.setAlpha(((RecyclerView.ViewHolder)arrayList).itemView, 1.0f);
            this.dispatchAddFinished((RecyclerView.ViewHolder)((Object)arrayList));
            this.mPendingAdditions.remove(n3);
        }
        for (n3 = this.mPendingChanges.size() - 1; n3 >= 0; --n3) {
            this.endChangeAnimationIfNecessary(this.mPendingChanges.get(n3));
        }
        this.mPendingChanges.clear();
        if (!this.isRunning()) {
            return;
        }
        for (n3 = this.mMovesList.size() - 1; n3 >= 0; --n3) {
            arrayList = this.mMovesList.get(n3);
            for (n2 = arrayList.size() - 1; n2 >= 0; --n2) {
                object = arrayList.get(n2);
                View view = object.holder.itemView;
                ViewCompat.setTranslationY(view, 0.0f);
                ViewCompat.setTranslationX(view, 0.0f);
                this.dispatchMoveFinished(object.holder);
                arrayList.remove(n2);
                if (!arrayList.isEmpty()) continue;
                this.mMovesList.remove(arrayList);
            }
        }
        for (n3 = this.mAdditionsList.size() - 1; n3 >= 0; --n3) {
            arrayList = this.mAdditionsList.get(n3);
            for (n2 = arrayList.size() - 1; n2 >= 0; --n2) {
                object = (RecyclerView.ViewHolder)arrayList.get(n2);
                ViewCompat.setAlpha(object.itemView, 1.0f);
                this.dispatchAddFinished((RecyclerView.ViewHolder)object);
                arrayList.remove(n2);
                if (!arrayList.isEmpty()) continue;
                this.mAdditionsList.remove(arrayList);
            }
        }
        for (n3 = this.mChangesList.size() - 1; n3 >= 0; --n3) {
            arrayList = this.mChangesList.get(n3);
            for (n2 = arrayList.size() - 1; n2 >= 0; --n2) {
                this.endChangeAnimationIfNecessary((ChangeInfo)arrayList.get(n2));
                if (!arrayList.isEmpty()) continue;
                this.mChangesList.remove(arrayList);
            }
        }
        this.cancelAll(this.mRemoveAnimations);
        this.cancelAll(this.mMoveAnimations);
        this.cancelAll(this.mAddAnimations);
        this.cancelAll(this.mChangeAnimations);
        this.dispatchAnimationsFinished();
    }

    @Override
    public boolean isRunning() {
        return !this.mPendingAdditions.isEmpty() || !this.mPendingChanges.isEmpty() || !this.mPendingMoves.isEmpty() || !this.mPendingRemovals.isEmpty() || !this.mMoveAnimations.isEmpty() || !this.mRemoveAnimations.isEmpty() || !this.mAddAnimations.isEmpty() || !this.mChangeAnimations.isEmpty() || !this.mMovesList.isEmpty() || !this.mAdditionsList.isEmpty() || !this.mChangesList.isEmpty();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void runPendingAnimations() {
        Runnable runnable;
        ArrayList<MoveInfo> arrayList;
        boolean bl2;
        boolean bl3;
        boolean bl4;
        block12: {
            block11: {
                bl4 = !this.mPendingRemovals.isEmpty();
                bl3 = !this.mPendingMoves.isEmpty();
                bl2 = !this.mPendingChanges.isEmpty();
                boolean bl5 = !this.mPendingAdditions.isEmpty();
                if (!bl4 && !bl3 && !bl5 && !bl2) break block11;
                arrayList = this.mPendingRemovals.iterator();
                while (arrayList.hasNext()) {
                    this.animateRemoveImpl(arrayList.next());
                }
                this.mPendingRemovals.clear();
                if (bl3) {
                    arrayList = new ArrayList();
                    arrayList.addAll(this.mPendingMoves);
                    this.mMovesList.add(arrayList);
                    this.mPendingMoves.clear();
                    runnable = new Runnable(){

                        @Override
                        public void run() {
                            for (MoveInfo moveInfo : arrayList) {
                                DefaultItemAnimator.this.animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY, moveInfo.toX, moveInfo.toY);
                            }
                            arrayList.clear();
                            DefaultItemAnimator.this.mMovesList.remove(arrayList);
                        }
                    };
                    if (bl4) {
                        ViewCompat.postOnAnimationDelayed(((MoveInfo)arrayList.get((int)0)).holder.itemView, runnable, this.getRemoveDuration());
                    } else {
                        runnable.run();
                    }
                }
                if (bl2) {
                    arrayList = new ArrayList<MoveInfo>();
                    arrayList.addAll(this.mPendingChanges);
                    this.mChangesList.add(arrayList);
                    this.mPendingChanges.clear();
                    runnable = new Runnable(){

                        @Override
                        public void run() {
                            for (ChangeInfo changeInfo : arrayList) {
                                DefaultItemAnimator.this.animateChangeImpl(changeInfo);
                            }
                            arrayList.clear();
                            DefaultItemAnimator.this.mChangesList.remove(arrayList);
                        }
                    };
                    if (bl4) {
                        ViewCompat.postOnAnimationDelayed(((ChangeInfo)arrayList.get((int)0)).oldHolder.itemView, runnable, this.getRemoveDuration());
                    } else {
                        runnable.run();
                    }
                }
                if (bl5) break block12;
            }
            return;
        }
        arrayList = new ArrayList();
        arrayList.addAll(this.mPendingAdditions);
        this.mAdditionsList.add(arrayList);
        this.mPendingAdditions.clear();
        runnable = new Runnable(){

            @Override
            public void run() {
                for (RecyclerView.ViewHolder viewHolder : arrayList) {
                    DefaultItemAnimator.this.animateAddImpl(viewHolder);
                }
                arrayList.clear();
                DefaultItemAnimator.this.mAdditionsList.remove(arrayList);
            }
        };
        if (!(bl4 || bl3 || bl2)) {
            runnable.run();
            return;
        }
        long l2 = bl4 ? this.getRemoveDuration() : 0L;
        long l3 = bl3 ? this.getMoveDuration() : 0L;
        long l4 = bl2 ? this.getChangeDuration() : 0L;
        l3 = Math.max(l3, l4);
        ViewCompat.postOnAnimationDelayed(((RecyclerView.ViewHolder)arrayList.get((int)0)).itemView, runnable, l2 + l3);
    }

    private static class ChangeInfo {
        public int fromX;
        public int fromY;
        public RecyclerView.ViewHolder newHolder;
        public RecyclerView.ViewHolder oldHolder;
        public int toX;
        public int toY;

        private ChangeInfo(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            this.oldHolder = viewHolder;
            this.newHolder = viewHolder2;
        }

        private ChangeInfo(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2, int n2, int n3, int n4, int n5) {
            this(viewHolder, viewHolder2);
            this.fromX = n2;
            this.fromY = n3;
            this.toX = n4;
            this.toY = n5;
        }

        public String toString() {
            return "ChangeInfo{oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
        }
    }

    private static class MoveInfo {
        public int fromX;
        public int fromY;
        public RecyclerView.ViewHolder holder;
        public int toX;
        public int toY;

        private MoveInfo(RecyclerView.ViewHolder viewHolder, int n2, int n3, int n4, int n5) {
            this.holder = viewHolder;
            this.fromX = n2;
            this.fromY = n3;
            this.toX = n4;
            this.toY = n5;
        }
    }

    private static class VpaListenerAdapter
    implements ViewPropertyAnimatorListener {
        private VpaListenerAdapter() {
        }

        @Override
        public void onAnimationCancel(View view) {
        }

        @Override
        public void onAnimationEnd(View view) {
        }

        @Override
        public void onAnimationStart(View view) {
        }
    }
}

