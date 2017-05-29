package it5.p04.fileadapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A class capable of deleting a file by its specified path
 *
 * Created by martin on 16/05/17.
 */
public class FileDeleter {

    private static final String path = "resources/out/";

    /**
     * A method capable of deleting a file by its specified fully qualified path
     *
     * @param fileName the file name, that will be added to the final path
     */
    public void delete(String fileName){
        try {
            Files.delete(Paths.get(path + fileName));
        } catch (IOException e) {
            System.out.println("File was not deleted: " + fileName);
            e.printStackTrace();
        }
    }

}
