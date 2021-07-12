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
 *  android.util.Log
 */
package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompatApi21;
import android.support.v4.media.RatingCompat;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

public final class MediaMetadataCompat
implements Parcelable {
    public static final Parcelable.Creator<MediaMetadataCompat> CREATOR;
    static final ArrayMap<String, Integer> METADATA_KEYS_TYPE;
    public static final String METADATA_KEY_ALBUM = "android.media.metadata.ALBUM";
    public static final String METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART";
    public static final String METADATA_KEY_ALBUM_ARTIST = "android.media.metadata.ALBUM_ARTIST";
    public static final String METADATA_KEY_ALBUM_ART_URI = "android.media.metadata.ALBUM_ART_URI";
    public static final String METADATA_KEY_ART = "android.media.metadata.ART";
    public static final String METADATA_KEY_ARTIST = "android.media.metadata.ARTIST";
    public static final String METADATA_KEY_ART_URI = "android.media.metadata.ART_URI";
    public static final String METADATA_KEY_AUTHOR = "android.media.metadata.AUTHOR";
    public static final String METADATA_KEY_BT_FOLDER_TYPE = "android.media.metadata.BT_FOLDER_TYPE";
    public static final String METADATA_KEY_COMPILATION = "android.media.metadata.COMPILATION";
    public static final String METADATA_KEY_COMPOSER = "android.media.metadata.COMPOSER";
    public static final String METADATA_KEY_DATE = "android.media.metadata.DATE";
    public static final String METADATA_KEY_DISC_NUMBER = "android.media.metadata.DISC_NUMBER";
    public static final String METADATA_KEY_DISPLAY_DESCRIPTION = "android.media.metadata.DISPLAY_DESCRIPTION";
    public static final String METADATA_KEY_DISPLAY_ICON = "android.media.metadata.DISPLAY_ICON";
    public static final String METADATA_KEY_DISPLAY_ICON_URI = "android.media.metadata.DISPLAY_ICON_URI";
    public static final String METADATA_KEY_DISPLAY_SUBTITLE = "android.media.metadata.DISPLAY_SUBTITLE";
    public static final String METADATA_KEY_DISPLAY_TITLE = "android.media.metadata.DISPLAY_TITLE";
    public static final String METADATA_KEY_DURATION = "android.media.metadata.DURATION";
    public static final String METADATA_KEY_GENRE = "android.media.metadata.GENRE";
    public static final String METADATA_KEY_MEDIA_ID = "android.media.metadata.MEDIA_ID";
    public static final String METADATA_KEY_MEDIA_URI = "android.media.metadata.MEDIA_URI";
    public static final String METADATA_KEY_NUM_TRACKS = "android.media.metadata.NUM_TRACKS";
    public static final String METADATA_KEY_RATING = "android.media.metadata.RATING";
    public static final String METADATA_KEY_TITLE = "android.media.metadata.TITLE";
    public static final String METADATA_KEY_TRACK_NUMBER = "android.media.metadata.TRACK_NUMBER";
    public static final String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";
    public static final String METADATA_KEY_WRITER = "android.media.metadata.WRITER";
    public static final String METADATA_KEY_YEAR = "android.media.metadata.YEAR";
    static final int METADATA_TYPE_BITMAP = 2;
    static final int METADATA_TYPE_LONG = 0;
    static final int METADATA_TYPE_RATING = 3;
    static final int METADATA_TYPE_TEXT = 1;
    private static final String[] PREFERRED_BITMAP_ORDER;
    private static final String[] PREFERRED_DESCRIPTION_ORDER;
    private static final String[] PREFERRED_URI_ORDER;
    private static final String TAG = "MediaMetadata";
    final Bundle mBundle;
    private MediaDescriptionCompat mDescription;
    private Object mMetadataObj;

    static {
        METADATA_KEYS_TYPE = new ArrayMap();
        METADATA_KEYS_TYPE.put(METADATA_KEY_TITLE, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ARTIST, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DURATION, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_AUTHOR, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_WRITER, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMPOSER, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMPILATION, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DATE, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_YEAR, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_GENRE, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_TRACK_NUMBER, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_NUM_TRACKS, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISC_NUMBER, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ARTIST, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ART, 2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ART_URI, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ART, 2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ART_URI, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_USER_RATING, 3);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RATING, 3);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_TITLE, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_SUBTITLE, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_DESCRIPTION, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_ICON, 2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_ICON_URI, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_MEDIA_ID, 1);
        METADATA_KEYS_TYPE.put(METADATA_KEY_BT_FOLDER_TYPE, 0);
        METADATA_KEYS_TYPE.put(METADATA_KEY_MEDIA_URI, 1);
        PREFERRED_DESCRIPTION_ORDER = new String[]{METADATA_KEY_TITLE, METADATA_KEY_ARTIST, METADATA_KEY_ALBUM, METADATA_KEY_ALBUM_ARTIST, METADATA_KEY_WRITER, METADATA_KEY_AUTHOR, METADATA_KEY_COMPOSER};
        PREFERRED_BITMAP_ORDER = new String[]{METADATA_KEY_DISPLAY_ICON, METADATA_KEY_ART, METADATA_KEY_ALBUM_ART};
        PREFERRED_URI_ORDER = new String[]{METADATA_KEY_DISPLAY_ICON_URI, METADATA_KEY_ART_URI, METADATA_KEY_ALBUM_ART_URI};
        CREATOR = new Parcelable.Creator<MediaMetadataCompat>(){

            public MediaMetadataCompat createFromParcel(Parcel parcel) {
                return new MediaMetadataCompat(parcel);
            }

            public MediaMetadataCompat[] newArray(int n2) {
                return new MediaMetadataCompat[n2];
            }
        };
    }

    MediaMetadataCompat(Bundle bundle) {
        this.mBundle = new Bundle(bundle);
    }

    MediaMetadataCompat(Parcel parcel) {
        this.mBundle = parcel.readBundle();
    }

    public static MediaMetadataCompat fromMediaMetadata(Object object) {
        if (object == null || Build.VERSION.SDK_INT < 21) {
            return null;
        }
        Parcel parcel = Parcel.obtain();
        MediaMetadataCompatApi21.writeToParcel(object, parcel, 0);
        parcel.setDataPosition(0);
        MediaMetadataCompat mediaMetadataCompat = (MediaMetadataCompat)CREATOR.createFromParcel(parcel);
        parcel.recycle();
        mediaMetadataCompat.mMetadataObj = object;
        return mediaMetadataCompat;
    }

    public boolean containsKey(String string2) {
        return this.mBundle.containsKey(string2);
    }

    public int describeContents() {
        return 0;
    }

    public Bitmap getBitmap(String string2) {
        try {
            string2 = (Bitmap)this.mBundle.getParcelable(string2);
            return string2;
        }
        catch (Exception exception) {
            Log.w((String)TAG, (String)"Failed to retrieve a key as Bitmap.", (Throwable)exception);
            return null;
        }
    }

    public Bundle getBundle() {
        return this.mBundle;
    }

    /*
     * Enabled aggressive block sorting
     */
    public MediaDescriptionCompat getDescription() {
        int n2;
        if (this.mDescription != null) {
            return this.mDescription;
        }
        String string2 = this.getString(METADATA_KEY_MEDIA_ID);
        CharSequence[] charSequenceArray = new CharSequence[3];
        String string3 = null;
        String string4 = null;
        CharSequence charSequence = this.getText(METADATA_KEY_DISPLAY_TITLE);
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            charSequenceArray[0] = charSequence;
            charSequenceArray[1] = this.getText(METADATA_KEY_DISPLAY_SUBTITLE);
            charSequenceArray[2] = this.getText(METADATA_KEY_DISPLAY_DESCRIPTION);
        } else {
            int n3 = 0;
            for (n2 = 0; n3 < charSequenceArray.length && n2 < PREFERRED_DESCRIPTION_ORDER.length; ++n2) {
                charSequence = this.getText(PREFERRED_DESCRIPTION_ORDER[n2]);
                int n4 = n3;
                if (!TextUtils.isEmpty((CharSequence)charSequence)) {
                    charSequenceArray[n3] = charSequence;
                    n4 = n3 + 1;
                }
                n3 = n4;
            }
        }
        n2 = 0;
        while (true) {
            charSequence = string3;
            if (n2 >= PREFERRED_BITMAP_ORDER.length || (charSequence = this.getBitmap(PREFERRED_BITMAP_ORDER[n2])) != null) break;
            ++n2;
        }
        n2 = 0;
        while (true) {
            block13: {
                block12: {
                    string3 = string4;
                    if (n2 >= PREFERRED_URI_ORDER.length) break block12;
                    string3 = this.getString(PREFERRED_URI_ORDER[n2]);
                    if (TextUtils.isEmpty((CharSequence)string3)) break block13;
                    string3 = Uri.parse((String)string3);
                }
                string4 = null;
                Object object = this.getString(METADATA_KEY_MEDIA_URI);
                if (!TextUtils.isEmpty((CharSequence)object)) {
                    string4 = Uri.parse((String)object);
                }
                object = new MediaDescriptionCompat.Builder();
                ((MediaDescriptionCompat.Builder)object).setMediaId(string2);
                ((MediaDescriptionCompat.Builder)object).setTitle(charSequenceArray[0]);
                ((MediaDescriptionCompat.Builder)object).setSubtitle(charSequenceArray[1]);
                ((MediaDescriptionCompat.Builder)object).setDescription(charSequenceArray[2]);
                ((MediaDescriptionCompat.Builder)object).setIconBitmap((Bitmap)charSequence);
                ((MediaDescriptionCompat.Builder)object).setIconUri((Uri)string3);
                ((MediaDescriptionCompat.Builder)object).setMediaUri((Uri)string4);
                if (this.mBundle.containsKey(METADATA_KEY_BT_FOLDER_TYPE)) {
                    charSequence = new Bundle();
                    charSequence.putLong("android.media.extra.BT_FOLDER_TYPE", this.getLong(METADATA_KEY_BT_FOLDER_TYPE));
                    ((MediaDescriptionCompat.Builder)object).setExtras((Bundle)charSequence);
                }
                this.mDescription = ((MediaDescriptionCompat.Builder)object).build();
                return this.mDescription;
            }
            ++n2;
        }
    }

    public long getLong(String string2) {
        return this.mBundle.getLong(string2, 0L);
    }

    public Object getMediaMetadata() {
        if (this.mMetadataObj != null || Build.VERSION.SDK_INT < 21) {
            return this.mMetadataObj;
        }
        Parcel parcel = Parcel.obtain();
        this.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        this.mMetadataObj = MediaMetadataCompatApi21.createFromParcel(parcel);
        parcel.recycle();
        return this.mMetadataObj;
    }

    public RatingCompat getRating(String object) {
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                return RatingCompat.fromRating(this.mBundle.getParcelable((String)object));
            }
            object = (RatingCompat)this.mBundle.getParcelable((String)object);
            return object;
        }
        catch (Exception exception) {
            Log.w((String)TAG, (String)"Failed to retrieve a key as Rating.", (Throwable)exception);
            return null;
        }
    }

    public String getString(String charSequence) {
        if ((charSequence = this.mBundle.getCharSequence((String)charSequence)) != null) {
            return charSequence.toString();
        }
        return null;
    }

    public CharSequence getText(String string2) {
        return this.mBundle.getCharSequence(string2);
    }

    public Set<String> keySet() {
        return this.mBundle.keySet();
    }

    public int size() {
        return this.mBundle.size();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeBundle(this.mBundle);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BitmapKey {
    }

    public static final class Builder {
        private final Bundle mBundle;

        public Builder() {
            this.mBundle = new Bundle();
        }

        public Builder(MediaMetadataCompat mediaMetadataCompat) {
            this.mBundle = new Bundle(mediaMetadataCompat.mBundle);
        }

        public Builder(MediaMetadataCompat object, int n2) {
            this((MediaMetadataCompat)object);
            for (String string2 : this.mBundle.keySet()) {
                Object object2 = this.mBundle.get(string2);
                if (object2 == null || !(object2 instanceof Bitmap)) continue;
                if ((object2 = (Bitmap)object2).getHeight() > n2 || object2.getWidth() > n2) {
                    this.putBitmap(string2, this.scaleBitmap((Bitmap)object2, n2));
                    continue;
                }
                if (Build.VERSION.SDK_INT < 14 || !string2.equals(MediaMetadataCompat.METADATA_KEY_ART) && !string2.equals(MediaMetadataCompat.METADATA_KEY_ALBUM_ART)) continue;
                this.putBitmap(string2, object2.copy(object2.getConfig(), false));
            }
        }

        private Bitmap scaleBitmap(Bitmap bitmap, int n2) {
            float f2 = n2;
            f2 = Math.min(f2 / (float)bitmap.getWidth(), f2 / (float)bitmap.getHeight());
            n2 = (int)((float)bitmap.getHeight() * f2);
            return Bitmap.createScaledBitmap((Bitmap)bitmap, (int)((int)((float)bitmap.getWidth() * f2)), (int)n2, (boolean)true);
        }

        public MediaMetadataCompat build() {
            return new MediaMetadataCompat(this.mBundle);
        }

        public Builder putBitmap(String string2, Bitmap bitmap) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 2) {
                throw new IllegalArgumentException("The " + string2 + " key cannot be used to put a Bitmap");
            }
            this.mBundle.putParcelable(string2, (Parcelable)bitmap);
            return this;
        }

        public Builder putLong(String string2, long l2) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 0) {
                throw new IllegalArgumentException("The " + string2 + " key cannot be used to put a long");
            }
            this.mBundle.putLong(string2, l2);
            return this;
        }

        public Builder putRating(String string2, RatingCompat ratingCompat) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 3) {
                throw new IllegalArgumentException("The " + string2 + " key cannot be used to put a Rating");
            }
            if (Build.VERSION.SDK_INT >= 19) {
                this.mBundle.putParcelable(string2, (Parcelable)ratingCompat.getRating());
                return this;
            }
            this.mBundle.putParcelable(string2, (Parcelable)ratingCompat);
            return this;
        }

        public Builder putString(String string2, String string3) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 1) {
                throw new IllegalArgumentException("The " + string2 + " key cannot be used to put a String");
            }
            this.mBundle.putCharSequence(string2, (CharSequence)string3);
            return this;
        }

        public Builder putText(String string2, CharSequence charSequence) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 1) {
                throw new IllegalArgumentException("The " + string2 + " key cannot be used to put a CharSequence");
            }
            this.mBundle.putCharSequence(string2, charSequence);
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LongKey {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RatingKey {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TextKey {
    }
}

