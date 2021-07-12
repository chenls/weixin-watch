/*
 * Decompiled with CFR 0.151.
 */
package com.umeng.analytics.social;

public class d {
    private int a = -1;
    private String b = "";
    private String c = "";
    private Exception d = null;

    public d(int n2) {
        this.a = n2;
    }

    public d(int n2, Exception exception) {
        this.a = n2;
        this.d = exception;
    }

    public Exception a() {
        return this.d;
    }

    public void a(int n2) {
        this.a = n2;
    }

    public void a(String string2) {
        this.b = string2;
    }

    public int b() {
        return this.a;
    }

    public void b(String string2) {
        this.c = string2;
    }

    public String c() {
        return this.b;
    }

    public String d() {
        return this.c;
    }

    public String toString() {
        return "status=" + this.a + "\r\n" + "msg:  " + this.b + "\r\n" + "data:  " + this.c;
    }
}

