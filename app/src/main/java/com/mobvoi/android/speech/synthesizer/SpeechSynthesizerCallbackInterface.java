/*
 * Decompiled with CFR 0.151.
 */
package com.mobvoi.android.speech.synthesizer;

import com.mobvoi.android.speech.synthesizer.ErrorCode;

public interface SpeechSynthesizerCallbackInterface {
    public void onCompletion();

    public void onError(ErrorCode var1, String var2);

    public void onStart();
}

