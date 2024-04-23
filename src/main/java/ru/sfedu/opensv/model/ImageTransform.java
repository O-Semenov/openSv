package ru.sfedu.opensv.model;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

import java.util.ArrayList;
import java.util.List;

public class ImageTransform {

    // path 1
    public static Mat useSobel(Mat srcImage, int dx, int dy) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(srcImage, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat dstSobel = new Mat();
        Imgproc.Sobel(grayImage, dstSobel, CvType.CV_32F, dx, dy);
        return dstSobel;
    }

    public static Mat useLaplace(Mat srcImage) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(srcImage, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat dstLaplace = new Mat();
        Imgproc.Laplacian(srcImage, dstLaplace, CvType.CV_32F);
        return dstLaplace;
    }
    // path 2
    public static Mat flip(Mat srcImage, boolean horizontal, boolean vertical) {
        Mat output = new Mat();
        if (horizontal & vertical) {
            Core.flip(srcImage, output, -1);
            return output;
        }
        if (horizontal) {
            Core.flip(srcImage, output, 1);
        }
        if (vertical) {
            Core.flip(srcImage, output, 0);
        }
        return output;
    }

    public static Mat concat(List<Mat> srcImages, boolean horizontal, boolean vertical) {
        Mat output = new Mat();
        if (horizontal) {
            Core.hconcat(srcImages, output);
        }
        if (vertical) {
            Core.vconcat(srcImages, output);
        }
        return output;
    }

    public static Mat repeat(Mat srcImage, int numberVertical, int numberHorizontal) {
        Mat output = new Mat();
        Core.repeat(srcImage, numberVertical, numberHorizontal ,output);
        return  output;
    }

    public static Mat resize(Mat srcImage, Size newSize) {
        Mat output = new Mat();
        Imgproc.resize(srcImage, output, newSize);
        return  output;
    }
    // path 3
    public static Mat turn(Mat srcImage, int angle, boolean trim) {
        Mat output = new Mat();
        Point center = new Point((double) srcImage.width() / 2, (double) srcImage.height() / 2);
        double zoom = 1;
        if (trim) {
            zoom += 0.7;
        }
        Mat rotationMat = Imgproc.getRotationMatrix2D(center, angle, zoom);
        Imgproc.warpAffine(
                srcImage,
                output,
                rotationMat,
                new Size(srcImage.width(),srcImage.height()),
                Imgproc.INTER_LINEAR,
                Core.BORDER_TRANSPARENT,
                new Scalar(0,0,0,255)
        );
        return output;
    }
    // path 4
    public static Mat shift(Mat srcImage, int value, boolean horizontal, boolean vertical) {
        Mat output = new Mat();
        Mat transformMat = new Mat( 2, 3, CvType.CV_64FC1 );
        transformMat.put(
                0,
                0,
                1,
                0,
                value * ((horizontal) ? 1 : 0),
                0,
                1,
                value * ((vertical) ? 1 : 0)
        );
        Imgproc.warpAffine(srcImage, output, transformMat, srcImage.size());
        return output;
    }
    // path 5
    public static Mat perspective(Mat srcImage, List<Point> corners) {
        Mat output = new Mat();
        List<Point> target = new ArrayList<Point>();
        target.add(new Point(0, 0));
        target.add(new Point(srcImage.cols(), 0));
        target.add(new Point(0, srcImage.rows()));
        target.add(new Point(srcImage.cols(), srcImage.rows()));
        Mat cornersMat = Converters.vector_Point2f_to_Mat(corners);
        Mat targetMat = Converters.vector_Point2f_to_Mat(target);
        Mat transformMat = Imgproc.getPerspectiveTransform(cornersMat, targetMat);
        Imgproc.warpPerspective(srcImage, output, transformMat, srcImage.size());
        return output;
    }
}
