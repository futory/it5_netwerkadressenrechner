package org.mnm.ipv4.ipv4;

import java.util.stream.Stream;

/**
 * Created by martin on 14/04/17.
 */
public class IPv4HostAddress extends IPv4Address {

    public IPv4HostAddress(int[] ipv4Address) {
        this.setIpv4Address(ipv4Address);
    }

    public IPv4HostAddress(String ipv4Address) {
        this.setIpv4Address(
                Stream.of(ipv4Address.split("."))
                .mapToInt(s -> Integer.parseInt(s))
                .toArray()
        );
    }
}
