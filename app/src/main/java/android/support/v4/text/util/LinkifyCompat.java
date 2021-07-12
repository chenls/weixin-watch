/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.text.Spannable
 *  android.text.SpannableString
 *  android.text.method.LinkMovementMethod
 *  android.text.method.MovementMethod
 *  android.text.style.URLSpan
 *  android.text.util.Linkify
 *  android.text.util.Linkify$MatchFilter
 *  android.text.util.Linkify$TransformFilter
 *  android.webkit.WebView
 *  android.widget.TextView
 */
package android.support.v4.text.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.PatternsCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.webkit.WebView;
import android.widget.TextView;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LinkifyCompat {
    private static final Comparator<LinkSpec> COMPARATOR;
    private static final String[] EMPTY_STRING;

    static {
        EMPTY_STRING = new String[0];
        COMPARATOR = new Comparator<LinkSpec>(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public final int compare(LinkSpec linkSpec, LinkSpec linkSpec2) {
                block6: {
                    block5: {
                        if (linkSpec.start < linkSpec2.start) break block5;
                        if (linkSpec.start > linkSpec2.start) {
                            return 1;
                        }
                        if (linkSpec.end < linkSpec2.end) {
                            return 1;
                        }
                        if (linkSpec.end <= linkSpec2.end) break block6;
                    }
                    return -1;
                }
                return 0;
            }
        };
    }

    private LinkifyCompat() {
    }

    private static void addLinkMovementMethod(@NonNull TextView textView) {
        MovementMethod movementMethod = textView.getMovementMethod();
        if ((movementMethod == null || !(movementMethod instanceof LinkMovementMethod)) && textView.getLinksClickable()) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public static final void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String string2) {
        LinkifyCompat.addLinks(textView, pattern, string2, null, null, null);
    }

    public static final void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String string2, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        LinkifyCompat.addLinks(textView, pattern, string2, null, matchFilter, transformFilter);
    }

    public static final void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String string2, @Nullable String[] stringArray, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        SpannableString spannableString = SpannableString.valueOf((CharSequence)textView.getText());
        if (LinkifyCompat.addLinks((Spannable)spannableString, pattern, string2, stringArray, matchFilter, transformFilter)) {
            textView.setText((CharSequence)spannableString);
            LinkifyCompat.addLinkMovementMethod(textView);
        }
    }

    public static final boolean addLinks(@NonNull Spannable spannable, int n2) {
        Object object;
        if (n2 == 0) {
            return false;
        }
        Object object2 = (URLSpan[])spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int i2 = ((URLSpan[])object2).length - 1; i2 >= 0; --i2) {
            spannable.removeSpan((Object)object2[i2]);
        }
        if ((n2 & 4) != 0) {
            Linkify.addLinks((Spannable)spannable, (int)4);
        }
        object2 = new ArrayList();
        if ((n2 & 1) != 0) {
            object = PatternsCompat.AUTOLINK_WEB_URL;
            Linkify.MatchFilter matchFilter = Linkify.sUrlMatchFilter;
            LinkifyCompat.gatherLinks((ArrayList<LinkSpec>)object2, spannable, (Pattern)object, new String[]{"http://", "https://", "rtsp://"}, matchFilter, null);
        }
        if ((n2 & 2) != 0) {
            LinkifyCompat.gatherLinks((ArrayList<LinkSpec>)object2, spannable, PatternsCompat.AUTOLINK_EMAIL_ADDRESS, new String[]{"mailto:"}, null, null);
        }
        if ((n2 & 8) != 0) {
            LinkifyCompat.gatherMapLinks((ArrayList<LinkSpec>)object2, spannable);
        }
        LinkifyCompat.pruneOverlaps((ArrayList<LinkSpec>)object2, spannable);
        if (((ArrayList)object2).size() == 0) {
            return false;
        }
        object2 = ((ArrayList)object2).iterator();
        while (object2.hasNext()) {
            object = (LinkSpec)object2.next();
            if (((LinkSpec)object).frameworkAddedSpan != null) continue;
            LinkifyCompat.applyLink(((LinkSpec)object).url, ((LinkSpec)object).start, ((LinkSpec)object).end, spannable);
        }
        return true;
    }

    public static final boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String string2) {
        return LinkifyCompat.addLinks(spannable, pattern, string2, null, null, null);
    }

    public static final boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String string2, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        return LinkifyCompat.addLinks(spannable, pattern, string2, null, matchFilter, transformFilter);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static final boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern object, @Nullable String stringArray, @Nullable String[] object2, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        int n2;
        Object object3;
        block11: {
            block10: {
                object3 = stringArray;
                if (stringArray == null) {
                    object3 = "";
                }
                if (object2 == null) break block10;
                stringArray = object2;
                if (((String[])object2).length >= 1) break block11;
            }
            stringArray = EMPTY_STRING;
        }
        String[] stringArray2 = new String[stringArray.length + 1];
        stringArray2[0] = ((String)object3).toLowerCase(Locale.ROOT);
        for (n2 = 0; n2 < stringArray.length; ++n2) {
            void var3_7;
            String string2 = stringArray[n2];
            if (string2 == null) {
                String string3 = "";
            } else {
                String string4 = string2.toLowerCase(Locale.ROOT);
            }
            stringArray2[n2 + 1] = var3_7;
        }
        boolean bl2 = false;
        object = ((Pattern)object).matcher((CharSequence)spannable);
        while (((Matcher)object).find()) {
            void var5_10;
            void var4_9;
            n2 = ((Matcher)object).start();
            int n3 = ((Matcher)object).end();
            boolean bl3 = true;
            if (var4_9 != null) {
                bl3 = var4_9.acceptMatch((CharSequence)spannable, n2, n3);
            }
            if (!bl3) continue;
            LinkifyCompat.applyLink(LinkifyCompat.makeUrl(((Matcher)object).group(0), stringArray2, (Matcher)object, (Linkify.TransformFilter)var5_10), n2, n3, spannable);
            bl2 = true;
        }
        return bl2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static final boolean addLinks(@NonNull TextView textView, int n2) {
        if (n2 == 0) return false;
        CharSequence charSequence = textView.getText();
        if (charSequence instanceof Spannable) {
            if (!LinkifyCompat.addLinks((Spannable)charSequence, n2)) return false;
            LinkifyCompat.addLinkMovementMethod(textView);
            return true;
        }
        if (!LinkifyCompat.addLinks((Spannable)(charSequence = SpannableString.valueOf((CharSequence)charSequence)), n2)) {
            return false;
        }
        LinkifyCompat.addLinkMovementMethod(textView);
        textView.setText(charSequence);
        return true;
    }

    private static void applyLink(String string2, int n2, int n3, Spannable spannable) {
        spannable.setSpan((Object)new URLSpan(string2), n2, n3, 33);
    }

    private static void gatherLinks(ArrayList<LinkSpec> arrayList, Spannable spannable, Pattern object, String[] stringArray, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        object = ((Pattern)object).matcher((CharSequence)spannable);
        while (((Matcher)object).find()) {
            int n2 = ((Matcher)object).start();
            int n3 = ((Matcher)object).end();
            if (matchFilter != null && !matchFilter.acceptMatch((CharSequence)spannable, n2, n3)) continue;
            LinkSpec linkSpec = new LinkSpec();
            linkSpec.url = LinkifyCompat.makeUrl(((Matcher)object).group(0), stringArray, (Matcher)object, transformFilter);
            linkSpec.start = n2;
            linkSpec.end = n3;
            arrayList.add(linkSpec);
        }
    }

    private static final void gatherMapLinks(ArrayList<LinkSpec> arrayList, Spannable object) {
        object = object.toString();
        int n2 = 0;
        while (true) {
            int n3;
            String string2;
            block8: {
                try {
                    string2 = WebView.findAddress((String)object);
                    if (string2 == null) break;
                }
                catch (UnsupportedOperationException unsupportedOperationException) {
                    return;
                }
                n3 = ((String)object).indexOf(string2);
                if (n3 >= 0) break block8;
                return;
            }
            LinkSpec linkSpec = new LinkSpec();
            int n4 = n3 + string2.length();
            linkSpec.start = n2 + n3;
            linkSpec.end = n2 + n4;
            object = ((String)object).substring(n4);
            n2 += n4;
            try {
                string2 = URLEncoder.encode(string2, "UTF-8");
                linkSpec.url = "geo:0,0?q=" + string2;
                arrayList.add(linkSpec);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
            }
            continue;
            break;
        }
    }

    private static String makeUrl(@NonNull String string2, @NonNull String[] stringArray, Matcher object, @Nullable Linkify.TransformFilter transformFilter) {
        String string3 = string2;
        if (transformFilter != null) {
            string3 = transformFilter.transformUrl((Matcher)object, string2);
        }
        boolean bl2 = false;
        int n2 = 0;
        while (true) {
            block9: {
                boolean bl3;
                block8: {
                    bl3 = bl2;
                    string2 = string3;
                    if (n2 >= stringArray.length) break block8;
                    if (!string3.regionMatches(true, 0, stringArray[n2], 0, stringArray[n2].length())) break block9;
                    bl3 = bl2 = true;
                    string2 = string3;
                    if (!string3.regionMatches(false, 0, stringArray[n2], 0, stringArray[n2].length())) {
                        string2 = stringArray[n2] + string3.substring(stringArray[n2].length());
                        bl3 = bl2;
                    }
                }
                object = string2;
                if (!bl3) {
                    object = string2;
                    if (stringArray.length > 0) {
                        object = stringArray[0] + string2;
                    }
                }
                return object;
            }
            ++n2;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final void pruneOverlaps(ArrayList<LinkSpec> arrayList, Spannable spannable) {
        LinkSpec linkSpec;
        int n2;
        URLSpan uRLSpan = (URLSpan[])spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (n2 = 0; n2 < ((URLSpan[])uRLSpan).length; ++n2) {
            linkSpec = new LinkSpec();
            linkSpec.frameworkAddedSpan = uRLSpan[n2];
            linkSpec.start = spannable.getSpanStart((Object)uRLSpan[n2]);
            linkSpec.end = spannable.getSpanEnd((Object)uRLSpan[n2]);
            arrayList.add(linkSpec);
        }
        Collections.sort(arrayList, COMPARATOR);
        int n3 = arrayList.size();
        int n4 = 0;
        while (n4 < n3 - 1) {
            uRLSpan = arrayList.get(n4);
            linkSpec = arrayList.get(n4 + 1);
            n2 = -1;
            if (uRLSpan.start <= linkSpec.start && uRLSpan.end > linkSpec.start) {
                if (linkSpec.end <= uRLSpan.end) {
                    n2 = n4 + 1;
                } else if (uRLSpan.end - uRLSpan.start > linkSpec.end - linkSpec.start) {
                    n2 = n4 + 1;
                } else if (uRLSpan.end - uRLSpan.start < linkSpec.end - linkSpec.start) {
                    n2 = n4;
                }
                if (n2 != -1) {
                    uRLSpan = arrayList.get((int)n2).frameworkAddedSpan;
                    if (uRLSpan != null) {
                        spannable.removeSpan((Object)uRLSpan);
                    }
                    arrayList.remove(n2);
                    --n3;
                    continue;
                }
            }
            ++n4;
        }
        return;
    }

    private static class LinkSpec {
        int end;
        URLSpan frameworkAddedSpan;
        int start;
        String url;

        LinkSpec() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LinkifyMask {
    }
}

