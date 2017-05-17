package test.org.mnm.ipv4.ipv4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mnm.ipv4.ipv4.IPv4BroadcastAddress;
import org.mnm.ipv4.ipv4.IPv4HostAddress;
import org.mnm.ipv4.ipv4.IPv4NetworkID;
import org.mnm.ipv4.subnet.IPv4Subnet;
import org.mnm.ipv4.subnet.IPv4SubnetMask;
import org.mnm.ipv4.subnet.SubnetUtils;

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
    void calcPrefixByHosts() {

        assertEquals(32, SubnetUtils.calcPrefixByHosts(0));
        assertEquals(31, SubnetUtils.calcPrefixByHosts(1));
        assertEquals(30, SubnetUtils.calcPrefixByHosts(2));
        assertEquals(29, SubnetUtils.calcPrefixByHosts(7));

        assertEquals(32, SubnetUtils.calcPrefixByHosts(0));
        assertEquals(32, SubnetUtils.calcPrefixByHosts(0));
        assertEquals(32, SubnetUtils.calcPrefixByHosts(0));
        assertEquals(32, SubnetUtils.calcPrefixByHosts(0));
        assertEquals(32, SubnetUtils.calcPrefixByHosts(0));
        assertEquals(32, SubnetUtils.calcPrefixByHosts(0));

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
        invalidHosts.add(new IPv4HostAddress(new int[]{0, 0, 0, 0}));

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
        //testing the one valid broadcast
        assertTrue(SubnetUtils.isBroadcast(new int[]{192, 168, 0, 255}, subnet));
        //testing a lst of invalid IPv4HostAdresses
        invalidBroadcasts.stream()
                .forEach(h -> assertFalse(SubnetUtils.isBroadcast(h.getIpv4Address(), subnet)));
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
        //testing a lst of invalid IPv4HostAdresses
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
        fail("Not yet implemented");
    }

    @Test
    void isValidNetID1() {
        fail("Not yet implemented");
    }

    @Test
    void toBinaryString() {
        assertEquals(
                "11111111.11111111.11111111.10000000",
                SubnetUtils.toBinaryString(new int[]{255,255,255,128}));
    }

    @Test
    void isValidIP() {
        fail("Not yet implemented");
    }

    @Test
    void isValidSubnetMask() {
        fail("Not yet implemented");
    }

    @Test
    void calcPrefixByMask() {
        fail("Not yet implemented");
    }

    @Test
    void calcMaxHosts() {
        fail("Not yet implemented");
    }

    @Test
    void calcMaskByPrefix() {
        fail("Not yet implemented");
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