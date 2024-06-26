package ru.sfedu.opensv.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.opencv.imgproc.Imgproc;
import ru.sfedu.opensv.ImageAPI;

public class ImageFiltrationAndMorphologicalTest {

    public static String FIRST_IMAGE_NAME = "sample2.png";
    public static String SECOND_IMAGE_NAME = "sample4.png";
    private static final Logger log = LogManager.getLogger(ImageFiltrationAndMorphologicalTest.class);

    @Test
    public void testFilter() {
        try {
            log.info("TEST - testFilter");
            log.info("---------------------------------------");
            ImageAPI imageAPI = new ImageAPI();
            for (int i = 1; i < 8; i+=2) {
                ImageFiltrationAndMorphological.filter(FIRST_IMAGE_NAME, i);
                log.info("Save images with kernel - " + i);
            }
        } catch (Exception e) {
            log.error("Exception thrown: {}", e.getMessage());
        }
    }

    @Test
    public void testMorphology() {
        try {
            log.info("TEST - testMorphology");
            log.info("---------------------------------------");
            ImageAPI imageAPI = new ImageAPI();
            int[] morphs = {Imgproc.MORPH_RECT, Imgproc.MORPH_ELLIPSE, Imgproc.MORPH_CROSS};
            for (int i = 3; i < 16; i+=2) {
                for (int morph : morphs) {
                    ImageFiltrationAndMorphological.morphology(SECOND_IMAGE_NAME, i, morph);
                    log.info("Save images with kernel - " + i + " morph - " + morph);
                }
            }
        } catch (Exception e) {
            log.error("Exception thrown: {}", e.getMessage());
        }
    }
}
