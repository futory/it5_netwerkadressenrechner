package org.mnm.ipv4.subnet;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by martin on 08/04/17.
 */
public class IPv4SubnetMask {

    public static final long MAXIMUM_AMOUNT_OF_HOSTS = 4294967294L;

    private long maxHosts;
    private int prefix;
    private int[] subnetMask = {0, 0, 0, 0};

    public IPv4SubnetMask(Builder builder) {
        this.subnetMask = builder.subnetMask;
        this.maxHosts = builder.maxHosts;
        this.prefix = builder.prefix;
    }

    @Override
    public String toString() {
        return Arrays.stream(subnetMask)
                .mapToObj(i -> ((Integer) i).toString())
                .collect(Collectors.joining("."));
    }

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

        public IPv4SubnetMask build() {
            return new IPv4SubnetMask(this);
        }

        public IPv4SubnetMask buildByPrefix(int prefix) {

            if (SubnetUtils.isValidPrefix(prefix))
                this.prefix = prefix;
            else
                throw new SubnetBuildingError("A false prefix was detected: " + prefix);

            subnetMask = SubnetUtils.calcMaskByPrefix(prefix);
            if (!SubnetUtils.isValidSubnetMask(subnetMask))
                throw new SubnetBuildingError("A false subnet mask was detected: " + subnetMask.toString());

            this.maxHosts = SubnetUtils.calcMaxHosts(prefix);

            return new IPv4SubnetMask(this);
        }

        public IPv4SubnetMask buildByString(String string) {

            return this.buildByArray(Stream.of(string.split("\\.")).mapToInt(Integer::parseInt).toArray());
        }

        public IPv4SubnetMask buildByArray(int[] mask) {
            this.subnetMask = mask;
            if (!SubnetUtils.isValidSubnetMask(subnetMask))
                throw new SubnetBuildingError("A false subnet mask was detected: " + subnetMask.toString());

            this.prefix = SubnetUtils.calcPrefixByMask(this.subnetMask);
            this.maxHosts = SubnetUtils.calcMaxHosts(this.prefix);

            return new IPv4SubnetMask(this);
        }
    }
}