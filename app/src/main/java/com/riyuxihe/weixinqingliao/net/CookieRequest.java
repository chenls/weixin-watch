package com.riyuxihe.weixinqingliao.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.riyuxihe.weixinqingliao.model.Token;
import com.riyuxihe.weixinqingliao.util.Constants;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class CookieRequest extends JsonRequest<JSONObject> {
    private final Map<String, String> mHeaders = new HashMap();

    public CookieRequest(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, null, listener, errorListener);
    }

    public CookieRequest(int method, String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(
                            response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(
                    new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    public void setCookie(String cookie) {
        this.mHeaders.put(Token.COOKIE, cookie);
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        this.mHeaders.put("User-Agent", Constants.USER_AGENT);
        this.mHeaders.put("Referer", "https://wx2.qq.com/");
        return this.mHeaders;
    }
}
