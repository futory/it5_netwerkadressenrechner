package org.mnm.ipv4.subnet;

import it5.p04.fileadapter.Type;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * &lt;pre&gt;
 * Programmatic representation of an Ipv4SubnetMask.
 *
 * Has an int[] = {0,0,0,0}
 *
 * Created by martin on 08/04/17.
 * &lt;/pre&gt;
 */
public class IPv4SubnetMask {

    private Type type = Type.MASK;

    public static final long MAXIMUM_AMOUNT_OF_HOSTS = 4294967294L;

    private long maxHosts;
    private int prefix;
    private int[] subnetMask = {0, 0, 0, 0};

    public IPv4SubnetMask(Builder builder) {
        this.subnetMask = builder.subnetMask;
        this.maxHosts = builder.maxHosts;
        this.prefix = builder.prefix;
    }

    /**
     * &lt;pre&gt;
     * &#64;Override
     *          toString
     *
     * &#64;return a dotted decimal notation ot the IPv4Address
     * &lt;/pre&gt;
     */
    @Override
    public String toString() {
        return Arrays.stream(subnetMask)
                .mapToObj(i -> ((Integer) i).toString())
                .collect(Collectors.joining("."));
    }

    /**
     * &lt;pre&gt;
     * &#64;Override
     *          toString
     *
     * &#64;return a dotted binary notation ot the IPv4Address
     * &lt;/pre&gt;
     */
    public String toBinaryString() {
        return Arrays.stream(subnetMask)
                .mapToObj(i -> ((Integer) i).toBinaryString(i))
                .collect(Collectors.joining("."));
    }

    public long getMaxHosts() {
        return maxHosts;
    }

    public void setMaxHosts(long maxHosts) {
        this.maxHosts = maxHosts;
    }

    public int[] getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(int[] subnetMask) {
        this.subnetMask = subnetMask;
    }

    public int getPrefix() {
        return prefix;
    }

    public void setPrefix(int prefix) {
        this.prefix = prefix;
    }

    public Type getType(){
        return type;
    }

    /**
     * &lt;pre&gt;
     * private class capable of building a IPv4SubnetMask, either by prefix, array or by string
     * &lt;/pre&gt;
     */
    public static class Builder {

        private long maxHosts;

        private int prefix;

        private int[] subnetMask;

        public IPv4SubnetMask.Builder maxHosts(long maxHosts) {
            this.maxHosts = maxHosts;
            return this;
        }

        public IPv4SubnetMask.Builder prefix(int prefix) {
            this.prefix = prefix;
            return this;
        }

        public IPv4SubnetMask.Builder subnetMask(int[] subnetMask) {
            this.subnetMask = subnetMask;
            return this;
        }

        /**
         * &lt;pre&gt;
         * builds the IPv4SubnetMask
         * &#64;return IPv4SubnetMask
         * &lt;/pre&gt;
         */
        public IPv4SubnetMask build() {
            return new IPv4SubnetMask(this);
        }

        /**
         * &lt;pre&gt;
         * builds an IPv4SubnetMask by its prefix with validation
         *
         * &#64;param prefix int, the prefix notation of the subnetMask
         * &#64;return IPv4SubnetMask
         * &#64;throws SubnetBuildingError
         * &#64;throws FalsePrefixExeption
         * &lt;/pre&gt;
         */
        public IPv4SubnetMask buildByPrefix(int prefix) throws SubnetBuildingError, FalsePrefixExeption {

            if (ipv4SubnetUtils.isValidPrefix(prefix))
                this.prefix = prefix;
            else
                throw new SubnetBuildingError("A false prefix was detected: " + prefix);

            subnetMask = ipv4SubnetUtils.calcMaskByPrefix(prefix);
            if (!ipv4SubnetUtils.isValidSubnetMask(subnetMask))
                throw new SubnetBuildingError("A false subnet mask was detected: " + subnetMask.toString());

            this.maxHosts = ipv4SubnetUtils.calcMaxHosts(prefix);

            return new IPv4SubnetMask(this);
        }

        /**
         * &lt;pre&gt;
         * builds an IPv4SubnetMask by String ("255.255.255.0") with validation
         *
         * &#64;param string the String notation of the SubnetMask, is validated before building
         * &#64;return IPv4SubnetMask
         * &#64;throws SubnetBuildingError
         * &#64;throws FalsePrefixExeption
         * &lt;/pre&gt;
         */
        public IPv4SubnetMask buildByString(String string) throws SubnetBuildingError, FalsePrefixExeption {

            return this.buildByArray(Stream.of(string.split("\\.")).mapToInt(Integer::parseInt).toArray());
        }

        /**
         * &lt;pre&gt;
         * builds an IPv4SubnetMask by int array with validation
         *
         * &#64;param mask int[], the subnetMask to be build
         * &#64;return IPv4SubnetMask
         * &#64;throws SubnetBuildingError
         * &#64;throws FalsePrefixExeption
         * &lt;/pre&gt;
         */
        public IPv4SubnetMask buildByArray(int[] mask) throws SubnetBuildingError, FalsePrefixExeption {
            this.subnetMask = mask;
            if (!ipv4SubnetUtils.isValidSubnetMask(subnetMask))
                throw new SubnetBuildingError("A false subnet mask was detected: " + subnetMask.toString());

            this.prefix = ipv4SubnetUtils.calcPrefixByMask(this.subnetMask);
            this.maxHosts = ipv4SubnetUtils.calcMaxHosts(this.prefix);

            return new IPv4SubnetMask(this);
        }
    }
}