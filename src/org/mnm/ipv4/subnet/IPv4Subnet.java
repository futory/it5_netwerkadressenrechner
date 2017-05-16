package org.mnm.ipv4.subnet;

import org.mnm.ipv4.ipv4.IPv4Address;
import org.mnm.ipv4.ipv4.IPv4BroadcastAddress;
import org.mnm.ipv4.ipv4.IPv4HostAddress;
import org.mnm.ipv4.ipv4.IPv4NetworkID;

import java.util.ArrayList;
import java.util.List;

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
}
