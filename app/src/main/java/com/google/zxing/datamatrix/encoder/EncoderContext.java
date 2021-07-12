/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import com.google.zxing.datamatrix.encoder.SymbolInfo;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import java.nio.charset.Charset;

final class EncoderContext {
    private final StringBuilder codewords;
    private Dimension maxSize;
    private Dimension minSize;
    private final String msg;
    private int newEncoding;
    int pos;
    private SymbolShapeHint shape;
    private int skipAtEnd;
    private SymbolInfo symbolInfo;

    EncoderContext(String string2) {
        byte[] byArray = string2.getBytes(Charset.forName("ISO-8859-1"));
        StringBuilder stringBuilder = new StringBuilder(byArray.length);
        int n2 = byArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            char c2 = (char)(byArray[i2] & 0xFF);
            if (c2 == '?' && string2.charAt(i2) != '?') {
                throw new IllegalArgumentException("Message contains characters outside ISO-8859-1 encoding.");
            }
            stringBuilder.append(c2);
        }
        this.msg = stringBuilder.toString();
        this.shape = SymbolShapeHint.FORCE_NONE;
        this.codewords = new StringBuilder(string2.length());
        this.newEncoding = -1;
    }

    private int getTotalMessageCharCount() {
        return this.msg.length() - this.skipAtEnd;
    }

    public int getCodewordCount() {
        return this.codewords.length();
    }

    public StringBuilder getCodewords() {
        return this.codewords;
    }

    public char getCurrent() {
        return this.msg.charAt(this.pos);
    }

    public char getCurrentChar() {
        return this.msg.charAt(this.pos);
    }

    public String getMessage() {
        return this.msg;
    }

    public int getNewEncoding() {
        return this.newEncoding;
    }

    public int getRemainingCharacters() {
        return this.getTotalMessageCharCount() - this.pos;
    }

    public SymbolInfo getSymbolInfo() {
        return this.symbolInfo;
    }

    public boolean hasMoreCharacters() {
        return this.pos < this.getTotalMessageCharCount();
    }

    public void resetEncoderSignal() {
        this.newEncoding = -1;
    }

    public void resetSymbolInfo() {
        this.symbolInfo = null;
    }

    public void setSizeConstraints(Dimension dimension, Dimension dimension2) {
        this.minSize = dimension;
        this.maxSize = dimension2;
    }

    public void setSkipAtEnd(int n2) {
        this.skipAtEnd = n2;
    }

    public void setSymbolShape(SymbolShapeHint symbolShapeHint) {
        this.shape = symbolShapeHint;
    }

    public void signalEncoderChange(int n2) {
        this.newEncoding = n2;
    }

    public void updateSymbolInfo() {
        this.updateSymbolInfo(this.getCodewordCount());
    }

    public void updateSymbolInfo(int n2) {
        if (this.symbolInfo == null || n2 > this.symbolInfo.getDataCapacity()) {
            this.symbolInfo = SymbolInfo.lookup(n2, this.shape, this.minSize, this.maxSize, true);
        }
    }

    public void writeCodeword(char c2) {
        this.codewords.append(c2);
    }

    public void writeCodewords(String string2) {
        this.codewords.append(string2);
    }
}

