/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.mobvoi.android.speech.synthesizer;

import android.content.Context;
import com.mobvoi.android.speech.synthesizer.SpeechSynthesizerCallbackInterface;
import mobvoiapi.az;
import mobvoiapi.bp;

public class SpeechSynthesizerApi {
    private static az a = null;
    private static final Object b = new Object();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int getMilliSecondsToPlay() {
        Object object = b;
        synchronized (object) {
            if (a != null) return a.a();
            bp.b("SpeechSynthesizerApi", "no speech synthesizer is running");
            return -1;
        }
    }

    public static void setVolume(float f2) {
        az.a(f2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void startSynthesizer(Context context, SpeechSynthesizerCallbackInterface speechSynthesizerCallbackInterface, String string2, long l2) {
        Object object = b;
        synchronized (object) {
            if (a != null && !a.b()) {
                a.a(0);
                a = null;
                bp.d("SpeechSynthesizerApi", "Do you forget to stop the previous speech synthesizer?");
                return;
            }
            a = new az(context, speechSynthesizerCallbackInterface);
            a.a(string2, l2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void stopSynthesizer() {
        Object object = b;
        synchronized (object) {
            if (a == null) {
                bp.b("SpeechSynthesizerApi", "speech synthesizer is already stopped.");
            }
            a.a(0);
            a = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void stopSynthesizer(int n2) {
        Object object = b;
        synchronized (object) {
            if (a == null) {
                bp.b("SpeechSynthesizerApi", "speech synthesizer is already stopped.");
                return;
            }
            a.a(n2);
            a = null;
            return;
        }
    }
}

