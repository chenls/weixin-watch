/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.media.AudioManager
 *  android.media.MediaPlayer
 *  android.media.MediaPlayer$OnCompletionListener
 *  android.net.ConnectivityManager
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.text.TextUtils
 *  android.util.Log
 *  org.apache.http.client.ClientProtocolException
 *  org.apache.http.client.methods.HttpGet
 *  org.apache.http.client.methods.HttpUriRequest
 *  org.apache.http.client.utils.URLEncodedUtils
 *  org.apache.http.impl.client.DefaultHttpClient
 *  org.apache.http.message.BasicNameValuePair
 */
package mobvoiapi;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import com.mobvoi.android.speech.synthesizer.ErrorCode;
import com.mobvoi.android.speech.synthesizer.SpeechSynthesizerCallbackInterface;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import mobvoiapi.ba;
import mobvoiapi.bp;
import mobvoiapi.bq;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class az {
    private static float q = 1.0f;
    private String a = "ticwatch";
    private String b = "wifi";
    private String c = "unknown";
    private String d = "female";
    private String e = null;
    private Context f = null;
    private SpeechSynthesizerCallbackInterface g = null;
    private Handler h;
    private boolean i = false;
    private boolean j = false;
    private MediaPlayer k = new MediaPlayer();
    private final Object l = new Object();
    private String m = null;
    private Thread n = new Thread(new Runnable(){

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            bp.a("SpeechSynthesizerClient", "mTtsHttpPoster Start text: " + az.a(az.this));
            if (TextUtils.isEmpty((CharSequence)az.a(az.this))) {
                az.b(az.this);
                return;
            }
            if (!bq.a((ConnectivityManager)az.c(az.this).getSystemService("connectivity"))) {
                az.a(az.this, true);
                az.d(az.this).onError(ErrorCode.NO_NETWORK, "no network is available");
                return;
            }
            try {
                var2_1 = az.a(az.this, az.a(az.this));
                var2_1 = new HttpGet("http://tts.mobvoi.com/api/synthesis?" + (String)var2_1);
                var3_4 = new DefaultHttpClient().execute((HttpUriRequest)var2_1);
                var2_1 = az.e(az.this);
                synchronized (var2_1) {
                }
            }
            catch (ClientProtocolException var2_2) {
                az.a(az.this, true);
                az.d(az.this).onError(ErrorCode.SYSTEM_ERROR, var2_2.toString());
lbl21:
                // 4 sources

                while (true) {
                    bp.a("SpeechSynthesizerClient", "mTtsHttpPoster Done");
                    return;
                }
            }
            {
                az.b(az.this, true);
                az.f(az.this).cancel();
                if (az.g(az.this)) {
                    return;
                }
            }
            try {
                var1_5 = var3_4.getStatusLine().getStatusCode();
                if (200 == var1_5) {
                    az.d(az.this).onStart();
                    az.a(az.this, var3_4.getEntity().getContent());
                }
                ** GOTO lbl-1000
            }
            catch (IOException var2_3) {
                az.a(az.this, true);
                az.d(az.this).onError(ErrorCode.SYSTEM_ERROR, var2_3.toString());
            }
            ** GOTO lbl21
lbl-1000:
            // 1 sources

            {
                az.a(az.this, true);
                az.d(az.this).onError(ErrorCode.SYSTEM_ERROR, "We received http status code as " + String.valueOf(var1_5));
                ** continue;
            }
        }
    });
    private Timer o = new Timer();
    private TimerTask p = new TimerTask(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            Object object = az.this.l;
            synchronized (object) {
                if (az.this.j) {
                    return;
                }
                az.a(az.this, true);
            }
            bp.a("SpeechSynthesizerClient", "timeout for http call, cancel this synthesis");
            az.this.h.removeCallbacks((Runnable)az.this.n);
            az.this.n.interrupt();
            try {
                az.this.n.join();
            }
            catch (InterruptedException interruptedException) {
                bp.a("SpeechSynthesizerClient", interruptedException.toString());
            }
            az.this.g.onError(ErrorCode.NETWORK_TIMEOUT, "tts failed because your network might be too slow");
            bp.a("SpeechSynthesizerClient", "Done joining the mTtsHttpPoster");
        }
    };

    public az(Context context, SpeechSynthesizerCallbackInterface speechSynthesizerCallbackInterface) {
        this.f = context;
        this.g = speechSynthesizerCallbackInterface;
        if (this.g == null || this.f == null) {
            throw new RuntimeException("SpeechSynthesizerClientFailed to instantiate TTSClient!");
        }
        context = new HandlerThread("SpeechSynthesizerClientHandlerThread");
        context.start();
        this.h = new Handler(context.getLooper());
    }

    private String a(String string2) {
        LinkedList<BasicNameValuePair> linkedList = new LinkedList<BasicNameValuePair>();
        linkedList.add(new BasicNameValuePair("product", this.a));
        linkedList.add(new BasicNameValuePair("nettype", this.b));
        linkedList.add(new BasicNameValuePair("device_id", this.e));
        linkedList.add(new BasicNameValuePair("text", string2));
        linkedList.add(new BasicNameValuePair("timestamp", String.valueOf(System.currentTimeMillis())));
        linkedList.add(new BasicNameValuePair("voice", this.d));
        linkedList.add(new BasicNameValuePair("language", this.c));
        return URLEncodedUtils.format(linkedList, (String)"UTF-8");
    }

    static /* synthetic */ String a(az az2) {
        return az2.m;
    }

    static /* synthetic */ String a(az az2, String string2) {
        return az2.a(string2);
    }

    public static void a(float f2) {
        if (f2 < 0.0f) {
            q = 0.0f;
            return;
        }
        if (f2 > 1.0f) {
            q = 1.0f;
            return;
        }
        q = f2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void a(InputStream var1_1) {
        var2_3 = (AudioManager)this.f.getSystemService("audio");
        var2_3.setStreamVolume(9, (int)((float)var2_3.getStreamMaxVolume(9) * az.q), 1);
        var1_1 = var2_3 = new ba((InputStream)var1_1);
        var3_4 /* !! */  = var2_3.a(null);
        var1_1 = var2_3;
        this.k.setDataSource(this.f, var3_4 /* !! */ );
        var1_1 = var2_3;
        this.k.setAudioStreamType(9);
        var1_1 = var2_3;
        this.k.prepare();
        var1_1 = var2_3;
        if (this.k.isPlaying()) ** GOTO lbl31
        var1_1 = var2_3;
        var3_4 /* !! */  = this.l;
        var1_1 = var2_3;
        // MONITORENTER : var3_4 /* !! */ 
        if (!this.i) {
            this.k.start();
            this.k.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (az.this.k != mediaPlayer) {
                        throw new RuntimeException("SpeechSynthesizerClient Why these two are not equal");
                    }
                    az.this.c();
                }
            });
        }
        // MONITOREXIT : var3_4 /* !! */ 
        ** GOTO lbl34
        {
            catch (Throwable var4_9) {}
            var1_1 = var2_3;
            try {
                try {
                    throw var4_9;
                }
                catch (Exception var3_5) {}
lbl31:
                // 1 sources

                var1_1 = var2_3;
                bp.d("SpeechSynthesizerClient", "Since the MediaPlayer is already playing something, I couldn't play the synthresize audio");
lbl34:
                // 2 sources

                if (var2_3 == null) return;
                var2_3.a();
                return;
                ** GOTO lbl-1000
            }
            catch (Throwable var3_7) {
                block14: {
                    var2_3 = var1_1;
                    var1_1 = var3_7;
                    break block14;
                    catch (Throwable var1_2) {
                        var2_3 = null;
                    }
                }
                if (var2_3 == null) throw var1_1;
                var2_3.a();
                throw var1_1;
            }
            catch (Exception var3_8) {
                var2_3 = null;
            }
lbl-1000:
            // 2 sources

            {
                var1_1 = var2_3;
                Log.e((String)"SpeechSynthesizerClient", (String)var3_6.toString());
                if (var2_3 == null) return;
                var2_3.a();
                return;
            }
        }
    }

    static /* synthetic */ void a(az az2, InputStream inputStream) {
        az2.a(inputStream);
    }

    static /* synthetic */ boolean a(az az2, boolean bl2) {
        az2.i = bl2;
        return bl2;
    }

    static /* synthetic */ boolean b(az az2, boolean bl2) {
        az2.j = bl2;
        return bl2;
    }

    static /* synthetic */ Context c(az az2) {
        return az2.f;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void c() {
        synchronized (this) {
            Object object = this.l;
            synchronized (object) {
                if (!this.i) {
                    this.i = true;
                    if (this.k.isPlaying()) {
                        this.k.stop();
                        this.k.release();
                    }
                    this.g.onCompletion();
                }
                return;
            }
        }
    }

    static /* synthetic */ Timer f(az az2) {
        return az2.o;
    }

    static /* synthetic */ boolean g(az az2) {
        return az2.i;
    }

    public int a() {
        if (this.i) {
            return 0;
        }
        return this.k.getDuration() - this.k.getCurrentPosition();
    }

    public void a(final int n2) {
        this.h.post(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                if (!az.this.k.isPlaying()) {
                    az.this.c();
                    return;
                }
                if (n2 == 0) {
                    az.this.c();
                    return;
                }
                int n22 = n2 * 5;
                float f2 = 0.8f / (float)n22;
                float f3 = 0.8f - f2;
                for (int i2 = 0; i2 < n22 && f3 >= 0.0f; ++i2, f3 -= f2) {
                    az.this.k.setVolume(f3, f3);
                    try {
                        Thread.sleep(200L);
                        continue;
                    }
                    catch (InterruptedException interruptedException) {
                        bp.a("SpeechSynthesizerClient", interruptedException.toString());
                    }
                }
                az.this.c();
            }
        });
    }

    public void a(String string2, long l2) {
        this.m = string2;
        this.h.post((Runnable)this.n);
        if (l2 > 0L) {
            this.o.schedule(this.p, l2);
        }
    }

    public boolean b() {
        return this.i;
    }
}

