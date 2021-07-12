/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.os.Bundle
 *  android.view.KeyEvent
 *  android.view.WindowManager$LayoutParams
 *  android.view.accessibility.AccessibilityEvent
 */
package ticwear.design.internal.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import ticwear.design.internal.app.AlertController;

public abstract class AlertActivity
extends Activity
implements DialogInterface {
    protected AlertController mAlert;
    protected AlertController.AlertParams mAlertParams;

    public void cancel() {
        this.finish();
    }

    public void dismiss() {
        if (!this.isFinishing()) {
            this.finish();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setClassName((CharSequence)Dialog.class.getName());
        accessibilityEvent.setPackageName((CharSequence)this.getPackageName());
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        boolean bl2 = layoutParams.width == -1 && layoutParams.height == -1;
        accessibilityEvent.setFullScreen(bl2);
        return false;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mAlert = new AlertController((Context)this, this, this.getWindow());
        this.mAlertParams = new AlertController.AlertParams((Context)this);
    }

    public boolean onKeyDown(int n2, KeyEvent keyEvent) {
        if (this.mAlert.onKeyDown(n2, keyEvent)) {
            return true;
        }
        return super.onKeyDown(n2, keyEvent);
    }

    public boolean onKeyUp(int n2, KeyEvent keyEvent) {
        if (this.mAlert.onKeyUp(n2, keyEvent)) {
            return true;
        }
        return super.onKeyUp(n2, keyEvent);
    }

    protected void setupAlert() {
        this.mAlertParams.apply(this.mAlert);
        this.mAlert.installContent();
    }
}

