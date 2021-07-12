/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.view.KeyEvent
 *  android.view.KeyEvent$Callback
 *  android.view.KeyEvent$DispatcherState
 *  android.view.View
 */
package android.support.v4.view;

import android.os.Build;
import android.support.v4.view.KeyEventCompatHoneycomb;
import android.view.KeyEvent;
import android.view.View;

public final class KeyEventCompat {
    static final KeyEventVersionImpl IMPL = Build.VERSION.SDK_INT >= 11 ? new HoneycombKeyEventVersionImpl() : new BaseKeyEventVersionImpl();

    private KeyEventCompat() {
    }

    @Deprecated
    public static boolean dispatch(KeyEvent keyEvent, KeyEvent.Callback callback, Object object, Object object2) {
        return keyEvent.dispatch(callback, (KeyEvent.DispatcherState)object, object2);
    }

    @Deprecated
    public static Object getKeyDispatcherState(View view) {
        return view.getKeyDispatcherState();
    }

    public static boolean hasModifiers(KeyEvent keyEvent, int n2) {
        return IMPL.metaStateHasModifiers(keyEvent.getMetaState(), n2);
    }

    public static boolean hasNoModifiers(KeyEvent keyEvent) {
        return IMPL.metaStateHasNoModifiers(keyEvent.getMetaState());
    }

    public static boolean isCtrlPressed(KeyEvent keyEvent) {
        return IMPL.isCtrlPressed(keyEvent);
    }

    @Deprecated
    public static boolean isTracking(KeyEvent keyEvent) {
        return keyEvent.isTracking();
    }

    public static boolean metaStateHasModifiers(int n2, int n3) {
        return IMPL.metaStateHasModifiers(n2, n3);
    }

    public static boolean metaStateHasNoModifiers(int n2) {
        return IMPL.metaStateHasNoModifiers(n2);
    }

    public static int normalizeMetaState(int n2) {
        return IMPL.normalizeMetaState(n2);
    }

    @Deprecated
    public static void startTracking(KeyEvent keyEvent) {
        keyEvent.startTracking();
    }

    static class BaseKeyEventVersionImpl
    implements KeyEventVersionImpl {
        private static final int META_ALL_MASK = 247;
        private static final int META_MODIFIER_MASK = 247;

        BaseKeyEventVersionImpl() {
        }

        /*
         * Enabled aggressive block sorting
         */
        private static int metaStateFilterDirectionalModifiers(int n2, int n3, int n4, int n5, int n6) {
            int n7 = 1;
            boolean bl2 = (n3 & n4) != 0;
            n3 = (n3 & (n5 |= n6)) != 0 ? n7 : 0;
            if (bl2) {
                if (n3 == 0) return n2 & ~n5;
                throw new IllegalArgumentException("bad arguments");
            }
            n5 = n2;
            if (n3 == 0) return n5;
            return n2 & ~n4;
        }

        @Override
        public boolean isCtrlPressed(KeyEvent keyEvent) {
            return false;
        }

        @Override
        public boolean metaStateHasModifiers(int n2, int n3) {
            return BaseKeyEventVersionImpl.metaStateFilterDirectionalModifiers(BaseKeyEventVersionImpl.metaStateFilterDirectionalModifiers(this.normalizeMetaState(n2) & 0xF7, n3, 1, 64, 128), n3, 2, 16, 32) == n3;
        }

        @Override
        public boolean metaStateHasNoModifiers(int n2) {
            return (this.normalizeMetaState(n2) & 0xF7) == 0;
        }

        @Override
        public int normalizeMetaState(int n2) {
            int n3 = n2;
            if ((n2 & 0xC0) != 0) {
                n3 = n2 | 1;
            }
            n2 = n3;
            if ((n3 & 0x30) != 0) {
                n2 = n3 | 2;
            }
            return n2 & 0xF7;
        }
    }

    static class HoneycombKeyEventVersionImpl
    extends BaseKeyEventVersionImpl {
        HoneycombKeyEventVersionImpl() {
        }

        @Override
        public boolean isCtrlPressed(KeyEvent keyEvent) {
            return KeyEventCompatHoneycomb.isCtrlPressed(keyEvent);
        }

        @Override
        public boolean metaStateHasModifiers(int n2, int n3) {
            return KeyEventCompatHoneycomb.metaStateHasModifiers(n2, n3);
        }

        @Override
        public boolean metaStateHasNoModifiers(int n2) {
            return KeyEventCompatHoneycomb.metaStateHasNoModifiers(n2);
        }

        @Override
        public int normalizeMetaState(int n2) {
            return KeyEventCompatHoneycomb.normalizeMetaState(n2);
        }
    }

    static interface KeyEventVersionImpl {
        public boolean isCtrlPressed(KeyEvent var1);

        public boolean metaStateHasModifiers(int var1, int var2);

        public boolean metaStateHasNoModifiers(int var1);

        public int normalizeMetaState(int var1);
    }
}

