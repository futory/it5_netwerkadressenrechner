package org.mnm.ipv4.subnet;

import org.mnm.ipv4.ipv4.IPv4Address;
import org.mnm.ipv4.ipv4.IPv4BroadcastAddress;
import org.mnm.ipv4.ipv4.IPv4HostAddress;
import org.mnm.ipv4.ipv4.IPv4NetworkID;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by martin on 04/05/17.
 */
public class IPv4Subnet {

    private List<IPv4Address> addressList = new ArrayList<>();
    private IPv4SubnetMask subnetMask;
    private IPv4BroadcastAddress broadcastAddress;
    private IPv4NetworkID networkID;

    private String name;

    public IPv4Subnet() {
    }

    public IPv4Subnet(IPv4Subnet.Builder builder) {
        this.name = builder.name;
        this.subnetMask = builder.subnetMask;
        this.broadcastAddress = builder.broadcastAddress;
        this.networkID = builder.networkID;
    }

    public IPv4Subnet addHost(IPv4HostAddress address) {
        this.addressList.add(address);
        return this;
    }

    public IPv4Subnet addNetID(IPv4NetworkID address) {
        this.networkID = address;
        this.addressList.add(address);
        return this;
    }

    public IPv4Subnet addBroadcast(IPv4BroadcastAddress address) {
        this.broadcastAddress = address;
        this.addressList.add(address);
        return this;
    }

    public IPv4BroadcastAddress getBroadcast() {
        return broadcastAddress;
    }

    public IPv4Subnet setBroadcastAddress(IPv4BroadcastAddress address) {
        this.addressList.add(address);
        this.broadcastAddress = address;
        return this;
    }

    public IPv4Subnet setNetworkID(IPv4NetworkID address) {
        this.addressList.add(address);
        this.networkID = address;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public IPv4Subnet setName(String name) {
        this.name = name;
        return this;
    }


    public void print() {
        System.out.println(subnetMask.getClass().toString() + ": " + subnetMask.toString());
        addressList.stream()
                .filter(a -> a != null)
                .forEach((IPv4Address a) -> {
                    System.out.println(a.getClass().toString() + ": " + a);
                });
    }

    public IPv4NetworkID getNetID() {
        return networkID;
    }

    public List<IPv4Address> getAddressList() {
        return addressList;
    }

    public IPv4Subnet setAddressList(List<IPv4Address> addressList) {
        this.addressList = addressList;
        return this;
    }

    public IPv4SubnetMask getSubnetMask() {
        return subnetMask;
    }

    public IPv4Subnet setSubnetMask(IPv4SubnetMask subnetMask) {
        this.subnetMask = subnetMask;
        return this;
    }

    public static class Builder {

        private IPv4SubnetMask subnetMask;
        private IPv4BroadcastAddress broadcastAddress;
        private IPv4NetworkID networkID;
        private String name;

        public IPv4Subnet.Builder subnetMask(IPv4SubnetMask subnetMask) {
            this.subnetMask = subnetMask;
            return this;
        }

        public IPv4Subnet.Builder name(String name){
            this.name = name;
            return this;
        }

        public IPv4Subnet.Builder broadcastAddress(IPv4BroadcastAddress address) {
            this.broadcastAddress = address;
            return this;
        }

        public IPv4Subnet.Builder networkID(IPv4NetworkID address) {
            this.networkID = address;
            return this;
        }

        public IPv4Subnet build(){
            return new IPv4Subnet(this);
        }

        public IPv4Subnet buildByName(String name) {
            String temp[] = name.split("/");
            int[] id = Stream.of(temp[0].split("\\.")).mapToInt(Integer::parseInt).toArray();

            this.networkID = new IPv4NetworkID(id);

            this.subnetMask = new IPv4SubnetMask.Builder().buildByPrefix(Integer.parseInt(temp[1]));

            if (!ipv4SubnetUtils.isValidNetID(id, subnetMask))
                throw new SubnetBuildingError("An invalid netID was detected: " + this.networkID.toString());

            this.broadcastAddress = ipv4SubnetUtils.calcBroadcast(subnetMask, networkID);

            return build();
        }

        public IPv4Subnet buildByAmountOfHosts(int[] netID, long hosts){
            this.subnetMask = new IPv4SubnetMask.Builder()
                    .buildByPrefix(ipv4SubnetUtils.calcPrefixByHosts(hosts));
            if(ipv4SubnetUtils.isNetID(netID, subnetMask))
                this.networkID = new IPv4NetworkID(netID);

            this.broadcastAddress = ipv4SubnetUtils.calcBroadcast(subnetMask, networkID);
            if(!ipv4SubnetUtils.isBroadcast(broadcastAddress.getIpv4Address(), subnetMask))
                throw new SubnetBuildingError("A false broadcastAddress was detected: " + broadcastAddress);

            return build();
        }
    }
}
