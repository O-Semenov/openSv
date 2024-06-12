package ru.sfedu.opensv.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import ru.sfedu.opensv.ImageAPI;

import java.io.IOException;

public class ImageSegmentationTest {

    public static String FIRST_IMAGE_NAME = "sample4.png";
    public static String SECOND_IMAGE_NAME = "sample5.png";
    public static String THIRD_IMAGE_NAME = "sample6.png";
    private static final Logger log = LogManager.getLogger(ImageSegmentationTest.class);

    @Test
    public void testFlood() {
        try {
            log.info("TEST - testFlood");
            log.info("---------------------------------------");
            ImageAPI imageAPI = new ImageAPI();
            Mat image = Image.readImage(FIRST_IMAGE_NAME);
            log.info("Image read successfully");
            ImageSegmentation.flood(
                    image,
                    new Point(0, 0),
                    new Scalar(12, 210, 174),
                    new Scalar(200, 10, 10),
                    new Scalar(100, 100, 100)
            );
            Image.writeImage("lab_5_flood.png",image);
            image = Image.readImage(FIRST_IMAGE_NAME);
            ImageSegmentation.flood(
                    image,
                    new Point(0, 0),
                    null,
                    null,
                    null
            );
            Image.writeImage("lab_5_flood_null.png",image);
        } catch (Exception e) {
            log.error("Exception thrown: {}", e.getMessage());
        }
    }

    @Test
    public void testStepUpDownPyramid() {
        try {
            log.info("TEST - testStepUpDownPyramid");
            log.info("---------------------------------------");
            ImageAPI imageAPI = new ImageAPI();
            Mat image = Image.readImage(FIRST_IMAGE_NAME);
            log.info("Image read successfully");
            ImageSegmentation.stepUpDownPyramid(image, 10);
            Image.writeImage("lab_5_step_down.png",image);
        } catch (Exception e) {
            log.error("Exception thrown: {}", e.getMessage());
        }
    }

    @Test
    public void testDefineRectangles() {
        try {
            log.info("TEST - testDefineRectangles");
            log.info("---------------------------------------");
            ImageAPI imageAPI = new ImageAPI();
            Mat image = Image.readImage(SECOND_IMAGE_NAME);
            log.info("Image read successfully");
            int count = ImageSegmentation.defineRectangles(image, 138, 138);
            log.info(count + " rectangles found with height - " + 138 + " width - " + 138);
            Image.writeImage("lab_5_define_result.png", image);
        } catch (Exception e) {
            log.error("Exception thrown: {}", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void testObjectsEdgeIdentification(int ratio) {
        try {
            log.info("TEST - testObjectsEdgeIdentification with ratio=" + ratio);
            log.info("---------------------------------------");
            ImageAPI imageAPI = new ImageAPI();
            Mat image = Image.readImage(THIRD_IMAGE_NAME);
            log.info("Image read successfully");
            Mat result = ImageSegmentation.objectsEdgeIdentification(image, ratio);
            Image.writeImage("lab_6_ratio_" + ratio + "_result.png", result);
        } catch (IOException e){
            log.error("IOException thrown: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Exception thrown: {}", e.getMessage());
        }
    }
}
