package org.andrieiew.plex.params;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilePathValidatorTest {
    @Disabled
    @Test
    public void testValidPath() {
        String file = "input.txt";
        assertTrue(FilePathValidator.isValid(file));
        String projectDir = System.getProperty("user.dir");
        assertTrue(FilePathValidator.isValid(projectDir + "/" + file));
    }

    @Test
    public void testInvalidPath() {
        String file = "input.txt";
        assertFalse(FilePathValidator.isValid(file + "1"));
        String projectDir = System.getProperty("user.dir");
        assertFalse(FilePathValidator.isValid(projectDir + "1/" + file));
        assertFalse(FilePathValidator.isValid(projectDir + "/" + file + "1"));
    }
}