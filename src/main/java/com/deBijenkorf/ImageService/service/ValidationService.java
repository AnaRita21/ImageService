package com.deBijenkorf.ImageService.service;

import com.deBijenkorf.ImageService.config.ImageTypeConfig;
import com.deBijenkorf.ImageService.entity.ScaleType;
import com.deBijenkorf.ImageService.exceptions.PredefinedImageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service validation to properties
 */
@Service
public class ValidationService {

    @Autowired
    private ImageTypeConfig imageTypeConfig;

    /**
     * Get properties for a predefined type image
     * @param predefinedTypeName predefined type name in the URL
     * @return image type properties
     */
    public ImageTypeConfig.Properties getProperties(String predefinedTypeName){
        ImageTypeConfig.Properties properties = imageTypeConfig.getProperties().get(predefinedTypeName);
        return properties;
    }

    /**
     * Basic validations to the properties
     * @param properties image type properties
     */

    public void validateProperties(ImageTypeConfig.Properties properties){
        if (properties == null){
            throw new PredefinedImageException("validateProperties", "INFO", "The requested predefined image type does not exist.");
        }
    }
}
