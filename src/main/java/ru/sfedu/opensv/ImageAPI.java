package ru.sfedu.opensv;

import ru.sfedu.opensv.utils.ConfigurationUtil;

public class ImageAPI {

    public ImageAPI() throws Exception
    {
        switch (Constants.getOperatingSystemType()) {
            case LINUX:
                System.load(ConfigurationUtil.getConfigurationEntry(Constants.OPEN_SV_LIB_PATH_LINUX));
                break;
            case WINDOWS:
                System.load(ConfigurationUtil.getConfigurationEntry(Constants.OPEN_SV_LIB_PATH_WINDOWS));
                break;
            case MACOSX:
                throw new Exception("Mac OS does not support!!!!!!!!");
            case UNKNOWN:
                throw new Exception("Current OS does not support!!!!!");
            default:
                throw new Exception("Your OS does not support!!!");
        }
    }

    public static void main(String[] args) throws Exception {
        ImageAPI imageAPI = new ImageAPI();
    }
}
