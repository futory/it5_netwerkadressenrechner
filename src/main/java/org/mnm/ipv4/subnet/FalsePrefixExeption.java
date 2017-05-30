package org.mnm.ipv4.subnet;

/**
 * &lt;pre&gt;
 * Exeption extends RuntimeException
 * throw, if an Prefix was determined to be false
 *
 * Created by martin on 11/04/17.
 * &lt;/pre&gt;
 */
public class FalsePrefixExeption extends Exception {

    public FalsePrefixExeption() {
        super("An invalid Prefix was detected.");
    }

    public FalsePrefixExeption(String message) {
        super(message);
    }

}
