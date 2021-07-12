/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Build$VERSION
 */
package android.support.v4.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContentResolverCompatJellybean;
import android.support.v4.os.CancellationSignal;
import android.support.v4.os.OperationCanceledException;

public final class ContentResolverCompat {
    private static final ContentResolverCompatImpl IMPL = Build.VERSION.SDK_INT >= 16 ? new ContentResolverCompatImplJB() : new ContentResolverCompatImplBase();

    private ContentResolverCompat() {
    }

    public static Cursor query(ContentResolver contentResolver, Uri uri, String[] stringArray, String string2, String[] stringArray2, String string3, CancellationSignal cancellationSignal) {
        return IMPL.query(contentResolver, uri, stringArray, string2, stringArray2, string3, cancellationSignal);
    }

    static interface ContentResolverCompatImpl {
        public Cursor query(ContentResolver var1, Uri var2, String[] var3, String var4, String[] var5, String var6, CancellationSignal var7);
    }

    static class ContentResolverCompatImplBase
    implements ContentResolverCompatImpl {
        ContentResolverCompatImplBase() {
        }

        @Override
        public Cursor query(ContentResolver contentResolver, Uri uri, String[] stringArray, String string2, String[] stringArray2, String string3, CancellationSignal cancellationSignal) {
            if (cancellationSignal != null) {
                cancellationSignal.throwIfCanceled();
            }
            return contentResolver.query(uri, stringArray, string2, stringArray2, string3);
        }
    }

    static class ContentResolverCompatImplJB
    extends ContentResolverCompatImplBase {
        ContentResolverCompatImplJB() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Cursor query(ContentResolver contentResolver, Uri uri, String[] stringArray, String string2, String[] stringArray2, String string3, CancellationSignal object) {
            if (object != null) {
                try {
                    object = ((CancellationSignal)object).getCancellationSignalObject();
                    return ContentResolverCompatJellybean.query(contentResolver, uri, stringArray, string2, stringArray2, string3, object);
                }
                catch (Exception exception) {
                    if (!ContentResolverCompatJellybean.isFrameworkOperationCanceledException(exception)) throw exception;
                    throw new OperationCanceledException();
                }
            }
            object = null;
            return ContentResolverCompatJellybean.query(contentResolver, uri, stringArray, string2, stringArray2, string3, object);
        }
    }
}

