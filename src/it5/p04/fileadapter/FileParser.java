package it5.p04.fileadapter;

import org.mnm.ipv4.ipv4.IPv4BroadcastAddress;
import org.mnm.ipv4.ipv4.IPv4HostAddress;
import org.mnm.ipv4.ipv4.IPv4NetworkID;
import org.mnm.ipv4.subnet.IPv4Subnet;
import org.mnm.ipv4.subnet.SubnetBuildingError;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * reading a subnet file. format is:
 * &lt;p&gt;
 *      subnet name
 *      flag_type; address
 *      flag_type; address
 *      flag_type; address
 *      .
 *      .
 *      .
 * &lt;p&gt;
 * Created by martin on 15/05/17.
 */
public class FileParser {

    private IPv4BroadcastAddress broadcastAddress;
    private IPv4NetworkID networkID;

    List<String> lines;

    /**
     * constructor instantiating the class
     *
     * &#64;param path file path for the file to be parsed
     * &#64;throws Exception IOException |
     */
    public FileParser(String path) throws Exception {
        lines = Files.lines(Paths.get(path)).collect(Collectors.toList());
    }

    /**
     * class capable of parsing the files cerated by the class FileWriter
     *
     * &#64;return IPv4Subnet
     * &#64;throws IOException
     */
    public IPv4Subnet parse() throws IOException {
        IPv4Subnet subnet = new IPv4Subnet();


        this.broadcastAddress = new IPv4BroadcastAddress(getByType(Type.BROADCAST).get(0));
        this.networkID = new IPv4NetworkID(getByType(Type.NETID).get(0));

        subnet.setName(getByType(Type.NAME).get(0))
                .setBroadcastAddress(broadcastAddress)
                .setNetworkID(networkID);
        List<String> hosts = getByType(Type.HOST);
        for(String s : hosts)
            try {
                subnet.addHost(new IPv4HostAddress(s));
            } catch (SubnetBuildingError subnetBuildingError) {
                subnetBuildingError.printStackTrace();
            }
        return subnet;
    }

    /**
     * private class capable of splitting the lines read by the file reader
     *
     * &#64;param t Type of entry in line
     * &#64;return List&lt;String&gt; of line, delimited by ";"
     */
    private List<String> getByType(Type t){
        return lines.stream()
               .filter(l -> l.startsWith(String.valueOf(t)))
                .map(l -> l.split(";")[1])
                .collect(Collectors.toList());
    }
}
