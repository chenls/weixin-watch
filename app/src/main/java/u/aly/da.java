/*
 * Decompiled with CFR 0.151.
 */
package u.aly;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import u.aly.dc;
import u.aly.dd;

public class da
extends dc {
    protected InputStream a = null;
    protected OutputStream b = null;

    protected da() {
    }

    public da(InputStream inputStream) {
        this.a = inputStream;
    }

    public da(InputStream inputStream, OutputStream outputStream) {
        this.a = inputStream;
        this.b = outputStream;
    }

    public da(OutputStream outputStream) {
        this.b = outputStream;
    }

    @Override
    public int a(byte[] byArray, int n2, int n3) throws dd {
        if (this.a == null) {
            throw new dd(1, "Cannot read from null inputStream");
        }
        try {
            n2 = this.a.read(byArray, n2, n3);
            if (n2 < 0) {
                throw new dd(4);
            }
        }
        catch (IOException iOException) {
            throw new dd(0, (Throwable)iOException);
        }
        return n2;
    }

    @Override
    public boolean a() {
        return true;
    }

    @Override
    public void b() throws dd {
    }

    @Override
    public void b(byte[] byArray, int n2, int n3) throws dd {
        if (this.b == null) {
            throw new dd(1, "Cannot write to null outputStream");
        }
        try {
            this.b.write(byArray, n2, n3);
            return;
        }
        catch (IOException iOException) {
            throw new dd(0, (Throwable)iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void c() {
        if (this.a != null) {
            try {
                this.a.close();
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
            this.a = null;
        }
        if (this.b != null) {
            try {
                this.b.close();
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
            this.b = null;
        }
    }

    @Override
    public void d() throws dd {
        if (this.b == null) {
            throw new dd(1, "Cannot flush null outputStream");
        }
        try {
            this.b.flush();
            return;
        }
        catch (IOException iOException) {
            throw new dd(0, (Throwable)iOException);
        }
    }
}

