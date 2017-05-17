package org.mnm.ipv4.ipv4;

import it5.p04.fileadapter.Type;
import org.mnm.ipv4.subnet.ipv4SubnetUtils;

import java.util.stream.Stream;

/**
 * Created by martin on 14/04/17.
 */
public class IPv4HostAddress extends IPv4Address {

    private Type type = Type.HOST;

    public IPv4HostAddress(int[] ipv4Address) {
        this.setIpv4Address(ipv4Address);
    }

    public IPv4HostAddress(String ipv4Address) {
        this.setIpv4Address(Stream.of(ipv4Address.split("\\.")).mapToInt(Integer::parseInt).toArray());
        if (!ipv4SubnetUtils.isValidIP(this.getIpv4Address()))
            throw new FalseIPExeption();
    }

    public Type getType(){
        return type;
    }
}
