package ru.sfedu.opensv.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import ru.sfedu.opensv.ImageAPI;
import ru.sfedu.opensv.ImageAPITest;

import java.io.IOException;

public class ImageTest {

    private static final Logger log = LogManager.getLogger(ImageAPITest.class);

    @Test
    public void testDropImageChannels() {
        try {
            log.info("TESTS Image");
            log.info("---------------------------------------");
            ImageAPI imageAPI = new ImageAPI();
            Mat image = Image.readImage("sample2.png");
            log.info("Image read successfully");
            Image.showImage(image, "Origin");
            for (int i = 0; i < 3; i++) {
                testDropImageChannel(image, i);
            }

        } catch (Exception e) {
            log.error("Exception thrown: " + e.getMessage());
        }
    }

    private void testDropImageChannel(Mat image, int channel) throws IOException {
        Mat imageWithoutChannel = image.clone();
        Image.dropImageChannel(channel, imageWithoutChannel);
        Image.writeImage("sample_without_channel_" + channel + ".png",imageWithoutChannel);
        Image.showImage(imageWithoutChannel, "Without channel " + channel);
        log.info("Saved image - sample_without_channel_" + channel);
    }
}
