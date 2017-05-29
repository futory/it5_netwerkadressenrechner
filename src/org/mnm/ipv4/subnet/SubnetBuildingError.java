package org.mnm.ipv4.subnet;

/**
 * Created by martin on 09/05/17.
 */
public class SubnetBuildingError extends Exception {

    public SubnetBuildingError(String message) {
        super(message);
    }

    public SubnetBuildingError() {
        super("An error happened during the creation of the subnet");
    }
}
