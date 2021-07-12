/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.os.Handler
 *  android.os.Message
 *  android.util.Log
 *  android.view.Choreographer
 *  android.view.Choreographer$FrameCallback
 *  android.view.SurfaceHolder
 */
package android.support.wearable.watchface;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.watchface.WatchFaceService;
import android.util.Log;
import android.view.Choreographer;
import android.view.SurfaceHolder;

@TargetApi(value=21)
public abstract class CanvasWatchFaceService
extends WatchFaceService {
    private static final boolean LOG_VERBOSE = false;
    private static final String TAG = "CanvasWatchFaceService";
    private static final boolean TRACE_DRAW = false;

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    public class Engine
    extends WatchFaceService.Engine {
        private static final int MSG_INVALIDATE = 0;
        private Choreographer mChoreographer = Choreographer.getInstance();
        private boolean mDestroyed;
        private boolean mDrawRequested;
        private final Choreographer.FrameCallback mFrameCallback = new Choreographer.FrameCallback(){

            /*
             * Enabled aggressive block sorting
             */
            public void doFrame(long l2) {
                if (Engine.this.mDestroyed || !Engine.this.mDrawRequested) {
                    return;
                }
                Engine.this.draw(Engine.this.getSurfaceHolder());
            }
        };
        private final Handler mHandler = new Handler(){

            public void handleMessage(Message message) {
                switch (message.what) {
                    default: {
                        return;
                    }
                    case 0: 
                }
                Engine.this.invalidate();
            }
        };

        private void draw(SurfaceHolder surfaceHolder) {
            this.mDrawRequested = false;
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas == null) {
                return;
            }
            try {
                this.onDraw(canvas, surfaceHolder.getSurfaceFrame());
                return;
            }
            finally {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void invalidate() {
            if (!this.mDrawRequested) {
                this.mDrawRequested = true;
                this.mChoreographer.postFrameCallback(this.mFrameCallback);
            }
        }

        @Override
        public void onDestroy() {
            this.mDestroyed = true;
            this.mHandler.removeMessages(0);
            this.mChoreographer.removeFrameCallback(this.mFrameCallback);
            super.onDestroy();
        }

        public void onDraw(Canvas canvas, Rect rect) {
        }

        public void onSurfaceChanged(SurfaceHolder surfaceHolder, int n2, int n3, int n4) {
            if (Log.isLoggable((String)CanvasWatchFaceService.TAG, (int)3)) {
                Log.d((String)CanvasWatchFaceService.TAG, (String)"onSurfaceChanged");
            }
            super.onSurfaceChanged(surfaceHolder, n2, n3, n4);
            this.invalidate();
        }

        public void onSurfaceCreated(SurfaceHolder surfaceHolder) {
            if (Log.isLoggable((String)CanvasWatchFaceService.TAG, (int)3)) {
                Log.d((String)CanvasWatchFaceService.TAG, (String)"onSurfaceCreated");
            }
            super.onSurfaceCreated(surfaceHolder);
            this.invalidate();
        }

        public void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
            if (Log.isLoggable((String)CanvasWatchFaceService.TAG, (int)3)) {
                Log.d((String)CanvasWatchFaceService.TAG, (String)"onSurfaceRedrawNeeded");
            }
            super.onSurfaceRedrawNeeded(surfaceHolder);
            this.draw(surfaceHolder);
        }

        public void postInvalidate() {
            this.mHandler.sendEmptyMessage(0);
        }
    }
}

