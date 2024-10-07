package org.andrieiew.plex.params;

import java.util.Objects;

public class ParamsValidator {
    private final Dialog dialog = Dialog.getInstance();

    public AppParams validate(AppParams appParams) {
        getValidFileName(appParams);
        getValidThreadsCount(appParams);
        return appParams;
    }

    private void getValidThreadsCount(AppParams appParams) {
        int threadsCount = appParams.getThreadsCount();
        while (threadsCount <= 0) {
            threadsCount = dialog.getInt("Please enter correct number of threads (greater than 0): ");
        }
        appParams.setThreadsCount(threadsCount);
    }

    private void getValidFileName(AppParams appParams) {
        String fileName = appParams.getFileName();
        while (!FilePathValidator.isValid(fileName)) {
            String message = Objects.equals(appParams.getFileName(), AppParams.DEFAULT_FILE_NAME) ?
                    "File 'input.txt' not found in project directory. Please enter correct file name or add file 'input.txt' to project directory and pres enter: " :
                    "File not found. Please enter correct file name or press Enter to use default file name ('input.txt): ";
            fileName = dialog.getString(message);
            if (fileName.isEmpty()) {
                fileName = AppParams.DEFAULT_FILE_NAME;
            }
        }
        appParams.setFileName(fileName);
    }

}
