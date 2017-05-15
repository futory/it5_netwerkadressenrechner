package org.mnm.ipv4.ipv4;

/**
 * Created by martin on 15/05/17.
 */
public class FalseIPExeption extends RuntimeException {

    public FalseIPExeption() {
        super("A false IP was detected.");
    }

    public FalseIPExeption(String message) {
        super(message);
    }
}
