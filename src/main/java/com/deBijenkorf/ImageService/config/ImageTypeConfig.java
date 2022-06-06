package com.deBijenkorf.ImageService.config;

import com.deBijenkorf.ImageService.entity.ImageType;
import com.deBijenkorf.ImageService.entity.ScaleType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Class responsible to get the predefined image type configurations
 */
@Configuration
@Data
@ConfigurationProperties (prefix = "image-types")
public class ImageTypeConfig {

    private Map<String, Properties> properties;

    /**
     * Class with fields that constitute a predefined image type
     */
    @Data
    public static class Properties {
        private int height;
        private int width;
        private int quality;
        private ScaleType scaleType;
        private String fillColor;
        private ImageType type;
    }
}
