package com.riyuxihe.weixinqingliao;

import android.media.MediaRecorder;
import android.os.Environment;
import com.riyuxihe.weixinqingliao.util.Constants;
import java.io.IOException;

public class SoundMeter {
    private static final double EMA_FILTER = 0.6d;
    private double mEMA = 0.0d;
    private MediaRecorder mRecorder = null;

    public void start(String name) {
        if (Environment.getExternalStorageState().equals("mounted") && this.mRecorder == null) {
            this.mRecorder = new MediaRecorder();
            this.mRecorder.setAudioSource(1);
            this.mRecorder.setOutputFormat(3);
            this.mRecorder.setAudioEncoder(1);
            this.mRecorder.setOutputFile(Environment.getExternalStorageDirectory() + Constants.AUDIO_DIRECTORY + name);
            try {
                this.mRecorder.prepare();
                this.mRecorder.start();
                this.mEMA = 0.0d;
            } catch (IllegalStateException e) {
                System.out.print(e.getMessage());
            } catch (IOException e2) {
                System.out.print(e2.getMessage());
            }
        }
    }

    public void stop() {
        if (this.mRecorder != null) {
            this.mRecorder.stop();
            this.mRecorder.release();
            this.mRecorder = null;
        }
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

    public double getAmplitude() {
        if (this.mRecorder != null) {
            return ((double) this.mRecorder.getMaxAmplitude()) / 2700.0d;
        }
        return 0.0d;
    }

    public double getAmplitudeEMA() {
        this.mEMA = (EMA_FILTER * getAmplitude()) + (0.4d * this.mEMA);
        return this.mEMA;
    }
}
