/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import java.util.EnumMap;
import java.util.Map;

public final class Result {
    private final BarcodeFormat format;
    private final byte[] rawBytes;
    private Map<ResultMetadataType, Object> resultMetadata;
    private ResultPoint[] resultPoints;
    private final String text;
    private final long timestamp;

    public Result(String string2, byte[] byArray, ResultPoint[] resultPointArray, BarcodeFormat barcodeFormat) {
        this(string2, byArray, resultPointArray, barcodeFormat, System.currentTimeMillis());
    }

    public Result(String string2, byte[] byArray, ResultPoint[] resultPointArray, BarcodeFormat barcodeFormat, long l2) {
        this.text = string2;
        this.rawBytes = byArray;
        this.resultPoints = resultPointArray;
        this.format = barcodeFormat;
        this.resultMetadata = null;
        this.timestamp = l2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addResultPoints(ResultPoint[] resultPointArray) {
        ResultPoint[] resultPointArray2 = this.resultPoints;
        if (resultPointArray2 == null) {
            this.resultPoints = resultPointArray;
            return;
        } else {
            if (resultPointArray == null || resultPointArray.length <= 0) return;
            ResultPoint[] resultPointArray3 = new ResultPoint[resultPointArray2.length + resultPointArray.length];
            System.arraycopy(resultPointArray2, 0, resultPointArray3, 0, resultPointArray2.length);
            System.arraycopy(resultPointArray, 0, resultPointArray3, resultPointArray2.length, resultPointArray.length);
            this.resultPoints = resultPointArray3;
            return;
        }
    }

    public BarcodeFormat getBarcodeFormat() {
        return this.format;
    }

    public byte[] getRawBytes() {
        return this.rawBytes;
    }

    public Map<ResultMetadataType, Object> getResultMetadata() {
        return this.resultMetadata;
    }

    public ResultPoint[] getResultPoints() {
        return this.resultPoints;
    }

    public String getText() {
        return this.text;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void putAllMetadata(Map<ResultMetadataType, Object> map) {
        block3: {
            block2: {
                if (map == null) break block2;
                if (this.resultMetadata != null) break block3;
                this.resultMetadata = map;
            }
            return;
        }
        this.resultMetadata.putAll(map);
    }

    public void putMetadata(ResultMetadataType resultMetadataType, Object object) {
        if (this.resultMetadata == null) {
            this.resultMetadata = new EnumMap<ResultMetadataType, Object>(ResultMetadataType.class);
        }
        this.resultMetadata.put(resultMetadataType, object);
    }

    public String toString() {
        return this.text;
    }
}

