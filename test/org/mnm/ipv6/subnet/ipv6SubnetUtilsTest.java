package org.mnm.ipv6.subnet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by martin on 17/05/17.
 */
class ipv6SubnetUtilsTest {

    List<String> ipv6 = new ArrayList<>();
    List<String> invalidipv6 = new ArrayList<>();

    /*
private String ip11 = "[2001:0000:4136:e378:8000:63bf:3fff:fdd2]:5060", 1 },
private String ip11 = "2001:0000:4136:e378:8000:63bf:3fff:fdd2:5060", 0 },
*/


    @BeforeEach
    void setUp() {
        ipv6.add("2001:0db8:1234:5678:00aa:aaaa:aaaa:aaaa");
        ipv6.add("2001:0db8:0000:1234:0056:78aa:aaaa:aaaa");
        ipv6.add("2001:0db8:0000:0012:0034:5678:aaaa:aaaa");
        ipv6.add("2001:0db8:0000:0000:0012:3456:78aa:aaaa");
        ipv6.add("2001:0db8:0000:0000:0000:0000:1234:5678");
        ipv6.add("2001:0db8:0000:0000:0000:0000:18.52.86.120");
        ipv6.add("0064:ff9b:0000:0000::0000:0000:18.52.86.120");
        ipv6.add("0064:ff9b::1234:5678");
        ipv6.add("64:ff9b::1234:5678");
        ipv6.add("fdf8:f53b:82e4::53");
        ipv6.add("fe80::200:5aee:feaa:20a2");
        ipv6.add("2001::1");
        ipv6.add("2001:0000:4136:e378:8000:63bf:3fff:fdd2");
        ipv6.add("2001:0002:6c::430");
        ipv6.add("2001:10:240:ab::a");
        ipv6.add("2002:cb0a:3cdd:1::1");
        ipv6.add("2001:db8:8:4::2");
        ipv6.add("ff01:0:0:0:0:0:0:2");
        ipv6.add("ff01:0:0:0:0:0:0:2");
        ipv6.add("[fe80::200:5aee:feaa:20a2]");
        ipv6.add("[2001::1]");


        invalidipv6.add("2001:0db8:1234:5678:00aa:aaaa:aaaa:aaaa:aaaa");
        invalidipv6.add("fffff:0db8:1234:5678:00aa:aaaa:aaaa:aaaa");
        invalidipv6.add("2001::1234:5678:00aa::aaaa");
        invalidipv6.add("fe80::200::abcd");
        invalidipv6.add("ffff:0lb8:1234:5678:00aa:aaaa:aaaa:aaaa");
        invalidipv6.add("::");  // therotically valid, but determines, that a host does not have an address, thus
                                // not practical for our case
        invalidipv6.add("127.0.0.1");
    }

    @Test
    void isValidIP() {
        ipv6.stream()
                .forEach(i -> assertTrue(ipv6SubnetUtils.isValidIP(i)));

        invalidipv6.stream()
                .forEach(i -> assertFalse(ipv6SubnetUtils.isValidIP(i)));
    }

    @Test
    void resolveIP() {

        assertArrayEquals(
                new int[]{8193, 3512, 4660, 22136, 170, 43690, 43690, 43690},
                ipv6SubnetUtils.resolveIP("2001:0db8:1234:5678:00aa:aaaa:aaaa:aaaa")
        );

        assertArrayEquals(
                new int[]{8193, 3512, 4660, 0, 0, 0, 43690, 43690},
                ipv6SubnetUtils.resolveIP("2001:0db8:1234::aaaa:aaaa")
        );

        assertArrayEquals(
                new int[]{8193, 3512, 4660, 0, 0, 0, 0, 0},
                ipv6SubnetUtils.resolveIP("2001:0db8:1234::")
        );

        assertArrayEquals(
                new int[]{0, 0, 0, 0, 0, 0, 43690, 43690},
                ipv6SubnetUtils.resolveIP("::aaaa:aaaa")
        );

        assertArrayEquals(
                new int[]{8193, 3512, 0, 0, 0, 0, 0, 43690},
                ipv6SubnetUtils.resolveIP("2001:0db8::aaaa")
        );
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