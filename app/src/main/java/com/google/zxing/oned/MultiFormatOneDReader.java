/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;
import com.google.zxing.oned.CodaBarReader;
import com.google.zxing.oned.Code128Reader;
import com.google.zxing.oned.Code39Reader;
import com.google.zxing.oned.Code93Reader;
import com.google.zxing.oned.ITFReader;
import com.google.zxing.oned.MultiFormatUPCEANReader;
import com.google.zxing.oned.OneDReader;
import com.google.zxing.oned.rss.RSS14Reader;
import com.google.zxing.oned.rss.expanded.RSSExpandedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class MultiFormatOneDReader
extends OneDReader {
    private final OneDReader[] readers;

    /*
     * Enabled aggressive block sorting
     */
    public MultiFormatOneDReader(Map<DecodeHintType, ?> map) {
        Collection collection = map == null ? null : (Collection)map.get((Object)DecodeHintType.POSSIBLE_FORMATS);
        boolean bl2 = map != null && map.get((Object)DecodeHintType.ASSUME_CODE_39_CHECK_DIGIT) != null;
        ArrayList<OneDReader> arrayList = new ArrayList<OneDReader>();
        if (collection != null) {
            if (collection.contains((Object)BarcodeFormat.EAN_13) || collection.contains((Object)BarcodeFormat.UPC_A) || collection.contains((Object)BarcodeFormat.EAN_8) || collection.contains((Object)BarcodeFormat.UPC_E)) {
                arrayList.add(new MultiFormatUPCEANReader(map));
            }
            if (collection.contains((Object)BarcodeFormat.CODE_39)) {
                arrayList.add(new Code39Reader(bl2));
            }
            if (collection.contains((Object)BarcodeFormat.CODE_93)) {
                arrayList.add(new Code93Reader());
            }
            if (collection.contains((Object)BarcodeFormat.CODE_128)) {
                arrayList.add(new Code128Reader());
            }
            if (collection.contains((Object)BarcodeFormat.ITF)) {
                arrayList.add(new ITFReader());
            }
            if (collection.contains((Object)BarcodeFormat.CODABAR)) {
                arrayList.add(new CodaBarReader());
            }
            if (collection.contains((Object)BarcodeFormat.RSS_14)) {
                arrayList.add(new RSS14Reader());
            }
            if (collection.contains((Object)BarcodeFormat.RSS_EXPANDED)) {
                arrayList.add(new RSSExpandedReader());
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add(new MultiFormatUPCEANReader(map));
            arrayList.add(new Code39Reader());
            arrayList.add(new CodaBarReader());
            arrayList.add(new Code93Reader());
            arrayList.add(new Code128Reader());
            arrayList.add(new ITFReader());
            arrayList.add(new RSS14Reader());
            arrayList.add(new RSSExpandedReader());
        }
        this.readers = arrayList.toArray(new OneDReader[arrayList.size()]);
    }

    @Override
    public Result decodeRow(int n2, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException {
        for (OneDReader object : this.readers) {
            try {
                Result result = object.decodeRow(n2, bitArray, map);
                return result;
            }
            catch (ReaderException readerException) {
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override
    public void reset() {
        OneDReader[] oneDReaderArray = this.readers;
        int n2 = oneDReaderArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            oneDReaderArray[i2].reset();
        }
    }
}

