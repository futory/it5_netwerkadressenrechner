package org.mnm.ipv4.subnet;

/**
 * Created by martin on 11/04/17.
 */
public class FalsePrefixExeption extends RuntimeException {

    public FalsePrefixExeption() {
        super("An invalid Prefix was detected.");
    }

    public FalsePrefixExeption(String message) {
        super(message);
    }

}
