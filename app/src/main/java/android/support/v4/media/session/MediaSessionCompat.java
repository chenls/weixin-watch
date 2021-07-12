/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.media.AudioManager
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteCallbackList
 *  android.os.RemoteException
 *  android.os.ResultReceiver
 *  android.os.SystemClock
 *  android.text.TextUtils
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.KeyEvent
 */
package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompatApi14;
import android.support.v4.media.session.MediaSessionCompatApi18;
import android.support.v4.media.session.MediaSessionCompatApi19;
import android.support.v4.media.session.MediaSessionCompatApi21;
import android.support.v4.media.session.MediaSessionCompatApi22;
import android.support.v4.media.session.MediaSessionCompatApi23;
import android.support.v4.media.session.MediaSessionCompatApi24;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MediaSessionCompat {
    static final String ACTION_ARGUMENT_EXTRAS = "android.support.v4.media.session.action.ARGUMENT_EXTRAS";
    static final String ACTION_ARGUMENT_MEDIA_ID = "android.support.v4.media.session.action.ARGUMENT_MEDIA_ID";
    static final String ACTION_ARGUMENT_QUERY = "android.support.v4.media.session.action.ARGUMENT_QUERY";
    static final String ACTION_ARGUMENT_URI = "android.support.v4.media.session.action.ARGUMENT_URI";
    static final String ACTION_PLAY_FROM_URI = "android.support.v4.media.session.action.PLAY_FROM_URI";
    static final String ACTION_PREPARE = "android.support.v4.media.session.action.PREPARE";
    static final String ACTION_PREPARE_FROM_MEDIA_ID = "android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID";
    static final String ACTION_PREPARE_FROM_SEARCH = "android.support.v4.media.session.action.PREPARE_FROM_SEARCH";
    static final String ACTION_PREPARE_FROM_URI = "android.support.v4.media.session.action.PREPARE_FROM_URI";
    public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;
    public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
    private static final int MAX_BITMAP_SIZE_IN_DP = 320;
    static final String TAG = "MediaSessionCompat";
    static int sMaxBitmapSize;
    private final ArrayList<OnActiveChangeListener> mActiveListeners = new ArrayList();
    private final MediaControllerCompat mController;
    private final MediaSessionImpl mImpl;

    private MediaSessionCompat(Context context, MediaSessionImpl mediaSessionImpl) {
        this.mImpl = mediaSessionImpl;
        this.mController = new MediaControllerCompat(context, this);
    }

    public MediaSessionCompat(Context context, String string2) {
        this(context, string2, null, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public MediaSessionCompat(Context context, String string2, ComponentName componentName, PendingIntent pendingIntent) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("tag must not be null or empty");
        }
        this.mImpl = Build.VERSION.SDK_INT >= 21 ? new MediaSessionImplApi21(context, string2) : new MediaSessionImplBase(context, string2, componentName, pendingIntent);
        this.mController = new MediaControllerCompat(context, this);
        if (sMaxBitmapSize == 0) {
            sMaxBitmapSize = (int)TypedValue.applyDimension((int)1, (float)320.0f, (DisplayMetrics)context.getResources().getDisplayMetrics());
        }
    }

    public static MediaSessionCompat fromMediaSession(Context context, Object object) {
        if (context == null || object == null || Build.VERSION.SDK_INT < 21) {
            return null;
        }
        return new MediaSessionCompat(context, new MediaSessionImplApi21(object));
    }

    @Deprecated
    public static MediaSessionCompat obtain(Context context, Object object) {
        return MediaSessionCompat.fromMediaSession(context, object);
    }

    public void addOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener == null) {
            throw new IllegalArgumentException("Listener may not be null");
        }
        this.mActiveListeners.add(onActiveChangeListener);
    }

    public String getCallingPackage() {
        return this.mImpl.getCallingPackage();
    }

    public MediaControllerCompat getController() {
        return this.mController;
    }

    public Object getMediaSession() {
        return this.mImpl.getMediaSession();
    }

    public Object getRemoteControlClient() {
        return this.mImpl.getRemoteControlClient();
    }

    public Token getSessionToken() {
        return this.mImpl.getSessionToken();
    }

    public boolean isActive() {
        return this.mImpl.isActive();
    }

    public void release() {
        this.mImpl.release();
    }

    public void removeOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener == null) {
            throw new IllegalArgumentException("Listener may not be null");
        }
        this.mActiveListeners.remove(onActiveChangeListener);
    }

    public void sendSessionEvent(String string2, Bundle bundle) {
        if (TextUtils.isEmpty((CharSequence)string2)) {
            throw new IllegalArgumentException("event cannot be null or empty");
        }
        this.mImpl.sendSessionEvent(string2, bundle);
    }

    public void setActive(boolean bl2) {
        this.mImpl.setActive(bl2);
        Iterator<OnActiveChangeListener> iterator = this.mActiveListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onActiveChanged();
        }
    }

    public void setCallback(Callback callback) {
        this.setCallback(callback, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCallback(Callback callback, Handler handler) {
        MediaSessionImpl mediaSessionImpl = this.mImpl;
        if (handler == null) {
            handler = new Handler();
        }
        mediaSessionImpl.setCallback(callback, handler);
    }

    public void setExtras(Bundle bundle) {
        this.mImpl.setExtras(bundle);
    }

    public void setFlags(int n2) {
        this.mImpl.setFlags(n2);
    }

    public void setMediaButtonReceiver(PendingIntent pendingIntent) {
        this.mImpl.setMediaButtonReceiver(pendingIntent);
    }

    public void setMetadata(MediaMetadataCompat mediaMetadataCompat) {
        this.mImpl.setMetadata(mediaMetadataCompat);
    }

    public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
        this.mImpl.setPlaybackState(playbackStateCompat);
    }

    public void setPlaybackToLocal(int n2) {
        this.mImpl.setPlaybackToLocal(n2);
    }

    public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
        if (volumeProviderCompat == null) {
            throw new IllegalArgumentException("volumeProvider may not be null!");
        }
        this.mImpl.setPlaybackToRemote(volumeProviderCompat);
    }

    public void setQueue(List<QueueItem> list) {
        this.mImpl.setQueue(list);
    }

    public void setQueueTitle(CharSequence charSequence) {
        this.mImpl.setQueueTitle(charSequence);
    }

    public void setRatingType(int n2) {
        this.mImpl.setRatingType(n2);
    }

    public void setSessionActivity(PendingIntent pendingIntent) {
        this.mImpl.setSessionActivity(pendingIntent);
    }

    public static abstract class Callback {
        final Object mCallbackObj;

        public Callback() {
            if (Build.VERSION.SDK_INT >= 24) {
                this.mCallbackObj = MediaSessionCompatApi24.createCallback(new StubApi24());
                return;
            }
            if (Build.VERSION.SDK_INT >= 23) {
                this.mCallbackObj = MediaSessionCompatApi23.createCallback(new StubApi23());
                return;
            }
            if (Build.VERSION.SDK_INT >= 21) {
                this.mCallbackObj = MediaSessionCompatApi21.createCallback(new StubApi21());
                return;
            }
            this.mCallbackObj = null;
        }

        public void onCommand(String string2, Bundle bundle, ResultReceiver resultReceiver) {
        }

        public void onCustomAction(String string2, Bundle bundle) {
        }

        public void onFastForward() {
        }

        public boolean onMediaButtonEvent(Intent intent) {
            return false;
        }

        public void onPause() {
        }

        public void onPlay() {
        }

        public void onPlayFromMediaId(String string2, Bundle bundle) {
        }

        public void onPlayFromSearch(String string2, Bundle bundle) {
        }

        public void onPlayFromUri(Uri uri, Bundle bundle) {
        }

        public void onPrepare() {
        }

        public void onPrepareFromMediaId(String string2, Bundle bundle) {
        }

        public void onPrepareFromSearch(String string2, Bundle bundle) {
        }

        public void onPrepareFromUri(Uri uri, Bundle bundle) {
        }

        public void onRewind() {
        }

        public void onSeekTo(long l2) {
        }

        public void onSetRating(RatingCompat ratingCompat) {
        }

        public void onSkipToNext() {
        }

        public void onSkipToPrevious() {
        }

        public void onSkipToQueueItem(long l2) {
        }

        public void onStop() {
        }

        private class StubApi21
        implements MediaSessionCompatApi21.Callback {
            StubApi21() {
            }

            @Override
            public void onCommand(String string2, Bundle bundle, ResultReceiver resultReceiver) {
                Callback.this.onCommand(string2, bundle, resultReceiver);
            }

            @Override
            public void onCustomAction(String string2, Bundle bundle) {
                if (string2.equals(MediaSessionCompat.ACTION_PLAY_FROM_URI)) {
                    string2 = (Uri)bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI);
                    bundle = (Bundle)bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                    Callback.this.onPlayFromUri((Uri)string2, bundle);
                    return;
                }
                if (string2.equals(MediaSessionCompat.ACTION_PREPARE)) {
                    Callback.this.onPrepare();
                    return;
                }
                if (string2.equals(MediaSessionCompat.ACTION_PREPARE_FROM_MEDIA_ID)) {
                    string2 = bundle.getString(MediaSessionCompat.ACTION_ARGUMENT_MEDIA_ID);
                    bundle = bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                    Callback.this.onPrepareFromMediaId(string2, bundle);
                    return;
                }
                if (string2.equals(MediaSessionCompat.ACTION_PREPARE_FROM_SEARCH)) {
                    string2 = bundle.getString(MediaSessionCompat.ACTION_ARGUMENT_QUERY);
                    bundle = bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                    Callback.this.onPrepareFromSearch(string2, bundle);
                    return;
                }
                if (string2.equals(MediaSessionCompat.ACTION_PREPARE_FROM_URI)) {
                    string2 = (Uri)bundle.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI);
                    bundle = bundle.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                    Callback.this.onPrepareFromUri((Uri)string2, bundle);
                    return;
                }
                Callback.this.onCustomAction(string2, bundle);
            }

            @Override
            public void onFastForward() {
                Callback.this.onFastForward();
            }

            @Override
            public boolean onMediaButtonEvent(Intent intent) {
                return Callback.this.onMediaButtonEvent(intent);
            }

            @Override
            public void onPause() {
                Callback.this.onPause();
            }

            @Override
            public void onPlay() {
                Callback.this.onPlay();
            }

            @Override
            public void onPlayFromMediaId(String string2, Bundle bundle) {
                Callback.this.onPlayFromMediaId(string2, bundle);
            }

            @Override
            public void onPlayFromSearch(String string2, Bundle bundle) {
                Callback.this.onPlayFromSearch(string2, bundle);
            }

            @Override
            public void onRewind() {
                Callback.this.onRewind();
            }

            @Override
            public void onSeekTo(long l2) {
                Callback.this.onSeekTo(l2);
            }

            @Override
            public void onSetRating(Object object) {
                Callback.this.onSetRating(RatingCompat.fromRating(object));
            }

            @Override
            public void onSkipToNext() {
                Callback.this.onSkipToNext();
            }

            @Override
            public void onSkipToPrevious() {
                Callback.this.onSkipToPrevious();
            }

            @Override
            public void onSkipToQueueItem(long l2) {
                Callback.this.onSkipToQueueItem(l2);
            }

            @Override
            public void onStop() {
                Callback.this.onStop();
            }
        }

        private class StubApi23
        extends StubApi21
        implements MediaSessionCompatApi23.Callback {
            StubApi23() {
            }

            @Override
            public void onPlayFromUri(Uri uri, Bundle bundle) {
                Callback.this.onPlayFromUri(uri, bundle);
            }
        }

        private class StubApi24
        extends StubApi23
        implements MediaSessionCompatApi24.Callback {
            StubApi24() {
            }

            @Override
            public void onPrepare() {
                Callback.this.onPrepare();
            }

            @Override
            public void onPrepareFromMediaId(String string2, Bundle bundle) {
                Callback.this.onPrepareFromMediaId(string2, bundle);
            }

            @Override
            public void onPrepareFromSearch(String string2, Bundle bundle) {
                Callback.this.onPrepareFromSearch(string2, bundle);
            }

            @Override
            public void onPrepareFromUri(Uri uri, Bundle bundle) {
                Callback.this.onPrepareFromUri(uri, bundle);
            }
        }
    }

    static interface MediaSessionImpl {
        public String getCallingPackage();

        public Object getMediaSession();

        public Object getRemoteControlClient();

        public Token getSessionToken();

        public boolean isActive();

        public void release();

        public void sendSessionEvent(String var1, Bundle var2);

        public void setActive(boolean var1);

        public void setCallback(Callback var1, Handler var2);

        public void setExtras(Bundle var1);

        public void setFlags(int var1);

        public void setMediaButtonReceiver(PendingIntent var1);

        public void setMetadata(MediaMetadataCompat var1);

        public void setPlaybackState(PlaybackStateCompat var1);

        public void setPlaybackToLocal(int var1);

        public void setPlaybackToRemote(VolumeProviderCompat var1);

        public void setQueue(List<QueueItem> var1);

        public void setQueueTitle(CharSequence var1);

        public void setRatingType(int var1);

        public void setSessionActivity(PendingIntent var1);
    }

    static class MediaSessionImplApi21
    implements MediaSessionImpl {
        private PendingIntent mMediaButtonIntent;
        private final Object mSessionObj;
        private final Token mToken;

        public MediaSessionImplApi21(Context context, String string2) {
            this.mSessionObj = MediaSessionCompatApi21.createSession(context, string2);
            this.mToken = new Token(MediaSessionCompatApi21.getSessionToken(this.mSessionObj));
        }

        public MediaSessionImplApi21(Object object) {
            this.mSessionObj = MediaSessionCompatApi21.verifySession(object);
            this.mToken = new Token(MediaSessionCompatApi21.getSessionToken(this.mSessionObj));
        }

        @Override
        public String getCallingPackage() {
            if (Build.VERSION.SDK_INT < 24) {
                return null;
            }
            return MediaSessionCompatApi24.getCallingPackage(this.mSessionObj);
        }

        @Override
        public Object getMediaSession() {
            return this.mSessionObj;
        }

        @Override
        public Object getRemoteControlClient() {
            return null;
        }

        @Override
        public Token getSessionToken() {
            return this.mToken;
        }

        @Override
        public boolean isActive() {
            return MediaSessionCompatApi21.isActive(this.mSessionObj);
        }

        @Override
        public void release() {
            MediaSessionCompatApi21.release(this.mSessionObj);
        }

        @Override
        public void sendSessionEvent(String string2, Bundle bundle) {
            MediaSessionCompatApi21.sendSessionEvent(this.mSessionObj, string2, bundle);
        }

        @Override
        public void setActive(boolean bl2) {
            MediaSessionCompatApi21.setActive(this.mSessionObj, bl2);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void setCallback(Callback object, Handler handler) {
            Object object2 = this.mSessionObj;
            object = object == null ? null : ((Callback)object).mCallbackObj;
            MediaSessionCompatApi21.setCallback(object2, object, handler);
        }

        @Override
        public void setExtras(Bundle bundle) {
            MediaSessionCompatApi21.setExtras(this.mSessionObj, bundle);
        }

        @Override
        public void setFlags(int n2) {
            MediaSessionCompatApi21.setFlags(this.mSessionObj, n2);
        }

        @Override
        public void setMediaButtonReceiver(PendingIntent pendingIntent) {
            this.mMediaButtonIntent = pendingIntent;
            MediaSessionCompatApi21.setMediaButtonReceiver(this.mSessionObj, pendingIntent);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void setMetadata(MediaMetadataCompat object) {
            Object object2 = this.mSessionObj;
            object = object == null ? null : ((MediaMetadataCompat)object).getMediaMetadata();
            MediaSessionCompatApi21.setMetadata(object2, object);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void setPlaybackState(PlaybackStateCompat object) {
            Object object2 = this.mSessionObj;
            object = object == null ? null : ((PlaybackStateCompat)object).getPlaybackState();
            MediaSessionCompatApi21.setPlaybackState(object2, object);
        }

        @Override
        public void setPlaybackToLocal(int n2) {
            MediaSessionCompatApi21.setPlaybackToLocal(this.mSessionObj, n2);
        }

        @Override
        public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
            MediaSessionCompatApi21.setPlaybackToRemote(this.mSessionObj, volumeProviderCompat.getVolumeProvider());
        }

        @Override
        public void setQueue(List<QueueItem> object) {
            ArrayList<Object> arrayList = null;
            if (object != null) {
                ArrayList<Object> arrayList2 = new ArrayList<Object>();
                object = object.iterator();
                while (true) {
                    arrayList = arrayList2;
                    if (!object.hasNext()) break;
                    arrayList2.add(((QueueItem)object.next()).getQueueItem());
                }
            }
            MediaSessionCompatApi21.setQueue(this.mSessionObj, arrayList);
        }

        @Override
        public void setQueueTitle(CharSequence charSequence) {
            MediaSessionCompatApi21.setQueueTitle(this.mSessionObj, charSequence);
        }

        @Override
        public void setRatingType(int n2) {
            if (Build.VERSION.SDK_INT < 22) {
                return;
            }
            MediaSessionCompatApi22.setRatingType(this.mSessionObj, n2);
        }

        @Override
        public void setSessionActivity(PendingIntent pendingIntent) {
            MediaSessionCompatApi21.setSessionActivity(this.mSessionObj, pendingIntent);
        }
    }

    static class MediaSessionImplBase
    implements MediaSessionImpl {
        final AudioManager mAudioManager;
        volatile Callback mCallback;
        private final Context mContext;
        final RemoteCallbackList<IMediaControllerCallback> mControllerCallbacks;
        boolean mDestroyed = false;
        Bundle mExtras;
        int mFlags;
        private MessageHandler mHandler;
        private boolean mIsActive = false;
        private boolean mIsMbrRegistered = false;
        private boolean mIsRccRegistered = false;
        int mLocalStream;
        final Object mLock = new Object();
        private final ComponentName mMediaButtonReceiverComponentName;
        private final PendingIntent mMediaButtonReceiverIntent;
        MediaMetadataCompat mMetadata;
        final String mPackageName;
        List<QueueItem> mQueue;
        CharSequence mQueueTitle;
        int mRatingType;
        private final Object mRccObj;
        PendingIntent mSessionActivity;
        PlaybackStateCompat mState;
        private final MediaSessionStub mStub;
        final String mTag;
        private final Token mToken;
        private VolumeProviderCompat.Callback mVolumeCallback;
        VolumeProviderCompat mVolumeProvider;
        int mVolumeType;

        public MediaSessionImplBase(Context context, String string2, ComponentName componentName, PendingIntent pendingIntent) {
            this.mControllerCallbacks = new RemoteCallbackList();
            this.mVolumeCallback = new VolumeProviderCompat.Callback(){

                @Override
                public void onVolumeChanged(VolumeProviderCompat object) {
                    if (MediaSessionImplBase.this.mVolumeProvider != object) {
                        return;
                    }
                    object = new ParcelableVolumeInfo(MediaSessionImplBase.this.mVolumeType, MediaSessionImplBase.this.mLocalStream, ((VolumeProviderCompat)object).getVolumeControl(), ((VolumeProviderCompat)object).getMaxVolume(), ((VolumeProviderCompat)object).getCurrentVolume());
                    MediaSessionImplBase.this.sendVolumeInfoChanged((ParcelableVolumeInfo)object);
                }
            };
            ComponentName componentName2 = componentName;
            if (componentName == null) {
                componentName2 = componentName = MediaButtonReceiver.getMediaButtonReceiverComponent(context);
                if (componentName == null) {
                    Log.w((String)MediaSessionCompat.TAG, (String)"Couldn't find a unique registered media button receiver in the given context.");
                    componentName2 = componentName;
                }
            }
            componentName = pendingIntent;
            if (componentName2 != null) {
                componentName = pendingIntent;
                if (pendingIntent == null) {
                    componentName = new Intent("android.intent.action.MEDIA_BUTTON");
                    componentName.setComponent(componentName2);
                    componentName = PendingIntent.getBroadcast((Context)context, (int)0, (Intent)componentName, (int)0);
                }
            }
            if (componentName2 == null) {
                throw new IllegalArgumentException("MediaButtonReceiver component may not be null.");
            }
            this.mContext = context;
            this.mPackageName = context.getPackageName();
            this.mAudioManager = (AudioManager)context.getSystemService("audio");
            this.mTag = string2;
            this.mMediaButtonReceiverComponentName = componentName2;
            this.mMediaButtonReceiverIntent = componentName;
            this.mStub = new MediaSessionStub();
            this.mToken = new Token(this.mStub);
            this.mRatingType = 0;
            this.mVolumeType = 1;
            this.mLocalStream = 3;
            if (Build.VERSION.SDK_INT >= 14) {
                this.mRccObj = MediaSessionCompatApi14.createRemoteControlClient((PendingIntent)componentName);
                return;
            }
            this.mRccObj = null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendEvent(String string2, Bundle bundle) {
            int n2 = this.mControllerCallbacks.beginBroadcast() - 1;
            while (true) {
                if (n2 < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n2);
                try {
                    iMediaControllerCallback.onEvent(string2, bundle);
                }
                catch (RemoteException remoteException) {}
                --n2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendExtras(Bundle bundle) {
            int n2 = this.mControllerCallbacks.beginBroadcast() - 1;
            while (true) {
                if (n2 < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n2);
                try {
                    iMediaControllerCallback.onExtrasChanged(bundle);
                }
                catch (RemoteException remoteException) {}
                --n2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendMetadata(MediaMetadataCompat mediaMetadataCompat) {
            int n2 = this.mControllerCallbacks.beginBroadcast() - 1;
            while (true) {
                if (n2 < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n2);
                try {
                    iMediaControllerCallback.onMetadataChanged(mediaMetadataCompat);
                }
                catch (RemoteException remoteException) {}
                --n2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendQueue(List<QueueItem> list) {
            int n2 = this.mControllerCallbacks.beginBroadcast() - 1;
            while (true) {
                if (n2 < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n2);
                try {
                    iMediaControllerCallback.onQueueChanged(list);
                }
                catch (RemoteException remoteException) {}
                --n2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendQueueTitle(CharSequence charSequence) {
            int n2 = this.mControllerCallbacks.beginBroadcast() - 1;
            while (true) {
                if (n2 < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n2);
                try {
                    iMediaControllerCallback.onQueueTitleChanged(charSequence);
                }
                catch (RemoteException remoteException) {}
                --n2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendSessionDestroyed() {
            int n2 = this.mControllerCallbacks.beginBroadcast() - 1;
            while (true) {
                if (n2 < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    this.mControllerCallbacks.kill();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n2);
                try {
                    iMediaControllerCallback.onSessionDestroyed();
                }
                catch (RemoteException remoteException) {}
                --n2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void sendState(PlaybackStateCompat playbackStateCompat) {
            int n2 = this.mControllerCallbacks.beginBroadcast() - 1;
            while (true) {
                if (n2 < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n2);
                try {
                    iMediaControllerCallback.onPlaybackStateChanged(playbackStateCompat);
                }
                catch (RemoteException remoteException) {}
                --n2;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean update() {
            boolean bl2;
            boolean bl3 = false;
            if (this.mIsActive) {
                if (!this.mIsMbrRegistered && (this.mFlags & 1) != 0) {
                    if (Build.VERSION.SDK_INT >= 18) {
                        MediaSessionCompatApi18.registerMediaButtonEventReceiver(this.mContext, this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                    } else {
                        ((AudioManager)this.mContext.getSystemService("audio")).registerMediaButtonEventReceiver(this.mMediaButtonReceiverComponentName);
                    }
                    this.mIsMbrRegistered = true;
                } else if (this.mIsMbrRegistered && (this.mFlags & 1) == 0) {
                    if (Build.VERSION.SDK_INT >= 18) {
                        MediaSessionCompatApi18.unregisterMediaButtonEventReceiver(this.mContext, this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                    } else {
                        ((AudioManager)this.mContext.getSystemService("audio")).unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverComponentName);
                    }
                    this.mIsMbrRegistered = false;
                }
                bl2 = bl3;
                if (Build.VERSION.SDK_INT < 14) return bl2;
                if (!this.mIsRccRegistered && (this.mFlags & 2) != 0) {
                    MediaSessionCompatApi14.registerRemoteControlClient(this.mContext, this.mRccObj);
                    this.mIsRccRegistered = true;
                    return true;
                }
                bl2 = bl3;
                if (!this.mIsRccRegistered) return bl2;
                bl2 = bl3;
                if ((this.mFlags & 2) != 0) return bl2;
                MediaSessionCompatApi14.setState(this.mRccObj, 0);
                MediaSessionCompatApi14.unregisterRemoteControlClient(this.mContext, this.mRccObj);
                this.mIsRccRegistered = false;
                return false;
            }
            if (this.mIsMbrRegistered) {
                if (Build.VERSION.SDK_INT >= 18) {
                    MediaSessionCompatApi18.unregisterMediaButtonEventReceiver(this.mContext, this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                } else {
                    ((AudioManager)this.mContext.getSystemService("audio")).unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverComponentName);
                }
                this.mIsMbrRegistered = false;
            }
            bl2 = bl3;
            if (!this.mIsRccRegistered) return bl2;
            MediaSessionCompatApi14.setState(this.mRccObj, 0);
            MediaSessionCompatApi14.unregisterRemoteControlClient(this.mContext, this.mRccObj);
            this.mIsRccRegistered = false;
            return false;
        }

        void adjustVolume(int n2, int n3) {
            if (this.mVolumeType == 2) {
                if (this.mVolumeProvider != null) {
                    this.mVolumeProvider.onAdjustVolume(n2);
                }
                return;
            }
            this.mAudioManager.adjustStreamVolume(this.mLocalStream, n2, n3);
        }

        @Override
        public String getCallingPackage() {
            return null;
        }

        @Override
        public Object getMediaSession() {
            return null;
        }

        @Override
        public Object getRemoteControlClient() {
            return this.mRccObj;
        }

        @Override
        public Token getSessionToken() {
            return this.mToken;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        PlaybackStateCompat getStateWithUpdatedPosition() {
            PlaybackStateCompat playbackStateCompat;
            Object object;
            block12: {
                Object var8_5;
                long l2;
                long l3;
                block13: {
                    l3 = -1L;
                    object = this.mLock;
                    synchronized (object) {
                        playbackStateCompat = this.mState;
                        l2 = l3;
                        if (this.mMetadata != null) {
                            l2 = l3;
                            if (this.mMetadata.containsKey("android.media.metadata.DURATION")) {
                                l2 = this.mMetadata.getLong("android.media.metadata.DURATION");
                            }
                        }
                    }
                    var8_5 = null;
                    object = var8_5;
                    if (playbackStateCompat == null) break block12;
                    if (playbackStateCompat.getState() == 3 || playbackStateCompat.getState() == 4) break block13;
                    object = var8_5;
                    if (playbackStateCompat.getState() != 5) break block12;
                }
                l3 = playbackStateCompat.getLastPositionUpdateTime();
                long l4 = SystemClock.elapsedRealtime();
                object = var8_5;
                if (l3 > 0L) {
                    l3 = (long)(playbackStateCompat.getPlaybackSpeed() * (float)(l4 - l3)) + playbackStateCompat.getPosition();
                    if (l2 < 0L || l3 <= l2) {
                        l2 = l3;
                        if (l3 < 0L) {
                            l2 = 0L;
                        }
                    }
                    object = new PlaybackStateCompat.Builder(playbackStateCompat);
                    ((PlaybackStateCompat.Builder)object).setState(playbackStateCompat.getState(), l2, playbackStateCompat.getPlaybackSpeed(), l4);
                    object = ((PlaybackStateCompat.Builder)object).build();
                }
            }
            if (object == null) {
                return playbackStateCompat;
            }
            return object;
        }

        @Override
        public boolean isActive() {
            return this.mIsActive;
        }

        void postToHandler(int n2) {
            this.postToHandler(n2, null);
        }

        void postToHandler(int n2, Object object) {
            this.postToHandler(n2, object, null);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void postToHandler(int n2, Object object, Bundle bundle) {
            Object object2 = this.mLock;
            synchronized (object2) {
                if (this.mHandler != null) {
                    this.mHandler.post(n2, object, bundle);
                }
                return;
            }
        }

        @Override
        public void release() {
            this.mIsActive = false;
            this.mDestroyed = true;
            this.update();
            this.sendSessionDestroyed();
        }

        @Override
        public void sendSessionEvent(String string2, Bundle bundle) {
            this.sendEvent(string2, bundle);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void sendVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) {
            int n2 = this.mControllerCallbacks.beginBroadcast() - 1;
            while (true) {
                if (n2 < 0) {
                    this.mControllerCallbacks.finishBroadcast();
                    return;
                }
                IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(n2);
                try {
                    iMediaControllerCallback.onVolumeInfoChanged(parcelableVolumeInfo);
                }
                catch (RemoteException remoteException) {}
                --n2;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void setActive(boolean bl2) {
            block3: {
                block2: {
                    if (bl2 == this.mIsActive) break block2;
                    this.mIsActive = bl2;
                    if (this.update()) break block3;
                }
                return;
            }
            this.setMetadata(this.mMetadata);
            this.setPlaybackState(this.mState);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setCallback(Callback object, Handler object2) {
            this.mCallback = object;
            if (object == null) {
                if (Build.VERSION.SDK_INT >= 18) {
                    MediaSessionCompatApi18.setOnPlaybackPositionUpdateListener(this.mRccObj, null);
                }
                if (Build.VERSION.SDK_INT < 19) return;
                MediaSessionCompatApi19.setOnMetadataUpdateListener(this.mRccObj, null);
                return;
            }
            object = object2;
            if (object2 == null) {
                object = new Handler();
            }
            object2 = this.mLock;
            synchronized (object2) {
                this.mHandler = new MessageHandler(object.getLooper());
            }
            object = new MediaSessionCompatApi19.Callback(){

                @Override
                public void onSeekTo(long l2) {
                    MediaSessionImplBase.this.postToHandler(18, l2);
                }

                @Override
                public void onSetRating(Object object) {
                    MediaSessionImplBase.this.postToHandler(19, RatingCompat.fromRating(object));
                }
            };
            if (Build.VERSION.SDK_INT >= 18) {
                object2 = MediaSessionCompatApi18.createPlaybackPositionUpdateListener((MediaSessionCompatApi18.Callback)object);
                MediaSessionCompatApi18.setOnPlaybackPositionUpdateListener(this.mRccObj, object2);
            }
            if (Build.VERSION.SDK_INT < 19) return;
            object = MediaSessionCompatApi19.createMetadataUpdateListener((MediaSessionCompatApi19.Callback)object);
            MediaSessionCompatApi19.setOnMetadataUpdateListener(this.mRccObj, object);
        }

        @Override
        public void setExtras(Bundle bundle) {
            this.mExtras = bundle;
            this.sendExtras(bundle);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setFlags(int n2) {
            Object object = this.mLock;
            synchronized (object) {
                this.mFlags = n2;
            }
            this.update();
        }

        @Override
        public void setMediaButtonReceiver(PendingIntent pendingIntent) {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setMetadata(MediaMetadataCompat object) {
            MediaMetadataCompat mediaMetadataCompat;
            Object object2;
            Object object3;
            block9: {
                block8: {
                    object3 = null;
                    object2 = null;
                    mediaMetadataCompat = object;
                    if (object != null) {
                        mediaMetadataCompat = new MediaMetadataCompat.Builder((MediaMetadataCompat)object, sMaxBitmapSize).build();
                    }
                    object = this.mLock;
                    synchronized (object) {
                        this.mMetadata = mediaMetadataCompat;
                    }
                    this.sendMetadata(mediaMetadataCompat);
                    if (!this.mIsActive) break block8;
                    if (Build.VERSION.SDK_INT >= 19) {
                        object3 = this.mRccObj;
                        object = mediaMetadataCompat == null ? object2 : mediaMetadataCompat.getBundle();
                        long l2 = this.mState == null ? 0L : this.mState.getActions();
                        MediaSessionCompatApi19.setMetadata(object3, (Bundle)object, l2);
                        return;
                    }
                    if (Build.VERSION.SDK_INT >= 14) break block9;
                }
                return;
            }
            object2 = this.mRccObj;
            object = mediaMetadataCompat == null ? object3 : mediaMetadataCompat.getBundle();
            MediaSessionCompatApi14.setMetadata(object2, (Bundle)object);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
            Object object = this.mLock;
            synchronized (object) {
                this.mState = playbackStateCompat;
            }
            this.sendState(playbackStateCompat);
            if (!this.mIsActive) return;
            if (playbackStateCompat == null) {
                if (Build.VERSION.SDK_INT < 14) return;
                MediaSessionCompatApi14.setState(this.mRccObj, 0);
                MediaSessionCompatApi14.setTransportControlFlags(this.mRccObj, 0L);
                return;
            }
            if (Build.VERSION.SDK_INT >= 18) {
                MediaSessionCompatApi18.setState(this.mRccObj, playbackStateCompat.getState(), playbackStateCompat.getPosition(), playbackStateCompat.getPlaybackSpeed(), playbackStateCompat.getLastPositionUpdateTime());
            } else if (Build.VERSION.SDK_INT >= 14) {
                MediaSessionCompatApi14.setState(this.mRccObj, playbackStateCompat.getState());
            }
            if (Build.VERSION.SDK_INT >= 19) {
                MediaSessionCompatApi19.setTransportControlFlags(this.mRccObj, playbackStateCompat.getActions());
                return;
            }
            if (Build.VERSION.SDK_INT >= 18) {
                MediaSessionCompatApi18.setTransportControlFlags(this.mRccObj, playbackStateCompat.getActions());
                return;
            }
            if (Build.VERSION.SDK_INT < 14) {
                return;
            }
            MediaSessionCompatApi14.setTransportControlFlags(this.mRccObj, playbackStateCompat.getActions());
        }

        @Override
        public void setPlaybackToLocal(int n2) {
            if (this.mVolumeProvider != null) {
                this.mVolumeProvider.setCallback(null);
            }
            this.mVolumeType = 1;
            this.sendVolumeInfoChanged(new ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, 2, this.mAudioManager.getStreamMaxVolume(this.mLocalStream), this.mAudioManager.getStreamVolume(this.mLocalStream)));
        }

        @Override
        public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
            if (volumeProviderCompat == null) {
                throw new IllegalArgumentException("volumeProvider may not be null");
            }
            if (this.mVolumeProvider != null) {
                this.mVolumeProvider.setCallback(null);
            }
            this.mVolumeType = 2;
            this.mVolumeProvider = volumeProviderCompat;
            this.sendVolumeInfoChanged(new ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, this.mVolumeProvider.getVolumeControl(), this.mVolumeProvider.getMaxVolume(), this.mVolumeProvider.getCurrentVolume()));
            volumeProviderCompat.setCallback(this.mVolumeCallback);
        }

        @Override
        public void setQueue(List<QueueItem> list) {
            this.mQueue = list;
            this.sendQueue(list);
        }

        @Override
        public void setQueueTitle(CharSequence charSequence) {
            this.mQueueTitle = charSequence;
            this.sendQueueTitle(charSequence);
        }

        @Override
        public void setRatingType(int n2) {
            this.mRatingType = n2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void setSessionActivity(PendingIntent pendingIntent) {
            Object object = this.mLock;
            synchronized (object) {
                this.mSessionActivity = pendingIntent;
                return;
            }
        }

        void setVolumeTo(int n2, int n3) {
            if (this.mVolumeType == 2) {
                if (this.mVolumeProvider != null) {
                    this.mVolumeProvider.onSetVolumeTo(n2);
                }
                return;
            }
            this.mAudioManager.setStreamVolume(this.mLocalStream, n2, n3);
        }

        private static final class Command {
            public final String command;
            public final Bundle extras;
            public final ResultReceiver stub;

            public Command(String string2, Bundle bundle, ResultReceiver resultReceiver) {
                this.command = string2;
                this.extras = bundle;
                this.stub = resultReceiver;
            }
        }

        class MediaSessionStub
        extends IMediaSession.Stub {
            MediaSessionStub() {
            }

            @Override
            public void adjustVolume(int n2, int n3, String string2) {
                MediaSessionImplBase.this.adjustVolume(n2, n3);
            }

            @Override
            public void fastForward() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(16);
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Bundle getExtras() {
                Object object = MediaSessionImplBase.this.mLock;
                synchronized (object) {
                    return MediaSessionImplBase.this.mExtras;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public long getFlags() {
                Object object = MediaSessionImplBase.this.mLock;
                synchronized (object) {
                    return MediaSessionImplBase.this.mFlags;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public PendingIntent getLaunchPendingIntent() {
                Object object = MediaSessionImplBase.this.mLock;
                synchronized (object) {
                    return MediaSessionImplBase.this.mSessionActivity;
                }
            }

            @Override
            public MediaMetadataCompat getMetadata() {
                return MediaSessionImplBase.this.mMetadata;
            }

            @Override
            public String getPackageName() {
                return MediaSessionImplBase.this.mPackageName;
            }

            @Override
            public PlaybackStateCompat getPlaybackState() {
                return MediaSessionImplBase.this.getStateWithUpdatedPosition();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public List<QueueItem> getQueue() {
                Object object = MediaSessionImplBase.this.mLock;
                synchronized (object) {
                    return MediaSessionImplBase.this.mQueue;
                }
            }

            @Override
            public CharSequence getQueueTitle() {
                return MediaSessionImplBase.this.mQueueTitle;
            }

            @Override
            public int getRatingType() {
                return MediaSessionImplBase.this.mRatingType;
            }

            @Override
            public String getTag() {
                return MediaSessionImplBase.this.mTag;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParcelableVolumeInfo getVolumeAttributes() {
                Object object = MediaSessionImplBase.this.mLock;
                synchronized (object) {
                    int n2;
                    int n3;
                    int n4;
                    int n5 = MediaSessionImplBase.this.mVolumeType;
                    int n6 = MediaSessionImplBase.this.mLocalStream;
                    VolumeProviderCompat volumeProviderCompat = MediaSessionImplBase.this.mVolumeProvider;
                    if (n5 == 2) {
                        n4 = volumeProviderCompat.getVolumeControl();
                        n3 = volumeProviderCompat.getMaxVolume();
                        n2 = volumeProviderCompat.getCurrentVolume();
                    } else {
                        n4 = 2;
                        n3 = MediaSessionImplBase.this.mAudioManager.getStreamMaxVolume(n6);
                        n2 = MediaSessionImplBase.this.mAudioManager.getStreamVolume(n6);
                    }
                    return new ParcelableVolumeInfo(n5, n6, n4, n3, n2);
                }
            }

            @Override
            public boolean isTransportControlEnabled() {
                return (MediaSessionImplBase.this.mFlags & 2) != 0;
            }

            @Override
            public void next() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(14);
            }

            @Override
            public void pause() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(12);
            }

            @Override
            public void play() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(7);
            }

            @Override
            public void playFromMediaId(String string2, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(8, string2, bundle);
            }

            @Override
            public void playFromSearch(String string2, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(9, string2, bundle);
            }

            @Override
            public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(10, uri, bundle);
            }

            @Override
            public void prepare() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(3);
            }

            @Override
            public void prepareFromMediaId(String string2, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(4, string2, bundle);
            }

            @Override
            public void prepareFromSearch(String string2, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(5, string2, bundle);
            }

            @Override
            public void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(6, uri, bundle);
            }

            @Override
            public void previous() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(15);
            }

            @Override
            public void rate(RatingCompat ratingCompat) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(19, ratingCompat);
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                if (!MediaSessionImplBase.this.mDestroyed) {
                    MediaSessionImplBase.this.mControllerCallbacks.register((IInterface)iMediaControllerCallback);
                    return;
                }
                try {
                    iMediaControllerCallback.onSessionDestroyed();
                    return;
                }
                catch (Exception exception) {
                    return;
                }
            }

            @Override
            public void rewind() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(17);
            }

            @Override
            public void seekTo(long l2) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(18, l2);
            }

            @Override
            public void sendCommand(String string2, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                MediaSessionImplBase.this.postToHandler(1, new Command(string2, bundle, resultReceiverWrapper.mResultReceiver));
            }

            @Override
            public void sendCustomAction(String string2, Bundle bundle) throws RemoteException {
                MediaSessionImplBase.this.postToHandler(20, string2, bundle);
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public boolean sendMediaButton(KeyEvent keyEvent) {
                if ((MediaSessionImplBase.this.mFlags & 1) == 0) return false;
                boolean bl2 = true;
                if (!bl2) return bl2;
                MediaSessionImplBase.this.postToHandler(21, keyEvent);
                return bl2;
            }

            @Override
            public void setVolumeTo(int n2, int n3, String string2) {
                MediaSessionImplBase.this.setVolumeTo(n2, n3);
            }

            @Override
            public void skipToQueueItem(long l2) {
                MediaSessionImplBase.this.postToHandler(11, l2);
            }

            @Override
            public void stop() throws RemoteException {
                MediaSessionImplBase.this.postToHandler(13);
            }

            @Override
            public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                MediaSessionImplBase.this.mControllerCallbacks.unregister((IInterface)iMediaControllerCallback);
            }
        }

        private class MessageHandler
        extends Handler {
            private static final int KEYCODE_MEDIA_PAUSE = 127;
            private static final int KEYCODE_MEDIA_PLAY = 126;
            private static final int MSG_ADJUST_VOLUME = 2;
            private static final int MSG_COMMAND = 1;
            private static final int MSG_CUSTOM_ACTION = 20;
            private static final int MSG_FAST_FORWARD = 16;
            private static final int MSG_MEDIA_BUTTON = 21;
            private static final int MSG_NEXT = 14;
            private static final int MSG_PAUSE = 12;
            private static final int MSG_PLAY = 7;
            private static final int MSG_PLAY_MEDIA_ID = 8;
            private static final int MSG_PLAY_SEARCH = 9;
            private static final int MSG_PLAY_URI = 10;
            private static final int MSG_PREPARE = 3;
            private static final int MSG_PREPARE_MEDIA_ID = 4;
            private static final int MSG_PREPARE_SEARCH = 5;
            private static final int MSG_PREPARE_URI = 6;
            private static final int MSG_PREVIOUS = 15;
            private static final int MSG_RATE = 19;
            private static final int MSG_REWIND = 17;
            private static final int MSG_SEEK_TO = 18;
            private static final int MSG_SET_VOLUME = 22;
            private static final int MSG_SKIP_TO_ITEM = 11;
            private static final int MSG_STOP = 13;

            public MessageHandler(Looper looper) {
                super(looper);
            }

            /*
             * Enabled aggressive block sorting
             */
            private void onMediaButtonEvent(KeyEvent keyEvent, Callback callback) {
                boolean bl2;
                boolean bl3 = true;
                if (keyEvent == null) return;
                if (keyEvent.getAction() != 0) return;
                long l2 = MediaSessionImplBase.this.mState == null ? 0L : MediaSessionImplBase.this.mState.getActions();
                switch (keyEvent.getKeyCode()) {
                    default: {
                        return;
                    }
                    case 79: 
                    case 85: {
                        bl2 = MediaSessionImplBase.this.mState != null && MediaSessionImplBase.this.mState.getState() == 3;
                    }
                    case 126: {
                        if ((4L & l2) == 0L) return;
                        callback.onPlay();
                        return;
                    }
                    case 127: {
                        if ((2L & l2) == 0L) return;
                        callback.onPause();
                        return;
                    }
                    case 87: {
                        if ((0x20L & l2) == 0L) return;
                        callback.onSkipToNext();
                        return;
                    }
                    case 88: {
                        if ((0x10L & l2) == 0L) return;
                        callback.onSkipToPrevious();
                        return;
                    }
                    case 86: {
                        if ((1L & l2) == 0L) return;
                        callback.onStop();
                        return;
                    }
                    case 90: {
                        if ((0x40L & l2) == 0L) return;
                        callback.onFastForward();
                        return;
                    }
                    case 89: {
                        if ((8L & l2) == 0L) return;
                        callback.onRewind();
                        return;
                    }
                }
                boolean bl4 = (0x204L & l2) != 0L;
                if ((0x202L & l2) == 0L) {
                    bl3 = false;
                }
                if (bl2 && bl3) {
                    callback.onPause();
                    return;
                }
                if (bl2 || !bl4) {
                    return;
                }
                callback.onPlay();
            }

            /*
             * Enabled aggressive block sorting
             * Lifted jumps to return sites
             */
            public void handleMessage(Message object) {
                Callback callback = MediaSessionImplBase.this.mCallback;
                if (callback == null) {
                    return;
                }
                switch (object.what) {
                    default: {
                        return;
                    }
                    case 1: {
                        Command command = (Command)object.obj;
                        callback.onCommand(command.command, command.extras, command.stub);
                        return;
                    }
                    case 21: {
                        KeyEvent keyEvent = (KeyEvent)object.obj;
                        Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                        intent.putExtra("android.intent.extra.KEY_EVENT", (Parcelable)keyEvent);
                        if (callback.onMediaButtonEvent(intent)) return;
                        this.onMediaButtonEvent(keyEvent, callback);
                        return;
                    }
                    case 3: {
                        callback.onPrepare();
                        return;
                    }
                    case 4: {
                        callback.onPrepareFromMediaId((String)object.obj, object.getData());
                        return;
                    }
                    case 5: {
                        callback.onPrepareFromSearch((String)object.obj, object.getData());
                        return;
                    }
                    case 6: {
                        callback.onPrepareFromUri((Uri)object.obj, object.getData());
                        return;
                    }
                    case 7: {
                        callback.onPlay();
                        return;
                    }
                    case 8: {
                        callback.onPlayFromMediaId((String)object.obj, object.getData());
                        return;
                    }
                    case 9: {
                        callback.onPlayFromSearch((String)object.obj, object.getData());
                        return;
                    }
                    case 10: {
                        callback.onPlayFromUri((Uri)object.obj, object.getData());
                        return;
                    }
                    case 11: {
                        callback.onSkipToQueueItem((Long)object.obj);
                        return;
                    }
                    case 12: {
                        callback.onPause();
                        return;
                    }
                    case 13: {
                        callback.onStop();
                        return;
                    }
                    case 14: {
                        callback.onSkipToNext();
                        return;
                    }
                    case 15: {
                        callback.onSkipToPrevious();
                        return;
                    }
                    case 16: {
                        callback.onFastForward();
                        return;
                    }
                    case 17: {
                        callback.onRewind();
                        return;
                    }
                    case 18: {
                        callback.onSeekTo((Long)object.obj);
                        return;
                    }
                    case 19: {
                        callback.onSetRating((RatingCompat)object.obj);
                        return;
                    }
                    case 20: {
                        callback.onCustomAction((String)object.obj, object.getData());
                        return;
                    }
                    case 2: {
                        MediaSessionImplBase.this.adjustVolume((Integer)object.obj, 0);
                        return;
                    }
                    case 22: 
                }
                MediaSessionImplBase.this.setVolumeTo((Integer)object.obj, 0);
            }

            public void post(int n2) {
                this.post(n2, null);
            }

            public void post(int n2, Object object) {
                this.obtainMessage(n2, object).sendToTarget();
            }

            public void post(int n2, Object object, int n3) {
                this.obtainMessage(n2, n3, 0, object).sendToTarget();
            }

            public void post(int n2, Object object, Bundle bundle) {
                object = this.obtainMessage(n2, object);
                object.setData(bundle);
                object.sendToTarget();
            }
        }
    }

    public static interface OnActiveChangeListener {
        public void onActiveChanged();
    }

    public static final class QueueItem
    implements Parcelable {
        public static final Parcelable.Creator<QueueItem> CREATOR = new Parcelable.Creator<QueueItem>(){

            public QueueItem createFromParcel(Parcel parcel) {
                return new QueueItem(parcel);
            }

            public QueueItem[] newArray(int n2) {
                return new QueueItem[n2];
            }
        };
        public static final int UNKNOWN_ID = -1;
        private final MediaDescriptionCompat mDescription;
        private final long mId;
        private Object mItem;

        QueueItem(Parcel parcel) {
            this.mDescription = (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
            this.mId = parcel.readLong();
        }

        public QueueItem(MediaDescriptionCompat mediaDescriptionCompat, long l2) {
            this(null, mediaDescriptionCompat, l2);
        }

        private QueueItem(Object object, MediaDescriptionCompat mediaDescriptionCompat, long l2) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("Description cannot be null.");
            }
            if (l2 == -1L) {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            }
            this.mDescription = mediaDescriptionCompat;
            this.mId = l2;
            this.mItem = object;
        }

        public static QueueItem fromQueueItem(Object object) {
            if (object == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            return new QueueItem(object, MediaDescriptionCompat.fromMediaDescription(MediaSessionCompatApi21.QueueItem.getDescription(object)), MediaSessionCompatApi21.QueueItem.getQueueId(object));
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public static List<QueueItem> fromQueueItemList(List<?> list) {
            if (list == null) return null;
            if (Build.VERSION.SDK_INT < 21) {
                return null;
            }
            ArrayList<QueueItem> arrayList = new ArrayList<QueueItem>();
            Iterator<QueueItem> iterator = list.iterator();
            while (true) {
                list = arrayList;
                if (!iterator.hasNext()) return list;
                arrayList.add(QueueItem.fromQueueItem(iterator.next()));
            }
        }

        @Deprecated
        public static QueueItem obtain(Object object) {
            return QueueItem.fromQueueItem(object);
        }

        public int describeContents() {
            return 0;
        }

        public MediaDescriptionCompat getDescription() {
            return this.mDescription;
        }

        public long getQueueId() {
            return this.mId;
        }

        public Object getQueueItem() {
            if (this.mItem != null || Build.VERSION.SDK_INT < 21) {
                return this.mItem;
            }
            this.mItem = MediaSessionCompatApi21.QueueItem.createItem(this.mDescription.getMediaDescription(), this.mId);
            return this.mItem;
        }

        public String toString() {
            return "MediaSession.QueueItem {Description=" + this.mDescription + ", Id=" + this.mId + " }";
        }

        public void writeToParcel(Parcel parcel, int n2) {
            this.mDescription.writeToParcel(parcel, n2);
            parcel.writeLong(this.mId);
        }
    }

    static final class ResultReceiverWrapper
    implements Parcelable {
        public static final Parcelable.Creator<ResultReceiverWrapper> CREATOR = new Parcelable.Creator<ResultReceiverWrapper>(){

            public ResultReceiverWrapper createFromParcel(Parcel parcel) {
                return new ResultReceiverWrapper(parcel);
            }

            public ResultReceiverWrapper[] newArray(int n2) {
                return new ResultReceiverWrapper[n2];
            }
        };
        private ResultReceiver mResultReceiver;

        ResultReceiverWrapper(Parcel parcel) {
            this.mResultReceiver = (ResultReceiver)ResultReceiver.CREATOR.createFromParcel(parcel);
        }

        public ResultReceiverWrapper(ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n2) {
            this.mResultReceiver.writeToParcel(parcel, n2);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SessionFlags {
    }

    public static final class Token
    implements Parcelable {
        public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator<Token>(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public Token createFromParcel(Parcel parcel) {
                if (Build.VERSION.SDK_INT >= 21) {
                    parcel = parcel.readParcelable(null);
                    return new Token(parcel);
                }
                parcel = parcel.readStrongBinder();
                return new Token(parcel);
            }

            public Token[] newArray(int n2) {
                return new Token[n2];
            }
        };
        private final Object mInner;

        Token(Object object) {
            this.mInner = object;
        }

        public static Token fromToken(Object object) {
            if (object == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            return new Token(MediaSessionCompatApi21.verifyToken(object));
        }

        public int describeContents() {
            return 0;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block6: {
                block7: {
                    block5: {
                        if (this == object) break block5;
                        if (!(object instanceof Token)) {
                            return false;
                        }
                        object = (Token)object;
                        if (this.mInner != null) break block6;
                        if (((Token)object).mInner != null) break block7;
                    }
                    return true;
                }
                return false;
            }
            if (((Token)object).mInner == null) {
                return false;
            }
            return this.mInner.equals(((Token)object).mInner);
        }

        public Object getToken() {
            return this.mInner;
        }

        public int hashCode() {
            if (this.mInner == null) {
                return 0;
            }
            return this.mInner.hashCode();
        }

        public void writeToParcel(Parcel parcel, int n2) {
            if (Build.VERSION.SDK_INT >= 21) {
                parcel.writeParcelable((Parcelable)this.mInner, n2);
                return;
            }
            parcel.writeStrongBinder((IBinder)this.mInner);
        }
    }
}

