package it5.p04.fileadapter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * reading a subnet file. format is:
 *
 * flag_type; address; flag_v6/v4;
 *
 * Created by martin on 15/05/17.
 */
public class FileParser {
    private String path;

    private List<String> file;

    HashMap<String, String> hosts;

    public FileParser(String path) throws Exception{
        this.path = path;
        this.file = Files.lines(Paths.get(path)).collect(Collectors.toList());
    }

    public IPv4Subnet parse(){
        subnet.setName(String.valueOf(file.get(0)));
        fillHosts();

    }

    private void fillHosts(){
        file.stream()
                .filter(l -> l.startsWith(String.valueOf(Types.HOST)))
                .forEach(l -> {
                    hosts.put(
                            l.split(";")[1],
                            l.split(";")[2]);
                });
    }

}
