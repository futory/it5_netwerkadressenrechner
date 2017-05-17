package it5.p04.fileadapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by martin on 16/05/17.
 */
class FileListerTest {
    @BeforeEach
    void setUP(){

    }

    @Test
    void listFileNames() {
        FileLister lister = new FileLister();
        List<Path> files = lister.listFileNames();
        for(Path s : files)
            System.out.println(s.getFileName());
    }

}