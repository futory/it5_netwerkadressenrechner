package org.mnm.ipv4.ipv4;

/**
 * &lt;pre&gt;
 * Exeption extends RuntimeException
 * throw, if an IP was determined to be false
 *
 * Created by martin on 15/05/17.
 * &lt;/pre&gt;
 */
public class FalseIPExeption extends RuntimeException {

    public FalseIPExeption() {
        super("A false IP was detected.");
    }

    public FalseIPExeption(String message) {
        super(message);
    }
}
