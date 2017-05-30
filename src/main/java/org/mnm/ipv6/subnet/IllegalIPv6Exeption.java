package org.mnm.ipv6.subnet;

/**
 * Created by martin on 17/05/17.
 */
public class IllegalIPv6Exeption extends RuntimeException {

    public IllegalIPv6Exeption() {
        super("An invalid ipv6 was detected.");
    }

    public IllegalIPv6Exeption(String s) {
        super(s);
    }
}
