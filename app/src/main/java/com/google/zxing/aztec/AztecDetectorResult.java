/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.aztec;

import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;

public final class AztecDetectorResult
extends DetectorResult {
    private final boolean compact;
    private final int nbDatablocks;
    private final int nbLayers;

    public AztecDetectorResult(BitMatrix bitMatrix, ResultPoint[] resultPointArray, boolean bl2, int n2, int n3) {
        super(bitMatrix, resultPointArray);
        this.compact = bl2;
        this.nbDatablocks = n2;
        this.nbLayers = n3;
    }

    public int getNbDatablocks() {
        return this.nbDatablocks;
    }

    public int getNbLayers() {
        return this.nbLayers;
    }

    public boolean isCompact() {
        return this.compact;
    }
}

