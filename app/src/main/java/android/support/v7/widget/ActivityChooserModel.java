/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ResolveInfo
 *  android.database.DataSetObservable
 *  android.os.AsyncTask
 *  android.text.TextUtils
 *  android.util.Log
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v7.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

class ActivityChooserModel
extends DataSetObservable {
    private static final String ATTRIBUTE_ACTIVITY = "activity";
    private static final String ATTRIBUTE_TIME = "time";
    private static final String ATTRIBUTE_WEIGHT = "weight";
    private static final boolean DEBUG = false;
    private static final int DEFAULT_ACTIVITY_INFLATION = 5;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final int INVALID_INDEX = -1;
    private static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
    private static final String TAG_HISTORICAL_RECORD = "historical-record";
    private static final String TAG_HISTORICAL_RECORDS = "historical-records";
    private static final Map<String, ActivityChooserModel> sDataModelRegistry;
    private static final Object sRegistryLock;
    private final List<ActivityResolveInfo> mActivities;
    private OnChooseActivityListener mActivityChoserModelPolicy;
    private ActivitySorter mActivitySorter;
    private boolean mCanReadHistoricalData = true;
    private final Context mContext;
    private final List<HistoricalRecord> mHistoricalRecords;
    private boolean mHistoricalRecordsChanged = true;
    private final String mHistoryFileName;
    private int mHistoryMaxSize = 50;
    private final Object mInstanceLock = new Object();
    private Intent mIntent;
    private boolean mReadShareHistoryCalled = false;
    private boolean mReloadActivities = false;

    static {
        sRegistryLock = new Object();
        sDataModelRegistry = new HashMap<String, ActivityChooserModel>();
    }

    private ActivityChooserModel(Context context, String string2) {
        this.mActivities = new ArrayList<ActivityResolveInfo>();
        this.mHistoricalRecords = new ArrayList<HistoricalRecord>();
        this.mActivitySorter = new DefaultSorter();
        this.mContext = context.getApplicationContext();
        if (!TextUtils.isEmpty((CharSequence)string2) && !string2.endsWith(HISTORY_FILE_EXTENSION)) {
            this.mHistoryFileName = string2 + HISTORY_FILE_EXTENSION;
            return;
        }
        this.mHistoryFileName = string2;
    }

    static /* synthetic */ boolean access$502(ActivityChooserModel activityChooserModel, boolean bl2) {
        activityChooserModel.mCanReadHistoricalData = bl2;
        return bl2;
    }

    private boolean addHisoricalRecord(HistoricalRecord historicalRecord) {
        boolean bl2 = this.mHistoricalRecords.add(historicalRecord);
        if (bl2) {
            this.mHistoricalRecordsChanged = true;
            this.pruneExcessiveHistoricalRecordsIfNeeded();
            this.persistHistoricalDataIfNeeded();
            this.sortActivitiesIfNeeded();
            this.notifyChanged();
        }
        return bl2;
    }

    private void ensureConsistentState() {
        boolean bl2 = this.loadActivitiesIfNeeded();
        boolean bl3 = this.readHistoricalDataIfNeeded();
        this.pruneExcessiveHistoricalRecordsIfNeeded();
        if (bl2 | bl3) {
            this.sortActivitiesIfNeeded();
            this.notifyChanged();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ActivityChooserModel get(Context context, String string2) {
        Object object = sRegistryLock;
        synchronized (object) {
            ActivityChooserModel activityChooserModel;
            ActivityChooserModel activityChooserModel2 = activityChooserModel = sDataModelRegistry.get(string2);
            if (activityChooserModel == null) {
                activityChooserModel2 = new ActivityChooserModel(context, string2);
                sDataModelRegistry.put(string2, activityChooserModel2);
            }
            return activityChooserModel2;
        }
    }

    private boolean loadActivitiesIfNeeded() {
        boolean bl2;
        boolean bl3 = bl2 = false;
        if (this.mReloadActivities) {
            bl3 = bl2;
            if (this.mIntent != null) {
                this.mReloadActivities = false;
                this.mActivities.clear();
                List list = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
                int n2 = list.size();
                for (int i2 = 0; i2 < n2; ++i2) {
                    ResolveInfo resolveInfo = (ResolveInfo)list.get(i2);
                    this.mActivities.add(new ActivityResolveInfo(resolveInfo));
                }
                bl3 = true;
            }
        }
        return bl3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void persistHistoricalDataIfNeeded() {
        block5: {
            block4: {
                if (!this.mReadShareHistoryCalled) {
                    throw new IllegalStateException("No preceding call to #readHistoricalData");
                }
                if (!this.mHistoricalRecordsChanged) break block4;
                this.mHistoricalRecordsChanged = false;
                if (!TextUtils.isEmpty((CharSequence)this.mHistoryFileName)) break block5;
            }
            return;
        }
        AsyncTaskCompat.executeParallel(new PersistHistoryAsyncTask(), new ArrayList<HistoricalRecord>(this.mHistoricalRecords), this.mHistoryFileName);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        int n2 = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        if (n2 > 0) {
            this.mHistoricalRecordsChanged = true;
            for (int i2 = 0; i2 < n2; ++i2) {
                HistoricalRecord historicalRecord = this.mHistoricalRecords.remove(0);
            }
        }
    }

    private boolean readHistoricalDataIfNeeded() {
        if (this.mCanReadHistoricalData && this.mHistoricalRecordsChanged && !TextUtils.isEmpty((CharSequence)this.mHistoryFileName)) {
            this.mCanReadHistoricalData = false;
            this.mReadShareHistoryCalled = true;
            this.readHistoricalDataImpl();
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void readHistoricalDataImpl() {
        block23: {
            var2_1 = this.mContext.openFileInput(this.mHistoryFileName);
            try {}
            catch (Throwable var3_10) {
                if (var2_1 == null) throw var3_10;
                try {
                    var2_1.close();
                    throw var3_10;
                }
                catch (IOException var2_5) {
                    throw var3_10;
                }
            }
            ** try [egrp 2[TRYBLOCK] [1, 2 : 12->73)] { 
lbl13:
            // 1 sources

            break block23;
lbl14:
            // 1 sources

            catch (XmlPullParserException var3_8) {
                Log.e((String)ActivityChooserModel.LOG_TAG, (String)("Error reading historical recrod file: " + this.mHistoryFileName), (Throwable)var3_8);
                if (var2_1 == null) return;
                try {
                    var2_1.close();
                    return;
                }
                catch (IOException var2_2) {
                    return;
                }
            }
lbl23:
            // 1 sources

            catch (IOException var3_9) {
                Log.e((String)ActivityChooserModel.LOG_TAG, (String)("Error reading historical recrod file: " + this.mHistoryFileName), (Throwable)var3_9);
                if (var2_1 == null) return;
                try {
                    var2_1.close();
                    return;
                }
                catch (IOException var2_4) {
                    return;
                }
            }
lbl32:
            // 1 sources

            while (true) {
                if ((var1_11 = var3_7.next()) == 1) {
                    if (var2_1 == null) return;
                    try {
                        var2_1.close();
                        return;
                    }
                    catch (IOException var2_3) {
                        return;
                    }
                }
                if (var1_11 == 3 || var1_11 == 4) continue;
                {
                    if ("historical-record".equals(var3_7.getName())) ** GOTO lbl-1000
                    throw new XmlPullParserException("Share records file not well-formed.");
                }
lbl-1000:
                // 1 sources

                {
                    var4_12.add(new HistoricalRecord(var3_7.getAttributeValue(null, "activity"), Long.parseLong(var3_7.getAttributeValue(null, "time")), Float.parseFloat(var3_7.getAttributeValue(null, "weight"))));
                    continue;
                }
                break;
            }
            catch (FileNotFoundException var2_6) {
                // empty catch block
                return;
            }
        }
        var3_7 = Xml.newPullParser();
        var3_7.setInput((InputStream)var2_1, "UTF-8");
        var1_11 = 0;
        while (var1_11 != 1 && var1_11 != 2) {
            var1_11 = var3_7.next();
        }
        if (!"historical-records".equals(var3_7.getName())) {
            throw new XmlPullParserException("Share records file does not start with historical-records tag.");
        }
        var4_12 = this.mHistoricalRecords;
        var4_12.clear();
        ** while (true)
    }

    private boolean sortActivitiesIfNeeded() {
        if (this.mActivitySorter != null && this.mIntent != null && !this.mActivities.isEmpty() && !this.mHistoricalRecords.isEmpty()) {
            this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Intent chooseActivity(int n2) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            Intent intent;
            if (this.mIntent == null) {
                return null;
            }
            this.ensureConsistentState();
            ActivityResolveInfo activityResolveInfo = this.mActivities.get(n2);
            activityResolveInfo = new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name);
            Intent intent2 = new Intent(this.mIntent);
            intent2.setComponent((ComponentName)activityResolveInfo);
            if (this.mActivityChoserModelPolicy != null && this.mActivityChoserModelPolicy.onChooseActivity(this, intent = new Intent(intent2))) {
                return null;
            }
            this.addHisoricalRecord(new HistoricalRecord((ComponentName)activityResolveInfo, System.currentTimeMillis(), 1.0f));
            return intent2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ResolveInfo getActivity(int n2) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            return this.mActivities.get((int)n2).resolveInfo;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getActivityCount() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            return this.mActivities.size();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getActivityIndex(ResolveInfo resolveInfo) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            List<ActivityResolveInfo> list = this.mActivities;
            int n2 = list.size();
            int n3 = 0;
            while (n3 < n2) {
                if (list.get((int)n3).resolveInfo == resolveInfo) {
                    return n3;
                }
                ++n3;
            }
            return -1;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ResolveInfo getDefaultActivity() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            if (this.mActivities.isEmpty()) return null;
            return this.mActivities.get((int)0).resolveInfo;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getHistoryMaxSize() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            return this.mHistoryMaxSize;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getHistorySize() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            return this.mHistoricalRecords.size();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Intent getIntent() {
        Object object = this.mInstanceLock;
        synchronized (object) {
            return this.mIntent;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setActivitySorter(ActivitySorter activitySorter) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            if (this.mActivitySorter == activitySorter) {
                return;
            }
            this.mActivitySorter = activitySorter;
            if (this.sortActivitiesIfNeeded()) {
                this.notifyChanged();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setDefaultActivity(int n2) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.ensureConsistentState();
            ActivityResolveInfo activityResolveInfo = this.mActivities.get(n2);
            ActivityResolveInfo activityResolveInfo2 = this.mActivities.get(0);
            float f2 = activityResolveInfo2 != null ? activityResolveInfo2.weight - activityResolveInfo.weight + 5.0f : 1.0f;
            this.addHisoricalRecord(new HistoricalRecord(new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name), System.currentTimeMillis(), f2));
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setHistoryMaxSize(int n2) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            if (this.mHistoryMaxSize == n2) {
                return;
            }
            this.mHistoryMaxSize = n2;
            this.pruneExcessiveHistoricalRecordsIfNeeded();
            if (this.sortActivitiesIfNeeded()) {
                this.notifyChanged();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setIntent(Intent intent) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            if (this.mIntent == intent) {
                return;
            }
            this.mIntent = intent;
            this.mReloadActivities = true;
            this.ensureConsistentState();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnChooseActivityListener(OnChooseActivityListener onChooseActivityListener) {
        Object object = this.mInstanceLock;
        synchronized (object) {
            this.mActivityChoserModelPolicy = onChooseActivityListener;
            return;
        }
    }

    public static interface ActivityChooserModelClient {
        public void setActivityChooserModel(ActivityChooserModel var1);
    }

    public final class ActivityResolveInfo
    implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo resolveInfo) {
            this.resolveInfo = resolveInfo;
        }

        @Override
        public int compareTo(ActivityResolveInfo activityResolveInfo) {
            return Float.floatToIntBits(activityResolveInfo.weight) - Float.floatToIntBits(this.weight);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block6: {
                block5: {
                    if (this == object) break block5;
                    if (object == null) {
                        return false;
                    }
                    if (this.getClass() != object.getClass()) {
                        return false;
                    }
                    object = (ActivityResolveInfo)object;
                    if (Float.floatToIntBits(this.weight) != Float.floatToIntBits(((ActivityResolveInfo)object).weight)) break block6;
                }
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Float.floatToIntBits(this.weight) + 31;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append("resolveInfo:").append(this.resolveInfo.toString());
            stringBuilder.append("; weight:").append(new BigDecimal(this.weight));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    public static interface ActivitySorter {
        public void sort(Intent var1, List<ActivityResolveInfo> var2, List<HistoricalRecord> var3);
    }

    private final class DefaultSorter
    implements ActivitySorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final Map<ComponentName, ActivityResolveInfo> mPackageNameToActivityMap = new HashMap<ComponentName, ActivityResolveInfo>();

        private DefaultSorter() {
        }

        @Override
        public void sort(Intent object, List<ActivityResolveInfo> list, List<HistoricalRecord> list2) {
            Object object2;
            int n2;
            object = this.mPackageNameToActivityMap;
            object.clear();
            int n3 = list.size();
            for (n2 = 0; n2 < n3; ++n2) {
                object2 = list.get(n2);
                ((ActivityResolveInfo)object2).weight = 0.0f;
                object.put(new ComponentName(object2.resolveInfo.activityInfo.packageName, object2.resolveInfo.activityInfo.name), object2);
            }
            n2 = list2.size();
            float f2 = 1.0f;
            --n2;
            while (n2 >= 0) {
                object2 = list2.get(n2);
                ActivityResolveInfo activityResolveInfo = (ActivityResolveInfo)object.get(((HistoricalRecord)object2).activity);
                float f3 = f2;
                if (activityResolveInfo != null) {
                    activityResolveInfo.weight += ((HistoricalRecord)object2).weight * f2;
                    f3 = f2 * 0.95f;
                }
                --n2;
                f2 = f3;
            }
            Collections.sort(list);
        }
    }

    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(ComponentName componentName, long l2, float f2) {
            this.activity = componentName;
            this.time = l2;
            this.weight = f2;
        }

        public HistoricalRecord(String string2, long l2, float f2) {
            this(ComponentName.unflattenFromString((String)string2), l2, f2);
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block8: {
                block7: {
                    if (this == object) break block7;
                    if (object == null) {
                        return false;
                    }
                    if (this.getClass() != object.getClass()) {
                        return false;
                    }
                    object = (HistoricalRecord)object;
                    if (this.activity == null ? ((HistoricalRecord)object).activity != null : !this.activity.equals((Object)((HistoricalRecord)object).activity)) {
                        return false;
                    }
                    if (this.time != ((HistoricalRecord)object).time) {
                        return false;
                    }
                    if (Float.floatToIntBits(this.weight) != Float.floatToIntBits(((HistoricalRecord)object).weight)) break block8;
                }
                return true;
            }
            return false;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int hashCode() {
            int n2;
            if (this.activity == null) {
                n2 = 0;
                return ((n2 + 31) * 31 + (int)(this.time ^ this.time >>> 32)) * 31 + Float.floatToIntBits(this.weight);
            }
            n2 = this.activity.hashCode();
            return ((n2 + 31) * 31 + (int)(this.time ^ this.time >>> 32)) * 31 + Float.floatToIntBits(this.weight);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append("; activity:").append(this.activity);
            stringBuilder.append("; time:").append(this.time);
            stringBuilder.append("; weight:").append(new BigDecimal(this.weight));
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    public static interface OnChooseActivityListener {
        public boolean onChooseActivity(ActivityChooserModel var1, Intent var2);
    }

    private final class PersistHistoryAsyncTask
    extends AsyncTask<Object, Void, Void> {
        private PersistHistoryAsyncTask() {
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public Void doInBackground(Object ... object) {
            List list = (List)object[0];
            String string2 = (String)object[1];
            object = ActivityChooserModel.this.mContext.openFileOutput(string2, 0);
            string2 = Xml.newSerializer();
            try {
                block23: {
                    string2.setOutput((OutputStream)object, null);
                    string2.startDocument("UTF-8", Boolean.valueOf(true));
                    string2.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                    int n2 = list.size();
                    for (int i2 = 0; i2 < n2; ++i2) {
                        HistoricalRecord historicalRecord = (HistoricalRecord)list.remove(0);
                        string2.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                        string2.attribute(null, ActivityChooserModel.ATTRIBUTE_ACTIVITY, historicalRecord.activity.flattenToString());
                        string2.attribute(null, ActivityChooserModel.ATTRIBUTE_TIME, String.valueOf(historicalRecord.time));
                        string2.attribute(null, ActivityChooserModel.ATTRIBUTE_WEIGHT, String.valueOf(historicalRecord.weight));
                        string2.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                    }
                    break block23;
                    catch (FileNotFoundException fileNotFoundException) {
                        Log.e((String)LOG_TAG, (String)("Error writing historical recrod file: " + string2), (Throwable)fileNotFoundException);
                        return null;
                    }
                }
                string2.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                string2.endDocument();
                return null;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.e((String)LOG_TAG, (String)("Error writing historical recrod file: " + ActivityChooserModel.this.mHistoryFileName), (Throwable)illegalArgumentException);
                return null;
            }
            catch (IllegalStateException illegalStateException) {
                Log.e((String)LOG_TAG, (String)("Error writing historical recrod file: " + ActivityChooserModel.this.mHistoryFileName), (Throwable)illegalStateException);
                return null;
                {
                    catch (Throwable throwable) {
                        throw throwable;
                    }
                }
                catch (IOException iOException) {
                    Log.e((String)LOG_TAG, (String)("Error writing historical recrod file: " + ActivityChooserModel.this.mHistoryFileName), (Throwable)iOException);
                    return null;
                }
            }
            finally {
                ActivityChooserModel.access$502(ActivityChooserModel.this, true);
                if (object == null) return null;
                ((FileOutputStream)object).close();
                return null;
            }
        }
    }
}

