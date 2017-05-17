package org.mnm.ipv4.subnet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mnm.ipv4.ipv4.IPv4BroadcastAddress;
import org.mnm.ipv4.ipv4.IPv4HostAddress;
import org.mnm.ipv4.ipv4.IPv4NetworkID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by martin on 06/05/17.
 */
class SubnetUtilsTest {
    @Test
    void isValidBroadcast() {
        //testing the one valid broadcast
        assertTrue(SubnetUtils.isValidBroadcast(new int[]{192, 168, 0, 255}, subnet));
        //testing a lst of invalid IPv4HostAdresses
        invalidBroadcasts.stream()
                .forEach(h -> assertFalse(SubnetUtils.isValidBroadcast(h.getIpv4Address(), subnet)));
    }

    @Test
    void calcPrefixByHosts() {

        assertEquals(32, SubnetUtils.calcPrefixByHosts(0));
        assertEquals(30, SubnetUtils.calcPrefixByHosts(1));
        assertEquals(29, SubnetUtils.calcPrefixByHosts(3));
        assertEquals(28, SubnetUtils.calcPrefixByHosts(7));
    }

    @Test
    void isValidPrefix() {
        for(int i = 0; i < 33; i++){ assertTrue(SubnetUtils.isValidPrefix(i)); }
        for(int i = -10; i < 0; i++){ assertFalse(SubnetUtils.isValidPrefix(i)); }
        for(int i = 33; i < 50; i++){ assertFalse(SubnetUtils.isValidPrefix(i)); }
    }

    IPv4Subnet subnet;
    IPv4SubnetMask mask;
    List<IPv4HostAddress> validHosts = new ArrayList<>();
    List<IPv4HostAddress> invalidHosts = new ArrayList<>();
    List<IPv4NetworkID> invalidNetId = new ArrayList<>();
    List<IPv4BroadcastAddress> invalidBroadcasts = new ArrayList<>();
    private IntUnaryOperator iterate = i -> i + 1;

    @BeforeEach
    void setUp() {

        //setting up a list of valid hosts
        IntStream.iterate(1, iterate).limit(253)
                .forEach(i -> validHosts.add(new IPv4HostAddress(new int[]{192, 168, 0, i})));

        //adding some invalid hosts manually
        invalidHosts.add(new IPv4HostAddress(new int[]{192, 168, 0, 0}));      //the net ID
        invalidHosts.add(new IPv4HostAddress(new int[]{192, 168, 0, 255}));    //the broadcast
        invalidHosts.add(new IPv4HostAddress(new int[]{192, 168, 1, 3}));
        invalidHosts.add(new IPv4HostAddress(new int[]{182, 166, 0, 3}));

        //adding all valid hosts to the invalid Network IDs and invalid Broadcast addresses
        validHosts.stream()
                .forEach(h -> {
                    invalidNetId.add(new IPv4NetworkID(h.getIpv4Address()));
                    invalidBroadcasts.add(new IPv4BroadcastAddress(h.getIpv4Address()));
                });

        //building a subnet with subnet mask, net ID and hosts
        mask = new IPv4SubnetMask.Builder().buildByPrefix(24);

        subnet = new IPv4Subnet.Builder().buildByName("192.168.0.0/24");

        validHosts.stream()
                .forEach(a -> subnet.addHost(a));

        //adding all invalid hosts with the exeption of the net ID and broadcast
        invalidHosts.stream()
                .filter(h -> !Arrays.equals(h.getIpv4Address(), subnet.getBroadcast().getIpv4Address()))
                .filter(h -> !Arrays.equals(h.getIpv4Address(), subnet.getNetID().getIpv4Address()))
                .forEach(h -> {
                    invalidNetId.add(new IPv4NetworkID(h.getIpv4Address()));
                    invalidBroadcasts.add(new IPv4BroadcastAddress(h.getIpv4Address()));
                });

    }

    @Test
    void isBroadcast() {
        assertTrue(SubnetUtils.isBroadcast(
                new int[]{192,168,0,255},
                new IPv4SubnetMask.Builder().buildByPrefix(24)));
    }

    @Test
    void calcBroadcast() {
        assertArrayEquals(subnet.getBroadcast().getIpv4Address(),
                SubnetUtils.calcBroadcast(subnet.getSubnetMask(),
                        subnet.getNetID()).getIpv4Address());
    }

    @Test
    void isNetID() {
        //testing the one valid net ID
        assertTrue(SubnetUtils.isNetID(new int[]{192, 168, 0, 0}, subnet.getSubnetMask()));

        int[] i = {0,0,0,0};
        assertTrue(SubnetUtils.isNetID(i,subnet.getSubnetMask()));

        //testing a list of invalid IPv4HostAdresses
        invalidNetId.stream()
                .forEach(n -> assertFalse(SubnetUtils.isNetID(n.getIpv4Address(), subnet.getSubnetMask())));
    }

    @Test
    void isHost() {
        //testing a list of valid hosts
        validHosts.stream()
                .forEach(n -> assertTrue(SubnetUtils.isHost(n.getIpv4Address(), subnet)));

        //testing a list of invalid hosts
        invalidHosts.stream()
                .forEach(n -> assertFalse(SubnetUtils.isHost(n.getIpv4Address(), subnet)));
    }

    @Test
    void isValidNetID() {
        int[]netID1 = {192,168,0,0};
        int[]mask1 = {255,255,255,0};

        int[]netID2 = {192,168,30,0};
        int[]mask2 = {255,255,254,0};

        int[]netID3 = {192,168,151,128};
        int[]mask3 = {255,255,255,128};

        int[]netID4 = {0,0,0,0};
        int[]mask4 = {0,0,0,0};

        int[]netID5 = {192,168,0,0};
        int[]mask5= {255,255,255,255};

        assertArrayEquals(netID1, SubnetUtils.andALL(netID1, mask1));
        assertArrayEquals(netID2, SubnetUtils.andALL(netID2, mask2));
        assertArrayEquals(netID3, SubnetUtils.andALL(netID3, mask3));
        assertArrayEquals(netID4, SubnetUtils.andALL(netID4, mask4));

    }

    @Test
    void toBinaryString() {
        assertEquals(
                "11111111.11111111.11111111.10000000",
                SubnetUtils.toBinaryString(new int[]{255,255,255,128}));
    }

    @Test
    void isValidIP() {
        assertFalse(SubnetUtils.isValidIP(new int[]{256,0,0,0}));
        assertFalse(SubnetUtils.isValidIP(new int[]{-1,0,0,0}));
        assertFalse(SubnetUtils.isValidIP(new int[]{0,255,0,0,0}));
        assertFalse(SubnetUtils.isValidIP(new int[]{1000,0,0,0}));
        assertFalse(SubnetUtils.isValidIP(new int[]{155,255,192,-10}));

        assertTrue(SubnetUtils.isValidIP(new int[]{0,0,0,0}));
        assertTrue(SubnetUtils.isValidIP(new int[]{255,255,255,255}));
        assertTrue(SubnetUtils.isValidIP(new int[]{192,0,0,0}));
        assertTrue(SubnetUtils.isValidIP(new int[]{1,1,1,1}));

    }

    @Test
    void isValidSubnetMask() {
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,255,255}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,255,254}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,255,252}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,255,248}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,255,240}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,255,224}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,255,192}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,255,128}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,255,0}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,254,0}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,252,0}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,248,0}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,240,0}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,224,0}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,192,0}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{255,255,128,0}));
        assertTrue(SubnetUtils.isValidSubnetMask(new int[]{0,0,0,0}));

        assertFalse(SubnetUtils.isValidSubnetMask(new int[]{255,255,224,128}));
        assertFalse(SubnetUtils.isValidSubnetMask(new int[]{255,255,255,255,255}));
        assertFalse(SubnetUtils.isValidSubnetMask(new int[]{255,255,255,-10}));
        assertFalse(SubnetUtils.isValidSubnetMask(new int[]{255,255,255,10}));
    }

    @Test
    void calcPrefixByMask() {
        assertEquals(24, SubnetUtils.calcPrefixByMask(new int[]{255,255,255,0}));
        assertEquals(25, SubnetUtils.calcPrefixByMask(new int[]{255,255,255,128}));
        assertEquals(23, SubnetUtils.calcPrefixByMask(new int[]{255,255,254,0}));
        assertEquals(32, SubnetUtils.calcPrefixByMask(new int[]{255,255,255,255}));
        assertEquals(0, SubnetUtils.calcPrefixByMask(new int[]{0,0,0,0}));
    }

    @Test
    void calcMaxHosts() {
        assertEquals(IPv4SubnetMask.MAXIMUM_AMOUNT_OF_HOSTS, SubnetUtils.calcMaxHosts(0));
        assertEquals(2, SubnetUtils.calcMaxHosts(30));
        assertEquals(6, SubnetUtils.calcMaxHosts(29));
        assertEquals(14, SubnetUtils.calcMaxHosts(28));
        assertEquals(4094, SubnetUtils.calcMaxHosts(20));
        assertEquals(131070, SubnetUtils.calcMaxHosts(15));
        assertEquals(0, SubnetUtils.calcMaxHosts(31));
        assertEquals(0, SubnetUtils.calcMaxHosts(32));
    }

    @Test
    void calcMaskByPrefix() {
        assertArrayEquals(new int[]{255,255,255,0}, SubnetUtils.calcMaskByPrefix(24));
        assertArrayEquals(new int[]{255,255,254,0}, SubnetUtils.calcMaskByPrefix(23));
        assertArrayEquals(new int[]{255,255,255,128}, SubnetUtils.calcMaskByPrefix(25));
        assertArrayEquals(new int[]{255,255,255,255}, SubnetUtils.calcMaskByPrefix(32));
        assertArrayEquals(new int[]{0,0,0,0}, SubnetUtils.calcMaskByPrefix(0));
        assertArrayEquals(new int[]{128,0,0,0}, SubnetUtils.calcMaskByPrefix(1));
    }

    @Test
    void negateAll() {
        //Trivial, do not test
    }

    @Test
    void orAll() {
        //Trivial, do not test
    }

    @Test
    void andALL() {
        //Trivial, do not test
    }

}