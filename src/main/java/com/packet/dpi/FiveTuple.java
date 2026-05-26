
package com.packet.dpi;

import java.util.Objects;

public class FiveTuple {
    public final String srcIp;
    public final String dstIp;
    public final int srcPort;
    public final int dstPort;
    public final String protocol;

    public FiveTuple(String srcIp, String dstIp, int srcPort, int dstPort, String protocol) {
        this.srcIp = srcIp;
        this.dstIp = dstIp;
        this.srcPort = srcPort;
        this.dstPort = dstPort;
        this.protocol = protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiveTuple fiveTuple = (FiveTuple) o;
        return srcPort == fiveTuple.srcPort && dstPort == fiveTuple.dstPort &&
                srcIp.equals(fiveTuple.srcIp) && dstIp.equals(fiveTuple.dstIp) &&
                protocol.equals(fiveTuple.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(srcIp, dstIp, srcPort, dstPort, protocol);
    }
}