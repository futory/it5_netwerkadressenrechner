package org.mnm.ipv4.ipv4;

import it5.p04.fileadapter.Types;
import org.mnm.ipv4.subnet.SubnetUtils;

import java.util.stream.Stream;

/**
 * Created by martin on 15/04/17.
 */
public class IPv4BroadcastAddress extends IPv4Address {

    private Types type = Types.BROADCAST;

    public IPv4BroadcastAddress(int[] ipv4Address) {
        super();
        if (SubnetUtils.isValidIP(ipv4Address))
            super.setIpv4Address(ipv4Address);
    }

    public IPv4BroadcastAddress(String ipv4Address) {
        super();
        super.setIpv4Address(Stream.of(ipv4Address.split("\\.")).mapToInt(Integer::parseInt).toArray());
    }

    public Types getType(){
        return type;
    }
}
