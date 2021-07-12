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
import com.google.zxing.oned.EAN13Reader;
import com.google.zxing.oned.EAN8Reader;
import com.google.zxing.oned.OneDReader;
import com.google.zxing.oned.UPCAReader;
import com.google.zxing.oned.UPCEANReader;
import com.google.zxing.oned.UPCEReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class MultiFormatUPCEANReader
extends OneDReader {
    private final UPCEANReader[] readers;

    /*
     * Enabled aggressive block sorting
     */
    public MultiFormatUPCEANReader(Map<DecodeHintType, ?> object) {
        object = object == null ? null : (Collection)object.get((Object)DecodeHintType.POSSIBLE_FORMATS);
        ArrayList<UPCEANReader> arrayList = new ArrayList<UPCEANReader>();
        if (object != null) {
            if (object.contains((Object)BarcodeFormat.EAN_13)) {
                arrayList.add(new EAN13Reader());
            } else if (object.contains((Object)BarcodeFormat.UPC_A)) {
                arrayList.add(new UPCAReader());
            }
            if (object.contains((Object)BarcodeFormat.EAN_8)) {
                arrayList.add(new EAN8Reader());
            }
            if (object.contains((Object)BarcodeFormat.UPC_E)) {
                arrayList.add(new UPCEReader());
            }
        }
        if (arrayList.isEmpty()) {
            arrayList.add(new EAN13Reader());
            arrayList.add(new EAN8Reader());
            arrayList.add(new UPCEReader());
        }
        this.readers = arrayList.toArray(new UPCEANReader[arrayList.size()]);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Result decodeRow(int n2, BitArray object, Map<DecodeHintType, ?> map) throws NotFoundException {
        Object object2;
        int n3;
        block10: {
            block9: {
                int n4 = 0;
                int[] nArray = UPCEANReader.findStartGuardPattern((BitArray)object);
                UPCEANReader[] uPCEANReaderArray = this.readers;
                int n5 = uPCEANReaderArray.length;
                n3 = 0;
                while (true) {
                    if (n3 >= n5) {
                        throw NotFoundException.getNotFoundInstance();
                    }
                    object2 = uPCEANReaderArray[n3];
                    try {
                        object2 = ((UPCEANReader)object2).decodeRow(n2, (BitArray)object, nArray, map);
                        if (((Result)object2).getBarcodeFormat() == BarcodeFormat.EAN_13 && ((Result)object2).getText().charAt(0) == '0') {
                            n2 = 1;
                            break;
                        }
                        n2 = 0;
                    }
                    catch (ReaderException readerException) {
                        ++n3;
                        continue;
                    }
                    break;
                }
                object = map == null ? null : (Collection)map.get((Object)DecodeHintType.POSSIBLE_FORMATS);
                if (object == null) break block9;
                n3 = n4;
                if (!object.contains((Object)BarcodeFormat.UPC_A)) break block10;
            }
            n3 = 1;
        }
        if (n2 != 0 && n3 != 0) {
            object = new Result(((Result)object2).getText().substring(1), ((Result)object2).getRawBytes(), ((Result)object2).getResultPoints(), BarcodeFormat.UPC_A);
            ((Result)object).putAllMetadata(((Result)object2).getResultMetadata());
            return object;
        }
        return object2;
    }

    @Override
    public void reset() {
        UPCEANReader[] uPCEANReaderArray = this.readers;
        int n2 = uPCEANReaderArray.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            uPCEANReaderArray[i2].reset();
        }
    }
}

