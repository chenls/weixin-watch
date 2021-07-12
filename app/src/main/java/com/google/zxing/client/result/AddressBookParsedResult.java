/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class AddressBookParsedResult
extends ParsedResult {
    private final String[] addressTypes;
    private final String[] addresses;
    private final String birthday;
    private final String[] emailTypes;
    private final String[] emails;
    private final String[] geo;
    private final String instantMessenger;
    private final String[] names;
    private final String[] nicknames;
    private final String note;
    private final String org;
    private final String[] phoneNumbers;
    private final String[] phoneTypes;
    private final String pronunciation;
    private final String title;
    private final String[] urls;

    public AddressBookParsedResult(String[] stringArray, String[] stringArray2, String string2, String[] stringArray3, String[] stringArray4, String[] stringArray5, String[] stringArray6, String string3, String string4, String[] stringArray7, String[] stringArray8, String string5, String string6, String string7, String[] stringArray9, String[] stringArray10) {
        super(ParsedResultType.ADDRESSBOOK);
        this.names = stringArray;
        this.nicknames = stringArray2;
        this.pronunciation = string2;
        this.phoneNumbers = stringArray3;
        this.phoneTypes = stringArray4;
        this.emails = stringArray5;
        this.emailTypes = stringArray6;
        this.instantMessenger = string3;
        this.note = string4;
        this.addresses = stringArray7;
        this.addressTypes = stringArray8;
        this.org = string5;
        this.birthday = string6;
        this.title = string7;
        this.urls = stringArray9;
        this.geo = stringArray10;
    }

    public AddressBookParsedResult(String[] stringArray, String[] stringArray2, String[] stringArray3, String[] stringArray4, String[] stringArray5, String[] stringArray6, String[] stringArray7) {
        this(stringArray, null, null, stringArray2, stringArray3, stringArray4, stringArray5, null, null, stringArray6, stringArray7, null, null, null, null, null);
    }

    public String[] getAddressTypes() {
        return this.addressTypes;
    }

    public String[] getAddresses() {
        return this.addresses;
    }

    public String getBirthday() {
        return this.birthday;
    }

    @Override
    public String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(100);
        AddressBookParsedResult.maybeAppend(this.names, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.nicknames, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.pronunciation, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.title, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.org, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.addresses, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.phoneNumbers, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.emails, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.instantMessenger, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.urls, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.birthday, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.geo, stringBuilder);
        AddressBookParsedResult.maybeAppend(this.note, stringBuilder);
        return stringBuilder.toString();
    }

    public String[] getEmailTypes() {
        return this.emailTypes;
    }

    public String[] getEmails() {
        return this.emails;
    }

    public String[] getGeo() {
        return this.geo;
    }

    public String getInstantMessenger() {
        return this.instantMessenger;
    }

    public String[] getNames() {
        return this.names;
    }

    public String[] getNicknames() {
        return this.nicknames;
    }

    public String getNote() {
        return this.note;
    }

    public String getOrg() {
        return this.org;
    }

    public String[] getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public String[] getPhoneTypes() {
        return this.phoneTypes;
    }

    public String getPronunciation() {
        return this.pronunciation;
    }

    public String getTitle() {
        return this.title;
    }

    public String[] getURLs() {
        return this.urls;
    }
}

