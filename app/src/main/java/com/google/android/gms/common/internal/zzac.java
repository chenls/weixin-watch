/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.util.AttributeSet
 *  android.widget.Button
 */
package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import com.google.android.gms.R;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzx;

public final class zzac
extends Button {
    public zzac(Context context) {
        this(context, null);
    }

    public zzac(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842824);
    }

    private void zza(Resources resources) {
        this.setTypeface(Typeface.DEFAULT_BOLD);
        this.setTextSize(14.0f);
        float f2 = resources.getDisplayMetrics().density;
        this.setMinHeight((int)(f2 * 48.0f + 0.5f));
        this.setMinWidth((int)(f2 * 48.0f + 0.5f));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zza(Resources resources, int n2, int n3, boolean bl2) {
        n2 = bl2 ? this.zzd(n2, this.zzf(n3, R.drawable.common_plus_signin_btn_icon_dark, R.drawable.common_plus_signin_btn_icon_light, R.drawable.common_plus_signin_btn_icon_dark), this.zzf(n3, R.drawable.common_plus_signin_btn_text_dark, R.drawable.common_plus_signin_btn_text_light, R.drawable.common_plus_signin_btn_text_dark)) : this.zzd(n2, this.zzf(n3, R.drawable.common_google_signin_btn_icon_dark, R.drawable.common_google_signin_btn_icon_light, R.drawable.common_google_signin_btn_icon_light), this.zzf(n3, R.drawable.common_google_signin_btn_text_dark, R.drawable.common_google_signin_btn_text_light, R.drawable.common_google_signin_btn_text_light));
        this.setBackgroundDrawable(resources.getDrawable(n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean zza(Scope[] scopeArray) {
        if (scopeArray != null) {
            int n2 = scopeArray.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                String string2 = scopeArray[i2].zzpb();
                if (string2.contains("/plus.") && !string2.equals("https://www.googleapis.com/auth/plus.me")) {
                    return true;
                }
                if (!string2.equals("https://www.googleapis.com/auth/games")) continue;
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void zzb(Resources resources, int n2, int n3, boolean bl2) {
        n3 = bl2 ? this.zzf(n3, R.color.common_plus_signin_btn_text_dark, R.color.common_plus_signin_btn_text_light, R.color.common_plus_signin_btn_text_dark) : this.zzf(n3, R.color.common_google_signin_btn_text_dark, R.color.common_google_signin_btn_text_light, R.color.common_google_signin_btn_text_light);
        this.setTextColor(zzx.zzz(resources.getColorStateList(n3)));
        switch (n2) {
            default: {
                throw new IllegalStateException("Unknown button size: " + n2);
            }
            case 0: {
                this.setText(resources.getString(R.string.common_signin_button_text));
                break;
            }
            case 1: {
                this.setText(resources.getString(R.string.common_signin_button_text_long));
                break;
            }
            case 2: {
                this.setText(null);
            }
        }
        this.setTransformationMethod(null);
    }

    private int zzd(int n2, int n3, int n4) {
        switch (n2) {
            default: {
                throw new IllegalStateException("Unknown button size: " + n2);
            }
            case 2: {
                n4 = n3;
            }
            case 0: 
            case 1: 
        }
        return n4;
    }

    private int zzf(int n2, int n3, int n4, int n5) {
        switch (n2) {
            default: {
                throw new IllegalStateException("Unknown color scheme: " + n2);
            }
            case 1: {
                n3 = n4;
            }
            case 0: {
                return n3;
            }
            case 2: 
        }
        return n5;
    }

    public void zza(Resources resources, int n2, int n3, Scope[] scopeArray) {
        boolean bl2 = this.zza(scopeArray);
        this.zza(resources);
        this.zza(resources, n2, n3, bl2);
        this.zzb(resources, n2, n3, bl2);
    }
}

