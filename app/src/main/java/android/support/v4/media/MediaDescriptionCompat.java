/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaDescriptionCompatApi21;
import android.support.v4.media.MediaDescriptionCompatApi23;
import android.text.TextUtils;

public final class MediaDescriptionCompat
implements Parcelable {
    public static final long BT_FOLDER_TYPE_ALBUMS = 2L;
    public static final long BT_FOLDER_TYPE_ARTISTS = 3L;
    public static final long BT_FOLDER_TYPE_GENRES = 4L;
    public static final long BT_FOLDER_TYPE_MIXED = 0L;
    public static final long BT_FOLDER_TYPE_PLAYLISTS = 5L;
    public static final long BT_FOLDER_TYPE_TITLES = 1L;
    public static final long BT_FOLDER_TYPE_YEARS = 6L;
    public static final Parcelable.Creator<MediaDescriptionCompat> CREATOR = new Parcelable.Creator<MediaDescriptionCompat>(){

        public MediaDescriptionCompat createFromParcel(Parcel parcel) {
            if (Build.VERSION.SDK_INT < 21) {
                return new MediaDescriptionCompat(parcel);
            }
            return MediaDescriptionCompat.fromMediaDescription(MediaDescriptionCompatApi21.fromParcel(parcel));
        }

        public MediaDescriptionCompat[] newArray(int n2) {
            return new MediaDescriptionCompat[n2];
        }
    };
    public static final String DESCRIPTION_KEY_MEDIA_URI = "android.support.v4.media.description.MEDIA_URI";
    public static final String DESCRIPTION_KEY_NULL_BUNDLE_FLAG = "android.support.v4.media.description.NULL_BUNDLE_FLAG";
    public static final String EXTRA_BT_FOLDER_TYPE = "android.media.extra.BT_FOLDER_TYPE";
    private final CharSequence mDescription;
    private Object mDescriptionObj;
    private final Bundle mExtras;
    private final Bitmap mIcon;
    private final Uri mIconUri;
    private final String mMediaId;
    private final Uri mMediaUri;
    private final CharSequence mSubtitle;
    private final CharSequence mTitle;

    MediaDescriptionCompat(Parcel parcel) {
        this.mMediaId = parcel.readString();
        this.mTitle = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtitle = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mDescription = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mIcon = (Bitmap)parcel.readParcelable(null);
        this.mIconUri = (Uri)parcel.readParcelable(null);
        this.mExtras = parcel.readBundle();
        this.mMediaUri = (Uri)parcel.readParcelable(null);
    }

    MediaDescriptionCompat(String string2, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.mMediaId = string2;
        this.mTitle = charSequence;
        this.mSubtitle = charSequence2;
        this.mDescription = charSequence3;
        this.mIcon = bitmap;
        this.mIconUri = uri;
        this.mExtras = bundle;
        this.mMediaUri = uri2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static MediaDescriptionCompat fromMediaDescription(Object object) {
        void var1_4;
        if (object == null || Build.VERSION.SDK_INT < 21) {
            return null;
        }
        Builder builder = new Builder();
        builder.setMediaId(MediaDescriptionCompatApi21.getMediaId(object));
        builder.setTitle(MediaDescriptionCompatApi21.getTitle(object));
        builder.setSubtitle(MediaDescriptionCompatApi21.getSubtitle(object));
        builder.setDescription(MediaDescriptionCompatApi21.getDescription(object));
        builder.setIconBitmap(MediaDescriptionCompatApi21.getIconBitmap(object));
        builder.setIconUri(MediaDescriptionCompatApi21.getIconUri(object));
        Bundle bundle = MediaDescriptionCompatApi21.getExtras(object);
        if (bundle == null) {
            Object var1_3 = null;
        } else {
            Uri uri = (Uri)bundle.getParcelable(DESCRIPTION_KEY_MEDIA_URI);
        }
        Bundle bundle2 = bundle;
        if (var1_4 != null) {
            if (bundle.containsKey(DESCRIPTION_KEY_NULL_BUNDLE_FLAG) && bundle.size() == 2) {
                bundle2 = null;
            } else {
                bundle.remove(DESCRIPTION_KEY_MEDIA_URI);
                bundle.remove(DESCRIPTION_KEY_NULL_BUNDLE_FLAG);
                bundle2 = bundle;
            }
        }
        builder.setExtras(bundle2);
        if (var1_4 != null) {
            builder.setMediaUri((Uri)var1_4);
        } else if (Build.VERSION.SDK_INT >= 23) {
            builder.setMediaUri(MediaDescriptionCompatApi23.getMediaUri(object));
        }
        MediaDescriptionCompat mediaDescriptionCompat = builder.build();
        mediaDescriptionCompat.mDescriptionObj = object;
        return mediaDescriptionCompat;
    }

    public int describeContents() {
        return 0;
    }

    @Nullable
    public CharSequence getDescription() {
        return this.mDescription;
    }

    @Nullable
    public Bundle getExtras() {
        return this.mExtras;
    }

    @Nullable
    public Bitmap getIconBitmap() {
        return this.mIcon;
    }

    @Nullable
    public Uri getIconUri() {
        return this.mIconUri;
    }

    public Object getMediaDescription() {
        Bundle bundle;
        if (this.mDescriptionObj != null || Build.VERSION.SDK_INT < 21) {
            return this.mDescriptionObj;
        }
        Object object = MediaDescriptionCompatApi21.Builder.newInstance();
        MediaDescriptionCompatApi21.Builder.setMediaId(object, this.mMediaId);
        MediaDescriptionCompatApi21.Builder.setTitle(object, this.mTitle);
        MediaDescriptionCompatApi21.Builder.setSubtitle(object, this.mSubtitle);
        MediaDescriptionCompatApi21.Builder.setDescription(object, this.mDescription);
        MediaDescriptionCompatApi21.Builder.setIconBitmap(object, this.mIcon);
        MediaDescriptionCompatApi21.Builder.setIconUri(object, this.mIconUri);
        Bundle bundle2 = bundle = this.mExtras;
        if (Build.VERSION.SDK_INT < 23) {
            bundle2 = bundle;
            if (this.mMediaUri != null) {
                bundle2 = bundle;
                if (bundle == null) {
                    bundle2 = new Bundle();
                    bundle2.putBoolean(DESCRIPTION_KEY_NULL_BUNDLE_FLAG, true);
                }
                bundle2.putParcelable(DESCRIPTION_KEY_MEDIA_URI, (Parcelable)this.mMediaUri);
            }
        }
        MediaDescriptionCompatApi21.Builder.setExtras(object, bundle2);
        if (Build.VERSION.SDK_INT >= 23) {
            MediaDescriptionCompatApi23.Builder.setMediaUri(object, this.mMediaUri);
        }
        this.mDescriptionObj = MediaDescriptionCompatApi21.Builder.build(object);
        return this.mDescriptionObj;
    }

    @Nullable
    public String getMediaId() {
        return this.mMediaId;
    }

    @Nullable
    public Uri getMediaUri() {
        return this.mMediaUri;
    }

    @Nullable
    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    @Nullable
    public CharSequence getTitle() {
        return this.mTitle;
    }

    public String toString() {
        return this.mTitle + ", " + this.mSubtitle + ", " + this.mDescription;
    }

    public void writeToParcel(Parcel parcel, int n2) {
        if (Build.VERSION.SDK_INT < 21) {
            parcel.writeString(this.mMediaId);
            TextUtils.writeToParcel((CharSequence)this.mTitle, (Parcel)parcel, (int)n2);
            TextUtils.writeToParcel((CharSequence)this.mSubtitle, (Parcel)parcel, (int)n2);
            TextUtils.writeToParcel((CharSequence)this.mDescription, (Parcel)parcel, (int)n2);
            parcel.writeParcelable((Parcelable)this.mIcon, n2);
            parcel.writeParcelable((Parcelable)this.mIconUri, n2);
            parcel.writeBundle(this.mExtras);
            parcel.writeParcelable((Parcelable)this.mMediaUri, n2);
            return;
        }
        MediaDescriptionCompatApi21.writeToParcel(this.getMediaDescription(), parcel, n2);
    }

    public static final class Builder {
        private CharSequence mDescription;
        private Bundle mExtras;
        private Bitmap mIcon;
        private Uri mIconUri;
        private String mMediaId;
        private Uri mMediaUri;
        private CharSequence mSubtitle;
        private CharSequence mTitle;

        public MediaDescriptionCompat build() {
            return new MediaDescriptionCompat(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, this.mIcon, this.mIconUri, this.mExtras, this.mMediaUri);
        }

        public Builder setDescription(@Nullable CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public Builder setExtras(@Nullable Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setIconBitmap(@Nullable Bitmap bitmap) {
            this.mIcon = bitmap;
            return this;
        }

        public Builder setIconUri(@Nullable Uri uri) {
            this.mIconUri = uri;
            return this;
        }

        public Builder setMediaId(@Nullable String string2) {
            this.mMediaId = string2;
            return this;
        }

        public Builder setMediaUri(@Nullable Uri uri) {
            this.mMediaUri = uri;
            return this;
        }

        public Builder setSubtitle(@Nullable CharSequence charSequence) {
            this.mSubtitle = charSequence;
            return this;
        }

        public Builder setTitle(@Nullable CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }
    }
}

