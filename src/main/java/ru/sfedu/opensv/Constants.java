package ru.sfedu.opensv;

import sun.awt.OSInfo;

import java.util.Locale;

public class Constants {
    public static String ENVIRONMENT_PATH = "env";
    public static String OPEN_SV_LIB_PATH_LINUX = "opensv.lib.path.linux";
    public static String OPEN_SV_LIB_PATH_WINDOWS = "opensv.lib.path.windows";
    public static String IMG_INPUT_PATH = "image.input.path";
    public static String IMG_OUTPUT_PATH = "image.output.path";

    public static OSInfo.OSType getOperatingSystemType() {
        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((OS.contains("mac")) || (OS.contains("darwin"))) {
            return OSInfo.OSType.MACOSX;
        } else if (OS.contains("win")) {
            return OSInfo.OSType.WINDOWS;
        } else if (OS.contains("nux")) {
            return OSInfo.OSType.LINUX;
        } else {
            return OSInfo.OSType.UNKNOWN;
        }
    }
}
