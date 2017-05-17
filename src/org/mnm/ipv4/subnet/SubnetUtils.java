package org.mnm.ipv4.subnet;

import org.mnm.ipv4.ipv4.IPv4BroadcastAddress;
import org.mnm.ipv4.ipv4.IPv4NetworkID;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by martin on 04/05/17.
 * <p>
 * A helper class able to validate various elements of a subnet, like a host/broadcast ip, subnet mask, or a net id
 */
public class SubnetUtils {

    private static final int ARRAY_LENGTH = 4;
    private static final int MOD = 255;

    private static final IntFunction<Integer> NEGATE = i -> ~i & MOD;

    private static final IntBinaryOperator OR = (i, j) -> (i | j) & MOD;
    private static final IntBinaryOperator AND = (i, j) -> (i & j) & MOD;

    private static final IntPredicate validIP = (i) -> (i < 256) && (i > -1);


    /**
     * broadcast = (id | (~mask & 255)) & 255
     * checks if the given ip is the broadcast address of the given subnet
     *
     * @param ip     the ip in question
     * @param subnet the subnet in question
     * @return true if the ip is the broadcast of the subnet, false if not
     */
    public static boolean isValidBroadcast(int[] ip, IPv4Subnet subnet) {
        return Arrays.equals(subnet.getBroadcast().getIpv4Address(), ip);
    }

    /**
     * broadcast = (id | (~mask & 255)) & 255
     * checks if the given ip is the broadcast address of the given subnet
     *
     * @param ip     the ip in question
     * @param mask   the subnetmask of the subnet in question
     * @return true if the ip is the broadcast of the subnet, false if not
     */
    public static boolean isBroadcast(int[] ip, IPv4SubnetMask mask){
        int[]negMask = negateAll(mask.getSubnetMask());
        return Arrays.equals(ip, orAll(negMask, ip));
    }

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

    public static long calcMaxHosts(int prefix) {
        if (prefix == 32)
            return 0;
        return (long) Math.pow(2, 32 - prefix) - 2;
    }

    public static int[] calcMaskByPrefix(int prefix) {
        if (prefix > 32)
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
     * broadcast = (id | (~mask & 255)) & 255
     * calculates the broadcast address of that given subnet.
     * the subnetMask networkID have to be set
     *
     * @param mask the subnet mask to calculate the broadcast for
     * @param id   the network id
     * @return int[] the broadcast address
     */
    public static IPv4BroadcastAddress calcBroadcast(IPv4SubnetMask mask, IPv4NetworkID id) {
        int[] negMask = negateAll(mask.getSubnetMask());
        int[] array = orAll(negMask, id.getIpv4Address());

        return new IPv4BroadcastAddress(array);
    }

    /**
     * host = ((subnet.mask & ip) & 255) = subnet.id
     * checks if the ip is a host of the given subnet
     *
     * @param ip     int[] the ip address to check
     * @param subnet org.mnm.ipv4.subnet.IPv4Subnet the subnet in which this ip
     * @return true if the host belongs to the subnet, false if not
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
     * checks if an ip is a valid one
     *
     * @param ip int[] the ip in question
     * @return true if the ip is valid
     * and false, if it is not
     */
    public static boolean isValidIP(int[] ip) {
        if(ip.length > 4)
            return false;

        return Arrays.stream(ip)
                .allMatch(validIP);
    }

    /**
     * helper method to get the index of a array element by a predicate. Only the first hit is returned
     *
     * @param ip   the ip to get the element index from
     * @param pred the predicate to check
     * @return int the index of the first element matching the predicate
     */
    private static int getIndex(int[] ip, IntPredicate pred) {
        return IntStream.range(0, 4)
                .filter(pred)
                .mapToObj(i -> i)
                .findFirst()
                .get();
    }

    /**
     * netID = (id & mask) & 255
     * checks if a network id is valid
     *
     * @param ip the ip in question
     * @return true if the valid net id of the subnet, else false
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
     * netID = (ip & mask) & 255
     * checks if a ip is the networkID of the given subnet mask
     *
     * @param ip   int[] the ip address to check
     * @param mask a subnet mask to check the id against
     * @return true if the ip is the networkID, false if not
     */
    public static boolean isNetID(int[] ip, IPv4SubnetMask mask) {
        return Arrays.equals(andALL(ip, mask.getSubnetMask()), ip);
    }


    /**
     * checks if a subnetMak is a valid one
     *
     * @param mask the subnetMask in question
     * @return true if it is a valid subnetMask, false if not
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
     * returns the input array as a binary representation, delimited by comma
     *
     * @param array to turn into binary representation
     * @return String array as binary representation, delimited with comma
     */
    public static String toBinaryString(int[] array) {
        return Arrays.stream(array)
                .mapToObj(i -> Integer.toBinaryString(i))
                .collect(Collectors.joining("."));
    }

    /**
     * negates all int values of an array
     *
     * @param array the array to negate its values
     * @return int[] a new array that has the negated values the input array
     */
    public static int[] negateAll(int[] array) {
        return Arrays.stream(array)
                .map(i -> NEGATE.apply(i))
                .toArray();
    }

    /**
     * does a bitwise or with all the values of the input arrays
     * arr1[i] | arr2[i]
     *
     * @param arr1 array one to be bitwise ored
     * @param arr2 array two to be bitwise ored
     * @return int[] new array with the result of the bitwise or of all values
     */
    public static int[] orAll(int[] arr1, int[] arr2) {
        int[] res = new int[ARRAY_LENGTH];
        IntStream.range(0, ARRAY_LENGTH)
                .forEach(i -> res[i] = OR.applyAsInt(arr1[i], arr2[i]));

        return res;
    }

    /**
     * does a bitwise and with all the values of the input arrays
     * arr1[i] | arr2[i]
     *
     * @param arr1 array one to be bitwise anded
     * @param arr2 array two to be bitwise anded
     * @return int[] new array with the result of the bitwise and of all values
     */
    public static int[] andALL(int[] arr1, int[] arr2) {
        int[] res = new int[ARRAY_LENGTH];
        IntStream.range(0, ARRAY_LENGTH)
                .forEach(i -> res[i] = AND.applyAsInt(arr1[i], arr2[i]));

        return res;
    }

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

    public static boolean isValidPrefix(int prefix) {
        return prefix >= 0 && prefix < 33;
    }
}
