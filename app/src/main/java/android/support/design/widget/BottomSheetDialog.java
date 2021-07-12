/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface$OnCancelListener
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.util.TypedValue
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.FrameLayout
 */
package android.support.design.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.R;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class BottomSheetDialog
extends AppCompatDialog {
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback(){

        @Override
        public void onSlide(@NonNull View view, float f2) {
        }

        @Override
        public void onStateChanged(@NonNull View view, int n2) {
            if (n2 == 5) {
                BottomSheetDialog.this.dismiss();
            }
        }
    };

    public BottomSheetDialog(@NonNull Context context) {
        this(context, 0);
    }

    public BottomSheetDialog(@NonNull Context context, @StyleRes int n2) {
        super(context, BottomSheetDialog.getThemeResId(context, n2));
        this.supportRequestWindowFeature(1);
    }

    protected BottomSheetDialog(@NonNull Context context, boolean bl2, DialogInterface.OnCancelListener onCancelListener) {
        super(context, bl2, onCancelListener);
        this.supportRequestWindowFeature(1);
    }

    private static int getThemeResId(Context context, int n2) {
        block3: {
            int n3;
            block2: {
                n3 = n2;
                if (n2 != 0) break block2;
                TypedValue typedValue = new TypedValue();
                if (!context.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, typedValue, true)) break block3;
                n3 = typedValue.resourceId;
            }
            return n3;
        }
        return R.style.Theme_Design_Light_BottomSheetDialog;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean shouldWindowCloseOnTouchOutside() {
        block5: {
            block4: {
                if (Build.VERSION.SDK_INT < 11) break block4;
                TypedValue typedValue = new TypedValue();
                if (!this.getContext().getTheme().resolveAttribute(16843611, typedValue, true)) {
                    return false;
                }
                if (typedValue.data == 0) break block5;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private View wrapInBottomSheet(int n2, View view, ViewGroup.LayoutParams layoutParams) {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout)View.inflate((Context)this.getContext(), (int)R.layout.design_bottom_sheet_dialog, null);
        View view2 = view;
        if (n2 != 0) {
            view2 = view;
            if (view == null) {
                view2 = this.getLayoutInflater().inflate(n2, (ViewGroup)coordinatorLayout, false);
            }
        }
        view = (FrameLayout)coordinatorLayout.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior.from(view).setBottomSheetCallback(this.mBottomSheetCallback);
        if (layoutParams == null) {
            view.addView(view2);
        } else {
            view.addView(view2, layoutParams);
        }
        if (this.shouldWindowCloseOnTouchOutside()) {
            coordinatorLayout.findViewById(R.id.touch_outside).setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    if (BottomSheetDialog.this.isShowing()) {
                        BottomSheetDialog.this.cancel();
                    }
                }
            });
        }
        return coordinatorLayout;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.getWindow().setLayout(-1, -1);
    }

    @Override
    public void setContentView(@LayoutRes int n2) {
        super.setContentView(this.wrapInBottomSheet(n2, null, null));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(this.wrapInBottomSheet(0, view, null));
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        super.setContentView(this.wrapInBottomSheet(0, view, layoutParams));
    }
}

