/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package android.support.v4.app;

import android.app.Activity;
import java.io.FileDescriptor;
import java.io.PrintWriter;

class ActivityCompatHoneycomb {
    ActivityCompatHoneycomb() {
    }

    static void dump(Activity activity, String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] stringArray) {
        activity.dump(string2, fileDescriptor, printWriter, stringArray);
    }

    static void invalidateOptionsMenu(Activity activity) {
        activity.invalidateOptionsMenu();
    }
}

