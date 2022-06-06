package com.deBijenkorf.ImageService.utils;

import com.deBijenkorf.ImageService.entity.ImageWrapper;
import com.deBijenkorf.ImageService.exceptions.GenericException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * Image utilities to handle image operations
 */
public class ImageUtils {

    /**
     * Delegates the resizing to the specific resizing type based on the properties provided
     * @param resized image to compress
     * @param type to compress to
     * @param qualityGrade to set to
     * @return new compressed image
     */
    public static BufferedImage compressImage(BufferedImage resized, String type, int qualityGrade)  {
        try {
            File compressedImageFile = new File("compressed_image" + type);
            OutputStream os = new FileOutputStream(compressedImageFile);

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(type);
            ImageWriter writer = writers.next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(os);

            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();

            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            Float quality = (float) qualityGrade / 100;
            param.setCompressionQuality(quality);

            writer.write(null, new IIOImage(resized, null, null), param);

            os.close();
            ios.close();
            writer.dispose();

            return ImageIO.read(compressedImageFile);
        } catch (Exception e) {
            throw new GenericException("compressImage", "ERROR", e.getMessage());
        }
    }

    /**
     * Converts image to byte array
     * @param image image to convert
     * @param filename unique filename and/or relative path to identify the original image
     * @param type to convert to
     * @return new Image wrapper
     */
    public static ImageWrapper convertImageToImageWrapper (BufferedImage image,String filename, String type){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image , type, byteArrayOutputStream);

            return new ImageWrapper(byteArrayOutputStream.toByteArray(), filename.split("\\.")[0] + "." + type);
        } catch (IOException e) {
            throw new GenericException("convertImageToByteArray", "ERROR", e.getMessage());
        }
    }
}
