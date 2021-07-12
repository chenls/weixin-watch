/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Rect
 *  android.opengl.EGL14
 *  android.opengl.EGLConfig
 *  android.opengl.EGLContext
 *  android.opengl.EGLDisplay
 *  android.opengl.EGLSurface
 *  android.opengl.GLES20
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Message
 *  android.util.Log
 *  android.view.Choreographer
 *  android.view.Choreographer$FrameCallback
 *  android.view.SurfaceHolder
 *  android.view.WindowInsets
 */
package android.support.wearable.watchface;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.watchface.WatchFaceService;
import android.util.Log;
import android.view.Choreographer;
import android.view.SurfaceHolder;
import android.view.WindowInsets;

@TargetApi(value=21)
public abstract class Gles2WatchFaceService
extends WatchFaceService {
    private static final int[] EGL_CONFIG_ATTRIB_LIST = new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12344};
    private static final int[] EGL_CONTEXT_ATTRIB_LIST = new int[]{12440, 2, 12344};
    private static final int[] EGL_SURFACE_ATTRIB_LIST = new int[]{12344};
    private static final boolean LOG_VERBOSE = false;
    private static final String TAG = "Gles2WatchFaceService";
    private static final boolean TRACE_DRAW = false;

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    public class Engine
    extends WatchFaceService.Engine {
        private static final int MSG_INVALIDATE = 0;
        private boolean mCalledOnGlContextCreated;
        private final Choreographer mChoreographer = Choreographer.getInstance();
        private boolean mDestroyed;
        private boolean mDrawRequested;
        private EGLConfig mEglConfig;
        private EGLContext mEglContext;
        private EGLDisplay mEglDisplay;
        private EGLSurface mEglSurface;
        private final Choreographer.FrameCallback mFrameCallback = new Choreographer.FrameCallback(){

            /*
             * Enabled aggressive block sorting
             */
            public void doFrame(long l2) {
                if (Engine.this.mDestroyed || !Engine.this.mDrawRequested) {
                    return;
                }
                Engine.this.drawFrame();
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
        private int mInsetBottom;
        private int mInsetLeft;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void drawFrame() {
            block5: {
                block4: {
                    this.mDrawRequested = false;
                    if (this.mEglSurface == null) break block4;
                    this.makeContextCurrent();
                    this.onDraw();
                    if (!EGL14.eglSwapBuffers((EGLDisplay)this.mEglDisplay, (EGLSurface)this.mEglSurface)) break block5;
                }
                return;
            }
            Log.w((String)Gles2WatchFaceService.TAG, (String)"eglSwapBuffers failed");
        }

        private void makeContextCurrent() {
            if (!EGL14.eglMakeCurrent((EGLDisplay)this.mEglDisplay, (EGLSurface)this.mEglSurface, (EGLSurface)this.mEglSurface, (EGLContext)this.mEglContext)) {
                throw new RuntimeException("eglMakeCurrent failed");
            }
        }

        public EGLConfig chooseEglConfig(EGLDisplay eGLDisplay) {
            int[] nArray = new int[1];
            EGLConfig[] eGLConfigArray = new EGLConfig[1];
            if (!EGL14.eglChooseConfig((EGLDisplay)eGLDisplay, (int[])EGL_CONFIG_ATTRIB_LIST, (int)0, (EGLConfig[])eGLConfigArray, (int)0, (int)eGLConfigArray.length, (int[])nArray, (int)0)) {
                throw new RuntimeException("eglChooseConfig failed");
            }
            if (nArray[0] == 0) {
                throw new RuntimeException("no matching EGL configs");
            }
            return eGLConfigArray[0];
        }

        public EGLContext createEglContext(EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
            if ((eGLDisplay = EGL14.eglCreateContext((EGLDisplay)eGLDisplay, (EGLConfig)eGLConfig, (EGLContext)EGL14.EGL_NO_CONTEXT, (int[])EGL_CONTEXT_ATTRIB_LIST, (int)0)) == EGL14.EGL_NO_CONTEXT) {
                throw new RuntimeException("eglCreateContext failed");
            }
            return eGLDisplay;
        }

        public EGLSurface createWindowSurface(EGLDisplay eGLDisplay, EGLConfig eGLConfig, SurfaceHolder surfaceHolder) {
            if ((eGLDisplay = EGL14.eglCreateWindowSurface((EGLDisplay)eGLDisplay, (EGLConfig)eGLConfig, (Object)surfaceHolder.getSurface(), (int[])EGL_SURFACE_ATTRIB_LIST, (int)0)) == EGL14.EGL_NO_SURFACE) {
                throw new RuntimeException("eglCreateWindowSurface failed");
            }
            return eGLDisplay;
        }

        public EGLDisplay initializeEglDisplay() {
            EGLDisplay eGLDisplay = EGL14.eglGetDisplay((int)0);
            if (eGLDisplay == EGL14.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay returned EGL_NO_DISPLAY");
            }
            int[] nArray = new int[2];
            if (!EGL14.eglInitialize((EGLDisplay)eGLDisplay, (int[])nArray, (int)0, (int[])nArray, (int)1)) {
                throw new RuntimeException("eglInitialize failed");
            }
            if (Log.isLoggable((String)Gles2WatchFaceService.TAG, (int)3)) {
                Log.d((String)Gles2WatchFaceService.TAG, (String)("EGL version " + nArray[0] + "." + nArray[1]));
            }
            return eGLDisplay;
        }

        public final void invalidate() {
            if (!this.mDrawRequested) {
                this.mDrawRequested = true;
                this.mChoreographer.postFrameCallback(this.mFrameCallback);
            }
        }

        public void onApplyWindowInsets(WindowInsets windowInsets) {
            if (Log.isLoggable((String)Gles2WatchFaceService.TAG, (int)3)) {
                Log.d((String)Gles2WatchFaceService.TAG, (String)("onApplyWindowInsets: " + windowInsets));
            }
            super.onApplyWindowInsets(windowInsets);
            if (Build.VERSION.SDK_INT <= 21) {
                Rect rect = this.getSurfaceHolder().getSurfaceFrame();
                this.mInsetLeft = windowInsets.getSystemWindowInsetLeft();
                this.mInsetBottom = windowInsets.getSystemWindowInsetBottom();
                this.makeContextCurrent();
                GLES20.glViewport((int)(-this.mInsetLeft), (int)(-this.mInsetBottom), (int)rect.width(), (int)rect.height());
            }
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            if (Log.isLoggable((String)Gles2WatchFaceService.TAG, (int)3)) {
                Log.d((String)Gles2WatchFaceService.TAG, (String)"onCreate");
            }
            super.onCreate(surfaceHolder);
            if (this.mEglDisplay == null) {
                this.mEglDisplay = this.initializeEglDisplay();
            }
            if (this.mEglConfig == null) {
                this.mEglConfig = this.chooseEglConfig(this.mEglDisplay);
            }
            if (this.mEglContext == null) {
                this.mEglContext = this.createEglContext(this.mEglDisplay, this.mEglConfig);
            }
        }

        @Override
        public void onDestroy() {
            this.mDestroyed = true;
            this.mHandler.removeMessages(0);
            this.mChoreographer.removeFrameCallback(this.mFrameCallback);
            if (this.mEglSurface != null) {
                if (!EGL14.eglDestroySurface((EGLDisplay)this.mEglDisplay, (EGLSurface)this.mEglSurface)) {
                    Log.w((String)Gles2WatchFaceService.TAG, (String)"eglDestroySurface failed");
                }
                this.mEglSurface = null;
            }
            if (this.mEglContext != null) {
                if (!EGL14.eglDestroyContext((EGLDisplay)this.mEglDisplay, (EGLContext)this.mEglContext)) {
                    Log.w((String)Gles2WatchFaceService.TAG, (String)"eglDestroyContext failed");
                }
                this.mEglContext = null;
            }
            if (this.mEglDisplay != null) {
                if (!EGL14.eglTerminate((EGLDisplay)this.mEglDisplay)) {
                    Log.w((String)Gles2WatchFaceService.TAG, (String)"eglTerminate failed");
                }
                this.mEglDisplay = null;
            }
            super.onDestroy();
        }

        public void onDraw() {
        }

        public void onGlContextCreated() {
        }

        public void onGlSurfaceCreated(int n2, int n3) {
        }

        public final void onSurfaceChanged(SurfaceHolder surfaceHolder, int n2, int n3, int n4) {
            if (Log.isLoggable((String)Gles2WatchFaceService.TAG, (int)3)) {
                Log.d((String)Gles2WatchFaceService.TAG, (String)"onSurfaceChanged");
            }
            super.onSurfaceChanged(surfaceHolder, n2, n3, n4);
            if (this.mEglSurface != null && !EGL14.eglDestroySurface((EGLDisplay)this.mEglDisplay, (EGLSurface)this.mEglSurface)) {
                Log.w((String)Gles2WatchFaceService.TAG, (String)"eglDestroySurface failed");
            }
            this.mEglSurface = this.createWindowSurface(this.mEglDisplay, this.mEglConfig, surfaceHolder);
            this.makeContextCurrent();
            GLES20.glViewport((int)(-this.mInsetLeft), (int)(-this.mInsetBottom), (int)n3, (int)n4);
            if (!this.mCalledOnGlContextCreated) {
                this.mCalledOnGlContextCreated = true;
                this.onGlContextCreated();
            }
            this.onGlSurfaceCreated(n3, n4);
            this.invalidate();
        }

        public final void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (Log.isLoggable((String)Gles2WatchFaceService.TAG, (int)3)) {
                Log.d((String)Gles2WatchFaceService.TAG, (String)"onSurfaceDestroyed");
            }
            try {
                if (!EGL14.eglDestroySurface((EGLDisplay)this.mEglDisplay, (EGLSurface)this.mEglSurface)) {
                    Log.w((String)Gles2WatchFaceService.TAG, (String)"eglDestroySurface failed");
                }
                this.mEglSurface = null;
                return;
            }
            finally {
                super.onSurfaceDestroyed(surfaceHolder);
            }
        }

        public final void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
            if (Log.isLoggable((String)Gles2WatchFaceService.TAG, (int)3)) {
                Log.d((String)Gles2WatchFaceService.TAG, (String)"onSurfaceRedrawNeeded");
            }
            super.onSurfaceRedrawNeeded(surfaceHolder);
            this.drawFrame();
        }

        public final void postInvalidate() {
            this.mHandler.sendEmptyMessage(0);
        }
    }
}

