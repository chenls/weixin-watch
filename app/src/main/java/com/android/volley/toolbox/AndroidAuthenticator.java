/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.accounts.AccountManager
 *  android.accounts.AccountManagerFuture
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.android.volley.toolbox;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.Authenticator;

public class AndroidAuthenticator
implements Authenticator {
    private final Account mAccount;
    private final AccountManager mAccountManager;
    private final String mAuthTokenType;
    private final boolean mNotifyAuthFailure;

    AndroidAuthenticator(AccountManager accountManager, Account account, String string2, boolean bl2) {
        this.mAccountManager = accountManager;
        this.mAccount = account;
        this.mAuthTokenType = string2;
        this.mNotifyAuthFailure = bl2;
    }

    public AndroidAuthenticator(Context context, Account account, String string2) {
        this(context, account, string2, false);
    }

    public AndroidAuthenticator(Context context, Account account, String string2, boolean bl2) {
        this(AccountManager.get((Context)context), account, string2, bl2);
    }

    public Account getAccount() {
        return this.mAccount;
    }

    @Override
    public String getAuthToken() throws AuthFailureError {
        String string2;
        block4: {
            String string3;
            Bundle bundle;
            AccountManagerFuture accountManagerFuture = this.mAccountManager.getAuthToken(this.mAccount, this.mAuthTokenType, this.mNotifyAuthFailure, null, null);
            try {
                bundle = (Bundle)accountManagerFuture.getResult();
                string2 = string3 = null;
            }
            catch (Exception exception) {
                throw new AuthFailureError("Error while retrieving auth token", exception);
            }
            if (!accountManagerFuture.isDone()) break block4;
            string2 = string3;
            if (accountManagerFuture.isCancelled()) break block4;
            if (bundle.containsKey("intent")) {
                throw new AuthFailureError((Intent)bundle.getParcelable("intent"));
            }
            string2 = bundle.getString("authtoken");
        }
        if (string2 == null) {
            throw new AuthFailureError("Got null auth token for type: " + this.mAuthTokenType);
        }
        return string2;
    }

    @Override
    public void invalidateAuthToken(String string2) {
        this.mAccountManager.invalidateAuthToken(this.mAccount.type, string2);
    }
}

