/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.util.Log
 */
package mobvoiapi;

import android.net.Uri;
import android.util.Log;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import mobvoiapi.ay;
import mobvoiapi.bp;

public class ba {
    private byte[] a = null;
    private final ServerSocket b;
    private Thread c;

    public ba(InputStream inputStream) throws IOException {
        this.a = ay.a(inputStream);
        this.b = new ServerSocket(0);
        this.c = new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    while (true) {
                        Socket socket = ba.this.b.accept();
                        new a(socket);
                    }
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                    return;
                }
            }
        });
        this.c.setName("Stream over HTTP");
        this.c.setDaemon(true);
        this.c.start();
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void a(Socket var1_1, String var2_5, String var3_8, Properties var4_9) {
        try {
            try {
                var5_10 = var1_1.getOutputStream();
                var6_11 = new PrintWriter(var5_10);
                var6_11.print("HTTP/1.0 " + (String)var2_5 + " \r\n");
                if (var3_8 != null) {
                    var6_11.print("Content-Type: " + var3_8 + "\r\n");
                }
                if (var4_9 != null) {
                    var2_5 = var4_9.keys();
                    while (var2_5.hasMoreElements()) {
                        var3_8 = (String)var2_5.nextElement();
                        var7_12 = var4_9.getProperty(var3_8);
                        var3_8 = var3_8 + ": " + var7_12 + "\r\n";
                        bp.a("StreamOverHttp", var3_8);
                        var6_11.print(var3_8);
                    }
                }
                ** GOTO lbl-1000
            }
            catch (IOException var2_6) {
                bp.a("StreamOverHttp", var2_6.toString());
                try {
                    var1_1.close();
                    return;
                }
                catch (Throwable var1_3) {
                    bp.a("StreamOverHttp", "Failed to close the socket: " + var1_3.toString());
                    return;
                }
            }
        }
        catch (Throwable var2_7) {
            try {
                var1_1.close();
                throw var2_7;
            }
            catch (Throwable var1_4) {
                bp.a("StreamOverHttp", "Failed to close the socket: " + var1_4.toString());
                throw var2_7;
            }
lbl-1000:
            // 1 sources

            {
                var6_11.print("\r\n");
                var6_11.flush();
                var5_10.write(this.a, 0, this.a.length);
                var5_10.flush();
                var5_10.close();
            }
            try {
                var1_1.close();
                return;
            }
            catch (Throwable var1_2) {
                bp.a("StreamOverHttp", "Failed to close the socket: " + var1_2.toString());
                return;
            }
        }
    }

    public Uri a(String string2) {
        String string3;
        int n2 = this.b.getLocalPort();
        String string4 = string3 = "http://localhost:" + n2;
        if (string2 != null) {
            string4 = string3 + '/' + URLEncoder.encode(string2);
        }
        return Uri.parse((String)string4);
    }

    public void a() {
        Log.i((String)"StreamOverHttp", (String)"Closing stream over http");
        try {
            this.b.close();
            this.c.join();
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    class a
    implements Runnable {
        private boolean b = false;
        private final Socket c;

        a(Socket socket) {
            Log.i((String)"StreamOverHttp", (String)("Stream over localhost: serving request on " + socket.getInetAddress()));
            this.c = socket;
            ba.this = new Thread((Runnable)this, "Http response");
            ((Thread)ba.this).setDaemon(true);
            ((Thread)ba.this).start();
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void a(Socket socket) {
            try {
                void var3_7;
                InputStream inputStream = socket.getInputStream();
                if (inputStream == null) {
                    return;
                }
                byte[] byArray = new byte[8192];
                int n2 = inputStream.read(byArray, 0, byArray.length);
                if (n2 <= 0 || !this.a(socket, new BufferedReader(new InputStreamReader(new ByteArrayInputStream(byArray, 0, n2))), new Properties())) return;
                Properties properties = new Properties();
                properties.put("Content-Length", String.valueOf(ba.this.a.length));
                if (this.b) {
                    String string2 = "bytes";
                } else {
                    String string3 = "none";
                }
                properties.put("Accept-Ranges", var3_7);
                ba.this.a(socket, "200 OK", "audio/mpeg", properties);
                inputStream.close();
                return;
            }
            catch (IOException iOException) {
                bp.a("StreamOverHttp", iOException.toString());
                try {
                    bp.d("StreamOverHttp", "SERVER INTERNAL ERROR: IOException: " + iOException.getMessage());
                    return;
                }
                catch (Throwable throwable) {
                    bp.a("StreamOverHttp", throwable.toString());
                    return;
                }
            }
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private boolean a(Socket object, BufferedReader bufferedReader, Properties properties) {
            StringTokenizer stringTokenizer;
            try {
                object = bufferedReader.readLine();
                if (object == null) {
                    return false;
                }
                stringTokenizer = new StringTokenizer((String)object);
                if (!stringTokenizer.hasMoreTokens()) {
                    throw new RuntimeException("StreamOverHttpHTTP_BADREQUEST\uff1a Syntax error " + (String)object);
                }
            }
            catch (IOException iOException) {
                throw new RuntimeException("StreamOverHttpHTTP_INTERNALERROR: SERVER INTERNAL ERROR: IOException: " + iOException.getMessage());
            }
            {
                if (!stringTokenizer.nextToken().equals("GET")) {
                    return false;
                }
                if (!stringTokenizer.hasMoreTokens()) {
                    throw new RuntimeException("StreamOverHttpHTTP_BADREQUEST\uff1a Missing URI " + (String)object);
                }
                while ((object = bufferedReader.readLine()) != null) {
                    int n2 = ((String)object).indexOf(58);
                    if (n2 < 0) continue;
                    properties.put(((String)object).substring(0, n2).trim().toLowerCase(Locale.US), ((String)object).substring(n2 + 1).trim());
                }
                return true;
            }
        }

        @Override
        public void run() {
            this.a(this.c);
        }
    }
}

