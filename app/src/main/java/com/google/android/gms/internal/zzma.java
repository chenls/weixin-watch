/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.SystemClock
 */
package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import com.google.android.gms.internal.zzne;

public final class zzma
extends Drawable
implements Drawable.Callback {
    private int mFrom;
    private long zzRD;
    private boolean zzajT = true;
    private int zzaka = 0;
    private int zzakb;
    private int zzakc = 255;
    private int zzakd;
    private int zzake = 0;
    private boolean zzakf;
    private zzb zzakg;
    private Drawable zzakh;
    private Drawable zzaki;
    private boolean zzakj;
    private boolean zzakk;
    private boolean zzakl;
    private int zzakm;

    public zzma(Drawable object, Drawable object2) {
        this(null);
        Drawable drawable2 = object;
        if (object == null) {
            drawable2 = zza.zzakn;
        }
        this.zzakh = drawable2;
        drawable2.setCallback((Drawable.Callback)this);
        object = this.zzakg;
        object.zzakq |= drawable2.getChangingConfigurations();
        object = object2;
        if (object2 == null) {
            object = zza.zzakn;
        }
        this.zzaki = object;
        object.setCallback((Drawable.Callback)this);
        object2 = this.zzakg;
        object2.zzakq |= object.getChangingConfigurations();
    }

    zzma(zzb zzb2) {
        this.zzakg = new zzb(zzb2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean canConstantState() {
        if (!this.zzakj) {
            boolean bl2 = this.zzakh.getConstantState() != null && this.zzaki.getConstantState() != null;
            this.zzakk = bl2;
            this.zzakj = true;
        }
        return this.zzakk;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void draw(Canvas canvas) {
        int n2 = 1;
        int n3 = 1;
        int n4 = 0;
        switch (this.zzaka) {
            case 1: {
                this.zzRD = SystemClock.uptimeMillis();
                this.zzaka = 2;
                n3 = n4;
                break;
            }
            case 2: {
                if (this.zzRD < 0L) break;
                float f2 = (float)(SystemClock.uptimeMillis() - this.zzRD) / (float)this.zzakd;
                n3 = f2 >= 1.0f ? n2 : 0;
                if (n3 != 0) {
                    this.zzaka = 0;
                }
                f2 = Math.min(f2, 1.0f);
                float f3 = this.mFrom;
                this.zzake = (int)(f2 * (float)(this.zzakb - this.mFrom) + f3);
            }
        }
        n2 = this.zzake;
        boolean bl2 = this.zzajT;
        Drawable drawable2 = this.zzakh;
        Drawable drawable3 = this.zzaki;
        if (n3 != 0) {
            if (!bl2 || n2 == 0) {
                drawable2.draw(canvas);
            }
            if (n2 == this.zzakc) {
                drawable3.setAlpha(this.zzakc);
                drawable3.draw(canvas);
            }
            return;
        }
        if (bl2) {
            drawable2.setAlpha(this.zzakc - n2);
        }
        drawable2.draw(canvas);
        if (bl2) {
            drawable2.setAlpha(this.zzakc);
        }
        if (n2 > 0) {
            drawable3.setAlpha(n2);
            drawable3.draw(canvas);
            drawable3.setAlpha(this.zzakc);
        }
        this.invalidateSelf();
    }

    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.zzakg.zzakp | this.zzakg.zzakq;
    }

    public Drawable.ConstantState getConstantState() {
        if (this.canConstantState()) {
            this.zzakg.zzakp = this.getChangingConfigurations();
            return this.zzakg;
        }
        return null;
    }

    public int getIntrinsicHeight() {
        return Math.max(this.zzakh.getIntrinsicHeight(), this.zzaki.getIntrinsicHeight());
    }

    public int getIntrinsicWidth() {
        return Math.max(this.zzakh.getIntrinsicWidth(), this.zzaki.getIntrinsicWidth());
    }

    public int getOpacity() {
        if (!this.zzakl) {
            this.zzakm = Drawable.resolveOpacity((int)this.zzakh.getOpacity(), (int)this.zzaki.getOpacity());
            this.zzakl = true;
        }
        return this.zzakm;
    }

    @TargetApi(value=11)
    public void invalidateDrawable(Drawable drawable2) {
        if (zzne.zzsd() && (drawable2 = this.getCallback()) != null) {
            drawable2.invalidateDrawable((Drawable)this);
        }
    }

    public Drawable mutate() {
        if (!this.zzakf && super.mutate() == this) {
            if (!this.canConstantState()) {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
            this.zzakh.mutate();
            this.zzaki.mutate();
            this.zzakf = true;
        }
        return this;
    }

    protected void onBoundsChange(Rect rect) {
        this.zzakh.setBounds(rect);
        this.zzaki.setBounds(rect);
    }

    @TargetApi(value=11)
    public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l2) {
        if (zzne.zzsd() && (drawable2 = this.getCallback()) != null) {
            drawable2.scheduleDrawable((Drawable)this, runnable, l2);
        }
    }

    public void setAlpha(int n2) {
        if (this.zzake == this.zzakc) {
            this.zzake = n2;
        }
        this.zzakc = n2;
        this.invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.zzakh.setColorFilter(colorFilter);
        this.zzaki.setColorFilter(colorFilter);
    }

    public void startTransition(int n2) {
        this.mFrom = 0;
        this.zzakb = this.zzakc;
        this.zzake = 0;
        this.zzakd = n2;
        this.zzaka = 1;
        this.invalidateSelf();
    }

    @TargetApi(value=11)
    public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
        if (zzne.zzsd() && (drawable2 = this.getCallback()) != null) {
            drawable2.unscheduleDrawable((Drawable)this, runnable);
        }
    }

    public Drawable zzqn() {
        return this.zzaki;
    }

    private static final class com.google.android.gms.internal.zzma$zza
    extends Drawable {
        private static final com.google.android.gms.internal.zzma$zza zzakn = new com.google.android.gms.internal.zzma$zza();
        private static final zza zzako = new zza();

        private com.google.android.gms.internal.zzma$zza() {
        }

        public void draw(Canvas canvas) {
        }

        public Drawable.ConstantState getConstantState() {
            return zzako;
        }

        public int getOpacity() {
            return -2;
        }

        public void setAlpha(int n2) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }

        private static final class zza
        extends Drawable.ConstantState {
            private zza() {
            }

            public int getChangingConfigurations() {
                return 0;
            }

            public Drawable newDrawable() {
                return zzakn;
            }
        }
    }

    static final class zzb
    extends Drawable.ConstantState {
        int zzakp;
        int zzakq;

        zzb(zzb zzb2) {
            if (zzb2 != null) {
                this.zzakp = zzb2.zzakp;
                this.zzakq = zzb2.zzakq;
            }
        }

        public int getChangingConfigurations() {
            return this.zzakp;
        }

        public Drawable newDrawable() {
            return new zzma(this);
        }
    }
}

