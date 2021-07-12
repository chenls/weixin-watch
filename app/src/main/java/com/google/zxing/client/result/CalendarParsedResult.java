/*
 * Decompiled with CFR 0.151.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CalendarParsedResult
extends ParsedResult {
    private static final Pattern DATE_TIME;
    private static final Pattern RFC2445_DURATION;
    private static final long[] RFC2445_DURATION_FIELD_UNITS;
    private final String[] attendees;
    private final String description;
    private final Date end;
    private final boolean endAllDay;
    private final double latitude;
    private final String location;
    private final double longitude;
    private final String organizer;
    private final Date start;
    private final boolean startAllDay;
    private final String summary;

    static {
        RFC2445_DURATION = Pattern.compile("P(?:(\\d+)W)?(?:(\\d+)D)?(?:T(?:(\\d+)H)?(?:(\\d+)M)?(?:(\\d+)S)?)?");
        RFC2445_DURATION_FIELD_UNITS = new long[]{604800000L, 86400000L, 3600000L, 60000L, 1000L};
        DATE_TIME = Pattern.compile("[0-9]{8}(T[0-9]{6}Z?)?");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public CalendarParsedResult(String object, String string2, String string3, String string4, String string5, String string6, String[] stringArray, String string7, double d2, double d3) {
        block5: {
            block4: {
                super(ParsedResultType.CALENDAR);
                this.summary = object;
                try {
                    this.start = CalendarParsedResult.parseDate(string2);
                    if (string3 != null) break block4;
                }
                catch (ParseException parseException) {
                    throw new IllegalArgumentException(parseException.toString());
                }
                long l2 = CalendarParsedResult.parseDurationMS(string4);
                object = l2 < 0L ? null : new Date(this.start.getTime() + l2);
                this.end = object;
                break block5;
            }
            try {
                this.end = CalendarParsedResult.parseDate(string3);
            }
            catch (ParseException parseException) {
                throw new IllegalArgumentException(parseException.toString());
            }
        }
        boolean bl2 = string2.length() == 8;
        this.startAllDay = bl2;
        bl2 = string3 != null && string3.length() == 8;
        this.endAllDay = bl2;
        this.location = string5;
        this.organizer = string6;
        this.attendees = stringArray;
        this.description = string7;
        this.latitude = d2;
        this.longitude = d3;
    }

    private static DateFormat buildDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat;
    }

    private static DateFormat buildDateTimeFormat() {
        return new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.ENGLISH);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String format(boolean bl2, Date date) {
        DateFormat dateFormat;
        if (date == null) {
            return null;
        }
        if (bl2) {
            dateFormat = DateFormat.getDateInstance(2);
            return dateFormat.format(date);
        }
        dateFormat = DateFormat.getDateTimeInstance(2, 2);
        return dateFormat.format(date);
    }

    private static Date parseDate(String object) throws ParseException {
        if (!DATE_TIME.matcher((CharSequence)object).matches()) {
            throw new ParseException((String)object, 0);
        }
        if (((String)object).length() == 8) {
            return CalendarParsedResult.buildDateFormat().parse((String)object);
        }
        if (((String)object).length() == 16 && ((String)object).charAt(15) == 'Z') {
            object = CalendarParsedResult.buildDateTimeFormat().parse(((String)object).substring(0, 15));
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            long l2 = ((Date)object).getTime() + (long)gregorianCalendar.get(15);
            gregorianCalendar.setTime(new Date(l2));
            return new Date(l2 + (long)gregorianCalendar.get(16));
        }
        return CalendarParsedResult.buildDateTimeFormat().parse((String)object);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static long parseDurationMS(CharSequence object) {
        long l2 = -1L;
        if (object != null && ((Matcher)(object = RFC2445_DURATION.matcher((CharSequence)object))).matches()) {
            long l3 = 0L;
            int n2 = 0;
            while (true) {
                l2 = l3;
                if (n2 >= RFC2445_DURATION_FIELD_UNITS.length) break;
                String string2 = ((Matcher)object).group(n2 + 1);
                l2 = l3;
                if (string2 != null) {
                    l2 = l3 + RFC2445_DURATION_FIELD_UNITS[n2] * (long)Integer.parseInt(string2);
                }
                ++n2;
                l3 = l2;
            }
        }
        return l2;
    }

    public String[] getAttendees() {
        return this.attendees;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(100);
        CalendarParsedResult.maybeAppend(this.summary, stringBuilder);
        CalendarParsedResult.maybeAppend(CalendarParsedResult.format(this.startAllDay, this.start), stringBuilder);
        CalendarParsedResult.maybeAppend(CalendarParsedResult.format(this.endAllDay, this.end), stringBuilder);
        CalendarParsedResult.maybeAppend(this.location, stringBuilder);
        CalendarParsedResult.maybeAppend(this.organizer, stringBuilder);
        CalendarParsedResult.maybeAppend(this.attendees, stringBuilder);
        CalendarParsedResult.maybeAppend(this.description, stringBuilder);
        return stringBuilder.toString();
    }

    public Date getEnd() {
        return this.end;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public String getLocation() {
        return this.location;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getOrganizer() {
        return this.organizer;
    }

    public Date getStart() {
        return this.start;
    }

    public String getSummary() {
        return this.summary;
    }

    public boolean isEndAllDay() {
        return this.endAllDay;
    }

    public boolean isStartAllDay() {
        return this.startAllDay;
    }
}

