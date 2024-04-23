package ru.sfedu.opensv.model;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.sfedu.opensv.Constants;
import ru.sfedu.opensv.utils.ConfigurationUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;

public class Image {

    public static void showImage(Mat m, String title){
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( m.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte [] b = new byte[bufferSize];
        m.get(0,0,b);
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        ImageIcon icon=new ImageIcon(image);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(image.getWidth(null)+50, image.getHeight(null)+50);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setTitle(title);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static byte[] convertImageToJavaMatrix(Mat image) throws IOException {
        int totalBytes = (int) (image.total() * image.elemSize());
        byte buffer[] = new byte[totalBytes];
        image.get(0, 0, buffer);
        return buffer;
    }

    public static Mat dropImageChannel(int numChannel, Mat image) throws IOException {
        byte buffer[] = convertImageToJavaMatrix(image);
        int totalBytes = (int) (image.total() * image.elemSize());
        for (int i = 0; i < totalBytes; i+=image.channels()) {
            buffer[i + numChannel] = 0;
        }
        image.put(0, 0, buffer);
        return  image;
    }
    public static Mat readImage(String imageName) throws IOException {
        String imageDirPath = ConfigurationUtil.getConfigurationEntry(Constants.IMG_INPUT_PATH);
        return Imgcodecs.imread(imageDirPath + imageName);
    }
    public static void writeImage(String imageName, Mat image) throws IOException {
        String imageDirPath = ConfigurationUtil.getConfigurationEntry(Constants.IMG_OUTPUT_PATH);
        Imgcodecs.imwrite(imageDirPath + imageName, image);
    }
}
