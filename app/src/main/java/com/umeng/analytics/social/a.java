/*
 * Decompiled with CFR 0.151.
 */
package com.umeng.analytics.social;

public class a
extends RuntimeException {
    private static final long b = -4656673116019167471L;
    protected int a = 5000;
    private String c = "";

    public a(int n2, String string2) {
        super(string2);
        this.a = n2;
        this.c = string2;
    }

    public a(String string2) {
        super(string2);
        this.c = string2;
    }

    public a(String string2, Throwable throwable) {
        super(string2, throwable);
        this.c = string2;
    }

    public int a() {
        return this.a;
    }

    @Override
    public String getMessage() {
        return this.c;
    }
}

