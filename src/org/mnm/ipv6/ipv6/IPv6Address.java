package org.mnm.ipv6.ipv6;

import it5.p04.fileadapter.Type;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by martin on 17/05/17.
 */
public abstract class IPv6Address {

    private int[] ipv6Address;

    private Type type;

    public int[] getIpv6Address() {
        return ipv6Address;
    }

    public void setIpv6Address(int[] ipv6Address) {
        this.ipv6Address = ipv6Address;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return Arrays.stream(ipv6Address)
                .mapToObj(i -> ((Integer) i).toString())
                .collect(Collectors.joining("."));
    }

    public String toBinaryString() {
        return Arrays.stream(ipv6Address)
                .mapToObj(i -> ((Integer) i).toBinaryString(i))
                .collect(Collectors.joining("."));
    }
}
