package it5.p04.fileadapter;


import org.mnm.ipv4.subnet.IPv4Subnet;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Class capable of transforming a IPv4Subnet into a textfile.
 *
 * Created by martin on 15/05/17.
 */
public class FileWriter {

    private static final String path = "resources/out/";
    private static final String suffix = ".txt";

    String date = new java.text.SimpleDateFormat("ddMMyyyyHHmmss").format(new java.util.Date());

    private List<String> out = new ArrayList<>();

    private IPv4Subnet subnet;

    private String fileName;

    /**
     * constructor instantiating the class
     *
     * @param fileName String, the name of the final file
     * @param subnet IPv4Subnet
     */
    public FileWriter (String fileName, IPv4Subnet subnet){
        this.fileName = fileName;
        this.subnet = subnet;
    }

    /**
     * method that writes the file
     *
     * @throws Exception IOExeption
     */
    public void write() throws Exception {
        add(String.valueOf(Type.NAME), subnet.getName());
        add(String.valueOf(Type.MASK), subnet.getSubnetMask().toString());
        subnet.getAddressList().stream()
                .forEach(l -> add(String.valueOf(l.getType()), l.toString()));

        Files.write(Paths.get(path + fileName + date + suffix), out);
    }

    private void add(String k, String s){
        out.add(k+ ";" + s);
    }
}
