package org.mnm.ipv4.subnet;

/**
 * &lt;pre&gt;
 * Exeption extends RuntimeException
 * throw, if an the amount of hosts exeeds the maximum amount of hosts
 *
 * Created by martin on 08/04/17.
 * &lt;/pre&gt;
 */
public class TooManyHostsExeption extends RuntimeException {

    public TooManyHostsExeption() {
        super("The maximum amount of Hosts is 4294967296.");
    }

    public TooManyHostsExeption(String message) {
        super(message);
    }

}
