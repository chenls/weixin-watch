/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Fragment
 *  android.graphics.Rect
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnLayoutChangeListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.TextView
 */
package android.support.wearable.view;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.wearable.R;
import android.support.wearable.view.CardFrame;
import android.support.wearable.view.CardScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

@TargetApi(value=20)
public class CardFragment
extends Fragment {
    private static final String CONTENT_SAVED_STATE = "CardScrollView_content";
    public static final int EXPAND_DOWN = 1;
    public static final int EXPAND_UP = -1;
    public static final String KEY_ICON_RESOURCE = "CardFragment_icon";
    public static final String KEY_TEXT = "CardFragment_text";
    public static final String KEY_TITLE = "CardFragment_title";
    private boolean mActivityCreated;
    private CardFrame mCard;
    private int mCardGravity = 80;
    private final Rect mCardMargins = new Rect(-1, -1, -1, -1);
    private Rect mCardPadding;
    private CardScrollView mCardScroll;
    private int mExpansionDirection = 1;
    private boolean mExpansionEnabled = true;
    private float mExpansionFactor = 10.0f;
    private boolean mScrollToBottom;
    private boolean mScrollToTop;

    static /* synthetic */ boolean access$102(CardFragment cardFragment, boolean bl2) {
        cardFragment.mScrollToTop = bl2;
        return bl2;
    }

    static /* synthetic */ boolean access$202(CardFragment cardFragment, boolean bl2) {
        cardFragment.mScrollToBottom = bl2;
        return bl2;
    }

    private void applyCardGravity() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.mCard.getLayoutParams();
        layoutParams.gravity = this.mCardGravity;
        this.mCard.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    private void applyCardMargins() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)this.mCard.getLayoutParams();
        if (this.mCardMargins.left != -1) {
            marginLayoutParams.leftMargin = this.mCardMargins.left;
        }
        if (this.mCardMargins.top != -1) {
            marginLayoutParams.topMargin = this.mCardMargins.top;
        }
        if (this.mCardMargins.right != -1) {
            marginLayoutParams.rightMargin = this.mCardMargins.right;
        }
        if (this.mCardMargins.bottom != -1) {
            marginLayoutParams.bottomMargin = this.mCardMargins.bottom;
        }
        this.mCard.setLayoutParams((ViewGroup.LayoutParams)marginLayoutParams);
    }

    private void applyPadding() {
        if (this.mCard != null) {
            this.mCard.setContentPadding(this.mCardPadding.left, this.mCardPadding.top, this.mCardPadding.right, this.mCardPadding.bottom);
        }
    }

    public static CardFragment create(CharSequence charSequence, CharSequence charSequence2) {
        return CardFragment.create(charSequence, charSequence2, 0);
    }

    public static CardFragment create(CharSequence charSequence, CharSequence charSequence2, int n2) {
        CardFragment cardFragment = new CardFragment();
        Bundle bundle = new Bundle();
        if (charSequence != null) {
            bundle.putCharSequence(KEY_TITLE, charSequence);
        }
        if (charSequence2 != null) {
            bundle.putCharSequence(KEY_TEXT, charSequence2);
        }
        if (n2 != 0) {
            bundle.putInt(KEY_ICON_RESOURCE, n2);
        }
        cardFragment.setArguments(bundle);
        return cardFragment;
    }

    public Rect getContentPadding() {
        return new Rect(this.mCardPadding);
    }

    public int getContentPaddingBottom() {
        return this.mCardPadding.bottom;
    }

    public int getContentPaddingLeft() {
        return this.mCardPadding.left;
    }

    public int getContentPaddingRight() {
        return this.mCardPadding.right;
    }

    public int getContentPaddingTop() {
        return this.mCardPadding.top;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mActivityCreated = true;
        this.applyCardMargins();
        this.applyCardGravity();
    }

    public View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(R.layout.watch_card_content, viewGroup, false);
        viewGroup = this.getArguments();
        if (viewGroup != null) {
            TextView textView;
            bundle = (TextView)layoutInflater.findViewById(R.id.title);
            if (viewGroup.containsKey(KEY_TITLE) && bundle != null) {
                bundle.setText(viewGroup.getCharSequence(KEY_TITLE));
            }
            if (viewGroup.containsKey(KEY_TEXT) && (textView = (TextView)layoutInflater.findViewById(R.id.text)) != null) {
                textView.setText(viewGroup.getCharSequence(KEY_TEXT));
            }
            if (viewGroup.containsKey(KEY_ICON_RESOURCE) && bundle != null) {
                bundle.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, viewGroup.getInt(KEY_ICON_RESOURCE), 0);
            }
        }
        return layoutInflater;
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mCardScroll = new CardScrollView(layoutInflater.getContext());
        this.mCardScroll.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.mCard = new CardFrame(layoutInflater.getContext());
        this.mCard.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -2, this.mCardGravity));
        this.mCard.setExpansionEnabled(this.mExpansionEnabled);
        this.mCard.setExpansionFactor(this.mExpansionFactor);
        this.mCard.setExpansionDirection(this.mExpansionDirection);
        if (this.mCardPadding != null) {
            this.applyPadding();
        }
        this.mCardScroll.addView((View)this.mCard);
        if (this.mScrollToTop || this.mScrollToBottom) {
            this.mCardScroll.addOnLayoutChangeListener(new View.OnLayoutChangeListener(){

                /*
                 * Enabled aggressive block sorting
                 */
                public void onLayoutChange(View view, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
                    CardFragment.this.mCardScroll.removeOnLayoutChangeListener(this);
                    if (CardFragment.this.mScrollToTop) {
                        CardFragment.access$102(CardFragment.this, false);
                        CardFragment.this.scrollToTop();
                        return;
                    } else {
                        if (!CardFragment.this.mScrollToBottom) return;
                        CardFragment.access$202(CardFragment.this, false);
                        CardFragment.this.scrollToBottom();
                        return;
                    }
                }
            });
        }
        viewGroup = null;
        if (bundle != null) {
            viewGroup = bundle.getBundle(CONTENT_SAVED_STATE);
        }
        if ((layoutInflater = this.onCreateContentView(layoutInflater, this.mCard, (Bundle)viewGroup)) != null) {
            this.mCard.addView((View)layoutInflater);
        }
        return this.mCardScroll;
    }

    public void onDestroy() {
        this.mActivityCreated = false;
        super.onDestroy();
    }

    public void scrollToBottom() {
        if (this.mCardScroll != null) {
            this.mCardScroll.scrollBy(0, this.mCardScroll.getAvailableScrollDelta(1));
            return;
        }
        this.mScrollToTop = true;
        this.mScrollToBottom = false;
    }

    public void scrollToTop() {
        if (this.mCardScroll != null) {
            this.mCardScroll.scrollBy(0, this.mCardScroll.getAvailableScrollDelta(-1));
            return;
        }
        this.mScrollToTop = true;
        this.mScrollToBottom = false;
    }

    public void setCardGravity(int n2) {
        this.mCardGravity = n2 & 0x70;
        if (this.mActivityCreated) {
            this.applyCardGravity();
        }
    }

    public void setCardMarginBottom(int n2) {
        this.mCardMargins.bottom = n2;
        if (this.mActivityCreated) {
            this.applyCardMargins();
        }
    }

    public void setCardMarginLeft(int n2) {
        this.mCardMargins.left = n2;
        if (this.mActivityCreated) {
            this.applyCardMargins();
        }
    }

    public void setCardMarginRight(int n2) {
        this.mCardMargins.right = n2;
        if (this.mActivityCreated) {
            this.applyCardMargins();
        }
    }

    public void setCardMarginTop(int n2) {
        this.mCardMargins.top = n2;
        if (this.mActivityCreated) {
            this.applyCardMargins();
        }
    }

    public void setCardMargins(int n2, int n3, int n4, int n5) {
        this.mCardMargins.set(n2, n3, n4, n5);
        if (this.mActivityCreated) {
            this.applyCardMargins();
        }
    }

    public void setContentPadding(int n2, int n3, int n4, int n5) {
        this.mCardPadding = new Rect(n2, n3, n4, n5);
        this.applyPadding();
    }

    public void setContentPaddingBottom(int n2) {
        this.mCardPadding.bottom = n2;
        this.applyPadding();
    }

    public void setContentPaddingLeft(int n2) {
        this.mCardPadding.left = n2;
        this.applyPadding();
    }

    public void setContentPaddingRight(int n2) {
        this.mCardPadding.right = n2;
        this.applyPadding();
    }

    public void setContentPaddingTop(int n2) {
        this.mCardPadding.top = n2;
        this.applyPadding();
    }

    public void setExpansionDirection(int n2) {
        this.mExpansionDirection = n2;
        if (this.mCard != null) {
            this.mCard.setExpansionDirection(this.mExpansionDirection);
        }
    }

    public void setExpansionEnabled(boolean bl2) {
        this.mExpansionEnabled = bl2;
        if (this.mCard != null) {
            this.mCard.setExpansionEnabled(this.mExpansionEnabled);
        }
    }

    public void setExpansionFactor(float f2) {
        this.mExpansionFactor = f2;
        if (this.mCard != null) {
            this.mCard.setExpansionFactor(f2);
        }
    }
}

