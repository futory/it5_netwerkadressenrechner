package org.mnm.ipv4.subnet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by martin on 09/05/17.
 */
class SubnetMaskBuilderTest {

    IPv4SubnetMask psubnetMask24;
    IPv4SubnetMask psubnetMask25;
    IPv4SubnetMask psubnetMask23;
    IPv4SubnetMask psubnetMask1;
    IPv4SubnetMask psubnetMask32;
    IPv4SubnetMask psubnetMask0;

    IPv4SubnetMask ssubnetMask24;
    IPv4SubnetMask ssubnetMask25;
    IPv4SubnetMask ssubnetMask23;
    IPv4SubnetMask ssubnetMask1;
    IPv4SubnetMask ssubnetMask32;
    IPv4SubnetMask ssubnetMask0;


    @BeforeEach
    void setUp() {
        setUpPrefix();
        setUpName();
    }

    private void setUpName() {
        ssubnetMask24 = new IPv4SubnetMask.Builder()
                .buildByString("255.255.255.0");
        ssubnetMask25 = new IPv4SubnetMask.Builder()
                .buildByString("255.255.255.128");
        ssubnetMask23 = new IPv4SubnetMask.Builder()
                .buildByString("255.255.254.0");
        ssubnetMask32 = new IPv4SubnetMask.Builder()
                .buildByString("255.255.255.255");
        ssubnetMask0 = new IPv4SubnetMask.Builder()
                .buildByString("0.0.0.0");
        ssubnetMask1 = new IPv4SubnetMask.Builder()
                .buildByString("128.0.0.0");
    }

    private void setUpPrefix(){
        psubnetMask24 = new IPv4SubnetMask.Builder()
                .buildByPrefix(24);
        psubnetMask25 = new IPv4SubnetMask.Builder()
                .buildByPrefix(25);
        psubnetMask23 = new IPv4SubnetMask.Builder()
                .buildByPrefix(23);
        psubnetMask32 = new IPv4SubnetMask.Builder()
                .buildByPrefix(32);
        psubnetMask0 = new IPv4SubnetMask.Builder()
                .buildByPrefix(0);
        psubnetMask1 = new IPv4SubnetMask.Builder()
                .buildByPrefix(1);
    }

    @Test
    void buildByPrefix() {
        assertArrayEquals(new int[]{255,255,255,0}, psubnetMask24.getSubnetMask());
        assertEquals(24, psubnetMask24.getPrefix());
        assertEquals(254, psubnetMask24.getMaxHosts());

        assertArrayEquals(new int[]{255,255,255,128}, psubnetMask25.getSubnetMask());
        assertEquals(25, psubnetMask25.getPrefix());
        assertEquals(126, psubnetMask25.getMaxHosts());

        assertArrayEquals(new int[]{255,255,254,0}, psubnetMask23.getSubnetMask());
        assertEquals(23, psubnetMask23.getPrefix());
        assertEquals(510, psubnetMask23.getMaxHosts());

        assertArrayEquals(new int[]{255,255,255,255}, psubnetMask32.getSubnetMask());
        assertEquals(32, psubnetMask32.getPrefix());
        assertEquals(0, psubnetMask32.getMaxHosts());

        assertArrayEquals(new int[]{128,0,0,0}, psubnetMask1.getSubnetMask());
        assertEquals(1, psubnetMask1.getPrefix());
        assertEquals(2147483646, psubnetMask1.getMaxHosts());

        assertArrayEquals(new int[]{0,0,0,0}, psubnetMask0.getSubnetMask());
        assertEquals(0, psubnetMask0.getPrefix());
        assertEquals(IPv4SubnetMask.MAXIMUM_AMOUNT_OF_HOSTS, psubnetMask0.getMaxHosts());
    }

    @Test
    void buildByString(){
        assertArrayEquals(new int[]{255,255,255,0}, ssubnetMask24.getSubnetMask());
        assertEquals(24, ssubnetMask24.getPrefix());
        assertEquals(254, ssubnetMask24.getMaxHosts());

        assertArrayEquals(new int[]{255,255,255,128}, ssubnetMask25.getSubnetMask());
        assertEquals(25, ssubnetMask25.getPrefix());
        assertEquals(126, ssubnetMask25.getMaxHosts());

        assertArrayEquals(new int[]{255,255,254,0}, ssubnetMask23.getSubnetMask());
        assertEquals(23, ssubnetMask23.getPrefix());
        assertEquals(510, ssubnetMask23.getMaxHosts());

        assertArrayEquals(new int[]{255,255,255,255}, ssubnetMask32.getSubnetMask());
        assertEquals(32, ssubnetMask32.getPrefix());
        assertEquals(0, ssubnetMask32.getMaxHosts());

        assertArrayEquals(new int[]{128,0,0,0}, ssubnetMask1.getSubnetMask());
        assertEquals(1, ssubnetMask1.getPrefix());
        assertEquals(2147483646, ssubnetMask1.getMaxHosts());

        assertArrayEquals(new int[]{0,0,0,0}, ssubnetMask0.getSubnetMask());
        assertEquals(0, ssubnetMask0.getPrefix());
        assertEquals(IPv4SubnetMask.MAXIMUM_AMOUNT_OF_HOSTS, ssubnetMask0.getMaxHosts());
    }
}