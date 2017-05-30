package org.mnm.ipv4.subnet;

/**
 * &lt;pre&gt;
 * Exeption extends RuntimeException
 * throw, if an Error occured during SubnetBuilding
 *
 * Created by martin on 09/05/17.
 * &lt;/pre&gt;
 */
public class SubnetBuildingError extends Exception {

    public SubnetBuildingError(String message) {
        super(message);
    }

    public SubnetBuildingError() {
        super("An error happened during the creation of the subnet");
    }
}
