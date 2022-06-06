package com.deBijenkorf.ImageService.service.resize;

import com.deBijenkorf.ImageService.config.ImageTypeConfig;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Class responsible to do the Fill operation on an image
 */
public class Fill implements ResizeType{

    /**
     * Resize the image based on the specified configurations.
     * If the resizing is greater than the actual image, then the excess image is filled with the color defined in the properties
     * @param originalImage to be resized
     * @param properties to be used for the resizing
     * @return new image with the resize applied
     */
    @Override
    public BufferedImage resize(BufferedImage originalImage, ImageTypeConfig.Properties properties) {
        BufferedImage fillImage = new BufferedImage(properties.getWidth(), properties.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = fillImage.createGraphics();
        graphics.setPaint (Color.decode(properties.getFillColor())); // set the default color on the entire new layer
        graphics.drawImage(originalImage, 0, 0, null); // draw image on top of the new layer
        graphics.dispose();

        return fillImage;
    }
}
