/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.ColorMatrix
 *  android.graphics.ColorMatrixColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.RectF
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.CancellationSignal
 *  android.os.CancellationSignal$OnCancelListener
 *  android.os.ParcelFileDescriptor
 *  android.print.PageRange
 *  android.print.PrintAttributes
 *  android.print.PrintAttributes$Builder
 *  android.print.PrintAttributes$Margins
 *  android.print.PrintAttributes$MediaSize
 *  android.print.PrintDocumentAdapter
 *  android.print.PrintDocumentAdapter$LayoutResultCallback
 *  android.print.PrintDocumentAdapter$WriteResultCallback
 *  android.print.PrintDocumentInfo
 *  android.print.PrintDocumentInfo$Builder
 *  android.print.PrintManager
 *  android.print.pdf.PrintedPdfDocument
 *  android.util.Log
 */
package android.support.v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class PrintHelperKitkat {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    private static final String LOG_TAG = "PrintHelperKitkat";
    private static final int MAX_PRINT_SIZE = 3500;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    int mColorMode = 2;
    final Context mContext;
    BitmapFactory.Options mDecodeOptions = null;
    protected boolean mIsMinMarginsHandlingCorrect = true;
    private final Object mLock = new Object();
    int mOrientation;
    protected boolean mPrintActivityRespectsOrientation = true;
    int mScaleMode = 2;

    PrintHelperKitkat(Context context) {
        this.mContext = context;
    }

    static /* synthetic */ Bitmap access$100(PrintHelperKitkat printHelperKitkat, Bitmap bitmap, int n2) {
        return printHelperKitkat.convertBitmapForColorMode(bitmap, n2);
    }

    static /* synthetic */ Matrix access$200(PrintHelperKitkat printHelperKitkat, int n2, int n3, RectF rectF, int n4) {
        return printHelperKitkat.getMatrix(n2, n3, rectF, n4);
    }

    private Bitmap convertBitmapForColorMode(Bitmap bitmap, int n2) {
        if (n2 != 1) {
            return bitmap;
        }
        Bitmap bitmap2 = Bitmap.createBitmap((int)bitmap.getWidth(), (int)bitmap.getHeight(), (Bitmap.Config)Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        paint.setColorFilter((ColorFilter)new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        canvas.setBitmap(null);
        return bitmap2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Matrix getMatrix(int n2, int n3, RectF rectF, int n4) {
        Matrix matrix = new Matrix();
        float f2 = rectF.width() / (float)n2;
        f2 = n4 == 2 ? Math.max(f2, rectF.height() / (float)n3) : Math.min(f2, rectF.height() / (float)n3);
        matrix.postScale(f2, f2);
        matrix.postTranslate((rectF.width() - (float)n2 * f2) / 2.0f, (rectF.height() - (float)n3 * f2) / 2.0f);
        return matrix;
    }

    private static boolean isPortrait(Bitmap bitmap) {
        return bitmap.getWidth() <= bitmap.getHeight();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Bitmap loadBitmap(Uri object, BitmapFactory.Options options) throws FileNotFoundException {
        if (object == null) throw new IllegalArgumentException("bad argument to loadBitmap");
        if (this.mContext == null) {
            throw new IllegalArgumentException("bad argument to loadBitmap");
        }
        Object object2 = null;
        try {
            object2 = object = this.mContext.getContentResolver().openInputStream((Uri)object);
            options = BitmapFactory.decodeStream((InputStream)object, null, (BitmapFactory.Options)options);
            if (object == null) return options;
        }
        catch (Throwable throwable) {
            if (object2 == null) throw throwable;
            try {
                ((InputStream)object2).close();
            }
            catch (IOException iOException) {
                Log.w((String)LOG_TAG, (String)"close fail ", (Throwable)iOException);
                throw throwable;
            }
            throw throwable;
        }
        try {
            ((InputStream)object).close();
            return options;
        }
        catch (IOException iOException) {
            Log.w((String)LOG_TAG, (String)"close fail ", (Throwable)iOException);
            return options;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Bitmap loadConstrainedBitmap(Uri object, int n2) throws FileNotFoundException {
        BitmapFactory.Options options;
        if (n2 <= 0) throw new IllegalArgumentException("bad argument to getScaledBitmap");
        if (object == null) throw new IllegalArgumentException("bad argument to getScaledBitmap");
        if (this.mContext == null) {
            throw new IllegalArgumentException("bad argument to getScaledBitmap");
        }
        Object object2 = new BitmapFactory.Options();
        object2.inJustDecodeBounds = true;
        this.loadBitmap((Uri)object, (BitmapFactory.Options)object2);
        int n3 = object2.outWidth;
        int n4 = object2.outHeight;
        if (n3 <= 0) return null;
        if (n4 <= 0) {
            return null;
        }
        int n5 = 1;
        for (int i2 = Math.max(n3, n4); i2 > n2; i2 >>>= 1, n5 <<= 1) {
        }
        if (n5 <= 0) return null;
        if (Math.min(n3, n4) / n5 <= 0) return null;
        object2 = this.mLock;
        synchronized (object2) {
            this.mDecodeOptions = new BitmapFactory.Options();
            this.mDecodeOptions.inMutable = true;
            this.mDecodeOptions.inSampleSize = n5;
            options = this.mDecodeOptions;
        }
        try {
            object2 = this.loadBitmap((Uri)object, options);
            return object2;
        }
        finally {
            object = this.mLock;
            synchronized (object) {
                this.mDecodeOptions = null;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeBitmap(final PrintAttributes printAttributes, final int n2, final Bitmap bitmap, final ParcelFileDescriptor parcelFileDescriptor, final CancellationSignal cancellationSignal, final PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
        final PrintAttributes printAttributes2 = this.mIsMinMarginsHandlingCorrect ? printAttributes : this.copyAttributes(printAttributes).setMinMargins(new PrintAttributes.Margins(0, 0, 0, 0)).build();
        new AsyncTask<Void, Void, Throwable>(){

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            protected Throwable doInBackground(Void ... var1_1) {
                block22: {
                    if (cancellationSignal.isCanceled()) {
                        return null;
                    }
                    var4_6 = new PrintedPdfDocument(PrintHelperKitkat.this.mContext, printAttributes2);
                    var3_8 = PrintHelperKitkat.access$100(PrintHelperKitkat.this, bitmap, printAttributes2.getColorMode());
                    var2_9 = cancellationSignal.isCanceled();
                    if (var2_9) return null;
                    {
                        catch (Throwable var1_2) {
                            return var1_2;
                        }
                    }
                    var5_10 = var4_6.startPage(1);
                    if (PrintHelperKitkat.this.mIsMinMarginsHandlingCorrect) {
                        var1_1 = new RectF(var5_10.getInfo().getContentRect());
                    } else {
                        var6_11 = new PrintedPdfDocument(PrintHelperKitkat.this.mContext, printAttributes);
                        var7_12 = var6_11.startPage(1);
                        var1_1 = new RectF(var7_12.getInfo().getContentRect());
                        var6_11.finishPage(var7_12);
                        var6_11.close();
                    }
                    var6_11 = PrintHelperKitkat.access$200(PrintHelperKitkat.this, var3_8.getWidth(), var3_8.getHeight(), var1_1, n2);
                    if (!PrintHelperKitkat.this.mIsMinMarginsHandlingCorrect) {
                        var6_11.postTranslate(var1_1.left, var1_1.top);
                        var5_10.getCanvas().clipRect(var1_1);
                    }
                    var5_10.getCanvas().drawBitmap(var3_8, (Matrix)var6_11, null);
                    var4_6.finishPage(var5_10);
                    var2_9 = cancellationSignal.isCanceled();
                    if (!var2_9) ** GOTO lbl45
                    var4_6.close();
                    var1_1 = parcelFileDescriptor;
                    if (var1_1 == null) ** GOTO lbl64
                    parcelFileDescriptor.close();
                    catch (Throwable var1_3) {
                        var4_6.close();
                        var4_6 = parcelFileDescriptor;
                        if (var4_6 == null) ** GOTO lbl55
                        parcelFileDescriptor.close();
lbl45:
                        // 1 sources

                        var4_6.writeTo((OutputStream)new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
                        var4_6.close();
                        var1_1 = parcelFileDescriptor;
                        if (var1_1 == null) break block22;
                        ** try [egrp 9[TRYBLOCK] [18 : 355->362)] { 
lbl-1000:
                        // 1 sources

                        {
                            parcelFileDescriptor.close();
                            break block22;
                        }
                        catch (IOException var4_7) {}
lbl55:
                        // 3 sources

                        if (var3_8 == bitmap) throw var1_3;
                        var3_8.recycle();
                        throw var1_3;
                    }
lbl58:
                    // 1 sources

                    catch (IOException var1_4) {}
                }
                if (var3_8 == bitmap) return null;
                var3_8.recycle();
                return null;
                catch (IOException var1_5) {}
lbl64:
                // 3 sources

                if (var3_8 == bitmap) return null;
                var3_8.recycle();
                return null;
            }

            protected void onPostExecute(Throwable throwable) {
                if (cancellationSignal.isCanceled()) {
                    writeResultCallback.onWriteCancelled();
                    return;
                }
                if (throwable == null) {
                    writeResultCallback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                    return;
                }
                Log.e((String)PrintHelperKitkat.LOG_TAG, (String)"Error writing printed content", (Throwable)throwable);
                writeResultCallback.onWriteFailed(null);
            }
        }.execute((Object[])new Void[0]);
    }

    protected PrintAttributes.Builder copyAttributes(PrintAttributes printAttributes) {
        PrintAttributes.Builder builder = new PrintAttributes.Builder().setMediaSize(printAttributes.getMediaSize()).setResolution(printAttributes.getResolution()).setMinMargins(printAttributes.getMinMargins());
        if (printAttributes.getColorMode() != 0) {
            builder.setColorMode(printAttributes.getColorMode());
        }
        return builder;
    }

    public int getColorMode() {
        return this.mColorMode;
    }

    public int getOrientation() {
        if (this.mOrientation == 0) {
            return 1;
        }
        return this.mOrientation;
    }

    public int getScaleMode() {
        return this.mScaleMode;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void printBitmap(final String string2, final Bitmap bitmap, final OnPrintFinishCallback onPrintFinishCallback) {
        if (bitmap == null) {
            return;
        }
        final int n2 = this.mScaleMode;
        PrintManager printManager = (PrintManager)this.mContext.getSystemService("print");
        PrintAttributes.MediaSize mediaSize = PrintHelperKitkat.isPortrait(bitmap) ? PrintAttributes.MediaSize.UNKNOWN_PORTRAIT : PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
        mediaSize = new PrintAttributes.Builder().setMediaSize(mediaSize).setColorMode(this.mColorMode).build();
        printManager.print(string2, new PrintDocumentAdapter(){
            private PrintAttributes mAttributes;

            public void onFinish() {
                if (onPrintFinishCallback != null) {
                    onPrintFinishCallback.onFinish();
                }
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
                boolean bl2 = true;
                this.mAttributes = printAttributes2;
                cancellationSignal = new PrintDocumentInfo.Builder(string2).setContentType(1).setPageCount(1).build();
                if (printAttributes2.equals((Object)printAttributes)) {
                    bl2 = false;
                }
                layoutResultCallback.onLayoutFinished((PrintDocumentInfo)cancellationSignal, bl2);
            }

            public void onWrite(PageRange[] pageRangeArray, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
                PrintHelperKitkat.this.writeBitmap(this.mAttributes, n2, bitmap, parcelFileDescriptor, cancellationSignal, writeResultCallback);
            }
        }, (PrintAttributes)mediaSize);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void printBitmap(final String string2, Uri object, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
        PrintManager printManager;
        PrintDocumentAdapter printDocumentAdapter = new PrintDocumentAdapter((Uri)object, (OnPrintFinishCallback)printManager, this.mScaleMode){
            private PrintAttributes mAttributes;
            Bitmap mBitmap = null;
            AsyncTask<Uri, Boolean, Bitmap> mLoadBitmap;
            final /* synthetic */ OnPrintFinishCallback val$callback;
            final /* synthetic */ int val$fittingMode;
            final /* synthetic */ Uri val$imageFile;
            {
                this.val$imageFile = uri;
                this.val$callback = onPrintFinishCallback;
                this.val$fittingMode = n2;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            private void cancelLoad() {
                Object object = PrintHelperKitkat.this.mLock;
                synchronized (object) {
                    if (PrintHelperKitkat.this.mDecodeOptions != null) {
                        PrintHelperKitkat.this.mDecodeOptions.requestCancelDecode();
                        PrintHelperKitkat.this.mDecodeOptions = null;
                    }
                    return;
                }
            }

            public void onFinish() {
                super.onFinish();
                this.cancelLoad();
                if (this.mLoadBitmap != null) {
                    this.mLoadBitmap.cancel(true);
                }
                if (this.val$callback != null) {
                    this.val$callback.onFinish();
                }
                if (this.mBitmap != null) {
                    this.mBitmap.recycle();
                    this.mBitmap = null;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            public void onLayout(final PrintAttributes printAttributes, final PrintAttributes printAttributes2, final CancellationSignal cancellationSignal, final PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
                boolean bl2 = true;
                // MONITORENTER : this
                this.mAttributes = printAttributes2;
                // MONITOREXIT : this
                if (cancellationSignal.isCanceled()) {
                    layoutResultCallback.onLayoutCancelled();
                    return;
                }
                if (this.mBitmap == null) {
                    this.mLoadBitmap = new AsyncTask<Uri, Boolean, Bitmap>(){

                        protected Bitmap doInBackground(Uri ... bitmap) {
                            try {
                                bitmap = PrintHelperKitkat.this.loadConstrainedBitmap(val$imageFile, 3500);
                                return bitmap;
                            }
                            catch (FileNotFoundException fileNotFoundException) {
                                return null;
                            }
                        }

                        protected void onCancelled(Bitmap bitmap) {
                            layoutResultCallback.onLayoutCancelled();
                            mLoadBitmap = null;
                        }

                        /*
                         * Enabled aggressive block sorting
                         * Enabled unnecessary exception pruning
                         * Enabled aggressive exception aggregation
                         */
                        protected void onPostExecute(Bitmap bitmap) {
                            Bitmap bitmap2;
                            block10: {
                                PrintAttributes.MediaSize mediaSize;
                                block11: {
                                    super.onPostExecute((Object)bitmap);
                                    bitmap2 = bitmap;
                                    if (bitmap == null) break block10;
                                    if (!PrintHelperKitkat.this.mPrintActivityRespectsOrientation) break block11;
                                    bitmap2 = bitmap;
                                    if (PrintHelperKitkat.this.mOrientation != 0) break block10;
                                }
                                synchronized (this) {
                                    mediaSize = mAttributes.getMediaSize();
                                }
                                bitmap2 = bitmap;
                                if (mediaSize != null) {
                                    bitmap2 = bitmap;
                                    if (mediaSize.isPortrait() != PrintHelperKitkat.isPortrait(bitmap)) {
                                        bitmap2 = new Matrix();
                                        bitmap2.postRotate(90.0f);
                                        bitmap2 = Bitmap.createBitmap((Bitmap)bitmap, (int)0, (int)0, (int)bitmap.getWidth(), (int)bitmap.getHeight(), (Matrix)bitmap2, (boolean)true);
                                    }
                                }
                            }
                            mBitmap = bitmap2;
                            if (bitmap2 != null) {
                                bitmap = new PrintDocumentInfo.Builder(string2).setContentType(1).setPageCount(1).build();
                                boolean bl2 = !printAttributes2.equals((Object)printAttributes);
                                layoutResultCallback.onLayoutFinished((PrintDocumentInfo)bitmap, bl2);
                            } else {
                                layoutResultCallback.onLayoutFailed(null);
                            }
                            mLoadBitmap = null;
                        }

                        protected void onPreExecute() {
                            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener(){

                                public void onCancel() {
                                    this.cancelLoad();
                                    this.cancel(false);
                                }
                            });
                        }
                    }.execute((Object[])new Uri[0]);
                    return;
                }
                cancellationSignal = new PrintDocumentInfo.Builder(string2).setContentType(1).setPageCount(1).build();
                if (printAttributes2.equals((Object)printAttributes)) {
                    bl2 = false;
                }
                layoutResultCallback.onLayoutFinished((PrintDocumentInfo)cancellationSignal, bl2);
            }

            public void onWrite(PageRange[] pageRangeArray, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
                PrintHelperKitkat.this.writeBitmap(this.mAttributes, this.val$fittingMode, this.mBitmap, parcelFileDescriptor, cancellationSignal, writeResultCallback);
            }
        };
        printManager = (PrintManager)this.mContext.getSystemService("print");
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setColorMode(this.mColorMode);
        if (this.mOrientation == 1 || this.mOrientation == 0) {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
        } else if (this.mOrientation == 2) {
            builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
        }
        printManager.print(string2, printDocumentAdapter, builder.build());
    }

    public void setColorMode(int n2) {
        this.mColorMode = n2;
    }

    public void setOrientation(int n2) {
        this.mOrientation = n2;
    }

    public void setScaleMode(int n2) {
        this.mScaleMode = n2;
    }

    public static interface OnPrintFinishCallback {
        public void onFinish();
    }
}

