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
    private CacheType cacheType = CacheType.NONE;
    private boolean skipEmptyLines = false;
    private boolean skipStrings = false;
    private boolean isDebug = false;

    protected AppParams() {
    }

    public enum CacheType {
        NONE,
        FULL,
        STEPS;

        public static CacheType fromString(String value) {
            for (CacheType type : CacheType.values()) {
                if (type.name().equalsIgnoreCase(value)) {
                    return type;
                }
            }
            return null;
        }
    }

}
