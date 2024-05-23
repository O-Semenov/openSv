package ru.sfedu.opensv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;

public class ImageAPITest {
    private static final Logger log = LogManager.getLogger(ImageAPITest.class);

    @Test
    public void testImageAPI() {
        try {
            log.info("TESTS ImageApi");
            log.info("---------------------------------------");
            ImageAPI imageAPI = new ImageAPI();
            log.info("OS version - " + Constants.getOperatingSystemType());
            log.info("Open CV version - " + Core.getVersionString());
        } catch (Exception e) {
            log.error("Exception thrown: " + e.getMessage());
        }
    }
}
