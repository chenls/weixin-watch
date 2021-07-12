/*
 * Decompiled with CFR 0.151.
 */
package com.mobvoi.android.speech.synthesizer;

import com.mobvoi.android.speech.synthesizer.ErrorCode;
import com.mobvoi.android.speech.synthesizer.SpeechSynthesizerCallbackInterface;
import mobvoiapi.ag;

public class DefaultSpeechSynthesizerCallback
implements SpeechSynthesizerCallbackInterface {
    @Override
    public void onCompletion() {
        ag.b("SynthesizerCallback", "Done playing synthesized data");
    }

    @Override
    public void onError(ErrorCode errorCode, String string2) {
        ag.d("SynthesizerCallback", "ErrorCode: " + (Object)((Object)errorCode) + " Error Message: " + string2);
    }

    @Override
    public void onStart() {
        ag.b("SynthesizerCallback", "Start play synthesized data");
    }
}

