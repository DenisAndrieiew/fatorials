package org.andrieiew.plex.params;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PACKAGE)
public class AppParams {
    public final static String DEFAULT_FILE_NAME = "input.txt";

    private String fileName = DEFAULT_FILE_NAME;
    private int threadsCount;
    private boolean skipEmptyLines = false;
    private boolean skipStrings = false;
    private boolean isDebug = false;
    private boolean skipDelays = false;

    protected AppParams() {
    }


}
