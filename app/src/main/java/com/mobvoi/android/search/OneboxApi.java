/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.mobvoi.android.search;

import com.mobvoi.android.common.api.MobvoiApiClient;
import com.mobvoi.android.common.api.PendingResult;
import com.mobvoi.android.common.api.Result;
import com.mobvoi.android.search.OneboxRequest;
import org.json.JSONArray;
import org.json.JSONObject;

public interface OneboxApi {
    public PendingResult<OneboxResult> fetchOneboxResult(MobvoiApiClient var1, OneboxRequest var2);

    public static interface OneboxResult
    extends Result {
        public String getMsgId();

        public JSONArray getResponse();

        public JSONObject getSemantic();
    }
}

