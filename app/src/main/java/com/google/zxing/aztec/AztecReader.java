/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.aztec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.aztec.decoder.Decoder;
import com.google.zxing.aztec.detector.Detector;
import java.util.Map;

public final class AztecReader
implements Reader {
    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException {
        return this.decode(binaryBitmap, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Result decode(BinaryBitmap var1_1, Map<DecodeHintType, ?> var2_5) throws NotFoundException, FormatException {
        block12: {
            var8_6 = null;
            var7_8 = null;
            var11_10 = new Detector(var1_1.getBlackMatrix());
            var10_11 /* !! */  = null;
            var6_12 /* !! */  = null;
            var9_13 = null;
            var1_1 = var6_12 /* !! */ ;
            var5_14 = var10_11 /* !! */ ;
            try {
                var12_15 = var11_10.detect(false);
                var1_1 = var6_12 /* !! */ ;
                var5_14 = var10_11 /* !! */ ;
                var6_12 /* !! */  = var12_15.getPoints();
                var1_1 = var6_12 /* !! */ ;
                var5_14 = var6_12 /* !! */ ;
                var10_11 /* !! */  = new Decoder().decode(var12_15);
                var5_14 = var10_11 /* !! */ ;
                var1_1 = var6_12 /* !! */ ;
                var6_12 /* !! */  = var5_14;
            }
            catch (NotFoundException var8_7) {
                var6_12 /* !! */  = var9_13;
            }
            catch (FormatException var7_9) {
                var6_12 /* !! */  = var9_13;
                var1_1 = var5_14;
            }
            var5_14 = var6_12 /* !! */ ;
            if (var6_12 /* !! */  == null) {
                var5_14 = var11_10.detect(true);
                var1_1 = var5_14.getPoints();
                var5_14 = new Decoder().decode((AztecDetectorResult)var5_14);
            }
            if (var2_5 == null || (var2_5 = (ResultPointCallback)var2_5.get((Object)DecodeHintType.NEED_RESULT_POINT_CALLBACK)) == null) break block12;
            var4_16 = ((Object)var1_1).length;
            for (var3_17 = 0; var3_17 < var4_16; ++var3_17) {
                var2_5.foundPossibleResultPoint((ResultPoint)var1_1[var3_17]);
            }
            break block12;
            catch (NotFoundException var1_2) {}
            ** GOTO lbl-1000
        }
        var1_1 = new Result(var5_14.getText(), var5_14.getRawBytes(), (ResultPoint[])var1_1, BarcodeFormat.AZTEC);
        var2_5 = var5_14.getByteSegments();
        if (var2_5 != null) {
            var1_1.putMetadata(ResultMetadataType.BYTE_SEGMENTS, var2_5);
        }
        if ((var2_5 = var5_14.getECLevel()) != null) {
            var1_1.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, var2_5);
        }
        return var1_1;
        catch (FormatException var1_4) {}
lbl-1000:
        // 2 sources

        {
            if (var8_6 != null) {
                throw var8_6;
            }
            if (var7_8 != null) {
                throw var7_8;
            }
            throw var1_3;
        }
    }

    @Override
    public void reset() {
    }
}

