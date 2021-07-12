/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ClipData
 *  android.content.ClipDescription
 *  android.content.Intent
 *  android.os.Bundle
 */
package android.support.v4.app;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInputCompatBase;

class RemoteInputCompatJellybean {
    public static final String EXTRA_RESULTS_DATA = "android.remoteinput.resultsData";
    private static final String KEY_ALLOW_FREE_FORM_INPUT = "allowFreeFormInput";
    private static final String KEY_CHOICES = "choices";
    private static final String KEY_EXTRAS = "extras";
    private static final String KEY_LABEL = "label";
    private static final String KEY_RESULT_KEY = "resultKey";
    public static final String RESULTS_CLIP_LABEL = "android.remoteinput.results";

    RemoteInputCompatJellybean() {
    }

    static void addResultsToIntent(RemoteInputCompatBase.RemoteInput[] intent, Intent intent2, Bundle bundle) {
        Bundle bundle2 = new Bundle();
        for (RemoteInputCompatBase.RemoteInput remoteInput : intent) {
            Object object = bundle.get(remoteInput.getResultKey());
            if (!(object instanceof CharSequence)) continue;
            bundle2.putCharSequence(remoteInput.getResultKey(), (CharSequence)object);
        }
        intent = new Intent();
        intent.putExtra(EXTRA_RESULTS_DATA, bundle2);
        intent2.setClipData(ClipData.newIntent((CharSequence)RESULTS_CLIP_LABEL, (Intent)intent));
    }

    static RemoteInputCompatBase.RemoteInput fromBundle(Bundle bundle, RemoteInputCompatBase.RemoteInput.Factory factory) {
        return factory.build(bundle.getString(KEY_RESULT_KEY), bundle.getCharSequence(KEY_LABEL), bundle.getCharSequenceArray(KEY_CHOICES), bundle.getBoolean(KEY_ALLOW_FREE_FORM_INPUT), bundle.getBundle(KEY_EXTRAS));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static RemoteInputCompatBase.RemoteInput[] fromBundleArray(Bundle[] bundleArray, RemoteInputCompatBase.RemoteInput.Factory factory) {
        if (bundleArray == null) {
            return null;
        }
        RemoteInputCompatBase.RemoteInput[] remoteInputArray = factory.newArray(bundleArray.length);
        int n2 = 0;
        while (true) {
            RemoteInputCompatBase.RemoteInput[] remoteInputArray2 = remoteInputArray;
            if (n2 >= bundleArray.length) return remoteInputArray2;
            remoteInputArray[n2] = RemoteInputCompatJellybean.fromBundle(bundleArray[n2], factory);
            ++n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    static Bundle getResultsFromIntent(Intent intent) {
        ClipDescription clipDescription;
        if ((intent = intent.getClipData()) == null || !(clipDescription = intent.getDescription()).hasMimeType("text/vnd.android.intent") || !clipDescription.getLabel().equals(RESULTS_CLIP_LABEL)) {
            return null;
        }
        return (Bundle)intent.getItemAt(0).getIntent().getExtras().getParcelable(EXTRA_RESULTS_DATA);
    }

    static Bundle toBundle(RemoteInputCompatBase.RemoteInput remoteInput) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RESULT_KEY, remoteInput.getResultKey());
        bundle.putCharSequence(KEY_LABEL, remoteInput.getLabel());
        bundle.putCharSequenceArray(KEY_CHOICES, remoteInput.getChoices());
        bundle.putBoolean(KEY_ALLOW_FREE_FORM_INPUT, remoteInput.getAllowFreeFormInput());
        bundle.putBundle(KEY_EXTRAS, remoteInput.getExtras());
        return bundle;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static Bundle[] toBundleArray(RemoteInputCompatBase.RemoteInput[] remoteInputArray) {
        if (remoteInputArray == null) {
            return null;
        }
        Bundle[] bundleArray = new Bundle[remoteInputArray.length];
        int n2 = 0;
        while (true) {
            Bundle[] bundleArray2 = bundleArray;
            if (n2 >= remoteInputArray.length) return bundleArray2;
            bundleArray[n2] = RemoteInputCompatJellybean.toBundle(remoteInputArray[n2]);
            ++n2;
        }
    }
}

