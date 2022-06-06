package com.deBijenkorf.ImageService.service;

import com.deBijenkorf.ImageService.config.ImageTypeConfig;
import com.deBijenkorf.ImageService.entity.ScaleType;
import com.deBijenkorf.ImageService.service.resize.Crop;
import com.deBijenkorf.ImageService.service.resize.Fill;
import com.deBijenkorf.ImageService.service.resize.ResizeType;
import com.deBijenkorf.ImageService.service.resize.Skew;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

/**
 * Service responsible to resize an image based on the provided configurations
 */
@Service
public class ResizeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    /**
     * Delegates the resizing to the specific resizing type based on the properties provided
     * @param originalImage to be resized
     * @param properties to be used for the resizing
     * @return new resized image
     */
    public BufferedImage resize (BufferedImage originalImage, ImageTypeConfig.Properties properties) {
        //Default type
        ResizeType resizeType = new Skew();

        if (properties.getScaleType().equals(ScaleType.FILL)){
            resizeType = new Fill();
        } else if (properties.getScaleType().equals(ScaleType.CROP)){
            resizeType = new Crop();
        } else if (properties.getScaleType().equals(ScaleType.SKEW)){
            resizeType = new Skew();
        }
         return resizeType.resize(originalImage, properties);
    }


}
