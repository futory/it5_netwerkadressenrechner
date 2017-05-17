package it5.p04.fileadapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * TO BE CREATED
 *
 * Created by martin on 16/05/17.
 */
public class FileDeleter {

    private static final String path = "resources/out/";

    public void delete(String fileName){
        try {
            Files.delete(Paths.get(path + fileName));
        } catch (IOException e) {
            System.out.println("File was not deleted: " + fileName);
            e.printStackTrace();
        }
    }

}
