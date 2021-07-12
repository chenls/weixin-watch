/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package android.support.v4.graphics;

import android.graphics.Bitmap;

class BitmapCompatJellybeanMR2 {
    BitmapCompatJellybeanMR2() {
    }

    public static boolean hasMipMap(Bitmap bitmap) {
        return bitmap.hasMipMap();
    }

    public static void setHasMipMap(Bitmap bitmap, boolean bl2) {
        bitmap.setHasMipMap(bl2);
    }
}

