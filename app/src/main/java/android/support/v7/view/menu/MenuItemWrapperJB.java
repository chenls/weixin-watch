/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.view.ActionProvider
 *  android.view.ActionProvider$VisibilityListener
 *  android.view.MenuItem
 *  android.view.View
 */
package android.support.v7.view.menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.support.v7.view.menu.MenuItemWrapperICS;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;

@TargetApi(value=16)
class MenuItemWrapperJB
extends MenuItemWrapperICS {
    MenuItemWrapperJB(Context context, SupportMenuItem supportMenuItem) {
        super(context, supportMenuItem);
    }

    @Override
    MenuItemWrapperICS.ActionProviderWrapper createActionProviderWrapper(ActionProvider actionProvider) {
        return new ActionProviderWrapperJB(this.mContext, actionProvider);
    }

    class ActionProviderWrapperJB
    extends MenuItemWrapperICS.ActionProviderWrapper
    implements ActionProvider.VisibilityListener {
        ActionProvider.VisibilityListener mListener;

        public ActionProviderWrapperJB(Context context, ActionProvider actionProvider) {
            super(context, actionProvider);
        }

        @Override
        public boolean isVisible() {
            return this.mInner.isVisible();
        }

        public void onActionProviderVisibilityChanged(boolean bl2) {
            if (this.mListener != null) {
                this.mListener.onActionProviderVisibilityChanged(bl2);
            }
        }

        @Override
        public View onCreateActionView(MenuItem menuItem) {
            return this.mInner.onCreateActionView(menuItem);
        }

        @Override
        public boolean overridesItemVisibility() {
            return this.mInner.overridesItemVisibility();
        }

        @Override
        public void refreshVisibility() {
            this.mInner.refreshVisibility();
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void setVisibilityListener(ActionProvider.VisibilityListener object) {
            this.mListener = object;
            ActionProvider actionProvider = this.mInner;
            object = object != null ? this : null;
            actionProvider.setVisibilityListener((ActionProvider.VisibilityListener)object);
        }
    }
}

