/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accessibilityservice.AccessibilityServiceInfo
 *  android.view.accessibility.AccessibilityManager
 *  android.view.accessibility.AccessibilityManager$AccessibilityStateChangeListener
 */
package android.support.v4.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityManager;
import java.util.List;

class AccessibilityManagerCompatIcs {
    AccessibilityManagerCompatIcs() {
    }

    public static boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerWrapper accessibilityStateChangeListenerWrapper) {
        return accessibilityManager.addAccessibilityStateChangeListener((AccessibilityManager.AccessibilityStateChangeListener)accessibilityStateChangeListenerWrapper);
    }

    public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager accessibilityManager, int n2) {
        return accessibilityManager.getEnabledAccessibilityServiceList(n2);
    }

    public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager accessibilityManager) {
        return accessibilityManager.getInstalledAccessibilityServiceList();
    }

    public static boolean isTouchExplorationEnabled(AccessibilityManager accessibilityManager) {
        return accessibilityManager.isTouchExplorationEnabled();
    }

    public static boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerWrapper accessibilityStateChangeListenerWrapper) {
        return accessibilityManager.removeAccessibilityStateChangeListener((AccessibilityManager.AccessibilityStateChangeListener)accessibilityStateChangeListenerWrapper);
    }

    static interface AccessibilityStateChangeListenerBridge {
        public void onAccessibilityStateChanged(boolean var1);
    }

    public static class AccessibilityStateChangeListenerWrapper
    implements AccessibilityManager.AccessibilityStateChangeListener {
        Object mListener;
        AccessibilityStateChangeListenerBridge mListenerBridge;

        public AccessibilityStateChangeListenerWrapper(Object object, AccessibilityStateChangeListenerBridge accessibilityStateChangeListenerBridge) {
            this.mListener = object;
            this.mListenerBridge = accessibilityStateChangeListenerBridge;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block6: {
                block5: {
                    if (this == object) break block5;
                    if (object == null || this.getClass() != object.getClass()) {
                        return false;
                    }
                    object = (AccessibilityStateChangeListenerWrapper)object;
                    if (this.mListener != null) {
                        return this.mListener.equals(((AccessibilityStateChangeListenerWrapper)object).mListener);
                    }
                    if (((AccessibilityStateChangeListenerWrapper)object).mListener != null) break block6;
                }
                return true;
            }
            return false;
        }

        public int hashCode() {
            if (this.mListener == null) {
                return 0;
            }
            return this.mListener.hashCode();
        }

        public void onAccessibilityStateChanged(boolean bl2) {
            this.mListenerBridge.onAccessibilityStateChanged(bl2);
        }
    }
}

