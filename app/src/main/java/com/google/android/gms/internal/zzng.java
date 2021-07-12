/*
 * Decompiled with CFR 0.151.
 */
package com.google.android.gms.internal;

public final class zzng {
    private static int zza(StackTraceElement[] stackTraceElementArray, StackTraceElement[] stackTraceElementArray2) {
        int n2 = 0;
        int n3 = stackTraceElementArray2.length;
        int n4 = stackTraceElementArray.length;
        while (--n4 >= 0 && --n3 >= 0 && stackTraceElementArray2[n3].equals(stackTraceElementArray[n4])) {
            ++n2;
        }
        return n2;
    }

    public static String zzso() {
        StringBuilder stringBuilder = new StringBuilder();
        Throwable throwable = new Throwable();
        StackTraceElement[] stackTraceElementArray = throwable.getStackTrace();
        stringBuilder.append("Async stack trace:");
        for (StackTraceElement stackTraceElementArray2 : stackTraceElementArray) {
            stringBuilder.append("\n\tat ").append(stackTraceElementArray2);
        }
        for (throwable = throwable.getCause(); throwable != null; throwable = throwable.getCause()) {
            stringBuilder.append("\nCaused by: ");
            stringBuilder.append(throwable);
            StackTraceElement[] stackTraceElementArray2 = throwable.getStackTrace();
            int n2 = zzng.zza(stackTraceElementArray2, stackTraceElementArray);
            for (int i2 = 0; i2 < stackTraceElementArray2.length - n2; ++i2) {
                stringBuilder.append("\n\tat " + stackTraceElementArray2[i2]);
            }
            if (n2 > 0) {
                stringBuilder.append("\n\t... " + n2 + " more");
            }
            stackTraceElementArray = stackTraceElementArray2;
        }
        return stringBuilder.toString();
    }
}

