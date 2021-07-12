/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 *  android.util.Log
 */
package com.mobvoi.android.speech;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class SpeechRecognitionApi {
    public static final String START_MODE = "start_mode";
    public static final String START_MODE_WITH_RESULT_CONTACT_ONLY = "start_mode_with_result_contact_only";

    public static void startContactsSearch(Activity activity, int n2) {
        Intent intent = new Intent("com.mobvoi.ticwear.action.SPEECH");
        intent.putExtra(START_MODE, START_MODE_WITH_RESULT_CONTACT_ONLY);
        try {
            activity.startActivityForResult(intent, n2);
            return;
        }
        catch (Exception exception) {
            Log.e((String)"SpeechRecognitionApi", (String)("start speech failed: " + exception.getMessage()));
            return;
        }
    }

    public static void startRecognition(Activity activity, int n2) {
        SpeechRecognitionApi.startRecognition(activity, n2, null);
    }

    public static void startRecognition(Activity activity, int n2, String string2) {
        Intent intent = new Intent("com.mobvoi.ticwear.action.SPEECH");
        if (string2 != null) {
            intent.putExtra("tips", string2);
        }
        intent.putExtra(START_MODE, "start_mode_only_voice_result");
        try {
            activity.startActivityForResult(intent, n2);
            return;
        }
        catch (Exception exception) {
            Log.e((String)"SpeechRecognitionApi", (String)("start speech failed: " + exception.getMessage()));
            return;
        }
    }

    public static void startVoiceInput(Activity activity, int n2) {
        Intent intent = new Intent("com.mobvoi.ticwear.action.SPEECH");
        intent.putExtra(START_MODE, "start_mode_with_voice_input");
        try {
            activity.startActivityForResult(intent, n2);
            return;
        }
        catch (Exception exception) {
            Log.e((String)"SpeechRecognitionApi", (String)("start speech failed: " + exception.getMessage()));
            return;
        }
    }

    public static abstract class SpeechRecogActivity
    extends Activity {
        public static final int DEFAULT_REQUEST_CODE = 57;
        public static final String SPEECH_CONTENT = "speech_content";
        protected int a = 57;

        protected void onActivityResult(int n2, int n3, Intent intent) {
            block3: {
                block2: {
                    if (n2 != this.a) break block2;
                    if (intent == null) break block3;
                    this.onRecognitionSuccess(intent.getExtras().getString(SPEECH_CONTENT));
                }
                return;
            }
            this.onRecognitionFailed();
        }

        public abstract void onRecognitionFailed();

        public abstract void onRecognitionSuccess(String var1);

        public void startContactSearch() {
            SpeechRecognitionApi.startContactsSearch(this, this.a);
        }

        public void startRecognition() {
            this.startRecognition(null);
        }

        public void startRecognition(String string2) {
            SpeechRecognitionApi.startRecognition(this, this.a, string2);
        }

        public void startVoiceInput() {
            SpeechRecognitionApi.startVoiceInput(this, this.a);
        }
    }
}

