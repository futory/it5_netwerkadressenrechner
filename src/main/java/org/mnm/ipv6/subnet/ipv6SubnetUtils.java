package org.mnm.ipv6.subnet;

import java.net.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by martin on 17/05/17.
 */
public class ipv6SubnetUtils {

    //(([0-9a-f]{1,4}\:\:?){1,7})([0-9a-f]{1,4})?(([0-9]\.|1[0-9][0-9]\.|2[0-4][0-9]\.|25[0-5]\.|[0-9][0-9]\.){1,3}[0-9][0-9]?[0-9]?)?
    // by Niklas Krahl, he is a god
    // contains one error, does not detect, if more than one "::" is present
    public static final String IPV_6_PATTERN = "(\\[?([0-9a-f]{1,4}\\:\\:?){1,7})([0-9a-f]{1,4}\\]?)?(([0-9]\\.|1[0-9][0-9]\\.|2[0-4][0-9]\\.|25[0-5]\\.|[0-9][0-9]\\.){1,3}[0-9][0-9]?[0-9]?\\]?)?";
    public static final String containsIPv4 = "^\\[?[A-Fa-f0-9:]{0,30}[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\[?";
    //public static final String containsIPv4 = "^\\[?([A-Fa-f0-9:]{1,5}){1,6}([0-9\\.]{0,4}){0,4}\\]?";

    /**
     * &lt;pre&gt;
     * Method that validates an ipv6 as a String
     *
     * &#64;param ipv6Address string representing an ipv6 address
     * &#64;return true if valid, false, if invalid
     * &lt;/pre&gt;
     */
    public static boolean isValidIP(String ipv6Address) {
        Pattern pat = Pattern.compile(IPV_6_PATTERN);
        if(pat.matcher(ipv6Address.toLowerCase()).matches())
            if(countCharSeq(ipv6Address, "::") < 2)
                return true;
        return false;
    }


    private static int countCharSeq(String ipv6Address, String seq) {
        Pattern p = Pattern.compile(seq);
        Matcher m = p.matcher(ipv6Address);
        int count = 0;
        while (m.find()){
            count +=1;
        }
        return count;
    }


    /**
     * &lt;pre&gt;
     * Method used to transform a String notated ipv6Address into
     * an array.
     *
     * valid ipv6 follows the following rules:
     *
     * 8 blocks of 16 bit hexadecimal, delimited by ":"
     * leading zeros can be omitted
     * given there are several blocks of value zero, they can be replaced with "::"
     * replacing several blocks of zeros with "::" can only be done once
     * the last two blocks of 16 bits can be notated in decimal notation (as ipv4)
     *
     * &#64;param ipv6Address the ipv6 String to be resolved into a int[]
     * &#64;return the ipv6 String as int[]
     * &lt;/pre&gt;
     */
    public static int[] resolveIP(String ipv6Address) {
        if(containsIpv4(ipv6Address))
            return withIPv4Part(ipv6Address);
        else
            return withoutIPv4Part(ipv6Address);
    }

    public static boolean containsIpv4(String ipv6Address) {
        Pattern p = Pattern.compile(containsIPv4);
        return p.matcher(ipv6Address).matches();
    }

    private static int[] withIPv4Part(String ipv6Address) {
        if(ipv6Address.contains("::"))
            return complexResolvingWithIPv4(ipv6Address);
        else
            return simpleResolvingWithIPv4(ipv6Address);
    }

    private static int[] simpleResolvingWithIPv4(String ipv6Address) {
        String [] array = ipv6Address.split(":");
        int[] ip = {0,0,0,0,0,0,0,0};
        for(int i = 0; i < array.length-2; i = i+2){
            ip[i] = Integer.parseInt(array[i]);
        }
        return fillIPv4Part(ip, array[array.length-1]);
    }

    private static int[] fillIPv4Part(int[] ip, String ipv4) {
        String[] array = (String[]) Arrays.stream(ipv4.split("."))
                                            .mapToInt(Integer::parseInt)
                                            .mapToObj(i -> Integer.toHexString(i))
                                            .toArray();


        ip[6] = Integer.parseInt(array[0] + array[1]);
        ip[7] = Integer.parseInt(array[2] + array[3]);
        return ip;
    }

    private static int[] complexResolvingWithIPv4(String ipv6Address) {
        return null;
    }

    private static int [] withoutIPv4Part(String ipv6Address){
        if(ipv6Address.contains("::"))
            return complexResolving(ipv6Address);
        else
            return simpleResolving(ipv6Address);
    }

    /**
     * &lt;pre&gt;
     * Method able to resolve ipv6 Strings without "::"
     *
     * &#64;param ipv6Address the ipv6 String to be resolved into a int[]
     * &#64;return the ipv6 String as int[]
     * &lt;/pre&gt;
     */
    private static int[] simpleResolving(String ipv6Address) {
        return Stream.of(ipv6Address.split(":"))
                .mapToInt(hex -> Integer.parseInt(hex, 16))
                .toArray();
    }

    /**
     * &lt;pre&gt;
     * Method able to resolve ipv6 Strings with "::"
     *
     * &#64;param ipv6Address the ipv6 String to be resolved into a int[]
     * &#64;return the ipv6 String as int[]
     * &lt;/pre&gt;
     */
    private static int[] complexResolving(String ipv6Address) {
        //prepopulated ip address array. 0's will be replaced with actual values
        int[] ip = {0,0,0,0,0,0,0,0};
        //declaring part1 and part2, to avoid the "Variable might not be initialized" error
        String part1 = "0", part2 = "0";


        try {
            //splitting the full ipv6 into the part before and after the "::"
            part1 = ipv6Address.split("::")[0];
            part2 = ipv6Address.split("::")[1];
        }catch (ArrayIndexOutOfBoundsException e) {
            // do nothing
        }

        //declaring the sub parts as 0, to catch the following cases "::aaaa" or "aaaa::"
        String[] part1Parts = {"0"}, part2Parts = {"0"};

        //splitting the substrings by the ":"
        if(part1.contains(":") || !part1.equals(""))
            part1Parts = part1.split(":");

        if(part2.contains(":") || !part2.equals(""))
            part2Parts = part2.split(":");

        //counting the ":" in the part before "::"
        int count = countCharSeq(part1, ":");

        //filling the values before the "::" into the ip array
        for(int i = 0; i <= count; i++)
            ip[i] = hexToInt(part1Parts[i]);

        //filling the values after the "::" into the ip array
        for(int i = 7; i > 7 - part2Parts.length; i--)
            ip[i] = hexToInt(part2Parts[7-i]);

        return ip;
    }

    /**
     * &lt;pre&gt;
     * Method capable of transforming a string notation of a hexadecimal value into an integer.
     * Also checks if the hex value is within 0 &lt;= hex &lt;= 65536, else throws IllegalIPv6Exeption
     *
     * &#64;param hex String representing a hexadecimal value
     * &#64;return the decimal representation of this value
     * &lt;/pre&gt;
     */
    public static int hexToInt(String hex) {
        int i =  Integer.valueOf(hex, 16);
        if(i < 0 || i > 65536)
            throw new IllegalIPv6Exeption("An out of range (0 <= hex <= 65536) hexadecimal value was detected: " + hex);

        return i;
    }
}
