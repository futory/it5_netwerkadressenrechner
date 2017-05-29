package it5.p04.fileadapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by martin on 16/05/17.
 */
public class FileLister {

    private static final String path = "resources/out/";

    public List<Path> listFileNames(){
        List<Path> files = new ArrayList<>();

        try {
            files = Files.list(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

}
