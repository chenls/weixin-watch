/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.auth.firstparty.shared;

public enum zzd {
    zzYC("ClientLoginDisabled"),
    zzYD("DeviceManagementRequiredOrSyncDisabled"),
    zzYE("SocketTimeout"),
    zzYF("Ok"),
    zzYG("UNKNOWN_ERR"),
    zzYH("NetworkError"),
    zzYI("ServiceUnavailable"),
    zzYJ("InternalError"),
    zzYK("BadAuthentication"),
    zzYL("EmptyConsumerPackageOrSig"),
    zzYM("InvalidSecondFactor"),
    zzYN("PostSignInFlowRequired"),
    zzYO("NeedsBrowser"),
    zzYP("Unknown"),
    zzYQ("NotVerified"),
    zzYR("TermsNotAgreed"),
    zzYS("AccountDisabled"),
    zzYT("CaptchaRequired"),
    zzYU("AccountDeleted"),
    zzYV("ServiceDisabled"),
    zzYW("NeedPermission"),
    zzYX("INVALID_SCOPE"),
    zzYY("UserCancel"),
    zzYZ("PermissionDenied"),
    zzZa("ThirdPartyDeviceManagementRequired"),
    zzZb("DeviceManagementInternalError"),
    zzZc("DeviceManagementSyncDisabled"),
    zzZd("DeviceManagementAdminBlocked"),
    zzZe("DeviceManagementAdminPendingApproval"),
    zzZf("DeviceManagementStaleSyncRequired"),
    zzZg("DeviceManagementDeactivated"),
    zzZh("DeviceManagementRequired"),
    zzZi("ReauthRequired"),
    zzZj("ALREADY_HAS_GMAIL"),
    zzZk("WeakPassword"),
    zzZl("BadRequest"),
    zzZm("BadUsername"),
    zzZn("DeletedGmail"),
    zzZo("ExistingUsername"),
    zzZp("LoginFail"),
    zzZq("NotLoggedIn"),
    zzZr("NoGmail"),
    zzZs("RequestDenied"),
    zzZt("ServerError"),
    zzZu("UsernameUnavailable"),
    zzZv("GPlusOther"),
    zzZw("GPlusNickname"),
    zzZx("GPlusInvalidChar"),
    zzZy("GPlusInterstitial"),
    zzZz("ProfileUpgradeError");

    private final String zzZA;

    private zzd(String string3) {
        this.zzZA = string3;
    }

    public static boolean zza(zzd zzd2) {
        return zzYK.equals((Object)zzd2) || zzYT.equals((Object)zzd2) || zzYW.equals((Object)zzd2) || zzYO.equals((Object)zzd2) || zzYY.equals((Object)zzd2) || zzZa.equals((Object)zzd2) || zzd.zzb(zzd2);
    }

    public static boolean zzb(zzd zzd2) {
        return zzYD.equals((Object)zzd2) || zzZb.equals((Object)zzd2) || zzZc.equals((Object)zzd2) || zzZd.equals((Object)zzd2) || zzZe.equals((Object)zzd2) || zzZf.equals((Object)zzd2) || zzZg.equals((Object)zzd2) || zzZh.equals((Object)zzd2);
    }

    public static final zzd zzbY(String string2) {
        zzd zzd2 = null;
        for (zzd zzd3 : zzd.values()) {
            if (!zzd3.zzZA.equals(string2)) continue;
            zzd2 = zzd3;
        }
        return zzd2;
    }

    public static boolean zzc(zzd zzd2) {
        return zzYH.equals((Object)zzd2) || zzYI.equals((Object)zzd2);
    }
}

