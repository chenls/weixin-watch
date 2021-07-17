package com.chenls.weixin.model;

import android.graphics.Bitmap;

public class GenerateInfo {
    private Bitmap bitmap;
    private String uuid;

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid2) {
        this.uuid = uuid2;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }
}
