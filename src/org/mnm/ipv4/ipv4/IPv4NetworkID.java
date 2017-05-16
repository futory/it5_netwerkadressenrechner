package org.mnm.ipv4.ipv4;

import it5.p04.fileadapter.Types;

import java.util.stream.Stream;

/**
 * Created by martin on 04/05/17.
 */
public class IPv4NetworkID extends IPv4Address {

    private Types type = Types.NETID;

    public IPv4NetworkID(int[] ipv4Address) {
        this.setIpv4Address(ipv4Address);
    }

    public IPv4NetworkID(String ipv4Address) {
        this.setIpv4Address(Stream.of(ipv4Address.split("\\.")).mapToInt(Integer::parseInt).toArray());
    }

    public Types getType(){
        return type;
    }
}
