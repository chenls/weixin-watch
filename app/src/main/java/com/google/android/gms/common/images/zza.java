/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.widget.ImageView
 */
package com.google.android.gms.common.images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.internal.zzma;
import com.google.android.gms.internal.zzmb;
import com.google.android.gms.internal.zzmc;
import com.google.android.gms.internal.zzmd;
import java.lang.ref.WeakReference;

public abstract class zza {
    final zza zzajO;
    protected int zzajP = 0;
    protected int zzajQ = 0;
    protected boolean zzajR = false;
    protected ImageManager.OnImageLoadedListener zzajS;
    private boolean zzajT = true;
    private boolean zzajU = false;
    private boolean zzajV = true;
    protected int zzajW;

    public zza(Uri uri, int n2) {
        this.zzajO = new zza(uri);
        this.zzajQ = n2;
    }

    private Drawable zza(Context context, zzmd zzmd2, int n2) {
        Resources resources = context.getResources();
        if (this.zzajW > 0) {
            zzmd.zza zza2 = new zzmd.zza(n2, this.zzajW);
            Drawable drawable2 = (Drawable)zzmd2.get(zza2);
            context = drawable2;
            if (drawable2 == null) {
                drawable2 = resources.getDrawable(n2);
                context = drawable2;
                if ((this.zzajW & 1) != 0) {
                    context = this.zza(resources, drawable2);
                }
                zzmd2.put(zza2, context);
            }
            return context;
        }
        return resources.getDrawable(n2);
    }

    protected Drawable zza(Resources resources, Drawable drawable2) {
        return zzmb.zza(resources, drawable2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected zzma zza(Drawable drawable2, Drawable drawable3) {
        Drawable drawable4;
        if (drawable2 != null) {
            drawable4 = drawable2;
            if (drawable2 instanceof zzma) {
                drawable4 = ((zzma)drawable2).zzqn();
            }
            return new zzma(drawable4, drawable3);
        }
        drawable4 = null;
        return new zzma(drawable4, drawable3);
    }

    void zza(Context context, Bitmap bitmap, boolean bl2) {
        com.google.android.gms.common.internal.zzb.zzv(bitmap);
        Bitmap bitmap2 = bitmap;
        if ((this.zzajW & 1) != 0) {
            bitmap2 = zzmb.zzb(bitmap);
        }
        context = new BitmapDrawable(context.getResources(), bitmap2);
        if (this.zzajS != null) {
            this.zzajS.onImageLoaded(this.zzajO.uri, (Drawable)context, true);
        }
        this.zza((Drawable)context, bl2, false, true);
    }

    void zza(Context context, zzmd zzmd2) {
        if (this.zzajV) {
            Drawable drawable2 = null;
            if (this.zzajP != 0) {
                drawable2 = this.zza(context, zzmd2, this.zzajP);
            }
            this.zza(drawable2, false, true, false);
        }
    }

    void zza(Context context, zzmd zzmd2, boolean bl2) {
        Drawable drawable2 = null;
        if (this.zzajQ != 0) {
            drawable2 = this.zza(context, zzmd2, this.zzajQ);
        }
        if (this.zzajS != null) {
            this.zzajS.onImageLoaded(this.zzajO.uri, drawable2, false);
        }
        this.zza(drawable2, bl2, false, false);
    }

    protected abstract void zza(Drawable var1, boolean var2, boolean var3, boolean var4);

    protected boolean zzb(boolean bl2, boolean bl3) {
        return this.zzajT && !bl3 && (!bl2 || this.zzajU);
    }

    public void zzbM(int n2) {
        this.zzajQ = n2;
    }

    static final class zza {
        public final Uri uri;

        public zza(Uri uri) {
            this.uri = uri;
        }

        public boolean equals(Object object) {
            if (!(object instanceof zza)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            return zzw.equal(((zza)object).uri, this.uri);
        }

        public int hashCode() {
            return zzw.hashCode(this.uri);
        }
    }

    public static final class zzb
    extends zza {
        private WeakReference<ImageView> zzajX;

        public zzb(ImageView imageView, int n2) {
            super(null, n2);
            com.google.android.gms.common.internal.zzb.zzv(imageView);
            this.zzajX = new WeakReference<ImageView>(imageView);
        }

        public zzb(ImageView imageView, Uri uri) {
            super(uri, 0);
            com.google.android.gms.common.internal.zzb.zzv(imageView);
            this.zzajX = new WeakReference<ImageView>(imageView);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void zza(ImageView object, Drawable object2, boolean bl2, boolean bl3, boolean bl4) {
            Object object3;
            block8: {
                block7: {
                    int n2;
                    block6: {
                        n2 = !bl3 && !bl4 ? 1 : 0;
                        if (n2 == 0 || !(object instanceof zzmc)) break block6;
                        int n3 = ((zzmc)((Object)object)).zzqp();
                        if (this.zzajQ != 0 && n3 == this.zzajQ) break block7;
                    }
                    bl2 = this.zzb(bl2, bl3);
                    if (this.zzajR && object2 != null) {
                        object2 = object2.getConstantState().newDrawable();
                    }
                    object3 = object2;
                    if (bl2) {
                        object3 = this.zza(object.getDrawable(), (Drawable)object2);
                    }
                    object.setImageDrawable((Drawable)object3);
                    if (object instanceof zzmc) {
                        object2 = (zzmc)((Object)object);
                        object = bl4 ? this.zzajO.uri : null;
                        ((zzmc)((Object)object2)).zzm((Uri)object);
                        n2 = n2 != 0 ? this.zzajQ : 0;
                        ((zzmc)((Object)object2)).zzbO(n2);
                    }
                    if (bl2) break block8;
                }
                return;
            }
            ((zzma)((Object)object3)).startTransition(250);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            if (!(object instanceof zzb)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            zzb zzb2 = (zzb)object;
            object = (ImageView)this.zzajX.get();
            zzb2 = (ImageView)zzb2.zzajX.get();
            if (zzb2 == null) return false;
            if (object == null) return false;
            if (!zzw.equal(zzb2, object)) return false;
            return true;
        }

        public int hashCode() {
            return 0;
        }

        @Override
        protected void zza(Drawable drawable2, boolean bl2, boolean bl3, boolean bl4) {
            ImageView imageView = (ImageView)this.zzajX.get();
            if (imageView != null) {
                this.zza(imageView, drawable2, bl2, bl3, bl4);
            }
        }
    }

    public static final class zzc
    extends zza {
        private WeakReference<ImageManager.OnImageLoadedListener> zzajY;

        public zzc(ImageManager.OnImageLoadedListener onImageLoadedListener, Uri uri) {
            super(uri, 0);
            com.google.android.gms.common.internal.zzb.zzv(onImageLoadedListener);
            this.zzajY = new WeakReference<ImageManager.OnImageLoadedListener>(onImageLoadedListener);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            if (!(object instanceof zzc)) {
                return false;
            }
            if (this == object) {
                return true;
            }
            object = (zzc)object;
            ImageManager.OnImageLoadedListener onImageLoadedListener = (ImageManager.OnImageLoadedListener)this.zzajY.get();
            ImageManager.OnImageLoadedListener onImageLoadedListener2 = (ImageManager.OnImageLoadedListener)((zzc)object).zzajY.get();
            if (onImageLoadedListener2 == null) return false;
            if (onImageLoadedListener == null) return false;
            if (!zzw.equal(onImageLoadedListener2, onImageLoadedListener)) return false;
            if (!zzw.equal(((zzc)object).zzajO, this.zzajO)) return false;
            return true;
        }

        public int hashCode() {
            return zzw.hashCode(this.zzajO);
        }

        @Override
        protected void zza(Drawable drawable2, boolean bl2, boolean bl3, boolean bl4) {
            ImageManager.OnImageLoadedListener onImageLoadedListener;
            if (!bl3 && (onImageLoadedListener = (ImageManager.OnImageLoadedListener)this.zzajY.get()) != null) {
                onImageLoadedListener.onImageLoaded(this.zzajO.uri, drawable2, bl4);
            }
        }
    }
}

