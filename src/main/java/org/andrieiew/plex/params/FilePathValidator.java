package org.andrieiew.plex.params;

import java.io.File;

public class FilePathValidator {

        public static boolean isValid(String path) {
            File file = new File(path);
            return file.exists() && file.isFile();
        }
}
