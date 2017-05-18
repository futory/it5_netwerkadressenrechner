package org.mnm.ipv6.subnet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.DynAnyPackage.Invalid;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by martin on 17/05/17.
 */
class ipv6SubnetUtilsTest {

    private String ip1 = "2001:0db8:1234:5678:00aa:aaaa:aaaa:aaaa";
    private String ip2 = "2001:0db8:0000:1234:0056:78aa:aaaa:aaaa";
    private String ip3 = "2001:0db8:0000:0012:0034:5678:aaaa:aaaa";
    private String ip4 = "2001:0db8:0000:0000:0012:3456:78aa:aaaa";
    private String ip5 = "2001:0db8:0000:0000:0000:0000:1234:5678";
    private String ip6 = "2001:0db8:0000:0000:0000:0000:18.52.86.120";  // kann das pattern noch nicht
    private String ip7 = "0064:ff9b:0000:0000::0000:0000:18.52.86.120";  // kann das pattern noch nicht
    private String ip8 = "0064:ff9b::1234:5678";
    private String ip9 = "0064:ff9b::1234:5678";
    private String ip10 = "64:ff9b::1234:5678";

    @Test
    void isValidIP() {
        assertTrue(ipv6SubnetUtils.isValidIP(ip1));
        assertTrue(ipv6SubnetUtils.isValidIP(ip2));
        assertTrue(ipv6SubnetUtils.isValidIP(ip3));
        assertTrue(ipv6SubnetUtils.isValidIP(ip4));
        assertTrue(ipv6SubnetUtils.isValidIP(ip5));
        assertTrue(ipv6SubnetUtils.isValidIP(ip6)); //pattern cannot deal with that
        assertTrue(ipv6SubnetUtils.isValidIP(ip7)); //pattern cannot deal with that
        assertTrue(ipv6SubnetUtils.isValidIP(ip8));
        assertTrue(ipv6SubnetUtils.isValidIP(ip9));
        assertTrue(ipv6SubnetUtils.isValidIP(ip10));
    }

    @Test
    void resolveIP() {
        fail("Not yet implemented");
    }

    @Test
    @DisplayName("testing ipv6SubnetUtils.hexToInt()")
    void hexToInt() {
        assertEquals(1, ipv6SubnetUtils.hexToInt("1"));
        assertEquals(15, ipv6SubnetUtils.hexToInt("f"));
        assertEquals(255, ipv6SubnetUtils.hexToInt("ff"));
        assertEquals(4095, ipv6SubnetUtils.hexToInt("fff"));
        assertEquals(65535, ipv6SubnetUtils.hexToInt("ffff"));
    }

    @Test
    @DisplayName("testing if IllegalIPv6Exeption is thrown, when an out of range hex is entered")
    void hexToInt2() {
        assertThrows(IllegalIPv6Exeption.class, () -> ipv6SubnetUtils.hexToInt("fffff"));
        assertThrows(IllegalIPv6Exeption.class, () -> ipv6SubnetUtils.hexToInt("-1"));
    }

}