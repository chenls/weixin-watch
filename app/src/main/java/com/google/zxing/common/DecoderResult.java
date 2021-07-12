/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.common;

import java.util.List;

public final class DecoderResult {
    private final List<byte[]> byteSegments;
    private final String ecLevel;
    private Integer erasures;
    private Integer errorsCorrected;
    private Object other;
    private final byte[] rawBytes;
    private final int structuredAppendParity;
    private final int structuredAppendSequenceNumber;
    private final String text;

    public DecoderResult(byte[] byArray, String string2, List<byte[]> list, String string3) {
        this(byArray, string2, list, string3, -1, -1);
    }

    public DecoderResult(byte[] byArray, String string2, List<byte[]> list, String string3, int n2, int n3) {
        this.rawBytes = byArray;
        this.text = string2;
        this.byteSegments = list;
        this.ecLevel = string3;
        this.structuredAppendParity = n3;
        this.structuredAppendSequenceNumber = n2;
    }

    public List<byte[]> getByteSegments() {
        return this.byteSegments;
    }

    public String getECLevel() {
        return this.ecLevel;
    }

    public Integer getErasures() {
        return this.erasures;
    }

    public Integer getErrorsCorrected() {
        return this.errorsCorrected;
    }

    public Object getOther() {
        return this.other;
    }

    public byte[] getRawBytes() {
        return this.rawBytes;
    }

    public int getStructuredAppendParity() {
        return this.structuredAppendParity;
    }

    public int getStructuredAppendSequenceNumber() {
        return this.structuredAppendSequenceNumber;
    }

    public String getText() {
        return this.text;
    }

    public boolean hasStructuredAppend() {
        return this.structuredAppendParity >= 0 && this.structuredAppendSequenceNumber >= 0;
    }

    public void setErasures(Integer n2) {
        this.erasures = n2;
    }

    public void setErrorsCorrected(Integer n2) {
        this.errorsCorrected = n2;
    }

    public void setOther(Object object) {
        this.other = object;
    }
}

