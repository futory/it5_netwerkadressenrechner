package org.mnm.ipv4.ipv4;

import org.mnm.ipv4.subnet.SubnetUtils;

/**
 * Created by martin on 15/04/17.
 */
public class IPv4BroadcastAddress extends IPv4Address {

    public IPv4BroadcastAddress(int[] ipv4Address) {
        if(SubnetUtils.isValidIP(ipv4Address))
            this.setIpv4Address(ipv4Address);
    }

}
