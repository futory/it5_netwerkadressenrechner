package org.mnm.ipv4.subnet;

import org.mnm.ipv4.ipv4.IPv4BroadcastAddress;
import org.mnm.ipv4.ipv4.IPv4HostAddress;
import org.mnm.ipv4.ipv4.IPv4NetworkID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *&lt;pre&gt;
 * Created by martin on 04/05/17.
 * A helper class able to validate various elements of a subnet, like a host/broadcast ip, subnet mask, or a net id
 * &lt;/pre&gt;
 */
public class ipv4SubnetUtils {

    private static final int ARRAY_LENGTH = 4;
    private static final int MOD = 255;

    private static final IntFunction<Integer> NEGATE = i -> ~i & MOD;
    private static final IntBinaryOperator OR = (i, j) -> (i | j) & MOD;
    private static final IntBinaryOperator AND = (i, j) -> (i & j) & MOD;

    private static final IntPredicate validIP = (i) -> (i < 256) && (i > -1);


    /**
     * &lt;pre&gt;
     * broadcast = (id | (~mask &amp; 255)) &amp; 255
     * checks if the given ip is the broadcast address of the given subnet
     *
     * &#64;param ip     the ip in question
     * &#64;param subnet the subnet in question
     * &#64;return true if the ip is the broadcast of the subnet, false if not
     * &lt;/pre&gt;
     */
    public static boolean isValidBroadcast(int[] ip, IPv4Subnet subnet) {
        return Arrays.equals(subnet.getBroadcast().getIpv4Address(), ip);
    }

    /**
     * &lt;pre&gt;
     * broadcast = (id | (~mask &amp; 255)) &amp; 255
     * checks if the given ip is the broadcast address of the given subnet
     *
     * &#64;param ip     the ip in question
     * &#64;param mask   the subnetmask of the subnet in question
     * &#64;return true if the ip is the broadcast of the subnet, false if not
     * &lt;/pre&gt;
     */
    public static boolean isBroadcast(int[] ip, IPv4SubnetMask mask){
        int[]negMask = negateAll(mask.getSubnetMask());
        return Arrays.equals(ip, orAll(negMask, ip));
    }

    /**
     * &lt;pre&gt;
     * calculating the prefix by its subnetMask
     *
     * &#64;param subnetMask to breate the prefix from
     * &#64;return int, the prefix
     * &lt;/pre&gt;
     */
    public static int calcPrefixByMask(int[] subnetMask) {
        if (Arrays.stream(subnetMask).allMatch(i -> i == 0))
            return 0;

        int prefix = 0;

        for (int i : subnetMask) {
            if (i == 255)
                prefix += 8;
            else if (i != 0) {
                prefix += choose(i);
            }
        }
        return prefix;
    }

    /**
     * &lt;pre&gt;
     * method to calculate the maximum amount of hosts of a subnet by a given prefix
     * &#64;param prefix prefix of a subnet
     * &#64;return long maximum amount of hosts of that prefix
     * &lt;/pre&gt;
     */
    public static long calcMaxHosts(int prefix) throws FalsePrefixExeption {
        if(!isValidPrefix(prefix))
            throw new FalsePrefixExeption("A false prefix was detected: " + prefix);

        if (prefix == 32)
            return 0;
        return (long) Math.pow(2, 32 - prefix) - 2;
    }

    /**
     * &lt;pre&gt;
     * calculated a subnetmask by a given prefix
     *
     * &#64;param prefix the prefix to calculate the mask by
     * &#64;return IPv4SubnetMask
     * &lt;/pre&gt;
     */
    public static int[] calcMaskByPrefix(int prefix) throws FalsePrefixExeption {
        if (!isValidPrefix(prefix))
            throw new FalsePrefixExeption();

        int[] temp = {0, 0, 0, 0};
        int mod = prefix % 8; //2^this = last part
        int part = prefix / 8; //the amount of 255 in the subnet
        IntStream.range(0, part).forEach(i -> temp[i] = 255);
        if (mod != 0) {
            for (int i = 1; i <= mod; i++)
                temp[part] += (int) Math.pow(2, 8 - i);
        }
        return temp;
    }

    /**
     * &lt;pre&gt;
     * method to choose a subnet mask part by an integer
     * &#64;param i int part of the prefix
     * &#64;return one of 8 values, -1 if nothing was found
     * &lt;/pre&gt;
     */
    private static int choose(int i) {
        switch (i) {
            case (128):
                return 1;
            case (192):
                return 2;
            case (224):
                return 3;
            case (240):
                return 4;
            case (248):
                return 5;
            case (252):
                return 6;
            case (254):
                return 7;
            default:
                return -1;
        }
    }

    /**
     * &lt;pre&gt;
     * broadcast = (id | (~mask &amp; 255)) &amp; 255
     * calculates the broadcast address of that given subnet.
     * the subnetMask networkID have to be set
     *
     * &#64;param mask the subnet mask to calculate the broadcast for
     * &#64;param id   the network id
     * &#64;return int[] the broadcast address
     * &lt;/pre&gt;
     */
    public static IPv4BroadcastAddress calcBroadcast(IPv4SubnetMask mask, IPv4NetworkID id) {
        int[] negMask = negateAll(mask.getSubnetMask());
        int[] array = orAll(negMask, id.getIpv4Address());

        return new IPv4BroadcastAddress(array);
    }

    /**
     * &lt;pre&gt;
     * host = ((subnet.mask &amp; ip) &amp; 255) = subnet.id
     * checks if the ip is a host of the given subnet
     *
     * &#64;param ip     int[] the ip address to check
     * &#64;param subnet org.mnm.ipv4.subnet.IPv4Subnet the subnet in which this ip
     * &#64;return true if the host belongs to the subnet, false if not
     * &lt;/pre&gt;
     */
    public static boolean isHost(int[] ip, IPv4Subnet subnet) {
        if (!isValidIP(ip))
            return false;

        int[] id = subnet.getNetID().getIpv4Address();
        int[] mask = subnet.getSubnetMask().getSubnetMask();
        int[] broad = subnet.getBroadcast().getIpv4Address();
        if (Arrays.equals(ip, id))
            return false;

        if (Arrays.equals(ip, broad))
            return false;

        int[] idANDmask = andALL(mask, id);
        int[] ipANDmask = andALL(mask, ip);
        return Arrays.equals(idANDmask, ipANDmask);
    }

    /**
     * &lt;pre&gt;
     * checks if an ip is a valid one
     *
     * &#64;param ip int[] the ip in question
     * &#64;return true if the ip is valid
     * and false, if it is not
     * &lt;/pre&gt;
     */
    public static boolean isValidIP(int[] ip) {
        if(ip.length > 4)
            return false;

        return Arrays.stream(ip)
                .allMatch(validIP);
    }

    /**
     * &lt;pre&gt;
     * checks if an ip is a valid one
     *
     * &#64;param ip int[] the ip in question
     * &#64;return true if the ip is valid
     * and false, if it is not
     * &lt;/pre&gt;
     */
    public static boolean isValidIP(String ip) {
        return isValidIP(Arrays.stream(ip.split("\\."))
                            .mapToInt(Integer::parseInt)
                            .toArray());
    }

    /**
     * &lt;pre&gt;
     * helper method to get the index of a array element by a predicate. Only the first hit is returned
     *
     * &#64;param ip   the ip to get the element index from
     * &#64;param pred the predicate to check
     * &#64;return int the index of the first element matching the predicate
     * &lt;/pre&gt;
     */
    private static int getIndex(int[] ip, IntPredicate pred) {
        return IntStream.range(0, 4)
                .filter(pred)
                .mapToObj(i -> i)
                .findFirst()
                .get();
    }

    /**
     * &lt;pre&gt;
     * netID = (id &amp; mask) &amp; 255
     * checks if a network id is valid
     *
     * &#64;param ip the ip in question
     * &#64;return true if the valid net id of the subnet, else false
     * &lt;/pre&gt;
     */
    public static boolean isValidNetID(int[] ip, IPv4SubnetMask mask) {
        boolean valid = false;
        if (isValidIP(ip)) {
            valid = ip[3] < 253;
        }

        valid = isNetID(ip, mask);
        return valid;
    }


    /**
     * &lt;pre&gt;
     * netID = (ip &amp; mask) &amp; 255
     * checks if a ip is the networkID of the given subnet mask
     *
     * &#64;param ip   int[] the ip address to check
     * &#64;param mask a subnet mask to check the id against
     * &#64;return true if the ip is the networkID, false if not
     * &lt;/pre&gt;
     */
    public static boolean isNetID(int[] ip, IPv4SubnetMask mask) {
        return Arrays.equals(andALL(ip, mask.getSubnetMask()), ip);
    }


    /**
     * &lt;pre&gt;
     * checks if a subnetMak is a valid one
     *
     * &#64;param mask the subnetMask in question
     * &#64;return true if it is a valid subnetMask, false if not
     * &lt;/pre&gt;
     */
    public static boolean isValidSubnetMask(int[] mask) {
        if(!isValidIP(mask))
            return false;

        String binaryMask = toBinaryString(mask).replaceAll("\\.", "");
        int index = binaryMask.indexOf("0");
        if (index != -1)
            return !binaryMask.substring(index).contains("1");
        else
            return true;
    }

    /**
     * &lt;pre&gt;
     * checks if a subnetMak is a valid one
     *
     * &#64;param mask the subnetMask in question
     * &#64;return true if it is a valid subnetMask, false if not
     * &lt;/pre&gt;
     */
    public static boolean isValidSubnetMask(String mask) {
        return isValidSubnetMask(Arrays.stream(mask.split("\\."))
                                    .mapToInt(Integer::parseInt)
                                    .toArray());
    }

    /**
     * &lt;pre&gt;
     * returns the input array as a binary representation, delimited by comma
     *
     * &#64;param array to turn into binary representation
     * &#64;return String array as binary representation, delimited with comma
     * &lt;/pre&gt;
     */
    public static String toBinaryString(int[] array) {
        return Arrays.stream(array)
                .mapToObj(i -> Integer.toBinaryString(i))
                .collect(Collectors.joining("."));
    }

    /**
     * &lt;pre&gt;
     * negates all int values of an array
     *
     * &#64;param array the array to negate its values
     * &#64;return int[] a new array that has the negated values the input array
     * &lt;/pre&gt;
     */
    public static int[] negateAll(int[] array) {
        return Arrays.stream(array)
                .map(i -> NEGATE.apply(i))
                .toArray();
    }

    /**
     * &lt;pre&gt;
     * does a bitwise or with all the values of the input arrays
     * arr1[i] | arr2[i]
     *
     * &#64;param arr1 array one to be bitwise ored
     * &#64;param arr2 array two to be bitwise ored
     * &#64;return int[] new array with the result of the bitwise or of all values
     * &lt;/pre&gt;
     */
    public static int[] orAll(int[] arr1, int[] arr2) {
        int[] res = new int[ARRAY_LENGTH];
        IntStream.range(0, ARRAY_LENGTH)
                .forEach(i -> res[i] = OR.applyAsInt(arr1[i], arr2[i]));

        return res;
    }

    /**
     * &lt;pre&gt;
     * does a bitwise and with all the values of the input arrays
     * arr1[i] | arr2[i]
     *
     * &#64;param arr1 array one to be bitwise anded
     * &#64;param arr2 array two to be bitwise anded
     * &#64;return int[] new array with the result of the bitwise and of all values
     * &lt;/pre&gt;
     */
    public static int[] andALL(int[] arr1, int[] arr2) {
        int[] res = new int[ARRAY_LENGTH];
        IntStream.range(0, ARRAY_LENGTH)
                .forEach(i -> res[i] = AND.applyAsInt(arr1[i], arr2[i]));

        return res;
    }

    /**
     * &lt;pre&gt;
     * calculating the prefix ba the amount of hosts
     *
     * &#64;param hosts long, the amount of hosts
     * &#64;return int, the prefix
     * &lt;/pre&gt;
     */
    public static int calcPrefixByHosts(long hosts) {
        if (hosts == 0)
            return 32;
        long tempHosts = 0;
        int prefix;
        for (prefix = 0; prefix < 33; prefix++) {
            tempHosts = (long) Math.pow(2, prefix)-2;
            if (tempHosts >= hosts)
                return 32 - prefix;
        }
        return -1;
    }

    public static List<IPv4HostAddress> getAllHosts(IPv4Subnet subnet) {
        int [] first = subnet.getMinHost().getIpv4Address();
        int [] last = subnet.getMaxHost().getIpv4Address();
        int [] tempHost = first;
        int index = 3;
        List<IPv4HostAddress> hostAddressList = new ArrayList<>();
        long amountHosts = subnet.getSubnetMask().getMaxHosts();

        for(long l = 0; l < amountHosts; l++){
            tempHost[index] += 1;
            hostAddressList.add(new IPv4HostAddress(tempHost));
            if(Arrays.equals(tempHost, last))
                return hostAddressList;
        }
        return hostAddressList;
    }

    public static IPv4HostAddress calcMaxHost(IPv4BroadcastAddress broadcastAddress){
            int[] h = broadcastAddress.getIpv4Address();
            h[3] = h[3]-1;
            return new IPv4HostAddress(h);
    }

    public static IPv4HostAddress calcMinHost(IPv4NetworkID iPv4NetworkID){
        int[] h = iPv4NetworkID.getIpv4Address();
        h[3] = h[3]+1;
        return new IPv4HostAddress(h);
    }

    /**
     * &lt;pre&gt;
     * Method validating if a prefix is valid
     *
     * &#64;param prefix int, the prefix
     * &#64;return true, if prefix is valid, false, if prefix is not valid
     * &lt;/pre&gt;
     */
    public static boolean isValidPrefix(int prefix) {
        return prefix >= 0 && prefix < 33;
    }
}
