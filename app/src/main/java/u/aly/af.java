/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import com.umeng.analytics.AnalyticsConfig;
import u.aly.an;

public class af
implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler a;
    private an b;

    public af() {
        if (Thread.getDefaultUncaughtExceptionHandler() == this) {
            return;
        }
        this.a = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private void a(Throwable throwable) {
        if (AnalyticsConfig.CATCH_EXCEPTION) {
            this.b.a(throwable);
            return;
        }
        this.b.a(null);
    }

    public void a(an an2) {
        this.b = an2;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        this.a(throwable);
        if (this.a != null && this.a != Thread.getDefaultUncaughtExceptionHandler()) {
            this.a.uncaughtException(thread, throwable);
        }
    }
}

