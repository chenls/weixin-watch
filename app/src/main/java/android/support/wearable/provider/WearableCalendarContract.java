/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package android.support.wearable.provider;

import android.net.Uri;

public class WearableCalendarContract {
    public static final String AUTHORITY = "com.google.android.wearable.provider.calendar";
    public static final Uri CONTENT_URI = Uri.parse((String)"content://com.google.android.wearable.provider.calendar");

    public static final class Attendees {
        public static final Uri CONTENT_URI = Uri.withAppendedPath((Uri)CONTENT_URI, (String)"attendees");

        private Attendees() {
        }
    }

    public static final class Instances {
        public static final Uri CONTENT_URI = Uri.withAppendedPath((Uri)CONTENT_URI, (String)"instances/when");

        private Instances() {
        }
    }

    public static final class Reminders {
        public static final Uri CONTENT_URI = Uri.withAppendedPath((Uri)CONTENT_URI, (String)"reminders");

        private Reminders() {
        }
    }
}

