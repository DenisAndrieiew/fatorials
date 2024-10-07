package org.andrieiew.plex.params;

public class RunParamsResolver {

    public static AppParams resolve(String[] args) {
        boolean isFileParamSet = false;
        boolean isThreadsParamSet = false;
        boolean isSkipDelaysSet = false;
        AppParams appParams = new AppParams();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-f":
                case "--file":
                    if (!isFileParamSet && i + 1 < args.length) {
                        appParams.setFileName(args[++i]);
                        isFileParamSet = true;
                    }
                    break;
                case "-t":
                case "--threads":
                    if (!isThreadsParamSet && i + 1 < args.length) {
                        int count = 0;
                        try {
                            isThreadsParamSet = true;
                            count = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException ignored) {
                        }
                        appParams.setThreadsCount(count);
                    }
                    break;
                case "-se":
                case "--skip-empty":
                    appParams.setSkipEmptyLines(true);
                    break;
                case "-ss":
                case "--skip-strings":
                    appParams.setSkipStrings(true);
                    break;
                case "-dbg":
                case "--debug":
                    appParams.setDebug(true);
                    break;
                case "-sd":
                case "--skip-delays":
                    if (!isSkipDelaysSet) {
                        appParams.setSkipDelays(true);
                        isSkipDelaysSet = true;
                    }
                    break;
                default:
            }
        }

        ParamsValidator validator = new ParamsValidator();
        return validator.validate(appParams);
    }
}
