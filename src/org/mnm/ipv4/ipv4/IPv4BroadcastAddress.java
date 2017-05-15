package org.mnm.ipv4.ipv4;

import org.mnm.ipv4.subnet.SubnetUtils;

import java.util.stream.Stream;

/**
 * Created by martin on 15/04/17.
 */
public class IPv4BroadcastAddress extends IPv4Address {

    public IPv4BroadcastAddress(int[] ipv4Address) {
        super();
        if (SubnetUtils.isValidIP(ipv4Address))
            this.setIpv4Address(ipv4Address);
    }

    public IPv4BroadcastAddress(String first) {
        super();
        Stream.of(first.split("\\.")).mapToInt(s -> Integer.parseInt(s)).toArray();
    }
}
