/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.media.MediaRecorder
 *  android.os.Environment
 */
package com.riyuxihe.weixinqingliao;

import android.media.MediaRecorder;
import android.os.Environment;
import java.io.IOException;

public class SoundMeter {
    private static final double EMA_FILTER = 0.6;
    private double mEMA = 0.0;
    private MediaRecorder mRecorder = null;

    public double getAmplitude() {
        if (this.mRecorder != null) {
            return (double)this.mRecorder.getMaxAmplitude() / 2700.0;
        }
        return 0.0;
    }

    public double getAmplitudeEMA() {
        this.mEMA = 0.6 * this.getAmplitude() + 0.4 * this.mEMA;
        return this.mEMA;
    }

    public void pause() {
        if (this.mRecorder != null) {
            this.mRecorder.stop();
        }
    }

    public void start() {
        if (this.mRecorder != null) {
            this.mRecorder.start();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void start(String string2) {
        if (!Environment.getExternalStorageState().equals("mounted") || this.mRecorder != null) {
            return;
        }
        this.mRecorder = new MediaRecorder();
        this.mRecorder.setAudioSource(1);
        this.mRecorder.setOutputFormat(3);
        this.mRecorder.setAudioEncoder(1);
        this.mRecorder.setOutputFile(Environment.getExternalStorageDirectory() + "/weixinQingliao/" + string2);
        try {
            this.mRecorder.prepare();
            this.mRecorder.start();
            this.mEMA = 0.0;
            return;
        }
        catch (IllegalStateException illegalStateException) {
            System.out.print(illegalStateException.getMessage());
            return;
        }
        catch (IOException iOException) {
            System.out.print(iOException.getMessage());
            return;
        }
    }

    public void stop() {
        if (this.mRecorder != null) {
            this.mRecorder.stop();
            this.mRecorder.release();
            this.mRecorder = null;
        }
    }
}

