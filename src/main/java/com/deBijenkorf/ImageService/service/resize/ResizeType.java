package com.deBijenkorf.ImageService.service.resize;

import com.deBijenkorf.ImageService.config.ImageTypeConfig;

import java.awt.image.BufferedImage;

public interface ResizeType {

    /**
     * Resize the image based on the specified configurations.
     * @param originalImage to be resized
     * @param properties to be used for the resizing
     * @return new image with the resize applied
     */
    public BufferedImage resize (BufferedImage originalImage, ImageTypeConfig.Properties properties);
}
