package it5.p04.fileadapter;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


/**
 * Created by martin on 16/05/17.
 */
class FileDeleterTest {
    @BeforeEach
    void setUP() throws IOException {
        File f = new File("resources/out/test.txt");
        f.createNewFile();
    }

    @Test
    void delete() {
        FileDeleter deleter = new FileDeleter();
        deleter.delete("test.txt");
    }

}