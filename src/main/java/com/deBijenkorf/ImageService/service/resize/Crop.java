package com.deBijenkorf.ImageService.service.resize;

import com.deBijenkorf.ImageService.config.ImageTypeConfig;
import java.awt.image.BufferedImage;

/**
 * Class responsible to do the Crop operation on an image
 */
public class Crop implements ResizeType{

    /**
     * Resize the image based on the specified configurations.
     * The Crop operation removes any excess of the image if the target dimensions are smaller than the original one's
     * @param originalImage to be resized
     * @param properties to be used for the resizing
     * @return new image with the resize applied
     */
    @Override
    public BufferedImage resize(BufferedImage originalImage, ImageTypeConfig.Properties properties) {
        BufferedImage resizedImg = originalImage.getSubimage(0, 0, properties.getWidth(), properties.getHeight());
        return resizedImg;
    }
}
