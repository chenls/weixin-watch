/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.google.android.gms.common;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;

public final class AccountPicker {
    private AccountPicker() {
    }

    public static Intent newChooseAccountIntent(Account account, ArrayList<Account> arrayList, String[] stringArray, boolean bl2, String string2, String string3, String[] stringArray2, Bundle bundle) {
        return AccountPicker.zza(account, arrayList, stringArray, bl2, string2, string3, stringArray2, bundle, false);
    }

    public static Intent zza(Account account, ArrayList<Account> arrayList, String[] stringArray, boolean bl2, String string2, String string3, String[] stringArray2, Bundle bundle, boolean bl3) {
        return AccountPicker.zza(account, arrayList, stringArray, bl2, string2, string3, stringArray2, bundle, bl3, 0, 0);
    }

    public static Intent zza(Account account, ArrayList<Account> arrayList, String[] stringArray, boolean bl2, String string2, String string3, String[] stringArray2, Bundle bundle, boolean bl3, int n2, int n3) {
        return AccountPicker.zza(account, arrayList, stringArray, bl2, string2, string3, stringArray2, bundle, bl3, n2, n3, null, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static Intent zza(Account account, ArrayList<Account> arrayList, String[] stringArray, boolean bl2, String string2, String string3, String[] stringArray2, Bundle bundle, boolean bl3, int n2, int n3, String string4, boolean bl4) {
        Intent intent = new Intent();
        if (!bl4) {
            boolean bl5 = string4 == null;
            zzx.zzb(bl5, (Object)"We only support hostedDomain filter for account chip styled account picker");
        }
        String string5 = bl4 ? "com.google.android.gms.common.account.CHOOSE_ACCOUNT_USERTILE" : "com.google.android.gms.common.account.CHOOSE_ACCOUNT";
        intent.setAction(string5);
        intent.setPackage("com.google.android.gms");
        intent.putExtra("allowableAccounts", arrayList);
        intent.putExtra("allowableAccountTypes", stringArray);
        intent.putExtra("addAccountOptions", bundle);
        intent.putExtra("selectedAccount", (Parcelable)account);
        intent.putExtra("alwaysPromptForAccount", bl2);
        intent.putExtra("descriptionTextOverride", string2);
        intent.putExtra("authTokenType", string3);
        intent.putExtra("addAccountRequiredFeatures", stringArray2);
        intent.putExtra("setGmsCoreAccount", bl3);
        intent.putExtra("overrideTheme", n2);
        intent.putExtra("overrideCustomTheme", n3);
        intent.putExtra("hostedDomainFilter", string4);
        return intent;
    }
}

