package it5.p04.fileadapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

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

        files.contains("TestFile16052017101858.txt");
    }

}