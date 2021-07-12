/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package com.android.volley.toolbox;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class NetworkImageView
extends ImageView {
    private int mDefaultImageId;
    private int mErrorImageId;
    private ImageLoader.ImageContainer mImageContainer;
    private ImageLoader mImageLoader;
    private String mUrl;

    public NetworkImageView(Context context) {
        this(context, null);
    }

    public NetworkImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NetworkImageView(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    private void setDefaultImageOrNull() {
        if (this.mDefaultImageId != 0) {
            this.setImageResource(this.mDefaultImageId);
            return;
        }
        this.setImageBitmap(null);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.invalidate();
    }

    public String getImageURL() {
        return this.mUrl;
    }

    /*
     * Enabled aggressive block sorting
     */
    void loadImageIfNecessary(final boolean bl2) {
        int n2;
        int n3;
        ImageView.ScaleType scaleType;
        int n4;
        int n5;
        block7: {
            block8: {
                block6: {
                    n5 = this.getWidth();
                    n4 = this.getHeight();
                    scaleType = this.getScaleType();
                    n3 = 0;
                    n2 = 0;
                    if (this.getLayoutParams() != null) {
                        n3 = this.getLayoutParams().width == -2 ? 1 : 0;
                        n2 = this.getLayoutParams().height == -2 ? 1 : 0;
                    }
                    boolean bl3 = n3 != 0 && n2 != 0;
                    if (n5 == 0 && n4 == 0 && !bl3) break block6;
                    if (TextUtils.isEmpty((CharSequence)this.mUrl)) {
                        if (this.mImageContainer != null) {
                            this.mImageContainer.cancelRequest();
                            this.mImageContainer = null;
                        }
                        this.setDefaultImageOrNull();
                        return;
                    }
                    if (this.mImageContainer == null || this.mImageContainer.getRequestUrl() == null) break block7;
                    if (!this.mImageContainer.getRequestUrl().equals(this.mUrl)) break block8;
                }
                return;
            }
            this.mImageContainer.cancelRequest();
            this.setDefaultImageOrNull();
        }
        n3 = n3 != 0 ? 0 : n5;
        n2 = n2 != 0 ? 0 : n4;
        this.mImageContainer = this.mImageLoader.get(this.mUrl, new ImageLoader.ImageListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (NetworkImageView.this.mErrorImageId != 0) {
                    NetworkImageView.this.setImageResource(NetworkImageView.this.mErrorImageId);
                }
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onResponse(final ImageLoader.ImageContainer imageContainer, boolean bl22) {
                if (bl22 && bl2) {
                    NetworkImageView.this.post(new Runnable(){

                        @Override
                        public void run() {
                            this.onResponse(imageContainer, false);
                        }
                    });
                    return;
                } else {
                    if (imageContainer.getBitmap() != null) {
                        NetworkImageView.this.setImageBitmap(imageContainer.getBitmap());
                        return;
                    }
                    if (NetworkImageView.this.mDefaultImageId == 0) return;
                    NetworkImageView.this.setImageResource(NetworkImageView.this.mDefaultImageId);
                    return;
                }
            }
        }, n3, n2, scaleType);
    }

    protected void onDetachedFromWindow() {
        if (this.mImageContainer != null) {
            this.mImageContainer.cancelRequest();
            this.setImageBitmap(null);
            this.mImageContainer = null;
        }
        super.onDetachedFromWindow();
    }

    protected void onLayout(boolean bl2, int n2, int n3, int n4, int n5) {
        super.onLayout(bl2, n2, n3, n4, n5);
        this.loadImageIfNecessary(true);
    }

    public void setDefaultImageResId(int n2) {
        this.mDefaultImageId = n2;
    }

    public void setErrorImageResId(int n2) {
        this.mErrorImageId = n2;
    }

    public void setImageUrl(String string2, ImageLoader imageLoader) {
        this.mUrl = string2;
        this.mImageLoader = imageLoader;
        this.loadImageIfNecessary(false);
    }
}

