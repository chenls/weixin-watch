/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.Checkable
 *  android.widget.TextView
 */
package ticwear.design.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import android.widget.TextView;

public class CheckedTextView
extends TextView
implements Checkable {
    private static final int[] CHECKED_STATE_SET = new int[]{0x10100A0};
    private boolean mChecked;

    public CheckedTextView(Context context) {
        this(context, null);
    }

    public CheckedTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public CheckedTextView(Context context, AttributeSet attributeSet, int n2) {
        this(context, attributeSet, n2, 0);
    }

    public CheckedTextView(Context context, AttributeSet attributeSet, int n2, int n3) {
        super(context, attributeSet, n2, n3);
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    protected int[] onCreateDrawableState(int n2) {
        int[] nArray = super.onCreateDrawableState(n2 + 1);
        if (this.isChecked()) {
            CheckedTextView.mergeDrawableStates((int[])nArray, (int[])CHECKED_STATE_SET);
        }
        return nArray;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)CheckedTextView.class.getName());
        accessibilityEvent.setChecked(this.mChecked);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)CheckedTextView.class.getName());
        accessibilityNodeInfo.setCheckable(true);
        accessibilityNodeInfo.setChecked(this.mChecked);
    }

    public void setChecked(boolean bl2) {
        if (this.mChecked != bl2) {
            this.mChecked = bl2;
            this.refreshDrawableState();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void toggle() {
        boolean bl2 = !this.mChecked;
        this.setChecked(bl2);
    }
}

