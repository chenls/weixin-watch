/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.pdf417;

public final class PDF417ResultMetadata {
    private String fileId;
    private boolean lastSegment;
    private int[] optionalData;
    private int segmentIndex;

    public String getFileId() {
        return this.fileId;
    }

    public int[] getOptionalData() {
        return this.optionalData;
    }

    public int getSegmentIndex() {
        return this.segmentIndex;
    }

    public boolean isLastSegment() {
        return this.lastSegment;
    }

    public void setFileId(String string2) {
        this.fileId = string2;
    }

    public void setLastSegment(boolean bl2) {
        this.lastSegment = bl2;
    }

    public void setOptionalData(int[] nArray) {
        this.optionalData = nArray;
    }

    public void setSegmentIndex(int n2) {
        this.segmentIndex = n2;
    }
}

