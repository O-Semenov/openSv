package ru.sfedu.opensv.model;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ImageSegmentation {

    public static void flood(Mat image, Point seedPoint, Scalar scalarVal,  Scalar loDiff, Scalar upDiff) {
        if (scalarVal == null) {
            scalarVal = random();
        }
        if (loDiff == null) {
            loDiff = random();
        }
        if (upDiff == null) {
            upDiff = random();
        }

        Imgproc.floodFill(
                image,
                new Mat(),
                seedPoint,
                scalarVal,
                new Rect(new Point(0, 0), new Point(100, 100)),
                loDiff,
                upDiff,
                4
        );
    }

    private static Scalar random() {
        return  new Scalar(
                (int) (Math.random() * 256),
                (int) (Math.random() * 256),
                (int) (Math.random() * 256)
        );
    }

    public static void stepUpDownPyramid(Mat image, int count) {
        for (int i = 0; i < count; i++) {
            Imgproc.pyrUp(image, image);
            Imgproc.pyrDown(image, image);
        }
    }

    public static int defineRectangles(Mat srcImage, int height, int width) throws IOException {
        Mat grayMatImage = new Mat();
        int rectanglesCount = 0;
        Imgproc.cvtColor(srcImage, grayMatImage, Imgproc.COLOR_BGR2GRAY);

        Mat denoisingImage = new Mat();
        Photo.fastNlMeansDenoising(grayMatImage, denoisingImage);
        Image.writeImage("lab_5_denoisingImage.png",denoisingImage);

        Mat histogramEqualizationImageMat = new Mat();
        Imgproc.equalizeHist(denoisingImage, histogramEqualizationImageMat);
        Image.writeImage("lab_5_histogramImage.png", histogramEqualizationImageMat);

        Mat morhologicalOpeningImageMat = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5,5));
        Imgproc.morphologyEx(histogramEqualizationImageMat, morhologicalOpeningImageMat, Imgproc.MORPH_RECT,  kernel);
        Image.writeImage("lab_5_morphologicalOpening.png", morhologicalOpeningImageMat);

        Mat substractImage = new Mat();
        Core.subtract(histogramEqualizationImageMat, morhologicalOpeningImageMat, substractImage);
        Image.writeImage("lab_5_substractImage.png", substractImage);

        Mat thresholdImage = new Mat();
        double threshold = Imgproc.threshold(
                denoisingImage,
                thresholdImage,
                100,
                255,
                Imgproc.THRESH_BINARY
        );
//        thresholdImage.convertTo(thresholdImage, CvType.CV_8U);
//        Image.writeImage("lab_5_thresholdImage.png", thresholdImage);
//
//        Mat edgeImage = new Mat();
//        Imgproc.Canny(thresholdImage, edgeImage, threshold, threshold * 3, 3, true);
//        Image.writeImage("lab_5_edgeImage.png", edgeImage);
//
//        Mat dilatedImage = new Mat();
//        Imgproc.dilate(thresholdImage, dilatedImage, kernel);
//        Image.writeImage("lab_5_dilation.png", edgeImage);

        ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(substractImage, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        contours.sort(Collections.reverseOrder(Comparator.comparing(Imgproc::contourArea)));
        ArrayList<MatOfPoint> rectanglesContours = new ArrayList<MatOfPoint>();
        for (int i = 0; i < contours.size(); i++) {
            if (i == 0) {
                continue;
            }
            MatOfPoint contour = contours.get(i);
            MatOfPoint2f point2f = new MatOfPoint2f();
            MatOfPoint2f approxContour2f = new MatOfPoint2f();
            MatOfPoint approxContour = new MatOfPoint();

            contour.convertTo(point2f, CvType.CV_32FC2);

            double arcLength = Imgproc.arcLength(point2f, true);
            Imgproc.approxPolyDP(point2f, approxContour2f, 0.01 * arcLength, true);

            approxContour2f.convertTo(approxContour, CvType.CV_32S);
            Rect rect = Imgproc.boundingRect(approxContour);

            if (rect.height != height || rect.width != width) {
                continue;
            }
            rectanglesCount += 1;
            rectanglesContours.add(contour);
            Imgproc.putText(
                    srcImage,
                    "" + contour.hashCode(),
                    new Point(rect.x + 1 , rect.y + (rect.height / 1.5)),
                    Imgproc.FONT_ITALIC,
                    0.5,
                    new Scalar(0, 0, 0),
                    1
            );
        }
        Imgproc.drawContours(srcImage, rectanglesContours, -1, new Scalar(0, 255, 0), 3);
        return rectanglesCount;
    }

    public static Mat objectsEdgeIdentification(Mat srcImage, int ratio)
    {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(srcImage, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat detectedEdges = new Mat();
        Imgproc.blur(grayImage, detectedEdges, new Size(3, 3));
        Mat thresholdImage = new Mat();
        double threshold = Imgproc.threshold(grayImage, thresholdImage, 0, 255, Imgproc.THRESH_OTSU);
        Imgproc.Canny(detectedEdges, detectedEdges, threshold, threshold * ratio);
        return  detectedEdges;
    }
}
