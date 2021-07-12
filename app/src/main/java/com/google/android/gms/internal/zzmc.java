/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Path
 *  android.net.Uri
 *  android.widget.ImageView
 */
package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.Path;
import android.net.Uri;
import android.widget.ImageView;

public final class zzmc
extends ImageView {
    private Uri zzakr;
    private int zzaks;
    private int zzakt;
    private zza zzaku;
    private int zzakv;
    private float zzakw;

    protected void onDraw(Canvas canvas) {
        if (this.zzaku != null) {
            canvas.clipPath(this.zzaku.zzl(this.getWidth(), this.getHeight()));
        }
        super.onDraw(canvas);
        if (this.zzakt != 0) {
            canvas.drawColor(this.zzakt);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n2, int n3) {
        super.onMeasure(n2, n3);
        switch (this.zzakv) {
            default: {
                return;
            }
            case 1: {
                n2 = this.getMeasuredHeight();
                n3 = (int)((float)n2 * this.zzakw);
                break;
            }
            case 2: {
                n3 = this.getMeasuredWidth();
                n2 = (int)((float)n3 / this.zzakw);
            }
        }
        this.setMeasuredDimension(n3, n2);
    }

    public void zzbO(int n2) {
        this.zzaks = n2;
    }

    public void zzm(Uri uri) {
        this.zzakr = uri;
    }

    public int zzqp() {
        return this.zzaks;
    }

    public static interface zza {
        public Path zzl(int var1, int var2);
    }
}

