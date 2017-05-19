package org.mnm.ipv6.subnet;

import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by martin on 17/05/17.
 */
public class ipv6SubnetUtils {

    public static boolean isValidIP(String ipv6Address) {
        //(([0-9a-f]{1,4}\:\:?){1,7})([0-9a-f]{4})?(([0-9]\.|1[0-9][0-9]\.|2[0-4][0-9]\.|25[0-5]\.|[0-9][0-9]\.){3}[0-9][0-9]?[0-9]?)?
        // by Niklas Krahl, to be tested

        //http://stackoverflow.com/questions/53497/regular-expression-that-matches-valid-ipv6-addresses
        //kann cannot resolve ipv4 notation at the two last parts
        final String ipv6Pattern = "(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))";
        Pattern pat = Pattern.compile(ipv6Pattern);
        if(pat.matcher(ipv6Address).matches())
            return true;
        return false;
    }

    /**
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
     * @param ipv6Address
     * @return
     */
    public static int[] resolveIP(String ipv6Address) {
        if(ipv6Address.split(":").length > 8)
            throw new IllegalIPv6Exeption("The ipv6 " + ipv6Address + "was invalid (too many blocks)");



        return null;
    }

    /**
     * Method capable of transforming a string notation of a hexadecimal value into an integer.
     * Also checks if the hex value is within 0 <= hex <= 65536, else throws IllegalIPv6Exeption
     *
     * @param hex String representing a hexadecimal value
     * @return the decimal representation of this value
     */
    public static int hexToInt(String hex) {
        int i =  Integer.valueOf(hex, 16);
        if(i < 0 || i > 65536)
            throw new IllegalIPv6Exeption("An out of range (0 <= hex <= 65536) hexadecimal value was detected: " + hex);

        return i;
    }
}
