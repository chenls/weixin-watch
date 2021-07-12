/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.ActivityManager
 *  android.content.ComponentCallbacks
 *  android.content.ComponentCallbacks2
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.ResultReceiver
 *  android.os.SystemClock
 *  android.util.Log
 *  android.widget.ImageView
 */
package com.google.android.gms.common.images;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.images.zza;
import com.google.android.gms.internal.zzmd;
import com.google.android.gms.internal.zzne;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager {
    private static HashSet<Uri> zzajA;
    private static ImageManager zzajB;
    private static ImageManager zzajC;
    private static final Object zzajz;
    private final Context mContext;
    private final Handler mHandler;
    private final ExecutorService zzajD;
    private final zzb zzajE;
    private final zzmd zzajF;
    private final Map<com.google.android.gms.common.images.zza, ImageReceiver> zzajG;
    private final Map<Uri, ImageReceiver> zzajH;
    private final Map<Uri, Long> zzajI;

    static {
        zzajz = new Object();
        zzajA = new HashSet();
    }

    /*
     * Enabled aggressive block sorting
     */
    private ImageManager(Context context, boolean bl2) {
        this.mContext = context.getApplicationContext();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.zzajD = Executors.newFixedThreadPool(4);
        if (bl2) {
            this.zzajE = new zzb(this.mContext);
            if (zzne.zzsg()) {
                this.zzqj();
            }
        } else {
            this.zzajE = null;
        }
        this.zzajF = new zzmd();
        this.zzajG = new HashMap<com.google.android.gms.common.images.zza, ImageReceiver>();
        this.zzajH = new HashMap<Uri, ImageReceiver>();
        this.zzajI = new HashMap<Uri, Long>();
    }

    public static ImageManager create(Context context) {
        return ImageManager.zzc(context, false);
    }

    private Bitmap zza(zza.zza zza2) {
        if (this.zzajE == null) {
            return null;
        }
        return (Bitmap)this.zzajE.get(zza2);
    }

    public static ImageManager zzc(Context context, boolean bl2) {
        if (bl2) {
            if (zzajC == null) {
                zzajC = new ImageManager(context, true);
            }
            return zzajC;
        }
        if (zzajB == null) {
            zzajB = new ImageManager(context, false);
        }
        return zzajB;
    }

    @TargetApi(value=14)
    private void zzqj() {
        this.mContext.registerComponentCallbacks((ComponentCallbacks)new zze(this.zzajE));
    }

    public void loadImage(ImageView imageView, int n2) {
        this.zza(new zza.zzb(imageView, n2));
    }

    public void loadImage(ImageView imageView, Uri uri) {
        this.zza(new zza.zzb(imageView, uri));
    }

    public void loadImage(ImageView object, Uri uri, int n2) {
        object = new zza.zzb((ImageView)object, uri);
        ((com.google.android.gms.common.images.zza)object).zzbM(n2);
        this.zza((com.google.android.gms.common.images.zza)object);
    }

    public void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        this.zza(new zza.zzc(onImageLoadedListener, uri));
    }

    public void loadImage(OnImageLoadedListener object, Uri uri, int n2) {
        object = new zza.zzc((OnImageLoadedListener)object, uri);
        ((com.google.android.gms.common.images.zza)object).zzbM(n2);
        this.zza((com.google.android.gms.common.images.zza)object);
    }

    public void zza(com.google.android.gms.common.images.zza zza2) {
        com.google.android.gms.common.internal.zzb.zzcD("ImageManager.loadImage() must be called in the main thread");
        new zzd(zza2).run();
    }

    @KeepName
    private final class ImageReceiver
    extends ResultReceiver {
        private final Uri mUri;
        private final ArrayList<com.google.android.gms.common.images.zza> zzajJ;

        ImageReceiver(Uri uri) {
            super(new Handler(Looper.getMainLooper()));
            this.mUri = uri;
            this.zzajJ = new ArrayList();
        }

        public void onReceiveResult(int n2, Bundle bundle) {
            bundle = (ParcelFileDescriptor)bundle.getParcelable("com.google.android.gms.extra.fileDescriptor");
            ImageManager.this.zzajD.execute(new zzc(this.mUri, (ParcelFileDescriptor)bundle));
        }

        public void zzb(com.google.android.gms.common.images.zza zza2) {
            com.google.android.gms.common.internal.zzb.zzcD("ImageReceiver.addImageRequest() must be called in the main thread");
            this.zzajJ.add(zza2);
        }

        public void zzc(com.google.android.gms.common.images.zza zza2) {
            com.google.android.gms.common.internal.zzb.zzcD("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.zzajJ.remove(zza2);
        }

        public void zzqm() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", (Parcelable)this.mUri);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", (Parcelable)this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            ImageManager.this.mContext.sendBroadcast(intent);
        }
    }

    public static interface OnImageLoadedListener {
        public void onImageLoaded(Uri var1, Drawable var2, boolean var3);
    }

    @TargetApi(value=11)
    private static final class zza {
        static int zza(ActivityManager activityManager) {
            return activityManager.getLargeMemoryClass();
        }
    }

    private static final class zzb
    extends LruCache<zza.zza, Bitmap> {
        public zzb(Context context) {
            super(zzb.zzas(context));
        }

        /*
         * Enabled aggressive block sorting
         */
        @TargetApi(value=11)
        private static int zzas(Context context) {
            ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
            int n2 = (context.getApplicationInfo().flags & 0x100000) != 0 ? 1 : 0;
            if (n2 != 0 && zzne.zzsd()) {
                n2 = zza.zza(activityManager);
                return (int)((float)(n2 * 0x100000) * 0.33f);
            }
            n2 = activityManager.getMemoryClass();
            return (int)((float)(n2 * 0x100000) * 0.33f);
        }

        @Override
        protected /* synthetic */ void entryRemoved(boolean bl2, Object object, Object object2, Object object3) {
            this.zza(bl2, (zza.zza)object, (Bitmap)object2, (Bitmap)object3);
        }

        @Override
        protected /* synthetic */ int sizeOf(Object object, Object object2) {
            return this.zza((zza.zza)object, (Bitmap)object2);
        }

        protected int zza(zza.zza zza2, Bitmap bitmap) {
            return bitmap.getHeight() * bitmap.getRowBytes();
        }

        protected void zza(boolean bl2, zza.zza zza2, Bitmap bitmap, Bitmap bitmap2) {
            super.entryRemoved(bl2, zza2, bitmap, bitmap2);
        }
    }

    private final class zzc
    implements Runnable {
        private final Uri mUri;
        private final ParcelFileDescriptor zzajL;

        public zzc(Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.mUri = uri;
            this.zzajL = parcelFileDescriptor;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            void var3_6;
            com.google.android.gms.common.internal.zzb.zzcE("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            boolean bl2 = false;
            boolean bl3 = false;
            Object var3_3 = null;
            CountDownLatch countDownLatch = null;
            if (this.zzajL != null) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeFileDescriptor((FileDescriptor)this.zzajL.getFileDescriptor());
                    bl2 = bl3;
                }
                catch (OutOfMemoryError outOfMemoryError) {
                    Log.e((String)"ImageManager", (String)("OOM while loading bitmap for uri: " + this.mUri), (Throwable)outOfMemoryError);
                    bl2 = true;
                    CountDownLatch countDownLatch2 = countDownLatch;
                }
                try {
                    this.zzajL.close();
                }
                catch (IOException iOException) {
                    Log.e((String)"ImageManager", (String)"closed failed", (Throwable)iOException);
                }
            }
            countDownLatch = new CountDownLatch(1);
            ImageManager.this.mHandler.post((Runnable)new zzf(this.mUri, (Bitmap)var3_6, bl2, countDownLatch));
            try {
                countDownLatch.await();
                return;
            }
            catch (InterruptedException interruptedException) {
                Log.w((String)"ImageManager", (String)("Latch interrupted while posting " + this.mUri));
                return;
            }
        }
    }

    private final class zzd
    implements Runnable {
        private final com.google.android.gms.common.images.zza zzajM;

        public zzd(com.google.android.gms.common.images.zza zza2) {
            this.zzajM = zza2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            com.google.android.gms.common.internal.zzb.zzcD("LoadImageRunnable must be executed on the main thread");
            Object object = (ImageReceiver)((Object)ImageManager.this.zzajG.get(this.zzajM));
            if (object != null) {
                ImageManager.this.zzajG.remove(this.zzajM);
                ((ImageReceiver)((Object)object)).zzc(this.zzajM);
            }
            zza.zza zza2 = this.zzajM.zzajO;
            if (zza2.uri == null) {
                this.zzajM.zza(ImageManager.this.mContext, ImageManager.this.zzajF, true);
                return;
            }
            object = ImageManager.this.zza(zza2);
            if (object != null) {
                this.zzajM.zza(ImageManager.this.mContext, (Bitmap)object, true);
                return;
            }
            object = (Long)ImageManager.this.zzajI.get(zza2.uri);
            if (object != null) {
                if (SystemClock.elapsedRealtime() - (Long)object < 3600000L) {
                    this.zzajM.zza(ImageManager.this.mContext, ImageManager.this.zzajF, true);
                    return;
                }
                ImageManager.this.zzajI.remove(zza2.uri);
            }
            this.zzajM.zza(ImageManager.this.mContext, ImageManager.this.zzajF);
            Object object2 = (ImageReceiver)((Object)ImageManager.this.zzajH.get(zza2.uri));
            object = object2;
            if (object2 == null) {
                object = new ImageReceiver(zza2.uri);
                ImageManager.this.zzajH.put(zza2.uri, object);
            }
            ((ImageReceiver)((Object)object)).zzb(this.zzajM);
            if (!(this.zzajM instanceof zza.zzc)) {
                ImageManager.this.zzajG.put(this.zzajM, object);
            }
            object2 = zzajz;
            synchronized (object2) {
                if (!zzajA.contains(zza2.uri)) {
                    zzajA.add(zza2.uri);
                    ((ImageReceiver)((Object)object)).zzqm();
                }
                return;
            }
        }
    }

    @TargetApi(value=14)
    private static final class zze
    implements ComponentCallbacks2 {
        private final zzb zzajE;

        public zze(zzb zzb2) {
            this.zzajE = zzb2;
        }

        public void onConfigurationChanged(Configuration configuration) {
        }

        public void onLowMemory() {
            this.zzajE.evictAll();
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onTrimMemory(int n2) {
            if (n2 >= 60) {
                this.zzajE.evictAll();
                return;
            } else {
                if (n2 < 20) return;
                this.zzajE.trimToSize(this.zzajE.size() / 2);
                return;
            }
        }
    }

    private final class zzf
    implements Runnable {
        private final Bitmap mBitmap;
        private final Uri mUri;
        private boolean zzajN;
        private final CountDownLatch zzpJ;

        public zzf(Uri uri, Bitmap bitmap, boolean bl2, CountDownLatch countDownLatch) {
            this.mUri = uri;
            this.mBitmap = bitmap;
            this.zzajN = bl2;
            this.zzpJ = countDownLatch;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void zza(ImageReceiver object, boolean bl2) {
            object = ((ImageReceiver)((Object)object)).zzajJ;
            int n2 = ((ArrayList)object).size();
            int n3 = 0;
            while (n3 < n2) {
                com.google.android.gms.common.images.zza zza2 = (com.google.android.gms.common.images.zza)((ArrayList)object).get(n3);
                if (bl2) {
                    zza2.zza(ImageManager.this.mContext, this.mBitmap, false);
                } else {
                    ImageManager.this.zzajI.put(this.mUri, SystemClock.elapsedRealtime());
                    zza2.zza(ImageManager.this.mContext, ImageManager.this.zzajF, false);
                }
                if (!(zza2 instanceof zza.zzc)) {
                    ImageManager.this.zzajG.remove(zza2);
                }
                ++n3;
            }
            return;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            Object object;
            com.google.android.gms.common.internal.zzb.zzcD("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean bl2 = this.mBitmap != null;
            if (ImageManager.this.zzajE != null) {
                if (this.zzajN) {
                    ImageManager.this.zzajE.evictAll();
                    System.gc();
                    this.zzajN = false;
                    ImageManager.this.mHandler.post((Runnable)this);
                    return;
                }
                if (bl2) {
                    ImageManager.this.zzajE.put(new zza.zza(this.mUri), this.mBitmap);
                }
            }
            if ((object = (ImageReceiver)((Object)ImageManager.this.zzajH.remove(this.mUri))) != null) {
                this.zza((ImageReceiver)((Object)object), bl2);
            }
            this.zzpJ.countDown();
            object = zzajz;
            synchronized (object) {
                zzajA.remove(this.mUri);
                return;
            }
        }
    }
}

