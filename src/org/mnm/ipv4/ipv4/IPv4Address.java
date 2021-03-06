package org.mnm.ipv4.ipv4;

import it5.p04.fileadapter.Type;
import org.mnm.ipv4.subnet.ipv4SubnetUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by martin on 08/04/17.
 */
public abstract class IPv4Address{

    private int[] ipv4Address;

    private Type type;

    public int[] getIpv4Address() {
        return ipv4Address;
    }

    public void setIpv4Address(int[] ipv4Address) {
        if (ipv4SubnetUtils.isValidIP(ipv4Address)) {
            this.ipv4Address = ipv4Address;
        }
    }

    @Override
    public String toString() {
        return Arrays.stream(ipv4Address)
                .mapToObj(i -> ((Integer) i).toString())
                .collect(Collectors.joining("."));
    }

    public String toBinaryString() {
        return Arrays.stream(ipv4Address)
                .mapToObj(i -> ((Integer) i).toBinaryString(i))
                .collect(Collectors.joining("."));
    }

    public Type setType(Type type){
        this.type = type;
        return type;
    }

    public Type getType(){
        return this.type;
    }
}
