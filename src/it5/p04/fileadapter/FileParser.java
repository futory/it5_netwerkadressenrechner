package it5.p04.fileadapter;

import org.mnm.ipv4.ipv4.IPv4Address;
import org.mnm.ipv4.ipv4.IPv4BroadcastAddress;
import org.mnm.ipv4.ipv4.IPv4HostAddress;
import org.mnm.ipv4.ipv4.IPv4NetworkID;
import org.mnm.ipv4.subnet.IPv4Subnet;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * reading a subnet file. format is:
 * <p>
 *      subnet name
 *      flag_type; address
 *      flag_type; address
 *      flag_type; address
 *      .
 *      .
 *      .
 * <p>
 * Created by martin on 15/05/17.
 */
public class FileParser {
    private String path;

    private Stream<String> file;

    private List<IPv4HostAddress> ipv4hosts;
    private IPv4BroadcastAddress broadcastAddress;
    private IPv4NetworkID networkID;

    public FileParser(String path) throws Exception {
        this.path = path;
        this.file = Files.lines(Paths.get(path));
    }

    public IPv4Subnet parse() {
        IPv4Subnet subnet = new IPv4Subnet();

        fillHosts(file.filter(l -> l.startsWith(String.valueOf(Types.HOST))));
        this.broadcastAddress = (IPv4BroadcastAddress) getByType(Types.BROADCAST);
        this.networkID = (IPv4NetworkID) getByType(Types.NETID);

        subnet.setName(String.valueOf(file.findFirst()));
        ipv4hosts.stream().forEach(h -> subnet.addHost(h));
        subnet.setBroadcastAddress(broadcastAddress);
        subnet.setNetworkID(networkID);
        return subnet;
    }

    private void fillHosts(Stream<String> file) {
        file
                .forEach(l -> ipv4hosts.add(new IPv4HostAddress(l.split(";")[1])));
    }

    private IPv4Address getByType(Types t) {
        return new IPv4BroadcastAddress(
                String.valueOf(
                        file.filter(l -> l.startsWith(String.valueOf(t))).findFirst()));
    }

}
