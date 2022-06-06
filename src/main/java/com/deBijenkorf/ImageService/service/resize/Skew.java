package com.deBijenkorf.ImageService.service.resize;

import com.deBijenkorf.ImageService.config.ImageTypeConfig;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Class responsible to do the Skew operation on an image
 */
public class Skew implements ResizeType{

    /**
     * Resize the image based on the specified configurations.
     * If the resizing is smaller than the actual image, then the image is shrunk (scaled down)
     * @param originalImage to be resized
     * @param properties to be used for the resizing
     * @return new image with the resize applied
     */
    @Override
    public BufferedImage resize(BufferedImage originalImage, ImageTypeConfig.Properties properties) {
        BufferedImage resizedImage = new BufferedImage(properties.getWidth(), properties.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(originalImage, 0, 0, properties.getWidth(), properties.getHeight(), null);
        graphics.dispose();

        return resizedImage;
    }
}
