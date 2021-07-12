/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.ViewParent
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 */
package android.support.design.widget;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

public class TextInputEditText
extends AppCompatEditText {
    public TextInputEditText(Context context) {
        super(context);
    }

    public TextInputEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TextInputEditText(Context context, AttributeSet attributeSet, int n2) {
        super(context, attributeSet, n2);
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        ViewParent viewParent;
        InputConnection inputConnection = super.onCreateInputConnection(editorInfo);
        if (inputConnection != null && editorInfo.hintText == null && (viewParent = this.getParent()) instanceof TextInputLayout) {
            editorInfo.hintText = ((TextInputLayout)viewParent).getHint();
        }
        return inputConnection;
    }
}

