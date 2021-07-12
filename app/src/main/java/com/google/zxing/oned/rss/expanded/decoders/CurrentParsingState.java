/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned.rss.expanded.decoders;

final class CurrentParsingState {
    private State encoding = State.NUMERIC;
    private int position = 0;

    CurrentParsingState() {
    }

    int getPosition() {
        return this.position;
    }

    void incrementPosition(int n2) {
        this.position += n2;
    }

    boolean isAlpha() {
        return this.encoding == State.ALPHA;
    }

    boolean isIsoIec646() {
        return this.encoding == State.ISO_IEC_646;
    }

    boolean isNumeric() {
        return this.encoding == State.NUMERIC;
    }

    void setAlpha() {
        this.encoding = State.ALPHA;
    }

    void setIsoIec646() {
        this.encoding = State.ISO_IEC_646;
    }

    void setNumeric() {
        this.encoding = State.NUMERIC;
    }

    void setPosition(int n2) {
        this.position = n2;
    }

    private static enum State {
        NUMERIC,
        ALPHA,
        ISO_IEC_646;

    }
}

