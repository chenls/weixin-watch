/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package android.support.v4.media;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.RatingCompatKitkat;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class RatingCompat
implements Parcelable {
    public static final Parcelable.Creator<RatingCompat> CREATOR = new Parcelable.Creator<RatingCompat>(){

        public RatingCompat createFromParcel(Parcel parcel) {
            return new RatingCompat(parcel.readInt(), parcel.readFloat());
        }

        public RatingCompat[] newArray(int n2) {
            return new RatingCompat[n2];
        }
    };
    public static final int RATING_3_STARS = 3;
    public static final int RATING_4_STARS = 4;
    public static final int RATING_5_STARS = 5;
    public static final int RATING_HEART = 1;
    public static final int RATING_NONE = 0;
    private static final float RATING_NOT_RATED = -1.0f;
    public static final int RATING_PERCENTAGE = 6;
    public static final int RATING_THUMB_UP_DOWN = 2;
    private static final String TAG = "Rating";
    private Object mRatingObj;
    private final int mRatingStyle;
    private final float mRatingValue;

    RatingCompat(int n2, float f2) {
        this.mRatingStyle = n2;
        this.mRatingValue = f2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static RatingCompat fromRating(Object object) {
        RatingCompat ratingCompat;
        if (object == null || Build.VERSION.SDK_INT < 19) {
            return null;
        }
        int n2 = RatingCompatKitkat.getRatingStyle(object);
        if (RatingCompatKitkat.isRated(object)) {
            switch (n2) {
                default: {
                    return null;
                }
                case 1: {
                    ratingCompat = RatingCompat.newHeartRating(RatingCompatKitkat.hasHeart(object));
                    break;
                }
                case 2: {
                    ratingCompat = RatingCompat.newThumbRating(RatingCompatKitkat.isThumbUp(object));
                    break;
                }
                case 3: 
                case 4: 
                case 5: {
                    ratingCompat = RatingCompat.newStarRating(n2, RatingCompatKitkat.getStarRating(object));
                    break;
                }
                case 6: {
                    ratingCompat = RatingCompat.newPercentageRating(RatingCompatKitkat.getPercentRating(object));
                    break;
                }
            }
        } else {
            ratingCompat = RatingCompat.newUnratedRating(n2);
        }
        ratingCompat.mRatingObj = object;
        return ratingCompat;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static RatingCompat newHeartRating(boolean bl2) {
        float f2;
        if (bl2) {
            f2 = 1.0f;
            return new RatingCompat(1, f2);
        }
        f2 = 0.0f;
        return new RatingCompat(1, f2);
    }

    public static RatingCompat newPercentageRating(float f2) {
        if (f2 < 0.0f || f2 > 100.0f) {
            Log.e((String)TAG, (String)"Invalid percentage-based rating value");
            return null;
        }
        return new RatingCompat(6, f2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static RatingCompat newStarRating(int n2, float f2) {
        float f3;
        switch (n2) {
            default: {
                Log.e((String)TAG, (String)("Invalid rating style (" + n2 + ") for a star rating"));
                return null;
            }
            case 3: {
                f3 = 3.0f;
                break;
            }
            case 4: {
                f3 = 4.0f;
                break;
            }
            case 5: {
                f3 = 5.0f;
            }
        }
        if (f2 < 0.0f || f2 > f3) {
            Log.e((String)TAG, (String)"Trying to set out of range star-based rating");
            return null;
        }
        return new RatingCompat(n2, f2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static RatingCompat newThumbRating(boolean bl2) {
        float f2;
        if (bl2) {
            f2 = 1.0f;
            return new RatingCompat(2, f2);
        }
        f2 = 0.0f;
        return new RatingCompat(2, f2);
    }

    public static RatingCompat newUnratedRating(int n2) {
        switch (n2) {
            default: {
                return null;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
        }
        return new RatingCompat(n2, -1.0f);
    }

    public int describeContents() {
        return this.mRatingStyle;
    }

    public float getPercentRating() {
        if (this.mRatingStyle != 6 || !this.isRated()) {
            return -1.0f;
        }
        return this.mRatingValue;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object getRating() {
        if (this.mRatingObj != null || Build.VERSION.SDK_INT < 19) {
            return this.mRatingObj;
        }
        if (this.isRated()) {
            switch (this.mRatingStyle) {
                default: {
                    return null;
                }
                case 1: {
                    this.mRatingObj = RatingCompatKitkat.newHeartRating(this.hasHeart());
                    return this.mRatingObj;
                }
                case 2: {
                    this.mRatingObj = RatingCompatKitkat.newThumbRating(this.isThumbUp());
                    return this.mRatingObj;
                }
                case 3: 
                case 4: 
                case 5: {
                    this.mRatingObj = RatingCompatKitkat.newStarRating(this.mRatingStyle, this.getStarRating());
                    return this.mRatingObj;
                }
                case 6: 
            }
            this.mRatingObj = RatingCompatKitkat.newPercentageRating(this.getPercentRating());
            return null;
        }
        this.mRatingObj = RatingCompatKitkat.newUnratedRating(this.mRatingStyle);
        return this.mRatingObj;
    }

    public int getRatingStyle() {
        return this.mRatingStyle;
    }

    /*
     * Enabled aggressive block sorting
     */
    public float getStarRating() {
        switch (this.mRatingStyle) {
            default: {
                return -1.0f;
            }
            case 3: 
            case 4: 
            case 5: {
                if (!this.isRated()) return -1.0f;
                return this.mRatingValue;
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean hasHeart() {
        boolean bl2 = true;
        if (this.mRatingStyle != 1) {
            return false;
        }
        if (this.mRatingValue != 1.0f) return false;
        return bl2;
    }

    public boolean isRated() {
        return this.mRatingValue >= 0.0f;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isThumbUp() {
        return this.mRatingStyle == 2 && this.mRatingValue == 1.0f;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        String string2;
        StringBuilder stringBuilder = new StringBuilder().append("Rating:style=").append(this.mRatingStyle).append(" rating=");
        if (this.mRatingValue < 0.0f) {
            string2 = "unrated";
            return stringBuilder.append(string2).toString();
        }
        string2 = String.valueOf(this.mRatingValue);
        return stringBuilder.append(string2).toString();
    }

    public void writeToParcel(Parcel parcel, int n2) {
        parcel.writeInt(this.mRatingStyle);
        parcel.writeFloat(this.mRatingValue);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StarStyle {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Style {
    }
}

