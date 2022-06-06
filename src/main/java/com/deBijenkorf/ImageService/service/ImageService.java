package com.deBijenkorf.ImageService.service;


import com.deBijenkorf.ImageService.config.ImageTypeConfig;
import com.deBijenkorf.ImageService.entity.ImageWrapper;
import com.deBijenkorf.ImageService.exceptions.SourceImageException;
import com.deBijenkorf.ImageService.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Service image - all the image optimization and flush logic
 */
@Service
public class ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    @Value ("${source.root.url}")
    private String urlImages;

    @Autowired
    private ResizeService resizeService;

    @Autowired
    private AwsService awsService;

    @Autowired
    private ValidationService validationService;

    /**
     * Upon request the image service will download the original image from the source and serve
     * it back to the client
     * @param fileName unique filename and/or relative path to identify the original image
     * @param predefinedTypeName name of the definition type, for example, thumbnail
     * @return optimized image
     */
    public ImageWrapper requestOptimizedImage(String fileName, String predefinedTypeName) {
        LOGGER.info("Starting optimizeImage.....");

        //basic validation check
        ImageTypeConfig.Properties properties = validationService.getProperties(predefinedTypeName);
        validationService.validateProperties(properties);
        String type = properties.getType().toString();

        // to-do
        // logic to extract four chars from the image name to decode the target folder of the image
        // update filename accordingly with the path

        LOGGER.info("Getting optimized image...... ");
        BufferedImage optimizedImage = awsService.getImageFromBucket(fileName,predefinedTypeName);

        if (optimizedImage != null){
            LOGGER.info("Optimized image found in bucket. Returning....");
            return ImageUtils.convertImageToImageWrapper(optimizedImage, fileName, type);
        } else{
            LOGGER.info("Getting original image.....");
            BufferedImage original =  awsService.getImageFromBucket(fileName, "original");

            if (original == null){
                LOGGER.info("Original image not found.....");
                original =  downloadImage(fileName, type);
            }

            LOGGER.info("Optimizing original image.....");
            BufferedImage resized = resizeService.resize(original, properties);
            BufferedImage compressed = ImageUtils.compressImage(resized,type, properties.getQuality());

            LOGGER.info("Upload optimized image to correspondent bucket.....");
            awsService.uploadImageToBucket(compressed, predefinedTypeName, type, fileName, false);

            return ImageUtils.convertImageToImageWrapper(compressed,fileName, type);
        }

    }

    /**
     * Download image from url source
     * @param fileName unique filename and/or relative path to identify the original image
     * @param type extension of the image
     * @return downloaded image
     */
    private BufferedImage downloadImage(String fileName, String type) throws SourceImageException {
        try {
            LOGGER.info("DownloadImage - downloading image from source.");

            URL url = new URL(urlImages + fileName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0");
            BufferedImage bImage = ImageIO.read(connection.getInputStream());

            awsService.uploadImageToBucket(bImage, "original", type, fileName, false);

            LOGGER.info("DownloadImage - download completed.");
            return bImage;

        } catch (IOException URLException) {
            throw new SourceImageException("downloadOriginalImage", "INFO", "Requested source image does not exist");
        }
    }

    /**
     * Logic to delete image from the bucket
     * @param fileName unique filename and/or relative path to identify the original image
     * @param predefinedTypeName name of the definition type, for example, thumbnail
     * @return name of file removed
     */
    public String flushImage (String fileName, String predefinedTypeName) {
        LOGGER.info("FlushImage - deleting image.....");

        if (predefinedTypeName.equals("original")){
            // to-do
            // delete all optimized images for this reference
            return fileName + " all references removed.";
        } else {
            awsService.flushImage(fileName, predefinedTypeName);
            return fileName + " removed from /" + predefinedTypeName;
        }
    }


}
