package ru.sfedu.opensv.model;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import ru.sfedu.opensv.ImageAPI;
import ru.sfedu.opensv.ImageAPITest;

import java.util.ArrayList;
import java.util.List;

public class ImageTransformTest extends TestCase {

    public static String FIRST_IMAGE_NAME = "sample2.png";
    public static String SECOND_IMAGE_NAME = "sample3.png";
    private static final Logger log = LogManager.getLogger(ImageAPITest.class);

    public void testSobelAndLaplace() {
        try {
            log.info("TEST - testSobelAndLaplace");
            log.info("---------------------------------------");
            ImageAPI imageAPI = new ImageAPI();
            Mat image = Image.readImage(FIRST_IMAGE_NAME);
            log.info("Image read successfully");
            Image.writeImage("lab_3_use_sobel_x.png", ImageTransform.useSobel(image, 1, 0));
            Image.writeImage("lab_3_use_sobel_y.png", ImageTransform.useSobel(image, 0, 1));
            Image.writeImage("lab_3_use_sobel_x_y.png", ImageTransform.useSobel(image, 1, 1));
            Image.writeImage("lab_3_use_laplace.png", ImageTransform.useLaplace(image));
        } catch (Exception e) {
            log.error("Exception thrown: {}", e.getMessage());
        }
    }

    public void testFlipConcatRepeatResize() {
        try {
            log.info("TEST - testFlipConcatRepeatResize");
            log.info("---------------------------------------");
            ImageAPI imageAPI = new ImageAPI();
            ArrayList<Mat> imageList = new ArrayList<>();
            Mat image = Image.readImage(FIRST_IMAGE_NAME);
            Mat image2 = Image.readImage(SECOND_IMAGE_NAME);
            imageList.add(image);
            imageList.add(image2);
            log.info("Images read successfully");
            Image.writeImage("lab_3_flip_vertical.png", ImageTransform.flip(image, false, true));
            Image.writeImage("lab_3_concat_horizontal.png", ImageTransform.concat(imageList, true, false));
            Image.writeImage("lab_3_repeat_horizontal.png", ImageTransform.repeat(image, 1, 2));
            Image.writeImage("lab_3_resize_100_100.png", ImageTransform.resize(image, new Size(100, 100)));
        } catch (Exception e) {
            log.error("Exception thrown: {}", e.getMessage());
        }
    }

    public void testTurn() {
        try {
            log.info("TEST - testTurn");
            log.info("---------------------------------------");
            ImageAPI imageAPI = new ImageAPI();
            Mat image = Image.readImage(FIRST_IMAGE_NAME);
            log.info("Image read successfully");
            Image.writeImage("lab_3_turn_without_trim.png", ImageTransform.turn(image, 15, false));
            Image.writeImage("lab_3_turn_with_trim.png", ImageTransform.turn(image, -15, true));
        } catch (Exception e) {
            log.error("Exception thrown: {}", e.getMessage());
        }
    }

    public void testShift() {
        try {
            log.info("TEST - testShift");
            log.info("---------------------------------------");
            ImageAPI imageAPI = new ImageAPI();
            Mat image = Image.readImage(FIRST_IMAGE_NAME);
            log.info("Image read successfully");
            Image.writeImage("lab_3_shift_horizontal.png", ImageTransform.shift(image, 100, true,false));
            Image.writeImage("lab_3_shift_vertical.png", ImageTransform.shift(image, -150, false,true));
        } catch (Exception e) {
            log.error("Exception thrown: {}", e.getMessage());
        }
    }

    public void testPerspective() {
        try {
            log.info("TEST - testPerspective");
            log.info("---------------------------------------");
            ImageAPI imageAPI = new ImageAPI();
            Mat image = Image.readImage(FIRST_IMAGE_NAME);
            log.info("Image read successfully");
            List<Point> corners = new ArrayList<Point>();
            corners.add(new Point(-200, 0));
            corners.add(new Point(image.cols() + 200, 0));
            corners.add(new Point(0, image.rows()));
            corners.add(new Point(image.cols(), image.rows()));
            Image.writeImage("lab_3_perspective_up.png", ImageTransform.perspective(image, corners));
            corners = new ArrayList<Point>();
            corners.add(new Point(0, 0));
            corners.add(new Point(image.cols(), 0));
            corners.add(new Point(-200, image.rows()));
            corners.add(new Point(image.cols() + 200, image.rows()));
            Image.writeImage("lab_3_perspective_down.png", ImageTransform.perspective(image, corners));
        } catch (Exception e) {
            log.error("Exception thrown: {}", e.getMessage());
        }
    }
}
