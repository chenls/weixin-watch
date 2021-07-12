/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.aztec.AztecReader;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.maxicode.MaxiCodeReader;
import com.google.zxing.oned.MultiFormatOneDReader;
import com.google.zxing.pdf417.PDF417Reader;
import com.google.zxing.qrcode.QRCodeReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class MultiFormatReader
implements Reader {
    private Map<DecodeHintType, ?> hints;
    private Reader[] readers;

    private Result decodeInternal(BinaryBitmap binaryBitmap) throws NotFoundException {
        if (this.readers != null) {
            for (Reader object : this.readers) {
                try {
                    Result result = object.decode(binaryBitmap, this.hints);
                    return result;
                }
                catch (ReaderException readerException) {
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException {
        this.setHints(null);
        return this.decodeInternal(binaryBitmap);
    }

    @Override
    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        this.setHints(map);
        return this.decodeInternal(binaryBitmap);
    }

    public Result decodeWithState(BinaryBitmap binaryBitmap) throws NotFoundException {
        if (this.readers == null) {
            this.setHints(null);
        }
        return this.decodeInternal(binaryBitmap);
    }

    @Override
    public void reset() {
        if (this.readers != null) {
            Reader[] readerArray = this.readers;
            int n2 = readerArray.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                readerArray[i2].reset();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setHints(Map<DecodeHintType, ?> map) {
        boolean bl2 = false;
        this.hints = map;
        boolean bl3 = map != null && map.containsKey((Object)DecodeHintType.TRY_HARDER);
        Collection collection = map == null ? null : (Collection)map.get((Object)DecodeHintType.POSSIBLE_FORMATS);
        ArrayList<Reader> arrayList = new ArrayList<Reader>();
        if (collection != null) {
            if (collection.contains((Object)BarcodeFormat.UPC_A) || collection.contains((Object)BarcodeFormat.UPC_E) || collection.contains((Object)BarcodeFormat.EAN_13) || collection.contains((Object)BarcodeFormat.EAN_8) || collection.contains((Object)BarcodeFormat.CODABAR) || collection.contains((Object)BarcodeFormat.CODE_39) || collection.contains((Object)BarcodeFormat.CODE_93) || collection.contains((Object)BarcodeFormat.CODE_128) || collection.contains((Object)BarcodeFormat.ITF) || collection.contains((Object)BarcodeFormat.RSS_14) || collection.contains((Object)BarcodeFormat.RSS_EXPANDED)) {
                bl2 = true;
            }
            if (bl2 && !bl3) {
                arrayList.add(new MultiFormatOneDReader(map));
            }
            if (collection.contains((Object)BarcodeFormat.QR_CODE)) {
                arrayList.add(new QRCodeReader());
            }
            if (collection.contains((Object)BarcodeFormat.DATA_MATRIX)) {
                arrayList.add(new DataMatrixReader());
            }
            if (collection.contains((Object)BarcodeFormat.AZTEC)) {
                arrayList.add(new AztecReader());
            }
            if (collection.contains((Object)BarcodeFormat.PDF_417)) {
                arrayList.add(new PDF417Reader());
            }
            if (collection.contains((Object)BarcodeFormat.MAXICODE)) {
                arrayList.add(new MaxiCodeReader());
            }
            if (bl2 && bl3) {
                arrayList.add(new MultiFormatOneDReader(map));
            }
        }
        if (arrayList.isEmpty()) {
            if (!bl3) {
                arrayList.add(new MultiFormatOneDReader(map));
            }
            arrayList.add(new QRCodeReader());
            arrayList.add(new DataMatrixReader());
            arrayList.add(new AztecReader());
            arrayList.add(new PDF417Reader());
            arrayList.add(new MaxiCodeReader());
            if (bl3) {
                arrayList.add(new MultiFormatOneDReader(map));
            }
        }
        this.readers = arrayList.toArray(new Reader[arrayList.size()]);
    }
}

