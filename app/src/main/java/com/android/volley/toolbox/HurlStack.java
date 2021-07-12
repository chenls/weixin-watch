/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.apache.http.Header
 *  org.apache.http.HttpEntity
 *  org.apache.http.HttpResponse
 *  org.apache.http.ProtocolVersion
 *  org.apache.http.StatusLine
 *  org.apache.http.entity.BasicHttpEntity
 *  org.apache.http.message.BasicHeader
 *  org.apache.http.message.BasicHttpResponse
 *  org.apache.http.message.BasicStatusLine
 */
package com.android.volley.toolbox;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpStack;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class HurlStack
implements HttpStack {
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private final SSLSocketFactory mSslSocketFactory;
    private final UrlRewriter mUrlRewriter;

    public HurlStack() {
        this(null);
    }

    public HurlStack(UrlRewriter urlRewriter) {
        this(urlRewriter, null);
    }

    public HurlStack(UrlRewriter urlRewriter, SSLSocketFactory sSLSocketFactory) {
        this.mUrlRewriter = urlRewriter;
        this.mSslSocketFactory = sSLSocketFactory;
    }

    private static void addBodyIfExists(HttpURLConnection object, Request<?> request) throws IOException, AuthFailureError {
        byte[] byArray = request.getBody();
        if (byArray != null) {
            ((URLConnection)object).setDoOutput(true);
            ((URLConnection)object).addRequestProperty(HEADER_CONTENT_TYPE, request.getBodyContentType());
            object = new DataOutputStream(((URLConnection)object).getOutputStream());
            ((FilterOutputStream)object).write(byArray);
            ((FilterOutputStream)object).close();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static HttpEntity entityFromConnection(HttpURLConnection httpURLConnection) {
        InputStream inputStream;
        BasicHttpEntity basicHttpEntity = new BasicHttpEntity();
        try {
            inputStream = httpURLConnection.getInputStream();
        }
        catch (IOException iOException) {
            inputStream = httpURLConnection.getErrorStream();
        }
        basicHttpEntity.setContent(inputStream);
        basicHttpEntity.setContentLength((long)httpURLConnection.getContentLength());
        basicHttpEntity.setContentEncoding(httpURLConnection.getContentEncoding());
        basicHttpEntity.setContentType(httpURLConnection.getContentType());
        return basicHttpEntity;
    }

    private static boolean hasResponseBody(int n2, int n3) {
        return n2 != 4 && (100 > n3 || n3 >= 200) && n3 != 204 && n3 != 304;
    }

    private HttpURLConnection openConnection(URL uRL, Request<?> request) throws IOException {
        HttpURLConnection httpURLConnection = this.createConnection(uRL);
        int n2 = request.getTimeoutMs();
        httpURLConnection.setConnectTimeout(n2);
        httpURLConnection.setReadTimeout(n2);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        if ("https".equals(uRL.getProtocol()) && this.mSslSocketFactory != null) {
            ((HttpsURLConnection)httpURLConnection).setSSLSocketFactory(this.mSslSocketFactory);
        }
        return httpURLConnection;
    }

    static void setConnectionParametersForRequest(HttpURLConnection object, Request<?> request) throws IOException, AuthFailureError {
        switch (request.getMethod()) {
            default: {
                throw new IllegalStateException("Unknown method type.");
            }
            case -1: {
                byte[] byArray = request.getPostBody();
                if (byArray != null) {
                    ((URLConnection)object).setDoOutput(true);
                    ((HttpURLConnection)object).setRequestMethod("POST");
                    ((URLConnection)object).addRequestProperty(HEADER_CONTENT_TYPE, request.getPostBodyContentType());
                    object = new DataOutputStream(((URLConnection)object).getOutputStream());
                    ((FilterOutputStream)object).write(byArray);
                    ((FilterOutputStream)object).close();
                }
                return;
            }
            case 0: {
                ((HttpURLConnection)object).setRequestMethod("GET");
                return;
            }
            case 3: {
                ((HttpURLConnection)object).setRequestMethod("DELETE");
                return;
            }
            case 1: {
                ((HttpURLConnection)object).setRequestMethod("POST");
                HurlStack.addBodyIfExists((HttpURLConnection)object, request);
                return;
            }
            case 2: {
                ((HttpURLConnection)object).setRequestMethod("PUT");
                HurlStack.addBodyIfExists((HttpURLConnection)object, request);
                return;
            }
            case 4: {
                ((HttpURLConnection)object).setRequestMethod("HEAD");
                return;
            }
            case 5: {
                ((HttpURLConnection)object).setRequestMethod("OPTIONS");
                return;
            }
            case 6: {
                ((HttpURLConnection)object).setRequestMethod("TRACE");
                return;
            }
            case 7: 
        }
        ((HttpURLConnection)object).setRequestMethod("PATCH");
        HurlStack.addBodyIfExists((HttpURLConnection)object, request);
    }

    protected HttpURLConnection createConnection(URL uRL) throws IOException {
        return (HttpURLConnection)uRL.openConnection();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public HttpResponse performRequest(Request<?> object, Map<String, String> object22) throws IOException, AuthFailureError {
        void var2_5;
        String string2;
        Object object2 = ((Request)((Object)object)).getUrl();
        BasicStatusLine basicStatusLine = new HashMap();
        basicStatusLine.putAll(((Request)((Object)object)).getHeaders());
        basicStatusLine.putAll(object22);
        String string3 = object2;
        if (this.mUrlRewriter != null && (string2 = this.mUrlRewriter.rewriteUrl((String)object2)) == null) {
            throw new IOException("URL blocked by rewriter: " + (String)object2);
        }
        HttpURLConnection httpURLConnection = this.openConnection(new URL((String)var2_5), (Request<?>)((Object)object));
        for (String string4 : basicStatusLine.keySet()) {
            httpURLConnection.addRequestProperty(string4, (String)basicStatusLine.get(string4));
        }
        HurlStack.setConnectionParametersForRequest(httpURLConnection, object);
        object2 = new ProtocolVersion("HTTP", 1, 1);
        if (httpURLConnection.getResponseCode() == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        basicStatusLine = new BasicStatusLine((ProtocolVersion)object2, httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
        object2 = new BasicHttpResponse((StatusLine)basicStatusLine);
        if (HurlStack.hasResponseBody(((Request)((Object)object)).getMethod(), basicStatusLine.getStatusCode())) {
            object2.setEntity(HurlStack.entityFromConnection(httpURLConnection));
        }
        for (Map.Entry<String, List<String>> entry : httpURLConnection.getHeaderFields().entrySet()) {
            if (entry.getKey() == null) continue;
            object2.addHeader((Header)new BasicHeader(entry.getKey(), entry.getValue().get(0)));
        }
        return object2;
    }

    public static interface UrlRewriter {
        public String rewriteUrl(String var1);
    }
}

