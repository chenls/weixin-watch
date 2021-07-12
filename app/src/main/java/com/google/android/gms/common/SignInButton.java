/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.FrameLayout
 */
package com.google.android.gms.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.R;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.dynamic.zzg;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SignInButton
extends FrameLayout
implements View.OnClickListener {
    public static final int COLOR_AUTO = 2;
    public static final int COLOR_DARK = 0;
    public static final int COLOR_LIGHT = 1;
    public static final int SIZE_ICON_ONLY = 2;
    public static final int SIZE_STANDARD = 0;
    public static final int SIZE_WIDE = 1;
    private int mColor;
    private int mSize;
    private Scope[] zzafT;
    private View zzafU;
    private View.OnClickListener zzafV = null;

    public SignInButton(Context context) {
        this(context, null);
    }

    public SignInButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SignInButton(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
        this.zza(context, attributeSet);
        this.setStyle(this.mSize, this.mColor, this.zzafT);
    }

    private static Button zza(Context context, int n2, int n3, Scope[] scopeArray) {
        zzac zzac2 = new zzac(context);
        zzac2.zza(context.getResources(), n2, n3, scopeArray);
        return zzac2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zza(Context context, AttributeSet object) {
        context = context.getTheme().obtainStyledAttributes((AttributeSet)object, R.styleable.SignInButton, 0, 0);
        try {
            this.mSize = context.getInt(R.styleable.SignInButton_buttonSize, 0);
            this.mColor = context.getInt(R.styleable.SignInButton_colorScheme, 2);
            object = context.getString(R.styleable.SignInButton_scopeUris);
            if (object == null) {
                this.zzafT = null;
                return;
            } else {
                object = ((String)object).trim().split("\\s+");
                this.zzafT = new Scope[((Object)object).length];
                for (int i2 = 0; i2 < ((Object)object).length; ++i2) {
                    this.zzafT[i2] = new Scope(((String)object[i2]).toString());
                }
            }
            return;
        }
        finally {
            context.recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzar(Context context) {
        if (this.zzafU != null) {
            this.removeView(this.zzafU);
        }
        try {
            this.zzafU = zzab.zzb(context, this.mSize, this.mColor, this.zzafT);
        }
        catch (zzg.zza zza2) {
            Log.w((String)"SignInButton", (String)"Sign in button not found, using placeholder instead");
            this.zzafU = SignInButton.zza(context, this.mSize, this.mColor, this.zzafT);
        }
        this.addView(this.zzafU);
        this.zzafU.setEnabled(this.isEnabled());
        this.zzafU.setOnClickListener((View.OnClickListener)this);
    }

    public void onClick(View view) {
        if (this.zzafV != null && view == this.zzafU) {
            this.zzafV.onClick((View)this);
        }
    }

    public void setColorScheme(int n2) {
        this.setStyle(this.mSize, n2, this.zzafT);
    }

    public void setEnabled(boolean bl2) {
        super.setEnabled(bl2);
        this.zzafU.setEnabled(bl2);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.zzafV = onClickListener;
        if (this.zzafU != null) {
            this.zzafU.setOnClickListener((View.OnClickListener)this);
        }
    }

    public void setScopes(Scope[] scopeArray) {
        this.setStyle(this.mSize, this.mColor, scopeArray);
    }

    public void setSize(int n2) {
        this.setStyle(n2, this.mColor, this.zzafT);
    }

    public void setStyle(int n2, int n3) {
        this.setStyle(n2, n3, this.zzafT);
    }

    public void setStyle(int n2, int n3, Scope[] scopeArray) {
        this.mSize = n2;
        this.mColor = n3;
        this.zzafT = scopeArray;
        this.zzar(this.getContext());
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ButtonSize {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ColorScheme {
    }
}

