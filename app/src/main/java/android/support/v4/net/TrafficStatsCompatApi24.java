/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  android.net.TrafficStats
 */
package android.support.v4.net;

import android.net.TrafficStats;
import java.net.DatagramSocket;
import java.net.SocketException;

public class TrafficStatsCompatApi24 {
    public static void tagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
        TrafficStats.tagDatagramSocket((DatagramSocket)datagramSocket);
    }

    public static void untagDatagramSocket(DatagramSocket datagramSocket) throws SocketException {
        TrafficStats.untagDatagramSocket((DatagramSocket)datagramSocket);
    }
}

