/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonObjectRequest
extends JsonRequest<JSONObject> {
    public JsonObjectRequest(int n2, String string2, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(n2, string2, null, listener, errorListener);
    }

    public JsonObjectRequest(int n2, String string2, String string3, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(n2, string2, string3, listener, errorListener);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public JsonObjectRequest(int n2, String string2, JSONObject object, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        void var5_8;
        void var4_7;
        void var3_5;
        if (object == null) {
            Object var3_4 = null;
        } else {
            String string3 = object.toString();
        }
        super(n2, string2, (String)var3_5, var4_7, (Response.ErrorListener)var5_8);
    }

    public JsonObjectRequest(String string2, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(0, string2, null, listener, errorListener);
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonObjectRequest(String string2, JSONObject jSONObject, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        int n2 = jSONObject == null ? 0 : 1;
        this(n2, string2, jSONObject, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse object) {
        try {
            object = Response.success(new JSONObject(new String(((NetworkResponse)object).data, HttpHeaderParser.parseCharset(((NetworkResponse)object).headers, "utf-8"))), HttpHeaderParser.parseCacheHeaders((NetworkResponse)object));
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return Response.error(new ParseError(unsupportedEncodingException));
        }
        catch (JSONException jSONException) {
            return Response.error(new ParseError(jSONException));
        }
    }
}

