/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.RemoteInput
 *  android.app.RemoteInput$Builder
 *  android.content.Intent
 *  android.os.Bundle
 */
package android.support.v4.app;

import android.app.RemoteInput;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInputCompatBase;

class RemoteInputCompatApi20 {
    RemoteInputCompatApi20() {
    }

    static void addResultsToIntent(RemoteInputCompatBase.RemoteInput[] remoteInputArray, Intent intent, Bundle bundle) {
        RemoteInput.addResultsToIntent((RemoteInput[])RemoteInputCompatApi20.fromCompat(remoteInputArray), (Intent)intent, (Bundle)bundle);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static RemoteInput[] fromCompat(RemoteInputCompatBase.RemoteInput[] remoteInputArray) {
        if (remoteInputArray == null) {
            return null;
        }
        RemoteInput[] remoteInputArray2 = new RemoteInput[remoteInputArray.length];
        int n2 = 0;
        while (true) {
            Object object = remoteInputArray2;
            if (n2 >= remoteInputArray.length) return object;
            object = remoteInputArray[n2];
            remoteInputArray2[n2] = new RemoteInput.Builder(((RemoteInputCompatBase.RemoteInput)object).getResultKey()).setLabel(((RemoteInputCompatBase.RemoteInput)object).getLabel()).setChoices(((RemoteInputCompatBase.RemoteInput)object).getChoices()).setAllowFreeFormInput(((RemoteInputCompatBase.RemoteInput)object).getAllowFreeFormInput()).addExtras(((RemoteInputCompatBase.RemoteInput)object).getExtras()).build();
            ++n2;
        }
    }

    static Bundle getResultsFromIntent(Intent intent) {
        return RemoteInput.getResultsFromIntent((Intent)intent);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static RemoteInputCompatBase.RemoteInput[] toCompat(RemoteInput[] remoteInputArray, RemoteInputCompatBase.RemoteInput.Factory factory) {
        if (remoteInputArray == null) {
            return null;
        }
        RemoteInput remoteInput = factory.newArray(remoteInputArray.length);
        int n2 = 0;
        while (true) {
            RemoteInput remoteInput2 = remoteInput;
            if (n2 >= remoteInputArray.length) return remoteInput2;
            remoteInput2 = remoteInputArray[n2];
            remoteInput[n2] = factory.build(remoteInput2.getResultKey(), remoteInput2.getLabel(), remoteInput2.getChoices(), remoteInput2.getAllowFreeFormInput(), remoteInput2.getExtras());
            ++n2;
        }
    }
}

