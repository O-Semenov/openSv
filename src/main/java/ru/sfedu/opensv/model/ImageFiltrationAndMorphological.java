package ru.sfedu.opensv.model;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class ImageFiltrationAndMorphological {

    public static void filter(String imageName, int kernelSize) throws IOException {
        Mat image = Image.readImage(imageName);
        Mat outputImage = new Mat();

        // Усреднение пикселей
        Imgproc.blur(image, outputImage, new Size(kernelSize, kernelSize));
        Image.writeImage("lab_4_blur_filter_" + kernelSize + ".png", outputImage);
        // Гауссовский фильтр
        Imgproc.GaussianBlur(image, outputImage, new Size(kernelSize, kernelSize), 0);
        Image.writeImage("lab_4_gaussian_blur_filter_" + kernelSize + ".png", outputImage);
        // Медианное сглаживание
        Imgproc.medianBlur(image, outputImage, kernelSize);
        Image.writeImage("lab_4_median_blur_filter_" + kernelSize + ".png", outputImage);
        // Двусторонняя фильтрация
        Imgproc.bilateralFilter(image, outputImage, kernelSize, 400, 170);
        Image.writeImage("lab_4_bilateral_filter_" + kernelSize + ".png", outputImage);
    }

    public static void morphology(String imageName, int kernelSize, int morph) throws IOException {
        Mat image = Image.readImage(imageName);
        Mat outputImage = new Mat();
        Mat element = Imgproc.getStructuringElement(morph, new Size(kernelSize, kernelSize));
        // Размытие
        Imgproc.erode(image, outputImage, element);
        Image.writeImage("lab_4_morphology_erode_ks_" + kernelSize + "_morph_" + morph + ".png", outputImage);
        // Расширение
        Imgproc.dilate(image, outputImage, element);
        Image.writeImage("lab_4_morphology_dilate_ks_" + kernelSize + "_morph_" + morph + ".png", outputImage);
    }
}
