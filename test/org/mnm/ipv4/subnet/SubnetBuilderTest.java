package org.mnm.ipv4.subnet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Created by martin on 09/05/17.
 */
class SubnetBuilderTest {
    private IPv4Subnet subnet24;
    private IPv4Subnet subnet25;
    private IPv4Subnet subnet23;
    private IPv4Subnet subnet32;
    private IPv4Subnet subnet0;
    private IPv4Subnet subnet1;

    private IPv4Subnet hsubnet24;
    private IPv4Subnet hsubnet25;
    private IPv4Subnet hsubnet23;
    private IPv4Subnet hsubnet32;
    private IPv4Subnet hsubnet0;
    private IPv4Subnet hsubnet1;
    private IPv4Subnet hsubnet2;


    @BeforeEach
    public void setUp(){
        try {
            setUpByName();
            setUpByAmountOfHosts();
        } catch (SubnetBuildingError subnetBuildingError) {
            subnetBuildingError.printStackTrace();
        }
    }

    private void setUpByAmountOfHosts() throws SubnetBuildingError {

        hsubnet24 = new IPv4Subnet.Builder()
                .buildByAmountOfHosts(new int[]{192,168,0,0}, 160);
        hsubnet25 = new IPv4Subnet.Builder()
                .buildByAmountOfHosts(new int[]{192,168,0,0}, 140);
        hsubnet23 = new IPv4Subnet.Builder()
                .buildByAmountOfHosts(new int[]{192,168,0,0}, 180);
        hsubnet0 = new IPv4Subnet.Builder()
                .buildByAmountOfHosts(new int[]{0,0,0,0}, IPv4SubnetMask.MAXIMUM_AMOUNT_OF_HOSTS-10);
        hsubnet32 = new IPv4Subnet.Builder()
                .buildByAmountOfHosts(new int[]{192,168,0,0}, 0);
        hsubnet1 = new IPv4Subnet.Builder()
                .buildByAmountOfHosts(new int[]{128,0,0,0}, 2147483638L);
        hsubnet2 = new IPv4Subnet.Builder()
                .buildByAmountOfHosts(new int[]{192,168,0,0}, 1);
    }

    private void setUpByName() throws SubnetBuildingError {
        subnet24 = new IPv4Subnet.Builder()
                .buildByName("192.168.0.0/24");
        subnet25 = new IPv4Subnet.Builder()
                .buildByName("192.168.0.0/25");
        subnet23 = new IPv4Subnet.Builder()
                .buildByName("192.168.0.0/23");
        subnet0 = new IPv4Subnet.Builder()
                .buildByName("0.0.0.0/0");
        subnet32 = new IPv4Subnet.Builder()
                .buildByName("192.168.0.0/32");
        subnet1 = new IPv4Subnet.Builder()
                .buildByName("128.0.0.0/1");
    }

    @Test
    void buildByName() {

        assertArrayEquals(new int[]{192,168,0,0}, subnet24.getNetID().getIpv4Address());
        assertArrayEquals(new int[]{255,255,255,0}, subnet24.getSubnetMask().getSubnetMask());
        assertArrayEquals(new int[]{192,168,0,255}, subnet24.getBroadcast().getIpv4Address());

        assertArrayEquals(new int[]{192,168,0,0}, subnet23.getNetID().getIpv4Address());
        assertArrayEquals(new int[]{255,255,254,0}, subnet23.getSubnetMask().getSubnetMask());
        assertArrayEquals(new int[]{192,168,1,255}, subnet23.getBroadcast().getIpv4Address());

        assertArrayEquals(new int[]{192,168,0,0}, subnet25.getNetID().getIpv4Address());
        assertArrayEquals(new int[]{255,255,255,128}, subnet25.getSubnetMask().getSubnetMask());
        assertArrayEquals(new int[]{192,168,0,127}, subnet25.getBroadcast().getIpv4Address());

        assertArrayEquals(new int[]{192,168,0,0}, subnet32.getNetID().getIpv4Address());
        assertArrayEquals(new int[]{255,255,255,255}, subnet32.getSubnetMask().getSubnetMask());
        assertArrayEquals(new int[]{192,168,0,0}, subnet32.getBroadcast().getIpv4Address());

        assertArrayEquals(new int[]{0,0,0,0}, subnet0.getNetID().getIpv4Address());
        assertArrayEquals(new int[]{0,0,0,0}, subnet0.getSubnetMask().getSubnetMask());
        assertArrayEquals(new int[]{255,255,255,255}, subnet0.getBroadcast().getIpv4Address());

        assertArrayEquals(new int[]{128,0,0,0}, subnet1.getNetID().getIpv4Address());
        assertArrayEquals(new int[]{128,0,0,0}, subnet1.getSubnetMask().getSubnetMask());
        assertArrayEquals(new int[]{255,255,255,255}, subnet1.getBroadcast().getIpv4Address());
    }

    @Test
    void buildByAmountOfHosts() {

        assertArrayEquals(new int[]{0,0,0,0}, hsubnet0.getNetID().getIpv4Address());
        assertArrayEquals(new int[]{0,0,0,0}, hsubnet0.getSubnetMask().getSubnetMask());
        assertArrayEquals(new int[]{255,255,255,255}, hsubnet0.getBroadcast().getIpv4Address());

        assertArrayEquals(new int[]{128,0,0,0}, hsubnet1.getNetID().getIpv4Address());
        assertArrayEquals(new int[]{128,0,0,0}, hsubnet1.getSubnetMask().getSubnetMask());
        assertArrayEquals(new int[]{255,255,255,255}, hsubnet1.getBroadcast().getIpv4Address());

        assertArrayEquals(new int[]{192,168,0,0}, hsubnet2.getNetID().getIpv4Address());
        assertArrayEquals(new int[]{255,255,255,252}, hsubnet2.getSubnetMask().getSubnetMask());
        assertArrayEquals(new int[]{192,168,0,3}, hsubnet2.getBroadcast().getIpv4Address());

        assertArrayEquals(new int[]{192,168,0,0}, hsubnet23.getNetID().getIpv4Address());
        assertArrayEquals(new int[]{255,255,255,0}, hsubnet23.getSubnetMask().getSubnetMask());
        assertArrayEquals(new int[]{192,168,0,255}, hsubnet23.getBroadcast().getIpv4Address());

        assertArrayEquals(new int[]{192,168,0,0}, hsubnet24.getNetID().getIpv4Address());
        assertArrayEquals(new int[]{255,255,255,0}, hsubnet24.getSubnetMask().getSubnetMask());
        assertArrayEquals(new int[]{192,168,0,255}, hsubnet24.getBroadcast().getIpv4Address());

        assertArrayEquals(new int[]{192,168,0,0}, hsubnet25.getNetID().getIpv4Address());
        assertArrayEquals(new int[]{255,255,255,0}, hsubnet25.getSubnetMask().getSubnetMask());
        assertArrayEquals(new int[]{192,168,0,255}, hsubnet25.getBroadcast().getIpv4Address());

        assertArrayEquals(new int[]{192,168,0,0}, hsubnet32.getNetID().getIpv4Address());
        assertArrayEquals(new int[]{255,255,255,255}, hsubnet32.getSubnetMask().getSubnetMask());
        assertArrayEquals(new int[]{192,168,0,0}, hsubnet32.getBroadcast().getIpv4Address());

    }
}