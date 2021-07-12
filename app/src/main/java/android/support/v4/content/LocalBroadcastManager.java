/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Message
 *  android.util.Log
 */
package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public final class LocalBroadcastManager {
    private static final boolean DEBUG = false;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final String TAG = "LocalBroadcastManager";
    private static LocalBroadcastManager mInstance;
    private static final Object mLock;
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions;
    private final Context mAppContext;
    private final Handler mHandler;
    private final ArrayList<BroadcastRecord> mPendingBroadcasts;
    private final HashMap<BroadcastReceiver, ArrayList<IntentFilter>> mReceivers = new HashMap();

    static {
        mLock = new Object();
    }

    private LocalBroadcastManager(Context context) {
        this.mActions = new HashMap();
        this.mPendingBroadcasts = new ArrayList();
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()){

            public void handleMessage(Message message) {
                switch (message.what) {
                    default: {
                        super.handleMessage(message);
                        return;
                    }
                    case 1: 
                }
                LocalBroadcastManager.this.executePendingBroadcasts();
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void executePendingBroadcasts() {
        block3: while (true) {
            BroadcastRecord[] broadcastRecordArray;
            int n2;
            Object object = this.mReceivers;
            synchronized (object) {
                n2 = this.mPendingBroadcasts.size();
                if (n2 <= 0) {
                    return;
                }
                broadcastRecordArray = new BroadcastRecord[n2];
                this.mPendingBroadcasts.toArray(broadcastRecordArray);
                this.mPendingBroadcasts.clear();
            }
            n2 = 0;
            while (true) {
                if (n2 >= broadcastRecordArray.length) continue block3;
                object = broadcastRecordArray[n2];
                for (int i2 = 0; i2 < ((BroadcastRecord)object).receivers.size(); ++i2) {
                    object.receivers.get((int)i2).receiver.onReceive(this.mAppContext, ((BroadcastRecord)object).intent);
                }
                ++n2;
            }
            break;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static LocalBroadcastManager getInstance(Context object) {
        Object object2 = mLock;
        synchronized (object2) {
            if (mInstance != null) return mInstance;
            mInstance = new LocalBroadcastManager(object.getApplicationContext());
            return mInstance;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerReceiver(BroadcastReceiver object, IntentFilter intentFilter) {
        HashMap<BroadcastReceiver, ArrayList<IntentFilter>> hashMap = this.mReceivers;
        synchronized (hashMap) {
            ReceiverRecord receiverRecord = new ReceiverRecord(intentFilter, (BroadcastReceiver)object);
            Object object2 = this.mReceivers.get(object);
            ArrayList<Object> arrayList = object2;
            if (object2 == null) {
                arrayList = new ArrayList(1);
                this.mReceivers.put((BroadcastReceiver)object, arrayList);
            }
            arrayList.add(intentFilter);
            int n2 = 0;
            while (n2 < intentFilter.countActions()) {
                object2 = intentFilter.getAction(n2);
                arrayList = this.mActions.get(object2);
                object = arrayList;
                if (arrayList == null) {
                    object = new ArrayList(1);
                    this.mActions.put((String)object2, (ArrayList<ReceiverRecord>)object);
                }
                ((ArrayList)object).add(receiverRecord);
                ++n2;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean sendBroadcast(Intent intent) {
        HashMap<BroadcastReceiver, ArrayList<IntentFilter>> hashMap = this.mReceivers;
        synchronized (hashMap) {
            ArrayList arrayList;
            int n2;
            block29: {
                block28: {
                    ArrayList<ReceiverRecord> arrayList2;
                    String string2 = intent.getAction();
                    String string3 = intent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
                    Uri uri = intent.getData();
                    String string4 = intent.getScheme();
                    Set set = intent.getCategories();
                    n2 = (intent.getFlags() & 8) != 0 ? 1 : 0;
                    if (n2 != 0) {
                        Log.v((String)TAG, (String)("Resolving type " + string3 + " scheme " + string4 + " of intent " + intent));
                    }
                    if ((arrayList2 = this.mActions.get(intent.getAction())) == null) break block28;
                    if (n2 != 0) {
                        Log.v((String)TAG, (String)("Action list: " + arrayList2));
                    }
                    arrayList = null;
                    for (int i2 = 0; i2 < arrayList2.size(); ++i2) {
                        ArrayList arrayList3;
                        ReceiverRecord receiverRecord = arrayList2.get(i2);
                        if (n2 != 0) {
                            Log.v((String)TAG, (String)("Matching against filter " + receiverRecord.filter));
                        }
                        if (receiverRecord.broadcasting) {
                            arrayList3 = arrayList;
                            if (n2 != 0) {
                                Log.v((String)TAG, (String)"  Filter's target already added");
                                arrayList3 = arrayList;
                            }
                        } else {
                            int n3 = receiverRecord.filter.match(string2, string3, string4, uri, set, TAG);
                            if (n3 >= 0) {
                                if (n2 != 0) {
                                    Log.v((String)TAG, (String)("  Filter matched!  match=0x" + Integer.toHexString(n3)));
                                }
                                arrayList3 = arrayList;
                                if (arrayList == null) {
                                    arrayList3 = new ArrayList();
                                }
                                arrayList3.add(receiverRecord);
                                receiverRecord.broadcasting = true;
                            } else {
                                arrayList3 = arrayList;
                                if (n2 != 0) {
                                    switch (n3) {
                                        default: {
                                            arrayList3 = "unknown reason";
                                            break;
                                        }
                                        case -3: {
                                            arrayList3 = "action";
                                            break;
                                        }
                                        case -4: {
                                            arrayList3 = "category";
                                            break;
                                        }
                                        case -2: {
                                            arrayList3 = "data";
                                            break;
                                        }
                                        case -1: {
                                            arrayList3 = "type";
                                        }
                                    }
                                    Log.v((String)TAG, (String)("  Filter did not match: " + arrayList3));
                                    arrayList3 = arrayList;
                                }
                            }
                        }
                        arrayList = arrayList3;
                    }
                    if (arrayList != null) break block29;
                }
                return false;
            }
            for (n2 = 0; n2 < arrayList.size(); ++n2) {
                ((ReceiverRecord)arrayList.get((int)n2)).broadcasting = false;
            }
            this.mPendingBroadcasts.add(new BroadcastRecord(intent, arrayList));
            if (!this.mHandler.hasMessages(1)) {
                this.mHandler.sendEmptyMessage(1);
            }
            return true;
        }
    }

    public void sendBroadcastSync(Intent intent) {
        if (this.sendBroadcast(intent)) {
            this.executePendingBroadcasts();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        HashMap<BroadcastReceiver, ArrayList<IntentFilter>> hashMap = this.mReceivers;
        synchronized (hashMap) {
            ArrayList<IntentFilter> arrayList = this.mReceivers.remove(broadcastReceiver);
            if (arrayList == null) {
                return;
            }
            int n2 = 0;
            block3: while (n2 < arrayList.size()) {
                IntentFilter intentFilter = arrayList.get(n2);
                int n3 = 0;
                while (true) {
                    block12: {
                        ArrayList<ReceiverRecord> arrayList2;
                        String string2;
                        block13: {
                            block11: {
                                if (n3 >= intentFilter.countActions()) break block11;
                                string2 = intentFilter.getAction(n3);
                                arrayList2 = this.mActions.get(string2);
                                if (arrayList2 == null) break block12;
                                break block13;
                            }
                            ++n2;
                            continue block3;
                        }
                        int n4 = 0;
                        while (true) {
                            int n5;
                            if (n4 < arrayList2.size()) {
                                n5 = n4;
                                if (arrayList2.get((int)n4).receiver == broadcastReceiver) {
                                    arrayList2.remove(n4);
                                    n5 = n4 - 1;
                                }
                            } else {
                                if (arrayList2.size() > 0) break;
                                this.mActions.remove(string2);
                                break;
                            }
                            n4 = n5 + 1;
                        }
                    }
                    ++n3;
                }
                break;
            }
            return;
        }
    }

    private static class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        BroadcastRecord(Intent intent, ArrayList<ReceiverRecord> arrayList) {
            this.intent = intent;
            this.receivers = arrayList;
        }
    }

    private static class ReceiverRecord {
        boolean broadcasting;
        final IntentFilter filter;
        final BroadcastReceiver receiver;

        ReceiverRecord(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.filter = intentFilter;
            this.receiver = broadcastReceiver;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("Receiver{");
            stringBuilder.append(this.receiver);
            stringBuilder.append(" filter=");
            stringBuilder.append(this.filter);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }
}

