/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.util.TypedValue
 *  android.view.LayoutInflater
 *  android.view.View
 */
package ticwear.design.app;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import ticwear.design.R;
import ticwear.design.app.AlertDialog;
import ticwear.design.widget.FloatingActionButton;
import ticwear.design.widget.NumberPicker;

public class NumberPickerDialog
extends AlertDialog
implements DialogInterface.OnClickListener {
    private FloatingActionButton buttonPositive;
    private NumberPicker numberPicker;
    private OnValuePickCancelListener onValuePickCancelListener;
    private OnValuePickedListener onValuePickedListener;

    public NumberPickerDialog(Context context, @StyleRes int n2, CharSequence charSequence, OnValuePickedListener onValuePickedListener, OnValuePickCancelListener onValuePickCancelListener, int n3, int n4, int n5, String[] stringArray) {
        super(context, NumberPickerDialog.resolveDialogTheme(context, n2));
        context = LayoutInflater.from((Context)this.getContext()).inflate(R.layout.dialog_number_picker, null);
        this.setView((View)context);
        this.numberPicker = (NumberPicker)context.findViewById(R.id.tic_numberPicker);
        this.numberPicker.setMinValue(n3);
        this.numberPicker.setMaxValue(n4);
        if (n5 != Integer.MIN_VALUE) {
            this.numberPicker.setValue(n5);
        }
        this.numberPicker.setDisplayedValues(stringArray);
        this.onValuePickedListener = onValuePickedListener;
        this.onValuePickCancelListener = onValuePickCancelListener;
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.setTitle(charSequence);
        }
        this.setButton(-1, this.getContext().getDrawable(R.drawable.tic_ic_btn_ok), (DialogInterface.OnClickListener)this);
    }

    @StyleRes
    static int resolveDialogTheme(Context context, @StyleRes int n2) {
        int n3 = n2;
        if (n2 == 0) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(16843948, typedValue, true);
            n3 = typedValue.resourceId;
        }
        return n3;
    }

    public NumberPicker getNumberPicker() {
        return this.numberPicker;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onClick(DialogInterface dialogInterface, int n2) {
        if (n2 == -1) {
            if (this.onValuePickedListener == null) return;
            this.onValuePickedListener.onValuePicked(this, this.numberPicker.getValue());
            return;
        }
        if (this.onValuePickCancelListener == null) return;
        this.onValuePickCancelListener.onValuePickCancelled(this);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.buttonPositive = (FloatingActionButton)this.getWindow().findViewById(R.id.iconButton1);
        this.numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener(){

            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int n2) {
                if (n2 == 0) {
                    NumberPickerDialog.this.showButtonsDelayed();
                    return;
                }
                NumberPickerDialog.this.minimizeButtons();
            }
        });
    }

    public static class Builder {
        private Context context;
        private int defaultValue;
        String[] displayedValues;
        private int maxValue;
        private int minValue;
        private OnValuePickCancelListener onValuePickCancelListener;
        private OnValuePickedListener onValuePickedListener;
        private int themeResId;
        private CharSequence title;

        public Builder(Context context) {
            this.context = context;
            this.themeResId = 0;
            this.minValue = 0;
            this.maxValue = 0;
            this.defaultValue = Integer.MIN_VALUE;
        }

        static int resolveDialogTheme(Context context, int n2) {
            int n3 = n2;
            if (n2 == 0) {
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(16843529, typedValue, true);
                n3 = typedValue.resourceId;
            }
            return n3;
        }

        public NumberPickerDialog create() {
            return new NumberPickerDialog(this.context, this.themeResId, this.title, this.onValuePickedListener, this.onValuePickCancelListener, this.minValue, this.maxValue, this.defaultValue, this.displayedValues);
        }

        public Builder defaultValue(int n2) {
            this.defaultValue = n2;
            return this;
        }

        public Builder displayedValues(String[] stringArray) {
            this.displayedValues = stringArray;
            return this;
        }

        public Builder maxValue(int n2) {
            this.maxValue = n2;
            return this;
        }

        public Builder minValue(int n2) {
            this.minValue = n2;
            return this;
        }

        public Builder pickCancellistener(OnValuePickCancelListener onValuePickCancelListener) {
            this.onValuePickCancelListener = onValuePickCancelListener;
            return this;
        }

        public NumberPickerDialog show() {
            NumberPickerDialog numberPickerDialog = this.create();
            numberPickerDialog.show();
            return numberPickerDialog;
        }

        public Builder theme(@StyleRes int n2) {
            this.themeResId = Builder.resolveDialogTheme(this.context, n2);
            return this;
        }

        public Builder title(CharSequence charSequence) {
            this.title = charSequence;
            return this;
        }

        public Builder valuePickedlistener(OnValuePickedListener onValuePickedListener) {
            this.onValuePickedListener = onValuePickedListener;
            return this;
        }
    }

    public static interface OnValuePickCancelListener {
        public void onValuePickCancelled(NumberPickerDialog var1);
    }

    public static interface OnValuePickedListener {
        public void onValuePicked(NumberPickerDialog var1, int var2);
    }
}

