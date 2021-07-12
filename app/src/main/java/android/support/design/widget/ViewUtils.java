/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.view.View
 */
package android.support.design.widget;

import android.os.Build;
import android.support.design.widget.ValueAnimatorCompat;
import android.support.design.widget.ValueAnimatorCompatImplEclairMr1;
import android.support.design.widget.ValueAnimatorCompatImplHoneycombMr1;
import android.support.design.widget.ViewUtilsLollipop;
import android.view.View;

class ViewUtils {
    static final ValueAnimatorCompat.Creator DEFAULT_ANIMATOR_CREATOR = new ValueAnimatorCompat.Creator(){

        /*
         * WARNING - void declaration
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public ValueAnimatorCompat createAnimator() {
            void var1_2;
            if (Build.VERSION.SDK_INT >= 12) {
                ValueAnimatorCompatImplHoneycombMr1 valueAnimatorCompatImplHoneycombMr1 = new ValueAnimatorCompatImplHoneycombMr1();
                return new ValueAnimatorCompat((ValueAnimatorCompat.Impl)var1_2);
            }
            ValueAnimatorCompatImplEclairMr1 valueAnimatorCompatImplEclairMr1 = new ValueAnimatorCompatImplEclairMr1();
            return new ValueAnimatorCompat((ValueAnimatorCompat.Impl)var1_2);
        }
    };
    private static final ViewUtilsImpl IMPL = Build.VERSION.SDK_INT >= 21 ? new ViewUtilsImplLollipop() : new ViewUtilsImplBase();

    ViewUtils() {
    }

    static ValueAnimatorCompat createAnimator() {
        return DEFAULT_ANIMATOR_CREATOR.createAnimator();
    }

    static void setBoundsViewOutlineProvider(View view) {
        IMPL.setBoundsViewOutlineProvider(view);
    }

    private static interface ViewUtilsImpl {
        public void setBoundsViewOutlineProvider(View var1);
    }

    private static class ViewUtilsImplBase
    implements ViewUtilsImpl {
        private ViewUtilsImplBase() {
        }

        @Override
        public void setBoundsViewOutlineProvider(View view) {
        }
    }

    private static class ViewUtilsImplLollipop
    implements ViewUtilsImpl {
        private ViewUtilsImplLollipop() {
        }

        @Override
        public void setBoundsViewOutlineProvider(View view) {
            ViewUtilsLollipop.setBoundsViewOutlineProvider(view);
        }
    }
}

