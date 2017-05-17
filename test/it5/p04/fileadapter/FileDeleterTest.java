package it5.p04.fileadapter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by martin on 16/05/17.
 */
class FileDeleterTest {
    @Test
    void delete() {
        FileDeleter deleter = new FileDeleter();
        deleter.delete("TestFile16052017101858.txt");
    }

}