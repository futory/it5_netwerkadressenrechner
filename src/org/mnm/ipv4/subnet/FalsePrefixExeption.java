package org.mnm.ipv4.subnet;

/**
 * Exeption extends RuntimeException
 * throw, if an Prefix was determined to be false
 *
 * Created by martin on 11/04/17.
 */
public class FalsePrefixExeption extends Exception {

    public FalsePrefixExeption() {
        super("An invalid Prefix was detected.");
    }

    public FalsePrefixExeption(String message) {
        super(message);
    }

}
