/*
 * Decompiled with CFR 0.151.
 */
package ticwear.design.widget;

import android.support.annotation.NonNull;
import ticwear.design.widget.NumberPicker;

public interface MultiPickerContainer {
    public void setMultiPickerClient(MultiPickerClient var1);

    public static interface MultiPickerClient {
        public void onPickerPostFocus(@NonNull NumberPicker var1);

        public boolean onPickerPreFocus(NumberPicker var1, boolean var2);
    }
}

